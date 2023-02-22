package com.local.ducdv.dto;

import com.local.ducdv.util.ResponseStatusCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponseValidationDto {
    private Integer code;
    private String message;
    private Object[] errors;

    public ApiResponseValidationDto(Object[] errors) {
        setCode(ResponseStatusCode.UNPROCESSABLE_ENTITY);
        setMessage("Request is invalid");
        setErrors(errors);
    }
}
