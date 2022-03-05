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

import main.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import vs.CentralService;



public class RPCSensorTransmitter {

    private static final Logger logger = LogManager.getLogger(RPCSensorTransmitter.class);

    private static boolean initialized = false;

    public static void init(final String ip, final int port) {
        initialized = true;
    }
    
    public static void writeToDB(String type, int identifier, String value, String serverIp, int serverPort) {
        logger.trace("RPC sending: type:{}, identifier:{}, value:{}", type, identifier, value);
        if (!initialized) {
            throw new RuntimeException("waiting for initialization of rpc transmitter");
        }

        boolean result = false;
        TTransport transport = new TSocket(serverIp, serverPort, 0, 0);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            CentralService.Client client = new CentralService.Client(protocol);
            result = client.addSensorReading(type, identifier, value);
        } catch (TException e) {
            logger.error("unable to persist", e);
            logger.error("serverIp {}, serverPort {}", serverIp, serverPort);
        }

        if (transport.isOpen()) {
            transport.close();
        }

        if(result) {
        }
    }
}