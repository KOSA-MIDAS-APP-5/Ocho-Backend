package hacathon.hacathon.domain.mapPoint.exception;

import hacathon.hacathon.global.exception.application.BaseException;
import hacathon.hacathon.global.exception.application.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class MapPointException extends BaseException {

    private BaseExceptionType exceptionType;
    @Override
    public BaseExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
