**Readme des Projekts: Connected Cars**
##Projektteam: 
- Harijan, Stephan; 
- Kraus, Andreas; 
- Projektadresse: https://code.fbi.h-da.de/distributed-systems/2020_wise_lab/group_d_1
___
## Projektbeschreibung
- Das Projekt ist mittels Docker containerisiert. <br>Als Buildtool dient Maven Build.
  <br>In diesem Projekt werden die drei Java Programme *Sensor*, *Central* und *Server* gebaut und zur Ausführung gebracht. 
- Von den Sensoren werden 4 Instanzen erstellt, die unterschiedliche Daten simulieren: Verkehrsdichte (TRAFFIC), Geschwindigkeit (SPEED), Kilometerstand (DISTANCE) und Tankinhalt (FUEL). Die gelieferten Werte sind pseudo-zufällig. 
- Diese vier Sensoren liefern ihre Daten jeweils mittels eines Mqtt-Protokolls in Form des Datenformats JSON an Central.
- Von Central aus werden die simulierten Sensordaten mittels RPC Thrift in JSON-Datenformat an den Server gesendet.
- Vom Server werden die Daten an das Datenbankcluster (Anzahl Master: 1, Anzahl Slaves: 2) via Maxscale geroutet und persistiert.
___
## Anforderungssammlung
- Die zu Projektbeginn erstellten Anforderungen erfuhren im Laufe des Projekts Anpassungen und Korrekturen. Ihre aktuellste Fassung ist in Form von GitLab Issues verfasst und unter der Projektadresse einsehbar. 
___
## Projektstruktur
- Die Projektstruktur ist schematisch in connectedCars.png dargestellt. Diese Darstellung wurde bei jedem der Meilensteine aktualisiert und stellt die Projektstruktur des aktuellen Meilensteins dar.
___
## Dockerfiles
- Die verwendeten Dockerfiles befinden sich gesammelt im Verzeichnis "Dockerfiles".
- [Dockerfile_Sensor](Dockerfiles), [Dockerfile_Server](Dockerfiles) und [Dockerfile_Central](Dockerfiles) mittels derer die Java Programme, die als FAT JAR vorliegen in einen neuen Container kopiert und dort zur Ausführung gebracht.
- Die Dockerfiles [Dockerfile_Broker](Dockerfiles), [Dockerfile_Server](Dockerfiles) werden zur Erstellung der jeweiligen Service-Container verwendet.
___
## Docker Compose
Das "Connected Car" wird mit vier Sensoren, die unterschiedliche Daten liefern, im Compose File konfiguriert und lässt sich darüber starten.
Zu beachten ist hierbei, dass die Variablen für die Konfiguration in den Container bis zum Jar File weitergereicht werden.
Es wird keine eigene Netzwerkkonfiguration vorgenommen, sondern stattdessen der DNS Service von Docker Compose verwendet.
___
## How To - local build and run
- `[sudo -E] make start`	(Bildet und startet die Container im Hintergrund)
- `[sudo -E] make logs`	    (Gibt die System.out.println-Werte wieder)
- `[sudo -E] make stop`	    (Beendet die Container)
- `[sudo -E] make clean` 	(Beendet die Container und entfernt die Images) 
---
## How To - DB Control
- `[sudo -E] make databases`(Zeigt die aktuelle Master-Slave-Rollenverteilung)
- `[sudo -E] make killdb1`  (Stoppt DB-Container "master")
- `[sudo -E] make killdb2`  (Stoppt DB-Container "slave1")
- `[sudo -E] make killdb3`  (Stoppt DB-Container "slave2")
- `[sudo -E] make restart`  (Startet gestoppte DB-Container wieder)
___
## Ausgabe der gesendeten Daten mittels Http-GET Request erfolgt unter folgenden Adressen
- `localhost:8081/`				(zeigt ALLE AKTIVEN Sensoren)
- `localhost:8081/SensorData/current/all` 	(gibt ALLE AKTUELLEN Sensordaten wieder).
- `localhost:8081/SensorData/current/traffic` 	(gibt DAS AKTUELLE Sensordatum des TRAFFIC-Sensors wieder). 
- `localhost:8081/SensorData/current/speed` 	(gibt DAS AKTUELLE Sensordatum des SPEED-Sensors wieder). 
- `localhost:8081/SensorData/current/distance` 	(gibt DAS AKTUELLE Sensordatum des DISTANCE-Sensors wieder). 
- `localhost:8081/SensorData/current/fuel` 	(gibt DAS AKTUELLE Sensordatum des FUEL-Sensors wieder).
- `localhost:8081/SensorData/history/all`	(gibt ALLE Sensordaten wieder).
- `localhost:8081/SensorData/history/traffic` 	(gibt ALLE Sensordaten des TRAFFIC-Sensors).
- `localhost:8081/SensorData/history/speed`	(gibt ALLE Sensordaten SPEED-Sensors wieder).
- `localhost:8081/SensorData/history/distance` (gibt ALLE Sensordaten des DISTANCE-Sensors wieder).
- `localhost:8081/SensorData/history/fuel` 	(gibt ALLE Sensordaten des FUEL-Sensors wieder).
___
## Legal Information
- Das Projekt unterliegt der Lizenz GNU GLP V3
___
## TESTS unterschiedlicher Testwege
- Die Testfalldokumentation für den jeweiligen Meilenstein befindet sich im Verzeichnis [Test](Test).
___
##Credits 
- Als Ausgangsprojekt diente das unter https://code.fbi.h-da.de/sttistof/vs-demo/-/jobs vorliegende Projekt von TIM STOFFEL. 
- Zur Umsetzung des Projektziels "Hohe Verfügbarkeit" wurde folgende Datenbankkonfiguration verwendet: https://github.com/mariadb-corporation/maxscale-docker


