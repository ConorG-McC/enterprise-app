INSERT INTO project (restaurant_id, restaurant_name)
        VALUES('r1', 'Royal Balti');

INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
VALUES('1', 'basmati rice', 2.5, 'r1');
INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
VALUES('2', 'chicken korma', 9.5, 'r1');
INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
VALUES('3', 'tarka dhaal', 4.0, 'r1');
INSERT INTO menu_item (menu_item_id, menu_name, menu_price, restaurant_id)
VALUES('4', 'garlic naan', 3.5, 'r1');

INSERT INTO orders (id, consumer_id, restaurant_id, payment_method, order_state,
                    delivery_day_time, delivery_address_house_name_number,
                    delivery_address_street, delivery_address_town, delivery_address_postal_code)
        VALUES ('ord1','c1','r1', 'visa-debit', 1,
                '2024-01-10 21:00','one',
                'the street','towny town','TN1 1NG');
INSERT INTO orders (id, consumer_id, restaurant_id, payment_method, order_state,
                    delivery_day_time, delivery_address_house_name_number,
                    delivery_address_street, delivery_address_town, delivery_address_postal_code)
VALUES ('ord2','c1','r1', 'visa-debit', 1,
        '2024-01-17 18:30','one',
        'the street','towny town','TN1 1NG');


INSERT INTO order_item (order_item_menu_item_id, order_item_menu_name,
                        order_item_quantity, order_item_price,
                        order_id)
        VALUES(1, 'chicken korma',
               1, 9.5,
               'ord1');
INSERT INTO order_item (order_item_menu_item_id, order_item_menu_name,
                        order_item_quantity, order_item_price,
                        order_id)
        VALUES(1, 'basmati rice',
               1, 2.5,
               'ord1');

INSERT INTO order_item (order_item_menu_item_id, order_item_menu_name,
                        order_item_quantity, order_item_price,
                        order_id)
    VALUES(1, 'tarka dhaal',
            3, 4.0,
           'ord2');
INSERT INTO order_item (order_item_menu_item_id, order_item_menu_name,
                        order_item_quantity, order_item_price,
                        order_id)
    VALUES(1, 'garlic naan',
       1, 3.5,
       'ord2');
--sequence for id's required for saves of new records in the application class (not when using sql here)
--check OrderLineItem (infrastructure) for use of sequence
create sequence order_item_sequence_id start with (select max(order_item_id) + 1 from order_item);