/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sit.onlinedataextractor;


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
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 *
 * @author Alford
 */
public class addOnlineDataTask implements Runnable {
    public static final IngestServices ingestServices = IngestServices.getInstance();
    public static final Logger logger = ingestServices.getLogger("FacebookDataExtractor"); 
    
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
        //Create a instance of ChromeOptions class
        ChromeOptions options = new ChromeOptions();
        //Add chrome switch to disable notification - "**--disable-notifications**"
        options.addArguments("--disable-notifications");
        //Pass ChromeOptions instance to ChromeDriver Constructor
        WebDriver driver = new ChromeDriver(options);
         // explicit wait - to wait for the download button to be click-able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(120));
         
        String username = panelSettings.getUsername();
        String password = panelSettings.getPassword();
        Boolean formatType = panelSettings.isJsonOrHtml();
        Boolean LatestExport = panelSettings.isDefaultLatestExport();
              
        driver.get("https://www.facebook.com/dyi/?referrer=yfi_settings");
        WebElement fb = driver.findElement(By.name("email"));
        fb.sendKeys(username);
        WebElement ps = driver.findElement(By.name("pass"));
        //input ur own password and username
        ps.sendKeys(password);
        WebElement login = driver.findElement(By.name("login"));
        login.click();
        
        if(panelSettings.isDataExport()){
            try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Format']")));
            WebElement format = driver.findElement(By.cssSelector("[aria-label='Format']"));
            format.click();
            //if true; export file as json, else html
            if(formatType == true)
            format.sendKeys("j");
            else{
            format.sendKeys("h");}

            format.sendKeys(Keys.RETURN);
            WebElement ddl = driver.findElement(By.cssSelector("[aria-label='Date range (required)']"));
            ddl.click();
            ddl.sendKeys("a");
            ddl.sendKeys(Keys.RETURN);
            WebElement requestdl = driver.findElement(By.cssSelector("[aria-label='Request a download']"));
            requestdl.click();
            }
            catch(Exception e){
            System.out.println(e);
            }
        }
        //download files
        if(LatestExport == true)
        {
            driver.get("https://www.facebook.com/dyi/?tab=all_archives");   
            String numFiles = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][4]")).getText();
            System.out.println(numFiles);

            String numFilesArray[] = numFiles.split(" ", 2);
            int numFile = Integer.parseInt(numFilesArray[0]); 

            //to download number of files based on download information given   
                try{
                for (int i = 1; i < numFile+1; i++) {
                    // click on the Download button as soon as the "Download" button is visible; else wait
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label=Download]")));
                    driver.findElement(By.cssSelector("[aria-label=Download]")).click();
                    if(numFile > 1){
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='x1i10hfl xjbqb8w x6umtig x1b1mbwd xaqea5y xav7gou xe8uvvx x1hl2dhg xggy1nq x1o1ewxj "
                                + "x3x9cwd x1e5q0jg x13rtm0m x87ps6o x1lku1pv x1a2a7pz xjyslct x9f619 x1ypdohk x78zum5 x1q0g3np x2lah0s x1w4qvff "
                                + "x13mpval xdj266r xat24cr xz9dl7a x1sxyh0 xsag5q8 xurb0ha x1n2onr6 x16tdsg8 x1ja2u2z x6s0dn4']["+i+"]")));
                        driver.findElement(By.xpath("//div[@class='x1i10hfl xjbqb8w x6umtig x1b1mbwd xaqea5y xav7gou xe8uvvx x1hl2dhg xggy1nq x1o1ewxj "
                                + "x3x9cwd x1e5q0jg x13rtm0m x87ps6o x1lku1pv x1a2a7pz xjyslct x9f619 x1ypdohk x78zum5 x1q0g3np x2lah0s x1w4qvff "
                                + "x13mpval xdj266r xat24cr xz9dl7a x1sxyh0 xsag5q8 xurb0ha x1n2onr6 x16tdsg8 x1ja2u2z x6s0dn4']["+i+"]")).click();
                    }
                    Thread.sleep(600);
                }
                }
                 catch(InterruptedException e){
                    System.out.println(e);
                }            
        }
        
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
