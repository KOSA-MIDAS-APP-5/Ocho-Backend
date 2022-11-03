package hacathon.hacathon.domain.admin.web.dto.response;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class AdminUserDetailResponseDto {
    private final double latitude;
    private final double longitude;
    private final String name;
    private final String status;
    private final LocalTime workTime;
    private final LocalTime remainingTime;
    //TODO : 소속부서

    @Builder
    public AdminUserDetailResponseDto(Attendance attendance) {
        this.latitude = attendance.getUser().getPoint().getLatitude();
        this.longitude = attendance.getUser().getPoint().getLongitude();
        this.name = attendance.getUser().getName();
        this.status = attendance.getAttendanceStatus().getName();
        this.workTime = attendance.getWorkTime();
        this.remainingTime = attendance.getRemainingTime();
    }
}
