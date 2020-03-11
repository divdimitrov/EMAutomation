/*

*/

/**
 * 
 * Data driven test which verify that surveys can be completed with each surveywebDriver.type
 * 
 * surveyType = SurveywebDriver.type
 * 
 * eventFolder = Event Folder in IA
 * 
 * IAfolderValueMC = Mapping for the multiple choice question
 * 
 * mappingOption1/mappingOption2 = Mapping options for the multiple choice question
 * 
 * answer1/answer2 = Text answer fields of the multiple choice question
 * 
 * theme = Survey theme
 * 
 * @throws Exception
 * 
 */

package concep.selenium.ia;

import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.IA.Api.Contact;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;

public class SurveyCompletionWithAdditionalFieldMapping extends BaseIA {

    private String  sourceFolder = "Firm Contacts - Companies";
    private boolean isSourced    = true;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void deleteAndCreateConnectionToIA(
                                               @Optional("config/firefox.IA") String configLocation )
                                                                                                     throws Exception {

        driver.init( configLocation );
        iaConnectionName = driver.config.getProperty( "iaConnectionName" );
        iaConnectionUsername = driver.config.getProperty( "iaConnectionUsername" );
        iaConnectionPassword = driver.config.getProperty( "iaConnectionPassword" );
        iaSendUser = driver.config.getProperty( "iaSendUser2" );
        iaSendPassword = driver.config.getProperty( "iaSendPassword" );
        driver.url = driver.config.getProperty( "url" );
        surveyEnableXPath = driver.config.getProperty( "surveyEnableXpath" );
        clearAndCreatConnection( sourceFolder, isSourced );

        driver.stopSelenium();

    }

    /**
     * 
     * Generic method for data-driven testing of survey completion with linked
     * questions that's mapped to global or local additional fields and non-existing user
     * 
     * @throws Exception
     * 
     */
    //To finish with the paramaters
    private void completeSurveyWithMappingQuestionToFieldTypeListAndFreeText(
                                                                              boolean isCompanyMapped,
                                                                              String surveyType,
                                                                              boolean isMultipleChoice,
                                                                              String eventFolder,
                                                                              MCcontactFiled mcContactFiled,
                                                                              FTcontactField ftContactField,
                                                                              String theme ) throws Exception {

        Contact par = new Contact();

        if( isCompanyMapped ) {
            log.startTest( "Verify that a survey can be successfully completed with Survey Type '"
                           + surveyType + "' with Event Folder '" + eventFolder + "' mapped to the '"
                           + mcContactFiled.name + "" + ftContactField.conntactFiled
                           + " with none existing Company" );
        } else {
            log.startTest( "Verify that a survey can be successfully completed with Survey Type '"
                           + surveyType + "' in '" + eventFolder + "' mapped to the '" + mcContactFiled.name
                           + "" + ftContactField.conntactFiled + " with none existing contact" );
        }

        try {

            String number = driver.getTimestamp();
            String emailAddress = number + "emailLinked@concep.com";
            String lastName = number + "lastName";
            String companyName = number + "companyName";
            String surveyName = number + surveyType + eventFolder;

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType, eventFolder ).iaQuestion.editFTQAndMapItToEmail( false );

            if( isMultipleChoice ) {
                send.iaSurvey.iaQuestion.addOrUpdateMCQuestion( QuestionStatus.EDIT, mcContactFiled );
            } else {
                send.iaSurvey.iaQuestion.addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                      ftContactField,
                                                                      false );
            }
            send.iaSurvey.iaQuestion.addFTQAndMapItToLastName( false );

            if( isCompanyMapped ) {
                send.iaSurvey.iaQuestion.addFTQAndMapItToCompany( false );
            }

            // Save and deploy survey
            send.iaSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey();

            if( isMultipleChoice ) {
                send.iaSurvey.iaQuestion.answerMcQuestion( mcContactFiled.question,
                                                           new String[]{ mcContactFiled.option_1 } );
            } else {
                send.iaSurvey.iaQuestion.answerFtQuestion( ftContactField.question, "Programming" );
            }

