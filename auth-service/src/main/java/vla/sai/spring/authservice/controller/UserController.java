package vla.sai.spring.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.service.UserService;

import java.util.Optional;


@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/delete-by-id")
    @Operation(summary = "Удаление пользователя по id")
    public void deleteUserById(Long id) {
        userService.deleteById(id);
    }

    @PostMapping(path = "/delete-user")
    @Operation(summary = "Удаление пользователя")
    public void deleteUserByEmail(String email) {
        userService.deleteByEmail(email);
    }

    @GetMapping
    @Operation(summary = "Получение всех пользователей")
    public Page<UserDto> getAllUsers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping(path = "/find-by-role")
    @Operation(summary = "Получение всех пользователей 1 роли")
    public Page<UserDto> getAllUserByRole(String roleName, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.findAllByRole_Name(roleName, pageable);
    }

    @GetMapping(path = "/find-by-country")
    @Operation(summary = "Получение всех пользователей 1 страны")
    public Page<UserDto> getAllUserByCountry(String countryName, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.findAllByPerson_Country(countryName, pageable);
    }

    @GetMapping(path = "/find-by-id")
    @Operation(summary = "Получение  пользователя по id")
    public Optional<UserDto> getUserById(Long id) {
        return userService.findById(id);
    }

    @GetMapping(path = "/find-by-email")
    @Operation(summary = "Получение  пользователя по email")
    public Optional<UserDto> getUserById(String email) {
        return userService.findByEmail(email);
    }

    @PostMapping(path = "/save")
    @Operation(summary = "Создание пользователя")
    public UserDto save(UserDto userDto) {
        return userService.save(userDto);
    }
}