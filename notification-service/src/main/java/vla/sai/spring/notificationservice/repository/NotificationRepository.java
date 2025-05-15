package vla.sai.spring.notificationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.notificationservice.entity.Notification;
import vla.sai.spring.notificationservice.entity.NotificationType;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification save(Notification notification);
    void saveNotification(Notification notification);

    void deleteNotificationById(Long id);
    void deleteAllNotificationByReceiver(String receiver);

    Page<Notification> findAllByNotificationType(NotificationType notificationType, Pageable pageable);
    Page<Notification> findAllByReceiver(String receiver, Pageable pageable);
    Page<Notification> findAllByReceiverAndNotificationType(String receiver,NotificationType type, Pageable pageable);
    Page<Notification> findAllByReceiverAndTitle(String receiver, String title, Pageable pageable);

    Optional<Notification> findById(Long id);

}