package io.desofme.msfilehandler.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.desofme.msfilehandler.config.properties.AWSProperties;
import io.desofme.msfilehandler.domain.FileInfo;
import io.desofme.msfilehandler.mapper.FileMapper;
import io.desofme.msfilehandler.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;
    private final AWSProperties awsProperties;
    private final FileDataRepository fileDataRepository;
    private final FileMapper fileMapper;

    @Transactional
    public void upload(FileInfo fileInfo) throws Exception {
        File file = toFile(fileInfo);
        try {
            String fileName = generateFileName(fileInfo);
            String keyName = awsProperties.getFolder() + "/" + fileName;

            var putObjectRequest = new PutObjectRequest(awsProperties.getBucketName(), keyName, file);
            amazonS3.putObject(putObjectRequest);
            saveFileInfo(fileInfo);
            log.info("File was uploaded to cloud storage uniqueFileId: {}", fileInfo.getUniqueFileId());

            if(1==1) {
                throw new RuntimeException();
            }

        }finally {
            file.delete();
        }
    }

    @SneakyThrows
    private File toFile(FileInfo fileInfo) {
        String fileName = generateFileName(fileInfo);
        File file = new File(fileName);
        file.createNewFile();

        try(OutputStream os = new FileOutputStream(file)) {
            os.write(fileInfo.getBody());
        }

        return file;
    }

    private String generateFileName(FileInfo fileInfo) {
        return fileInfo.getUniqueFileId().concat(".").concat(fileInfo.getExtension());
    }

    @Transactional
    public void saveFileInfo(FileInfo fileInfo) {
        var fileData = fileMapper.toFileData(fileInfo);
        fileDataRepository.save(fileData);
    }

}