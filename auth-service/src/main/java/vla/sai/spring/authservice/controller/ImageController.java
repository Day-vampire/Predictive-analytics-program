package vla.sai.spring.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.authservice.dto.UserImageDataDto;
import vla.sai.spring.authservice.service.UserImageService;

import java.io.IOException;



@RestController
@RequestMapping(value = "/images")
@RequiredArgsConstructor
public class ImageController {

    private final UserImageService userImageService;

    @PostMapping(path = "/save-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка фото аватара")
    public UserImageDataDto uploadFile(
            @RequestParam(value = "image",required = false) MultipartFile image,
            @RequestParam(value = "userName", required = false) String userName){
        return userImageService.save(image,userName);
    }
}
