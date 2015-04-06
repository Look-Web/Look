-- phpMyAdmin SQL Dump
-- version 4.3.11.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 03, 2015 at 09:31 PM
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

CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `users_user_ID` int(10) unsigned NOT NULL,
  `image_url` varchar(100) DEFAULT NULL,
  `time_posted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`post_id`, `title`, `description`, `users_user_ID`, `image_url`, `time_posted`) VALUES
(1, 'Big Flower', 'Pic of a flower', 123, 'bigflower.jpg', '2015-03-15 20:39:02'),
(43, 'Sweet leaves!', 'These leaves r so kool guyz', 1234, 'leaves.jpg', '2015-03-15 21:01:22'),
(45, 'Test', 'Testing this', 123, 'bigflower.jpg', '2015-03-24 15:09:58');

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE IF NOT EXISTS `tags` (
  `tag_id` varchar(45) NOT NULL,
  `tag` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tags_has_posts`
--

CREATE TABLE IF NOT EXISTS `tags_has_posts` (
  `tags_tag_id` varchar(45) NOT NULL,
  `posts_post_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) unsigned NOT NULL,
  `username` varchar(16) NOT NULL,
  `pass` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `pass`, `first_name`, `last_name`, `date_created`) VALUES
(123, 'testUser', '*94BDCEBE19083CE2A1F959FD02F964C7AF4CFC29', 'Test', 'Testerson', '2015-03-15 20:36:54'),
(1234, 'mfonner', '*91D9861DFC07DD967611B8C96953474EF270AD5E', 'mat', 'door', '2015-03-13 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`post_id`), ADD KEY `title` (`title`), ADD KEY `fk_posts_users_idx` (`users_user_ID`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`tag_id`), ADD KEY `tag` (`tag`);

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
-- Constraints for dumped tables
--

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
ADD CONSTRAINT `fk_posts_users` FOREIGN KEY (`users_user_ID`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tags_has_posts`
--
ALTER TABLE `tags_has_posts`
ADD CONSTRAINT `fk_tags_has_posts_posts1` FOREIGN KEY (`posts_post_id`) REFERENCES `posts` (`post_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_tags_has_posts_tags1` FOREIGN KEY (`tags_tag_id`) REFERENCES `tags` (`tag_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
