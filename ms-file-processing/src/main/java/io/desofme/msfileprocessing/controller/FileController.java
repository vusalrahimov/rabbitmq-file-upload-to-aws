package io.desofme.msfileprocessing.controller;

import io.desofme.msfileprocessing.dto.response.FileUploadResponse;
import io.desofme.msfileprocessing.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public FileUploadResponse upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping("/{uniqueFileId}")
    public ResponseEntity<Resource> download(@PathVariable("uniqueFileId") String fileId) {
        return fileService.download(fileId);
    }

}
