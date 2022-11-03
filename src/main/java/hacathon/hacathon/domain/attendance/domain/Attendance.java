package hacathon.hacathon.domain.attendance.domain;

import hacathon.hacathon.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private LocalTime startTime;

    private LocalTime workTime;

    private LocalTime remainingTime;

    private LocalDate today;

    @Builder
    public Attendance(LocalTime startTime, LocalDate today) {
        this.startTime = startTime;
        this.today = today;
    }

    public void addAttendanceGoWork() {
        this.attendanceStatus = AttendanceStatus.DUTY;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addAttendance(this);
    }

    public void addAttendanceLeaveWork() {
        this.attendanceStatus = AttendanceStatus.LEAVE_WORK;
    }

    public void updateTimes(LocalTime now) {
        this.workTime = now.minus(this.startTime.getMinute(), ChronoUnit.MINUTES).withNano(0);
        this.remainingTime = this.todayTotalWorkTime.minus(this.workTime.getMinute(), ChronoUnit.MINUTES);
    }
}
