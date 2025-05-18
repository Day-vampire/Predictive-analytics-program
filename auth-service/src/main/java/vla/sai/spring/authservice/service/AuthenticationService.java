package vla.sai.spring.authservice.service;

import vla.sai.spring.authservice.dto.AuthenticationRequest;
import vla.sai.spring.authservice.dto.AuthenticationResponse;
import vla.sai.spring.authservice.dto.RegistrationRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegistrationRequest registrationRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}