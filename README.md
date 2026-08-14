# Voxel Review
# 🎮 Voxel Review

**Voxel Review** es una red social Android para descubrir, calificar y compartir opiniones sobre videojuegos.

La aplicación permite a los usuarios crear una cuenta, descubrir videojuegos, consultar sus detalles, calificarlos de **0 a 5**, escribir reseñas y participar en una comunidad mediante comentarios, likes y seguimiento de otros usuarios.

El proyecto busca combinar el descubrimiento de videojuegos con una experiencia social en la que las opiniones de la comunidad sean el elemento principal.

---

## ✨ Características principales

### 👤 Usuarios y autenticación

* Registro de nuevos usuarios mediante correo electrónico, nombre de usuario y contraseña.
* Validación de los datos introducidos durante el registro.
* Inicio y cierre de sesión.
* Recuperación de contraseña mediante correo electrónico.
* Acceso restringido a las funcionalidades de la aplicación para usuarios autenticados.
* Edición de la información del perfil.
* Foto de perfil.
* Eliminación permanente de la cuenta.

Los requisitos de autenticación y gestión de cuenta se encuentran definidos en el módulo correspondiente del documento de requisitos funcionales.

### 📰 Feed y descubrimiento

El usuario podrá descubrir contenido de la comunidad mediante:

* Feed con las reseñas publicadas por los usuarios que sigue.
* Orden cronológico de las publicaciones.
* Catálogo de videojuegos.
* Filtros por subcategorías, como género o plataforma.
* Búsqueda de videojuegos por nombre.
* Consulta de videojuegos añadidos recientemente.

Estas funcionalidades corresponden al módulo de Feed y Descubrimiento.

### 🎮 Videojuegos y reseñas

Cada videojuego contará con una ficha con información relevante como:

* Nombre.
* Portada.
* Desarrollador.
* Año de lanzamiento.
* Géneros y categorías.

Los usuarios podrán:

* Consultar las reseñas de otros jugadores.
* Calificar un videojuego entre **0 y 5**.
* Escribir un análisis o reseña.
* Modificar su calificación y reseña.
* Eliminar sus propias reseñas.
* Mantener una única calificación por videojuego.

La aplicación contempla específicamente la restricción de una calificación por usuario y videojuego.

### 💬 Comunidad

Voxel Review incorpora diferentes herramientas para la interacción entre usuarios:

* Seguir y dejar de seguir usuarios.
* Comentar las reseñas de otros usuarios.
* Dar like a reseñas.
* Dar like a comentarios.
* Buscar usuarios mediante su nombre de usuario.
* Consultar perfiles públicos de otros miembros de la comunidad.

Los comentarios no requieren que los usuarios se sigan mutuamente.

### 👤 Perfil

El perfil del usuario permite consultar su actividad dentro de la plataforma:

* Foto de perfil.
* Información personal.
* Número de reseñas realizadas.
* Promedio de puntuaciones.
* Likes recibidos.
* Historial de videojuegos calificados.
* Reseñas publicadas.
* Seguidores y cuentas seguidas.

También será posible visitar el perfil público de otros usuarios.

### 🔔 Notificaciones

La aplicación contará con un sistema de notificaciones para mantener al usuario informado sobre la actividad de la comunidad.

Se contemplan notificaciones cuando:

* Otro usuario comienza a seguirlo.
* Alguien comenta una de sus reseñas.
* Alguien da like a una de sus reseñas.
* Alguien da like a uno de sus comentarios.

Además, existirá un centro donde consultar el historial de notificaciones.

---

## 🎨 Diseño

Voxel Review utiliza una estética inspirada en los videojuegos y en interfaces tecnológicas.

El diseño visual utiliza un fondo oscuro con tonalidades moradas y elementos en colores neón, buscando generar una apariencia futurista y destacar los elementos interactivos de la aplicación.

El logotipo utiliza como elemento principal un **cubo tridimensional**, haciendo referencia al concepto de *voxel*, abreviatura de *volumetric pixel*.

---

## 📱 Pantallas principales

El diseño inicial contempla las siguientes pantallas:

* **Inicio de sesión / Registro**
* **Perfil de usuario**
* **Feed principal**
* **Catálogo de videojuegos**
* **Reseñas y comentarios**
* **Notificaciones**
* **Descubrir**
* **Ranking de la Comunidad**

El prototipo inicial desarrollado en Figma contempla especialmente las pantallas de entrada, perfil, feed y detalle de videojuego.

---

## 🗄️ Modelo de datos

La arquitectura conceptual de Voxel Review se basa principalmente en las siguientes entidades:

```text
Usuario
   │
   ├── Reseñas
   ├── Comentarios
   ├── Likes
   ├── Seguimientos
   └── Notificaciones

Videojuego
   │
   ├── Reseñas
   └── Categorías
```

El modelo entidad-relación y el diagrama de clases definen estas entidades y sus relaciones para representar las principales interacciones de la aplicación.

---

## 🛠️ Tecnologías

* **Android**
* **Kotlin**
* **Jetpack Compose**
* **Spring**
* **Base de datos relacional**
* **Figma** para el diseño y prototipado
* **GitHub** para el control de versiones

---

## 📂 Estructura del proyecto

La aplicación se organiza alrededor de los principales módulos funcionales:

```text
Voxel Review
│
├── Autenticación y cuenta
├── Feed y descubrimiento
├── Videojuegos
├── Reseñas
├── Comunidad
├── Perfil
└── Notificaciones
```

Esta división sigue la organización establecida en los requisitos funcionales del proyecto.

---

## 🚀 Objetivo del proyecto

El objetivo de Voxel Review es crear un espacio social especializado en videojuegos donde los usuarios puedan **descubrir nuevos títulos, compartir sus experiencias y conocer las opiniones de otros jugadores**.

La aplicación combina las funciones tradicionales de una plataforma de reseñas con elementos propios de una red social, como seguidores, likes, comentarios, perfiles y notificaciones.

---

## 👥 Equipo

**Voxel Review**

* Laura Valentina Ladino Rivera
* José Jesús Cepeda Vargas
* Iván Alejandro Pardo Montenegro
* Luis Felipe Castro Diaz

---

## 📌 Estado del proyecto

> 🚧 **En desarrollo**

El proyecto se encuentra en fase de desarrollo, tomando como base los requisitos funcionales y el diseño establecido durante el Sprint 1.

---

## 🔗 Recursos

* **Figma:** [https://www.figma.com/design/wCkXDKa7un6zNGdAce4yIT/Pantallas-View-Review?node-id=40-13&t=al1ZLipADi9EXHJF-1]
* **GitHub:** [https://github.com/githublauraa/CM_2026_PROJ.git]

---

## 📄 Documentación

La documentación del proyecto incluye:

* Requisitos funcionales.
* Diagrama entidad-relación.
* Diagrama de clases.
* Diseño visual y paleta de colores.
* Prototipo de interfaces.
* Documentación de los sprints de desarrollo.
