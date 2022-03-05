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
package SensorDataHandling;

import org.json.JSONObject;

public class SensorData  {
  private JSONObject jsonSensorData;
  private String type;
  private int identifier;
  private String value;

  public SensorData(String type, String identifier, String value, String timestamp) {
    this.jsonSensorData = new JSONObject();
    this.type = type;
    this.identifier = Integer.parseInt(identifier);
    this.value = value;
    this.jsonSensorData.put("type", type);
    this.jsonSensorData.put("identifier", identifier);
    this.jsonSensorData.put("value", value);
    this.jsonSensorData.put("timestamp", timestamp);
  }

  @Override
  public String toString() {
    return this.jsonSensorData.toString();
  }

  public JSONObject getJsonSensorData() {
    return jsonSensorData;
  }

  public String getType() {
    return type;
  }

  public int getIdentifier() {return identifier;}

  public String getValue() {return value;}
}