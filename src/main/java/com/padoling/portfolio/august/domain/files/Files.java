package com.padoling.portfolio.august.domain.files;

import com.padoling.portfolio.august.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Files extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    private String originalName;

    private String savedName;

    private String filePath;

    private String contentType;

    private Long size;

    @Builder
    public Files(String originalName, String savedName, String filePath, String contentType, Long size) {
        this.originalName = originalName;
        this.savedName = savedName;
        this.filePath = filePath;
        this.contentType = contentType;
        this.size = size;
    }
}
