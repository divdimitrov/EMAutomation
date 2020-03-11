/*

*/

package concep.selenium.Dynamic;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ConnectionPage extends BaseMSD {

    /**
     * 
     * Verify that as user, I won't be able to delete a connection
     * 
     * @throws Exception
     */
    @Test(groups = { "connection-page", "all-tests" })
    public void unsuccessfullyDeleteConnection() throws Exception {

        log.startTest( "Verify that as user, I won't be able to delete a connection" );
        try {

            loginToSend().goToConnectionsPage();

            log.resultStep( "verify that the 'x' button is no longer available" );
            driver.hoverToElement( "//ul[@id='connntetionsList']/li/a[@href='#']" );
            log.endStep( !driver.isElementPresent( "//span[@title='Delete connection']", driver.negativeTimeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "connection-page", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDiableSurveyConnectionOption() throws Exception {

        log.startTest( "verify that user can successfully disable the survey check box from the dynamic connection" );

        try {
            loginToSend().goToConnectionsPage().dynamicConnection.updateExistingConnection( false )
                                                                 .loginToExistingConnection();

            log.resultStep( "verify that Survey check box is unchecked" );
            log.endStep( !driver.isChecked( element.msd4ConnectionSurveyEnableBox ));

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    @Test(groups = { "connection-page", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyEnableSurveyConnectionOption() throws Exception {

        log.startTest( "verify that user can successfully enable the survey check box from the dynamic connection" );

        try {
            loginToSend().goToConnectionsPage().dynamicConnection.updateExistingConnection( true )
                                                                 .loginToExistingConnection();

            log.resultStep( "verify that Survey check box is unchecked" );
            log.endStep( driver.isChecked( element.msd4ConnectionSurveyEnableBox ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

    }

    @AfterClass(alwaysRun = true)
    @Parameters({ "config" })
    public void resetConnectionSettings(
                                         @Optional("config/firefox.MSD4") String configLocation )
                                                                                                 throws Exception {

        driver.init( configLocation );
        msdSendUser = driver.config.getProperty( "msdSendUser" );
        msdSendUser2 = driver.config.getProperty( "msdSendUser2" );
        msdSendUser3 = driver.config.getProperty( "msdSendUser3" );
        msdSendPassword = driver.config.getProperty( "msdSendPassword" );
        driver.url = driver.config.getProperty( "url" );
        loginToSend().goToConnectionsPage().dynamicConnection.updateExistingConnection( true );
    }
    //TODO test cases needs to be created for negative authentication. 
}
