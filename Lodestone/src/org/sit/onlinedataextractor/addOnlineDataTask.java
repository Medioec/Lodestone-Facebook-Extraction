/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sit.onlinedataextractor;


import org.openide.util.NbBundle.Messages;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.casemodule.services.FileManager;
import org.sleuthkit.datamodel.Host;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorProgressMonitor;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorCallback;
import org.sleuthkit.autopsy.ingest.IngestServices;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.datamodel.Content;
import org.sleuthkit.datamodel.LocalFilesDataSource;
import org.sleuthkit.datamodel.TskCoreException;
import org.sleuthkit.datamodel.TskDataException;

import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.sleuthkit.datamodel.AbstractFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Alford
 */
public class addOnlineDataTask implements Runnable {
    public static final IngestServices ingestServices = IngestServices.getInstance();
    public static final Logger logger = ingestServices.getLogger("iOSDeviceDataExtractor"); 
    
    private final DataSourceProcessorProgressMonitor progressMonitor;
    private final DataSourceProcessorCallback callbackObj;
    private final Host host;
    
    private boolean hasCriticalError = false;
    private final List<String> errorList = new ArrayList<>();
    private final OnlineDataProcessorPanelSettings panelSettings;
    private final List<String> localFilePaths = new ArrayList<>();
    private final List<Content> newDataSources = new ArrayList<>();
    
    
    public addOnlineDataTask(Host host, OnlineDataProcessorPanelSettings panelSettings, DataSourceProcessorProgressMonitor aProgressMonitor, DataSourceProcessorCallback cbObj){
        this.host = null;
        this.progressMonitor = aProgressMonitor;
        this.callbackObj = cbObj;
        this.panelSettings = panelSettings;        
    }
    
    @Messages({
    "addOnlineDataTask.downloading.website.error=Facebook website error.",
    "addOnlineDataTask.downloading.login.error=Credentials incorrect.",
    "addOnlineDataTask.downloading.login.success=Login success.",
    "addOnlineDataTask.downloading.export.notfound=Data export website does not have file to be exported",
    "addOnlineDataTask.error.add.files.dataSources=Error add files to new DataSources"})
    
    @Override    
    public void run() {
        errorList.clear();
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.youtube.com/");
        
        boolean check = panelSettings.isBackupEncrypted();
        
        //// this is just a test, not supposed to run the script here, create a thread to run these instead
        DataSourceProcessorCallback.DataSourceProcessorResult result = DataSourceProcessorCallback.DataSourceProcessorResult.NO_ERRORS;
        List<String> errorList = new ArrayList<>();
        List<Content> newDataSources = new ArrayList<>();
        List<String> localFilePaths = new ArrayList<>();
        
        // trying copying local file to module output file
        FileManager fileManager = Case.getCurrentCase().getServices().getFileManager();
        String modDir = Case.getCurrentCase().getModulesOutputDirAbsPath();
        String newDataSourceName = "Facebook";
        
        String sourceFilePath = "D:\\Desktop\\OneDrive - Singapore Institute Of Technology\\Singapore Institute of Technology\\2.1\\Schedule.csmo";
        
        File source = new File(sourceFilePath);
        String src_filename = source.getName();
        String destFilePath = modDir+"\\Facebook\\"+src_filename;
        File dest = new File(destFilePath);
        try {
            Files.createDirectories(Paths.get(modDir+"\\Facebook"));
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        String sourceFilePath1 = "E:\\Desktop\\School Stuff\\Singapore Institute of Technology\\Miscellaneous\\Programming\\Autopsy\\GoogleTakeout\\Outlook.pst";
        String sourceFilePath2 = "E:\\Desktop\\School Stuff\\Singapore Institute of Technology\\Miscellaneous\\Programming\\Autopsy\\GoogleTakeout\\SolrCore.properties";
        // local file to add, probably downloaded files
        localFilePaths.add(destFilePath);
        localFilePaths.add(sourceFilePath1);
        localFilePaths.add(sourceFilePath2);
        try {
            LocalFilesDataSource newDataSource = fileManager.addLocalFilesDataSource(UUID.randomUUID().toString(), newDataSourceName, "", host, localFilePaths, new ProgressUpdater());
        } catch (TskCoreException | TskDataException ex) {
            errorList.add(Bundle.addOnlineDataTask_error_add_files_dataSources());
            logger.log(Level.SEVERE, Bundle.addOnlineDataTask_error_add_files_dataSources());
            hasCriticalError = true;                    
        }
        System.out.println(source.toPath());
        System.out.println(src_filename);
        System.out.println(destFilePath);
        doCallBack();
    }
    
    private void doCallBack() {
        DataSourceProcessorCallback.DataSourceProcessorResult result;

        if (hasCriticalError) {
            result = DataSourceProcessorCallback.DataSourceProcessorResult.CRITICAL_ERRORS;            
        } else if (!errorList.isEmpty()) {
            result = DataSourceProcessorCallback.DataSourceProcessorResult.NONCRITICAL_ERRORS;
        } else {
            result = DataSourceProcessorCallback.DataSourceProcessorResult.NO_ERRORS;
        }
        callbackObj.done(result, errorList, newDataSources);
    }
    
    private class ProgressUpdater implements FileManager.FileAddProgressUpdater {
        private int count;
        
        @Override
        public void fileAdded(AbstractFile file) {
            count++;
            if(count % 10 == 0){               
                progressMonitor.setProgressText(NbBundle.getMessage(this.getClass(), "addDeviceDataTask.localFileAdd.progress.text", file.getParentPath(), file.getName()));           
            }
        }
    }
}
