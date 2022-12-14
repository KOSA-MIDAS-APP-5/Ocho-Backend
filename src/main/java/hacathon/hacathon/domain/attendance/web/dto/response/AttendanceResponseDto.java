package hacathon.hacathon.domain.attendance.web.dto.response;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class AttendanceResponseDto {
    private final LocalTime startTime; //출근한 시간
    private final LocalTime workTime; //몇분동안 일했는지
    private final LocalTime remainingTime; //남은 시간
    private final String status; //근태 상태

    @Builder
    public AttendanceResponseDto(Attendance attendance) {
        this.status = attendance.getAttendanceStatus().getName();
        this.startTime = attendance.getStartTime();
        this.workTime = attendance.getWorkTime();
        this.remainingTime = attendance.getRemainingTime();
    }
}
