# API de Depósitos - Documentación

## Descripción
Microservicio para la gestión de depósitos y estadías de contenedores. Permite crear depósitos, gestionar estadías de contenedores y consultar información relacionada.

## Endpoints Principales

### 1. POST /api/depositos
**Roles:** OPERADOR  
**Descripción:** Crea un nuevo depósito.

**Request Body:**
```json
{
  "codigo": "DEP001",
  "nombre": "Depósito Central",
  "direccion": "Av. Corrientes 1234",
  "ciudad": "Buenos Aires",
  "provincia": "Buenos Aires",
  "pais": "Argentina",
  "capacidadMaxima": 1000.0,
  "capacidadActual": 0.0,
  "activo": true,
  "observaciones": "Depósito principal"
}
```

**Response:** 201 Created
```json
{
  "id": 1,
  "codigo": "DEP001",
  "nombre": "Depósito Central",
  "direccion": "Av. Corrientes 1234",
  "ciudad": "Buenos Aires",
  "provincia": "Buenos Aires",
  "pais": "Argentina",
  "capacidadMaxima": 1000.0,
  "capacidadActual": 0.0,
  "activo": true,
  "fechaCreacion": "2024-01-15T10:00:00",
  "fechaActualizacion": "2024-01-15T10:00:00",
  "observaciones": "Depósito principal"
}
```

### 2. GET /api/depositos
**Roles:** OPERADOR  
**Descripción:** Lista todos los depósitos.

**Response:** 200 OK
```json
[
  {
    "id": 1,
    "codigo": "DEP001",
    "nombre": "Depósito Central",
    "direccion": "Av. Corrientes 1234",
    "ciudad": "Buenos Aires",
    "provincia": "Buenos Aires",
    "pais": "Argentina",
    "capacidadMaxima": 1000.0,
    "capacidadActual": 250.0,
    "activo": true,
    "fechaCreacion": "2024-01-15T10:00:00",
    "fechaActualizacion": "2024-01-15T10:00:00",
    "observaciones": "Depósito principal"
  }
]
```

### 3. GET /api/depositos/{id}/contenedores
**Roles:** OPERADOR  
**Descripción:** Lista contenedores en un depósito específico.

**Response:** 200 OK
```json
[
  {
    "id": 1,
    "contenedorId": 2,
    "depositoId": 1,
    "fechaIngreso": "2024-01-18T09:15:00",
    "fechaSalida": null,
    "fechaCreacion": "2024-01-18T09:15:00",
    "fechaActualizacion": "2024-01-18T09:15:00",
    "observaciones": "Contenedor actualmente en depósito"
  }
]
```

### 4. POST /api/estadias
**Roles:** OPERADOR  
**Descripción:** Inicia estadía de contenedor en depósito.

**Request Body:**
```json
{
  "contenedorId": 2,
  "depositoId": 1,
  "fechaIngreso": "2024-01-18T09:15:00",
  "observaciones": "Contenedor ingresado para almacenamiento"
}
```

**Response:** 201 Created
```json
{
  "id": 1,
  "contenedorId": 2,
  "depositoId": 1,
  "fechaIngreso": "2024-01-18T09:15:00",
  "fechaSalida": null,
  "fechaCreacion": "2024-01-18T09:15:00",
  "fechaActualizacion": "2024-01-18T09:15:00",
  "observaciones": "Contenedor ingresado para almacenamiento"
}
```

### 5. PATCH /api/estadias/{id}/salida
**Roles:** OPERADOR  
**Descripción:** Finaliza estadía de contenedor en depósito.

**Response:** 200 OK
```json
{
  "id": 1,
  "contenedorId": 2,
  "depositoId": 1,
  "fechaIngreso": "2024-01-18T09:15:00",
  "fechaSalida": "2024-01-20T14:30:00",
  "fechaCreacion": "2024-01-18T09:15:00",
  "fechaActualizacion": "2024-01-20T14:30:00",
  "observaciones": "Contenedor ingresado para almacenamiento"
}
```

## Endpoints Adicionales

### GET /api/depositos/{id}
**Descripción:** Consulta un depósito específico por ID.

### GET /api/depositos/activos
**Descripción:** Lista solo los depósitos activos.

### GET /api/depositos/disponibles
**Descripción:** Lista depósitos con capacidad disponible.

### GET /api/estadias/contenedor/{contenedorId}
**Descripción:** Consulta todas las estadías de un contenedor específico.

### GET /api/estadias/contenedor/{contenedorId}/activa
**Descripción:** Consulta la estadía activa de un contenedor específico.

## Configuración

### Base de Datos
- **Tipo:** H2 (en memoria)
- **Puerto:** 9091
- **Consola H2:** http://localhost:9091/h2-console
- **JDBC URL:** jdbc:h2:mem:depositodb

### Swagger/OpenAPI
- **URL:** http://localhost:9091/swagger-ui.html
- **OpenAPI JSON:** http://localhost:9091/v3/api-docs

## Reglas de Negocio

1. **Creación de Depósitos:**
   - El código debe ser único
   - La capacidad actual no puede ser mayor que la capacidad máxima
   - Los depósitos se crean activos por defecto

2. **Gestión de Estadías:**
   - Un contenedor no puede tener múltiples estadías activas simultáneamente
   - Solo se pueden finalizar estadías que no hayan sido finalizadas previamente
   - El depósito debe estar activo para recibir contenedores

3. **Validaciones:**
   - Todos los campos obligatorios deben ser proporcionados
   - Las fechas de ingreso no pueden ser futuras
   - Las fechas de salida no pueden ser anteriores a las fechas de ingreso

## Códigos de Error

- **400 Bad Request:** Datos de entrada inválidos
- **404 Not Found:** Recurso no encontrado
- **409 Conflict:** Violación de reglas de negocio (ej: contenedor ya tiene estadía activa)

## Ejemplos de Uso

### Flujo Completo de Gestión de Depósito

1. **Crear depósito:**
```bash
curl -X POST http://localhost:9091/api/depositos \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "DEP001",
    "nombre": "Depósito Central",
    "direccion": "Av. Corrientes 1234",
    "ciudad": "Buenos Aires",
    "provincia": "Buenos Aires",
    "pais": "Argentina",
    "capacidadMaxima": 1000.0,
    "observaciones": "Depósito principal"
  }'
```

2. **Iniciar estadía de contenedor:**
```bash
curl -X POST http://localhost:9091/api/estadias \
  -H "Content-Type: application/json" \
  -d '{
    "contenedorId": 1,
    "depositoId": 1,
    "observaciones": "Contenedor ingresado"
  }'
```

3. **Consultar contenedores en depósito:**
```bash
curl -X GET http://localhost:9091/api/depositos/1/contenedores
```

4. **Finalizar estadía:**
```bash
curl -X PATCH http://localhost:9091/api/estadias/1/salida
```
