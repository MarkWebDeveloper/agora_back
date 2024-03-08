INSERT INTO texts (title, description, image) VALUES
('Lorem ipsum dolor sit amet', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor.', 'image1'),
('Curabitur at ipsum ac tellus semper interdum', 'Curabitur at ipsum ac tellus semper interdum. Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc.', 'image2'),
('In facilisis. In sapien ipsum, porta a, auctor quis, euismod sed, lectus.', 'In facilisis. In sapien ipsum, porta a, auctor quis, euismod sed, lectus. Sed bibendum ultrices ante. Sed mollis. Integer tincidunt. Cras tincidunt turpis and a elit.', 'image3');


-- Insertar un usuario
INSERT INTO users (username, password, age, name, lastname, email, phone)
VALUES ('usuario', 'contraseña', 25, 'Nombre', 'Apellido', 'correo@example.com', 1234567890);
-- Obtener el ID del usuario insertado para usarlo como autor en los posts
SET @user_id = (SELECT id FROM users WHERE username = 'usuario' LIMIT 1);
-- -- Insertar un post con el usuario como autor
INSERT INTO posts (title, message, creation_date, postname, user_id)
VALUES ('Titulo del post', 'Contenido del post', NOW(), 'nombre_del_post', @user_id);


-- INSERT INTO replys (id, title, message, creation_date, user_id)
-- VALUES (2, 'Respuesta al post 1', 'Esta es una respuesta al post 1', NOW(), 1);

-- INSERT INTO replys (id, title, message, creation_date, user_id)
-- VALUES (3, 'Respuesta al post 2', 'Esta es una respuesta al post 2', NOW(), 2);