# Ciervus

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

- **POST /usuarios**  
  Crear un nuevo usuario en la plataforma.

- **GET /usuarios/{id}**  
  Obtener la información de un usuario específico por su `id`.

- **PUT /usuarios/{id}**  
  Actualizar la información de un usuario por su `id`.

- **DELETE /usuarios/{id}**  
  Eliminar un usuario de la plataforma por su `id`.

---

### **2. Productos**

- **POST /productos**  
  Crear un nuevo producto disponible para alquiler.

- **GET /productos**  
  Obtener la lista de todos los productos disponibles.

- **GET /productos/{id}**  
  Obtener detalles de un producto específico por su `id`.

- **PUT /productos/{id}**  
  Actualizar información de un producto por su `id`.

- **DELETE /productos/{id}**  
  Eliminar un producto de la plataforma por su `id`.

---

### **3. Reservas**

- **POST /reservas**  
  Crear una nueva reserva para un producto.

- **GET /reservas/usuario/{usuarioId}**  
  Consultar las reservas activas de un usuario específico por su `usuarioId`.

- **PUT /reservas/{id}**  
  Modificar las fechas de una reserva existente.

- **DELETE /reservas/{id}**  
  Cancelar una reserva existente.

---




