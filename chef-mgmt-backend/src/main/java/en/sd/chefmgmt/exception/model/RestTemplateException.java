package en.sd.chefmgmt.exception.model;

import lombok.Getter;

@Getter
public class RestTemplateException extends RuntimeException {

    private final String code;

    public RestTemplateException(ExceptionCode exceptionCode, Object... args) {
        super(String.format(exceptionCode.getMessage(), args));
        this.code = exceptionCode.getCode();
    }
}