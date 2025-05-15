package vla.sai.spring.notificationservice.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.notificationservice.dto.NotificationDto;
import vla.sai.spring.notificationservice.entity.NotificationType;
import vla.sai.spring.notificationservice.exception.MailSendException;
import vla.sai.spring.notificationservice.service.NotificationService;

import java.util.Optional;

@RestController
@RequestMapping("/email-notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(value = "/user-notification")
    public NotificationDto sendNotification(@RequestBody NotificationDto notificationDto) {

        try {
            return notificationService.sendNotification(notificationDto);
        } catch (MailException mailException) {
            log.error("Error while sending out email..%s".formatted(mailException.getStackTrace()));
            return null;
        } catch (MessagingException e) {
            throw new MailSendException("Ошибка при отправке сообщения %s".formatted(e.getStackTrace()));
        }
    }

    @PostMapping(value = "/file-notification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void sendNotificationFile(@RequestParam(value = "file", required = false) MultipartFile file,
                                     @RequestParam(value = "authName") String fileAuthorName,
                                     @RequestParam(value = "notificationType") NotificationType notificationType) {
        try {
            notificationService.sendFileNotification(file, fileAuthorName, notificationType);
        } catch (MessagingException mailException) {
            log.error("Error while sending out email..{}", mailException.getStackTrace());
        }
    }

    @GetMapping("/find-by-receiver")
    public Page<NotificationDto> findByReceiver(@RequestParam("receiver") String receiver,
                                                @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return notificationService.findAllByReceiver(receiver, pageable);
    }

    @GetMapping("/find-by-notification-type")
    public Page<NotificationDto> findByNotificationType(@RequestParam("notificationType") NotificationType notificationType,
                                                        @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return notificationService.findAllByNotificationType(notificationType, pageable);
    }

    @GetMapping("/find-by-receiver-and-notification-type")
    public Page<NotificationDto> findAllByReceiverAndNotificationType(@RequestParam("receiver") String receiver,
                                                                      @RequestParam("notificationType") NotificationType notificationType,
                                                                      @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return notificationService.findAllByReceiverAndNotificationType(receiver, notificationType, pageable);
    }

    @GetMapping("/find-by-receiver-and-title")
    public Page<NotificationDto> findAllByReceiverAndTitle(@RequestParam("receiver") String receiver,
                                                           @RequestParam("title") String title,
                                                           @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return notificationService.findAllByReceiverAndTitle(receiver, title, pageable);
    }

    @GetMapping("/find-by-id")
    public Optional<NotificationDto> findByNotificationType(@RequestParam("id") Long id) {
        return notificationService.findById(id);
    }

    @DeleteMapping("/delete-by-id")
    public void deleteById(@RequestParam("id") Long id) {
        notificationService.deleteNotificationById(id);
    }

    @GetMapping("/delete-by-receiver")
    public void deleteByReceiver(@RequestParam("receiver") String receiver) {
        notificationService.deleteAllNotificationByReceiver(receiver);
    }
}
