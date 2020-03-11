/*

*/

package concep.selenium.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;


public class GenericSeleniumWebDriver {

    public Properties config;
    private WebDriver driver;
    protected String  browser;
    protected int     pageLoadTimeOut;
    public int        ajaxTimeOut;
    protected int     longTimeout;
    protected String  seleniumBrowserType;
    public int        timeOut;
    public int        negativeTimeOut;
    public String     url;

    public void init(
                      String configProperties ) throws FileNotFoundException, Exception {

        config = new Properties();
        config.load( new FileInputStream( configProperties ) );
        String seleniumServerHost = config.getProperty( "seleniumServerHost" );
        String seleniumBrowserType = config.getProperty( "seleniumBrowserType" );
        timeOut = Integer.parseInt( ( config.getProperty( "timeOut" ) ) );
        negativeTimeOut = Integer.parseInt( ( config.getProperty( "negativeTimeOut" ) ) );
        startSelenium( seleniumServerHost, seleniumBrowserType );
        pageLoadTimeOut = Integer.parseInt( config.getProperty( "pageLoadTimeOut" ) );
        ajaxTimeOut = Integer.parseInt( config.getProperty( "ajaxTimeOut" ) );
        browser = config.getProperty( "browser" );
    }

    public void open() {

        driver.get( url );
        waitForPageToLoad();
        driver.manage().window().maximize();

    }
    
    public void open(
                      String url ) {

        driver.get( url );
        waitForPageToLoad();
        driver.manage().window().maximize();

    }

    public void startSelenium(
                               String url,
                               String browser ) throws MalformedURLException {

        driver = getDriverInstance( url, browser );

    }
    
            
    public WebDriver getDriverInstance(
                                        String Url,
                                        String browser ) throws MalformedURLException {

        WebDriver driver = null;
        URL url = new URL( Url );
        if( browser.equals( "firefox" ) ) {
            DesiredCapabilities capability = DesiredCapabilities.firefox();
            driver = new RemoteWebDriver( url, capability );
        } else if( browser.equals( "chrome" ) ) {
            DesiredCapabilities capability = DesiredCapabilities.chrome();
            driver = new RemoteWebDriver( url, capability );
        } else if( browser.equals( "IE" ) ) {
            DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
            capability.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
            capability.setJavascriptEnabled(true);
            capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capability.setCapability(CapabilityType.ENABLE_PROFILING_CAPABILITY, true);
            capability.setCapability("enableElementCacheCleanup", false);
            capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driver = new RemoteWebDriver( url, capability );
        }
        return driver;
    }

    // webdriver overridden API methods

    public Select select(
                          String selectedValue ) {

        Select selected = new Select( driver.findElement( By.xpath( selectedValue ) ) );
        return selected;

    }

    public void hoverToElement(
                                String xpathLocator ) {

        try {
            Actions builder = new Actions( driver );
            builder.moveToElement( driver.findElement( By.xpath( xpathLocator ) ) ).build().perform();
        } catch( NoSuchElementException e ) {

        }
    }

    public String[] getDropDownOptions(
                                        String xpathLocator ) {

        ArrayList<String> dropDownMenu = new ArrayList<String>();
        Select dropDown = select( xpathLocator );
        List<WebElement> options = dropDown.getOptions();
        for( WebElement elements : options ) {
            dropDownMenu.add( elements.getText() );
        }
        String[] values = new String[dropDownMenu.size()];
        values = dropDownMenu.toArray( values );
        return values;
    }

    public void dragAndDrop(
                             String from,
                             String to ) {

        try {

            WebElement From = driver.findElement( By.xpath( from ) );
            WebElement To = driver.findElement( By.xpath( to ) );
            Actions builder = new Actions( driver );
            Action dragThenDrop = builder.clickAndHold( From ).moveToElement( To ).release( To ).build();
            dragThenDrop.perform();

        } catch( NoSuchElementException e ) {}

    }

