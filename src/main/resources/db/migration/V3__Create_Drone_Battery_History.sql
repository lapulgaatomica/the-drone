CREATE TABLE drone_battery_history (
  id VARCHAR(255) NOT NULL,
   current_day_time TIMESTAMP WITHOUT TIME ZONE,
   battery_percentage INTEGER,
   drone_id VARCHAR(255),
   CONSTRAINT pk_drone_battery_history PRIMARY KEY (id)
);

ALTER TABLE drone_battery_history ADD CONSTRAINT FK_DRONE_BATTERY_HISTORY_ON_DRONE FOREIGN KEY (drone_id) REFERENCES drone (id);