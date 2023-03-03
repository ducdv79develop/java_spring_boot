package com.local.ducdv.service;

import com.local.ducdv.entity.User;
import com.local.ducdv.mapper.UserMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Service
public class UploadFileService {

    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void handelFileCsv(String filePath, String fullPath) throws IOException, CsvException, ParseException {
        FileInputStream fis = new FileInputStream(filePath);
        CSVReader reader = new CSVReader(new InputStreamReader(fis, "SJIS"));
        List<String[]> rows = reader.readAll();
        reader.close();
        int index = 0;
        for (String[] row : rows) {
            if (index == 0 || row.length < 4) {
                index++;
                continue;
            }
            User user = new User();
            user.setName(row[0]);
            user.setEmail(row[1]);
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(row[2]));
            user.setStatus(Objects.equals(row[3], "1"));
            userMapper.insertUserXml(user);

            System.out.println(row[0] + ", " + row[1] + ", " + row[2] + ", " + row[3]);
            index++;
        }

        Path fileToDeletePath = Paths.get(fullPath);
        Files.delete(fileToDeletePath);
    }
}