            send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                    .answerFtQuestion( FTcontactField.LASTNAME.question, lastName );

            if( isCompanyMapped ) {
                send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.COMPANY.question, companyName );
            }

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            par.questions.add( FTcontactField.EMAIL.conntactFiled );
            par.questions.add( FTcontactField.LASTNAME.conntactFiled );
            if( isMultipleChoice ) {
                par.questions.add( mcContactFiled.name );
            } else {
                par.questions.add( ftContactField.conntactFiled );
            }
            if( isCompanyMapped ) {
                par.questions.add( FTcontactField.COMPANY.conntactFiled );
            }

            par.answers.add( emailAddress );
            par.lastName = lastName;
            if( isMultipleChoice ) {
                par.answers.add( mcContactFiled.option_1 );
            } else {
                par.answers.add( "Programming" );
            }
            if( isCompanyMapped ) {
                par.answers.add( companyName );
            }

            par.folderName = eventFolder;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[1].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Generic method for data-driven testing of survey completion with linked
     * questions that's mapped to global or local additional fields and non-existing user
     * 
     * @throws Exception
     * 
     */

    private void completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText(
                                                                                      boolean isCompanyMapped,
                                                                                      String surveyType,
                                                                                      String eventFolder,
                                                                                      String theme )
                                                                                                    throws Exception {

        Contact par = new Contact();
        String number = driver.getTimestamp();
        String emailAddress = number + "emailLinked@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType + eventFolder;
        String companyName = number + "companyName";

        if( isCompanyMapped ) {

            log.startTest( "Verify that Survey Type '"
                           + surveyType
                           + "' with Interaction Folder '"
                           + eventFolder
                           + "' can be successfully completed with Multiple questions mapped to Global, Local additional fields FText, MChoice Questions with NoneExisting Company" );
        } else {
            log.startTest( "Verify that Survey Type '"
                           + surveyType
                           + "' with Interaction Folder '"
                           + eventFolder
                           + "' can be successfully completed with Multiple questions mapped to Global and Local additional fields FText, MChoice Questions with NoneExisting Contact" );
        }
        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType, eventFolder ).iaQuestion.editFTQAndMapItToEmail( false )
                                                                                                             .editMCQAndMapItToGenderG()
                                                                                                             .dragAndDropMCQAndMapItToColorG()
                                                                                                             .addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP,
                                                                                                                                     MCcontactFiled.GENDERLOCAL )
                                                                                                             .addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP,
                                                                                                                                     MCcontactFiled.COLORLOCAL )
                                                                                                             .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                           FTcontactField.HOBBYGLOBAL,
                                                                                                                                           false )
                                                                                                             .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                           FTcontactField.LASTNAME,
                                                                                                                                           false )
                                                                                                             .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                           FTcontactField.EMPLOYEEGLOBAL,
                                                                                                                                           false )
                                                                                                             .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                           FTcontactField.HOBBYLOCAL,
                                                                                                                                           false )
                                                                                                             .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                           FTcontactField.EMPLOYEELOCAL,
                                                                                                                                           false );

            //Injecting Folder Name into xml
            log.startStep( "Injecting Folder Name parameter to external file" );
            par.folderName = eventFolder;
            log.endStep();

            if( isCompanyMapped ) {
                send.iaSurvey.iaQuestion.addFTQAndMapItToCompany( false );
            }

            // Save and deploy survey
            send.iaSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                            new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                         .answerMcQuestion( MCcontactFiled.GENDERLOCAL.question,
                                                                                                            new String[]{ MCcontactFiled.GENDERLOCAL.option_2 } )
                                                                                         .answerMcQuestion( MCcontactFiled.COLORLOCAL.question,
                                                                                                            new String[]{ MCcontactFiled.COLORLOCAL.option_1 } )
                                                                                         .answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                                            new String[]{ MCcontactFiled.COLORGLOBAL.option_2 } )
                                                                                         .answerFtQuestion( FTcontactField.HOBBYLOCAL.question,
                                                                                                            "Programming" )
                                                                                         .answerFtQuestion( FTcontactField.HOBBYGLOBAL.question,
                                                                                                            "Programming" )
                                                                                         .answerFtQuestion( FTcontactField.EMPLOYEEGLOBAL.question,
                                                                                                            "QA" )
                                                                                         .answerFtQuestion( FTcontactField.EMPLOYEELOCAL.question,
                                                                                                            "Programming" )
                                                                                         .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                            emailAddress )
                                                                                         .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                            lastName );

            if( isCompanyMapped ) {
                send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.COMPANY.question, companyName );
            }

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            par.questions.add( FTcontactField.EMAIL.conntactFiled );
            par.questions.add( MCcontactFiled.GENDERGLOBAL.name );
            par.questions.add( MCcontactFiled.COLORGLOBAL.name );
            par.questions.add( MCcontactFiled.GENDERLOCAL.name );
            par.questions.add( MCcontactFiled.COLORLOCAL.name );
            par.questions.add( FTcontactField.HOBBYGLOBAL.conntactFiled );
            par.questions.add( FTcontactField.LASTNAME.conntactFiled );
            par.questions.add( FTcontactField.EMPLOYEEGLOBAL.conntactFiled );
            par.questions.add( FTcontactField.HOBBYLOCAL.conntactFiled );
            par.questions.add( FTcontactField.EMPLOYEELOCAL.conntactFiled );
            if( isCompanyMapped ) {
                par.questions.add( FTcontactField.COMPANY.conntactFiled );
            }

            par.answers.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            par.answers.add( MCcontactFiled.GENDERLOCAL.option_2 );
            par.answers.add( MCcontactFiled.GENDERLOCAL.option_1 );
            par.answers.add( MCcontactFiled.GENDERGLOBAL.option_2 );
            par.answers.add( "Programming" );
            par.answers.add( "Programming" );
            par.answers.add( emailAddress );
            par.lastName = lastName;
            if( isCompanyMapped ) {
                par.answers.add( companyName );
            }

            par.folderName = eventFolder;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[1].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    private void completeSurveyWithMappingMultipleQuestionToFieldTypeListWithMultipleValues(
                                                                                             String surveyType,
                                                                                             String eventFolder,
                                                                                             String theme )
                                                                                                           throws Exception {

        Contact par = new Contact();
        String number = driver.getTimestamp();
        String emailAddress = number + "emailLinked@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType + eventFolder;

        log.startTest( "successfully completed surveywebDriver.type '"
                       + surveyType
                       + "' in '"
                       + eventFolder
                       + "' with Multiple questions mapped to Global and Local additional fieldswebDriver.type FText, MChoice Questions that has multiple values" );
        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType, eventFolder ).iaQuestion.editFTQAndMapItToEmail( false )
                                                                                                             .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                                                                                                     MCcontactFiled.COLORGLOBAL,
                                                                                                                                     MCQuestionAnswerType.MULTIPLEANSWERS )
                                                                                                             .addOrUpdateMCQuestion( QuestionStatus.DRAGANDDROP,
                                                                                                                                     MCcontactFiled.COLORLOCAL,
                                                                                                                                     MCQuestionAnswerType.MULTIPLEANSWERS )
                                                                                                             .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                           FTcontactField.HOBBYGLOBAL,
                                                                                                                                           false )
                                                                                                             .addFTQAndMapItToLastName( false );
            send.iaSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.HOBBYGLOBAL.question,
                                                                                                            "Programming" )
                                                                                         .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                            lastName )
                                                                                         .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                            emailAddress )
                                                                                         .answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                                            new String[]{ MCcontactFiled.COLORGLOBAL.option_1,
                                                                                                                    MCcontactFiled.COLORGLOBAL.option_2 } )
                                                                                         .answerMcQuestion( MCcontactFiled.COLORLOCAL.question,
                                                                                                            new String[]{ MCcontactFiled.COLORLOCAL.option_1,
                                                                                                                    MCcontactFiled.COLORLOCAL.option_2 } );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            par.questions.add( FTcontactField.EMAIL.conntactFiled );
            par.questions.add( MCcontactFiled.COLORGLOBAL.name );
            par.questions.add( MCcontactFiled.COLORLOCAL.name );
            par.questions.add( FTcontactField.HOBBYGLOBAL.conntactFiled );
            par.questions.add( FTcontactField.LASTNAME.conntactFiled );

            par.answers.add( "Programming" );
            par.answers.add( emailAddress );
            par.answers.add( MCcontactFiled.COLORGLOBAL.option_1 );
            par.answers.add( MCcontactFiled.COLORGLOBAL.option_2 );
            par.answers.add( MCcontactFiled.COLORLOCAL.option_1 );
            par.answers.add( MCcontactFiled.COLORLOCAL.option_2 );
            par.lastName = lastName;
            par.folderName = eventFolder;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[1].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyMarkitngListNoSponsorFolderMultipleQuestionsLinkedToGFAndLFNewContact()
                                                                                                                  throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( false,
                                                                             surveyType3,
                                                                             eventFolderNoSponsor,
                                                                             "Oceanic" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeMarkitngListSponsorfolderMultipleQuestionLinkedToGFAndLFNewContact()
                                                                                                                   throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( false,
                                                                             surveyType2,
                                                                             eventFolderWithSponsor,
                                                                             "Water" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Survey can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeAdministrativefolderMultipleQuestionsLinkedToGFAndLFNewContact()
                                                                                                               throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( false,
                                                                             surveyType1,
                                                                             eventFolderAdministrative,
                                                                             "Aqua" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyMarkitngListNoSponsorFolderMultipleQuestionsLinkedToGFAndLFNewCompany()
                                                                                                                  throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( true,
                                                                             surveyType3,
                                                                             eventFolderNoSponsor,
                                                                             "Oceanic" );
    }

    /**
         * 
         * Verify that SurveywebDriver.type Survey can be completed successfully with linked
         * questions.
         * 
         * @throws Exception
         * 
         */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeAdministrativefolderMultipleQuestionsLinkedToGFAndLFNewCompnay()
                                                                                                               throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( true,
                                                                             surveyType1,
                                                                             eventFolderAdministrative,
                                                                             "Aqua" );
    }

    /**
        * 
        * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
        * questions.
        * 
        * @throws Exception
        * 
        */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyTypeMarkitngListSponsorfolderMultipleQuestionLinkedToGFAndLFNewCompany()
                                                                                                                   throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( true,
                                                                             surveyType2,
                                                                             eventFolderWithSponsor,
                                                                             "Water" );
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorContactOnlyFolderMultipleQuestionsLinkedToGFAndLFNewContact()
                                                                                                                 throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( false,
                                                                             surveyType3,
                                                                             MarketingListNoSponsorContactOnly,
                                                                             "Oceanic" );
    }

    /* @Test(groups = { "survey-completion", "all-tests" })
     public void successfullyCompletedSurveyTypeAdministrativeContactOnlyFolderMultipleQuestionsLinkedToGFAndLFNewContact()
                                                                                                                           throws Exception {

         completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( false,
                                                                              surveyType1,
                                                                              AdminContactOnly,
                                                                              "Aqua" );
     }*/

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorContactOnlyFolderMultipleQuestionLinkedToGFAndLFNewContact()
                                                                                                                  throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( false,
                                                                             surveyType2,
                                                                             MarketingListSposorContactOnly,
                                                                             "Water" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Survey can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderMCQuestionLinkedToFGNewContact()
                                                                                                        throws Exception {

     completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                  "multiple choice",
                                                                  surveyType1,
                                                                  true,
                                                                  eventFolderAdministrative,
                                                                  GlobalFieldTypeList1,
                                                                  "",
                                                                  "Aqua" );
    }*/

    /**
     * 
     * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderMCQuestionLinkedToGFNewContact()
                                                                                                                      throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     true,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderMCQuestionLinkedToGFNewContact()
                                                                                                              throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     true,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderMCQuestionLinkedToLFNewContact()
                                                                                                              throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     true,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERLOCAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderMCQuestionLinkedToLFNewContact()
                                                                                                                      throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     true,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERLOCAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Survey can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderMCQuestionLinkedToLFNewContact()
                                                                                                        throws Exception {

     completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                  "multiple choice",
                                                                  surveyType1,
                                                                  true,
                                                                  eventFolderAdministrative,
                                                                  LocalFieldTypeList1,
                                                                  "",
                                                                  "Aqua" );
    }*/

    /**
     * 
     * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderFTQuestionLinkedToGFNewContact()
                                                                                                              throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     false,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYGLOBAL,
                                                                     "Oceanic" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderFTQuestionLinkedToGFNewContact()
                                                                                                                      throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     false,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYGLOBAL,
                                                                     "Water" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Survey can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderFTQuestionLinkedToGFNewContact()
                                                                                                        throws Exception {

     completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                  "free text",
                                                                  surveyType1,
                                                                  false,
                                                                  eventFolderAdministrative,
                                                                  "",
                                                                  GlobalFieldTypeFreeText1,
                                                                  "Aqua" );
    }*/

    /**
     * 
     * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderFTQuestionLinkedToLFNewContact()
                                                                                                              throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     false,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderFTQuestionLinkedToLFNewContact()
                                                                                                                      throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     false,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Survey can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderFTQuestionLinkedToLFNewContact()
                                                                                                        throws Exception {

     completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                  "free text",
                                                                  surveyType1,
                                                                  false,
                                                                  eventFolderAdministrative,
                                                                  "",
                                                                  LocalFieldTypeFreeText1,
                                                                  "Aqua" );
    }*/

    /**
     * 
     * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderFTQuestionLinkedToGFNewContactNewCompany()
                                                                                                                        throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType3,
                                                                     false,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYGLOBAL,
                                                                     "Oceanic" );
    }

    /**
    * 
    * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
    * questions.
    * 
    * @throws Exception
    * 
    */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderFTQuestionLinkedToLFNewContactNewCompany()
                                                                                                                        throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType3,
                                                                     false,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    /**
        * 
        * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
        * questions.
        * 
        * @throws Exception
        * 
        */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderMCQuestionLinkedToGFNewContactNewCompany()
                                                                                                                        throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType3,
                                                                     true,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    /**
        * 
        * Verify that SurveywebDriver.type Feedback Form can be completed successfully with linked
        * questions.
        * 
        * @throws Exception
        * 
        */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyMarkitngListNoSponsorFolderMCQuestionLinkedToLFNewContactNewCompany()
                                                                                                                        throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType3,
                                                                     true,
                                                                     eventFolderNoSponsor,
                                                                     MCcontactFiled.GENDERLOCAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    /**
        * 
        * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
        * questions.
        * 
        * @throws Exception
        * 
        */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderFTQuestionLinkedToGFNewContactNewCompany()
                                                                                                                                throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType2,
                                                                     false,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYGLOBAL,
                                                                     "Water" );
    }

    /**
    * 
    * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
    * questions.
    * 
    * @throws Exception
    * 
    */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderFTQuestionLinkedToLFNewContactNewCompany()
                                                                                                                                throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType2,
                                                                     false,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    /**
         * 
         * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
         * questions.
         * 
         * @throws Exception
         * 
         */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderMCQuestionLinkedToGFNewContactNewCompany()
                                                                                                                                throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType2,
                                                                     true,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    /**
         * 
         * Verify that SurveywebDriver.type Registration Form can be completed successfully with linked
         * questions.
         * 
         * @throws Exception
         * 
         */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeRegistrationMarkitngListSponsorIAfolderMCQuestionLinkedToLFNewContactNewCompany()
                                                                                                                                throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                     surveyType2,
                                                                     true,
                                                                     eventFolderWithSponsor,
                                                                     MCcontactFiled.GENDERLOCAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    /**
     * 
     * Verify that SurveywebDriver.type Survey can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderFTQuestionLinkedToGFNewContactNewCompany()
                                                                                                                  throws Exception {

     completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                  "free text",
                                                                  surveyType1,
                                                                  false,
                                                                  eventFolderAdministrative,
                                                                  "",
                                                                  GlobalFieldTypeFreeText1,
                                                                  "Aqua" );
    }*/

    /**
      * 
      * Verify that SurveywebDriver.type Survey can be completed successfully with linked
      * questions.
      * 
      * @throws Exception
      * 
      */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderFTQuestionLinkedToLFNewContactNewCompany()
                                                                                                                 throws Exception {

    completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                 "free text",
                                                                 surveyType1,
                                                                 false,
                                                                 eventFolderAdministrative,
                                                                 "",
                                                                 LocalFieldTypeFreeText1,
                                                                 "Aqua" );
    }*/

    /**
     * 
     * Verify that SurveywebDriver.type Survey can be completed successfully with linked
     * questions.
     * 
     * @throws Exception
     * 
     */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderMCQuestionLinkedToFGNewContactNewCompany()
                                                                                                                  throws Exception {

     completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                  "multiple choice",
                                                                  surveyType1,
                                                                  true,
                                                                  eventFolderAdministrative,
                                                                  GlobalFieldTypeList1,
                                                                  "",
                                                                  "Aqua" );
    }
    */
    /**
      * 
      * Verify that SurveywebDriver.type Survey can be completed successfully with linked
      * questions.
      * 
      * @throws Exception
      * 
      */
    /*
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeIAfolderMCQuestionLinkedToLFNewContactNewCompany()
                                                                                                                 throws Exception {

    completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( true,
                                                                 "multiple choice",
                                                                 surveyType1,
                                                                 true,
                                                                 eventFolderAdministrative,
                                                                 LocalFieldTypeList1,
                                                                 "",
                                                                 "Aqua" );
    }*/

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyNoSponsorContactOnlyFolderFTQuestionLinkedToGFNewContact()
                                                                                                             throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     false,
                                                                     MarketingListNoSponsorContactOnly,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYGLOBAL,
                                                                     "Oceanic" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyNoSponsorContactOnlyFolderFTQuestionLinkedToLFNewContact()
                                                                                                             throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     false,
                                                                     MarketingListNoSponsorContactOnly,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyNoContactOnlySponsorFolderMCQuestionLinkedToGFNewContact()
                                                                                                             throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     true,
                                                                     MarketingListNoSponsorContactOnly,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedFeedbackSurveyNoSponsorContactOnlyFolderMCQuestionLinkedToLFNewContact()
                                                                                                             throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType3,
                                                                     true,
                                                                     MarketingListNoSponsorContactOnly,
                                                                     MCcontactFiled.GENDERLOCAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Oceanic" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationSponsorContactOnlyIAfolderFTQuestionLinkedToGFNewContact()
                                                                                                                     throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     false,
                                                                     MarketingListSposorContactOnly,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYGLOBAL,
                                                                     "Water" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationSponsorContactOnlyIAfolderFTQuestionLinkedToLFNewContact()
                                                                                                                     throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     false,
                                                                     MarketingListSposorContactOnly,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationSponsorContactOnlyIAfolderMCQuestionLinkedToGFNewContact()
                                                                                                                     throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     true,
                                                                     MarketingListSposorContactOnly,
                                                                     MCcontactFiled.GENDERGLOBAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationSponsorContactOnlyIAfolderMCQuestionLinkedToLFNewContact()
                                                                                                                     throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     surveyType2,
                                                                     true,
                                                                     MarketingListSposorContactOnly,
                                                                     MCcontactFiled.GENDERLOCAL,
                                                                     FTcontactField.HOBBYLOCAL,
                                                                     "Water" );
    }

    /* @Test(groups = { "survey-completion", "all-tests" })
     public void successfullyCompletedSurveyTypeSurveyAdministrativeContactOnlyIAfolderFTQuestionLinkedToGFNewContact()
                                                                                                                       throws Exception {

         completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                      "free text",
                                                                      surveyType1,
                                                                      false,
                                                                      AdminContactOnly,
                                                                      "",
                                                                      GlobalFieldTypeFreeText1,
                                                                      "Aqua" );
     }*/

    /* @Test(groups = { "survey-completion", "all-tests" })
     public void successfullyCompletedSurveyTypeSurveyAdministrativeContactOnlyIAfolderFTQuestionLinkedToLFNewContact()
                                                                                                                       throws Exception {

         completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                      "free text",
                                                                      surveyType1,
                                                                      false,
                                                                      AdminContactOnly,
                                                                      "",
                                                                      LocalFieldTypeFreeText1,
                                                                      "Aqua" );
     }*/

    /*@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSurveyAdministrativeContactOnlyIAfolderMCQuestionLinkedToFGNewContact()
                                                                                                                      throws Exception {

        completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                     "multiple choice",
                                                                     surveyType1,
                                                                     true,
                                                                     AdminContactOnly,
                                                                     GlobalFieldTypeList1,
                                                                     "",
                                                                     "Aqua" );
    }
    */
    /*  @Test(groups = { "survey-completion", "all-tests" })
      public void successfullyCompletedSurveyTypeSurveyAdministrativeContactOnlyIAfolderMCQuestionLinkedToLFNewContact()
                                                                                                                        throws Exception {

          completeSurveyWithMappingQuestionToFieldTypeListAndFreeText( false,
                                                                       "multiple choice",
                                                                       surveyType1,
                                                                       true,
                                                                       AdminContactOnly,
                                                                       LocalFieldTypeList1,
                                                                       "",
                                                                       "Aqua" );
      }
    */
    /**
     * verify that survey can be completed but company must not be created when
     * mapping the survey to an event folderwebDriver.type person contact only
     * 
     * @throws Exception
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorContactOnlyFolderMultipleQuestionsLinkedToGFAndLFNewCompanyMustNotCreated()
                                                                                                                               throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( true,
                                                                             surveyType3,
                                                                             MarketingListNoSponsorContactOnly,
                                                                             "Oceanic" );
    }

    /** * verify that survey can be completed but company and contact must not be created when
     * mapping the survey to an event folderwebDriver.type company only
     * 
     * @throws Exception
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorContactOnlyFolderMultipleQuestionsLinkedToGFAndLFNewCompanyAndContactMustNotCreated()
                                                                                                                                         throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListAndFreeText( true,
                                                                             surveyType3,
                                                                             MarketingListNoSponsorCompanyOnly,
                                                                             "Oceanic" );
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderMultipleQuestionsLinkedWithMultipleValuesToNewContact()
                                                                                                                 throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListWithMultipleValues( surveyType3,
                                                                                    eventFolderNoSponsor,
                                                                                    "Oceanic" );
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorContactOnlyFolderMultipleQuestionsLinkedWithMultipleValuesToNewContact()
                                                                                                                            throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListWithMultipleValues( surveyType3,
                                                                                    MarketingListNoSponsorContactOnly,
                                                                                    "Oceanic" );
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeSponsorfolderMultipleQuestionLinkedWithMultipleValuesToNewContact()
                                                                                                                  throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListWithMultipleValues( surveyType2,
                                                                                    eventFolderWithSponsor,
                                                                                    "Water" );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorContactOnlyFolderMultipleQuestionLinkedWithMultipleValuesToNewContact()
                                                                                                                             throws Exception {

        completeSurveyWithMappingMultipleQuestionToFieldTypeListWithMultipleValues( surveyType2,
                                                                                    MarketingListSposorContactOnly,
                                                                                    "Water" );
    }

}
