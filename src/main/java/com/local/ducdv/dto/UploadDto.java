package com.local.ducdv.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UploadDto {
    @NotEmpty(message = "chon file di")
    private MultipartFile file;
}
