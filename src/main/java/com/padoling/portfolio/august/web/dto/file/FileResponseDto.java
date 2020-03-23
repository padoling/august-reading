package com.padoling.portfolio.august.web.dto.file;

import com.padoling.portfolio.august.domain.files.Files;
import lombok.Getter;

@Getter
public class FileResponseDto {

    private Long id;
    private String fileName;
    private String savedName;
    private String filePath;

    public FileResponseDto(Files entity) {
        this.id = entity.getId();
        this.fileName = entity.getFileName();
        this.savedName = entity.getSavedName();
        this.filePath = entity.getFilePath();
    }
}
