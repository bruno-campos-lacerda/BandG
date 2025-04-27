-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 28, 2025 at 12:34 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `byg`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addLap` (IN `_TEAM_NAME` VARCHAR(30), IN `_LAP` VARCHAR(45))  BEGIN
	INSERT INTO laps(team_name,lap) VALUES(_TEAM_NAME,_LAP);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `call_team` (IN `_TEAM_NAME` VARCHAR(30))  BEGIN
	SELECT * FROM team WHERE team_name = _TEAM_NAME;
    SELECT team_name,lap FROM laps WHERE team_name = _TEAM_NAME;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_team` (IN `_TEAM_NAME` VARCHAR(30))  BEGIN
DELETE FROM laps WHERE team_name=_TEAM_NAME;
DELETE FROM team WHERE team_name=_TEAM_NAME;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `signUp` (IN `_TEAM_NAME` VARCHAR(30), IN `_MEMBER_ONE` VARCHAR(30), IN `_MEMBER_TWO` VARCHAR(30), IN `_MEMBER_THREE` VARCHAR(30), IN `_LAP` VARCHAR(45))  BEGIN
IF(_LAP IS NULL) THEN
	INSERT INTO team VALUES(_TEAM_NAME,_MEMBER_ONE,_MEMBER_TWO,_MEMBER_THREE);
ELSE
	INSERT INTO team VALUES(_TEAM_NAME,_MEMBER_ONE,_MEMBER_TWO,_MEMBER_THREE);
    INSERT INTO laps (team_name,lap) VALUES(_TEAM_NAME,_LAP);
END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `laps`
--

CREATE TABLE `laps` (
  `team_name` varchar(30) DEFAULT NULL,
  `lap` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `team_name` varchar(30) NOT NULL,
  `member_one` varchar(30) DEFAULT NULL,
  `member_two` varchar(30) DEFAULT NULL,
  `member_three` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `laps`
--
ALTER TABLE `laps`
  ADD KEY `fk_team_name` (`team_name`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`team_name`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `laps`
--
ALTER TABLE `laps`
  ADD CONSTRAINT `fk_team_name` FOREIGN KEY (`team_name`) REFERENCES `team` (`team_name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
