package com.itoshi_m_dev.schedulingapi.security;

import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserRequestDTO;
import com.itoshi_m_dev.schedulingapi.enums.RolesEnum;
import com.itoshi_m_dev.schedulingapi.mapper.UsersMapper;
import com.itoshi_m_dev.schedulingapi.model.Users;
import com.itoshi_m_dev.schedulingapi.repositories.UserRepository;
import com.itoshi_m_dev.schedulingapi.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        Users usuario = repository.findByEmail(email).orElseGet(() -> {
            Users novoUsuario = new Users();
            novoUsuario.setEmail(email);
            novoUsuario.setLogin(email.substring(0, email.indexOf("@")));
            novoUsuario.setPassword(encoder.encode("oauth2User"));
            novoUsuario.setRoles(Set.of(RolesEnum.ROLE_CLIENT));
            novoUsuario.setPhone("99999-99999");
            novoUsuario.setCreatedAt(LocalDateTime.now());
            return repository.save(novoUsuario);
        });

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRoles())),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}
