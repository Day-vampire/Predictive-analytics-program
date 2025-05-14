package vla.sai.spring.notificationservice.service;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.notificationservice.dto.NotificationDto;

public interface NotificationService {

    NotificationDto sendNotification(NotificationDto notificationDto) throws MessagingException;
    NotificationDto sendFileNotification(MultipartFile multipartFile, String authName) throws MessagingException;
}
