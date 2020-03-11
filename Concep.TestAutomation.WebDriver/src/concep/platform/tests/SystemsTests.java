package concep.platform.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import concep.platform.Systems.SystemsRecord;
import concep.platform.Systems.Enums.SystemType;
import concep.platform.core.PlatformPage;

public class SystemsTests extends PlatformBaseTests{
	
	@BeforeMethod
    public void testInit() throws Exception {

    	system.navigateToPage(PlatformPage.SYSTEMS);
    }    
	
	@Test
	public void test() throws Exception {
			
		SystemsRecord systemRecord = system.getSystemRecord(SystemType.CRM_PROVIDER);
		
		system.startCreatingRecord(false);
		system.createSystemRecord(systemRecord);
		system.saveRecord(false);
		system.assertion.systemRecordAssertion(systemRecord);
	}
}
