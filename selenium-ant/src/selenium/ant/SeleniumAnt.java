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
/**
 *
 * @author Alford
 */
public class SeleniumAnt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               
        System.setProperty("webdriver.chrome.driver","A:\\Users\\JJ\\Documents\\GitHub\\Lodestone\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
      
        driver.get("https://www.facebook.com/dyi/?referrer=yfi_settings");
        WebElement fb = driver.findElement(By.name("email"));
        fb.sendKeys("destinyblazer@live.com");
        WebElement ps = driver.findElement(By.name("pass"));
        //input ur own password and username
        ps.sendKeys("p@ssw0rd");
        WebElement login = driver.findElement(By.name("login"));
        login.click();
        
        try{
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("[aria-label=Download]")).click();
        }
        catch(InterruptedException ie){
        }
         
    }
    
}
