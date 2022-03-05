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

    @Option(name="-d", aliases = { "--database" }, usage = "the database host and port")
    private String database;

    @Option(name="-p", aliases = { "--port" }, usage = "the rpc port")
    private int port = 50555;

    @Option(name = "-h", aliases = { "--help" }, help = true)
    private boolean printHelpMessage = false;

    public String getDatabase() {
        return database;
    }

    public int getPort() {
        return port;
    }

    public boolean isPrintHelpMessage() {
        return printHelpMessage;
    }
}
