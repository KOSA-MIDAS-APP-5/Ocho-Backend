package hacathon.hacathon.domain.mapPoint.domain;

import hacathon.hacathon.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapPointRepository extends JpaRepository<MapPoint, Long> {
    boolean existsByUser(User user);
    Optional<MapPoint> findByUser(User user);
}
