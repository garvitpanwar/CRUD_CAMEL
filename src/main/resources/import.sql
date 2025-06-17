--DROP TABLE IF EXISTS provider_supportedchannels;
--
--CREATE TABLE IF NOT EXISTS sla (
--  id SERIAL PRIMARY KEY,
--  delivery_time_ms INT NOT NULL,
--  uptime_percent DOUBLE PRECISION NOT NULL
--);
--
--CREATE TABLE IF NOT EXISTS provider (
--  id SERIAL PRIMARY KEY,
--  partner_id VARCHAR(50) NOT NULL,
--  name VARCHAR(100) NOT NULL,
--  contact_info VARCHAR(255) NOT NULL,
--  sla_id INT NOT NULL REFERENCES sla(id),
--  supported_channels TEXT[] -- array column
--);


-- Clear existing tables if they exist
DROP TABLE IF EXISTS provider;
DROP TABLE IF EXISTS sla;

-- Create SLA table first (since provider references it)
CREATE TABLE sla (
  id SERIAL PRIMARY KEY,
  delivery_time_ms BIGINT NOT NULL,  -- Changed INT to BIGINT to match Java 'long'
  uptime_percent DOUBLE PRECISION NOT NULL
);

-- Create Provider table with proper constraints
CREATE TABLE provider (
  id SERIAL PRIMARY KEY,
  partner_id VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  contact_info VARCHAR(255),          -- Removed NOT NULL to match entity
  supported_channels TEXT[],          -- Array column for PostgreSQL
  sla_id INT REFERENCES sla(id)       -- Foreign key reference
);


-- import.sql mein ye changes karo
ALTER TABLE provider ADD CONSTRAINT uk_provider_partner_id UNIQUE (partner_id);

-- Sample data insertion
INSERT INTO sla (delivery_time_ms, uptime_percent) VALUES (500, 99.9);
INSERT INTO provider (partner_id, name, contact_info, supported_channels, sla_id)
VALUES ('p001', 'Gupshup', 'support@gupshup.io', ARRAY['SMS','WhatsApp'],
        (SELECT id FROM sla WHERE delivery_time_ms = 500 LIMIT 1));