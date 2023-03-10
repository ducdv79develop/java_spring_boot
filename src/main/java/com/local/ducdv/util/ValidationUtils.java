package com.local.ducdv.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationUtils {

    public static List<String> getErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> {
                    var defaultMessage = error.getDefaultMessage();
                    if (error instanceof FieldError fieldError) {
                        return String.format("%s : %s", fieldError.getField(), defaultMessage);
                    } else {
                        return defaultMessage;
                    }
                })
                .collect(Collectors.toList());
    }
}
