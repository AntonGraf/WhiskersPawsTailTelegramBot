create table if not exists Report
(
    id                    bigint generated always as identity primary key,
    diet                  varchar(256) ,
    report_about_feelings text         ,
    report_about_habits   text         ,
    photo_pet             bytea        ,
    pet_id                bigint,
    constraint fk_pet_id foreign key (pet_id) references pet (id)
);