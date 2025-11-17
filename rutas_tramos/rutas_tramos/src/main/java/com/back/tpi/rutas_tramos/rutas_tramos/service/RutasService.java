// Paquete: com.back.tpi.rutas_tramos.rutas_tramos.service
// Archivo: RutasService.java

package com.back.tpi.rutas_tramos.rutas_tramos.service;

import com.back.tpi.rutas_tramos.rutas_tramos.entity.EstadoTramo;
import com.back.tpi.rutas_tramos.rutas_tramos.entity.Tramo;
import com.back.tpi.rutas_tramos.rutas_tramos.repository.TramoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate; 
import java.util.List;
import java.util.Map;
import java.util.Random; // Para simular valores

@Service
@RequiredArgsConstructor
@Transactional
public class RutasService {

    private final TramoRepository tramoRepository;
    private final RestTemplate restTemplate; 

    // URLs de microservicios inyectadas desde application.properties
    @Value("${microservice.camiones.url:http://localhost:8088/api}")
    private String camionesUrl;
    @Value("${microservice.contenedores.url:http://localhost:8082/api}")
    private String contenedoresUrl;

    /**
     * Simula la creación de un tramo en estado BORRADOR con valores estimados.
     */
    public Tramo previewRuta(Long solicitudId, String origen, String destino, Double distanciaKm) { 
        Tramo tramo = new Tramo();
        tramo.setSolicitudId(solicitudId);
        tramo.setOrigen(origen);
        tramo.setDestino(destino);
        tramo.setDistanciaKmEstimada(distanciaKm); // Almacena la distancia estimada
        tramo.setEstado(EstadoTramo.BORRADOR);
        
        // Simulación de otros valores
        Random random = new Random();
        // Simulación de costo estimado basado en distancia
        tramo.setCostoEstimado(Math.round(distanciaKm * (100 + random.nextDouble() * 50)) / 100.0);
        // Simulación de duración en minutos
        tramo.setDuracionMinutos(Math.max(10L, (long) (distanciaKm * 1.5 + random.nextInt(60)))); 
        
        return tramo;
    }

