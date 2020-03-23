package com.padoling.portfolio.august.web;

import com.padoling.portfolio.august.service.files.FilesService;
import com.padoling.portfolio.august.web.dto.file.FileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FileApiController {

    private final FilesService filesService;

    @PostMapping("/api/v1/image")
    public ResponseEntity<?> imageUpload(@RequestParam MultipartFile file) {
        try {
            return ResponseEntity.ok().body(filesService.save(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/image/{id}")
    public String getImage(@PathVariable Long id) {
        FileResponseDto file = filesService.findById(id);
        return "<img src=\""+file.getFilePath()+"\" width=480 height=\"auto\"/>";
    }
}