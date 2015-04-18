-- phpMyAdmin SQL Dump
-- version 4.3.11.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 09, 2015 at 03:02 AM
-- Server version: 5.6.23
-- PHP Version: 5.5.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `look_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `users_user_ID` int(10) unsigned NOT NULL,
  `image_url` varchar(100) DEFAULT NULL,
  `time_posted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`post_id`), ADD KEY `title` (`title`), ADD KEY `fk_posts_users_idx` (`users_user_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
ADD CONSTRAINT `fk_posts_users` FOREIGN KEY (`users_user_ID`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
