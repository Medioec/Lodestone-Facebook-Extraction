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

/**
 *
 * @author Alford
 */
public class SeleniumAnt {

    /**
     * @param args the command line arguments
     */
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
        ps.sendKeys("");
        WebElement login = driver.findElement(By.name("login"));
        login.click();
        
        try{
        Thread.sleep(5000);
        }
        catch(InterruptedException ie){
        }
        driver.findElement(By.cssSelector("[aria-label=Download]")).click();
         
    }
    
}
