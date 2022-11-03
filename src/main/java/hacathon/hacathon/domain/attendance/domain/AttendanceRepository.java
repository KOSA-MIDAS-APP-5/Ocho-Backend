package hacathon.hacathon.domain.attendance.domain;

import hacathon.hacathon.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUser(User user);
}
