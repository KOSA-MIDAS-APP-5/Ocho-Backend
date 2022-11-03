package hacathon.hacathon.global.exception.application;

public abstract class BaseException extends RuntimeException {
    public abstract BaseExceptionType getExceptionType();
}
