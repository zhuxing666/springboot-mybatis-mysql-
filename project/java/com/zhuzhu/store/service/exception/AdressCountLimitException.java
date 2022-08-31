package com.zhuzhu.store.service.exception;

public class AdressCountLimitException extends ServiceException{
    public AdressCountLimitException() {
        super();
    }

    public AdressCountLimitException(String message) {
        super(message);
    }

    public AdressCountLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdressCountLimitException(Throwable cause) {
        super(cause);
    }

    protected AdressCountLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
