/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lodestone.facebooksource;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
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
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.lodestone.facebooksource.Bundle;
import sun.net.ProgressMonitor;


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
    private final OnlineDataProcessorPanelSettings panelSettings;
    private List<String> errorList;
    private List<String> localFilePaths;
    private List<Content> newDataSources;
    private final String modDir;
    private final String newDataSourceName;
    
    
    public addOnlineDataTask(Host host, OnlineDataProcessorPanelSettings panelSettings, DataSourceProcessorProgressMonitor aProgressMonitor, DataSourceProcessorCallback cbObj){
        this.host = null;
        this.progressMonitor = aProgressMonitor;
        this.callbackObj = cbObj;
        this.panelSettings = panelSettings;
        this.errorList = new ArrayList<>();
        this.localFilePaths = new ArrayList<>();
        this.newDataSources = new ArrayList<>();
        this.modDir = Case.getCurrentCase().getModulesOutputDirAbsPath()+"\\Facebook\\";
        this.newDataSourceName = "Facebook";
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
        
        FileManager fileManager = Case.getCurrentCase().getServices().getFileManager();
        
        
        //Create a instance of ChromeOptions class
        ChromeOptions options = new ChromeOptions();
        //Add chrome switch to disable notification - "**--disable-notifications**"
        options.addArguments("--disable-notifications");
        
        try {
            Files.createDirectories(Paths.get(modDir));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", modDir);
        options.setExperimentalOption("prefs", chromePrefs);
        
        //Pass ChromeOptions instance to ChromeDriver Constructor
        WebDriver driver = new ChromeDriver(options);
         // explicit wait - to wait for the download button to be click-able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(120));
         
        String username = panelSettings.getUsername();
        String password = panelSettings.getPassword();
        Boolean formatType = panelSettings.isJsonOrHtml();
        Boolean LatestExport = panelSettings.isDefaultLatestExport();
        Boolean DataExport =panelSettings.isDataExport();
              
        driver.get("https://www.facebook.com/dyi/?referrer=yfi_settings");
        WebElement fb = driver.findElement(By.name("email"));
        fb.sendKeys(username);
        WebElement ps = driver.findElement(By.name("pass"));
        //input ur own password and username
        ps.sendKeys(password);
        WebElement login = driver.findElement(By.name("login"));
        login.click();
        //calls data request method
        DataRequest(driver,formatType,DataExport);
        //download files, if true wait if there is a pending request, if no pending request = download latest available file.
        try{     
                Thread.sleep(600);
                driver.get("https://www.facebook.com/dyi/?tab=all_archives");}
        catch(InterruptedException e){
               System.out.println(e);
            }
        if(LatestExport == true)
        {
            try{   
                Thread.sleep(600);
                try{
                    //Wait until pending disappears
                String status = driver.findElement(By.xpath("//div[@class='x6s0dn4 x78zum5 x13a6bvl']")).getText();
                progressMonitor.setProgressText("Files "+status+" for download, please wait");
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='x6s0dn4 x78zum5 x13a6bvl']")));
                Thread.sleep(600);
                }
                catch(Exception e){
                   System.out.println("No Pending files available for download"); 
                }
                String numFiles = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][4]")).getText();
                System.out.println(numFiles);
                String numFilesArray[] = numFiles.split(" ", 2);
                int numFile = Integer.parseInt(numFilesArray[0]);
                String fileDate = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][1]")).getText();
                String fileFormat = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][2]")).getText();
                String fileQuality = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][3]")).getText();
                
                progressMonitor.setProgressText("Downloading file \n"+fileDate+"\n"+fileFormat+"\n"+fileQuality+"\n"+numFiles);

                //to download number of files based on download information given   
                
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
        else{
            try{
                    Thread.sleep(800);
                    String numFiles = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][4]")).getText();
                    System.out.println(numFiles);
                    String numFilesArray[] = numFiles.split(" ", 2);
                    int numFile = Integer.parseInt(numFilesArray[0]);
                    String fileDate = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][1]")).getText();
                    String fileFormat = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][2]")).getText();
                    String fileQuality = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][3]")).getText();

                        progressMonitor.setProgressText("Downloading file \n"+fileDate+"\n"+fileFormat+"\n"+fileQuality+"\n"+numFiles);
                        for (int i = 1; i < numFile+1; i++) 
                        {
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
        
        errorList = new ArrayList<>();
        newDataSources = new ArrayList<>();
        localFilePaths = new ArrayList<>();
        
        // Adding downloaded files as a Data Source
        File downloadFolder = new File(modDir);
        File[] listOfFiles = downloadFolder.listFiles();
        
        for (File file : listOfFiles) {
            localFilePaths.add(file.getAbsolutePath());
            System.out.println(file.getAbsolutePath());
        }
        LocalFilesDataSource newDataSource;
        try {
            newDataSource = fileManager.addLocalFilesDataSource(UUID.randomUUID().toString(), newDataSourceName, "", host, localFilePaths, new ProgressUpdater());
            newDataSources.add(newDataSource);
        } catch (TskCoreException | TskDataException ex) {
            errorList.add(Bundle.addOnlineDataTask_error_add_files_dataSources());
            logger.log(Level.SEVERE, Bundle.addOnlineDataTask_error_add_files_dataSources());
            hasCriticalError = true;                    
        }
        
        doCallBack();
    }
    private void DataRequest(WebDriver driver, Boolean formatType, Boolean dataExport){
        
         // explicit wait - to wait for the download button to be click-able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(120));
         if(dataExport){
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
