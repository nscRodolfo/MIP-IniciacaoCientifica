-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Tempo de geração: 04-Jun-2020 às 19:41
-- Versão do servidor: 10.3.16-MariaDB
-- versão do PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `id11752321_mip`
--

DELIMITER $$
--
-- Procedimentos
--
CREATE DEFINER=`id11752321_root`@`%` PROCEDURE `addCulturaTalhao` (`tamanho` FLOAT, `Cod_Propriedade` INT(11), `Cod_Planta` INT(11), `quantidadeTalhoes` INT(11))  begin 
declare codCultura, cont int; 
set cont = 1;
INSERT INTO Cultura(TamanhoDaCultura, fk_Propriedade_Cod_Propriedade, fk_Planta_Cod_Planta)  VALUES (tamanho, Cod_Propriedade, Cod_Planta);
set codCultura = (SELECT Cod_Cultura from Cultura where fk_Propriedade_Cod_Propriedade = Cod_Propriedade and fk_Planta_Cod_Planta = Cod_Planta);
While cont <= quantidadeTalhoes do
INSERT INTO Talhao(fk_Cultura_Cod_Cultura, fk_Planta_Cod_Planta) VALUES (codCultura, Cod_Planta);
set cont = cont +1;
end while;
end$$

CREATE DEFINER=`id11752321_root`@`%` PROCEDURE `deletePragaTotal` (`Cod_Praga` INT(11), `Cod_Cultura` INT(11))  begin  
declare Cod_Talhao int;
DELETE FROM PresencaPraga WHERE fk_Praga_Cod_Praga=Cod_Praga AND fk_Cultura_Cod_Cultura=Cod_Cultura;

SET Cod_Talhao = (SELECT Talhao.Cod_Talhao FROM Talhao WHERE Talhao.fk_Cultura_Cod_Cultura = Cod_Cultura);

DELETE FROM PlanoAmostragem WHERE PlanoAmostragem.fk_Praga_Cod_Praga = Cod_Praga AND PlanoAmostragem.fk_Talhao_Cod_Talhao = Cod_Talhao;

DELETE FROM Aplicacao WHERE Aplicacao.fk_Cultura_Cod_Cultura = Cod_Cultura AND Aplicacao.fk_Praga_Cod_Praga = Cod_Praga;

end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Administrador`
--

CREATE TABLE `Administrador` (
  `Cod_Administrador` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Administrador`
--

INSERT INTO `Administrador` (`Cod_Administrador`, `fk_Usuario_Cod_Usuario`) VALUES
(1, 11);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Aplicacao`
--

CREATE TABLE `Aplicacao` (
  `Cod_Aplicacao` int(11) NOT NULL,
  `Autor` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Data` date NOT NULL,
  `Estacao` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Temperatura` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `UmidadeDoAr` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Chuva` tinyint(1) DEFAULT NULL,
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Aplicacao`
--

INSERT INTO `Aplicacao` (`Cod_Aplicacao`, `Autor`, `Data`, `Estacao`, `Temperatura`, `UmidadeDoAr`, `Chuva`, `fk_MetodoDeControle_Cod_MetodoControle`, `fk_Cultura_Cod_Cultura`, `fk_Praga_Cod_Praga`) VALUES
(22, NULL, '2020-03-08', NULL, NULL, NULL, NULL, 8, 52, 3),
(23, NULL, '2020-03-22', NULL, NULL, NULL, NULL, 4, 11, 7),
(25, NULL, '2020-03-26', NULL, NULL, NULL, NULL, 10, 72, 5),
(26, NULL, '2020-03-26', NULL, NULL, NULL, NULL, 8, 73, 10),
(27, NULL, '2020-03-21', NULL, NULL, NULL, NULL, 18, 79, 10),
(28, NULL, '2020-03-30', NULL, NULL, NULL, NULL, 1, 11, 1),
(30, NULL, '2020-03-30', NULL, NULL, NULL, NULL, 6, 79, 1),
(31, NULL, '2020-03-31', NULL, NULL, NULL, NULL, 8, 79, 11),
(33, NULL, '2020-04-03', NULL, NULL, NULL, NULL, 1, 43, 1),
(34, NULL, '2020-05-05', NULL, NULL, NULL, NULL, 6, 79, 11),
(37, NULL, '2020-04-17', NULL, NULL, NULL, NULL, 1, 11, 1),
(38, NULL, '2020-04-18', NULL, NULL, NULL, NULL, 5, 11, 1),
(39, 'Rodolfo Chagas Marinho Nascimento', '2020-04-07', NULL, NULL, NULL, NULL, 1, 82, 1),
(40, 'proprietario', '2020-04-09', NULL, NULL, NULL, NULL, 20, 88, 6),
(42, 'ze', '2020-04-19', NULL, NULL, NULL, NULL, 2, 90, 1),
(43, 'Rodolfo Chagas Marinho Nascimento', '2020-04-05', NULL, NULL, NULL, NULL, 7, 91, 1),
(44, 'José Carlos', '2020-04-15', NULL, NULL, NULL, NULL, 7, 91, 1),
(45, 'Funcionário 1', '2020-04-30', NULL, NULL, NULL, NULL, 7, 91, 1),
(46, 'José Carlos', '2020-05-08', NULL, NULL, NULL, NULL, 7, 91, 1),
(48, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', NULL, NULL, NULL, NULL, 8, 11, 3),
(49, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', NULL, NULL, NULL, NULL, 6, 79, 1),
(50, 'proprietario', '2020-05-22', NULL, NULL, NULL, NULL, 8, 88, 6);

--
-- Acionadores `Aplicacao`
--
DELIMITER $$
CREATE TRIGGER `TriggerInserirDadosParaGraficoAplicacao` AFTER INSERT ON `Aplicacao` FOR EACH ROW begin
	DECLARE dataResgatada INT;
	DECLARE PopPragas INT;
    DECLARE NumPlantas INT;
     SET @dataResgatada = (SELECT MAX(PlanoAmostragem.Data)
                           from PlanoAmostragem, Talhao
                           WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao = Talhao.Cod_Talhao
                           AND PlanoAmostragem.fk_Praga_Cod_Praga = NEW.fk_Praga_Cod_Praga
                           AND Talhao.fk_Cultura_Cod_Cultura = NEW.fk_Cultura_Cod_Cultura);
                           
     SET @PopPragas = (SELECT  SUM(PlanoAmostragem.PlantasInfestadas)
                        from PlanoAmostragem, Talhao
                        WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao = Talhao.Cod_Talhao
                        AND PlanoAmostragem.fk_Praga_Cod_Praga = NEW.fk_Praga_Cod_Praga
                        AND Talhao.fk_Cultura_Cod_Cultura = NEW.fk_Cultura_Cod_Cultura
                        and PlanoAmostragem.Data = (SELECT MAX(PlanoAmostragem.Data) FROM PlanoAmostragem, Talhao 
                                                    WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao = Talhao.Cod_Talhao
    												AND PlanoAmostragem.fk_Praga_Cod_Praga = NEW.fk_Praga_Cod_Praga
   													 AND Talhao.fk_Cultura_Cod_Cultura = NEW.fk_Cultura_Cod_Cultura)
                                                     GROUP BY PlanoAmostragem.Data);
                        
     SET @NumPlantas = (SELECT  SUM(PlanoAmostragem.PlantasAmostradas)
                        from PlanoAmostragem, Talhao
                        WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao = Talhao.Cod_Talhao
                        AND PlanoAmostragem.fk_Praga_Cod_Praga = NEW.fk_Praga_Cod_Praga
                        AND Talhao.fk_Cultura_Cod_Cultura = NEW.fk_Cultura_Cod_Cultura
                        and PlanoAmostragem.Data = (SELECT MAX(PlanoAmostragem.Data) FROM PlanoAmostragem, Talhao 
                                                    WHERE PlanoAmostragem.fk_Talhao_Cod_Talhao = Talhao.Cod_Talhao
    												AND PlanoAmostragem.fk_Praga_Cod_Praga = NEW.fk_Praga_Cod_Praga
   													 AND Talhao.fk_Cultura_Cod_Cultura = NEW.fk_Cultura_Cod_Cultura)
                                                     GROUP BY PlanoAmostragem.Data);
                                                     
	INSERT INTO Aplicacao_Plano(DataPlano, PlantasInfestadas, PlantasAmostradas, fk_Aplicacao_Cod_Aplicacao) VALUES (@dataResgatada, @PopPragas, @NumPlantas, NEW.Cod_Aplicacao);
    
end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Aplicacao_Plano`
--

