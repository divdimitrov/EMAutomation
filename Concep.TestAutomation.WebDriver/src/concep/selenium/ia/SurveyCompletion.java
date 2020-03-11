package concep.selenium.ia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.IA.Api.Contact;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;

public class SurveyCompletion extends BaseIA {
    private String  contactSearchFolder = "Firm Contacts - People";
    private String  companySearchFolder = "Firm Contacts - Companies";
    private String  sourceFolder        = "Firm Contacts - Companies";
    private boolean isSourced           = true;

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
        surveyEnableXPath = driver.config.getProperty( "surveyEnableXpath" );
        clearAndCreatConnection( sourceFolder, isSourced );

        driver.stopSelenium();

    }

    /**
     * 
     * Verify that by Completing a Survey an Existing Company or contact can Successfully be Updated
     * 
     * @throws Exception 
     * 
     */
    //Need to be reworked
    private void successfullyUpatingExistingContacts(
                                                      String testDescription,
                                                      boolean isCompany,
                                                      String surveyType,
                                                      String eventFolder,
                                                      String eventFolderOfExistingContact ) throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that a survey can be successfully completed with NO Additional fields in '"
                       + eventFolder + "' event folder, to an existing '" + testDescription + "'" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@test.com";
        String lastName = number + "lastName";
        String company = number + "company";
        String firstName = number + "firstName";
        String surveyName = number + surveyType + eventFolder;
        String theme = "Aqua";

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType, eventFolder ).iaQuestion.editFTQAndMapItToEmail( false );

            if( isCompany ) {
                //Add New Free Text Question For Company.
                send.iaSurvey.iaQuestion.addFTQAndMapItToCompany( false );

            }
            //Add New Free Text Question For Last Name.
            send.iaSurvey.iaQuestion.addFTQAndMapItToLastName( false ).addFTQAndMapItToFirstName( false );
            send.iaSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey();

            if( isCompany ) {

                log.startStep( "Creating an existing company " + company + " via ia Api" );
                interAction.connection.login();
                interAction.addPerson( "",
                                       emailAddress,
                                       interAction.getFolderId( eventFolderOfExistingContact ),
                                       true,
                                       company );
                log.endStep();

                send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                        .answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
                                        .answerFtQuestion( FTcontactField.COMPANY.question, company );
            } else {

                log.startStep( "Creating an existing contact " + lastName + " via ia Api" );
                interAction.connection.login();
                interAction.addPerson( lastName,
                                       emailAddress,
                                       interAction.getFolderId( eventFolderOfExistingContact ),
                                       false,
                                       "" );
                log.endStep();

                send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                           lastName + "updated" );
            }

            send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );
            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            if( isCompany ) {
                questionsArray.add( FTcontactField.EMAIL.conntactFiled );
                questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
                questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
                questionsArray.add( FTcontactField.COMPANY.conntactFiled );

                par.lastName = lastName;
                answersArray.add( emailAddress );
                answersArray.add( firstName );
                answersArray.add( company );
            } else {

                questionsArray.add( FTcontactField.EMAIL.conntactFiled );
                questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
                questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );

                par.lastName = lastName + "updated";
                answersArray.add( emailAddress );
                answersArray.add( firstName );

            }

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
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

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWithExistingCompany() throws Exception {

        successfullyUpatingExistingContacts( "Company",
                                             true,
                                             surveyType3,
                                             eventFolderNoSponsor,
                                             eventFolderNoSponsor );
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorContactOnlyFolderWithExistingCompany() throws Exception {

        successfullyUpatingExistingContacts( "Company, No Company Must Be Created!",
                                             true,
                                             surveyType3,
                                             MarketingListNoSponsorContactOnly,
                                             MarketingListNoSponsorContactOnly );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWithExistingContact() throws Exception {

        successfullyUpatingExistingContacts( "contact",
                                             false,
                                             surveyType3,
                                             eventFolderNoSponsor,
                                             eventFolderNoSponsor );
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorCompanyOnlyFolderWithExistingContact() throws Exception {

        successfullyUpatingExistingContacts( "Contact, No Contact Must Be Created!",
                                             false,
                                             surveyType3,
                                             MarketingListNoSponsorCompanyOnly,
                                             MarketingListNoSponsorCompanyOnly );
    }

    /*  @Test(groups = { "survey-completion", "all-tests" })
      public void successfullyCompletedSurveyTypeAdministrativefolderWithExistingCompnay() throws Exception {

          successfullyUpatingExistingContacts( "Company", true, surveyType1, eventFolderAdministrative );
      }*/

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeAdministrativeContactOnlyfolderWithExistingCompnay()
                                                                                                   throws Exception {

        successfullyUpatingExistingContacts( "Company, No Company Must Be Created!",
                                             true,
                                             surveyType1,
                                             AdminContactOnly,
                                             AdminContactOnly );
    }

    /* @Test(groups = { "survey-completion", "all-tests" })
     public void successfullyCompletedSurveyTypeAdministrativefolderWithExistingContact() throws Exception {

         successfullyUpatingExistingContacts( "Contact", false, surveyType1, eventFolderAdministrative );
     }*/

    /*@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeAdministrativeCompanyOnlyfolderWithExistingContact()
                                                                                                   throws Exception {

        successfullyUpatingExistingContacts( "contact, No Contact Must Be Created!",
                                             false,
                                             surveyType1,
                                             AdminCompanyOnly );
    }*/

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorfolderWithExistingCompany() throws Exception {

        successfullyUpatingExistingContacts( "Company",
                                             true,
                                             surveyType2,
                                             eventFolderWithSponsor,
                                             eventFolderWithSponsor );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorContactOnlyfolderWithExistingCompany() throws Exception {

        successfullyUpatingExistingContacts( "Company, No Company Must Be Created!",
                                             true,
                                             surveyType2,
                                             MarketingListSposorContactOnly,
                                             MarketingListSposorContactOnly );
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeSponsorfolderWithExistingContact() throws Exception {

        successfullyUpatingExistingContacts( "Contact",
                                             false,
                                             surveyType2,
                                             eventFolderWithSponsor,
                                             eventFolderWithSponsor );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorCompanyOnlyfolderWithExistingContact() throws Exception {

        successfullyUpatingExistingContacts( "contact, No Contact Must Be Created!",
                                             false,
                                             surveyType2,
                                             MarketingListSposorCompanyOnly,
                                             MarketingListSposorCompanyOnly );
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWithExistingContactInConnectionSearchFolder()
                                                                                                       throws Exception {

        successfullyUpatingExistingContacts( "contact in " + contactSearchFolder + " contact must be updated",
                                             false,
                                             surveyType3,
                                             eventFolderNoSponsor,
                                             contactSearchFolder );
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeSponsorfolderWithExistingCompanyInConnectionSearchFolder()
                                                                                                         throws Exception {

        successfullyUpatingExistingContacts( "Company in" + companySearchFolder
                                                     + " company must be linked to the contact",
                                             true,
                                             surveyType2,
                                             eventFolderWithSponsor,
                                             companySearchFolder );
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveySponsorFolderWithExistingContactInIaButNotInSourceFolder()
                                                                                                     throws Exception {

        successfullyUpatingExistingContacts( "contact in " + eventFolderNoSponsor + " not in "
                                                     + eventFolderWithSponsor
                                                     + " new contact must be created",
                                             false,
                                             surveyType3,
                                             eventFolderWithSponsor,
                                             eventFolderNoSponsor );
    }

    /**
     * 
     * Verify that survey with 10 mapped questions can be completed successfully
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    //TODO it's not using concep.IA.Api
    public void successfullyCompletedSurveyWith10MappedQuestions() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that survey with 10 mapped questions can be completed successfully" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType1 + eventFolderAdministrative;
        String theme = "Survey";

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .addOrUpdateFTQuestionBy( FTcontactField.LASTNAME.question,
                                                              QuestionStatus.NEW );

            driver.showHiddenElement( "qTypetextInteractionField select" );
            String[] interactionField = driver.getDropDownOptions( "//label[@id='qTypetextInteractionField']/select" );
            driver.hideShowenElement( "qTypetextInteractionField select" );

            send.iaSurvey.iaQuestion.mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();
            //Add 7 more questions FTQs.
            for( int i = 3; i <= 9; i++ ) {

                send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId );

                if( interactionField[i].equals( "Primary Email" ) || interactionField[i].equals( "Last Name" ) )
                    continue;

                send.iaSurvey.iaQuestion.enterQuestion( interactionField[i] )
                                        .mapFtQuestionToContactField( interactionField[i] )
                                        .updateQuestion();

                questionsArray.add( interactionField[i] );
            }

            // Save and deploy survey
            send.iaSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                            new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                         .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                            emailAddress )
                                                                                         .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                            lastName );

            //Answer the additional 7 questions
            for( int i = 3; i <= 9; i++ ) {
                if( interactionField[i].equals( "Primary Email" ) || interactionField[i].equals( "Last Name" ) )
                    continue;
                send.iaSurvey.iaQuestion.answerFtQuestion( interactionField[i], "Answer" );

                answersArray.add( "Answer" );
            }

            send.iaSurvey.surveyNext();
            log.startStep( "Save all the parameters to external file" );

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            par.lastName = lastName;
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed with 10 Pages
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWith10Pages() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that survey can be successfully completed on 10 pages" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType2 + eventFolderWithSponsor;
        String theme = "Olive";

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType2, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false ).editMCQAndMapItToGenderG();

            //Go to Page 2
            log.startStep( "Click the button Page 2" );
            driver.click( "//span[contains(text(),'Page 2')]", driver.timeOut );
            log.endStep();

            //Edit the Thank you question
            send.iaSurvey.iaQuestion.editQuestionType( "Thank you" )
                                    .changeQuestionType( element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled );

            driver.showHiddenElement( "qTypetextInteractionField select" );
            String[] interactionField = driver.getDropDownOptions( "//label[@id='qTypetextInteractionField']/select" );
            driver.hideShowenElement( "qTypetextInteractionField select" );

            send.iaSurvey.iaQuestion.updateQuestion();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();
            //Add 7 more questions
            for( int i = 3; i <= 9; i++ ) {
                if( interactionField[i].equals( FTcontactField.EMAIL.conntactFiled )
                    || interactionField[i].equals( FTcontactField.LASTNAME.conntactFiled ) )
                    continue;

                String pageName = "page" + Integer.toString( i );
                //Add a Page
                send.iaSurvey.addAndGoToPage( pageName ).iaQuestion.addNewQuestionType( "Free Text",
                                                                                        element.ftQId )
                                                                   .enterQuestion( interactionField[i] )
                                                                   .mapFtQuestionToContactField( interactionField[i] )
                                                                   .updateQuestion();
                questionsArray.add( interactionField[i] );
            }

            //Add Page
            send.iaSurvey.addAndGoToPage( "Last Page" ).iaQuestion.addNewQuestionType( "Content",
                                                                                       element.contentQId )
                                                                  .enterQuestion( "Thank You" )
                                                                  .updateQuestion();
            send.iaSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                            new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                         .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                            emailAddress );

            log.startStep( "Click the Next button" );
            driver.click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.LASTNAME.question, lastName );

            //Answer the additional 7 questions
            for( int i = 3; i <= 9; i++ ) {
                if( interactionField[i].equals( "Email" ) || interactionField[i].equals( "Last Name" ) )
                    continue;

                log.startStep( "Click the Next button" );
                driver.click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]",
                              driver.timeOut );
                log.endStep();

                send.iaSurvey.iaQuestion.answerFtQuestion( interactionField[i], "Answer" );

                answersArray.add( "Answer" );

            }

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( emailAddress );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWith3Pages() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that survey with 3 pages can be successfully completed" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String surveyName = number + surveyType3 + eventFolderNoSponsor;

        try {
            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false ).editMCQAndMapItToGenderG();

            //Go to Page 2.
            log.startStep( "Click the button Page 2" );
            driver.click( "//span[contains(text(),'Page 2')]", driver.timeOut );
            log.endStep();

            //Edit the Thank you question.
            send.iaSurvey.iaQuestion.editQuestionType( "Thank you" )
                                    .changeQuestionType( element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addFTQAndMapItToFirstName( false );

            //Add Page
            send.iaSurvey.addAndGoToPage( "Last Page" ).iaQuestion.addNewQuestionType( "Content",
                                                                                       element.contentQId )
                                                                  .enterQuestion( "Thank you" )
                                                                  .updateQuestion();
            send.iaSurvey.saveAndSelectTheme( Theme.DISCO.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress );

            log.startStep( "Click the Next button" );
            driver.click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
                                    .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWith5TypesOfQuestions() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that survey can be completed with 5 different questions" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType4 + eventFolderWithSponsor;
        String theme = "Disco";
        String matrixValue1 = "Matrix Value 1";
        String matrixValue2 = "Matrix Value 2";
        String matrixValue3 = "Matrix Value 3";
        String matrixValue4 = "Matrix Value 3";

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType4, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .addFTQAndMapItToLastName( false );

            //Add Matrix Question
            send.iaSurvey.iaQuestion.addNewQuestionType( "Matrix", element.matrixQId )
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

            send.iaSurvey.iaQuestion.updateQuestion();

            // Go to Page 2
            log.startStep( "Click the button Page 2" );
            driver.click( "//span[contains(text(),'Page 2')]", driver.timeOut );
            log.endStep();

            //Add Result Question
            send.iaSurvey.iaQuestion.addNewQuestionType( "Results", element.resultsQId )
                                    .enterQuestion( "results" )
                                    .updateQuestion();
            send.iaSurvey.saveAndSelectTheme( theme ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                            new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
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

            //Press Next
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyWithDragAndDropAndAddQuestion() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that surveys can be successfully completed with drag and drop question" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType1 + eventFolderAdministrative;

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .dragAndDropFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.name() );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name() );
            questionsArray.add( FTcontactField.LASTNAME.name() );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( lastName );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

        Contact par = new Contact();

        log.startTest( "Verify that survey can be completed successfully with drag and drop questions on three pages." );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String surveyName = number + surveyType2 + eventFolderWithSponsor;

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType2, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.deleteMcQuestionType( "A multiple choice question" )
                                    .dragAndDropFTQAndMapItToEmail( false )
                                    .deleteFtQuestionType( "A free text question" )
                                    .dragAndDropMCQAndMapItToGenderG();

            // Go to Page 2.
            log.startStep( "Click the button Page 2" );
            driver.click( "//span[contains(text(),'Page 2')]", driver.timeOut );
            log.endStep();

            // Add Free Text Question with Drag and Drop for Last Name.
            send.iaSurvey.iaQuestion.dragAndDropFTQAndMapItToLastName( false )
                                    .deleteContentQuestionType( "Thank you" )
                                    .dragAndDropFTQAndMapItToFirstName( false );

            send.iaSurvey.addAndGoToPage( "Page 3" ).iaQuestion.addContentQuestion( true, "Thank you" );
            send.iaSurvey.saveAndSelectTheme( Theme.DISCO.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress );

            //Press Next
            log.startStep( "Click the Next button" );
            driver.click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName )
                                    .answerFtQuestion( FTcontactField.LASTNAME.question, lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropQuestionsOnly() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that surveys can successfully be completed with Drag and drop questions only." );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType3 + eventFolderNoSponsor;

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.deleteMcQuestionType( "A multiple choice question" )
                                    .dragAndDropFTQAndMapItToEmail( false )
                                    .deleteFtQuestionType( "A free text question" )
                                    .dragAndDropFTQAndMapItToLastName( false )
                                    .dragAndDropMCQAndMapItToColorG();

            send.iaSurvey.saveAndSelectTheme( Theme.RED.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                                                     new String[]{ MCcontactFiled.COLORGLOBAL.option_1 } )
                                                                                                  .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                     emailAddress )
                                                                                                  .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                     lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * Verify that user can 'login as' a different account and successfully complete a survey
     * FirstName is valid for Manual_QA_Surveys_IA user, if you want to run this test with another user, edit the Firstname
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyWithLoginAs() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that user can use the feature 'login as' and a survey can be successfully completed" );

        String number = driver.getTimestamp();
        String FirstName = "InterAction";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType4 + eventFolderNoSponsor;

        try {

            log.startStep( "Log in Send and go to admin Page" );
            send.loginToSend( sendSuperUser, sendSuperPassword ).goToAdmin();
            log.endStep();

            log.startStep( "Click the 'Users' tab" );
            driver.click( "//ul[@id='typesMenu']//span[contains(text(),'Users')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the 'User' page is loaded" );
            log.endStep( driver.isElementPresent( "//li[@id='s_nav-tabsUsers']//span[contains(text(),'Users')]",
                                                  driver.timeOut ) );

            log.startStep( "Type '" + iaSendUser + "' in the search text box" );
            driver.type( "//input[@id='txtSearch']", iaSendUser, driver.timeOut );
            log.endStep();

            log.startStep( "Click the 'Search' button" );
            driver.click( "//a[@id='btnSearch']", driver.timeOut );
            log.endStep();

            log.startStep( "Click '" + iaSendUser + "' Tools button" );
            driver.click( "//tr//td[contains(a,'" + iaSendUser + "')]/../td/a[contains(text(),'Tools')]",
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

            log.startStep( "Verify that the user is successfully logged in '" + iaSendUser + "'" );
            log.endStep( driver.isElementPresent( "//li[contains(text(),'" + FirstName + "')]",
                                                  driver.timeOut ) );
            //Create and name the survey.
            send.goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                          .selectSurveyFolders( surveyType4, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToColorG()
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.FUNKY.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.COLORGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.COLORGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.COLORGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * data driven method to verify that survey can be completed without answering all the questions 
     * test case is created to cover GT-23
     * @throws Exception 
     * 
     */
    private void successfullyCompletedSurveyWithoutAnsweringAllQuestions(
                                                                          String testDescription,
                                                                          boolean isMCMapped,
                                                                          boolean isFTMapped )
                                                                                              throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that survey can successfully be completed without answering "
                       + testDescription );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType1 + eventFolderAdministrative;

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderAdministrative );
            if( isMCMapped ) {
                send.iaSurvey.iaQuestion.editMCQAndMapItToColorG();
            }

            if( isFTMapped ) {
                send.iaSurvey.iaQuestion.addFTQAndMapItToCompany( false );
            }

            //Edit the already existing Free Text Question.
            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false ).addFTQAndMapItToLastName( false );

            // Save and deploy survey
            send.iaSurvey.saveAndSelectTheme( Theme.DEFAULT.type ).viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                         emailAddress )
                                                                                                      .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                         lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

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
     * Verify that surveys can be successfully completed without answering 
     * mapped Multiple choice question 
     *  
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompleteSurveyWithoutAnsweringMCMappedQuestion() throws Exception {

        successfullyCompletedSurveyWithoutAnsweringAllQuestions( "mapped multiple choice question",
                                                                 true,
                                                                 false );
    }

    /**
     * 
     * Verify that surveys can be successfully completed without answering 
     * mapped Multiple choice question 
     *  
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompleteSurveyWithoutAnsweringFTMappedQuestion() throws Exception {

        successfullyCompletedSurveyWithoutAnsweringAllQuestions( "free text question", false, true );
    }

    /**
     * 
     * Verify that surveys can be successfully completed without answering 
     * mapped Multiple choice question 
     *  
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompleteSurveyWithoutAnsweringMCQuestion() throws Exception {

        successfullyCompletedSurveyWithoutAnsweringAllQuestions( "un-mapped multiple choice question",
                                                                 false,
                                                                 false );
    }

    /**
     * 
     * Verify that survey with RSVP question can be successfully completed
     * 
     * @throws Exception
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithRSVPQuestion() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that survey with RSVP question can be successfully completed" );
        try {

            String number = driver.getTimestamp();
            String emailAddress = number + "emailLinked@concep.com";
            String lastName = number + "lastName";
            String surveyName = number + surveyType1 + eventFolderAdministrative;

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false );

            //Edit the already existing Multiple Choice Question TO RSVP.
            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .enterQuestion( MCcontactFiled.GENDERGLOBAL.question )
                                    .showQuestionSettings( "qTypemc" );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 );

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Delete unnecessary answer options" );
            driver.click( "//ul[@id='qAnswerList']/li[3]//a[contains(text(), 'Delete')]", driver.timeOut );
            driver.click( "//ul[@id='qAnswerList']/li[3]//a[contains(text(), 'Delete')]", driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.updateQuestion();

            //Add New Free Text Question.
            send.iaSurvey.iaQuestion.addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

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

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDifferentSpecialCharacters() throws Exception {

        Contact par = new Contact();

        String surveyNumber = driver.getTimestamp();
        String lastName = surveyNumber + "lastName";
        String surveyName = surveyNumber + "SurveyWithDifferentSpecialCharacters";
        String number = driver.getTimestamp();
        String emailLocalPart = number + "testemail";
        String emailDomainPart = "@concep.com";

        log.startTest( "Verify that survey must be completed successfully when email contains special legal characters" );

        try {

            // Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType2, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.SPORT.type );

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
                    "~" };

            for( int i = 0; i < specialCharacters.length; i++ ) {

                send.iaSurvey.viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                    new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    emailLocalPart
                                                                                            + specialCharacters[i]
                                                                                            + emailDomainPart )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );
                send.iaSurvey.surveyNext();

                questionsArray = new ArrayList<>();
                answersArray = new ArrayList<>();

                questionsArray.add( FTcontactField.EMAIL.conntactFiled );
                questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
                questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

                answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
                answersArray.add( emailLocalPart + specialCharacters[i] + emailDomainPart );
                par.lastName = lastName;

                for( int j = 0; j < questionsArray.size(); j++ ) {

                    par.questions.add( questionsArray.get( j ) );
                }

                for( int j = 0; j < answersArray.size(); j++ ) {

                    par.answers.add( answersArray.get( j ) );
                }

                driver.close();

                driver.selectWindow();
            }
            log.startStep( "Save all the parameters to external file" );
            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();
            driver.close();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * verify that an new company is successfully created when 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCreatedCompanyInSponsorFolderWhenPartOfCompanyNameExist() throws Exception {

        Contact par = new Contact();

        log.startTest( "Verify that a company is successfully created in sponsor folder when part of the company name exist" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@test.com";
        String lastName = number + "lastName";
        String company = number + "company";
        String firstName = number + "firstName";
        String surveyName = number + surveyType3 + eventFolderWithSponsor;

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToCompany( false )
                                    .addFTQAndMapItToLastName( false )
                                    .addFTQAndMapItToFirstName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey();

            log.startStep( "Creating an existing company " + company + " via ia Api" );
            interAction.connection.login();
            interAction.addPerson( "",
                                   emailAddress,
                                   interAction.getFolderId( eventFolderWithSponsor ),
                                   true,
                                   company );
            log.endStep();

            send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                    .answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
                                    .answerFtQuestion( FTcontactField.COMPANY.question, company )
                                    .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.COMPANY.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );

            answersArray.add( emailAddress );
            answersArray.add( company );
            answersArray.add( firstName );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed with different special characters in emails
     * 
     * @throws Exception
     * 
     */

    private void completedSurveyWithDifferentEmails(
                                                     String surveyName,
                                                     String emailAddress,
                                                     String desciption ) throws Exception {

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String lastName = number + "lastName";
        log.startTest( "Verify that survey can be successfully completed with " + desciption );
        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType4, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.RED.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                     new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                  .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                     emailAddress )
                                                                                                  .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                     lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

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
     * Verify that survey can be successfully completed with Special Characters in the email address -
     * ! # $ % & ' * + - / = ? ^ _ ` { | } ~
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithSpecialCharacters() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmails( number + "specialCharatersInEmail",
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

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithHyphen() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmails( number + "EmailWithHyphen",
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

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDigits0to9InTheEmail() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmails( number + "Digits0to9InTheEmail",
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

        completedSurveyWithDifferentEmails( number + "EmailWithDot",
                                            number + "email.test@concep.com",
                                            "Dot '.' in the email" );
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

        log.startTest( "Verify that a warning message is displayed when already submitted survey is opened again" );
        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType4, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.DISCO.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );
            send.iaSurvey.surveyNext();

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

            log.startStep( "Verify that the survey type is retained as " + surveyType4 );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep( driver.isSelected( "//select[@id='surveyTypeDropDown']", surveyType4 ) );

            log.startStep( "Verify that the campaign type is retained as " + eventFolderWithSponsor );
            log.endStep( driver.isSelected( "//select[@id='surveyInteractionFolder']", eventFolderWithSponsor ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /**
     * 
     * Verify that survey can be successfully completed with required free text question
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyWithRequiredFTQ() throws Exception {

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithRequireQuestion";
        String lastName = number + "lastName";
        String emailAddress = number + "email@concep.com";

        log.startTest( "Verify that survey can be successfully completed with required free text question" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( true )
                                    .editMCQAndMapItToGenderG()
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey();

            log.startStep( "Verify the validation of the required free text question" );
            driver.click( "//a[@id='btnNext']/span", driver.timeOut );
            log.endStep( driver.isTextPresent( "//div[@class='sQ sText sTextSingle']//label[@class='qError']",
                                               "This field is required",
                                               driver.timeOut ) );

            send.iaSurvey.iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                    .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                    .answerFtQuestion( FTcontactField.LASTNAME.question, lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String surveyName = number + "BusinessRequiredMappings";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String jobTitle = number + "jobTitle";
        String company = number + "company";

        log.startTest( "Verify that survey can be successfully completed with First name, Last Name, Job Title and Email Address free text questions" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( true )
                                    .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                            MCcontactFiled.GENDERGLOBAL,
                                                            MCQuestionAnswerType.MANADATORY )
                                    .addFTQAndMapItToLastName( true )
                                    .addFTQAndMapItToFirstName( true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.JOBTITLE,
                                                                  true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.COMPANY,
                                                                  true );

            send.iaSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                      new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                   .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                      emailAddress )
                                                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                      lastName )
                                                                                                   .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                      firstName )
                                                                                                   .answerFtQuestion( FTcontactField.JOBTITLE.question,
                                                                                                                      jobTitle )
                                                                                                   .answerFtQuestion( FTcontactField.COMPANY.question,
                                                                                                                      company );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
            questionsArray.add( FTcontactField.JOBTITLE.conntactFiled );
            questionsArray.add( FTcontactField.COMPANY.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( jobTitle );
            answersArray.add( company );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithMCQWithDropDown() throws Exception {

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithDropDown";

        log.startTest( "Verify that survey can be successfully completed with MCQ with drop down" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                            MCcontactFiled.GENDERGLOBAL,
                                                            MCQuestionAnswerType.DROPDOWN )
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().iaQuestion.answerDropDownQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                             MCcontactFiled.GENDERGLOBAL.option_1 )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithMCQWithMultipleAnswers() throws Exception {

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithMultipleAnswers";

        log.startTest( "Verify that survey can be successfully completed with survey which has MCQ with multiple answers" );
        try {
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                            MCcontactFiled.COLORGLOBAL,
                                                            MCQuestionAnswerType.MULTIPLEANSWERS )
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.COLORGLOBAL.option_1,
                                                                                                                               MCcontactFiled.COLORGLOBAL.option_2 } )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.COLORGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.COLORGLOBAL.option_1 );
            answersArray.add( MCcontactFiled.COLORGLOBAL.option_2 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

        Contact par = new Contact();
        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "MCQWithRequiredQuestion";

        log.startTest( "Verify that survey can be successfully completed with required MCQ question" );
        try {
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3,
                                                                         eventFolderAdministrative );
            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                            MCcontactFiled.COLORGLOBAL,
                                                            MCQuestionAnswerType.MANADATORY )
                                    .addFTQAndMapItToLastName( false );

            send.iaSurvey.saveAndSelectTheme( Theme.BEACH.type ).viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName )
                                                                                                    .answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.COLORGLOBAL.option_1 } );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.COLORGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.COLORGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "WithPageJumping";

        log.startTest( "Verify that survey can be successfully completed with page jumping" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType4, eventFolderWithSponsor );
            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false ).addFTQAndMapItToLastName( false );

            // Add Additional Pages
            for( int i = 1; i < 4; i++ ) {
                String pageNumber = Integer.toString( i + 2 );
                send.iaSurvey.addAndGoToPage( "Page " + pageNumber ).goToPageInSurvey( "Page 1" );
            }

            // Edit the default multiple choice question
            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .enterQuestion( MCcontactFiled.COLORGLOBAL.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.COLORGLOBAL )
                                    .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                       MCcontactFiled.COLORGLOBAL.option_2 )
                                    .deleteMcQuestionAnswers( 2 );

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 3" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.iaSurvey.iaQuestion.updateQuestion();
            send.iaSurvey.goToPageInSurvey( "Page 2" );
            send.iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .enterQuestion( MCcontactFiled.GENDERGLOBAL.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.OTHERANSWER );

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 5" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.iaSurvey.iaQuestion.deleteContentQuestionType( "Thank you" );

            send.iaSurvey.iaQuestion.updateQuestion();
            send.iaSurvey.goToPageInSurvey( "Page 3" );

            send.iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.COLORLOCAL.question,
                                                              QuestionStatus.NEW )
                                    .enterQuestion( MCcontactFiled.COLORLOCAL.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.COLORLOCAL.name,
                                                                  MCcontactFiled.COLORLOCAL.option_1,
                                                                  MCcontactFiled.COLORLOCAL.option_2 )
                                    .fillinMCQAnswers( MCcontactFiled.COLORLOCAL.option_1,
                                                       MCcontactFiled.COLORLOCAL.option_2 )
                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.OTHERANSWER );

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 2" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.iaSurvey.iaQuestion.updateQuestion();
            send.iaSurvey.goToPageInSurvey( "Page 5" ).iaQuestion.addNewQuestionType( "content",
                                                                                      element.contentQId )
                                                                 .enterQuestion( "Thank You" )
                                                                 .updateQuestion();

            // Save and deploy survey
            send.iaSurvey.saveAndSelectTheme( "Survey" ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                                               new String[]{ MCcontactFiled.COLORGLOBAL.option_1 } )
                                                                                            .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                               emailAddress )
                                                                                            .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                               lastName );

            log.startStep( "Click the Next button to answer on the 2nd MCQ" );
            driver.click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.answerMcQuestion( MCcontactFiled.COLORLOCAL.question,
                                                       new String[]{ MCcontactFiled.COLORLOCAL.option_1 } );

            log.startStep( "Click the Next button to answer the 3rd MCQ" );
            driver.click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( MCcontactFiled.COLORGLOBAL.name );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( MCcontactFiled.COLORLOCAL.name );

            answersArray.add( MCcontactFiled.COLORGLOBAL.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( MCcontactFiled.COLORLOCAL.option_1 );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "WithContentQuestionWithLink";
        String link = "http://www.lipsum.com/feed/html";

        log.startTest( "Verify that survey can be completed with content question which has link and the link is click-able" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3,
                                                                         eventFolderAdministrative );
            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG()
                                    .addContentQuestion( false, "Content" )
                                    .editQuestionType( "Content" );

            log.startStep( "Open the Collabsed toolbar text edittor for the question" );
            driver.click( "//div[@id='cke_qText']//span[@class='cke_arrow']", driver.timeOut );
            log.endStep();

            log.startStep( "Click on the create link button" );
            driver.click( "//span[@class='cke_button_icon cke_button__link_icon']", driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Add a link" );
            driver.switchToIframeByID( "DOMWindowIframe" );
            driver.waitForPageToLoad();
            driver.clear( "//input[@id='createLinktxtURL']" );
            driver.type( "//input[@id='createLinktxtURL']", link, driver.timeOut );
            log.endStep();

            log.startStep( "Press the 'Create Link to URL' button" );
            driver.click( "//a[@id='createLinkbtnAddURL']/span", driver.timeOut );
            driver.switchToTopWindow();
            log.endStep();

            send.iaSurvey.iaQuestion.updateQuestion();
            send.iaSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                              new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                           .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                              emailAddress )
                                                                                           .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                              lastName );

            // Verify that the link is working
            log.startStep( "Verify that the link is working" );
            log.endStep( driver.isClickable( "//a[contains(text(), 'http://www.lipsum.com/feed/html')]",
                                             driver.timeOut ) );

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
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

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "ContentQuestion-" + description;
        String content = "Nam elementum in enim id posuere. Praesent sed augue non elit accumsan tempor a a sapien. Morbi ac maximus enim. Maecenas et varius ligula. Vestibulum ornare efficitur ipsum. Fusce vel neque gravida, posuere augue finibus, pharetra eros. Sed varius in libero nec dictum. Vestibulum sollicitudin et ante porttitor luctus. Nunc a diam nec tortor efficitur aliquet.";

        log.startTest( "Verify that survey can be successfully completed with additionally added "
                       + description + " content question" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3,
                                                                         eventFolderAdministrative );
            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG()
                                    .addContentQuestion( true, content );

            send.iaSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );

            // Verify the content
            log.startStep( "Verify the content question content is visible" );
            log.endStep( driver.isTextPresent( "//div[@class='sQ sContent']", content, driver.timeOut ) );

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

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

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithEmailConfirmation() throws Exception {

        Contact par = new Contact();
        String number = driver.getTimestamp();
        String emailAddress = number + "email@mailinator.com";
        String lastName = number + "lastName";
        String surveyName = number + "EmailConfirmation";

        log.startTest( "Verify that survey can be successfully completed with email confirmation option turned on" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG()
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.EMAIL,
                                                                  false )
                                    .editQuestionType( FTcontactField.EMAIL.question );

            //.send.iaQuestion.addNewQuestionType( "free text question",
            //                                                                                                        element.ftQId )
            //                                                                                   .enterQuestion( emailQuestion )
            //                                                                                   .mapFtQuestionToContactField( "Primary Email" );

            // Add Email Confirmation to the question
            addEmailConfirmation().send.iaSurvey.iaQuestion.updateQuestion();
            send.iaSurvey.saveSurvey();

            // Create Email confirmation rules for the created contact and mappings
            addEmailConfirmationContactMappings( MCcontactFiled.GENDERGLOBAL.question,
                                                 "Gender",
                                                 FTcontactField.EMAIL.question,
                                                 "Email",
                                                 FTcontactField.LASTNAME.question,
                                                 "Last Name" );
            send.iaSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                       new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                    .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       emailAddress )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

            mailinator.accessMailinator().searchCustomerEmail( emailAddress );

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

        Contact par = new Contact();
        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "SurveyWithLanguagePages";
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3,
                                                                         eventFolderAdministrative );
            send.iaSurvey.iaQuestion.editFTQAndMapItToLastName( false )
                                    .addFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG();

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages )
                                                     .saveSurvey();

            send.goToSurveyTab( "Questions" );
            for( int i = 0; i < ( languages.length ) - 1; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i + 1] ).iaQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                                                                   .enterQuestion( FTcontactField.EMAIL.question
                                                                                                   + " - "
                                                                                                   + languages[i + 1] )
                                                                                   .updateQuestion()
                                                                                   .editQuestionType( FTcontactField.LASTNAME.question )
                                                                                   .enterQuestion( FTcontactField.LASTNAME.question
                                                                                                   + " - "
                                                                                                   + languages[i + 1] )
                                                                                   .updateQuestion()
                                                                                   .editQuestionType( MCcontactFiled.GENDERGLOBAL.question )
                                                                                   .enterQuestion( MCcontactFiled.GENDERGLOBAL.question
                                                                                                   + " -"
                                                                                                   + languages[i + 1] )
                                                                                   .updateQuestion();
            }
            send.iaSurvey.saveAndSelectTheme( Theme.BEACH.type )
                         .viewAndDeployTheSurvey()
                         .switchSurveySubmitionPageLanguage( surveyLanguage ).iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                           new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                           emailAddress )
                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                           lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[1].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithTwoAdditionalLanguagePages() throws Exception {

        log.startTest( "Verify that survey can be successfully completed with Two additional language pages" );
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

    private void completeSurveyAssociatePersonAndCompanyContacts(
                                                                  String contactEmail,
                                                                  String companyName ) throws Exception {

        //Paremeters par = new Paremeters();

        String number = driver.getTimestamp();
        String surveyName = number + "survey";
        String firstName = number + "firstName";
        String lastName = number + "lastName";

        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderWithSponsor );
            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .addFTQAndMapItToFirstName( false )
                                    .addFTQAndMapItToCompany( false );

            send.iaSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                       contactEmail )
                                                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName )
                                                                                                    .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                       firstName )
                                                                                                    .answerFtQuestion( FTcontactField.COMPANY.question,
                                                                                                                       companyName );
            send.iaSurvey.surveyNext();

            /*log.startStep("Save all parameters to external file");
            par.folderName = eventFolderWithSponsor;
            par.email = contactEmail;
            par.lastName = lastName;
            par.firstName = firstName;
            par.companyName = companyName;
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[1].getMethodName(), par, isJson );
            log.endStep();*/

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    //@Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompleteMLVSurveyNotExistingPersonButExistingCompanyWithPrimrayLanguageOnly()
                                                                                                         throws Exception {

        log.startTest( "Verify that when a survey is submitted to not existing person but existing company that has only a primary language set"
                       + " the newly created contact is with primray language matching with the language of the company" );
        completeSurveyAssociatePersonAndCompanyContacts( "not existing email",
                                                         "existing company with primary language only" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyNotExistingPersonButExistingCompanyWithPrimaryAndOtherLanguages()
                                                                                                              throws Exception {

        log.startTest( "Verify that when a survey is submitted to not existing person but existing company that has a primary and other languages set"
                       + " the newly created contact is with primray language matching with the language of the company" );
        completeSurveyAssociatePersonAndCompanyContacts( "not existing email",
                                                         "existing company with primary and other languages" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyNotExistingPersonAndCompany() throws Exception {

        log.startTest( "Verify that when a survey is submitted to not existing person and company the newly created contacts must be with the default primary language" );
        completeSurveyAssociatePersonAndCompanyContacts( "not existing email", "not existing company" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonOnlyPrimaryLanguageAndCompanyWithMatchingPrimrayLanguage()
                                                                                                                     throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company that has only a primary language set"
                       + " which matches the primary language of the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary language only",
                                                         "existing company with primary language that matches the primary language of the contact" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonOnlyPrimaryLanguageAndCompanyWithNotMatchingPrimrayLanguages()
                                                                                                                         throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company that has matching primary languages"
                       + " the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary language",
                                                         "existing company with primary language that differs from the person primary language" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonOnlyPrimaryLanguageAndCompanyWithAtLeastOneOtherLanguageNoMatches()
                                                                                                                              throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company that has at least one other language and none of the languages matching "
                       + "with the primary language of the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary language",
                                                         "existing company with primary and other languages not matching to person primary" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonOnlyPrimaryLanguageAndCompanyWithAtLeastOneOtherLanguageMatches()
                                                                                                                            throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company that has at least one other that matching "
                       + "with the primary language of the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary language",
                                                         "existing company with primary and other languages that matching to person primary" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonOnlyPrimaryLanguageAndNotExistingCompany()
                                                                                                     throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and not existing company "
                       + "the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary language",
                                                         "not existing company" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    // @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonAtLeastOneOtherLanguageAndCompanyMatchingPrimrayLanguage()
                                                                                                                     throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company that has primary language set"
                       + " which matches the primary language of the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary and other languages",
                                                         "existing company with primary language that matches the primary language of the contact" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonAtLeastOneOtherLanguageAndCompanyNotMatchingPrimrayLanguage()
                                                                                                                        throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company that has primary language set"
                       + " which doesn't matches the primary language of the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary and other languages",
                                                         "existing company with primary language that doesn't matches the primary language of the contact" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonAndCompanyWithAtLeastOneOtherLanguagesNotMatching()
                                                                                                              throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company which have at least one other language"
                       + " which doesn't mathcing the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary and other languages",
                                                         "existing company with at least one other language not matching" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    // @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonAndCompanyWithAtLeastOneOtherLanguagesMatchingToPrimary()
                                                                                                                    throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company which have at least one other language"
                       + " and the primary language matches one of the other languages of the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary and other languages",
                                                         "existing company with at least one other language matching to primary" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    //@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonAndCompanyWithAtLeastOneOtherLanguagesMatching()
                                                                                                           throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person and company which have at least one other language"
                       + " that matching one of the other languages of the contact the association of person and company contacts is successfull" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary and other languages",
                                                         "existing company with at least one other language matching" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    // @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteMLVSurveyExistingPersonWithAtLeastOneOtherLanguagesAndNotExistingCompany()
                                                                                                              throws Exception {

        log.startTest( "Verify that when a survey is submitted to existing person but not existing company the company is created with"
                       + " the same primary language as the Person Contact and the association is successful" );
        completeSurveyAssociatePersonAndCompanyContacts( "existing contact with primary and other languages",
                                                         "not existing company" ); //TODO: Test data must be created in IA
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePage()
                                                                                                     throws Exception {

        Contact par = new Contact();

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

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            idFTQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            idMCQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.GENDER.option_1 );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            idFTQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion();

            idMCQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.GENDER.option_2 );

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            for( int i = 0; i < submissionsCount; i++ ) {

                send.iaSurvey.viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                    new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    i + email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastNameFirst )
                                                                 .answerFtQuestion( "Last Name Duplicated",
                                                                                    lastNameSecond )
                                                                 .answerMcQuestion( "Gender Duplicated",
                                                                                    new String[]{ MCcontactFiled.GENDER.option_2 } );
                send.iaSurvey.surveyNext();

                answersArray.add( duplicatedMCQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
                answersArray.add( i + email );
                par.lastName = ( duplicatedFTQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids ) ) );

                driver.close();
                driver.selectWindow();
            }

            log.startStep( "Save all the parameters to external file" );

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

            log.resultStep( "Highest FTQ question ID is "
                            + send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids )
                            + " and has an answer of: "
                            + duplicatedFTQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids ) ) );
            log.endStep();

            log.resultStep( "Highest MCQ question ID is "
                            + send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids )
                            + " and has an answer of: "
                            + duplicatedMCQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePageMultipleAnswersEnabled()
                                                                                                                           throws Exception {

        Contact par = new Contact();

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

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same page when Multiple Answers are enabled" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            idFTQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                       MCcontactFiled.COLORGLOBAL.option_2 )
                                    .enterQuestion( MCcontactFiled.COLORGLOBAL.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.COLORGLOBAL )
                                    .deleteMcQuestionAnswers( 2 )
                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.MULTIPLEANSWERS )
                                    .updateQuestion();

            idMCQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.COLORGLOBAL.option_1 );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            idFTQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( "Color Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                       MCcontactFiled.COLORGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.COLORGLOBAL )
                                    .addOptionsToMultipleCQuestion( MCQuestionAnswerType.MULTIPLEANSWERS )
                                    .updateQuestion();

            idMCQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.COLORGLOBAL.option_2 );

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            for( int i = 0; i < submissionsCount; i++ ) {

                send.iaSurvey.viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.COLORGLOBAL.question,
                                                                                    new String[]{ MCcontactFiled.COLORGLOBAL.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    i + email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastNameFirst )
                                                                 .answerFtQuestion( "Last Name Duplicated",
                                                                                    lastNameSecond )
                                                                 .answerMcQuestion( "Color Duplicated",
                                                                                    new String[]{ MCcontactFiled.COLORGLOBAL.option_2 } );
                send.iaSurvey.surveyNext();

                answersArray.add( duplicatedMCQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
                answersArray.add( i + email );
                par.lastName = ( duplicatedFTQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids ) ) );

                driver.close();
                driver.selectWindow();
            }

            log.startStep( "Save all the parameters to external file" );

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.COLORGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

            log.resultStep( "Highest FTQ question ID is "
                            + send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids )
                            + " and has an answer of: "
                            + duplicatedFTQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids ) ) );
            log.endStep();

            log.resultStep( "Highest MCQ question ID is "
                            + send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids )
                            + " and has an answer of: "
                            + duplicatedMCQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
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

        Contact par = new Contact();

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

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            idFTQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            idMCQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.GENDER.option_1 );

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addNewQuestionType( "Free Text",
                                                                                      element.ftQId )
                                                                 .enterQuestion( "Last Name Duplicated" )
                                                                 .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                 .updateQuestion();

            idFTQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion();

            idMCQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.GENDER.option_2 );

            send.iaSurvey.addAndGoToPage( "Page 3" ).iaQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy();

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            for( int i = 0; i < submissionsCount; i++ ) {

                send.iaSurvey.viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                    new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    i + email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastNameFirst );

                send.iaSurvey.surveyNext().iaQuestion.answerFtQuestion( "Last Name Duplicated",
                                                                        lastNameSecond )
                                                     .answerMcQuestion( "Gender Duplicated",
                                                                        new String[]{ MCcontactFiled.GENDER.option_2 } );

                send.iaSurvey.surveyNext();

                answersArray.add( duplicatedMCQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
                answersArray.add( i + email );
                par.lastName = ( duplicatedFTQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids ) ) );

                driver.close();
                driver.selectWindow();
            }

            log.startStep( "Save all the parameters to external file" );

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

            log.resultStep( "Highest FTQ question ID is "
                            + send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids )
                            + " and has an answer of: "
                            + duplicatedFTQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedFTQids ) ) );
            log.endStep();

            log.resultStep( "Highest MCQ question ID is "
                            + send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids )
                            + " and has an answer of: "
                            + duplicatedMCQids.get( send.iaSurvey.iaQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredSamePage() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on the same page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion();

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy()
                         .viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                             .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                email )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( email );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredSamePage() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstName = randNumber + "firstName";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on the same page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "First Name Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion();

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy()
                         .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                email )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName )
                                                             .answerFtQuestion( "First Name Duplicated",
                                                                                firstName )
                                                             .answerMcQuestion( "Gender Duplicated",
                                                                                new String[]{ MCcontactFiled.GENDER.option_2 } );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );

            answersArray.add( email );
            par.lastName = lastName;
            answersArray.add( firstName );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_2 );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredDifferentPages() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on different pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addNewQuestionType( "Free Text",
                                                                                      element.ftQId )
                                                                 .enterQuestion( "Last Name Duplicated" )
                                                                 .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                    MCcontactFiled.GENDER.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).iaQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy()
                         .viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                             .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                email )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName );

            send.iaSurvey.surveyNext().surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( email );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredDifferentPages() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstName = randNumber + "firstName";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on different pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addNewQuestionType( "Free Text",
                                                                                      element.ftQId )
                                                                 .enterQuestion( "First Name Duplicated" )
                                                                 .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                    MCcontactFiled.GENDER.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).iaQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy()
                         .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                email )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName );

            send.iaSurvey.surveyNext().iaQuestion.answerFtQuestion( "First Name Duplicated", firstName )
                                                 .answerMcQuestion( "Gender Duplicated",
                                                                    new String[]{ MCcontactFiled.GENDER.option_2 } );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );

            answersArray.add( email );
            par.lastName = lastName;
            answersArray.add( firstName );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_2 );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsSamePage() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on the same page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "First Name Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion();

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy()
                         .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                email )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( email );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsDifferentPages() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on different pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addNewQuestionType( "Free Text",
                                                                                      element.ftQId )
                                                                 .enterQuestion( "First Name Duplicated" )
                                                                 .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                    MCcontactFiled.GENDER.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).iaQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy()
                         .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                email )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName );

            send.iaSurvey.surveyNext().surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( email );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCreateSurveyResponseWhenAnswerExceedsMaximumCharLengthLF() throws Exception {

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String surveyName = number + "ExceedMaxCharLengthLF";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String jobTitle = number + "jobTitle";
        String company = number + "company";
        String boundaryCharTestLF = "AbcdefghijAbcdefghijAbcd"; // Max char length is 20 characters

        log.startTest( "[Manual]Verify that survey response is not created when one of the answers (BoundryCharTest LF) exceeds the maximum char length of local field" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( true )
                                    .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                            MCcontactFiled.GENDERGLOBAL,
                                                            MCQuestionAnswerType.MANADATORY )
                                    .addFTQAndMapItToLastName( true )
                                    .addFTQAndMapItToFirstName( true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.JOBTITLE,
                                                                  true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.COMPANY,
                                                                  true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.BOUNDARYCHARTESTLF,
                                                                  true );

            send.iaSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                      new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                   .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                      emailAddress )
                                                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                      lastName )
                                                                                                   .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                      firstName )
                                                                                                   .answerFtQuestion( FTcontactField.JOBTITLE.question,
                                                                                                                      jobTitle )
                                                                                                   .answerFtQuestion( FTcontactField.COMPANY.question,
                                                                                                                      company )
                                                                                                   .answerFtQuestion( FTcontactField.BOUNDARYCHARTESTLF.question,
                                                                                                                      boundaryCharTestLF );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
            questionsArray.add( FTcontactField.JOBTITLE.conntactFiled );
            questionsArray.add( FTcontactField.COMPANY.conntactFiled );
            questionsArray.add( FTcontactField.BOUNDARYCHARTESTLF.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;
            answersArray.add( firstName );
            answersArray.add( jobTitle );
            answersArray.add( company );
            answersArray.add( boundaryCharTestLF );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCreateSurveyResponseWhenAnswerExceedsMaximumCharLengthGF() throws Exception {

        Contact par = new Contact();

        String number = driver.getTimestamp();
        String surveyName = number + "ExceedMaxCharLengthLF";
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String jobTitle = number + "jobTitle";
        String company = number + "company";
        String boundaryCharTestGF = "AbcdefghijAbcdefghijAbcd"; // Max char length is 20 characters

        log.startTest( "[Manual]Verify that survey response is not created when one of the answers (BoundryCharTest GF) exceeds the maximum char length of local field" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( true )
                                    .addOrUpdateMCQuestion( QuestionStatus.EDIT,
                                                            MCcontactFiled.GENDERGLOBAL,
                                                            MCQuestionAnswerType.MANADATORY )
                                    .addFTQAndMapItToLastName( true )
                                    .addFTQAndMapItToFirstName( true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.JOBTITLE,
                                                                  true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.COMPANY,
                                                                  true )
                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                  FTcontactField.BOUNDARYCHARTESTGF,
                                                                  true );

            send.iaSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                                      new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                                                   .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                      emailAddress )
                                                                                                   .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                      lastName )
                                                                                                   .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                      firstName )
                                                                                                   .answerFtQuestion( FTcontactField.JOBTITLE.question,
                                                                                                                      jobTitle )
                                                                                                   .answerFtQuestion( FTcontactField.COMPANY.question,
                                                                                                                      company )
                                                                                                   .answerFtQuestion( FTcontactField.BOUNDARYCHARTESTGF.question,
                                                                                                                      boundaryCharTestGF );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
            questionsArray.add( FTcontactField.JOBTITLE.conntactFiled );
            questionsArray.add( FTcontactField.COMPANY.conntactFiled );
            questionsArray.add( FTcontactField.BOUNDARYCHARTESTGF.conntactFiled );

            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( emailAddress );
            par.lastName = lastName;
            answersArray.add( firstName );
            answersArray.add( jobTitle );
            answersArray.add( company );
            answersArray.add( boundaryCharTestGF );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "surveys", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyCompleteSurveyWithNotMappedFTQs() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String emailAddress = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String notMappedFTQ1 = randNumber + "answer1";
        String notMappedFTQ2 = randNumber + "answer2";

        log.startTest( "Verify that both not mapped FTQ responses will be logged as answers to the CRM" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderWithSponsor ).iaQuestion.addFTQAndMapItToLastName( false )
                                                                                                                         .addFTQAndMapItToEmail( false )
                                                                                                                         .addNewQuestionType( "Free Text",
                                                                                                                                              element.ftQId )
                                                                                                                         .enterQuestion( "Not mapped FTQ1" )
                                                                                                                         .updateQuestion()
                                                                                                                         .addNewQuestionType( "Free Text",
                                                                                                                                              element.ftQId )
                                                                                                                         .enterQuestion( "Not mapped FTQ2" )
                                                                                                                         .updateQuestion();

            send.iaSurvey.saveAndContinueToTheme()
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy()
                         .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                emailAddress )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName )
                                                             .answerFtQuestion( "Not mapped FTQ1",
                                                                                notMappedFTQ1 )
                                                             .answerFtQuestion( "Not mapped FTQ2",
                                                                                notMappedFTQ2 );

            send.msdSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "surveys", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyCompleteSurveyWithNotMappedMCQOnlyMappedToCampaignResponse() throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String emailAddress = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that both not mapped MCQ campaign responses will be logged to the CRM" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderWithSponsor ).iaQuestion.editFTQAndMapItToEmail( false )
                                                                                                                         .addFTQAndMapItToLastName( false )
                                                                                                                         .addNewQuestionType( "Multiple Choice",
                                                                                                                                              element.mcQId )
                                                                                                                         .showQuestionSettings( "qTypemc" );
            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.fillinMCQAnswers( "Accept", "Decline" )
                                    .enterQuestion( "RSVP1" )
                                    .updateQuestion()
                                    .addNewQuestionType( "Multiple Choice", element.mcQId );

            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.fillinMCQAnswers( "Accept", "Decline" )
                                    .enterQuestion( "RSVP2" )
                                    .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                 emailAddress )
                                                              .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                 lastName )
                                                              .answerMcQuestion( "RSVP1",
                                                                                 new String[]{ "Accept" } )
                                                              .answerMcQuestion( "RSVP2",
                                                                                 new String[]{ "Decline" } );

            send.msdSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( emailAddress );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderNoSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "surveys", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePageAndTwoNotMappedMCQCampaignResponses()
                                                                                                                                        throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";
        String lastNameSecond = randNumber + "lastNameSecond";

        int idFTQ1;
        int idFTQ2;
        int idMCQ1;
        int idMCQ2;

        Map<Integer, String> duplicatedFTQids = new HashMap<Integer, String>();
        Map<Integer, String> duplicatedMCQids = new HashMap<Integer, String>();

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same page together with the not mapped MCQs (only to RSVP field)" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderWithSponsor );
            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            idFTQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ1, lastNameFirst );

            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .enterQuestion( MCcontactFiled.GENDER.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER.name,
                                                                  MCcontactFiled.GENDER.option_1,
                                                                  MCcontactFiled.GENDER.option_2 )
                                    .deleteMcQuestionAnswers( 2 )
                                    .showQuestionSettings( "qTypemc" );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.updateQuestion();

            idMCQ1 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ1, MCcontactFiled.GENDER.option_1 );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            idFTQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedFTQids.put( idFTQ2, lastNameSecond );

            send.iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER.name,
                                                                  MCcontactFiled.GENDER.option_1,
                                                                  MCcontactFiled.GENDER.option_2 );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.updateQuestion();

            idMCQ2 = send.iaSurvey.iaQuestion.questionID;
            duplicatedMCQids.put( idMCQ2, MCcontactFiled.GENDER.option_2 );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Multiple Choice", element.mcQId );

            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.fillinMCQAnswers( "Accept", "Decline" )
                                    .enterQuestion( "RSVP1" )
                                    .updateQuestion()
                                    .addNewQuestionType( "Multiple Choice", element.mcQId );

            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.fillinMCQAnswers( "Accept", "Decline" )
                                    .enterQuestion( "RSVP2" )
                                    .updateQuestion();

            send.iaSurvey.saveAndContinueToTheme()
                         .handleDuplicateMappingsDialog( true )
                         .selectTheme( Theme.TABLE.type )
                         .saveAndContinueToDeploy();

            send.iaSurvey.viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                             .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                email )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastNameFirst )
                                                             .answerFtQuestion( "Last Name Duplicated",
                                                                                lastNameSecond )
                                                             .answerMcQuestion( "Gender Duplicated",
                                                                                new String[]{ MCcontactFiled.GENDER.option_2 } )
                                                             .answerMcQuestion( "RSVP1",
                                                                                new String[]{ "Accept" } )
                                                             .answerMcQuestion( "RSVP2",
                                                                                new String[]{ "Decline" } );

            send.iaSurvey.surveyNext();

            driver.close();

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

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDER.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( email );
            answersArray.add( duplicatedMCQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedMCQids ) ) );
            par.lastName = duplicatedFTQids.get( send.msdSurvey.dynamicQuestion.getHighesQuestionId( duplicatedFTQids ) );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "surveys", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyCompleteSurveyWithOneMappedMCQAndMCQMappedOnlyToCampaignResponse()
                                                                                              throws Exception {

        Contact par = new Contact();

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that both MCQ campaign responses will be logged to the CRM, when one of the MCQ is not mapped to contact field" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderWithSponsor ).iaQuestion.editQuestionType( "A free text question" )
                                                                                                                         .enterQuestion( FTcontactField.EMAIL.question )
                                                                                                                         .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                         .updateQuestion()
                                                                                                                         .addNewQuestionType( "Free Text",
                                                                                                                                              element.ftQId )
                                                                                                                         .enterQuestion( FTcontactField.LASTNAME.question )
                                                                                                                         .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                         .updateQuestion()
                                                                                                                         .editMCQAndMapItToGenderG()
                                                                                                                         .editQuestionType( MCcontactFiled.GENDERGLOBAL.question )
                                                                                                                         .showQuestionSettings( "qTypemc" );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.updateQuestion();

            send.iaSurvey.iaQuestion.addNewQuestionType( "Multiple Choice", element.mcQId );

            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );

            log.startStep( "Check on the 'Is RSVP' check box" );
            driver.click( "//input[@id='qTypeIsRSVP']", driver.timeOut );
            log.endStep();

            log.startStep( "Check the Accept checkbox for the first answer" );
            driver.click( "//li[1][@class='s_clear answerItem']//label[@title='Accept']/input",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Check the Decline checkbox for the second answer" );
            driver.click( "//li[2][@class='s_clear answerItem']//label[@title='Decline']/input",
                          driver.timeOut );
            log.endStep();

            send.iaSurvey.iaQuestion.fillinMCQAnswers( "Accept", "Decline" )
                                    .enterQuestion( "RSVP1" )
                                    .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme()
                          .selectTheme( Theme.TABLE.type )
                          .saveAndContinueToDeploy()
                          .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                 email )
                                                              .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                 lastName )
                                                              .answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                 new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                              .answerMcQuestion( "RSVP1",
                                                                                 new String[]{ "Decline" } );

            send.msdSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( FTcontactField.EMAIL.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );

            answersArray.add( email );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            par.lastName = lastName;

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderWithSponsor;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveIt( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
