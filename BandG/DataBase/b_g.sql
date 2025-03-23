-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2025 at 06:12 AM
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
-- Database: `b&g`
--

-- --------------------------------------------------------

--
-- Table structure for table `lapcounter`
--

CREATE TABLE `lapcounter` (
  `TEAM` varchar(50) NOT NULL,
  `MEMBERS` varchar(200) DEFAULT NULL,
  `LAP_ONE` int(11) NOT NULL,
  `LAP_TWO` int(11) NOT NULL,
  `AVAREGE_TIME` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lapcounter`
--

INSERT INTO `lapcounter` (`TEAM`, `MEMBERS`, `LAP_ONE`, `LAP_TWO`, `AVAREGE_TIME`) VALUES
('b&g', 'BRUNO, GABRIELA', 36, 39, 37.5);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `lapcounter`
--
ALTER TABLE `lapcounter`
  ADD PRIMARY KEY (`TEAM`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
