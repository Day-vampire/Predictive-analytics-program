package vla.sai.spring.authservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import vla.sai.spring.authservice.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserDto save(UserDto user);

    Optional<UserDto> findByEmail(String email);
    Optional<UserDto> findById(Long id);

    Page<UserDto> findAllByRole_Name(String roleName, Pageable pageable);
    Page<UserDto> findAllByPerson_Country(String personCountry, Pageable pageable);
    Page<UserDto> findAll(Pageable pageable);
    void deleteById(Long id);
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    void updateUserImage( Long userId, @Param("imageId") Long imageId);
}
