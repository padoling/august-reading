package com.padoling.portfolio.august.domain.files;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesRepositoryTest {

    @Autowired
    private FilesRepository filesRepository;

    @After
    public void cleanUp() {
        filesRepository.deleteAll();
    }

    @Test
    public void testLoadingFiles() {
        //given
        String originalName = "test originalName";
        String savedName = "test savedName";
        String filePath = "test filePath";

        filesRepository.save(Files.builder()
                .originalName(originalName)
                .savedName(savedName)
                .filePath(filePath)
                .build()
        );

        //when
        List<Files> files = filesRepository.findAll();

        //then
        Files file = files.get(0);
        assertThat(file.getOriginalName()).isEqualTo(originalName);
        assertThat(file.getSavedName()).isEqualTo(savedName);
        assertThat(file.getFilePath()).isEqualTo(filePath);
    }
}
