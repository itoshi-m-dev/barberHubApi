package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserResponseDTO;
import com.itoshi_m_dev.schedulingapi.mapper.UsersMapper;
import com.itoshi_m_dev.schedulingapi.model.Users;
import com.itoshi_m_dev.schedulingapi.repositories.UserRepository;
import com.itoshi_m_dev.schedulingapi.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository repository;
    private final UsersMapper mapper;
    private final PasswordEncoder encoder;


    public Optional<Users> getByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<Users> getByEmail(String name) {
        return repository.findByEmail(name);
    }

    public void cadastro(UserRequestDTO dto) {
        String senha = dto.password();
        Users user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(senha));
        repository.save(user);
    }

    public Users getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Usuario nao encontrado");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails details) {
            return details.getUsers();
        }

        if(principal instanceof Jwt jwt){
            String login = jwt.getSubject();

            return repository.findByLogin(login)
                    .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        }

        throw new RuntimeException("Tipo de usuário inválido");
    }

    @Transactional
    public void updateUser(Long userId, UserRequestDTO dto){
        Users user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Nenhum usuario encontrado com este ID: " + userId));

        if(dto.login() != null){
            user.setLogin(dto.login());
        }
        if(dto.password() != null){
            user.setPassword(encoder.encode(dto.password()));
        }
        if(dto.email() != null){
            user.setEmail(dto.email());
        }
        if(dto.phone() != null){
            user.setPhone(dto.phone());
        }
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        Page<Users> page = repository.findAll(pageable);
        return page.map(mapper::toDTO);
    }


    public void deleteUser(Long userId){
        Users user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Nenhum usuario encontrado"));

        repository.delete(user);
    }
}
