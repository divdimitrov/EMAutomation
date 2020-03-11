/*

*/

package concep.selenium.Dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import concep.selenium.Dynamic.Attributes.SurveyAttributes;
import concep.selenium.send.MSD4Questions;
import concep.selenium.send.SendEnum;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;

public class SurveyCompletion extends BaseMSD {
    /**
     * 
     * Data driven test which verify that surveys can be completed with each survey type
     * 
     * @throws Exception
     * 
     */
    private void completeSurveyWithLinkedQuestionsAndNonExistingContacts(
                                                                          String testDescription,
                                                                          String surveyType,
                                                                          String campaignName,
                                                                          SendEnum.MCcontactFiled contactField,
                                                                          String theme ) throws Exception {

        log.startTest( testDescription );
        try {

            String number = driver.getTimestamp();
            String emailAddress = number + "email@concep.com";
            String lastName = number + "lastName";
            String surveyName = number + surveyType + campaignName;

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType, campaignName ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                                                                                                            contactField );
            send.msdSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( contactField.question,
                                                                                                                  new String[]{ contactField.option_1 } )
                                                                                               .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                  emailAddress )
                                                                                               .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                  lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( contactField.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( contactField.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType,
                                                                           campaignName,
                                                                           surveyName );
            
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[1].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();

    }

