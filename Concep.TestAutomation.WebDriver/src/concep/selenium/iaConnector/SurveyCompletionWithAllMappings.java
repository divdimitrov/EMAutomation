package concep.selenium.iaConnector;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.iaConnector.IAattributes.ContactDetails;
import concep.selenium.send.IAConnection.ConnectionWizardButtonAction;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;

public class SurveyCompletionWithAllMappings extends BaseIAconnector {

	private final boolean isActivitiesEnabled = false;
	private final boolean isSurveysEnabled = true;	
	private final boolean isSourced = true;	
	private final boolean isActivityLinkedToCompany = false;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void beforeClassSetUP(
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

    /**
    * 
    * Verify that survey can be completed and successfully mapped with multiple mappings
    * 
    * @throws Exception
    * 
    */

    private void successfullyCompletedSurveyWithAllAdditioanlFieldsMappedQuestions(
                                                                                    String testDescription,
                                                                                    HashMap<String, String> contactTextFields,
                                                                                    HashMap<String, String> contactNumberFields ) throws Exception {

        //Contact par = new Contact();
        ContactDetails par = new ContactDetails();

        log.startTest( testDescription );
        try {

            String number = driver.getTimestamp();
            String emailAddress = number + "email@concep.com";
            String lastName = number + "lastName";
            String surveyName = number + surveyType1 + eventFolderAdministrative;

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderAdministrative );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .addFTQAndMapItToLastName( false );

            par.additionalFields.questions.add( FTcontactField.EMAIL.conntactFiled );
            par.additionalFields.questions.add( MCcontactFiled.GENDERGLOBAL.name );
            par.additionalFields.questions.add( FTcontactField.LASTNAME.conntactFiled );

            par.additionalFields.answers.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            par.additionalFields.answers.add( emailAddress );
            par.lastName.add( lastName );

            for( Map.Entry<String, String> textfieldsquestions : contactTextFields.entrySet() ) {
                send.iaSurvey.iaQuestion.addOrUpdateFTQuestionBy( textfieldsquestions.getKey(),
                                                                  QuestionStatus.NEW )
                                        .mapFtQuestionToContactField( textfieldsquestions.getKey() )
                                        .updateQuestion();

                par.additionalFields.questions.add( textfieldsquestions.getKey() );
            }

            for( Map.Entry<String, String> numberfieldsquestions : contactNumberFields.entrySet() ) {
                send.iaSurvey.iaQuestion.addOrUpdateFTQuestionBy( numberfieldsquestions.getKey(),
                                                                  QuestionStatus.NEW )
                                        .mapFtQuestionToContactField( numberfieldsquestions.getKey() )
                                        .updateQuestion();

                par.additionalFields.questions.add( numberfieldsquestions.getKey() );
            }

            // Save and deploy survey
            send.iaSurvey.saveAndSelectTheme( Theme.AQUA.type )
                         .viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                             .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                emailAddress )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName );
            for( Map.Entry<String, String> textfields : contactTextFields.entrySet() ) {
                //   String contactFieldStrapper = contactTextFields[i].replaceAll( "\\\\", "" );
                send.iaSurvey.iaQuestion.answerFtQuestion( textfields.getKey(), textfields.getValue() );
                par.additionalFields.answers.add( textfields.getValue() );
                par.additionalFields.questions.add( textfields.getKey() );

            }

            for( Map.Entry<String, String> numberFields : contactNumberFields.entrySet() ) {

                send.iaSurvey.iaQuestion.answerFtQuestion( numberFields.getKey(), numberFields.getValue() );
                // par.additionalFields.phoneNumber.add( numberFields.getValue() );

            }

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            par.folderName = eventFolderAdministrative;

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
    *  
    * Verify that the survey can be completed and successfully mapped with different types of fields
    * 
    * @throws Exception
    * 
    */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDifferentTypesOfAdditionalField() throws Exception {

        HashMap<String, String> iaContactTextFileds = new HashMap<String, String>();
        iaContactTextFileds.put( "Middle Name", "Middle Name" );
        iaContactTextFileds.put( "Company", "Company" );
        iaContactTextFileds.put( "Salutation", "Salutation" );
        iaContactTextFileds.put( "Suffix", "Suffix" );
        iaContactTextFileds.put( "Job Title", "Job Title" );
        iaContactTextFileds.put( "Goes By", "Goes By" );
        iaContactTextFileds.put( "Business\\Address Description", "Address Description" );
        iaContactTextFileds.put( "Business\\Additional", "Additional" );
        iaContactTextFileds.put( "Business\\Street", "Street" );
        iaContactTextFileds.put( "Business\\City", "City" );
        iaContactTextFileds.put( "Business\\State", "State" );
        iaContactTextFileds.put( "Business\\Country", "Country" );
        iaContactTextFileds.put( "Business\\WebSite", "WebSite" );
        iaContactTextFileds.put( "Business\\Postal Code", "111111" );

        HashMap<String, String> iaContactPhoneFields = new HashMap<String, String>();
        iaContactPhoneFields.put( "Business\\Phone Number", "222222" );
        iaContactPhoneFields.put( "Business\\Fax Number", "333333" );

        successfullyCompletedSurveyWithAllAdditioanlFieldsMappedQuestions( "Verify that IA standards fields can be mapped suceessfully",
                                                                           iaContactTextFileds,
                                                                           iaContactPhoneFields );

    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithBMobileHMobileDepartmentAssistantFields() throws Exception {

        HashMap<String, String> iaContactTextFileds = new HashMap<String, String>();
        iaContactTextFileds.put( "Department", "Department" );
        iaContactTextFileds.put( "Assistant", "Assistant" );

        HashMap<String, String> iaContactPhoneFields = new HashMap<String, String>();
        iaContactTextFileds.put( "Home\\Mobile Number", "555555" );
        iaContactTextFileds.put( "Business\\Mobile Number", "444444" );

        successfullyCompletedSurveyWithAllAdditioanlFieldsMappedQuestions( "Verify that IA standards fields can be mapped suceessfully",
                                                                           iaContactTextFileds,
                                                                           iaContactPhoneFields );

    }
}
