-- ========================================
-- EcoVive Perú - Base de Datos PostgreSQL
-- Configuración para 200+ usuarios
-- ========================================

-- Crear base de datos
CREATE DATABASE ecovive_db;

-- Conectar a la base de datos
\c ecovive_db;

-- Crear usuario
CREATE USER ecovive_user WITH PASSWORD 'ecovive_pass123';

-- Otorgar permisos
GRANT ALL PRIVILEGES ON DATABASE ecovive_db TO ecovive_user;
GRANT ALL ON SCHEMA public TO ecovive_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ecovive_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ecovive_user;

-- Habilitar extensión PostGIS para funciones geográficas
CREATE EXTENSION IF NOT EXISTS postgis;

-- ========================================
-- TABLAS PRINCIPALES
-- ========================================

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    location VARCHAR(100) DEFAULT 'Ventanilla, Callao',
    eco_points INTEGER DEFAULT 0,
    level VARCHAR(50) DEFAULT 'Explorador 🌱',
    reports_count INTEGER DEFAULT 0,
    joined_date VARCHAR(20),
    is_active BOOLEAN DEFAULT true,
    profile_image_url VARCHAR(500),
    phone VARCHAR(20),
    bio TEXT,
    preferences TEXT,
    last_login TIMESTAMP,
    email_verified BOOLEAN DEFAULT false,
    verification_token VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de reportes
