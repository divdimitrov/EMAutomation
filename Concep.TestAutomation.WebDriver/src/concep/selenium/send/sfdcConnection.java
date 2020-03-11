package concep.selenium.send;

public class sfdcConnection extends Connections<sfdcConnection> {
    protected String sfdcConnectionName     = "https://test.salesforce.com";
    protected String sfdcConnectionUsername = "concep_send@concep.com.alsandbox";
    protected String sfdcConnectionPassword = "C0ncep2014!DDdmQcAYuFoQ49ApYUblWg4iQ";

    public sfdcConnection createConnection() throws Exception {

        return super.createConnection( sfdcConnectionName, sfdcConnectionUsername, sfdcConnectionPassword );
    }

    public sfdcConnection loginToExistingConnection() throws Exception {

        loginToExistingConnection( sfdcConnectionUsername, sfdcConnectionPassword );
        return this;
    }

    public sfdcConnection selectContactEntity(
                                               String contactEntity ) throws Exception {

        log.startStep( "Select contact entity" );
        driver.select( element.sfdcConnectionSelectContactEntity ).selectByVisibleText( contactEntity );
        log.endStep();
        return this;
    }

    public sfdcConnection enableSurveys() throws Exception {

        return enableSurveys( element.sfdcConnectionSurveyEnableBox );
    }

    public sfdcConnection disableSurveys() throws Exception {

        return disableSurveys( element.sfdcConnectionSurveyEnableBox );
    }

    public sfdcConnection enableActiveCampaign() throws Exception {

        log.startStep( "Check 'Only use active campaigns'" );
        if( driver.isChecked( element.sfdcConnectionActivCampaignsBox ) ) {} else {
            driver.click( element.sfdcConnectionActivCampaignsBox, driver.timeOut );
        }
        log.endStep();

        return this;
    }

    public sfdcConnection enableAndSelectLeadEntity() throws Exception {

        if( driver.isChecked( element.sfdcConnectionLeadEnableBox ) ) {} else {

            log.startStep( "Check 'Use lead entities in SalesForce'" );
            driver.click( element.sfdcConnectionLeadEnableBox, driver.timeOut );
            log.endStep();

            log.startStep( "Select the contact and lead fields that represent First name" );
            driver.select( "//h5/span[contains(text(), 'First name')]//../..//label/select[@class='contactFieldMappings']" )
                  .selectByVisibleText( "First Name" );
            driver.select( "//h5/span[contains(text(), 'First name')]//../..//label/select[@class='leadFieldMappings']" )
                  .selectByVisibleText( "First Name" );
            log.endStep();

            log.startStep( "Select the contact and lead fields that represent Last name" );
            driver.select( "//h5/span[contains(text(), 'Last name')]//../..//label/select[@class='contactFieldMappings']" )
                  .selectByVisibleText( "Last Name" );
            driver.select( "//h5/span[contains(text(), 'Last name')]//../..//label/select[@class='leadFieldMappings']" )
                  .selectByVisibleText( "Last Name" );
            log.endStep();

            log.startStep( "Select the contact and lead fields that represent Email" );
            driver.select( "//h5/span[contains(text(), 'Email')]//../..//label/select[@class='contactFieldMappings']" )
                  .selectByVisibleText( "Email" );
            driver.select( "//h5/span[contains(text(), 'Email')]//../..//label/select[@class='leadFieldMappings']" )
                  .selectByVisibleText( "Email" );
            log.endStep();
        }

        return this;
    }

    public sfdcConnection disableLeadEntity() throws Exception {

        if( driver.isChecked( element.sfdcConnectionLeadEnableBox ) ) {

            log.startStep( "un-check 'Use lead entities in SalesForce'" );
            driver.click( element.sfdcConnectionLeadEnableBox, driver.timeOut );
            log.endStep();
        }
        return this;
    }

    public sfdcConnection updateExistingConnection(
                                                    String contactEntity,
                                                    boolean isLeadEnabled ) throws Exception {

        try {

            if( driver.isElementPresent( element.connectionWindow, driver.timeOut ) ) {

                loginToExistingConnection();

            } else {

                createConnection();
            }

            log.startStep( "Verify that the Features window is displayed" );
            log.endStep( driver.isElementPresent( element.sfdcConnectionSurveyEnableBox, driver.timeOut ) );
            enableSurveys().selectContactEntity( contactEntity ).enableActiveCampaign();

            if( isLeadEnabled ) {
                enableAndSelectLeadEntity();
            } else {
                disableLeadEntity();
            }

            saveAndCloseConnectionPage();

            log.startStep( "check that connection is succesfully created" );
            log.endStep( driver.isElementPresent( element.connectionWindow, driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;

        }
        log.endTest();

        return this;
    }
}
