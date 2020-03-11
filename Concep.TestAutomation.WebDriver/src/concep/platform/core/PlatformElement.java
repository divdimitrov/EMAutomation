package concep.platform.core;

import org.openqa.selenium.WebElement;

import concep.seleniumWD.core.SeleniumDriverCore;

public class PlatformElement {

    protected SeleniumDriverCore driver;

    public PlatformElement( SeleniumDriverCore driver ) {
        this.driver = driver;
    }
    
    public WebElement systemsPageTab() {
		return driver.elementFinder.findElementByXpath("//ul[@class='nav navbar-nav']/li/a[text()[contains(., 'Systems')]]");
	}

	public WebElement alertPageTab() {
		return driver.elementFinder.findElementByXpath("//ul[@class='nav navbar-nav']/li/a[text()[contains(., 'Alert')]]");
	}
	
	public WebElement schedulesPageTab() {
		return driver.elementFinder.findElementByXpath("//ul[@class='nav navbar-nav']/li/a[text()[contains(., 'Schedules')]]");
	}

	public WebElement logsPageTab() {
		return driver.elementFinder.findElementByXpath("//ul[@class='nav navbar-nav']/li/a[text()[contains(., 'Logs')]]");
	}
	
    public WebElement addNewRecord() {
    	return driver.elementFinder.findElementByXpath("//th/i[@title='Add new record' and @data-is-disabled='false']");
    }
    
    public WebElement addSubgridRecord() {
    	return driver.elementFinder.findElementByXpath("//div[@name='modal-body']//th/i[@title='Add new record']");
    }
    
    public WebElement saveRecord() {
    	return driver.elementFinder.findElementByXpath("//td//div[@class='editable-controls form-group']//td//button[@name='save'] | //td//form[contains(@class, 'form-buttons') and not(contains(@class, 'ng-hide'))]//button[@name='save']");
    }

    public WebElement saveSubgridRecord() {
    	return driver.elementFinder.findElementByXpath("//div[@name='modal-body']//button[@name='save']");
    }
}
