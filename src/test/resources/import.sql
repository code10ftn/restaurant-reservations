INSERT INTO menu (id) VALUES (1);
INSERT INTO restaurant (id, name, description, menu) VALUES (1, 'Test restaurant', 'Test restaurant description', 1);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (1, 'ADMIN', 'admin@admin', 'admin', 'Luka', 'Maletin', 'true');
INSERT INTO admin (id) VALUES (1);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (2, 'WAITER', 'alek@nik', 'alek', 'Aleksandar', 'Nikolic', 'true');
INSERT INTO employee (id, birthDate, clothingSize, shoeSize, restaurant_id) VALUES (2, '1995-03-14 19:44:52', 'XL', 8, 1);
INSERT INTO waiter (id) VALUES (2);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (3, 'BARTENDER', 'aco@nik', 'aco', 'Aleksandar', 'Nikolic', 'true');
INSERT INTO employee (id, birthDate, clothingSize, shoeSize, restaurant_id) VALUES (3, '1995-03-14 19:44:52', 'XL', 8, 1);
INSERT INTO bartender (id) VALUES (3);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (4, 'GUEST', 'gost@gost', 'gost', 'Gost', 'Gostic', 'true');
INSERT INTO guest (id) VALUES (4);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (5, 'MANAGER', 'manager@manager', 'manager', 'Luka', 'Maletin', 'true');
INSERT INTO manager (id, restaurant) VALUES (5, 1);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (6, 'GUEST', 'pero@pero', 'pero', 'Pero', 'Peric', true);
INSERT INTO guest (id) VALUES (6);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (7, 'GUEST', 'nikola@nikola', 'nikola', 'Nikola', 'Nikolic', true);
INSERT INTO guest (id) VALUES (7);

INSERT INTO shift (id, employee_id, restaurant_id, startTime, endTime) VALUES (1, 2, 1, '2017-06-19 11:00:00', '2017-06-25 19:30:00');
INSERT INTO shift (id, employee_id, restaurant_id, startTime, endTime) VALUES (2, 3, 1, '2017-04-29 18:00:00', '2017-04-30 00:00:00');

INSERT INTO MenuItem (id, type, name, description, price, menu) VALUES  (1, 'FOOD', 'test food', 'Desc of food', 500, 1);
INSERT INTO MenuItem (id, type, name, description, price, menu) VALUES  (2, 'DRINK', 'test drink', 'Desc of drink', 650, 1);

INSERT INTO Friendship (id, sender_id, receiver_id, accepted) VALUES (1, 4, 6, false);

INSERT INTO user_table (id, role, email, password, firstName, lastName, verified) VALUES (8, 'SUPPLIER', 'supplier@supplier', 'supplier', 'Luka', 'Maletin', true);
INSERT INTO supplier (id, restaurant) VALUES (8, 1);

INSERT INTO offer (id, restaurant_id, startDate, endDate) VALUES (1, 1, '2017-05-15 11:00:00', '2017-05-25 11:00:00');
INSERT INTO OfferItem (id, name, offer_id, amount) VALUES (1, 'test item', 1, 20);
INSERT INTO bid (id, offer_id, price, dateOfDelivery, status, supplier_id, version) VALUES (1, 1, 200.5, '2017-05-20 11:00:00', 'ACTIVE', 8, 1);

INSERT INTO Table_table (id, restaurant, area, horizontalPosition, verticalPosition, seatCount, version, changes) VALUES  (1, 1, 'INSIDE_NONSMOKING', 50, 50, 3, 0, 0);