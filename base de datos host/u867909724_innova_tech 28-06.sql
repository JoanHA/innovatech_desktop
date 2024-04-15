-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 28-06-2023 a las 05:51:37
-- Versión del servidor: 10.6.14-MariaDB-cll-lve
-- Versión de PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `u867909724_innova_tech`
--
CREATE DATABASE IF NOT EXISTS `u867909724_innova_tech` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `u867909724_innova_tech`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calificaciones`
--

CREATE TABLE IF NOT EXISTS `calificaciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calificacion` int(1) NOT NULL,
  `comentario` text DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Calificacion_Usuario1` (`usuario_id`),
  KEY `fk_Calificacion_Productos1` (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrusels`
--

CREATE TABLE IF NOT EXISTS `carrusels` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `posicion` int(1) NOT NULL,
  `imagen` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Carrusel_Usuario1` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE IF NOT EXISTS `compras` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `proveedor_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Pedidos_Proveedores1` (`proveedor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamentos`
--

CREATE TABLE IF NOT EXISTS `departamentos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `departamento` varchar(255) NOT NULL,
  `Estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `pais_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Departamentos_Paises1` (`pais_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `departamentos`
--

INSERT INTO `departamentos` (`id`, `departamento`, `Estado`, `created_at`, `updated_at`, `pais_id`) VALUES
(5, 'ANTIOQUIA', '1', NULL, NULL, 1),
(8, 'ATLÁNTICO', '1', NULL, NULL, 1),
(11, 'BOGOTÁ, D.C.', '1', NULL, NULL, 1),
(13, 'BOLÍVAR', '1', NULL, NULL, 1),
(15, 'BOYACÁ', '1', NULL, NULL, 1),
(17, 'CALDAS', '1', NULL, NULL, 1),
(18, 'CAQUETÁ', '1', NULL, NULL, 1),
(19, 'CAUCA', '1', NULL, NULL, 1),
(20, 'CESAR', '1', NULL, NULL, 1),
(23, 'CÓRDOBA', '1', NULL, NULL, 1),
(25, 'CUNDINAMARCA', '1', NULL, NULL, 1),
(27, 'CHOCÓ', '1', NULL, NULL, 1),
(41, 'HUILA', '1', NULL, NULL, 1),
(44, 'LA GUAJIRA', '1', NULL, NULL, 1),
(47, 'MAGDALENA', '1', NULL, NULL, 1),
(50, 'META', '1', NULL, NULL, 1),
(52, 'NARIÑO', '1', NULL, NULL, 1),
(54, 'NORTE DE SANTANDER', '1', NULL, NULL, 1),
(63, 'QUINDIO', '1', NULL, NULL, 1),
(66, 'RISARALDA', '1', NULL, NULL, 1),
(68, 'SANTANDER', '1', NULL, NULL, 1),
(70, 'SUCRE', '1', NULL, NULL, 1),
(73, 'TOLIMA', '1', NULL, NULL, 1),
(76, 'VALLE DEL CAUCA', '1', NULL, NULL, 1),
(81, 'ARAUCA', '1', NULL, NULL, 1),
(85, 'CASANARE', '1', NULL, NULL, 1),
(86, 'PUTUMAYO', '1', NULL, NULL, 1),
(88, 'ARCHIPIÉLAGO DE SAN ANDRÉS, PROVIDENCIA Y SANTA CATALINA', '1', NULL, NULL, 1),
(91, 'AMAZONAS', '1', NULL, NULL, 1),
(94, 'GUAINÍA', '1', NULL, NULL, 1),
(95, 'GUAVIARE', '1', NULL, NULL, 1),
(97, 'VAUPÉS', '1', NULL, NULL, 1),
(99, 'VICHADA', '1', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_compras`
--

CREATE TABLE IF NOT EXISTS `detalle_compras` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(5) NOT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `compra_id` bigint(20) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Detalle_pedido_Productos1` (`producto_id`),
  KEY `fk_Detalle_pedido_Pedidos1` (`compra_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ventas`
--

CREATE TABLE IF NOT EXISTS `detalle_ventas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `producto_id` bigint(20) NOT NULL,
  `venta_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_table1_Compra1` (`venta_id`),
  KEY `fk_table1_Productos1` (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direcciones`
--

CREATE TABLE IF NOT EXISTS `direcciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `barrio` varchar(255) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `piso` varchar(10) DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `municipio_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Direccion_Usuario1` (`usuario_id`),
  KEY `fk_Direccion_Municipios1` (`municipio_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `informes`
--

CREATE TABLE IF NOT EXISTS `informes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `imagen` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Informes_Usuario1` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lista_deseos`
--

CREATE TABLE IF NOT EXISTS `lista_deseos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Deseos_Usuario1` (`usuario_id`),
  KEY `fk_Deseos_Productos1` (`producto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipios`
--

CREATE TABLE IF NOT EXISTS `municipios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `municipio` varchar(45) NOT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `departamento_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Municipios_Departamentos1` (`departamento_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `municipios`
--

INSERT INTO `municipios` (`id`, `municipio`, `estado`, `created_at`, `updated_at`, `departamento_id`) VALUES
(1, 'Abriaquí', '1', NULL, NULL, 5),
(2, 'Acacías', '1', NULL, NULL, 50),
(3, 'Acandí', '1', NULL, NULL, 27),
(4, 'Acevedo', '1', NULL, NULL, 41),
(5, 'Achí', '1', NULL, NULL, 13),
(6, 'Agrado', '1', NULL, NULL, 41),
(7, 'Agua de Dios', '1', NULL, NULL, 25),
(8, 'Aguachica', '1', NULL, NULL, 20),
(9, 'Aguada', '1', NULL, NULL, 68),
(10, 'Aguadas', '1', NULL, NULL, 17),
(11, 'Aguazul', '1', NULL, NULL, 85),
(12, 'Agustín Codazzi', '1', NULL, NULL, 20),
(13, 'Aipe', '1', NULL, NULL, 41),
(14, 'Albania', '1', NULL, NULL, 18),
(15, 'Albania', '1', NULL, NULL, 44),
(16, 'Albania', '1', NULL, NULL, 68),
(17, 'Albán', '1', NULL, NULL, 25),
(18, 'Albán (San José)', '1', NULL, NULL, 52),
(19, 'Alcalá', '1', NULL, NULL, 76),
(20, 'Alejandria', '1', NULL, NULL, 5),
(21, 'Algarrobo', '1', NULL, NULL, 47),
(22, 'Algeciras', '1', NULL, NULL, 41),
(23, 'Almaguer', '1', NULL, NULL, 19),
(24, 'Almeida', '1', NULL, NULL, 15),
(25, 'Alpujarra', '1', NULL, NULL, 73),
(26, 'Altamira', '1', NULL, NULL, 41),
(27, 'Alto Baudó (Pie de Pato)', '1', NULL, NULL, 27),
(28, 'Altos del Rosario', '1', NULL, NULL, 13),
(29, 'Alvarado', '1', NULL, NULL, 73),
(30, 'Amagá', '1', NULL, NULL, 5),
(31, 'Amalfi', '1', NULL, NULL, 5),
(32, 'Ambalema', '1', NULL, NULL, 73),
(33, 'Anapoima', '1', NULL, NULL, 25),
(34, 'Ancuya', '1', NULL, NULL, 52),
(35, 'Andalucía', '1', NULL, NULL, 76),
(36, 'Andes', '1', NULL, NULL, 5),
(37, 'Angelópolis', '1', NULL, NULL, 5),
(38, 'Angostura', '1', NULL, NULL, 5),
(39, 'Anolaima', '1', NULL, NULL, 25),
(40, 'Anorí', '1', NULL, NULL, 5),
(41, 'Anserma', '1', NULL, NULL, 17),
(42, 'Ansermanuevo', '1', NULL, NULL, 76),
(43, 'Anzoátegui', '1', NULL, NULL, 73),
(44, 'Anzá', '1', NULL, NULL, 5),
(45, 'Apartadó', '1', NULL, NULL, 5),
(46, 'Apulo', '1', NULL, NULL, 25),
(47, 'Apía', '1', NULL, NULL, 66),
(48, 'Aquitania', '1', NULL, NULL, 15),
(49, 'Aracataca', '1', NULL, NULL, 47),
(50, 'Aranzazu', '1', NULL, NULL, 17),
(51, 'Aratoca', '1', NULL, NULL, 68),
(52, 'Arauca', '1', NULL, NULL, 81),
(53, 'Arauquita', '1', NULL, NULL, 81),
(54, 'Arbeláez', '1', NULL, NULL, 25),
(55, 'Arboleda (Berruecos)', '1', NULL, NULL, 52),
(56, 'Arboledas', '1', NULL, NULL, 54),
(57, 'Arboletes', '1', NULL, NULL, 5),
(58, 'Arcabuco', '1', NULL, NULL, 15),
(59, 'Arenal', '1', NULL, NULL, 13),
(60, 'Argelia', '1', NULL, NULL, 5),
(61, 'Argelia', '1', NULL, NULL, 19),
(62, 'Argelia', '1', NULL, NULL, 76),
(63, 'Ariguaní (El Difícil)', '1', NULL, NULL, 47),
(64, 'Arjona', '1', NULL, NULL, 13),
(65, 'Armenia', '1', NULL, NULL, 5),
(66, 'Armenia', '1', NULL, NULL, 63),
(67, 'Armero (Guayabal)', '1', NULL, NULL, 73),
(68, 'Arroyohondo', '1', NULL, NULL, 13),
(69, 'Astrea', '1', NULL, NULL, 20),
(70, 'Ataco', '1', NULL, NULL, 73),
(71, 'Atrato (Yuto)', '1', NULL, NULL, 27),
(72, 'Ayapel', '1', NULL, NULL, 23),
(73, 'Bagadó', '1', NULL, NULL, 27),
(74, 'Bahía Solano (Mútis)', '1', NULL, NULL, 27),
(75, 'Bajo Baudó (Pizarro)', '1', NULL, NULL, 27),
(76, 'Balboa', '1', NULL, NULL, 19),
(77, 'Balboa', '1', NULL, NULL, 66),
(78, 'Baranoa', '1', NULL, NULL, 8),
(79, 'Baraya', '1', NULL, NULL, 41),
(80, 'Barbacoas', '1', NULL, NULL, 52),
(81, 'Barbosa', '1', NULL, NULL, 5),
(82, 'Barbosa', '1', NULL, NULL, 68),
(83, 'Barichara', '1', NULL, NULL, 68),
(84, 'Barranca de Upía', '1', NULL, NULL, 50),
(85, 'Barrancabermeja', '1', NULL, NULL, 68),
(86, 'Barrancas', '1', NULL, NULL, 44),
(87, 'Barranco de Loba', '1', NULL, NULL, 13),
(88, 'Barranquilla', '1', NULL, NULL, 8),
(89, 'Becerríl', '1', NULL, NULL, 20),
(90, 'Belalcázar', '1', NULL, NULL, 17),
(91, 'Bello', '1', NULL, NULL, 5),
(92, 'Belmira', '1', NULL, NULL, 5),
(93, 'Beltrán', '1', NULL, NULL, 25),
(94, 'Belén', '1', NULL, NULL, 15),
(95, 'Belén', '1', NULL, NULL, 52),
(96, 'Belén de Bajirá', '1', NULL, NULL, 27),
(97, 'Belén de Umbría', '1', NULL, NULL, 66),
(98, 'Belén de los Andaquíes', '1', NULL, NULL, 18),
(99, 'Berbeo', '1', NULL, NULL, 15),
(100, 'Betania', '1', NULL, NULL, 5),
(101, 'Beteitiva', '1', NULL, NULL, 15),
(102, 'Betulia', '1', NULL, NULL, 5),
(103, 'Betulia', '1', NULL, NULL, 68),
(104, 'Bituima', '1', NULL, NULL, 25),
(105, 'Boavita', '1', NULL, NULL, 15),
(106, 'Bochalema', '1', NULL, NULL, 54),
(107, 'Bogotá D.C.', '1', NULL, NULL, 11),
(108, 'Bojacá', '1', NULL, NULL, 25),
(109, 'Bojayá (Bellavista)', '1', NULL, NULL, 27),
(110, 'Bolívar', '1', NULL, NULL, 5),
(111, 'Bolívar', '1', NULL, NULL, 19),
(112, 'Bolívar', '1', NULL, NULL, 68),
(113, 'Bolívar', '1', NULL, NULL, 76),
(114, 'Bosconia', '1', NULL, NULL, 20),
(115, 'Boyacá', '1', NULL, NULL, 15),
(116, 'Briceño', '1', NULL, NULL, 5),
(117, 'Briceño', '1', NULL, NULL, 15),
(118, 'Bucaramanga', '1', NULL, NULL, 68),
(119, 'Bucarasica', '1', NULL, NULL, 54),
(120, 'Buenaventura', '1', NULL, NULL, 76),
(121, 'Buenavista', '1', NULL, NULL, 15),
(122, 'Buenavista', '1', NULL, NULL, 23),
(123, 'Buenavista', '1', NULL, NULL, 63),
(124, 'Buenavista', '1', NULL, NULL, 70),
(125, 'Buenos Aires', '1', NULL, NULL, 19),
(126, 'Buesaco', '1', NULL, NULL, 52),
(127, 'Buga', '1', NULL, NULL, 76),
(128, 'Bugalagrande', '1', NULL, NULL, 76),
(129, 'Burítica', '1', NULL, NULL, 5),
(130, 'Busbanza', '1', NULL, NULL, 15),
(131, 'Cabrera', '1', NULL, NULL, 25),
(132, 'Cabrera', '1', NULL, NULL, 68),
(133, 'Cabuyaro', '1', NULL, NULL, 50),
(134, 'Cachipay', '1', NULL, NULL, 25),
(135, 'Caicedo', '1', NULL, NULL, 5),
(136, 'Caicedonia', '1', NULL, NULL, 76),
(137, 'Caimito', '1', NULL, NULL, 70),
(138, 'Cajamarca', '1', NULL, NULL, 73),
(139, 'Cajibío', '1', NULL, NULL, 19),
(140, 'Cajicá', '1', NULL, NULL, 25),
(141, 'Calamar', '1', NULL, NULL, 13),
(142, 'Calamar', '1', NULL, NULL, 95),
(143, 'Calarcá', '1', NULL, NULL, 63),
(144, 'Caldas', '1', NULL, NULL, 5),
(145, 'Caldas', '1', NULL, NULL, 15),
(146, 'Caldono', '1', NULL, NULL, 19),
(147, 'California', '1', NULL, NULL, 68),
(148, 'Calima (Darién)', '1', NULL, NULL, 76),
(149, 'Caloto', '1', NULL, NULL, 19),
(150, 'Calí', '1', NULL, NULL, 76),
(151, 'Campamento', '1', NULL, NULL, 5),
(152, 'Campo de la Cruz', '1', NULL, NULL, 8),
(153, 'Campoalegre', '1', NULL, NULL, 41),
(154, 'Campohermoso', '1', NULL, NULL, 15),
(155, 'Canalete', '1', NULL, NULL, 23),
(156, 'Candelaria', '1', NULL, NULL, 8),
(157, 'Candelaria', '1', NULL, NULL, 76),
(158, 'Cantagallo', '1', NULL, NULL, 13),
(159, 'Cantón de San Pablo', '1', NULL, NULL, 27),
(160, 'Caparrapí', '1', NULL, NULL, 25),
(161, 'Capitanejo', '1', NULL, NULL, 68),
(162, 'Caracolí', '1', NULL, NULL, 5),
(163, 'Caramanta', '1', NULL, NULL, 5),
(164, 'Carcasí', '1', NULL, NULL, 68),
(165, 'Carepa', '1', NULL, NULL, 5),
(166, 'Carmen de Apicalá', '1', NULL, NULL, 73),
(167, 'Carmen de Carupa', '1', NULL, NULL, 25),
(168, 'Carmen de Viboral', '1', NULL, NULL, 5),
(169, 'Carmen del Darién (CURBARADÓ)', '1', NULL, NULL, 27),
(170, 'Carolina', '1', NULL, NULL, 5),
(171, 'Cartagena', '1', NULL, NULL, 13),
(172, 'Cartagena del Chairá', '1', NULL, NULL, 18),
(173, 'Cartago', '1', NULL, NULL, 76),
(174, 'Carurú', '1', NULL, NULL, 97),
(175, 'Casabianca', '1', NULL, NULL, 73),
(176, 'Castilla la Nueva', '1', NULL, NULL, 50),
(177, 'Caucasia', '1', NULL, NULL, 5),
(178, 'Cañasgordas', '1', NULL, NULL, 5),
(179, 'Cepita', '1', NULL, NULL, 68),
(180, 'Cereté', '1', NULL, NULL, 23),
(181, 'Cerinza', '1', NULL, NULL, 15),
(182, 'Cerrito', '1', NULL, NULL, 68),
(183, 'Cerro San Antonio', '1', NULL, NULL, 47),
(184, 'Chachaguí', '1', NULL, NULL, 52),
(185, 'Chaguaní', '1', NULL, NULL, 25),
(186, 'Chalán', '1', NULL, NULL, 70),
(187, 'Chaparral', '1', NULL, NULL, 73),
(188, 'Charalá', '1', NULL, NULL, 68),
(189, 'Charta', '1', NULL, NULL, 68),
(190, 'Chigorodó', '1', NULL, NULL, 5),
(191, 'Chima', '1', NULL, NULL, 68),
(192, 'Chimichagua', '1', NULL, NULL, 20),
(193, 'Chimá', '1', NULL, NULL, 23),
(194, 'Chinavita', '1', NULL, NULL, 15),
(195, 'Chinchiná', '1', NULL, NULL, 17),
(196, 'Chinácota', '1', NULL, NULL, 54),
(197, 'Chinú', '1', NULL, NULL, 23),
(198, 'Chipaque', '1', NULL, NULL, 25),
(199, 'Chipatá', '1', NULL, NULL, 68),
(200, 'Chiquinquirá', '1', NULL, NULL, 15),
(201, 'Chiriguaná', '1', NULL, NULL, 20),
(202, 'Chiscas', '1', NULL, NULL, 15),
(203, 'Chita', '1', NULL, NULL, 15),
(204, 'Chitagá', '1', NULL, NULL, 54),
(205, 'Chitaraque', '1', NULL, NULL, 15),
(206, 'Chivatá', '1', NULL, NULL, 15),
(207, 'Chivolo', '1', NULL, NULL, 47),
(208, 'Choachí', '1', NULL, NULL, 25),
(209, 'Chocontá', '1', NULL, NULL, 25),
(210, 'Chámeza', '1', NULL, NULL, 85),
(211, 'Chía', '1', NULL, NULL, 25),
(212, 'Chíquiza', '1', NULL, NULL, 15),
(213, 'Chívor', '1', NULL, NULL, 15),
(214, 'Cicuco', '1', NULL, NULL, 13),
(215, 'Cimitarra', '1', NULL, NULL, 68),
(216, 'Circasia', '1', NULL, NULL, 63),
(217, 'Cisneros', '1', NULL, NULL, 5),
(218, 'Ciénaga', '1', NULL, NULL, 15),
(219, 'Ciénaga', '1', NULL, NULL, 47),
(220, 'Ciénaga de Oro', '1', NULL, NULL, 23),
(221, 'Clemencia', '1', NULL, NULL, 13),
(222, 'Cocorná', '1', NULL, NULL, 5),
(223, 'Coello', '1', NULL, NULL, 73),
(224, 'Cogua', '1', NULL, NULL, 25),
(225, 'Colombia', '1', NULL, NULL, 41),
(226, 'Colosó (Ricaurte)', '1', NULL, NULL, 70),
(227, 'Colón', '1', NULL, NULL, 86),
(228, 'Colón (Génova)', '1', NULL, NULL, 52),
(229, 'Concepción', '1', NULL, NULL, 5),
(230, 'Concepción', '1', NULL, NULL, 68),
(231, 'Concordia', '1', NULL, NULL, 5),
(232, 'Concordia', '1', NULL, NULL, 47),
(233, 'Condoto', '1', NULL, NULL, 27),
(234, 'Confines', '1', NULL, NULL, 68),
(235, 'Consaca', '1', NULL, NULL, 52),
(236, 'Contadero', '1', NULL, NULL, 52),
(237, 'Contratación', '1', NULL, NULL, 68),
(238, 'Convención', '1', NULL, NULL, 54),
(239, 'Copacabana', '1', NULL, NULL, 5),
(240, 'Coper', '1', NULL, NULL, 15),
(241, 'Cordobá', '1', NULL, NULL, 63),
(242, 'Corinto', '1', NULL, NULL, 19),
(243, 'Coromoro', '1', NULL, NULL, 68),
(244, 'Corozal', '1', NULL, NULL, 70),
(245, 'Corrales', '1', NULL, NULL, 15),
(246, 'Cota', '1', NULL, NULL, 25),
(247, 'Cotorra', '1', NULL, NULL, 23),
(248, 'Covarachía', '1', NULL, NULL, 15),
(249, 'Coveñas', '1', NULL, NULL, 70),
(250, 'Coyaima', '1', NULL, NULL, 73),
(251, 'Cravo Norte', '1', NULL, NULL, 81),
(252, 'Cuaspud (Carlosama)', '1', NULL, NULL, 52),
(253, 'Cubarral', '1', NULL, NULL, 50),
(254, 'Cubará', '1', NULL, NULL, 15),
(255, 'Cucaita', '1', NULL, NULL, 15),
(256, 'Cucunubá', '1', NULL, NULL, 25),
(257, 'Cucutilla', '1', NULL, NULL, 54),
(258, 'Cuitiva', '1', NULL, NULL, 15),
(259, 'Cumaral', '1', NULL, NULL, 50),
(260, 'Cumaribo', '1', NULL, NULL, 99),
(261, 'Cumbal', '1', NULL, NULL, 52),
(262, 'Cumbitara', '1', NULL, NULL, 52),
(263, 'Cunday', '1', NULL, NULL, 73),
(264, 'Curillo', '1', NULL, NULL, 18),
(265, 'Curití', '1', NULL, NULL, 68),
(266, 'Curumaní', '1', NULL, NULL, 20),
(267, 'Cáceres', '1', NULL, NULL, 5),
(268, 'Cáchira', '1', NULL, NULL, 54),
(269, 'Cácota', '1', NULL, NULL, 54),
(270, 'Cáqueza', '1', NULL, NULL, 25),
(271, 'Cértegui', '1', NULL, NULL, 27),
(272, 'Cómbita', '1', NULL, NULL, 15),
(273, 'Córdoba', '1', NULL, NULL, 13),
(274, 'Córdoba', '1', NULL, NULL, 52),
(275, 'Cúcuta', '1', NULL, NULL, 54),
(276, 'Dabeiba', '1', NULL, NULL, 5),
(277, 'Dagua', '1', NULL, NULL, 76),
(278, 'Dibulla', '1', NULL, NULL, 44),
(279, 'Distracción', '1', NULL, NULL, 44),
(280, 'Dolores', '1', NULL, NULL, 73),
(281, 'Don Matías', '1', NULL, NULL, 5),
(282, 'Dos Quebradas', '1', NULL, NULL, 66),
(283, 'Duitama', '1', NULL, NULL, 15),
(284, 'Durania', '1', NULL, NULL, 54),
(285, 'Ebéjico', '1', NULL, NULL, 5),
(286, 'El Bagre', '1', NULL, NULL, 5),
(287, 'El Banco', '1', NULL, NULL, 47),
(288, 'El Cairo', '1', NULL, NULL, 76),
(289, 'El Calvario', '1', NULL, NULL, 50),
(290, 'El Carmen', '1', NULL, NULL, 54),
(291, 'El Carmen', '1', NULL, NULL, 68),
(292, 'El Carmen de Atrato', '1', NULL, NULL, 27),
(293, 'El Carmen de Bolívar', '1', NULL, NULL, 13),
(294, 'El Castillo', '1', NULL, NULL, 50),
(295, 'El Cerrito', '1', NULL, NULL, 76),
(296, 'El Charco', '1', NULL, NULL, 52),
(297, 'El Cocuy', '1', NULL, NULL, 15),
(298, 'El Colegio', '1', NULL, NULL, 25),
(299, 'El Copey', '1', NULL, NULL, 20),
(300, 'El Doncello', '1', NULL, NULL, 18),
(301, 'El Dorado', '1', NULL, NULL, 50),
(302, 'El Dovio', '1', NULL, NULL, 76),
(303, 'El Espino', '1', NULL, NULL, 15),
(304, 'El Guacamayo', '1', NULL, NULL, 68),
(305, 'El Guamo', '1', NULL, NULL, 13),
(306, 'El Molino', '1', NULL, NULL, 44),
(307, 'El Paso', '1', NULL, NULL, 20),
(308, 'El Paujil', '1', NULL, NULL, 18),
(309, 'El Peñol', '1', NULL, NULL, 52),
(310, 'El Peñon', '1', NULL, NULL, 13),
(311, 'El Peñon', '1', NULL, NULL, 68),
(312, 'El Peñón', '1', NULL, NULL, 25),
(313, 'El Piñon', '1', NULL, NULL, 47),
(314, 'El Playón', '1', NULL, NULL, 68),
(315, 'El Retorno', '1', NULL, NULL, 95),
(316, 'El Retén', '1', NULL, NULL, 47),
(317, 'El Roble', '1', NULL, NULL, 70),
(318, 'El Rosal', '1', NULL, NULL, 25),
(319, 'El Rosario', '1', NULL, NULL, 52),
(320, 'El Tablón de Gómez', '1', NULL, NULL, 52),
(321, 'El Tambo', '1', NULL, NULL, 19),
(322, 'El Tambo', '1', NULL, NULL, 52),
(323, 'El Tarra', '1', NULL, NULL, 54),
(324, 'El Zulia', '1', NULL, NULL, 54),
(325, 'El Águila', '1', NULL, NULL, 76),
(326, 'Elías', '1', NULL, NULL, 41),
(327, 'Encino', '1', NULL, NULL, 68),
(328, 'Enciso', '1', NULL, NULL, 68),
(329, 'Entrerríos', '1', NULL, NULL, 5),
(330, 'Envigado', '1', NULL, NULL, 5),
(331, 'Espinal', '1', NULL, NULL, 73),
(332, 'Facatativá', '1', NULL, NULL, 25),
(333, 'Falan', '1', NULL, NULL, 73),
(334, 'Filadelfia', '1', NULL, NULL, 17),
(335, 'Filandia', '1', NULL, NULL, 63),
(336, 'Firavitoba', '1', NULL, NULL, 15),
(337, 'Flandes', '1', NULL, NULL, 73),
(338, 'Florencia', '1', NULL, NULL, 18),
(339, 'Florencia', '1', NULL, NULL, 19),
(340, 'Floresta', '1', NULL, NULL, 15),
(341, 'Florida', '1', NULL, NULL, 76),
(342, 'Floridablanca', '1', NULL, NULL, 68),
(343, 'Florián', '1', NULL, NULL, 68),
(344, 'Fonseca', '1', NULL, NULL, 44),
(345, 'Fortúl', '1', NULL, NULL, 81),
(346, 'Fosca', '1', NULL, NULL, 25),
(347, 'Francisco Pizarro', '1', NULL, NULL, 52),
(348, 'Fredonia', '1', NULL, NULL, 5),
(349, 'Fresno', '1', NULL, NULL, 73),
(350, 'Frontino', '1', NULL, NULL, 5),
(351, 'Fuente de Oro', '1', NULL, NULL, 50),
(352, 'Fundación', '1', NULL, NULL, 47),
(353, 'Funes', '1', NULL, NULL, 52),
(354, 'Funza', '1', NULL, NULL, 25),
(355, 'Fusagasugá', '1', NULL, NULL, 25),
(356, 'Fómeque', '1', NULL, NULL, 25),
(357, 'Fúquene', '1', NULL, NULL, 25),
(358, 'Gachalá', '1', NULL, NULL, 25),
(359, 'Gachancipá', '1', NULL, NULL, 25),
(360, 'Gachantivá', '1', NULL, NULL, 15),
(361, 'Gachetá', '1', NULL, NULL, 25),
(362, 'Galapa', '1', NULL, NULL, 8),
(363, 'Galeras (Nueva Granada)', '1', NULL, NULL, 70),
(364, 'Galán', '1', NULL, NULL, 68),
(365, 'Gama', '1', NULL, NULL, 25),
(366, 'Gamarra', '1', NULL, NULL, 20),
(367, 'Garagoa', '1', NULL, NULL, 15),
(368, 'Garzón', '1', NULL, NULL, 41),
(369, 'Gigante', '1', NULL, NULL, 41),
(370, 'Ginebra', '1', NULL, NULL, 76),
(371, 'Giraldo', '1', NULL, NULL, 5),
(372, 'Girardot', '1', NULL, NULL, 25),
(373, 'Girardota', '1', NULL, NULL, 5),
(374, 'Girón', '1', NULL, NULL, 68),
(375, 'Gonzalez', '1', NULL, NULL, 20),
(376, 'Gramalote', '1', NULL, NULL, 54),
(377, 'Granada', '1', NULL, NULL, 5),
(378, 'Granada', '1', NULL, NULL, 25),
(379, 'Granada', '1', NULL, NULL, 50),
(380, 'Guaca', '1', NULL, NULL, 68),
(381, 'Guacamayas', '1', NULL, NULL, 15),
(382, 'Guacarí', '1', NULL, NULL, 76),
(383, 'Guachavés', '1', NULL, NULL, 52),
(384, 'Guachené', '1', NULL, NULL, 19),
(385, 'Guachetá', '1', NULL, NULL, 25),
(386, 'Guachucal', '1', NULL, NULL, 52),
(387, 'Guadalupe', '1', NULL, NULL, 5),
(388, 'Guadalupe', '1', NULL, NULL, 41),
(389, 'Guadalupe', '1', NULL, NULL, 68),
(390, 'Guaduas', '1', NULL, NULL, 25),
(391, 'Guaitarilla', '1', NULL, NULL, 52),
(392, 'Gualmatán', '1', NULL, NULL, 52),
(393, 'Guamal', '1', NULL, NULL, 47),
(394, 'Guamal', '1', NULL, NULL, 50),
(395, 'Guamo', '1', NULL, NULL, 73),
(396, 'Guapota', '1', NULL, NULL, 68),
(397, 'Guapí', '1', NULL, NULL, 19),
(398, 'Guaranda', '1', NULL, NULL, 70),
(399, 'Guarne', '1', NULL, NULL, 5),
(400, 'Guasca', '1', NULL, NULL, 25),
(401, 'Guatapé', '1', NULL, NULL, 5),
(402, 'Guataquí', '1', NULL, NULL, 25),
(403, 'Guatavita', '1', NULL, NULL, 25),
(404, 'Guateque', '1', NULL, NULL, 15),
(405, 'Guavatá', '1', NULL, NULL, 68),
(406, 'Guayabal de Siquima', '1', NULL, NULL, 25),
(407, 'Guayabetal', '1', NULL, NULL, 25),
(408, 'Guayatá', '1', NULL, NULL, 15),
(409, 'Guepsa', '1', NULL, NULL, 68),
(410, 'Guicán', '1', NULL, NULL, 15),
(411, 'Gutiérrez', '1', NULL, NULL, 25),
(412, 'Guática', '1', NULL, NULL, 66),
(413, 'Gámbita', '1', NULL, NULL, 68),
(414, 'Gámeza', '1', NULL, NULL, 15),
(415, 'Génova', '1', NULL, NULL, 63),
(416, 'Gómez Plata', '1', NULL, NULL, 5),
(417, 'Hacarí', '1', NULL, NULL, 54),
(418, 'Hatillo de Loba', '1', NULL, NULL, 13),
(419, 'Hato', '1', NULL, NULL, 68),
(420, 'Hato Corozal', '1', NULL, NULL, 85),
(421, 'Hatonuevo', '1', NULL, NULL, 44),
(422, 'Heliconia', '1', NULL, NULL, 5),
(423, 'Herrán', '1', NULL, NULL, 54),
(424, 'Herveo', '1', NULL, NULL, 73),
(425, 'Hispania', '1', NULL, NULL, 5),
(426, 'Hobo', '1', NULL, NULL, 41),
(427, 'Honda', '1', NULL, NULL, 73),
(428, 'Ibagué', '1', NULL, NULL, 73),
(429, 'Icononzo', '1', NULL, NULL, 73),
(430, 'Iles', '1', NULL, NULL, 52),
(431, 'Imúes', '1', NULL, NULL, 52),
(432, 'Inzá', '1', NULL, NULL, 19),
(433, 'Inírida', '1', NULL, NULL, 94),
(434, 'Ipiales', '1', NULL, NULL, 52),
(435, 'Isnos', '1', NULL, NULL, 41),
(436, 'Istmina', '1', NULL, NULL, 27),
(437, 'Itagüí', '1', NULL, NULL, 5),
(438, 'Ituango', '1', NULL, NULL, 5),
(439, 'Izá', '1', NULL, NULL, 15),
(440, 'Jambaló', '1', NULL, NULL, 19),
(441, 'Jamundí', '1', NULL, NULL, 76),
(442, 'Jardín', '1', NULL, NULL, 5),
(443, 'Jenesano', '1', NULL, NULL, 15),
(444, 'Jericó', '1', NULL, NULL, 5),
(445, 'Jericó', '1', NULL, NULL, 15),
(446, 'Jerusalén', '1', NULL, NULL, 25),
(447, 'Jesús María', '1', NULL, NULL, 68),
(448, 'Jordán', '1', NULL, NULL, 68),
(449, 'Juan de Acosta', '1', NULL, NULL, 8),
(450, 'Junín', '1', NULL, NULL, 25),
(451, 'Juradó', '1', NULL, NULL, 27),
(452, 'La Apartada y La Frontera', '1', NULL, NULL, 23),
(453, 'La Argentina', '1', NULL, NULL, 41),
(454, 'La Belleza', '1', NULL, NULL, 68),
(455, 'La Calera', '1', NULL, NULL, 25),
(456, 'La Capilla', '1', NULL, NULL, 15),
(457, 'La Ceja', '1', NULL, NULL, 5),
(458, 'La Celia', '1', NULL, NULL, 66),
(459, 'La Cruz', '1', NULL, NULL, 52),
(460, 'La Cumbre', '1', NULL, NULL, 76),
(461, 'La Dorada', '1', NULL, NULL, 17),
(462, 'La Esperanza', '1', NULL, NULL, 54),
(463, 'La Estrella', '1', NULL, NULL, 5),
(464, 'La Florida', '1', NULL, NULL, 52),
(465, 'La Gloria', '1', NULL, NULL, 20),
(466, 'La Jagua de Ibirico', '1', NULL, NULL, 20),
(467, 'La Jagua del Pilar', '1', NULL, NULL, 44),
(468, 'La Llanada', '1', NULL, NULL, 52),
(469, 'La Macarena', '1', NULL, NULL, 50),
(470, 'La Merced', '1', NULL, NULL, 17),
(471, 'La Mesa', '1', NULL, NULL, 25),
(472, 'La Montañita', '1', NULL, NULL, 18),
(473, 'La Palma', '1', NULL, NULL, 25),
(474, 'La Paz', '1', NULL, NULL, 68),
(475, 'La Paz (Robles)', '1', NULL, NULL, 20),
(476, 'La Peña', '1', NULL, NULL, 25),
(477, 'La Pintada', '1', NULL, NULL, 5),
(478, 'La Plata', '1', NULL, NULL, 41),
(479, 'La Playa', '1', NULL, NULL, 54),
(480, 'La Primavera', '1', NULL, NULL, 99),
(481, 'La Salina', '1', NULL, NULL, 85),
(482, 'La Sierra', '1', NULL, NULL, 19),
(483, 'La Tebaida', '1', NULL, NULL, 63),
(484, 'La Tola', '1', NULL, NULL, 52),
(485, 'La Unión', '1', NULL, NULL, 5),
(486, 'La Unión', '1', NULL, NULL, 52),
(487, 'La Unión', '1', NULL, NULL, 70),
(488, 'La Unión', '1', NULL, NULL, 76),
(489, 'La Uvita', '1', NULL, NULL, 15),
(490, 'La Vega', '1', NULL, NULL, 19),
(491, 'La Vega', '1', NULL, NULL, 25),
(492, 'La Victoria', '1', NULL, NULL, 15),
(493, 'La Victoria', '1', NULL, NULL, 17),
(494, 'La Victoria', '1', NULL, NULL, 76),
(495, 'La Virginia', '1', NULL, NULL, 66),
(496, 'Labateca', '1', NULL, NULL, 54),
(497, 'Labranzagrande', '1', NULL, NULL, 15),
(498, 'Landázuri', '1', NULL, NULL, 68),
(499, 'Lebrija', '1', NULL, NULL, 68),
(500, 'Leiva', '1', NULL, NULL, 52),
(501, 'Lejanías', '1', NULL, NULL, 50),
(502, 'Lenguazaque', '1', NULL, NULL, 25),
(503, 'Leticia', '1', NULL, NULL, 91),
(504, 'Liborina', '1', NULL, NULL, 5),
(505, 'Linares', '1', NULL, NULL, 52),
(506, 'Lloró', '1', NULL, NULL, 27),
(507, 'Lorica', '1', NULL, NULL, 23),
(508, 'Los Córdobas', '1', NULL, NULL, 23),
(509, 'Los Palmitos', '1', NULL, NULL, 70),
(510, 'Los Patios', '1', NULL, NULL, 54),
(511, 'Los Santos', '1', NULL, NULL, 68),
(512, 'Lourdes', '1', NULL, NULL, 54),
(513, 'Luruaco', '1', NULL, NULL, 8),
(514, 'Lérida', '1', NULL, NULL, 73),
(515, 'Líbano', '1', NULL, NULL, 73),
(516, 'López (Micay)', '1', NULL, NULL, 19),
(517, 'Macanal', '1', NULL, NULL, 15),
(518, 'Macaravita', '1', NULL, NULL, 68),
(519, 'Maceo', '1', NULL, NULL, 5),
(520, 'Machetá', '1', NULL, NULL, 25),
(521, 'Madrid', '1', NULL, NULL, 25),
(522, 'Magangué', '1', NULL, NULL, 13),
(523, 'Magüi (Payán)', '1', NULL, NULL, 52),
(524, 'Mahates', '1', NULL, NULL, 13),
(525, 'Maicao', '1', NULL, NULL, 44),
(526, 'Majagual', '1', NULL, NULL, 70),
(527, 'Malambo', '1', NULL, NULL, 8),
(528, 'Mallama (Piedrancha)', '1', NULL, NULL, 52),
(529, 'Manatí', '1', NULL, NULL, 8),
(530, 'Manaure', '1', NULL, NULL, 44),
(531, 'Manaure Balcón del Cesar', '1', NULL, NULL, 20),
(532, 'Manizales', '1', NULL, NULL, 17),
(533, 'Manta', '1', NULL, NULL, 25),
(534, 'Manzanares', '1', NULL, NULL, 17),
(535, 'Maní', '1', NULL, NULL, 85),
(536, 'Mapiripan', '1', NULL, NULL, 50),
(537, 'Margarita', '1', NULL, NULL, 13),
(538, 'Marinilla', '1', NULL, NULL, 5),
(539, 'Maripí', '1', NULL, NULL, 15),
(540, 'Mariquita', '1', NULL, NULL, 73),
(541, 'Marmato', '1', NULL, NULL, 17),
(542, 'Marquetalia', '1', NULL, NULL, 17),
(543, 'Marsella', '1', NULL, NULL, 66),
(544, 'Marulanda', '1', NULL, NULL, 17),
(545, 'María la Baja', '1', NULL, NULL, 13),
(546, 'Matanza', '1', NULL, NULL, 68),
(547, 'Medellín', '1', NULL, NULL, 5),
(548, 'Medina', '1', NULL, NULL, 25),
(549, 'Medio Atrato', '1', NULL, NULL, 27),
(550, 'Medio Baudó', '1', NULL, NULL, 27),
(551, 'Medio San Juan (ANDAGOYA)', '1', NULL, NULL, 27),
(552, 'Melgar', '1', NULL, NULL, 73),
(553, 'Mercaderes', '1', NULL, NULL, 19),
(554, 'Mesetas', '1', NULL, NULL, 50),
(555, 'Milán', '1', NULL, NULL, 18),
(556, 'Miraflores', '1', NULL, NULL, 15),
(557, 'Miraflores', '1', NULL, NULL, 95),
(558, 'Miranda', '1', NULL, NULL, 19),
(559, 'Mistrató', '1', NULL, NULL, 66),
(560, 'Mitú', '1', NULL, NULL, 97),
(561, 'Mocoa', '1', NULL, NULL, 86),
(562, 'Mogotes', '1', NULL, NULL, 68),
(563, 'Molagavita', '1', NULL, NULL, 68),
(564, 'Momil', '1', NULL, NULL, 23),
(565, 'Mompós', '1', NULL, NULL, 13),
(566, 'Mongua', '1', NULL, NULL, 15),
(567, 'Monguí', '1', NULL, NULL, 15),
(568, 'Moniquirá', '1', NULL, NULL, 15),
(569, 'Montebello', '1', NULL, NULL, 5),
(570, 'Montecristo', '1', NULL, NULL, 13),
(571, 'Montelíbano', '1', NULL, NULL, 23),
(572, 'Montenegro', '1', NULL, NULL, 63),
(573, 'Monteria', '1', NULL, NULL, 23),
(574, 'Monterrey', '1', NULL, NULL, 85),
(575, 'Morales', '1', NULL, NULL, 13),
(576, 'Morales', '1', NULL, NULL, 19),
(577, 'Morelia', '1', NULL, NULL, 18),
(578, 'Morroa', '1', NULL, NULL, 70),
(579, 'Mosquera', '1', NULL, NULL, 25),
(580, 'Mosquera', '1', NULL, NULL, 52),
(581, 'Motavita', '1', NULL, NULL, 15),
(582, 'Moñitos', '1', NULL, NULL, 23),
(583, 'Murillo', '1', NULL, NULL, 73),
(584, 'Murindó', '1', NULL, NULL, 5),
(585, 'Mutatá', '1', NULL, NULL, 5),
(586, 'Mutiscua', '1', NULL, NULL, 54),
(587, 'Muzo', '1', NULL, NULL, 15),
(588, 'Málaga', '1', NULL, NULL, 68),
(589, 'Nariño', '1', NULL, NULL, 5),
(590, 'Nariño', '1', NULL, NULL, 25),
(591, 'Nariño', '1', NULL, NULL, 52),
(592, 'Natagaima', '1', NULL, NULL, 73),
(593, 'Nechí', '1', NULL, NULL, 5),
(594, 'Necoclí', '1', NULL, NULL, 5),
(595, 'Neira', '1', NULL, NULL, 17),
(596, 'Neiva', '1', NULL, NULL, 41),
(597, 'Nemocón', '1', NULL, NULL, 25),
(598, 'Nilo', '1', NULL, NULL, 25),
(599, 'Nimaima', '1', NULL, NULL, 25),
(600, 'Nobsa', '1', NULL, NULL, 15),
(601, 'Nocaima', '1', NULL, NULL, 25),
(602, 'Norcasia', '1', NULL, NULL, 17),
(603, 'Norosí', '1', NULL, NULL, 13),
(604, 'Novita', '1', NULL, NULL, 27),
(605, 'Nueva Granada', '1', NULL, NULL, 47),
(606, 'Nuevo Colón', '1', NULL, NULL, 15),
(607, 'Nunchía', '1', NULL, NULL, 85),
(608, 'Nuquí', '1', NULL, NULL, 27),
(609, 'Nátaga', '1', NULL, NULL, 41),
(610, 'Obando', '1', NULL, NULL, 76),
(611, 'Ocamonte', '1', NULL, NULL, 68),
(612, 'Ocaña', '1', NULL, NULL, 54),
(613, 'Oiba', '1', NULL, NULL, 68),
(614, 'Oicatá', '1', NULL, NULL, 15),
(615, 'Olaya', '1', NULL, NULL, 5),
(616, 'Olaya Herrera', '1', NULL, NULL, 52),
(617, 'Onzaga', '1', NULL, NULL, 68),
(618, 'Oporapa', '1', NULL, NULL, 41),
(619, 'Orito', '1', NULL, NULL, 86),
(620, 'Orocué', '1', NULL, NULL, 85),
(621, 'Ortega', '1', NULL, NULL, 73),
(622, 'Ospina', '1', NULL, NULL, 52),
(623, 'Otanche', '1', NULL, NULL, 15),
(624, 'Ovejas', '1', NULL, NULL, 70),
(625, 'Pachavita', '1', NULL, NULL, 15),
(626, 'Pacho', '1', NULL, NULL, 25),
(627, 'Padilla', '1', NULL, NULL, 19),
(628, 'Paicol', '1', NULL, NULL, 41),
(629, 'Pailitas', '1', NULL, NULL, 20),
(630, 'Paime', '1', NULL, NULL, 25),
(631, 'Paipa', '1', NULL, NULL, 15),
(632, 'Pajarito', '1', NULL, NULL, 15),
(633, 'Palermo', '1', NULL, NULL, 41),
(634, 'Palestina', '1', NULL, NULL, 17),
(635, 'Palestina', '1', NULL, NULL, 41),
(636, 'Palmar', '1', NULL, NULL, 68),
(637, 'Palmar de Varela', '1', NULL, NULL, 8),
(638, 'Palmas del Socorro', '1', NULL, NULL, 68),
(639, 'Palmira', '1', NULL, NULL, 76),
(640, 'Palmito', '1', NULL, NULL, 70),
(641, 'Palocabildo', '1', NULL, NULL, 73),
(642, 'Pamplona', '1', NULL, NULL, 54),
(643, 'Pamplonita', '1', NULL, NULL, 54),
(644, 'Pandi', '1', NULL, NULL, 25),
(645, 'Panqueba', '1', NULL, NULL, 15),
(646, 'Paratebueno', '1', NULL, NULL, 25),
(647, 'Pasca', '1', NULL, NULL, 25),
(648, 'Patía (El Bordo)', '1', NULL, NULL, 19),
(649, 'Pauna', '1', NULL, NULL, 15),
(650, 'Paya', '1', NULL, NULL, 15),
(651, 'Paz de Ariporo', '1', NULL, NULL, 85),
(652, 'Paz de Río', '1', NULL, NULL, 15),
(653, 'Pedraza', '1', NULL, NULL, 47),
(654, 'Pelaya', '1', NULL, NULL, 20),
(655, 'Pensilvania', '1', NULL, NULL, 17),
(656, 'Peque', '1', NULL, NULL, 5),
(657, 'Pereira', '1', NULL, NULL, 66),
(658, 'Pesca', '1', NULL, NULL, 15),
(659, 'Peñol', '1', NULL, NULL, 5),
(660, 'Piamonte', '1', NULL, NULL, 19),
(661, 'Pie de Cuesta', '1', NULL, NULL, 68),
(662, 'Piedras', '1', NULL, NULL, 73),
(663, 'Piendamó', '1', NULL, NULL, 19),
(664, 'Pijao', '1', NULL, NULL, 63),
(665, 'Pijiño', '1', NULL, NULL, 47),
(666, 'Pinchote', '1', NULL, NULL, 68),
(667, 'Pinillos', '1', NULL, NULL, 13),
(668, 'Piojo', '1', NULL, NULL, 8),
(669, 'Pisva', '1', NULL, NULL, 15),
(670, 'Pital', '1', NULL, NULL, 41),
(671, 'Pitalito', '1', NULL, NULL, 41),
(672, 'Pivijay', '1', NULL, NULL, 47),
(673, 'Planadas', '1', NULL, NULL, 73),
(674, 'Planeta Rica', '1', NULL, NULL, 23),
(675, 'Plato', '1', NULL, NULL, 47),
(676, 'Policarpa', '1', NULL, NULL, 52),
(677, 'Polonuevo', '1', NULL, NULL, 8),
(678, 'Ponedera', '1', NULL, NULL, 8),
(679, 'Popayán', '1', NULL, NULL, 19),
(680, 'Pore', '1', NULL, NULL, 85),
(681, 'Potosí', '1', NULL, NULL, 52),
(682, 'Pradera', '1', NULL, NULL, 76),
(683, 'Prado', '1', NULL, NULL, 73),
(684, 'Providencia', '1', NULL, NULL, 52),
(685, 'Providencia', '1', NULL, NULL, 88),
(686, 'Pueblo Bello', '1', NULL, NULL, 20),
(687, 'Pueblo Nuevo', '1', NULL, NULL, 23),
(688, 'Pueblo Rico', '1', NULL, NULL, 66),
(689, 'Pueblorrico', '1', NULL, NULL, 5),
(690, 'Puebloviejo', '1', NULL, NULL, 47),
(691, 'Puente Nacional', '1', NULL, NULL, 68),
(692, 'Puerres', '1', NULL, NULL, 52),
(693, 'Puerto Asís', '1', NULL, NULL, 86),
(694, 'Puerto Berrío', '1', NULL, NULL, 5),
(695, 'Puerto Boyacá', '1', NULL, NULL, 15),
(696, 'Puerto Caicedo', '1', NULL, NULL, 86),
(697, 'Puerto Carreño', '1', NULL, NULL, 99),
(698, 'Puerto Colombia', '1', NULL, NULL, 8),
(699, 'Puerto Concordia', '1', NULL, NULL, 50),
(700, 'Puerto Escondido', '1', NULL, NULL, 23),
(701, 'Puerto Gaitán', '1', NULL, NULL, 50),
(702, 'Puerto Guzmán', '1', NULL, NULL, 86),
(703, 'Puerto Leguízamo', '1', NULL, NULL, 86),
(704, 'Puerto Libertador', '1', NULL, NULL, 23),
(705, 'Puerto Lleras', '1', NULL, NULL, 50),
(706, 'Puerto López', '1', NULL, NULL, 50),
(707, 'Puerto Nare', '1', NULL, NULL, 5),
(708, 'Puerto Nariño', '1', NULL, NULL, 91),
(709, 'Puerto Parra', '1', NULL, NULL, 68),
(710, 'Puerto Rico', '1', NULL, NULL, 18),
(711, 'Puerto Rico', '1', NULL, NULL, 50),
(712, 'Puerto Rondón', '1', NULL, NULL, 81),
(713, 'Puerto Salgar', '1', NULL, NULL, 25),
(714, 'Puerto Santander', '1', NULL, NULL, 54),
(715, 'Puerto Tejada', '1', NULL, NULL, 19),
(716, 'Puerto Triunfo', '1', NULL, NULL, 5),
(717, 'Puerto Wilches', '1', NULL, NULL, 68),
(718, 'Pulí', '1', NULL, NULL, 25),
(719, 'Pupiales', '1', NULL, NULL, 52),
(720, 'Puracé (Coconuco)', '1', NULL, NULL, 19),
(721, 'Purificación', '1', NULL, NULL, 73),
(722, 'Purísima', '1', NULL, NULL, 23),
(723, 'Pácora', '1', NULL, NULL, 17),
(724, 'Páez', '1', NULL, NULL, 15),
(725, 'Páez (Belalcazar)', '1', NULL, NULL, 19),
(726, 'Páramo', '1', NULL, NULL, 68),
(727, 'Quebradanegra', '1', NULL, NULL, 25),
(728, 'Quetame', '1', NULL, NULL, 25),
(729, 'Quibdó', '1', NULL, NULL, 27),
(730, 'Quimbaya', '1', NULL, NULL, 63),
(731, 'Quinchía', '1', NULL, NULL, 66),
(732, 'Quipama', '1', NULL, NULL, 15),
(733, 'Quipile', '1', NULL, NULL, 25),
(734, 'Ragonvalia', '1', NULL, NULL, 54),
(735, 'Ramiriquí', '1', NULL, NULL, 15),
(736, 'Recetor', '1', NULL, NULL, 85),
(737, 'Regidor', '1', NULL, NULL, 13),
(738, 'Remedios', '1', NULL, NULL, 5),
(739, 'Remolino', '1', NULL, NULL, 47),
(740, 'Repelón', '1', NULL, NULL, 8),
(741, 'Restrepo', '1', NULL, NULL, 50),
(742, 'Restrepo', '1', NULL, NULL, 76),
(743, 'Retiro', '1', NULL, NULL, 5),
(744, 'Ricaurte', '1', NULL, NULL, 25),
(745, 'Ricaurte', '1', NULL, NULL, 52),
(746, 'Rio Negro', '1', NULL, NULL, 68),
(747, 'Rioblanco', '1', NULL, NULL, 73),
(748, 'Riofrío', '1', NULL, NULL, 76),
(749, 'Riohacha', '1', NULL, NULL, 44),
(750, 'Risaralda', '1', NULL, NULL, 17),
(751, 'Rivera', '1', NULL, NULL, 41),
(752, 'Roberto Payán (San José)', '1', NULL, NULL, 52),
(753, 'Roldanillo', '1', NULL, NULL, 76),
(754, 'Roncesvalles', '1', NULL, NULL, 73),
(755, 'Rondón', '1', NULL, NULL, 15),
(756, 'Rosas', '1', NULL, NULL, 19),
(757, 'Rovira', '1', NULL, NULL, 73),
(758, 'Ráquira', '1', NULL, NULL, 15),
(759, 'Río Iró', '1', NULL, NULL, 27),
(760, 'Río Quito', '1', NULL, NULL, 27),
(761, 'Río Sucio', '1', NULL, NULL, 17),
(762, 'Río Viejo', '1', NULL, NULL, 13),
(763, 'Río de oro', '1', NULL, NULL, 20),
(764, 'Ríonegro', '1', NULL, NULL, 5),
(765, 'Ríosucio', '1', NULL, NULL, 27),
(766, 'Sabana de Torres', '1', NULL, NULL, 68),
(767, 'Sabanagrande', '1', NULL, NULL, 8),
(768, 'Sabanalarga', '1', NULL, NULL, 5),
(769, 'Sabanalarga', '1', NULL, NULL, 8),
(770, 'Sabanalarga', '1', NULL, NULL, 85),
(771, 'Sabanas de San Angel (SAN ANGEL)', '1', NULL, NULL, 47),
(772, 'Sabaneta', '1', NULL, NULL, 5),
(773, 'Saboyá', '1', NULL, NULL, 15),
(774, 'Sahagún', '1', NULL, NULL, 23),
(775, 'Saladoblanco', '1', NULL, NULL, 41),
(776, 'Salamina', '1', NULL, NULL, 17),
(777, 'Salamina', '1', NULL, NULL, 47),
(778, 'Salazar', '1', NULL, NULL, 54),
(779, 'Saldaña', '1', NULL, NULL, 73),
(780, 'Salento', '1', NULL, NULL, 63),
(781, 'Salgar', '1', NULL, NULL, 5),
(782, 'Samacá', '1', NULL, NULL, 15),
(783, 'Samaniego', '1', NULL, NULL, 52),
(784, 'Samaná', '1', NULL, NULL, 17),
(785, 'Sampués', '1', NULL, NULL, 70),
(786, 'San Agustín', '1', NULL, NULL, 41),
(787, 'San Alberto', '1', NULL, NULL, 20),
(788, 'San Andrés', '1', NULL, NULL, 68),
(789, 'San Andrés Sotavento', '1', NULL, NULL, 23),
(790, 'San Andrés de Cuerquía', '1', NULL, NULL, 5),
(791, 'San Antero', '1', NULL, NULL, 23),
(792, 'San Antonio', '1', NULL, NULL, 73),
(793, 'San Antonio de Tequendama', '1', NULL, NULL, 25),
(794, 'San Benito', '1', NULL, NULL, 68),
(795, 'San Benito Abad', '1', NULL, NULL, 70),
(796, 'San Bernardo', '1', NULL, NULL, 25),
(797, 'San Bernardo', '1', NULL, NULL, 52),
(798, 'San Bernardo del Viento', '1', NULL, NULL, 23),
(799, 'San Calixto', '1', NULL, NULL, 54),
(800, 'San Carlos', '1', NULL, NULL, 5),
(801, 'San Carlos', '1', NULL, NULL, 23),
(802, 'San Carlos de Guaroa', '1', NULL, NULL, 50),
(803, 'San Cayetano', '1', NULL, NULL, 25),
(804, 'San Cayetano', '1', NULL, NULL, 54),
(805, 'San Cristobal', '1', NULL, NULL, 13),
(806, 'San Diego', '1', NULL, NULL, 20),
(807, 'San Eduardo', '1', NULL, NULL, 15),
(808, 'San Estanislao', '1', NULL, NULL, 13),
(809, 'San Fernando', '1', NULL, NULL, 13),
(810, 'San Francisco', '1', NULL, NULL, 5),
(811, 'San Francisco', '1', NULL, NULL, 25),
(812, 'San Francisco', '1', NULL, NULL, 86),
(813, 'San Gíl', '1', NULL, NULL, 68),
(814, 'San Jacinto', '1', NULL, NULL, 13),
(815, 'San Jacinto del Cauca', '1', NULL, NULL, 13),
(816, 'San Jerónimo', '1', NULL, NULL, 5),
(817, 'San Joaquín', '1', NULL, NULL, 68),
(818, 'San José', '1', NULL, NULL, 17),
(819, 'San José de Miranda', '1', NULL, NULL, 68),
(820, 'San José de Montaña', '1', NULL, NULL, 5),
(821, 'San José de Pare', '1', NULL, NULL, 15),
(822, 'San José de Uré', '1', NULL, NULL, 23),
(823, 'San José del Fragua', '1', NULL, NULL, 18),
(824, 'San José del Guaviare', '1', NULL, NULL, 95),
(825, 'San José del Palmar', '1', NULL, NULL, 27),
(826, 'San Juan de Arama', '1', NULL, NULL, 50),
(827, 'San Juan de Betulia', '1', NULL, NULL, 70),
(828, 'San Juan de Nepomuceno', '1', NULL, NULL, 13),
(829, 'San Juan de Pasto', '1', NULL, NULL, 52),
(830, 'San Juan de Río Seco', '1', NULL, NULL, 25),
(831, 'San Juan de Urabá', '1', NULL, NULL, 5),
(832, 'San Juan del Cesar', '1', NULL, NULL, 44),
(833, 'San Juanito', '1', NULL, NULL, 50),
(834, 'San Lorenzo', '1', NULL, NULL, 52),
(835, 'San Luis', '1', NULL, NULL, 73),
(836, 'San Luís', '1', NULL, NULL, 5),
(837, 'San Luís de Gaceno', '1', NULL, NULL, 15),
(838, 'San Luís de Palenque', '1', NULL, NULL, 85),
(839, 'San Marcos', '1', NULL, NULL, 70),
(840, 'San Martín', '1', NULL, NULL, 20),
(841, 'San Martín', '1', NULL, NULL, 50),
(842, 'San Martín de Loba', '1', NULL, NULL, 13),
(843, 'San Mateo', '1', NULL, NULL, 15),
(844, 'San Miguel', '1', NULL, NULL, 68),
(845, 'San Miguel', '1', NULL, NULL, 86),
(846, 'San Miguel de Sema', '1', NULL, NULL, 15),
(847, 'San Onofre', '1', NULL, NULL, 70),
(848, 'San Pablo', '1', NULL, NULL, 13),
(849, 'San Pablo', '1', NULL, NULL, 52),
(850, 'San Pablo de Borbur', '1', NULL, NULL, 15),
(851, 'San Pedro', '1', NULL, NULL, 5),
(852, 'San Pedro', '1', NULL, NULL, 70),
(853, 'San Pedro', '1', NULL, NULL, 76),
(854, 'San Pedro de Cartago', '1', NULL, NULL, 52),
(855, 'San Pedro de Urabá', '1', NULL, NULL, 5),
(856, 'San Pelayo', '1', NULL, NULL, 23),
(857, 'San Rafael', '1', NULL, NULL, 5),
(858, 'San Roque', '1', NULL, NULL, 5),
(859, 'San Sebastián', '1', NULL, NULL, 19),
(860, 'San Sebastián de Buenavista', '1', NULL, NULL, 47),
(861, 'San Vicente', '1', NULL, NULL, 5),
(862, 'San Vicente del Caguán', '1', NULL, NULL, 18),
(863, 'San Vicente del Chucurí', '1', NULL, NULL, 68),
(864, 'San Zenón', '1', NULL, NULL, 47),
(865, 'Sandoná', '1', NULL, NULL, 52),
(866, 'Santa Ana', '1', NULL, NULL, 47),
(867, 'Santa Bárbara', '1', NULL, NULL, 5),
(868, 'Santa Bárbara', '1', NULL, NULL, 68),
(869, 'Santa Bárbara (Iscuandé)', '1', NULL, NULL, 52),
(870, 'Santa Bárbara de Pinto', '1', NULL, NULL, 47),
(871, 'Santa Catalina', '1', NULL, NULL, 13),
(872, 'Santa Fé de Antioquia', '1', NULL, NULL, 5),
(873, 'Santa Genoveva de Docorodó', '1', NULL, NULL, 27),
(874, 'Santa Helena del Opón', '1', NULL, NULL, 68),
(875, 'Santa Isabel', '1', NULL, NULL, 73),
(876, 'Santa Lucía', '1', NULL, NULL, 8),
(877, 'Santa Marta', '1', NULL, NULL, 47),
(878, 'Santa María', '1', NULL, NULL, 15),
(879, 'Santa María', '1', NULL, NULL, 41),
(880, 'Santa Rosa', '1', NULL, NULL, 13),
(881, 'Santa Rosa', '1', NULL, NULL, 19),
(882, 'Santa Rosa de Cabal', '1', NULL, NULL, 66),
(883, 'Santa Rosa de Osos', '1', NULL, NULL, 5),
(884, 'Santa Rosa de Viterbo', '1', NULL, NULL, 15),
(885, 'Santa Rosa del Sur', '1', NULL, NULL, 13),
(886, 'Santa Rosalía', '1', NULL, NULL, 99),
(887, 'Santa Sofía', '1', NULL, NULL, 15),
(888, 'Santana', '1', NULL, NULL, 15),
(889, 'Santander de Quilichao', '1', NULL, NULL, 19),
(890, 'Santiago', '1', NULL, NULL, 54),
(891, 'Santiago', '1', NULL, NULL, 86),
(892, 'Santo Domingo', '1', NULL, NULL, 5),
(893, 'Santo Tomás', '1', NULL, NULL, 8),
(894, 'Santuario', '1', NULL, NULL, 5),
(895, 'Santuario', '1', NULL, NULL, 66),
(896, 'Sapuyes', '1', NULL, NULL, 52),
(897, 'Saravena', '1', NULL, NULL, 81),
(898, 'Sardinata', '1', NULL, NULL, 54),
(899, 'Sasaima', '1', NULL, NULL, 25),
(900, 'Sativanorte', '1', NULL, NULL, 15),
(901, 'Sativasur', '1', NULL, NULL, 15),
(902, 'Segovia', '1', NULL, NULL, 5),
(903, 'Sesquilé', '1', NULL, NULL, 25),
(904, 'Sevilla', '1', NULL, NULL, 76),
(905, 'Siachoque', '1', NULL, NULL, 15),
(906, 'Sibaté', '1', NULL, NULL, 25),
(907, 'Sibundoy', '1', NULL, NULL, 86),
(908, 'Silos', '1', NULL, NULL, 54),
(909, 'Silvania', '1', NULL, NULL, 25),
(910, 'Silvia', '1', NULL, NULL, 19),
(911, 'Simacota', '1', NULL, NULL, 68),
(912, 'Simijaca', '1', NULL, NULL, 25),
(913, 'Simití', '1', NULL, NULL, 13),
(914, 'Sincelejo', '1', NULL, NULL, 70),
(915, 'Sincé', '1', NULL, NULL, 70),
(916, 'Sipí', '1', NULL, NULL, 27),
(917, 'Sitionuevo', '1', NULL, NULL, 47),
(918, 'Soacha', '1', NULL, NULL, 25),
(919, 'Soatá', '1', NULL, NULL, 15),
(920, 'Socha', '1', NULL, NULL, 15),
(921, 'Socorro', '1', NULL, NULL, 68),
(922, 'Socotá', '1', NULL, NULL, 15),
(923, 'Sogamoso', '1', NULL, NULL, 15),
(924, 'Solano', '1', NULL, NULL, 18),
(925, 'Soledad', '1', NULL, NULL, 8),
(926, 'Solita', '1', NULL, NULL, 18),
(927, 'Somondoco', '1', NULL, NULL, 15),
(928, 'Sonsón', '1', NULL, NULL, 5),
(929, 'Sopetrán', '1', NULL, NULL, 5),
(930, 'Soplaviento', '1', NULL, NULL, 13),
(931, 'Sopó', '1', NULL, NULL, 25),
(932, 'Sora', '1', NULL, NULL, 15),
(933, 'Soracá', '1', NULL, NULL, 15),
(934, 'Sotaquirá', '1', NULL, NULL, 15),
(935, 'Sotara (Paispamba)', '1', NULL, NULL, 19),
(936, 'Sotomayor (Los Andes)', '1', NULL, NULL, 52),
(937, 'Suaita', '1', NULL, NULL, 68),
(938, 'Suan', '1', NULL, NULL, 8),
(939, 'Suaza', '1', NULL, NULL, 41),
(940, 'Subachoque', '1', NULL, NULL, 25),
(941, 'Sucre', '1', NULL, NULL, 19),
(942, 'Sucre', '1', NULL, NULL, 68),
(943, 'Sucre', '1', NULL, NULL, 70),
(944, 'Suesca', '1', NULL, NULL, 25),
(945, 'Supatá', '1', NULL, NULL, 25),
(946, 'Supía', '1', NULL, NULL, 17),
(947, 'Suratá', '1', NULL, NULL, 68),
(948, 'Susa', '1', NULL, NULL, 25),
(949, 'Susacón', '1', NULL, NULL, 15),
(950, 'Sutamarchán', '1', NULL, NULL, 15),
(951, 'Sutatausa', '1', NULL, NULL, 25),
(952, 'Sutatenza', '1', NULL, NULL, 15),
(953, 'Suárez', '1', NULL, NULL, 19),
(954, 'Suárez', '1', NULL, NULL, 73),
(955, 'Sácama', '1', NULL, NULL, 85),
(956, 'Sáchica', '1', NULL, NULL, 15),
(957, 'Tabio', '1', NULL, NULL, 25),
(958, 'Tadó', '1', NULL, NULL, 27),
(959, 'Talaigua Nuevo', '1', NULL, NULL, 13),
(960, 'Tamalameque', '1', NULL, NULL, 20),
(961, 'Tame', '1', NULL, NULL, 81),
(962, 'Taminango', '1', NULL, NULL, 52),
(963, 'Tangua', '1', NULL, NULL, 52),
(964, 'Taraira', '1', NULL, NULL, 97),
(965, 'Tarazá', '1', NULL, NULL, 5),
(966, 'Tarqui', '1', NULL, NULL, 41),
(967, 'Tarso', '1', NULL, NULL, 5),
(968, 'Tasco', '1', NULL, NULL, 15),
(969, 'Tauramena', '1', NULL, NULL, 85),
(970, 'Tausa', '1', NULL, NULL, 25),
(971, 'Tello', '1', NULL, NULL, 41),
(972, 'Tena', '1', NULL, NULL, 25),
(973, 'Tenerife', '1', NULL, NULL, 47),
(974, 'Tenjo', '1', NULL, NULL, 25),
(975, 'Tenza', '1', NULL, NULL, 15),
(976, 'Teorama', '1', NULL, NULL, 54),
(977, 'Teruel', '1', NULL, NULL, 41),
(978, 'Tesalia', '1', NULL, NULL, 41),
(979, 'Tibacuy', '1', NULL, NULL, 25),
(980, 'Tibaná', '1', NULL, NULL, 15),
(981, 'Tibasosa', '1', NULL, NULL, 15),
(982, 'Tibirita', '1', NULL, NULL, 25),
(983, 'Tibú', '1', NULL, NULL, 54),
(984, 'Tierralta', '1', NULL, NULL, 23),
(985, 'Timaná', '1', NULL, NULL, 41),
(986, 'Timbiquí', '1', NULL, NULL, 19),
(987, 'Timbío', '1', NULL, NULL, 19),
(988, 'Tinjacá', '1', NULL, NULL, 15),
(989, 'Tipacoque', '1', NULL, NULL, 15),
(990, 'Tiquisio (Puerto Rico)', '1', NULL, NULL, 13),
(991, 'Titiribí', '1', NULL, NULL, 5),
(992, 'Toca', '1', NULL, NULL, 15),
(993, 'Tocaima', '1', NULL, NULL, 25),
(994, 'Tocancipá', '1', NULL, NULL, 25),
(995, 'Toguí', '1', NULL, NULL, 15),
(996, 'Toledo', '1', NULL, NULL, 5),
(997, 'Toledo', '1', NULL, NULL, 54),
(998, 'Tolú', '1', NULL, NULL, 70),
(999, 'Tolú Viejo', '1', NULL, NULL, 70),
(1000, 'Tona', '1', NULL, NULL, 68),
(1001, 'Topagá', '1', NULL, NULL, 15),
(1002, 'Topaipí', '1', NULL, NULL, 25),
(1003, 'Toribío', '1', NULL, NULL, 19),
(1004, 'Toro', '1', NULL, NULL, 76),
(1005, 'Tota', '1', NULL, NULL, 15),
(1006, 'Totoró', '1', NULL, NULL, 19),
(1007, 'Trinidad', '1', NULL, NULL, 85),
(1008, 'Trujillo', '1', NULL, NULL, 76),
(1009, 'Tubará', '1', NULL, NULL, 8),
(1010, 'Tuchín', '1', NULL, NULL, 23),
(1011, 'Tulúa', '1', NULL, NULL, 76),
(1012, 'Tumaco', '1', NULL, NULL, 52),
(1013, 'Tunja', '1', NULL, NULL, 15),
(1014, 'Tunungua', '1', NULL, NULL, 15),
(1015, 'Turbaco', '1', NULL, NULL, 13),
(1016, 'Turbaná', '1', NULL, NULL, 13),
(1017, 'Turbo', '1', NULL, NULL, 5),
(1018, 'Turmequé', '1', NULL, NULL, 15),
(1019, 'Tuta', '1', NULL, NULL, 15),
(1020, 'Tutasá', '1', NULL, NULL, 15),
(1021, 'Támara', '1', NULL, NULL, 85),
(1022, 'Támesis', '1', NULL, NULL, 5),
(1023, 'Túquerres', '1', NULL, NULL, 52),
(1024, 'Ubalá', '1', NULL, NULL, 25),
(1025, 'Ubaque', '1', NULL, NULL, 25),
(1026, 'Ubaté', '1', NULL, NULL, 25),
(1027, 'Ulloa', '1', NULL, NULL, 76),
(1028, 'Une', '1', NULL, NULL, 25),
(1029, 'Unguía', '1', NULL, NULL, 27),
(1030, 'Unión Panamericana (ÁNIMAS)', '1', NULL, NULL, 27),
(1031, 'Uramita', '1', NULL, NULL, 5),
(1032, 'Uribe', '1', NULL, NULL, 50),
(1033, 'Uribia', '1', NULL, NULL, 44),
(1034, 'Urrao', '1', NULL, NULL, 5),
(1035, 'Urumita', '1', NULL, NULL, 44),
(1036, 'Usiacuri', '1', NULL, NULL, 8),
(1037, 'Valdivia', '1', NULL, NULL, 5),
(1038, 'Valencia', '1', NULL, NULL, 23),
(1039, 'Valle de San José', '1', NULL, NULL, 68),
(1040, 'Valle de San Juan', '1', NULL, NULL, 73),
(1041, 'Valle del Guamuez', '1', NULL, NULL, 86),
(1042, 'Valledupar', '1', NULL, NULL, 20),
(1043, 'Valparaiso', '1', NULL, NULL, 5),
(1044, 'Valparaiso', '1', NULL, NULL, 18),
(1045, 'Vegachí', '1', NULL, NULL, 5),
(1046, 'Venadillo', '1', NULL, NULL, 73),
(1047, 'Venecia', '1', NULL, NULL, 5),
(1048, 'Venecia (Ospina Pérez)', '1', NULL, NULL, 25),
(1049, 'Ventaquemada', '1', NULL, NULL, 15),
(1050, 'Vergara', '1', NULL, NULL, 25),
(1051, 'Versalles', '1', NULL, NULL, 76),
(1052, 'Vetas', '1', NULL, NULL, 68),
(1053, 'Viani', '1', NULL, NULL, 25),
(1054, 'Vigía del Fuerte', '1', NULL, NULL, 5),
(1055, 'Vijes', '1', NULL, NULL, 76),
(1056, 'Villa Caro', '1', NULL, NULL, 54),
(1057, 'Villa Rica', '1', NULL, NULL, 19),
(1058, 'Villa de Leiva', '1', NULL, NULL, 15),
(1059, 'Villa del Rosario', '1', NULL, NULL, 54),
(1060, 'Villagarzón', '1', NULL, NULL, 86),
(1061, 'Villagómez', '1', NULL, NULL, 25),
(1062, 'Villahermosa', '1', NULL, NULL, 73),
(1063, 'Villamaría', '1', NULL, NULL, 17),
(1064, 'Villanueva', '1', NULL, NULL, 13),
(1065, 'Villanueva', '1', NULL, NULL, 44),
(1066, 'Villanueva', '1', NULL, NULL, 68),
(1067, 'Villanueva', '1', NULL, NULL, 85),
(1068, 'Villapinzón', '1', NULL, NULL, 25),
(1069, 'Villarrica', '1', NULL, NULL, 73),
(1070, 'Villavicencio', '1', NULL, NULL, 50),
(1071, 'Villavieja', '1', NULL, NULL, 41),
(1072, 'Villeta', '1', NULL, NULL, 25),
(1073, 'Viotá', '1', NULL, NULL, 25),
(1074, 'Viracachá', '1', NULL, NULL, 15),
(1075, 'Vista Hermosa', '1', NULL, NULL, 50),
(1076, 'Viterbo', '1', NULL, NULL, 17),
(1077, 'Vélez', '1', NULL, NULL, 68),
(1078, 'Yacopí', '1', NULL, NULL, 25),
(1079, 'Yacuanquer', '1', NULL, NULL, 52),
(1080, 'Yaguará', '1', NULL, NULL, 41),
(1081, 'Yalí', '1', NULL, NULL, 5),
(1082, 'Yarumal', '1', NULL, NULL, 5),
(1083, 'Yolombó', '1', NULL, NULL, 5),
(1084, 'Yondó (Casabe)', '1', NULL, NULL, 5),
(1085, 'Yopal', '1', NULL, NULL, 85),
(1086, 'Yotoco', '1', NULL, NULL, 76),
(1087, 'Yumbo', '1', NULL, NULL, 76),
(1088, 'Zambrano', '1', NULL, NULL, 13),
(1089, 'Zapatoca', '1', NULL, NULL, 68),
(1090, 'Zapayán (PUNTA DE PIEDRAS)', '1', NULL, NULL, 47),
(1091, 'Zaragoza', '1', NULL, NULL, 5),
(1092, 'Zarzal', '1', NULL, NULL, 76),
(1093, 'Zetaquirá', '1', NULL, NULL, 15),
(1094, 'Zipacón', '1', NULL, NULL, 25),
(1095, 'Zipaquirá', '1', NULL, NULL, 25),
(1096, 'Zona Bananera (PRADO - SEVILLA)', '1', NULL, NULL, 47),
(1097, 'Ábrego', '1', NULL, NULL, 54),
(1098, 'Íquira', '1', NULL, NULL, 41),
(1099, 'Úmbita', '1', NULL, NULL, 15),
(1100, 'Útica', '1', NULL, NULL, 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paises`
--

CREATE TABLE IF NOT EXISTS `paises` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pais` varchar(255) DEFAULT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `paises`
--

INSERT INTO `paises` (`id`, `pais`, `estado`, `created_at`, `updated_at`) VALUES
(1, 'COLOMBIA', '1', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parametros`
--

CREATE TABLE IF NOT EXISTS `parametros` (
  `id` bigint(20) NOT NULL,
  `tp_id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `foreign` bigint(20) DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parametro_tipo_parametro1` (`tp_id`),
  KEY `fk_parametro_parametro1` (`foreign`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `parametros`
--

INSERT INTO `parametros` (`id`, `tp_id`, `nombre`, `foreign`, `estado`, `created_at`, `updated_at`) VALUES
(100, 100, 'Sony ', NULL, '1', '2023-06-28 00:37:53', NULL),
(101, 100, 'ios', NULL, '1', '2023-06-28 00:38:28', NULL),
(150, 101, 'diademas', NULL, '1', '2023-06-28 00:36:15', NULL),
(200, 102, 'azul', NULL, '1', '2023-06-27 22:56:52', NULL),
(201, 102, 'rojo', NULL, '1', '2023-06-28 00:30:51', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE IF NOT EXISTS `personas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `identificacion` varchar(255) NOT NULL,
  `nombres` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `celular` varchar(10) DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `token` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `identificacion`, `nombres`, `apellidos`, `celular`, `estado`, `created_at`, `updated_at`, `token`) VALUES
(1, '1212121212', 'innova', 'tech', '3121212121', '1', '2023-06-27 10:09:18', NULL, 'tnryuaskmdknkhjq');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pqrs`
--

CREATE TABLE IF NOT EXISTS `pqrs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `motivo` varchar(15) NOT NULL,
  `titulo` varchar(15) NOT NULL,
  `texto_pqrst` text NOT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_PQRSD_Usuario1` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE IF NOT EXISTS `productos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(15) NOT NULL,
  `precio_venta` float DEFAULT NULL,
  `iva` float DEFAULT NULL,
  `referencia` varchar(12) NOT NULL,
  `cantidad` int(5) DEFAULT NULL,
  `imagen` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `etiquetas` varchar(50) DEFAULT NULL,
  `calificacion` int(1) DEFAULT NULL,
  `descuento` int(3) DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `param_marca` bigint(20) DEFAULT NULL,
  `param_color` bigint(20) NOT NULL,
  `param_categoria` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Referencia_UNIQUE` (`referencia`),
  KEY `fk_productos_parametro1` (`param_color`),
  KEY `fk_productos_parametro2` (`param_categoria`),
  KEY `param_marca` (`param_marca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE IF NOT EXISTS `proveedores` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL,
  `persona_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Proveedores_Persona1` (`persona_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_parametros`
--

CREATE TABLE IF NOT EXISTS `tipo_parametros` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `rango_min` bigint(20) NOT NULL,
  `rango_max` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `tipo_parametros`
--

INSERT INTO `tipo_parametros` (`id`, `nombre`, `rango_min`, `rango_max`) VALUES
(100, 'marca', 100, 149),
(101, 'categoria', 150, 199),
(102, 'color', 200, 249);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `tipo_user` varchar(1) NOT NULL,
  `suscripcion` varchar(1) DEFAULT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `persona_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Usuario_Persona1` (`persona_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `remember_token`, `tipo_user`, `suscripcion`, `estado`, `created_at`, `updated_at`, `persona_id`) VALUES
(1, 'innova tech', 'innovatech.tech2006@gmail.com', NULL, '$2y$10$IrombPtHdMOEojRDINLfqeFJ8qFTiF5Zowq6kjGciOQZBrX3rttSm', NULL, '2', '1', '1', '2023-06-27 10:09:18', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE IF NOT EXISTS `ventas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metodo_pago` varchar(15) NOT NULL,
  `total_pago` int(15) NOT NULL,
  `cantidad` int(5) NOT NULL,
  `forma_envio` varchar(255) NOT NULL,
  `estado` varchar(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `direccion_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Productos_has_Usuario_Usuario1` (`usuario_id`),
  KEY `fk_ventas_Direccion1` (`direccion_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `calificaciones`
--
ALTER TABLE `calificaciones`
  ADD CONSTRAINT `fk_Calificacion_Productos1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Calificacion_Usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `carrusels`
--
ALTER TABLE `carrusels`
  ADD CONSTRAINT `fk_Carrusel_Usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `fk_Pedidos_Proveedores1` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedores` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `departamentos`
--
ALTER TABLE `departamentos`
  ADD CONSTRAINT `fk_Departamentos_Paises1` FOREIGN KEY (`pais_id`) REFERENCES `paises` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detalle_compras`
--
ALTER TABLE `detalle_compras`
  ADD CONSTRAINT `fk_Detalle_pedido_Pedidos1` FOREIGN KEY (`compra_id`) REFERENCES `compras` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Detalle_pedido_Productos1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD CONSTRAINT `fk_table1_Compra1` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_table1_Productos1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `direcciones`
--
ALTER TABLE `direcciones`
  ADD CONSTRAINT `fk_Direccion_Municipios1` FOREIGN KEY (`municipio_id`) REFERENCES `municipios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Direccion_Usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `informes`
--
ALTER TABLE `informes`
  ADD CONSTRAINT `fk_Informes_Usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `lista_deseos`
--
ALTER TABLE `lista_deseos`
  ADD CONSTRAINT `fk_Deseos_Productos1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Deseos_Usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `municipios`
--
ALTER TABLE `municipios`
  ADD CONSTRAINT `fk_Municipios_Departamentos1` FOREIGN KEY (`departamento_id`) REFERENCES `departamentos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `parametros`
--
ALTER TABLE `parametros`
  ADD CONSTRAINT `fk_parametro_parametro1` FOREIGN KEY (`foreign`) REFERENCES `parametros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_parametro_tipo_parametro1` FOREIGN KEY (`tp_id`) REFERENCES `tipo_parametros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pqrs`
--
ALTER TABLE `pqrs`
  ADD CONSTRAINT `fk_PQRSD_Usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_parametro1` FOREIGN KEY (`param_color`) REFERENCES `parametros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_productos_parametro2` FOREIGN KEY (`param_categoria`) REFERENCES `parametros` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`param_marca`) REFERENCES `parametros` (`id`);

--
-- Filtros para la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD CONSTRAINT `fk_Proveedores_Persona1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_Usuario_Persona1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `fk_Productos_has_Usuario_Usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ventas_Direccion1` FOREIGN KEY (`direccion_id`) REFERENCES `direcciones` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
