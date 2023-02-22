package com.local.ducdv.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponseDto {
    private Integer code;
    private String message;
    private Object[] data;

    public ApiResponseDto(Integer code, String message, Object[] data) {
        setCode(code);
        setMessage(message);
        setData(data);
    }
}
