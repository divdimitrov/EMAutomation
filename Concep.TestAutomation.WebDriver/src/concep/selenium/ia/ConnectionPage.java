/*

*/

package concep.selenium.ia;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * 
 * NOTES: 
 * 
 * 1- The connection annotation is not included in the regression test suite which is currently with annotation all-surveys.
 * 
 * 2- The negative test cases which are for wrong credentials should be created for Dynamics and SalesForce.
 *
 */

public class ConnectionPage extends BaseIA {
	private final boolean isSourced = true;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void beforeClassSetUP(
                                  @Optional("config/firefox.IA") String configLocation ) throws Exception {

        driver.init( configLocation );
        iaConnectionName = driver.config.getProperty( "iaConnectionName" );
        iaConnectionUsername = driver.config.getProperty( "iaConnectionUsername" );
        iaConnectionPassword = driver.config.getProperty( "iaConnectionPassword" );
        iaSendUser = driver.config.getProperty( "iaSendUser" );
        iaSendPassword = driver.config.getProperty( "iaSendPassword" );
        driver.url = driver.config.getProperty( "url" );

        driver.stopSelenium();

    }

    public void clearAndCreatConnection(
                                         boolean isSurveyEnabled,
                                         boolean isFoldersEnabled,
                                         boolean isSourced ) throws Exception {

        //clear the connection settings
        returnConnectionToDefault();

        //Create new Connection to IA.
        send.iaConnection.loginToExistingConnection( iaConnectionUsername, iaConnectionPassword )
                         .enableSurveyAndSelectIAFolders( surveyEnableXPath,
                                                          isSurveyEnabled,
                                                          isFoldersEnabled );
        if( isSurveyEnabled ) {
            send.iaConnection.selectAndSaveFolderConfiguration( "Firm Contacts - Companies", isSourced );
        }
        send.iaConnection.clickNextToActivtiesPage()
                         .selectSurveyConnectionActivities()
                         .saveAndCloseConnectionPage();

        log.startStep( "Verify that the connection is created successfully" );
        log.endStep( driver.isElementPresent( "//ul[@id='connntetionsList']/li/a[@href='#']", driver.timeOut ) );
    }

