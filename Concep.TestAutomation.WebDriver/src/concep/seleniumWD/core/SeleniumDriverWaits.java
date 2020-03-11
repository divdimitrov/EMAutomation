package concep.seleniumWD.core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDriverWaits {

    private RemoteWebDriver driver;

    public int              pageLoadTimeOut;
    public int              ajaxTimeOut;
    public int              elementTimeOut;

    public SeleniumDriverWaits( RemoteWebDriver driver, WaitValue waitValue ) {
    	this.driver = driver;
    	this.pageLoadTimeOut=waitValue.getPageLoadTimeOut();
    	this.elementTimeOut=waitValue.getElementTimeOut();
    	this.ajaxTimeOut=waitValue.getAjaxTimeOut();
        
    }

    public void waitForElementTobePresentByXpath(
                                                  String element,
                                                  int timeOut ) {

        try {
            WebDriverWait wait = new WebDriverWait( this.driver, timeOut );
            wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( element ) ) );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void waitForElementTobePresentByXpath(
                                                  String xpathLocator ) {

        waitForElementTobePresentByXpath( xpathLocator, this.elementTimeOut );
    }

    public void waitForElementTobePresentByID(
                                               String element,
                                               int timeOut ) {

        try {
            WebDriverWait wait = new WebDriverWait( this.driver, timeOut );
            wait.until( ExpectedConditions.presenceOfElementLocated( By.id( element ) ) );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void waitForElementTobePresentByID(
                                               String xpathLocator ) {

        waitForElementTobePresentByID( xpathLocator, this.elementTimeOut );
    }

    public void waitForElementTobeVisable(
                                           WebElement element,
                                           int timeOut ) {

        try {
            WebDriverWait wait = new WebDriverWait( this.driver, timeOut );
            wait.until( ExpectedConditions.visibilityOf( element ) );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }

    }

    public void waitForElementTobeVisable(
                                           WebElement element ) {

        waitForElementTobeVisable( element, this.elementTimeOut );
    }

    public void waiteTillElementIsClickable(
                                             WebElement element,
                                             int timeOut ) {

        try {
            WebDriverWait wait = new WebDriverWait( this.driver, timeOut );
            wait.until( ExpectedConditions.elementToBeClickable( element ) );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void waitforAlertToBePresent(
                                         int timeOut ) {

        try {
            WebDriverWait wait = new WebDriverWait( this.driver, timeOut );
            wait.until( ExpectedConditions.alertIsPresent() );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void waiteTillElementIsClickable(
                                             WebElement element ) {

        waiteTillElementIsClickable( element, this.elementTimeOut );
    }

    public void waitForAjaxToComplete(
                                       int timeOut ) {

        try {
            new WebDriverWait( driver, timeOut ) {}.until( new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(
                                      WebDriver driverObject ) {

                    return ( Boolean ) ( ( JavascriptExecutor ) driverObject ).executeScript( "return jQuery.active == 0" );
                }
            } );
        } catch( Exception e ) {
            System.out.println( "there were no JS to wait for" );;
        }
    }

    public void waitForAjaxToComplete() {

        waitForAjaxToComplete( this.ajaxTimeOut );
    }

    public void waitTillIframeIsVisable(
                                         WebElement iframe,
                                         WebElement iframe2,
                                         int timeOut ) {

        try {

            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( iframe ) );
            wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( iframe2 ) );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void waitForTextTobePresent(
                                        WebElement xpathLocator,
                                        String text,
                                        int timeOut ) {

        try {

            WebDriverWait wait = new WebDriverWait( this.driver, timeOut );
            wait.until( ExpectedConditions.textToBePresentInElement( xpathLocator, text ) );

        } catch( TimeoutException e ) {
            e.printStackTrace();
        }
    }

    public void waitForTextTobePresent(
                                        WebElement xpathLocator,
                                        String text ) {

        waitForTextTobePresent( xpathLocator, text, this.elementTimeOut );
    }

    public void waitForPageToLoad(
                                   int timeOut ) {

        this.driver.manage().timeouts().pageLoadTimeout( timeOut, TimeUnit.SECONDS );
    }

    public void waitForPageToLoad() {

        waitForPageToLoad( this.pageLoadTimeOut );
    }

    public void waitTillIframeIsVisable(
                                         WebElement iframe,
                                         int timeOut ) {

        try {

            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( iframe ) );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean waitUntilElementIsClickableCustom(
                                                      WebElement element,
                                                      int timeOut ) throws InterruptedException {

        for( int i = 0;; i++ ) {
            if( i >= timeOut )
                return ( false );
            try {
                waiteTillElementIsClickable( element, timeOut );
                return ( true );
            } catch( Exception e ) {}
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

    //Implicit wait time out must be set to 0 when you use it so it won't confuse with the wait-untill method
    public void nullifyImplicitWait() {

        this.driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS ); //nullify implicitlyWait() 
    }

    //For implicitly using the wait method 
    public void setImplicitWait(
                                 int waitTime_InSeconds ) {

        this.driver.manage().timeouts().implicitlyWait( waitTime_InSeconds, TimeUnit.SECONDS );
    }

}
