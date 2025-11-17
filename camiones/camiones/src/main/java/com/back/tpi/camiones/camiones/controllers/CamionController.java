package com.back.tpi.camiones.camiones.controllers;

import com.back.tpi.camiones.camiones.entityDTO.CamionDTO;
import com.back.tpi.camiones.camiones.entityDTO.TransportistaDTO;
import com.back.tpi.camiones.camiones.services.CamionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Camiones", description = "API para gestión de camiones y transportistas")
public class CamionController {
    
    private final CamionService camionService;
    
    @PostMapping("/transportistas")
    @Operation(summary = "Crear transportista", description = "Crea un nuevo transportista. Roles: OPERADOR")
    public ResponseEntity<TransportistaDTO> crearTransportista(@RequestBody TransportistaDTO transportistaDTO) {
        try {
            TransportistaDTO transportistaCreado = camionService.crearTransportista(transportistaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(transportistaCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/transportistas/{id}")
    @Operation(summary = "Actualizar transportista", description = "Actualiza la información de un transportista existente. Roles: OPERADOR")
    public ResponseEntity<TransportistaDTO> actualizarTransportista(@PathVariable Long id, @RequestBody TransportistaDTO transportistaDTO) {
        try {
            // El servicio debe buscar, actualizar y guardar el transportista.
            TransportistaDTO transportistaActualizado = camionService.actualizarTransportista(id, transportistaDTO);
            return ResponseEntity.ok(transportistaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/transportistas")
    @Operation(summary = "Listar transportistas", description = "Lista todos los transportistas. Roles: OPERADOR")
    public ResponseEntity<List<TransportistaDTO>> listarTransportistas() {
        List<TransportistaDTO> transportistas = camionService.listarTodosLosTransportistas();
        return ResponseEntity.ok(transportistas);
    }
    
    @PostMapping("/camiones")
    @Operation(summary = "Crear camión", description = "Crea un nuevo camión. Roles: OPERADOR")
    public ResponseEntity<CamionDTO> crearCamion(@RequestBody CamionDTO camionDTO) {
        try {
            CamionDTO camionCreado = camionService.crearCamion(camionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(camionCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/camiones/{id}")
    @Operation(summary = "Actualizar camión", description = "Actualiza la información de un camión existente (peso/volumen/etc). Roles: OPERADOR")
    public ResponseEntity<CamionDTO> actualizarCamion(@PathVariable Long id, @RequestBody CamionDTO camionDTO) {
        try {
            // El servicio debe buscar, actualizar y guardar el camión.
            CamionDTO camionActualizado = camionService.actualizarCamion(id, camionDTO);
            return ResponseEntity.ok(camionActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/camiones")
    @Operation(summary = "Listar camiones", description = "Lista todos los camiones. Roles: OPERADOR")
    public ResponseEntity<List<CamionDTO>> listarCamiones() {
        List<CamionDTO> camiones = camionService.listarTodosLosCamiones();
        return ResponseEntity.ok(camiones);
    }
    
    @PatchMapping("/camiones/{id}/disponible")
    @Operation(summary = "Actualizar disponibilidad del camión", description = "Actualiza la disponibilidad de un camión específico. Roles: OPERADOR")
    public ResponseEntity<CamionDTO> actualizarDisponibilidadCamion(
            @Parameter(description = "ID del camión") @PathVariable Long id,
            @Parameter(description = "Nuevo estado de disponibilidad") @RequestBody Boolean disponible) {
        try {
            CamionDTO camionActualizado = camionService.actualizarDisponibilidadCamion(id, disponible);
            return ResponseEntity.ok(camionActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/camiones/disponibles")
    @Operation(summary = "Consultar camiones disponibles", description = "Consulta camiones disponibles para asignación. Roles: OPERADOR")
    public ResponseEntity<List<CamionDTO>> consultarCamionesDisponibles() {
        List<CamionDTO> camiones = camionService.consultarCamionesDisponibles();
        return ResponseEntity.ok(camiones);
    }
    
    @GetMapping("/camiones/transportista/{transportistaId}")
    @Operation(summary = "Consultar camiones por transportista", description = "Consulta todos los camiones de un transportista específico. Roles: OPERADOR")
    public ResponseEntity<List<CamionDTO>> consultarCamionesPorTransportista(
            @Parameter(description = "ID del transportista") @PathVariable Long transportistaId) {
        List<CamionDTO> camiones = camionService.consultarCamionesPorTransportista(transportistaId);
        return ResponseEntity.ok(camiones);
    }
}