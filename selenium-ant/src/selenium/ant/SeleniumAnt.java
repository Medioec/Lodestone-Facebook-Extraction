/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
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
      
        driver.get("https://www.facebook.com/dyi/?referrer=yfi_settings");
        WebElement fb = driver.findElement(By.name("email"));
        fb.sendKeys("destinyblazer@live.com");
        WebElement ps = driver.findElement(By.name("pass"));
        //input ur own password and username
        ps.sendKeys("p@ssw0rd");
        WebElement login = driver.findElement(By.name("login"));
        login.click();

        try{
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
        try{
        Thread.sleep(1000);
        }
        catch(InterruptedException ie){
        }
         
        driver.get("https://www.facebook.com/dyi/?tab=all_archives");
        // explicit wait - to wait for the download button to be click-able
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(120));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label=Download]")));
        // click on the Download button as soon as the "Download" button is visible
        driver.findElement(By.cssSelector("[aria-label=Download]")).click();
        
        List<WebElement> inputElements = driver.findElements(By.xpath("//x1uvtmcs.x4k7w5x.x1h91t0o.x1beo9mf.xaigb6o.x12ejxvf.x3igimt.xarpa2k.xedcshv.x1lytzrv.x1t2pt76.x7ja8zs.x1n2onr6.x1qrby5j.x1jfb8zj"));

	for (WebElement ml : inputElements) {
	if (ml.getAttribute("tabIndex") != null) {
				System.out.println("Tab order is " + ml.getAttribute("tabIndex"));
    }
                }
    
    
    
}
}