package com.local.ducdv.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.local.ducdv.mapper.UserMapper;
import com.local.ducdv.model.UserModel;
import com.local.ducdv.util.CustomBaseFontPDF;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExportFileService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    public void handleExportCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        List<UserModel> userLists = userService.getUserList();

        StatefulBeanToCsv<UserModel> writer =
                new StatefulBeanToCsvBuilder<UserModel>
                        (response.getWriter())
                        .withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
                        .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false).build();

        writer.write(userLists);
    }

    public void handleExportPDF(HttpServletResponse response) throws IOException {


        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = CustomBaseFontPDF.fontArialUnicodeMs();
        font.setSize(20);
        Paragraph paragraph = new Paragraph("いつでもウェルネスオンラインショップ", font);

        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(5);
        document.add(paragraph);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{2, 3, 4, 2, 2});
        table.setSpacingBefore(5);

        executeHeaderTablePDF(table);
        executeBodyTablePDF(table);

        document.add(table);
        document.close();
    }

    public void handleExportExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("List Users");

        executeWriteHeaderExcel(workbook, sheet);
        executeWriteBodyExcel(workbook, sheet);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    private void executeHeaderTablePDF(PdfPTable table) throws IOException {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.DARK_GRAY);
        cell.setPadding(5);

        Font font = CustomBaseFontPDF.fontArialUnicodeMs();
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("NAME", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("EMAIL", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("BIRTHDAY", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("STATUS", font));
        table.addCell(cell);
    }

    private void executeBodyTablePDF(PdfPTable table) throws IOException {

        List<UserModel> userLists = userService.getUserList();

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        Font font = CustomBaseFontPDF.fontArialUnicodeMs();
        font.setColor(Color.BLACK);

        for (UserModel userModel : userLists) {

            table.addCell(String.valueOf(userModel.id));
            cell.setPhrase(new Phrase(userModel.name, font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(userModel.email, font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(userModel.birthday, font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(userModel.status), font));
            table.addCell(cell);
        }
    }

    private void executeWriteHeaderExcel(XSSFWorkbook workbook, XSSFSheet sheet) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        Row row = sheet.createRow(0);

        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeight(16);
        style.setFont(font);
        style.setFillBackgroundColor(IndexedColors.BLACK.index);
        style.setFillPattern(FillPatternType.BIG_SPOTS);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());

        createCell(sheet, row, 0, "ID", style);
        createCell(sheet, row, 1, "NAME", style);
        createCell(sheet, row, 2, "EMAIL", style);
        createCell(sheet, row, 3, "BIRTHDAY", style);
        createCell(sheet, row, 4, "STATUS", style);
    }
    private void executeWriteBodyExcel(XSSFWorkbook workbook, XSSFSheet sheet) {
        List<UserModel> userLists = userService.getUserList();

        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (UserModel user : userLists) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(sheet, row, columnCount++, user.id, style);
            createCell(sheet, row, columnCount++, user.name, style);
            createCell(sheet, row, columnCount++, user.email, style);
            createCell(sheet, row, columnCount++, user.birthday, style);
            createCell(sheet, row, columnCount++, user.status, style);
        }
    }


    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);

        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);

        } else if (valueOfCell instanceof Boolean) {
            cell.setCellValue((Boolean) valueOfCell);

        } else {
            cell.setCellValue((String) valueOfCell);
        }
        cell.setCellStyle(style);
    }
}
