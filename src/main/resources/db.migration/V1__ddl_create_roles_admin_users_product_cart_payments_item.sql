create table if not exists roles
(
    id   bigint auto_increment primary key,
    name VARCHAR(30)       NOT NULL
);
#

CREATE TABLE `admin`
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(30)           NOT NULL,
    last_name  VARCHAR(30)           NOT NULL,
    user_name  VARCHAR(18)           NOT NULL,
    CONSTRAINT pk_admin PRIMARY KEY (id)
);

#

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL ,
    first_name VARCHAR(30)           NOT NULL,
    last_name  VARCHAR(30)           NOT NULL,
    user_name  VARCHAR(18)           NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    role_id   BIGINT               NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLES FOREIGN KEY (role_id) REFERENCES roles (id);

#

CREATE TABLE product
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NOT NULL,
    price    DOUBLE                NULL,
    quantity INT                   NULL,
    amount   DOUBLE                NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

#

CREATE TABLE cart
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    product_id BIGINT                NULL,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

ALTER TABLE cart
    ADD CONSTRAINT FK_CART_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);
# #

CREATE TABLE payments
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    amount  DOUBLE              NOT NULL,
    date       DATETIME              NULL,
    user_id BIGINT                ,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);

ALTER TABLE payments
    ADD CONSTRAINT FK_PAYMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

#

CREATE TABLE item
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    quantity   INT                   NOT NULL,
    amount     DOUBLE                NOT NULL,
    product_id BIGINT                NULL,
    payment_id BIGINT                NULL,
    CONSTRAINT pk_item PRIMARY KEY (id)
);

ALTER TABLE item
    ADD CONSTRAINT FK_ITEM_ON_PAYMENT FOREIGN KEY (payment_id) REFERENCES payments (id) ;

ALTER TABLE item
    ADD CONSTRAINT FK_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);





