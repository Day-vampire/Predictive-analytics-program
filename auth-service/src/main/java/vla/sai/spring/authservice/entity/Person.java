package vla.sai.spring.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="last_name", nullable = false)
    private String lastName;

    @Column(name ="number")
    private String number;

    @Column(name ="country")
    private String country;

    @OneToOne(mappedBy = "person")
    private User user;
}
