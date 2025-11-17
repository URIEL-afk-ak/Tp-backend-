package com.back.tpi.depositos.depositos.services;

import com.back.tpi.depositos.depositos.entity.Deposito;
import com.back.tpi.depositos.depositos.entityDTO.DepositoDTO;
import com.back.tpi.depositos.depositos.repository.DepositoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepositoService {
    
    private final DepositoRepository depositoRepository;
    
    public DepositoDTO crearDeposito(DepositoDTO depositoDTO) {
        Deposito deposito = new Deposito();
        deposito.setNombre(depositoDTO.getNombre());
        deposito.setDireccion(depositoDTO.getDireccion());
        deposito.setUbicacionGeografica(depositoDTO.getUbicacionGeografica());
        
        Deposito depositoGuardado = depositoRepository.save(deposito);
        return convertirADTO(depositoGuardado);
    }
    
    /**
     * NUEVO: Método para actualizar depósito (Req. -+.)
     */
    public DepositoDTO actualizarDeposito(Long id, DepositoDTO depositoDTO) {
        Deposito deposito = depositoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Depósito no encontrado con ID: " + id));

        // Actualizar campos
        if (depositoDTO.getNombre() != null) {
            deposito.setNombre(depositoDTO.getNombre());
        }
        if (depositoDTO.getDireccion() != null) {
            deposito.setDireccion(depositoDTO.getDireccion());
        }
        if (depositoDTO.getUbicacionGeografica() != null) {
            deposito.setUbicacionGeografica(depositoDTO.getUbicacionGeografica());
        }

        Deposito depositoActualizado = depositoRepository.save(deposito);
        return convertirADTO(depositoActualizado);
    }
    
    @Transactional(readOnly = true)
    public List<DepositoDTO> listarTodosLosDepositos() {
        return depositoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Optional<DepositoDTO> consultarDepositoPorId(Long id) {
        return depositoRepository.findById(id)
                .map(this::convertirADTO);
    }
    
    @Transactional(readOnly = true)
    public List<DepositoDTO> buscarDepositosPorNombre(String nombre) {
        return depositoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    private DepositoDTO convertirADTO(Deposito deposito) {
        DepositoDTO dto = new DepositoDTO();
        dto.setId(deposito.getId());
        dto.setNombre(deposito.getNombre());
        dto.setDireccion(deposito.getDireccion());
        dto.setUbicacionGeografica(deposito.getUbicacionGeografica());
        // Se asume que Deposito tiene fechaCreacion
        // dto.setFechaCreacion(deposito.getFechaCreacion());
        return dto;
    }
}