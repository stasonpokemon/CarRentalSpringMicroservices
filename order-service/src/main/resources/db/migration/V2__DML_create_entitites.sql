insert into order_service.orders(order_date, order_status, price, rental_period, car_id, user_id, created_at,
                   updated_at)
VALUES (now(), 0, 1000, 2, '8c091ab8-ed54-4b64-9931-cf9fcb1081d5', '011ebe9c-ced6-11ed-a81c-0242ac140002', now(),
        now()),
       (now(), 0, 1400, 2, 'a61bf792-7646-4811-ba55-40a706f81094', 'c2dc3f28-c401-4cb0-8ce6-9501fb82e166', now(),
        now());
