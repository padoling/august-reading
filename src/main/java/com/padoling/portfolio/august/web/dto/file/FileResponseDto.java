package com.padoling.portfolio.august.web.dto.file;

import com.padoling.portfolio.august.domain.files.Files;
import lombok.Getter;

@Getter
public class FileResponseDto {

    private Long id;
    private String originalName;
    private String savedName;
    private String filePath;
    private String contentType;
    private Long size;

    public FileResponseDto(Files entity) {
        this.id = entity.getId();
        this.originalName = entity.getOriginalName();
        this.savedName = entity.getSavedName();
        this.filePath = entity.getFilePath();
        this.contentType = entity.getContentType();
        this.size = entity.getSize();
    }
}
