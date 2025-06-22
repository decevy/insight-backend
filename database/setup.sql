-- PostgreSQL Database Setup for Insight Backend
-- Database: duality_db
-- Based on JPA entities from com.insightapp.insight_backend.entity

-- Create database (run this as superuser)
-- CREATE DATABASE duality_db;

-- Connect to the database
-- \c duality_db;

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create users table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create insights table
CREATE TABLE insights (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content TEXT NOT NULL,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    is_public BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create concepts table
CREATE TABLE concepts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    canonical_name VARCHAR(100) NOT NULL,
    synonyms TEXT[],
    related_terms TEXT[],
    antonyms TEXT[],
    languages VARCHAR(10)[],
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create insight_concepts junction table (many-to-many relationship)
CREATE TABLE insight_concepts (
    insight_id UUID NOT NULL REFERENCES insights(id) ON DELETE CASCADE,
    concept_id UUID NOT NULL REFERENCES concepts(id) ON DELETE CASCADE,
    weight DECIMAL(3,2) DEFAULT 0.50,
    source VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (insight_id, concept_id)
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_insights_user_id ON insights(user_id);
CREATE INDEX idx_insights_created_at ON insights(created_at);
CREATE INDEX idx_insights_is_public ON insights(is_public);
CREATE INDEX idx_concepts_canonical_name ON concepts(canonical_name);
CREATE INDEX idx_insight_concepts_insight_id ON insight_concepts(insight_id);
CREATE INDEX idx_insight_concepts_concept_id ON insight_concepts(concept_id);

-- Create updated_at trigger function
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for updated_at columns
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_insights_updated_at BEFORE UPDATE ON insights
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_concepts_updated_at BEFORE UPDATE ON concepts
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Insert sample data (optional)
-- Sample user
INSERT INTO users (username, email) VALUES 
('testuser', 'test@example.com');

-- Sample concepts
INSERT INTO concepts (canonical_name, synonyms, related_terms, description) VALUES 
('Machine Learning', ARRAY['ML', 'AI Learning'], ARRAY['Artificial Intelligence', 'Data Science'], 'A subset of artificial intelligence focused on algorithms that can learn from data'),
('Data Science', ARRAY['DS'], ARRAY['Analytics', 'Statistics'], 'An interdisciplinary field that uses scientific methods to extract knowledge from data'),
('Artificial Intelligence', ARRAY['AI'], ARRAY['Machine Learning', 'Deep Learning'], 'The simulation of human intelligence in machines');

-- Sample insight
INSERT INTO insights (content, user_id, is_public) 
SELECT 'Machine learning is transforming how we approach data analysis', id, true 
FROM users WHERE username = 'testuser';

-- Sample insight-concept associations
INSERT INTO insight_concepts (insight_id, concept_id, weight, source)
SELECT 
    i.id as insight_id,
    c.id as concept_id,
    0.75 as weight,
    'user' as source
FROM insights i, concepts c, users u
WHERE i.user_id = u.id 
    AND u.username = 'testuser'
    AND c.canonical_name = 'Machine Learning';

-- Grant permissions (adjust as needed)
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres; 