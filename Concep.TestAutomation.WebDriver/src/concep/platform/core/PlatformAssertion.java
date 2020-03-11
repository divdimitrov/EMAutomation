package concep.platform.core;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import concep.Utilities.LogResults;
import concep.seleniumWD.core.SeleniumDriverCore;

public class PlatformAssertion {
	
    protected SeleniumDriverCore driver;
    protected LogResults log;

    public PlatformAssertion( SeleniumDriverCore driver ) {
        this.driver = driver;
        this.log = new LogResults();
    }

    public void verifyElements(
                                HashMap<String, WebElement> elements ) {

        for( Map.Entry<String, WebElement> element : elements.entrySet() ) {
            log.resultStep( "Verify that " + element.getKey() + " elements is successfully displayed" );
            log.endStep( driver.assertion.isElementPresent( element.getValue(),
                                                            driver.waits.elementTimeOut ) );
        }
    }
}
