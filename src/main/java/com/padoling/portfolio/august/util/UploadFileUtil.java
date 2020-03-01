package com.padoling.portfolio.august.util;

import com.padoling.portfolio.august.web.dto.file.FileSaveRequestDto;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadFileUtil {

    public static FileSaveRequestDto saveFile(String uploadPath, MultipartFile file) throws IOException{
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String savedName = UUID.randomUUID() + extension;

        File target = new File(uploadPath + File.separator, savedName);

        FileCopyUtils.copy(file.getBytes(), target);

        return FileSaveRequestDto.builder()
                .originalName(originalName)
                .savedName(savedName)
                .filePath(uploadPath + File.separator + savedName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
    }
}
