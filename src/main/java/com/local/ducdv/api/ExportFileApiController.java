package com.local.ducdv.api;

import com.local.ducdv.service.ExportFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

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

            exportFileService.handleExportCsv(filename, response);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @GetMapping(value = "/pdf")
    public void exportPdf(HttpServletResponse response) {
        try {
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String filename = "PDF_export_users_" + date + ".pdf";

            exportFileService.handleExportPDF(filename, response);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
