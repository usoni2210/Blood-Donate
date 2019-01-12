-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 21, 2018 at 02:24 PM
-- Server version: 5.6.38
-- PHP Version: 7.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blood_donate`
--
CREATE DATABASE IF NOT EXISTS `blood_donate` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `blood_donate`;

-- --------------------------------------------------------

--
-- Table structure for table `article_db`
--

CREATE TABLE `article_db` (
  `id` int(11) NOT NULL,
  `Title` text NOT NULL,
  `Article` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `article_db`
--

TRUNCATE TABLE `article_db`;
--
-- Dumping data for table `article_db`
--

INSERT INTO `article_db` (`id`, `Title`, `Article`) VALUES
(1, 'Blood donation is safe', '<p>It is safer than walking on the footpath or driving to the office every day. </p>\r\n\r\n<p>There is no reported case of death anywhere in the world directly associated with blood donation, in the history of the practice of modern medicine. There is no risk of any infection associated with blood donation.</p>\r\n\r\n<p>The needles and kit used are new and sterile and they are never reused.</p>\r\n        '),
(2, 'Money cannot buy blood', '<p>It is illegal to buy or sell blood or blood products in India. In the year 1997, the WHO declared that all blood donors will be unpaid volunteers.</p> \r\n\r\n<p>The money charged by the blood banks is the cost of processing the blood and blood products. The National Blood Transfusion Council monitors and recommends the processing fees.</p>\r\n        ');

-- --------------------------------------------------------

--
-- Table structure for table `otp_db`
--

CREATE TABLE `otp_db` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `otp` varchar(10) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `otp_db`
--

TRUNCATE TABLE `otp_db`;
-- --------------------------------------------------------

--
-- Table structure for table `user_db`
--

CREATE TABLE `user_db` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` text NOT NULL,
  `contact_no` varchar(15) NOT NULL,
  `dob` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `bloodgrp` varchar(12) NOT NULL,
  `city` varchar(20) DEFAULT NULL,
  `latlag` text,
  `isdonor` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `user_db`
--

TRUNCATE TABLE `user_db`;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `article_db`
--
ALTER TABLE `article_db`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `otp_db`
--
ALTER TABLE `otp_db`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_db`
--
ALTER TABLE `user_db`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `contact_no` (`contact_no`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `article_db`
--
ALTER TABLE `article_db`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `otp_db`
--
ALTER TABLE `otp_db`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_db`
--
ALTER TABLE `user_db`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
