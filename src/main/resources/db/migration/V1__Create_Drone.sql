CREATE TABLE drone (
   id VARCHAR(255) NOT NULL,
   serial_number VARCHAR(255),
   model VARCHAR(255),
   weight_limit INTEGER,
   battery_percentage INTEGER,
   state VARCHAR(255),
   CONSTRAINT pk_drone PRIMARY KEY (id)
);

ALTER TABLE drone ADD CONSTRAINT uc_drone_serial_number UNIQUE (serial_number);