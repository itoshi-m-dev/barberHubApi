package com.itoshi_m_dev.schedulingapi.controller;


import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserResponseDTO;
import com.itoshi_m_dev.schedulingapi.mapper.UsersMapper;
import com.itoshi_m_dev.schedulingapi.model.Users;
import com.itoshi_m_dev.schedulingapi.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService service;
    private final UsersMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar usuário",
            description = "Realiza o cadastro de um novo usuário no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Usuário já existente"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void register(@RequestBody @Valid UserRequestDTO dto){
        service.cadastro(dto);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Buscar perfil do usuário autenticado",
            description = "Retorna os dados do usuário autenticado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UserResponseDTO> getUserProfileDetails(){
            Users user = service.getAuthenticatedUser();
            UserResponseDTO dto = mapper.toDTO(user);

            return ResponseEntity.ok().body(dto);
    }


    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDTO dto){
        service.updateUser(id, dto);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista paginada de usuários."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuários listados com sucesso"
            ),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@Parameter(hidden = true) Pageable pageable){
        Page<UserResponseDTO> page = service.getAllUsers(pageable);
        return ResponseEntity.ok().body(page);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
