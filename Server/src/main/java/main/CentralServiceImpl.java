/**
 * Copyright (C) 2020 Harijan Stephan, Kraus Andreas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import vs.CentralService;

public class CentralServiceImpl implements CentralService.Iface {

    private static final Logger logger = LogManager.getLogger(CentralServiceImpl.class);

    private String databaseSocket;

    private Jdbi jdbi;

    public CentralServiceImpl(String databaseSocket) {
        jdbi = Jdbi.create("jdbc:mariadb://" + databaseSocket + "/connectedCars", "maxuser", "maxpwd");
        jdbi.installPlugin(new SqlObjectPlugin()); // sql objects
        this.databaseSocket = databaseSocket;
    }

    @Override
    public boolean addSensorReading(String type, int identifier, String value){
        logger.trace("Server received data via RPC. Now writing it in DB: type:{}, identifier:{}, value:{}", type, identifier, value);
            jdbi.useHandle(handle -> {
                try {
                    MeasurementDAO dao = handle.attach(MeasurementDAO.class);
                    switch (type) {
                        case "TRAFFIC":
                            dao.insertTraffic(type, identifier, value);
                            break;
                        case "SPEED":
                            dao.insertSpeed(type, identifier, value);
                            break;
                        case "DISTANCE":
                            dao.insertDistance(type, identifier, value);
                            break;
                        case "FUEL":
                            dao.insertFuel(type, identifier, value);
                            break;
                    }
                    handle.commit();
                } catch (Exception e) {
                    try {
                        //Fehlerbehandlung bei Master-Ausfall: Erneutes Senden der Daten, bis Switch zum neuen Master erfolgt.
                        Thread.sleep(1000L);
                        jdbi = Jdbi.create("jdbc:mariadb://" + this.databaseSocket + "/connectedCars", "maxuser", "maxpwd");
                        jdbi.installPlugin(new SqlObjectPlugin()); // sql objects
                        addSensorReading(type, identifier, value);
                    } catch (InterruptedException e1) {
                        logger.error(e1);
                    }
                }
            });
        return true;
    }
}
