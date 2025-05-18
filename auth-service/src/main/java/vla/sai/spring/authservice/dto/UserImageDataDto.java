package vla.sai.spring.authservice.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class UserImageDataDto {
    String name;
    String type;
    byte[] imageContent;
    Long userId;
}
