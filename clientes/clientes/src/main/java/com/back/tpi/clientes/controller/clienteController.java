package com.back.tpi.clientes.controller;

import com.back.tpi.clientes.entity.Cliente;
import com.back.tpi.clientes.entityDTO.clienteDTO;
import com.back.tpi.clientes.services.clienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class clienteController {
    
    @Autowired
    private clienteService clienteService;
    
    // Inyección del valor del puerto configurado en la aplicación
    @Value("${server.port}")
    private String puerto;
    
    // POST /clientes - Roles: OPERADOR. Crea cliente.
    @PostMapping("/clientes")
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
    public ResponseEntity<Cliente> obtenerMisDatos(@RequestParam String email) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorEmail(email);
        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
