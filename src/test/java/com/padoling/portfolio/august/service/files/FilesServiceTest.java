package com.padoling.portfolio.august.service.files;

import com.padoling.portfolio.august.domain.files.Files;
import com.padoling.portfolio.august.domain.files.FilesRepository;
import com.padoling.portfolio.august.web.dto.file.FileResponseDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesServiceTest {

    @Autowired
    private FilesService filesService;

    @Autowired
    private FilesRepository filesRepository;

    @After
    public void cleanup() {
        filesRepository.deleteAll();
    }

    @Test
    public void testFilesSave() {
        //given
        String name = "file";
        String originalFilename = "test.jpg";
        String contentType = "image/jpeg";
        String file = "test data";
        MultipartFile multipartFile = new MockMultipartFile(name, originalFilename, contentType, file.getBytes());

        //when
        Long id = null;
        try {
            id = filesService.save(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //then
        Files image = filesRepository.findAll().get(0);
        assertThat(image).isNotNull();
        assertThat(image.getId()).isEqualTo(id);
        assertThat(image.getOriginalName()).isEqualTo(originalFilename);
        assertThat(image.getContentType()).isEqualTo(contentType);
    }

    @Test
    public void testLoadResource() throws IOException {
        //given
        String name = "file";
        String originalFilename = "test.jpg";
        String contentType = "image/jpeg";
        String file = "test data";
        MultipartFile multipartFile = new MockMultipartFile(name, originalFilename, contentType, file.getBytes());

        Long id = null;
        try {
            id = filesService.save(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //when
        Resource resource = null;
        FileResponseDto responseDto = filesService.findById(id);
        try {
            resource = filesService.loadResource(responseDto.getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //then
        assertThat(resource).isNotNull();
        assertThat(resource.getFilename()).isEqualTo(responseDto.getSavedName());
        assertThat(resource.contentLength()).isEqualTo(responseDto.getSize());
    }
}
