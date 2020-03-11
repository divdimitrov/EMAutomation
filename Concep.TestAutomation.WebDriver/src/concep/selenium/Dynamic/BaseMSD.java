/*

*/

package concep.selenium.Dynamic;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.json.Json;
import concep.selenium.Dynamic.Attributes.AnswersAttributes;
import concep.selenium.Dynamic.Attributes.CampaignResponseAttribute;
import concep.selenium.Dynamic.Attributes.QuestionsAttributes;
import concep.selenium.Dynamic.Attributes.SurveyAttributes;
import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import concep.selenium.mailinator.Mailinator;
import concep.selenium.send.Elements;
import concep.selenium.send.Send;

public class BaseMSD {

    protected String                          msdSendUser;
    protected String                          msdSendUser2;
    protected String                          msdSendUser3;
    protected String                          msdSendUserTemplates;
    protected String                          msdSendPassword;
    protected String                          msdExistingContact;
    protected String                          sendSuperUser;
    protected String                          sendSuperPassword;
    protected String                          surveyType1;
    protected String                          surveyType2;
    protected String                          surveyType3;
    protected String                          surveyType4;
    protected String                          surveyType5;
    protected String                          campaignName1;
    protected String                          campaignName2;
    protected String                          campaignName3;
    protected String                          campaignName4;
    protected String                          campaignName5;
    protected String                          campaignResponse1 = "Interested";
    protected String                          campaignRespose2  = "Not Interested";

