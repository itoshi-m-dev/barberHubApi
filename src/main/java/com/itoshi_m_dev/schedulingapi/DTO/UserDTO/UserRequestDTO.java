package com.itoshi_m_dev.schedulingapi.DTO.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Nome é obrigatorio")
        @Size(min = 10, max = 30, message = "Nome deve ter entre 10 e 30 caracteres")
       String name,

       @NotBlank(message = "Email é obrigatorio")
       @Email(message = "Formato do email inválido")
       String email,

       @NotBlank(message = "Senha nao pode nula")
       @Size(min = 4, message = "A senha tem que ter no mínimo 4 caracteres")
       String password,

       @NotBlank(message = "Um telefone é obrigatorio para cadastro")
       String phone


) {

}
