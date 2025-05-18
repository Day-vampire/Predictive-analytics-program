package vla.sai.spring.authservice.service;

import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.authservice.dto.UserImageDataDto;

import java.util.Optional;

public interface UserImageService {
    UserImageDataDto save(MultipartFile image, Long userId);
    Optional<UserImageDataDto> findByUser_Email(String userEmail);
    Optional<UserImageDataDto> findByUser_Id(Long userId);
    Optional<UserImageDataDto> findByName(String name);


}
