package com.motoo.server.domain.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ImageType {
    JPG, JPEG, GIF, PNG;

    public static boolean isImageType(String fileFormat) {
        boolean result = false;
        for (ImageType type : ImageType.values()) {
            fileFormat = fileFormat.toUpperCase();

            if(type.name().equals(fileFormat)){
                result = true;
            }
        }
        return result;
    }
}
