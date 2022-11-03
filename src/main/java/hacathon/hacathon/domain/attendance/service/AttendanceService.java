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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public void createAttendance() {
        User user = validateUser();

        if(attendanceRepository.findByUser(user).isPresent()) {
            throw new AttendanceException(AttendanceExceptionType.ALREADY_DUTY);
        }

        Attendance attendance = Attendance.builder()
                .startTime(LocalTime.now())
                .today(LocalDate.now())
                .build();

        attendance.confirmUser(user);

        attendance.addAttendanceDuty();
        attendanceRepository.save(attendance);
    }

    @Transactional(readOnly = true)
    public AttendanceResponseDto getUserAttendance() {
        User user = validateUser();

        return attendanceRepository.findByUser(user)
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .map(attendance -> {
                    attendance.updateTimes(LocalTime.now());
                    return AttendanceResponseDto.builder().attendance(attendance).build();
                })
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_START_ATTENDANCE_YET));
    }

    @Transactional(readOnly = true)
    public List<AttendanceAllResponseDto> getDutyUserAttendances() {
        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> attendance.getAttendanceStatus().equals(AttendanceStatus.DUTY))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AttendanceAllResponseDto> getNotDutyUserAttendances() {
        return attendanceRepository.findAll().stream()
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .filter(attendance -> !attendance.getAttendanceStatus().equals(AttendanceStatus.DUTY))
                .map(AttendanceAllResponseDto::new)
                .collect(Collectors.toList());
    }

    public AttendanceResponseDto updateAttendanceStatus() {
        User user = validateUser();

        return attendanceRepository.findByUser(user)
                .filter(attendance -> attendance.getToday().compareTo(LocalDate.now()) == 0)
                .map(attendance -> {
                    attendance.addAttendanceLeaveWork();
                    attendance.updateTimes(LocalTime.now());
                    return AttendanceResponseDto.builder().attendance(attendance).build();
                })
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_START_ATTENDANCE_YET));
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void startRest() {
        Attendance attendance = validateUserAndAttendance();
        attendance.startRestTime(LocalTime.now());
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void endRest() {
        Attendance attendance = validateUserAndAttendance();
        LocalTime restTime = LocalTime.now().minus(attendance.getStartRestTime().getMinute(), ChronoUnit.MINUTES);
        attendance.updateTimes(restTime);
    }

    private Attendance validateUserAndAttendance() {
        User user = validateUser();

        return attendanceRepository.findByUser(user)
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_START_ATTENDANCE_YET));
    }

    private User validateUser() {
        return userRepository.findByName(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new UserException(UserExceptionType.REQUIRED_DO_LOGIN));
    }
}
