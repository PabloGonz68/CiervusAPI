# Creación de una API Segura - Gestion de Alquiler de Productos

## Nombre del proyecto
**Ciervus**

## Idea del proyecto
Ciervus es una plataforma web diseñada para facilitar el alquiler de productos entre particulares. Los usuarios pueden listar productos que no utilizan con frecuencia para alquilarlos, y también pueden buscar y reservar artículos que necesitan temporalmente. El objetivo principal es fomentar el consumo colaborativo, el ahorro económico y la sostenibilidad.

## Justificación del proyecto
Este proyecto busca ofrecer una solución práctica y accesible para aprovechar al máximo los recursos disponibles en una comunidad. Permite a los usuarios monetizar productos en desuso y acceder a herramientas, equipos y otros objetos sin necesidad de comprarlos. Al conectar directamente a propietarios y arrendatarios, Ciervus también fomenta la confianza y la interacción local.

## Descripción detallada de las tablas

### 1. Usuarios
Representa a los usuarios de la plataforma, quienes pueden ser propietarios y/o arrendatarios.

| Campo            | Tipo     | Restricciones           |
|-------------------|----------|-------------------------|
| **id**           | Long     | PRIMARY KEY             |
| **username**       | String   | NULLABLE                |
| **email**        | String   | UNIQUE, NULLABLE        |
| **password**   | String   | NULLABLE                |
| **roles**          | String   | NULLABLE                |
| **fecha_registro** | Date    | NULLABLE                |

---

### 2. Productos
Contiene los datos de los productos disponibles para alquilar.

| Campo               | Tipo     | Restricciones           |
|----------------------|----------|-------------------------|
| **id**              | Long     | PRIMARY KEY             |
| **nombre**          | String   | NULLABLE                |
| **descripcion**     | String   |                         |
| **precio**          | double   | NULLABLE                |
| **fecha_publicacion** | Date    | NULLABLE                |
| **propietario_id**  | int      | FOREIGN KEY (Usuarios)  |

---

### 3. Reservas
Gestiona la relación entre los usuarios y los productos alquilados.

| Campo            | Tipo     | Restricciones               |
|-------------------|----------|-----------------------------|
| **id**           | Long     | PRIMARY KEY                 |
| **fecha_inicio** | Date     | NULLABLE                    |
| **fecha_fin**    | Date     | NULLABLE                    |
| **usuario_id**   | int      | FOREIGN KEY (Usuarios)      |
| **producto_id**  | int      | FOREIGN KEY (Productos)     |

## Endpoints

### **1. Usuarios**

