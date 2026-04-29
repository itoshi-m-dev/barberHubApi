package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.ServiceModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/services")
@RequiredArgsConstructor
@Tag(name = "ServiceModel")
@Slf4j
public class ServiceModelController {

    private final ServiceModelService service;

    @PostMapping("/{id}")
    @Operation(summary = "Salvar", description = "Cadastrar serviço em um Estabelecimento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Serviço ja cadastrado"),
    })
    public ResponseEntity<ServiceModelResponseDTO> createServiceInEstablishmentById(
            @PathVariable Long id,
            @RequestBody @Valid ServiceModelRequestDTO dto) {

        ServiceModelResponseDTO show = service.createServiceInEstablishmentById(id, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/services/{id}")
                .buildAndExpand(show.id())
                .toUri();

        return ResponseEntity.created(uri).body(show);

    }

    @GetMapping("/establishment/{establishmentId}/services")
    @Operation(summary = "Listar serviços", description = "Lista todos serviços em um Estabelecimento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento nao encontrado"),
            @ApiResponse(responseCode = "400", description = "ID do estabelecimento inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<ServiceModelResponseDTO>> listAllServicesInEstablishment(
            @PathVariable Long establishmentId) {

        List<ServiceModelResponseDTO> show = service.listAllServicesInEstablishment(establishmentId);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Achar serviço por ID",
            description = "Retorna os dados de um serviço especifico com base no ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID invalido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ServiceModelResponseDTO> findById(@PathVariable Long id) {
        ServiceModelResponseDTO show = service.findById(id);

        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/establishments/{establishmentId}/services/{serviceId}")
    @Operation(summary = "Atualizar serviços de um estabelecimento",
            description = "Atualiza parcialmente os dados de um serviço pertencente a um estabelecimento específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento ou serviço nao encontrados"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos para atualização"),

    })
    public ResponseEntity<ServiceModelResponseDTO> updateServiceByEstablishment(
            @PathVariable Long establishmentId, @PathVariable Long serviceId, @RequestBody ServiceModelRequestDTO dto) {

        ServiceModelResponseDTO show = service.updateServiceInEstablishment(establishmentId, serviceId, dto);
        return ResponseEntity.ok(show);

    }

    @DeleteMapping("/establishments/{establishmentId}/services/{serviceId}")
    @Operation(summary = "Deletar serviços do Estabelecimento",
            description = "Deleta serviços do estabelecimento e em geral."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado ou serviço " +
                    "nao pertence ao estabelecimento desejado"),
            @ApiResponse(responseCode = "400", description = "ID's Invalidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteServiceByEstablishmentId(@PathVariable Long establishmentId,
                                                               @PathVariable Long serviceId) {

        service.deleteServiceByEstablishmentId(establishmentId, serviceId);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @Operation(
            summary = "Listar serviços com filtros",
            description = "Retorna uma lista paginada de serviços podendo filtrar por nome, faixa de preço e duração em minutos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviços encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros com filtros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Page<ServiceModelResponseDTO>> findAll(
            @Parameter(description = "Nome do serviço para filtro", example = "Corte")
            @RequestParam(value = "name", required = false) String name,

            @Parameter(description = "Preço minimo", example = "10.00")
            @RequestParam(value = "min", required = false) BigDecimal min,

            @Parameter(description = "Preço máximo", example = "180.00")
            @RequestParam(value = "max", required = false) BigDecimal max,

            @Parameter(description = "Duração em minutos", example = "60")
            @RequestParam(value = "minutes", required = false) Integer minutes,

            @Parameter(hidden = true)
            Pageable pageable) {

        Page<ServiceModelResponseDTO> show = service.findAll(name, min, max, minutes, pageable);

        return ResponseEntity.ok().body(show);


    }

    //Testar apos a configuraçao do professionalService.
    @PostMapping("/establishments/{establishmentId}/services/{serviceId}/add-professional/{professionalId}")
    @Operation(summary = "Adicionar um profissional ao serviço", description = "Associa um profissional a um serviço " +
            "dentro de um estabelecimento específico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profissional vinculado ao serviço"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento, serviço ou profissional não encontrados"),
            @ApiResponse(responseCode = "400", description = "ID Invalido"),
            @ApiResponse(responseCode = "409", description = "Serviço ja possui um profissional vinculado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
    })
    public ResponseEntity<ServiceModelResponseDTO> addProfessionalToService(@PathVariable Long establishmentId,
                                                                            @PathVariable Long serviceId,
                                                                            @PathVariable Long professionalId) {

        ServiceModelResponseDTO show = service.addProfessionalToService(establishmentId, serviceId, professionalId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/services/establishments/{establishmentId}/services/{serviceId}/add-professional/{professionalId}")
                .buildAndExpand(establishmentId, serviceId, professionalId)
                .toUri();

        return ResponseEntity.created(uri).body(show);
    }


}
