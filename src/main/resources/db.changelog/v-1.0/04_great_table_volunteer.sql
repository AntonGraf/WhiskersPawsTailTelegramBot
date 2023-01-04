CREATE TABLE volunteer_info
(
    id  bigserial ,
    info_volunteer TEXT

)
CREATE TABLE volunteer
(
    id  bigserial PRIMARY KEY ,
    full_name VARCHAR(256) ,
    phone VARCHAR(30),
    schedule TEXT

)