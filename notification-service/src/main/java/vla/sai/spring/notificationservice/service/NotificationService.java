package vla.sai.spring.notificationservice.service;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.notificationservice.dto.NotificationDto;
import vla.sai.spring.notificationservice.entity.NotificationType;

public interface NotificationService {

    NotificationDto sendNotification(NotificationDto notificationDto) throws MessagingException;
    void sendFileNotification(MultipartFile multipartFile, String authName, NotificationType notificationType) throws MessagingException;
    void sendFileNotification(MultipartFile multipartFile, NotificationDto notificationDto) throws MessagingException;

}
