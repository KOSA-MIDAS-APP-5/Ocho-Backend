package hacathon.hacathon.domain.attendance.domain;

import hacathon.hacathon.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus = AttendanceStatus.LEAVE_WORK;

    private final LocalTime todayTotalWorkTime = LocalTime.of(8, 0);

    private LocalTime startTime = LocalTime.of(0, 0, 0);

    private LocalTime workTime = LocalTime.of(0, 0, 0);

    private LocalTime remainingTime = LocalTime.of(0, 0, 0);

    private LocalDate today;

    private LocalTime startRestTime = LocalTime.of(0, 0, 0);

    @Builder
    public Attendance(LocalTime startTime, LocalDate today) {
        this.startTime = startTime;
        this.today = today;
    }

    public void addAttendanceDuty() {
        this.attendanceStatus = AttendanceStatus.DUTY;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addAttendance(this);
    }

    public void addAttendanceLeaveWork() {
        this.attendanceStatus = AttendanceStatus.LEAVE_WORK;
    }

    public void addAttendanceRest() {
        this.attendanceStatus = AttendanceStatus.REST;
    }

    public void updateTimes(LocalTime now) {
        this.workTime = now.minusHours(this.startTime.getHour()).withNano(0);
        this.workTime = this.workTime.minusMinutes(this.startTime.getMinute()).withNano(0);
        this.workTime = this.workTime.minusSeconds(this.startTime.getSecond()).withNano(0);
        this.remainingTime = this.todayTotalWorkTime.minusHours(this.workTime.getHour()).withNano(0);
        this.remainingTime = this.remainingTime.minusMinutes(this.workTime.getMinute()).withNano(0);
        this.remainingTime = this.remainingTime.minusSeconds(this.workTime.getSecond()).withNano(0);
    }

    public void startRestTime(LocalTime startRestTime) {
        this.startRestTime = startRestTime;
    }
}
