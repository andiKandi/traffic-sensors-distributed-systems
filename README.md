# Connected Cars
This project simulates a distributed systems of sensors inside cars (because this project was part of study assignment some parts may still be in german... sorry).

## Project description
- The project is containerized using Docker.
- Maven Build is used as the build tool.
- In this project, three Java programs *Sensor*, *Central* and *Server* are built and run. 
- Of the sensors, 4 instances are created to simulate different data:
- Traffic Density (TRAFFIC), Speed (SPEED), Mileage (DISTANCE) and Fuel Level (FUEL).  The values provided are pseudo-random.
- These four sensors each provide their data to Central using a Mqtt protocol in the form of JSON data format.
- From Central, the simulated sensor data is sent to the server using RPC Thrift in JSON data format.
- From the server, the data is routed to the database cluster (number of masters: 1, number of slaves: 2) via Maxscale and persisted.

## Project structure
- The project structure is shown schematically in connectedCars.png.

## Docker Compose
The "Connected Car" is configured with four sensors, which provide different data, 
in the Compose file and can be started via this.
It should be noted here that the variables for the configuration in the container are passed on to the Jar file.
No own network configuration is made, but instead the DNS service of Docker Compose is used.

## How To - local build and run
- `[sudo -E] make start` (Builds and starts the containers in the background)
- `[sudo -E] make logs` (Returns the System.out.println values)
- `[sudo -E] make stop` (Stops the containers)
- `[sudo -E] make clean` (Stops the containers and removes the images)

## How To - DB Control
- `[sudo -E] make databases`(Shows the current master-slave role distribution)
- `[sudo -E] make killdb1` (Stops DB container "master")
- `[sudo -E] make killdb2` (Stops DB container "slave1")
- `[sudo -E] make killdb3` (Stops DB container "slave2")
- `[sudo -E] make restart` (Restarts stopped DB containers)

## Ausgabe der gesendeten Daten mittels Http-GET Request erfolgt unter folgenden Adressen
## Output of the sent data by Http-GET request is done at the following addresses
- `localhost:8081/` (shows ALL ACTIVE sensors)
- `localhost:8081/SensorData/current/all` (shows ALL CURRENT sensor data).
- `localhost:8081/SensorData/current/traffic` (returns THE CURRENT sensor data of the TRAFFIC sensor).
- `localhost:8081/SensorData/current/speed` (returns the CURRENT sensor date of the SPEED sensor).
- `localhost:8081/SensorData/current/distance` (returns the CURRENT sensor date of the DISTANCE sensor).
- `localhost:8081/SensorData/current/fuel` (returns the CURRENT sensor date of the FUEL sensor).
- `localhost:8081/SensorData/history/all` (returns ALL sensor data).
- `localhost:8081/SensorData/history/traffic` (returns ALL sensor data of the TRAFFIC sensor).
- `localhost:8081/SensorData/history/speed` (returns ALL sensor data of SPEED sensor).
- `localhost:8081/SensorData/history/distance` (returns ALL sensor data of the DISTANCE sensor).
- `localhost:8081/SensorData/history/fuel` (returns ALL sensor data of the FUEL sensor).

## Legal Information
The project is subject to the license GNU GLP V3

## TESTS
The test case documentation for the respective milestone is located in the [Test](Test) directory.

##Credits 
- This project was created in cooperation with Stephan Harijan.
- The project by TIM STOFFEL available at https://code.fbi.h-da.de/sttistof/vs-demo/-/jobs served as the initial project.
- The following database configuration was used to implement the project goal "High availability": https://github.com/mariadb-corporation/maxscale-docker