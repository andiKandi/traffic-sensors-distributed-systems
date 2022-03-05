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

public enum HttpStatus {

    OK ("200 Ok"),
    NOT_FOUND ("404 Not Found"),
    INTERNAL_SERVER_ERROR ("500 Internal Server Error"),
    NOT_IMPLEMENTED ("501 Not Implemented");

    private final String key;

    private HttpStatus(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
