INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (1, 'ADMIN', 'admin@admin', 'admin', 'Luka', 'Maletin', true);
INSERT INTO admin (id) VALUES (1);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (2, 'GUEST', 'guest@guest', 'guest', 'Luka', 'Maletin', true);
INSERT INTO guest (id) VALUES (2);

INSERT INTO menu (id) VALUES (1);
INSERT INTO restaurant (id, name, description, address, menu, "type") VALUES (1, 'Test restaurant', 'Test restaurant description', 'Spens, Novi Sad', 1, 'fast food');

INSERT INTO menu (id) VALUES (2);
INSERT INTO restaurant (id, name, description, address, menu) VALUES (2, 'Test restaurant 2', 'Test restaurant description 2', 'Fruskogorska 5, Novi Sad', 2);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (3, 'WAITER', 'alek@nik', 'alek', 'Aleksandar', 'Nikolic', true);
INSERT INTO employee (id, birthDate, clothingSize, shoeSize, restaurant_id) VALUES (3, '1995-03-14 19:44:52', 'XL', 8, 1);
INSERT INTO waiter (id) VALUES (3);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (4, 'CHEF', 'nik@nik', 'nik', 'Pera', 'Peric', true);
INSERT INTO employee (id, birthDate, clothingSize, shoeSize, restaurant_id) VALUES (4, '1995-03-14 19:44:52', 'XL', 8, 1);
INSERT INTO chef (id) VALUES (4);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (5, 'MANAGER', 'manager@manager', 'manager', 'Luka', 'Maletin', true);
INSERT INTO manager (id, restaurant) VALUES (5, 1);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (6, 'GUEST', 'pero@pero', 'pero', 'Pero', 'Peric', true);
INSERT INTO guest (id) VALUES (6);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (7, 'SUPPLIER', 'supplier@supplier', 'supplier', 'Luka', 'Maletin', true);
INSERT INTO supplier (id, restaurant) VALUES (7, 1);

INSERT INTO shift (id, employee_id, restaurant_id, startTime, endTime, area) VALUES (1, 3, 1, '2017-06-19 11:00:00', '2017-06-25 18:35:00', 'INSIDE_NONSMOKING');

INSERT INTO MenuItem (id, type, name, description, price, menu) VALUES  (1, 'FOOD', 'test food', 'Desc of food', 500, 1);
INSERT INTO MenuItem (id, type, name, description, price, menu) VALUES  (2, 'DRINK', 'test drink', 'Desc of drink', 650, 1);

INSERT INTO Table_table (id, restaurant, area, horizontalPosition, verticalPosition, seatCount, version, changes) VALUES  (1, 1, 'INSIDE_NONSMOKING', 50, 50, 3, 0, 0);
INSERT INTO Table_table (id, restaurant, area, horizontalPosition, verticalPosition, seatCount, version, changes) VALUES  (2, 1, 'INSIDE_SMOKING', 60, 50, 3, 0, 0);

INSERT INTO offer (id, restaurant_id, startDate, endDate) VALUES (1, 1, '2017-05-15 11:00:00', '2017-05-25 11:00:00');
INSERT INTO OfferItem (id, name, offer_id, amount) VALUES (1, 'test item', 1, 20);
INSERT INTO bid (id, offer_id, price, dateOfDelivery, status, supplier_id, version) VALUES (1, 1, 200.5, '2017-05-20 11:00:00', 'ACTIVE', 7, 1);

INSERT INTO offer (id, restaurant_id, startDate, endDate) VALUES (2, 1, '2017-05-16 11:00:00', '2017-05-25 11:00:00');
INSERT INTO OfferItem (id, name, offer_id, amount) VALUES (2, 'test item', 2, 20);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (8, 'WAITER', 'waiter@waiter', 'waiter', 'Waiter', 'White', true);
INSERT INTO employee (id, birthDate, clothingSize, shoeSize, restaurant_id) VALUES (8, '1995-03-14 19:44:52', 'XL', 8, 1);
INSERT INTO waiter (id) VALUES (8);
INSERT INTO shift (id, employee_id, restaurant_id, startTime, endTime, area) VALUES (11, 8, 1, '2017-06-19 11:00:00', '2017-06-25 19:30:00', 'INSIDE_NONSMOKING');

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (9, 'BARTENDER', 'bart@ender', 'bartender', 'Bart', 'Ender', true);
INSERT INTO employee (id, birthDate, clothingSize, shoeSize, restaurant_id) VALUES (9, '1995-03-14 19:44:52', 'XL', 8, 1);
INSERT INTO bartender (id) VALUES (9);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (10, 'BARTENDER', 'h@h', 'h', 'Helena', 'Helenic', true);
INSERT INTO employee (id, birthDate, clothingSize, shoeSize, restaurant_id) VALUES (10, '1995-03-14 19:44:52', 'M', 8, 1);
INSERT INTO bartender (id) VALUES (10);

INSERT INTO shift (id, employee_id, restaurant_id, startTime, endTime) VALUES (12, 9, 1, '2017-06-19 11:00:00', '2017-06-25 19:30:00');
INSERT INTO shift (id, employee_id, restaurant_id, startTime, endTime) VALUES (13, 10, 1, '2017-06-19 11:00:00', '2017-06-25 19:30:00');
INSERT INTO shift (id, employee_id, restaurant_id, startTime, endTime) VALUES (14, 4, 1, '2017-06-19 11:00:00', '2017-06-25 19:30:00');


INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (11, 'SUPPLIER', 'supplier2@supplier2', 'supplier2', 'Luka', 'Maletin', true);
INSERT INTO supplier (id, restaurant) VALUES (11, 1);

ALTER SEQUENCE hibernate_sequence RESTART WITH 100;