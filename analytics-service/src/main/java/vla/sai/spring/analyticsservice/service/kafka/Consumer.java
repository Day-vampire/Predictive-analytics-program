package vla.sai.spring.analyticsservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import vla.sai.spring.analyticsservice.dto.FileDataDto;
import vla.sai.spring.analyticsservice.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class Consumer {


    @KafkaListener(topics = "file_create_topic", groupId = "analytics_consumers")
    public void listenCreateUserId(ConsumerRecord<FileDataDto, byte[]> record) {
        FileDataDto fileDataDto = record.key(); // Получение ключа (данных для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        Path path = FileUtil.saveFile(fileDataDto); // создание файла
        try {
            Files.write(path, fileContent); // Запись содержимого в файл
            System.out.println("File saved to: " + path);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }

    }

    @KafkaListener(topics = "file_delete_topic", groupId = "analytics_consumers")
    public void listenDeleteUserId(ConsumerRecord<FileDataDto, String> record) {
       }
}