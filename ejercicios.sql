-- Base de datos FitnessPro
CREATE DATABASE IF NOT EXISTS fitnesspro;
USE fitnesspro;

CREATE TABLE IF NOT EXISTS ejercicios (
    codigo           VARCHAR(10)  PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL,
    tipo             ENUM('CARDIOVASCULAR', 'FUERZA') NOT NULL,
    nivel            ENUM('BASICO', 'INTERMEDIO', 'AVANZADO', 'ALTO_RENDIMIENTO') NOT NULL,
    tiempo_estimado  INT          NOT NULL,
    descripcion      TEXT,
    semana_ultimo_uso INT DEFAULT 0
);

INSERT INTO ejercicios VALUES
('C001', 'Trote suave',          'CARDIOVASCULAR', 'BASICO',           20, 'Trote a ritmo bajo manteniendo conversación', 0),
('C002', 'Salto de cuerda',      'CARDIOVASCULAR', 'INTERMEDIO',       15, 'Saltar con cuerda a ritmo constante',         0),
('C003', 'Ciclismo de montaña',  'CARDIOVASCULAR', 'AVANZADO',         45, 'Ciclismo en terreno irregular con pendientes', 0),
('C004', 'Sprint en intervalos', 'CARDIOVASCULAR', 'ALTO_RENDIMIENTO', 30, 'Series de 100 m al 90% de velocidad máxima',  0),
('C005', 'Natación libre',       'CARDIOVASCULAR', 'INTERMEDIO',       40, 'Nado libre a ritmo moderado',                 0),
('F001', 'Sentadilla',           'FUERZA',         'BASICO',           20, '3 series de 12 repeticiones con peso corporal', 0),
('F002', 'Press de banca',       'FUERZA',         'INTERMEDIO',       25, '4 series de 10 repeticiones con barra',        0),
('F003', 'Peso muerto',          'FUERZA',         'AVANZADO',         30, '5 series de 5 repeticiones con carga alta',    0),
('F004', 'Dominadas lastradas',  'FUERZA',         'ALTO_RENDIMIENTO', 20, '4 series de 6 repeticiones con peso extra',    0),
('F005', 'Plancha abdominal',    'FUERZA',         'BASICO',           15, '3 series de 45 segundos de plancha frontal',   0);
