-- phpMyAdmin SQL Dump
-- version 4.3.11.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 18, 2015 at 06:50 PM
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
CREATE DATABASE IF NOT EXISTS `look_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `look_db`;

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
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`post_id`, `title`, `description`, `users_user_ID`, `image_url`, `time_posted`) VALUES
(1, 'Big Flower', 'Pic of a flower', 123, 'bigflower.jpg', '2015-03-15 20:39:02'),
(43, 'Sweet leaves!', 'These leaves r so kool guyz', 1234, 'leaves.jpg', '2015-03-15 21:01:22'),
(45, 'Test', 'Testing this', 123, 'bigflower.jpg', '2015-03-24 15:09:58'),
(55, 'OMG IT WORKS!!!', 'This is a mountain!!!', 123, '55.jpeg', '2015-04-16 23:51:44'),
(56, 'A baby lion', 'This baby lion is the best baby lion', 123, '56.jpeg', '2015-04-16 23:54:21'),
(57, 'Cool space pic', 'This pic of space is rly cool', 123, '57.jpeg', '2015-04-17 00:00:37'),
(58, 'Break it', 'the images are not really aligning properly', 123, '58.jpeg', '2015-04-17 00:08:37'),
(59, 'Mountains!', 'These mountains are so tall!!!', 123, '59.jpeg', '2015-04-17 17:01:28'),
(60, 'testing', 'does it work', 123, '60.jpg', '2015-04-17 20:51:17'),
(61, 'User auth working', 'for real though', 123, '61.jpg', '2015-04-17 20:54:45'),
(62, 'Redirect', 'test', 123, '62.jpeg', '2015-04-17 20:56:27'),
(63, 'test', 'testsetestest', 123, '63.JPG', '2015-04-18 01:03:56'),
(64, 'Hey!', 'Hey!', 123, '64.jpg', '2015-04-18 02:05:12'),
(65, 'Test', 'test', 123, '65.jpg', '2015-04-18 02:07:22'),
(67, 'My image!', 'I have an image!', 3277785, '67.jpg', '2015-04-18 02:54:20'),
(68, 'My new kitten!', 'This is my new kitten chloe', 2900141, '68.jpg', '2015-04-18 04:04:44'),
(69, 'Northern Lights', 'Pic of northern lights', 3277785, '69.jpeg', '2015-04-18 16:48:54'),
(79, 'Space!', 'test', 3277785, '79.jpg', '2015-04-18 18:33:48'),
(80, 'From my phone!', 'My feet', 3277785, '80.jpg', '2015-04-18 18:42:38');

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
CREATE TABLE IF NOT EXISTS `tags` (
  `tag_id` int(45) NOT NULL,
  `tag` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`tag_id`, `tag`) VALUES
(10, 'feet'),
(11, 'shoes');

-- --------------------------------------------------------

--
-- Table structure for table `tags_has_posts`
--

DROP TABLE IF EXISTS `tags_has_posts`;
CREATE TABLE IF NOT EXISTS `tags_has_posts` (
  `tags_tag_id` varchar(45) NOT NULL,
  `posts_post_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) unsigned NOT NULL,
  `username` varchar(16) NOT NULL,
  `pass` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `pass`, `first_name`, `last_name`, `date_created`) VALUES
(123, 'testUser', '*94BDCEBE19083CE2A1F959FD02F964C7AF4CFC29', 'Test', 'Testerson', '2015-03-15 20:36:54'),
(1234, 'mfonner', '*91D9861DFC07DD967611B8C96953474EF270AD5E', 'mat', 'door', '2015-03-13 00:00:00'),
(2900141, 'mjenkins', '*2470C0C06DEE42FD1618BB99005ADCA2EC9D1E19', 'Morgan', 'Jenkins', '2015-04-18 00:03:33'),
(3277785, 'kholland', '*24432AD03B115619D8321F3791BDBF50025F3EDF', 'Kevin', 'Holland', NULL),
(4045216, 'dijfodisaf', '*4F838FDFEF7CE6EDE2FD7DCC007A4B90EDA4A342', 'fjdiosfjioas', 'fjdiosafjsda', '2015-04-18 01:03:23'),
(4691005, 'testUser', '*2470C0C06DEE42FD1618BB99005ADCA2EC9D1E19', 'fjdiosfjioas', 'fjdiosafjsda', '2015-04-18 01:03:41');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`post_id`), ADD KEY `fk_posts_users` (`users_user_ID`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`tag_id`), ADD UNIQUE KEY `tag` (`tag`);

--
-- Indexes for table `tags_has_posts`
--
ALTER TABLE `tags_has_posts`
  ADD PRIMARY KEY (`tags_tag_id`,`posts_post_id`), ADD KEY `fk_tags_has_posts_posts1_idx` (`posts_post_id`), ADD KEY `fk_tags_has_posts_tags1_idx` (`tags_tag_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=81;
--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `tag_id` int(45) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
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
