CREATE TABLE `st_tasks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_img_url` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL default 'Locked',
  `answer` varchar(100) NOT NULL,
  `task`   varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
