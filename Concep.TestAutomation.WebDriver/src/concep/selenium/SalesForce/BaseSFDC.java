/*

*/

package concep.selenium.SalesForce;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.json.Json;
import concep.selenium.SalesForce.Attibutes.Campaign;
import concep.selenium.SalesForce.Attibutes.SurveyAttributes;
import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import concep.selenium.send.Elements;
import concep.selenium.send.Send;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;

public class BaseSFDC {

    private String                            emailNotMappedErrorMessage = "In order to log responses in your CRM system, E-Mail must be mapped. Please map a question to this contact field and try again.";

    protected String                          sfdcSendUser;
    protected String                          sfdcSendUser2;
    protected String                          sfdcSendUser3;
    protected String                          sfdcSendPassword;
    protected String                          sfdcCampaign1;
    protected String                          sfdcCampaign2;
    protected String                          sfdcCampaign3;
    protected String                          sfdcCampaign4;
    protected String                          sfdcCampaign5;
    protected String                          sfdcCampaign6;
    protected String                          sfdcExistingLead;
    protected String                          sfdcExistingContact;
    protected String                          sfdcExistingPersonAccount;
    protected String                          sendSuperUser;
    protected String                          sendSuperPassword;
    protected String                          surveyType1;
    protected String                          surveyType2;
    protected String                          surveyType3;
    protected String                          surveyType4;
    protected String                          surveyType5;
    protected String                          surveyType6;
    protected String                          campaignResponseSent       = "Sent";
    protected String                          campaignResponseRespond    = "Responded";
    protected boolean                         isJson;

    protected static GenericSeleniumWebDriver driver                     = DriverAccessor.getDriverInstance();
    protected LogResults                      log                        = new LogResults();
    protected Elements                        element                    = new Elements();
    protected Send                            send                       = new Send();
    protected SurveyAttributes                surveyAttributes;
    protected Campaign                        campaign;
    protected Json                            json;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config" })
    public void startSelenium(
                               @Optional("config/firefox.SFDC") String configLocation ) throws Exception {

        driver.init( configLocation );
        sendSuperUser = driver.config.getProperty( "sendSuperUser" );
        sendSuperPassword = driver.config.getProperty( "sendSuperPassword" );
        surveyType1 = driver.config.getProperty( "surveyType1" );
        surveyType2 = driver.config.getProperty( "surveyType2" );
        surveyType3 = driver.config.getProperty( "surveyType3" );
        surveyType4 = driver.config.getProperty( "surveyType4" );
        surveyType5 = driver.config.getProperty( "surveyType5" );
        surveyType6 = driver.config.getProperty( "surveyType6" );
        sfdcCampaign1 = driver.config.getProperty( "sfdcCampaign1" );
        sfdcCampaign2 = driver.config.getProperty( "sfdcCampaign2" );
        sfdcCampaign3 = driver.config.getProperty( "sfdcCampaign3" );
        sfdcCampaign4 = driver.config.getProperty( "sfdcCampaign4" );
        sfdcCampaign5 = driver.config.getProperty( "sfdcCampaign5" );
        sfdcCampaign6 = driver.config.getProperty( "sfdcCampaign6" );
        sfdcExistingLead = driver.config.getProperty( "sfdcExistingLead" );
        sfdcExistingContact = driver.config.getProperty( "sfdcExistingContact" );
        sfdcExistingPersonAccount = driver.config.getProperty( "sfdcExistingPersonAccount" );
        driver.url = driver.config.getProperty( "url" );
        isJson = Boolean.parseBoolean( driver.config.getProperty( "isJson" ) );
        surveyAttributes = new SurveyAttributes();
        campaign = new Campaign();
        json = new Json();

    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {

        driver.stopSelenium();
    }

    protected Send loginToSend() throws Exception {

        send.loginToSend( sfdcSendUser, sfdcSendPassword );
        return send;
    }

    protected Send loginToSend2() throws Exception {

        send.loginToSend( sfdcSendUser2, sfdcSendPassword );
        return send;
    }

    protected Send loginToSend3() throws Exception {

        send.loginToSend( sfdcSendUser3, sfdcSendPassword );
        return send;
    }

    protected boolean verifyLogResponseInCRM() throws InterruptedException {

        for( int i = 0;; i++ ) {
            if( i >= driver.timeOut )
                return ( false );
            try {
                if( driver.isChecked( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']" ) ) {
                    return ( true );
                }
            } catch( Exception e ) {}
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

    protected void unsuccessfullyCreateSurveyWhenSaving(
                                                         boolean isSaveOnly ) throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "negative" + "WarningMessageWhenSaving";
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1,
                                                                           sfdcCampaign1 ).sfdcQuestion.editQuestionType( "A free text question" )
                                                                                                       .enterQuestion( "Not Mapped Email" )
                                                                                                       .updateQuestion();

            if( isSaveOnly ) {

                send.sfdcSurvey.saveSurvey();

            } else {

                send.sfdcSurvey.saveAndContinueToTheme();
            }

            log.startStep( "Verify the warning message when 'email' is not mapped ." );
            log.endStep( driver.isTextPresent( "//div[@class='modalContent ui-helper-clearfix']/div",
                                               emailNotMappedErrorMessage,
                                               driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    /**
     * 
     *  Data driven method which verifies that survey can be completed with different survey types and campaigns while using connection which has leads disabled and contact entity
     * @throws Exception
     * 
     */

    protected void completeSurveyWithLinkedQuestionsAndNonExistingContacts(
                                                                            String surveyType,
                                                                            String sfdcCampaign,
                                                                            boolean isLead,
                                                                            String theme ) throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType + sfdcCampaign;
        String testDescription = "Verify that Survey type " + surveyType
                                 + " can be completed successfully with non existing user and contact/person account entity options";

        log.startTest( testDescription );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType,
                                                                           sfdcCampaign ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                      .editMCquestionAndMapItToLeadSource( isLead );
            send.sfdcSurvey.saveAndSelectTheme( theme )
                           .viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                    new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    emailAddress )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );
            send.sfdcSurvey.surveyNext();
            surveyAttributes.surveyName = surveyName;
            surveyAttributes.surveyType = surveyType;
            surveyAttributes.campaign.name = sfdcCampaign;
            surveyAttributes.campaign.campaignResponse.add( campaignResponseSent );

            surveyAttributes.questions.add( MCcontactFiled.LEADSOURCE.question );
            surveyAttributes.questions.add( FTcontactField.EMAIL.question );
            surveyAttributes.questions.add( FTcontactField.LASTNAME.question );

            surveyAttributes.answers.add( MCcontactFiled.LEADSOURCE.option_1 );
            surveyAttributes.answers.add( emailAddress );
            surveyAttributes.answers.add( lastName );

            surveyAttributes.email.add( emailAddress );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();

    }

    protected void verifyPageFooter() throws Exception {

        log.startStep( "Verify that the 'Copyright 2015. All Rights Reserved' and 'Concep Logo' are displayed at the bottom of the page" );
        log.endStep( driver.isElementPresent( "//div[@id='s_footer']//p[contains(text(), 'Copyright 2015. All Rights Reserved')]",
                                              driver.timeOut )
                     & driver.isElementPresent( "//div[@id='s_footer']//img", driver.timeOut ) );
    }

}