    protected List<String>                    questionsArray;
    protected List<String>                    answersArray;
    protected List<String>                    campaignResponseArray;
    protected static GenericSeleniumWebDriver driver            = DriverAccessor.getDriverInstance();
    protected LogResults                      log               = new LogResults();
    protected Json                            json              = new Json();
    protected Elements                        element           = new Elements();
    protected Send                            send              = new Send();
    protected Mailinator                      mailinator        = new Mailinator();
    protected SurveyAttributes                surveyAttributes;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config" })
    public void startSelenium(
                               @Optional("config/firefox.MSD4") String configLocation ) throws Exception {

        driver.init( configLocation );
        msdSendUser = driver.config.getProperty( "msdSendUser" );
        msdSendUser2 = driver.config.getProperty( "msdSendUser2" );
        msdSendUser3 = driver.config.getProperty( "msdSendUser3" );
        msdSendUserTemplates = driver.config.getProperty( "msdSendUserTemplates" );
        msdSendPassword = driver.config.getProperty( "msdSendPassword" );
        sendSuperUser = driver.config.getProperty( "sendSuperUser" );
        sendSuperPassword = driver.config.getProperty( "sendSuperPassword" );
        surveyType1 = driver.config.getProperty( "surveyType1" );
        surveyType2 = driver.config.getProperty( "surveyType2" );
        surveyType3 = driver.config.getProperty( "surveyType3" );
        surveyType4 = driver.config.getProperty( "surveyType4" );
        surveyType5 = driver.config.getProperty( "surveyType5" );
        campaignName1 = driver.config.getProperty( "campaignName1" );
        campaignName2 = driver.config.getProperty( "campaignName2" );
        campaignName3 = driver.config.getProperty( "campaignName3" );
        campaignName4 = driver.config.getProperty( "campaignName4" );
        campaignName5 = driver.config.getProperty( "campaignName5" );
        msdExistingContact = driver.config.getProperty( "msdExistingContact" );
        driver.url = driver.config.getProperty( "url" );
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {

        driver.stopSelenium();
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters({ "config" })
    public void setup(
                       @Optional("config/firefox.MSD4") String configLocation ) throws Exception {

        driver.init( configLocation );
        msdSendUser = driver.config.getProperty( "msdSendUser" );
        msdSendUser2 = driver.config.getProperty( "msdSendUser2" );
        msdSendUser3 = driver.config.getProperty( "msdSendUser3" );
        msdSendUserTemplates = driver.config.getProperty( "msdSendUserTemplates" );
        msdSendPassword = driver.config.getProperty( "msdSendPassword" );
        sendSuperUser = driver.config.getProperty( "sendSuperUser" );
        sendSuperPassword = driver.config.getProperty( "sendSuperPassword" );
        surveyType1 = driver.config.getProperty( "surveyType1" );
        surveyType2 = driver.config.getProperty( "surveyType2" );
        surveyType3 = driver.config.getProperty( "surveyType3" );
        surveyType4 = driver.config.getProperty( "surveyType4" );
        surveyType5 = driver.config.getProperty( "surveyType5" );
        campaignName1 = driver.config.getProperty( "campaignName1" );
        campaignName2 = driver.config.getProperty( "campaignName2" );
        campaignName3 = driver.config.getProperty( "campaignName3" );
        campaignName4 = driver.config.getProperty( "campaignName4" );
        campaignName5 = driver.config.getProperty( "campaignName5" );
        msdExistingContact = driver.config.getProperty( "msdExistingContact" );
        driver.url = driver.config.getProperty( "url" );
        //Creates a connection with Dynamics if it's required.
        //loginToSend().goToConnectionsPage().dynamicConnection.updateExistingConnection( true );
        driver.stopSelenium();
    }

    protected Send loginToSend() throws Exception {

        send.loginToSend( msdSendUser, msdSendPassword );
        return send;
    }

    protected Send loginToSend2() throws Exception {

        send.loginToSend( msdSendUser2, msdSendPassword );
        return send;
    }

    protected Send loginToSend3() throws Exception {

        send.loginToSend( msdSendUser3, msdSendPassword );
        return send;
    }

    protected Send loginToSendTemplates() throws Exception {

        send.loginToSend( msdSendUserTemplates, msdSendPassword );
        return send;
    }

    protected void verifyPageFooter() throws Exception {

        log.startStep( "Verify that the 'Copyright 2015. All Rights Reserved' and 'Concep Logo' are displayed at the bottom of the page" );
        log.endStep( driver.isElementPresent( "//div[@id='s_footer']//p[contains(text(), 'Copyright 2015. All Rights Reserved')]",
                                              driver.timeOut )
                     & driver.isElementPresent( "//div[@id='s_footer']//img", driver.timeOut ) );
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

    protected void addEmailConfirmation() throws Exception {

        send.msdSurvey.dynamicQuestion.showQuestionSettings( "qTypetext" ).addManadatoryOption();

        log.startStep( "Check the 'Confirm Email Address' check-box" );
        if( !driver.isChecked( "//input[@id='chkConfirm']" ) ) {
            driver.click( "//input[@id='chkConfirm']", driver.timeOut );
        }
        log.endStep();
        send.msdSurvey.dynamicQuestion.hideQuestionSettings( "qTypetext" );
    }

    protected void addEmailConfirmationContactMappings(
                                                        String firstQuestion,
                                                        String firstQuestionMapping,
                                                        String secondQuestion,
                                                        String secondQuestionMapping,
                                                        String thirdQuestion,
                                                        String thirdQuestionMapping ) throws Exception {

        // A pop up Add Action Window appears
        log.startStep( "Click 'Add Action' button" );
        driver.click( "//a[@id='pageOptions']", driver.timeOut );
        driver.click( "//ul[@id='p_surveys_page']//li[a='Actions']", driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Select 'Add Contact' radio button" );
        driver.click( "//input[@id='radContact']", driver.timeOut );
        log.endStep();

        log.startStep( "Type 'Add Contact' in the 'Title' field" );
        driver.type( "//input[@id='frmATitle']", "Add Contact", driver.timeOut );
        log.endStep();

        log.startStep( "Select mapping value for the " + firstQuestion + " question" );
        driver.select( "//td[contains(text(), '" + firstQuestion + "')]//..//select" )
              .selectByVisibleText( firstQuestionMapping );
        log.endStep();

        log.startStep( "Select mapping value for the " + secondQuestion + " question" );
        driver.select( "//td[contains(text(), '" + secondQuestion + "')]//..//select" )
              .selectByVisibleText( secondQuestionMapping );
        log.endStep();

        log.startStep( "Select mapping value for the " + thirdQuestion + " question" );
        driver.select( "//td[contains(text(), '" + thirdQuestion + "')]//..//select" )
              .selectByVisibleText( thirdQuestionMapping );
        log.endStep();

        log.startStep( "Click the 'Update' button" );
        driver.click( "//div[@id='s_DOMWindow-footer']//span[contains(text(), 'Update')]", driver.timeOut );
        log.endStep();

        log.startStep( "Click the 'Save and Close' button" );
        driver.click( "//span[contains(text(),'Save and Close')]", driver.timeOut );
        log.endStep();
    }

    protected SurveyAttributes injectSurveyAttributeToken(
                                                           String surveyType,
                                                           String campaignName,
                                                           String surveyName ) {

        surveyAttributes = new SurveyAttributes();
        surveyAttributes.surveyType = surveyType;
        surveyAttributes.surveyName = surveyName;
        surveyAttributes.campaignType = campaignName;
        for( int i = 0; i < questionsArray.size(); i++ ) {
            AnswersAttributes _answer = new AnswersAttributes();
            QuestionsAttributes _question = new QuestionsAttributes();
            _answer.Answer = answersArray.get( i );
            _question.question = questionsArray.get( i );
            surveyAttributes.answerses.add( _answer );
            surveyAttributes.questions.add( _question );
        }

        for( int i = 0; i < campaignResponseArray.size(); i++ ) {
            CampaignResponseAttribute _campaignResponse = new CampaignResponseAttribute();
            _campaignResponse.ResponseCode = campaignResponseArray.get( i );
            surveyAttributes.CampaignResponseAttribute.add( _campaignResponse );
        }

        return surveyAttributes;
    }
}
