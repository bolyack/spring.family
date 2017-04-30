Code11----------------------------------------------

User对应的表和角色表，一个都不能少

CREATE TABLE `s_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  PRIMARY KEY (`id`)
)

insert into `s_user` (`id`, `name`, `email`, `password`, `dob`) values('1','admin','admin@qq.com','111111','2017-04-30');
insert into `s_user` (`id`, `name`, `email`, `password`, `dob`) values('2','bamboo','bamboo@qq.com','123456','2017-04-30');


CREATE TABLE `s_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userrole` (`uid`),
  CONSTRAINT `userrole` FOREIGN KEY (`uid`) REFERENCES `s_user` (`id`)
)
