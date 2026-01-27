package com.punnawat.backend.expections;

import org.springframework.http.HttpStatus;

public class FileException extends BaseException {

    public FileException(String code, HttpStatus status) {
        super("file." + code, status);
    }

    // file is null or empty
    public static FileException isNull() {
        return new FileException("is_null", HttpStatus.BAD_REQUEST);
    }

    // file size too large
    public static FileException fileTooLarge() {
        return new FileException("too_large", HttpStatus.PAYLOAD_TOO_LARGE);
    }

    // content type missing
    public static FileException contentTypeMissing() {
        return new FileException("content_type_missing", HttpStatus.BAD_REQUEST);
    }

    // unsupported file type
    public static FileException unsupportedType() {
        return new FileException("unsupported_type", HttpStatus.BAD_REQUEST);
    }
}
