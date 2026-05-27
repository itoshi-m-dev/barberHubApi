package com.itoshi_m_dev.schedulingapi.security;

import com.itoshi_m_dev.schedulingapi.model.Users;
import com.itoshi_m_dev.schedulingapi.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersService service;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        Users user = service.getByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado."
                        )
                );
        return new CustomUserDetails(user);
    }
}
