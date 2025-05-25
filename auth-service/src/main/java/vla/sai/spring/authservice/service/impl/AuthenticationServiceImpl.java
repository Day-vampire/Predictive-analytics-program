package vla.sai.spring.authservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vla.sai.spring.authservice.config.UserDetailsImpl;
import vla.sai.spring.authservice.dto.AuthenticationRequest;
import vla.sai.spring.authservice.dto.AuthenticationResponse;
import vla.sai.spring.authservice.dto.RegistrationRequest;
import vla.sai.spring.authservice.entity.User;
import vla.sai.spring.authservice.exception.AlreadyExistException;
import vla.sai.spring.authservice.exception.NotFoundException;
import vla.sai.spring.authservice.repository.RoleRepository;
import vla.sai.spring.authservice.repository.UserRepository;
import vla.sai.spring.authservice.service.AuthenticationService;
import vla.sai.spring.authservice.service.JwtService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        String login = registrationRequest.login();
        if (userRepository.findByEmail(login).isPresent()) {
            throw new AlreadyExistException("User with email: %s already exists".formatted(login));
        }
        User user = new User()
                .setEmail(login)
                .setPassword(passwordEncoder.encode(registrationRequest.password()))
                .setDeleted(false)
                .setRole(roleRepository.findRoleByName("ROLE_USER")
                        .orElseThrow(()->new NotFoundException("Role not found")));
        userRepository.save(user);
        String token = jwtService.generateToken(new UserDetailsImpl(user));
        return new AuthenticationResponse(token);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.login(),
                        authenticationRequest.password()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.login());
        String token = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }
}
