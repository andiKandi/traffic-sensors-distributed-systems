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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SensorMessage implements Serializable {

    private static final long serialVersionUid = 1L;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private final String type;
    private final String identifier;
    private final String value;
    private final String timestamp;

    public SensorMessage(final String sensorType, final String identifier, final String value) {
        this.type = sensorType;
        this.identifier = identifier;
        this.value = value;
        this.timestamp = DATE_FORMAT.format(new Date());
    }

    @Override
    public String toString() {
        return "{" +
                "type:" + type +
                ", identifier:'" + identifier + '\'' +
                ", value:'" + value + '\'' +
                ", timestamp:'" + timestamp + '\'' +
                '}';
    }
}
