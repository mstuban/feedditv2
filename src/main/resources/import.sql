
INSERT INTO users(userid, username, email, password, enabled) VALUES (1, 'user','user@abc.com','$2a$04$K5TjmDug0b2fKYhPuBXWnuQF8gtI5D7W/lIgg0/ScVP.viWlBD3p2', 1);
INSERT INTO users(userid, username, email, password, enabled) VALUES (2, 'admin','admin@def.com','$2a$04$HoX.MOnCmEftG5CLCVpGye1tkn6OKFxS3jNWTKU0SH3chfQ/jhhs6', 1);
 
INSERT INTO user_roles (user_role_id, userid, role) VALUES (1, 1, 'ROLE_USER');
INSERT INTO user_roles (user_role_id, userid, role) VALUES (2, 2, 'ROLE_ADMIN');

INSERT INTO posts(post_id, author_id, author_name, headline, number_of_upvotes, posturl, submit_date, userid) VALUES(1, 1, 'user', 'Headline', 0, 'https://www.yahoo.com/', '2017-01-19 03:14:07', 1)