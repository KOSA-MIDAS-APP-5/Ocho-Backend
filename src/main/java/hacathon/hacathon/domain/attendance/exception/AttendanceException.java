package hacathon.hacathon.domain.attendance.exception;

import hacathon.hacathon.global.exception.application.BaseException;
import hacathon.hacathon.global.exception.application.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceException extends BaseException {

    private BaseExceptionType exceptionType;

    @Override
    public BaseExceptionType getExceptionType() {
        return this.exceptionType;
    }
}
