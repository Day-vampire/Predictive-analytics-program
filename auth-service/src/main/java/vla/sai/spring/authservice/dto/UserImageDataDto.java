package vla.sai.spring.authservice.dto;

import lombok.Setter;
import lombok.Value;

@Value
@Setter
public class UserImageDataDto {
    String name;
    String type;
    Long userId;
}
