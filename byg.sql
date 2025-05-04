-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 04, 2025 at 05:39 PM
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `addLap` (IN `_TEAM_NAME` VARCHAR(30), IN `_LAP` VARCHAR(45), IN `_RANK` VARCHAR(45))  BEGIN
	IF(_TEAM_NAME NOT LIKE '')THEN
		INSERT INTO laps(team_name,lap,total_time) VALUES(_TEAM_NAME,_LAP,_RANK);
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `call_laps` (IN `_TEAM_ONE` VARCHAR(30), IN `_TEAM_TWO` VARCHAR(30))  BEGIN
	SELECT T.team_name,T.total_time
    FROM laps T
    WHERE T.team_name = _TEAM_ONE OR T.team_name = _TEAM_TWO 
    ORDER BY total_time ASC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `call_team` (IN `_TEAM_NAME` VARCHAR(30), IN `_FIRST_TEAM` VARCHAR(30))  BEGIN
    IF(_TEAM_NAME = '') THEN
        if(_FIRST_TEAM = '') THEN
    		SELECT * FROM team;
    	ELSE
            SELECT * FROM team WHERE team_name NOT LIKE _FIRST_TEAM;
    		SELECT team_name,lap FROM laps WHERE team_name NOT LIKE _FIRST_TEAM;
        END IF;
    ELSE
		/*SELECT * FROM team WHERE team_name = _TEAM_NAME;*/
        SELECT * FROM team WHERE team_name LIKE CONCAT('%',_TEAM_NAME,'%');
		/*SELECT team_name,lap FROM laps WHERE team_name = _TEAM_NAME;*/
        SELECT team_name,lap FROM laps WHERE team_name LIKE CONCAT('%',_TEAM_NAME,'%');
    END IF;	
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_team` (IN `_TEAM_NAME` VARCHAR(30))  BEGIN
DELETE FROM laps WHERE team_name=_TEAM_NAME;
DELETE FROM team WHERE team_name=_TEAM_NAME;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `signUp` (IN `_TEAM_NAME` VARCHAR(30), IN `_MEMBER_ONE` VARCHAR(30), IN `_MEMBER_TWO` VARCHAR(30), IN `_MEMBER_THREE` VARCHAR(30), IN `_LAP` VARCHAR(45), IN `_RANK` VARCHAR(45))  BEGIN
IF(_TEAM_NAME NOT LIKE '')THEN
	IF(_LAP = '') THEN
		INSERT INTO team VALUES(_TEAM_NAME,_MEMBER_ONE,_MEMBER_TWO,_MEMBER_THREE);
	ELSE
		INSERT INTO team VALUES(_TEAM_NAME,_MEMBER_ONE,_MEMBER_TWO,_MEMBER_THREE);
    	INSERT INTO laps (team_name,lap,total_time) VALUES(_TEAM_NAME,_LAP,_RANK);
	END IF;
END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `laps`
--

CREATE TABLE `laps` (
  `team_name` varchar(30) DEFAULT NULL,
  `lap` varchar(45) DEFAULT NULL,
  `total_time` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `laps`
--

INSERT INTO `laps` (`team_name`, `lap`, `total_time`) VALUES
('BYG', 'Volta 1 - 0:3:186\nVolta 2 - 0:3:-126\n', '0:5:1050'),
('SlashSnake', 'Volta 1 - 0:1:758\nVolta 2 - 0:3:-126\n', '0:7:506'),
('SlashSnake', 'Volta 1 - 0:6:156\nVolta 2 - 0:0:724\n', '0:6:880'),
('BYG', 'Volta 1 - 0:3:246\nVolta 2 - 0:1:246\n', '0:4:492'),
('SlashSnake', 'Volta 1 - 0:1:558\nVolta 2 - 0:0:264\n', '0:1:822'),
('BYG', 'Volta 1 - 0:2:760\nVolta 2 - 0:1:-666\n', '0:3:94'),
('SlashSnake', 'Volta 1 - 0:3:138\nVolta 2 - 0:2:128\n', '0:5:266'),
('BYG', 'Volta 1 - 0:2:156\nVolta 2 - 0:3:110\n', '0:5:266');

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
-- Dumping data for table `team`
--

INSERT INTO `team` (`team_name`, `member_one`, `member_two`, `member_three`) VALUES
('BYG', 'Bruno', 'Gabriela', ''),
('SlashSnake', 'Pedro', 'Arthur', 'Dan');

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
