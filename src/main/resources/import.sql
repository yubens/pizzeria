insert into product (name, long_description, short_description, unit_price) values ('Muzzarella', 'Pizza con muzzarella y aceitunas', 'Pizza muzzarella', 500.0);
insert into product (name, long_description, short_description, unit_price) values ('Especial', 'Pizza especial con jamon', 'Pizza especial', 600.0);
insert into product (name, long_description, short_description, unit_price) values ('Napolitana', 'Pizza con muzzarella, tomates y aceitunas', 'Pizza napolitana', 550.0);


insert into order_header(created_at, address, email, phone, total, discount, schedule, status) values ('2022-03-21 00:00:00', 'las piedras 123, Mendoza', 'juan@mail.com', '2614440000', 1000.0, false, '21:00', 'PENDIENTE');
insert into order_header(created_at, address, email, phone, total, discount, schedule, status) values ('2022-03-21 00:00:00', 'guadalajara 40, Godoy Cruz', 'laura@mail.com', '2615550000', 1550.0, false, '21:00', 'PENDIENTE');
insert into order_header(created_at, address, email, phone, total, discount, schedule, status) values ('2022-03-22 00:00:00', 'cadetes 510, Mendoza', 'pedro@mail.com', '2616660000', 600.0, false, '21:00', 'PENDIENTE');

insert into order_item(price, quantity, header_id, product_id) values (1000, 2, 1, 1);
insert into order_item(price, quantity, header_id, product_id) values (1000, 2, 2, 1);
insert into order_item(price, quantity, header_id, product_id) values (550, 1, 2, 3);
insert into order_item(price, quantity, header_id, product_id) values (600, 1, 3, 2);
