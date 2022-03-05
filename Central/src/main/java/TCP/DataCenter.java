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

import SensorDataHandling.SensorData;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataCenter {

  public static Map<String, SensorData> currentSensorData;
  public static ListMultimap<String, SensorData> historySensorData;

  private static DataCenter dataCenter = new DataCenter();

  public static synchronized DataCenter getInstance(){
    if(DataCenter.dataCenter == null){
      DataCenter.dataCenter = new DataCenter();
    }
    return DataCenter.dataCenter;
  }

  public void init() {
    this.currentSensorData = new ConcurrentHashMap<>(); //Thread safe
    this.historySensorData = Multimaps.synchronizedListMultimap(ArrayListMultimap.create()); //Thread safe
  }

  public Map<String, SensorData> getCurrentSensorData() {
    return currentSensorData;
  }

  public ListMultimap<String, SensorData> getHistorySensorData() {
    return historySensorData;
  }

  public void refreshCurrentSensordata(SensorData s) { //called at UDPListenerthread
    currentSensorData.put(s.getType(), s);
  }

  public void addHistorySensorData(SensorData s) { //called at UDPListenerthread
    this.historySensorData.put(s.getType(), s);
  }
}
