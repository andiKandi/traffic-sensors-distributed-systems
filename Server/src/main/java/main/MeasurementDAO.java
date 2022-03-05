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

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface MeasurementDAO {
    //insert new data into MariaDB
    @SqlUpdate("INSERT INTO speed (type, identifier, value) values (:type, :identifier, :value)")
    void insertSpeed(@Bind("type") String type, @Bind("identifier") int identifier, @Bind ("value") String value);

    @SqlUpdate("INSERT INTO traffic (type, identifier, value) values (:type, :identifier, :value)")
    void insertTraffic(@Bind("type") String type, @Bind("identifier") int identifier, @Bind ("value") String value);

    @SqlUpdate("INSERT INTO distance (type, identifier, value) values (:type, :identifier, :value)")
    void insertDistance(@Bind("type") String type, @Bind("identifier") int identifier, @Bind ("value") String value);

    @SqlUpdate("INSERT INTO fuel (type, identifier, value) values (:type, :identifier, :value)")
    void insertFuel(@Bind("type") String type, @Bind("identifier") int identifier, @Bind ("value") String value);

    /*
    //request specific data from MariaDB (String type is used for simplicity)
    @SqlQuery("SELECT ShipCity FROM Orders WHERE ShipCountry = :shipCountry")
    String findShipCityByShipCountry(@Bind("shipCountry") String shipCountry);
    */


}