| **Endpoint**              | **Descripción**                                              |
|---------------------------|--------------------------------------------------------------|
| **POST /usuarios/**       | Crear un nuevo usuario en la plataforma (Registro). [Admin] & [Usuario(solo el suyo)] |
| **POST /login**           | Iniciar sesión en la plataforma. [Público]                  |
| **GET /usuarios/**        | Obtener la información de todos los usuarios. [Admin]       |
| **GET /usuarios/{id}**    | Obtener la información de un usuario específico por su `id`. [Admin] & [Usuario(solo el suyo)] |
| **PUT /usuarios/{id}**    | Actualizar la información de un usuario por su `id`. [Admin] & [Usuario(solo el suyo)] |
| **DELETE /usuarios/{id}** | Eliminar un usuario de la plataforma por su `id`. [Admin] & [Usuario(solo el suyo)] |




---

### **2. Productos**

| **Endpoint**               | **Descripción**                                              |
|----------------------------|--------------------------------------------------------------|
| **POST /productos/**       | Crear un nuevo producto disponible para alquiler. [Admin] & [Usuario(solo los suyos)]              |
| **GET /productos/**        | Obtener la lista de todos los productos disponibles. [Público]          |
| **GET /productos/{id}**    | Obtener detalles de un producto específico por su `id`. [Público]       |
| **PUT /productos/{id}**    | Actualizar información de un producto por su `id`. [Admin] & [Usuario(solo los suyos)]           |
| **DELETE /productos/{id}** | Eliminar un producto de la plataforma por su `id`. [Admin] & [Usuario(solo los suyos)]           |

---

### **3. Reservas**

| **Endpoint**                  | **Descripción**                                              |
|-------------------------------|--------------------------------------------------------------|
| **POST /reservas/**           | Crear una nueva reserva para un producto. [Admin] & [Usuario(solo las suyas)]                    |
| **GET /reservas/{usuarioId}** | Consultar las reservas activas de un usuario específico por su `usuarioId`. [Admin] & [Usuario(solo las suyas)]   |
| **PUT /reservas/{id}**        | Modificar las fechas de una reserva existente. [Admin] & [Usuario(solo las suyas)]                |
| **DELETE /reservas/{id}**     | Cancelar una reserva existente. [Admin] & [Usuario(solo las suyas)]                               |

---

## **Lógica de Negocio**

### **1. Gestión de Usuarios**
- **Registro y autenticación**:  
  Los usuarios se registran proporcionando su nombre, email y contraseña. La contraseña se almacena cifrada. Los usuarios inician sesión con su email y contraseña para obtener un token de autenticación.

- **Roles**:  
  Los usuarios tienen roles definidos como "usuario" o "admin".
    - **Usuarios**: Publican productos, realizan reservas y gestionan sus datos personales.
    - **Administradores**: Supervisan y gestionan usuarios, productos y reservas en toda la plataforma.

- **Verificación de usuarios**:  
  Se implementa un sistema de validación para asegurar que los datos de los usuarios sean únicos y válidos.

---

### **2. Gestión de Productos**
- **Publicación y edición**:  
  Los usuarios pueden publicar productos para alquilar proporcionando nombre, descripción, precio, y fechas de disponibilidad. Solo un administrador o el propietario puede editar o eliminar su producto.

- **Visualización**:  
  Los usuarios pueden explorar los productos disponibles utilizando filtros como categoría, precio, y fechas de disponibilidad.

- **Restricción de duplicados**:  
  No se permite que un usuario publique dos productos idénticos (mismo nombre y descripción) dentro de un intervalo corto de tiempo.

---
### **3. Gestión de Reservas**
- **Creación de reservas**:  
  Los usuarios pueden reservar un producto especificando una fecha de inicio y fin. La lógica valida que:
    - El producto no esté reservado en el rango de fechas solicitado. Si ya existe una reserva para esas fechas, la solicitud será rechazada con un error `409 Conflict`.
    - El usuario no sea el propietario del producto reservado(prohibido auto-reservar).

- **Cancelación de reservas**:  
  Las reservas pueden cancelarse siempre que la fecha de inicio no haya pasado. Si ya está activa, la cancelación no será permitida.

- **Disponibilidad**:  
  La lógica verifica las fechas de disponibilidad del producto para asegurar que la reserva solicitada esté dentro de las fechas permitidas por el propietario.

---

## Excepciones y Códigos de Estado
### Excepciones
- **400 Bad Request**: Cuando la solicitud tiene datos incorrectos o falta información.
- **401 Unauthorized**: Cuando falta autenticación o el token es inválido.
- **403 Forbidden**: Cuando el acceso al recurso está prohibido por falta de permisos.
- **404 Not Found**: Cuando un recurso no se encuentra (por ejemplo, usuario, producto, o reserva).
- **409 Conflict**: Cuando se intenta crear un recurso con datos duplicados.
- **500 Internal Server Error**: Cuando ocurre un error inesperado en el servidor.
### Codigos de Estado Restantes
- **200 OK**: Cuando la solicitud se ha procesado correctamente y la respuesta contiene los datos solicitados.
- **201 Created**: Cuando se crea un nuevo recurso exitosamente.
- **204 No Content**: Cuando se elimina un recurso correctamente, pero no se devuelve ningún contenido.