    public void select2DropDown(
                                 String dropDown,
                                 String searchInput,
                                 String element ) {

        for( int second = 0;; second++ ) {
            if( second >= 10 ) {
                break;
            } else {
                try {
                    click( dropDown, timeOut );
                    if( driver.findElement( By.xpath( searchInput ) ).isDisplayed() ) {
                        break;
                    }
                } catch( Exception e ) {}

                try {
                    Thread.sleep( 500 );
                } catch( InterruptedException e ) {}
            }
        }
        type( searchInput, element, timeOut );
        pressKey( searchInput, Keys.ENTER, timeOut );
    }
    
    public void select2DropDown(String dropdownContainer, String dropdownInput, String searchValue, String listItem) throws InterruptedException {
    	
		click(dropdownContainer, timeOut);
		
		type(dropdownInput, searchValue, timeOut );			
		
		waitUntilElementIsLocated(listItem, timeOut);
		waitUntilElementIsClickable(listItem);

		click(listItem, timeOut);
    }

    public void click(
                       String xpathLocator,
                       int timeOut ) {

    	try {
            findElements( xpathLocator, timeOut );
            driver.findElement( By.xpath( xpathLocator ) ).click();
        } catch( NoSuchElementException e ) {
            e.getStackTrace();
        }

    }

    public void clickAndVerify(
                                String element,
                                String verifiedElement ) {

        int i = 0;
        int maxIteration = 20;

        do {
            click( element, timeOut );
            waitForPageToLoad();
            i++;
        } while( ! ( isElementPresent( verifiedElement, 1 ) || i < maxIteration ) );
    }

    public void clear(
                       String xpathLocator ) {

        try {
            driver.findElement( By.xpath( xpathLocator ) ).clear();
        } catch( NoSuchElementException e ) {

        }
    }
    
 
    public void type(
                      String xpathLocator,
                      String text,
                      int timeOut ) {

        try {
            findElements( xpathLocator, timeOut );
            driver.findElement( By.xpath( xpathLocator ) ).sendKeys( text );
        } catch( NoSuchElementException e ) {

        }
    }

