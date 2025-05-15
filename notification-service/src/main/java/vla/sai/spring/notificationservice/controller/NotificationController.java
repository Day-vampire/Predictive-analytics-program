package vla.sai.spring.notificationservice.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.notificationservice.dto.NotificationDto;
import vla.sai.spring.notificationservice.entity.NotificationType;
import vla.sai.spring.notificationservice.exception.MailSendException;
import vla.sai.spring.notificationservice.service.NotificationService;

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
                                                @RequestParam(value = "authName", required = true) String fileAuthorName,
                                                @RequestParam(value = "notificationType", required = true)NotificationType notificationType) throws MessagingException {
        try {
             notificationService.sendFileNotification(file, fileAuthorName, notificationType);
        } catch (MessagingException mailException) {
            log.error("Error while sending out email..{}", mailException.getStackTrace());
        }

    }
}
