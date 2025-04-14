package vla.sai.spring.fileservice.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vla.sai.spring.fileservice.dto.FileDataDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<FileDataDto, byte[]> kafkaTemplateCreateFile;
    private final KafkaTemplate<String, FileDataDto> kafkaTemplateDeleteFile;

    public void sendCreatedFileData(File file, FileDataDto fileDataDto) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            ProducerRecord<FileDataDto, byte[]> producerRecord = new ProducerRecord<>("file_create_topic", fileDataDto, fileContent);
            kafkaTemplateCreateFile.send(producerRecord);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendDeletedFileData(FileDataDto fileDataDto) {
        ProducerRecord<String, FileDataDto> producerRecord = new ProducerRecord<>("file_delete_topic", "deletedFile", fileDataDto);
        kafkaTemplateDeleteFile.send(producerRecord);
    }
}