package hacathon.hacathon.domain.attendance.domain;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    DUTY("근무중"),
    LEAVE_WORK("퇴근"),
    VACATION("휴가중"),
    HOME("재택근무중");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
