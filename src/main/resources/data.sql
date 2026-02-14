INSERT INTO users (first_name, last_name, email, password, role, active)
VALUES ('Admin', 'SmartCommerce', 'admin@smartcommerce.com',
        '$2a$10$8qvFu7ZR2QzCqMEkKk47OOoIbKrOJFrKjR1VjXaHi0KEDPaLjMn0O', 'ADMIN', true)
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (first_name, last_name, email, password, role, active)
VALUES ('Seller', 'SmartCommerce', 'seller@smartcommerce.com',
        '$2a$10$8qvFu7ZR2QzCqMEkKk47OOoIbKrOJFrKjR1VjXaHi0KEDPaLjMn0O', 'SELLER', true)
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (first_name, last_name, email, password, role, active)
VALUES ('Client', 'SmartCommerce', 'client@smartcommerce.com',
        '$2a$10$8qvFu7ZR2QzCqMEkKk47OOoIbKrOJFrKjR1VjXaHi0KEDPaLjMn0O', 'CLIENT', true)
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (first_name, last_name, email, password, role, active)
VALUES ('Carlos', 'Administrador', 'carlos.admin@smartcommerce.com',
        '$2a$10$8qvFu7ZR2QzCqMEkKk47OOoIbKrOJFrKjR1VjXaHi0KEDPaLjMn0O', 'ADMIN', true)
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (first_name, last_name, email, password, role, active)
VALUES ('María', 'Vendedora', 'maria.seller@smartcommerce.com',
        '$2a$10$8qvFu7ZR2QzCqMEkKk47OOoIbKrOJFrKjR1VjXaHi0KEDPaLjMn0O', 'SELLER', true)
ON CONFLICT (email) DO NOTHING;