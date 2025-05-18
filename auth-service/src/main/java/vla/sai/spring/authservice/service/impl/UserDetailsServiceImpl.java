package vla.sai.spring.authservice.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vla.sai.spring.authservice.repository.UserRepository;
import vla.sai.spring.authservice.config.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByEmail(login)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: %s not found".formatted(login)));
    }
}