package io.desofme.msfileprocessing.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.desofme.msfileprocessing.config.properties.AWSProperties;
import io.desofme.msfileprocessing.config.properties.RabbitMQProperties;
import io.desofme.msfileprocessing.dto.FileInfo;
import io.desofme.msfileprocessing.dto.response.FileUploadResponse;
import io.desofme.msfileprocessing.entity.FileData;
import io.desofme.msfileprocessing.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;
    private final ObjectMapper objectMapper;
    private final FileDataRepository fileDataRepository;
    private final AWSProperties awsProperties;
    private final AmazonS3 amazonS3;

    @SneakyThrows
    public FileUploadResponse upload(MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        byte[] body = multipartFile.getBytes();
        String mediaType = multipartFile.getContentType();
        String uniqueFileId = UUID.randomUUID().toString();
        String name = multipartFile.getOriginalFilename();

        FileInfo fileInfo = FileInfo.builder()
                .uniqueFileId(uniqueFileId)
                .mediaType(mediaType)
                .body(body)
                .extension(extension)
                .name(name)
                .build();
        sendMessage(fileInfo);

        return FileUploadResponse.of(uniqueFileId);
    }

    @SneakyThrows
    private void sendMessage(FileInfo fileInfo) {
        byte[] json =  objectMapper.writeValueAsBytes(fileInfo);
        Message message = new Message(json);

        log.info("Message sending to queue: {}", rabbitMQProperties.getQueue());
        rabbitTemplate.send(rabbitMQProperties.getExchange(), rabbitMQProperties.getRoutingKey(), message);
    }

    @SneakyThrows
    public ResponseEntity<Resource> download(String fileId) {
        FileData fileData = fileDataRepository.findByUniqueFileId(fileId)
                .orElseThrow(() ->new IllegalArgumentException("File Not found"));

        S3Object s3Object = amazonS3.getObject(awsProperties.getBucketName(), fileData.getKeyName());
        byte[] bytes = s3Object.getObjectContent().readAllBytes();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileData.getName() + "\"")
                .contentType(MediaType.parseMediaType(fileData.getMediaType()))
                .body(new ByteArrayResource(bytes));
    }

}