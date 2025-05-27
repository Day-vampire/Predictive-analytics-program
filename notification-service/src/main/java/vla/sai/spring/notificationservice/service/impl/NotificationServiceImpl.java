package vla.sai.spring.notificationservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.notificationservice.dto.NotificationDto;
import vla.sai.spring.notificationservice.entity.Notification;
import vla.sai.spring.notificationservice.entity.NotificationType;
import vla.sai.spring.notificationservice.repository.NotificationRepository;
import vla.sai.spring.notificationservice.service.NotificationService;
import vla.sai.spring.notificationservice.service.mapper.NotificationMapper;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public NotificationDto sendNotification(NotificationDto notificationDto) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(notificationDto.getReceiver());
        mimeMessageHelper.setSubject(notificationDto.getTitle() + notificationDto.getNotificationType());
        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setText(notificationDto.getBody());
        emailSender.send(message);
        Notification notification = notificationMapper.toEntity(notificationDto);
        return notificationMapper.toDto(notificationRepository.save(notification));
    }

    @Override
    public void sendFileNotification(MultipartFile multipartFile, String authName, NotificationType notificationType) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(authName);
        messageHelper.setSubject(notificationType.getValue());
        messageHelper.setText("Доброго времени суток, уважаемый %s. Ваш файл %s готов. Благодарим вас за пользование нашими услугами".formatted(authName, multipartFile.getOriginalFilename()));
        messageHelper.setFrom(fromMail);
        messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
        emailSender.send(mimeMessage);

        notificationRepository.save(Notification
                .builder()
                .title(notificationType.getValue())
                .body("Доброго времени суток, уважаемый %s. Ваш файл готов. Благодарим вас за пользование нашими услугами".formatted(authName))
                .receiver(authName)
                .notificationType(notificationType)
                .build());
    }

    @Override
    public void sendFileNotification(MultipartFile multipartFile, NotificationDto notificationDto) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(notificationDto.getReceiver());
        messageHelper.setSubject(notificationDto.getTitle());
        messageHelper.setText(notificationDto.getBody());
        messageHelper.setFrom(fromMail);
        messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
        emailSender.send(mimeMessage);

        notificationRepository.save(Notification
                .builder()
                .title(notificationDto.getTitle())
                .body(notificationDto.getBody())
                .receiver(notificationDto.getReceiver())
                .notificationType(notificationDto.getNotificationType())
                .build());
    }

    @Override
    public void deleteNotificationById(Long id) {
        notificationRepository.deleteNotificationById(id);
    }

    @Override
    public void deleteAllNotificationByReceiver(String receiver) {
        notificationRepository.deleteAllNotificationByReceiver(receiver);
    }

    @Override
    public Page<NotificationDto> findAllByNotificationType(NotificationType notificationType, Pageable pageable) {
        return notificationRepository
                .findAllByNotificationType(notificationType, pageable)
                .map(notificationMapper::toDto);
    }

    @Override
    public Page<NotificationDto> findAllByReceiver(String receiver, Pageable pageable) {
        return notificationRepository.findAllByReceiver(receiver, pageable).map(notificationMapper::toDto);
    }

    @Override
    public Page<NotificationDto> findAllByReceiverAndNotificationType(String receiver, NotificationType type, Pageable pageable) {
        return notificationRepository.findAllByReceiverAndNotificationType(receiver, type, pageable).map(notificationMapper::toDto);
    }

    @Override
    public Page<NotificationDto> findAllByReceiverAndTitle(String receiver, String title, Pageable pageable) {
        return notificationRepository.findAllByReceiverAndTitle(receiver, title, pageable).map(notificationMapper::toDto);
    }

    @Override
    public Optional<NotificationDto> findById(Long id) {
        return notificationRepository.findById(id).map(notificationMapper::toDto);
    }


}
