/* 
 * Copyright (C) 2021 Grzegorz Bieś, Ernest Bieś
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * iOS Device Data Extractor (Autopsy module), version  1.0
 *
 */

package org.lodestone.facebooksource;


public class OnlineDataProcessorPanelSettings {
    private boolean dataExport;
    private boolean jsonOrHtml;
    private boolean defaultLatestExport;
    private String username;
    private String password;

    public boolean isDataExport() {
        return dataExport;
    }

    public void setDataExport(boolean dataExport) {
        this.dataExport = dataExport;
    }

    public boolean isJsonOrHtml() {
        return jsonOrHtml;
    }

    public void setJsonOrHtml(boolean jsonOrHtml) {
        this.jsonOrHtml = jsonOrHtml;
    }

    public boolean isDefaultLatestExport() {
        return defaultLatestExport;
    }

    public void setDefaultLatestExport(boolean defaultLatestExport) {
        this.defaultLatestExport = defaultLatestExport;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }      
}
