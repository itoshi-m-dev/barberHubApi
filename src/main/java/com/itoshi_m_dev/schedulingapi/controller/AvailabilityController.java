package com.itoshi_m_dev.schedulingapi.controller;

import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityResponseDTO;
import com.itoshi_m_dev.schedulingapi.services.AvailabilityService;
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
public class AvailabilityController {

    private final AvailabilityService service;

    @PostMapping("/professionals/{professionalId}/availability")
    public ResponseEntity<AvailabilityResponseDTO> addAvailabilityToProfessional(@PathVariable Long professionalId,
                                                                                 @RequestBody AvailabilityRequestDTO dto) {
        AvailabilityResponseDTO show = service.addAvailabilityToProfessional(professionalId, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(show.id())
                .toUri();

        return ResponseEntity.created(uri).body(show);

    }

    @GetMapping("/professionals/{professionalId}/availability")
    public ResponseEntity<List<AvailabilityResponseDTO>> findByProfessionalId(@PathVariable Long professionalId) {
        List<AvailabilityResponseDTO> show = service.findByProfessionalId(professionalId);

        return ResponseEntity.ok().body(show);
    }

    @PatchMapping("/professionals/{professionalId}/availability/{availabilityId}")
    public ResponseEntity<AvailabilityResponseDTO> update(@PathVariable Long professionalId,
                                                          @PathVariable Long availabilityId,
                                                          @RequestBody AvailabilityRequestDTO dto) {

        AvailabilityResponseDTO show = service.updateAvailability(professionalId, availabilityId, dto);

        return ResponseEntity.ok(show);
    }

    @DeleteMapping("/professionals/{professionalId}/availability/{availabilityId}")
    public ResponseEntity<Void> delete(@PathVariable Long professionalId, @PathVariable Long availabilityId) {
        service.deleteAvailability(professionalId, availabilityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professionals/{professionalId}")
    public ResponseEntity<List<AvailabilityResponseDTO>> getByProfessionalAndDayOfWeek(
            @PathVariable Long professionalId,
            @RequestParam(required = true, value = "day") DayOfWeek dayOfWeek) {

        List<AvailabilityResponseDTO> show = service.getByProfessionalIdAndDayOfWeek(professionalId, dayOfWeek);

        return ResponseEntity.ok().body(show);

    }


}
