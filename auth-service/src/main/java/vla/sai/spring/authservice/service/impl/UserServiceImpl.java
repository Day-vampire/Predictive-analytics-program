package vla.sai.spring.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.entity.User;
import vla.sai.spring.authservice.mapper.UserMapper;
import vla.sai.spring.authservice.repository.UserRepository;
import vla.sai.spring.authservice.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> findAllByRole_Name(String roleName, Pageable pageable) {
        return userRepository.findAllByRole_Name(roleName, pageable).map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> findAllByPerson_Country(String personCountry, Pageable pageable) {
        return userRepository.findAllByPerson_Country(personCountry, pageable).map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return  userRepository.existsByEmail(email);
    }
}
