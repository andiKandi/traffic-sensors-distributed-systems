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
package sensor;

public class TrafficSituation {

    private static String traffic;

    public static String getTrafficSituation(int id){
        switch (id){
            case 0: {
                traffic = "NO TRAFFIC";
                return traffic;
            }
            case 1: {
                traffic = "LOW TRAFFIC";
                return traffic;
            }
            case 2: {
                traffic = "HIGH TRAFFIC";
                return traffic;
            }
            case 3:
                traffic = "JAM";
                return traffic;
            default: throw new IllegalArgumentException("TrafficSituation not found for id: " + id);
        }

    }
}
