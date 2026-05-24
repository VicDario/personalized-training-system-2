# Paradigmas de Programación — Tarea 2
# Sistema de Gestión de Rutinas de Entrenamiento Personalizadas
Este proyecto es una aplicación de escritorio desarrollada en **Java** usando **Swing** y construida con **Ant**. Está diseñada para abrirse, compilarse y ejecutarse de manera nativa utilizando el IDE **NetBeans**.

---

## Requisitos

Para ejecutar la aplicación, asegúrate de tener instalado:
1. **Java JDK 17** o superior.
2. **MySQL Server 8.x** activo en `localhost:3306`.
3. Librerías JAR externas (incluidas en la carpeta `lib/`):
   - `mysql-connector-j-9.7.0.jar` (Driver JDBC oficial de MySQL).
   - `dotenv-java-3.0.2.jar` (Soporte para lectura de archivos de configuración `.env`).

---

## Configuración de variables de entorno

1. Copia el archivo `.env.example` en la raíz del proyecto y renombralo a `.env`. Configura las credenciales correspondientes a tu servidor MySQL local:
   ```env
   FITNESSPRO_DB_URL=jdbc:mysql://localhost:3306/fitnesspro
   FITNESSPRO_DB_USER=tu_usuario
   FITNESSPRO_DB_PASSWORD=tu_contraseña
   ```

## Configuración y Población de base de datos
1. Accede a la base de datos
   ```bash
   mysql -u tu_usuario -p < ejercicios.sql
   ```
2. Importa el script `ejercicios.sql` en tu servidor de base de datos MySQL para crear la base de datos `fitnesspro`, estructurar las tablas e insertar los ejercicios por defecto.

---

## Instrucciones de Ejecución desde NetBeans
1. Inicia **NetBeans**.
2. Dirígete a **Archivo > Abrir proyecto** (*File > Open Project*).
3. Selecciona la carpeta raíz de este proyecto. NetBeans reconocerá automáticamente la configuración de Ant y los directorios de código fuente.
4. Haz clic derecho sobre el proyecto en la barra lateral y selecciona **Ejecutar** (*Run*), o presiona la tecla `F6`.