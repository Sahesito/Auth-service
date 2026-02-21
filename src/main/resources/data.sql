INSERT INTO users (first_name, last_name, email, password, role, active)
SELECT 'Admin', 'SmartCommerce', 'admin@smartcommerce.com',
       '$2a$12$P0.T0ipXFeosqFkIGXVYO.J2gAtXOQ8/CnVTWVaTPtB8VMQq8qNLa', 'ADMIN', true
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@smartcommerce.com');

INSERT INTO users (first_name, last_name, email, password, role, active)
SELECT 'Seller', 'SmartCommerce', 'seller@smartcommerce.com',
       '$2a$12$P0.T0ipXFeosqFkIGXVYO.J2gAtXOQ8/CnVTWVaTPtB8VMQq8qNLa', 'SELLER', true
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'seller@smartcommerce.com');

INSERT INTO users (first_name, last_name, email, password, role, active)
SELECT 'Client', 'SmartCommerce', 'client@smartcommerce.com',
       '$2a$12$P0.T0ipXFeosqFkIGXVYO.J2gAtXOQ8/CnVTWVaTPtB8VMQq8qNLa', 'CLIENT', true
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'client@smartcommerce.com');