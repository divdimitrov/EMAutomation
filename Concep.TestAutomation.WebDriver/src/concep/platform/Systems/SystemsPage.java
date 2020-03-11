package concep.platform.Systems;

import java.security.InvalidParameterException;

import concep.Utilities.RandomValuesGenerator;
import concep.platform.Systems.Enums.Authentication;
import concep.platform.Systems.Enums.SystemForType;
import concep.platform.Systems.Enums.SystemType;
import concep.platform.Systems.Enums.SystemsTab;
import concep.platform.core.PlatformBase;
import concep.seleniumWD.core.SeleniumDriverCore;

public class SystemsPage extends PlatformBase {

	private RandomValuesGenerator valuesGenerator;

	protected SystemsElement element;
	public SystemsAssertions assertion;

	public SystemsPage(SeleniumDriverCore driver) {
		super(driver);
		this.valuesGenerator = new RandomValuesGenerator();
		this.element = new SystemsElement(super.driver);
		this.assertion = new SystemsAssertions(super.driver);
	}

	public void navigateToSystemTab(SystemsTab tab) throws Exception {

		log.startStep("Click on tab: " + tab);

		switch (tab) {
		case SYSTEMS:
			driver.click(this.element.systemTab());
			break;
		case SYSTEM_USERS:
			driver.click(this.element.systemUserTab());
			break;
		case SEND_INTEGRATED_USERS:
			driver.click(this.element.systemUserTab());
			break;
		case CONNECTIONS:
			driver.click(this.element.connectionTab());
			break;

		default:
			throw new InvalidParameterException(
					"The following parameter is invalid: " + tab);
		}

		driver.waits.waitForPageToLoad();
		log.endStep();
	}

	public SystemsRecord getSystemRecord(SystemType systemType) throws Exception {

		SystemsRecord cmsProviderSystemRecord = new SystemsRecord();

		cmsProviderSystemRecord.setName(valuesGenerator.getTSCustomName("name"));
		cmsProviderSystemRecord.setAuthentication(Authentication.NONE.value);
		cmsProviderSystemRecord.setAddress(valuesGenerator.getTSAddress());

		switch (systemType) {
		case NOT_SET:
			cmsProviderSystemRecord.setSystemType(SystemType.NOT_SET.value);
			break;
		case CMS_PROVIDER:
			cmsProviderSystemRecord
					.setSystemType(SystemType.CMS_PROVIDER.value);
			break;
		case CRM_PROVIDER:
			cmsProviderSystemRecord
					.setSystemType(SystemType.CRM_PROVIDER.value);
			cmsProviderSystemRecord
					.setSystemForType(SystemForType.DYNAMICS.value);
			break;
		case EMAIL_PROVIDER:
			cmsProviderSystemRecord
					.setSystemType(SystemType.EMAIL_PROVIDER.value);
			cmsProviderSystemRecord.setSystemForType(SystemForType.SEND.value);
			break;
		case OTHER_PROVIDER:
			cmsProviderSystemRecord
					.setSystemType(SystemType.OTHER_PROVIDER.value);
			break;
		default:	throw new InvalidParameterException(
				"The following parameter is invalid: " + systemType);
		}

		return cmsProviderSystemRecord;
	}

	public void createSystemRecord(SystemsRecord system) throws Exception {

		typeExternalSystemName(system.getName());
		selectExternalSystemType(system.getSystemType());
		selectExternalSystemForType(system.getSystemForType());
		selectExternalSystemAuthentication(system.getAuthentication());
		typeExternalSystemAddress(system.getAddress());
	}
	
	private void typeExternalSystemName(String systemName) throws Exception {
		
		if (systemName != null) {
			
			log.startStep( "Type in '" + systemName + "' for the External System record" );
	        driver.clear( element.extSystemName() );
	        driver.type( element.extSystemName(), systemName);
	        log.endStep();
		}		
	}
	
	private void selectExternalSystemType(String  systemType) throws Exception {
		
		if (systemType != null) {
			
			log.startStep( "Select a '" + systemType + "' from the System Type dropdown" );
	        driver.select( element.extSystemType() ).selectByVisibleText( systemType );
	        log.endStep();
		}		
	}
	
	private void selectExternalSystemForType(String systemForType) throws Exception {
		
		if (systemForType != null) {
			
			log.startStep( "Select a '" + systemForType + "' from the System for Type dropdown" );
	        driver.select( element.extSystemForType() ).selectByVisibleText( systemForType );
	        log.endStep();
		}		 
	}
	
	private void selectExternalSystemAuthentication(String systemAuthentication) throws Exception {
		
		if (systemAuthentication != null) {
			
			log.startStep( "Select a '" + systemAuthentication + "' from the System Type dropdown" );
	        driver.select( element.extSystemAuthentication() ).selectByVisibleText( systemAuthentication );
	        log.endStep();
		}		
	}

	private void typeExternalSystemAddress(String systemAddress) throws Exception {
		
		if (systemAddress != null) {
			
			log.startStep( "Type in '" + systemAddress + "' for the External System record" );
	        driver.clear( element.extSystemAddress() );
	        driver.type( element.extSystemAddress(), systemAddress);
	        log.endStep();
		}		
	}	
}
