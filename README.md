# Prismify User API

API que posee funcionalidades de usuario

### Descripción

Proyecto que permite la gestión de usuarios y autentificación en la plataforma de Prismify. Entrega JWT tokens y validación de estos, permite registrar usuarios con un admin, ingresar con username o email, también permite el cambio de contraseña validando con correo.

### Features

* Usuarios pueden ingresar a plataforma
* Admin puede gestionar usuarios con rol de Manager y Auditor
* Sistema de envío de correos internos

### Guía de instalación

* Clona el repositorio [aquí](https://github.com/adolfo-7z/prismify-api-user.git)
* Ejecuta git flow init para empezar a trabajar en la rama develop
* Crea un archivo .env en la carpeta raíz y agregar las variables requeridas para el proyecto.
* Ejecuta mvn clean install para empaquetar la aplicación

### API Endpoints

| HTTP Verbs | Endpoints | Action |
| --- | --- | --- |
| POST | /auth/login | Para ingresar a la plataforma con un usuario existente |
| GET | /users | Para ver todos los usuarios |
| POST | /users | Para crear un usuario |
| GET | /users/{id} | Para ver el detalle de un usuario |
| PATCH | /users/{id} | Para actualizar información de un usuario |
| DELETE | /users/{id} | Para eliminar un usuario |
| PATCH | /users/{id}/status | Para actualizar el estado activo/inactivo de un usuario |
| GET | /users/{id}/notifications | Para ver notificaciones de un usuario |
| DELETE | /users/{userId}/notifications | Para eliminar todas las notificaciones de un usuario |
| DELETE | /users/{userId}/notifications/{id} | Para eliminar una notificación de un usuario |
| POST | /users/recovery/code | Para solicitar código de recuperación de contraseña |
| POST | /users/recovery/validate | Para validar código de recuperación de contraseña |
| POST | /users/recovery/password | Para crear nueva contraseña |

### Tecnologías utilizadas

* [Spring_Framework] (https://spring.io/projects/spring-framework)
* [Spring_Boot] (https://spring.io/projects/spring-boot)
* [Spring_Security] (https://spring.io/projects/spring-security)
* [Hibernate] (https://hibernate.org/orm/)
* [Spring_Data] (https://spring.io/projects/spring-data)

### Autores

* [Adolfo_Plaza] (https://github.com/adolfo-7z)