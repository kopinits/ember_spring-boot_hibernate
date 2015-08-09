# MarkusTodoList
***Simple TODO list application.***

A simple RestFull application using Ember.js+Spring-boot+JPA+Hibernate+MySQL

***Services url:***

- http://localhost:8080/task/save
- http://localhost:8080/task/list
- http://localhost:8080/task/get
- http://localhost:8080/task/remove

***Json format:***

{"id":null, "name":"Default TODOTask Name", "description":"Default TODOTask Description", "startDate":1437361289578,"endDate":1437879689706,"location":"At Home"}


***DB Script***

CREATE DATABASE  IF NOT EXISTS `todolist`;
USE `todolist`;

DROP TABLE IF EXISTS `todotask`;
CREATE TABLE `todotask` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(400) DEFAULT NULL,
  `location` varchar(400) DEFAULT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;