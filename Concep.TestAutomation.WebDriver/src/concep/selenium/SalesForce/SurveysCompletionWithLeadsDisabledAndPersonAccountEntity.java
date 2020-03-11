/**
 * Test cases for SalesForce Surveys completion when connection has Leads disabled and 'Person Account' entity set.
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

public class SurveysCompletionWithLeadsDisabledAndPersonAccountEntity extends BaseSFDC {

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    private void createConnectionWithLeadsOffAndPersonAccountEntity(
                                                                     @Optional("config/firefox.SFDC") String configLocation )
                                                                                                                             throws Exception {

        driver.init( configLocation );
        driver.url = driver.config.getProperty( "url" );
        sfdcSendUser = driver.config.getProperty( "sfdcSendUser" );
        sfdcSendUser2 = driver.config.getProperty( "sfdcSendUser2" );
        sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Person account", false );
        driver.stopSelenium();
    }

    /**
     * 
     * Verify that Survey type Survey can be completed successfully with linked
     * questions which has leads disabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyWithLinkedQuestionsLeadsDisabledAndPerson()
                                                                                                throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType1,
                                                                 sfdcCampaign1,
                                                                 false,
                                                                 Theme.BEACH.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Registration Form can be completed successfully
     * with linked questions which has leads disabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationFormWithLinkedQuestionsLeadsDisabledAndPerson()
                                                                                                          throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType2,
                                                                 sfdcCampaign2,
                                                                 false,
                                                                 Theme.DEFAULT.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Feedback Form can be completed successfully with
     * linked questions which has leads disabled and person account entity options
     * 
     * @throws Exception
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeFeedbackFormWithLinkedQuestionsLeadsDisabledAndPerson()
                                                                                                      throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType3,
                                                                 sfdcCampaign3,
                                                                 false,
                                                                 Theme.DISCO.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Questionnaire can be completed successfully with
     * linked questions which has leads disabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeQuestionnaireWithLinkedQuestionsLeadsDisabledAndPerson()
                                                                                                       throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType4,
                                                                 sfdcCampaign4,
                                                                 false,
                                                                 Theme.RED.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     * 
     * Verify that Survey type Form can be completed successfully with linked
     * questions which has leads disabled and person account entity options
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeFormWithLinkedQuestionsLeadsDisabledAndPerson()
                                                                                              throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( surveyType6,
                                                                 sfdcCampaign6,
                                                                 false,
                                                                 Theme.SPORT.type );
        StackTraceElement[] stack = new Throwable().getStackTrace();
        surveyAttributes.personType="None";
        json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
    }

    /**
     *
     * Verify that survey can be successfully completed with apostrophe in the email address
     * 
     * throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithApostrophe() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email'@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "ApostropheInEmail";

        log.startTest( "LeadDisable: Verify that survey can be successfully completed with apostophe in the email address" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( false )
                                                                                                                    .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
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
            surveyAttributes.personType="None";

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

    private void completedSurveyWithContentQuestion(
                                                     String description,
                                                     boolean isDragAndDrop ) throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "ContentQuestion-" + description;
        String content = "Nam elementum in enim id posuere. Praesent sed augue non elit accumsan tempor a a sapien. Morbi ac maximus enim. Maecenas et varius ligula. Vestibulum ornare efficitur ipsum. Fusce vel neque gravida, posuere augue finibus, pharetra eros. Sed varius in libero nec dictum. Vestibulum sollicitudin et ante porttitor luctus. Nunc a diam nec tortor efficitur aliquet.";

        log.startTest( "LeadDisable: Verify that survey can be successfully completed with additionally added "
                       + description + "content question" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addContentQuestion( isDragAndDrop,
                                                                                                                                         content );
            send.sfdcSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                  new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                               .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                  emailAddress )
                                                                                               .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                  lastName );

            // Verify the content
            log.startStep( "Verify the content question content is visible" );
            log.endStep( driver.isTextPresent( "//div[@class='sQ sContent']", content, driver.timeOut ) );

            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="None";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

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
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithAdditionalContentQuestion() throws Exception {

        completedSurveyWithContentQuestion( "normal", false );

    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropContentQuestion() throws Exception {

        completedSurveyWithContentQuestion( "DragAndDrop", true );
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
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
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

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
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

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
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
    public void successfullyUpdateExistingPAccountFirstLastNameLeadDisabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_PAccount1_test1@mail.bg";
        String firstName = randNumber + "firstNameUpdate";
        String lastName = randNumber + "lastNameUpdate";

        log.startTest( "Verify that existing Person Account's First name and Last name can be updated when the Lead is disabled" );
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
            surveyAttributes.personType="Account";

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
    public void successfullyUpdateExistingPAccountMCQLeadDisabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_PAccount1_test2@mail.bg";

        log.startTest( "Verify that existing Person Account's MCQ can be updated when the Lead is disabled" );
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
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Account";

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