    private void successfullyCreatedandVerifiedConnectionWithDifferentSettings(
                                                                                String testDescription,
                                                                                boolean isSurveyEnabled,
                                                                                boolean isFoldersEnabled,
                                                                                boolean isSourceChecked )
                                                                                                         throws Exception {

        log.startTest( testDescription );
        try {

            //login, set connection config to default and create a new connection 
            clearAndCreatConnection( isSurveyEnabled, isFoldersEnabled, isSourceChecked );

            send.iaConnection.loginToExistingConnection( iaConnectionUsername, iaConnectionPassword );

            if( isSurveyEnabled && isSourceChecked ) {

                log.startStep( "verify that Survey check box value is saved successfully" );
                log.endStep( driver.isChecked( "//label[@id='surveysEnbaled']/input" ) );

                send.iaConnection.enableSurveyAndSelectIAFolders( surveyEnableXPath,
                                                                  isSurveyEnabled,
                                                                  isFoldersEnabled );
                if( isSurveyEnabled ) {
                    send.iaConnection.clickConfigureButton();
                    log.startStep( "verify that 'Source new company contacts in Marketing Event folder, not the one above?' check box is saved successfully" );
                    log.endStep( driver.isChecked( "//div[@class='ui-helper-clearfix third']/label/input" ) );
                }

            } else if( !isSurveyEnabled && !isSourceChecked ) {

                log.startStep( "verify that Survey check box value is saved successfully" );
                log.endStep( !driver.isChecked( "//label[@id='surveysEnbaled']/input" ) );

                send.iaConnection.enableSurveyAndSelectIAFolders( surveyEnableXPath, false, isFoldersEnabled );

                log.startStep( "verify that 'configure folder' button is disabled" );
                log.endStep( driver.isElementPresent( "//span[contains(text(), 'Configure folders')][contains(@class, 'disabledAnchor')]",
                                                       driver.timeOut ) );

            } else if( isSurveyEnabled && !isSourceChecked ) {

                log.startStep( "verify that Survey check box value is saved successfully" );
                log.endStep( driver.isChecked( "//label[@id='surveysEnbaled']/input" ) );

                send.iaConnection.enableSurveyAndSelectIAFolders( surveyEnableXPath, false, isFoldersEnabled );

                log.startStep( "verify that 'configure folder' button is disabled" );
                log.endStep( driver.isElementPresent( "//span[contains(text(), 'Configure folders')][contains(@class, 'disabledAnchor')]",
                                                       driver.negativeTimeOut ) );

            } else if( !isSurveyEnabled && isSourceChecked ) {

                log.startStep( "verify that Survey check box value is saved successfully" );
                log.endStep( !driver.isChecked( "//label[@id='surveysEnbaled']/input" ) );

                send.iaConnection.enableSurveyAndSelectIAFolders( surveyEnableXPath, false, isFoldersEnabled );

                log.startStep( "verify that 'Configure folders' button is disabled" );
                log.endStep( driver.isElementPresent( "//span[contains(text(), 'Configure folders')][contains(@class, 'disabledAnchor')]",
                                                       driver.negativeTimeOut ) );
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    private void successfullyCreatedAndUpdatedConnection(
                                                          String testDescription,
                                                          boolean isSurveyEnabled,
                                                          boolean isSourceChecked,
                                                          boolean isSurveyUpdated,
                                                          boolean isSourcedUpdated ) throws Exception {

        log.startTest( testDescription );
        try {

            //login, set connection config to default and create a new connection 
            clearAndCreatConnection( isSurveyEnabled, isSurveyEnabled, isSourceChecked );

            send.iaConnection.loginToExistingConnection( iaConnectionUsername, iaConnectionPassword )
                             .enableSurveyAndSelectIAFolders( surveyEnableXPath,
                                                              isSurveyUpdated,
                                                              isSurveyUpdated );
            if( isSurveyUpdated ) {
                send.iaConnection.selectAndSaveFolderConfiguration( "Firm Contacts - Companies",
                                                                    isSourcedUpdated );
            }
            send.iaConnection.clickNextToActivtiesPage()
                             .selectSurveyConnectionActivities()
                             .saveAndCloseConnectionPage()

                             .loginToExistingConnection( iaConnectionUsername, iaConnectionPassword );

            if( isSurveyUpdated && isSourcedUpdated ) {

                log.startStep( "verify that Survey check box is updated successfully" );
                log.endStep( driver.isChecked( surveyEnableXPath ) );

                send.iaConnection.enableSurveyAndSelectIAFolders( surveyEnableXPath,
                                                                  isSurveyUpdated,
                                                                  isSurveyUpdated ).clickConfigureButton();

                log.startStep( "verify that 'Source new company contacts in Marketing Event folder, not the one above?' check box is updated" );
                log.endStep( driver.isChecked( "//div[@class='ui-helper-clearfix third']/label/input" ) );
            } else if( !isSurveyUpdated && !isSourcedUpdated ) {

                log.startStep( "verify that Survey check box is updated successfully" );
                log.endStep( !driver.isChecked( surveyEnableXPath ) );

                send.iaConnection.enableSurveyAndSelectIAFolders( surveyEnableXPath,
                                                                  isSurveyUpdated,
                                                                  isSurveyUpdated );

                log.startStep( "verify that 'Configure folders' button is disabled" );
                log.endStep( driver.isElementPresent( "//span[contains(text(), 'Configure folders')][contains(@class, 'disabledAnchor')]",
                                                       driver.negativeTimeOut ) );

            } else if( isSurveyUpdated && !isSourcedUpdated ) {

                log.startStep( "verify that Survey check box is updated successfully" );
                log.endStep( driver.isChecked( "//label[@id='surveysEnbaled']/input" ) );

                send.iaConnection.enableSurveyAndSelectIAFolders( surveyEnableXPath,
                                                                  isSurveyUpdated,
                                                                  isSurveyUpdated ).clickConfigureButton();

                log.startStep( "verify that 'Source new company contacts in Marketing Event folder, not the one above?' check box is updated" );
                log.endStep( !driver.isChecked( "//div[@class='ui-helper-clearfix third']/label/input" ) );
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();

    }

    /**
     * 
     * Successfully created connection With Survey Feature disabled and 'Create
     * new company in Marketing Event Folder' disabled and different folder
     * options
     * 
     */

    @Test(groups = { "connections", "all-tests" })
    public void successfullyCreatedConnectionWithSurveyDisabledAndMarketingEventFolderDisabled()
                                                                                                throws Exception {

        successfullyCreatedandVerifiedConnectionWithDifferentSettings( "Verify that a connection can be Successfully created with Survey disabled and create company in Marketing Event Folder disabled",
                                                                       false,
                                                                       false,
                                                                       false );
    }

    /**
     * 
     * Successfully created connection With Survey Feature enabled and 'Create
     * new company in Marketing Event Folder' enabled and different folder
     * options
     * 
     */
    @Test(groups = { "connections", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCreatedConnectionWithSurveyEnabledAndMarketingEventFolderEnabled()
                                                                                              throws Exception {

        successfullyCreatedandVerifiedConnectionWithDifferentSettings( "Verify that a connection can be successfully created with Survey Feature enabled and create company in Marketing Event folder enabled",
                                                                       true,
                                                                       true,
                                                                       true );
    }

    /**
     * 
     * Successfully created connection With Survey Feature enabled and 'Create
     * new company in Marketing Event Folder' disabled and different folder
     * options
     * 
     */

    @Test(groups = { "connections", "all-tests", "key-tests" })
    public void successfullyCreatedConnectionWithSurveyEnabledAndMarketingEventFolderDisabled()
                                                                                               throws Exception {

        successfullyCreatedandVerifiedConnectionWithDifferentSettings( "Verify that a connection can be successfully created with Survey Feature enabled and create company in Marketing Event Folder disabled",
                                                                       true,
                                                                       true,
                                                                       false );
    }

    /**
     * 
     * Successfully created connection With Survey Feature disabled and 'Create
     * new company in Marketing Event Folder' enabled and different folder
     * options
     * 
     */

    @Test(groups = { "connections", "all-tests" })
    public void successfullyCreatedConnectionWithSurveyDisabledAndMarketingEventFolderEnabled()
                                                                                               throws Exception {

        successfullyCreatedandVerifiedConnectionWithDifferentSettings( "Verify that a connection can be successfully created with Survey Feature disabled and create company in Marketing Event folder enabled",
                                                                       false,
                                                                       false,
                                                                       true );
    }

    @Test(groups = { "connections", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyUpdatedConnectionFromSurveyAndSourcedFolderEnabledToSurveyAndSourceFolderDisabled()
                                                                                                               throws Exception {

        successfullyCreatedAndUpdatedConnection( "Verify that a connection can be successfully created With Survey Feature enabled and create company in Marketing Event folder enabled, Updated to connection with Survey disabled and Marketing event Folder disabled",
                                                 true,
                                                 true,
                                                 false,
                                                 false );
    }

    @Test(groups = { "connections", "all-tests", "key-tests" })
    public void successfullyUpdatedConnectionFromSurveyAndSourcedFolderDisabledToSurveyAndSourceFolderEnabled()
                                                                                                               throws Exception {

        successfullyCreatedAndUpdatedConnection( "Verify that a connection can be successfully created with Survey disabled and Source new company contacts in Marketing Event folder is disabled, Updated to connection with Survey enabled and Marketing event Folder enabled",
                                                 false,
                                                 false,
                                                 true,
                                                 true );
    }

    @Test(groups = { "connections", "all-tests", "smoke-tests" })
    public void successfullyUpdatedConnectionFromSurveyAndSourcedFolderDisabledToSurveyEnabledAndSourceFolderDisabled()
                                                                                                                       throws Exception {

        successfullyCreatedAndUpdatedConnection( "Verify that a connection can be successfully created with Survey disabled and Source new company contacts in Marketing Event folder is disabled, Updated to connection with Survey enabled and Marketing event Folder enabled",
                                                 false,
                                                 false,
                                                 true,
                                                 false );
    }

    private void unsuccessfullyCreatedConnectionWithWrongCredentials(
                                                                      String testDescription,
                                                                      String IAuserName,
                                                                      String IApassword ) throws Exception {

        log.startTest( testDescription );
        try {

            log.startStep( "Go to Connections page from the Main Page" );
            loginToSend().goToConnectionsPage();
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            send.iaConnection.loginToExistingConnection( IAuserName, IApassword );

            log.startStep( "Verify that the login failed error message is displayed" );
            log.endStep( driver.isElementPresent( "//div[contains(text(), 'Login failed. Please check your information and try again.')]",
                                                  driver.timeOut ) );
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /**
     * 
     * Unsuccessfully created Connection to InterAction due to wrong password.
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "connections", "all-tests", "smoke-tests" })
    public void unsuccessfullyCreatedConnectionWithWrongPassword() throws Exception {

        unsuccessfullyCreatedConnectionWithWrongCredentials( "Verify that it is not possible to create a connection to IA with wrong password",
                                                             iaConnectionUsername,
                                                             "fakePassword" );

    }

    /**
     * 
     * Unsuccessfully created Connection to InterAction due to wrong username.
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "connections", "all-tests" })
    public void unsuccessfullyCreatedConnectionWithWrongUsername() throws Exception {

        unsuccessfullyCreatedConnectionWithWrongCredentials( "Verify that it is not possible to create a connection to IA with wrong username",
                                                             "fakeUserName",
                                                             iaConnectionPassword );

    }

    /**
     * @throws Exception 
     * 
     * 
     * 
     */
    @Test(groups = { "connections", "all-tests", "key-tests" })
    public void verifyLogResponseCheckBoxWithDifferentConnection() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + surveyType1 + eventFolderAdministrative;

        log.startTest( "Verify that 'IA folder' and Survey type check box is not available when Survey is not enabled in the connection page" );
        try {

            clearAndCreatConnection( false, false, false );

            send.goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName );

            log.startStep( "verify that 'Interaction Folder' drop down is not available" );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep( !driver.isElementPresent( "//span[contains(text(),'Interaction Folder')]/../select[@id='surveyInteractionFolder']",
                                                   driver.negativeTimeOut ) );

            log.startStep( "verify that 'Interaction Folder' drow down is not available" );
            log.endStep( !driver.isElementPresent( "//span[contains(text(),'Survey Type')]/../select[@id='surveyTypeDropDown']",
                                                   driver.negativeTimeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();

    }
}