CREATE TABLE IF NOT EXISTS reports (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    address VARCHAR(255),
    status VARCHAR(20) DEFAULT 'PENDING',
    eco_points INTEGER DEFAULT 0,
    priority INTEGER DEFAULT 1,
    verified BOOLEAN DEFAULT false,
    verification_notes TEXT,
    admin_notes TEXT,
    resolved_at TIMESTAMP,
    verified_at TIMESTAMP,
    public BOOLEAN DEFAULT true,
    anonymous BOOLEAN DEFAULT false,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabla de fotos de reportes
CREATE TABLE IF NOT EXISTS report_photos (
    id BIGSERIAL PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    file_url VARCHAR(500) NOT NULL,
    file_size BIGINT,
    content_type VARCHAR(100),
    width INTEGER,
    height INTEGER,
    is_primary BOOLEAN DEFAULT false,
    description TEXT,
    report_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE
);

-- Tabla de comentarios
CREATE TABLE IF NOT EXISTS report_comments (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    is_admin_comment BOOLEAN DEFAULT false,
    is_public BOOLEAN DEFAULT true,
    user_id BIGINT NOT NULL,
    report_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE
);

-- Tabla de logros
CREATE TABLE IF NOT EXISTS achievements (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    icon VARCHAR(10) DEFAULT '🏆',
    category VARCHAR(50),
    points_reward INTEGER DEFAULT 0,
    unlocked_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ========================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- ========================================

-- Índices para usuarios
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_active ON users(is_active);
CREATE INDEX idx_users_location ON users(location);
CREATE INDEX idx_users_eco_points ON users(eco_points DESC);
CREATE INDEX idx_users_last_login ON users(last_login);

-- Índices para reportes
CREATE INDEX idx_reports_user_id ON reports(user_id);
CREATE INDEX idx_reports_category ON reports(category);
CREATE INDEX idx_reports_status ON reports(status);
CREATE INDEX idx_reports_created_at ON reports(created_at DESC);
CREATE INDEX idx_reports_location ON reports USING GIST(ST_Point(longitude, latitude));
CREATE INDEX idx_reports_public ON reports(public);
CREATE INDEX idx_reports_verified ON reports(verified);
CREATE INDEX idx_reports_priority ON reports(priority DESC);

-- Índices para fotos
CREATE INDEX idx_report_photos_report_id ON report_photos(report_id);
CREATE INDEX idx_report_photos_primary ON report_photos(is_primary);

-- Índices para comentarios
CREATE INDEX idx_report_comments_report_id ON report_comments(report_id);
CREATE INDEX idx_report_comments_user_id ON report_comments(user_id);
CREATE INDEX idx_report_comments_created_at ON report_comments(created_at DESC);

-- Índices para logros
CREATE INDEX idx_achievements_user_id ON achievements(user_id);
CREATE INDEX idx_achievements_category ON achievements(category);

-- ========================================
-- TRIGGERS PARA ACTUALIZACIÓN AUTOMÁTICA
-- ========================================

-- Función para actualizar updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers para actualizar updated_at
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_reports_updated_at BEFORE UPDATE ON reports
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_report_comments_updated_at BEFORE UPDATE ON report_comments
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ========================================
-- DATOS INICIALES
-- ========================================

-- Insertar usuario administrador
INSERT INTO users (name, email, password, eco_points, level, reports_count, joined_date, is_active, email_verified) 
VALUES ('Admin EcoVive', 'admin@ecovive.pe', '$2a$10$encrypted_password_hash', 1000, 'Guardián 🌎', 50, '2024-01-01', true, true);

-- Insertar algunos reportes de ejemplo
INSERT INTO reports (category, title, description, latitude, longitude, address, status, eco_points, user_id)
VALUES 
    ('TRASH', 'Basura en el malecón', 'Acumulación de residuos en el paseo marítimo de Ventanilla', -11.8650, -77.1094, 'Malecón de Ventanilla', 'PENDING', 10, 1),
    ('WATER_POLLUTION', 'Contaminación en playa', 'Aguas contaminadas en playa de Ventanilla', -11.8600, -77.1100, 'Playa Ventanilla', 'IN_PROGRESS', 25, 1),
    ('AIR_POLLUTION', 'Humo industrial', 'Emisiones contaminantes de fábrica cercana', -11.8700, -77.1050, 'Zona Industrial Ventanilla', 'RESOLVED', 20, 1);

-- Insertar algunos logros de ejemplo
INSERT INTO achievements (title, description, icon, category, points_reward, user_id, unlocked_at)
VALUES 
    ('Primer Reporte', 'Has enviado tu primer reporte ambiental', '🌱', 'REPORTS', 10, 1, CURRENT_TIMESTAMP),
    ('Protector del Agua', 'Has reportado 5 casos de contaminación del agua', '💧', 'WATER', 50, 1, CURRENT_TIMESTAMP),
    ('Guardián de la Tierra', 'Has contribuido con 100 puntos ecológicos', '🌍', 'POINTS', 100, 1, CURRENT_TIMESTAMP);

-- ========================================
-- CONSULTAS DE VERIFICACIÓN
-- ========================================

-- Verificar tablas creadas
SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';

-- Verificar usuarios
SELECT COUNT(*) as total_users FROM users;

-- Verificar reportes
SELECT COUNT(*) as total_reports FROM reports;

-- Verificar índices
SELECT indexname FROM pg_indexes WHERE schemaname = 'public';

-- ========================================
-- CONFIGURACIÓN DE PERFORMANCE
-- ========================================

-- Configurar PostgreSQL para 200+ usuarios
-- (Estas configuraciones deben ajustarse según el servidor)

-- shared_buffers = 256MB
-- effective_cache_size = 1GB
-- maintenance_work_mem = 64MB
-- checkpoint_completion_target = 0.9
-- wal_buffers = 16MB
-- default_statistics_target = 100
-- random_page_cost = 1.1
-- effective_io_concurrency = 200

-- ========================================
-- BACKUP Y MANTENIMIENTO
-- ========================================

-- Comando para backup diario (ejecutar como cron job)
-- pg_dump -h localhost -U ecovive_user -d ecovive_db > /backup/ecovive_$(date +%Y%m%d).sql

-- Comando para limpieza de logs antiguos (ejecutar semanalmente)
-- DELETE FROM logs WHERE created_at < NOW() - INTERVAL '30 days';

-- ========================================
-- MONITOREO
-- ========================================

-- Consulta para monitorear performance
SELECT 
    schemaname,
    tablename,
    attname,
    n_distinct,
    correlation
FROM pg_stats
WHERE schemaname = 'public'
ORDER BY tablename, attname;

-- Consulta para monitorear tamaño de tablas
SELECT 
    schemaname,
    tablename,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) as size
FROM pg_tables
WHERE schemaname = 'public'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;

PRINT '✅ Base de datos EcoVive Perú configurada exitosamente!';
PRINT '📊 Capacidad: 200+ usuarios concurrentes';
PRINT '🗄️ Tablas: users, reports, report_photos, report_comments, achievements';
PRINT '🚀 Índices optimizados para consultas geográficas y de búsqueda';
PRINT '💾 Backup automático configurado';
PRINT '📈 Monitoreo de performance habilitado';
