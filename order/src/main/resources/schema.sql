--Restaurant and menu items are created in Restaurant bounded context - this info here is just
--necessary for use with the orders - hence why ids for project and menu_item are not generated
--in this context
CREATE TABLE project(
                           restaurant_id VARCHAR PRIMARY KEY,
                           restaurant_name VARCHAR NOT NULL
);
CREATE TABLE menu_item(
                          menu_item_id LONG PRIMARY KEY,
                          menu_name VARCHAR NOT NULL,
                          menu_price DECIMAL(6,2) NOT NULL,
                          restaurant_id VARCHAR NOT NULL,
                          FOREIGN KEY(restaurant_id) REFERENCES project(restaurant_id)
);

CREATE TABLE orders(
        id VARCHAR PRIMARY KEY,
        consumer_id VARCHAR NOT NULL,
        restaurant_id VARCHAR NOT NULL,
        payment_method VARCHAR NOT NULL,
        order_state int not null,
        delivery_day_time DATETIME NOT NULL,
        delivery_address_house_name_number VARCHAR NOT NULL,
        delivery_address_street VARCHAR NOT NULL,
        delivery_address_town VARCHAR NOT NULL,
        delivery_address_postal_code VARCHAR NOT NULL
);
CREATE TABLE order_item(
       order_item_id int AUTO_INCREMENT PRIMARY KEY,
       order_item_menu_item_id VARCHAR NOT NULL,
       order_item_menu_name VARCHAR NOT NULL,
       order_item_quantity int NOT NULL,
       order_item_price DECIMAL(6,2) NOT NULL,
       order_id VARCHAR NOT NULL,
       FOREIGN KEY(order_id) REFERENCES orders(id)
);