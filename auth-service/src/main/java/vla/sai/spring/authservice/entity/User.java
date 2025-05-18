package vla.sai.spring.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="email", nullable = false)
    private String email;

    @Column(name ="password")
    private String password;

    @OneToOne
    @JoinColumn(name="user_image_data_id", nullable = true)
    private UserImageData userImageData;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
