package com.local.ducdv.api;

import com.local.ducdv.dto.ApiResponseDto;
import com.local.ducdv.mapper.PostMapper;
import com.local.ducdv.model.PostModel;
import com.local.ducdv.util.FileDetailResponse;
import com.local.ducdv.util.FilesStorageHelper;
import com.local.ducdv.util.ResponseStatusCode;
import com.local.ducdv.util.StringCustomUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UploadCsvController {
    private final Logger logger = LoggerFactory.getLogger(UploadCsvController.class);
    @Autowired
    private PostMapper postMapper;
    @Autowired
    FilesStorageHelper fileStorageService;
    @PostMapping(value = "/upload/csv", consumes = { "multipart/form-data" })
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            FileDetailResponse fileDetailResponse = (FileDetailResponse) fileStorageService.storeFile(file);
            if (fileDetailResponse.getFilepath() == null) {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
            FileInputStream fis = new FileInputStream(fileDetailResponse.getFilepath());
            CSVReader reader = new CSVReader(new InputStreamReader(fis, "SJIS"));
            List<String[]> rows = reader.readAll();
            reader.close();
            for (String[] row : rows) {
                StringBuilder print = new StringBuilder();
                for(String item : row) {
                    print.append(StringCustomUtils.convertShiftJToUTF8(item)).append(",");
                }
                System.out.println(print);
            }
            Path fileToDeletePath = Paths.get(fileDetailResponse.getDirectory() + "/" +  fileDetailResponse.getFilename());
            Files.delete(fileToDeletePath);

            Object[] data = rows.toArray();
            ApiResponseDto apiResponseDto = new ApiResponseDto(ResponseStatusCode.OK, "success", data);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/export/csv")
    public void export(HttpServletResponse response) {
        try {
            // set file name and content type
            String filename = "post_list.csv";

            response.setContentType("text/csv; charset=SJIS");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"");

            // create a csv writer
            StatefulBeanToCsv<PostModel> writer =
                    new StatefulBeanToCsvBuilder<PostModel>
                            (response.getWriter())
                            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                            .withOrderedResults(false).build();

            // write all employees to csv file
            writer.write(getPost());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private List<PostModel> getPost() {
        return Arrays.asList(
                new PostModel(1, "1 誠に勝手ながら", "1 いつでもウェルネスオンラインショップ", "Ducdv1"),
                new PostModel(2, "2 誠に勝手ながら", "2 いつでもウェルネスオンラインショップ", "Ducdv1"),
                new PostModel(3, "3 誠に勝手ながら", "3 いつでもウェルネスオンラインショップ", "Ducdv1"),
                new PostModel(4, "4 誠に勝手ながら", "4 いつでもウェルネスオンラインショップ", "Ducdv2"),
                new PostModel(5, "5 誠に勝手ながら", "5 いつでもウェルネスオンラインショップ", "Ducdv2"),
                new PostModel(6, "6 誠に勝手ながら", "6 いつでもウェルネスオンラインショップ", "Ducdv2")
        );
    }

}
