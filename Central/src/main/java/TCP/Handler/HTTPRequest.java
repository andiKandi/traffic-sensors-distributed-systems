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
package TCP.Handler;
import java.io.BufferedReader;
import java.io.IOException;

public class HTTPRequest {

    //used in rest api
    public String path;
    public String method;
    public String[] params;

    //additional
    public String protocolversion;
    public String cache;
    public String connection;
    public String useragent;
    public String accept;

    public HTTPRequest(BufferedReader httpIn) throws IOException {
        String line = httpIn.readLine();
        boolean firstrow = true;
        while(line != null && !line.equals("")) {

            if(firstrow) {
                firstrow = false;

                this.method = line.split(" ")[0];
                this.protocolversion = line.split(" ")[2];

                String req = line.split(" ")[1]; //split at whitespace, get second param
                this.path = req.contains("?") ? req.split("\\?")[0] : req; //get path

                if(req.contains("?")) {
                    String paramlist = req.split("\\?")[1];
                    this.params = paramlist.split("&");
                } else {
                    this.params = new String[0];
                }

            } else {
                int separatorIndex = line.indexOf(":");
                if(separatorIndex != -1) {
                    String key = line.substring(0, separatorIndex);
                    String value = line.substring(separatorIndex +1);

                    switch(key) {
                        case "Connection":
                            this.connection = value;
                            break;
                        case "User-Agent":
                            this.useragent = value;
                            break;
                        case "Accept":
                            this.accept = value;
                            break;
                        case "Cache-Control":
                            this.cache = value;
                            break;
                    }
                }
            }

            line = httpIn.readLine();
        }

    }

    public String getParam(String key) {

        for(int i = 0; i < this.params.length; i++) {
            String _key = this.params[i].split("=")[0];
            String value = this.params[i].split("=")[1];

            if(_key.equals(key))
                return value;
        }

        return null;
    }

}
