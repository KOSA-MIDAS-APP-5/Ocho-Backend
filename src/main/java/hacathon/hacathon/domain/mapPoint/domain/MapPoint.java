package hacathon.hacathon.domain.mapPoint.domain;

import hacathon.hacathon.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MapPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;

    private double hardness;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public MapPoint(double latitude, double hardness) {
        this.latitude = latitude;
        this.hardness = hardness;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addMapPoint(this);
    }
}
