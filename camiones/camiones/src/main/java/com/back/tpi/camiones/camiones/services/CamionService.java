package com.back.tpi.camiones.camiones.services;

import com.back.tpi.camiones.camiones.entity.Camion;
import com.back.tpi.camiones.camiones.entity.Transportista;
import com.back.tpi.camiones.camiones.entityDTO.CamionDTO;
import com.back.tpi.camiones.camiones.entityDTO.TransportistaDTO;
import com.back.tpi.camiones.camiones.repository.CamionRepository;
import com.back.tpi.camiones.camiones.repository.TransportistaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CamionService {
    
    private final CamionRepository camionRepository;
    private final TransportistaRepository transportistaRepository;

    // --- Métodos de Transportista ---
    
    public TransportistaDTO crearTransportista(TransportistaDTO transportistaDTO) {
        if (transportistaRepository.existsByCodigo(transportistaDTO.getCodigo())) {
            throw new RuntimeException("Ya existe un transportista con el código: " + transportistaDTO.getCodigo());
        }
        
        Transportista transportista = new Transportista();
        transportista.setNombre(transportistaDTO.getNombre());
        transportista.setCodigo(transportistaDTO.getCodigo());
        transportista.setContacto(transportistaDTO.getContacto());
        transportista.setActivo(true); // Asume que se crea activo
        
        Transportista transportistaGuardado = transportistaRepository.save(transportista);
        return convertirTransportistaADTO(transportistaGuardado);
    }
    
    /**
     * NUEVO: Actualiza la información de un transportista existente. (Req. -+.)
     */
    public TransportistaDTO actualizarTransportista(Long id, TransportistaDTO dto) {
        Transportista transportista = transportistaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transportista no encontrado con ID: " + id));

        // Actualizar campos
        if (dto.getNombre() != null) {
            transportista.setNombre(dto.getNombre());
        }
        if (dto.getContacto() != null) {
            transportista.setContacto(dto.getContacto());
        }
        if (dto.getActivo() != null) {
            transportista.setActivo(dto.getActivo());
        }

        Transportista actualizado = transportistaRepository.save(transportista);
        return convertirTransportistaADTO(actualizado);
    }

    @Transactional(readOnly = true)
    public List<TransportistaDTO> listarTodosLosTransportistas() {
        return transportistaRepository.findAll()
                .stream()
                .map(this::convertirTransportistaADTO)
                .collect(Collectors.toList());
    }

    // --- Métodos de Camión ---
    
    public CamionDTO crearCamion(CamionDTO camionDTO) {
        if (camionRepository.existsByPatente(camionDTO.getPatente())) {
            throw new RuntimeException("Ya existe un camión con la patente: " + camionDTO.getPatente());
        }
        
        // Verificar existencia del transportista
        transportistaRepository.findById(camionDTO.getTransportistaId())
            .orElseThrow(() -> new RuntimeException("Transportista no encontrado con ID: " + camionDTO.getTransportistaId()));
        
        Camion camion = new Camion();
        camion.setTransportistaId(camionDTO.getTransportistaId());
        camion.setPatente(camionDTO.getPatente());
        camion.setCapacidadPesoKg(camionDTO.getCapacidadPesoKg());
        camion.setCapacidadVolumenM3(camionDTO.getCapacidadVolumenM3());
        camion.setCostoBaseKm(camionDTO.getCostoBaseKm());
        camion.setDisponible(true); // Se crea disponible por defecto
        
        Camion camionGuardado = camionRepository.save(camion);
        return convertirCamionADTO(camionGuardado);
    }

    /**
     * NUEVO: Actualiza la información de un camión existente. (Req. -+.)
     */
    public CamionDTO actualizarCamion(Long id, CamionDTO dto) {
        Camion camion = camionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Camión no encontrado con ID: " + id));

        // Actualizar campos
        if (dto.getPatente() != null) {
            camion.setPatente(dto.getPatente());
        }
        if (dto.getCapacidadPesoKg() != null) {
            camion.setCapacidadPesoKg(dto.getCapacidadPesoKg());
        }
        if (dto.getCapacidadVolumenM3() != null) {
            camion.setCapacidadVolumenM3(dto.getCapacidadVolumenM3());
        }
        if (dto.getCostoBaseKm() != null) {
            camion.setCostoBaseKm(dto.getCostoBaseKm());
        }
        // No modificar disponibilidad o transportistaId, ya que tienen endpoints/lógica específicos.

        Camion actualizado = camionRepository.save(camion);
        return convertirCamionADTO(actualizado);
    }
    
    public CamionDTO actualizarDisponibilidadCamion(Long id, Boolean disponible) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con ID: " + id));
        camion.setDisponible(disponible);
        Camion actualizado = camionRepository.save(camion);
        return convertirCamionADTO(actualizado);
    }

    @Transactional(readOnly = true)
    public List<CamionDTO> consultarCamionesDisponibles() {
        return camionRepository.findCamionesDisponibles()
                .stream()
                .map(this::convertirCamionADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CamionDTO> listarTodosLosCamiones() {
        return camionRepository.findAll()
                .stream()
                .map(this::convertirCamionADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CamionDTO> consultarCamionesPorTransportista(Long transportistaId) {
        return camionRepository.findByTransportistaId(transportistaId)
                .stream()
                .map(this::convertirCamionADTO)
                .collect(Collectors.toList());
    }
    
    // --- Métodos de Conversión DTO ---
    
    private CamionDTO convertirCamionADTO(Camion camion) {
        CamionDTO dto = new CamionDTO();
        dto.setId(camion.getId());
        dto.setTransportistaId(camion.getTransportistaId());
        dto.setPatente(camion.getPatente());
        // Se asume que capacidadPesoKg y capacidadVolumenM3 son los nombres correctos en la Entity.
        dto.setCapacidadPesoKg(camion.getCapacidadPesoKg()); 
        dto.setCapacidadVolumenM3(camion.getCapacidadVolumenM3()); 
        dto.setCostoBaseKm(camion.getCostoBaseKm());
        dto.setDisponible(camion.getDisponible());
        return dto;
    }
    
    private TransportistaDTO convertirTransportistaADTO(Transportista transportista) {
        TransportistaDTO dto = new TransportistaDTO();
        dto.setId(transportista.getId());
        dto.setNombre(transportista.getNombre());
        dto.setCodigo(transportista.getCodigo());
        dto.setContacto(transportista.getContacto());
        dto.setActivo(transportista.getActivo());
        return dto;
    }
}