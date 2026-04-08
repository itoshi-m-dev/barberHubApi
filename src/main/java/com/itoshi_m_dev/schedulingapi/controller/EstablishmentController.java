package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.EstablishmentDTOS.EstablishmentResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.EstablishmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/establishments")
@RequiredArgsConstructor
public class EstablishmentController {

    private final EstablishmentService service;

    @PostMapping
    public ResponseEntity<EstablishmentResponseDTO> create(@RequestBody @Valid EstablishmentRequestDTO dto){
        EstablishmentResponseDTO show = service.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/establishments/{id}").
                buildAndExpand(show.id()).toUri();

        return ResponseEntity.created(uri).body(show);

    }

    @GetMapping
    public ResponseEntity<Page<EstablishmentResponseDTO>> findAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "createdAt", required = false) LocalDateTime createdAt,
            Pageable pageable){

        Page<EstablishmentResponseDTO> show = service.findAll(name,createdAt,pageable);
        return ResponseEntity.ok().body(show);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EstablishmentResponseDTO> findById(@PathVariable Long id){
        EstablishmentResponseDTO show = service.findById(id);
        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EstablishmentResponseDTO> updateById(
             @PathVariable Long id,
             @RequestBody EstablishmentRequestDTO dto){

        EstablishmentResponseDTO show = service.updateById(id, dto);
        return ResponseEntity.ok(show);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
