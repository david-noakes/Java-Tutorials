-- Script 2 - Set Up Tablse and Grant Permissions
-- START 'address_read'
DROP TABLE IF EXISTS address_read;
DROP SEQUENCE IF EXISTS address_read_id_seq;
CREATE SEQUENCE address_read_id_seq;

CREATE TABLE address_read (

	-- Input Record Metadata Section
    -- 1. Auto-generated ID
	id bigint NOT NULL DEFAULT nextval('address_read_id_seq'),	
	-- 2. Timestampt when the record is created
	record_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	-- 3. Supplied ID (if any)
	entity_key VARCHAR(100),
	-- 4. Record Processing Priority
	priority boolean DEFAULT false,		
	-- 5. Record Update Status 
	status VARCHAR(100) DEFAULT 'NONE',
	
	-- Address Section
	-- 1. Full Address (one line)
	full_address VARCHAR(250),	
	-- 2. Address Parts
	street_number VARCHAR(100),
	street_name VARCHAR(100),
	suburb VARCHAR(100),
	postcode VARCHAR(100),
	state VARCHAR(100),
    country VARCHAR(100),
	country_code CHAR(2),

	PRIMARY KEY(id)
);
ALTER SEQUENCE address_read_id_seq OWNED BY address_read.id;

GRANT SELECT, UPDATE, DELETE ON TABLE public.address_read TO geouser;
GRANT ALL ON TABLE public.address_read_id_seq TO geouser;
-- END 'address_read'


-- START 'address_write'
DROP TABLE IF EXISTS address_write;
DROP SEQUENCE IF EXISTS address_write_id_seq;

CREATE SEQUENCE address_write_id_seq;
CREATE TABLE address_write (
	-- Output Record Metadata Section
	-- 1. Auto-generated ID
	id bigint NOT NULL DEFAULT nextval('address_write_id_seq'),
	-- 2. Timestampt when the record is created
	record_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,	
	-- 3. Record Update Status (not related to Input Table 'status' column
	status VARCHAR(100) DEFAULT 'PROCESSING',

	-- Copy of Original Input Data
	-- Start of 'address_read' data  	
	input_id bigint NOT NULL,
	input_record_timestamp TIMESTAMP NOT NULL,
	input_entity_key VARCHAR(100),		
	input_priority boolean,
	input_full_address VARCHAR(250),
	input_street_number VARCHAR(100),
	input_street_name VARCHAR(100),
	input_suburb VARCHAR(100),
	input_postcode VARCHAR(100),
	input_state VARCHAR(100),
    input_country VARCHAR(100),
	input_country_code VARCHAR(100),
	-- End 'address_read' data 
	
	-- Address Section
	-- 1. Address parts
	subpremise VARCHAR(100),
	premise VARCHAR(100),
	street_number VARCHAR(100),
	route VARCHAR(100),
	sublocality_level_4 VARCHAR(100),
	sublocality_level_3 VARCHAR(100),
	sublocality_level_2 VARCHAR(100),
	sublocality_level_1 VARCHAR(100),
	locality VARCHAR(100),
	administrative_area_level_2 VARCHAR(100),
	administrative_area_level_1 VARCHAR(100),
	postal_code VARCHAR(100),
	country VARCHAR(100),
	country_code CHAR(2),	
	-- 2. Full Address (one line)
	formatted_address VARCHAR(250),	
	-- 3. Geo-coding parts
	lat NUMERIC DEFAULT NULL,
	lon NUMERIC DEFAULT NULL,
	location_type VARCHAR(100),
	types VARCHAR(100),

	PRIMARY KEY(id)
);
ALTER SEQUENCE address_write_id_seq OWNED BY address_write.id;

GRANT INSERT, SELECT ON TABLE public.address_write TO geouser;
GRANT ALL ON TABLE public.address_write_id_seq TO geouser;
-- END 'address_write'

-- Populate with Test Data
COPY address_read (full_address) FROM '/opt/local/geocode/scripts/address_data.csv' DELIMITER ',' CSV;

SELECT * FROM address_read;
