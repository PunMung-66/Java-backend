package com.punnawat.backend.expections;

import org.springframework.http.HttpStatus;

public class UserException extends BaseException{

    public UserException(String code, HttpStatus status) {
        super("user." + code, status);
    }
    // All Requests

    // REQUEST
    public static UserException requestNull() {
        return new UserException("request.null", HttpStatus.EXPECTATION_FAILED);
    }
    public static UserException requestEmailNull() {
        return new UserException("request.email.null", HttpStatus.EXPECTATION_FAILED);
    }
    public static UserException requestPasswordNull() {
        return new UserException("request.password.null", HttpStatus.EXPECTATION_FAILED);
    }
    public static UserException requestNameNull() {
        return new UserException("request.name.null", HttpStatus.EXPECTATION_FAILED);
    }


    // CREATE SERVICE
    public static UserException createEmailNull() {
        return new UserException("create.email.null", HttpStatus.EXPECTATION_FAILED);
    }
    public static UserException createPasswordNull() {
        return new UserException("create.password.null", HttpStatus.EXPECTATION_FAILED);
    }
    public static UserException createNameNull() {
        return new UserException("create.name.null", HttpStatus.EXPECTATION_FAILED);
    }
    public static UserException createEmailDuplicated() {
        return new UserException("create.email.duplicated", HttpStatus.CONFLICT);
    }

    //VERIFY LOGIN REQUEST
    public static UserException verifyLoginEmailNull() {
        return new UserException("verifyLogin.email.null", HttpStatus.BAD_REQUEST);
    }

    public static UserException verifyLoginPasswordNull() {
        return new UserException("verifyLogin.password.null", HttpStatus.BAD_REQUEST);
    }

    public static UserException verifyLoginFailed() {
        return new UserException("verifyLogin.failed", HttpStatus.UNAUTHORIZED);
    }


}
