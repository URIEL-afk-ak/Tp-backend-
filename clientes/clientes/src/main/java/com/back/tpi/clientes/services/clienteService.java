package com.back.tpi.clientes.services;

import com.back.tpi.clientes.entity.Cliente;
import com.back.tpi.clientes.entityDTO.clienteDTO;
import com.back.tpi.clientes.repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class clienteService {
    
    @Autowired
    private clienteRepository clienteRepository;
    
    public Cliente crearCliente(clienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre_completo(clienteDTO.getNombre_completo());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setFecha_registro(LocalDateTime.now());
        
        return clienteRepository.save(cliente);
    }
    
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}