    /**
     * 
     * Verify that Survey type Survey can be completed successfully 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeSurvey() throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( "Verify that Survey type "
                                                                         + surveyType1
                                                                         + " can be completed successfully with non existing user",
                                                                 surveyType1,
                                                                 campaignName1,
                                                                 MCcontactFiled.GENDER,
                                                                 Theme.AQUA.type );
    }

    /**
     * 
     * Verify that Survey type Registration Form can be completed successfully
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationForm() throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( "Verify that Survey type "
                                                                         + surveyType2
                                                                         + " can be completed successfully with non existing user",
                                                                 surveyType2,
                                                                 campaignName2,
                                                                 MCcontactFiled.ROLE,
                                                                 Theme.BEACH.type );
    }

    /**
     * 
     * Verify that Survey type Feedback Form can be completed successfully 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeFeedbackForm() throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( "Verify that Survey type "
                                                                         + surveyType3
                                                                         + " can be completed successfully with non existing user",
                                                                 surveyType3,
                                                                 campaignName3,
                                                                 MCcontactFiled.MARITALSTATUS,
                                                                 Theme.WATER.type );
    }

    /**
     * 
     * Verify that Survey type Questionnaire can be completed successfully 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeQuestionnaire() throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( "Verify that Survey type "
                                                                         + surveyType4
                                                                         + " can be completed successfully with non existing user",
                                                                 surveyType4,
                                                                 campaignName4,
                                                                 MCcontactFiled.GENDER,
                                                                 Theme.DEFAULT.type );
    }

    /**
     * 
     * Verify that Survey type Poll can be completed successfully 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypePoll() throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingContacts( "Verify that Survey type "
                                                                         + surveyType5
                                                                         + " can be completed successfully with non existing user",
                                                                 surveyType5,
                                                                 campaignName5,
                                                                 MCcontactFiled.ROLE,
                                                                 Theme.DISCO.type );
    }

    /**
     * 
     * Verify that survey with 10 mapped questions can be completed successfully 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyWith10MappedQuestions() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "10Question";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";

        log.startTest( "Verify that survey with 10 mapped questions can be completed successfully" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType1, campaignName1 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToROLE()
                                                                                                                      .addNewQuestionType( "Free text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( FTcontactField.LASTNAME.question );

            driver.showHiddenElement( "qTypetextDynamicsContactsField select" );

            log.startStep( "Get the values of the drop down for the for loop" );
            String[] contactsDropDown = driver.getDropDownOptions( "//label[@id='qTypetextDynamicsContactsField']/select" );
            log.endStep();

            driver.hideShowenElement( "qTypetextDynamicsContactsField select" );

            send.msdSurvey.dynamicQuestion.mapFTQuestionToContactField( "Last Name" ).updateQuestion();

            // Add 7 more questions
            questionsArray = new ArrayList<>();
            for( int i = 1; i <= 7; i++ ) {
                if( contactsDropDown[i].equals( FTcontactField.EMAIL.conntactFiled )
                    || contactsDropDown[i].equals( FTcontactField.LASTNAME.conntactFiled ) )
                    continue;

                send.msdSurvey.dynamicQuestion.addOrUpdateFTQuestionBy( contactsDropDown[i],
                                                                        QuestionStatus.NEW )
                                              .mapQuestionToContactField( contactsDropDown[i],
                                                                          element.msdContactFieldFTid );

                questionsArray.add( contactsDropDown[i] );
            }

            // Save and deploy survey
            send.msdSurvey.saveAndSelectTheme( "Beach" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.ROLE.question,
                                                                                                                    new String[]{ MCcontactFiled.ROLE.option_1 } )
                                                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                    emailAddress )
                                                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                    lastName );

            //Answer the additional 7 questions
            answersArray = new ArrayList<>();
            for( int i = 1; i <= 7; i++ ) {
                if( contactsDropDown[i].equals( "Email" ) || contactsDropDown[i].equals( "Last Name" ) )
                    continue;

                send.msdSurvey.dynamicQuestion.answerFtQuestion( contactsDropDown[i], contactsDropDown[i] );

                answersArray.add( contactsDropDown[i] );
            }

            send.msdSurvey.surveyNext();

            questionsArray.add( MCcontactFiled.ROLE.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray.add( MCcontactFiled.ROLE.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                           campaignName1,
                                                                           surveyName );
            
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();

    }

    /**
     * 
     * Verify that survey can be successfully completed on 10 pages
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWith10Pages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "10Pages";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";

        log.startTest( "Verify that survey can be successfully completed on 10 pages" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName2 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.editQuestionType( "Thank you" )
                                                                       .changeQuestionType( element.ftQId )
                                                                       .enterQuestion( FTcontactField.LASTNAME.question );

            driver.showHiddenElement( "qTypetextDynamicsContactsField select" );

            log.startStep( "Get the values of the contact field drop down" );
            String[] contactsDropDown = driver.getDropDownOptions( "//label[@id='qTypetextDynamicsContactsField']/select" );
            log.endStep();

            driver.hideShowenElement( "qTypetextDynamicsContactsField select" );

            send.msdSurvey.dynamicQuestion.mapFTQuestionToContactField( "Last Name" ).updateQuestion();

            // Add 7 more questions
            questionsArray = new ArrayList<>();
            for( int i = 1; i <= 7; i++ ) {
                if( contactsDropDown[i].equals( "Email" ) || contactsDropDown[i].equals( "Last Name" ) )
                    continue;

                String pageNumber = Integer.toString( i + 2 );

                send.msdSurvey.addAndGoToPage( "Page " + pageNumber ).dynamicQuestion.addOrUpdateFTQuestionBy( contactsDropDown[i],
                                                                                                               QuestionStatus.NEW )
                                                                                     .mapQuestionToContactField( contactsDropDown[i],
                                                                                                                 element.msdContactFieldFTid )
                                                                                     .updateQuestion();
                questionsArray.add( contactsDropDown[i] );
            }

            send.msdSurvey.addAndGoToPage( "Last Page" ).dynamicQuestion.addContentQuestion( false,
                                                                                             "Thank You" );
            send.msdSurvey.saveAndSelectTheme( "Funky" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                    new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                    emailAddress );
            send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                          lastName );
            send.msdSurvey.surveyNext();

            // Answer the additional 7 questions
            answersArray = new ArrayList<>();
            for( int i = 1; i <= 7; i++ ) {
                if( contactsDropDown[i].equals( "Email" ) || contactsDropDown[i].equals( "Last Name" ) )
                    continue;

                send.msdSurvey.dynamicQuestion.answerFtQuestion( contactsDropDown[i], "Answer" + i );
                send.msdSurvey.surveyNext();

                answersArray.add( contactsDropDown[i] );
            }

            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType2,
                                                                           campaignName2,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCreateSurveyResponseWhenAnswerExceedsMaximumCharLength() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "ExceedingCharLength";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";

        log.startTest( "Verify that survey response is not created when one of the answers (Address 1: Post Office Box) exceeds the maximum char length" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName2 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.editQuestionType( "Thank you" )
                                                                       .changeQuestionType( element.ftQId )
                                                                       .enterQuestion( FTcontactField.LASTNAME.question );

            driver.showHiddenElement( "qTypetextDynamicsContactsField select" );

            log.startStep( "Get the values of the contact field drop down" );
            String[] contactsDropDown = driver.getDropDownOptions( "//label[@id='qTypetextDynamicsContactsField']/select" );
            log.endStep();

            driver.hideShowenElement( "qTypetextDynamicsContactsField select" );

            send.msdSurvey.dynamicQuestion.mapFTQuestionToContactField( "Last Name" ).updateQuestion();

            // Add 7 more questions
            questionsArray = new ArrayList<>();
            for( int i = 1; i <= 7; i++ ) {
                if( contactsDropDown[i].equals( "Email" ) || contactsDropDown[i].equals( "Last Name" ) )
                    continue;

                String pageNumber = Integer.toString( i + 2 );

                send.msdSurvey.addAndGoToPage( "Page " + pageNumber ).dynamicQuestion.addOrUpdateFTQuestionBy( contactsDropDown[i],
                                                                                                               QuestionStatus.NEW )
                                                                                     .mapQuestionToContactField( contactsDropDown[i],
                                                                                                                 element.msdContactFieldFTid )
                                                                                     .updateQuestion();

                questionsArray.add( contactsDropDown[i] );
            }

            send.msdSurvey.addAndGoToPage( "Last Page" ).dynamicQuestion.addContentQuestion( false,
                                                                                             "Thank You" );
            send.msdSurvey.saveAndSelectTheme( "Funky" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                    new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                    emailAddress );
            send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                          lastName );
            send.msdSurvey.surveyNext();

            // Answer the additional 7 questions
            answersArray = new ArrayList<>();
            for( int i = 1; i <= 7; i++ ) {
                if( contactsDropDown[i].equals( "Email" ) || contactsDropDown[i].equals( "Last Name" ) )
                    continue;

                send.msdSurvey.dynamicQuestion.answerFtQuestion( contactsDropDown[i], contactsDropDown[i] );

                answersArray.add( contactsDropDown[i] );

                send.msdSurvey.surveyNext();
            }

            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType2,
                                                                           campaignName2,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /**
     * 
     * Verify that survey with 3 pages can be successfully completed
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyWith3Pages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "3Pages";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";

        log.startTest( "Verify that survey with 3 pages can be successfully completed" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.editQuestionType( "Thank you" )
                                                                       .enterQuestion( FTcontactField.FIRSTNAME.question )
                                                                       .changeQuestionType( element.ftQId )
                                                                       .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                       .updateQuestion()
                                                                       .addFTQAndMapItToLastName( false );
            send.msdSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );
            send.msdSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress );
            send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                          firstName )
                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                          lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.FIRSTNAME.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed with 5 different questions
     * 
     * @throws Exception
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWith5TypesOfQuestions() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "5TypesOfQuestions";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String matrixValue1 = "Matrix Value 1";
        String matrixValue2 = "Matrix Value 2";
        String matrixValue3 = "Matrix Value 3";
        String matrixValue4 = "Matrix Value 4";

        log.startTest( "Verify that survey can be completed with 5 different questions" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToMartialStatus()
                                                                                                                      .addFTQAndMapItToLastName( false );

            // Add Matrix Question
            log.startStep( "Click the 'Add Question' button" );
            driver.click( "//a[@id='qAdd']/span[contains(text(), 'Add Question')]", driver.timeOut );
            log.endStep();

            log.startStep( "Click the 'Matrix' radio button" );
            driver.click( "//input[@id='radMatrix' and @type='radio']", driver.timeOut );
            log.endStep();

            log.startStep( "Enter a question 'Matrix' in the question editable area" );
            driver.switchToIframe( "//div[@id='cke_qText']//iframe" );
            driver.clear( "//body" );
            driver.type( "//body", "Matrix", driver.timeOut );
            driver.switchToTopWindow();
            log.endStep();

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

            send.msdSurvey.dynamicQuestion.updateQuestion();

            // Go to Page 2
            log.startStep( "Click the button Page 2" );
            driver.click( "//span[contains(text(),'Page 2')]", driver.timeOut );
            log.endStep();

            // Add Result Question
            log.startStep( "Click the 'Add Question' button" );
            driver.click( "//a[@id='qAdd']/span[contains(text(), 'Add Question')]", driver.timeOut );
            log.endStep();

            log.startStep( "Click the 'Result' radio button" );
            driver.click( "//input[@id='radResult' and @type='radio']", driver.timeOut );
            log.endStep();

            log.startStep( "Enter a question 'Result' in the question editable area" );
            driver.switchToIframe( "//div[@id='cke_qText']//iframe" );
            driver.clear( "//body" );
            driver.type( "//body", "Result", driver.timeOut );
            driver.switchToTopWindow();
            log.endStep();

            send.msdSurvey.dynamicQuestion.updateQuestion();

            // Save and deploy survey
            send.msdSurvey.saveAndSelectTheme( "Beach" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
                                                                                                                    new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
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

            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that surveys can be successfully completed with drag and drop question.
     * 
     * @throws Exception 
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropQuestion() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "DragAndDropQuestion";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";

        log.startTest( "Verify that surveys can be successfully completed with drag and drop question" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType5, campaignName5 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToGender()
                                                                                                                      .dragAndDropFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.SPORT.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType5,
                                                                           campaignName5,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that surveys can successfully be completed with Drag and drop questions only.
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyWithDragAndDropQuestionsOnly() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "DragAndDropQuestionsOnly";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";

        log.startTest( "Verify that surveys can successfully be completed with Drag and drop questions only." );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType1, campaignName1 ).dynamicQuestion.deleteMcQuestionType( "A multiple choice question" )
                                                                                                                      .dragAndDropFTQAndMapItToEmail( false )
                                                                                                                      .deleteFtQuestionType( "A free text question" )
                                                                                                                      .dragAndDropFTQAndMapItToLastName( false )
                                                                                                                      .dragAndDropMCQAndMapItToGender();

            send.msdSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                           campaignName1,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed successfully with drag and drop questions on two pages.
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDragAndDropOn3Pages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "DragAndDropOn3Pages";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";

        log.startTest( "Verify that survey can be completed successfully with drag and drop questions on three pages." );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName2 ).dynamicQuestion.deleteMcQuestionType( "A multiple choice question" )
                                                                                                                      .dragAndDropFTQAndMapItToEmail( false )
                                                                                                                      .deleteFtQuestionType( "A free text question" )
                                                                                                                      .dragAndDropMCQAndMapItToMartialStatus();
            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.dragAndDropFTQAndMapItToLastName( false )
                                                                       .deleteContentQuestionType( "Thank you" )
                                                                       .dragAndDropFTQAndMapItToFirstName( false );

            send.msdSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addDragAndDropQuestionType( "Content" )
                                                                     .enterQuestion( "Thank you" )
                                                                     .updateQuestion();
            send.msdSurvey.saveAndSelectTheme( Theme.RED.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
                                                                                                                           new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress );
            send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                          firstName )
                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                          lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.FIRSTNAME.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType2,
                                                                           campaignName2,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that user can 'login as' a different account and successfully complete a survey
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithLoginAs() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "loginAs";

        log.startTest( "Verify that user can use the feature 'login as' and a survey can be successfully completed" );
        try {

            send.loginToSend( sendSuperUser, sendSuperPassword ).goToAdmin();

            log.startStep( "Click the 'Users' tab" );
            driver.click( "//ul[@id='typesMenu']//span[contains(text(),'Users')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the 'User' page is loaded" );
            log.endStep( driver.isElementPresent( "//li[@id='s_nav-tabsUsers']//span[contains(text(),'Users')]",
                                                  driver.timeOut ) );

            log.startStep( "Type '" + msdSendUser + "' in the search text box" );
            driver.type( "//input[@id='txtSearch']", msdSendUser, driver.timeOut );
            log.endStep();

            log.startStep( "Click the 'Search' button" );
            driver.click( "//a[@id='btnSearch']", driver.timeOut );
            log.endStep();

            log.startStep( "Click '" + msdSendUser + "' Tools button" );
            driver.click( "//tr//td[contains(a,'" + msdSendUser + "')]/../td/a[contains(text(),'Tools')]",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Select 'Login as' option " );
            driver.click( "//ul[@id='p_users_user']//a[contains(text(),'Login As')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Type 'For Testing' the reason quired to login in the text box" );
            driver.type( "//div[@class='s_form-elementsRow']/input[@id[starts-with(.,'di_')]]", "For Testing", driver.timeOut );
            log.endStep();

            log.startStep( "Click on 'login' button" );
            driver.click( "//a[@class='s_button']/span[contains(text(),'Login')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            send.goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                           .checkLogResponseInCRM()
                                           .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                             .editMCQAndMapItToROLE()
                                                                                                             .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.FUNKY.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.ROLE.question,
                                                                                                                             new String[]{ MCcontactFiled.ROLE.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.ROLE.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.ROLE.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can successfully be completed while there is a multiple choice question which is not mapped to contact field
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "surveys-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithMCQWhichIsNotMapped() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MultipleQuestionWhichIsNotMapped";
        String theme = "Water";

        log.startTest( "Verify that survey can successfully be completed while there is a multiple choice question which is not mapped to contact field" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                                                                                                                QuestionStatus.EDIT )
                                                                                                                      .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                                                                         MCcontactFiled.GENDER.option_2 )
                                                                                                                      .updateQuestion()
                                                                                                                      .addFTQAndMapItToLastName( false );

            send.msdSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                  new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                               .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                  emailAddress )
                                                                                               .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                  lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     *
     * Verify that survey can be successfully Completed with Different Symbols in the Email
     * 
     * @throws Exception
     * 
     */
    private void completedSurveyWithDifferentEmailFormats(
                                                           String surveyName,
                                                           String emailAddress,
                                                           String desciption ) throws Exception {

        String number = driver.getTimestamp();
        String lastName = number + "lastName";

        log.startTest( "Verify that survey can be successfully completed with " + desciption );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToMartialStatus()
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
                                                                                                                             new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[1].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     *
     * Verify that survey can be successfully completed with Special Characters in the email address -
     * ! # $ % & ' * + - / = ? ^ _ ` { | } ~
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithSpecialCharacters() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmailFormats( number + "specialCharatersInEmail",
                                                  number + "!#$%&*+-/=?^_^`'{|}~Email'@concep.com",
                                                  "apostophe in the email address !#$%&*+-/=?^_^`'{|}~' " );
    }

