package hacathon.hacathon.domain.attendance.domain;

import hacathon.hacathon.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus = AttendanceStatus.EGRESSION;

    private final LocalTime todayTotalWorkTime = LocalTime.of(8, 0);

    private LocalTime startTime;

    @Builder
    public Attendance(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void addAttendanceGoWork() {
        this.attendanceStatus = AttendanceStatus.DUTY;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addAttendance(this);
    }
}
