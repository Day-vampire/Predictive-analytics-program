package vla.sai.spring.authservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Setter;
import lombok.Value;
import vla.sai.spring.authservice.entity.User;

@Value
@Setter
public class PersonDto {
    Long id;

    String name;

    String lastName;

    String number;

    String country;

    Long userId;
}
