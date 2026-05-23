SISTEMA DE GESTIÓN DE RUTINAS DE ENTRENAMIENTO PERSONALIZADAS
Paradigmas de Programación — Tarea 2

================================================================
INSTRUCCIONES DE EJECUCIÓN
================================================================

PROYECTO NETBEANS
  - Abrir directamente esta carpeta raíz (tarea2), que contiene
    build.xml, nbproject/ y src/.
  - No se necesita instalar Ant por separado para usar el proyecto
    desde NetBeans.
  - En NetBeans: Archivo > Abrir proyecto > seleccionar esta carpeta.

REQUISITOS
  - Java JDK 17 o superior.
  - MySQL Server 8.x corriendo en localhost:3306.
  - JARs incluidos en la carpeta lib/:
      mysql-connector-j-9.7.0.jar  (driver JDBC de MySQL)
      dotenv-java-3.0.2.jar        (lectura del archivo .env)

PREPARACIÓN DE LA BASE DE DATOS
  1. Iniciar MySQL en localhost:3306 con usuario/clave que corresponda.
  2. Configurar las credenciales en el archivo .env de la raíz del
     proyecto (usar .env.example como plantilla):

         FITNESSPRO_DB_URL=jdbc:mysql://localhost:3306/fitnesspro
         FITNESSPRO_DB_USER=root
         FITNESSPRO_DB_PASSWORD=tu_clave

  3. Ejecutar el script ejercicios.sql para crear la base de datos
     "fitnesspro", la tabla "ejercicios" y la carga inicial de datos:

         mysql -u root < ejercicios.sql

COMPILACIÓN
  Desde la raíz del proyecto (TAREA-2):

      javac -cp "lib/*" -d build/classes src/AppRutinas.java src/backend/modelo/*.java src/backend/excepciones/*.java src/backend/datos/*.java src/frontend/*.java

EJECUCIÓN
      java -cp "build/classes:lib/*" AppRutinas

  En NetBeans basta con abrir el proyecto y usar Run Project.

================================================================
FORMATO DEL ARCHIVO DE EJERCICIOS
================================================================

Los ejercicios se almacenan en la tabla "ejercicios" de la base de
datos MySQL "fitnesspro". El esquema es:

  codigo            VARCHAR(10)  PK    Identificador único.
  nombre            VARCHAR(100) NN    Nombre del ejercicio.
  tipo              ENUM         NN    CARDIOVASCULAR | FUERZA
  nivel             ENUM         NN    BASICO | INTERMEDIO |
                                       AVANZADO | ALTO_RENDIMIENTO
  tiempo_estimado   INT          NN    Duración en minutos.
  descripcion       TEXT               Descripción libre.
  pulso_recomendado INT                Pulso en bpm (solo cardio).
  peso              INT                Peso en kg (solo fuerza).
  semana_ultimo_uso INT DEFAULT 0      Semana en que se usó por
                                       última vez (0 = nunca).

La base incluye además las tablas "clientes", "rutinas" y
"rutina_ejercicios", que guardan los clientes y las rutinas que
se les generan semana a semana.

================================================================
PANTALLAS
================================================================

La aplicación abre en MenuPrincipal, con acceso a dos secciones:

  Ejercicios (VistaEjercicios)
    - Listado en tabla, crear/editar (FormEjercicio), eliminar,
      ver detalle y buscar por nivel de intensidad.

  Clientes (VistaClientes)
    - Alta de clientes (FormCliente) y selección.
    - Al seleccionar un cliente se abre VistaCliente, donde se
      generan rutinas (FormRutina: cantidad e intensidad) y se
      listan las rutinas existentes.

Todas las pantallas son formularios del diseñador de NetBeans
(pares .java + .form) ubicados en src/frontend.

================================================================
ARQUITECTURA
================================================================

  backend/modelo       - clases de dominio (Ejercicio,
                         EjercicioCardiovascular, EjercicioFuerza,
                         Cliente, Rutina, TipoEjercicio,
                         NivelIntensidad).
  backend/datos        - conexión y acceso a BD (ConexionBD,
                         EjercicioDAO, ClienteDAO, RutinaDAO).
  backend/excepciones  - ConexionFallidaException.
  frontend             - formularios Swing del diseñador.

REGLAS DEL DOMINIO
  - Tipos cerrados: CARDIOVASCULAR y FUERZA.
  - Niveles cerrados: BASICO, INTERMEDIO, AVANZADO,
    ALTO_RENDIMIENTO.
  - Al generar una rutina no se repiten los ejercicios usados
    en la rutina de la semana anterior del mismo cliente.
  - Si MySQL no responde, los DAO lanzan ConexionFallidaException.
