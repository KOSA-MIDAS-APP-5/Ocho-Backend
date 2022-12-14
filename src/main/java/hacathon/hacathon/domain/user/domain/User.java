package hacathon.hacathon.domain.user.domain;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import hacathon.hacathon.domain.mapPoint.domain.MapPoint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Attendance> attendances = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private MapPoint point;

    private LocalTime dutyTime = LocalTime.of(0, 0);

    @Builder
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void addAuthorityUser() {
        this.authority = Authority.ROLE_USER;
    }

    public void encodedPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void addAttendance(Attendance attendance) {
        this.attendances.add(attendance);
    }

    public void updateUserName(String name) {
        this.name = name;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, this.password);
    }

    public void settingDutyTime(LocalTime settingDutyTime) {
        this.dutyTime = settingDutyTime;
    }

    public void addMapPoint(MapPoint point) {
        this.point = point;
    }
}
