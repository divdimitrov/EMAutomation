/*

 */

package concep.seleniumWD.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumDriverCore {

    protected RemoteWebDriver          driver;
    public SeleniumDriverBrowser       browser;
    public SeleniumDriverAssertion     assertion;
    public SeleniumDriverElementFinder elementFinder;
    public SeleniumDriverWaits         waits;
    public WaitValue                   waitValue;
    private Properties                 config;
    private String                     appConfig = "Config/wdConfig";

    protected int                      eleTime;

    public SeleniumDriverCore() {

        this.config = new Properties();
        this.driver = lunchDriver( this.appConfig );
        this.browser = new SeleniumDriverBrowser( this.driver );
        this.assertion = new SeleniumDriverAssertion( this.driver, this.waitValue );
        this.elementFinder = new SeleniumDriverElementFinder( this.driver );
    }

    public RemoteWebDriver lunchDriver(
                                        String configLocation ) {

        try {
            this.config.load( new FileInputStream( configLocation ) );
            String browser = this.config.getProperty( "browser" );
            String urlHost = this.config.getProperty( "urlHost" );

            switch( browser ){
                case "fireFox":
                    this.lunchDriver( urlHost, Browsers.FireFox );
                    break;
                case "chrome":
                    this.lunchDriver( urlHost, Browsers.Chrome );
                    break;
                case "ie":
                    this.lunchDriver( urlHost, Browsers.IE );
                    break;
                default:
                    System.out.println( "please select the right browser!" );
                    break;
            }

        } catch( IOException e ) {
            e.printStackTrace();
        }

        return this.driver;
    }

    public RemoteWebDriver lunchDriver(
                                        String url,
                                        Browsers browser ) throws MalformedURLException {

        try {
            URL _url = new URL( url );
            switch( browser ){
                case FireFox:
                    DesiredCapabilities capability = DesiredCapabilities.firefox();
                    this.driver = new RemoteWebDriver( _url, capability );
                    break;
                case Chrome:
                    DesiredCapabilities capabilityCh = DesiredCapabilities.chrome();
                    this.driver = new RemoteWebDriver( _url, capabilityCh );
                    break;
                case IE:
                    DesiredCapabilities capabilityIe = DesiredCapabilities.internetExplorer();
                    capabilityIe.setCapability( CapabilityType.SUPPORTS_JAVASCRIPT, true );
                    capabilityIe.setJavascriptEnabled( true );
                    capabilityIe.setCapability( CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
                                                true );
                    capabilityIe.setCapability( CapabilityType.ENABLE_PROFILING_CAPABILITY, true );
                    capabilityIe.setCapability( "enableElementCacheCleanup", false );
                    capabilityIe.setCapability( InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                                                true );
                    this.driver = new RemoteWebDriver( _url, capabilityIe );
                    break;
                default:
                    throw new InvalidParameterException( "Not valid parameter" );
            }

        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
        return this.driver;
    }

    // webdriver overridden API methods

    public Select select(
                          WebElement element ) {

        try {

            Select selected = new Select( element );
            return selected;
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }

    }

    public void selectSearchedValueByXpath(
                                            WebElement menu,
                                            WebElement txtField,
                                            String value ) {

        this.click( menu );
        this.type( txtField, value );
        this.pressKey( txtField, Keys.ENTER );

    }

    public List<String> getDropDownListOptions(
                                                WebElement menu ) {

        ArrayList<String> dropDownMenu = new ArrayList<String>();
        List<WebElement> menuOptions = this.select( menu ).getOptions();
        for( WebElement elements : menuOptions ) {
            dropDownMenu.add( elements.getText() );
        }
        return dropDownMenu;
    }

    public void pressKey(
                          WebElement locator,
                          Keys key ) {

        try {
            locator.sendKeys( key );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }

    }

    public void hoverToElement(
                                WebElement element ) {

        try {
            Actions builder = new Actions( this.driver );
            builder.moveToElement( element ).build().perform();
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public String[] getDropDownOptions(
                                        WebElement elementr ) {

        ArrayList<String> dropDownMenu = new ArrayList<String>();
        Select dropDown = select( elementr );
        List<WebElement> options = dropDown.getOptions();
        for( WebElement elements : options ) {
            dropDownMenu.add( elements.getText() );
        }
        String[] values = new String[dropDownMenu.size()];
        values = dropDownMenu.toArray( values );
        return values;
    }

    public void dragAndDrop(
                             WebElement from,
                             WebElement to ) {

        try {

            Actions builder = new Actions( this.driver );
            Action dragThenDrop = builder.clickAndHold( from ).moveToElement( to ).release( to ).build();
            dragThenDrop.perform();

        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }

    }

    public void click(
                       WebElement element,
                       BY by ) {

        try {
            if( element != null ) {
                switch( by ){
                    case Xpath:
                        this.waits.waitForElementTobePresentByXpath( elementFinder.xpathString );
                        break;
                    case Id:
                        this.waits.waitForElementTobePresentByID( elementFinder.idString );
                        break;
                    default:
                        System.out.println( "please enter a valid Id" );
                        break;
                }
            }
            element.click();
        } catch( Exception e ) {
            e.getStackTrace();
            throw e;
        }
    }

    public void click(
                       WebElement element ) {

        this.click( element, BY.Xpath );

    }

    public void clickStaleElement(
                                   WebElement element ) {

        for( int i = 0; i < 5; i++ ) {
            try {
                element.click();
                break;
            } catch( Exception e ) {}
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                System.out.println( "click '" + element + "' try number:'" + i + "'" );
            }
        }
    }

    public void clear(
                       WebElement element ) {

        try {
            element.clear();
        } catch( NoSuchElementException e ) {

        }
    }

    public void type(
                      WebElement element,
                      String text ) {

        try {
            this.waits.waitForElementTobeVisable( element );
            this.clear( element );
            element.sendKeys( text );
        } catch( Exception e ) {
            e.getStackTrace();
        }
    }

    public void selectWindow() {

        try {

            for( String newWindow : driver.getWindowHandles() ) {
                driver.switchTo().window( newWindow );
            }
        } catch( Exception e ) {
            System.out.println( "The requested window is not valid" );
        }

    }

    public void switchToIframe(
                                WebElement iframeXpath ) {

        try {

            driver.switchTo().frame( iframeXpath );

        } catch( NoSuchFrameException e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void switchToIframeByID(
                                    String iframeID ) {

        try {

            driver.switchTo().frame( iframeID );

        } catch( NoSuchFrameException e ) {
            e.printStackTrace();
            throw e;
        }

    }

    public void switchToTopWindow() {

        driver.switchTo().defaultContent();
    }

    public void quit() {

        this.driver.quit();

    }

    public void clickHiddenElement(
                                    String className,
                                    String title ) {

        try {
            ( ( JavascriptExecutor ) this.driver ).executeScript( "var p = document.getElementsByClassName('"
                                                                  + className
                                                                  + "');for (var i = 0; i < p.length; i++) {if (p[i].getAttribute('title') == '"
                                                                  + title + "') {p[i].click();}}" );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void showHiddenElementByID(
                                       String id ) {

        try {
            ( ( JavascriptExecutor ) this.driver ).executeScript( "$('#" + id + "').show()" );
        } catch( Exception e ) {
            System.out.println( "Show Hidden element is not working" );;
        }
    }

    public void hideShowenElementByID(
                                       String id ) {

        try {
            ( ( JavascriptExecutor ) this.driver ).executeScript( "$('#" + id + "').hide()" );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void checkAlert(
                            int timeOut ) {

        try {
            waits.waitforAlertToBePresent( timeOut );
            Alert alert = this.driver.switchTo().alert();
            alert.accept();
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void checkAlert() {

        this.checkAlert( waits.elementTimeOut );
    }

    public String getInputFieldValue(
                                      WebElement element ) {

        try {

            return element.getAttribute( "value" );

        } catch( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public String getInnerText(
                                WebElement element ) {

        try {
            return element.getText();
        } catch( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSelectedValueText(
                                        WebElement element ) {

        try {

            Select dropdown = new Select( element );

            return dropdown.getFirstSelectedOption().getText();

        } catch( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}