/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package selenium.ant;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

/**
 *
 * @author Alford
 */
public class SeleniumAnt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Alford\\Documents\\NetBeansProjects\\iOSDeviceDataExtractor\\chromedriver.exe");           
        WebDriver driver = new ChromeDriver(); 
        driver.get("http://www.google.com/");    
    }
    
}
