package com.back.tpi.contenedores.contenedores.services;

import com.back.tpi.contenedores.contenedores.entity.Contenedor;
import com.back.tpi.contenedores.contenedores.entity.EstadoContenedor;
import com.back.tpi.contenedores.contenedores.entityDTO.contenedorDTO;
import com.back.tpi.contenedores.contenedores.repository.contenedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class contenedoresService {
    
    private final contenedorRepository contenedorRepository;
    
    public contenedorDTO crearContenedor(contenedorDTO contenedorDTO) {
        if (contenedorRepository.findByNumeroIdentificacion(contenedorDTO.getNumeroIdentificacion()).isPresent()) {
            throw new RuntimeException("Ya existe un contenedor con el número de identificación: " + contenedorDTO.getNumeroIdentificacion());
        }
        
        Contenedor contenedor = new Contenedor();
        contenedor.setNumeroIdentificacion(contenedorDTO.getNumeroIdentificacion());
        contenedor.setTipo(contenedorDTO.getTipo());
        contenedor.setCapacidad(contenedorDTO.getCapacidad());
        contenedor.setUbicacion(contenedorDTO.getUbicacion());
        contenedor.setEstado(EstadoContenedor.PENDIENTE);
        contenedor.setClienteId(contenedorDTO.getClienteId());
        contenedor.setObservaciones(contenedorDTO.getObservaciones());
        
        Contenedor contenedorGuardado = contenedorRepository.save(contenedor);
        return convertirADTO(contenedorGuardado);
    }
    
    public boolean existePorNumeroIdentificacion(String numeroIdentificacion) {
        return contenedorRepository.existsByNumeroIdentificacion(numeroIdentificacion);
    }
    
    @Transactional(readOnly = true)
    public Optional<contenedorDTO> consultarContenedorPorNumeroIdentificacion(String numeroIdentificacion) {
        return contenedorRepository.findByNumeroIdentificacion(numeroIdentificacion)
                .map(this::convertirADTO);
    }
    
    @Transactional(readOnly = true)
    public List<contenedorDTO> listarTodosLosContenedores() {
        return contenedorRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<contenedorDTO> consultarContenedorPorId(Long id) {
        return contenedorRepository.findById(id)
                .map(this::convertirADTO);
    }
    
    public contenedorDTO actualizarEstadoContenedor(Long id, EstadoContenedor nuevoEstado) {
        Contenedor contenedor = contenedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado con ID: " + id));
        
        contenedor.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoContenedor.ENTREGADO) {
            contenedor.setFechaEntrega(LocalDateTime.now());
        }
        
        Contenedor contenedorActualizado = contenedorRepository.save(contenedor);
        return convertirADTO(contenedorActualizado);
    }
    
    @Transactional(readOnly = true)
    public List<contenedorDTO> consultarContenedoresPendientes() {
        return contenedorRepository.findContenedoresPendientes()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<contenedorDTO> consultarContenedoresPendientesConFiltros(EstadoContenedor estado, LocalDateTime fechaDesde) {
        return contenedorRepository.findPendientesConFiltros(estado, fechaDesde)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<contenedorDTO> consultarContenedoresPorCliente(Long clienteId) {
        return contenedorRepository.findByClienteId(clienteId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    private contenedorDTO convertirADTO(Contenedor contenedor) {
        contenedorDTO dto = new contenedorDTO();
        dto.setId(contenedor.getId());
        dto.setClienteId(contenedor.getClienteId());
        dto.setNumeroIdentificacion(contenedor.getNumeroIdentificacion());
        dto.setTipo(contenedor.getTipo());
        dto.setCapacidad(contenedor.getCapacidad());
        dto.setUbicacion(contenedor.getUbicacion());
        dto.setEstado(contenedor.getEstado());
        dto.setFechaCreacion(contenedor.getFechaCreacion());
        dto.setFechaActualizacion(contenedor.getFechaActualizacion());
        dto.setFechaEntrega(contenedor.getFechaEntrega());
        dto.setObservaciones(contenedor.getObservaciones());
        return dto;
    }
}