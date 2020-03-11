package concep.platform.Systems;

import concep.platform.core.PlatformAssertion;
import concep.seleniumWD.core.SeleniumDriverCore;

public class SystemsAssertions extends PlatformAssertion {

    private SystemsElement element;

    public SystemsAssertions( SeleniumDriverCore driver ) {
        super( driver );
        this.element = new SystemsElement( super.driver );
    }
    
    public void systemRecordAssertion(SystemsRecord systemRecord) throws Exception {
    	
    	verifySystemName(systemRecord.getName());
    	verifySystemType(systemRecord.getName(), systemRecord.getSystemType());
    	verifySystemForType(systemRecord.getName(), systemRecord.getSystemForType());
    	verifySystemAuthentication(systemRecord.getName(), systemRecord.getAuthentication());
    	verifySystemAddress(systemRecord.getAddress());
    }
    
    private void verifySystemName(String name) {
    	
    	log.startStep("Verify that External System record has a Name value of: " + name);
    	log.endStep(driver.assertion.isElementPresent(element.systemRecordNameValue(name)));
	}
    
    private void verifySystemType(String name, String systemType) {
    	
		log.startStep("Verify that External System record with name: " + name + " has a System Type value of: " + systemType);
    	log.endStep(driver.assertion.isElementPresent(element.systemRecordTypeValue(name, systemType)));
	}
    
    private void verifySystemForType(String name, String systemForType) {
    	
		log.startStep("Verify that External System record with name: " + name + " has a System For Type value of: " + systemForType);
    	log.endStep(driver.assertion.isElementPresent(element.systemRecordForTypeValue(name, systemForType)));
	}
    
    private void verifySystemAuthentication(String name, String authentication) {
    	
		log.startStep("Verify that External System record with name: " + name + " has a Authentication value of: " + authentication);
    	log.endStep(driver.assertion.isElementPresent(element.systemRecordAuthenticationValue(name, authentication)));
	}

	private void verifySystemAddress(String address) {
		
		log.startStep("Verify that External System record has a Address value of: " + address);
    	log.endStep(driver.assertion.isElementPresent(element.systemRecordAddressValue(address)));
	}	
}
