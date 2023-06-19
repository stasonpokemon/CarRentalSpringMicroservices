CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table car_service.cars
(
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    damage_status CHARACTER VARYING(255),
    car_status    INT CHECK ( car_status >= 0 AND car_status <= 3) DEFAULT 0 NOT NULL,
    img_link      CHARACTER VARYING(1000)                                    NOT NULL,
    model         CHARACTER VARYING(255)                                     NOT NULL,
    price_per_day DOUBLE PRECISION CHECK ( price_per_day >= 0 )              NOT NULL,
    producer      CHARACTER VARYING(255)                                     NOT NULL,
    release_date  DATE                                                       NOT NULL,
    created_at    TIMESTAMP                                                  NOT NULL,
    updated_at    TIMESTAMP                                                  NOT NULL,
    CONSTRAINT car_pk PRIMARY KEY (id)
);
