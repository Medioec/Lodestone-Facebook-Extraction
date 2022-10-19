/*
 * Copyright (C) 2021 Grzegorz Bieś, Ernest Bieś
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful
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
package org.sit.onlinedataextractor;

import javax.swing.JPanel;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessor;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorCallback;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorProgressMonitor;
import org.openide.util.lookup.ServiceProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sleuthkit.datamodel.Host;

import java.util.List;
import java.util.ArrayList;
import org.sleuthkit.datamodel.Content;

import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.services.FileManager;
import org.sleuthkit.datamodel.LocalFilesDataSource;
import java.util.UUID;
import org.sleuthkit.datamodel.AbstractFile;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskDataException;

@ServiceProvider(service = DataSourceProcessor.class)
public class OnlineDataProcessor implements DataSourceProcessor {

    private final String modulename = "Extraction data from online cloud sources";
    private final OnlineDataProcessorPanel processorPanel;

    public OnlineDataProcessor() {
        processorPanel = new OnlineDataProcessorPanel();
        
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Alford\\Documents\\NetBeansProjects\\Lodestone\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com/");
    }

    @Override
    public String getDataSourceType() {
        return modulename;
    }

    @Override
    public JPanel getPanel() {
        return processorPanel;
    }

    @Override
    public boolean isPanelValid() {
        return processorPanel.validatePanel();
    }

    @Override
    public void run(DataSourceProcessorProgressMonitor progressMonitor, DataSourceProcessorCallback callback) {
        //WebDriver driver = new ChromeDriver();
        //driver.get("http://www.yahoo.com/");
        //new Thread(new addDeviceDataTask(processorPanel.getPanelSettings(), progressMonitor, callback)).start();
    }

    @Override // Live
    public void run(Host host, DataSourceProcessorProgressMonitor progressMonitor, DataSourceProcessorCallback callback) {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.youtube.com/");
        //new Thread(new addDeviceDataTask(host, processorPanel.getPanelSettings(), progressMonitor, callback)).start();
        
        
        //// this is just a test, not supposed to run the script here, create a thread to run these instead
        DataSourceProcessorCallback.DataSourceProcessorResult result = DataSourceProcessorCallback.DataSourceProcessorResult.NO_ERRORS;
        List<String> errorList = new ArrayList<>();
        List<Content> newDataSources = new ArrayList<>();
        List<String> localFilePaths = new ArrayList<>();
        
        FileManager fileManager = Case.getCurrentCase().getServices().getFileManager();
        String newDataSourceName = "Facebook";
        
        // local file to add, probably downloaded files
        localFilePaths.add("D:\\Desktop\\OneDrive - Singapore Institute Of Technology\\Singapore Institute of Technology\\2.1\\Schedule.csmo");
        
        try {
            LocalFilesDataSource newDataSource = fileManager.addLocalFilesDataSource(UUID.randomUUID().toString(), newDataSourceName, "", host, localFilePaths, new ProgressUpdater());
        } catch (TskCoreException | TskDataException ex) {                 
        }
        callback.done(result,errorList,newDataSources);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void reset() {
        processorPanel.resetPanel();
    }
    
    private class ProgressUpdater implements FileManager.FileAddProgressUpdater {        
        @Override
        public void fileAdded(AbstractFile file) {
        }
    }
}
