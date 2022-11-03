package hacathon.hacathon.domain.attendance.validate;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import hacathon.hacathon.domain.attendance.domain.AttendanceQuerydslRepository;
import hacathon.hacathon.domain.attendance.domain.AttendanceStatus;
import hacathon.hacathon.domain.attendance.exception.AttendanceException;
import hacathon.hacathon.domain.attendance.exception.AttendanceExceptionType;
import hacathon.hacathon.domain.user.domain.User;
import hacathon.hacathon.domain.user.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AttendanceValidator {
    private final AttendanceQuerydslRepository attendanceQuerydslRepository;
    private final UserValidator userValidator;

    public Attendance validateAttendanceByUser() {
        User user = validateUser();

        return attendanceQuerydslRepository.getAttendanceByUser(user)
                .orElseThrow(() -> new AttendanceException(AttendanceExceptionType.NOT_START_ATTENDANCE_YET));
    }

    public User validateUser() {
        return userValidator.validateUser();
    }

    public boolean isLeaveWorkUser(Attendance attendance) {
        return attendance.getAttendanceStatus().equals(AttendanceStatus.LEAVE_WORK);
    }
}
