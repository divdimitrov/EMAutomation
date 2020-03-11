/*

*/

package concep.selenium.SalesForce;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ConnectionPage extends BaseSFDC {

	@BeforeClass(alwaysRun = true)
	@Parameters({ "config" })
	private void initConnectionTargetUser(@Optional("config/firefox.SFDC") String configLocation ) throws Exception {	
		
	     driver.init( configLocation );
	     sfdcSendUser = driver.config.getProperty( "sfdcSendUser" );
	     sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
	     driver.stopSelenium();
	}
	
    @Test(groups = { "all-tests", "connection-page", "smoke-tests", "key-tests" })
    public void successfullyUpdateConnectionWithLeadsOnAndContactEntity() throws Exception {

        log.startTest( "Verify that an existing connection can be successfully updated with Contact in use and Lead entity enabled" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", true );
        log.endTest();

    }

    @Test(groups = { "all-tests", "connection-page", "key-tests" })
    public void successfullyUpdateConnectionWithLeadsOnAndPersonAccountEntity() throws Exception {

        log.startTest( "Verify that an existing connection can be successfully updated with Person Account in use and Lead entity enabled" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Person account", true );
        log.endTest();

    }

    @Test(groups = { "all-tests", "connection-page", "smoke-tests", "key-tests" })
    public void successfullyUpdateConnectionWithLeadsOffAndContactEntity() throws Exception {

        log.startTest( "Verify that an existing connection can be successfully updated with Contact in use and Lead entity disabled" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", false );
        log.endTest();

    }

    @Test(groups = { "all-tests", "connection-page", "key-tests" })
    public void successfullyUpdateConnectionWithLeadsOffAndPersonAccountEntity() throws Exception {

        log.startTest( "Verify that an existing connection can be successfully updated with Person Account in use and Lead entity disabled" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Person account", false );
        log.endTest();

    }

}
