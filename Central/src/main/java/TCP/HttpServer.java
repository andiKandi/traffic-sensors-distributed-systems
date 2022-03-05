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
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

import SensorDataHandling.SensorData;
import TCP.Handler.HTTPRequest;
import com.google.common.collect.ListMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpServer implements Runnable {

    private static final Logger logger = LogManager.getLogger(HttpServer.class);

    private ServerSocket socket;
    private int port;

    public HttpServer(int port) throws IOException {
        this.port = port;
        this.socket = new ServerSocket(this.port);
    }

    private void restGetCurrentActiveSensor(Socket connection, DataOutputStream httpOut, HTTPRequest httpReq) throws IOException {
        try {
            Map<String, SensorData> stringSensorDataMap = DataCenter.getInstance().getCurrentSensorData();
            JSONArray jsonArray = new JSONArray();
            stringSensorDataMap.entrySet().stream().map(e -> {
                JSONObject sensorD = new JSONObject();
                JSONArray sensorInfo = new JSONArray();
                sensorInfo.put("ACTIVE; Current Sensor Data: localhost:8081/SensorData/current/" + e.getKey().toString().toLowerCase());
                sensorInfo.put("ACTIVE; All Sensor Data: localhost:8081/SensorData/history/" + e.getKey().toString().toLowerCase());
                sensorD.put(e.getKey(), sensorInfo);
                return sensorD;
            }).forEach(jsonArray::put);

            HttpResponse.ok().withBody(jsonArray).send(httpOut);

        } catch (final Exception ex) {
            HttpResponse.error(ex).send(httpOut);
        }
    }

    //Sends all current sensorData
    private void restGetCurrentSensorData(Socket connection, DataOutputStream httpOut, HTTPRequest httpReq) throws IOException {
        try {
            //get current data
            //       0           1         2      3
            //localhost:8081/SensorData/current/traffic
            String req = httpReq.path.split("/")[3].replaceAll("%20", " ");
            req = req.toUpperCase();
            Map<String, SensorData> stringSensorDataMap = DataCenter.getInstance().getCurrentSensorData();
            JSONArray jsonArray = new JSONArray();

            if (req.equals("TRAFFIC") || req.equals("SPEED") || req.equals("DISTANCE") || req.equals("FUEL")) {
                Collection<SensorData> coll = Collections.singleton(stringSensorDataMap.get(req));
                for (SensorData elem : coll) {
                    jsonArray.put(elem.getJsonSensorData());
                }
                    HttpResponse.ok().withBody(jsonArray).send(httpOut);
            }

            else if (req.equals("ALL")){
                stringSensorDataMap.entrySet().stream().map(e -> {
                    JSONObject sensorD = new JSONObject();
                    sensorD.put(e.getKey(), e.getValue().getJsonSensorData());
                    return sensorD;
                }).forEach(jsonArray::put);

                HttpResponse.ok().withBody(jsonArray).send(httpOut);

            }
            else {
                HttpResponse.notImplemented().send(httpOut);
            }
        } catch (Exception ex) {
            HttpResponse.error(ex).send(httpOut);
        }
    }

    private void restGetHistorySensorData(Socket connection, DataOutputStream httpOut, HTTPRequest httpReq) throws IOException {
        try {
            //get current data
            //       0           1         2      3
            //localhost:8081/SensorData/current/traffic
            String req = httpReq.path.split("/")[3].replaceAll("%20", " ");
            req = req.toUpperCase();
            ListMultimap<String, SensorData> stringSensorDataListMultimap = DataCenter.getInstance().getHistorySensorData();
            JSONArray jsonArray = new JSONArray();

            if (req.equals("TRAFFIC") || req.equals("SPEED") || req.equals("DISTANCE") || req.equals("FUEL")) {

                for(SensorData value : stringSensorDataListMultimap.values()){
                    if (value.getType().equals(req)) {
                        jsonArray.put(value.getJsonSensorData());
                    }
                }

                HttpResponse.ok().withBody(jsonArray).send(httpOut);

            } else if (req.equals("ALL")){
                for(SensorData value : stringSensorDataListMultimap.values()){
                    jsonArray.put(value.getJsonSensorData());
                }

                HttpResponse.ok().withBody(jsonArray).send(httpOut);

            } else {
                HttpResponse.notImplemented().send(httpOut);
            }
        } catch (Exception ex) {
            HttpResponse.error(ex).send(httpOut);
        }
    }





    public void run() {
        while(true) {

            Socket connection = null;
            try {

                connection = this.socket.accept();
                BufferedReader httpIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                DataOutputStream httpOut = new DataOutputStream(connection.getOutputStream());

                HTTPRequest httpReq = new HTTPRequest(httpIn);


                if(httpReq.method.equals("GET")) {

                    if(httpReq.path.equals("/")) {
                        this.restGetCurrentActiveSensor(connection, httpOut, httpReq);
                    } else if((boolean) Pattern.matches("\\/SensorData\\/current\\/.*", httpReq.path)) {
                        this.restGetCurrentSensorData(connection, httpOut, httpReq);

                    } else if((boolean) Pattern.matches("\\/SensorData\\/history\\/.*", httpReq.path)) {//get all sensordata
                        this.restGetHistorySensorData(connection, httpOut, httpReq);
                    }
                    else {
                        HttpResponse.notFound().send(httpOut);
                    }

                //Method is not GET
                } else {
                    HttpResponse.notImplemented().send(httpOut);
                }
                httpOut.flush();

            } catch (Exception ex) {
                logger.error("Failed to send / receive message: ", ex);
            } finally {
                if(connection != null) {
                    try {
                        connection.close();
                    } catch (IOException e) {
                        logger.error("Failed to close connection: ", e);
                    }
                }
            }
        }
    }

}
