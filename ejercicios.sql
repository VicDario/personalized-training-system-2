CREATE DATABASE IF NOT EXISTS fitnesspro;
USE fitnesspro;

DROP TABLE IF EXISTS rutina_ejercicios;
DROP TABLE IF EXISTS rutinas;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS ejercicios;

CREATE TABLE ejercicios (
    codigo            VARCHAR(10)  PRIMARY KEY,
    nombre            VARCHAR(100) NOT NULL,
    tipo              ENUM('CARDIOVASCULAR', 'FUERZA') NOT NULL,
    nivel             ENUM('BASICO', 'INTERMEDIO', 'AVANZADO', 'ALTO_RENDIMIENTO') NOT NULL,
    tiempo_estimado   INT          NOT NULL,
    descripcion       TEXT,
    pulso_recomendado INT,
    peso              INT,
    semana_ultimo_uso INT DEFAULT 0
);

CREATE TABLE clientes (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    rut           VARCHAR(20)  NOT NULL UNIQUE,
    semana_actual INT NOT NULL DEFAULT 1
);

CREATE TABLE rutinas (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    semana     INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

CREATE TABLE rutina_ejercicios (
    rutina_id        INT NOT NULL,
    ejercicio_codigo VARCHAR(10) NOT NULL,
    FOREIGN KEY (rutina_id) REFERENCES rutinas(id) ON DELETE CASCADE,
    FOREIGN KEY (ejercicio_codigo) REFERENCES ejercicios(codigo) ON DELETE CASCADE
);

INSERT INTO ejercicios (codigo, nombre, tipo, nivel, tiempo_estimado, descripcion, pulso_recomendado, peso) VALUES
('C001', 'Trote suave',            'CARDIOVASCULAR', 'BASICO',           20, 'Carrera continua a ritmo bajo para mejorar resistencia base.',        120, NULL),
('C002', 'Ciclismo urbano',        'CARDIOVASCULAR', 'INTERMEDIO',       30, 'Pedaleo moderado enfocado en control respiratorio y constancia.',     135, NULL),
('C003', 'Sprints en pista',       'CARDIOVASCULAR', 'AVANZADO',         18, 'Intervalos cortos de alta velocidad con pausas activas.',             160, NULL),
('C004', 'HIIT cardio',            'CARDIOVASCULAR', 'ALTO_RENDIMIENTO', 25, 'Bloques intensos de trabajo cardiovascular con recuperacion breve.',  175, NULL),
('C005', 'Caminata inclinada',     'CARDIOVASCULAR', 'BASICO',           25, 'Trabajo aerobico continuo con inclinacion moderada.',                 118, NULL),
('C006', 'Remo ergometrico',       'CARDIOVASCULAR', 'INTERMEDIO',       22, 'Sesion cardiovascular de cuerpo completo con ritmo sostenido.',       140, NULL),
('C007', 'Escaladora',             'CARDIOVASCULAR', 'AVANZADO',         20, 'Subida continua para mejorar capacidad cardiorrespiratoria.',         155, NULL),
('C008', 'Burpees por rondas',     'CARDIOVASCULAR', 'ALTO_RENDIMIENTO', 16, 'Series exigentes de burpees con pausas cortas.',                      172, NULL),
('C009', 'Salto de cuerda',        'CARDIOVASCULAR', 'INTERMEDIO',       15, 'Coordinacion y resistencia con intervalos dinamicos.',                145, NULL),
('C010', 'Fartlek',                'CARDIOVASCULAR', 'AVANZADO',         28, 'Cambios de ritmo alternando tramos suaves e intensos.',               158, NULL),
('F001', 'Sentadillas',            'FUERZA',         'BASICO',           15, 'Trabajo de tren inferior con enfoque en tecnica y control.',          NULL, 20),
('F002', 'Press de pecho',         'FUERZA',         'INTERMEDIO',       20, 'Ejercicio de empuje para desarrollar fuerza del torso.',              NULL, 35),
('F003', 'Peso muerto',            'FUERZA',         'AVANZADO',         25, 'Levantamiento compuesto para cadena posterior y estabilidad.',        NULL, 60),
('F004', 'Thrusters',              'FUERZA',         'ALTO_RENDIMIENTO', 20, 'Movimiento explosivo combinado de sentadilla y press.',               NULL, 30),
('F005', 'Zancadas con mancuerna', 'FUERZA',         'BASICO',           18, 'Fortalecimiento unilateral para piernas y equilibrio.',               NULL, 16),
('F006', 'Remo con barra',         'FUERZA',         'INTERMEDIO',       22, 'Ejercicio de traccion para espalda y brazos.',                        NULL, 32),
('F007', 'Press militar',          'FUERZA',         'AVANZADO',         20, 'Empuje vertical para hombros y estabilidad del core.',                NULL, 28),
('F008', 'Clean and press',        'FUERZA',         'ALTO_RENDIMIENTO', 18, 'Levantamiento compuesto de alta demanda tecnica.',                    NULL, 42),
('F009', 'Hip thrust',             'FUERZA',         'INTERMEDIO',       17, 'Trabajo de gluteos y cadena posterior con carga controlada.',         NULL, 40),
('F010', 'Dominadas lastradas',    'FUERZA',         'ALTO_RENDIMIENTO', 14, 'Ejercicio avanzado de traccion con carga adicional.',                 NULL, 18);
