package hacathon.hacathon.domain.admin.service;

import hacathon.hacathon.domain.admin.web.dto.request.AdminSettingDutyTimeRequestDto;
import hacathon.hacathon.domain.admin.web.dto.request.AdminUpdateUserRequestDto;
import hacathon.hacathon.domain.admin.web.dto.response.AdminUserDetailResponseDto;
import hacathon.hacathon.domain.attendance.domain.Attendance;
import hacathon.hacathon.domain.attendance.domain.AttendanceQuerydslRepository;
import hacathon.hacathon.domain.attendance.domain.AttendanceRepository;
import hacathon.hacathon.domain.attendance.exception.AttendanceException;
import hacathon.hacathon.domain.attendance.exception.AttendanceExceptionType;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import hacathon.hacathon.domain.mapPoint.domain.MapPointRepository;
import hacathon.hacathon.domain.mapPoint.web.dto.response.MapPointResponseDto;
import hacathon.hacathon.domain.user.domain.Authority;
import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.exception.UserException;
import hacathon.hacathon.domain.user.exception.UserExceptionType;
import hacathon.hacathon.global.security.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final MapPointRepository mapPointRepository;
    private final AttendanceQuerydslRepository attendanceQuerydslRepository;

    public AdminUserDetailResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));

        Attendance attendance = attendanceQuerydslRepository.getAttendanceByUser(user)
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_START_ATTENDANCE_YET));

        return AdminUserDetailResponseDto.builder()
                .attendance(attendance)
                .build();
    }

    public void updateUserName(Long id, AdminUpdateUserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));
        validateAdmin(user);
        user.updateUserName(requestDto.getName());
    }

    @Transactional(readOnly = true)
    public List<AttendanceAllResponseDto> getOvertimeUsers() {
        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> attendance.getWorkTime().isBefore(attendance.getTodayTotalWorkTime()))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public void settingDutyTime(AdminSettingDutyTimeRequestDto requestDto) {
        User user = userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));

        validateAdmin(user);

        LocalTime localTime = LocalTime.of(requestDto.getHour(), requestDto.getMinute());
        user.settingDutyTime(localTime);
    }

    public List<AttendanceAllResponseDto> getLateUsers() {
        User user = userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));

        validateAdmin(user);

        //TODO : 포인트
        if(user.getDutyTime().equals(LocalTime.of(0, 0))) {
            throw new UserException(UserExceptionType.NOT_SETTING_DUTY_TIME);
        }

        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> user.getDutyTime().isBefore(attendance.getStartTime()))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MapPointResponseDto> getMapPointAll() {
        return mapPointRepository.findAll().stream()
                .map(MapPointResponseDto::new)
                .collect(Collectors.toList());
    }

    private void validateAdmin(User user) {
        if(!user.getAuthority().equals(Authority.ROLE_ADMIN)) {
            throw new UserException(UserExceptionType.FORBIDDEN);
        }
    }
}
