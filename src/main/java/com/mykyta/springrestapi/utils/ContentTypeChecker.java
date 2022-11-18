package com.mykyta.springrestapi.utils;

import org.springframework.http.MediaType;

public class ContentTypeChecker {
    public static MediaType contentType(String filename) {
        String fileType = filename.substring(filename.length() - 3);
        switch (fileType) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
