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
package transmitter;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import sensor.SensorMessage;
import sensor.TrafficSituation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MqttTransmitter {
    private static final Logger logger = LogManager.getLogger(MqttTransmitter.class);
    final Mqtt5Client client;
    final String sensorType;
    private static int identifier = 0;

    public MqttTransmitter(String brokerName, String sensorType) throws UnknownHostException {

        final InetAddress ip = InetAddress.getByName(brokerName);
        String brokerIp = ip.toString().substring(7);
        logger.trace("ip.toString(): {}", ip.toString().substring(7));

        Mqtt5Client client = Mqtt5Client.builder()
                .identifier(sensorType) // use a unique identifier
                .serverHost(brokerIp) // use the public HiveMQ broker
                .automaticReconnectWithDefaultConfig() // the client automatically reconnects
                .build();
        this.client = client;
        this.sensorType = sensorType;
        logger.trace("Mqtt-Client-Sensor started");
    }

    public void init(){ //connect client to broker
        client.toBlocking().connectWith()
                .willPublish()
                .topic("home/will")
                .qos(MqttQos.EXACTLY_ONCE)
                .payload("sensor gone".getBytes())
                .applyWillPublish()
                .send();
        logger.trace("Last Will was sent");
    }

    public void publishData(int sendInterval){
        while (true) {
            try{
            client.toBlocking().publishWith()
                    .topic("sensor/" + sensorType)
                    .qos(MqttQos.EXACTLY_ONCE)
                    .payload(getMessage(this.sensorType))
                    .send();

            TimeUnit.MILLISECONDS.sleep(sendInterval);
            identifier++;

        } catch (Exception e){
                logger.error(e);
            }
        }
    }

    private static byte[] getMessage(String sensorType) {
        final Random random = new Random();
        String value = "";
        switch (sensorType) {
            case "FUEL":
                value = random.nextInt(101) + " %";
                break;
            case "DISTANCE":
                value = random.nextInt(250000) + " km";
                break;
            case "TRAFFIC":
                value = TrafficSituation.getTrafficSituation(random.nextInt(4));
                break;
            case "SPEED":
                value = random.nextInt(200) + " km/h";
                break;
            default:
                value = "DEFAULT";
                break;
        }

        SensorMessage sensorMessage = new SensorMessage(sensorType, Integer.toString(identifier), value);
        logger.trace("data published: {}", sensorMessage);
        final JSONObject jsonObject = new JSONObject(sensorMessage.toString());
        final byte[] data = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
        return data;
    }
}
