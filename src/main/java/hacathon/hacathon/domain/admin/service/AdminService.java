package hacathon.hacathon.domain.admin.service;

import hacathon.hacathon.domain.admin.web.dto.request.AdminCreateEssentialGoWorkTimeRequestDto;
import hacathon.hacathon.domain.admin.web.dto.request.AdminUpdateUserRequestDto;
import hacathon.hacathon.domain.attendance.domain.AttendanceRepository;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
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

    public void updateUser(Long id, AdminUpdateUserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_USER));
        validateAdmin(user);
        user.updateUser(requestDto.getName());
    }

    @Transactional(readOnly = true)
    public List<AttendanceAllResponseDto> getAttendancesByWorkTime() {
        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> attendance.getWorkTime().isBefore(attendance.getTodayTotalWorkTime()))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public void createEssentialGoWorkTime(AdminCreateEssentialGoWorkTimeRequestDto requestDto) {
        User user = userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));

        validateAdmin(user);

        LocalTime localTime = LocalTime.of(requestDto.getHour(), requestDto.getMinute());
        user.updateEssentialGoWorkTime(localTime);
    }

    public List<AttendanceAllResponseDto> getLateUserByEssentialGoWorkTime() {
        User user = userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));

        validateAdmin(user);

        if(user.getEssentialGoWorkTime() == null) {
            throw new UserException(UserExceptionType.NOT_CREATE_ESSENTIAL_GO_WORK_TIME);
        }

        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> user.getEssentialGoWorkTime().isBefore(attendance.getStartTime()))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    private void validateAdmin(User user) {
        if(!user.getAuthority().equals(Authority.ROLE_ADMIN)) {
            throw new UserException(UserExceptionType.FORBIDDEN);
        }
    }
}
