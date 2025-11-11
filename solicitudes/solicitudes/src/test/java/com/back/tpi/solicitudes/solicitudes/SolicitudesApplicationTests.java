package com.back.tpi.solicitudes.solicitudes; // Ajusta este paquete si es necesario

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // Importación clave

@SpringBootTest
@ActiveProfiles("test") // <--- ESTO ES LO QUE ESTABA FALTANDO/FALANDO
class SolicitudesApplicationTests {

	@Test
	void contextLoads() {
	}
}