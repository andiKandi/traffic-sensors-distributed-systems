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
package TCP;

import SensorDataHandling.SensorData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class DataProcessor {
    private static final Logger logger = LogManager.getLogger(DataProcessor.class);

    public static void processData(byte[] message, String serverIp, int serverPort) {
        JSONObject json = new JSONObject(new String(message, StandardCharsets.UTF_8));
        String type = json.getString("type");
        String identifier = json.getString("identifier");
        String value = json.getString("value");
        String timestamp = json.getString("timestamp");

        SensorData sensorData = new SensorData(type, identifier, value, timestamp);

        logger.trace("CENTRAL receiving: {}",
            sensorData
        );

        //buffern der Sensordaten: Map und Multimap
        DataCenter.getInstance().refreshCurrentSensordata(sensorData);
        DataCenter.getInstance().addHistorySensorData(sensorData);

        //persistieren der Sensordaten in der Datenbank: mariaDB
        RPCSensorTransmitter.writeToDB(sensorData.getType(), sensorData.getIdentifier(), sensorData.getValue(), serverIp, serverPort);
    }
}
