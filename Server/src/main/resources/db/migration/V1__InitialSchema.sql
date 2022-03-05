--
-- Load balancer maxscale einbinden
--


--
-- Tabellenstruktur für Tabelle `sensorType speed `
--
--ALTER TABLE flyway_schema_history ALTER COLUMN 'version' AUTO_INCREMENT = 2;

DROP TABLE IF EXISTS connectedCars.distance;
DROP TABLE IF EXISTS connectedCars.fuel;
DROP TABLE IF EXISTS connectedCars.speed;
DROP TABLE IF EXISTS connectedCars.traffic;

 CREATE TABLE `speed` (
                         `type` char(5) NOT NULL,
                         `identifier` int NOT NULL,
                         `value` varchar(10)  NOT NULL,
                         `timestamp` timestamp(3) NOT NULL DEFAULT current_timestamp(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sensorType distance `
--

CREATE TABLE `distance` (
                            `type` char(8)  NOT NULL,
                            `identifier` int NOT NULL,
                            `value` varchar(10)  NOT NULL,
                            `timestamp` timestamp(3) NOT NULL DEFAULT current_timestamp(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sensorType fuel `
--

CREATE TABLE `fuel` (
                        `type` char(4)  NOT NULL,
                        `identifier` int NOT NULL,
                        `value` varchar(10)  NOT NULL,
                        `timestamp` timestamp(3) NOT NULL DEFAULT current_timestamp(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sensorType traffic `
--

CREATE TABLE `traffic` (
                           `type` char(7)  NOT NULL,
                           `identifier` int NOT NULL,
                           `value` varchar(20) NOT NULL,
                           `timestamp` timestamp(3) NOT NULL DEFAULT current_timestamp(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

-- --------------------------------------------------------

--
-- Hinzufügen von Primary Keys
--

ALTER TABLE `speed`
    ADD PRIMARY KEY (`identifier`, `timestamp`);

ALTER TABLE `distance`
    ADD PRIMARY KEY (`identifier`, `timestamp`);

ALTER TABLE `fuel`
    ADD PRIMARY KEY (`identifier`, `timestamp`);

ALTER TABLE `traffic`
    ADD PRIMARY KEY (`identifier`, `timestamp`);