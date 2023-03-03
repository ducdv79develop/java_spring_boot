package com.local.ducdv.util;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

import java.io.IOException;

public class CustomBaseFontPDF {
    private static final String ARIAL_UNICODE_MS_PATH = "src/main/resources/static/font/arial-unicode-ms.ttf";

    public static Font fontArialUnicodeMs() throws IOException {
        BaseFont baseFont =  BaseFont.createFont(ARIAL_UNICODE_MS_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return new Font(baseFont);
    }
}
