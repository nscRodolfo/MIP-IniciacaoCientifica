-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 27-Nov-2019 às 22:02
-- Versão do servidor: 10.4.6-MariaDB
-- versão do PHP: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `mip`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `administrador`
--

CREATE TABLE `administrador` (
  `Cod_Administrador` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `aplicacao`
--

CREATE TABLE `aplicacao` (
  `Cod_Aplicacao` int(11) NOT NULL,
  `Data` date NOT NULL,
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `aplicacao`
--

INSERT INTO `aplicacao` (`Cod_Aplicacao`, `Data`, `fk_MetodoDeControle_Cod_MetodoControle`, `fk_Cultura_Cod_Cultura`) VALUES
(1, '2019-11-05', 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `atinge`
--

CREATE TABLE `atinge` (
  `Cod_Atinge` int(11) NOT NULL,
  `NivelDanoEconomico` float DEFAULT NULL,
  `NivelDeControle` float DEFAULT NULL,
  `NivelDeEquilibrio` float DEFAULT NULL,
  `NumeroPlantasAmostradas` float DEFAULT NULL,
  `PontosPorTalhao` int(11) DEFAULT NULL,
  `PlantasPorPonto` int(11) DEFAULT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `atinge`
--

INSERT INTO `atinge` (`Cod_Atinge`, `NivelDanoEconomico`, `NivelDeControle`, `NivelDeEquilibrio`, `NumeroPlantasAmostradas`, `PontosPorTalhao`, `PlantasPorPonto`, `fk_Praga_Cod_Praga`, `fk_Planta_Cod_Planta`) VALUES
(1, NULL, 0.6, NULL, 50, 10, 5, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `combate`
--

CREATE TABLE `combate` (
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_InimigoNatural_Cod_Inimigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `combate`
--

INSERT INTO `combate` (`fk_Praga_Cod_Praga`, `fk_InimigoNatural_Cod_Inimigo`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `controla`
--

CREATE TABLE `controla` (
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `controla`
--

INSERT INTO `controla` (`fk_MetodoDeControle_Cod_MetodoControle`, `fk_Praga_Cod_Praga`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cultura`
--

CREATE TABLE `cultura` (
  `Cod_Cultura` int(11) NOT NULL,
  `fk_Propriedade_Cod_Propriedade` int(11) NOT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `cultura`
--

INSERT INTO `cultura` (`Cod_Cultura`, `fk_Propriedade_Cod_Propriedade`, `fk_Planta_Cod_Planta`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `fotoinimigo`
--

CREATE TABLE `fotoinimigo` (
  `Cod_FotoInimigo` int(11) NOT NULL,
  `FotoInimigo` text DEFAULT NULL,
  `fk_InimigoNatural_Cod_Inimigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `fotometodo`
--

CREATE TABLE `fotometodo` (
  `Cod_FotoMetodo` int(11) NOT NULL,
  `FotoMetodo` text DEFAULT NULL,
  `fk_MetodoDeControle_Cod_MetodoControle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `fotoplanta`
--

CREATE TABLE `fotoplanta` (
  `Cod_FotoPlanta` int(11) NOT NULL,
  `FotoPlanta` text DEFAULT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `fotopraga`
--

CREATE TABLE `fotopraga` (
  `Cod_FotoPraga` int(11) NOT NULL,
  `FotoPraga` text DEFAULT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario`
--

CREATE TABLE `funcionario` (
  `Cod_Funcionario` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `funcionario`
--

INSERT INTO `funcionario` (`Cod_Funcionario`, `fk_Usuario_Cod_Usuario`) VALUES
(3, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `inimigonatural`
--

CREATE TABLE `inimigonatural` (
  `Cod_Inimigo` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Familia` varchar(100) NOT NULL,
  `Ordem` varchar(100) NOT NULL,
  `NomeCientifico` varchar(100) DEFAULT NULL,
  `Descricao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `inimigonatural`
--

INSERT INTO `inimigonatural` (`Cod_Inimigo`, `Nome`, `Familia`, `Ordem`, `NomeCientifico`, `Descricao`) VALUES
(1, 'Inimigo da mosca', 'contra mosca', 'xmoscae', NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `metododecontrole`
--

CREATE TABLE `metododecontrole` (
  `Cod_MetodoControle` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `MateriaisNecessarios` text DEFAULT NULL,
  `ModoDePreparo` text DEFAULT NULL,
  `IntervaloAplicacao` int(11) NOT NULL,
  `EfeitoColateral` text DEFAULT NULL,
  `Observacoes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `metododecontrole`
--

INSERT INTO `metododecontrole` (`Cod_MetodoControle`, `Nome`, `MateriaisNecessarios`, `ModoDePreparo`, `IntervaloAplicacao`, `EfeitoColateral`, `Observacoes`) VALUES
(1, 'Calda pra mosca', NULL, NULL, 15, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `planoamostragem`
--

CREATE TABLE `planoamostragem` (
  `Cod_Plano` int(11) NOT NULL,
  `Data` date NOT NULL,
  `PlantasInfestadas` int(11) NOT NULL,
  `PlantasAmostradas` int(11) NOT NULL,
  `fk_Talhao_Cod_Talhao` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `planoamostragem`
--

INSERT INTO `planoamostragem` (`Cod_Plano`, `Data`, `PlantasInfestadas`, `PlantasAmostradas`, `fk_Talhao_Cod_Talhao`, `fk_Praga_Cod_Praga`) VALUES
(1, '2019-11-05', 20, 50, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `planta`
--

CREATE TABLE `planta` (
  `Cod_Planta` int(11) NOT NULL,
  `Familia` varchar(100) DEFAULT NULL,
  `Temperatura` varchar(100) DEFAULT NULL,
  `PH` varchar(100) DEFAULT NULL,
  `Espacamento` varchar(255) DEFAULT NULL,
  `Solo` varchar(255) DEFAULT NULL,
  `Nome` varchar(255) NOT NULL,
  `NomeCientifico` varchar(100) DEFAULT NULL,
  `TamanhoTalhao` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `planta`
--

INSERT INTO `planta` (`Cod_Planta`, `Familia`, `Temperatura`, `PH`, `Espacamento`, `Solo`, `Nome`, `NomeCientifico`, `TamanhoTalhao`) VALUES
(1, 'Solanaceae', '15 ºC a 19 ºC', '5,5 e 6,5', 'entre plantas de 50 a 60 centímetros e, entre os sulcos, de 1 a 1,20 metro', NULL, 'Tomate', 'Solanum lycopersicum', '0,5 ha ');

-- --------------------------------------------------------

--
-- Estrutura da tabela `praga`
--

CREATE TABLE `praga` (
  `Cod_Praga` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Familia` varchar(100) NOT NULL,
  `Ordem` varchar(100) NOT NULL,
  `Descricao` text DEFAULT NULL,
  `NomeCientifico` varchar(100) DEFAULT NULL,
  `Localizacao` varchar(255) DEFAULT NULL,
  `AmbientePropicio` varchar(255) DEFAULT NULL,
  `CicloVida` varchar(100) DEFAULT NULL,
  `Injurias` text NOT NULL,
  `Observacoes` text DEFAULT NULL,
  `HorarioDeAtuacao` text DEFAULT NULL,
  `EstagioDeAtuacao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `praga`
--

INSERT INTO `praga` (`Cod_Praga`, `Nome`, `Familia`, `Ordem`, `Descricao`, `NomeCientifico`, `Localizacao`, `AmbientePropicio`, `CicloVida`, `Injurias`, `Observacoes`, `HorarioDeAtuacao`, `EstagioDeAtuacao`) VALUES
(1, 'Mosca branca ', 'Aleyrodidae', 'Hemiptera', 'Sugador de seiva –  Os adultos são de coloração amarelo-pálida. Medem de 1 a 2 mm, sendo a fêmea maior que o macho. Quando em repouso, as asas são mantidas levemente separadas, com os lados paralelos, deixando o abdome visível. O ovo, de coloração amarela, apresenta formato de pêra e mede cerca de 0,2 a 0,3 mm. As ninfas são translúcidas e apresentam coloração amarela a amarelo-pálida. Tem função de vetor de vírus (diferentes espécies de geminivírus), pode causar perdas substanciais na cultura do tomateiro (40% a 70%). Quando o vírus infecta as plantas ainda jovens, essas têm o crescimento paralisado', 'Bemisia argentifolii', 'face inferior de uma folha localizada no terço mediano da copa do tomateiro e de uma folha baixeira – a amostragem deve ser feita de preferência, pela manhã até as 9 h, virando-se cuidadosamente o folíolo, de modo a não afugentar os adultos', 'temperaturas médias de 32 °C, quanto mais quente mais rápido acontece o ciclo', '18 a 21 dias', 'Suga a seiva das plantas, com a introdução do estilete no tecido vegetal, os insetos (adultos e ninfas) provocam alterações no desenvolvimento vegetativo e reprodutivo da planta, debilitando-a e reduzindo a produtividade e qualidade dos frutos. Em casos de altas densidades populacionais, podem ocorrer perdas de até 50% da produção. Infestações muito intensas ocasionam murcha, queda de folhas e perda de frutos. Nos frutos causa amadurecimento irregular. Ao se alimentarem da seiva eliminam uma substância açucarada levando ao aparecimento de fungos saprófitos que prejudicam a fotossíntese (fumagina)', 'lupa 20x - monitorar constantemente, retirar a planta com sintomas de vírus (na base dos folíolos adquire inicialmente, uma clorose entre as nervuras, evoluindo para um mosaico amarelo. Posteriormente, os sintomas se generalizam, as folhas tornam-se coriáceas e com intensa rugosidade, podendo ocorrer o dobramento ou enrolamento dos bordos para cima) – A dispersão ocorre pelo vento, maquinas, implementos agrícolas, pessoas e animais', 'A maior atividade do vôo da mosca-branca ocorre entre as 6h30min e 8h30min e entre as 15h30min e 17h30min, com uma redução entre as 10h30min e 13h30min', 'Ocorre em todo o ciclo embora a cor seja um fator determinante na seleção do hospedeiro à distância, destacando-se, em ordem de preferência, o verde-amarelado, o amarelo, o vermelho, o alaranjado-avermelhado, o verde escuro e o arroxeado');

-- --------------------------------------------------------

--
-- Estrutura da tabela `presencapraga`
--

CREATE TABLE `presencapraga` (
  `Cod_PresencaPraga` int(11) NOT NULL,
  `fk_Praga_Cod_Praga` int(11) NOT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `presencapraga`
--

INSERT INTO `presencapraga` (`Cod_PresencaPraga`, `fk_Praga_Cod_Praga`, `fk_Cultura_Cod_Cultura`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `produtor`
--

CREATE TABLE `produtor` (
  `Cod_Produtor` int(11) NOT NULL,
  `fk_Usuario_Cod_Usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `produtor`
--

INSERT INTO `produtor` (`Cod_Produtor`, `fk_Usuario_Cod_Usuario`) VALUES
(1, 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `propriedade`
--

CREATE TABLE `propriedade` (
  `Cod_Propriedade` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Cidade` varchar(100) NOT NULL,
  `Estado` varchar(100) NOT NULL,
  `fk_Produtor_Cod_Produtor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `propriedade`
--

INSERT INTO `propriedade` (`Cod_Propriedade`, `Nome`, `Cidade`, `Estado`, `fk_Produtor_Cod_Produtor`) VALUES
(1, 'Sertão Feliz', 'Ibiúna', 'São Paulo', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `talhao`
--

CREATE TABLE `talhao` (
  `Cod_Talhao` int(11) NOT NULL,
  `NumeroDePlantas` int(11) NOT NULL,
  `fk_Cultura_Cod_Cultura` int(11) NOT NULL,
  `fk_Planta_Cod_Planta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `talhao`
--

INSERT INTO `talhao` (`Cod_Talhao`, `NumeroDePlantas`, `fk_Cultura_Cod_Cultura`, `fk_Planta_Cod_Planta`) VALUES
(1, 200, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `trabalha`
--

CREATE TABLE `trabalha` (
  `fk_Propriedade_Cod_Propriedade` int(11) NOT NULL,
  `fk_Funcionario_Cod_Funcionario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `trabalha`
--

INSERT INTO `trabalha` (`fk_Propriedade_Cod_Propriedade`, `fk_Funcionario_Cod_Funcionario`) VALUES
(1, 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `Cod_Usuario` int(11) NOT NULL,
  `Nome` varchar(255) NOT NULL,
  `Telefone` varchar(11) DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `Senha` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`Cod_Usuario`, `Nome`, `Telefone`, `Email`, `Senha`) VALUES
(1, 'Rodolfo Chagas', '32999183003', 'nsc.rodolfo@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(2, 'Daniel Pimenta', '32999183003', 'danielP@gmail.com', 'e10adc3949ba59abbe56e057f20f883e'),
(3, 'Monica Simão Giponi', '32999183003', 'monicagiponi@gmail.com', '25d55ad283aa400af464c76d713c07ad');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`Cod_Administrador`),
  ADD KEY `FK_Administrador_2` (`fk_Usuario_Cod_Usuario`);

--
-- Índices para tabela `aplicacao`
--
ALTER TABLE `aplicacao`
  ADD PRIMARY KEY (`Cod_Aplicacao`),
  ADD KEY `FK_Aplicacao_2` (`fk_MetodoDeControle_Cod_MetodoControle`),
  ADD KEY `FK_Aplicacao_3` (`fk_Cultura_Cod_Cultura`);

--
-- Índices para tabela `atinge`
--
ALTER TABLE `atinge`
  ADD PRIMARY KEY (`Cod_Atinge`),
  ADD KEY `FK_Atinge_2` (`fk_Praga_Cod_Praga`),
  ADD KEY `FK_Atinge_3` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `combate`
--
ALTER TABLE `combate`
  ADD KEY `FK_Combate_1` (`fk_Praga_Cod_Praga`),
  ADD KEY `FK_Combate_2` (`fk_InimigoNatural_Cod_Inimigo`);

--
-- Índices para tabela `controla`
--
ALTER TABLE `controla`
  ADD KEY `FK_Controla_1` (`fk_MetodoDeControle_Cod_MetodoControle`),
  ADD KEY `FK_Controla_2` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `cultura`
--
ALTER TABLE `cultura`
  ADD PRIMARY KEY (`Cod_Cultura`),
  ADD KEY `FK_Cultura_2` (`fk_Propriedade_Cod_Propriedade`),
  ADD KEY `FK_Cultura_3` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `fotoinimigo`
--
ALTER TABLE `fotoinimigo`
  ADD PRIMARY KEY (`Cod_FotoInimigo`),
  ADD KEY `FK_FotoInimigo_2` (`fk_InimigoNatural_Cod_Inimigo`);

--
-- Índices para tabela `fotometodo`
--
ALTER TABLE `fotometodo`
  ADD PRIMARY KEY (`Cod_FotoMetodo`),
  ADD KEY `FK_FotoMetodo_2` (`fk_MetodoDeControle_Cod_MetodoControle`);

--
-- Índices para tabela `fotoplanta`
--
ALTER TABLE `fotoplanta`
  ADD PRIMARY KEY (`Cod_FotoPlanta`),
  ADD KEY `FK_FotoPlanta_2` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `fotopraga`
--
ALTER TABLE `fotopraga`
  ADD PRIMARY KEY (`Cod_FotoPraga`),
  ADD KEY `FK_FotoPraga_2` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `funcionario`
--
ALTER TABLE `funcionario`
  ADD PRIMARY KEY (`Cod_Funcionario`),
  ADD KEY `FK_Funcionario_2` (`fk_Usuario_Cod_Usuario`);

--
-- Índices para tabela `inimigonatural`
--
ALTER TABLE `inimigonatural`
  ADD PRIMARY KEY (`Cod_Inimigo`);

--
-- Índices para tabela `metododecontrole`
--
ALTER TABLE `metododecontrole`
  ADD PRIMARY KEY (`Cod_MetodoControle`);

--
-- Índices para tabela `planoamostragem`
--
ALTER TABLE `planoamostragem`
  ADD PRIMARY KEY (`Cod_Plano`),
  ADD KEY `FK_PlanoAmostragem_2` (`fk_Talhao_Cod_Talhao`),
  ADD KEY `FK_PlanoAmostragem_3` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `planta`
--
ALTER TABLE `planta`
  ADD PRIMARY KEY (`Cod_Planta`);

--
-- Índices para tabela `praga`
--
ALTER TABLE `praga`
  ADD PRIMARY KEY (`Cod_Praga`);

--
-- Índices para tabela `presencapraga`
--
ALTER TABLE `presencapraga`
  ADD PRIMARY KEY (`Cod_PresencaPraga`),
  ADD KEY `FK_PresencaPraga_2` (`fk_Cultura_Cod_Cultura`),
  ADD KEY `FK_PresencaPraga_3` (`fk_Praga_Cod_Praga`);

--
-- Índices para tabela `produtor`
--
ALTER TABLE `produtor`
  ADD PRIMARY KEY (`Cod_Produtor`),
  ADD KEY `FK_Produtor_2` (`fk_Usuario_Cod_Usuario`);

--
-- Índices para tabela `propriedade`
--
ALTER TABLE `propriedade`
  ADD PRIMARY KEY (`Cod_Propriedade`),
  ADD KEY `FK_Propriedade_2` (`fk_Produtor_Cod_Produtor`);

--
-- Índices para tabela `talhao`
--
ALTER TABLE `talhao`
  ADD PRIMARY KEY (`Cod_Talhao`),
  ADD KEY `FK_Talhao_2` (`fk_Cultura_Cod_Cultura`),
  ADD KEY `FK_Talhao_3` (`fk_Planta_Cod_Planta`);

--
-- Índices para tabela `trabalha`
--
ALTER TABLE `trabalha`
  ADD KEY `FK_Trabalha_1` (`fk_Propriedade_Cod_Propriedade`),
  ADD KEY `FK_Trabalha_2` (`fk_Funcionario_Cod_Funcionario`);

--
-- Índices para tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Cod_Usuario`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `administrador`
--
ALTER TABLE `administrador`
  MODIFY `Cod_Administrador` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `aplicacao`
--
ALTER TABLE `aplicacao`
  MODIFY `Cod_Aplicacao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `atinge`
--
ALTER TABLE `atinge`
  MODIFY `Cod_Atinge` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `cultura`
--
ALTER TABLE `cultura`
  MODIFY `Cod_Cultura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `fotoinimigo`
--
ALTER TABLE `fotoinimigo`
  MODIFY `Cod_FotoInimigo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `fotometodo`
--
ALTER TABLE `fotometodo`
  MODIFY `Cod_FotoMetodo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `fotoplanta`
--
ALTER TABLE `fotoplanta`
  MODIFY `Cod_FotoPlanta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `fotopraga`
--
ALTER TABLE `fotopraga`
  MODIFY `Cod_FotoPraga` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `funcionario`
--
ALTER TABLE `funcionario`
  MODIFY `Cod_Funcionario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `inimigonatural`
--
ALTER TABLE `inimigonatural`
  MODIFY `Cod_Inimigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `metododecontrole`
--
ALTER TABLE `metododecontrole`
  MODIFY `Cod_MetodoControle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `planoamostragem`
--
ALTER TABLE `planoamostragem`
  MODIFY `Cod_Plano` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `planta`
--
ALTER TABLE `planta`
  MODIFY `Cod_Planta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `praga`
--
ALTER TABLE `praga`
  MODIFY `Cod_Praga` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `presencapraga`
--
ALTER TABLE `presencapraga`
  MODIFY `Cod_PresencaPraga` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `produtor`
--
ALTER TABLE `produtor`
  MODIFY `Cod_Produtor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `propriedade`
--
ALTER TABLE `propriedade`
  MODIFY `Cod_Propriedade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `talhao`
--
ALTER TABLE `talhao`
  MODIFY `Cod_Talhao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `Cod_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `administrador`
--
ALTER TABLE `administrador`
  ADD CONSTRAINT `FK_Administrador_2` FOREIGN KEY (`fk_Usuario_Cod_Usuario`) REFERENCES `usuario` (`Cod_Usuario`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `aplicacao`
--
ALTER TABLE `aplicacao`
  ADD CONSTRAINT `FK_Aplicacao_2` FOREIGN KEY (`fk_MetodoDeControle_Cod_MetodoControle`) REFERENCES `metododecontrole` (`Cod_MetodoControle`),
  ADD CONSTRAINT `FK_Aplicacao_3` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `cultura` (`Cod_Cultura`);

--
-- Limitadores para a tabela `atinge`
--
ALTER TABLE `atinge`
  ADD CONSTRAINT `FK_Atinge_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `praga` (`Cod_Praga`),
  ADD CONSTRAINT `FK_Atinge_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `planta` (`Cod_Planta`);

--
-- Limitadores para a tabela `combate`
--
ALTER TABLE `combate`
  ADD CONSTRAINT `FK_Combate_1` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `praga` (`Cod_Praga`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Combate_2` FOREIGN KEY (`fk_InimigoNatural_Cod_Inimigo`) REFERENCES `inimigonatural` (`Cod_Inimigo`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `controla`
--
ALTER TABLE `controla`
  ADD CONSTRAINT `FK_Controla_1` FOREIGN KEY (`fk_MetodoDeControle_Cod_MetodoControle`) REFERENCES `metododecontrole` (`Cod_MetodoControle`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Controla_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `praga` (`Cod_Praga`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `cultura`
--
ALTER TABLE `cultura`
  ADD CONSTRAINT `FK_Cultura_2` FOREIGN KEY (`fk_Propriedade_Cod_Propriedade`) REFERENCES `propriedade` (`Cod_Propriedade`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Cultura_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `planta` (`Cod_Planta`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `fotoinimigo`
--
ALTER TABLE `fotoinimigo`
  ADD CONSTRAINT `FK_FotoInimigo_2` FOREIGN KEY (`fk_InimigoNatural_Cod_Inimigo`) REFERENCES `inimigonatural` (`Cod_Inimigo`);

--
-- Limitadores para a tabela `fotometodo`
--
ALTER TABLE `fotometodo`
  ADD CONSTRAINT `FK_FotoMetodo_2` FOREIGN KEY (`fk_MetodoDeControle_Cod_MetodoControle`) REFERENCES `metododecontrole` (`Cod_MetodoControle`);

--
-- Limitadores para a tabela `fotoplanta`
--
ALTER TABLE `fotoplanta`
  ADD CONSTRAINT `FK_FotoPlanta_2` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `planta` (`Cod_Planta`);

--
-- Limitadores para a tabela `fotopraga`
--
ALTER TABLE `fotopraga`
  ADD CONSTRAINT `FK_FotoPraga_2` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `praga` (`Cod_Praga`);

--
-- Limitadores para a tabela `funcionario`
--
ALTER TABLE `funcionario`
  ADD CONSTRAINT `FK_Funcionario_2` FOREIGN KEY (`fk_Usuario_Cod_Usuario`) REFERENCES `usuario` (`Cod_Usuario`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `planoamostragem`
--
ALTER TABLE `planoamostragem`
  ADD CONSTRAINT `FK_PlanoAmostragem_2` FOREIGN KEY (`fk_Talhao_Cod_Talhao`) REFERENCES `talhao` (`Cod_Talhao`),
  ADD CONSTRAINT `FK_PlanoAmostragem_3` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `praga` (`Cod_Praga`);

--
-- Limitadores para a tabela `presencapraga`
--
ALTER TABLE `presencapraga`
  ADD CONSTRAINT `FK_PresencaPraga_2` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `cultura` (`Cod_Cultura`),
  ADD CONSTRAINT `FK_PresencaPraga_3` FOREIGN KEY (`fk_Praga_Cod_Praga`) REFERENCES `praga` (`Cod_Praga`);

--
-- Limitadores para a tabela `produtor`
--
ALTER TABLE `produtor`
  ADD CONSTRAINT `FK_Produtor_2` FOREIGN KEY (`fk_Usuario_Cod_Usuario`) REFERENCES `usuario` (`Cod_Usuario`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `propriedade`
--
ALTER TABLE `propriedade`
  ADD CONSTRAINT `FK_Propriedade_2` FOREIGN KEY (`fk_Produtor_Cod_Produtor`) REFERENCES `produtor` (`Cod_Produtor`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `talhao`
--
ALTER TABLE `talhao`
  ADD CONSTRAINT `FK_Talhao_2` FOREIGN KEY (`fk_Cultura_Cod_Cultura`) REFERENCES `cultura` (`Cod_Cultura`),
  ADD CONSTRAINT `FK_Talhao_3` FOREIGN KEY (`fk_Planta_Cod_Planta`) REFERENCES `planta` (`Cod_Planta`) ON DELETE CASCADE;

--
-- Limitadores para a tabela `trabalha`
--
ALTER TABLE `trabalha`
  ADD CONSTRAINT `FK_Trabalha_1` FOREIGN KEY (`fk_Propriedade_Cod_Propriedade`) REFERENCES `propriedade` (`Cod_Propriedade`),
  ADD CONSTRAINT `FK_Trabalha_2` FOREIGN KEY (`fk_Funcionario_Cod_Funcionario`) REFERENCES `funcionario` (`Cod_Funcionario`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
