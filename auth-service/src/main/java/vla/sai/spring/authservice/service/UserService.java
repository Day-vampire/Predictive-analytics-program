package vla.sai.spring.authservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.entity.User;

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
}
