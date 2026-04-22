package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ServiceDTOS.ServiceModelResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.ServiceModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class ServiceModelController {

    private final ServiceModelService service;

    @PostMapping("/{id}")
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
    public ResponseEntity<List<ServiceModelResponseDTO>> listAllServicesInEstablishment(
            @PathVariable Long establishmentId) {

        List<ServiceModelResponseDTO> show = service.listAllServicesInEstablishment(establishmentId);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceModelResponseDTO> findById(@PathVariable Long id) {
        ServiceModelResponseDTO show = service.findById(id);

        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/establishments/{establishmentId}/services/{serviceId}")
    public ResponseEntity<ServiceModelResponseDTO> updateServiceByEstablishment(
            @PathVariable Long establishmentId, @PathVariable Long serviceId, @RequestBody ServiceModelRequestDTO dto) {

        ServiceModelResponseDTO show = service.updateServiceInEstablishment(establishmentId, serviceId, dto);
        return ResponseEntity.ok(show);

    }

    @DeleteMapping("/establishments/{establishmentId}/services/{serviceId}")
    public ResponseEntity<Void> deleteServiceByEstablishmentId(@PathVariable Long establishmentId,
                                                               @PathVariable Long serviceId) {

        service.deleteServiceByEstablishmentId(establishmentId, serviceId);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Page<ServiceModelResponseDTO>> findAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "min", required = false) BigDecimal min,
            @RequestParam(value = "max", required = false) BigDecimal max,
            @RequestParam(value = "minutes", required = false) Integer minutes,
            Pageable pageable) {

        Page<ServiceModelResponseDTO> show = service.findAll(name, min, max, minutes, pageable);

        return ResponseEntity.ok().body(show);


    }

    //Testar apos a configuraçao do professionalService.
    @PostMapping("/establishments/{establishmentId}/services/{serviceId}/add-professional/{professionalId}")
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
