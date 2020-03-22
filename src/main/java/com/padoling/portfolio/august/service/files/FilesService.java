package com.padoling.portfolio.august.service.files;

import com.padoling.portfolio.august.domain.files.Files;
import com.padoling.portfolio.august.domain.files.FilesRepository;
import com.padoling.portfolio.august.util.UploadFileUtil;
import com.padoling.portfolio.august.web.dto.file.FileResponseDto;
import com.padoling.portfolio.august.web.dto.file.FileSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class FilesService {

    private final FilesRepository filesRepository;

    private Path rootPath = Paths.get("/home/ec2-user/app/step1/temp");

    @Transactional
    public Long save(MultipartFile file) throws Exception {
        try {
            if(file.isEmpty()) {
                throw new Exception("파일 저장 실패 : 빈 파일 " + file.getOriginalFilename());
            }
            FileSaveRequestDto requestDto = UploadFileUtil.saveFile(rootPath.toString(), file);
            return filesRepository.save(requestDto.toEntity()).getId();
        } catch (IOException e) {
            throw new Exception("파일 저장 실패 : 오류 " + file.getOriginalFilename(), e);
        }
    }

    public FileResponseDto findById(Long id) {
        Files entity = filesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id=" + id));

        return new FileResponseDto(entity);
    }

    public Resource loadResource(String fileName) throws Exception {
        try {
            Path file = rootPath.resolve(fileName);
            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new Exception("파일 읽기 실패 : 오류 " + fileName, e);
        }
    }
}
