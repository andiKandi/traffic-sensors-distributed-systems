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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LogManager.getLogger(HttpResponse.class);

    private final ListMultimap<HttpHeader, Object> headers = ArrayListMultimap.create();
    private final HttpStatus status;
    private Object body;

    private HttpResponse(final HttpStatus status) {
        this.status = status;
    }

    public static HttpResponse ok() {
        return new HttpResponse(HttpStatus.OK);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatus.NOT_FOUND);
    }

    public static HttpResponse error(final Exception e) { //hier sollte eigentlich der Feher nicht öffentlich gemacht werden, aus Sicherheitsaspekten
        return new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR).withBody(e.toString());
    }

    public static HttpResponse notImplemented (){
        return new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
    }

    public HttpResponse withHeader(final HttpHeader header, final Object value) {
        headers.put(header, value);
        return this;
    }

    public HttpResponse withBody(final String contentType, final Object body) {
        this.body = body;
        return this.withHeader(HttpHeader.CONTENT_TYPE, contentType).withHeader(HttpHeader.CONTENT_LENGTH, body.toString().length());
    }

    public HttpResponse withBody(final JSONObject json) {
        return this.withBody("application/json", json);
    }

    public HttpResponse withBody(final JSONArray json) {
        return this.withBody("application/json", json);
    }

    public HttpResponse withBody(final String text) {
        return this.withBody("plain/text", text);
    }

    public void send(final DataOutputStream output) throws IOException {
        final String httpResponse = this.toString();
        output.writeBytes(httpResponse);
        logger.trace("HTTPResponse: {}", httpResponse);
    }

    @Override
    public String toString() {
        final StringBuilder response = new StringBuilder();

        response.append("HTTP/1.1 ");
        response.append(status.getKey());
        response.append("\r\n");

        headers.forEach((header, value) -> {
            response.append(header.getKey());
            response.append(": ");
            response.append(value);
            response.append("\r\n");
        });

        if (body != null) {
            response.append("\r\n");
            response.append(body);
        } else {
            response.append(HttpHeader.CONTENT_LENGTH);
            response.append(": 0\r\n");
        }
        return response.toString();
    }
}
