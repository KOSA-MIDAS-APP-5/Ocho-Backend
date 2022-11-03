package hacathon.hacathon.global.exception.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseException(BaseException exception) {
        log.error("BaseException errorMessage(): {}", exception.getExceptionType().getErrorMessage());
        log.error("BaseException errorCode(): {}", exception.getExceptionType().getErrorCode());

        return new ResponseEntity(ExceptionDto.builder()
                .errorCode(exception.getExceptionType().getErrorCode())
                .errorMessage(exception.getExceptionType().getErrorMessage())
                .build(), exception.getExceptionType().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity hadleMemberException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ExceptionDto dto = ExceptionDto.builder()
                .errorCode(405)
                .errorMessage("잘못된 메서드 요청입니다.").build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity processValidationError(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
        }

        return new ResponseEntity(ExceptionDto.builder()
                .errorCode(700)
                .errorMessage(builder.toString())
                .build(), HttpStatus.BAD_REQUEST);

    }

    @Data
    static class ExceptionDto {
        private Integer errorCode;
        private String errorMessage;

        @Builder
        public ExceptionDto(Integer errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }
}