CREATE TABLE `Aplicacao_Plano` (
  `Cod_Aplicacao` int(11) NOT NULL,
  `DataPlano` date NOT NULL,
  `PlantasInfestadas` int(11) DEFAULT NULL,
  `PlantasAmostradas` int(11) DEFAULT NULL,
  `fk_Aplicacao_Cod_Aplicacao` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Aplicacao_Plano`
--

INSERT INTO `Aplicacao_Plano` (`Cod_Aplicacao`, `DataPlano`, `PlantasInfestadas`, `PlantasAmostradas`, `fk_Aplicacao_Cod_Aplicacao`) VALUES
(1, '2020-04-06', 14, 15, 37),
(3, '2020-04-07', 1, 26, 39),
(4, '2020-04-09', 56, 100, 40),
(6, '2020-04-19', 57, 146, 42),
(7, '2020-04-06', 50, 70, 38),
(8, '2020-03-30', 5, 20, 28),
(9, '2020-04-01', 100, 400, 43),
(10, '2020-04-16', 90, 400, 44),
(11, '2020-04-30', 61, 400, 45),
(12, '2020-05-08', 54, 400, 46),
(14, '2020-05-18', 98, 99, 48),
(15, '2020-05-18', 3, 3, 49),
(16, '2020-05-22', 4, 21, 50);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Atinge`
--

CREATE TABLE `Atinge` (
  `Cod_Atinge` int(11) NOT NULL,
  `NivelDanoEconomico` float DEFAULT NULL,
  `NivelDeControle` float DEFAULT NULL,
  `NivelDeEquilibrio` float DEFAULT NULL,
  `NumeroPlantasAmostradas` int(11) DEFAULT NULL,
  `PontosPorTalhao` int(11) DEFAULT NULL,
  `PlantasPorPonto` int(11) DEFAULT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL,
  `NumAmostras` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Atinge`
--

INSERT INTO `Atinge` (`Cod_Atinge`, `NivelDanoEconomico`, `NivelDeControle`, `NivelDeEquilibrio`, `NumeroPlantasAmostradas`, `PontosPorTalhao`, `PlantasPorPonto`, `fk_Praga_Cod_Praga`, `fk_Planta_Cod_Planta`, `NumAmostras`) VALUES
(1, NULL, 0.01, NULL, 100, 20, 5, 1, 1, 1),
(3, NULL, 0.2, NULL, 100, 20, 5, 3, 1, 1),
(4, NULL, 0.05, NULL, 100, 20, 5, 4, 1, 1),
(5, NULL, 0.2, NULL, 42, 7, 6, 5, 1, 1),
(6, NULL, 0.05, NULL, 50, 10, 5, 6, 1, 1),
(7, NULL, 0.25, NULL, 40, 10, 4, 7, 1, 1),
(8, NULL, 0.1, NULL, 100, 20, 5, 8, 1, 1),
(9, NULL, 0.1, NULL, 100, 20, 5, 9, 1, 1),
(10, NULL, 0.1, NULL, 100, 20, 5, 10, 1, 1),
(11, NULL, 0.1, NULL, 100, 20, 5, 11, 3, 1),
(12, NULL, 0.1, NULL, 100, 20, 5, 9, 3, 1),
(13, NULL, 0.1, NULL, 100, 20, 5, 10, 3, 1),
(14, NULL, 0.05, NULL, 50, 10, 5, 6, 3, 1),
(15, NULL, 0.25, NULL, 40, 10, 4, 7, 3, 1),
(17, NULL, 0.05, NULL, 50, 10, 5, 13, 3, 1),
(18, NULL, 0.05, NULL, 100, 20, 5, 14, 3, 1),
(20, NULL, 0.1, NULL, 100, 20, 5, 11, 4, 1),
(21, NULL, 0.1, NULL, 100, 20, 10, 9, 4, 1),
(22, NULL, 0.1, NULL, 100, 20, 5, 10, 4, 1),
(23, NULL, 0.1, NULL, 100, 20, 5, 8, 4, 1),
(24, NULL, 0.05, NULL, 50, 10, 5, 6, 4, 1),
(26, NULL, 0.25, NULL, 40, 10, 4, 7, 4, 1),
(27, NULL, 0.05, NULL, 50, 10, 5, 16, 5, 1),
(28, NULL, 0.05, NULL, 50, 10, 5, 16, 6, 1),
(29, NULL, 0.1, NULL, 40, 10, 4, 17, 5, 1),
(30, NULL, 0.01, NULL, 100, 20, 5, 1, 3, 1),
(31, NULL, 0.01, NULL, 100, 20, 5, 1, 4, 1),
(32, NULL, 0.1, NULL, 40, 10, 4, 18, 6, 1),
(33, NULL, 0.1, NULL, 40, 10, 4, 18, 5, 1),
(34, NULL, 0.25, NULL, 40, 10, 4, 3, 3, 1),
(35, NULL, 0.25, NULL, 40, 10, 4, 3, 4, 1),
(36, NULL, 0.2, NULL, 100, 20, 5, 19, 1, 1),
(37, NULL, 0.1, NULL, 20, 10, 2, 19, 7, 1),
(38, NULL, 0.2, NULL, 50, 10, 5, 16, 7, 1),
(39, NULL, 0.1, NULL, 40, 10, 4, 18, 7, 1),
(40, NULL, 0.1, NULL, 40, 10, 4, 17, 7, 1),
(41, NULL, 0.1, NULL, 40, 10, 4, 20, 7, 1),
(42, NULL, 0.25, NULL, 100, 20, 5, 21, 1, 1),
(43, NULL, 0.25, NULL, 100, 20, 5, 21, 3, 1),
(44, NULL, 0.25, NULL, 100, 20, 5, 21, 4, 1),
(45, NULL, 0.25, NULL, 100, 20, 5, 21, 5, 1),
(46, NULL, 0.25, NULL, 100, 20, 5, 21, 6, 1),
(47, NULL, 0.25, NULL, 100, 20, 5, 21, 7, 1),
(48, NULL, 0.1, NULL, 20, 10, 2, 19, 6, 1),
(49, NULL, 0.1, NULL, 20, 10, 2, 19, 5, 1),
(51, NULL, 0.1, NULL, 40, 10, 4, 17, 6, 1),
(52, NULL, 0.1, NULL, 40, 10, 4, 17, 8, 1),
(53, NULL, 0.1, NULL, 40, 10, 4, 18, 8, 1),
(54, NULL, 0.1, NULL, 20, 10, 2, 19, 8, 1),
(55, NULL, 0.05, NULL, 50, 10, 5, 16, 8, 1),
(56, NULL, 0.25, NULL, 100, 20, 4, 21, 8, 1),
(59, NULL, 0.01, NULL, 100, 20, 5, 14, 1, 1),
(60, NULL, 0.3, NULL, 100, 25, 4, 23, 9, 32),
(61, NULL, 0.03, NULL, 30, 30, 1, 24, 9, 60),
(62, NULL, 0.3, NULL, 100, 25, 4, 23, 10, 32),
(63, NULL, 0.03, NULL, 30, 30, 1, 20, 10, 60),
(64, NULL, 0.1, NULL, 20, 20, 20, 25, 11, 50);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Combate`
--

CREATE TABLE `Combate` (
  `Cod_Combate` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_InimigoNatural_Cod_Inimigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Combate`
--

INSERT INTO `Combate` (`Cod_Combate`, `fk_Praga_Cod_Praga`, `fk_InimigoNatural_Cod_Inimigo`) VALUES
(5, 16, 6),
(6, 16, 5),
(7, 17, 7),
(8, 6, 12),
(9, 6, 15),
(10, 6, 8),
(11, 6, 9),
(12, 6, 10),
(13, 6, 11),
(14, 6, 13),
(15, 6, 14),
(16, 5, 16),
(17, 5, 17),
(18, 5, 18),
(19, 5, 19),
(20, 5, 8),
(21, 5, 20),
(22, 4, 21),
(23, 4, 22),
(24, 4, 23),
(25, 4, 24),
(26, 4, 25),
(27, 1, 8),
(28, 1, 9),
(29, 1, 10),
(30, 1, 11),
(31, 1, 14),
(32, 1, 15),
(33, 1, 30),
(34, 1, 27),
(35, 1, 29),
(36, 1, 28),
(37, 1, 26),
(38, 3, 28),
(39, 3, 11),
(40, 3, 20),
(41, 8, 9),
(42, 8, 10),
(43, 8, 28),
(44, 8, 11),
(45, 9, 9),
(46, 9, 11),
(47, 9, 28),
(48, 9, 10),
(49, 10, 28),
(50, 10, 11),
(51, 10, 10),
(52, 10, 9),
(53, 7, 23),
(54, 11, 31),
(55, 11, 32),
(56, 13, 8),
(57, 13, 9),
(58, 13, 28),
(59, 13, 11),
(60, 13, 10),
(61, 13, 12),
(62, 13, 13),
(63, 13, 14),
(64, 13, 31),
(65, 14, 8),
(66, 14, 9),
(67, 14, 10),
(68, 14, 28),
(69, 14, 11),
(70, 14, 12),
(71, 14, 13),
(72, 14, 14),
(73, 14, 15),
(78, 19, 28),
(79, 19, 11),
(80, 19, 31),
(81, 19, 14),
(82, 19, 15),
(83, 19, 13),
(84, 21, 14),
(85, 21, 13),
(86, 21, 15),
(88, 19, 33),
(89, 23, 34),
(90, 23, 35),
(91, 24, 37),
(92, 24, 36),
(93, 24, 14),
(94, 25, 14);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Controla`
--

CREATE TABLE `Controla` (
  `Cod_Controla` int(11) NOT NULL,
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Controla`
--

INSERT INTO `Controla` (`Cod_Controla`, `fk_MetodoDeControle_Cod_MetodoControle`, `fk_Praga_Cod_Praga`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 4, 7),
(4, 5, 7),
(5, 6, 1),
(6, 7, 3),
(7, 2, 3),
(8, 8, 3),
(9, 9, 3),
(10, 10, 3),
(11, 10, 4),
(12, 7, 4),
(13, 8, 4),
(14, 2, 4),
(15, 7, 5),
(16, 10, 5),
(17, 8, 5),
(18, 9, 5),
(19, 1, 6),
(20, 8, 6),
(21, 11, 6),
(22, 8, 8),
(23, 8, 9),
(24, 8, 10),
(25, 12, 7),
(26, 8, 11),
(30, 1, 13),
(31, 8, 13),
(32, 11, 13),
(33, 8, 14),
(34, 1, 14),
(35, 11, 14),
(41, 13, 16),
(42, 14, 17),
(43, 16, 17),
(44, 15, 17),
(45, 5, 17),
(46, 17, 17),
(47, 2, 16),
(48, 2, 17),
(49, 18, 5),
(50, 18, 4),
(53, 18, 10),
(54, 18, 9),
(55, 18, 8),
(56, 18, 11),
(58, 19, 6),
(59, 19, 17),
(60, 20, 13),
(61, 20, 6),
(62, 20, 10),
(63, 20, 11),
(64, 20, 8),
(65, 20, 9),
(66, 21, 13),
(67, 21, 6),
(68, 9, 17),
(69, 22, 16),
(70, 23, 16),
(71, 14, 13),
(72, 14, 14),
(73, 14, 6),
(74, 15, 13),
(75, 15, 14),
(76, 15, 6),
(77, 13, 6),
(78, 13, 14),
(79, 13, 13),
(80, 4, 13),
(81, 4, 6),
(82, 4, 14),
(83, 5, 13),
(86, 12, 13),
(87, 12, 14),
(88, 12, 6),
(89, 18, 13),
(91, 18, 6),
(93, 19, 13),
(94, 19, 14),
(100, 20, 14),
(102, 15, 3),
(103, 15, 16),
(104, 12, 8),
(105, 12, 9),
(106, 12, 10),
(107, 12, 11),
(110, 18, 14),
(111, 2, 19),
(112, 8, 18),
(113, 1, 18),
(114, 2, 18),
(115, 7, 18),
(116, 11, 18),
(117, 20, 18),
(118, 4, 18),
(119, 12, 18),
(120, 8, 20),
(121, 1, 20),
(122, 2, 20),
(123, 7, 20),
(124, 11, 20),
(125, 20, 20),
(126, 4, 20),
(127, 12, 20),
(128, 11, 21),
(129, 24, 18),
(130, 24, 21),
(131, 24, 13),
(132, 24, 14),
(133, 24, 6),
(134, 25, 23),
(135, 6, 25),
(136, 26, 25),
(137, 6, 24);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Cultura`
--

CREATE TABLE `Cultura` (
  `Cod_Cultura` int(11) NOT NULL,
  `TamanhoDaCultura` float DEFAULT NULL,
  `Aplicado` tinyint(1) NOT NULL DEFAULT 0,
  `fk_Propriedade_Cod_Propriedade` int(11) NOT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Cultura`
--

INSERT INTO `Cultura` (`Cod_Cultura`, `TamanhoDaCultura`, `Aplicado`, `fk_Propriedade_Cod_Propriedade`, `fk_Planta_Cod_Planta`) VALUES
(11, 2, 1, 3, 1),
(19, 1, 0, 4, 1),
(43, 0.5, 0, 5, 1),
(52, 0.5, 0, 14, 1),
(72, 0.5, 0, 23, 1),
(73, 0.5, 1, 23, 4),
(76, 0.7, 0, 4, 5),
(79, 0.5, 0, 3, 4),
(82, 0.5, 0, 4, 4),
(88, 1, 1, 27, 4),
(90, 1.7, 0, 29, 1),
(91, 2, 0, 30, 4),
(93, 0.5, 0, 3, 5),
(94, 2, 0, 27, 3),
(95, 1, 0, 31, 1),
(96, 5, 0, 3, 9),
(97, 5, 0, 31, 9),
(98, 0.5, 0, 31, 5),
(99, 0.5, 0, 31, 3),
(100, 0.5, 0, 31, 7),
(101, 1, 0, 31, 11),
(102, 0.2, 0, 31, 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoAmostra`
--

CREATE TABLE `FotoAmostra` (
  `Cod_FotoAmostra` int(11) NOT NULL,
  `FotoAmostra` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_Praga_Cod_Praga` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `FotoAmostra`
--

INSERT INTO `FotoAmostra` (`Cod_FotoAmostra`, `FotoAmostra`, `fk_Praga_Cod_Praga`) VALUES
(1, 'mineiro.jpeg', 23),
(5, 'broca do café amostra.jpg\r\n', 24),
(6, 'Polyphagotarsonemus latus - amostra Bruce Watt, University of Maine, Bugwood.org.jpg\r\n', 10),
(7, 'Acaro do bronzeamento amostra.jpg\r\n', 8),
(8, 'Acaro rajado - Frank Peairs, Colorado State University, Bugwood.org.jpg\r\n', 11),
(9, 'Broca da couve - Alton N. Sparks, Jr., University of Georgia, Bugwood.org1.jpg\r\n', 20),
(10, 'Helicoverpa zea-John Ruberson, Kansas State University, Bugwood.org.jpg\r\n', 5),
(11, 'Broca pequena - Sinais de entrada.jpg\r\n', 4),
(12, 'curuquere da couve1.JPG\r\n', 17),
(13, 'Lagarta Rosca W.M. Hantsbarger, Bugwood.org.jpg\r\n', 18),
(14, 'Mosca Branca5.jpg\r\n', 1),
(15, 'mosca minadora - tomate 8.jpg\r\n', 19),
(16, 'Pulgão das inflorescencias - Mohammed El Damir, Bugwood.org.jpg\r\n', 13),
(17, 'Pulgão verde-Whitney Cranshaw, Colorado State University, Bugwood.org.jpg\r\n', 6),
(18, 'Traça das cruciferas-Ko Ko Maung, Bugwood.org.jpg\r\n', 16),
(19, 'Traça do tomateiro15.jpg\r\n', 3),
(20, 'Tripes1.png\r\n', 7),
(21, 'vaquinha1.jpg\r\n', 21),
(22, 'Pulgão das solanaceas-Whitney Cranshaw, Colorado State University, Bugwood.org.....jpg\r\n', 14),
(24, 'Acaro vermelho - Alain Migeon, Acaro vermelho-CBGP - INRA, Monferrier-sur-Lez (FR)..jpg\r\n', 9),
(25, 'Cosmopolites sordidus - Jennifer C. Girón Duque, University of Kansas, Bugwood.org1.jpg\r\n', 25);

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoInimigo`
--

CREATE TABLE `FotoInimigo` (
  `Cod_FotoInimigo` int(11) NOT NULL,
  `FotoInimigo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_InimigoNatural_Cod_Inimigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `FotoInimigo`
--

INSERT INTO `FotoInimigo` (`Cod_FotoInimigo`, `FotoInimigo`, `fk_InimigoNatural_Cod_Inimigo`) VALUES
(1, 'Diadegma sp - Roger Ryan, USFS PNW Station, Bugwood.org.jpg', 5),
(2, 'Apanteles sp - Paul Langlois, Museum Collections Hymenoptera, USDA APHIS PPQ, Bugwood.org.jpg', 6),
(3, 'Cotesia glomerata.jpeg', 7),
(4, 'Ação do fungo Beauveria bassiana - Keith Weller, USDA Agricultural Research Service, Bugwood.org.jpg', 14),
(5, 'Ação do fungo Beauveria bassiana - Louis Tedders, USDA Agricultural Research Service, Bugwood.org.jpg', 14),
(6, 'Ação do fungo Beauveria bassiana - Mark Bailey, University of Georiga, Bugwood.org.jpg', 14),
(7, 'Ação do fungo Paecilomyces fumosoroseus - Vladimir Gouli, University of Vermont, Bugwood.org.jpg', 15),
(8, 'Aphidius colemani - David Cappaert, Bugwood.org.jpg', 12),
(9, 'Aphidius colemani - David Cappaert, Bugwood.org1.jpg', 12),
(11, 'Coleomegilla maculata - Patrick Marquez, USDA APHIS PPQ, Bugwood.org.jpg', 8),
(12, 'Coleomegilla maculata - Whitney Cranshaw, Colorado State University, Bugwood.org.jpg', 8),
(13, 'Cycloneda sanguínea - Jerry A. Payne, USDA Agricultural Research Service, Bugwood.org.jpg', 9),
(14, 'Cycloneda sanguínea - John Ruberson, Kansas State University, Bugwood.org.jpg', 9),
(15, 'Eriopis connexa.jpg', 10),
(16, 'Eriopis-connexa-1.jpg', 10),
(17, 'Metarhizium anisopliae - Svetlana Y. Gouli, University of Vermont, Bugwood.org.jpg', 13),
(18, 'Chrysoperla- Joseph Berger, Bugwood.org.jpg', 11),
(19, 'Geocoris punctipes - Russ Ottens, University of Georgia, Bugwood.org1.jpg', 19),
(20, 'linear earwig (Doru lineare)- Johnny N. Dell, Bugwood.org.jpg', 16),
(21, 'Solonepsis invicta - April Noble, Antweb.org, Bugwood.org.jpg', 18),
(22, 'Trichogramma wasp (Trichogramma ostriniae) - Peggy Greb, USDA Agricultural Research Service, Bugwood.org.jpg', 20),
(23, '(Doru sp.) - Johnny N. Dell, Bugwood.org.jpg', 16),
(24, 'Campoletis sonorensis - Natasha Wright, Cook\'s Pest Control, Bugwood.org.jpg', 17),
(25, 'Geocoris punctipes - Russ Ottens, University of Georgia, Bugwood.org.jpg', 19),
(26, 'Encyrtid wasp (Oobius agrili) - Debbie Miller, USDA Forest Service, Bugwood.org.jpg', 22),
(27, 'Eulophid wasp (Tetrastichus planipennisi) - Houping Liu, Michigan State University, Bugwood.org.jpg', 23),
(28, 'Ichneumonid wasp (Itoplectis conquisitor) - Gerald J. Lenhard, Louisiana State University, Bugwood.org.jpg', 24),
(29, 'Tachinid fly (Carcelia yalensis) - USDA Forest Service - Ogden , USDA Forest Service, Bugwood.org.jpg', 25),
(30, 'Chalcidid wasp (Brachymeria flavipes) - Paul Langlois, Museum Collections- Hymenoptera,.jpg', 21),
(31, 'Encarsia formosa - Paul Langlois, Museum Collections- Hymenoptera, USDA APHIS PPQ, Bugwood.org.jpg', 30),
(32, 'Whitney Cranshaw, Colorado State University, Bugwood.org.jpg', 27),
(33, 'Whitney Cranshaw, Colorado State University, Bugwood.org-Coccinella undecimpunctata.jpg', 27),
(34, 'Amblyseius tamatavensis.jpeg', 26),
(35, 'Amblyseius tamatavensiss.jpg', 26),
(37, 'Chrysoperla carnea1.jpg', 28),
(38, 'Delphastus pusillus- Groton, Middlesex County, Massachusetts, USA.jpg', 29),
(39, 'Delphastus pusillus.jpg', 29),
(40, 'Delphastus pusillus1.jpg', 29),
(41, 'Encarsia formosa - David Cappaert, Bugwood.org.jpg', 30),
(42, 'David Cappaert, Bugwood.org.jpg\r\n', 23),
(45, 'Phytoseiulus macropilis.jpg\r\n', 31),
(46, 'Neoseiulus californicus.jpg', 32),
(47, 'Inimigo natural - mosca minadora - Opius scabriventris (Hymenoptera Braconidae).png', 33),
(48, 'Polybia scutellaris (Vespidae Polystinae).jpg\r\n', 35),
(49, 'Protonectarina silveirae -.jpg\r\n', 34),
(50, 'Protonectarina silveirae1.jpg\r\n', 34),
(51, 'Cephalonomia stephanoderis.png\r\n', 37),
(52, 'Prorops nasuta.jpg\r\n', 36);

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoMetodo`
--

CREATE TABLE `FotoMetodo` (
  `Cod_FotoMetodo` int(11) NOT NULL,
  `FotoMetodo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `FotoMetodo`
--

INSERT INTO `FotoMetodo` (`Cod_FotoMetodo`, `FotoMetodo`, `fk_MetodoDeControle_Cod_MetodoControle`) VALUES
(1, 'Bacillus thuringiensis subspec thuringiensis e bacillus thuringiensis subspec. aizawai.jpg', 9),
(2, 'Bacillus thuringiensis subspec thuringiensis e bacillus thuringiensis subspec. aizawai1.jpg', 9),
(3, 'Beauveria bassiana.jpg', 6),
(4, 'Beauveria bassiana1.jpg', 6),
(5, 'Beauveria bassiana2.jpg', 6),
(8, 'Extrato alcoólico de alho (Allium sativum).jpg', 22),
(9, 'Extrato alcoólico de alho (Allium sativum)1.jpg', 22),
(10, 'Extrato alcoólico de alho (Allium sativum)2.jpg', 22),
(11, 'Extrato aquoso de Alho (Allium sativum).jpg', 18),
(15, 'Extrato aquoso de pó de casca de pereiro (Aspidosperma pyrifolium).jpg', 23),
(16, 'Extrato aquoso de pó de casca de pereiro (Aspidosperma pyrifolium)1.jpg', 23),
(17, 'Extrato aquoso de pó de casca de pereiro (Aspidosperma pyrifolium)2.jpg', 23),
(20, 'Extrato de Abricó-do-pará (Mammea americana).jpg', 16),
(21, 'Extrato de Abricó-do-pará (Mammea americana)1.jpg', 16),
(22, 'Extrato de Abricó-do-pará (Mammea americana)2.jpg', 16),
(23, 'Extrato de Cebolinha (Allium fistulosum L.).jpg', 11),
(24, 'Extrato de Cebolinha (Allium fistulosum L.)1.jpg', 11),
(25, 'Extrato de Cebolinha (Allium fistulosum L.)2.jpg', 11),
(28, 'Extrato de Cravo-de-defunto (Tagetes erecta)2.jpg', 20),
(29, 'Extrato de Fruta-do-conde (Annona retículeta, A. muricata)..jpg', 13),
(30, 'Extrato de Fruta-do-conde (Annona retículeta, A. muricata).1.jpg', 13),
(31, 'Extrato de Fruta-do-conde (Annona retículeta, A. muricata).2.jpg', 13),
(32, 'Extrato de Jacatupé (Pachyrhizus tuberosus L. Spreng.).jpg', 14),
(33, 'Extrato de Jacatupé (Pachyrhizus tuberosus L. Spreng.)1.jpg', 14),
(34, 'Extrato de Jacatupé (Pachyrhizus tuberosus L. Spreng.)2.jpg', 14),
(35, 'Extrato de Jacatupé Bravo (Pachyrrhizus erosus L. Urban.).jpg', 15),
(36, 'Extrato de Jacatupé Bravo (Pachyrrhizus erosus L. Urban.)1.jpg', 15),
(37, 'Extrato de Jacatupé Bravo (Pachyrrhizus erosus L. Urban.)2.jpg', 15),
(38, 'Extrato de Pimenta-do-reino (Piper nigrum).jpg', 4),
(39, 'Extrato de Pimenta-do-reino (Piper nigrum)1.jpg', 4),
(40, 'Extrato de Pimenta-do-reino (Piper nigrum)2.jpg', 4),
(41, 'Extrato de Pimenta-malagueta (Capsicum frutescens).jpg', 21),
(42, 'Extrato de Pimenta-malagueta (Capsicum frutescens)1.jpg', 21),
(43, 'Extrato de Pimenta-malagueta (Capsicum frutescens)2.jpg', 21),
(44, 'Extrato de Ryania (Ryania speciosa).jpg', 5),
(45, 'Extrato de Ryania (Ryania speciosa)1.jpg', 5),
(46, 'Extrato de Ryania (Ryania speciosa)2.jpg', 5),
(47, 'Extrato de Sálvia (Salvia officinales L.).jpg', 17),
(48, 'Extrato de Sálvia (Salvia officinales L.)1.jpg', 17),
(49, 'Extrato de Sálvia (Salvia officinales L.)2.jpg', 17),
(50, 'Extrato de Timbó -Arruda- Losna Branca (Derris ssp.- Ruta graveolens- Artemisia absinthium).jpg', 12),
(51, 'Extrato de Timbó -Arruda- Losna Branca (Derris ssp.- Ruta graveolens- Artemisia absinthium)1.jpg', 12),
(52, 'Extrato de Timbó -Arruda- Losna Branca (Derris ssp.- Ruta graveolens- Artemisia absinthium)2.jpg', 12),
(53, 'Trichogramma pretiosum.jpg', 10),
(54, 'Trichogramma pretiosum1.jpg', 10),
(55, 'Extrato aquoso de Cinamomo a 10 (Melia azedarach L.).jpg', 19),
(56, 'Extrato aquoso de Cinamomo a 10 (Melia azedarach L.)1.jpg', 19),
(57, 'Extrato aquoso de Cinamomo a 10 (Melia azedarach L.)2.jpg', 19),
(58, 'Pessegueiro-em-flor-Caçador-SC-10.jpg\r\n', 24),
(59, 'pessegueiro DSC00027.JPG\r\n', 24),
(60, 'farinha.jpg\r\n', 8),
(61, 'farinha0.jpg\r\n', 8),
(62, 'nim1.jpg\r\n', 2),
(63, 'nim0.jpg\r\n', 2),
(64, 'sal0.jpg\r\n', 1),
(65, 'sal.jpg\r\n', 1),
(66, 'nim1.jpg\r\n', 7),
(67, 'nim0.jpg\r\n', 7),
(68, 'Extrato de Cravo-de-defunto (Tagetes erecta).jpg\r\n', 20),
(69, 'Extrato de Cravo-de-defunto (Tagetes erecta)1.jpg\r\n', 20),
(70, 'calda sulfocalcica.png\r\n', 25),
(71, 'isca telha[.png', 26),
(72, 'isca queijo[.png', 26);

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoPlanta`
--

CREATE TABLE `FotoPlanta` (
  `Cod_FotoPlanta` int(11) NOT NULL,
  `FotoPlanta` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `FotoPlanta`
--

INSERT INTO `FotoPlanta` (`Cod_FotoPlanta`, `FotoPlanta`, `fk_Planta_Cod_Planta`) VALUES
(3, 'maxresdefault.jpg', 1),
(4, 'Tomate-Híbrido-e-Compack-Coronel.jpg', 1),
(5, 'pimentao003.jpg\r\n', 3),
(6, 'pimentao006.jpg\r\n', 3),
(7, '000493-berinjela.jpg\r\n', 4),
(8, 'ed048-beringela.jpg\r\n', 4),
(9, 'repolho0001.jpg', 7),
(11, 'REpolho.jpg\r\n', 7),
(12, 'couve0.jpg\r\n', 5),
(13, 'couve.jpg\r\n', 5),
(14, 'brocolis.jpg\r\n', 6),
(15, 'brocolis_ramoso.jpg\r\n', 6),
(16, 'couve flor.jpg\r\n', 8),
(17, 'couve flor0.jpg\r\n', 8),
(18, 'Cafe-conilon.jpg\r\n', 10),
(19, 'cafe conilon.jpg\r\n', 10),
(20, 'arvore-de-cafe-arabica_87374-24.jpg\r\n', 9),
(21, 'cafe arabica.jpg\r\n', 9),
(22, 'banana.jpg\r\n', 11),
(23, 'plantar-bananeira.jpg\r\n', 11);

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoPraga`
--

CREATE TABLE `FotoPraga` (
  `Cod_FotoPraga` int(11) NOT NULL,
  `FotoPraga` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `FotoPraga`
--

INSERT INTO `FotoPraga` (`Cod_FotoPraga`, `FotoPraga`, `fk_Praga_Cod_Praga`) VALUES
(200, 'Mosca Branca3.jpg\r\n', 1),
(201, 'Mosca Branca4.jpg\r\n', 1),
(202, 'Mosca Branca5.jpg\r\n', 1),
(203, 'Mosca Branca6.jpg\r\n', 1),
(204, 'Mosca Branca7.jpg\r\n', 1),
(205, 'Mosca Branca8.jpg\r\n', 1),
(206, 'Mosca Branca-Charles Olsen, USDA APHIS PPQ, Bugwood.org.jpg\r\n', 1),
(207, 'Mosca Branca-Florida Division of Plant Industry , Florida Department of Agriculture and Consumer Services, Bugwood.org.jpg\r\n', 1),
(208, 'Mosca Branca-Scott Bauer, USDA Agricultural Research Service, Bugwood.org.jpg\r\n', 1),
(209, 'Mosca Branca-Scott Bauer, USDA Agricultural Research Service, Bugwood.orgg.jpg\r\n', 1),
(210, 'Mosca Branca - Alton N. Sparks, Jr., University of Georgia, Bugwood.org.jpg\r\n', 1),
(211, 'Mosca Branca - Florida Division of Plant Industry , Florida Department of Agriculture and Consumer Services, Bugwood.org.jpg\r\n', 1),
(212, 'Mosca Branca - Jeffrey W. Lotz, Florida Department of Agriculture and Consumer Services, Bugwood.org.jpg\r\n', 1),
(213, 'Mosca Branca - W. Billen, Pflanzenbeschaustelle, Weil am Rhein, Bugwood.org.jpg\r\n', 1),
(215, 'Mosca Branca0.jpg\r\n', 1),
(216, 'Mosca Branca1.jpg\r\n', 1),
(217, 'Mosca Branca2.jpg\r\n', 1),
(248, 'Traça do tomateiro5.jpg\r\n', 3),
(249, 'Traça do tomateiro6.jpg\r\n', 3),
(251, 'Traça do tomateiro8.jpg\r\n', 3),
(253, 'Traça do tomateiro10.jpg\r\n', 3),
(256, 'Traça do tomateiro13.jpg\r\n', 3),
(258, 'Traça do tomateiro15.jpg\r\n', 3),
(263, 'Traça do tomateiro20.jpg\r\n', 3),
(264, 'Traça do tomateiro21.jpg\r\n', 3),
(268, 'Traça do tomateiro-James Hayden, Microlepidoptera on Solanaceae, USDA APHIS PPQ, Bugwood.org.jpg\r\n', 3),
(269, 'Traça do tomateiroMetin GULESCI, Bugwood.org..jpg\r\n', 3),
(270, 'Traça do tomateiro-Metin GULESCI, Bugwood.org.jpg\r\n', 3),
(272, 'Traça do tomateiro - dano.jpg\r\n', 3),
(273, 'Traça do tomateiro - egg.jpg\r\n', 3),
(274, 'Traça do tomateiro - Metin GULESCI, Bugwood.org..jpg\r\n', 3),
(276, 'Traça do tomateiro- Metin GULESCI, Bugwood.org..jpg\r\n', 3),
(277, 'Traça do tomateiro0.png\r\n', 3),
(278, 'Traça do tomateiro1.jpg\r\n', 3),
(279, 'Pulgão verde - Eugene E. Nelson, Bugwood.org.jpg', 6),
(280, 'Pulgão verde - Eugene E. Nelson, Bugwood.org1.jpg', 6),
(281, 'Pulgão verde - J. Michael Moore, University of Georgia, Bugwood.org.jpg', 6),
(282, 'Pulgão verde - Jim Baker, North Carolina State University, Bugwood.org.jpg', 6),
(283, 'Pulgão verde - Jim Baker, North Carolina State University, Bugwood.org2.jpg', 6),
(284, 'Pulgão verde - Metin GULESCI, Bugwood.org.jpg', 6),
(285, 'Pulgão verde Jim Baker, North Carolina State University, Bugwood.org.jpg', 6),
(286, 'Pulgão verde- Jim Baker, North Carolina State University, Bugwood.org.jpgs.jpg', 6),
(287, 'Pulgão verde--Jim Baker, North Carolina State University, Bugwood.org.jpg', 6),
(288, 'Pulgão verde-Jim Baker, North Carolina State University, Bugwood.org.jpg', 6),
(289, 'Pulgão verdeJim Baker, North Carolina State University, Bugwood.org.jpg', 6),
(290, 'Pulgão verde-Jim Baker, North Carolina State University, Bugwood.orgd.jpg', 6),
(291, 'Pulgão verde-Whitney Cranshaw, Colorado State University, Bugwood.org.jpg', 6),
(292, 'Helicoverpa zea - Clemson University - Alton N. Sparks, Jr., University of Georgia, Bugwood.org..jpg\r\n', 5),
(293, 'Helicoverpa zea - Clemson University - Alton N. Sparks, Jr., University of Georgia, Bugwood.org.jpg\r\n', 5),
(294, 'Helicoverpa zea - Clemson University - USDA Cooperative Extension Slide Series, Bugwood.org......jpg\r\n', 5),
(295, 'Helicoverpa zea - Clemson University - USDA Cooperative Extension Slide Series, Bugwood.org.jpg\r\n', 5),
(296, 'Helicoverpa zea - Eric Burkness, Bugwood.org.jpg\r\n', 5),
(297, 'Helicoverpa zea - Whitney Cranshaw, Colorado State University, Bugwood.org.jpg\r\n', 5),
(298, 'Helicoverpa zea.jpeg\r\n', 5),
(299, 'Helicoverpa zea1.jpeg\r\n', 5),
(300, 'Helicoverpa zea-Bruce Watt, University of Maine, Bugwood.org.jpg\r\n', 5),
(301, 'Helicoverpa zea--Clemson University - USDA Cooperative Extension Slide Series, Bugwood.org..jpg\r\n', 5),
(302, 'Helicoverpa zea-Clemson University - USDA Cooperative Extension Slide Series, Bugwood.org.jpg\r\n', 5),
(303, 'Helicoverpa zea--Clemson University - USDA Cooperative Extension Slide Series, Bugwood.orgg.jpg\r\n', 5),
(304, 'Helicoverpa zea-Clemson University - USDA Cooperative Extension Slide Series, Bugwood.orggg.jpg\r\n', 5),
(305, 'Helicoverpa zea-John Ruberson, Kansas State University, Bugwood.org.jpg\r\n', 5),
(306, 'Helicoverpa zea-Steve L. Brown, University of Georgia, Bugwood.or.jpg\r\n', 5),
(307, 'Helicoverpa zea-University of Georgia , University of Georgia, Bugwood.org.jpg\r\n', 5),
(308, 'Helicoverpa zea-William Lambert, University of Georgia, Bugwood.org.jpg\r\n', 5),
(309, 'Helicoverpa zea - Clemson University - Alton N. Sparks, Jr., University of Georgia, Bugwood.org...jpg\r\n', 5),
(310, 'Acaro Branco - damage - Gerald Holmes, California Polytechnic State University at San Luis Obispo, Bugwood.org -.jpg\r\n', 10),
(311, 'Acaro Branco - Gerald Holmes, California Polytechnic State University at San Luis Obispo, Bugwood.org.jpg\r\n', 10),
(312, 'Acaro Branco- Chazz Hesselein, Alabama Cooperative Extension System, Bugwood.org.jpg\r\n', 10),
(313, 'Acaro Branco0.jpg\r\n', 10),
(314, 'Acaro Branco1.png\r\n', 10),
(315, 'Acaro Branco-Nancy Gregory, University of Delaware, Bugwood.org.jpg\r\n', 10),
(316, 'Acaro Branco - Bruce Watt, University of Maine, Bugwood.org.jpg\r\n', 10),
(317, 'Acaro do bronzeamento0.jpg\r\n', 8),
(318, 'Acaro do bronzeamento1.jpg\r\n', 8),
(319, 'Acaro do bronzeamento3.jpg\r\n', 8),
(321, 'Acaro do bronzeamento5.jpg\r\n', 8),
(322, 'Acaro vermelho - Alain Migeon, Acaro vermelho-CBGP - INRA, Monferrier-sur-Lez (FR)..jpg\r\n', 9),
(323, 'Acaro vermelho - Alain Migeon, CBGP - INRA, Monferrier-sur-Lez (FR)....jpg\r\n', 9),
(324, 'Acaro vermelho - Alain Migeon, CBGP - INRA, Monferrier-sur-Lez (FR)...jpg\r\n', 9),
(325, 'Acaro vermelho0.jpg\r\n', 9),
(326, 'Acaro vermelho1.jpg\r\n', 9),
(327, 'Acaro vermelho2.jpg\r\n', 9),
(328, 'Acaro vermelho3.jpg\r\n', 9),
(329, 'Acaro vermelhoAlain Migeon, CBGP - INRA, Monferrier-sur-Lez (FR)..jpg\r\n', 9),
(330, 'Broca pequena-Kurt Ahlmark, Microlepidoptera on Solanaceae, USDA APHIS PPQ, Bugwood.org.jpg\r\n', 4),
(331, 'Broca pqEntrance and exit holes of Neoleucinodes elegantalis on a tomato fruit. Photo courtesy of A Diaz Montilla, Corpoica La Selva (CO.jpg\r\n', 4),
(332, 'Broca prquena0.jpg\r\n', 4),
(333, 'Broca prquena1.jpg\r\n', 4),
(334, 'Broca prquena2.jpg\r\n', 4),
(335, 'Broca prquena3.jpg\r\n', 4),
(336, 'Broca pequena - Kurt Ahlmark, Microlepidoptera on Solanaceae, USDA APHIS PPQ, Bugwood.orgg.jpg\r\n', 4),
(337, 'Broca pequena - Sinais de entrada.jpg\r\n', 4),
(338, 'Broca pequena localização dos ovos.jpg\r\n', 4),
(339, 'Broca pequena4.jpg\r\n', 4),
(340, 'Broca pequena5.jpg\r\n', 4),
(341, 'Broca pequena6.jpg\r\n', 4),
(342, 'Broca pequena7.jpg\r\n', 4),
(343, 'Broca pequena-do-tomateiro-A-Ovos-depositados-em-diferentes.png\r\n', 4),
(344, 'Tripes1.png\r\n', 7),
(345, 'Tripes0.jpg\r\n', 7),
(346, 'Tripes - Vivek Kumar, Entomology and Nematology Department, University of Florida..png\r\n', 7),
(347, 'Acaro rajado - Louis Tedders, USDA Agricultural Research Service, Bugwood.org.jpg\r\n', 11),
(348, 'Acaro rajado - O.P. Sharma, Bugwood.org.jpg\r\n', 11),
(349, 'Acaro rajado - Whitney Cranshaw, Colorado State University, Bugwood.org.jpg\r\n', 11),
(350, 'Acaro rajado- John C. French Sr., Retired, Universities -Auburn, GA, Clemson and U of MO, Bugwood.org.jpg\r\n', 11),
(351, 'Acaro rajado sintomas -Min Shen, Taichung District Agricultural Research and Extension Station, Bugwood.org.jpg\r\n', 11),
(352, 'Acaro rajadoe - Eugene E. Nelson, Bugwood.org.jpg\r\n', 11),
(353, 'Acaro rajado - Frank Peairs, Colorado State University, Bugwood.org.jpg\r\n', 11),
(362, 'Broca da couve - Alton N. Sparks, Jr., University of Georgia, Bugwood.org1.jpg\r\n', 20),
(363, 'Broca da couve - Natasha Wright, Cook\'s Pest Control, Bugwood.org.jpg\r\n', 20),
(364, 'Broca da couve0.jpg\r\n', 20),
(365, 'Broca da couve1.jpeg\r\n', 20),
(366, 'Broca da couve-Alton N. Sparks, Jr., University of Georgia, Bugwood.org - broca da couve.jpg\r\n', 20),
(367, 'Broca da couveMerle Shepard, Gerald R.Carner, and P.A.C Ooi, Insects and their Natural Enemies Associated with Vegetables and Soybean in Southeast Asia, Bugwood..jpg\r\n', 20),
(368, 'Broca-da-couve ou broca do repolho.jpg\r\n', 20),
(369, 'curuquere da couve - - Alton N. Sparks, Jr., University of Georgia, Bugwood.org1.jpg\r\n', 17),
(370, 'curuquere da couve - Alton N. Sparks, Jr., University of Georgia, Bugwood.org.jpg\r\n', 17),
(371, 'curuquere da couve - Joseph LaForest, University of Georgia, Bugwood.org1.jpg\r\n', 17),
(372, 'curuquere da couve - Whitney Cranshaw, Colorado State University, Bugwood.org.jpg\r\n', 17),
(373, 'curuquere da couve -Hanna Royals, Screening Aids, USDA APHIS PPQ, Bugwood.org.jpg\r\n', 17),
(374, 'curuquere da couve -Joseph LaForest, University of Georgia, Bugwood.org.jpg\r\n', 17),
(375, 'curuquere da couve0.png\r\n', 17),
(376, 'curuquere da couve1.JPG\r\n', 17),
(377, 'curuquere da couve2.jpg\r\n', 17),
(378, 'Lagarta Rosca - Adam Sisson, Iowa State University, Bugwood.org2.jpg\r\n', 18),
(379, 'Lagarta Rosca - Adam Sisson, Iowa State University, Bugwood.org3.jpg\r\n', 18),
(380, 'Lagarta Rosca - Ian Kimber, Bugwood.org.jpg\r\n', 18),
(381, 'Lagarta Rosca Pest and Diseases Image Library, Bugwood.org.jpg\r\n', 18),
(382, 'Lagarta Rosca -Robert J. Bauernfeind, Kansas State University, Bugwood.org.jpg\r\n', 18),
(383, 'Lagarta Rosca -Roger Schmidt, University of Wisconsin-Madison, Bugwood.org.jpg\r\n', 18),
(384, 'Lagarta Rosca -Roger Schmidt, University of Wisconsin-Madison, Bugwood.org1.jpg\r\n', 18),
(385, 'Lagarta Rosca W.M. Hantsbarger, Bugwood.org.jpg\r\n', 18),
(386, 'Lagarta Rosca-Adam Sisson, Iowa State University, Bugwood.org4.jpg\r\n', 18),
(387, 'Lagarta Rosca-Merle Shepard, Gerald R.Carner, and P.A.C Ooi, Insects and their Natural Enemies Associated with Vegetables and Soybean in Southeast Asia,.jpg\r\n', 18),
(388, 'Lagarta Rosca - Adam Sisson, Iowa State University, Bugwood.org.jpg\r\n', 18),
(389, 'Lagarta Rosca - Adam Sisson, Iowa State University, Bugwood.org1.jpg\r\n', 18),
(390, 'Mosca minadora - Liriomyza huidobrensis - National Plant Protection Organization, the Netherlands , Bugwood.org.jpg\r\n', 19),
(391, 'Mosca minadora - Liriomyza huidobrensis-National Plant Protection Organization, the Netherlands , Bugwood.org.jpg\r\n', 19),
(392, 'Mosca minadora - Liriomyza sativae-Whitney Cranshaw, Colorado State University, Bugwood.org.jpg\r\n', 19),
(393, 'Mosca minadora - Liriomyza trifolii - Central Science Laboratory, Harpenden , British Crown, Bugwood.org.jpg\r\n', 19),
(394, 'Mosca minadora - Liriomyza trifolii - Central Science Laboratory, Harpenden , British Crown, Bugwood.org1.jpg\r\n', 19),
(395, 'Mosca minadora - Liriomyza trifolii-SRPV, Poitiers , Les Services Régionaux de la Protection des Végétaux, Bugwood.org.jpg\r\n', 19),
(396, 'Mosca minadora- Liriomyza sativae-Whitney Cranshaw, Colorado State University, Bugwood.org.jpg\r\n', 19),
(397, 'Mosca minadora0.png\r\n', 19),
(398, 'Mosca minadora1.png\r\n', 19),
(399, 'Mosca minadora - Howard F. Schwartz, Colorado State University, Bugwood.org.jpg\r\n', 19),
(401, 'Pulgão das inflorescencias - Jim Baker, North Carolina State University, Bugwood.org 1.jpg\r\n', 13),
(402, 'Pulgão das inflorescencias - Mohammed El Damir, Bugwood.org.jpg\r\n', 13),
(403, 'Pulgão das inflorescencias -Aphis gossypii - Jim Baker, North Carolina State University, Bugwood.org.jpg\r\n', 13),
(404, 'Pulgão das inflorescencias s - Jim Baker, North Carolina State University, Bugwood.org...jpg\r\n', 13),
(405, 'Pulgão das inflorescencias-Clemson University - USDA Cooperative Extension Slide Series, Bugwood.org.jpg\r\n', 13),
(406, 'Pulgão das inflorescencias-Jim Baker, North Carolina State University, Bugwood.org.jpg\r\n', 13),
(407, 'Pulgão das solanaceas - - Whitney Cranshaw, Colorado State University, Bugwood.org.jpg\r\n', 14),
(408, 'Pulgão das solanaceas - Macrosiphum euphorbiae - Joseph Berger, Bugwood.org.jpg\r\n', 14),
(409, 'Pulgão das solanaceas - Whitney Cranshaw, Colorado State University, Bugwood.org........jpg\r\n', 14),
(410, 'Pulgão das solanaceas - Whitney Cranshaw, Colorado State University, Bugwood.org..jpg\r\n', 14),
(411, 'Pulgão das solanaceas-Whitney Cranshaw, Colorado State University, Bugwood.org.....jpg\r\n', 14),
(412, 'Pulgão das solanaceas-Joseph Berger, Bugwood.org.jpg\r\n', 14),
(443, 'vaquinha0.jpg\r\n', 21),
(444, 'vaquinha1.jpg\r\n', 21),
(445, 'vaquinha2.jpg\r\n', 21),
(446, 'vaquinha3.jpg\r\n', 21),
(447, 'vaquinha4.jpg\r\n', 21),
(448, 'vaquinha5.jpg\r\n', 21),
(449, 'vaquinha6.jpg\r\n', 21),
(450, 'vaquinha7.jpg\r\n', 21),
(459, 'bicho mineiro - mina2.jpg\r\n', 23),
(460, 'bicho mineiro - mina3.jpg\r\n', 23),
(461, 'bicho mineiro - mina4.png\r\n', 23),
(462, 'bicho mineiro - mina5.jpg\r\n', 23),
(463, 'bicho mineiro - mina6.jpeg\r\n', 23),
(464, 'bicho mineiro - mina7.jpg\r\n', 23),
(465, 'bicho mineiro - mina8.jpg\r\n', 23),
(466, 'bicho mineiro - mina1.jpg\r\n', 23),
(467, 'Hypothenemus hampei - Peggy Greb, USDA Agricultural Research Service, Bugwood.org5.jpg\r\n', 24),
(468, 'Hypothenemus hampei - Whitney Cranshaw, Colorado State University, Bugwood.org2.jpg\r\n', 24),
(469, 'Hypothenemus hampei - Whitney Cranshaw, Colorado State University, Bugwood.org3.jpg\r\n', 24),
(470, 'Hypothenemus hampei - Whitney Cranshaw, Colorado State University, Bugwood.org4.jpg\r\n', 24),
(471, 'Hypothenemus hampei - Eric Erbe, USDA Agricultural Research Service, Bugwood.org1.jpg\r\n', 24),
(472, 'Cosmopolites sordidus - Jennifer C. Girón Duque, University of Kansas, Bugwood.org2.jpg', 25),
(473, 'Cosmopolites sordidus - Jennifer C. Girón Duque, University of Kansas, Bugwood.org1.jpg', 25),
(474, 'Cosmopolites sordidus - Oswaldo Brito, Independent Consultant, Bugwood.org3.jpg', 25),
(475, 'Cosmopolites sordidus4.jpg', 25),
(476, 'isca queijo[.png', 25),
(479, 'isca telha[.png', 25),
(497, 'Traça das cruciferas-Whitney Cranshaw, Colorado State University, Bugwood.org9.jpg', 16),
(498, 'Traça das cruciferas-Whitney Cranshaw, Colorado State University, Bugwood.org8.jpg', 16),
(499, 'Traça das cruciferas-Whitney Cranshaw, Colorado State University, Bugwood.org2.jpg', 16),
(500, 'Traça das cruciferas-Whitney Cranshaw, Colorado State University, Bugwood.org12.jpg', 16),
(501, 'Traça das cruciferas-Whitney Cranshaw, Colorado State University, Bugwood.org11.jpg', 16),
(502, 'Traça das cruciferas-Whitney Cranshaw, Colorado State University, Bugwood.org10.jpg', 16),
(503, 'Traça das cruciferas-Russ Ottens, University of Georgia, Bugwood.org.jpg', 16),
(504, 'Traça das cruciferas-Ko Ko Maung, Bugwood.org1.jpg', 16),
(505, 'Traça das cruciferas-Ko Ko Maung, Bugwood.org.jpg', 16),
(506, 'Traça das cruciferas-David Riley, University of Georgia, Bugwood.org1.jpg', 16),
(507, 'Traça das cruciferas-David Riley, University of Georgia, Bugwood.org.jpg', 16),
(508, 'Traça das cruciferas-David Cappaert, Bugwood.org.jpg', 16),
(509, 'Traça das cruciferas-Alton N. Sparks, Jr., University of Georgia, Bugwood.org.jpg', 16),
(510, 'Traça das cruciferas - David Cappaert, Bugwood.org1.jpg', 16);

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoUsuario`
--

CREATE TABLE `FotoUsuario` (
  `Cod_FotoUsuario` int(11) NOT NULL,
  `FotoUsuario` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Funcionario`
--

CREATE TABLE `Funcionario` (
  `Cod_Funcionario` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Funcionario`
--

INSERT INTO `Funcionario` (`Cod_Funcionario`, `fk_Usuario_Cod_Usuario`) VALUES
(1, 6),
(2, 7),
(3, 8),
(4, 9),
(5, 10),
(6, 12),
(7, 13),
(8, 15),
(9, 16),
(10, 18);

-- --------------------------------------------------------

--
-- Estrutura da tabela `InimigoNatural`
--

CREATE TABLE `InimigoNatural` (
  `Cod_Inimigo` int(11) NOT NULL,
  `Nome` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `InimigoNatural`
--

INSERT INTO `InimigoNatural` (`Cod_Inimigo`, `Nome`) VALUES
(5, 'Diadegma sp (Hymenoptera: Ichneumonidae) '),
(6, 'Apanteles sp (Hymenoptera: Braconidae)'),
(7, 'Cotesia glomerata (Hymenoptera: Braconidae)'),
(8, 'Coleomegilla maculata (Coleoptera: Coccinellidae)'),
(9, 'Cycloneda sanguínea (Coleoptera: Coccinellidae)'),
(10, 'Eriopis connexa (Coleoptera: Eriopis connexa)'),
(11, 'Chrysoperla externa (Hagen) (Neuroptera: Chrysopidae)'),
(12, 'Aphidius colemani Viereck (Hymenoptera: Braconidae)'),
(13, 'Metarhizium anisopliae (Metsch.)'),
(14, 'Beauveria bassiana (Bals.) Vuill.'),
(15, 'Paecilomyces fumosoroseus (Wize) Brown & Smith'),
(16, 'Doru luteipes (Scudder) (Dermaptera: Forficulidae)'),
(17, 'Campoletis sonorensis (Cameron) (Hymenoptera, Ichneumonidae, Campopleginae)'),
(18, 'Solonepsis invicta Buren (Hymenoptera: Formicidae)'),
(19, 'Geocoris punctipes (Say)  (Hemiptera: Geocoridae)'),
(20, 'Trichogramma West. (Hymenoptera: Trichogrammatidae)'),
(21, 'Brachymeria flavipes (Hymenoptera: Chalcidoidea)'),
(22, 'Oobius agrili (Hymenoptera: Encyrtidae)'),
(23, 'Tetrastichus planipennis (Hymenoptera: Eulophidae)'),
(24, 'Itoplectis conquisitor (Hymenoptera: Ichneumonidae)'),
(25, 'Carcelia yalensis (Diptera: Tachinidae)'),
(26, 'Amblyseius tamatavensis (Acari: Phytoseiidae)'),
(27, 'Coccinella undecimpunctata (Coleoptera: Coccinellidae)'),
(28, 'Chrysoperla carnea (Neuroptera: Chrysopidae)'),
(29, 'Delphastus pusillus (Coleoptera: Coccinellidae)'),
(30, 'Encarsia formosa (Hymenoptera: Aphelinidae)'),
(31, 'Phytoseiulus macropilis (Acari: Phytoseiidae)'),
(32, 'Neoseiulus californicus (Acari: Phytoseiidae)'),
(33, 'Opius scabriventris (Hymenoptera: Braconidae)'),
(34, 'Protonectarina sylveirae (Hymenoptera: Vespidae)'),
(35, 'Polybia scutellaris (Hymenoptera: Vespidae)'),
(36, 'Prorops nasuta (Hymenoptera: Bethylidae)'),
(37, 'Cephalonomia stephanoderis (Hymenoptera: Bethylidae)');

-- --------------------------------------------------------

--
-- Estrutura da tabela `MetodoDeControle`
--

CREATE TABLE `MetodoDeControle` (
  `Cod_MetodoControle` int(11) NOT NULL,
  `Nome` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `MateriaisNecessarios` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `ModoDePreparo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `IntervaloAplicacao` int(11) NOT NULL,
  `EfeitoColateral` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Observacoes` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Atuacao` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `MetodoDeControle`
--

INSERT INTO `MetodoDeControle` (`Cod_MetodoControle`, `Nome`, `MateriaisNecessarios`, `ModoDePreparo`, `IntervaloAplicacao`, `EfeitoColateral`, `Observacoes`, `Atuacao`) VALUES
(1, 'Calda de sal', '5g de sal (1 colher de chá); 20 ml de vinagre (4 colheres de chá); 1 litro de água; 2,5 ml de sabão líquido neutro (1/2 colher de chá).', 'Mistura dos ingredientes em uma única operação em uma vasilha. Aplique à noite ou em dias nublados.', 7, 'Não aplicar com frequência o preparado de sal para não salinizar o solo.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Mosca branca (Bemisia argentifoli) e lagarta do repolho.'),
(2, 'Extrato aquoso de sementes de Nim (Azadirachta indica) 1', '1kg de sementes de nim moídas; 1litro de água; 2g de sabão neutro ou de cinzas.', 'Para imersão do nim em água, coloque as sementes moídas em um pano no formato de um saquinho, amarre o saquinho e mergulhe-o na água. Deixe em repouso por 12 horas. Depois esprema o saquinho para a extração do óleo das sementes; dilua o sabão no extrato e misture bem; acrescente a solução a 20 litros de água; pulverize imediatamente sobre as plantas atacadas.', 5, 'Possui ação tóxica sobre alguns agentes polinizadores.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Mosca branca (Bemisia argentifoli), Mosca minadora (Liromyza sativae), traça das crucíferas (Plutella xylostela), Traça do tomateiro (Tuta absoluta), Broca pequena do fruto (Neoleucinodes elegantalis); Curuquerê-da-couve (Ascia monuste orseis) e lagartas em geral.'),
(4, 'Extrato de Pimenta-do-reino (Piper nigrum)', '100 gramas de pimenta do reino moídas; 1 litro de álcool; 25 gramas de sabão neutro.', 'Pegar 100 gramas de pimenta do reino e juntar a 1 litro de álcool em vidro ou garrafa, com tampa. Deixar em repouso por uma semana; Dissolver 25 gramas de sabão neutro em 1 litro de água quente. Na hora de usar, pegar um copo de extrato de pimenta do reino, a solução de sabão, diluir em 10 litros de água, agitando a mistura e pulverizar. \r\nPara melhorar o efeito de proteção desta calda contra insetos pode-se adicionar o extrato alcoólico de alho a calda antes da pulverização, sendo recomendado, principalmente para a cultura do tomateiro; Triturar 100 gramas de alho e juntar a 1 litro de álcool em vidro ou garrafa, com tampa. Deixar em repouso por uma semana; Na hora de usar, pegar um copo de extrato de pimenta do reino, ½ copo de extrato de alho, a solução de sabão, diluir em 10 litros de água, agitar a mistura e pulverizar; Para o caso das duas receitas, antes de usá-las deve-se observar se estão ocorrendo inimigos naturais das pragas nas culturas e se estes, sozinhos não estão sendo eficientes no controle.', 3, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Bicho mineiro (Leucoptera coffeella), Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Tripes schultzei (Frankliniella schultzei), lagartas e cigarrinha das solanáceas.'),
(5, 'Extrato de Ryania (Ryania speciosa)', '30 a 40 gramas de pó de Ryania (talos e raízes); 08 litros de água.', 'Misturar o pó de Ryania em água, filtrar e aplicar.', 12, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Mosca das frutas (Anastrepha spp), Lagarta do cartucho (Spodoptera frugiperda), Curuquerê-da-couve (Ascia monuste orseis), Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Tripes schultzei (Frankliniella schultzei), Piolho da cebola (Thrips tabaci) e Podridão das raízes (Phytophthora drechsleri).'),
(6, 'Beauveria bassiana', 'Produto comercial.', 'Ver recomendação do Fabricante.', 0, 'Vide rótulo.', 'Inseticida microbiológico.  Número, época de aplicação e intervalo de aplicação varia de acordo com o distribuidor.', 'Mosca branca (Bemisia argentifoli), Ácaro-rajado (Tetranychus urticae), Cigarrinha-do-milho (Dalbulus maidis), Gorgulho-do-eucalipto (Gonipterus scutellatus), Moleque-da-bananeira (Cosmopolites sordidus), Broca-do-café (Hypothenemus hampei) e Cochonilha (Coccus viridis).'),
(7, 'Extrato aquoso de sementes de Nim (Azadirachta indica) 2', '25g a 50g de sementes de nim moídas; 1 litro de água.', 'Coloque as sementes moídas em um pano no formato de um saquinho. Amarre o saquinho e mergulhe-o na água. Deixe em repouso por 1 dia. Para a extração do óleo de nim, esprema o saquinho e misture o líquido extraído na água. Pulverize o extrato aquoso sobre a planta atacada.', 5, 'Possui ação tóxica sobre alguns agentes polinizadores.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Traça-do-tomateiro (Tuta absoluta), Broca-pequena do fruto (Neoleucinodes elegantalis), Broca grande do fruto (Helicoverpa zea), lagartas e gafanhotos.'),
(8, 'Calda de farinha de trigo', '20 g farinha de trigo (1 colher de sopa); 1 litro de água.', 'Misture os ingredientes em uma única operação em uma vasilha e aplique pela manhã com orvalho.', 15, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Traça do tomateiro (Tuta absoluta), Broca-pequena do fruto (Neoleucinodes elegantalis), Broca grande do fruto (Helicoverpa zea), lagartas e ácaros.'),
(9, 'Bacillus thuringiensis subspec thuringiensis e bacillus thuringiensis subspec. aizawai', 'Produto comercial.', 'Ver recomendação do fabricante.', 0, 'Vide rótulo.', 'Inseticida microbiológico. Número, época de aplicação e intervalo de aplicação varia de acordo com o distribuidor.', 'Curuquerê (Alabama argilácea), Broca grande do fruto (Helicoverpa zea), Lagarta-da-soja (Anticarsia gemmatalis), Curuquerê-da-couve (Ascia monuste orseis), Mandarová (Erinnys ello), Traça-do-tomateiro (Tuta absoluta) entre outros. Alternar entre as duas subspecies.'),
(10, 'Trichogramma pretiosum', 'Produto comercial.', 'Ver recomendação do fabricante.', 0, 'Vide rótulo.', 'Inseticida microbiológico. Número, época de aplicação e intervalo de aplicação varia de acordo com o distribuidor.', 'Lagarta-da-soja (Anticarsia gemmatalis), Falsa-Medideira (Pseudoplusia includens), Traça-do-tomateiro (Tuta absoluta), Lagarta-do-cartucho (Spodoptera frugiperda), Broca grande do fruto (Helicoverpa zea) e Broca-pequena do fruto (Neoleucinodes elegantalis).'),
(11, 'Extrato de Cebolinha (Allium fistulosum L.)', '1 kg de cebolinha verde; 10 litros de água.', 'Cortar a cebolinha e misturar em 10 litros de água, deixando o preparado curtir durante 7 dias. Para pulverizar as plantas, diluir 1 litro da mistura para 3 litros de água. ', 15, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), lagartas e vaquinhas.'),
(12, 'Extrato de Timbó /Arruda/ Losna Branca (Derris ssp./ Ruta graveolens/ Artemisia absinthium)', '50 gramas de timbó ou arruda ou losna branca; 50 ml de acetona; 900 ml de álcool.', 'Picar o vegetal (apenas um dos citados), misturar com acetona e esmagar. Acrescentar álcool e deixar descansar por 2 dias. A cada litro do preparado, colocar 10 a 15 litros de água e pulverizar.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae) Tripes schultzei (Frankliniella schultzei), lagartas e ácaros.'),
(13, 'Extrato de Fruta-do-conde (Annona retículeta, A. muricata)', 'Óleo de sementes de Anona; 9L de água.', 'Diluir 1 litro de óleo de anona em 9 litros de água. Aplicar logo em seguida.', 7, 'Sem referência. ', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Traça das crucíferas (Plutella xylostella), gafanhotos, besouros e piolhos.'),
(14, 'Extrato de Jacatupé (Pachyrhizus tuberosus L. Spreng.)', '100 gramas de sementes de Jacatupé; 250 ml solução de água + álcool (9:1)', 'Moer as sementes e deixá-las em solução de água + álcool (9:1) por 24 horas. Filtrar com pano fino e diluir o preparado na proporção de uma parte do preparado para 5 de água e aplicar ao solo ou na cultura afetada.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Curuquerê-da-couve (Ascia monuste orseis), Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae) e Saúvas.'),
(15, 'Extrato de Jacatupé Bravo (Pachyrrhizus erosus L. Urban.)', '2 kg de sementes de jacatupé bravo; 20 litros de água.', 'Misturar as sementes moídas de jacatupé-bravo em água e deixar descansar esta mistura, por um dia. Filtrar e completar para 400 litros de água. Pulverizar logo em seguida.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Traça do tomateiro (Tuta absoluta), Traça das crucíferas (Plutella xylostella) e Curuquerê-da-couve (Ascia monuste orseis).'),
(16, 'Extrato de Abricó-do-pará (Mammea americana)', '4 kg de sementes moídas; 420 litros de água; 0,5 kg de substância adesiva (sabão).', 'Misturar as sementes moídas de Abricó em 20 litros de água e deixar descansar por 12 horas. Coar e diluir esta solução para 400 litros de água com substância adesiva.', 7, 'Sem referência.', ' Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Curuquerê-da-couve (Ascia monuste orseis).'),
(17, 'Extrato de Sálvia (Salvia officinales L.) ', 'Folhas de Sálvia; 1 litro de água.', 'Derramar 1 litro de água fervente sobre 2 colheres (de sopa) de folhas secas de sálvia. Tampar o recipiente e deixar em infusão durante 10 minutos. Agitar bem, filtrar e pulverizar imediatamente sobre as plantas.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Curuquerê-da-couve (Ascia monuste orseis).'),
(18, 'Extrato aquoso de Alho (Allium sativum)', '1 dente de alho; 2 litros de água.', 'Bata o alho no liquidificador com água (2 litros para cada dente). Em seguida pulverize as plantas atacadas.', 7, 'Não utilizar sobre feijões, pois o alho inibe seu crescimento.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Broca grande do fruto (Helicoverpa zea) e Broca-pequena do fruto (Neoleucinodes elegantalis), Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), cochonilhas e ácaros.'),
(19, 'Extrato aquoso de Cinamomo a 10% (Melia azedarach L.)', '100g de folhas e frutos de Cinamomo; 1 litro de água.', 'Macere as folhas e frutos de Cinamomo em água, faça infusão por 24 horas, coe e pulverize na cultura desejada, semanalmente.', 7, 'As folhas e frutos do cinamomo são tóxicas e sua ingestão pode causar aumento da salivação, náuseas, vômitos, cólicas abdominais, diarréia intensa; em casos graves pode ocorrer depressão do sistema nervoso central. Verificar com a certificadora orgânica se é liberada a aplicação.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae) e Curuquerê-da-couve (Ascia monuste orseis).'),
(20, 'Extrato de Cravo-de-defunto (Tagetes erecta)', '100g de folhas e/ou talo de cravo-de-defunto; 1 litro de água.', 'Misture as folhas e/ou talos de cravo-de-defunto em 1 litro de água. Leve ao fogo e deixe ferver durante meia hora. Coe o caldo obtido e pulverize as plantas atacadas.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), ácaros e algumas lagartas'),
(21, 'Extrato de Pimenta-malagueta (Capsicum frutescens)', '500g de pimenta vermelha (malagueta); 4 litros de água; 5 colheres (sopa) de sabão de coco em pó.', 'Bater as pimentas em um liquidificador com 2 litros de água até a maceração total. Coar o preparado e misturar com o sabão de coco em pó, acrescentando então os 2 litros de água restantes. Pulverizar sobre as plantas atacadas.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Vaquinha (Diabrotica speciosa) e cochonilha.'),
(22, 'Extrato alcoólico de alho (Allium sativum) ', '100g de alho picado, 1L de álcool, 50g de sabão de coco, 20L de água, 1L de água morna.', 'Deixar o alho embebido no álcool durante 7 dias. Coar e diluir 100 mL da solução de alho em 20 L de água com a adição de 50g de sabão de coco dissolvido em 1 L de água quente na hora da aplicação.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Traça das crucíferas (Plutella xylostella). '),
(23, 'Extrato aquoso de pó de casca de pereiro (Aspidosperma pyrifolium)', '100g de casca de pereiro, 1L de água.', 'Coletar a casca de pereiro e deixar secar por alguns dias, picar em pequenos pedaços (se possível em moinho de facas, sendo o pó peneirado em peneira de 0,8 mm). Misturar 100 g da casca moída e 1L de água, e deixar em repouso durante 12 horas. Após o período de descanso, coe o conteúdo com um tecido fino tipo \'voile\' e aplique sobre a cultura.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Traça das crucíferas (Plutella xylostella).'),
(24, 'Extrato de Pessegueiro (Prunus persica Batsch.)', '1 Kg de folhas de pessegueiro; 5 litros de água.', 'Misturar as folhas de pessegueiro em 5 litros de água e deixar ferver durante meia hora. Para pulverizar as plantas utilize 1 litro do produto em 5 litros de água.', 7, 'Sem referência.', 'Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Pulgão-verde (Myzus persicae), Pulgão-das-inflorescências (Aphis gossypii), Pulgão-das-solanáceas (Macrosiphum euphorbiae), Vaquinha (Diabrotica speciosa) e lagartas.'),
(25, 'Calda Sulfocálcica ', '25 kg de enxofre ventilado; água quente; 80 L de água; 12,5 kg de cal virgem.', 'Para o preparo de 100 L de calda Sulfocálcica: misturar 25 kg de enxofre ventilado com água quente até adquirir consistência pastosa. Posteriormente, deve-se adicionar à pasta de enxofre, 80 L de água e aquecer a mistura até cerca de 50º C, quando deve ser adicionado 12,5 kg de cal virgem. Após o início da fervura, mexer durante uma hora e sempre completar com água fria até o nível de 100 L. Quando a coloração da calda tornar-se pardo-avermelhada, retirar do fogo e deixar esfriar. Finalmente, deve-se coar em pano de algodão. Para o preparo de quantidades menores da calda, devem ser feitas reduções proporcionais nas quantidades dos ingredientes. A pessoa que estiver preparando a calda deve estar devidamente protegida, usando equipamentos de proteção individual (EPIs), pois há emissão de gases durante o processo de preparo que podem causar intoxicação. O contato da calda com a pele poderá causar irritações e queimaduras. Antes de ser armazenada, deve-se medir a concentração da calda. Para isso, o agricultor poderá utilizar o densímetro ou aerômetro de Baumé. A calda ideal possui densidade de 32º Baumé, mas densidades de 29º ou 30º Baumé são consideradas boas. Acima ou abaixo dessas densidades, a calda não apresenta os efeitos esperados. Posteriormente, deve ser guardada em garrafas de vidro ou recipientes plásticos, devidamente vedados, pois a entrada de ar provoca decomposição dos polissulfetos. A calda deve ser armazenada em local fresco e escuro, sendo ideal sua utilização por um período de até 60 dias após o preparo.', 15, 'sem referência.', 'Utilizar EPI, no manuseio e na aplicação da calda, pois trata-se de uma mistura cáustica; misturar bem a calda e manter boa agitação no tanque do pulverizador durante a aplicação; não pulverizar com floradas abertas; utilizar a calda quando a temperatura ambiente for maior que 18ºC, pois abaixo desta a ação fumigante é prejudicada, e menor que 30ºC, pois poderão ocorrer injúrias nos tecidos mais sensíveis da planta; não misturar a calda com produtos que não tolerem meio alcalino, com óleo mineral ou vegetal, com sais micronutrientes ou com fertilizantes foliares; respeitar um intervalo mínimo de 15 dias, para aplicações subseqüentes de outros produtos; proteger o equipamento de pulverização, com óleo diesel ou similar, antes da utilização da calda; aplicar o produto no mesmo dia em que for feita a diluição no tanque de pulverização; lavar o pulverizador com solução de ácido cítrico anidro a 20% ou solução de vinagre ou limão a 10%, após o uso; não descartar os excedentes em nascentes, cursos d’água, açudes ou poços. Importante aplicar sempre na parte da planta onde se encontra o inseto e cada produto deve ser utilizado durante o ciclo completo da praga de modo a atuar apenas sobre uma geração da praga, sendo substituído por outro, caso seja necessária a continuidade das pulverizações. Não pulverizar as caldas nas horas mais quentes do dia (entre 10:00 e 16:00 horas); na hora de pulverizar usar roupa de proteção (isto é válido para qualquer tipo de produto, natural ou sintético). Utilizar como período de carência (período entre a última aplicação e a colheita) o intervalo entre as aplicações referente a cada método de controle.', 'Bicho mineiro do café (Leucoptera coffeella).'),
(26, 'Armadilhas de pseudocaule de bananeira', 'Pseudocaules de bananeiras que já produziram', 'Existem dois tipos de iscas mais comuns, conhecidas como “telha” e “queijo”.  Armadilha tipo “queijo” é confeccionada rebaixando-se o pseudocaule de planta colhida a uma altura de 40 a 60 cm do solo, e efetuando-se um novo corte no sentido longitudinal, a cerca de 15 cm da base. Esse corte deve ser de preferência parcial, para evitar o tombamento da parte superior da armadilha.  A armadilha tipo “telha” é cada metade de um pedaço de pseudocaule, de aproximadamente 60 cm de comprimento, partido ao meio no sentido longitudinal. Dessa forma, cada pedaço de pseudocaule fornece duas armadilhas, as quais devem ser distribuídas com a face cortada em contato com o solo, na base da planta. ', 15, 'sem referência.', 'Para controle, é recomendado o uso de 50 a 100 armadilhas/ha, sendo distribuídas durante todo o ano, a depender da infestação do bananal. As coletas dos insetos devem ser feitas manualmente, no mínimo, uma vez por semana, e, após destruídos, quando não forem utilizados produtos químicos ou biológicos para seu controle. Todas as armadilhas não tratadas com produtos químicos ou biológicos devem ser destruídas após os quinze dias da confecção das mesmas para evitar a multiplicação da praga.', 'Broca do rizoma/moleque da bananeira (Cosmopolites sordidus).');

-- --------------------------------------------------------

--
-- Estrutura da tabela `PlanoAmostragem`
--

CREATE TABLE `PlanoAmostragem` (
  `Cod_Plano` int(11) NOT NULL,
  `Autor` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Data` date NOT NULL,
  `PlantasInfestadas` int(11) DEFAULT NULL,
  `PlantasAmostradas` int(11) DEFAULT NULL,
  `fk_Talhao_Cod_Talhao` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `PlanoAmostragem`
--

INSERT INTO `PlanoAmostragem` (`Cod_Plano`, `Autor`, `Data`, `PlantasInfestadas`, `PlantasAmostradas`, `fk_Talhao_Cod_Talhao`, `fk_Praga_Cod_Praga`) VALUES
(257, NULL, '2020-03-24', 10, 10, 77, 10),
(258, NULL, '2020-03-25', 15, 33, 77, 10),
(259, NULL, '2020-03-26', 15, 23, 77, 10),
(260, NULL, '2020-03-27', 24, 24, 77, 10),
(261, NULL, '2020-03-28', 26, 38, 77, 10),
(262, NULL, '2020-03-29', 12, 12, 77, 10),
(263, NULL, '2020-03-29', 1, 2, 77, 1),
(264, NULL, '2020-03-29', 2, 4, 10, 1),
(265, NULL, '2020-03-29', 1, 2, 13, 1),
(266, NULL, '2020-03-29', 1, 2, 11, 1),
(267, NULL, '2020-03-29', 1, 2, 12, 1),
(272, NULL, '2020-03-29', 1, 21, 67, 1),
(275, NULL, '2020-03-29', 2, 100, 80, 1),
(286, NULL, '2020-03-30', 1, 100, 13, 1),
(287, NULL, '2020-03-30', 0, 100, 10, 1),
(288, NULL, '2020-03-30', 0, 100, 11, 1),
(289, NULL, '2020-03-30', 0, 100, 12, 1),
(292, NULL, '2020-03-30', 5, 7, 77, 1),
(293, NULL, '2020-03-31', 10, 15, 77, 1),
(294, NULL, '2020-04-01', 2, 50, 77, 1),
(295, NULL, '2020-04-02', 5, 200, 77, 1),
(297, NULL, '2020-04-04', 17, 33, 77, 1),
(298, NULL, '2020-03-31', 16, 46, 77, 11),
(300, NULL, '2020-04-01', 4, 4, 77, 3),
(306, NULL, '2020-04-02', 1, 40, 12, 7),
(307, NULL, '2020-04-02', 3, 40, 10, 7),
(308, NULL, '2020-04-02', 2, 40, 13, 7),
(309, NULL, '2020-04-02', 1, 6, 11, 7),
(310, NULL, '2020-04-06', 5, 40, 77, 3),
(311, NULL, '2020-04-03', 1, 57, 38, 1),
(312, NULL, '2020-05-02', 10, 50, 77, 11),
(313, NULL, '2020-04-05', 5, 100, 77, 10),
(314, NULL, '2020-04-05', 6, 100, 77, 1),
(315, NULL, '2020-04-06', 3, 3, 13, 1),
(316, NULL, '2020-04-06', 3, 3, 12, 1),
(317, NULL, '2020-04-06', 4, 5, 10, 1),
(318, NULL, '2020-04-06', 4, 4, 11, 1),
(319, NULL, '2020-04-09', 5, 10, 10, 1),
(320, NULL, '2020-04-09', 2, 10, 11, 1),
(321, NULL, '2020-04-09', 3, 10, 12, 1),
(322, NULL, '2020-04-09', 1, 10, 13, 1),
(323, 'Rodolfo Chagas Marinho Nascimento', '2020-04-07', 7, 100, 77, 21),
(324, 'Rodolfo Chagas Marinho Nascimento', '2020-04-07', 1, 26, 80, 1),
(325, 'Rodolfo Chagas Marinho Nascimento', '2020-04-07', 1, 1, 77, 1),
(326, 'proprietario', '2020-04-09', 26, 50, 89, 6),
(327, 'proprietario', '2020-04-09', 30, 50, 90, 6),
(329, 'ze', '2020-04-19', 14, 23, 92, 1),
(330, 'ze', '2020-04-19', 3, 12, 93, 1),
(331, 'ze', '2020-04-19', 7, 11, 95, 1),
(332, 'ze', '2020-04-19', 33, 100, 94, 1),
(345, 'Rodolfo Chagas Marinho Nascimento', '2020-04-01', 50, 100, 96, 1),
(346, 'Rodolfo Chagas Marinho Nascimento', '2020-04-01', 20, 100, 97, 1),
(347, 'Rodolfo Chagas Marinho Nascimento', '2020-04-01', 30, 100, 98, 1),
(348, 'Rodolfo Chagas Marinho Nascimento', '2020-04-01', 10, 100, 99, 1),
(349, 'José Carlos', '2020-04-15', 40, 100, 96, 1),
(350, 'José Carlos', '2020-04-15', 15, 100, 97, 1),
(351, 'José Carlos', '2020-04-15', 26, 100, 98, 1),
(352, 'José Carlos', '2020-04-15', 9, 100, 99, 1),
(353, 'Funcionário 1', '2020-04-29', 30, 100, 96, 1),
(354, 'Funcionário 1', '2020-04-29', 10, 100, 97, 1),
(355, 'Funcionário 1', '2020-04-29', 16, 100, 98, 1),
(356, 'Funcionário 1', '2020-04-29', 5, 100, 99, 1),
(357, 'Rodolfo Chagas Marinho Nascimento', '2020-05-08', 20, 100, 96, 1),
(358, 'Rodolfo Chagas Marinho Nascimento', '2020-05-08', 12, 100, 97, 1),
(359, 'Rodolfo Chagas Marinho Nascimento', '2020-05-08', 15, 100, 98, 1),
(360, 'Rodolfo Chagas Marinho Nascimento', '2020-05-08', 7, 100, 99, 1),
(363, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', 8, 8, 77, 7),
(364, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', 3, 3, 77, 1),
(365, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', 6, 6, 77, 11),
(366, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', 34, 34, 13, 3),
(367, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', 20, 20, 10, 3),
(368, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', 22, 22, 11, 3),
(369, 'Rodolfo Chagas Marinho Nascimento', '2020-05-18', 22, 23, 12, 3),
(370, 'proprietario', '2020-05-22', 3, 20, 89, 6),
(371, 'proprietario', '2020-05-22', 1, 1, 90, 6),
(374, 'Rodolfo Chagas Marinho Nascimento', '2020-05-28', 49, 100, 77, 11),
(377, 'Monica Simão Giponi', '2020-06-02', 99, 50, 114, 25);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Planta`
--

CREATE TABLE `Planta` (
  `Cod_Planta` int(11) NOT NULL,
  `Nome` text COLLATE utf8_unicode_ci NOT NULL,
  `NomeCientifico` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Familia` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Botanica` text COLLATE utf8_unicode_ci NOT NULL,
  `AmbientePropicio` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Cultivo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `TratosCulturais` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Ciclo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `TamanhoTalhao` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Planta`
--

INSERT INTO `Planta` (`Cod_Planta`, `Nome`, `NomeCientifico`, `Familia`, `Botanica`, `AmbientePropicio`, `Cultivo`, `TratosCulturais`, `Ciclo`, `TamanhoTalhao`) VALUES
(1, 'Tomate (tutorado)', 'Solanum lycopersicum', 'Solanaceae', 'Está espécie possui um grande número de variedades, com diferentes adaptações climáticas e características morfológicas. Possui porte arbustivo e crescimento vegetativo variado entre determinado e indeterminado. Sendo os cultivares de crescimento determinado caracterizados pelo crescimento limitado, conhecidos como tomate rasteiro. As plantas de crescimento indeterminado têm seu cultivo tutorado para que a planta fique ereta e os frutos não mantenham contato com o solo. ', 'Pode se desenvolver em temperaturas entre 10°C e 34°C. A coloração e a firmeza do fruto de tomate são muito influenciadas por fatores ambientais, não devendo cultiva-los fora da faixa recomendada de temperatura. A cultura tem seu desenvolvimento influenciado também pela radiação solar com um nível relativamente alto. ', 'Pode ser cultivado em qualquer tipo de solo, desde que sem impedimento físico e recomenda-se serem ricos em matéria orgânica, com boa retenção de umidade e bem drenados. Deve-se fazer análise do solo antes do plantio para cálculos de recomendação de correção de pH e adubação. O pH ideal para desenvolvimento da cultura encontra-se entre 5,5 e 7,0, variando dependendo do cultivar e a saturação de bases em torno de 70%. A calagem deve ser feita sempre com antecedência de 60 a 90 dias e sua ação só ocorre na presença de umidade. O preparo do solo convencional consiste em uma ou duas arações, seguidas ou não de uma ou duas gradagens. O transplantio geralmente é feito em sulcos ou covas com espaçamento flexível, adotando espaçamentos maiores no verão quando as temperaturas e umidade são elevadas e se deseja produzir frutos com maior peso (1,10 a 1,20 m entre fileiras por 0,60 a 0,70 m entre plantas) para cultivo em campo aberto. Para cultivo em ambiente protegido o espaçamento sugerido para cultivares de hábito de crescimento determinado e linha simples conduzida em uma única haste é de 1,00 a 1,10 m entre fileiras e de 0,30 a 0,50 m entre plantas na casa de vegetação. Quando a condução for de duas hastes, sugere-se o espaçamento entre plantas de 0,40 a 0,50 m. ', 'A adubação orgânica deve ser feita com a antecedência de pelo menos quinze dias antes do transplantio, deve ser realizada com base na análise de solo e na demanda da cultura. O amontoa deve ser feito de 15 a 20 dias após o transplantio, já tendo sido feita a primeira adubação de base, e se refere a ação de chegar terra no pé da planta para aumentar o sistema radicular e a capacidade de absorver nutrientes. O amarrio é feito com fitilho de polietileno e não pode causar o estrangulamento do caule, tem como objetivo garantir a correta condução da planta, geralmente fazem-se cerca de cinco a seis amarrios até o topo. Existem ferramentas que facilitam a operação de amarrio por serem de fácil utilização, como por exemplo o alceador. A desbrota é feita com a eliminação dos brotos quando estes estão com 2 a 5 cm laterais que surgem nas axilas de cada folha, realizando a quebra dos mesmos, com a finalidade de diminuir a competitividade entre os frutos e induzir o crescimento uniforme, além de facilitar a aeração e o controle fitossanitário. A poda ou capação é uma operação que consiste na eliminação do broto terminal das hastes, realizada exclusivamente em materiais de hábito de crescimento indeterminado. Com a poda tem-se maior controle do crescimento da planta, sobre a floração e frutificação, limitando o número de pencas, garantindo frutos mais graúdos. A poda das folhas no tomateiro é recomendada para melhorar o arejamento, aumentado a eficiência fotossintética e principalmente reduzindo os riscos de incidência de pragas e doenças. A eliminação das folhas deve ser feita de baixo para cima, devendo ser cortadas apenas aquelas abaixo das pencas já colhidas. O raleio dos frutos é indicado para reduzir, entre eles, a competitividade por assimilados na planta. São deixados na planta os frutos com maior potencial para bom desenvolvimento. A cobertura do solo é realizada como forma de proteger o solo e o tomateiro de plantas invasoras e agentes causadores de doenças. O tutoramento é a adotado para favorecer a entrada de ar, menor incidência de problemas causados por doenças e pragas, maior facilidade de controle fitossanitário, evitar pisoteio de frutos e tombamento com a ação do vento. O uso de irrigação por aspersão pode ser utilizado para fins de controle mecânico de pulgões, tripes, mosca-branca e lagartas presentes nas folhas.', 'O ciclo do tomate pode variar de 95 a 125 dias dependendo do cultivar.', 0.5),
(3, 'Pimentão ', 'Capsicum annuum L.', 'Solanaceae', 'Possui porte arbustivo, altura de 0,50 a 1,50 m de altura, pode ser classificada como anual ou perene (se podada no primeiro ano), a propagação é feita por meio de sementes e fecunda-se preferencialmente por autopolinização. ', 'A cultura é de clima tropical e desenvolve-se e produz sob temperaturas elevadas ou amenas, possui o desenvolvimento ótimo sob temperaturas diurnas de 20 a 25ºC e noturnas 16 a 18ºC. O fotoperíodo não tem função limitante para essa cultura. Seu plantio é feito geralmente na primavera-verão, podendo se estender ao longo do ano em regiões de baixa altitude, com inverno ameno.', 'A cultura desenvolve-se melhor em solos de textura média, profundo, bem drenado. Deve-se fazer análise do solo antes do plantio para cálculos de recomendação de correção de pH e adubação. O pH ideal para desenvolvimento da cultura encontra-se entre 5,5 e 6,8 e saturação por bases em torno de 70%. A calagem deve ser feita sempre com antecedência de 60 a 90 dias e sua ação só ocorre na presença de umidade. O preparo do solo convencional pode ser feito com uma ou duas arações e duas gradagens. O plantio pode ser feito em sulcos ou covas, o espaçamento recomendado é de 80 a 120 cm entre as linhas e 40 a 60 cm entre as plantas, variando com o cultivar e as condições de cultivo.', 'A adubação orgânica de plantio pode ser realizada 10 dias antes do transplante das mudas, as adubações de cobertura podem ser realizadas aos 45 e 90 dias após o transplante. Deve-se fazer o controle de espontâneas conhecidas com hospedeiras de pragas e patógenos da cultura. O tutoramento deve ser realizado devido o porte da planta como forma de evitar o tombamento ou quebra, sendo realizado com o uso de uma estaca de bambu ou outro material, amarrando as hastes conforme o crescimento da planta, cuidando para não ocasionar ferimentos na planta, que favorecem a entrada de fitopatógenos. A desbrota têm a função de equilibrar o crescimento dos frutos, tirando os ramos e brotos abaixo da primeira bifurcação ao longo do crescimento da planta. O desbaste dos frutos deve ser efetuado estando os mesmos ainda em desenvolvimento, quando ocorrerem má formação ou anomalias fisiológicas, mantendo apenas aqueles pimentões bem formados e sem defeitos.', 'Desde a semeadura até o início da colheita dos frutos verdes é de 100 a 110 dias.', 1),
(4, 'Berinjela', 'Solanum melongena L.', 'Solanaceae', 'A berinjela possui porte arbustivo, altura de 0,5 a 1,8 m, pode ser classificada como anual ou perene (se podada no primeiro ano), a propagação é feita por meio de sementes ou dos ramos axilares através da estaquia, e fecunda-se preferencialmente por autopolinização. ', 'A cultura é de clima tropical e subtropical e tem preferência por regiões de clima quente, com temperatura média diurna de 25-35°C e noturna de 20-27°C e umidade relativa do ar de 80%, nestas condições pode ser cultivada durante todo o ano. Em locais com temperatura média inferior a 18°C no inverno, o plantio deve ser realizado na primavera ou verão.', 'A cultura desenvolve-se melhor em solos de textura média, profundos, ricos em matéria orgânica, com boa retenção de umidade e bem drenados. Deve-se fazer análise do solo antes do plantio para cálculos de recomendação de correção de pH e adubação. O pH ideal para desenvolvimento da cultura encontra-se entre 5,5 e 6,5 e saturação por bases em torno de 70%. A calagem deve ser feita sempre com antecedência de 60 a 90 dias e sua ação só ocorre na presença de umidade. O preparo do solo convencional consiste em uma a duas arações e duas gradagens. O plantio pode ser feito em sulcos ou covas com 20 cm de profundidade, o espaçamento recomendado é de 1,2 a 1,5 m entre linhas e de 0,7 a 1,0 m entre as mudas. ', 'A adubação de plantio deve ser realizada 10 dias antes do transplante das mudas, as adubações de cobertura podem ser realizadas aos 45 e 90 dias após o transplante. Deve-se fazer o controle de espontâneas conhecidas com hospedeiras de pragas e patógenos da cultura. O tutoramento deve ser realizado devido o porte da planta como forma de evitar o tombamento ou quebra, sendo realizado com o uso de uma estaca de bambu ou outro material, amarrando as hastes conforme o crescimento da planta, tomando cuidado para não danifica-la. A desbrota na Berinjela têm a função de equilibrar o crescimento dos frutos, tirando os ramos e brotos abaixo da primeira bifurcação ao longo do crescimento da planta.', 'A colheita inicia-se de 90 a 110 dias da semeadura, a cada 4 ou 5 dias, mas no verão, a cada 2 dias. Dependendo do vigor da planta, a colheita pode durar até 3 meses.', 0.5),
(5, 'Couve comum', 'Brassica oleracea L. var. acephala', 'Brassicaceae', 'A couve de folha é uma hortaliça anual ou bienal, com inúmeros cultivares que variam em coloração, formato e tamanho de planta. É uma hortaliça arbustiva, herbácea, com caule vertical que sempre emite novas folhas em seu ápice e não forma cabeça como em repolho. É comum encontrar plantas com mais de 2 metros de altura e podem chegar a mais de 5 metros.', 'A couve é uma cultura típica de outono-inverno, bem adaptada ao frio intenso e resistente à geada. No verão se desenvolve bem em áreas serranas, com altitudes acima de 800 m. A produção da couve é melhor quando as temperaturas médias mensais se situam entre 16 e 22 °C, com temperaturas mínimas de 5 a 10 °C e temperaturas máximas de 28 °C.', 'A couve de folha pode ser propagada por sementes ou por mudas, dependendo da cultivar, geralmente é cultivada através da propagação vegetativa, com a formação de mudas a partir de brotos que surgem nas axilas das folhas. Os brotos podem ser enraizados em saquinhos de plástico ou de jornal, com 5 a 6 cm de diâmetro e 10 a 15 cm de altura, ou ainda em bandejas de plástico com 128 células, preenchidas com substratos ou solo rico em matéria orgânica. Na fase de pegamento das mudas é necessário irrigar várias vezes ao dia para garantir um bom enraizamento destas, até que possam ser transplantadas para o local definitivo. Na formação de mudas a partir de sementes recomenda-se utilizar bandejas de plástico de 128 células, com substrato comercial à base de casca de pinus ou de fibra de coco. Realizar a fertirrigação até as plantas alcançarem 4 a 6 pares de folhas definitivas e 10 a 15 cm de altura. O espaçamento recomendado em plantios comerciais de couve é de 80 a 100 cm entrelinhas por 50 a 70 cm entre plantas. Recomenda-se realizar anualmente a análise química do solo de 0 a 20 cm de profundidade, amostrando-se em separado as diferentes glebas e arquivando os resultados da análise para a rastreabilidade da produção. Realizar a aplicação de calcário, de 30 a 90 dias antes do plantio (conforme o PRNT do corretivo), para elevar a saturação por bases a 80% e o teor de magnésio a um mínimo de 9 mmolc dm- ³. Distribuir em área total do canteiro misturando bem com a terra, desde a superfície até 20 a 30 cm de profundidade. Para a adubação orgânica pode-se aplicar, cerca de 30 dias antes do plantio das mudas, 20 a 40 t ha-1 de esterco bovino ou 5 a 10 t ha-1 de esterco de galinha ou cama de frango, todos bem compostados e bioestabilizados. O húmus de minhoca deve ser utilizado nas mesmas quantidades em relação ao esterco de galinha. O composto orgânico Bokashi pode ser utilizado na dose de 150 a 250 g por m2 para ajudar na recuperação de solos degradados. No entanto para determinar a quantidade de adubo a ser aplicada para evitar perdas ou carência de nutrientes é importante realizar a análise do solo para determinar a disponibilidade de nutriente, analise do adubo a ser utilizado, e verificar a recomendação para a cultura.  É importante lembrar que a couve é uma hortaliça exigente em boro e molibdênio. O boro deve ser aplicado junto com os fertilizantes de plantio, na dose de 1,0 a 2,5 kg ha-1, conforme o teor deste micronutriente no solo.', 'Na falta de mão de obra para realizar o tutoramento da couve é recomendado a utilização de quebra-ventos. Recomenda-se a implantação de faixas de proteção com bananeiras, pupunheiras e outros palmitos, os quais, além de impedir ou minimizar a passagem de ventos, proporcionam uma renda extra ao produtor. Outras plantas, como palmeiras ornamentais diversas e guandu, podem ser utilizadas como quebra-ventos, devendo-se evitar o plantio de espécies da mesma família botânica da couve. Recomenda-se utilizar a cobertura morta (mulching) para diminuição das perdas de água do solo. Nas operações de colheita, o produtor deve retirar os brotos “ladrões” que surgem nas axilas das folhas, os quais podem ser utilizados para formação de mudas.', 'A colheita das folhas inicia-se de dois a três meses após o transplante das mudas. A colheita é realizada a cada 7-10 dias em uma mesma planta, sendo retiradas as folhas bem desenvolvidas e que estejam no tamanho exigido pelo mercado (20-30 cm de comprimento). A produtividade média da couve é de 3 a 5 kg de folhas por planta, durante o ciclo de 6 a 8 meses.', 1),
(6, 'Brócolis', 'Brassica oleracea var. italica', 'Brassicaceae', 'Os brócolis é uma planta semelhante à couve-flor em sua constituição botânica, possui caule relativamente mais longo, com folhas de nervuras menos salientes e pedúnculos compridos e mais distanciados.  Tem a inflorescência central menos compacta, de colorações que variam do verde ao azulado, com emissão de numerosos rebentos nas axilas das folhas, que terminam em capítulos de flores imperfeitas. Os ápices florais são constituídos de botões com pétalas de coloração amarelada, separadas em quatro. Os estames são organizados em seis longos segmentos. O pistilo é comprido com estigma esférico. O fruto é denominado síliqua, de formato  alongado, que, em seu interior, possui um septo onde estão localizadas as sementes, de formato redondo e de coloração escura, em ambas as suas faces. As cultivares disponíveis para brócolis do tipo ramoso são, predominantemente, de polinização aberta e as do tipo inflorescência única são híbridos simples.', 'O clima frio e úmido é ideal para o desenvolvimento do brócolis, com faixa de temperatura média ótima variando entre 12 e 16 ºC, sendo que temperaturas acima de 20 ºC interferem na formação da inflorescência e abaixo de 0 ºC afetam o desenvolvimento da planta.  Entretanto, existem cultivares adaptadas a clima mais quente. ', 'Para a escolha da área de plantio, não são recomendadas áreas recém-trabalhadas, com resíduos de restos culturais, madeira ou touceiras de capim em decomposição. Os brócolis são hortaliças medianamente resistentes à salinidade. O pH ótimo para seu desenvolvimento oscila entre 6,5 e 7,0. Valores menores aumentam as carências de molibdênio (Mb) e valores maiores aumentam as carências nutricionais, especialmente de elementos como Mn e boro (B). Para os brócolis, como recomendação geral, deve-se aplicar calcário para elevar a saturação por bases a 80% e o teor de Mg a um mínimo de 9 mmol/dm3. A adubação orgânica deve ser realizada com doses de 30 t/ha a 60 t/ha de composto orgânico curtido, a depender do teor de matéria orgânica (MO) do solo. No entanto para determinar a quantidade de adubo a ser aplicada para evitar perdas ou carência de nutrientes é importante realizar a análise do solo para determinar a disponibilidade de nutriente, analise do adubo a ser utilizado, e verificar a recomendação para a cultura.  Após a semeadura em bandeja de 30 a 35 dias, as mudas se encontram em estágio ideal para o transplantio, quando atingem de 12 cm a 15 cm de altura e possuem de quatro a seis folhas definitivas. O brócolis tipo ramoso pode ser plantado em linhas duplas. O espaçamento entre linhas duplas (fileiras) varia de 1 m a 1,20 m, e 0,5 m entre plantas e linhas (fileira), totalizando 23 mil plantas por hectare. Em áreas com textura e umidade do solo adequadas ao uso da mecanização, pode-se realizar o plantio em canteiros. As medidas geralmente são de 1 m ou 1,5 m de largura por 15 cm a 20 cm de altura. Os canteiros podem ser cobertos com mulching, tanto com plástico de cor preta, cinza ou prata, quanto com palha.', 'O período mais crítico de controle de plantas daninhas nessa cultura compreende as primeiras semanas após o transplantio, quando a planta deve permanecer livre de competição para evitar a disputa por água, luz e nutrientes. É crescente o uso do Sistema Plantio Direto em Hortaliças (SPDH), entre elas os brócolis, que consiste no transplantio de mudas sobre a palhada de plantas de cobertura (milheto, milho ou braquiária no verão, e aveia-preta ou trigo no inverno. Sugere-se a inclusão de leguminosas para enriquecer o SPDH em função da fixação biológica de N) previamente roçadas, trituradas e/ou dessecadas, com preparo restrito a covas ou linhas de plantio.', 'A colheita inicia-se por volta dos 50 a 80 dias após o transplante, dependendo da cultivar ou do híbrido utilizado. Os pedúnculos florais devem ser cortados quando com a coloração verde azulada for intensa e os botões estiverem fechados, deixando a haste o mais longo possível. O período de colheita pode durar até 40 dias.', 1),
(7, 'Repolho', 'Brassica oleracea var. capitata', 'Brassicaceae', 'Planta herbácea bianual, às vezes perene, cultivada como anual. Superposição e embricamento das folhas centrais, formando uma cabeça compacta que envolve a gema apical. Atinge 60 a 90 cm de altura quando completa o ciclo. Possui caule ereto, sem ramificações laterais, grosso e curto, com cerca de 10 a 15 cm de comprimento. Cerca de 70 a 80% das raízes encontram-se entre 20 a 30 cm. Inflorescência amarela que se abrem-se ao amanhecer. A formação da cabeça inicia-se 60 a 70 dias, através de um rápido desenvolvimento das folhas internas, cujo número chega a 30, por ocasião da colheita. ', 'As regiões de clima temperado e úmido são mais adequadas ao seu cultivo. Baixas temperaturas (5ºC) induzem a formação de maiores quantidades de acúcares nas folhas e maiores quantidade de proteínas, sobretudo albumina. Muito resistente ao frio, quando em pleno vigor e bem formada. suporta temperaturas de -6,5°C ou -15°C por curtos períodos de tempo. No estádio inicial de crescimento (1 a 2 folhas definidas), é susceptível à geadas, causam danos à gema apical e às folhas mais velhas. A temperatura ótima para crescimento entre 15 a 21°C, para cultivares de outono-inverno, abaixo e acima desta faixa de temperatura, o crescimento é demorado. Sobre altas temperaturas a planta prolonga seu ciclo, continuando a formação de novas folhas, e repolhos de inverno não formam cabeças, quando formam, são leves, frouxas e de péssimo formato. O sombreamento leva a um maior acúmulo de nitrogênio solúvel nas plantas, ocorrendo um maior alongamento do caule.', 'A cultura se desenvolve bem em solos de textura média, solto, profundo e rico em matéria orgânica. Áreas arenosas são menos favoráveis ao desenvolvimento devido a baixa retenção de umidade. É importante que a área seja bem ensolarada, com boa disponibilidade de água, e o local não tenha sido cultivado antes com outras brássicas, como couve, couve-flor e próprio repolho. ', 'É importante realizar uma análise química do solo, para definir a adubação e a calagem. A recomendação para a cultura é o pH estar entre 5,5 a 6,8, devendo a calagem elevar a saturação por bases a 70% e o pH a 6,5. Para o preparo do solo convencional é recomendado aração profunda (20 a 30 cm), realizar a incorporação do calcário, e duas gradagens cruzadas para eliminar os torrões e nivelar o terreno; após a gradagem preparar as covas, com 20 cm de profundidade, ou sulcos, com 10 cm de profundidade; as linhas de covas e os sulcos devem cortar o sentido da declividade do terreno. Para adubação orgânica é indicado a aplicação de 20 a 50 t/ha de esterco de curral e 10 a 20 t/ha de cama de aviário para a cultura – porém para cálculos mais exatos é importante fazer a análise do adubo escolhido, disponibilidade de nutriente do solo e a necessidade da cultura.', 'A colheita do repolho ocorre de dois a quatro meses após o plantio, dependendo da variedade cultivada e das condições de cultivo.', 1),
(8, 'Couve flor', 'Brassica oleracea L. var. botrytis L', 'Brassicaceae', 'A couve flor possui folhas alongadas, com limbo elíptico, raízes concentradas na profundidade de 20 cm em função do sistema de cultivo. A parte comestível é composta por uma inflorescência imatura inserida sobre um caule curto, podendo ter coloração branca, creme, amarela, e mais recentemente roxa e verde. A couve-flor é uma planta alógama, cuja polinização é feita por insetos. A flor hermafrodita possui quatro sépalas e quatro pétalas com coloração variável entre branca, creme e amarela. Os estames são em número de seis. As anteras estão receptivas somente quando se aproxima a abertura da flor. O fruto é uma síliqua com número de sementes variando de um a sete, em condições normais.', 'É uma planta originária de clima frio, cujas cultivares ou híbridos necessitam de baixas temperaturas para a passagem da fase vegetativa para a reprodutiva. A faixa ótima de temperatura para couve-flor é de 14 a 20 ºC, e o cultivo em temperaturas acima de 25 ºC pode provocar a não-formação da inflorescência ou a perda de compacidade. Temperaturas próximas a 0 ºC causam injúrias por congelamento no ápice dos ramos, resultando também em não-formação da inflorescência.  As cultivares de verão não podem ser plantadas sob condições de temperaturas abaixo de 20 ºC, pois poderá haver formação precoce da inflorescência, com tamanho reduzido e sem valor comercial.', 'De forma geral, observa-se na couve-flor melhor comportamento em solos mais argilosos, ricos em matéria orgânica e bem drenados. Essa cultura é pouco tolerante à acidez e ao alumínio, exigindo pH entre 6,0 e 6,8. Tanto a calagem quanto à adubação são fundamentais para sistemas que buscam altas produtividades. Com base nos resultados da análise do solo, deve-se aplicar calcário para elevar a saturação por bases (V) a 80%, e o teor de magnésio a um mínimo de 9 mmolc dm-3. Para a correção da acidez do solo, utiliza-se o calcário, que deve ser aplicado durante o preparo do solo, anteriormente à aração. A adubação orgânica, especialmente com esterco aviário incorporado ao sulco, semanas antes do transplante têm sido prática rotineira entre os produtores, sugerem a aplicação de 40 a 60 toneladas por hectare de esterco de curral, ou a quarta parte dessa quantidade em esterco de galinha. A produção de mudas deve ser feita preferencialmente em bandejas de poliestireno expandido (isopor) com 128 a 200 células, dependendo do tamanho das mudas que se deseja levar ao campo. As mudas estão prontas para serem encaminhadas ao local de cultivo com 4 a 5 folhas definitivas, o que ocorre cerca de 25 a 30 dias após a emergência das plântulas. Deve-se dar preferência na escolha do terreno, às áreas que não tenham sido cultivadas anteriormente com outras espécies da família Brassicaceae, por período prolongado visando à diminuição da pressão de doenças severas, principalmente no que se refere à Hérnia das Crucíferas (agente causal = Plasmodiophora brassicae Wor.), que é capaz de inviabilizar o cultivo na área por vários anos. O espaçamento pode variar de 0,8 a 1,0 m entre linhas e 0,4 a 0,5 m entre plantas, dependendo da arquitetura foliar. As cultivares, cujas folhas tem um crescimento mais ereto, podem ser plantadas em espaçamentos mais adensados.', 'A planta deve permanecer livre da competição por plantas daninhas principalmente nas primeiras semanas após o plantio das mudas, para evitar a concorrência na utilização de água, luz e nutrientes, por meio de capinas ou com o uso da cobertura do solo, que pode ser feita com resto de palhada, com plástico e com TNT (Tecido Não Tecido) de coloração preta. A cobertura da inflorescência consiste em amarrar duas folhas das plantas sobre as inflorescências da couve-flor, de modo a cobri-las logo no início de sua formação e, assim, deixá-las até a colheita. O objetivo dessa prática cultural é dificultar a passagem dos raios de sol que deixam a inflorescência amarelada, prejudicando a qualidade do produto comercializável. Precisamos sempre lembrar que a preferência de mercado é por cabeças brancas. No entanto, deve-se ter consciência de que, principalmente em regiões úmidas, essa prática pode aumentar o número de cabeças com algum sinal de apodrecimento. Com a proteção da inflorescência há a formação de uma pequena câmara úmida propícia para o aparecimento de doenças bacterianas. Algumas cultivares de verão têm uma arquitetura de planta um pouco mais ereta o que permite bom recobrimento da cabeça, dispensando essa trabalhosa prática que, muitas vezes, acaba machucando as folhas das plantas e abrindo caminho para a entrada de patógenos.', 'A planta completar seu ciclo, variando de 90 a 96 dias. A colheita é realizada quando as inflorescências estão totalmente desenvolvidas, com os botões florais ainda unidos (cabeça compacta e ainda firme), realizando o corte no colo da planta e deixando algumas folhas para a sua proteção durante o transporte até os centros de consumo.', 1),
(9, 'Café Arábica', 'Coffea arabica', 'Rubiaceae', 'O café arábica é uma espécie originária das florestas subtropicais da região serrana da Etiópia e se adequa ao clima tropical de altitude. Suas variedades mais conhecidas são o “\'Typica” e o “Bourbon”, mas a partir dessas variedades desenvolveram-se muitas linhagens e cultivares, como o Caturra (Brasil, Colômbia), o Mundo Novo (Brasil), o Tico (América Central), o San Ramon anão e o Blue Mountain jamaicano.  O cafeeiro Arábica médio é um arbusto grande com folhas ovaladas verde-escuras.', 'A faixa de temperatura ideal para o cultivo fica entre 15-24° C. Temperaturas mais altas promovem formação de botões florais e estimulam o crescimento dos frutos e estimulam também, a proliferação de pragas e aumenta o risco de infecções que podem comprometer a qualidade da bebida. Sobre baixas temperaturas (abaixo de 10oC) e geadas ocorre a inibição do crescimento da planta.', 'O cafeeiro tem preferência por solos bem drenados. Os solos ricos em húmus e levemente ácidos são os mais propícios para o desenvolvimento da planta. Deve-se verificar a aptidão agrícola da área e utilizar praticas conservacionistas do solo, como uso de terraceamento, plantio em curva de nível entre outras. O uso de máquinas somente é permitido quando o declive for menor que 15%. A escolha do cultivar deve ser feita em função de diversos aspectos como produtividade, qualidade de bebida, época de maturação, espaçamento, microclima, ocorrência de pragas e doenças, dentre outras. Para fins de implantação do cultivo em sistema orgânico é essencial realizar a correção de acidez do solo antes do plantio, devendo este ser realizado em consórcios com outras culturas, evitando os espaçamentos mais adensados, para favorecer o consorcio nas entrelinhas do cultivo.', 'A adubação deve ser feita em função das análises de solo e da analise foliar para culturas já implantadas, sendo importante manter a matéria orgânica no solo, afim de melhorar aspectos físico, químico e biológicos. O incremento de matéria orgânica pode ser feito com por meio da adubação verde e da adição de adubos orgânicos (estercos, camas de aviário, palhas, restos vegetais e compostos). As demais informações quanto à implantação e manejo, deverão ser buscadas em cartilhas especificas para cada variedade que se deseja cultivar.', 'O início da colheita do café varia de acordo com a região, mas em média, a colheita é feita sete meses após a floração.', 5),
(10, 'Café Conilon/Robusta', 'Coffea canephora', 'Rubiaceae', 'É originário das regiões equatoriais baixas, quentes e úmidas da bacia do Congo. O termo “Robusta” na verdade é o nome de uma variedade silvestre desta espécie.  É um arbusto volumoso ou pequena árvore de até 10 metros de altura, mas com um sistema radicular de pouca profundidade.  Os frutos são arredondados e levam até 11 meses para amadurecer; as sementes são de formato ovalado e menores que as do C. arabica.', 'Café canéfora é resistente a temperaturas altas e a doenças. Adapta-se bem em regiões com média anual de temperatura entre 22 a 26oC. A quantidade de chuva ideal para o desenvolvimento da cultura fica na faixa de 1500 a 1900 mm anuais, bem distribuídos. Uma distribuição muito irregular de chuva causa floração desuniforme e maturação desigual dos frutos. O cafeeiro é uma planta adaptada ao sombreamento parcial.', ': O cafeeiro tem preferência por solos bem drenados. Os solos ricos em húmus e levemente ácidos são os mais propícios para o desenvolvimento da planta. Deve-se verificar a aptidão agrícola da área e utilizar praticas conservacionistas do solo, como uso de terraceamento, plantio em curva de nível entre outras. O uso de máquinas somente é permitido quando o declive for menor que 15%. A escolha do cultivar deve ser feita em função de diversos aspectos como produtividade, qualidade de bebida, época de maturação, espaçamento, microclima, ocorrência de pragas e doenças, dentre outras. Para fins de implantação do cultivo em sistema orgânico é essencial realizar a correção de acidez do solo antes do plantio, devendo este ser realizado em consórcios com outras culturas, evitando os espaçamentos mais adensados, para favorecer o consorcio nas entrelinhas do cultivo.', 'A adubação deve ser feita em função das análises de solo e da analise foliar para culturas já implantadas, sendo importante manter a matéria orgânica no solo, afim de melhorar aspectos físico, químico e biológicos. O incremento de matéria orgânica pode ser feito com por meio da adubação verde e da adição de adubos orgânicos (estercos, camas de aviário, palhas, restos vegetais e compostos). As demais informações quanto à implantação e manejo, deverão ser buscadas em cartilhas especificas para cada variedade que se deseja cultivar.', 'O início da colheita do café varia de acordo com a região, mas em média, a colheita é feita sete meses após a floração.', 5),
(11, 'Banana', 'Musa spp.', 'Musaceae ', 'A bananeira é uma planta típica das regiões tropicais úmidas, é um vegetal herbáceo completo, pois apresenta raiz, tronco, folhas, flores, frutos e sementes. O tronco é representado pelo rizoma e o conjunto de bainhas das folhas de pseudocaule.', 'A bananeira é uma planta tipicamente tropical, exigindo calor constante, chuvas bem distribuídas (100 mm a 150 mm/mês) e elevada umidade relativa para seu desenvolvimento. As temperaturas de 15°C e 35°C são tidas como limites extremos para além dos quais a banana paralisa seu crescimento. Baixas temperaturas aumentam o ciclo de produção, prejudicam os tecidos e impedem que a polpa da banana amoleça normalmente. Tais danos fisiológicos são conhecidos por chilling ou “friagem”. Por sua vez, temperaturas acima de 35°C causam prejuízos ao desenvolvimento da planta e à qualidade dos frutos, especialmente sob condições de sequeiro. As regiões onde a umidade relativa média situa-se acima de 80% são as mais favoráveis à bananicultura. Os ventos secos causam transpiração excessiva e rápido déficit hídrico das folhas (desidratação por evaporação), enquanto os ventos frios prejudicam sensivelmente as bananeiras e seus cachos. A bananeira requer intensa luminosidade para seu desenvolvimento. Quando cultivada sob baixa luminosidade, por período prolongado, tende a interromper seu desenvolvimento, não ocorrendo ou atrasando a diferenciação floral, o que prolonga o seu ciclo vegetativo. A insolação é outro fator importante, pois, quando excessiva, causa queimadura nas partes curvas da haste que sustenta o cacho (engaço) e nos frutos, os quais podem apodrecer.', 'O solo ideal para a bananeira é o aluvial profundo, rico em matéria orgânica, bem drenado e com boa capacidade de retenção de água. No entanto, a bananeira pode ser cultivada em diferentes tipos de solos. Os muito arenosos devem, porém, ser evitados, pois geralmente apresentam baixa fertilidade e baixo poder de retenção de água, aumentando os custos de produção pela necessidade de adubações mais frequentes e de práticas para melhorar o suprimento de água. Por sua vez, os muito argilosos podem ocasionar má drenagem e aeração deficiente, prejudicando o sistema radicular da planta; em áreas sujeitas a encharcamento, deve-se, portanto, estabelecer um bom sistema de drenagem, para evitar esses problemas. Para o preparo do solo, em áreas manualmente trabalhadas, inicialmente faz-se sua limpeza executando-se a roçagem do mato, a destoca, o encoivaramento e a queima das coivaras. O preparo do solo resume-se ao coveamento manual. Em áreas mecanizadas, a limpeza pode ser feita por máquinas, evitando-se remover a camada superficial do solo, por ser mais rica em matéria orgânica. Em seguida, faz a aração, a uma profundidade mínima de 20 cm, seguida da gradagem – ou essas duas práticas podem ser substituídas pela escarificação – e o coveamento ou sulcamento para plantio. Vale lembrar que o solo deve ser revolvido o mínimo possível. Há condição ideal de umidade para trabalhar o terreno quando o solo se torna friável, ou seja, úmido o suficiente para não levantar poeira durante o seu preparo, e tampouco aderir aos implementos. Além disso, devem-se usar máquinas e implementos leves e acompanhar as curvas de nível do terreno. O cultivo da bananeira deve ser feito de preferência em terrenos planos, onde são mínimos os riscos de erosão. No entanto, ela é comumente cultivada em áreas com declives acentuados, exigindo a adoção de cuidados especiais para a conservação do solo, principalmente no primeiro ciclo da cultura, quando o solo permanece descoberto durante grande parte do ano. Como medida preventiva, deve-se evitar que a água da chuva escorra com velocidade, provocando a erosão e o empobrecimento do solo. Nesse caso, é necessário adotar certas práticas, como o plantio em curvas de nível, o uso de renques de vegetação, a alternância de capinas e a cobertura do solo (morta ou viva). A cobertura morta com resíduos vegetais da própria bananeira, ou de outras plantas, representa uma grande aplicação de matéria orgânica, contribuindo para evitar a erosão, manter a umidade do solo, melhorar sua estrutura e, conseqüentemente, sua drenagem e sua aeração. Além disso, essa prática aumenta significativamente a quantidade de nutrientes no solo e reduz o número de capinas. Uma outra maneira de cobrir o solo e incorporar resíduos vegetais é cultivar plantas melhoradoras do solo (feijão-de-porco, crotalária, leucena e outras) nas entrelinhas do bananal, no período das águas, ceifando-as no início do período seco e deixando os resíduos na superfície do solo, como cobertura morta. Após a escolha da área onde será implantado o bananal, deve-se amostrar o solo para análise química. Recomenda-se formar amostras compostas de 15 a 20 amostras simples, coletadas de uma gleba homogênea quanto à vegetação e ao relevo, e que não exceda a 10 ha. Pela análise química do solo, determinam-se os teores de nutrientes nele existentes e, assim, é possível recomendar as quantidades de corretivo e de adubos a serem aplicadas. A aplicação do corretivo, quando necessário, deve ser feita com antecedência mínima de 30 dias do plantio, preferencialmente. Aplica-se o calcário a lanço em toda a área, após a aração, incorporando-o ao solo por meio de gradagem ou apenas fazendo uma escarificação do solo após a aplicação. ', 'A época mais favorável ao plantio é o período de chuvas esparsas, quando não ocorre o encharcamento do solo. Com isso, evita-se o apodrecimento das mudas. Em áreas sob irrigação, pode-se fazer o plantio em qualquer época do ano. Faz-se o plantio com mudas de um mesmo tipo, na mesma área, de modo a uniformizar a germinação e a colheita. Isso facilita o planejamento e a realização dos tratos culturais. Os tratos culturais no bananal abrangem os seguintes procedimentos: irrigação, capinas, desbaste, desfolha, escoramento, ensacamento do cacho e corte do pseudocaule (tronco) após a colheita do cacho. Na irrigação, os métodos variam de acordo com o tipo de solo e a disponibilidade de água. As plantas infestantes afetam o desenvolvimento da bananeira, competindo com ela por luz, água, espaço e nutrientes, sendo mais prejudiciais nos 5 primeiros meses, período em que o bananal deve ser mantido limpo. O desbaste é a eliminação do excesso de filhos ou rebentos produzidos pela bananeira. Deve-se deixar só um ou dois filhos por touceira, segundo o espaçamento adotado. De modo geral, os desbastes são realizados aos 4, 6 e 10 meses após o plantio, quando os rebentos atingem de 20 cm a 30 cm de altura. A parte aérea do rebento é cortada rente ao solo, com penado, faca ou facão, e, em seguida, extrai-se a gema apical de crescimento com o equipamento conhecido por “Lurdinha”. A desfolha consiste na eliminação de folhas secas, mortas e das que apresentam o pecíolo (cabo) quebrado, mesmo estando ainda verdes. Essas folhas não têm função ativa na planta, mas, em contrapartida, proporcionam a incorporação ao solo de considerável quantidade de matéria orgânica. As folhas são eliminadas de baixo para cima, em geral aos 4, 6 e 10 meses, mediante corte dos pecíolos, bem rente ao pseudocaule. Nas culturas já formadas, a desfolha deve ser feita sistematicamente, antes do desbaste e depois das adubações. O uso de escoras impede que as plantas tombem pela ação de ventos fortes, pelo peso do cacho, por causa da altura das bananeiras ou por sua má sustentação, e como resultado do ataque de nematóides ou da broca-do-rizoma. Evitando-se o tombamento das plantas, não se perdem os cachos, e, em consequência, o produtor tem garantido um aumento de receita. O ensacamento do cacho é prática utilizada em plantios mais tecnificados. Seu emprego melhora substancialmente a qualidade dos frutos, uma vez que cria um microclima favorável ao desenvolvimento dos frutos e evita o ataque de pragas, como a abelha-arapuá e o tripes. Faz-se o ensacamento logo após a emissão da última penca (falsa penca), que é eliminada juntamente com a raque masculina (rabo ou extremidade inferior do cacho que sustenta o coração). O corte do pseudocaule deve ser efetuado imediatamente após a colheita, e se recomenda fazer o corte de 30 cm a 50 cm do nível do solo, por ser mais prático e econômico. ', 'O desenvolvimento de uma bananeira é, em média, de cerca de 12 a 14 meses, desde o crescimento da planta até ao corte do cacho de bananas.', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Praga`
--

CREATE TABLE `Praga` (
  `Cod_Praga` int(11) NOT NULL,
  `Nome` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Familia` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Ordem` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Descricao` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `NomeCientifico` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Localizacao` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `AmbientePropicio` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `CicloVida` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Injurias` text COLLATE utf8_unicode_ci NOT NULL,
  `Observacoes` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `HorarioDeAtuacao` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `EstagioDeAtuacao` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `ControleCultural` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Praga`
--

INSERT INTO `Praga` (`Cod_Praga`, `Nome`, `Familia`, `Ordem`, `Descricao`, `NomeCientifico`, `Localizacao`, `AmbientePropicio`, `CicloVida`, `Injurias`, `Observacoes`, `HorarioDeAtuacao`, `EstagioDeAtuacao`, `ControleCultural`) VALUES
(1, 'Mosca branca', 'Aleyrodidae', 'Hemiptera', 'Sugador de seiva –  Os adultos são de coloração amarelo-pálida. Medem de 1 a 2 mm, sendo a fêmea maior que o macho. Quando em repouso, as asas são mantidas levemente separadas, com os lados paralelos, deixando o abdome visível. O ovo, de coloração amarela, apresenta formato de pêra e mede cerca de 0,2 a 0,3 mm. A postura é feita na face inferior das folhas das culturas que atacam, relacionada a uma resposta geotrópica negativa. A postura é realizada de forma irregular, sendo os ovos presos à folha por um pedicelo curto. A sua coloração é variável, sendo branco amarelada nos primeiros instantes após a postura e tornando-se castanho escuro à medida que se aproxima da eclosão. As ninfas são translúcidas e apresentam coloração amarela a amarelo-pálida. Tem função de vetor de vírus (diferentes espécies de geminivírus) que quando infecta as plantas ainda jovens, essas têm o crescimento paralisado. Esta praga possui a preferência em ovipositar nas folhas mais jovens e sua localização na planta irá variar justamente em função disto, uma vez que, à medida que a planta cresce, as folhas novas tornam-se maduras e envelhecem. ', 'Bemisia argentifolii', 'A amostra é feita por meio da verificação da presença de adultos na folha, localizados na face inferior de uma folha localizada no terço mediano da planta e de uma folha baixeira. A amostragem deve ser feita de preferência, pela manhã até as 9 h, virando-se cuidadosamente o folíolo, de modo a não afugentar os adultos. ', 'Temperaturas médias de 32 °C, quanto mais quente mais rápido acontece o ciclo.', '18 a 21 dias', 'Atua sugando a seiva das plantas, com a introdução do estilete no tecido vegetal, os insetos (adultos e ninfas) provocam alterações no desenvolvimento vegetativo e reprodutivo da planta, debilitando-a e reduzindo a produtividade e qualidade dos frutos. Em casos de altas densidades populacionais, podem ocorrer perdas de até 50% da produção. Infestações muito intensas ocasionam murcha, queda de folhas e perda de frutos. Nos frutos causa amadurecimento irregular. Ao se alimentarem da seiva eliminam uma substância açucarada levando ao aparecimento de fungos saprófitos que prejudicam a fotossíntese (fumagina).', 'Utilizar lupa de 20x - monitorar semanalmente, retirando a planta com sintomas de vírus (na base dos folíolos adquire inicialmente, uma clorose entre as nervuras, evoluindo para um mosaico amarelo. Posteriormente, os sintomas se generalizam, as folhas tornam-se coriáceas e com intensa rugosidade, podendo ocorrer o dobramento ou enrolamento dos bordos para cima) – A dispersão deste inseto ocorre pelo vento, maquinas, implementos agrícolas, pessoas e animais.', 'A maior atividade do vôo da mosca-branca ocorre entre as 6h30min e 8h30min e entre as 15h30min e 17h30min, com uma redução entre as 10h30min e 13h30min.', 'Ocorre em todo o ciclo embora a cor seja um fator determinante na seleção do hospedeiro à distância, destacando-se, em ordem de preferência, o verde-amarelado, o amarelo, o vermelho, o alaranjado-avermelhado, o verde escuro e o arroxeado.', 'Manter a área livre de espontâneas com potencial de hospedeira de patógenos, se possível, trinta dias antes do plantio; utilizar como barreiras sorgo forrageiro, milho ou outra planta similar, instaladas a cerca de 10 metros de distância da periferia da área cultivada; usar sementes de boa qualidade e de alto poder germinativo; produzir mudas longe de culturas infestadas com mosca branca e contaminadas por geminivírus; proteger a sementeira com tela, tecido ou plástico; proteger a sementeira com inseticidas registrados para a cultura, alternando-os em grupos químicos diferentes; instalar os plantios escalonados em direção contrária ao vento, para evitar a disseminação da praga de uma área para outra; utilizar mudas sadias e vigorosas e pulverizá-Ias antes do transplante; não efetuar o transplante das mudas antes de 21 dias; utilizar armadilhas visando a redução da população de adultos (as armadilhas podem ser confeccionadas com recipientes plásticos, metal ou placas de nylon, papelão, madeira e lonas, entre outras, pintadas com tinta amarela, untadas com produtos aderentes como óleo, graxa, cola, vaselina, entre outros e instaladas na periferia da área cultivada, na altura das planta, para fins de monitoramento); aumentar a densidade de plantas, para eliminar aquelas que apresentarem sintomas de viroses; usar coberturas repelentes à mosca branca (plástico preto ou prateado, a palha de arroz ou restos vegetais provenientes de capina, têm sido usados, pois pelo reflexo da luz ou por mudanças na temperatura, repelem a praga e têm sido recomendados para várias hortaliças); destruir os restos culturais imediatamente após a colheita, para evitar a sobrevivência da praga; efetuar rotação de culturas; em casos extremos e de forma generalizada, manter a área em pousio. É importante respeitar o calendário de plantio em algumas regiões do país, para evitar a disseminação da praga de áreas mais velhas para as mais novas.'),
(3, 'Traça do tomateiro ', 'Gelechiidae', 'Lepidoptera', 'A traça-do-tomateiro é uma pequena mariposa com envergadura de 6-7 mm e antenas filiformes, apresentando coloração cinza-prateada e pontos pretos nas asas anteriores. As fêmeas são maiores e mais volumosas do que os machos, e podem ovipositar nas folhas, hastes ou frutos de tomate, sendo uma única fêmea capaz de depositar cerca de 260 ovos durante sua vida. Nota-se que há uma preferência para oviposição nos estratos medianos e superiores do dossel da planta, correspondente aos estádios vegetativos e reprodutivos, respectivamente. Os ovos da traça-do-tomateiro possuem formato elíptico e tamanho consideravelmente pequeno, com cerca de 0,36 mm de comprimento e 0,22 mm de largura, sendo depositados isoladamente ou em grupos sobre a planta. No momento da postura são de coloração amarelo-claro brilhante, assumindo tonalidade marrom avermelhada à medida que se aproxima à eclosão das larvas, que ocorre de 4 a 7 dias após a postura. As larvas possuem coloração esverdeada, e após a eclosão penetram imediatamente na planta, completando o desenvolvimento em torno de 9 a 13 dias distribuídos em 4 ínstares. Quando estão próximas a atingir a fase de pupa, adquirem coloração rosada (pré-pupa), e podem tecer um fio de seda para descer da planta e pupar no solo ou nas folhas, especialmente as secas onde tecem um casulo esbranquiçado. As pupas têm formato cilíndrico e são de coloração esverdeada quando recém-formadas, tornando-se mais escura à medida que se aproxima a emergência do adulto, que ocorre após 7 a 10 dias.', 'Tuta absoluta', 'Analisar a terceira folha a partir do ápice (terço superior) totalmente expandida verificando se possui ou não minas.', 'Períodos quentes e secos favorecem sua ocorrência, verificando-se menor população em períodos chuvosos.', '40 dias', 'Se alimentam do parênquima foliar, formando galerias transparentes ou minas, nos locais de ataque observam-se fezes escuras. Atacam também o caule, as hastes e os ponteiros, causando o surgimento de brotações laterais que atrasam o desenvolvimento das plantas e a perda de frutos. Em altas infestações podem destruir completamente as folhas do tomateiro, causando o secamento dos folíolos e a morte da planta. As galerias abertas pelo inseto podem facilitar a penetração de patógenos nos tecidos da planta, tornando os frutos impróprios para o consumo e o processamento. As infestações da traça-do-tomateiro são mais intensas no período de frutificação da cultura, pois as lagartas ao permanecerem no interior dos frutos não são afetadas pelas ações de controle. Com isso, ocorre a emergência de adultos e a reinfestação das partes vegetativas e reprodutivas das plantas.', 'Utilizar lupa de 20x com monitoramento semanal. A disseminação da praga é feita pelo vento e pelo transporte de frutos atacados contendo lagartas.', 'Podem ser vistas ao amanhecer e ao entardecer, quando voam, acasalam e fazem a postura.', 'Ocorre durante todo o ciclo da cultura. As maiores infestações ocorrem no período de frutificação, pois as lagartas permanecem intactas às ações de controle no interior dos frutos.', 'Evitar o plantio sucessivo na mesma área, realizar a destruição dos restos vegetais; eliminar plantas espontâneas com potencial para ser hospedeira da praga, como Joá-bravo e Maria pretinha; realizar a irrigação por aspersão para induzir a derrubada dos ovos da traça e realizar a adubação adequada.  É importante realizar a rotação de culturas, de modo a interromper gerações sucessivas de traça do tomateiro na mesma área ou região. Respeitar o calendário de plantio em algumas regiões do país, para evitar a disseminação da praga de áreas mais velhas para as mais novas.'),
(4, 'Broca pequena do fruto', 'Crambidae', 'Lepidoptera', 'O ovo recém colocado é branco leitoso, passando a amarelo claro, alaranjado e avermelhado, quando próximos a eclosão. Têm formato achatado e são postos isolados ou agrupados nas flores, no pecíolo e sépalas, apresentam de 12 a 20 mm de diâmetro. Ao eclodirem, as lagartas penetram nos frutos (esta perfuração de entrada cicatriza-se), deixando apenas o furo de saída. O broqueamento dos frutos, inviabilizando sua comercialização. Os adultos são mariposas de 2,5 cm de envergadura, coloração branca e asas transparentes. As asas anteriores apresentam na parte mediana três manchas irregulares de coloração marrom e no ápice, uma de cor avermelhada. As asas posteriores são ornadas com alguns pontos escuros quase pretos e no ápice uma mancha de cor preta menos intensa. O corpo e as antenas são pardas esbranquiçadas. As lagartas possuem até 1,3 mm de comprimento, e são esbranquiçadas nos primeiros ínstares e rosadas no último ínstar. ', 'Neoleucinodes elegantalis', 'Avaliar visualmente as pencas no terço superior contendo frutos em fase inicial de desenvolvimento. Verificar sinais de entrada da lagarta recém-eclodidas nos frutos, com analises semanais.', 'Ataca a cultura do tomateiro principalmente no período chuvoso do ano, onde as altas temperaturas e umidades relativas são mais favoráveis ao crescimento populacional da praga. É favorecida com umidade relativa superior a 65% e temperatura máxima de 25°C.', '40 dias.', 'As larvas perfuram e broqueiam os frutos. A cicatriz de entrada da larva na casca dos frutos é pequena, cerca de 0,5 mm. O furo de saída da larva para empupar deixando uma ferida que permitirá a entrada de microrganismos patógenos no fruto. Além disso, podem comprometer o controle de qualidade das empresas que produzem sementes, devido ao menor poder germinativo das mesmas. Em culturas atacadas, pode ocorrer queda prematura dos frutos, sendo os danos geralmente mais evidentes próximos da colheita.', 'Utilizar lupa de 20x - realizar a amostragem semanal. No tomate, a maior proporção de ovos (48%) é ovipositada entre o cálice (face inferior) e o fruto, 28% na superfície do fruto, 20% no cálice, 3% na haste floral e 1% na haste/botões de flores; quando a praga está presente em altas densidades, os ovos podem ser encontrados nas folhas e no caule.', 'Realiza suas atividades de cópula, alimentação e oviposição durante a noite, iniciando-as por volta das 19 horas.', 'Ocorre em todos os estágios da planta, porém prefere os frutos mais novos.', 'Realizar a catação dos frutos atacados e enterrio dos mesmos. Pode-se realizar o ensacamento dos frutos para evitar o ataque, os estudos mostram que o ensacamento é tão eficiente quanto o controle químico com metamidofós, para N. elegantalis e o procedimento deve ser efetuando a partir do início da formação. O cultivo pode ser feito em local telado.'),
(5, 'Broca grande do fruto', 'Noctuidae ', 'Lepidoptera', 'Os ovos desta espécie têm formato esféricos, de coloração branca a amarela, com saliências laterais, passando para marrom próximo à eclosão, medem 1 mm de diâmetro, são depositados isoladamente, comportamento que os tornam mais expostos ao parasitismo e predação. As larvas medem cerca de 35 mm, de coloração que varia entre verde-claro, rosa, marrom ou quase preta, com faixas longitudinais escuras e claras alternando-se ao longo do corpo, cabeça amarela ou marrom, espiráculos escuros e bem evidentes, e a pele é áspera com inúmeros espinhos. As pupas têm coloração marrom, brilhante e escura, medem cerca de 20 mm. Os adultos possuem cerca de 40 mm de envergadura, apresentando asas anteriores de coloração amarelo-parda, com uma faixa transversal mais escura, possuem manchas escuras dispersas sobre as asas e asas posteriores são mais claras, com uma faixa nas bordas externas. Quando totalmente desenvolvida, a larva sai da planta e dirige-se para o solo, onde se transforma em pupa, período que dura em torno de 12 dias, emergindo o inseto adulto e reiniciando todo o ciclo. A mariposa geralmente oviposita a maioria dos seus ovos em massas de 2 a 4 ovos. ', 'Helicoverpa zea', 'Avaliar visualmente a presença de cachos com ovos no cacho mais baixeiro da planta.', 'Ocorre com maior frequência no período quente e na transição das estações do ano.', '40 a 45 dias.', 'Assim que os ovos eclodem, as lagartas podem se alimentar das folhas e flores como sua principal fonte de alimento na ausência de frutos. Após se deslocarem para o fruto, nos quais, com seu aparelho bucal mastigador, raspam a pele destes. As lagartas mais velhas abrem buracos profundos nas laterais dos frutos de tomate, e se alimentam dentro consumindo a polpa, deixando uma cavidade cheia de fluidos e excrementos, proporcionando uma rápida deterioração e apodrecimento. O ataque aos frutos é facilmente detectado por apresentarem orifícios irregulares na casca, migram facilmente de um fruto para o outro.', 'Utilizar lupa de 20x – realizar amostragem semanal ou em intervalo menor que uma semana quando atando com alguma forma de controle sobre a praga.', 'A praga possui hábitos noturnos movimentando-se a partir do entardecer.', 'É observada nas fases de crescimento vegetativo e, principalmente, durante as fases de desenvolvimento dos frutos e maturação.', 'Recomenda-se realizar o ensacamento das pencas de tomate nos três primeiros cachos por planta, pois a ocorrência de frutos brocados nas terceiras pencas é maior devido a arquitetura da planta bloquear os frutos com as folhas, fazendo com que os produtos químicos não cheguem no alvo. Também é recomendado a retirada das folhas baixeiras para facilitar a entrada dos produtos alternativos e biológicos no alvo. Realizar a catação dos frutos atacados e enterrio dos mesmos. A utilização associada de táticas como o controle biológico, monitoramento e ensacamento apresentam uma maior eficiência, no manejo dos broqueadores de tomate, quando comparados com o convencional.'),
(6, 'Pulgão verde', 'Aphididae', 'Hemiptera', 'Os insetos alados e ápteros vivem na colônia. A espécie apresenta de 1 a 3 mm de comprimento; ninfas e adultos ápteros (sem asas) são de coloração verde-clara, rosada ou avermelhada, enquanto os adultos alados possuem abdome de coloração verde-amarelado, cabeça e tórax pretos e sifúnculos escurecidos no ápice e ao final do abdome possuem dois apêndices tubulares laterais, chamados cornículas por onde são expelidas grandes quantidades de líquido adocicado (“honeydew”), decorrentes de sua alimentação, podendo favorecer o desenvolvimento de fumagina, afetando a fotosintese. Cada fêmea é capaz de gerar até 80 indivíduos durante sua vida.', 'Myzus persicae', 'Localiza-se na face inferior das folhas, brotações e flores, verificando a presença ou não de colônias de pulgões.', 'Atacam as plantas hortícolas principalmente em épocas do ano mais secas e de temperaturas mais amenas.', 'O ciclo de vida dura cerca de 10 dias.', 'O pulgão é considerado um inseto-praga importante, pois além de causar danos diretos pela sucção de seiva, o que resulta no enrolamento e engorvinhamento de folhas e brotos novos das plantas, também causa danos indiretos pela transmissão de vírus como o vírus do topo amarelo do tomateiro (PYPV), o vírus Y da batata (PVY) e Luteovírus.', 'Utilizar lupa de 20x -  realizar o monitoramento semanalmente.', 'sem referência.', 'Pode ocorrer em todo o ciclo da planta.', 'Proteger as mudas do período da semeadura ao transplante com tela anti-afídeo. Uso de barreiras ao redor do talhão com milho, sorgo ou crotalária e demais culturas que não são hospedeiras do pulgão, além de cobertura morta. Além disso, a remoção e destruição das plantas doentes (roguing ou desbaste fitossanitário) e a rotação de cultura com plantas que não sejam hospedeiras de viroses, como por exemplo gramíneas, que diminuem a fonte de inóculo na área de cultivo. Igualmente, os novos talhões devem ser plantados longe de plantios mais velhos para reduzir o inóculo primário. Importante realizar o controle de plantas hospedeiras como Capsicum spp. (pimenteiras), Nicotiana tabacum (fumo), Nicandria physaloides, Physalis sp., Petunia hybrida, Solanum americanum (maria-pretinha), S. sisymbriifolium (juá), S. atropurpureum, Tropaeolum majus e Cassia occidentalis (fedegoso). A cobertura do solo com palha de arroz reflete a radiação ultravioleta, proporcionando a repelência dos pulgões alados que estão voando em busca de plantas para fazer a picada de prova.'),
(7, 'Tripes schultzei', 'Thripidae', 'Thysanoptera', 'Possuem tamanhos variando de 1,0 mm a 2,0 mm de comprimento, com coloração amarelo-clara a amarelo escuro brilhante, cabeça quadrangular, aparelho bucal do tipo raspador-sugador. Os insetos adultos possuem asas estreitas com longas franjas em suas margens, enquanto que os jovens são ápteros. Na fase adulta tem como hábito o comportamento dispersivo, o qual facilita a escolha da espécie vegetal hospedeira, além da ação do vento ajudar em sua locomoção.', 'Frankliniella schultzei ', 'Realizar a contagem direta dos insetos na folha mais apical do ramo das plantas ou batedura dos ponteiros em bandeja plástica branca (3 litros).', 'Se desenvolve bem em temperaturas em torno de 24,5°C.', 'O ciclo de vida acontece entre 10 e 30 dias podendo chegar a 12,6 dias de acordo com a temperatura.', 'Durante a alimentação, através da sucção da seiva o inseto causa o dobramento dos bordos para cima e a descoloração esbranquiçada, manchas necróticas e raspagem da epiderme do tecido vegetal. Quando o ataque ocorre nas inflorescências, a descoloração é avermelhada e pode resultar em esterilidade das espiguetas, aborto de flores e redução da frutificação, devido se alimentarem do grão de pólen. Os maiores danos são provocados de forma indireta pela transmissão do vírus vira-cabeça, que os tripes liberam ao sugarem a seiva da planta (causando os sintomas de folhas bronzeadas, caule com faixas escuras, frutos com manchas amareladas e curvamento dos ponteiros das plantas).', 'Utilizar lupa de 10x – realizar amostragem semanalmente.', 'Encontraram-se frequentemente adultos, machos e fêmeas, bem como formas jovens, aglomerados nas folhas e nos frutos nas horas mais quentes do dia, especialmente com a temperatura acima dos 25°C.', 'O desenvolvimento da população da praga evolui conforme o crescimento das plantas, atingindo seu pico no florescimento. Possivelmente a cor amarela das flores pode ser um fator atrativo para os tripes.', 'Deve-se utilizar barreiras utilizando crotalária, milho e sorgo ao redor do plantio. Também devem ser utilizadas plantas armadilhas, podendo estas ser o rabanete (Raphanus sativus), nabiça (R. raphanistrum) e mostarda (Sinapsis arvensis). cultivo em volta da horta ou dentro do canteiro, em fileiras ou em covas alternadas de coentro (C. sativum), tagetes ou cravo-de-defunto (Tagetes sp.), hortelã (Mentha spp.), calêndula (Calendula officinalis), mastruz (Chenopodium ambrosioides), artemisia (Artemisia sp.) e arruda (Ruta graveolens). Estas plantas liberam voláteis que repelem os insetos sugadores adultos, mantendo-os afastados das hortaliças. Outra opção é o monitoramento de tripes adultos com armadilhas adesivas ou bandejas de coloração azul.'),
(8, 'Ácaro do bronzeamento', 'Eriophyidae', 'Acari', 'Os ovos têm cerca de 0,02mm de diâmetro, possuem formato arredondado e cor branca. A larva apresenta dois estádios ninfal, possuindo cor branca, muito semelhante ao adulto, porém menor e menos ativa.  O adulto possui cerca de 0,15 a 0,2mm, formato alongado, vermiforme com apenas dois pares de pernas, cor amarelo alaranjado. ', 'Aculops lycopersici', 'A inspeção deve ser feita nos ponteiros e na face inferior das folhas do terço médio, para detecção dos sintomas e presença de ácaros.', 'Temperaturas entre 15 - 25°C e umidade relativa entre 70 - 80%, favorece o desenvolvimento dos ácaros, tendo seu desenvolvimento acelerado à temperatura de 27° C e 30% de umidade relativa do ar. A dispersão pode ser realizada através do vento ou mecanicamente por pessoas, implementos agrícolas, animais ou insetos que se movimentam na área.', '6 dias.', 'Ataca todas as partes da planta, com intensa sucção, inicialmente provoca pontuações cloróticas na face superior das folhas que, com o aumento da população, evolui para uma coloração cinza-prateada e posteriormente para um bronzeamento, até o secamento por completo da planta atacada. O caule e pecíolos perdem a pilosidade tornando-se bronzeados. Alta infestação causa severa descoloração da superfície dos frutos com aparecimento de pequenas rachaduras. O dano se propaga de baixo para cima, sendo as folhas baixeiras as primeiras a serem infestadas. Flores com desenvolvimento anormal resultam em má formação de frutos, os quais permanecem pequenos de um lado e amadurecem com dificuldade. A infestação de 450 ácaros/cm² de folha/dia é capaz de reduzir a taxa fotossintética do tomateiro em até 50%, e quando o índice de infestação nos folíolos excede 15% pode haver perdas substanciais na produção.', 'Utilizar lupa de 10x – realizar amostragem semanalmente.', 'sem referência.', 'sem referência.', 'A destruição de restos da cultura, por exemplo, pode evitar a infestação de um ciclo de cultivo para outro. A irrigação por aspersão também constitui uma prática muito eficaz, reduzindo sensivelmente as populações de ácaros pela ação direta e pelo aumento da umidade relativa do ar. Uso do mulch (plástico) que cobre o solo, este provoca o aumento da temperatura do solo pela luz solar e faz o controle pelo processo chamado de solarização. Evitar o plantio próximo às plantas hospedeiras de ácaros, tais como mamona, algodão, feijão, soja, mandioca, batata, berinjela, mamoeiro, morangueiro, pimentão, alho, amendoim, macieira e pessegueiro; Transplantar as mudas em períodos chuvosos.'),
(9, 'Ácaro vermelho', 'Tetranychidae', 'Acari', 'As fêmeas têm aproximadamente 0,5 mm de tamanho e uma ampla forma oval. Os machos são muito menores com 0,3 mm, de cor laranja a palha e têm uma forma triangular mais alongada. Os ovos dos ácaros vermelhos são arredondados e de cor laranja profundo a pálido. Eles são mais claros quando recém colocados, ficando vermelhos como ferrugem antes da eclosão. As larvas são de cor verde claro ou rosado, ligeiramente maiores que os ovos e têm seis patas. As ninfas são semelhantes aos adultos com oito pernas, mas são menores e de cor esverdeada a laranja avermelhada.  Formam colônias entre as nervuras das folhas, onde tecem as suas teias, assim como os demais ácaros da família Tetranychidae, cuja principal função é a proteção dos ovos contra a dessecação e o forrageamento por predadores. Eles podem variar de cor durante seu ciclo de vida, de laranja claro a laranja escuro vermelho ou marrom. ', 'Tetranychus evansi', 'Inspeção dos ponteiros, da face inferior e superior das folhas do terço médio, para detecção dos sintomas e presença de ácaros.', 'São favorecidos por condições quentes e secas. A temperatura mínima para crescimento é de 10 °C e a temperatura ideal é de 34 °C.', 'Cerca de 14 dias. ', 'Para se alimentar, o ácaro ataca as folhas e absorve a seiva celular, ocasionando cloróticas e posteriormente caem, ocasionando definhamento das plantas e queda na produção. Também pode afetar a taxa fotossintética da planta com suas teias, afetando o crescimento.', 'Utilizar lupa de 10x – realizar amostragem semanalmente.', 'sem referência.', 'sem referência.', 'A destruição de restos da cultura, por exemplo, pode evitar a infestação de um ciclo de cultivo para outro. A irrigação por aspersão também constitui uma prática muito eficaz, reduzindo sensivelmente as populações de ácaros pela ação direta e pelo aumento da umidade relativa do ar. Uso do mulch (plástico) que cobre o solo, este provoca o aumento da temperatura do solo pela luz solar e faz o controle pelo processo chamado de solarização. Evitar o plantio próximo às plantas hospedeiras de ácaros, tais como mamona, algodão, feijão, soja, mandioca, batata, berinjela, mamoeiro, morangueiro, pimentão, alho, amendoim, macieira e pessegueiro; Transplantar as mudas em períodos chuvosos.'),
(10, 'Ácaro branco ', 'Tarsonemidae', 'Acari', 'O ácaro adulto emergente possui apenas 0,2 mm de comprimento, é oval e largo, e possui cor pálida amarela ou verde amarelada, dependendo do tipo e quantidade de alimentos consumidos. Ácaros fêmea possuem uma listra branca nas costas. O ácaro branco tem como característica que os difere dos demais é a não produção de teia. ', 'Polyphagotarsonemus latus', 'Inspeção dos ponteiros, da face inferior e superior das folhas do terço médio, para detecção dos sintomas e presença de ácaros.', 'O Ácaro branco é favorecido com temperaturas elevadas e tempo chuvoso.', '5 dias.', 'Danifica as folhas e brotações novas provocando a paralisação do crescimento e atrofia dos ramos através da sucção de seiva. Os ácaros preferem as plantas jovens em desenvolvimento, como pontas em crescimento, folhas tenras e brotos jovens.', 'Utilizar lupa de 10x – realizar amostragem semanalmente.', 'sem referência.', 'sem referência.', 'A destruição de restos da cultura, por exemplo, pode evitar a infestação de um ciclo de cultivo para outro. A irrigação por aspersão também constitui uma prática muito eficaz, reduzindo sensivelmente as populações de ácaros pela ação direta e pelo aumento da umidade relativa do ar. Uso do mulch (plástico) que cobre o solo, este provoca o aumento da temperatura do solo pela luz solar e faz o controle pelo processo chamado de solarização. Evitar o plantio próximo às plantas hospedeiras de ácaros, tais como mamona, algodão, feijão, soja, mandioca, batata, berinjela, mamoeiro, morangueiro, pimentão, alho, amendoim, macieira e pessegueiro; Transplantar as mudas em períodos chuvosos.'),
(11, 'Ácaro rajado ', 'Tetranychidae', 'Acari', 'Este ácaro pode se apresentar em duas formas distintas com duas biologias estreitamente relacionados e produzindo o mesmo dano. Uma delas com a coloração amarelo-esverdeada e outra com a cor avermelhada- alaranjada que trata dos indivíduos que ocorrem no inverno. Os adultos têm 2 manchas escuras típicas nas costas e 4 pares de pernas. A fêmea tem 0,5 mm de comprimento; o macho é menor e esbelto com 0,3 mm de comprimento. O ovo apresenta formato esférico, com menos de 0,1 mm de diâmetro, liso, esbranquiçado e translúcido após a postura. A larva tem tamanho reduzido e possui 3 pares de pernas. Esta espécie também tem a característica de produzir teias para auxiliar na oviposição e na proteção contra o ataque de predadores, estas teias podem restringir a área fotossintética da planta e interferir em seu desenvolvimento.', 'Tetranychus urticae', 'Inspeção da face inferior das folhas do terço médio, para detecção dos sintomas e presença de ácaros.', 'Se desenvolve melhor em altas temperaturas e tempo seco.', 'Cerca de 14 dias.', 'Para se alimentar, o ácaro ataca as folhas e absorve a seiva celular, ocasionando manchas amareladas que, com o passar do tempo, tornam-se pardo-avermelhadas e secam. Ocorre definhamento das plantas e queda na produção. O fruto atacado fica endurecido, seco e com coloração marrom. Durante o ataque o ácaro injeta saliva no interior dos tecidos lesionados resultando em algumas modificações de natureza fisiológica como o aumento da transpiração celular e posterior quadro de déficit hídrico.', 'Utilizar lupa de 10x – realizar amostragem semanalmente.', 'sem referência.', 'sem referência.', 'A destruição de restos da cultura, por exemplo, pode evitar a infestação de um ciclo de cultivo para outro. A irrigação por aspersão também constitui uma prática muito eficaz, reduzindo sensivelmente as populações de ácaros pela ação direta e pelo aumento da umidade relativa do ar. Uso do mulch (plástico) que cobre o solo, este provoca o aumento da temperatura do solo pela luz solar e faz o controle pelo processo chamado de solarização.'),
(13, 'Pulgão das inflorescências ', 'Aphididae ', 'Hemiptera', 'As ninfas são pequenas com formato ovalados e coloração que varia do marrom ao cinza, cabeça e tórax mais escuros que o restante do corpo. A fêmeas adulta são ápteras e medem de 1 a 2 mm de comprimento e corpo comumente de coloração verde clara com manchas verde escuro. As pernas de coloração clara, com tíbias e tarsos mais escuros. As fêmeas aladas medem de 1,2 a 1,8 mm e apresentam cabeça e tórax de coloração preto, e abdome esverdeado. Fêmeas adultas ápteras dão origem a ninfas fêmeas por partenogênese.', 'Aphis gossypii', 'Avaliar as brotações novas para se verificar a presença ou não de colônias de pulgões.', 'Atacam as plantas hortícolas principalmente em épocas do ano mais secas e de temperaturas mais amenas. As temperaturas ideais para reprodução variam entre 25 °C a 27 °C.', 'Duração média de 15 dias.', 'O pulgão é considerado um inseto-praga importante, pois além de causar danos diretos pela sucção de seiva, o que resulta no enrolamento e engorvinhamento de folhas e brotos novos das plantas, também causa danos indiretos pela transmissão de alguns vírus. A sucção contínua de seiva pode ocorrer o retardamento do crescimento da planta. Ocorre, também, a excreção de uma substância açucarada denominada “honeydew”, deixando as folhas pegajosas e meladas, servindo como substrato para o desenvolvimento de fungos, principalmente daqueles do gênero Capnodium, identificados pelo crescimento de uma massa escura sobre as folhas, facilmente removida por raspagem e conhecida como fumagina, que podem recobrir folhas e ramos e culminar na redução da capacidade fotossintética da planta.', 'Utilizar lupa 20x. Realizar monitoramento semanal.', 'sem referência.', 'Atua em todo o ciclo da planta.', 'Proteger as mudas do período da semeadura ao transplante com tela anti-afídeo. Uso de barreiras ao redor do talhão com milho, sorgo ou crotalária e demais culturas que não são hospedeiras do pulgão, além de cobertura morta. Além disso, a remoção e destruição das plantas doentes (roguing ou desbaste fitossanitário) e a rotação de cultura com plantas que não sejam hospedeiras de viroses, como por exemplo gramíneas, que diminuem a fonte de inóculo na área de cultivo. Igualmente, os novos talhões devem ser plantados longe de plantios mais velhos para reduzir o inóculo primário. Importante realizar o controle de plantas hospedeiras como Capsicum spp. (pimenteiras), Nicotiana tabacum (fumo), Nicandria physaloides, Physalis sp., Petunia hybrida, Solanum americanum (maria-pretinha), S. sisymbriifolium (juá), S. atropurpureum, Tropaeolum majus e Cassia occidentalis (fedegoso). A cobertura do solo com palha de arroz reflete a radiação ultravioleta, proporcionando a repelência dos pulgões alados que estão voando em busca de plantas para fazer a picada de prova. Uso de táticas de controle comportamental ou etológico: instalação de armadilhas adesivas amarelas, que se baseia no princípio da atração dos pulgões alados pela cor amarela, os quais ficam retidos na superfície dos painéis adesivos. Esses painéis devem ser localizados nas bordaduras da cultura para capturar os insetos migrantes.'),
(14, 'Pulgão das solanáceas', 'Aphididae', 'Hemiptera', 'O pulgão-das-solanáceas M. euphorbiae é o maior das três espécies que ocorrem em pimentão. Os indivíduos dessa espécie que não apresentam asas, medem cerca de 3,5 mm de comprimento, apresentam coloração verde-claro e possuem as pernas e os sifúnculos com as extremidades escurecidas. As formas aladas são maiores, apresentando cerca de 4 mm de comprimento, antenas ultrapassando o tamanho do corpo e coloração variando do verde-claro ao verde-escuro, embora haja referências a formas rosadas ou amarelas com manchas escuras no dorso. O corpo é alongado e as pernas e antenas são compridas. Os cornículos são cilíndricos e de comprimento aproximadamente igual a um terço do tamanho do corpo. A cauda é de tamanho igual a um terço do comprimento dos comículos. M euphorbille pode transmitir o vírus do mosaico do pimentão.', 'Macrosiphum euphorbiae ', 'Realizar a batedura do ponteiro em uma bandeja de fundo branco para verificar a presença do inseto.', 'sem referência.', '10 dias.', 'Se alimenta das folhas e os ramos novos das plantas, e as torna enroladas, encarquilhadas e os brotos ficam curvos e achatados. Devido à sucção contínua de seiva pode ocorrer o retardamento do crescimento da planta. Ocorre, também, a excreção de uma substância açucarada denominada “honeydew”, deixando as folhas pegajosas e meladas, servindo como substrato para o desenvolvimento de fungos, principalmente daqueles do gênero Capnodium, identificados pelo crescimento de uma massa escura sobre as folhas, facilmente removida por raspagem e conhecida como fumagina, que podem recobrir folhas e ramos e culminar na redução da capacidade fotossintética da planta. Além desses danos, os pulgões podem transmitir diversos vírus como o mosaico amarelo do pimentão (Pepper yellow mosaic virus – PepYMV), o vírus Y da batata (Potato virus Y – PVY) e o mosaico do pepino (Cucumer mosaic virus – CMV), os quais podem ocasionar redução no crescimento das plantas, redução da qualidade dos frutos e da produção. Os prejuízos decorrentes da infestação desses insetos variam de acordo com o estágio de desenvolvimento da cultura no momento da transmissão da virose.', 'Utilizar lupa 20x. Realizar monitoramento semanal.', 'sem referência.', 'Ocorre em todo o ciclo da planta.', 'Proteger as mudas do período da semeadura ao transplante com tela anti-afídeo. Uso de barreiras ao redor do talhão com milho, sorgo ou crotalária e demais culturas que não são hospedeiras do pulgão, além de cobertura morta. Além disso, a remoção e destruição das plantas doentes (roguing ou desbaste fitossanitário) e a rotação de cultura com plantas que não sejam hospedeiras de viroses, como por exemplo gramíneas, que diminuem a fonte de inóculo na área de cultivo. Igualmente, os novos talhões devem ser plantados longe de plantios mais velhos para reduzir o inóculo primário. Importante realizar o controle de plantas hospedeiras como Capsicum spp. (pimenteiras), Nicotiana tabacum (fumo), Nicandria physaloides, Physalis sp., Petunia hybrida, Solanum americanum (maria-pretinha), S. sisymbriifolium (juá), S. atropurpureum, Tropaeolum majus e Cassia occidentalis (fedegoso). A cobertura do solo com palha de arroz reflete a radiação ultravioleta, proporcionando a repelência dos pulgões alados que estão voando em busca de plantas para fazer a picada de prova. Uso de táticas de controle comportamental ou etológico: instalação de armadilhas adesivas amarelas, que se baseia no princípio da atração dos pulgões alados pela cor amarela, os quais ficam retidos na superfície dos painéis adesivos. Esses painéis devem ser localizados nas bordaduras da cultura para capturar os insetos migrantes.'),
(16, 'Traça das crucíferas', 'Plutellidae', 'Lepidoptera', 'Os ovos da traça das crucíferas medem menos de 1 mm, possuem forma oval e são encontrados geralmente isolados na parte superior ou inferior das folhas. Inicialmente são de coloracão amarela tornando-se pretos próximo a eclosão. As larvas possuem quatro estádios e são de coloração verde-escura quando se alimentam em folhas de repolho, brócolos ou couve-flor, verde-clara quando se alimentam em cabeças de couve-flor. Para empupar as larvas constroem um casulo. As pupas são inicialmente claras e próximo a emergência dos adultos são escuras. Os machos quando pousados sobre as folhas exibem uma mancha clara em forma de diamante na parte dorsal. As fêmeas são altamente férteis e podem depositar até 350 ovos durante o seu ciclo de vida. ', 'Plutella xylostella', 'Realizar amostragem inspecionando a parte inferior das folhas para identificar a presença ou ausência de larvas da traça-das-crucíferas. \r\nPara repolho: verificar a\r\npresença de furos e de lagartas na face inferior das folhas e na cabeça do repolho.', 'A traça das crucíferas se desenvolve bem em temperatura por volta de 15°C. No período chuvoso a densidade populacional é baixa devido à remoção dos ovos das folhas e à morte de larvas e pupas por afogamento. Períodos com ausência de chuvas e temperaturas ao redor de 22°C favorecem o crescimento populacional.', 'De 12 a 20 dias.', 'As larvas de primeiro estádio minam as folhas e se alimentam no interior das minas. Já as de segundo e terceiro estádio se alimentam das folhas consumindo todo o tecido foliar, exceto a epiderme superior, o que faz com que minas transparentes se formem nas folhas. As larvas de quarto estádio se alimentam de todas as partes da folha. Em repolho as larvas causam furos nas cabeças, reduzindo o valor comercial do produto. Quando em baixas populações as larvas preferem as folhas mais jovens das plantas; já em altas populações os insetos se distribuem por toda a planta. Os locais da planta onde a traça-das-cruciferas se desenvolve oferecem proteção contra predadores, parasitóides e até inseticidas. Em plantas de repolho, larvas e pupas são encontradas na parte superior das folhas que circundam as cabeças. Quando localizadas em folhas externas, larvas e pupas são encontradas na parte inferior das folhas. ', 'Utilizar lupa de 10x – realizar amostragem semanalmente. ', 'Os adultos voam a noite e durante o dia se escondem nas folhagens.', 'Pode ocorrer em todo o ciclo das brássicas. ', 'Uma das grandes dificuldades para o controle da traça-das-cruciferas se deve sobretudo ao fato de geralmente as áreas de cultivo serem pequenas e cultivadas o ano todo com plantas de diferentes idades. Isto permite a multiplicação contínua da praga nos locais de cultivo. Assim sendo, plantios sucessivos de brássicas devem ser evitados, a fim de não prover hospedeiros para a praga continuamente. A irrigação efetuada durante a noite reduz o acasalamento e pode contribuir para diminuir a população da praga na área. Irrigações efetuadas durante o dia podem contribuir para a redução da população da traçadas-crucíferas através da remoção dos ovos da planta.'),
(17, 'Curuquerê da couve', 'Pieridae', 'Lepidoptera', 'Os ovos, amarelos e alongados, são afixados em grupos, nos dois lados das folhas, em posição ereta, ocorrendo a eclosão 4 a 5 dias após a postura. As fêmeas de A. monuste preferem ovipositar em folhas jovens e e apresentam melhor performance quando se alimentam dessas folhas, porém mostram habilidades para compensar alimentos com menor valor nutritivo. A postura é feita predominantemente na face inferior das folhas e os ovos distanciam-se aproximadamente 1 mm um do outro. A lagarta, que chega a 35 mm de comprimento, é de coloração cinza-esverdeada com faixas longitudinais marrons e amarelas, alternadas. A cabeça é de coloração escura e as faixas amarelas possuem doze pares de pontos pretos. A. munuste orseis apresenta cinco ínstares larvais, inicialmente com 3 mm de comprimento e coloração amarelo-pálida brilhante. Com as ecdises a tonalidade verde escura é adquirida e o corpo coberto por pelos. No quinto ínstar as lagartas podem atingir 35 mm de comprimento. Próximo a se transformarem em pupas, as lagartas suspendem a alimentação e tecem fios de ceda por onde se fixam na planta ou no solo. O adulto é uma borboleta de asas branco-amareladas com os bordos marrom-escuros e corpo preto, possuindo 5 cm de envergadura e hábito diurno.', 'Ascia monuste orseis', 'Realizar a amostragem analisando a presença de plantas atacadas ou da praga em um de seus estágios de desenvolvimento.', 'Sem referência.', 'Aproximadamente 50 dias.', 'As folhas são atacadas por um grupo de lagartas, que são muito vorazes e consomem toda a área foliar das bordas para o centro da folha, exceto as nervuras mais grossas. O seu ataque às folhas inicia logo após a eclosão, devorando-as durante todo o período larval. Em caso de intensa desfolha da planta, ocorre comprometimento da produção, resultando em sério prejuízo.  ', 'Amostragem semanal. Para amostragem dos ovos utilizar lupa de 10x inspecionando as duas faces das folhas.', 'A oviposição ocorre em sua maioria durante a manhã.', 'Podem atuar em todo o ciclo da planta.', 'O controle cultural de pragas se inicia pela aplicação dos padrões técnicos recomendados para adubação, irrigação e tratos culturais de cada cultura, objetivando estabelecer condições para a cultura competir com a praga. Algumas das medidas de controle cultural de pragas que vêm sendo empregadas são: seleção do local adequado para a instalação do cultivo, destruição de restos culturais, rotação de cultura e densidade de plantio, adubação e irrigação adequados. Antes mesmo da instalação do cultivo, medidas preventivas de controle de pragas devem ser tomadas, como a seleção de local adequado para o plantio, evitando locais próximos a espécies de plantas hospedeiras da praga e dando preferência a locais próximos a matas, uma vez que estas geralmente possuem ninhos de Vespidae, importantes predadores de lagartas desfolhadoras.'),
(18, 'Lagarta rosca ', 'Noctuidae', 'Lepidoptera', 'Esta lagarta possui o hábito de se enrolarem ao serem perturbadas, daí surgiu o nome lagarta rosca. Os adultos de A. ipsilon são mariposas com 30 a 50 mm de envergadura, apresentando as asas anteriores de coloração marrom acinzentada com manchas escuras e uma faixa larga de cor castanho claro ao longo da margem externa, enquanto que as asas posteriores são semitransparentes. Os ovos, de coloração branca e globosos, medem menos de 1 mm de diâmetro e são colocados individualmente ou em grupos de 2 ou 3 sobre as folhas, caule ou no solo úmido. A espécie passa por cinco a sete estádios larvais. As lagartas chegam a medir de 30 a 50 mm de comprimento, com corpo cilíndrico e robusto, cinco pares de pernas abdominais, coloração variável, predominando o pardo-acinzentado escuro, podendo apresentar listras claras dispostas longitudinalmente. Tem o habito de manter-se escondida no solo durante o dia, a poucos centímetros de profundidade, junto à planta lesada. As pupas são encontradas em câmaras pupais construídas pela lagarta no solo a uma profundidade de 3 a 12 cm. Medem de 20 a 30 mm de comprimento e apresentam coloração marrom brilhante.', 'Agrotis ipsilon', 'Avaliar as plantas individualmente verificando a presença da praga ou de plantas atacadas.', 'Temperatura ideal para o desenvolvimento larval é em torno de 27 ºC.', 'Aproximadamente 42 dias.', 'As lagartas de A. ipsilon seccionam as plantas jovens de até 10 cm de altura rente ao solo, causando o tombamento das mudas em sementeira ou após o transplantio no campo, reduzindo o número de plantas por área cultivada. Em plantas com desenvolvimento adiantado, podem ainda abrir galerias que danificam os tecidos de sustentação e condução, fator desencadeador do fenômeno do perfilhamento excessivo da planta. ', 'Amostragem semanal.', 'Atua geralmente a noite.', 'Pode atuar em todo o ciclo da planta.', 'O controle cultural de pragas se inicia pela aplicação dos padrões técnicos recomendados para adubação, irrigação e tratos culturais de cada cultura, objetivando estabelecer condições para a cultura competir com a praga, como seleção do local adequado para a instalação do cultivo, destruição de restos culturais, rotação de cultura e densidade de plantio, adubação e irrigação adequados, entre outros. O controle cultural de pragas em geral deve ser considerado um dos métodos de maior influência, principalmente quando se avalia a viabilidade econômica das técnicas empregadas. O uso de métodos culturais deve começar antes mesmo da implantação da cultura, observando se a área a ser cultivada apresenta histórico de ataques da praga ou se apresenta uma grande população de ervas daninhas, principalmente solanáceas cultivadas e não-cultivadas, hospedeiras alternativas da mesma. Além da eliminação de ervas daninhas, a destruição de restos culturais também pode colaborar com o controle da praga, visto que as mariposas preferem estes locais para ovipositar. A exposição das lagartas e pupas abrigadas no solo à radiação solar e a predadores com a utilização de aração profunda representa uma prática de fácil utilização pelo produtor e bastante eficiente no controle da praga. '),
(19, 'Mosca/Larva minadora ', 'Agromyzidae', 'Diptera', 'Os adultos de Liriomyza spp. são pequenas moscas de coloração geralmente escura, com manchas amareladas nas laterais do corpo e no escutelo. Seu comprimento varia de 1,5 mm a 2 mm. Apresenta asas translúcidas e o corpo revestido de cerdas, sendo o macho menor e mais escuro que a fêmea. Os adultos são facilmente dispersos pelo vento, podendo ser transportados a longas distâncias. As fêmeas depositam seus ovos endofiticamente (dentro do tecido das plantas), sendo um ovo por punctura (punctura de oviposição). Cerca de 1/5 (um quinto) das puncturas realizadas pelas fêmeas é de puncturas de oviposição, sendo as demais puncturas realizadas para alimentação. Cada fêmea pode ovipositar de 500 a 700 ovos, sendo estes depositados, preferencialmente, pela manhã e nos seus primeiros dias de vida. Os ovos são elípticos, de coloração branco-leitosa, transparentes e medem aproximadamente 0,2 mm de comprimento e 0,13 mm de largura. As larvas são ápodas, têm corpo cilíndrico, podendo atingir 6 mm de comprimento, coloração inicial branca hialina e, posteriormente, amarela. As larvas transformam-se em pupas no solo, a pouca profundidade ou aderidas às folhas. As principais espécies de moscas-minadoras que ocorrem no Brasil são Liriomyza huidobrensis (Blanchard), Liriomyza sativae Blanchard e Liriomyza trifolii (Burgess), causando prejuízos nas culturas da batata, melancia, melão, pepino e tomate. ', 'Liriomyza ssp. Mik', 'Avaliar as folhas do ápice das plantas verificando a presença das minas/larva. Em solanáceas, analisar a terceira folha a partir do ápice (terço superior) totalmente expandida. Para as Brassicas recomenda-se avaliar uma a duas folhas em formação completamente abertas para verificar a presença de minas/larva.', 'O clima varia em função da espécie, de 15 a 25 °C. ', 'De 15 a 25 dias.', 'A principal característica da mosca-minadora é provocar galerias nas folhas de seus hospedeiros, provocando danos graves para muitas culturas. Na fase adulta realiza puncturas para alimentação/oviposição. As larvas eclodem dentro da folha e apresentam como principal fonte de alimentação na fase imatura o mesófilo foliar. O ataque causa prejuízo nas folhas, acarretando na redução da área fotossintética e a produção de metabólitos por parte da planta ficam comprometidos. O ataque das moscas-minadoras podem possibilitar a entrada de fitopatógenos, o que pode acarretar em grandes prejuízos econômicos, uma vez que estes insetos são possíveis transmissores indiretos de fungo e bactérias.', 'Utilizar lupa de 10x – realizar amostragem de duas a três vezes por semana para evitar aparecimento de focos de infestação.', 'Sem referência.', 'Em tomateiros a mosca-minadora pode infestar desde o transplantio até a fase da colheita.', 'O controle cultural de pragas se inicia pela aplicação dos padrões técnicos recomendados para adubação, irrigação e tratos culturais de cada cultura, objetivando estabelecer condições para a cultura competir com a praga, como seleção do local adequado para a instalação do cultivo, destruição de restos culturais, rotação de cultura e densidade de plantio, adubação e irrigação adequados, entre outros. O controle cultural de pragas em geral deve ser considerado um dos métodos de maior influência, principalmente quando se avalia a viabilidade econômica das técnicas empregadas. O uso de métodos culturais deve começar antes mesmo da implantação da cultura, observando se a área a ser cultivada apresenta histórico de ataques da praga ou se apresenta uma grande população de ervas daninhas, principalmente solanáceas cultivadas e não-cultivadas, hospedeiras alternativas da mesma. Além da eliminação de ervas daninhas, a destruição de restos culturais também pode colaborar com o controle da praga, visto que as mariposas preferem estes locais para ovipositar. O esmagamento das larvas com o auxílio das unhas ou até mesmo com alfinetes, ou a destruição das folhas com larvas é uma técnica rústica e pode ser adotado em cultivos de pequena dimensão, ou até mesmo em ambiente controlado de pequena dimensão como casa de vegetação. Porém, em cultivos de grande escala, essa técnica é inviável economicamente diante da grande demanda de mão-de-obra requerida para a execução desta prática.');
INSERT INTO `Praga` (`Cod_Praga`, `Nome`, `Familia`, `Ordem`, `Descricao`, `NomeCientifico`, `Localizacao`, `AmbientePropicio`, `CicloVida`, `Injurias`, `Observacoes`, `HorarioDeAtuacao`, `EstagioDeAtuacao`, `ControleCultural`) VALUES
(20, 'Broca da couve ', 'Crambidae', 'Lepidoptera', 'A broca-da-couve é uma mariposa com 15 mm de envergadura e suas asas são de cor marrom-dourada com cinco listras longitudinais dorsais, que realiza a postura preferencialmente na face superior das folhas, observando-se uma mancha preta na parte central da asa anterior e outras pequenas na borda. Os ovos medem cerca de 0,4 mm de diâmetro e são um pouco achatados, de cor verde a castanha. As lagartas recém-eclodidas possuem coloração verde-clara com a cabeça preta, e se alimentam por algumas horas sobre as folhas. Em seguida entram no interior do caule - na haste principal da planta, geralmente na parte da junção das folhas com os talos - onde acontece o restante da fase larval, quando as lagartas abrem galeria até atingir o completo desenvolvimento e, finalmente, saem da planta para se transformarem em pupa no solo. A fase de pré-pupa desta praga não se alimenta, assim como muitos lepidópteros. Durante esse período inseto tece um casulo de seda translúcida pouco abaixo da superfície do solo. O período de pupa, que se inicia após o período de prépupa e termina na emergência do adulto.', 'Hellula phidilealis', 'Avaliar as plantas individualmente verificando a presença da praga ou se a planta encontra-se atacada.', 'A temperatura ideal aproximada para eclosão dos ovos é de 27 ± 1 ºC e umidade relativa de 65 ± 10%.', 'Cerca de 35 dias.', 'O dano acontece no meristema apical das plantas que é destruído e, próximo a ele, surgem numerosos brotos. A destruição do ápice acaba forçando a planta a ter um crescimento anormal, sendo observadas diversas folhas com o limbo estreitado, principalmente de um dos lados, e naquelas imediatamente próximas ao ápice, quando as plantas apresentam razoável desenvolvimento, nota-se a cicatriz do orifício de penetração da lagarta. A cicatriz é grande porque se expandiu à medida que cresceram as folhas, observando-se diversas folhas com o limbo estreitado, principalmente de um dos lados. Inicialmente, as lagartas se alimentam da superfície foliar tenra e depois se dirigem ao meristema apical, perfurando-o e cobrindo-o com uma fina teia. No repolho, Brassica oleracea var. capitata, e na couve-flor, Brassica oleracea var. botrytis, os danos causados pela alimentação das lagartas de H. phidilealis ocorrem no ápice induzindo o crescimento lateral e a formação de falsas cabeças; além do mais, ataques fúngicos estão corriqueiramente associados com o ataque da praga. Na cultura da couve, Brassica oleracea var. acephala, a lagarta nos primeiros íntares raspa a folha e nos íntares posteriores ataca o caule e hastes da planta broqueando-os e provocando o secamento de toda planta.', 'Realizar amostragem semanalmente.', 'É uma praga de hábitos noturnos similar às demais mariposas.', 'O ataque pode acontecer logo após o transplante da cultura no campo.', 'Dentro do manejo cultural, a irrigação tem apresentado significativa importância no controle de H. phidilealis. A utilização da lâmina ideal de irrigação nas culturas do repolho (B. oleracea var. capitata) e brócolis (B. oleracea var. botrytis) reduziu 50% o nível de infestação de H. phidilealis quando comparados a lâminas inferiores (40 e 70% da lâmina ideal) e superiores (130 e 160% da lâmina ideal). O manejo adequado da irrigação aliado a outros métodos de manejo da praga podem favorecer o controle de H. phidilealis. De acordo com Alam (1982), as espécies Cleome spp. e G. gynandra são atrativas para H. phidilealis. Assim, o plantio de uma pequena área com essas espécies pode ser uma alternativa para o manejo dessa praga, uma vez que, haverá concentração da praga nessas plantas (armadilha) que poderão ser eliminadas manualmente ou com aplicação de inseticidas. A principal tática de controle recomendada é a remoção de plantas infestadas, assim como lagartas ou pupas encontradas na área já que o cultivo dessas olerícolas não é tão extenso.'),
(21, 'Vaquinha ', 'Chrysomelidae', 'Coleoptera', 'O adulto de D. speciosa é um besouro de coloração verde, com manchas amarelas, com 5 mm de comprimento, também conhecido como “patriota” ou “brasileirinho” em alusão a sua coloração. As posturas são feitas normalmente no solo; porém, quando o nível populacional é muito alto, pode colocar ovos na face inferior das folhas. A fêmea de D. speciosa deposita os ovos, aderentes uns aos outros, no solo ao redor das plantas. Os ovos são amarelos e medem 0,5 mm de diâmetro. As larvas são cilíndricas, com cerca de 10 mm, corpo com coloração esbranquiçada, com a cabeça e o ápice do abdome, de coloração preta, sendo conhecidas vulgarmente como larva-alfinete. Após o período larval, as larvas transformam-se em pupas no solo.', 'Diabrotica speciosa (Germar)', 'Avaliar as plantas individualmente verificando a presença da praga ou de desfolha na planta.', 'Sem referência.', 'Varia de 24 até 40 dias.', 'Os adultos de D. speciosa alimentam-se de folhas e flores, enquanto que as larvas (larva alfinete) se alimentam das raízes, inviabilizando-as para a comercialização, afetando diretamente a produção. Nas raízes a larva danifica o sistema causando sintomas aparentando deficiência de nutrientes, as plantas emitem raízes adventícias nos nós e desenvolvem um colmo em forma curvada em um formato de “pescoço de ganso. Ataca as culturas da batata, melancia, melão, tomate, solanáceas, cucurbitáceas, crucíferas, feijoeiro, soja, girassol, milho causando desfolha e em alguns casos são vetores de patógenos. No milho os adultos alimentarem das folhas e dos estilo-estígmas nas espigas, a fase larval é considerada problemática para a lavoura. As larvas apresentam uma distribuição em “reboleira”, sendo alta a variabilidade, ocorrendo de 0-100 larvas por planta. A mobilidade é pequena e o desenvolvimento da larva é favorecido pela maior umidade e matéria orgânica existente no solo. Cerca de 90% das larvas se concentram ao redor das plantas, sendo o primeiro instar disperso e os demais localizados na camada de 10 cm da superfície do solo.', 'Realizar a amostragem semanalmente.', 'Sem referência.', 'Pode atuar em todo o ciclo da planta.', 'Como alternativa de manejo, pode-se utilizar iscas atrativas, pois os adultos de crisomelídeos são atraídos por raízes do taiuiá (Cayaponia tayuya) ou pelos frutos da cabaça verde (Lagenaria sp.), ambas cucurbitáceas silvestres. Pedaços destas estruturas vegetais são colocados dentro de bacias com água e detergente, e periodicamente quando as iscas se saturarem de insetos deve se proceder a sua imersão na água, o que ocasionará a morte das vaquinhas por afogamento, auxiliando na redução populacional dessa praga no cultivo.  Outra estratégia para alguns cultivos é o uso de controle cultural, sendo importante considerar que a umidade e o método de preparo de solo podem afetar a população de larvas. Os adultos têm uma nítida preferência para ovipositar em solos mais escuros com maiores teores de matéria orgânica e de umidade. Outro fator que afeta a população de larvas é o método de preparo do solo. A ocorrência de larvas de D. speciosa tem sido relatada como mais frequentes nos sistemas de plantio convencional do que no sistema de plantio direto. Tambem é possível utilizar como medidas de controle, o uso de plástico amarelo impregnado com óleo para atrair os adultos, os quais morrem ao fixar-se no plástico.'),
(23, 'Bicho mineiro', 'Lyonetiidae', 'Lepidoptera', 'O adulto do bicho-mineiro é uma pequena mariposa de 2 mm de comprimento e 6,5 mm de envergadura (medidas médias), com as asas brancas na parte dorsal e uma mancha escura na ponta. A mariposa abriga-se durante o dia na face inferior das folhas da parte inferior do cafeeiro, e ao anoitecer abandona o esconderijo, iniciando a oviposição. Os ovos são achatados, brancos, com cerca de 0,3 mm de comprimento. São postos na página superior das folhas, em média sete ovos por noite, em pontos isolados da mesma folha ou em folhas diferentes. Após a fase de ovo, eclode a lagarta que atinge o comprimento de aproximadamente 3,5 mm. A infestação do bicho-mineiro manifesta-se quando a lagarta penetra na folha e aloja-se no seu interior começando a alimentar-se formando as minas, daí o nome popular bicho-mineiro. A fase de lagarta é encerrada quando deixa de se alimentar, abandona a lesão, tece um fio de seda, e desce para transformar-se em pupa nas folhas do terço inferior do cafeeiro, geralmente na face inferior, após construir um casulo com proteção de fios de seda em forma de X. Geralmente se encontra um casulo por folha, mas em condições de alta infestação pode ocorrer mais de uma e até várias.', 'Leucoptera coffeella', 'Avaliar a presença de minas ativas do bicho mineiro no 4º par de folhas em 4 ramos (2 no terço superior e 2 no terço inferior) de cada uma das 4 faces da planta- sempre escolher os ramos ao acaso. Cada amostra se trata de cada folha que será analisada, verificando se encontrou ou não minas ativas, portanto cada planta apresenta 32 amostras.', 'Normalmente a maior intensidade de ataque da praga é verificada no período de estiagem. A temperatura exerce influência positiva sobre o aumento populacional da praga, ao contrário da umidade. A presença de inimigos naturais (parasitos, predadores e patógenos) da praga reduz significativamente o nível de infestação do bicho-mineiro. Lavouras com espaçamentos maiores, favorecem as infestações dessa praga. A nutrição da planta, também exerce influência, pois cafezais bem nutridos resistem melhor à praga. Cafezais em regiões mais quentes apresentam maior número de gerações da praga, e, portanto, maior o risco de prejuízos. Em locais onde as temperaturas são menores, a praga aparece apenas em determinadas épocas e em níveis populacionais menores. ', 'O ciclo evolutivo dessa praga é influenciado por fatores climáticos, principalmente temperatura, umidade relativa do ar e precipitação pluvial, podendo variar de 19 a 87 dias.', 'As larvas formam minas nas folhas, e os prejuízos são devidos à redução da capacidade fotossintética, pela destruição das folhas (necrose dos tecidos afetados) e, principalmente, pela queda das mesmas. Se o ataque for intenso, ocorre a desfolha da planta, de cima para baixo, devido ocorrer maior infestação na parte superior da planta. Em geral, os cafeeiros que sofrem intenso ataque do bicho mineiro, apresentam o topo completamente desfolhado e podem levar até dois anos para se recuperarem, principalmente se a desfolha ocorrer num ano de grande produção de café. Geralmente, os prejuízos aparecem na safra seguinte, em que desfolhas drásticas sucessivas tornam as plantas enfraquecidas, e compromete a longevidade das mesmas.', 'Realizar amostragem a cada 15 dias com uso da lupa de 10x de aumento.', 'Possui hábito noturno e o seu ataque ocorre durante todo o ano, tendo um pico populacional entre os meses de outubro e junho em algumas regiões.', 'sem referência.', 'Faixas de vegetação entre talhões permitem aumento da população de inimigos naturais, sendo recomendado o manejo racional das espontâneas, utilização de cobertura morta e de culturas intercalares na formação das lavouras de café conilon, entre outras orientações baseadas nos princípios da agroecologia. O adensamento de lavouras favorece a manutenção de umidade mais elevada e podem desfavorecer o desenvolvimento populacional dessa praga.'),
(24, 'Broca do café', 'Scolytidae', 'Coleoptera', 'Os ovos são brancos, elípticos, com brilho leitoso e diminuto (0,5 a 0,8 mm de comprimento). Após quatro a dez dias de postura, nascem as larvas, com comprimento entre 0,72 e 0,84 mm. No início, alimentam-se desagregando pequenas partículas da câmara em que nasceram. Decorridos alguns dias, isto é, quando as larvas estão em pleno crescimento, a semente já perdeu quase totalmente o peso. Após essa fase, a larva transforma-se em pupa no interior da semente destruída, fase do ciclo em que não se alimenta. Apresenta coloração branca nos três ou quatro primeiros dias, cabeça completamente encoberta pelo pronoto, antenas e peças bucais livres e distintas, de cores castanho-claras. O comprimento varia de acordo com o sexo. As pupas fêmeas medem, em média, 1,8 mm de comprimento e os machos, 1,3 mm. O adulto da broca é um besourinho preto luzidio; de corpo cilíndrico e ligeiramente recurvado para a região posterior. As fêmeas medem 1,65 mm de comprimento, por 0,67 mm de largura e 0,73 mm de altura, enquanto os machos, que são bem menores, medem 1,18 mm de comprimento, por 0,51 mm de largura e 0,55 mm de altura. As fêmeas apresentam asas membranosas normais e voam. Os machos não voam, permanecendo nas sementes dos frutos, de onde se originam. ', 'Hypothenemus hampei', 'Avaliar se encontrou ou não frutos broqueados em diversos ramos e rosetas. Cada amostra trata-se de um fruto, portanto serão amostrados em cada planta 60 frutos. Deve-se escolher ao acaso 6 pontos diferentes na saia, meio e topo em 2 faces da planta, em cada um destes pontos serão avaliados 10 frutos. Considera-se o fruto broqueado aquele que apresentar perfuração na região da coroa, com galeria. O caminhamento pode ser realizado em zigue-zague.', 'Altas temperaturas causam redução do ciclo de vida do inseto e, conseqüentemente, aumento do número de gerações e maiores prejuízos por ocasião da colheita do café. Nos anos onde ocorrem maiores precipitações durante o período de frutificação e maturação dos frutos, a taxa de incremento de população da broca é menor. A ocorrência de estiagens pode favorecer a infestação da broca-do-café. Um inverno seco com baixa umidade relativa do ar desfavorece a sobrevivência da broca, e inverno úmido com muito orvalho favorece a sua sobrevivência. A baixa umidade relativa do ar provoca ressecamento dos frutos, que em primeiro lugar, reduz a multiplicação da broca; depois paralisa a postura e, finalmente, provoca a morte do inseto. As fêmeas não ovipositam em frutos com umidade inferior a 12%, 13%. Uma boa colheita consiste em não deixar frutos na planta e no chão, o que impede a sobrevivência da broca, a qual ataca somente o cafeeiro, seu único hospedeiro. Sombreamento e espaçamentos adensados podem favorecer a infestação da broca, pela redução da luminosidade e manutenção de maior umidade no cafezal. Outra razão para o favorecimento da broca é que lavouras adensadas e fechadas facilitam a dispersão dos adultos da broca de uma planta a outra. A altitude elevada paralisa a atividade do inseto, cujos adultos apresentam uma longevidade menor.', 'O ciclo evolutivo da broca-do-café desde a postura até a emergência do adulto completa-se de 27 a 30 dias.', 'Os danos causados nos frutos são provocados pela broca, que põe os ovos no interior da semente do café. Para isso, abre uma galeria, geralmente na região da coroa do fruto, de cerca de 1mm de diâmetro até alcançar a semente. Perfurada a semente, a galeria é alargada e transformada naquele ponto em câmara, na qual deposita seus ovos. Em geral a oviposição começa quando os frutos se tornam bem granados (janeiro-fevereiro). Verifica-se logo após a colheita, que a broca para sua atividade até a nova frutificação dos cafeeiros, por não haver frutos que ofereçam condições para a postura e desenvolvimento das proles. A postura é feita em frutos verdes com a semente formada, maduros (cerejas) e secos. Em frutos na fase inicial de crescimento (\"chumbinho\"), as fêmeas podem perfurá-los, porém com o conteúdo muito aquoso, são abandonados sem realizar a oviposição, ficando esses frutos comprometidos. O ataque da broca proporciona uma porta de entrada para microorganismos, os quais, sob condições propícias, podem desenvolver-se, atingindo os grãos e alterando a qualidade da bebida do café. Os prejuízos causados pela broca-do-café são: perda de peso do café beneficiado, devido a sua destruição pelas larvas; perda da qualidade, pela depreciação do produto na classificação por tipo, pois cinco sementes broqueadas constituem um defeito; queda prematura de frutos quando perfurados; apodrecimento de sementes em frutos broqueados, que apresentam maturação forçada, caindo precocemente no chão; inviabilidade de produção de sementes de café, já que os frutos broqueados são descartados; perda de mercado externo, já que os países importadores de café não aceitam nenhum café broqueado.', 'Monitorar de 80 a 90 dias após a principal florada, no período de trânsito da praga. Recomenda-se o monitoramento mensal da praga. Porém, em períodos de alta infestação, o monitoramento deverá ocorrer quinzenalmente.', 'sem referência.', 'Tem a capacidade de atacar os frutos em todos os estágios de maturação, de verde até maduro (cereja) ou seco.', 'O controle cultural consta de cuidados por ocasião da colheita, evitando-se a permanência de frutos na planta ou no solo, impedindo assim, a sobrevivência da broca nos frutos remanescentes de café na entressafra. O repasse é uma operação que é destinada a eliminar os focos da broca que ficam na lavoura depois da colheita. Esses focos são constituídos por frutos de café verdes, maduros ou secos, nos quais a broca se abriga no intervalo de safras e que tanto podem estar nos cafeeiros, ainda pendentes, como sobre o solo. O uso de espaçamentos maiores afeta a luminosidade, e ajudam a diminuir a infestação da praga. Em propriedades menores, o controle da broca-do-café pode ser feito utilizando-se de armadilhas alternativas. As armadilhas podem ser construídas com garrafas pet pintadas com a cor vermelha. A substância atraente dos insetos pode ser feita com a mistura de 1:3 de etanol e metanol, acrescida de 1% de ácido benzoico. Para o controle eficiente recomenda-se a utilização de 30 armadilhas por hectare. A substância atraente deve ser substituída a cada duas semanas.'),
(25, 'Broca do rizoma/moleque da bananeira', 'Curculionidae', 'Coleptera', 'A broca da bananeira é relatada como o principal inseto praga da cultura, sendo encontrada em quase todos os países produtores de banana. O inseto adulto é um besouro que mede cerca de 11 mm de comprimento por 4 mm de largura e possui coloração preta. Apresenta um “bico” proeminente, característico da família Curculionidae. As fêmeas, com suas mandíbulas abrem pequenas cavidades no rizoma ou na base do pseudocaule, onde colocam seus ovos durante o ano todo. As larvas apresentam coloração branca, são ápodas e quando completamente desenvolvidas alcançam 12 mm de comprimento. O período larval varia de 14 a 48 dias, após os quais as larvas dirigem-se para as extremidades das galerias próximas da superfície externa do rizoma preparando câmaras ovaladas, onde se transformam em pupas e permanecem nessa forma por um período 7 a 10 dias.', 'Cosmopolites sordidus', 'Antes de realizar a amostragem você já deverá ter as 20 iscas/armadilhas de pseudocaule instaladas em seu cultivo de 1 ha. Para amostragem serão considerados as plantas como sendo iscas/armadilhas, portanto o número de plantas como sendo o número de armadilhas a serem avaliadas. O número de insetos capturados em cada armadilha deve ser contabilizado para possibilitar a obtenção do número médio de adultos da broca-do-rizoma/ha. Como forma de facilitar a amostragem dos insetos, definimos o número máximo de 50 insetos por iscas/armadilhas. Portanto deve-se amostrar 50 insetos por iscas/armadilhas, caso tenha menos insetos basta preencher o restante das amostras como não encontrado.', 'O adulto é encontrado em ambientes úmidos e sombreados.', 'O ciclo evolutivo oscila de 23 a 70 dias, conforme as condições climáticas.', 'As primeiras manifestações de ataque da broca se caracterizam externamente pelo aspecto da planta, cujas folhas amarelecem, e pelos cachos que se tornam pequenos. Estima-se que, no Brasil, ocorra uma redução média de 30% na produção, devido ao seu ataque. O dano direto é causado pela larva que penetra e broqueia o rizoma, construindo galerias em todas as direções, provocando os sintomas acima descritos. As galerias abertas propiciam a entrada de microrganismos fitopatogênicos, entre os quais se destaca o Fusarium oxysporum f. cubense, responsável pela doença conhecida como “Mal do Panamá”. É comum em plantações intensamente atacadas a queda de plantas que já lançaram cachos, tombadas devido à falta de um sistema radicular vivo, suficiente para sustentar o acréscimo de peso dos mesmos.', 'Para amostragem de adultos do moleque-da-bananeira são utilizadas iscas confeccionadas com o pseudocaule da bananeira que já produziu. Existem dois tipos de iscas mais comuns, conhecidas como “telha” e “queijo”.  Armadilha tipo “queijo” é confeccionada rebaixando-se o pseudocaule de planta colhida a uma altura de 40 a 60 cm do solo, e efetuando-se um novo corte no sentido longitudinal, a cerca de 15 cm da base. Esse corte deve ser de preferência parcial, para evitar o tombamento da parte superior da armadilha.  A armadilha tipo “telha” é cada metade de um pedaço de pseudocaule, de aproximadamente 60 cm de comprimento, partido ao meio no sentido longitudinal. Dessa forma, cada pedaço de pseudocaule fornece duas armadilhas, as quais devem ser distribuídas com a face cortada em contato com o solo, na base da planta. Instalar cerca de 20 iscas por hectare para monitoramento da população. As avaliações são realizadas quinzenalmente (verificar as fotos da praga onde são demostradas as armadilhas prontas).', 'O inseto tem hábito noturno, abrigando-se durante o dia nas touceiras, bainhas das folhas e restos de cultura.', 'sem referência. ', 'Os meios culturais de combate à praga são baseados na destruição dos restos de cultura onde o besouro se abriga e alimenta. Durante a colheita os pseudocaules devem ser cortados o mais rente do solo e suas partes picadas e espalhadas na plantação. A procedência e o tratamento das mudas devem ser rigorosamente considerados para evitar a entrada do inseto na plantação.');

-- --------------------------------------------------------

--
-- Estrutura da tabela `PresencaPraga`
--

CREATE TABLE `PresencaPraga` (
  `Cod_PresencaPraga` int(11) NOT NULL,
  `Status` int(1) NOT NULL DEFAULT 1,
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `PresencaPraga`
--

INSERT INTO `PresencaPraga` (`Cod_PresencaPraga`, `Status`, `fk_Praga_Cod_Praga`, `fk_Cultura_Cod_Cultura`) VALUES
(2, 1, 1, 11),
(14, 1, 4, 11),
(16, 0, 7, 11),
(19, 1, 1, 19),
(20, 1, 1, 43),
(36, 2, 4, 52),
(37, 0, 5, 11),
(42, 1, 3, 11),
(53, 0, 10, 52),
(54, 1, 7, 52),
(55, 2, 1, 52),
(56, 1, 9, 52),
(57, 1, 3, 52),
(58, 0, 10, 72),
(59, 0, 8, 72),
(61, 1, 5, 72),
(62, 0, 1, 72),
(63, 1, 10, 73),
(64, 0, 10, 79),
(65, 2, 11, 79),
(70, 1, 1, 79),
(73, 1, 1, 82),
(90, 0, 3, 79),
(91, 1, 8, 11),
(113, 0, 21, 79),
(114, 1, 10, 43),
(115, 2, 7, 79),
(116, 1, 6, 79),
(117, 1, 6, 43),
(118, 1, 6, 88),
(120, 1, 1, 90),
(122, 2, 1, 91),
(123, 0, 10, 91),
(124, 1, 3, 91),
(130, 1, 10, 88),
(131, 1, 3, 95),
(133, 1, 19, 95),
(136, 1, 23, 96),
(137, 1, 23, 97),
(138, 1, 24, 97),
(141, 1, 14, 95),
(142, 1, 5, 95),
(143, 1, 14, 11),
(144, 1, 14, 19),
(145, 1, 10, 95),
(146, 1, 8, 95),
(147, 1, 9, 95),
(148, 1, 9, 11),
(149, 1, 4, 95),
(150, 1, 1, 95),
(151, 1, 6, 95),
(152, 1, 7, 95),
(153, 1, 21, 95),
(154, 1, 17, 93),
(155, 1, 17, 98),
(156, 1, 18, 98),
(157, 1, 16, 98),
(158, 1, 13, 99),
(159, 1, 20, 100),
(160, 1, 16, 100),
(161, 2, 25, 101);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Produtor`
--

CREATE TABLE `Produtor` (
  `Cod_Produtor` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Produtor`
--

INSERT INTO `Produtor` (`Cod_Produtor`, `fk_Usuario_Cod_Usuario`) VALUES
(1, 4),
(2, 5),
(3, 14),
(4, 17),
(5, 19);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Propriedade`
--

CREATE TABLE `Propriedade` (
  `Cod_Propriedade` int(11) NOT NULL,
  `Nome` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Cidade` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Estado` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `fk_Produtor_Cod_Produtor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Propriedade`
--

INSERT INTO `Propriedade` (`Cod_Propriedade`, `Nome`, `Cidade`, `Estado`, `fk_Produtor_Cod_Produtor`) VALUES
(3, 'Genesis', 'Ibiúna', 'SP', 2),
(4, 'Fazenda maneira', 'Rio Pomba', 'MG', 2),
(5, 'Lugar Legal', 'Rio Pomba', 'MG', 2),
(14, 'oi', 'Salvador', 'BA', 2),
(23, 'teste', 'teste', 'AC', 2),
(26, 'ukg', 'bhg', 'AC', 2),
(27, 'Fazenda', 'Rio Pomba', 'MG', 4),
(28, 'fazenda olhos dagua ', 'astolfo dutra', 'MG', 2),
(29, 'teste', 'rio pomba', 'AM', 5),
(30, 'Exemplo', 'Rio Pomba', 'MG', 2),
(31, 'Propriedade1', 'Rio Pomba', 'MG', 1),
(32, 'novo', 'novo', 'AC', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Talhao`
--

CREATE TABLE `Talhao` (
  `Cod_Talhao` int(11) NOT NULL,
  `NumeroDePlantas` int(11) DEFAULT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Talhao`
--

INSERT INTO `Talhao` (`Cod_Talhao`, `NumeroDePlantas`, `fk_Cultura_Cod_Cultura`, `fk_Planta_Cod_Planta`) VALUES
(10, NULL, 11, 1),
(11, NULL, 11, 1),
(12, NULL, 11, 1),
(13, NULL, 11, 1),
(17, NULL, 19, 1),
(18, NULL, 19, 1),
(38, NULL, 43, 1),
(49, NULL, 51, 1),
(50, NULL, 52, 1),
(67, NULL, 72, 1),
(68, NULL, 73, 4),
(71, NULL, 76, 5),
(72, NULL, 76, 5),
(77, NULL, 79, 4),
(80, NULL, 82, 4),
(89, NULL, 88, 4),
(90, NULL, 88, 4),
(92, NULL, 90, 1),
(93, NULL, 90, 1),
(94, NULL, 90, 1),
(95, NULL, 90, 1),
(96, NULL, 91, 4),
(97, NULL, 91, 4),
(98, NULL, 91, 4),
(99, NULL, 91, 4),
(102, NULL, 93, 5),
(103, NULL, 94, 3),
(104, NULL, 94, 3),
(105, NULL, 94, 3),
(106, NULL, 94, 3),
(107, NULL, 95, 1),
(108, NULL, 95, 1),
(109, NULL, 96, 9),
(110, NULL, 97, 9),
(111, NULL, 98, 5),
(112, NULL, 99, 3),
(113, NULL, 100, 7),
(114, NULL, 101, 11),
(115, NULL, 102, 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Trabalha`
--

CREATE TABLE `Trabalha` (
  `Cod_Trabalha` int(11) NOT NULL,
  `fk_Propriedade_Cod_Propriedade` int(11) NOT NULL,
  `fk_Funcionario_Cod_Funcionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Trabalha`
--

INSERT INTO `Trabalha` (`Cod_Trabalha`, `fk_Propriedade_Cod_Propriedade`, `fk_Funcionario_Cod_Funcionario`) VALUES
(1, 3, 1),
(2, 3, 3),
(3, 3, 4),
(4, 5, 5),
(6, 3, 5),
(8, 3, 6),
(9, 27, 5),
(10, 30, 1),
(11, 30, 10),
(14, 30, 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `Usuario`
--

CREATE TABLE `Usuario` (
  `Cod_Usuario` int(11) NOT NULL,
  `Nome` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Telefone` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Senha` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `Usuario`
--

INSERT INTO `Usuario` (`Cod_Usuario`, `Nome`, `Telefone`, `Email`, `Senha`) VALUES
(4, 'Monica Simão Giponi', '15998075241', 'monicagiponi@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(5, 'Rodolfo Chagas Marinho Nascimento', '32999183003', 'nsc.rodolfo@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(6, 'José Carlos', '15999999999', 'jose@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(7, 'Func1', '988888888', 'func1@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(8, 'Func2', '9999999', 'rodolfo__nascimento@hooooootmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(9, 'func3', '11111111', 'func3', 'e10adc3949ba59abbe56e057f20f883e'),
(10, 'func4', '4', 'func4@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(11, 'adm', '123', 'adm@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(12, 'Funcionário 1', '1234567890', 'funcionario1@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(13, 'jose', '15123456789', 'josee@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(14, 'produtor', '24673945449', 'prod@gmail.com', 'b482c66715c377d759c39ac96aa9fa4b'),
(15, 'funci', '97679494546', 'funci@gmail.com', 'b482c66715c377d759c39ac96aa9fa4b'),
(16, 'funci1', '97664994649', 'funci1@gmail.com', 'e807f1fcf82d132f9bb018ca6738a19f'),
(17, 'proprietario', '12999999999', 'proprietario@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(18, 'ze da cove', '34934491938', 'zedacove@teste.com', 'e10adc3949ba59abbe56e057f20f883e'),
(19, 'ze', '66665856596', 'ze@gmail.com', 'e10adc3949ba59abbe56e057f20f883e');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `Administrador`
--
ALTER TABLE `Administrador`
  ADD PRIMARY KEY (`Cod_Administrador`),
  ADD KEY `FK_Administrador_2` (`fk_Usuario_Cod_Usuario`);

--
-- Índices para tabela `Aplicacao`
--
ALTER TABLE `Aplicacao`
  ADD PRIMARY KEY (`Cod_Aplicacao`),
  ADD KEY `FK_Aplicacao_2` (`fk_MetodoDeControle_Cod_MetodoControle`),
  ADD KEY `FK_Aplicacao_3` (`fk_Cultura_Cod_Cultura`),
  ADD KEY `FK_Aplicacao_4` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `Aplicacao_Plano`
--
ALTER TABLE `Aplicacao_Plano`
  ADD PRIMARY KEY (`Cod_Aplicacao`),
  ADD KEY `FK_Aplicacao_Plano_2` (`fk_Aplicacao_Cod_Aplicacao`);

--
-- Índices para tabela `Atinge`
--
ALTER TABLE `Atinge`
  ADD PRIMARY KEY (`Cod_Atinge`),
  ADD KEY `FK_Atinge_2` (`fk_Praga_Cod_Praga`),
  ADD KEY `FK_Atinge_3` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `Combate`
--
ALTER TABLE `Combate`
  ADD PRIMARY KEY (`Cod_Combate`),
  ADD KEY `FK_Combate_1` (`fk_Praga_Cod_Praga`),
  ADD KEY `FK_Combate_2` (`fk_InimigoNatural_Cod_Inimigo`);

--
-- Índices para tabela `Controla`
--
ALTER TABLE `Controla`
  ADD PRIMARY KEY (`Cod_Controla`),
  ADD KEY `FK_Controla_1` (`fk_MetodoDeControle_Cod_MetodoControle`),
  ADD KEY `FK_Controla_2` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `Cultura`
--
ALTER TABLE `Cultura`
  ADD PRIMARY KEY (`Cod_Cultura`),
  ADD KEY `FK_Cultura_2` (`fk_Propriedade_Cod_Propriedade`),
  ADD KEY `FK_Cultura_3` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `FotoAmostra`
--
ALTER TABLE `FotoAmostra`
  ADD PRIMARY KEY (`Cod_FotoAmostra`),
  ADD KEY `FK_FotoAmostra_2` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `FotoInimigo`
--
ALTER TABLE `FotoInimigo`
  ADD PRIMARY KEY (`Cod_FotoInimigo`),
  ADD KEY `FK_FotoInimigo_2` (`fk_InimigoNatural_Cod_Inimigo`);

--
-- Índices para tabela `FotoMetodo`
--
ALTER TABLE `FotoMetodo`
  ADD PRIMARY KEY (`Cod_FotoMetodo`),
  ADD KEY `FK_FotoMetodo_2` (`fk_MetodoDeControle_Cod_MetodoControle`);

--
-- Índices para tabela `FotoPlanta`
--
ALTER TABLE `FotoPlanta`
  ADD PRIMARY KEY (`Cod_FotoPlanta`),
  ADD KEY `FK_FotoPlanta_2` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `FotoPraga`
--
ALTER TABLE `FotoPraga`
  ADD PRIMARY KEY (`Cod_FotoPraga`),
  ADD KEY `FK_FotoPraga_2` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `FotoUsuario`
--
ALTER TABLE `FotoUsuario`
  ADD PRIMARY KEY (`Cod_FotoUsuario`),
  ADD KEY `FK_FotoUsuario_2` (`fk_Usuario_Cod_Usuario`);

--
-- Índices para tabela `Funcionario`
--
ALTER TABLE `Funcionario`
  ADD PRIMARY KEY (`Cod_Funcionario`),
  ADD KEY `FK_Funcionario_2` (`fk_Usuario_Cod_Usuario`);

--
-- Índices para tabela `InimigoNatural`
--
ALTER TABLE `InimigoNatural`
  ADD PRIMARY KEY (`Cod_Inimigo`);

--
-- Índices para tabela `MetodoDeControle`
--
ALTER TABLE `MetodoDeControle`
  ADD PRIMARY KEY (`Cod_MetodoControle`);

--
-- Índices para tabela `PlanoAmostragem`
--
ALTER TABLE `PlanoAmostragem`
  ADD PRIMARY KEY (`Cod_Plano`),
  ADD KEY `FK_PlanoAmostragem_2` (`fk_Talhao_Cod_Talhao`),
  ADD KEY `FK_PlanoAmostragem_3` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `Planta`
--
ALTER TABLE `Planta`
  ADD PRIMARY KEY (`Cod_Planta`);

--
-- Índices para tabela `Praga`
--
ALTER TABLE `Praga`
  ADD PRIMARY KEY (`Cod_Praga`);

--
-- Índices para tabela `PresencaPraga`
--
ALTER TABLE `PresencaPraga`
  ADD PRIMARY KEY (`Cod_PresencaPraga`),
  ADD KEY `FK_PresencaPraga_2` (`fk_Cultura_Cod_Cultura`),
  ADD KEY `FK_PresencaPraga_3` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `Produtor`
--
ALTER TABLE `Produtor`
  ADD PRIMARY KEY (`Cod_Produtor`),
  ADD KEY `FK_Produtor_2` (`fk_Usuario_Cod_Usuario`);

--
-- Índices para tabela `Propriedade`
--
ALTER TABLE `Propriedade`
  ADD PRIMARY KEY (`Cod_Propriedade`),
  ADD KEY `FK_Propriedade_2` (`fk_Produtor_Cod_Produtor`);

--
-- Índices para tabela `Talhao`
--
ALTER TABLE `Talhao`
  ADD PRIMARY KEY (`Cod_Talhao`),
  ADD KEY `FK_Talhao_3` (`fk_Planta_Cod_Planta`),
  ADD KEY `FK_Talhao_2` (`fk_Cultura_Cod_Cultura`);

--
-- Índices para tabela `Trabalha`
--
ALTER TABLE `Trabalha`
  ADD PRIMARY KEY (`Cod_Trabalha`),
  ADD KEY `FK_Trabalha_2` (`fk_Funcionario_Cod_Funcionario`),
  ADD KEY `FK_Trabalha_1` (`fk_Propriedade_Cod_Propriedade`);

--
-- Índices para tabela `Usuario`
--
ALTER TABLE `Usuario`
  ADD PRIMARY KEY (`Cod_Usuario`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `Administrador`
--
ALTER TABLE `Administrador`
  MODIFY `Cod_Administrador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `Aplicacao`
--
ALTER TABLE `Aplicacao`
  MODIFY `Cod_Aplicacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de tabela `Aplicacao_Plano`
--
ALTER TABLE `Aplicacao_Plano`
  MODIFY `Cod_Aplicacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de tabela `Atinge`
--
ALTER TABLE `Atinge`
  MODIFY `Cod_Atinge` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT de tabela `Combate`
--
ALTER TABLE `Combate`
  MODIFY `Cod_Combate` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT de tabela `Controla`
--
ALTER TABLE `Controla`
  MODIFY `Cod_Controla` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=138;

--
-- AUTO_INCREMENT de tabela `Cultura`
--
ALTER TABLE `Cultura`
  MODIFY `Cod_Cultura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT de tabela `FotoAmostra`
--
ALTER TABLE `FotoAmostra`
  MODIFY `Cod_FotoAmostra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de tabela `FotoInimigo`
--
ALTER TABLE `FotoInimigo`
  MODIFY `Cod_FotoInimigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT de tabela `FotoMetodo`
--
ALTER TABLE `FotoMetodo`
  MODIFY `Cod_FotoMetodo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT de tabela `FotoPlanta`
--
ALTER TABLE `FotoPlanta`
  MODIFY `Cod_FotoPlanta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de tabela `FotoPraga`
--
ALTER TABLE `FotoPraga`
  MODIFY `Cod_FotoPraga` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=511;

--
-- AUTO_INCREMENT de tabela `FotoUsuario`
--
ALTER TABLE `FotoUsuario`
  MODIFY `Cod_FotoUsuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Funcionario`
--
ALTER TABLE `Funcionario`
  MODIFY `Cod_Funcionario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de tabela `InimigoNatural`
--
ALTER TABLE `InimigoNatural`
  MODIFY `Cod_Inimigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de tabela `MetodoDeControle`
--
ALTER TABLE `MetodoDeControle`
  MODIFY `Cod_MetodoControle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de tabela `PlanoAmostragem`
--
ALTER TABLE `PlanoAmostragem`
  MODIFY `Cod_Plano` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=378;

--
-- AUTO_INCREMENT de tabela `Planta`
--
ALTER TABLE `Planta`
  MODIFY `Cod_Planta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de tabela `Praga`
--
ALTER TABLE `Praga`
  MODIFY `Cod_Praga` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de tabela `PresencaPraga`
--
ALTER TABLE `PresencaPraga`
  MODIFY `Cod_PresencaPraga` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=162;

--
-- AUTO_INCREMENT de tabela `Produtor`
--
ALTER TABLE `Produtor`
  MODIFY `Cod_Produtor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `Propriedade`
--
ALTER TABLE `Propriedade`
  MODIFY `Cod_Propriedade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de tabela `Talhao`
--
ALTER TABLE `Talhao`
  MODIFY `Cod_Talhao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT de tabela `Trabalha`
--
ALTER TABLE `Trabalha`
  MODIFY `Cod_Trabalha` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de tabela `Usuario`
--
ALTER TABLE `Usuario`
  MODIFY `Cod_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `Administrador`
--
ALTER TABLE `Administrador`
  ADD CONSTRAINT `FK_Administrador_2` FOREIGN KEY (`fk_Usuario_Cod_Usuario`) REFERENCES `Usuario` (`Cod_Usuario`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Aplicacao`
--
ALTER TABLE `Aplicacao`
  ADD CONSTRAINT `FK_Aplicacao_2` FOREIGN KEY (`fk_MetodoDeControle_Cod_MetodoControle`) REFERENCES `MetodoDeControle` (`Cod_MetodoControle`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Aplicacao_3` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `Cultura` (`Cod_Cultura`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Aplicacao_4` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Aplicacao_Plano`
--
ALTER TABLE `Aplicacao_Plano`
  ADD CONSTRAINT `FK_Aplicacao_Plano_2` FOREIGN KEY (`fk_Aplicacao_Cod_Aplicacao`) REFERENCES `Aplicacao` (`Cod_Aplicacao`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Atinge`
--
ALTER TABLE `Atinge`
  ADD CONSTRAINT `FK_Atinge_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Atinge_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `Planta` (`Cod_Planta`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Combate`
--
ALTER TABLE `Combate`
  ADD CONSTRAINT `FK_Combate_1` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Combate_2` FOREIGN KEY (`fk_InimigoNatural_Cod_Inimigo`) REFERENCES `InimigoNatural` (`Cod_Inimigo`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Controla`
--
ALTER TABLE `Controla`
  ADD CONSTRAINT `FK_Controla_1` FOREIGN KEY (`fk_MetodoDeControle_Cod_MetodoControle`) REFERENCES `MetodoDeControle` (`Cod_MetodoControle`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Controla_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Cultura`
--
ALTER TABLE `Cultura`
  ADD CONSTRAINT `FK_Cultura_2` FOREIGN KEY (`fk_Propriedade_Cod_Propriedade`) REFERENCES `Propriedade` (`Cod_Propriedade`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Cultura_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `Planta` (`Cod_Planta`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `FotoAmostra`
--
ALTER TABLE `FotoAmostra`
  ADD CONSTRAINT `FK_FotoAmostra_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`);

--
-- Limitadores para a tabela `FotoInimigo`
--
ALTER TABLE `FotoInimigo`
  ADD CONSTRAINT `FK_FotoInimigo_2` FOREIGN KEY (`fk_InimigoNatural_Cod_Inimigo`) REFERENCES `InimigoNatural` (`Cod_Inimigo`);

--
-- Limitadores para a tabela `FotoMetodo`
--
ALTER TABLE `FotoMetodo`
  ADD CONSTRAINT `FK_FotoMetodo_2` FOREIGN KEY (`fk_MetodoDeControle_Cod_MetodoControle`) REFERENCES `MetodoDeControle` (`Cod_MetodoControle`);

--
-- Limitadores para a tabela `FotoPlanta`
--
ALTER TABLE `FotoPlanta`
  ADD CONSTRAINT `FK_FotoPlanta_2` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `Planta` (`Cod_Planta`);

--
-- Limitadores para a tabela `FotoPraga`
--
ALTER TABLE `FotoPraga`
  ADD CONSTRAINT `FK_FotoPraga_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `FotoUsuario`
--
ALTER TABLE `FotoUsuario`
  ADD CONSTRAINT `FK_FotoUsuario_2` FOREIGN KEY (`fk_Usuario_Cod_Usuario`) REFERENCES `Usuario` (`Cod_Usuario`);

--
-- Limitadores para a tabela `Funcionario`
--
ALTER TABLE `Funcionario`
  ADD CONSTRAINT `FK_Funcionario_2` FOREIGN KEY (`fk_Usuario_Cod_Usuario`) REFERENCES `Usuario` (`Cod_Usuario`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `PlanoAmostragem`
--
ALTER TABLE `PlanoAmostragem`
  ADD CONSTRAINT `FK_PlanoAmostragem_2` FOREIGN KEY (`fk_Talhao_Cod_Talhao`) REFERENCES `Talhao` (`Cod_Talhao`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_PlanoAmostragem_3` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `PresencaPraga`
--
ALTER TABLE `PresencaPraga`
  ADD CONSTRAINT `FK_PresencaPraga_2` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `Cultura` (`Cod_Cultura`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_PresencaPraga_3` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Produtor`
--
ALTER TABLE `Produtor`
  ADD CONSTRAINT `FK_Produtor_2` FOREIGN KEY (`fk_Usuario_Cod_Usuario`) REFERENCES `Usuario` (`Cod_Usuario`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Propriedade`
--
ALTER TABLE `Propriedade`
  ADD CONSTRAINT `FK_Propriedade_2` FOREIGN KEY (`fk_Produtor_Cod_Produtor`) REFERENCES `Produtor` (`Cod_Produtor`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Talhao`
--
ALTER TABLE `Talhao`
  ADD CONSTRAINT `FK_Talhao_2` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `Cultura` (`Cod_Cultura`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Talhao_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `Planta` (`Cod_Planta`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Trabalha`
--
ALTER TABLE `Trabalha`
  ADD CONSTRAINT `FK_Trabalha_1` FOREIGN KEY (`fk_Propriedade_Cod_Propriedade`) REFERENCES `Propriedade` (`Cod_Propriedade`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Trabalha_2` FOREIGN KEY (`fk_Funcionario_Cod_Funcionario`) REFERENCES `Funcionario` (`Cod_Funcionario`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
