package de.leafgrow.project.service;

import de.leafgrow.project.domain.dto.PotDto;
import de.leafgrow.project.domain.entity.Pot;
import de.leafgrow.project.repository.PotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PotService {

    private final PotRepository potRepository;

    @Autowired
    public PotService(PotRepository potRepository) {
        this.potRepository = potRepository;
    }

    public List<PotDto> getAllPots() {
        return potRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PotDto getPotById(Long id) {
        return potRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    public PotDto createPot(PotDto potDto) {
        Pot pot = mapToEntity(potDto);
        pot = potRepository.save(pot);
        return mapToDto(pot);
    }

    public PotDto updatePot(Long id, PotDto potDto) {
        Optional<Pot> optionalPot = potRepository.findById(id);
        if (optionalPot.isPresent()) {
            Pot pot = optionalPot.get();
            pot.setPlantName(potDto.getPlantName());
            pot.setActivated(potDto.isActivated());
            pot = potRepository.save(pot);
            return mapToDto(pot);
        } else {
            return null;
        }
    }

    public void deletePot(Long id) {
        potRepository.deleteById(id);
    }

    private PotDto mapToDto(Pot pot) {
        PotDto dto = new PotDto();
        dto.setId(pot.getId());
        dto.setPlantName(pot.getPlantName());
        dto.setActivated(pot.isActivated());
        return dto;
    }

    private Pot mapToEntity(PotDto dto) {
        Pot pot = new Pot();
        pot.setPlantName(dto.getPlantName());
        pot.setActivated(dto.isActivated());
        return pot;
    }
}
