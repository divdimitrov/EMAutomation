/**
 * Test cases for SalesForce Surveys completion when connection has Leads enabled and 'Contact' entity set.
 * 
 * @author Petar.Bozhinov
 * 
 */

package concep.selenium.SalesForce;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;

import java.util.*;

public class SurveysCompletionWithLeadsEnabledAndContactEntity extends BaseSFDC {

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    private void createConnectionWithLeadsOnAndContactEntity(
                                                              @Optional("config/firefox.SFDC") String configLocation )
                                                                                                                      throws Exception {

        driver.init( configLocation );
        driver.url = driver.config.getProperty( "url" );
        sfdcSendUser = driver.config.getProperty( "sfdcSendUser" );
        sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", true );
        driver.stopSelenium();

    }

    /**
     * 
     * "Verify that Survey type Survey can be completed successfully with linked
     * questions which has leads enabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeSurveyWithLinkedQuestionsLeadsEnabledAndContact()
                                                                                                throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType1,
                                                                 sfdcCampaign1,
                                                                 true,
                                                                 Theme.DISCO.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="Lead";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Registration Form can be completed successfully
     * with linked questions which has leads enabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationFormWithLinkedQuestionsLeadsEnabledAndContact()
                                                                                                          throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType2,
                                                                 sfdcCampaign2,
                                                                 true,
                                                                 Theme.DEFAULT.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="Lead";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Feedback Form can be completed successfully with
     * linked questions which has leads enabled and contact entity options
     * 
     * @throws Exception
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeFeedbackFormWithLinkedQuestionsLeadsEnabledAndContact()
                                                                                                      throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType3,
                                                                 sfdcCampaign3,
                                                                 true,
                                                                 Theme.SPORT.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="Lead";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Questionnaire can be completed successfully with
     * linked questions which has leads enabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeQuestionnaireWithLinkedQuestionsLeadsEnabledAndContact()
                                                                                                       throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType4,
                                                                 sfdcCampaign4,
                                                                 true,
                                                                 Theme.RED.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="Lead";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * "Verify that Survey type Form can be completed successfully with linked
     * questions which has leads enabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeFormWithLinkedQuestionsLeadsEnabledAndContact()
                                                                                              throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType6,
                                                                 sfdcCampaign6,
                                                                 true,
                                                                 Theme.TABLE.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="Lead";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

    }

    /**
     * 
     * Verify that survey can be completed with 5 different questions
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWith5TypesOfQuestions() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "5TypesOfQuestions";
        String matrixValue1 = "Matrix Value 1";
        String matrixValue2 = "Matrix Value 2";
        String matrixValue3 = "Matrix Value 3";
        String matrixValue4 = "Matrix Value 3";

        log.startTest( "LeadEnable: Verify that survey can be completed with 5 different questions" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( true )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addNewQuestionType( "Matrix",
                                                                                                                                         element.matrixQId )
                                                                                                                    .enterQuestion( "Matrix" );

            log.startStep( "Click Add Row button" );
            driver.click( "//a[@id='qAddRow']", driver.timeOut );
            log.endStep();

            log.startStep( "Click Add Column button" );
            driver.click( "//a[@id='qAddColumn']", driver.timeOut );
            log.endStep();

            log.startStep( "Add value to the first row" );
            driver.type( "//ul[@id='qRowList']/li[1]/input[@type='text']", matrixValue1, driver.timeOut );
            log.endStep();

            log.startStep( "Add value to the second row" );
            driver.type( "//ul[@id='qRowList']/li[2]/input[@type='text']", matrixValue2, driver.timeOut );
            log.endStep();

            log.startStep( "Add value to the first column" );
            driver.type( "//ul[@id='qColumnList']/li[1]/input[@type='text']", matrixValue3, driver.timeOut );
            log.endStep();

            log.startStep( "Add value to the second column" );
            driver.type( "//ul[@id='qColumnList']/li[2]/input[@type='text']", matrixValue4, driver.timeOut );
            log.endStep();

            send.sfdcSurvey.sfdcQuestion.updateQuestion();
            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "results",
                                                                                          element.resultsQId )
                                                                     .enterQuestion( "Results" )
                                                                     .updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                           new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName );

            log.startStep( "Select answers in the Matrix question" );
            driver.click( "//tr[@class='sR']/td[contains(text(), '" + matrixValue1 + "')]//..//td[2]//input",
                          driver.timeOut );
            driver.click( "//tr[@class='sR']/td[contains(text(), '" + matrixValue2 + "')]//..//td[2]//input",
                          driver.timeOut );
            log.endStep();

            // Press Next
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);

            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();

    }

    /**
     * 
     * Verify that surveys can be successfully completed with drag and drop question and add question button.
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropAndAddQuestion() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "DragAndDropAndAddQuestion";

        log.startTest( "LeadEnable: Verify that surveys can be successfully completed with drag and drop question and add question button." );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType6, sfdcCampaign6 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true )
                                                                                                                    .dragAndDropFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.SPORT.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                           new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType6;
            surveyAttributes.campaign.name=sfdcCampaign6;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
            
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * Verify that surveys can successfully be completed with Drag and drop questions only.
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDragAndDropQuestionsOnly() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "DragAndDropQuestionsOnly";

        log.startTest( "LeadEnable: Verify that surveys can successfully be completed with Drag and drop questions only." );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.deleteMcQuestionType( "A multiple choice question" )
                                                                                                                    .dragAndDropFTQAndMapItToEmail( false )
                                                                                                                    .dragAndDropMCquestionAndMapItToSalutation( true )
                                                                                                                    .dragAndDropFTQAndMapItToLastName( false )

                                                                                                                    .deleteFtQuestionType( "A free text question" );
            send.sfdcSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                          new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                          emailAddress )
                                                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                          lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();

    }

    /**
     * 
     * Verify that survey can be completed with an existing contact can successfully be updated
     * 
     * @sfdcExistingContact is hardcoded at the config file
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "updating-data", "key-tests" })
    public void successfullyCompletedSurveyWithExistingContact() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = sfdcExistingContact;
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String surveyName = number + "UpdatingContact";

        log.startTest( "LeadEnable: Verify that survey can be completed with an existing contact can successfully be updated" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType2, sfdcCampaign2 );
            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToEmail( false )
                                        .editMCquestionAndMapItToLeadSource( true )
                                        .addFTQAndMapItToLastName( false )
                                        .addFTQAndMapItToFirstName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.FUNKY.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                           new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName )
                                                                                                        .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                           firstName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType2;
            surveyAttributes.campaign.name=sfdcCampaign2;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Contact";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);

            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(firstName);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed with the following special characters separately ! # $ % & ' * + - / = ? ^ _ ` { | } ~
     * 
     * throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDifferentSpecialCharacters() throws Exception {

        String surveyNumber = driver.getTimestamp();
        String surveyName = surveyNumber + "SpecialCharactersInEmails";

        log.startTest( "LeadEnable: Verify that survey can be completed with the following special characters separately ! # $ % & ' * + - / = ? ^ _ ` { | } ~" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( true )
                                                                                                                    .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.AQUA.type );

            //! # $ % & ' * + - / = ? ^ _ ` { | } ~            
            String[] specialCharacters = { "!",
                    "#",
                    "$",
                    "%",
                    "'",
                    "&",
                    "+",
                    "-",
                    "/",
                    "=",
                    "?",
                    "^",
                    "_",
                    "`",
                    "{",
                    "}",
                    "|",
                    "~",
                    "." };

            for( int i = 0; i < specialCharacters.length; i++ ) {
                String number = driver.getTimestamp();
                String emailAddress = number + "test" + specialCharacters[i] + "email@concep.com";
                String lastName = number + "lastName";

                send.sfdcSurvey.viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                        new String[]{ "Partner" } )
                                                                     .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                        emailAddress )
                                                                     .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                        lastName );
                send.sfdcSurvey.surveyNext();
                
                surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);

                surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_2);
                surveyAttributes.answers.add(emailAddress);
                surveyAttributes.answers.add(lastName);
                
                surveyAttributes.email.add(emailAddress);

                driver.close();

                driver.selectWindow();

            }
            driver.close();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that after a survey is completed when opened again you get the proper warning message
     * 
     * throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedReopenedSurveyMappingWarningMessageVerification() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "CompletedSurveyWarningMessage";

        log.startTest( "LeadEnable: Verify that after a survey is completed when opened again you get the proper warning message" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( true )
                                                                                                                    .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( "Table" ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                  new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                                               .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                  emailAddress )
                                                                                               .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                  lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType3;
            surveyAttributes.campaign.name=sfdcCampaign3;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);

            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

            log.startStep( "Close the survey pop-up page" );
            driver.close();
            driver.selectWindow();
            log.endStep();

            send.goToSurveyPage();

            log.startStep( "Open the survey with name " + surveyName );
            driver.click( "//td/a[contains(text(), '" + surveyName + "')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify the Warning Message" );
            log.endStep( driver.isElementPresent( "//p[@class='s_form-instructionText']", driver.timeOut ) );

            log.startStep( "Click on the OK button" );
            driver.click( "//span[contains(text(), 'OK')]", driver.timeOut );
            log.endStep();

            log.startStep( "Verify that 'Log responses in your CRM system' is checked" );
            log.endStep( verifyLogResponseInCRM() );

            log.startStep( "Verify that the survey type is retained as " + surveyType3 );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep( driver.isSelected( "//select[@id='surveyTypeDropDown']", surveyType3 ) );

            log.startStep( "Verify that the campaign type is retained as " + sfdcCampaign3 );
            log.endStep( driver.isSelected( "//select[@id='surveySalesForceCampaigns']", sfdcCampaign3 ) );
            
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithRequiredFTQ() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithRequiredQuestion";
        String lastName = number + "lastName";
        String emailAddress = number + "email@concep.com";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with required free text questions" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true )
                                                                                                                    .addFTQAndMapItToLastName( true );
            send.sfdcSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey();
            log.startStep( "Verify the validation of the required free text question" );
            driver.click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", driver.timeOut );
            log.endStep( driver.isTextPresent( "//div[@class='sQ sText sTextSingle']//label[@class='qError']",
                                               "This field is required",
                                               driver.timeOut ) );
            send.sfdcSurvey.sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                           new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                        .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                        .answerFtQuestion( FTcontactField.LASTNAME.question, lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithBusinessRequiredMappings() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "BusinessRequiredMappings";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String company = number + "company";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with First name, Last Name, Job Title and Email Address free text questions" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( true )
                                                                                                                    .editMCquestionAndMapItToSalutation( true )
                                                                                                                    .addFTQAndMapItToLastName( true )
                                                                                                                    .addFTQAndMapItToFirstName( true )
                                                                                                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                                  FTcontactField.COMPANY,
                                                                                                                                                  true,
                                                                                                                                                  true );
            send.sfdcSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                  new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                               .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                  emailAddress )
                                                                                               .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                  lastName )
                                                                                               .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                  firstName )
                                                                                               .answerFtQuestion( FTcontactField.COMPANY.question,
                                                                                                                  company );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(FTcontactField.COMPANY.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(firstName);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(company);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithMCQWithDropDownAndLastResponseMapped() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String surveyName = number + "MCQWithDropDown";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with MCQ with drop down and the last response is mapped for the Lead" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question,
                                                                                                                                              QuestionStatus.EDIT )
                                                                                                                    .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                       MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .addMCQStatusOptions()
                                                                                                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.DROPDOWN )
                                                                                                                    .updateQuestion()
                                                                                                                    .addFTQAndMapItToFirstName(false);
            send.sfdcSurvey.saveAndSelectTheme( Theme.WATER.type );
            
            for(int i = 0; i < 5; i++){
            
            	send.sfdcSurvey.viewAndDeployTheSurvey().sfdcQuestion.answerDropDownQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                                 "Partner" )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName + "Update" + i )
                                                                                                        .answerFtQuestion(FTcontactField.FIRSTNAME.question,
                                                                                                        				   firstName + "Update" + i);	
            	send.sfdcSurvey.surveyNext();
            	
            	surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            	
            	surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_2);
                surveyAttributes.answers.add(emailAddress);
                surveyAttributes.answers.add(firstName);
                surveyAttributes.answers.add(lastName);
                
                surveyAttributes.email.add(emailAddress);
            
            	driver.close();

                driver.selectWindow();
            }
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType3;
            surveyAttributes.campaign.name=sfdcCampaign3;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
            
            driver.close();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithMCQWithOtherAnswer() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithOtherAnswer";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with survey which has other answer and the survey response will be"
                       + "logged successfully with the relevant information except the one for the 'Other answer'" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType2, sfdcCampaign2 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question,
                                                                                                                                              QuestionStatus.EDIT )
                                                                                                                    .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                       MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .addMCQStatusOptions()
                                                                                                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.OTHERANSWER )
                                                                                                                    .updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( Theme.FUNKY.type ).viewAndDeployTheSurvey();

            // Answer the Survey questions
            log.startStep( "Select Other Answer option of the " + MCcontactFiled.LEADSOURCE.question
                           + " question " );
            driver.click( "//div[@id='q_34281']//label[3]", driver.timeOut );
            driver.type( "//input[@id='q_34281_o']", "other Answer", driver.timeOut );
            log.endStep();

            send.sfdcSurvey.sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                        .answerFtQuestion( FTcontactField.LASTNAME.question, lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType2;
            surveyAttributes.campaign.name=sfdcCampaign2;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);

            surveyAttributes.answers.add("other Answer");
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithMCQWithMultipleAnswers() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithMultipleAnswers";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with multiple answer enabled" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType2, sfdcCampaign2 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question,
                                                                                                                                              QuestionStatus.EDIT )
                                                                                                                    .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                       MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .addMCQStatusOptions()
                                                                                                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.MULTIPLEANSWERS )
                                                                                                                    .updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                  new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                                               .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                  emailAddress )
                                                                                               .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                  lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType2;
            surveyAttributes.campaign.name=sfdcCampaign2;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);

            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithRequiredMCQ() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithRequiredQuestion";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with required MCQ question" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question,
                                                                                                                                              QuestionStatus.EDIT )
                                                                                                                    .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                       MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                                  MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_1,
                                                                                                                                               MCcontactFiled.LEADSOURCE.option_2 )
                                                                                                                    .addMCQStatusOptions()
                                                                                                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.MANADATORY )
                                                                                                                    .updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                           new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);

            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    public void submitSurveyWithMultipleLanguages(
                                                   String[] languages,
                                                   String surveyLanguage ) throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "SurveyWithLanguagePages";

        try {
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true );
            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages )
                                                       .saveSurvey();
            send.goToSurveyTab( "Questions" );
            for( int i = 0; i < ( languages.length ); i++ ) {
                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] ).sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                                                                   .enterQuestion( FTcontactField.EMAIL.question
                                                                                                   + " - "
                                                                                                   + languages[i] )
                                                                                   .updateQuestion()
                                                                                   .editQuestionType( FTcontactField.LASTNAME.question )
                                                                                   .enterQuestion( FTcontactField.LASTNAME.question
                                                                                                   + " - "
                                                                                                   + languages[i] )
                                                                                   .updateQuestion()
                                                                                   .editQuestionType( MCcontactFiled.SALUTATION.question )
                                                                                   .enterQuestion( MCcontactFiled.SALUTATION.question
                                                                                                   + " - "
                                                                                                   + languages[i] )
                                                                                   .updateQuestion();
                
                surveyAttributes.questions.add(FTcontactField.EMAIL.question
                        + " - "
                        + languages[i]);
                surveyAttributes.questions.add(FTcontactField.LASTNAME.question
                        + " - "
                        + languages[i]);
                surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question
                        + " - "
                        + languages[i]);
                
            }
            send.sfdcSurvey.saveAndSelectTheme( Theme.BEACH.type )
                           .viewAndDeployTheSurvey()
                           .switchSurveySubmitionPageLanguage( surveyLanguage ).sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                               new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                            .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                               emailAddress )
                                                                                            .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                               lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[1].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithAdditionalLanguagePages() throws Exception {

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with additional language pages" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian" }, "English" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithThreeAdditionalLanguagePages() throws Exception {

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with three additional language pages" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian", "Arabic" }, "English" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyUsingSecondaryLanguage() throws Exception {

        log.startTest( "LeadEnable: Verify that survey can be successfully submitted via the secondary language" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian" }, "Bulgarian" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyUsingThirdLanguage() throws Exception {

        log.startTest( "LeadEnable: Verify that survey can be successfully submitted via the Third language" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian", "Arabic" }, "Arabic" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";
        String lastNameSecond = randNumber + "lastNameSecond";
        int submissionsCount = 20;
        
        int idFTQ1;
        int idFTQ2;
        int idMCQ1;
        int idMCQ2;
        
        Map<Integer, String> duplicatedFTQids = new HashMap<Integer, String>();
        Map<Integer, String> duplicatedMCQids = new HashMap<Integer, String>();

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same page" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion();
            
            idFTQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ1, lastNameFirst);
            
            send.sfdcSurvey.sfdcQuestion.editMCquestionAndMapItToSalutation( true );
            
            idMCQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ1, MCcontactFiled.SALUTATION.option_1);
            
            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion();
            
            idFTQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ2, lastNameSecond);
            
            send.sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();
            
            idMCQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ2, MCcontactFiled.SALUTATION.option_2);

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy();
            
            for (int i = 0; i < submissionsCount; i++) {
				
            	send.sfdcSurvey.viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																                        new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																     .answerFtQuestion( FTcontactField.EMAIL.question,
																                        i + email )
																     .answerFtQuestion( FTcontactField.LASTNAME.question,
																                        lastNameFirst )
																     .answerFtQuestion( "Last Name Duplicated",
																                        lastNameSecond )
																     .answerMcQuestion( "Salutation Duplicated",
																                        new String[]{ MCcontactFiled.SALUTATION.option_2 } );
            	
				send.sfdcSurvey.surveyNext();
				
				surveyAttributes.answers.add(duplicatedMCQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids)));
	            surveyAttributes.answers.add(i + email);
	            surveyAttributes.answers.add(duplicatedFTQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids)));
	            
	            surveyAttributes.email.add(i + email);
				
				driver.close();
                driver.selectWindow();
			}
                        
            log.resultStep("Highest FTQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids) +
            		" and has an answer of: " +  duplicatedFTQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids)));
        	log.endStep();
        	
        	log.resultStep("Highest MCQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids) +
            		" and has an answer of: " +  duplicatedMCQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids)));
        	log.endStep();
        	
        	surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsDifferentPages()
                                                                                               throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";
        String lastNameSecond = randNumber + "lastNameSecond";
        int submissionsCount = 20;
        
        int idFTQ1;
        int idFTQ2;
        int idMCQ1;
        int idMCQ2;
        
        Map<Integer, String> duplicatedFTQids = new HashMap<Integer, String>();
        Map<Integer, String> duplicatedMCQids = new HashMap<Integer, String>();

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on different pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion();
            
            idFTQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ1, lastNameFirst);
            
            send.sfdcSurvey.sfdcQuestion.editMCquestionAndMapItToSalutation( true );
            
            idMCQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ1, MCcontactFiled.SALUTATION.option_1);

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Last Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                     .updateQuestion();
            
            idFTQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ2, lastNameSecond);
            
            send.sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();
            
            idMCQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ2, MCcontactFiled.SALUTATION.option_2);

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy();
            
            for (int i = 0; i < submissionsCount; i++) {
				
            	send.sfdcSurvey.viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																                        new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																     .answerFtQuestion( FTcontactField.EMAIL.question,
																                        i + email )
																     .answerFtQuestion( FTcontactField.LASTNAME.question,
																                        lastNameFirst );
																
			    send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( "Last Name Duplicated",
																            lastNameSecond )
																.answerMcQuestion( "Salutation Duplicated",
																            new String[]{ MCcontactFiled.SALUTATION.option_2 } );
																
				send.sfdcSurvey.surveyNext();
				driver.close();
                driver.selectWindow();
			}            
            
            log.resultStep("Highest FTQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids) +
            		" and has an answer of: " +  duplicatedFTQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids)));
        	log.endStep();
        	
        	log.resultStep("Highest MCQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids) +
            		" and has an answer of: " +  duplicatedMCQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids)));
        	log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastNameFirst";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on the same page" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                    new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstNameSecond = randNumber + "firstNameSecond";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on the same page" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.FIRSTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "First Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName )
                                                                 .answerFtQuestion( "First Name Duplicated",
                                                                                    firstNameSecond )
                                                                 .answerMcQuestion( "Salutation Duplicated",
                                                                                    new String[]{ MCcontactFiled.SALUTATION.option_2 } );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add("First Name Duplicated");
            surveyAttributes.questions.add("Salutation Duplicated");

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstNameSecond);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on different pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Last Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                    new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastNameFirst );

            send.sfdcSurvey.surveyNext().surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastNameFirst);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstNameSecond = randNumber + "firstNameSecond";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on different pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.FIRSTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "First Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );

            send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( "First Name Duplicated",
                                                                        firstNameSecond )
                                                     .answerMcQuestion( "Salutation Duplicated",
                                                                        new String[]{ MCcontactFiled.SALUTATION.option_2 } );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add("First Name Duplicated");
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add("Salutation Duplicated");

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstNameSecond);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on the same page" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.FIRSTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "First Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);


        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on different pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.FIRSTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "First Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                       email )
                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                       lastName );

            send.sfdcSurvey.surveyNext().surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyUpdateExistingContactFirstLastNameLeadEnabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_Contact_test3@mail.bg";
        String firstName = randNumber + "firstNameUpdate";
        String lastName = randNumber + "lastNameUpdate";

        log.startTest( "Verify that existing Contact's First name and Last name can be updated when the Lead is enabled" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToFirstName(false)
            							.addFTQAndMapItToLastName(false)
            							.addFTQAndMapItToEmail(false);

            send.sfdcSurvey.saveAndContinueToTheme()
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName )
                                                                 .answerFtQuestion(FTcontactField.FIRSTNAME.question,
                                                                		 			firstName);
                                                                 
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.personType="Contact";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstName);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyUpdateExistingContactMCQLeadEnabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_Contact_test4@mail.bg";

        log.startTest( "Verify that existing Contact's MCQ can be updated when the Lead is enabled" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign2 );

            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToEmail(false)
            							.editMCquestionAndMapItToLeadSource(true)
            							.addMCquestionAndMapItToSalutation(true);
            							

            send.sfdcSurvey.saveAndContinueToTheme()
                           .selectTheme( Theme.DISCO.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerMcQuestion(MCcontactFiled.LEADSOURCE.question,
                                                                		 			new String[] { MCcontactFiled.LEADSOURCE.option_1 })
                                                                 .answerMcQuestion(MCcontactFiled.SALUTATION.question,
                                                                		 			new String[] { MCcontactFiled.SALUTATION.option_2 });
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign2;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Contact";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            
            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            surveyAttributes.answers.add(email);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyUpdateExistingContactFTQandMCQLeadEnabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_Contact_test5@mail.bg";
        String lastName = randNumber + "lastNameUpdate";
        String firstName = randNumber + "firstNameUpdate";

        log.startTest( "Verify that existing Contact's FTQ and MCQ can be updated when the Lead is enabled" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToLastName(false)
            							.editMCquestionAndMapItToLeadSource(true)
            							.addMCquestionAndMapItToSalutation(true)
            							.addFTQAndMapItToEmail(false)
            							.addFTQAndMapItToFirstName(false);
            							

            send.sfdcSurvey.saveAndContinueToTheme()
                           .selectTheme( Theme.SPORT.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName )
                                                                 .answerFtQuestion(FTcontactField.FIRSTNAME.question,
                                                                		 			firstName)                  
                                                                 .answerMcQuestion(MCcontactFiled.LEADSOURCE.question,
                                                                		 			new String[] { MCcontactFiled.LEADSOURCE.option_1 })
                                                                 .answerMcQuestion(MCcontactFiled.SALUTATION.question,
                                                                		 			new String[] { MCcontactFiled.SALUTATION.option_2 });
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType3;
            surveyAttributes.campaign.name=sfdcCampaign3;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Contact";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            
            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(firstName);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();        
    }    
    
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompleteSurveyWithMappedQuestionsOnlyForLead() throws Exception {

    	String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        
        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstName = randNumber + "firstName";
         
        log.startTest( "Verify that a survey response with all the information and no missing questions or answers will be "
        		+ "logged successfully into CRM when survey has questions mapped to Lead only - [Contact Enabled]" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
							            .enterQuestion( FTcontactField.EMAIL.question )
							            .mapFTQuestionToLeadField(FTcontactField.EMAIL.conntactFiled)							            
							            .updateQuestion()
							            .addNewQuestionType( "Free Text", element.ftQId )
							            .enterQuestion( FTcontactField.LASTNAME.question )
							            .mapFTQuestionToLeadField( FTcontactField.LASTNAME.conntactFiled )
							            .mapFTQuestionToContactField("Please select")
							            .updateQuestion()
							            .addNewQuestionType( "Free Text", element.ftQId )
							            .enterQuestion( FTcontactField.FIRSTNAME.question )
							            .mapFTQuestionToLeadField( FTcontactField.FIRSTNAME.conntactFiled )
							            .mapFTQuestionToContactField("Please select")
							            .updateQuestion()
							            .addOrUpdateMCQuestionBy( MCcontactFiled.SALUTATION.question, QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question, QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                           MCcontactFiled.LEADSOURCE.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
                                                                   MCcontactFiled.LEADSOURCE.option_1,
                                                                   MCcontactFiled.LEADSOURCE.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();
            
            send.sfdcSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
						                    new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
						   .answerFtQuestion( FTcontactField.EMAIL.question, email )
						   .answerFtQuestion( FTcontactField.LASTNAME.question, lastName)
						   .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName)
						   .answerMcQuestion( MCcontactFiled.SALUTATION.question, new String[]{ MCcontactFiled.SALUTATION.option_1 } )
						   .answerMcQuestion( MCcontactFiled.LEADSOURCE.question, new String[]{ MCcontactFiled.LEADSOURCE.option_1 } );
			
            send.sfdcSurvey.surveyNext();
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();        
    }    
}
