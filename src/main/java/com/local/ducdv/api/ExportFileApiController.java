package com.local.ducdv.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.local.ducdv.service.ExportFileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/export")
public class ExportFileApiController {

    private final Logger logger = LoggerFactory.getLogger(ExportFileApiController.class);

    @Autowired
    ExportFileService exportFileService;

    @GetMapping(value = "/csv")
    public void exportCsv(HttpServletResponse response) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String filename = "CSV_export_users_" + date + ".csv";

            response.setContentType("text/csv; charset=SJIS");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

            exportFileService.handleExportCsv(response);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @GetMapping(value = "/pdf")
    public void exportPdf(HttpServletResponse response) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String filename = "PDF_export_users_" + date + ".pdf";

            response.setContentType("application/pdf; charset=SJIS");
            response.setCharacterEncoding("SJIS");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

            exportFileService.handleExportPDF(response);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @GetMapping(value = "/excel")
    public void exportExcel(HttpServletResponse response) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String filename = "EXCEL_export_users_" + date + ".xlsx";

            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

            exportFileService.handleExportExcel(response);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
