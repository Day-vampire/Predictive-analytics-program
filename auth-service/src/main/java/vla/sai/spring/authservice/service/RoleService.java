package vla.sai.spring.authservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vla.sai.spring.authservice.dto.RoleDto;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.entity.Role;

import java.util.Optional;

public interface RoleService {

    RoleDto save(RoleDto roleDto);
    boolean existsRoleById(Long id);
    boolean existsRoleByName(String roleName);

    Optional<RoleDto> findRoleById(Long id);
    Optional<RoleDto> findRoleByName(String roleName);

    void deleteById(Long id);
    Page<RoleDto> findAll(Pageable pageable);
}
