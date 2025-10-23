package com.back.tpi.contenedores.contenedores.controller;

import com.back.tpi.contenedores.contenedores.entity.EstadoContenedor;
import com.back.tpi.contenedores.contenedores.entityDTO.contenedorDTO;
import com.back.tpi.contenedores.contenedores.services.contenedoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Contenedores", description = "API para gestión de contenedores")
public class contenedorController {
    
    private final contenedoresService contenedoresService;
    
    @PostMapping("/contenedores")
    @Operation(summary = "Crear contenedor", description = "Crea un nuevo contenedor. Roles: CLIENTE | OPERADOR")
    public ResponseEntity<contenedorDTO> crearContenedor(@RequestBody contenedorDTO contenedorDTO) {
        try {
            contenedorDTO contenedorCreado = contenedoresService.crearContenedor(contenedorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(contenedorCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/contenedores")
    @Operation(summary = "Listar todos los contenedores", description = "Lista todos los contenedores. Roles: OPERADOR")
    public ResponseEntity<List<contenedorDTO>> listarTodosLosContenedores() {
        List<contenedorDTO> contenedores = contenedoresService.listarTodosLosContenedores();
        return ResponseEntity.ok(contenedores);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Consultar contenedor específico", description = "Consulta un contenedor específico por ID. Roles: CLIENTE (dueño) | OPERADOR")
    public ResponseEntity<contenedorDTO> consultarContenedorPorId(
            @Parameter(description = "ID del contenedor") @PathVariable Long id) {
        Optional<contenedorDTO> contenedor = contenedoresService.consultarContenedorPorId(id);
        return contenedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del contenedor", description = "Actualiza el estado de un contenedor específico. Roles: OPERADOR")
    public ResponseEntity<contenedorDTO> actualizarEstadoContenedor(
            @Parameter(description = "ID del contenedor") @PathVariable Long id,
            @Parameter(description = "Nuevo estado del contenedor") @RequestBody EstadoContenedor nuevoEstado) {
        try {
            contenedorDTO contenedorActualizado = contenedoresService.actualizarEstadoContenedor(id, nuevoEstado);
            return ResponseEntity.ok(contenedorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/pendientes")
    @Operation(summary = "Consultar contenedores pendientes", description = "Consulta contenedores pendientes de entrega con filtros. Roles: OPERADOR")
    public ResponseEntity<List<contenedorDTO>> consultarContenedoresPendientes(
            @Parameter(description = "Estado del contenedor para filtrar") @RequestParam(required = false) EstadoContenedor estado,
            @Parameter(description = "Fecha desde para filtrar (formato: yyyy-MM-ddTHH:mm:ss)") @RequestParam(required = false) String fechaDesde) {
        
        List<contenedorDTO> contenedores;
        
        if (estado != null && fechaDesde != null) {
            LocalDateTime fecha = LocalDateTime.parse(fechaDesde);
            contenedores = contenedoresService.consultarContenedoresPendientesConFiltros(estado, fecha);
        } else {
            contenedores = contenedoresService.consultarContenedoresPendientes();
        }
        
        return ResponseEntity.ok(contenedores);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Consultar contenedores por cliente", description = "Consulta todos los contenedores de un cliente específico")
    public ResponseEntity<List<contenedorDTO>> consultarContenedoresPorCliente(
            @Parameter(description = "ID del cliente") @PathVariable Long clienteId) {
        List<contenedorDTO> contenedores = contenedoresService.consultarContenedoresPorCliente(clienteId);
        return ResponseEntity.ok(contenedores);
    }
    
    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Consultar contenedor por código", description = "Consulta un contenedor por su código único")
    public ResponseEntity<contenedorDTO> consultarContenedorPorCodigo(
            @Parameter(description = "Código del contenedor") @PathVariable String codigo) {
        Optional<contenedorDTO> contenedor = contenedoresService.consultarContenedorPorCodigo(codigo);
        return contenedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
