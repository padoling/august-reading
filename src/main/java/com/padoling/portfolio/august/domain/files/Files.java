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

    private String fileName;

    private String savedName;

    private String filePath;

    @Builder
    public Files(String fileName, String savedName, String filePath) {
        this.fileName = fileName;
        this.savedName = savedName;
        this.filePath = filePath;
    }
}