    /**
     * 
     * Verify that survey can be successfully completed with Hyphen in the email
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests"})
    public void successfullyCompletedSurveyWithHyphen() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmailFormats( number + "EmailWithHyphen",
                                                  number + "email-comment@concep.com",
                                                  "Hyphen in the email" );
    }

    /**
     * 
     * Verify that survey can be successfully completed with Digits 0 to 9 in the email
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDigits0to9InTheEmail() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmailFormats( number + "Digits0to9InTheEmail",
                                                  number + "email0123456789@concep.com",
                                                  "Digits 0 to 9 in the email" );
    }

    /**
     * 
     * Verify that survey can be successfully completed with Dot "." in the email
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithDot() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmailFormats( number + "EmailWithDot",
                                                  number + "email.test@concep.com",
                                                  "Dot '.' in the email" );
    }

    /**
     * 
     * Verify that survey can be completed with the following special characters separately ! # $ % & ' * + - / = ? ^ _ ` { | } ~
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDifferentSpecialCharacters() throws Exception {

        String surveyNumber = driver.getTimestamp();
        String surveyName = surveyNumber + "DifferentSpecialCharacters";

        log.startTest( "Verify that survey can be completed with the following special characters separately ! # $ % & ' * + - / = ? ^ _ ` { | } ~" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToROLE()
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.BEACH.type );

            // ! # $ % & ' * + - / = ? ^ _ ` { | } ~            
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
            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();
            campaignResponseArray = new ArrayList<>();
            for( int i = 0; i < specialCharacters.length; i++ ) {
                String number = driver.getTimestamp();
                String emailAddress = number + "test" + specialCharacters[i] + "email@concep.com";
                String lastName = number + "lastName";

                send.msdSurvey.viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.ROLE.question,
                                                                                          new String[]{ MCcontactFiled.ROLE.option_1 } )
                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                          emailAddress )
                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                          lastName );
                send.msdSurvey.surveyNext();

                questionsArray.add( MCcontactFiled.ROLE.question );
                questionsArray.add( FTcontactField.EMAIL.question );
                questionsArray.add( FTcontactField.LASTNAME.question );

                answersArray.add( MCcontactFiled.ROLE.option_1 );
                answersArray.add( emailAddress );
                answersArray.add( lastName );

                campaignResponseArray.add( campaignResponse1 );

                driver.close();
                driver.selectWindow();
            }
            driver.close();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = specialCharacters.length;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

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
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedReopenedSurveyMappingWarningMessageVerification() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "CompletedSurveyWarningMessage";
        String lastName = number + "lastName";
        String emailAddress = number + "email@concep.com";

        log.startTest( "Verify that after a survey is completed when opened again you get the proper warning message" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToMartialStatus()
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
                                                                                                                             new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

            log.startStep( "Close the survey pop-up page" );
            driver.close();
            driver.selectWindow();
            log.endStep();

            send.goToSurveyPage().msdSurvey.openSurvey( surveyName );

            log.startStep( "Verify that the warning message is displayed" );
            log.endStep( driver.isElementPresent( "//p[@class='s_form-instructionText']", driver.timeOut ) );

            log.startStep( "Click on the OK button" );
            driver.click( "//span[contains(text(), 'OK')]", driver.timeOut );
            log.endStep();

            log.startStep( "Verify that 'Log responses in your CRM system' is checked" );
            log.endStep( verifyLogResponseInCRM() );

            log.startStep( "Verify that the survey type is retained as " + surveyType4 );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep( driver.isSelected( "//select[@id='surveyTypeDropDown']", surveyType4 ) );

            log.startStep( "Verify that the campaign type is retained as " + campaignName4 );
            log.endStep( driver.isSelected( "//select[@id='surveyDynamicsCampaigns']", campaignName4 ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that a survey can be successfully completed with required free text question
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithRequiredFTQ() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithRequireQuestion";
        String lastName = number + "lastName";
        String emailAddress = number + "email@concep.com";

        log.startTest( "Verify that a survey can be successfully completed with required free text question" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType1, campaignName1 ).dynamicQuestion.editFTQAndMapItToEmail( true )
                                                                                                                      .editMCQAndMapItToGender()
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().surveyNext();

            // Test the validation for the required questions
            log.startStep( "Verify the validation of the required free text question" );
            log.endStep( driver.isTextPresent( "//div[@class='sQ sText sTextSingle']//label[@class='qError']",
                                               "This field is required",
                                               driver.timeOut ) );

            // Answer the Survey questions
            send.msdSurvey.dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                          .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                          .answerFtQuestion( FTcontactField.LASTNAME.question, lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                           campaignName1,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be successfully completed with First name, Last Name, Job Title and Email Address free text questions
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithBusinessRequiredMappings() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "BusinessRequiredMappings";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String jobTitle = number + "jobTitle";

        log.startTest( "Verify that survey can be successfully completed with First name, Last Name, Job Title and Email Address free text questions" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType1, campaignName1 ).dynamicQuestion.editFTQAndMapItToEmail( true )
                                                                                                                      .editMCQAndMapItToGender()
                                                                                                                      .addFTQAndMapItToFirstName( true )
                                                                                                                      .addFTQAndMapItToLastName( true )
                                                                                                                      .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                                    FTcontactField.JOBTITLE,
                                                                                                                                                    true );
            send.msdSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                    new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                    emailAddress )
                                                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                    lastName )
                                                                                                 .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                    firstName )
                                                                                                 .answerFtQuestion( FTcontactField.JOBTITLE.question,
                                                                                                                    jobTitle );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );
            questionsArray.add( FTcontactField.FIRSTNAME.question );
            questionsArray.add( FTcontactField.JOBTITLE.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );
            answersArray.add( firstName );
            answersArray.add( jobTitle );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                           campaignName1,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be successfully completed with survey which has other answer
     * 
     * @throws Exception
     * 
     */

