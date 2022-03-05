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


import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import SensorDataHandling.SensorData;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MqttReceiver {
    private static final Logger logger = LogManager.getLogger(MqttReceiver.class);
    private Mqtt5Client client;

    // 1. create the client
    public MqttReceiver(String brokerName) throws Exception{
        final InetAddress ip = InetAddress.getByName(brokerName);
        String brokerIp = ip.toString().substring(7);
        logger.trace("ip.toString(): {}", brokerIp);

        client = Mqtt5Client.builder()
                .identifier("controlcenter") // use a unique identifier
                .serverHost(brokerIp) // connect to the given HiveMQ broker
                .automaticReconnectWithDefaultConfig() // the client automatically reconnects
                .build();
        this.client = client;
    }

    // 2. connect the client
    public void init() {
        client.toBlocking().connectWith()
                .cleanStart(false)
                .sessionExpiryInterval(TimeUnit.HOURS.toSeconds(1)) // buffer messages
                .send();
    }

    public void subscribeData(String serverIp, int serverPort){
        // 3. subscribe and consume messages
        client.toAsync().subscribeWith()
                .topicFilter("sensor/#")
                .callback(publish -> {
                    //DataHandling: Buffering Data & Writing Datato DB via RPCTransmitter
                    DataProcessor.processData(publish.getPayloadAsBytes(), serverIp, serverPort);
                })
                .send();
    }

}