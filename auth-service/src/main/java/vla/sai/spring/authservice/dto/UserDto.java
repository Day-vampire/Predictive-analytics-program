package vla.sai.spring.authservice.dto;

import lombok.Setter;
import lombok.Value;

@Value
@Setter
public class UserDto {

    Long id;
    String email;
    Long userImageDataId;
    Long personId;
    Long roleId;
    boolean deleted;
}
