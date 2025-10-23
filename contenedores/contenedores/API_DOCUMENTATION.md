# API de Gestión de Contenedores

## Descripción
API REST para la gestión de contenedores con control de roles (CLIENTE y OPERADOR).

## Endpoints Disponibles

### 1. POST /contenedores
**Roles:** CLIENTE | OPERADOR  
**Descripción:** Crea un nuevo contenedor

**Request Body:**
```json
{
  "codigo": "CONT001",
  "tipo": "20ft",
  "capacidad": 20.0,
  "ubicacion": "Puerto Buenos Aires",
  "clienteId": 1,
  "observaciones": "Contenedor estándar"
}
```

**Response:** 201 Created
```json
{
  "id": 1,
  "codigo": "CONT001",
  "tipo": "20ft",
  "capacidad": 20.0,
  "ubicacion": "Puerto Buenos Aires",
  "estado": "PENDIENTE",
  "clienteId": 1,
  "fechaCreacion": "2024-01-15T10:00:00",
  "fechaActualizacion": "2024-01-15T10:00:00",
  "fechaEntrega": null,
  "observaciones": "Contenedor estándar"
}
```

### 2. GET /contenedores
**Roles:** OPERADOR  
**Descripción:** Lista todos los contenedores

**Response:** 200 OK
```json
[
  {
    "id": 1,
    "codigo": "CONT001",
    "tipo": "20ft",
    "capacidad": 20.0,
    "ubicacion": "Puerto Buenos Aires",
    "estado": "PENDIENTE",
    "clienteId": 1,
    "fechaCreacion": "2024-01-15T10:00:00",
    "fechaActualizacion": "2024-01-15T10:00:00",
    "fechaEntrega": null,
    "observaciones": "Contenedor estándar"
  }
]
```

### 3. GET /contenedores/{id}
**Roles:** CLIENTE (dueño) | OPERADOR  
**Descripción:** Consulta un contenedor específico por ID

**Response:** 200 OK o 404 Not Found

### 4. PATCH /contenedores/{id}/estado
**Roles:** OPERADOR  
**Descripción:** Actualiza el estado de un contenedor

**Request Body:**
```json
"EN_TRANSITO"
```

**Estados disponibles:**
- PENDIENTE
- EN_TRANSITO
- ENTREGADO
- DEVUELTO
- MANTENIMIENTO

### 5. GET /contenedores/pendientes
**Roles:** OPERADOR  
**Descripción:** Consulta contenedores pendientes con filtros opcionales

**Query Parameters:**
- `estado` (opcional): Estado del contenedor para filtrar
- `fechaDesde` (opcional): Fecha desde para filtrar (formato: yyyy-MM-ddTHH:mm:ss)

**Ejemplos:**
- `GET /contenedores/pendientes` - Todos los pendientes
- `GET /contenedores/pendientes?estado=PENDIENTE` - Solo pendientes
- `GET /contenedores/pendientes?fechaDesde=2024-01-01T00:00:00` - Desde fecha específica

### 6. GET /contenedores/cliente/{clienteId}
**Descripción:** Consulta todos los contenedores de un cliente específico

### 7. GET /contenedores/codigo/{codigo}
**Descripción:** Consulta un contenedor por su código único

## Estados de Contenedor

| Estado | Descripción |
|--------|-------------|
| PENDIENTE | Contenedor creado, esperando procesamiento |
| EN_TRANSITO | Contenedor en camino a destino |
| ENTREGADO | Contenedor entregado al cliente |
| DEVUELTO | Contenedor devuelto al puerto |
| MANTENIMIENTO | Contenedor en reparación |

## Configuración de Base de Datos

La aplicación utiliza H2 en memoria con los siguientes datos de ejemplo:
- Puerto: 8080
- Consola H2: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:contenedordb

## Documentación Swagger

Una vez iniciada la aplicación, la documentación interactiva está disponible en:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Ejecución

```bash
mvn spring-boot:run
```

## Estructura del Proyecto

```
src/main/java/com/back/tpi/contenedores/contenedores/
├── config/
│   └── SecurityConfig.java          # Configuración de seguridad
├── controller/
│   └── contenedorController.java    # Controlador REST
├── entity/
│   ├── Contenedor.java             # Entidad principal
│   └── EstadoContenedor.java       # Enum de estados
├── entityDTO/
│   └── contenedorDTO.java          # DTO para transferencia
├── repository/
│   └── contenedorRepository.java   # Repositorio JPA
└── services/
    └── contenedoresService.java     # Lógica de negocio
```

## Notas de Seguridad

- En un entorno de producción, implementar autenticación JWT
- Validar permisos de usuario según roles
- Implementar logging de auditoría
- Validar entrada de datos más estricta
