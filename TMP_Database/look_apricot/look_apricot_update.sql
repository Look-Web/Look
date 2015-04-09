-- phpMyAdmin SQL Dump
-- version 4.3.11.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 09, 2015 at 02:46 AM
-- Server version: 5.6.23
-- PHP Version: 5.5.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: look_db
--

--
-- Dumping data for table posts
--

INSERT INTO posts (post_id, title, description, users_user_ID, image_url, time_posted) VALUES
(1, 'Big Flower', 'Pic of a flower', 123, 'bigflower.jpg', '2015-03-15 20:39:02'),
(43, 'Sweet leaves!', 'These leaves r so kool guyz', 1234, 'leaves.jpg', '2015-03-15 21:01:22'),
(45, 'Test', 'Testing this', 123, 'bigflower.jpg', '2015-03-24 15:09:58');

--
-- Dumping data for table users
--

INSERT INTO users (user_id, username, pass, first_name, last_name, date_created) VALUES
(123, 'testUser', '*94BDCEBE19083CE2A1F959FD02F964C7AF4CFC29', 'Test', 'Testerson', '2015-03-15 20:36:54'),
(1234, 'mfonner', '*91D9861DFC07DD967611B8C96953474EF270AD5E', 'mat', 'door', '2015-03-13 00:00:00');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
