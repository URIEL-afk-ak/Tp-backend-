package com.back.tpi.depositos.depositos.controller;

import com.back.tpi.depositos.depositos.entityDTO.DepositoDTO;
import com.back.tpi.depositos.depositos.entityDTO.EstadiaContenedorDTO;
import com.back.tpi.depositos.depositos.services.DepositoService;
import com.back.tpi.depositos.depositos.services.EstadiaContenedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Depósitos", description = "API para gestión de depósitos y estadías de contenedores")
public class DepositoController {
    
    private final DepositoService depositoService;
    private final EstadiaContenedorService estadiaService;
    
    // Endpoints para Depósitos
    @PostMapping("/depositos")
    @Operation(summary = "Crear depósito")
    public ResponseEntity<DepositoDTO> crearDeposito(@RequestBody DepositoDTO depositoDTO) {
        try {
            DepositoDTO depositoCreado = depositoService.crearDeposito(depositoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(depositoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/depositos/{id}")
    @Operation(summary = "Actualizar depósito", description = "Actualiza la información de un depósito existente. Roles: OPERADOR/ADMIN")
    public ResponseEntity<DepositoDTO> actualizarDeposito(@PathVariable Long id, @RequestBody DepositoDTO depositoDTO) {
        try {
            // El servicio debe buscar, actualizar y guardar el depósito.
            DepositoDTO depositoActualizado = depositoService.actualizarDeposito(id, depositoDTO);
            return ResponseEntity.ok(depositoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/depositos")
    @Operation(summary = "Listar todos los depósitos")
    public ResponseEntity<List<DepositoDTO>> listarDepositos() {
        List<DepositoDTO> depositos = depositoService.listarTodosLosDepositos();
        return ResponseEntity.ok(depositos);
    }
    
    @GetMapping("/depositos/{id}")
    @Operation(summary = "Consultar depósito por ID")
    public ResponseEntity<DepositoDTO> consultarDepositoPorId(@PathVariable Long id) {
        Optional<DepositoDTO> deposito = depositoService.consultarDepositoPorId(id);
        return deposito.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoints para Estadías de Contenedores
    @PostMapping("/estadias")
    @Operation(summary = "Iniciar estadía de contenedor")
    public ResponseEntity<EstadiaContenedorDTO> iniciarEstadia(@RequestBody EstadiaContenedorDTO estadiaDTO) {
        try {
            EstadiaContenedorDTO estadiaCreada = estadiaService.iniciarEstadia(estadiaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(estadiaCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/estadias/{id}/finalizar")
    @Operation(summary = "Finalizar estadía de contenedor")
    public ResponseEntity<EstadiaContenedorDTO> finalizarEstadia(
            @PathVariable Long id,
            @RequestParam(defaultValue = "100.0") Double costoPorDia) {
        try {
            EstadiaContenedorDTO estadiaFinalizada = estadiaService.finalizarEstadia(id, costoPorDia);
            return ResponseEntity.ok(estadiaFinalizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/depositos/{id}/contenedores")
    @Operation(summary = "Listar contenedores en depósito")
    public ResponseEntity<List<EstadiaContenedorDTO>> listarContenedoresEnDeposito(@PathVariable Long id) {
        List<EstadiaContenedorDTO> estadias = estadiaService.consultarEstadiasActivasEnDeposito(id);
        return ResponseEntity.ok(estadias);
    }
    
    @GetMapping("/estadias/contenedor/{contenedorId}")
    @Operation(summary = "Consultar estadías por contenedor")
    public ResponseEntity<List<EstadiaContenedorDTO>> consultarEstadiasPorContenedor(@PathVariable Long contenedorId) {
        List<EstadiaContenedorDTO> estadias = estadiaService.consultarEstadiasPorContenedor(contenedorId);
        return ResponseEntity.ok(estadias);
    }
    
    @GetMapping("/estadias/contenedor/{contenedorId}/activa")
    @Operation(summary = "Consultar estadía activa de contenedor")
    public ResponseEntity<EstadiaContenedorDTO> consultarEstadiaActivaDeContenedor(@PathVariable Long contenedorId) {
        Optional<EstadiaContenedorDTO> estadia = estadiaService.consultarEstadiaActivaDeContenedor(contenedorId);
        return estadia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}