package selenium.ant;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import javax.swing.JPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.*;

public class SeleniumAnt {
    
    public static void main(String[] args) {
        
       //Create a instance of ChromeOptions class
        ChromeOptions options = new ChromeOptions();
        //Add chrome switch to disable notification - "**--disable-notifications**"
        options.addArguments("--disable-notifications");
        //Set path for driver exe  
        System.setProperty("webdriver.chrome.driver","A:\\Users\\JJ\\Documents\\GitHub\\Lodestone\\chromedriver.exe");
        //Pass ChromeOptions instance to ChromeDriver Constructor
        WebDriver driver = new ChromeDriver(options);   
        // explicit wait - to wait for the download button to be click-able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(120));
        driver.get("https://www.facebook.com/dyi/?referrer=yfi_settings");
        WebElement fb = driver.findElement(By.name("email"));
        fb.sendKeys("destinyblazer@live.com");
        WebElement ps = driver.findElement(By.name("pass"));
        //input ur own password and username
        ps.sendKeys("passw0rdfree");
        WebElement login = driver.findElement(By.name("login"));
        login.click();
        
        try{
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Format']")));
        WebElement format = driver.findElement(By.cssSelector("[aria-label='Format']"));
        format.click();
        format.sendKeys("j");
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
//        try{
//        Thread.sleep(500);
//        }
//        catch(InterruptedException ie){
//        }    
        driver.get("https://www.facebook.com/dyi/?tab=all_archives");
        
        // click on the Download button as soon as the "Download" button is visible; else wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label=Download]")));
        
        String numFiles = driver.findElement(By.xpath("//span[@class='x193iq5w xeuugli x13faqbe x1vvkbs x1xmvt09 x1nxh6w3 x1sibtaa xo1l8bm xi81zsa'][4]")).getText();
        System.out.println(numFiles);
        
        String numFilesArray[] = numFiles.split(" ", 2);
        int numFile = Integer.parseInt(numFilesArray[0]); 
        
        //to download number of files based on download information given   
        try{
        for (int i = 1; i < numFile+1; i++) {     
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
}