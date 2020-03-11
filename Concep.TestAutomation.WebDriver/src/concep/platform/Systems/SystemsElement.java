package concep.platform.Systems;

import org.openqa.selenium.WebElement;

import concep.platform.core.PlatformElement;
import concep.seleniumWD.core.SeleniumDriverCore;

public class SystemsElement extends PlatformElement {

    public SystemsElement( SeleniumDriverCore driver ) {
        super( driver );
    }

	public WebElement systemTab() {
		return driver.elementFinder.findElementByXpath("//div[@class='sidebar-inner']//li/a[contains(text(), 'Systems')]");
	}

	public WebElement systemUserTab() {
		return driver.elementFinder.findElementByXpath("//div[@class='sidebar-inner']//li/a[contains(text(), 'System users')]");
	}
	
	public WebElement sendIntegratedUsersTab() {
		return driver.elementFinder.findElementByXpath("//div[@class='sidebar-inner']//li/a[contains(text(), 'Send integrated users')]");
	}

	public WebElement connectionTab() {
		return driver.elementFinder.findElementByXpath("//div[@class='sidebar-inner']//li/a[contains(text(), 'Connections')]");
	}
	
	// Systems page elements xPaths
    public final String extSystemPageTitle         = "Review, add and edit client system";
    
    public WebElement extSystemName() {
    	return driver.elementFinder.findElementByXpath("//td//input[@name='Name']");
    }
    
    public WebElement extSystemType() {
    	return driver.elementFinder.findElementByXpath("//td//select[@name='ProviderType']");
    }
    
    public WebElement extSystemForType()   {
    	return driver.elementFinder.findElementByXpath("//td//select[@name='ProviderTypeAsString']");
    }
    
    public WebElement extSystemAuthentication(){
    	return driver.elementFinder.findElementByXpath("//td//select[@name='AuthenticationTypeId']");
    }
    
    public WebElement extSystemAddress()    {
    	return driver.elementFinder.findElementByXpath("//tr/td//input[@name='Address']");
    }
    
    protected String getDropdownOption(
    		WebElement xPathDropdown,
            String optionText ) {

    	return xPathDropdown + "/option[contains(text(), '" + optionText + "')]";
	}
    
    protected WebElement systemRecordNameValue(String systemName) {
    	
    	return driver.elementFinder.findElementByXpath("//td//span[contains(text(), '" + systemName + "')]");
    }
    
    protected WebElement systemRecordTypeValue(String systemName, String systemType) {
    	
    	return driver.elementFinder.findElementByXpath("//td//span[contains(text(), '" + systemName + "')]/../..//td//span[contains(text(), '" + systemType + "')]");
    }
    
    protected WebElement systemRecordForTypeValue(String systemName, String systemForTypes) {
    	
    	return driver.elementFinder.findElementByXpath("//td//span[contains(text(), '" + systemName + "')]/../..//td//span[contains(text(), '" + systemForTypes + "')]");
    }
    
    protected WebElement systemRecordAuthenticationValue(String systemName, String systemAuthentication) {
    	
    	return driver.elementFinder.findElementByXpath("//td//span[contains(text(), '" + systemName + "')]/../..//td//span[contains(text(), '" + systemAuthentication + "')]");
    }
    
    protected WebElement systemRecordAddressValue(String systemAddress) {
    	
    	return driver.elementFinder.findElementByXpath("//td//a[contains(text(), '" + systemAddress + "')]");
    }
}
