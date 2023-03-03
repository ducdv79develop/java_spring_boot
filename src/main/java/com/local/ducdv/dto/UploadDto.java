package com.local.ducdv.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadDto {
    @NotEmpty(message = "chon file di")
    private MultipartFile file;
}
