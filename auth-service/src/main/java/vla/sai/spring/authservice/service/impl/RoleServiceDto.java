package vla.sai.spring.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vla.sai.spring.authservice.dto.RoleDto;
import vla.sai.spring.authservice.entity.Role;
import vla.sai.spring.authservice.mapper.RoleMapper;
import vla.sai.spring.authservice.repository.RoleRepository;
import vla.sai.spring.authservice.service.RoleService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceDto implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto save(RoleDto roleDto) {
        System.out.println(roleDto.getDescription()+" "+roleDto.getName()+" "+roleDto.getId());
        Role role = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public boolean existsRoleById(Long id) {
        return roleRepository.existsById(id);
    }

    @Override
    public boolean existsRoleByName(String roleName) {
        return roleRepository.existsRoleByName(roleName);
    }

    @Override
    public Optional<RoleDto> findRoleById(Long id) {
        return roleRepository.findById(id).map(roleMapper::toDto);
    }

    @Override
    public Optional<RoleDto> findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName).map(roleMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Page<RoleDto> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toDto);
    }
}
