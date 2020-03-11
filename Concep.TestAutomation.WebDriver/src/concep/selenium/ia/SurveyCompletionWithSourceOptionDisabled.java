package concep.selenium.ia;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.IA.Api.Contact;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.Theme;

public class SurveyCompletionWithSourceOptionDisabled extends BaseIA {
    private String  sourceFolder = "QA Source Folder";
    private boolean isSourced    = false;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void DeleteAndCreateConnectionToIA(
                                               @Optional("config/firefox.IA") String configLocation )
                                                                                                     throws Exception {

        driver.init( configLocation );
        iaConnectionName = driver.config.getProperty( "iaConnectionName" );
        iaConnectionUsername = driver.config.getProperty( "iaConnectionUsername" );
        iaConnectionPassword = driver.config.getProperty( "iaConnectionPassword" );
        iaSendUser = driver.config.getProperty( "iaSendUser3" );
        iaSendPassword = driver.config.getProperty( "iaSendPassword" );
        driver.url = driver.config.getProperty( "url" );
        surveyEnableXPath = driver.config.getProperty( "surveyEnableXpath" );
        clearAndCreatConnection( sourceFolder, isSourced );

        driver.stopSelenium();

    }

    public void CreatConnection(
                                 String sourceFolder ) throws Exception {

        // Log and Go to Connection Page
        loginToSend().goToConnectionsPage().iaConnection.loginToExistingConnection( iaConnectionUsername,
                                                                                    iaConnectionPassword )
                                                        .enableSurveyAndSelectIAFolders( surveyEnableXPath,
                                                                                         true,
                                                                                         true )
                                                        .selectAndSaveFolderConfiguration( sourceFolder,
                                                                                           isSourced )
                                                        .clickNextToActivtiesPage()
                                                        .selectSurveyConnectionActivities()
                                                        .saveAndCloseConnectionPage();

        log.startStep( "Verify that the connection is created successfully" );
        log.endStep( driver.isElementPresent( "//ul[@id='connntetionsList']/li/a[@href='#']", driver.timeOut ) );
    }

    /**
     * 
     * Verify that by Completing a Survey an Existing Company can Successfully be Updated
     * 
     * @throws Exception 
     * 
     */
    private void successfullyUpatingExistingContacts(
                                                      String surveyType,
                                                      String eventFolder,
                                                      String sourceFolder,
                                                      boolean isExistingComapny ) throws Exception {

        Contact par = new Contact();
        log.startTest( "successfully completed Survey in '"
                       + eventFolder
                       + "' event folder but Company MUST be mapped in '"
                       + sourceFolder
                       + "', source folder when 'Source them in this Folder' is enabled and existing company is: '"
                       + isExistingComapny + "'" );

        String number = driver.getTimestamp();
        String emailAddress = number + "email@test.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String company = number + "Company";
        String existingCompany = iaExistingCompany;
        String surveyName = number + sourceFolder + "NotIn" + eventFolder;

        try {

            CreatConnection( sourceFolder );
            send.goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                          .selectSurveyFolders( surveyType, eventFolder );

            send.iaSurvey.iaQuestion.editFTQAndMapItToLastName( false )
                                    .addFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToFirstName( false )
                                    .addFTQAndMapItToCompany( false );
            send.iaSurvey.saveAndSelectTheme( Theme.DISCO.type ).viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                       lastName );

            if( isExistingComapny ) {
                send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.COMPANY.question, existingCompany );
                log.endStep();
            } else {
                send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.COMPANY.question, company );
            }
            send.iaSurvey.iaQuestion.answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
                                    .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            par.questions.add( FTcontactField.EMAIL.conntactFiled );
            par.questions.add( FTcontactField.LASTNAME.conntactFiled );
            par.questions.add( FTcontactField.FIRSTNAME.conntactFiled );
            par.questions.add( FTcontactField.COMPANY.conntactFiled );

            par.answers.add( emailAddress );
            par.answers.add( firstName );
            par.answers.add( lastName );
            par.lastName = lastName;
            if( isExistingComapny ) {
                par.answers.add( existingCompany );
            } else {
                par.answers.add( company );
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
     * verify that survey can be successfully completed and logged when 'Source them in this Folder' feature
     * is enabled, in the source folder and not in the event folder. 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWhenSourcethemInThisFolderEnabled()
                                                                                             throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderAdministrative,
                                             eventFolderNoSponsor,
                                             false );
    }

    /**
     * verify that survey can be successfully completed and logged when 'Source them in this Folder' feature
     * is enabled, in the source folder and not in the event folder. 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyAdministrativeFolderWhenSourceThemInThisFolderEnabled()
                                                                                                  throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderNoSponsor,
                                             eventFolderAdministrative,
                                             false );
    }

    /**
     * verify that survey can be successfully completed and logged when 'Source them in this Folder' feature
     * is enabled, in the source folder and not in the event folder. 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourceThemInThisFolderEnabled()
                                                                                               throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderAdministrative,
                                             eventFolderWithSponsor,
                                             false );
    }

    /**
     * verify that survey can be successfully completed and logged when 'Source them in this Folder' feature
     * is enabled, in the source folder and not in the event folder With Existing Company. 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWhenSourceThemInThisFolderEnabledWithExistingCompany()
                                                                                                                throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderAdministrative,
                                             eventFolderNoSponsor,
                                             true );
    }

    /**
     * verify that survey can be successfully completed and logged when 'Source them in this Folder' feature
     * is enabled, in the source folder and not in the event folder With Existing Company. 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyAdministrativeFolderWhenSourceThemInThisFolderEnabledWithExistingCompany()
                                                                                                                     throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderNoSponsor,
                                             eventFolderAdministrative,
                                             true );
    }

    /**
     * verify that survey can be successfully completed and logged when 'Source them in this Folder' feature
     * is enabled, in the source folder and not in the event folder With Existing Company. 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourceThemInThisFolderEnabledWithExistingCompany()
                                                                                                                  throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderAdministrative,
                                             eventFolderWithSponsor,
                                             true );
    }

    /**
     * verify that survey can be successfully completed and logged when 'Source them in this Folder' feature
     * is enabled, in the source folder and not in the event folder With Existing Company. 
     * 
     * @throws Exception
     */
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourceThemInThisFolderEnabledAndTypeContactOnly()
                                                                                                                 throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderAdministrative,
                                             MarketingListSposorContactOnly,
                                             false );
    }

}
