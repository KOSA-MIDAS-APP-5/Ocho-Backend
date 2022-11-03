package hacathon.hacathon.domain.attendance.service;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import hacathon.hacathon.domain.attendance.domain.AttendanceRepository;
import hacathon.hacathon.domain.attendance.domain.AttendanceStatus;
import hacathon.hacathon.domain.attendance.exception.AttendanceException;
import hacathon.hacathon.domain.attendance.exception.AttendanceExceptionType;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceResponseDto;
import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.domain.UserRepository;
import hacathon.hacathon.domain.user.exception.UserException;
import hacathon.hacathon.domain.user.exception.UserExceptionType;
import hacathon.hacathon.global.security.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public void createAttendance() {
        Attendance attendance = Attendance.builder()
                .startTime(LocalTime.now())
                .today(LocalDate.now())
                .build();

        attendance.confirmUser(userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN)));

        attendance.addAttendanceGoWork();
        attendanceRepository.save(attendance);
    }

    @Transactional(readOnly = true)
    public AttendanceResponseDto getAttendance() {
        User user = userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));

        return attendanceRepository.findByUser(user)
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .map(attendance -> {
                    attendance.updateTimes(LocalTime.now());
                    return AttendanceResponseDto.builder().attendance(attendance).build();
                })
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_FOUND_ATTENDANCE));
    }

    @Transactional(readOnly = true)
    public List<AttendanceAllResponseDto> getAttendanceDuty() {
        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> attendance.getAttendanceStatus().equals(AttendanceStatus.DUTY))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AttendanceAllResponseDto> getAttendanceNotDuty() {
        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> !attendance.getAttendanceStatus().equals(AttendanceStatus.DUTY))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public AttendanceResponseDto updateAttendanceStatus() {
        User user = userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));

        return attendanceRepository.findByUser(user)
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .map(attendance -> {
                    attendance.addAttendanceLeaveWork();
                    attendance.updateTimes(LocalTime.now());
                    return AttendanceResponseDto.builder().attendance(attendance).build();
                })
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_FOUND_ATTENDANCE));
    }
}
