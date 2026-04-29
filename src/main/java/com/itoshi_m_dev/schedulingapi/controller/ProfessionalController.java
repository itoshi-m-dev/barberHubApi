package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.ProfessionalService;
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

import java.net.URI;

@RestController
@RequestMapping(value = "/api/professionals")
@RequiredArgsConstructor
@Tag(name = "Professional")
@Slf4j
public class ProfessionalController {

    private final ProfessionalService service;


    @PostMapping("/establishments/{establishmentId}/professionals")
    @Operation(summary = "Adicionar profissional em um estabelecimento",
            description = "Vincula profissional em um estabelecimento"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profissional adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Nenhum estabelecimento encontrado com este ID"),
            @ApiResponse(responseCode = "409", description = "Profissional ja existe no estabelecimento"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ProfessionalResponseDTO> addProfessionalInEstablishment(@PathVariable Long establishmentId,
                                                                                  @RequestBody @Valid ProfessionalRequestDTO dto) {
        ProfessionalResponseDTO show = service.addProfessionalToEstablishment(establishmentId, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(show.id())
                .toUri();

        return ResponseEntity.created(uri).body(show);
    }

    @GetMapping("/establishments/{establishmentId}/professionals")
    @Operation(summary = "Procurar todos profissionais do Estabelecimento",
            description = "Listar profissionais vinculados a um estabelecimento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento nao encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<ProfessionalResponseDTO>> findAllProfessionalByEstablishment(@PathVariable Long establishmentId,
                                                                                            @Parameter(hidden = true)
                                                                                            Pageable pageable) {

        Page<ProfessionalResponseDTO> show = service.findAllProfessionalByEstablishmentId(establishmentId, pageable);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping("/establishments/{establishmentId}/professionals/{professionalId}")
    @Operation(
            summary = "Buscar profissional por estabelecimento",
            description = "Retorna os detalhes de um profissional específico vinculado a um estabelecimento"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "IDs inválidos"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento ou profissional não encontrado, ou não pertence ao estabelecimento"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ProfessionalResponseDTO> professionalDetailByEstablishment(@PathVariable Long establishmentId,
                                                                                     @PathVariable Long professionalId) {

        ProfessionalResponseDTO show = service.professionalDetails(establishmentId, professionalId);

        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/establishments/{establishmentId}/professionals/{professionalId}")
    @Operation(summary = "Atualizar profissional", description = "Atualiza os dados do profissional parcialmente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento ou profissional não encontrado, ou não pertence ao estabelecimento"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados ao atualizar profissional"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ProfessionalResponseDTO> updateProfessional(@PathVariable Long establishmentId,
                                                                      @PathVariable Long professionalId,
                                                                      @RequestBody @Valid ProfessionalRequestDTO dto) {

        ProfessionalResponseDTO show = service.updateProfessional(establishmentId, professionalId, dto);

        return ResponseEntity.ok(show);
    }

    @DeleteMapping("/establishments/{establishmentId}/professionals/{professionalId}")
    @Operation(
            summary = "Remover profissional de um estabelecimento",
            description = "Remove um profissional vinculado a um estabelecimento específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Profissional removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "IDs inválidos"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento ou profissional não encontrado, ou não pertence ao estabelecimento"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long establishmentId,
                                                   @PathVariable Long professionalId) {

        service.deleteProfessional(establishmentId, professionalId);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/services/{serviceId}/professionals/{professionalId}")
    @Operation(
            summary = "Adicionar serviço ao profissional",
            description = "Associa um serviço a um profissional específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Serviço associado ao profissional com sucesso"),
            @ApiResponse(responseCode = "400", description = "IDs inválidos"),
            @ApiResponse(responseCode = "404", description = "Serviço ou profissional não encontrado"),
            @ApiResponse(responseCode = "409", description = "Serviço ja possui um profissional"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> addServiceToProfessional(@PathVariable Long serviceId,
                                                         @PathVariable Long professionalId) {

        service.addServiceToProfessional(professionalId, serviceId);

        return ResponseEntity.noContent().build();

    }


}
