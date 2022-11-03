package hacathon.hacathon.domain.admin.service;

import hacathon.hacathon.domain.admin.web.dto.request.AdminSettingDutyTimeRequestDto;
import hacathon.hacathon.domain.admin.web.dto.request.AdminUpdateUserRequestDto;
import hacathon.hacathon.domain.admin.web.dto.response.AdminUserDetailResponseDto;
import hacathon.hacathon.domain.attendance.domain.Attendance;
import hacathon.hacathon.domain.attendance.domain.AttendanceQuerydslRepository;
import hacathon.hacathon.domain.attendance.domain.AttendanceRepository;
import hacathon.hacathon.domain.attendance.exception.AttendanceException;
import hacathon.hacathon.domain.attendance.exception.AttendanceExceptionType;
import hacathon.hacathon.domain.mapPoint.domain.MapPointRepository;
import hacathon.hacathon.domain.mapPoint.web.dto.response.MapPointResponseDto;
import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.exception.UserException;
import hacathon.hacathon.domain.user.exception.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final MapPointRepository mapPointRepository;
    private final AttendanceQuerydslRepository attendanceQuerydslRepository;

    @Transactional(readOnly = true)
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

        user.updateUserName(requestDto.getName());
    }

    @Transactional(readOnly = true)
    public List<MapPointResponseDto> getMapPointAll() {
        return mapPointRepository.findAll().stream()
                .map(MapPointResponseDto::new)
                .collect(Collectors.toList());
    }
}
