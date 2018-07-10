-- phpMyAdmin SQL Dump
-- version 4.4.15.6
-- http://www.phpmyadmin.net
--
-- Host: db.f4.htw-berlin.de:3306
-- Erstellungszeit: 28. Jan 2018 um 21:18
-- Server-Version: 5.5.58-0+deb8u1-log
-- PHP-Version: 5.6.30-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `_s0555385__SongsDB`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Song`
--

CREATE TABLE IF NOT EXISTS `Song` (
  `songID` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `album` varchar(100) NOT NULL,
  `artist` varchar(100) NOT NULL,
  `released` varchar(100) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Song`
--

INSERT INTO `Song` (`songID`, `title`, `artist`, `album`, `released`) VALUES
(1, '777 Years', 'Lukas 777 (Album Seven)', 'Lukas 777', '2017'),
(9, 'Private Show', 'Britney Spears', 'Glory', '2016'),
(10, '7 Years', 'Lukas Graham', 'Lukas Graham (Blue Album)', '2015'),
(28, 'No', 'Meghan Trainor', 'Thank You', '2016'),
(37, 'i hate u, i love u', 'Gnash', 'Top Hits 2017', '2017'),
(46, 'I Took a Pill in Ibiza', 'Mike Posner', 'At Night, Alone.', '2016'),
(55, 'Bad Things', 'Camila Cabello, Machine Gun Kelly', 'Bloom', '2017'),
(64, 'Ghostbusters (I''m not a fraid)', 'Fall Out Boy, Missy Elliott', 'Ghostbusters', '2016'),
(73, 'Team', 'Iggy Azalea', 'Test', '2016'),
(82, 'Mom', 'Meghan Trainor, Kelli Trainor', 'Thank You', '2016'),
(91, 'Can’t Stop the Feeling', 'Justin Timberlake', 'Trolls', '2016');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `SongLists`
--

CREATE TABLE IF NOT EXISTS `SongLists` (
  `songlistsId` int(11) NOT NULL,
  `ownerID` varchar(100) NOT NULL,
  `isPublic` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `SongLists`
--

INSERT INTO `SongLists` (`songlistsId`, `ownerID`, `isPublic`) VALUES
(1, 'eschuler', 1),
(2, 'me', 1),
(3, 'eschuler', 0),
(4, 'me', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `SongSongListNMRelation`
--

CREATE TABLE IF NOT EXISTS `SongSongListNMRelation` (
  `songId` int(11) NOT NULL,
  `songlistsId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `SongSongListNMRelation`
--

INSERT INTO `SongSongListNMRelation` (`songId`, `songlistsId`) VALUES
(1, 1),
(10, 1),
(10, 2),
(28, 2),
(82, 3),
(91, 3),
(55, 4),
(64, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL,
  `userID` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `firstName` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `User`
--

INSERT INTO `User` (`id`, `userID`, `lastName`, `firstName`) VALUES
(1, 'me', 'Myself', 'Me'),
(2, 'eschuler', 'Schuler', 'Elena');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `Song`
--
ALTER TABLE `Song`
  ADD PRIMARY KEY (`songID`),
  ADD UNIQUE KEY `songID` (`songID`),
  ADD UNIQUE KEY `title` (`title`);

--
-- Indizes für die Tabelle `SongLists`
--
ALTER TABLE `SongLists`
  ADD PRIMARY KEY (`songlistsId`,`ownerID`),
  ADD UNIQUE KEY `songlistsId` (`songlistsId`),
  ADD KEY `ownerID` (`ownerID`);

--
-- Indizes für die Tabelle `SongSongListNMRelation`
--
ALTER TABLE `SongSongListNMRelation`
  ADD PRIMARY KEY (`songId`,`songlistsId`),
  ADD KEY `songlistsId` (`songlistsId`);

--
-- Indizes für die Tabelle `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `userID` (`userID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `Song`
--
ALTER TABLE `Song`
  MODIFY `songID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=92;
--
-- AUTO_INCREMENT für Tabelle `SongLists`
--
ALTER TABLE `SongLists`
  MODIFY `songlistsId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT für Tabelle `User`
--
ALTER TABLE `User`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `SongLists`
--
ALTER TABLE `SongLists`
  ADD CONSTRAINT `SongLists_ibfk_1` FOREIGN KEY (`ownerID`) REFERENCES `User` (`userID`);

--
-- Constraints der Tabelle `SongSongListNMRelation`
--
ALTER TABLE `SongSongListNMRelation`
  ADD CONSTRAINT `SongSongListNMRelation_ibfk_1` FOREIGN KEY (`songId`) REFERENCES `Song` (`songID`),
  ADD CONSTRAINT `SongSongListNMRelation_ibfk_2` FOREIGN KEY (`songlistsId`) REFERENCES `SongLists` (`songlistsId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
