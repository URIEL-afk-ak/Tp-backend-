package com.back.tpi.clientes.controller;

import com.back.tpi.clientes.entity.Cliente;
import com.back.tpi.clientes.entityDTO.clienteDTO;
import com.back.tpi.clientes.services.clienteService;
import com.back.tpi.clientes.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Clientes", description = "Operaciones CRUD para gestionar clientes")
public class clienteController {
    
    @Autowired
    private clienteService clienteService;
    
    // Inyección del valor del puerto configurado en la aplicación
    @Value("${server.port}")
    private String puerto;
    
    // POST /clientes - Roles: OPERADOR. Crea cliente.
    @PostMapping("/clientes")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN')")
    @Operation(
        summary = "Registrar nuevo cliente",
        description = "Crea un cliente con los datos proporcionados",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Cliente creado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
            ),
            @ApiResponse(responseCode = "400", description = "Datos invalidos")
        }
    )
    public ResponseEntity<Cliente> crearCliente(@RequestBody clienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteService.crearCliente(clienteDTO);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    // GET /clientes/{id} - Roles: OPERADOR. Devuelve cliente por ID.
    @GetMapping("/clientes/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'ADMIN')")
    @Operation(
        summary = "Obtener cliente por id",
        description = "Busca un cliente especifico mediante su identificador",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cliente encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
            ),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
        }
    )
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorId(id);
        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // GET /clientes/me - Roles: CLIENTE. Devuelve datos propios del cliente autenticado.
    @GetMapping("/clientes/me")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
        summary = "Obtener datos del cliente autenticado",
        description = "Devuelve los datos del cliente asociados al token JWT",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cliente encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
            ),
            @ApiResponse(responseCode = "401", description = "Token invalido o faltante"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
        }
    )
    public ResponseEntity<Cliente> obtenerMisDatos(Authentication authentication) {
        String email = null;
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            email = JwtUtils.extractEmail(jwt);
            if (email == null) {
                email = JwtUtils.extractUsername(jwt);
            }
        }
        
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        Optional<Cliente> cliente = clienteService.obtenerClientePorEmail(email);
        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
