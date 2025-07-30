package ru.shaxowskiy.apigateway.exception;

public class MissingOrInvalidAuthorizationHeaderException extends RuntimeException {
    public MissingOrInvalidAuthorizationHeaderException(String message) {
        super(message);
    }
}
