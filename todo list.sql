CREATE TABLE `project` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `judul` varchar(256) NOT NULL
);

CREATE TABLE `todo` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `completed` int NOT NULL,
  `judul` varchar(256) NOT NULL,
  `deadline` timestamp NOT NULL,
  `id_project` int NOT NULL
);

ALTER TABLE `todo` ADD FOREIGN KEY (`id_project`) REFERENCES `project` (`id`) ON DELETE CASCADE;
