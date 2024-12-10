# Proyecto Creación de una API SEGURA - API de una plataforma de alquiler de productos entre particulares

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

| **Endpoint**                | **Descripción**                                              |
|-----------------------------|--------------------------------------------------------------|
| **POST /usuarios**           | Crear un nuevo usuario en la plataforma. [Admin] & [Usuario(solo el suyo)]                    |
| **GET /usuarios**       | Obtener la información de todos los usuarios. [Admin]   |
| **GET /usuarios/{id}**       | Obtener la información de un usuario específico por su `id`. [Admin] & [Usuario(solo el suyo)]   |
| **PUT /usuarios/{id}**       | Actualizar la información de un usuario por su `id`. [Admin] & [Usuario(solo el suyo)]          |
| **DELETE /usuarios/{id}**    | Eliminar un usuario de la plataforma por su `id`. [Admin] & [Usuario(solo el suyo)]             |

---

### **2. Productos**

| **Endpoint**                | **Descripción**                                              |
|-----------------------------|--------------------------------------------------------------|
| **POST /productos**          | Crear un nuevo producto disponible para alquiler. [Admin] & [Usuario(solo los suyos)]              |
| **GET /productos**           | Obtener la lista de todos los productos disponibles. [Admin] & [Usuario]          |
| **GET /productos/{id}**      | Obtener detalles de un producto específico por su `id`. [Admin] & [Usuario]       |
| **PUT /productos/{id}**      | Actualizar información de un producto por su `id`. [Admin] & [Usuario(solo los suyos)]           |
| **DELETE /productos/{id}**   | Eliminar un producto de la plataforma por su `id`. [Admin] & [Usuario(solo los suyos)]           |

---

### **3. Reservas**

| **Endpoint**                | **Descripción**                                              |
|-----------------------------|--------------------------------------------------------------|
| **POST /reservas**           | Crear una nueva reserva para un producto. [Admin] & [Usuario(solo las suyas)]                    |
| **GET /reservas/{usuarioId}** | Consultar las reservas activas de un usuario específico por su `usuarioId`. [Admin] & [Usuario(solo las suyas)]   |
| **PUT /reservas/{id}**       | Modificar las fechas de una reserva existente. [Admin] & [Usuario(solo las suyas)]                |
| **DELETE /reservas/{id}**    | Cancelar una reserva existente. [Admin] & [Usuario(solo las suyas)]                               |

---

## Logica de negocio

### 1. Gestión de Usuarios
- Registro y autenticación: Los usuarios se registran, inician sesión y actualizan su información personal. Las contraseñas se almacenan de manera segura.
- Roles: Los usuarios tienen roles (usuario y admin). Los administradores gestionan el sistema.

### 2. Gestión de Productos
- Publicación y edición: Los usuarios pueden publicar y editar productos disponibles para alquilar, incluyendo nombre, descripción y precio.
- Visualización: Los usuarios pueden buscar y ver productos disponibles.

### 3. Gestión de Reservas
- Creación y cancelación: Los usuarios pueden reservar productos en fechas específicas, que deben ser verificadas por disponibilidad. Las reservas pueden cancelarse bajo ciertas condiciones.

## Excepciones y Códigos de Estado
### Excepciones
- **400 Bad Request**: Cuando la solicitud tiene datos incorrectos o falta información.
- **401 Unauthorized**: Cuando falta autenticación o el token es inválido.
- **403 Forbidden**: Cuando el acceso al recurso está prohibido por falta de permisos.
- **404 Not Found**: Cuando un recurso no se encuentra (por ejemplo, usuario, producto, o reserva).
- **405 Method Not Allowed**: Cuando se utiliza un método HTTP incorrecto.
- **409 Conflict**: Cuando se intenta crear un recurso con datos duplicados.
- **422 Unprocessable Entity**: Cuando los datos enviados no cumplen con las reglas de validación.
- **500 Internal Server Error**: Cuando ocurre un error inesperado en el servidor.
### Codigos de Estado Restantes
- **200 OK**: Cuando la solicitud se ha procesado correctamente y la respuesta contiene los datos solicitados.
- **201 Created**: Cuando se crea un nuevo recurso exitosamente (por ejemplo, un usuario o producto se ha creado correctamente).
- **204 No Content**: Cuando se elimina un recurso correctamente, pero no se devuelve ningún contenido (por ejemplo, una eliminación de producto exitosa).









