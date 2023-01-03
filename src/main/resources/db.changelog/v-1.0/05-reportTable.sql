create table if not exists Report
(
    id           bigint generated always as identity primary key,
    diet         varchar(256) not null,
    report_about_feelings text         not null,
    report_about_habits   text         not null,
    photo_dog            bytea        not null,
    constraint fk_dog_id foreign key (id) references dog (id)
);