package com.motoo.server.global.util;

import com.motoo.server.global.error.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

@Component
@Slf4j
public class FileUploadUtil {

    @Value("${file.upload.path}")
    private String rootPath;

    public String upload(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());

        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + extension;

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + originalFilename);
            }
            if (originalFilename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + originalFilename);
            }
            Path resolve = calcPath().resolve(newFileName);
            file.transferTo(resolve);

            return resolve.toString();
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFilename);
        }
    }

    /**
     * rootPath/yyyy/mm/dd 디렉토리
     */
    private Path calcPath() {

        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        Path path = Paths.get(rootPath, year, month, day);

        try {
            path = Files.createDirectories(path);
        } catch (Exception e) {
            throw new StorageException(
                    "Could not create the directory where the uploaded files will be stored."
                            + path.toString());
        }

        return path;
    }

}
