package vla.sai.spring.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "userImageDatas")
public class UserImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="type", nullable = false)
    private String type;

    @Column(name ="imageContent", nullable = false)
    private byte[] imageContent;

    @OneToOne(mappedBy = "userImageData")
    private User user;
}
