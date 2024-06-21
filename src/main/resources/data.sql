CREATE DATABASE IF NOT EXISTS spring_security_user;
USE spring_security_user;
-- Eliminar la tabla si ya existe
DROP TABLE IF EXISTS users;

-- Crear la tabla
CREATE TABLE IF NOT EXISTS users (
    id_user VARCHAR(100) NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    active TINYINT(1) NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Crear la tabla users
INSERT INTO users (id_user, username, last_name, password, email, active, creation_date, modification_date)
VALUES ('ebe4468b-b48f-4cdf-aee8-ca0af992fb74', 'emmanuel','sandoval','hashedpassword','test@example.com',  true, NOW(), NOW());

-- Crear la tabla roles
CREATE TABLE IF NOT EXISTS roles (
    id_roles VARCHAR(100) NOT NULL PRIMARY KEY,
    name_role VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
    );


INSERT INTO roles (id_roles, name_role, description) VALUES
('1', 'Administrador', 'Gestión total del sistema y seguridad.'),
('2', 'Usuario Final', 'Acceso básico para tareas diarias.'),
('3', 'Operador o Agente', 'Gestiona tareas diarias y atención al cliente.'),
('4', 'Supervisor / Gerente', 'Supervisa operaciones y aprueba cambios.'),
('5', 'Desarrollador', 'Desarrolla y mantiene el sistema.'),
('6', 'Auditor', 'Revisa conformidad y registros del sistema.'),
('7', 'Analista de Datos', 'Analiza datos y prepara informes.');


-- Crear índice en la tabla `users`
CREATE INDEX idx_users_id_user ON users (id_user);


-- Crear índices en la tabla `user_roles`
CREATE INDEX idx_user_roles_id_user ON user_roles (id_user);
CREATE INDEX idx_user_roles_id_roles ON user_roles (id_roles);



-- Verificar índices en la tabla `users`
SHOW INDEX FROM users;

-- Verificar índices en la tabla `user_roles`
SHOW INDEX FROM user_roles;





-- Insert en la tabla user_roles
INSERT INTO user_roles (id_user, id_roles, name_role) VALUES ('ebe4468b-b48f-4cdf-aee8-ca0af992fb74', 1, 'Administrador');


SELECT u.id_user, u.username, r.name_role
FROM users u
         JOIN user_roles ur ON u.id_user = ur.id_user
         JOIN roles r ON ur.id_roles = r.id_roles
WHERE u.id_user = '4f5341f4-4d57-4736-9d03-561886081b2a';

SELECT
    u.id_user,
    u.username,
    u.last_name,
    u.password,
    u.email,
    u.active,
    u.creation_date,
    u.modification_date,
    ur.id_roles,
    ur.name_role
FROM
    users u
        INNER JOIN
    user_roles ur ON u.id_user = ur.id_user;



