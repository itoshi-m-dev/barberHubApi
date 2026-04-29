package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.EstablishmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/establishments")
@RequiredArgsConstructor
@Tag(name = "Establishment")
public class EstablishmentController {

    private final EstablishmentService service;

    @PostMapping
    @Operation(summary = "Criar estabelecimento", description = "Cria estabelecimentos no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estabelecimento criado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EstablishmentResponseDTO> create(@RequestBody @Valid EstablishmentRequestDTO dto){
        EstablishmentResponseDTO show = service.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/establishments/{id}").
                buildAndExpand(show.id()).toUri();

        return ResponseEntity.created(uri).body(show);

    }

    @GetMapping
    @Operation(summary = "Lista todos estabelecimentos",
            description = "Retorna uma lista paginada de estabelecimentos, podendo filtrar por nome e data de criação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimentos encontrados"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<EstablishmentResponseDTO>> findAll(

            @Parameter(description = "Nome do estabelecimento para filtro", example = "Barbearia Shelby")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "Data de criação no formato dd/MM/yyyy HH:mm")
            @RequestParam(value = "createdAt", required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime createdAt,

            @Parameter(hidden = true)
            Pageable pageable){

        Page<EstablishmentResponseDTO> show = service.findAll(name,createdAt,pageable);
        return ResponseEntity.ok().body(show);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Procura estabelecimento especifico",
    description = "Procura estabelecimento especifico pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estabelecimento encontrado."),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EstablishmentResponseDTO> findById(@PathVariable Long id){
        EstablishmentResponseDTO show = service.findById(id);
        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar estabelecimento",
            description = "Atualiza os dados de um estabelecimento existente a partir do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estabelecimento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EstablishmentResponseDTO> updateById(
             @PathVariable Long id,
             @RequestBody @Valid EstablishmentRequestDTO dto){

        EstablishmentResponseDTO show = service.updateById(id, dto);
        return ResponseEntity.ok(show);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover estabelecimento",
            description = "Remove um estabelecimento existente a partir do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estabelecimento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
