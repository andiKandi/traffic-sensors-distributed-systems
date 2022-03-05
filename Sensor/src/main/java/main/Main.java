
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
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import transmitter.MqttTransmitter;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(final String... args) throws Exception{
        logger.info("Program 'Sensor'  Copyright (C) 2020  Harijan Stephan, Kraus Andreas");

        final CommandLineArguments arguments = new CommandLineArguments();
        final CmdLineParser parser = new CmdLineParser(arguments);
        try {
            parser.parseArgument(args);
            if (arguments.isPrintHelpMessage()) {
                parser.printUsage(System.out);
                System.exit(0);
            }
        } catch (final CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }

        logger.debug("type: {}, mqtt-broker: {}, port: {}, send interval: {}ms",
                arguments.getSensorType(),
                arguments.getIp(),
                arguments.getPort(),
                arguments.getSendInterval()
        );

        MqttTransmitter mqttTransmitter = new MqttTransmitter(arguments.getIp(), arguments.getSensorType());
        mqttTransmitter.init();
        mqttTransmitter.publishData(arguments.getSendInterval());
    }
}
