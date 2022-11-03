package hacathon.hacathon.domain.attendance.exception;

import hacathon.hacathon.global.exception.application.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AttendanceExceptionType implements BaseExceptionType {

    NOT_START_ATTENDANCE_YET(605, HttpStatus.BAD_REQUEST, "출석을 등록하지 않았습니다."),
    ALREADY_DUTY(701, HttpStatus.BAD_REQUEST, "이미 출근중입니다.");

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
