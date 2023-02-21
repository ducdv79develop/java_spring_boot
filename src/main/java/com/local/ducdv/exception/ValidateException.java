package com.local.ducdv.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ValidateException extends RuntimeException {
    private int code;
    private String message;
}
