-- Look Admin User
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

CREATE USER 'look_admin'@'localhost' IDENTIFIED BY 'lookpass';
GRANT ALL PRIVILEGES ON * . * TO 'look_admin'@'localhost';
FLUSH PRIVILEGES;

SELECT User FROM mysql.user WHERE User="look_admin";