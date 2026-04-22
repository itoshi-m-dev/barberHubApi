package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.AvailabilityDTOS.AvailabilityResponseDTO;
import com.itoshi_m_dev.schedulingapi.exception.ClosedBarberException;
import com.itoshi_m_dev.schedulingapi.exception.ResourceNotFoundException;
import com.itoshi_m_dev.schedulingapi.mapper.AvailabilityMapper;
import com.itoshi_m_dev.schedulingapi.model.Appointment;
import com.itoshi_m_dev.schedulingapi.model.Availability;
import com.itoshi_m_dev.schedulingapi.model.Professional;
import com.itoshi_m_dev.schedulingapi.repositories.AvailabilityRepository;
import com.itoshi_m_dev.schedulingapi.repositories.ProfessionalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository repository;
    private final AvailabilityMapper mapper;
    private final ProfessionalRepository professionalRepository;

    @Transactional
    public AvailabilityResponseDTO addAvailabilityToProfessional(Long professionalId, AvailabilityRequestDTO dto){
        if (!professionalRepository.existsById(professionalId)){
            throw new ResourceNotFoundException("Nenhum profissional encontrado com este ID: " + professionalId);
        }

        if (!dto.professionalId().equals(professionalId)){
            throw new ResourceNotFoundException("O Id de criação é diferente do Path.");
        }

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional encontrado com este ID" + professionalId));

        Availability entity = mapper.toEntity(dto);
        entity.setProfessional(professional);
        professional.getAvailabilityList().add(entity);

        repository.save(entity);

        return mapper.toDTO(entity);

    }


    public List<AvailabilityResponseDTO> findByProfessionalId(Long professionalId){
        if(!professionalRepository.existsById(professionalId)){
            throw new ResourceNotFoundException("Nenhum profissional encontrado com este ID: " + professionalId);
        }
        List<Availability> availabilities = repository.findByProfessionalId(professionalId);
        List<AvailabilityResponseDTO> availabilityResponseDTOS = availabilities.stream().map(mapper::toDTO).toList();

        return availabilityResponseDTOS;

    }

    @Transactional
    public AvailabilityResponseDTO updateAvailability(Long professionalId, Long availabilityId, AvailabilityRequestDTO dto){

        Availability availability = repository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhuma disponibilidade encontrada com este ID: " + availabilityId
                ));

        if (!availability.getProfessional().getId().equals(professionalId)) {
            throw new ResourceNotFoundException("Disponibilidade não pertence a este profissional");
        }

        update(availability, dto);

        return mapper.toDTO(availability);
    }

    @Transactional
    public void deleteAvailability(Long professionalId, Long availabilityId){
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhum profissional encontrado com este ID: " + professionalId
                ));

        Availability availability = repository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nenhuma disponibilidade encontrada com este ID: " + availabilityId
                ));

        if(!availability.getProfessional().equals(professional)){
            throw new ResourceNotFoundException("A disponibilidade nao pertence ao profissional desejado");
        }

        repository.delete(availability);
        professional.getAvailabilityList().remove(availability);

    }

    public List<AvailabilityResponseDTO> getByProfessionalIdAndDayOfWeek(Long professionalId, DayOfWeek dayOfWeek){

        if(!professionalRepository.existsById(professionalId)){
            throw new ResourceNotFoundException("Nenhum professional encontrado com este ID:" + professionalId);
        }

        if(dayOfWeek == DayOfWeek.SUNDAY){
            throw new ClosedBarberException("Sem disponibilidade aos domingos. A barbearia não abre.");
        }

        List<Availability> availabilities = repository.findByProfessionalIdAndDayOfWeek(professionalId,dayOfWeek);

        return availabilities.stream().map(mapper::toDTO).toList();


    }


        private Availability update(Availability availability, AvailabilityRequestDTO dto) {
        if(dto.professionalId() != null && !dto.professionalId().equals(availability.getProfessional().getId())){
           Professional professional = professionalRepository.findById(dto.professionalId())
                   .orElseThrow(() -> new ResourceNotFoundException("Nenhum profissional encontrado com este ID: " + dto.professionalId()));
           availability.setProfessional(professional);
        }
        if(dto.dayOfWeek() != null){
            availability.setDayOfWeek(dto.dayOfWeek());
        }
        if(dto.startTime() != null){
            availability.setStartTime(dto.startTime());
        }
        if(dto.endTime() != null){
            availability.setEndTime(dto.endTime());
        }
        return availability;
    }


}
