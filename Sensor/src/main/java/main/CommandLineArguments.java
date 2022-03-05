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

import org.kohsuke.args4j.Option;

public class CommandLineArguments {

    @Option(name="-b", aliases = { "--broker" }, usage = "the ip or hostname of the mqtt-broker")
    private String brokerIp = "127.0.0.1";

    @Option(name="-p", aliases = { "--port" }, usage = "the port of the mqtt-broker")
    private int port = 9876;

    @Option(name="-t", aliases = { "--type" }, usage = "the sensor type", required = true)
    private String sensorType;

    @Option(name="-i", aliases = { "--interval" }, usage = "the send interval of the sensor metrics")
    private int sendInterval = 3000;

    @Option(name = "-h", aliases = { "--help" }, help = true)
    private boolean printHelpMessage = false;

    public String getIp() {
        return brokerIp;
    }

    public int getPort() {
        return port;
    }

    public String getSensorType() {
        return sensorType;
    }

    public int getSendInterval() {
        return sendInterval;
    }

    public boolean isPrintHelpMessage() {
        return printHelpMessage;
    }
}