    /**
     * Asigna un camión a un tramo (validando capacidad) y lo guarda.
     */
    public Tramo asignarRuta(Tramo tramo) {
        Long contenedorId = getContenedorIdForSolicitud(tramo.getSolicitudId());
        Long camionId = tramo.getCamionId(); // El ID del camión viene en el tramo a asignar

        // 1. Obtener datos del contenedor (peso y volumen)
        Map contenedorData;
        try {
            // GET /contenedores/{id}
            contenedorData = restTemplate.getForObject(contenedoresUrl + "/contenedores/" + contenedorId, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener datos del Contenedor " + contenedorId);
        }

        Double pesoContenedor = (Double) contenedorData.get("pesoKg");
        Double volumenContenedor = (Double) contenedorData.get("volumenM3");

        // 2. Obtener datos del camión (capacidad)
        Map camionData;
        try {
            // GET /camiones/{id}
            camionData = restTemplate.getForObject(camionesUrl + "/camiones/" + camionId, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener datos del Camión " + camionId);
        }

        double maxPesoCamion = (Double) camionData.get("capacidadPesoKg");
        double maxVolumenCamion = (Double) camionData.get("capacidadVolumenM3");

        // 3. Validar capacidad
        if (pesoContenedor > maxPesoCamion) {
            throw new RuntimeException("El peso del contenedor (" + pesoContenedor + " kg) supera la capacidad máxima del camión (" + maxPesoCamion + " kg).");
        }
        if (volumenContenedor > maxVolumenCamion) {
            throw new RuntimeException("El volumen del contenedor (" + volumenContenedor + " m3) supera la capacidad máxima del camión (" + maxVolumenCamion + " m3).");
        }
        
        // 4. Asignar camión y guardar
        tramo.setCamionId(camionId);
        // Si el tramo es BORRADOR, se actualiza a PROGRAMADA al asignarle un camión
        if (tramo.getEstado() == EstadoTramo.BORRADOR) {
             tramo.setEstado(EstadoTramo.PROGRAMADA);
        }

        return tramoRepository.save(tramo);
    }
    
    /**
     * Actualiza el estado del tramo y registra valores reales si se completa (Req. 8).
     */
    public Tramo actualizarEstado(Long id, EstadoTramo nuevoEstado) {
        Tramo tramo = tramoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tramo no encontrado"));

        // Si se completa, se simula la finalización de valores REALES (Req. 8)
        if (nuevoEstado == EstadoTramo.COMPLETADO) {
            // Simulación de valores reales
            tramo.setDistanciaKmReal(tramo.getDistanciaKmEstimada() * 1.05); // +5% de error simulado
            tramo.setCostoReal(calcularCostoReal(tramo)); // Calcula el costo real
        }
        
        tramo.setEstado(nuevoEstado);
        return tramoRepository.save(tramo);
    }

    @Transactional(readOnly = true)
    public List<Tramo> listarTramosPorSolicitud(Long solicitudId) {
        return tramoRepository.findBySolicitudId(solicitudId);
    }
    
    @Transactional(readOnly = true)
    public List<Tramo> listarAsignados(Long transportistaId) {
        return tramoRepository.findByTransportistaId(transportistaId);
    }
    
    // --- Métodos Auxiliares ---

    private Long getContenedorIdForSolicitud(Long solicitudId) {
        // En una implementación real, esto sería una llamada al microservicio de Solicitudes/Contenedores.
        // Se simula con un valor fijo, asumiendo un Contenedor por Solicitud.
        return solicitudId; 
    }
    
    private Double calcularCostoReal(Tramo tramo) {
        // Simulación: Se usa una regla de tres simple basada en la distancia real vs estimada.
        // El cálculo real debería obtener el CostoBaseKm del Camión.
        // Fórmula simulada: CostoEstimado * (DistanciaReal / DistanciaEstimada)
        return Math.round(tramo.getCostoEstimado() * (tramo.getDistanciaKmReal() / tramo.getDistanciaKmEstimada()) * 100.0) / 100.0;
    }

    /**
     * Asigna un camión a un tramo existente (Req 4)
     */
    public Tramo asignarCamionATramo(Long tramoId, Long camionId) {
        Tramo tramo = tramoRepository.findById(tramoId)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado con id: " + tramoId));

        // Validar que el camión no esté ya asignado a este tramo
        if (tramo.getCamionId() != null && tramo.getCamionId().equals(camionId)) {
            throw new RuntimeException("El camión ya está asignado a este tramo");
        }

        Long contenedorId = getContenedorIdForSolicitud(tramo.getSolicitudId());

        // 1. Obtener datos del contenedor (peso y volumen)
        Map<String, Object> contenedorData;
        try {
            contenedorData = restTemplate.getForObject(contenedoresUrl + "/contenedores/" + contenedorId, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener datos del Contenedor " + contenedorId);
        }

        Double pesoContenedor = (Double) contenedorData.get("pesoKg");
        Double volumenContenedor = (Double) contenedorData.get("volumenM3");

        // 2. Obtener datos del camión (capacidad)
        Map<String, Object> camionData;
        try {
            camionData = restTemplate.getForObject(camionesUrl + "/camiones/" + camionId, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener datos del Camión " + camionId);
        }

        double maxPesoCamion = (Double) camionData.get("capacidadPesoKg");
        double maxVolumenCamion = (Double) camionData.get("capacidadVolumenM3");

        // 3. Validar capacidad
        if (pesoContenedor > maxPesoCamion) {
            throw new RuntimeException("El peso del contenedor (" + pesoContenedor + " kg) supera la capacidad máxima del camión (" + maxPesoCamion + " kg).");
        }
        if (volumenContenedor > maxVolumenCamion) {
            throw new RuntimeException("El volumen del contenedor (" + volumenContenedor + " m3) supera la capacidad máxima del camión (" + maxVolumenCamion + " m3).");
        }

        // 4. Asignar camión y actualizar estado si es necesario
        tramo.setCamionId(camionId);
        
        // Si el tramo está en estado BORRADOR, actualizar a PROGRAMADA
        if (tramo.getEstado() == EstadoTramo.BORRADOR) {
            tramo.setEstado(EstadoTramo.PROGRAMADA);
        }

        return tramoRepository.save(tramo);
    }

}