package com.local.ducdv.util;

import com.local.ducdv.api.UploadCsvController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


public class StringCustomUtils {
    private static final Logger logger = LoggerFactory.getLogger(UploadCsvController.class);

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public static String convertUTF8ToShiftJ(String str) {
        try {
            byte[] b = str.getBytes();
            return new String(b, "SHIFT-JIS");
        } catch (Exception e) {
            logger.error("[convertUTF8ToShiftJ] Error: " + e.getMessage());
            return str;
        }
    }

    public static String convertShiftJToUTF8(String str) {
        try {
            byte[] b = str.getBytes();
            return new String(b, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("[convertShiftJToUTF8] Error: " + e.getMessage());
            return str;
        }
    }

}
