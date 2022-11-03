package hacathon.hacathon.domain.attendance.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hacathon.hacathon.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import static hacathon.hacathon.domain.attendance.domain.QAttendance.attendance;

@Repository
@RequiredArgsConstructor
public class AttendanceQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Attendance> getAttendanceByUser(User user) {
        return Optional.ofNullable(queryFactory
                .selectFrom(attendance)
                .where(attendance.user.eq(user).and(attendance.today.eq(LocalDate.now())))
                .fetchOne());
    }
}
