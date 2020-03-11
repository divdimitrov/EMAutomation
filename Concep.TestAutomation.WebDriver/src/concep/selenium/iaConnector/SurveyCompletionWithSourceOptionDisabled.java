package concep.selenium.iaConnector;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.iaConnector.IAattributes.ContactDetails;
import concep.selenium.send.IAConnection.ConnectionWizardButtonAction;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.Theme;

public class SurveyCompletionWithSourceOptionDisabled extends BaseIAconnector {
   
	private final boolean isActivitiesEnabled = false;
	private final boolean isSurveysEnabled = true;	
	private final boolean isSourced = false;	
	private final boolean isActivityLinkedToCompany = false;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void DeleteAndCreateConnectionToIA(
                                               @Optional("config/firefox.IAconnectorV1.1") String configLocation ) throws Exception {

        driver.init( configLocation );
        iaConnectionName = driver.config.getProperty( "iaConnectionName" );
        iaConnectionUsername = driver.config.getProperty( "iaConnectionUsername" );
        iaConnectionPassword = driver.config.getProperty( "iaConnectionPassword" );
        iaSendUser = driver.config.getProperty( "iaSendUser3" );
        iaSendPassword = driver.config.getProperty( "iaSendPassword" );
        driver.url = driver.config.getProperty( "url" );
        surveyEnableXPath = driver.config.getProperty( "surveyEnableXpath" );

        setConnectionConfiguration();

        driver.stopSelenium();
    }
    
    private void setConnectionConfiguration() throws Exception {

    	send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().iaConnection
		.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
		.addUsers(sendAccounts)
		.setPermissions(sendAccounts, isActivitiesEnabled, isSurveysEnabled)
		.clickNextToFolderTypesPage()
		.selectFolderTypes(folderTypes)
		.clickNextToFolderConfigurationPage()
		.selectFoldersConfiguration(seachPeopleFolder,	searchCompaniesFolder, linkFolders, sourceFolder, isSourced)
		.clickNextToActivtiesPage()
		.selectActivityMappings(surveyActivities, surveyActivityMappings, isActivityLinkedToCompany)
		.connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ALL);
    }

    private void successfullyUpatingExistingContacts(
                                                      String surveyType,
                                                      String eventFolder,
                                                      String sourceFolder,
                                                      boolean isExistingComapny ) throws Exception {

        // Contact par = new Contact();
        ContactDetails par = new ContactDetails();

        log.startTest( "successfully completed Survey in '" + eventFolder
                       + "' event folder but Company MUST be mapped in '" + sourceFolder
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

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType, eventFolder );

            send.iaSurvey.iaQuestion.editFTQAndMapItToLastName( false )
                                    .addFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToFirstName( false )
                                    .addFTQAndMapItToCompany( false );
            send.iaSurvey.saveAndSelectTheme( Theme.DISCO.type )
                         .viewAndDeployTheSurvey().iaQuestion.answerFtQuestion( FTcontactField.LASTNAME.question,
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

            par.additionalFields.questions.add( FTcontactField.EMAIL.conntactFiled );
            par.additionalFields.questions.add( FTcontactField.LASTNAME.conntactFiled );
            par.additionalFields.questions.add( FTcontactField.FIRSTNAME.conntactFiled );
            par.additionalFields.questions.add( FTcontactField.COMPANY.conntactFiled );

            par.additionalFields.answers.add( emailAddress );
            par.additionalFields.answers.add( firstName );
            par.additionalFields.answers.add( lastName );
            par.lastName.add( lastName );
            if( isExistingComapny ) {
                par.additionalFields.answers.add( existingCompany );
            } else {
                par.additionalFields.answers.add( company );
            }

            par.folderName = eventFolder;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToIA( stack[1].getMethodName(), par, isJson );
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
    @Test(groups = { "source-company-ia-folder-disabled" })
    public void successfullyCompletedSurveyNoSponsorFolderWhenSourcethemInThisFolderEnabled() throws Exception {

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
    @Test(groups = { "source-company-ia-folder-disabled" })
    public void successfullyCompletedSurveyAdministrativeFolderWhenSourceThemInThisFolderEnabled() throws Exception {

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
    @Test(groups = { "source-company-ia-folder-disabled" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourceThemInThisFolderEnabled() throws Exception {

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
    @Test(groups = { "source-company-ia-folder-disabled" })
    public void successfullyCompletedSurveyNoSponsorFolderWhenSourceThemInThisFolderEnabledWithExistingCompany() throws Exception {

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
    @Test(groups = { "source-company-ia-folder-disabled" })
    public void successfullyCompletedSurveyAdministrativeFolderWhenSourceThemInThisFolderEnabledWithExistingCompany() throws Exception {

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
    @Test(groups = { "source-company-ia-folder-disabled" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourceThemInThisFolderEnabledWithExistingCompany() throws Exception {

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
    @Test(groups = { "source-company-ia-folder-disabled" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourceThemInThisFolderEnabledAndTypeContactOnly() throws Exception {

        successfullyUpatingExistingContacts( surveyType3,
                                             eventFolderAdministrative,
                                             MarketingListSposorContactOnly,
                                             false );
    }
}
