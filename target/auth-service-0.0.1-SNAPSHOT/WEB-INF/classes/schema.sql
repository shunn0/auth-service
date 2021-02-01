DROP TABLE IF EXISTS `role_permissions`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `permissions`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `secret` varchar(150) NOT NULL,
  `email` varchar(100) NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Unique_username` (`user_name`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `roles` (
  `role_name` varchar(50) NOT NULL,
  `description` varchar(150) NOT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `permissions` (
  `permission_code` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`role_name`),
  KEY `users_fk_username` (`user_id`),
  KEY `role_fk_roleid` (`role_name`),
  CONSTRAINT `role_fk_role_name` FOREIGN KEY (`role_name`) REFERENCES `roles` (`role_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `role_permissions` (
  `role_name` varchar(50) NOT NULL,
  `permission_code` varchar(100) NOT NULL,
  PRIMARY KEY (`role_name`,`permission_code`),
  KEY `permissions_fk_id` (`permission_code`),
  CONSTRAINT `permission_fk_code` FOREIGN KEY (`permission_code`) REFERENCES `permissions` (`permission_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `roles_fk_roleid` FOREIGN KEY (`role_name`) REFERENCES `roles` (`role_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;