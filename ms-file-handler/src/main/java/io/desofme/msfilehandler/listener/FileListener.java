package io.desofme.msfilehandler.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.desofme.msfilehandler.domain.FileInfo;
import io.desofme.msfilehandler.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileListener {

    private final FileService fileService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "file-queue", concurrency = "3")
    public void receiveMessage(String message) throws Exception {
        var fileInfo = objectMapper.readValue(message, FileInfo.class);
        log.info("Message was accept: {}", fileInfo.getName());
        fileService.upload(fileInfo);
    }

}