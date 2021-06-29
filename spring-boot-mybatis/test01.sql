-- MySQL dump 10.13  Distrib 5.7.16, for osx10.11 (x86_64)
--
-- Host: localhost    Database: test01
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `test01`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `test01` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `test01`;

--
-- Table structure for table `table_test_01`
--

DROP TABLE IF EXISTS `table_test_01`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_test_01` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `name` varchar(20) NOT NULL,
                                 `status` tinyint(4) NOT NULL DEFAULT '0',
                                 `test_column` varchar(32) NOT NULL DEFAULT '' COMMENT '测试字段',
                                 `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '测试字段id',
                                 `user_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '测试字段status',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_test_01`
--

LOCK TABLES `table_test_01` WRITE;
/*!40000 ALTER TABLE `table_test_01` DISABLE KEYS */;
INSERT INTO `table_test_01` VALUES (1,'tom',0,'',1,10),(2,'jetty',0,'',2,20),(3,'dog',0,'',3,1),(4,'cat',0,'',4,1);
/*!40000 ALTER TABLE `table_test_01` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-21 19:09:52