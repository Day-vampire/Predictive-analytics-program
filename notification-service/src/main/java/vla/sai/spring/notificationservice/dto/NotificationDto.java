package vla.sai.spring.notificationservice.dto;

import lombok.Setter;
import lombok.Value;
import org.antlr.v4.runtime.misc.NotNull;
import vla.sai.spring.notificationservice.entity.NotificationType;

@Value
@Setter
public class NotificationDto {

    @NotNull
    String title;

    @NotNull
    String body;

    @NotNull
    String receiver;

    @NotNull
    NotificationType notificationType;
}
