package vla.sai.spring.authservice.dto;

import lombok.Setter;
import lombok.Value;
import vla.sai.spring.authservice.entity.UserImageData;

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
