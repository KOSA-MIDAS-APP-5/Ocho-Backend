package hacathon.hacathon.domain.user.exception;

import hacathon.hacathon.global.exception.application.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionType implements BaseExceptionType {

    ALREADY_EXIST_NAME(600, HttpStatus.BAD_REQUEST, "이미 존재하는 이름입니다."),
    NOT_SIGNUP_NAME(601, HttpStatus.BAD_REQUEST, "가입되지 않은 이름입니다."),
    WRONG_PASSWORD(602, HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(603, HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    REQUIRED_DO_LOGIN(604, HttpStatus.BAD_REQUEST, "로그인이 필요합니다."),
    NOT_SETTING_DUTY_TIME(608, HttpStatus.BAD_REQUEST, "필수출근시간을 등록하지 않았습니다."),
    FORBIDDEN(609, HttpStatus.BAD_REQUEST, "권한이 부족합니다.");

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
