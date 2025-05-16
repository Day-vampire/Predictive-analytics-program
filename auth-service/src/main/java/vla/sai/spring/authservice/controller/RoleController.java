package vla.sai.spring.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vla.sai.spring.authservice.dto.RoleDto;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.service.RoleService;
import vla.sai.spring.authservice.service.UserService;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping(value = "/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @DeleteMapping(path = "/delete-by-id")
    @Operation(summary = "Удаление role по id")
    public void deleteUserById(Long id)  {
        roleService.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Получение всех role")
    public Page<RoleDto> getAllRoles(@PageableDefault(size = 10,page = 0) Pageable pageable) {
        return roleService.findAll(pageable);
    }

    @GetMapping(path = "/find-by-id")
    @Operation(summary = "Получение роли по id")
    public Optional<RoleDto> getRoleById(Long roleId) {
        return roleService.findRoleById(roleId);
    }

    @GetMapping(path = "/find-by-name")
    @Operation(summary = "Получение роли по имени")
    public Optional<RoleDto> getRoleByName(String roleName) {
        return roleService.findRoleByName(roleName);
    }

    @PostMapping(path = "/save")
    @Operation(summary = "Создание роли")
    public RoleDto save(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

}