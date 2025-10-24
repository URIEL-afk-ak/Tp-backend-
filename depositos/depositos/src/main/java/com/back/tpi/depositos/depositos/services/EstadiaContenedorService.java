package com.back.tpi.depositos.depositos.services;

import com.back.tpi.depositos.depositos.entity.EstadiaContenedor;
import com.back.tpi.depositos.depositos.entityDTO.EstadiaContenedorDTO;
import com.back.tpi.depositos.depositos.repository.EstadiaContenedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EstadiaContenedorService {
    
    private final EstadiaContenedorRepository estadiaRepository;
    
    public EstadiaContenedorDTO iniciarEstadia(EstadiaContenedorDTO estadiaDTO) {
        // Validar que el contenedor no tenga estadía activa
        Optional<EstadiaContenedor> estadiaActiva = estadiaRepository.findEstadiaActivaByContenedorId(estadiaDTO.getContenedorId());
        if (estadiaActiva.isPresent()) {
            throw new RuntimeException("El contenedor ya tiene una estadía activa");
        }
        
        EstadiaContenedor estadia = new EstadiaContenedor();
        estadia.setContenedorId(estadiaDTO.getContenedorId());
        estadia.setDepositoId(estadiaDTO.getDepositoId());
        estadia.setFechaEntrada(estadiaDTO.getFechaEntrada() != null ? estadiaDTO.getFechaEntrada() : LocalDateTime.now());
        
        EstadiaContenedor estadiaGuardada = estadiaRepository.save(estadia);
        return convertirADTO(estadiaGuardada);
    }
    
    public EstadiaContenedorDTO finalizarEstadia(Long id, Double costoPorDia) {
        EstadiaContenedor estadia = estadiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estadía no encontrada con ID: " + id));
        
        if (estadia.getFechaSalida() != null) {
            throw new RuntimeException("La estadía ya fue finalizada");
        }
        
        estadia.setFechaSalida(LocalDateTime.now());
        
        // Calcular días de estadía y costo
        long dias = ChronoUnit.DAYS.between(estadia.getFechaEntrada(), estadia.getFechaSalida());
        estadia.setDiasEstadia((int) dias);
        estadia.setCostoCalculado(dias * costoPorDia);
        
        EstadiaContenedor estadiaActualizada = estadiaRepository.save(estadia);
        return convertirADTO(estadiaActualizada);
    }
    
    @Transactional(readOnly = true)
    public List<EstadiaContenedorDTO> consultarEstadiasPorDeposito(Long depositoId) {
        return estadiaRepository.findByDepositoId(depositoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<EstadiaContenedorDTO> consultarEstadiasActivasEnDeposito(Long depositoId) {
        return estadiaRepository.findEstadiasActivasByDepositoId(depositoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<EstadiaContenedorDTO> consultarEstadiasPorContenedor(Long contenedorId) {
        return estadiaRepository.findByContenedorId(contenedorId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<EstadiaContenedorDTO> consultarEstadiaActivaDeContenedor(Long contenedorId) {
        return estadiaRepository.findEstadiaActivaByContenedorId(contenedorId)
                .map(this::convertirADTO);
    }
    
    private EstadiaContenedorDTO convertirADTO(EstadiaContenedor estadia) {
        EstadiaContenedorDTO dto = new EstadiaContenedorDTO();
        dto.setId(estadia.getId());
        dto.setContenedorId(estadia.getContenedorId());
        dto.setDepositoId(estadia.getDepositoId());
        dto.setFechaEntrada(estadia.getFechaEntrada());
        dto.setFechaSalida(estadia.getFechaSalida());
        dto.setDiasEstadia(estadia.getDiasEstadia());
        dto.setCostoCalculado(estadia.getCostoCalculado());
        dto.setFechaCreacion(estadia.getFechaCreacion());
        return dto;
    }
}