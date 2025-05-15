package vla.sai.spring.notificationservice.service;

import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.notificationservice.dto.NotificationDto;
import vla.sai.spring.notificationservice.entity.Notification;
import vla.sai.spring.notificationservice.entity.NotificationType;

import java.util.Optional;

public interface NotificationService {

    NotificationDto sendNotification(NotificationDto notificationDto) throws MessagingException;
    void sendFileNotification(MultipartFile multipartFile, String authName, NotificationType notificationType) throws MessagingException;
    void sendFileNotification(MultipartFile multipartFile, NotificationDto notificationDto) throws MessagingException;

    void deleteNotificationById(Long id);
    void deleteAllNotificationByReceiver(String receiver);

    Page<NotificationDto> findAllByNotificationType(NotificationType notificationType, Pageable pageable);
    Page<NotificationDto> findAllByReceiver(String receiver, Pageable pageable);
    Page<NotificationDto> findAllByReceiverAndNotificationType(String receiver,NotificationType type, Pageable pageable);
    Page<NotificationDto> findAllByReceiverAndTitle(String receiver, String title, Pageable pageable);

    Optional<NotificationDto> findById(Long id);
}
