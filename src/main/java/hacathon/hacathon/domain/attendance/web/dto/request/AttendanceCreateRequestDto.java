package hacathon.hacathon.domain.attendance.web.dto.request;

import hacathon.hacathon.domain.attendance.domain.Attendance;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AttendanceCreateRequestDto {

    private int todayWorkTime;
    private int nowTime;

    @Builder
    public Attendance toEntity() {
        return Attendance.builder()
                .todayWorkTime(todayWorkTime)
                .nowTime(nowTime)
                .build();
    }
}
