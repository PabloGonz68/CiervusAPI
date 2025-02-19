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

| **Endpoint**                | **Descripción**                                              |
|-----------------------------|--------------------------------------------------------------|
| **POST /usuarios/register** | Crear un nuevo usuario en la plataforma (Registro). [Público] |
| **POST /usuarios/login**    | Iniciar sesión en la plataforma. [Público]                  |
| **GET /usuarios/**          | Obtener la información de todos los usuarios. [Admin]       |
| **GET /usuarios/{id}**      | Obtener la información de un usuario específico por su `id`. [Admin] & [Usuario(solo el suyo)] |
| **PUT /usuarios/{id}**      | Actualizar la información de un usuario por su `id`. [Admin] & [Usuario(solo el suyo)] |
| **DELETE /usuarios/{id}**   | Eliminar un usuario de la plataforma por su `id`. [Admin] & [Usuario(solo el suyo)] |




---

### **2. Productos**

| **Endpoint**               | **Descripción**                                              |
|----------------------------|--------------------------------------------------------------|
| **POST /productos/**       | Crear un nuevo producto disponible para alquiler. [Público]             |
| **GET /productos/**        | Obtener la lista de todos los productos disponibles. [Público]          |
| **GET /productos/{id}**    | Obtener detalles de un producto específico por su `id`. [Público]       |
| **PUT /productos/{id}**    | Actualizar información de un producto por su `id`. [Admin] & [Usuario(solo los suyos)]           |
| **DELETE /productos/{id}** | Eliminar un producto de la plataforma por su `id`. [Admin] & [Usuario(solo los suyos)]           |

---

### **3. Reservas**

| **Endpoint**              | **Descripción**                                                                                |
|---------------------------|------------------------------------------------------------------------------------------------|
| **POST /reservas/**       | Crear una nueva reserva para un producto. [Solo autenticados]                                  |
| **GET /reservas/**        | Obtener la lista de todas las reservas disponibles. . [Admin]                                  |
| **GET /reservas/{id}**    | Obtener detalles de una reserva en específico por su `id`. [Admin] & [Usuario(solo las suyas)] |
| **PUT /reservas/{id}**    | Modificar las fechas de una reserva existente. [Admin] & [Usuario(solo las suyas)]             |
| **DELETE /reservas/{id}** | Cancelar una reserva existente. [Admin] & [Usuario(solo las suyas)]                            |

---

## **Lógica de Negocio**

### **1. Gestión de Usuarios**
- **Registro de usuario**:
  - Se valida que el correo electrónico no exista previamente.
  - Las contraseñas se almacenan encriptadas.

- **Autenticación**:
  - Validación de correo electrónico y contraseña.
  - Generación de tokens para sesiones seguras

- **Roles**:  
  Los usuarios tienen roles definidos como "USER" o "ADMIN".
  - **Usuarios**: Publican productos, realizan reservas y gestionan sus datos personales.
  - **Administradores**: Supervisan y gestionan usuarios, productos y reservas en toda la plataforma.

- **Verificación de usuarios**:  
  Se implementa un sistema de validación para asegurar que los datos de los usuarios sean únicos y válidos.

- **Visualización**:  
  Los usuarios pueden ver sus propios datos personales y los administradores pueden ver la información de otros usuarios tambien.

- **Actualizacion y eliminacion de usuarios**:
  - Los usuarios pueden actualizar sus propios datos personales y eliminarse de la plataforma
  - Los administradores pueden actualizar y eliminar cualquier usuario.

---

### **2. Gestión de Productos**
- **Publicación y edición**:  
  Los usuarios pueden publicar productos para alquilar proporcionando nombre, descripción, precio, y fechas de disponibilidad. Solo un administrador o el propietario puede editar o eliminar su producto.

- **Visualización**:  
  Los usuarios pueden explorar los productos disponibles para alquiler.

- **Eliminación de productos y sus reservas**:
  Los administradores o el propietario pueden eliminar un producto y sus reservas asociadas.

- **Restricción de duplicados**:  
  No se permite que un usuario publique dos productos idénticos (mismo nombre y descripción) dentro de un intervalo corto de tiempo.

---
### **3. Gestión de Reservas**
- **Creación de reservas**:  
  Los usuarios pueden realizar una reserva proporcionando el `usuario_id` y el `producto_id`. Se valida que el usuario y el producto existan, y se asignan fechas de inicio (la fecha actual) y fin (7 días después de la fecha actual). La reserva se guarda en la base de datos.

- **Visualización de reservas**:  
  Los usuarios pueden consultar sus reservas mediante su `id` o ver todas las reservas disponibles.

- **Actualización de reservas**:  
  Los usuarios pueden actualizar una reserva proporcionando un `id` válido. Se valida que el `usuario_id` y `producto_id` existan.

- **Eliminación de reservas**:  
  Los usuarios pueden eliminar una reserva proporcionando un `id` válido. Si no existe, se lanza un error.

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









