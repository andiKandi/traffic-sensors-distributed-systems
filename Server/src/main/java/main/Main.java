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
import org.apache.thrift.transport.TTransportException;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
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

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource("jdbc:mariadb://" + arguments.getDatabase() , "maxuser", "maxpwd")
                    .repeatableSqlMigrationPrefix("R")
                    .schemas("connectedCars")
                    .load();
            logger.info("Server started.");
            // Start the migration

            flyway.clean();
            flyway.migrate();
        } catch (FlywayException e) {
            logger.error(e);
        }

        RPCSensorReceiver rpcSensorReceiver = new RPCSensorReceiver();
        try {
            rpcSensorReceiver.start(arguments.getPort(), arguments.getDatabase());
        } catch (TTransportException e) {
            logger.error(e);
        }
    }
}
