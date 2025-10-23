package com.back.tpi.clientes.config;

import com.back.tpi.clientes.entity.Cliente;
import com.back.tpi.clientes.repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private clienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos
        if (clienteRepository.count() == 0) {
            // Crear datos de ejemplo
            Cliente cliente1 = new Cliente();
            cliente1.setNombre_completo("Juan Pérez García");
            cliente1.setEmail("juan.perez@email.com");
            cliente1.setTelefono("+54 11 1234-5678");
            cliente1.setFecha_registro(LocalDateTime.now().minusDays(7));
            clienteRepository.save(cliente1);

            Cliente cliente2 = new Cliente();
            cliente2.setNombre_completo("María González López");
            cliente2.setEmail("maria.gonzalez@email.com");
            cliente2.setTelefono("+54 11 2345-6789");
            cliente2.setFecha_registro(LocalDateTime.now().minusDays(6));
            clienteRepository.save(cliente2);

            Cliente cliente3 = new Cliente();
            cliente3.setNombre_completo("Carlos Rodríguez Martínez");
            cliente3.setEmail("carlos.rodriguez@email.com");
            cliente3.setTelefono("+54 11 3456-7890");
            cliente3.setFecha_registro(LocalDateTime.now().minusDays(5));
            clienteRepository.save(cliente3);

            Cliente cliente4 = new Cliente();
            cliente4.setNombre_completo("Ana Fernández Silva");
            cliente4.setEmail("ana.fernandez@email.com");
            cliente4.setTelefono("+54 11 4567-8901");
            cliente4.setFecha_registro(LocalDateTime.now().minusDays(4));
            clienteRepository.save(cliente4);

            Cliente cliente5 = new Cliente();
            cliente5.setNombre_completo("Luis Morales Torres");
            cliente5.setEmail("luis.morales@email.com");
            cliente5.setTelefono("+54 11 5678-9012");
            cliente5.setFecha_registro(LocalDateTime.now().minusDays(3));
            clienteRepository.save(cliente5);

            System.out.println("✅ Datos iniciales cargados exitosamente: " + clienteRepository.count() + " clientes");
        } else {
            System.out.println("ℹ️  La base de datos ya contiene " + clienteRepository.count() + " clientes");
        }
    }
}
