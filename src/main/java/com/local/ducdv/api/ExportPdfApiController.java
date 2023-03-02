package com.local.ducdv.api;

import com.local.ducdv.model.PostModel;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExportPdfApiController {
    private final Logger logger = LoggerFactory.getLogger(ExportPdfApiController.class);


    @GetMapping(value = "/export/pdf")
    public void export(HttpServletResponse response) {
        try {
            String filename = "post_list.pdf";
            List<PostModel> postModels = getPost();
            BaseFont bf = BaseFont.createFont("E:\\dvd-leaning\\java\\Spring Boot\\ducdv\\ducdv\\src\\main\\resources\\static\\font\\arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            response.setContentType("application/pdf; charset=SJIS");
            response.setCharacterEncoding("SJIS");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"");

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

//            Font fontTiltle = FontFactory.getFont(FontFactory.TIMES);
            Font fontTiltle = new Font(bf);
            fontTiltle.setSize(20);

            Paragraph paragraph1 = new Paragraph("いつでもウェルネスオンラインショップ", fontTiltle);

            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph1);
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100f);
            table.setWidths(new int[] {3,3,3,3});
            table.setSpacingBefore(5);

            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(CMYKColor.DARK_GRAY);
            cell.setPadding(5);

//            Font font = FontFactory.getFont(FontFactory.TIMES);
            Font font = new Font(bf);
            font.setColor(CMYKColor.WHITE);

            cell.setPhrase(new Phrase("いつでもウェルネス", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("いつでもウェルネス", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("いつでもウェルネス", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("いつでもウェルネス", font));
            table.addCell(cell);

            Font fontItem = new Font(bf);
            fontItem.setColor(CMYKColor.BLACK);
            PdfPCell cellItem = new PdfPCell();
            cellItem.setBackgroundColor(CMYKColor.WHITE);
            for (PostModel post: postModels) {
                table.addCell(String.valueOf(post.getId()));
                cellItem.setPhrase(new Phrase(post.getTitle(), fontItem));
                table.addCell(cellItem);
                cellItem.setPhrase(new Phrase(post.getContent(), fontItem));
                table.addCell(cellItem);
                cellItem.setPhrase(new Phrase(post.getAuthor(), fontItem));
                table.addCell(cellItem);
            }
            document.add(table);
            document.close();
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