    //@Test(groups = { "survey-completion", "all-tests" }) Currently not supported in our Dynamics 4 implementation.
    public void successfullyCompletedSurveyWithMCQWithOtherAnswer() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithOtherAnswer";

        log.startTest( "Verify that survey can be successfully completed with survey which has other answer" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName2 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                                                                                                              MCcontactFiled.GENDER,
                                                                                                                                              MCQuestionAnswerType.OTHERANSWER )
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType2,
                                                                           campaignName2,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be successfully completed with MCQ with drop down
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithMCQWithDropDown() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithDropDown";

        log.startTest( "Verify that survey can be successfully completed with MCQ with drop down" );
        try {

            loginToSend3().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                       .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                                                                                                               MCcontactFiled.GENDER,
                                                                                                                                               MCQuestionAnswerType.DROPDOWN )
                                                                                                                       .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName )
                                                                                                          .answerDropDownQuestion( MCcontactFiled.GENDER.question,
                                                                                                                                   MCcontactFiled.GENDER.option_1 );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be successfully completed with required MCQ question
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithRequiredMCQ() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithRequiredQuestion";

        log.startTest( "Verify that survey can be successfully completed with required MCQ question" );
        try {

            loginToSend3().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                       .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                                                                                                               MCcontactFiled.GENDER,
                                                                                                                                               MCQuestionAnswerType.MANADATORY )
                                                                                                                       .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be successfully completed with page jumping
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyWithPageJumping() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "WithPageJumping";

        log.startTest( "Verify that survey can be successfully completed with page jumping" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false );

            // Add Additional Pages
            for( int i = 1; i < 4; i++ ) {
                String pageNumber = Integer.toString( i + 2 );

                log.startStep( "Click on Add Page button to add a new Page" );
                driver.click( "//span[contains(text(),'Add Page')]", driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                log.endStep();

                log.startStep( "Name the Page - Page " + pageNumber );
                driver.type( "//div/label[contains(text(),'Page Name')]/../input[@type='text']",
                             "Page " + pageNumber,
                             driver.timeOut );
                log.endStep();

                log.startStep( "Click on the Add Page button to create a new Page" );
                driver.click( "//a[@class='s_button']/span[contains(text(),'Add Page')]", driver.timeOut );
                log.endStep();
            }

            // Edit the default multiple choice question
            send.msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                                    QuestionStatus.EDIT )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions();

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 3" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.msdSurvey.dynamicQuestion.updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 2" )

            .dynamicQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.MARITALSTATUS.question,
                                                      QuestionStatus.NEW )
                            .fillinMCQAnswers( MCcontactFiled.MARITALSTATUS.option_1,
                                               MCcontactFiled.MARITALSTATUS.option_2 )
                            .mapMCQuestionToContactField( MCcontactFiled.MARITALSTATUS )
                            .addMCResponseCodeOptions();
            // Add New MCQ

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 5" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.msdSurvey.dynamicQuestion.updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 3" )

            // Add new MCQ
            .dynamicQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.ROLE.question, QuestionStatus.NEW )
                            .fillinMCQAnswers( MCcontactFiled.ROLE.option_1, MCcontactFiled.ROLE.option_2 )
                            .mapMCQuestionToContactField( MCcontactFiled.ROLE )
                            .addMCResponseCodeOptions();

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 2" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.msdSurvey.dynamicQuestion.updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 5" ).dynamicQuestion.addNewQuestionType( "Content",
                                                                                            element.contentQId )
                                                                       .enterQuestion( "Thank you!" )
                                                                       .updateQuestion();
            send.msdSurvey.saveAndSelectTheme( Theme.SPORT.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );
            send.msdSurvey.surveyNext().dynamicQuestion.answerMcQuestion( MCcontactFiled.ROLE.question,
                                                                          new String[]{ MCcontactFiled.ROLE.option_1 } );
            send.msdSurvey.surveyNext().dynamicQuestion.answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
                                                                          new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );
            questionsArray.add( MCcontactFiled.ROLE.question );
            questionsArray.add( MCcontactFiled.MARITALSTATUS.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );
            answersArray.add( MCcontactFiled.ROLE.option_1 );
            answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );
            campaignResponseArray.add( campaignResponse1 );
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed with content question which has link and the link is click-able
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithContentQuestionWithLink() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "WithContentQuestionWithLink";
        String link = "http://www.lipsum.com/feed/html";

        log.startTest( "Verify that survey can be completed with content question which has link and the link is click-able" );
        try {

            loginToSend3().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                       .addFTQAndMapItToLastName( false )
                                                                                                                       .editMCQAndMapItToGender()

                                                                                                                       // Add Content Question with Link
                                                                                                                       .addNewQuestionType( "Content",
                                                                                                                                            element.contentQId );

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

            send.msdSurvey.dynamicQuestion.updateQuestion();
            send.msdSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );

            // Verify that the link is working
            log.startStep( "Verify that the link is working" );
            log.endStep( driver.isClickable( "//a[contains(text(), 'http://www.lipsum.com/feed/html')]",
                                             driver.timeOut ) );

            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Data Driven method which verifies that survey can be completed with different types of content questions
     * 
     * @throws Exception
     * 
     */

    private void completedSurveyWithContentQuestion(
                                                     String description,
                                                     boolean isDragAndDrop ) throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "ContentQuestion-" + description;
        String content = "Nam elementum in enim id posuere. Praesent sed augue non elit accumsan tempor a a sapien. Morbi ac maximus enim. Maecenas et varius ligula. Vestibulum ornare efficitur ipsum. Fusce vel neque gravida, posuere augue finibus, pharetra eros. Sed varius in libero nec dictum. Vestibulum sollicitudin et ante porttitor luctus. Nunc a diam nec tortor efficitur aliquet.";

        log.startTest( "Verify that survey can be successfully completed with additionally added "
                       + description + "content question" );
        try {

            loginToSend3().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, campaignName1 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                       .addFTQAndMapItToLastName( false )
                                                                                                                       .editMCQAndMapItToMartialStatus();
            send.msdSurvey.dynamicQuestion.addContentQuestion( isDragAndDrop, content );
            send.msdSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
                                                                                                                             new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName );

            // Verify the content
            log.startStep( "Verify the content question content is visible" );
            log.endStep( driver.isTextPresent( "//div[@class='sQ sContent']", content, driver.timeOut ) );

            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                           campaignName1,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[1].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be successfully completed with additionally added normal content question
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithAdditionalContentQuestion() throws Exception {

        completedSurveyWithContentQuestion( "normal", false );

    }

    /**
     * 
     * Verify that survey can be successfully completed with additionally added drag and drop content question
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropContentQuestion() throws Exception {

        completedSurveyWithContentQuestion( "DragAndDrop", true );
    }

    /**
     * 
     * Verify that survey can be successfully completed with email confirmation option turned on
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "open-defects", "smoke-tests" })
    public void successfullyCompletedSurveyWithEmailConfirmation() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@mailinator.com";
        String lastName = number + "lastName";
        String surveyName = number + "EmailConfirmation";

        log.startTest( "Verify that survey can be successfully completed with email confirmation option turned on" );
        try {

            // Log And Go to Survey Page
            loginToSend2().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToLastName( false );

            // Add new free text question for email with email confirmation
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free text", element.ftQId )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapQuestionToContactField( FTcontactField.EMAIL.conntactFiled,
                                                                      element.msdContactFieldFTid );

            // Add Email Confirmation to the question
            addEmailConfirmation();

            send.msdSurvey.dynamicQuestion.updateQuestion().addMCQAndMapItToGender();

            // Create Email confirmation rules for the created contact and mappings
            addEmailConfirmationContactMappings( MCcontactFiled.GENDER.question,
                                                 MCcontactFiled.GENDER.name,
                                                 FTcontactField.EMAIL.question,
                                                 FTcontactField.EMAIL.conntactFiled,
                                                 FTcontactField.LASTNAME.question,
                                                 FTcontactField.LASTNAME.conntactFiled );

            // Save and deploy survey
            send.msdSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                    new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                    emailAddress )
                                                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                    lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

            log.startStep( "Go to Mailinator" );
            driver.navigate( "https://mailinator.com/" );
            driver.waitForPageToLoad();
            log.endStep();

            log.startStep( "Log with email " + emailAddress );
            driver.type( "//input[@id='inboxfield']", emailAddress, driver.timeOut );
            driver.click( "//btn[contains(text(), 'Check it')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Select the last sent Campaign" );
            driver.click( "//ul[@id='mailcontainer']//li[1]/a", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Click on 'Yes, Confirm My Email Address' to confirm the email address" );
            driver.switchToIframe( "//iframe[@name='rendermail']" );
            driver.click( "//strong[contains(text(), 'Yes, Confirm My Email Address')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.selectWindow();
            log.endStep();

            log.startStep( "Verify that the 'Thank you' message is displayed" );
            log.endStep( driver.isTextPresent( "//div[@class='sQ sContent']", "Thank you", driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    private void submitSurveyWithMultipleLanguages(
                                                    String[] languages,
                                                    String surveyLanguage ) throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "SurveyWithLanguagePages";
        try {
            loginToSend2().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, campaignName4 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                       .addFTQAndMapItToLastName( false )
                                                                                                                       .editMCQAndMapItToGender();
            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .addLanguage( languages )
                                                      .saveSurvey();
            send.goToSurveyTab( "Questions" );
            for( int i = 0; i < ( languages.length ); i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] ).dynamicQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                                                                     .enterQuestion( FTcontactField.EMAIL.question
                                                                                                     + " - "
                                                                                                     + languages[i] )
                                                                                     .updateQuestion()
                                                                                     .editQuestionType( FTcontactField.LASTNAME.question )
                                                                                     .enterQuestion( FTcontactField.LASTNAME.question
                                                                                                     + " - "
                                                                                                     + languages[i] )
                                                                                     .updateQuestion()
                                                                                     .editQuestionType( MCcontactFiled.GENDER.question )
                                                                                     .enterQuestion( MCcontactFiled.GENDER.question
                                                                                                     + " - "
                                                                                                     + languages[i] )
                                                                                     .updateQuestion();
            }
            send.msdSurvey.saveAndSelectTheme( Theme.BEACH.type )
                          .viewAndDeployTheSurvey()
                          .switchSurveySubmitionPageLanguage( surveyLanguage ).dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                 new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                              .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                 emailAddress )
                                                                                              .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                 lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType4,
                                                                           campaignName4,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[1].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithAdditionalLanguagePages() throws Exception {

        log.startTest( "Verify that survey can be successfully completed with additional language pages" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian" }, "English" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithThreeAdditionalLanguagePages() throws Exception {

        log.startTest( "Verify that survey can be successfully completed with three additional language pages" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian", "Arabic" }, "English" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyUsingSecondaryLanguage() throws Exception {

        log.startTest( "Verify that survey can be successfully submitted via the secondary language" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian" }, "Bulgarian" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyUsingThirdLanguage() throws Exception {

        log.startTest( "Verify that survey can be successfully submitted via the third language" );
        submitSurveyWithMultipleLanguages( new String[]{ "English", "Bulgarian", "Arabic" }, "Arabic" );
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePage()
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

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same page" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion();

            idFTQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.msdSurvey.dynamicQuestion.editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.GENDER.option_1 );

            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion();

            idFTQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.GENDER.option_2 );

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();
            campaignResponseArray = new ArrayList<>();
            for( int i = 0; i < submissionsCount; i++ ) {

                send.msdSurvey.viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                          new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                          i + email )
                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                          lastNameFirst )
                                                                       .answerFtQuestion( "Last Name Duplicated",
                                                                                          lastNameSecond )
                                                                       .answerMcQuestion( "Gender Duplicated",
                                                                                          new String[]{ MCcontactFiled.GENDER.option_2 } );

                send.msdSurvey.surveyNext();

                questionsArray.add( MCcontactFiled.GENDER.question );
                questionsArray.add( FTcontactField.EMAIL.question );
                questionsArray.add( FTcontactField.LASTNAME.question );

                answersArray.add( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
                answersArray.add( email );
                answersArray.add( duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );

                if( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) == MCcontactFiled.GENDER.option_1 ) {

                    campaignResponseArray.add( campaignResponse1 );
                } else {
                    campaignResponseArray.add( campaignRespose2 );
                }
                driver.close();
                driver.selectWindow();
            }

            log.resultStep( "Highest FTQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids )
                            + " and has an answer of: "
                            + duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );
            log.endStep();

            log.resultStep( "Highest MCQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids )
                            + " and has an answer of: "
                            + duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
            log.endStep();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = submissionsCount;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion();

            idFTQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.msdSurvey.dynamicQuestion.editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.GENDER.option_1 );

            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                            element.ftQId )
                                                                       .enterQuestion( "Last Name Duplicated" )
                                                                       .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                       .updateQuestion();

            idFTQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.GENDER.option_2 );

            send.msdSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();
            campaignResponseArray = new ArrayList<>();
            for( int i = 0; i < submissionsCount; i++ ) {

                send.msdSurvey.viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                          new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                          i + email )
                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                          lastNameFirst );

                send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( "Last Name Duplicated",
                                                                              lastNameSecond )
                                                           .answerMcQuestion( "Gender Duplicated",
                                                                              new String[]{ MCcontactFiled.GENDER.option_2 } );

                send.msdSurvey.surveyNext();

                questionsArray.add( MCcontactFiled.GENDER.question );
                questionsArray.add( FTcontactField.EMAIL.question );
                questionsArray.add( FTcontactField.LASTNAME.question );

                answersArray.add( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
                answersArray.add( email );
                answersArray.add( duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );

                if( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) == MCcontactFiled.GENDER.option_1 ) {

                    campaignResponseArray.add( campaignResponse1 );
                } else {
                    campaignResponseArray.add( campaignRespose2 );
                }

                driver.close();
                driver.selectWindow();
            }

            log.resultStep( "Highest FTQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids )
                            + " and has an answer of: "
                            + duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );
            log.endStep();

            log.resultStep( "Highest MCQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids )
                            + " and has an answer of: "
                            + duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
            log.endStep();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = submissionsCount;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastNameFirst";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on the same page" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                      new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                   .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( email );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on different pages" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                            element.ftQId )
                                                                       .enterQuestion( "Last Name Duplicated" )
                                                                       .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                       .updateQuestion()
                                                                       .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                                 QuestionStatus.NEW )
                                                                       .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                          MCcontactFiled.GENDER.option_2 )
                                                                       .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                       .addMCResponseCodeOptions()
                                                                       .updateQuestion();

            send.msdSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                      new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                   .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastNameFirst );

            send.msdSurvey.surveyNext().surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( email );
            answersArray.add( lastNameFirst );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignResponse1 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstNameSecond = randNumber + "firstNameSecond";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on the same page" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
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
                                          .editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "First Name Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                          .updateQuestion()
                                          .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName )
                                                                   .answerFtQuestion( "First Name Duplicated",
                                                                                      firstNameSecond )
                                                                   .answerMcQuestion( "Gender Duplicated",
                                                                                      new String[]{ MCcontactFiled.GENDER.option_2 } );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );
            questionsArray.add( FTcontactField.FIRSTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_2 );
            answersArray.add( email );
            answersArray.add( lastName );
            answersArray.add( firstNameSecond );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignRespose2 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstNameSecond = randNumber + "firstNameSecond";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on different pages" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
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
                                          .editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                            element.ftQId )
                                                                       .enterQuestion( "First Name Duplicated" )
                                                                       .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                       .updateQuestion()
                                                                       .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                                 QuestionStatus.NEW )
                                                                       .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                          MCcontactFiled.GENDER.option_2 )
                                                                       .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                       .addMCResponseCodeOptions()
                                                                       .updateQuestion();

            send.msdSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName );

            send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( "First Name Duplicated",
                                                                          firstNameSecond )
                                                       .answerMcQuestion( "Gender Duplicated",
                                                                          new String[]{ MCcontactFiled.GENDER.option_2 } );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );
            questionsArray.add( FTcontactField.FIRSTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_2 );
            answersArray.add( email );
            answersArray.add( lastName );
            answersArray.add( firstNameSecond );

            campaignResponseArray = new ArrayList<>();
            campaignResponseArray.add( campaignRespose2 );

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on the same page" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
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
                                          .editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "First Name Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                          .updateQuestion()
                                          .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName );
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( email );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on different pages" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editQuestionType( "A free text question" )
                                                                                                                      .enterQuestion( FTcontactField.EMAIL.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( FTcontactField.LASTNAME.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( FTcontactField.FIRSTNAME.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .editQuestionType( "A multiple choice question" )
                                                                                                                      .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                                                                         MCcontactFiled.GENDER.option_2 )
                                                                                                                      .enterQuestion( MCcontactFiled.GENDER.question )
                                                                                                                      .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                                                                      .deleteMcQuestionAnswers( 2 )
                                                                                                                      .addMCResponseCodeOptions()
                                                                                                                      .updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                            element.ftQId )
                                                                       .enterQuestion( "First Name Duplicated" )
                                                                       .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                       .updateQuestion()
                                                                       .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                                 QuestionStatus.NEW )
                                                                       .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                          MCcontactFiled.GENDER.option_2 )
                                                                       .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                       .addMCResponseCodeOptions()
                                                                       .updateQuestion();

            send.msdSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName );

            send.msdSurvey.surveyNext().surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( email );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyCompleteSurveyWithNotMappedFTQs() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String notMappedFTQ1 = randNumber + "answer1";
        String notMappedFTQ2 = randNumber + "answer2";

        log.startTest( "Verify that both not mapped FTQ responses will be logged as answers to the CRM" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName4 ).dynamicQuestion.editQuestionType( "A free text question" )
                                                                                                                      .enterQuestion( FTcontactField.EMAIL.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( FTcontactField.LASTNAME.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( "Not mapped FTQ1" )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( "Not mapped FTQ2" )
                                                                                                                      .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName )
                                                                   .answerFtQuestion("Not mapped FTQ1", notMappedFTQ1)
                                                                   .answerFtQuestion("Not mapped FTQ2", notMappedFTQ2);

            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( email );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyCompleteSurveyWithNotMappedMCQOnlyMappedToCampaignResponse() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that both not mapped MCQ campaign responses will be logged to the CRM" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName4 ).dynamicQuestion.editQuestionType( "A free text question" )
                                                                                                                      .enterQuestion( FTcontactField.EMAIL.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( FTcontactField.LASTNAME.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Multiple Choice",
                                                                                                                                           element.mcQId );
                                                                                                                      driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
                                                                                                                      send.msdSurvey.dynamicQuestion.addMCResponseCodeOptions()
                                                                                                                      .fillinMCQAnswers("Interested", "Not Interested")
                                                                                                                      .enterQuestion( "Not mapped MCQ1" )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Multiple Choice",
                                                                                                                                           element.mcQId );
                                                                                                                      driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
                                                                                                                      send.msdSurvey.dynamicQuestion.addMCResponseCodeOptions()
                                                                                                                      .fillinMCQAnswers("Interested", "Not Interested")
                                                                                                                      .enterQuestion( "Not mapped MCQ2" )
                                                                                                                      .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName )
                                                                   .answerMcQuestion("Not mapped MCQ1", new String[] {"Interested"} )
                                                                   .answerMcQuestion("Not mapped MCQ2", new String[] {"Not Interested"} );

            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( email );
            answersArray.add( lastName );

            campaignResponseArray = new ArrayList<>();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePageAndTwoFTQnotMapped()
                                                                                                     throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";
        String lastNameSecond = randNumber + "lastNameSecond";
        String notMappedFTQ1 = randNumber + "answer1";
        String notMappedFTQ2 = randNumber + "answer2";
        int submissionsCount = 1;

        int idFTQ1;
        int idFTQ2;
        int idMCQ1;
        int idMCQ2;

        Map<Integer, String> duplicatedFTQids = new HashMap<Integer, String>();
        Map<Integer, String> duplicatedMCQids = new HashMap<Integer, String>();

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same together with the not mapped FTQs" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion();

            idFTQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.msdSurvey.dynamicQuestion.editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.GENDER.option_1 );

            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion();

            idFTQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.GENDER.option_2 );
            
            send.msdSurvey.dynamicQuestion.addNewQuestionType("Free Text", element.ftQId)
            							  .enterQuestion("Not mapped FTQ1")
            							  .updateQuestion()
            							  .addNewQuestionType("Free Text", element.ftQId)
            							  .enterQuestion("Not mapped FTQ2")
            							  .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();
            campaignResponseArray = new ArrayList<>();
            for( int i = 0; i < submissionsCount; i++ ) {

                send.msdSurvey.viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                          new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                          i + email )
                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                          lastNameFirst )
                                                                       .answerFtQuestion( "Last Name Duplicated",
                                                                                          lastNameSecond )
                                                                       .answerMcQuestion( "Gender Duplicated",
                                                                                          new String[]{ MCcontactFiled.GENDER.option_2 } )
                                                                       .answerFtQuestion("Not mapped FTQ1", notMappedFTQ1)
                                                                       .answerFtQuestion("Not mapped FTQ2", notMappedFTQ2);

                send.msdSurvey.surveyNext();

                questionsArray.add( MCcontactFiled.GENDER.question );
                questionsArray.add( FTcontactField.EMAIL.question );
                questionsArray.add( FTcontactField.LASTNAME.question );

                answersArray.add( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
                answersArray.add( email );
                answersArray.add( duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );

                if( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) == MCcontactFiled.GENDER.option_1 ) {

                    campaignResponseArray.add( campaignResponse1 );
                } else {
                    campaignResponseArray.add( campaignRespose2 );
                }
                driver.close();
                driver.selectWindow();
            }

            log.resultStep( "Highest FTQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids )
                            + " and has an answer of: "
                            + duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );
            log.endStep();

            log.resultStep( "Highest MCQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids )
                            + " and has an answer of: "
                            + duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
            log.endStep();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = submissionsCount;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePageAndTwoNotMappedMCQCampaignResponses()
                                                                                                     throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";
        String lastNameSecond = randNumber + "lastNameSecond";
        int submissionsCount = 1;

        int idFTQ1;
        int idFTQ2;
        int idMCQ1;
        int idMCQ2;

        Map<Integer, String> duplicatedFTQids = new HashMap<Integer, String>();
        Map<Integer, String> duplicatedMCQids = new HashMap<Integer, String>();

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same together with the not mapped FTQs" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion();

            idFTQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.msdSurvey.dynamicQuestion.editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ1 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.GENDER.option_1 );

            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion();

            idFTQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            idMCQ2 = send.msdSurvey.dynamicQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.GENDER.option_2 );
            
            send.msdSurvey.dynamicQuestion
            			  .addNewQuestionType( "Multiple Choice",
					                           element.mcQId );
            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
            send.msdSurvey.dynamicQuestion.addMCResponseCodeOptions()
            			  .fillinMCQAnswers("Interested", "Not Interested")
            			  .enterQuestion( "Not mapped MCQ1" )
            			  .updateQuestion()
            			  .addNewQuestionType( "Multiple Choice",
                                               element.mcQId );
            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
            send.msdSurvey.dynamicQuestion.addMCResponseCodeOptions()
            			  .fillinMCQAnswers("Interested", "Not Interested")
            			  .enterQuestion( "Not mapped MCQ2" )
            			  .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .handleDuplicateMappingsDialog( true )
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();
            campaignResponseArray = new ArrayList<>();
            for( int i = 0; i < submissionsCount; i++ ) {

                send.msdSurvey.viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                          new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                          i + email )
                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                          lastNameFirst )
                                                                       .answerFtQuestion( "Last Name Duplicated",
                                                                                          lastNameSecond )
                                                                       .answerMcQuestion( "Gender Duplicated",
                                                                                          new String[]{ MCcontactFiled.GENDER.option_2 } )
                                                                       .answerMcQuestion("Not mapped MCQ1", new String[] { "Interested" })
                                                                       .answerMcQuestion("Not mapped MCQ2", new String[] { "Not Interested" });
                                

                send.msdSurvey.surveyNext();

                questionsArray.add( MCcontactFiled.GENDER.question );
                questionsArray.add( FTcontactField.EMAIL.question );
                questionsArray.add( FTcontactField.LASTNAME.question );

                answersArray.add( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
                answersArray.add( email );
                answersArray.add( duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );

                if( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) == MCcontactFiled.GENDER.option_1 ) {

                    campaignResponseArray.add( campaignResponse1 );
                } else {
                    campaignResponseArray.add( campaignRespose2 );
                }
                driver.close();
                driver.selectWindow();
            }

            log.resultStep( "Highest FTQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids )
                            + " and has an answer of: "
                            + duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) ) );
            log.endStep();

            log.resultStep( "Highest MCQ question ID is "
                            + send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids )
                            + " and has an answer of: "
                            + duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
            log.endStep();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = submissionsCount;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyCompleteSurveyWithOneMappedMCQAndMCQMappedOnlyToCampaignResponse() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that both not mapped MCQ campaign responses will be logged to the CRM" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName4 ).dynamicQuestion.editQuestionType( "A free text question" )
                                                                                                                      .enterQuestion( FTcontactField.EMAIL.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( FTcontactField.LASTNAME.question )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .editMCQAndMapItToGender()
                                                                                                                      .addNewQuestionType( "Multiple Choice",
                                                                                                                                           element.mcQId );
                                                                                                                      driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
                                                                                                                      send.msdSurvey.dynamicQuestion.addMCResponseCodeOptions()
                                                                                                                      .fillinMCQAnswers("Interested", "Not Interested")
                                                                                                                      .enterQuestion( "Not mapped MCQ1" )
                                                                                                                      .updateQuestion();
                                                                                                                      

            send.msdSurvey.saveAndContinueToTheme()
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                      email )
                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                      lastName )
                                                                   .answerMcQuestion(MCcontactFiled.GENDER.question, 
                                                                		   			 new String[] {MCcontactFiled.GENDER.option_1} )
                                                                   .answerMcQuestion("Not mapped MCQ1", new String[] {"Not Interested"} );

            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.LASTNAME.question );
            questionsArray.add(MCcontactFiled.GENDER.question);

            answersArray = new ArrayList<>();
            answersArray.add( email );
            answersArray.add( lastName );
            answersArray.add( MCcontactFiled.GENDER.option_1);

            campaignResponseArray = new ArrayList<>();

            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
