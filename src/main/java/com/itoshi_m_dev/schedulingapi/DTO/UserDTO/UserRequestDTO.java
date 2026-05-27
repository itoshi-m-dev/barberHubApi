package com.itoshi_m_dev.schedulingapi.DTO.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserRequestDTO(

        @NotBlank(message = "Login é obrigatorio")
        @Size(min = 10, max = 30, message = "Login deve ter entre 5 e 10 caracteres")
        String login,

       @NotBlank(message = "Email é obrigatorio")
       @Email(message = "Formato do email inválido")
       String email,

       @NotBlank(message = "Senha nao pode nula")
       @Size(min = 4, message = "A senha tem que ter no mínimo 4 caracteres")
       String password,

       @NotBlank(message = "Um telefone é obrigatorio para cadastro")
       String phone,

        Set<String> roles


) {

}
