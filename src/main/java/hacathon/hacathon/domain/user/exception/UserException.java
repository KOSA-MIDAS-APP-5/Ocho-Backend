package hacathon.hacathon.domain.user.exception;

import hacathon.hacathon.global.exception.application.BaseException;
import hacathon.hacathon.global.exception.application.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class UserException extends BaseException {

    private BaseExceptionType exceptionType;

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
