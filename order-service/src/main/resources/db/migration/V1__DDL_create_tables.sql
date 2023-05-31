CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


create table order_service.refunds
(
    id                 UUID DEFAULT gen_random_uuid() NOT NULL,
    damage_description CHARACTER VARYING(500),
    damage_status      BOOLEAN                         NOT NULL,
    price              DOUBLE PRECISION                NOT NULL,
    refund_date        TIMESTAMP                       NOT NULL,
    created_at         TIMESTAMP                       NOT NULL,
    updated_at         TIMESTAMP                       NOT NULL,
    CONSTRAINT refund_pk PRIMARY KEY (id)
);


create table order_service.orders
(
    id            UUID                                                 DEFAULT gen_random_uuid() NOT NULL,
    order_date    TIMESTAMP                                                                       NOT NULL,
    order_status  INT CHECK ( order_status >= 0 AND order_status <= 3) DEFAULT 0                  NOT NULL,
    price         double precision                                                                NOT NULL,
    rental_period INT                                                                             NOT NULL,
    car_id        UUID                                                                            NOT NULL,
    refund_id     UUID UNIQUE,
    user_id       UUID                                                                            NOT NULL,
    created_at    TIMESTAMP                                                                       NOT NULL,
    updated_at    TIMESTAMP                                                                       NOT NULL,
    CONSTRAINT order_pk PRIMARY KEY (id),
    CONSTRAINT order_refund_fk FOREIGN KEY (refund_id) REFERENCES refunds (id)
);
