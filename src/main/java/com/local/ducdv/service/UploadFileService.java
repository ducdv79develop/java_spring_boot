package com.local.ducdv.service;

import com.local.ducdv.entity.User;
import com.local.ducdv.mapper.UserMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.constant.Constable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class UploadFileService {

    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void handelFileCsv(String filePath, String fullPath) throws IOException, CsvException, ParseException {
        FileInputStream fileStream = new FileInputStream(filePath);
        CSVReader reader = new CSVReader(new InputStreamReader(fileStream, "SJIS"));
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

    public void handleFileExcel(String filePath, String fullPath) throws IOException, ParseException {
        FileInputStream fileStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        // brear out header
        iterator.next();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            Double id = (Double) getValueByType(cellIterator.next());
            String name = (String) getValueByType(cellIterator.next());
            String email = (String) getValueByType(cellIterator.next());
            String birthdaySrt = (String) getValueByType(cellIterator.next());
            Boolean status = (Boolean) getValueByType(cellIterator.next());
            Date birthday = null;
            if (birthdaySrt != null) {
                birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdaySrt);
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setBirthday(birthday);
            user.setStatus(status);

            userMapper.insertUserXml(user);
        }

        workbook.close();
        fileStream.close();

        Path fileToDeletePath = Paths.get(fullPath);
        Files.delete(fileToDeletePath);
    }

    private Constable getValueByType(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case NUMERIC -> cell.getNumericCellValue();
            default -> null;
        };
    }
}
