package com.local.ducdv.service;

import com.local.ducdv.mapper.UserMapper;
import com.local.ducdv.model.UserModel;
import com.local.ducdv.util.CustomBaseFontPDF;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
public class ExportFileService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    public void handleExportCsv(String filename, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        List<UserModel> userLists = userService.getUserList();

        response.setContentType("text/csv; charset=SJIS");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<UserModel> writer =
                new StatefulBeanToCsvBuilder<UserModel>
                        (response.getWriter())
                        .withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
                        .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false).build();

        writer.write(userLists);
    }

    public void handleExportPDF(String filename, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf; charset=SJIS");
        response.setCharacterEncoding("SJIS");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

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

        executeHeaderTable(table);
        executeBodyTable(table);

        document.add(table);
        document.close();
    }

    private void executeHeaderTable(PdfPTable table) throws IOException {
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

    private void executeBodyTable(PdfPTable table) throws IOException {

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
}
