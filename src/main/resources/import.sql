DROP TABLE IF EXISTS provider_supportedchannels;

CREATE TABLE IF NOT EXISTS sla (
  id SERIAL PRIMARY KEY,
  delivery_time_ms INT NOT NULL,
  uptime_percent DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS provider (
  id SERIAL PRIMARY KEY,
  partner_id VARCHAR(50) NOT NULL,
  name VARCHAR(100) NOT NULL,
  contact_info VARCHAR(255) NOT NULL,
  sla_id INT NOT NULL REFERENCES sla(id),
  supported_channels TEXT[] -- array column
);
