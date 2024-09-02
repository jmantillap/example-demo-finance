CREATE DATABASE  IF NOT EXISTS `demo-finance` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `demo-finance`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: demo-finance
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo_identificacion` varchar(2) NOT NULL,
  `numero_identificacion` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_ultima_actualizacion` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_NUM_DOCU` (`tipo_identificacion`,`numero_identificacion`) /*!80000 INVISIBLE */
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (2,'CC','13544171','Javier','Mantilla','juan.perez@example.com','1978-01-15','2024-09-01 16:28:11','2024-09-01 16:28:11'),(3,'CC','37896334','Eliana','Bravo','eliana@example.com','1976-07-05','2024-09-01 18:48:59','2024-09-01 18:48:59');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `tipo_producto` varchar(2) NOT NULL COMMENT 'CC,CA',
  `numero_cuenta` varchar(12) NOT NULL,
  `saldo` decimal(38,2) NOT NULL,
  `excenta_gmf` tinyint(1) NOT NULL DEFAULT '0',
  `fecha_creacion` datetime NOT NULL,
  `fecha_modificacion` datetime NOT NULL,
  `estado` varchar(1) NOT NULL COMMENT 'Activa, Inactiva,Cancelada',
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_cuenta_UNIQUE` (`numero_cuenta`),
  KEY `fk_productos_idcliente_cliente_id` (`id_cliente`),
  CONSTRAINT `fk_productos_idcliente_cliente_id` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,2,'CA','535152770843',0.00,1,'2024-09-01 18:46:25','2024-09-01 18:46:26','A'),(2,3,'CA','539841345215',0.00,1,'2024-09-01 18:49:32','2024-09-01 18:49:32','A'),(3,2,'CC','330258148292',-500.00,0,'2024-09-01 18:52:05','2024-09-01 18:52:05','A');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaccion`
--

DROP TABLE IF EXISTS `transaccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaccion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `tipo` varchar(2) NOT NULL,
  `id_producto` int NOT NULL,
  `signo` varchar(1) NOT NULL,
  `id_transaccion_base` int DEFAULT NULL,
  `monto` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaccion_idproduct_producto_id_idx` (`id_producto`),
  KEY `fk_transaccion_idbase_transaccion_id_idx` (`id_transaccion_base`),
  CONSTRAINT `fk_transaccion_idbase_transaccion_id` FOREIGN KEY (`id_transaccion_base`) REFERENCES `transaccion` (`id`),
  CONSTRAINT `fk_transaccion_idproduct_producto_id` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaccion`
--

LOCK TABLES `transaccion` WRITE;
/*!40000 ALTER TABLE `transaccion` DISABLE KEYS */;
INSERT INTO `transaccion` VALUES (1,'2024-09-01 21:37:37','CO',1,'+',NULL,1000.00),(2,'2024-09-01 21:38:50','CO',1,'+',NULL,2500.00),(3,'2024-09-01 21:54:02','RE',3,'-',NULL,4000.00),(4,'2024-09-01 22:38:31','TS',1,'-',5,3500.00),(5,'2024-09-01 22:38:42','TE',2,'+',4,3500.00),(6,'2024-09-01 22:39:26','TS',2,'-',7,3500.00),(7,'2024-09-01 22:39:26','TE',3,'+',6,3500.00);
/*!40000 ALTER TABLE `transaccion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-02  3:19:02
