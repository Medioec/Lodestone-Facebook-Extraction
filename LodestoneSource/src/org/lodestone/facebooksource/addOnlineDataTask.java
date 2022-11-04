package org.lodestone.facebooksource;

import java.io.File;
import java.io.IOException;
import org.lodestone.facebooksource.Bundle;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import static org.awaitility.Durations.*;

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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import static java.util.concurrent.TimeUnit.MINUTES;
import java.util.zip.ZipFile;
import org.openide.util.Exceptions;

import static org.awaitility.Awaitility.*;
import static java.util.concurrent.TimeUnit.*;
import org.junit.Assert;




/**
 *
 * @author Alford, Jun Jie
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
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless");
   
        
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
        progressMonitor.setProgressText("Attempting to login with Facebook credentials, please wait");
        WebElement fb = driver.findElement(By.name("email"));
        fb.sendKeys(username);
        WebElement ps = driver.findElement(By.name("pass"));
        ps.sendKeys(password);
        WebElement login = driver.findElement(By.name("login"));
        login.click();
        WebDriverWait waitlogin = new WebDriverWait(driver, Duration.ofSeconds(15));
        try{
        waitlogin.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='x1ye3gou xn6708d x1qughib x78zum5 x6s0dn4']")));
        }
        catch(Exception e){
            errorList.add("Login Failed due to incorrect username or password");
            doCallBack();
            }
        //calls data request method
        DataRequest(driver,formatType,DataExport);
        //download files, if true wait if there is a pending request, if no pending request = download latest available file.
        try{     
                Thread.sleep(300);
                driver.get("https://www.facebook.com/dyi/?tab=all_archives");
                Thread.sleep(500);}
                
        catch(InterruptedException e){
               System.out.println(e);
            }
        //Number of files read from FB description
        NumberOfFiles numF = new NumberOfFiles();
        NumberOfFiles numCurrentZ = new NumberOfFiles();
        //set current number of zip files in dl directory using setNumFiles
        numCurrentZ.setNumFiles(CurrentNumOfZipFiles());
        //stores the localfilepaths for the download in an array
        localFilePaths = new ArrayList<>();
        
        
        //if latest export, wait for pending status to finish and download the latest file instead of available ones(if any)
        if(LatestExport)
        {
            try{
                //wait until any download button is available
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label=Download]")));
                String status = driver.findElement(By.xpath("//div[@class='x6s0dn4 x78zum5 x13a6bvl'][1]")).getText();
                progressMonitor.setProgressText("Files pending for download, please wait");
                if("Pending".equals(status))
                    {
                        //Wait until pending disappears
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='x6s0dn4 x78zum5 x13a6bvl'][1]")));
                    Thread.sleep(600);
                    }
                }
                catch(Exception e){
                    errorList.add("No Pending files available for download");
                    doCallBack();
                }  
                //calls Datadownload method and set return as number of files
                numF.setNumFiles(DataDownload(driver));   
        }
        else{
            //calls Datadownload method and set return as number of files
                numF.setNumFiles(DataDownload(driver));
        }

        // more efficient code for normal mode instead of headless mode used by chrome driver
        // number of zip files expected = before download count + facebook number of files to download
        //int numberOfFilesToWait = numCurrentZ.getNumFiles()+numF.getNumFiles();
        //check if files are downloaded by comparing before and after number of zips
        //        try{
        //        await().during(1000, MILLISECONDS).atMost(300, MINUTES).untilAsserted(() -> Assert.assertEquals(numberOfFilesToWait, CurrentNumOfZipFiles()));
        //        }
        //        catch (Exception e){
        //             System.out.println("Download took too long/Request still pending, please try to download again some time later as the request may still be pending\n"+e);
        //        }

        //ensure paths in the array are unique only
        HashSet<String> uniqueLocalFilePaths = new HashSet(localFilePaths);
        List<String> uLocalFilePaths = new ArrayList<String>(uniqueLocalFilePaths);
         System.out.println(uLocalFilePaths + " added as Data Source");
        
        progressMonitor.setProgressText("Downloads complete");
        try{
            Thread.sleep(500);
        }
         catch(InterruptedException e){
               System.out.println(e);
            }
        
        errorList = new ArrayList<>();
        newDataSources = new ArrayList<>();
   
        LocalFilesDataSource newDataSource;
        try {
            progressMonitor.setProgressText("Adding files to data source");
            newDataSource = fileManager.addLocalFilesDataSource(UUID.randomUUID().toString(), newDataSourceName, "", host, uLocalFilePaths, new ProgressUpdater());
            newDataSources.add(newDataSource);
        } catch (TskCoreException | TskDataException ex) {
            errorList.add(Bundle.addOnlineDataTask_error_add_files_dataSources());
            logger.log(Level.SEVERE, Bundle.addOnlineDataTask_error_add_files_dataSources());
            hasCriticalError = true;                    
        }
        doCallBack();
    }
    //method to check current number of zipfiles in modDir
    private int CurrentNumOfZipFiles(){
           // Adding downloaded files as a Data Source
        File downloadFolder = new File(modDir);
        File[] listOfFiles = downloadFolder.listFiles();
        int fileCounter = 0;
        for (File file : listOfFiles) {
            //check if file is a zip file, if true, run condition
            if(isZipFile(file.getAbsolutePath()))
            {
                fileCounter++;
            }
        }
        return fileCounter;
    }
    
    // getter and setter for numfiles
    public class NumberOfFiles {
       private int numfiles;
       // Getter
       public int getNumFiles() {
         return numfiles;
       }
       // Setter
       public void setNumFiles(int i) {
         this.numfiles = i;
       }
     }
    
    private boolean isZipFile(String path){
         if (path == null)
            return false;
        try {
            new ZipFile(path).close();
            return true;
        } 
        catch(IOException e){
            return false;
        }   
    }
    //method for datadownload, support multiple and single downloads
     private int DataDownload(WebDriver driver){
        int noOfFiles = 0;
         try{       
                    progressMonitor.setProgressText("Initiating download of files");
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(120));
                    Thread.sleep(800);
                    String numFiles = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][4]")).getText();
                    System.out.println(numFiles);
                    String numFilesArray[] = numFiles.split(" ", 2);
                    int numFile = Integer.parseInt(numFilesArray[0]);
                    noOfFiles = numFile;
                    String fileDate = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][1]")).getText();
                    String fileFormat = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][2]")).getText();
                    String fileQuality = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][3]")).getText();
                    String fileExpiry = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][5]")).getText();
                    //display file request details that it is attempting to download from
                    progressMonitor.setProgressText("Downloading file(s) "+"\n"+fileFormat+"\n"+fileQuality+"\n"+numFiles+"\nDownload: "+fileExpiry);
                        for (int i = 1; i < numFile+1; i++) 
                        {
                            // click on the Download button as soon as the "Download" button is visible; else wait
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label=Download]")));
                            driver.findElement(By.cssSelector("[aria-label=Download]")).click();
                            //click on sub elements, if number of files exceed 1 means subelements are present in dropdownlist.
                            if(numFile > 1){
                                progressMonitor.setProgressText("Downloading file "+"("+ i +")\t" +"\n"+fileFormat+"\n"+fileQuality+"\n"+numFiles+"\nDownload: "+fileExpiry);
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='x1i10hfl xjbqb8w x6umtig x1b1mbwd xaqea5y xav7gou xe8uvvx x1hl2dhg xggy1nq x1o1ewxj "
                                        + "x3x9cwd x1e5q0jg x13rtm0m x87ps6o x1lku1pv x1a2a7pz xjyslct x9f619 x1ypdohk x78zum5 x1q0g3np x2lah0s x1w4qvff "
                                        + "x13mpval xdj266r xat24cr xz9dl7a x1sxyh0 xsag5q8 xurb0ha x1n2onr6 x16tdsg8 x1ja2u2z x6s0dn4']["+i+"]")));
                                driver.findElement(By.xpath("//div[@class='x1i10hfl xjbqb8w x6umtig x1b1mbwd xaqea5y xav7gou xe8uvvx x1hl2dhg xggy1nq x1o1ewxj "
                                        + "x3x9cwd x1e5q0jg x13rtm0m x87ps6o x1lku1pv x1a2a7pz xjyslct x9f619 x1ypdohk x78zum5 x1q0g3np x2lah0s x1w4qvff "
                                        + "x13mpval xdj266r xat24cr xz9dl7a x1sxyh0 xsag5q8 xurb0ha x1n2onr6 x16tdsg8 x1ja2u2z x6s0dn4']["+i+"]")).click();
                            }
                            Thread.sleep(500);
                            
                            //wait until 1 zip download is complete before resuming
                            try{
                            await().during(1000, MILLISECONDS).atMost(300, MINUTES).untilAsserted(() -> Assert.assertEquals(1, CurrentNumOfZipFiles()));
                            }
                            catch (Exception e){
                                 System.out.println("Download took too long/Request still pending, please try to download again some time later as the request may still be pending\n"+e);
                            }
                             // Adding downloaded files as a Data Source
                            File downloadFolder = new File(modDir);
                            File[] listOfFiles = downloadFolder.listFiles();
                                 for (File file : listOfFiles) 
                                 {
                                        //unzipping files from downloadfolder dir after all downloads are completed.
                                        UnZipFile uzfile = new UnZipFile();
                                        //check if its a zip file, if yes, unzip this file when done then del
                                        if(isZipFile(file.getAbsolutePath()))
                                        {
                                            progressMonitor.setProgressText("Unzipping downloaded files");
                                            try {uzfile.UnZipFile(file.getAbsolutePath());} 
                                            catch (IOException ex) {
                                                Exceptions.printStackTrace(ex);
                                            }
                                            //newDir without ext name for the unzipped file
                                            String newFileDir = file.getAbsolutePath().replaceAll("\\.\\w+","");
                                            //add unzipped files to localfilepaths list
                                            localFilePaths.add(newFileDir);
                                            //delete zip after unzipping
                                            file.delete();                                           
                                        }
                                    }
                            Thread.sleep(500);
                        }
                }
            catch(InterruptedException e){
              errorList.add("No  files available for download");
              doCallBack();
            }  
         return noOfFiles;
    }
     //method for data request
    private void DataRequest(WebDriver driver, Boolean formatType, Boolean dataExport){
        
         // explicit wait - to wait for the download button to be click-able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(120));
         if(dataExport){
            try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Format']")));
            WebElement format = driver.findElement(By.cssSelector("[aria-label='Format']"));
            format.click();
            //if true; export file as json, else html
            if(formatType)
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
