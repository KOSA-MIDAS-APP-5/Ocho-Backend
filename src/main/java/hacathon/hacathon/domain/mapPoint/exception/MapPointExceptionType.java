package hacathon.hacathon.domain.mapPoint.exception;

import hacathon.hacathon.global.exception.application.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MapPointExceptionType implements BaseExceptionType {

    ALREADY_CREATE_MAP_POINT(800, HttpStatus.BAD_REQUEST, "좌표를 이미 등록했습니다."),
    NOT_EXISTS_MAP_POINT(801, HttpStatus.BAD_REQUEST, "좌표가 존재하지 않습니다.");

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
