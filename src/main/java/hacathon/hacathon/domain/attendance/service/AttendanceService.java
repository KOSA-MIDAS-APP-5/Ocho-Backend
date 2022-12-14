package hacathon.hacathon.domain.attendance.service;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import hacathon.hacathon.domain.attendance.domain.AttendanceQuerydslRepository;
import hacathon.hacathon.domain.attendance.domain.AttendanceRepository;
import hacathon.hacathon.domain.attendance.domain.AttendanceStatus;
import hacathon.hacathon.domain.attendance.exception.AttendanceException;
import hacathon.hacathon.domain.attendance.exception.AttendanceExceptionType;
import hacathon.hacathon.domain.attendance.validate.AttendanceValidator;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceAllResponseDto;
import hacathon.hacathon.domain.attendance.web.dto.response.AttendanceResponseDto;
import hacathon.hacathon.domain.mapPoint.service.MapPointService;
import hacathon.hacathon.domain.mapPoint.web.dto.request.MapPointCreateRequestDto;
import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.validate.UserValidator;
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
    private final AttendanceValidator attendanceValidator;
    private final AttendanceQuerydslRepository attendanceQuerydslRepository;
    private final MapPointService mapPointService;

    public void createAttendance(MapPointCreateRequestDto requestDto) {
        User user = attendanceValidator.validateUser();

        if(attendanceQuerydslRepository.getAttendanceByUser(user).isPresent()) {
            throw new AttendanceException(AttendanceExceptionType.ALREADY_DUTY);
        }

        mapPointService.createMapPoint(requestDto, user);

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
        User user = attendanceValidator.validateUser();

        Attendance attendance = attendanceQuerydslRepository.getAttendanceByUser(user)
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_START_ATTENDANCE_YET));

        if(attendance.getAttendanceStatus().equals(AttendanceStatus.DUTY)) {
            attendance.updateTimes(LocalTime.now().withNano(0));
        }

        return AttendanceResponseDto.builder()
                .attendance(attendance)
                .build();
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
        User user = attendanceValidator.validateUser();

        return attendanceQuerydslRepository.getAttendanceByUser(user)
                .map(attendance -> {
                    attendance.addAttendanceLeaveWork();
                    attendance.updateTimes(LocalTime.now().withNano(0));
                    return AttendanceResponseDto.builder().attendance(attendance).build();
                })
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_START_ATTENDANCE_YET));
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void startRest() {
        Attendance attendance = attendanceValidator.validateAttendanceByUser();

        if(!attendanceValidator.isLeaveWorkUser(attendance)) {
            attendance.addAttendanceRest();
        }
        attendance.startRestTime(LocalTime.now());
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void endRest() {
        Attendance attendance = attendanceValidator.validateAttendanceByUser();
        LocalTime restTime = LocalTime.now().minus(attendance.getStartRestTime().getMinute(), ChronoUnit.SECONDS);
        attendance.updateTimes(restTime.withNano(0));

        if(!attendanceValidator.isLeaveWorkUser(attendance)) {
            attendance.addAttendanceDuty();
        }
    }
}
