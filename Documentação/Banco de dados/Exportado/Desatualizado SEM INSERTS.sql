-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Tempo de geração: 05-Dez-2019 às 21:54
-- Versão do servidor: 10.3.16-MariaDB
-- versão do PHP: 7.3.10

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

-- --------------------------------------------------------

--
-- Estrutura da tabela `Administrador`
--

CREATE TABLE `Administrador` (
  `Cod_Administrador` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Aplicacao`
--

CREATE TABLE `Aplicacao` (
  `Cod_Aplicacao` int(11) NOT NULL,
  `Data` date NOT NULL,
  `Estacao` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Temperatura` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `UmidadeDoAr` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Chuva` tinyint(1) DEFAULT NULL,
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Combate`
--

CREATE TABLE `Combate` (
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_InimigoNatural_Cod_Inimigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Controla`
--

CREATE TABLE `Controla` (
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Cultura`
--

CREATE TABLE `Cultura` (
  `Cod_Cultura` int(11) NOT NULL,
  `TamanhoDaCultura` float DEFAULT NULL,
  `fk_Propriedade_Cod_Propriedade` int(11) NOT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoInimigo`
--

CREATE TABLE `FotoInimigo` (
  `Cod_FotoInimigo` int(11) NOT NULL,
  `FotoInimigo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_InimigoNatural_Cod_Inimigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoMetodo`
--

CREATE TABLE `FotoMetodo` (
  `Cod_FotoMetodo` int(11) NOT NULL,
  `FotoMetodo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoPlanta`
--

CREATE TABLE `FotoPlanta` (
  `Cod_FotoPlanta` int(11) NOT NULL,
  `FotoPlanta` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `FotoPraga`
--

CREATE TABLE `FotoPraga` (
  `Cod_FotoPraga` int(11) NOT NULL,
  `FotoPraga` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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

-- --------------------------------------------------------

--
-- Estrutura da tabela `InimigoNatural`
--

CREATE TABLE `InimigoNatural` (
  `Cod_Inimigo` int(11) NOT NULL,
  `Nome` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Familia` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Ordem` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `NomeCientifico` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Descricao` text COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  `Observacoes` text COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `PlanoAmostragem`
--

CREATE TABLE `PlanoAmostragem` (
  `Cod_Plano` int(11) NOT NULL,
  `Data` date NOT NULL,
  `PlantasInfestadas` int(11) DEFAULT NULL,
  `PlantasAmostradas` int(11) DEFAULT NULL,
  `fk_Talhao_Cod_Talhao` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Planta`
--

CREATE TABLE `Planta` (
  `Cod_Planta` int(11) NOT NULL,
  `Familia` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Temperatura` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PH` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Espacamento` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Solo` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `Nome` text COLLATE utf8_unicode_ci NOT NULL,
  `NomeCientifico` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TamanhoTalhao` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  `EstagioDeAtuacao` text COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `PresencaPraga`
--

CREATE TABLE `PresencaPraga` (
  `Cod_PresencaPraga` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `Produtor`
--

CREATE TABLE `Produtor` (
  `Cod_Produtor` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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

-- --------------------------------------------------------

--
-- Estrutura da tabela `Trabalha`
--

CREATE TABLE `Trabalha` (
  `fk_Propriedade_Cod_Propriedade` int(11) NOT NULL,
  `fk_Funcionario_Cod_Funcionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  ADD KEY `FK_Aplicacao_3` (`fk_Cultura_Cod_Cultura`);

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
  ADD KEY `FK_Combate_1` (`fk_Praga_Cod_Praga`),
  ADD KEY `FK_Combate_2` (`fk_InimigoNatural_Cod_Inimigo`);

--
-- Índices para tabela `Controla`
--
ALTER TABLE `Controla`
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
  ADD KEY `FK_Talhao_2` (`fk_Cultura_Cod_Cultura`),
  ADD KEY `FK_Talhao_3` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `Trabalha`
--
ALTER TABLE `Trabalha`
  ADD KEY `FK_Trabalha_1` (`fk_Propriedade_Cod_Propriedade`),
  ADD KEY `FK_Trabalha_2` (`fk_Funcionario_Cod_Funcionario`);

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
  MODIFY `Cod_Administrador` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Aplicacao`
--
ALTER TABLE `Aplicacao`
  MODIFY `Cod_Aplicacao` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Atinge`
--
ALTER TABLE `Atinge`
  MODIFY `Cod_Atinge` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Cultura`
--
ALTER TABLE `Cultura`
  MODIFY `Cod_Cultura` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `FotoInimigo`
--
ALTER TABLE `FotoInimigo`
  MODIFY `Cod_FotoInimigo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `FotoMetodo`
--
ALTER TABLE `FotoMetodo`
  MODIFY `Cod_FotoMetodo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `FotoPlanta`
--
ALTER TABLE `FotoPlanta`
  MODIFY `Cod_FotoPlanta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `FotoPraga`
--
ALTER TABLE `FotoPraga`
  MODIFY `Cod_FotoPraga` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `FotoUsuario`
--
ALTER TABLE `FotoUsuario`
  MODIFY `Cod_FotoUsuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Funcionario`
--
ALTER TABLE `Funcionario`
  MODIFY `Cod_Funcionario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `InimigoNatural`
--
ALTER TABLE `InimigoNatural`
  MODIFY `Cod_Inimigo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `MetodoDeControle`
--
ALTER TABLE `MetodoDeControle`
  MODIFY `Cod_MetodoControle` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `PlanoAmostragem`
--
ALTER TABLE `PlanoAmostragem`
  MODIFY `Cod_Plano` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Planta`
--
ALTER TABLE `Planta`
  MODIFY `Cod_Planta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Praga`
--
ALTER TABLE `Praga`
  MODIFY `Cod_Praga` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `PresencaPraga`
--
ALTER TABLE `PresencaPraga`
  MODIFY `Cod_PresencaPraga` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Produtor`
--
ALTER TABLE `Produtor`
  MODIFY `Cod_Produtor` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Propriedade`
--
ALTER TABLE `Propriedade`
  MODIFY `Cod_Propriedade` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Talhao`
--
ALTER TABLE `Talhao`
  MODIFY `Cod_Talhao` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Usuario`
--
ALTER TABLE `Usuario`
  MODIFY `Cod_Usuario` int(11) NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `FK_Aplicacao_2` FOREIGN KEY (`fk_MetodoDeControle_Cod_MetodoControle`) REFERENCES `MetodoDeControle` (`Cod_MetodoControle`),
  ADD CONSTRAINT `FK_Aplicacao_3` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `Cultura` (`Cod_Cultura`);

--
-- Limitadores para a tabela `Atinge`
--
ALTER TABLE `Atinge`
  ADD CONSTRAINT `FK_Atinge_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`),
  ADD CONSTRAINT `FK_Atinge_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `Planta` (`Cod_Planta`);

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
  ADD CONSTRAINT `FK_FotoPraga_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`);

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
  ADD CONSTRAINT `FK_PlanoAmostragem_2` FOREIGN KEY (`fk_Talhao_Cod_Talhao`) REFERENCES `Talhao` (`Cod_Talhao`),
  ADD CONSTRAINT `FK_PlanoAmostragem_3` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`);

--
-- Limitadores para a tabela `PresencaPraga`
--
ALTER TABLE `PresencaPraga`
  ADD CONSTRAINT `FK_PresencaPraga_2` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `Cultura` (`Cod_Cultura`),
  ADD CONSTRAINT `FK_PresencaPraga_3` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `Praga` (`Cod_Praga`);

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
  ADD CONSTRAINT `FK_Talhao_2` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `Cultura` (`Cod_Cultura`),
  ADD CONSTRAINT `FK_Talhao_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `Planta` (`Cod_Planta`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `Trabalha`
--
ALTER TABLE `Trabalha`
  ADD CONSTRAINT `FK_Trabalha_1` FOREIGN KEY (`fk_Propriedade_Cod_Propriedade`) REFERENCES `Propriedade` (`Cod_Propriedade`),
  ADD CONSTRAINT `FK_Trabalha_2` FOREIGN KEY (`fk_Funcionario_Cod_Funcionario`) REFERENCES `Funcionario` (`Cod_Funcionario`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
