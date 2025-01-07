CREATE TABLE medication (
  id VARCHAR(255) NOT NULL,
   name VARCHAR(255),
   weight INTEGER,
   code VARCHAR(255),
   image oid,
   drone_id VARCHAR(255),
   CONSTRAINT pk_medication PRIMARY KEY (id)
);

ALTER TABLE medication ADD CONSTRAINT FK_MEDICATION_ON_DRONE FOREIGN KEY (drone_id) REFERENCES drone (id);