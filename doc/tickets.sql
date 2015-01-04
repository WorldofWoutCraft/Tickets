--
-- Database: `tickets`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `ticketid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  KEY `ticketid` (`ticketid`),
  KEY `userid` (`userid`),
  KEY `userid_2` (`userid`)
);

-- --------------------------------------------------------

--
-- Table structure for table `solvers`
--

CREATE TABLE IF NOT EXISTS `solvers` (
  `ticketid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  KEY `ticketid` (`ticketid`),
  KEY `userid` (`userid`)
);

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE IF NOT EXISTS `tickets` (
  `ticketid` int(11) NOT NULL,
  `ticketstatus` varchar(50) NOT NULL,
  `tickettype` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `ownerid` int(11) NOT NULL,
  `dateasked` datetime NOT NULL,
  `dateclosed` datetime NOT NULL,
  `priority` int(11) NOT NULL,
  `locationid` int(11) NOT NULL,
  PRIMARY KEY (`ticketid`),
  KEY `ownerid` (`ownerid`)
);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userid` int(11) NOT NULL,
  `uuid` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  PRIMARY KEY (`userid`),
  KEY `userid` (`userid`)
);

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_user` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comments_ticket` FOREIGN KEY (`ticketid`) REFERENCES `tickets` (`ticketid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `solvers`
--
ALTER TABLE `solvers`
  ADD CONSTRAINT `ticket_user_user` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ticket_user_ticket` FOREIGN KEY (`ticketid`) REFERENCES `tickets` (`ticketid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `ticketowner` FOREIGN KEY (`ownerid`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;
