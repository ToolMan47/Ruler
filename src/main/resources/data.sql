CREATE TABLE `users` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `email` varchar(45) UNIQUE,
  `username` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `enabled` BOOLEAN DEFAULT true
);
 
CREATE TABLE `roles` (
  `id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL
);
 
CREATE TABLE `users_roles` (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT unique_user_role UNIQUE (user_id, role_id),
  FOREIGN KEY (`user_id`) REFERENCES users(`id`),
  FOREIGN KEY (`role_id`) REFERENCES roles(`id`)
);

INSERT INTO `roles` (`name`) VALUES ('USER');
INSERT INTO `roles` (`name`) VALUES ('CREATOR');
INSERT INTO `roles` (`name`) VALUES ('EDITOR');
INSERT INTO `roles` (`name`) VALUES ('ADMIN');

INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('patrick', 'patrick', true);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('alex', 'alex', true);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('john', 'john', true);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('namhm', 'namhm', true);
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('admin', 'admin', true);

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 1); -- user patrick has role USER
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (2, 2); -- user alex has role CREATOR
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (3, 3); -- user john has role EDITOR
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (4, 2); -- user namhm has role CREATOR
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (4, 3); -- user namhm has role EDITOR
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (5, 4); -- user admin has role ADMIN


