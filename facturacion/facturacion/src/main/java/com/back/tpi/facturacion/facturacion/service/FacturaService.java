package com.back.tpi.facturacion.facturacion.service;

import com.back.tpi.facturacion.facturacion.entity.Factura;
import com.back.tpi.facturacion.facturacion.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate; // Usado para simular la comunicación entre microservicios
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class FacturaService {
    private final FacturaRepository repo;
    private final RestTemplate restTemplate; // Cliente HTTP para comunicación

    // Inyección de URLs de microservicios (deben estar en application.properties)
    @Value("${microservice.rutas.url:http://localhost:8085/api}") // Ejemplo de inyección
    private String rutasUrl;
    @Value("${microservice.depositos.url:http://localhost:8083/api}")
    private String depositosUrl;
    @Value("${microservice.contenedores.url:http://localhost:8082/api}")
    private String contenedoresUrl;
    
    // Placeholder para TarifaRepository
    // private final TarifaRepository tarifaRepo;

    public FacturaService(FacturaRepository repo, RestTemplate restTemplate) {
        this.repo = repo; 
        this.restTemplate = restTemplate;
    }
    
    // MÉTODOS DE GESTIÓN DE TARIFAS (Req. -+.)
    // **********************************************
    // Usando Object como placeholder para el DTO/Entity de Tarifa
    public Object crearTarifa(Object tarifaDTO) {
        // Lógica de creación de Tarifa (persistencia)
        return tarifaDTO; // Placeholder
    }
    
    public Object actualizarTarifa(Long id, Object tarifaDTO) {
        // Lógica de actualización de Tarifa (persistencia)
        return tarifaDTO; // Placeholder
    }
    // **********************************************

    public double estimarTarifa(double distanciaKm, double pesoKg) {
        double base = 1000; // ejemplo
        double porKm = distanciaKm * 50;
        double porPeso = Math.min(5000, pesoKg * 0.1);
        return Math.round((base + porKm + porPeso) * 100.0) / 100.0;
    }
    
    /**
     * NUEVO: Calcula el costo real final de la entrega para una solicitud. (Req. 7)
     * Orquesta la obtención de datos de tramos, estadías y contenedor.
     */
    public Factura calcularYRegistrarFacturaFinal(Long solicitudId) {
        double costoTotal = 0.0;
        double distanciaTotalKm = 0.0;
        
        // 1. Obtener datos del Contenedor (simulación, debería ser una llamada HTTP)
        // Ejemplo de llamada: restTemplate.getForObject(contenedoresUrl + "/contenedores/solicitud/" + solicitudId, Map.class);
        double pesoContenedor = 10000.0; // Simulados
        
        // 2. Obtener todos los Tramos REALES de la solicitud (Llamada HTTP a Rutas)
        try {
            // Se asume que RutasController/Service tiene un endpoint para esto: /api/tramos/solicitud/{solicitudId}
            List<Map> tramos = restTemplate.getForObject(rutasUrl + "/tramos/solicitud/" + solicitudId, List.class);

            for (Map tramo : tramos) {
                // Asume que los tramos devuelven la distancia real y el costo real del transporte.
                distanciaTotalKm += (Double) tramo.getOrDefault("distanciaKmReal", 0.0);
                costoTotal += (Double) tramo.getOrDefault("costoReal", 0.0); 
            }
        } catch (Exception e) {
             throw new RuntimeException("Error al obtener tramos para la solicitud: " + e.getMessage());
        }

        // 3. Obtener todas las Estadías FINALIZADAS (Llamada HTTP a Depósitos)
        try {
            // Se asume un endpoint para obtener todas las estadías finalizadas: /api/estadias/solicitud/{solicitudId}/finalizadas
            List<Map> estadias = restTemplate.getForObject(depositosUrl + "/estadias/solicitud/" + solicitudId + "/finalizadas", List.class);
            
            for (Map estadia : estadias) {
                // Se asume que el costo de estadía ya fue calculado al finalizar la estadía (Req. 7.0)
                costoTotal += (Double) estadia.getOrDefault("costoTotal", 0.0);
            }
        } catch (Exception e) {
            // Si no hay estadías, simplemente se ignora.
        }


        // 4. Agregar costos por Peso y Volumen (Tarifa Base por Contenedor)
        double costoBaseContenedor = estimarTarifa(0, pesoContenedor); 
        costoTotal += costoBaseContenedor;
        
        // 5. Crear o Actualizar Factura
        Optional<Factura> facturaExistente = repo.findBySolicitudId(solicitudId); 
        Factura factura = facturaExistente.orElseGet(Factura::new);
        
        factura.setSolicitudId(solicitudId);
        factura.setCostoTotal(Math.round(costoTotal * 100.0) / 100.0);
        factura.setDistanciaTotalKm(distanciaTotalKm);
        
        return repo.save(factura);
    }
    
    public Factura crearFactura(Factura f) {
        return repo.save(f);
    }

    public Factura marcarPago(Long id, boolean pagado) {
        Factura f = repo.findById(id).orElseThrow(() -> new RuntimeException("Factura no encontrada: " + id));
        f.setPagado(pagado);
        return repo.save(f);
    }
}