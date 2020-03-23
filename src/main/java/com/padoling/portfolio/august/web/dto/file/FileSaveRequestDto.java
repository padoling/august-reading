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

    private String fileName;
    private String savedName;
    private String filePath;

    public Files toEntity() {
        return Files.builder()
                .fileName(fileName)
                .savedName(savedName)
                .filePath(filePath)
                .build();
    }
}
