package hacathon.hacathon.domain.attendance.domain;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    NOT_GO_WORK("출근하지 않음"),
    DUTY("근무중"),
    LEAVE_WORK("퇴근"),
    VACATION("휴가중"),
    EGRESSION("외출중");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
