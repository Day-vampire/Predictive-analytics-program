package vla.sai.spring.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.authservice.dto.UserImageDataDto;
import vla.sai.spring.authservice.entity.UserImageData;
import vla.sai.spring.authservice.exception.FileNotSavedException;
import vla.sai.spring.authservice.exception.NotFoundException;
import vla.sai.spring.authservice.mapper.UserImageDataMapper;
import vla.sai.spring.authservice.mapper.UserMapper;
import vla.sai.spring.authservice.repository.UserImageDataRepository;
import vla.sai.spring.authservice.service.UserImageService;
import vla.sai.spring.authservice.service.UserService;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {

    private final UserImageDataRepository imageDataRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserImageDataMapper userImageDataMapper;

    @Override
    public UserImageDataDto save(MultipartFile image, String userName) {
        try {
            if (!userService.existsByEmail(userName))
                throw new NotFoundException("User with email %s not found".formatted(userName));

            UserImageData userImageData = imageDataRepository.save(UserImageData
                    .builder()
                    .user(userMapper.toEntity(userService.findByEmail(userName).get()))
                    .type(image.getContentType())
                    .name(image.getOriginalFilename())
                    .imageContent(compressImage(image.getBytes()))
                    .build());

            System.out.println("проверка шармуты: " + userService.findByEmail(userName).get().getUserImageData().getName());
            return userImageDataMapper.toDto(userImageData);
        } catch (Exception e) {
            throw new FileNotSavedException("Image %s not saved".formatted(image.getOriginalFilename()), e);
        }
    }

    @Override
    public Optional<UserImageDataDto> findByUser_Email(String userEmail) {
        return imageDataRepository.findByUser_Email(userEmail).map(userImageDataMapper::toDto);
    }

    @Override
    public Optional<UserImageDataDto> findByUser_Id(Long userId) {
        return imageDataRepository.findByUser_Id(userId).map(userImageDataMapper::toDto);
    }

    @Override
    public Optional<UserImageDataDto> findByName(String name) {
        return imageDataRepository.findByName(name).map(userImageDataMapper::toDto);
    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<UserImageData> dbImage = imageDataRepository.findByName(name);
        byte[] image = decompressImage(dbImage.get().getImageContent());
        return image;
    }

    public static byte[] compressImage(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {

        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }
}
