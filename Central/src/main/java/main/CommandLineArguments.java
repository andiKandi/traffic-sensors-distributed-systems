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

    @Option(name="-s", aliases = { "--serverip" }, usage = "the serverip or servername of the server for the DB")
    private String serverIp = "server";

    @Option(name="-p", aliases = { "--serverport" }, usage = "the server port of the DBserver")
    private int serverPort = 50555;

    @Option(name="-a", aliases = { "--apiport" }, usage = "the apiport of the server")
    private int apiPort = 8081;

    @Option(name="-b", aliases = { "--brokerip" }, usage = "the ip of the mqtt-broker")
    private String brokerIp = "broker";

    @Option(name = "-h", aliases = { "--help" }, help = true)
    private boolean printHelpMessage = false;

    public int getApiPort() {
        return apiPort;
    }

    public int getMqttPort() {
        return serverPort;
    }

    public String getBrokerIp() { return brokerIp; }

    public boolean isPrintHelpMessage() {
        return printHelpMessage;
    }

    public String getServerIp() { return serverIp; }

    public int getServerPort() { return serverPort; }
}
