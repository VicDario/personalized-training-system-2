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

      javac -cp "lib/*" -d build/classes src/AppRutinas.java src/backend/modelo/*.java src/backend/excepciones/*.java src/backend/notificacion/*.java src/backend/datos/*.java src/frontend/*.java

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

VALIDACIONES AL CARGAR
  - codigo, nombre, tipo y nivel son obligatorios; un registro con
    alguno de estos campos vacío provoca InformacionIncompletaException.
  - tipo debe coincidir exactamente con un valor de TipoEjercicio,
    y nivel con uno de NivelIntensidad; un valor desconocido genera
    FormatoInvalidoException.
  - Si MySQL no responde, se lanza ConexionFallidaException.
  - Cualquiera de estos fallos se publica al frontend como
    CARGA_ERROR a través del bus de notificaciones.

================================================================
ALCANCES Y SUPUESTOS
================================================================

ALCANCES
  - Los ejercicios se leen desde MySQL al iniciar la aplicación,
    en un hilo separado para no bloquear la UI de Swing.
  - El backend está modularizado en cuatro paquetes:
      backend/modelo        - clases de dominio (Ejercicio,
                              EjercicioCardiovascular, EjercicioFuerza,
                              Rutina, TipoEjercicio, NivelIntensidad).
      backend/datos         - acceso a BD (ConexionBD,
                              CargadorEjercicios).
      backend/excepciones   - excepciones propias del sistema.
      backend/notificacion  - bus de eventos (Notificador,
                              Suscriptor, EventoSistema).
  - La comunicación backend - frontend se realiza mediante el
    patrón Observer: el Notificador singleton publica eventos
    (CARGA_OK, CARGA_ERROR, RUTINA_GENERADA, RUTINA_ERROR) y
    VentanaFitnessPro se registra como Suscriptor.
  - Las notificaciones se despachan en el Event Dispatch Thread
    (SwingUtilities.invokeLater), de modo que los suscriptores
    pueden tocar componentes Swing sin riesgos de concurrencia.
  - La ventana principal muestra estadísticas calculadas en vivo
    a partir de los ejercicios cargados (total, tiempo total,
    conteos por tipo y por nivel).

SUPUESTOS
  - MySQL está disponible localmente con las credenciales
    fijadas en ConexionBD
  - El número máximo de suscriptores del bus es 10
    (MAX_SUSCRIPTORES en Notificador).
  - El conjunto de tipos de ejercicio es cerrado:
    CARDIOVASCULAR y FUERZA. Cualquier otro valor en la BD
    se rechaza como FormatoInvalidoException.
  - El conjunto de niveles también es cerrado: BASICO,
    INTERMEDIO, AVANZADO y ALTO_RENDIMIENTO.
