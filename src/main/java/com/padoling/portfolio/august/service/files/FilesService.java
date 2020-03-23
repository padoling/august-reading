package com.padoling.portfolio.august.service.files;

import com.padoling.portfolio.august.domain.files.Files;
import com.padoling.portfolio.august.domain.files.FilesRepository;
import com.padoling.portfolio.august.util.S3Uploader;
import com.padoling.portfolio.august.web.dto.file.FileResponseDto;
import com.padoling.portfolio.august.web.dto.file.FileSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FilesService {

    private final FilesRepository filesRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public String save(MultipartFile file) throws Exception {
        try {
            if(file.isEmpty()) {
                throw new Exception("파일 저장 실패 : 빈 파일 " + file.getOriginalFilename());
            }
            String savedName = s3Uploader.upload(file);
            FileSaveRequestDto requestDto = FileSaveRequestDto.builder()
                    .fileName(file.getOriginalFilename())
                    .savedName(savedName)
                    .filePath("https://" + S3Uploader.CLOUD_FRONT_DOMAIN_NAME + "/" + savedName)
                    .build();
            return filesRepository.save(requestDto.toEntity()).getFilePath();
        } catch (IOException e) {
            throw new Exception("파일 저장 실패 : 오류 " + file.getOriginalFilename(), e);
        }
    }

    public FileResponseDto findById(Long id) {
        Files entity = filesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id=" + id));

        return new FileResponseDto(entity);
    }
}
