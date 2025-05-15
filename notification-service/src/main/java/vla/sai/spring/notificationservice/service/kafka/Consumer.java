package vla.sai.spring.notificationservice.service.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.notificationservice.dto.NotificationDto;
import vla.sai.spring.notificationservice.service.NotificationService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "pdf_file_notification_topic",
            groupId = "notification_consumers",
            containerFactory = "kafkaListenerFileNotificationContainerFactory"
    )
    public void notificationPdfFileData(ConsumerRecord<NotificationDto, byte[]> record) throws IOException, MessagingException {
        NotificationDto dto = record.key(); // Получение ключа (параметров для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        MultipartFile file = new MockMultipartFile(
                "file",                  // имя поля
                "file.pdf",             // имя файла
                "file/pdf",            // MIME-тип
                fileContent               // сами байты
        );
        notificationService.sendFileNotification(file, dto.getReceiver(), dto.getNotificationType());
    }


    @KafkaListener(
            topics = "excel_file_notification_topic",
            groupId = "notification_consumers",
            containerFactory = "kafkaListenerFileNotificationContainerFactory"
    )
    public void notificationExcelFileData(ConsumerRecord<NotificationDto, byte[]> record) throws IOException, MessagingException {
        NotificationDto dto = record.key(); // Получение ключа (параметров для сохранения)
        byte[] fileContent = record.value(); // Получение содержимого файла
        MultipartFile file = new MockMultipartFile(
                "file",                  // имя поля
                "file.xlsx",             // имя файла
                "file/xlsx",            // MIME-тип
                fileContent               // сами байты
        );
        notificationService.sendFileNotification(file, dto.getReceiver(), dto.getNotificationType());
    }

    @KafkaListener(
            topics = "notification_topic",
            groupId = "notification_consumers",
            containerFactory = "kafkaListenerNotificationContainerFactory"
    )
    public void notificationData(ConsumerRecord<String,NotificationDto> record) throws IOException, MessagingException {
        NotificationDto dto= record.value(); // Получение содержимого файла
        notificationService.sendNotification(dto);
    }

}