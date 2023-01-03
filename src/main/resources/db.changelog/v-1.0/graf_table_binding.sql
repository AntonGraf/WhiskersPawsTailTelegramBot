-- Связывает таблицы shelter и dog
ALTER TABLE dog ADD COLUMN shelter_id bigint;

ALTER TABLE dog
    ADD CONSTRAINT fk_dogs_shelters FOREIGN KEY (shelter_id) REFERENCES shelter (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- Связывает таблицы shelter и volunteer
ALTER TABLE volunteer ADD COLUMN shelter_id bigint;

ALTER TABLE volunteer
    ADD CONSTRAINT fk_volunteers_shelters FOREIGN KEY (shelter_id) REFERENCES shelter (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

-- Связывает таблицы dog и adoptive_parent
ALTER TABLE dog ADD COLUMN adoptive_parent_id bigint;

ALTER TABLE dog
    ADD CONSTRAINT fk_dogs_adoptive_parents FOREIGN KEY (adoptive_parent_id) REFERENCES adoptive_parent (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;