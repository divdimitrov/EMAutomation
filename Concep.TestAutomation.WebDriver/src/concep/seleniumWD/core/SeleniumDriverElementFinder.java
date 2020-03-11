package concep.seleniumWD.core;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumDriverElementFinder {

    public String     xpathString;
    public String idString;
    private RemoteWebDriver driver;

    public SeleniumDriverElementFinder( RemoteWebDriver driver ) {
        this.driver = driver;
    }


    public WebElement findElementByXpath(
                                          String element ) {

            this.xpathString= element ;
            try{
            return this.driver.findElement( By.xpath( element ) );
            }catch(Exception e){
            	e.printStackTrace();
            	return null;
            }
        
    }

    public WebElement fineElementByCSS(
                                        String element ) {

    	this.xpathString="This is not Xpath";
        try {
            return this.driver.findElement( By.cssSelector( element ) );
        } catch( Exception e ) {
        	e.printStackTrace();
        	return null;
        }
    }

    public WebElement findElementByID(
                                       String element ) {

    	this.idString=element;
        try {
            return this.driver.findElement( By.id( element ) );
        } catch( Exception e ) {
        	e.printStackTrace();
        	return null;
        }
    }

    public List<WebElement> findAll(
                                     String element ) {

    	this.xpathString="This is not Xpath";
        try {
            return this.driver.findElements( By.xpath( element ) );
        } catch( Exception e ) {
        	e.printStackTrace();
        	return null;
        }
    }
}
