/**
 * Test cases for SalesForce Surveys completion when connection has Leads disabled and 'Contact' entity set.
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
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;
import java.util.*;

public class SurveysCompletionWithLeadsDisabledAndContactEntity extends BaseSFDC {

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    private void createConnectionWithLeadsOffAndContactEntity(
                                                               @Optional("config/firefox.SFDC") String configLocation )
                                                                                                                       throws Exception {

        driver.init( configLocation );
        driver.url = driver.config.getProperty( "url" );
        sfdcSendUser2 = driver.config.getProperty( "sfdcSendUser2" );
        sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
        sfdcSendUser = driver.config.getProperty( "sfdcSendUser" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", false );
        driver.stopSelenium();
    }

    /**
     * 
     * "Verify that Survey type Survey can be completed successfully with linked
     * questions which has leads disabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests"})
    public void successfullyCompletedSurveyTypeSurveyWithLinkedQuestionsLeadsDisabledAndContact()
                                                                                                 throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType1,
                                                                 sfdcCampaign1,
                                                                 false,
                                                                 Theme.DISCO.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Registration Form can be completed successfully
     * with linked questions which has leads disabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationFormWithLinkedQuestionsLeadsDisabledAndContact()
                                                                                                           throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType2,
                                                                 sfdcCampaign2,
                                                                 false,
                                                                 Theme.RED.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Feedback Form can be completed successfully with
     * linked questions which has leads disabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeFeedbackFormWithLinkedQuestionsLeadsDisabledAndContact()
                                                                                                       throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType3,
                                                                 sfdcCampaign3,
                                                                 false,
                                                                 Theme.DEFAULT.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Questionnaire can be completed successfully with
     * linked questions which has leads disabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeQuestionnaireWithLinkedQuestionsLeadsDisabledAndContact()
                                                                                                        throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType4,
                                                                 sfdcCampaign4,
                                                                 false,
                                                                 Theme.SPORT.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * "Verify that Survey type Form can be completed successfully with linked
     * questions which has leads disabled and contact entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeFormWithLinkedQuestionsLeadsDisabledAndContact()
                                                                                               throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType6,
                                                                 sfdcCampaign6,
                                                                 false,
                                                                 Theme.BEACH.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that survey with 3 pages can be successfully completed
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWith3Pages() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String surveyName = number + "3Pages";

        log.startTest( "LeadDisable: Verify that survey with 3 pages is successfully completed" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( false );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addFTQAndMapItToLastName( false )
                                                                     .deleteContentQuestionType( "Thank you" )

                                                                     // Add 3rd Free Text Question For First Name
                                                                     .addFTQAndMapItToFirstName( false );
            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addContentQuestion( false, "Thank you" );
            send.sfdcSurvey.saveAndSelectTheme( Theme.DISCO.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                           new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress );
            send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                        firstName )
                                                     .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                        lastName );
            send.sfdcSurvey.surveyNext();

            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
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
     * Verify that the super user can use the option 'login as' with different account and successfully complete a survey
     * 
     * FirstName is valid for TestAutomation_QA_Survey_SalesForce user, if you want to run this test with another user, edit the Firstname
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithLoginAs() throws Exception {

        String FirstName = "Test";
        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "loginAs";

        log.startTest( "LeadDisable: Verify that the super user can use the option 'login as' with different account and successfully complete a survey" );
        try {
            send.loginToSend( sendSuperUser, sendSuperPassword ).goToAdmin();

            log.startStep( "Click the 'Users' tab" );
            driver.click( "//ul[@id='typesMenu']//span[contains(text(),'Users')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the 'User' page is loaded" );
            log.endStep( driver.isElementPresent( "//li[@id='s_nav-tabsUsers']//span[contains(text(),'Users')]",
                                                  driver.timeOut ) );

            log.startStep( "Type '" + sfdcSendUser2 + "' in the search text box" );
            driver.type( "//input[@id='txtSearch']", sfdcSendUser2, driver.timeOut );
            log.endStep();

            log.startStep( "Click the 'Search' button" );
            driver.click( "//a[@id='btnSearch']", driver.timeOut );
            log.endStep();

            log.startStep( "Click '" + sfdcSendUser2 + "' Tools button" );
            driver.click( "//tr//td[contains(a,'" + sfdcSendUser2 + "')]/../td/a[contains(text(),'Tools')]",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Select 'Login as' option " );
            driver.click( "//ul[@id='p_users_user']//a[contains(text(),'Login As')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Type 'For Testing' the reason quired to login in the text box" );
            driver.type( "//input[@id[starts-with(.,'di_')]]", "For Testing", driver.timeOut );
            log.endStep();

            log.startStep( "Click on 'login' button" );
            driver.click( "//a[@class='s_button']/span[contains(text(),'Login')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the user is successfully logged in '" + sfdcSendUser2 + "'" );
            log.endStep( driver.isElementPresent( "//li[contains(text(),'" + FirstName + "')]",
                                                  driver.timeOut ) );
            // Create a survey
            send.goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                            .checkLogResponseInCRM()
                                            .selectSurveyFolders( surveyType2, sfdcCampaign2 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                           .editMCquestionAndMapItToLeadSource( false )
                                                                                                           .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
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
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

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

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithContentQuestionWithLink() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "WithContentQuestionWithLink";
        String link = "http://www.lipsum.com/feed/html";

        log.startTest( "LeadDisable: Verify that survey can be completed with content question which has link and the link is click-able" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( false )

                                                                                                                    .addNewQuestionType( "content",
                                                                                                                                         element.contentQId )
                                                                                                                    .enterQuestion( " " );

            log.startStep( "Open the Collabsed toolbar text edittor for the question" );
            driver.click( "//div[@id='cke_qText']//span[@class='cke_arrow']", driver.timeOut );
            log.endStep();

            log.startStep( "Click on the create link button" );
            driver.click( "//span[@class='cke_button_icon cke_button__link_icon']", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Add a link" );
            driver.waitForPageToLoad();
            driver.switchToIframeByID( "DOMWindowIframe" );
            driver.clear( "//input[@id='createLinktxtURL']" );
            driver.type( "//input[@id='createLinktxtURL']", link, driver.timeOut );
            log.endStep();

            log.startStep( "Press the 'Create Link to URL' button" );
            driver.click( "//a[@id='createLinkbtnAddURL']/span", driver.timeOut );
            driver.switchToTopWindow();
            log.endStep();

            send.sfdcSurvey.sfdcQuestion.updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                           new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName );

            // Verify that the link is working
            log.startStep( "Verify that the link is working" );
            log.endStep( driver.isClickable( "//a[contains(text(), 'http://www.lipsum.com/feed/html')]",
                                             driver.timeOut ) );
            send.sfdcSurvey.surveyNext();

            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="None";

            surveyAttributes.questions.add( MCcontactFiled.SALUTATION.question);
            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

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

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
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
            
            send.sfdcSurvey.sfdcQuestion.editMCquestionAndMapItToSalutation( false );
            
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
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add("");
            surveyAttributes.personType="";

            surveyAttributes.questions.add("");
            surveyAttributes.answers.add("");
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
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
            
            send.sfdcSurvey.sfdcQuestion.editMCquestionAndMapItToSalutation( false );
            
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

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
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
                                        .editMCquestionAndMapItToSalutation( false )
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
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

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

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
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
                                        .editMCquestionAndMapItToSalutation( false )
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
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add( "First Name Duplicated");
            surveyAttributes.questions.add( FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add( "Salutation Duplicated");

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstNameSecond);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            
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
                                        .editMCquestionAndMapItToSalutation( false );

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
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add( FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add( MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastNameFirst);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            
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
                                        .editMCquestionAndMapItToSalutation( false );

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
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add( "First Name Duplicated");
            surveyAttributes.questions.add( FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add( "Salutation Duplicated");

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstNameSecond);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            
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
                                        .editMCquestionAndMapItToSalutation( false )
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
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add( FTcontactField.LASTNAME.question);

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
                                        .editMCquestionAndMapItToSalutation( false );

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
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add( FTcontactField.LASTNAME.question);

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
    
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyUpdateExistingContactFirstLastNameLeadDisabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_Contact_test1@mail.bg";
        String firstName = randNumber + "firstNameUpdate";
        String lastName = randNumber + "lastNameUpdate";

        log.startTest( "Verify that existing Contact's First name and Last name can be updated when the Lead is disabled" );
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
            surveyAttributes.questions.add( FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add( FTcontactField.LASTNAME.question);

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
    public void successfullyUpdateExistingContactMCQLeadDisabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_Contact_test2@mail.bg";

        log.startTest( "Verify that existing Contact's MCQ can be updated when the Lead is disabled" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToEmail(false)
            							.editMCquestionAndMapItToLeadSource(false)
            							.addMCquestionAndMapItToSalutation(false);

            send.sfdcSurvey.saveAndContinueToTheme()
                           .selectTheme( Theme.AQUA.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerMcQuestion(MCcontactFiled.LEADSOURCE.question,
                                                                		 			new String[] { MCcontactFiled.LEADSOURCE.option_1 })
                                                                 .answerMcQuestion(MCcontactFiled.SALUTATION.question,
                                                                		 			new String[] { MCcontactFiled.SALUTATION.option_2 });
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Contact";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
