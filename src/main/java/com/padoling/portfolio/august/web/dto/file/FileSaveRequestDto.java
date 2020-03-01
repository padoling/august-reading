package com.padoling.portfolio.august.web.dto.file;

import com.padoling.portfolio.august.domain.files.Files;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileSaveRequestDto {

    private String originalName;
    private String savedName;
    private String filePath;
    private String contentType;
    private Long size;

    public Files toEntity() {
        return Files.builder()
                .originalName(originalName)
                .savedName(savedName)
                .filePath(filePath)
                .contentType(contentType)
                .size(size)
                .build();
    }
}
