package hacathon.hacathon.domain.attendance.domain;

import hacathon.hacathon.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private AttendanceStatus attendanceStatus;

    private int todayWorkTime;
    private int nowTime;

    @Builder
    public Attendance(int todayWorkTime, int nowTime) {
        this.todayWorkTime = todayWorkTime;
        this.nowTime = nowTime;
    }

    public void addAttendanceGoWork() {
        this.attendanceStatus = AttendanceStatus.GO_WORK;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addAttendance(this);
    }
}
