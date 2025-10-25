package com.back.tpi.camiones.camiones.services;

import com.back.tpi.camiones.camiones.entity.Camion;
import com.back.tpi.camiones.camiones.entity.Transportista;
import com.back.tpi.camiones.camiones.entityDTO.CamionDTO;
import com.back.tpi.camiones.camiones.entityDTO.TransportistaDTO;
import com.back.tpi.camiones.camiones.repository.CamionRepository;
import com.back.tpi.camiones.camiones.repository.TransportistaRepository;

// correción: usar el Transactional de Spring para soportar readOnly
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CamionService {
    
    private final CamionRepository camionRepository;
    private final TransportistaRepository transportistaRepository;
    
    public TransportistaDTO crearTransportista(TransportistaDTO transportistaDTO) {
        if (transportistaRepository.existsByCodigo(transportistaDTO.getCodigo())) {
            throw new RuntimeException("Ya existe un transportista con el código: " + transportistaDTO.getCodigo());
        }
        
        Transportista transportista = new Transportista();
        transportista.setNombre(transportistaDTO.getNombre());
        transportista.setCodigo(transportistaDTO.getCodigo());
        transportista.setContacto(transportistaDTO.getContacto());
        transportista.setTelefono(transportistaDTO.getTelefono());
        transportista.setEmail(transportistaDTO.getEmail());
        transportista.setActivo(transportistaDTO.getActivo() != null ? transportistaDTO.getActivo() : true);
        
        Transportista transportistaGuardado = transportistaRepository.save(transportista);
        return convertirTransportistaADTO(transportistaGuardado);
    }
    
    @Transactional(readOnly = true)
    public List<TransportistaDTO> listarTodosLosTransportistas() {
        return transportistaRepository.findAll()
                .stream()
                .map(this::convertirTransportistaADTO)
                .collect(Collectors.toList());
    }
    
    public CamionDTO crearCamion(CamionDTO camionDTO) {
        if (camionRepository.existsByPatente(camionDTO.getPatente())) {
            throw new RuntimeException("Ya existe un camión con la patente: " + camionDTO.getPatente());
        }
        
        // Verificar que el transportista existe
        transportistaRepository.findById(camionDTO.getTransportistaId())
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado con ID: " + camionDTO.getTransportistaId()));
        
        Camion camion = new Camion();
        camion.setTransportistaId(camionDTO.getTransportistaId());
        camion.setPatente(camionDTO.getPatente());
        camion.setCapacidadPesoKg(camionDTO.getCapacidadPesoKg());
        camion.setCapacidadVolumenM3(camionDTO.getCapacidadVolumenM3());
        camion.setCostoBaseKm(camionDTO.getCostoBaseKm());
        camion.setDisponible(camionDTO.getDisponible() != null ? camionDTO.getDisponible() : true);
        
        Camion camionGuardado = camionRepository.save(camion);
        return convertirCamionADTO(camionGuardado);
    }
    
    @Transactional(readOnly = true)
    public List<CamionDTO> listarTodosLosCamiones() {
        return camionRepository.findAll()
                .stream()
                .map(this::convertirCamionADTO)
                .collect(Collectors.toList());
    }
    
    public CamionDTO actualizarDisponibilidadCamion(Long id, Boolean disponible) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con ID: " + id));
        
        camion.setDisponible(disponible);
        
        Camion camionActualizado = camionRepository.save(camion);
        return convertirCamionADTO(camionActualizado);
    }
    
    @Transactional(readOnly = true)
    public List<CamionDTO> consultarCamionesDisponibles() {
        return camionRepository.findCamionesDisponibles()
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
    
    private CamionDTO convertirCamionADTO(Camion camion) {
        CamionDTO dto = new CamionDTO();
        dto.setId(camion.getId());
        dto.setTransportistaId(camion.getTransportistaId());
        dto.setPatente(camion.getPatente());
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
        dto.setTelefono(transportista.getTelefono());
        dto.setEmail(transportista.getEmail());
        dto.setActivo(transportista.getActivo());
        return dto;
    }
}