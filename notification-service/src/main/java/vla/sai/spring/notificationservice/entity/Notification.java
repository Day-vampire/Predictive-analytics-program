package vla.sai.spring.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="title", nullable = false)
    private String title;

    @Column(name ="body", nullable = false)
    private String body;

    @Column(name ="receiver", nullable = false)
    private String receiver;

    @Enumerated(EnumType.STRING)
    @Column(name ="notification_type", nullable = false)
    private NotificationType notificationType;
}
