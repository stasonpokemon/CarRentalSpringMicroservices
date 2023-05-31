INSERT INTO user_service.users(id, username, password, active, email, created_at, updated_at)
VALUES ('011ebe9c-ced6-11ed-a81c-0242ac140002', 'admin', '11111111', true, 'stasonpokemon@icloud.com', now(), now()),
       ('c2dc3f28-c401-4cb0-8ce6-9501fb82e166', 'user', '11111111', true, 'egorgus123@gmail.com', now(), now()),
       ('d1c48c1e-99b4-41b5-8e50-1235ce728aaf', 'test', '11111111', true, 'test1@gmail.com', now(), now()),
       ('240184de-710e-4603-ac86-c150cca7b84b', 'test1', '11111111', true, 'test2@gmail.com', now(), now());


INSERT INTO user_service.passports(address, birthday, name, patronymic, surname, created_at, updated_at, user_id)
VALUES ('dsfddsf', now(), 'asfdsad', 'asdsadasd', 'sadasdasd', now(), now(), '011ebe9c-ced6-11ed-a81c-0242ac140002'),
       ('test', now(), 'test', 'test', 'test', now(), now(), 'c2dc3f28-c401-4cb0-8ce6-9501fb82e166'),
       ('test1', now(), 'test1', 'test1', 'test1', now(), now(), '240184de-710e-4603-ac86-c150cca7b84b');



INSERT INTO user_service.user_role(user_id, roles)
VALUES ('011ebe9c-ced6-11ed-a81c-0242ac140002', 'ADMIN'),
       ('011ebe9c-ced6-11ed-a81c-0242ac140002', 'USER'),
       ('c2dc3f28-c401-4cb0-8ce6-9501fb82e166', 'USER'),
       ('d1c48c1e-99b4-41b5-8e50-1235ce728aaf', 'USER'),
       ('240184de-710e-4603-ac86-c150cca7b84b', 'USER');

