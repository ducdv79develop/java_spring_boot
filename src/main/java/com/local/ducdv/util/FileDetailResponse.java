package com.local.ducdv.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileDetailResponse {
    private String filename;
    private String directory;
    private String filepath;
    FileDetailResponse(String filename, String directory, String filepath) {
        setFilename(filename);
        setDirectory(directory);
        setFilepath(filepath);
    }
}
