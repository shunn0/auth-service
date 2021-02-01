INSERT INTO `users`(id,`user_name`,`secret`, `email`, `created_at`, `created_by`, `is_active`)
VALUES(1,'Admin', '$2a$10$oWz/9nJp4Xu6MHyAu9/2n.4MCe6y6l/VtK4.HnDUaaHT1zf1OigmS', 'admin@autho.com', '2021-01-27 14:37:26', 'Admin', 1);

INSERT INTO `roles`(`role_name`,`description`) VALUES('ADMIN', 'Admin role');

INSERT INTO `user_roles`(`user_id`, `role_name`) VALUES(1, 'ADMIN');

INSERT INTO `permissions`(`permission_code`,`description`) VALUES('CREATE_USER', 'Create new User');
INSERT INTO `permissions`(`permission_code`,`description`) VALUES('UPDATE_USER', 'Update User');
INSERT INTO `permissions`(`permission_code`,`description`) VALUES('VIEW_USERS', 'View Users List');
INSERT INTO `permissions`(`permission_code`,`description`) VALUES('CREATE_ROLE', 'Create new role');
INSERT INTO `permissions`(`permission_code`,`description`) VALUES('VIEW_ROLE', 'View role list');
INSERT INTO `permissions`(`permission_code`,`description`) VALUES('CREATE_PERMISSION', 'Create new permission');
INSERT INTO `permissions`(`permission_code`,`description`) VALUES('VIEW_PERMISSION', 'View permissions list');

INSERT INTO `role_permissions`(`role_name`,`permission_code`) VALUES('ADMIN', 'CREATE_USER');
INSERT INTO `role_permissions`(`role_name`,`permission_code`) VALUES('ADMIN', 'UPDATE_USER');
INSERT INTO `role_permissions`(`role_name`,`permission_code`) VALUES('ADMIN', 'VIEW_USERS');
INSERT INTO `role_permissions`(`role_name`,`permission_code`) VALUES('ADMIN', 'CREATE_ROLE');
INSERT INTO `role_permissions`(`role_name`,`permission_code`) VALUES('ADMIN', 'VIEW_ROLE');
INSERT INTO `role_permissions`(`role_name`,`permission_code`) VALUES('ADMIN', 'CREATE_PERMISSION');
INSERT INTO `role_permissions`(`role_name`,`permission_code`) VALUES('ADMIN', 'VIEW_PERMISSION');
