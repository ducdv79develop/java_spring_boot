package com.local.ducdv.api;

import com.local.ducdv.config.FileStorageProperties;
import com.local.ducdv.dto.ApiResponseDto;
import com.local.ducdv.util.FileDetailResponse;
import com.local.ducdv.util.FilesStorageHelper;
import com.local.ducdv.util.ResponseStatusCode;
import com.local.ducdv.util.StringCustomUtils;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/upload")
public class UploadCsvController {
    private final Logger logger = LoggerFactory.getLogger(UploadCsvController.class);
    @Autowired
    FilesStorageHelper fileStorageService;
    @PostMapping(value = "/csv", consumes = { "multipart/form-data" })
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            FileDetailResponse fileDetailResponse = (FileDetailResponse) fileStorageService.storeFile(file);
            FileInputStream fis = new FileInputStream(fileDetailResponse.getFilepath());
            CSVReader reader = new CSVReader(new InputStreamReader(fis, "SJIS"));
            List<String[]> rows = reader.readAll();
            reader.close();
            for (String[] row : rows) {
                System.out.println(row[0]);
////                System.out.println(StringCustomUtils.convertShiftJToUTF8(row[0]) + "," + StringCustomUtils.convertShiftJToUTF8(row[1]) + "," + StringCustomUtils.convertShiftJToUTF8(row[2]));
            }
            Path fileToDeletePath = Paths.get(fileDetailResponse.getDirectory() + "/" +  fileDetailResponse.getFilename());
            Files.delete(fileToDeletePath);

            Object[] data = {};
            ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
