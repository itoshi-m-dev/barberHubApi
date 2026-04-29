package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
@Tag(name = "Availability")
public class AvailabilityController {

    private final AvailabilityService service;

    @PostMapping("/professionals/{professionalId}/availability")
    @Operation(
            summary = "Adicionar disponibilidade ao profissional",
            description = "Cria uma nova disponibilidade e a associa a um profissional existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disponibilidade criada com sucesso"
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de horário"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AvailabilityResponseDTO> addAvailabilityToProfessional(@PathVariable Long professionalId,
                                                                                 @RequestBody @Valid AvailabilityRequestDTO dto) {
        AvailabilityResponseDTO show = service.addAvailabilityToProfessional(professionalId, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(show.id())
                .toUri();

        return ResponseEntity.created(uri).body(show);

    }

    @GetMapping("/professionals/{professionalId}/availability")
    @Operation(
            summary = "Listar disponibilidades do profissional",
            description = "Retorna todas as disponibilidades associadas a um profissional específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de disponibilidades retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AvailabilityResponseDTO>> findByProfessionalId(@PathVariable Long professionalId) {
        List<AvailabilityResponseDTO> show = service.findByProfessionalId(professionalId);

        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/professionals/{professionalId}/availability/{availabilityId}")
    @Operation(
            summary = "Atualizar disponibilidade do profissional",
            description = "Atualiza uma disponibilidade específica associada a um profissional"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Profissional ou disponibilidade não encontrados"),
            @ApiResponse(responseCode = "409", description = "Conflito de horário"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AvailabilityResponseDTO> update(@PathVariable Long professionalId,
                                                          @PathVariable Long availabilityId,
                                                          @RequestBody @Valid AvailabilityRequestDTO dto) {

        AvailabilityResponseDTO show = service.updateAvailability(professionalId, availabilityId, dto);

        return ResponseEntity.ok(show);
    }

    @DeleteMapping("/professionals/{professionalId}/availability/{availabilityId}")
    @Operation(
            summary = "Remover disponibilidade do profissional",
            description = "Remove uma disponibilidade específica associada a um profissional"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disponibilidade removida com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Profissional e disponibilidade nao encontradas ou nao associadas corretamente."),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> delete(@PathVariable Long professionalId, @PathVariable Long availabilityId) {
        service.deleteAvailability(professionalId, availabilityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professionals/{professionalId}")
    @Operation(
            summary = "Listar disponibilidades por dia da semana",
            description = "Retorna as disponibilidades de um profissional filtrando pelo dia da semana"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidades retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AvailabilityResponseDTO>> getByProfessionalAndDayOfWeek(
            @PathVariable Long professionalId,

            @Parameter(
                    description = "Dia da semana (MONDAY, TUESDAY, ...)",
                    example = "MONDAY")
            @RequestParam(required = true, value = "day") DayOfWeek dayOfWeek) {

        List<AvailabilityResponseDTO> show = service.getByProfessionalIdAndDayOfWeek(professionalId, dayOfWeek);

        return ResponseEntity.ok().body(show);

    }


}
