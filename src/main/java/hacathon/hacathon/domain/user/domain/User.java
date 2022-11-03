package hacathon.hacathon.domain.user.domain;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "user")
    private Attendance attendance;

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
        this.attendance = attendance;
    }

    public void updateUser(String name) {
        this.name = name;
    }
}
