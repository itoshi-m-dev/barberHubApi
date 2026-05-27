package com.itoshi_m_dev.schedulingapi.security;

import com.itoshi_m_dev.schedulingapi.model.Users;
import com.itoshi_m_dev.schedulingapi.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsersService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        Users user = service.getByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Nenhum usuario encontrado"));

        if (user == null || !encoder.matches(rawPassword, user.getPassword())) {
            throw new UsernameNotFoundException("Usuario ou senha incorretos.");
        }

        UserDetails userDetails = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
