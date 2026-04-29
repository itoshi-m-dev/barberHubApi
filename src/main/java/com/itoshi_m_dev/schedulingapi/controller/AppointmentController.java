package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AppointmentDTOS.AppointmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.enums.AppointmentStatus;
import com.itoshi_m_dev.schedulingapi.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment")
public class AppointmentController {

    private final AppointmentService service;


    @PostMapping
    @Operation(
            summary = "Criar agendamento",
            description = "Cria um novo agendamento com base nos dados informados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Profissional ou serviço não encontrados"),
            @ApiResponse(responseCode = "409", description = "Conflito de horário"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO show = service.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(show.id())
                .toUri();

        return ResponseEntity.created(uri).body(show);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar agendamento por ID",
            description = "Retorna os dados de um agendamento específico a partir do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDTO show = service.detailAppointment(id);
        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/{id}/cancel")
    @Operation(
            summary = "Cancelar agendamento",
            description = "Cancela um agendamento existente a partir do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "409", description = "Agendamento não pode ser cancelado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        service.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    @Operation(
            summary = "Confirmar agendamento",
            description = "Confirma um agendamento existente a partir do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "409", description = "Agendamento não pode ser confirmado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> confirmAppointment(@PathVariable Long id) {
        service.confirmAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    @Operation(
            summary = "Finalizar agendamento",
            description = "Marca um agendamento como concluído a partir do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento finalizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "409", description = "Agendamento não pode ser finalizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> completeAppointment(@PathVariable Long id) {
        service.completeAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professionals/{id}/appointments")
    @Operation(
            summary = "Listar agendamentos do profissional por data ",
            description = "Retorna todos os agendamentos de um profissional por ID filtrando por uma data específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro de data inválido ou ID"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByProfessionalAndDate(
            @PathVariable Long id,
            @Parameter(
                    description = "Data no formato dd/MM/yyyy",
                    example = "29/04/2026"
            )
            @RequestParam(required = true, value = "day") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) {

        List<AppointmentResponseDTO> show = service.getAppointmentsByProfessionalAndDate(id, date);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping
    @Operation(
            summary = "Listar agendamentos",
            description = "Retorna todos os agendamentos, podendo filtrar por status"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Agendamentos retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> findAll(
            @Parameter(
                    description = "Status do agendamento (PENDING, CONFIRMED, CANCELLED, COMPLETED)",
                    example = "CONFIRMED"
            )
            @RequestParam(required = false) AppointmentStatus status) {
        List<AppointmentResponseDTO> show = service.findAll(status);
        return ResponseEntity.ok().body(show);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover agendamento",
            description = "Remove um agendamento existente a partir do seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "409", description = "Agendamento não pode ser removido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}