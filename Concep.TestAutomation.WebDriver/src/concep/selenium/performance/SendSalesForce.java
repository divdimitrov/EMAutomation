package concep.selenium.performance;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.Theme;

public class SendSalesForce extends BasePerformance {

    private int loopCount;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    private void createConnectionWithLeadsOnAndContactEntity(
                                                              @Optional("1") String loopCount )
                                                                                               throws Exception {

        driver.init( "config/firefox.Performance" );
        driver.url = driver.config.getProperty( "url" );
        sendUserName = driver.config.getProperty( "sfdcSendUser" );
        sendPassword = driver.config.getProperty( "sendPassword" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", true );
        this.loopCount = Integer.parseInt( loopCount );

    }

    public void surveyResponseSubmition() throws Exception {

        String number = driver.getTimestamp();
        String surveyType = "Survey";
        String campaignType = "QA campaign1";

        log.startTest( "Verify that if too many responses ("
                       + this.loopCount
                       + ") are awaiting on the Platform's DB to be synchronized they will be successfully extracted" );
        try {
            // Log And Go to Survey Page
            send.goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( number + "surveyPerformance" )
                                            .checkLogResponseInCRM()
                                            .selectSurveyFolders( surveyType, campaignType ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                         .editMCquestionAndMapItToLeadSource( true )
                                                                                                         .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.SPORT.type );
            for( int i = 0; i < this.loopCount; i++ ) {
                send.sfdcSurvey.viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                        new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                     .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                        number + "mail" + i
                                                                                                + "@test.com" )
                                                                     .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                        number + "lastName" );
                send.msdSurvey.surveyNext();
                driver.close();
                driver.selectWindow();
            }

            driver.close();
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "Salesforce" })
    public void sendSurveyResponse() throws Exception {

        surveyResponseSubmition();
    }

    @AfterClass(alwaysRun = true)
    public void shutDownSelenium() {

        driver.stopSelenium();

    }
}
