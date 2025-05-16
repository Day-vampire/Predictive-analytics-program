package vla.sai.spring.authservice.dto;

import lombok.Setter;
import lombok.Value;

@Value
@Setter
public class RoleDto {

    Long id;

    String name;

    String description;
}
