CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


create table user_service.users
(
    id              UUID DEFAULT gen_random_uuid() NOT NULL,
    activation_code CHARACTER VARYING(255),
    active          BOOLEAN                         NOT NULL,
    email           CHARACTER VARYING(255)          NOT NULL UNIQUE,
    password        CHARACTER VARYING(255)          NOT NULL,
    username        CHARACTER VARYING(255)          NOT NULL UNIQUE,
    created_at      TIMESTAMP                       NOT NULL,
    updated_at      TIMESTAMP                       NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

create table user_service.passports
(
    id         UUID DEFAULT gen_random_uuid() NOT NULL,
    address    CHARACTER VARYING(500)          NOT NULL,
    birthday   DATE                            NOT NULL,
    name       CHARACTER VARYING(255)          NOT NULL,
    patronymic CHARACTER VARYING(255)          NOT NULL,
    surname    CHARACTER VARYING(255)          NOT NULL,
    created_at TIMESTAMP                       NOT NULL,
    updated_at TIMESTAMP                       NOT NULL,
    user_id    UUID UNIQUE,
    CONSTRAINT passport_pk PRIMARY KEY (id),
    CONSTRAINT passport_user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);

create table user_service.user_role
(
    user_id UUID         NOT NULL,
    roles   varchar(255) NOT NULL,
    CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

