package hacathon.hacathon.domain.attendance.domain;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    DUTY("근무중"),
    LEAVE_WORK("퇴근"),
    REST("휴식중"),
    HOME("재택근무중");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
