package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.ProfessionalDTOS.ProfessionalResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService service;


    @PostMapping("/establishments/{establishmentId}/professionals")
    public ResponseEntity<ProfessionalResponseDTO> addProfessionalInEstablishment(@PathVariable Long establishmentId,
                                                                                  @RequestBody ProfessionalRequestDTO dto) {
        ProfessionalResponseDTO show = service.addProfessionalToEstablishment(establishmentId, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(show.id())
                .toUri();

        return ResponseEntity.created(uri).body(show);
    }

    @GetMapping("/establishments/{establishmentId}/professionals")
    public ResponseEntity<Page<ProfessionalResponseDTO>> findAllProfessionalByEstablishment(@PathVariable Long establishmentId,
                                                                                            Pageable pageable) {

        Page<ProfessionalResponseDTO> show = service.findAllProfessionalByEstablishmentId(establishmentId, pageable);

        return ResponseEntity.ok().body(show);
    }

    @GetMapping("/establishments/{establishmentId}/professionals/{professionalId}")
    public ResponseEntity<ProfessionalResponseDTO> professionalDetailByEstablishment(@PathVariable Long establishmentId,
                                                                                     @PathVariable Long professionalId) {

        ProfessionalResponseDTO show = service.professionalDetails(establishmentId, professionalId);

        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/establishments/{establishmentId}/professionals/{professionalId}")
    public ResponseEntity<ProfessionalResponseDTO> updateProfessional(@PathVariable Long establishmentId,
                                                                      @PathVariable Long professionalId,
                                                                      @RequestBody ProfessionalRequestDTO dto) {

        ProfessionalResponseDTO show = service.updateProfessional(establishmentId, professionalId, dto);

        return ResponseEntity.ok(show);
    }

    @DeleteMapping("/establishments/{establishmentId}/professionals/{professionalId}")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long establishmentId,
                                                   @PathVariable Long professionalId) {

        service.deleteProfessional(establishmentId, professionalId);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/services/{serviceId}/professionals/{professionalId}")
    public ResponseEntity<Void> addServiceToProfessional(@PathVariable Long serviceId,
                                                         @PathVariable Long professionalId) {

        service.addServiceToProfessional(professionalId, serviceId);

        return ResponseEntity.noContent().build();

    }


}
