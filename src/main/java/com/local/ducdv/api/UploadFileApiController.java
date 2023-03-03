package com.local.ducdv.api;

import com.local.ducdv.dto.ApiResponseDto;
import com.local.ducdv.service.UploadFileService;
import com.local.ducdv.util.FileDetailResponse;
import com.local.ducdv.util.FilesStorageHelper;
import com.local.ducdv.util.ResponseStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadFileApiController {
    private final Logger logger = LoggerFactory.getLogger(UploadFileApiController.class);
    @Autowired
    FilesStorageHelper fileStorageService;
    @Autowired
    UploadFileService uploadFileService;

    @PostMapping(value = "/csv", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            FileDetailResponse fileDetailResponse = (FileDetailResponse) fileStorageService.storeFile(file);
            if (fileDetailResponse.getFilepath() == null) {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
            String filePath = fileDetailResponse.getFilepath();
            String fullFilePath = fileDetailResponse.getDirectory() + "/" + fileDetailResponse.getFilename();
            uploadFileService.handelFileCsv(filePath, fullFilePath);

            Object[] data = {};
            ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