    public boolean isElementPresent(
                                     String xpathElement,
                                     int timeOut ) {

        boolean found = true;
        try {

            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( xpathElement ) ) );
        } catch( NoSuchElementException | TimeoutException | ScreenshotException e ) {
            found = false;
        }
        return found;
    }

    public boolean waitTillIframeIsVisable(
                                            String iframe,
                                            int timeOut ) {

        boolean found = true;
        try {

            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( iframe ) );
        } catch( NoSuchElementException | TimeoutException | ScreenshotException e ) {
            found = false;
        }
        return found;
    }

    public boolean waitTillIframeIsVisable(
                                            String iframe,
                                            String iframe2,
                                            int timeOut ) {

        boolean found = true;
        try {

            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( iframe ) );
            wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt( iframe2 ) );
        } catch( NoSuchElementException | TimeoutException | ScreenshotException e ) {
            found = false;
        }
        return found;
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

    public boolean isCheckBoxChecked(
                                      String checkBoxName ) {

        boolean checked = ( boolean ) ( ( JavascriptExecutor ) driver ).executeScript( " function isChecked(searchedItem) { var surveyinnerId = document.getElementById('surveysListsInnerContainer'),labels = surveyinnerId.getElementsByTagName('label'),checked = [];for (var i = 0; i < labels.length; i++) { var input = labels[i].getElementsByTagName('input'), span = labels[i].getElementsByTagName('span'); input[0].checked && span[0].innerHTML == searchedItem ? checked.push(input[i]) : false; }return checked.length != 0;} return isChecked('"
                                                                                       + checkBoxName + "');" );

        return checked;
    }

    public boolean isClickable(
                                String xpathlocator,
                                int timeOut ) {

        WebDriverWait wait = new WebDriverWait( driver, timeOut );

        try {
            wait.until( ExpectedConditions.elementToBeClickable( By.xpath( xpathlocator ) ) );
            return true;
        } catch( TimeoutException e ) {
            return false;
        }

    }

    public boolean isChecked(
                              String xpathLocator ) {

        return driver.findElement( By.xpath( xpathLocator ) ).isSelected();
    }

    public boolean isSelected(
                               String xpathLocator,
                               String value ) {

        try {
            Select dropDown = select( xpathLocator );
            String selectedValue = dropDown.getFirstSelectedOption().getText();
            if( selectedValue.equals( value ) ) {
                return true;
            }
        } catch( NoSuchElementException e ) {
            System.out.println( "element '" + xpathLocator + "' could not be found" );
            return false;
        }
        return false;

    }

    //for the hidden drop down menus
    public boolean isValueSelected(
                                    String css3,
                                    String value ) {

        String selectedValue = getSelectValue( css3 );
        if( selectedValue.equals( value ) ) {
            return true;
        } else {
            return false;
        }
    }

    public String getSelectValue(
                                  String css3 ) {

        String selectedValue = "";
        try {
            selectedValue = ( String ) ( ( JavascriptExecutor ) driver ).executeScript( "return $('"
                                                                                        + css3
                                                                                        + "').clone().children().remove().end().text()" );

        } catch( Exception e ) {

        }
        return selectedValue;
    }

    public void findElements(
                              String xpath,
                              int timeOut ) {

        try {
            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( xpath ) ) );

        } catch( Exception e ) {

            throw e;
        }

    }

    public void switchToIframe(
                                String iframeXpath ) {

        try {

            driver.switchTo().frame( driver.findElement( By.xpath( iframeXpath ) ) );

        } catch( NoSuchFrameException e ) {

        }
    }

    public void switchToIframeByID(
                                    String iframeID ) {

        try {

            driver.switchTo().frame( iframeID );

        } catch( NoSuchFrameException e ) {

        }

    }

    public void navigate(
                          String url ) {

        driver.navigate().to( url );
        driver.manage().window().maximize();

    }

    public void refreshPage() {

        driver.navigate().refresh();
    }

    public void switchToTopWindow() {

        driver.switchTo().defaultContent();
    }

    public boolean waitForAjaxToComplete(
                                          int timeOut ) {

        try {
            new WebDriverWait( driver, timeOut ) {}.until( new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(
                                      WebDriver driverObject ) {

                    return ( Boolean ) ( ( JavascriptExecutor ) driverObject ).executeScript( "return jQuery.active == 0" );
                }
            } );
            return true;
        } catch( Exception e ) {
            System.out.println( "there were no JS to wait for" );;
            return false;
        }
    }

    public void waitForPageToLoad() {

        driver.manage().timeouts().pageLoadTimeout( pageLoadTimeOut, TimeUnit.SECONDS );

    }

    //Implicit wait time out must be set to 0 when you use it so it won't confuse with the wait-untill method
    public void nullifyImplicitWait() {

        driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS ); //nullify implicitlyWait() 
    }

    //For implicitly using the wait method 
    public void setImplicitWait(
                                 int waitTime_InSeconds ) {

        driver.manage().timeouts().implicitlyWait( waitTime_InSeconds, TimeUnit.SECONDS );
    }

    public void stopSelenium() {

        driver.close();
        driver.quit();
    }

    public String getTimestamp() {

        return ( Long.toString( new Date().getTime() / 1000 ) );
    }
    
    public String getDateTime(String dateTimeFormat) {
        
        Date dateTime = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateTimeFormat);
       
        return simpleFormat.format(dateTime);
    }

    public boolean isTextPresent(
                                  String xpathLocator,
                                  String text,
                                  int timeOut ) {

        try {
            setImplicitWait( 10 );
            if( driver.findElement( By.xpath( xpathLocator ) ).getText().contains( text ) )
                ;
            nullifyImplicitWait();
            return true;

        } catch( NullPointerException e ) {
            nullifyImplicitWait();
            return false;
        }
    }  

    public boolean waitForTextTobePresent(
                                           String xpathLocator,
                                           String text,
                                           int timeOut ) {

        boolean found = true;
        try {

            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.textToBePresentInElement( driver.findElement( By.xpath( xpathLocator ) ),
                                                                     text ) );

        } catch( TimeoutException e ) {
            e.printStackTrace();
            found = false;
        }
        return found;
    }

    public void close() {

        driver.close();
    }

    public boolean waitUntilElementIsLocated(
                                              String xpathLocator,
                                              int timeOut ) {

        boolean found = true;
        try {
            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( xpathLocator ) ) );
        } catch( TimeoutException e ) {
            e.printStackTrace();
            found = false;
        }
        return found;
    }

    public String getCurrentUrl() {

        String currentUrl;
        currentUrl = driver.getCurrentUrl();
        System.out.println( currentUrl );
        return currentUrl;
    }

    public boolean waitUntilElementIsClickable(
                                                String xpath ) throws InterruptedException {

        for( int i = 0;; i++ ) {
            if( i >= timeOut )
                return ( false );
            try {

                isClickable( xpath, timeOut );
                return ( true );
            } catch( Exception e ) {}
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

    public void pressKey(
                          String xpathLocator,
                          Keys key,
                          int timeOut ) {

        try {
            findElements( xpathLocator, timeOut );
            driver.findElement( By.xpath( xpathLocator ) ).sendKeys( key );
        } catch( NoSuchElementException e ) {

        }

    }

    public void clickHiddenElement(
                                    String className,
                                    String title,
                                    int timeOut ) {

        try {

            ( ( JavascriptExecutor ) driver ).executeScript( "var p = document.getElementsByClassName('"
                                                             + className
                                                             + "');for (var i = 0; i < p.length; i++) {if (p[i].getAttribute('title') == '"
                                                             + title + "') {p[i].click();}}" );

        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void showHiddenElement(
                                   String id ) {

        try {
            ( ( JavascriptExecutor ) driver ).executeScript( "$('#" + id + "').show()" );
        } catch( Exception e ) {
            System.out.println( "Show Hidden element is not working" );;
        }
    }

    public void hideShowenElement(
                                   String id ) {

        try {
            ( ( JavascriptExecutor ) driver ).executeScript( "$('#" + id + "').hide()" );
        } catch( Exception e ) {
            e.printStackTrace();
            throw e;
        }
    }

    public void checkAlert(
                            int timeOut ) {

        try {
            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.alertIsPresent() );
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch( Exception e ) {

        }
    }

    public String getInputFieldValue(
                                      String xPath ) {

        try {

            return driver.findElement( By.xpath( xPath ) ).getAttribute( "value" );

        } catch( Exception e ) {

            e.printStackTrace();
            throw e;
        }
    }

    public String getInnerText(
                                String xpath ) {

        try {
            return driver.findElement( By.xpath( xpath ) ).getText();
        } catch( Exception e ) {

            e.printStackTrace();
            throw e;
        }
    }

    public String getSelectedValueText(
                                        String xPath ) {

        try {

            Select dropdown = new Select( driver.findElement( By.xpath( xPath ) ) );

            return dropdown.getFirstSelectedOption().getText();

        } catch( Exception e ) {

            e.printStackTrace();
            throw e;
        }
    }

    public void browserInteract(
                                 String command ) {

        try {

            switch( command ){

                case "Forward":
                    driver.navigate().forward();
                    break;
                case "Back":
                    driver.navigate().back();
                    break;
                case "Refresh":
                    driver.navigate().refresh();
                    break;

                default:
                    throw new InvalidParameterException( "Not valid parameter: " + command );
            }

        } catch( Exception e ) {

            e.printStackTrace();
            throw e;
        }
    }
     			
     public void pressEnter() {
    	 
    				 Actions action = new Actions(this.driver);
    				 action.sendKeys(Keys.ENTER).perform();	
    			}

	public GenericSeleniumWebDriver findElements(String xpath) {
		try {
            WebDriverWait wait = new WebDriverWait( driver, timeOut );
            wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( xpath ) ) );

        } catch( Exception e ) {

            throw e;
        }
		return this;
	}
}