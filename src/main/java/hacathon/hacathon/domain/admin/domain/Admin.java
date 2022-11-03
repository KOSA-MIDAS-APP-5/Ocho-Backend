package hacathon.hacathon.domain.admin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String password;

    private LocalTime essentialGoWorkTime;

    @Builder
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
