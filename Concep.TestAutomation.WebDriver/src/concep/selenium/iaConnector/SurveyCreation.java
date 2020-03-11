/*

*/

package concep.selenium.iaConnector;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.IAConnection.ConnectionWizardButtonAction;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;
import concep.seleniumWD.core.BY;

public class SurveyCreation extends BaseIAconnector {

	private final boolean isActivitiesEnabled = false;
	private final boolean isSurveysEnabled = true;	
	private final boolean isSourced = true;	
	private final boolean isActivityLinkedToCompany = false;
	
    private final String  warningMessageForEmail    = "In order to log responses in your CRM system, E-Mail must be mapped. Please map a question to this contact field and try again.";
    private final String  warningMessageForLastName = "Sorry! We noticed you have not mapped the Last Name field.";
    private final String  defaultDropdownValue      = "Please select";
    private final String  emailContactFieldMapping  = "Primary Email";

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void beforeClassSetUP(
                                  @Optional("config/firefox.IAconnectorV1.1") String configLocation ) throws Exception {

        driver.init( configLocation );
        iaConnectionName = driver.config.getProperty( "iaConnectionName" );
        iaConnectionUsername = driver.config.getProperty( "iaConnectionUsername" );
        iaConnectionPassword = driver.config.getProperty( "iaConnectionPassword" );
        iaSendUser = driver.config.getProperty( "iaSendUser" );
        iaSendPassword = driver.config.getProperty( "iaSendPassword" );
        driver.url = driver.config.getProperty( "url" );
        surveyEnableXPath = driver.config.getProperty( "surveyEnableXpath" );
        
        //setConnectionConfiguration();
        
        cleanup();
    }
    
    private void setConnectionConfiguration() throws Exception {

    	send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().iaConnection
		.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
		//.addUsers(sendAccounts)
		//.setPermissions(sendAccounts, isActivitiesEnabled, isSurveysEnabled)
		.clickNextToFolderTypesPage()
		.selectFolderTypes(folderTypes)
		.clickNextToFolderConfigurationPage()
		.selectFoldersConfiguration(seachPeopleFolder,	searchCompaniesFolder, linkFolders, sourceFolder, isSourced)
		.clickNextToActivtiesPage()
		//.selectActivityMappings(surveyActivities, surveyActivityMappings, isActivityLinkedToCompany)
		.connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ALL);
    }

    private void unsuccessfullyCreatedSurveyWhenSaving(
                                                        String testDescription,
                                                        String surveyType,
                                                        String eventFolder,
                                                        FTcontactField contactField,
                                                        String warningMessage,
                                                        Boolean saveOnly ) throws Exception {

        log.startTest( testDescription );

        String number = driver.getTimestamp();
        String surveyName = number + "negative" + surveyType + eventFolder;

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType, eventFolder );

            send.iaSurvey.iaQuestion.addOrUpdateFreeTextQuestion( QuestionStatus.EDIT, contactField, false );
            //Press Save
            if( saveOnly == true ) {

                send.iaSurvey.saveSurvey();
            }

            //Press Save and Continue to Theme
            if( saveOnly == false ) {

                send.iaSurvey.saveAndContinueToTheme();
            }

            log.startStep( "Verify the warning message when '" + contactField.conntactFiled
                           + "' is mapped only." );
            log.endStep( driver.isTextPresent( "//div[@class='modalContent ui-helper-clearfix']/div",
                                               warningMessage,
                                               driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can not be created when email is not mapped and you
     * click 'Save and Continue to Theme' button
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "survey-creation", "smoke-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveAndContinueButton()
                                                                                              throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( "Verify that survey can not be created when primary email is not mapped and you click Save and Continue to Theme button",
                                               surveyType1,
                                               eventFolderAdministrative,
                                               FTcontactField.LASTNAME,
                                               warningMessageForEmail,
                                               false );
    }

    /**
     * 
     * Verify that survey can not be created when email is not mapped and you
     * click Save button
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "survey-creation", "smoke-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveButton() throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( "Verify that survey can not be created when primary email is not mapped and you click Save button",
                                               surveyType2,
                                               eventFolderWithSponsor,
                                               FTcontactField.LASTNAME,
                                               warningMessageForEmail,
                                               true );
    }

    /**
     * 
     * Verify that the survey can not be created when last name is not mapped and you click Save button
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "survey-creation", "smoke-tests", "key-tests" })
    public void ussuccessfullyCreatedSurveyWhenLastNameIsNotMappedAndClickSaveButton() throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( " Verify the warning message when last name is not mapped and you click Save button",
                                               surveyType3,
                                               eventFolderNoSponsor,
                                               FTcontactField.EMAIL,
                                               warningMessageForLastName,
                                               true );
    }

    /**
     * 
     * Verify that the survey can not be created when last name is not mapped and you click Save and Continue button
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "survey-creation", "smoke-tests", "key-tests" })
    public void ussuccessfullyCreatedSurveyWhenLastNameIsNotMappedAndClickSaveButtonAndContinue()
                                                                                                 throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( " Verify the warning message when last name is not mapped and you click Save and Continue button",
                                               surveyType4,
                                               eventFolderWithSponsor,
                                               FTcontactField.EMAIL,
                                               warningMessageForLastName,
                                               false );
    }

    /**
     * 
     * Verify that contact/lead fields keeps their values after clicking again in the survey's content 
     * 
     * PLAT-1617
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "survey-creation", "smoke-tests", "key-tests" })
    public void successfullyRetainingFieldMapping() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + surveyType3 + eventFolderNoSponsor;

        log.startTest( "Verify that contact/lead fields retains their mappings after clicking again in the survey's content" );

        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false ).addFTQAndMapItToLastName( false );
            send.iaSurvey.saveAndSelectTheme( Theme.DEFAULT.type );
            log.startStep( "Click the 'Content' button" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            Thread.sleep( 5000 );
            log.endStep();

            //Verify the 1st free text question 
            send.iaSurvey.iaQuestion.editQuestionType( FTcontactField.EMAIL.question );

            log.startStep( "Verify the Contact field drop down '" + FTcontactField.EMAIL.question
                           + "' is still retain 'Primary email' value" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Primary Email" ) );

            //Verify the 2nd free text question
            send.iaSurvey.iaQuestion.editQuestionType( FTcontactField.LASTNAME.question );

            log.startStep( "Verify the Contact field drop down '" + FTcontactField.LASTNAME.question
                           + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Last Name" ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey mappings can be successfully updated 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "survey-creation", "smoke-tests", "key-tests" })
    public void successfullyUpdateAndVerifySurveyMappings() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + surveyType4 + eventFolderWithSponsor;

        log.startTest( "Verify that survey mappings can be successfully updated" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType4, eventFolderWithSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToColorG();
            send.iaSurvey.saveAndSelectTheme( Theme.WATER.type );

            log.startStep( "Click the 'Content' button" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            Thread.sleep( 5000 );
            log.endStep();

            //Update Email Question
            send.iaSurvey.iaQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion();

            //Update Last Name Question
            send.iaSurvey.iaQuestion.editQuestionType( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion();

            //Update Multiple Choice Question
            send.iaSurvey.iaQuestion.editQuestionType( MCcontactFiled.COLORGLOBAL.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .updateQuestion();

            // Verify the Email Question
            send.iaSurvey.iaQuestion.editQuestionType( FTcontactField.EMAIL.question );

            log.startStep( "Verify that the Last Name drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Last Name" ) );

            send.iaSurvey.iaQuestion.cancelQuestion();

            // Verify the Last Name question
            send.iaSurvey.iaQuestion.editQuestionType( FTcontactField.LASTNAME.question );

            log.startStep( "Verify that the 'Primary Email' drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField'] span", "Primary Email" ) );

            send.iaSurvey.iaQuestion.cancelQuestion();

            // Verify the multiple choice question
            send.iaSurvey.iaQuestion.editQuestionType( MCcontactFiled.COLORGLOBAL.question );

            log.startStep( "Verify that the '" + MCcontactFiled.GENDERGLOBAL.name
                           + "' contact field option is selected" );
            log.endStep( driver.isValueSelected( "#qTypemcInteractionField span",
                                                 MCcontactFiled.GENDERGLOBAL.name ) );

            log.startStep( "Verify the 1st answer field" );
            log.endStep( driver.isTextPresent( "//input[@id='29374'] ",
                                               MCcontactFiled.GENDERGLOBAL.option_1,
                                               driver.timeOut ) );

            log.startStep( "Verify the 2nd answer field" );
            log.endStep( driver.isTextPresent( "//input[@id='54119']",
                                               MCcontactFiled.GENDERGLOBAL.option_2,
                                               driver.timeOut ) );

            log.startStep( "Verify the 1st mapping drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerDropDown-29374']",
                                            MCcontactFiled.GENDERGLOBAL.option_1 ) );

            log.startStep( "Verify the 2nd mapping drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerDropDown-54119']",
                                            MCcontactFiled.GENDERGLOBAL.option_2 ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey drop downs have default value Please select.
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "survey-creation", "open-defects" })
    public void successfullyVerifySurveyDefaultDropDowns() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + surveyType4 + eventFolderNoSponsor;

        log.startTest( "Verify that survey drop downs have default value Please select" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName );

            log.startStep( "Verify the eventFolder drop down default value" );
            Thread.sleep( 6000 );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep( driver.isSelected( "//select[@id='surveyInteractionFolder']", "Please select" ) );

            log.startStep( "Verify the Survey Type drop down default value" );
            log.endStep( driver.isSelected( "//select[@id='surveyTypeDropDown']", "Please select" ) );

            log.startStep( "Select the 'Survey Type' option " + surveyType4 );
            driver.select( "//select[@id='surveyTypeDropDown']" ).selectByVisibleText( surveyType4 );
            log.endStep();

            log.startStep( "Select the folder '" + eventFolderNoSponsor + "' from the 'Campaign' drop down" );
            driver.select( "//select[@id='surveyInteractionFolder']" )
                  .selectByVisibleText( eventFolderNoSponsor );
            log.endStep();

            // Verify the default free text question
            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" );

            log.startStep( "Verify the InterAction Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Please select" ) );

            send.iaSurvey.iaQuestion.cancelQuestion().editQuestionType( "A multiple choice question" );

            log.startStep( "Verify the InterAction Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypemcInteractionField span", "Please select" ) );

            log.startStep( "Select the 'Gender' interaction field " );
            driver.click( "//label[@id='qTypemcInteractionField']//span[contains(text(),'Please select')]",
                          driver.timeOut );
            driver.type( "//label[@id='qTypemcInteractionField']//input", "Gender", driver.timeOut );
            driver.pressKey( "//label[@id='qTypemcInteractionField']//input", Keys.ENTER, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify the Response Codes default values" );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]/select", "Please select" )
                         && driver.isSelected( "//ul[@id='qAnswerList']/li[2]/select", "Please select" )
                         && driver.isSelected( "//ul[@id='qAnswerList']/li[3]/select", "Please select" )
                         && driver.isSelected( "//ul[@id='qAnswerList']/li[4]/select", "Please select" ) );

            send.iaSurvey.iaQuestion.cancelQuestion().addNewQuestionType( "Free text", element.ftQId );

            log.startStep( "Verify the Contact Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Please select" ) );

            send.iaSurvey.iaQuestion.cancelQuestion().addNewQuestionType( "Multiple choice", element.mcQId );

            log.startStep( "Verify the InterAction Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypemcInteractionField span", "Please select" ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that questions retrain their mappings if you add new question without pressing Save.
     * 
     * PLAT-2316
     * 
     * @throws Exception
     * 
     * String description - in case if its only new put it empty, if its drag and drop question write drag and drop 
     * 
     * String qType has two values - dragAndDrop or new depending on that if the questions are
     * either drag and drop or new
     * 
     */

    private void retainMappingsAfterAddingNewFreeTextQuestion(
                                                               String description,
                                                               String qType ) throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "retaingMappingsAfterAdding" + qType + "Questions";

        log.startTest( "Verify that questions retrain their mappings if you add new free text " + description
                       + " question without pressing Save." );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToLastName( false ).addFTQAndMapItToEmail( false );
            send.iaSurvey.iaQuestion.editQuestionType( FTcontactField.LASTNAME.question );

            log.startStep( "Verify the InterAction field drop down '" + FTcontactField.LASTNAME.question
                           + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .enterQuestion( MCcontactFiled.COLORGLOBAL.question )
                                    .mapMcQuestionToContactField( MCcontactFiled.COLORGLOBAL )
                                    .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                       MCcontactFiled.COLORGLOBAL.option_2 )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            // Add new MCQ
            if( qType == "dragAndDrop" ) {
                send.iaSurvey.iaQuestion.addDragAndDropQuestionType( "Multiple Choice" )
                                        .enterQuestion( MCcontactFiled.GENDERGLOBAL.question )
                                        .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                        .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                           MCcontactFiled.GENDERGLOBAL.option_2 )
                                        .deleteMcQuestionAnswers( 1 )
                                        .updateQuestion();
            } else {

                send.iaSurvey.iaQuestion.addMCQAndMapItToColorG();

            }
            send.iaSurvey.iaQuestion.editQuestionType( MCcontactFiled.COLORGLOBAL.question );

            log.startStep( "Verify the Interaction Field retained it's value "
                           + MCcontactFiled.COLORGLOBAL.name );
            log.endStep( driver.isValueSelected( "#qTypemcInteractionField span",
                                                 MCcontactFiled.COLORGLOBAL.name ) );

            // Verify drop down values for Mappings
            log.startStep( "Verify that the 1st status option is " + MCcontactFiled.COLORGLOBAL.option_1 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerDropDown')]]",
                                            MCcontactFiled.COLORGLOBAL.option_1 ) );

            log.startStep( "Verify that the 2nd status option is " + MCcontactFiled.COLORGLOBAL.option_2 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerDropDown')]]",
                                            MCcontactFiled.COLORGLOBAL.option_2 ) );

            log.startStep( "Verify that the text field of the 1st answer is"
                           + MCcontactFiled.COLORGLOBAL.option_1 );
            log.endStep( driver.isTextPresent( "//input[@id='29374']",
                                               MCcontactFiled.COLORGLOBAL.option_1,
                                               driver.timeOut ) );

            log.startStep( "Verify that the text field of the 2nd answer is"
                           + MCcontactFiled.COLORGLOBAL.option_2 );
            log.endStep( driver.isTextPresent( "//input[@id='54119']",
                                               MCcontactFiled.COLORGLOBAL.option_2,
                                               driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void successfullyRetainMappingsAfterAddingDragAndDropQuestions() throws Exception {

        retainMappingsAfterAddingNewFreeTextQuestion( "Drag And Drop", "dragAndDrop" );

    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void successfullyRetainmappingsAfterAddingNewFTQuestions() throws Exception {

        retainMappingsAfterAddingNewFreeTextQuestion( "", "new" );

    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void successfullyRetainContactFieldsWithBackslash() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "successfullyRetaingBackslash";

        log.startTest( "Verify that Contact field mappings which contain Backslash are retained" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addOrUpdateFTQuestionBy( "Backslash test", QuestionStatus.EDIT )
                                    .mapFtQuestionToContactField( "Business\\City" )
                                    .updateQuestion();
            send.iaSurvey.iaQuestion.editQuestionType( "Backslash test" );

            log.startStep( "Verify the InterAction field drop down '" + "Backslash test"
                           + "' is still retain 'Business\\City' value" );
            log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Business\\City" ) );
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void successfullyDisplaySurveyLanguagePages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithLanguagePages";

        log.startTest( "Verify that the newly added survey language page tabs are successfully dispalyed" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToLastName( false )
                                    .addFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG();
            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( new String[]{ "English", "Bulgarian" } )
                                                     .saveSurvey();

            send.goToSurveyTab( "Questions" );

            // Verify Language pages tabs
            log.startStep( "Verify that the default language tab is visible from the surveys page" );
            log.endStep( driver.isElementPresent( "//ul[@id='langSelection']//a[text()='English']",
                                                  driver.timeOut ) );

            log.startStep( "Verify that the second language tab is visible from the surveys page" );
            log.endStep( driver.isElementPresent( "//ul[@id='langSelection']//a[text()='Bulgarian']",
                                                  driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    public void retainSurveyMappingQuestionsWithAdditionalLanguages(
                                                                     String[] languages ) throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithLanguagePages";
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addOrUpdateFTQuestionBy( FTcontactField.LASTNAME.question + " - "
                                                                      + languages[0],
                                                              QuestionStatus.EDIT )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateFTQuestionBy( FTcontactField.EMAIL.question + " - "
                                                                      + languages[0],
                                                              QuestionStatus.NEW )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question + " - "
                                                                      + languages[0],
                                                              QuestionStatus.EDIT )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .updateQuestion();

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
                                                                                                   + " - "
                                                                                                   + languages[i + 1] )
                                                                                   .updateQuestion();
            }
            for( int i = 0; i < languages.length; i++ ) {

                // Verify question content and the mappings of the questions from the default language page
                send.iaSurvey.goToSurveyLanguagePage( languages[i] ).iaQuestion.editQuestionType( FTcontactField.EMAIL.question
                                                                                                  + " - "
                                                                                                  + languages[i] );

                log.startStep( "Verify that the 'Primary Email' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Primary Email" ) );

                send.iaSurvey.iaQuestion.cancelQuestion().editQuestionType( FTcontactField.LASTNAME.question
                                                                            + " - " + languages[i] );

                log.startStep( "Verify that the Last Name drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextInteractionField span",
                                                     FTcontactField.LASTNAME.conntactFiled ) );

                send.iaSurvey.iaQuestion.cancelQuestion()
                                        .editQuestionType( MCcontactFiled.GENDERGLOBAL.question + " - "
                                                           + languages[i] );

                log.startStep( "Verify that the 'Gener' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypemcInteractionField span",
                                                     MCcontactFiled.GENDERGLOBAL.name ) );
                send.iaSurvey.iaQuestion.cancelQuestion();
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void successfullyRetainSurveyMappingsAndQuestionContentAdditionalLanguagePages() throws Exception {

        log.startTest( "Verify that the mappings are successfully retained on the additional language pages" );
        retainSurveyMappingQuestionsWithAdditionalLanguages( new String[]{ "English", "Bulgarian" } );
        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyRetainSurveyMappingsAndQuestionContentThreeAdditionalLanguagePages()
                                                                                                throws Exception {

        log.startTest( "Verify that the mappings are successfully retained on the three additional language pages" );
        retainSurveyMappingQuestionsWithAdditionalLanguages( new String[]{ "English", "Bulgarian", "Arabic" } );
        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "open-defects", "survey-creation" })
    public void successfullyRetainSurveyMappingsAndQuestionContentAfterDeletingAdditionalLanguagePages()
                                                                                                        throws Exception {

        log.startTest( "Verify that the mappings are successfully retained after deleting additional language pages" );
        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithLanguagePages";
        String[] languages = { "English", "Bulgarian", "Arabic" };
        String[] newLanguages = { "English", "Bulgarian" };
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addOrUpdateFTQuestionBy( FTcontactField.LASTNAME.question + " - "
                                                                      + languages[0],
                                                              QuestionStatus.EDIT )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateFTQuestionBy( FTcontactField.EMAIL.question + " - "
                                                                      + languages[0],
                                                              QuestionStatus.NEW )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question + " - "
                                                                      + languages[0],
                                                              QuestionStatus.EDIT )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .updateQuestion();
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
                                                                                                   + " - "
                                                                                                   + languages[i + 1] )
                                                                                   .updateQuestion();
            }
            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .deleteLanguage( new String[]{ "3" } );
            send.goToSurveyTab( "Questions" );
            for( int i = 0; i < newLanguages.length; i++ ) {

                // Verify question content and the mappings of the questions from the default language page
                send.iaSurvey.goToSurveyLanguagePage( newLanguages[i] ).iaQuestion.editQuestionType( FTcontactField.EMAIL.question
                                                                                                     + " - "
                                                                                                     + newLanguages[i] );

                log.startStep( "Verify that the 'Primary Email' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Primary Email" ) );

                send.iaSurvey.iaQuestion.cancelQuestion().editQuestionType( FTcontactField.LASTNAME.question
                                                                            + " - " + newLanguages[i] );

                log.startStep( "Verify that the Last Name drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextInteractionField span", "Last Name" ) );

                send.iaSurvey.iaQuestion.cancelQuestion()
                                        .editQuestionType( MCcontactFiled.GENDERGLOBAL.question + " - "
                                                           + newLanguages[i] );

                log.startStep( "Verify that the 'Gener' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypemcInteractionField span",
                                                     MCcontactFiled.GENDERGLOBAL.name ) );
                send.iaSurvey.iaQuestion.cancelQuestion();
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyIsSaved() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when survey is mapped" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG();
            send.iaSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void unsuccessfullyDisplayMappedSurveyIndicatorWhenSurveyIsNotSaved() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is not displayed when survey is mapped but not saved" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void successfullyDisplayMappedSurveyIndicatorWhenAlreadyExistingNotMappedSurveyIsMapped()
                                                                                                    throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when already existing not mapped survey is mapped" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          false ).iaSurvey.openSurvey( surveyName )
                                                          .selectSurveyFolders( surveyType3,
                                                                                eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void unsuccessfullyDisplayMappedSurveyIndicatorWhenAlreadyExistingMappedSurveyIsUnMapped()
                                                                                                     throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is not displayed when already existing mapped survey is un-mapped" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true ).iaSurvey.openSurvey( surveyName )
                                                         .selectSurveyFolders( defaultDropdownValue,
                                                                               defaultDropdownValue );

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSearchForMappedSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when user is searching for an existing mapped survey" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true ).iaSurvey.searchSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyRecordIsDeleted() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        int surveysCount = 2;

        log.startTest( "Verify that survey mapping indicator is successfully displayed when one of the surveys in the table is deleted" );
        try {

            loginToSend().goToSurveyPage();

            for( int i = 1; i <= surveysCount; i++ ) {

                send.iaSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                             .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

                send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                        .addFTQAndMapItToLastName( false )
                                        .editMCQAndMapItToGenderG();

                send.iaSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.iaSurvey.openSurvey( surveyName + 1 ).selectSurveyFolders( defaultDropdownValue,
                                                                            defaultDropdownValue );;
            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.deleteSurvey( surveyName + 1 );

            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + 2 ) },
                                          new String[]{ surveyName + 2 + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorsWhenSortingRecords() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        int surveysCount = 3;

        log.startTest( "Verify that survey mapping indicator is successfully displayed for the mapped surveys when user filter the records from the table" );
        try {

            loginToSend().goToSurveyPage();

            for( int i = 0; i < surveysCount; i++ ) {

                send.iaSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                             .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

                send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                        .addFTQAndMapItToLastName( false )
                                        .editMCQAndMapItToGenderG();

                send.iaSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.iaSurvey.sortSurveysBy( "Created" ).sortSurveysBy( "Created" ); //This is not a duplication. The method is called twice because we are interested only by the recently created surveys

            for( int i = 0; i < surveysCount; i++ ) {

                send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyRecordIsCopied() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        int surveysCount = 2;

        log.startTest( "Verify that survey mapping indicator is successfully displayed when one of the surveys in the table is copied" );
        try {

            loginToSend().goToSurveyPage();

            for( int i = 1; i <= surveysCount; i++ ) {

                send.iaSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                             .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

                send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                        .addFTQAndMapItToLastName( false )
                                        .editMCQAndMapItToGenderG();

                send.iaSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName + 1, surveyName + 1 + "Copy", false );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + 1 ),
                                                  element.getSurveyMappingIndicator( surveyName + 2 ) },
                                          new String[]{ surveyName + 1 + " Mapping Indicator",
                                                  surveyName + 2 + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhilePaging() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when user is paging through the records" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true ).iaSurvey.pagingRecords( "Next", 1 )
                                                         .pagingRecords( "Previous", 1 );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorTextWhenHoverInteractionFolderSelected()
                                                                                                throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator text 'Linked To: <Marketing List Name>' is successfully displayed when the indicator is hovered" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .addFTQAndMapItToLastName( false )
                                    .editMCQAndMapItToGenderG();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName )
                                                        + element.getMappingIndicatorText( eventFolderNoSponsor ) },
                                          new String[]{ "Linked To: " + eventFolderNoSponsor + " text" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyDisplayMappedFreeTextQuestionsIndicatorWithoutSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped free text"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is not saved" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( defaultFTQtxt )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                     emailContactFieldMapping ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addNewQuestionType( "Free Text",
                                                                                         element.ftQId )
                                                                    .enterQuestion( addedFTQbyButton )
                                                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                     FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                    .enterQuestion( addedFTQbyDragDrop )
                                                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               emailContactFieldMapping ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().iaSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               emailContactFieldMapping ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void successfullyDisplayMappedFreeTextQuestionsIndicatorWhenSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped free text"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is saved" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( defaultFTQtxt )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                     emailContactFieldMapping ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addNewQuestionType( "Free Text",
                                                                                         element.ftQId )
                                                                    .enterQuestion( addedFTQbyButton )
                                                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                     FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                    .enterQuestion( addedFTQbyDragDrop )
                                                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                    .updateQuestion();
            send.iaSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               emailContactFieldMapping ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().iaSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               emailContactFieldMapping ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void unsuccessfullyDisplayNotMappedFreeTextQuestionsIndicatorWithoutSavingSurvey()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator icon is not displayed for not mapped free text questions "
                       + "(default one, added by the button, added by drag-and-drop) without saving the survey" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( defaultFTQtxt )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                     "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( addedFTQbyButton )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                     "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                     .enterQuestion( addedFTQbyDragDrop )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().iaSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void unsuccessfullyDisplayNotMappedFreeTextQuestionsIndicatorWhenSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator icon is not displayed for not mapped free text questions "
                       + "(default one, added by the button, added by drag-and-drop) when saving the survey" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( defaultFTQtxt )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                     "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Email" )
                                                                     .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Last Name" )
                                                                     .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( addedFTQbyButton )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                     "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                     .enterQuestion( addedFTQbyDragDrop )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false );
            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                               "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                               "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyDisplayMappedMultipleChoiceQuestionsIndicatorWithoutSavingSurvey()
                                                                                              throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is not saved" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( defaultMCQtxt )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                           MCcontactFiled.GENDER.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                              QuestionStatus.NEW )
                                                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                           MCcontactFiled.GENDERGLOBAL.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                              QuestionStatus.DRAGANDDROP )
                                                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           MCcontactFiled.GENDERLOCAL.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     MCcontactFiled.GENDERGLOBAL.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().iaSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           MCcontactFiled.GENDERLOCAL.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     MCcontactFiled.GENDERGLOBAL.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyDisplayMappedMultipleChoiceQuestionsIndicatorWhenSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is saved" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( defaultMCQtxt )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                           MCcontactFiled.GENDER.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                              QuestionStatus.NEW )
                                                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                           MCcontactFiled.GENDERGLOBAL.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                              QuestionStatus.DRAGANDDROP )
                                                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                                                    .updateQuestion();
            send.iaSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           MCcontactFiled.GENDERLOCAL.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     MCcontactFiled.GENDERGLOBAL.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().iaSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           MCcontactFiled.GENDERLOCAL.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     MCcontactFiled.GENDERGLOBAL.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void unsuccessfullyDisplayNotMappedMultipleChoiceQuestionsIndicatorWithoutSavingSurvey()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator icon is not displayed for not mapped multiple choice questions "
                       + " quesitons (default one, added by the button, added by drag-and-drop) without saving survey" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( defaultMCQtxt )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                           "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                        MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                           "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                               QuestionStatus.DRAGANDDROP )
                                                                     .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                        MCcontactFiled.GENDERLOCAL.option_2 )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().iaSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void unsuccessfullyDisplayNotMappedMultipleChoiceQuestionsIndicatorWhenSavingSurvey()
                                                                                                throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator icon is not displayed for not mapped multiple choice questions "
                       + " quesitons (default one, added by the button, added by drag-and-drop) when saving survey" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( defaultMCQtxt )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                           "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                        MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                           "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                               QuestionStatus.DRAGANDDROP )
                                                                     .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                        MCcontactFiled.GENDERLOCAL.option_2 )
                                                                     .updateQuestion();
            send.iaSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().iaSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                     "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                     "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedFreeTextQuestionsIndicatorOtherLanguagePages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped free text"
                       + " quesitons (default one, added by the button, added by drag-and-drop) on the other Language pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( defaultFTQtxt )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                     emailContactFieldMapping ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addNewQuestionType( "Free Text",
                                                                                         element.ftQId )
                                                                    .enterQuestion( addedFTQbyButton )
                                                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                    .updateQuestion();

            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                     FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                    .enterQuestion( addedFTQbyDragDrop )
                                                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                    .updateQuestion();
            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages )
                                                     .saveSurvey();
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                         FTcontactField.FIRSTNAME.conntactFiled ),
                                                      element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                   emailContactFieldMapping ),
                                                      element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                   FTcontactField.LASTNAME.conntactFiled ) },
                                              new String[]{ "Added by drag-and-drop free text question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default free text question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button free text quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              true );
            }

            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );

            for( int i = 0; i < languages.length; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                         FTcontactField.FIRSTNAME.conntactFiled ),
                                                      element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                   emailContactFieldMapping ),
                                                      element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                   FTcontactField.LASTNAME.conntactFiled ) },
                                              new String[]{ "Added by drag-and-drop free text question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default free text question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button free text quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              true );
            };

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayMappedMultipleChoiceQuestionsIndicatorOtherLanguagePages()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) on the other Language pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( defaultMCQtxt )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                           MCcontactFiled.GENDER.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                              QuestionStatus.NEW )
                                                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                           MCcontactFiled.GENDERGLOBAL.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                              QuestionStatus.DRAGANDDROP )
                                                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                                                    .updateQuestion();

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages )
                                                     .saveSurvey();
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                               MCcontactFiled.GENDERLOCAL.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                         MCcontactFiled.GENDER.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                         MCcontactFiled.GENDERGLOBAL.name ) },
                                              new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default multiple choice question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button multiple choice quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              true );
            }

            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );

            for( int i = 0; i < languages.length; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                               MCcontactFiled.GENDERLOCAL.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                         MCcontactFiled.GENDER.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                         MCcontactFiled.GENDERGLOBAL.name ) },
                                              new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default multiple choice question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button multiple choice quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              true );
            };

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void unsuccessfullyDisplayNotMappedFreeTextQuestionsIndicatorOtherLanguagePages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that the mapping indicator is not displayed for not mapped free text"
                       + " quesitons (default one, added by the button, added by drag-and-drop) on the other Language pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( defaultFTQtxt )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                     "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( addedFTQbyButton )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                     "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                     .enterQuestion( addedFTQbyDragDrop )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                     "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator" },
                                          false );

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop,
                                                                                                         "" ),
                                                      element.getFreeTextQuestionMappingIndicator( defaultFTQtxt,
                                                                                                   "" ),
                                                      element.getFreeTextQuestionMappingIndicator( addedFTQbyButton,
                                                                                                   "" ) },
                                              new String[]{ "Added by drag-and-drop free text question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default free text question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button free text quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              false );
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void unsuccessfullyDisplayNotMappedMultipleChoiceQuestionsIndicatorOtherLanguagePages()
                                                                                                  throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that the mapping indicator is not displayed for not mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) on the other Language pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A multiple choice question" )
                                    .enterQuestion( defaultMCQtxt )
                                    .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                           "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                        MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                     .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                           "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                               QuestionStatus.DRAGANDDROP )
                                                                     .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                        MCcontactFiled.GENDERLOCAL.option_2 )
                                                                     .updateQuestion();

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                           "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator" },
                                          false )
                .goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop,
                                                                                                               "" ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt,
                                                                                                         "" ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton,
                                                                                                         "" ) },
                                              new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default multiple choice question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button multiple choice quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              false );
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void unsuccessfullyDisplayContentQuestionsIndicatorOtherLanguagePages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String addedCQbyButton = "Content Question added by button";
        String addedCQbyDragDrop = "Content Question added by drag-and-drop";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that the mapping indicator is not displayed for the content type of questions"
                       + " (added by the button, added by drag-and-drop) on the other Language pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addContentQuestion( false, addedCQbyButton );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by button content quesiton Mapping Indicator" },
                                          false ).iaSurvey.iaQuestion.addContentQuestion( true,
                                                                                          addedCQbyDragDrop );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyDragDrop ),
                                                  element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by drag-and-drop content question Mapping Indicator",
                                                  "Added by button content quesiton Mapping Indicator" },
                                          false );

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.iaSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyDragDrop ),
                                                      element.getContentQuesiton( addedCQbyButton ) },
                                              new String[]{ "Added by drag-and-drop content question Mapping Indicator",
                                                      "Added by button content quesiton Mapping Indicator" },
                                              false );
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyDisplayWarningMessageDuplicatedFreeTextQuestionMappingsSamePage()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated free text question mappings at the same page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion();
            send.iaSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.duplicateMappingsDialogMsg,
                                                  element.reviewDuplicatedMappingsDialogBtn,
                                                  element.saveDuplicatedMappingsDialogBtn },
                                          new String[]{ element.duplicateMappingsDialogTxt
                                                        + " Warning Message",
                                                  "Review button",
                                                  "Save button" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayWarningMessageDuplicatedFreeTextQuestionMappingsDifferentPages()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated free text question mappings at different pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addNewQuestionType( "Free Text",
                                                                                      element.ftQId )
                                                                 .enterQuestion( "Email Duplicated" )
                                                                 .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                 .updateQuestion();
            send.iaSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.duplicateMappingsDialogMsg,
                                                  element.reviewDuplicatedMappingsDialogBtn,
                                                  element.saveDuplicatedMappingsDialogBtn },
                                          new String[]{ element.duplicateMappingsDialogTxt
                                                        + " Warning Message",
                                                  "Review button",
                                                  "Save button" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyDisplayWarningMessageDuplicatedMultipleChoiceQuestionMappingsSamePage()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated multiple choice question mappings at the same page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion();
            send.iaSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.duplicateMappingsDialogMsg,
                                                  element.reviewDuplicatedMappingsDialogBtn,
                                                  element.saveDuplicatedMappingsDialogBtn },
                                          new String[]{ element.duplicateMappingsDialogTxt
                                                        + " Warning Message",
                                                  "Review button",
                                                  "Save button" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyDisplayWarningMessageDuplicatedMultipleChoiceQuestionMappingsDifferentPages()
                                                                                                         throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated multiple choice question mappings at different pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                    MCcontactFiled.GENDER.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                                                 .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.duplicateMappingsDialogMsg,
                                                  element.reviewDuplicatedMappingsDialogBtn,
                                                  element.saveDuplicatedMappingsDialogBtn },
                                          new String[]{ element.duplicateMappingsDialogTxt
                                                        + " Warning Message",
                                                  "Review button",
                                                  "Save button" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void successfullyCreateSurveyWithDuplicatedQuesitonMappingsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to create a survey with duplicated question mappings on the same page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Email Duplicated" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().handleDuplicateMappingsDialog( true );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );

            Thread.sleep( 3000 );
            // TODO: verification steps to check survey mappings are retained have to be added

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void successfullyCreateSurveyWithDuplicatedQuesitonMappingsDifferentPage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to create a survey with duplicated question mappings on the different pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( "Email" )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( "Last Name" )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addNewQuestionType( "Free Text",
                                                                                      element.ftQId )
                                                                 .enterQuestion( "Email Duplicated" )
                                                                 .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                    MCcontactFiled.GENDER.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );
            send.iaSurvey.saveSurvey().handleDuplicateMappingsDialog( true );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );

            Thread.sleep( 3000 );
            // TODO: verification steps to check survey mappings are retained have to be added

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void sucecssfullyCopyFromOutsideSurveyWithAllQuesitonMappings() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from outside with all question mappings" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", false )
                                          .openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void sucecssfullyCopyFromOutsideSurveyWithAllQuesitonMappingsManyPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from outside with all question mappings and many pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                 .enterQuestion( FTcontactField.FIRSTNAME.question )
                                                                 .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                    MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                                                           QuestionStatus.DRAGANDDROP )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                    MCcontactFiled.GENDERLOCAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", false )
                                          .openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void sucecssfullyCopyFromOutsideSurveyWithAllQuesitonMappingsManyLanguages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that user is able to copy a survey from outside with all question mappings and many language pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages )
                                                     .saveSurvey();
            send.goToSurveyTab( "Questions" );

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", false )
                                          .openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void sucecssfullyCopyFromInsideSurveyWithAllQuesitonMappings() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from inside with all question mappings" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", false );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyFromInsideSurveyWithAllQuesitonMappingsManyPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from inside with all question mappings and many pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                 .enterQuestion( FTcontactField.FIRSTNAME.question )
                                                                 .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                    MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                                                           QuestionStatus.DRAGANDDROP )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                    MCcontactFiled.GENDERLOCAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", false );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void sucecssfullyCopyFromInsideSurveyWithAllQuesitonMappingsManyLanguages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that user is able to copy a survey from inside with all question mappings and many language pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages )
                                                     .saveSurvey();
            send.goToSurveyTab( "Questions" );

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", false );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromOutsideSurveyWithAllQuesitonMappings() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from outside with all question mappings" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromOutsideSurveyWithAllQuesitonMappingsManyPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from outside with all question mappings and many pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                 .enterQuestion( FTcontactField.FIRSTNAME.question )
                                                                 .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                    MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                                                           QuestionStatus.DRAGANDDROP )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                    MCcontactFiled.GENDERLOCAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation", "key-tests" })
    public void sucecssfullyCopyAndEditFromOutsideSurveyWithAllQuesitonMappingsManyLanguages()
                                                                                              throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that user is able to copy and edit a survey from outside with all question mappings and many language pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages )
                                                     .saveSurvey();
            send.goToSurveyTab( "Questions" );

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", false );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromInsideSurveyWithAllQuesitonMappings() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from inside with all question mappings" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromInsideSurveyWithAllQuesitonMappingsManyPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from inside with all question mappings and many pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion();

            send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                 .enterQuestion( FTcontactField.FIRSTNAME.question )
                                                                 .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                                                           QuestionStatus.NEW )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                    MCcontactFiled.GENDERGLOBAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                                 .updateQuestion()
                                                                 .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                                                           QuestionStatus.DRAGANDDROP )
                                                                 .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                                                    MCcontactFiled.GENDERLOCAL.option_2 )
                                                                 .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                                                 .updateQuestion();

            send.iaSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void sucecssfullyCopyAndEditFromInsideSurveyWithAllQuesitonMappingsManyLanguages()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that user is able to copy and edit a survey from inside with all question mappings and many language pages" );
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
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.goToSurveyTab( "Settings" ).iaSurvey.goToSurveySettingsTab( "Languages" )
                                                     .addLanguage( languages )
                                                     .saveSurvey();
            send.goToSurveyTab( "Questions" );

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyFromOutsideSurveyWithNotMappedDefaultFTQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from outside with all question mappings except the default free text question" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", false )
                                          .openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyFromOutsideSurveyWithNotMappedDefaultMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from outside with all question mappings except the default multiple choice question" );
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
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", false )
                                          .openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void sucecssfullyCopyFromOutsideSurveyWithNotMappedDefaultFTQandMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from outside with all question mappings except the default free text and multiple choice questions" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", false )
                                          .openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyFromInsideSurveyWithNotMappedDefaultFTQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from inside with all question mappings except the default free text question" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", false );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyFromInsideSurveyWithNotMappedDefaultMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from inside with all question mappings except the default multiple choice question" );
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
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", false );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void sucecssfullyCopyFromInsideSurveyWithNotMappedDefaultFTQandMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from inside with all question mappings except the default free text and multiple choice questions" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", false );
            send.goToSurveyPage().iaSurvey.openSurvey( surveyName + "Copy" );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromOutsideSurveyWithNotMappedDefaultFTQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from outside with all question mappings except the default free text question" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromOutsideSurveyWithNotMappedDefaultMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy a survey from outside with all question mappings except the default multiple choice question" );
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
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "smoke-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromOutsideSurveyWithNotMappedDefaultFTQandMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from outside with all question mappings except the default free text and multiple choice questions" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey();
            send.goToSurveyPage().iaSurvey.copySurvey( true, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromInsideSurveyWithNotMappedDefaultFTQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from inside with all question mappings except the default free text question" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .editQuestionType( "A multiple choice question" )
                                    .enterQuestion( "Gender" )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .deleteMcQuestionAnswers( 2 )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromInsideSurveyWithNotMappedDefaultMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from inside with all question mappings except the default multiple choice question" );
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
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation" })
    public void sucecssfullyCopyAndEditFromInsideSurveyWithNotMappedDefaultFTQandMCQ() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to copy and edit a survey from inside with all question mappings except the default free text and multiple choice questions" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                       MCcontactFiled.GENDER.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                              QuestionStatus.NEW )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                       MCcontactFiled.GENDERGLOBAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                    .updateQuestion()
                                    .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERLOCAL.question,
                                                              QuestionStatus.DRAGANDDROP )
                                    .fillinMCQAnswers( MCcontactFiled.GENDERLOCAL.option_1,
                                                       MCcontactFiled.GENDERLOCAL.option_2 )
                                    .mapMcQuestionToContactField( MCcontactFiled.GENDERLOCAL )
                                    .updateQuestion();

            send.iaSurvey.saveSurvey().copySurvey( false, surveyName, surveyName + "Copy", true );

            // TODO: Check for retained mappings is implemented but currently we have a problem with opening a question that needs to be fixed

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests", "survey-creation", "key-tests" })
    public void successfullyKeppAllSurveyMappingsAfterMaintenanceJobExecution() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MaintenanceJobTestSurvey";

        log.startTest( "Verify that newly created survey keeps its mappings after the execution of the Maintenance Job" );
        try {

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

            send.iaSurvey.saveSurvey();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    @Test(groups = { "testDeni" })
    public void verifyUserCanDragAdditionalQuestionToTheSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that you can drag additional question to the survey" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion();
                                    

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    @Test(groups = { "testDeni" })
    public void VerifyUserCanSuccessfullyDeleteQuestion() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that you can delete questions from the survey" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                    .enterQuestion( FTcontactField.EMAIL.question )
                                    .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                    .updateQuestion()
                                    .addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.LASTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                    .updateQuestion()
                                    .addDragAndDropQuestionType( "Free Text" )
                                    .enterQuestion( FTcontactField.FIRSTNAME.question )
                                    .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                    .updateQuestion()
                                    .deleteMcQuestionType("A multiple choice question");
                                    
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "testDeni" })
    public void VerifyCustomerCannotDeleteLastQuestion() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify a minimum of 1 question is required" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderNoSponsor );

            send.iaSurvey.iaQuestion.deleteFtQuestionType( "A free text question" )
                                    .deleteMcQuestionType("A multiple choice question");
            
            send.verifyDisplayedElements(new String[] {element.deleteQuestionErrorLabel},
            							 new String[] {element.deleteQuestionErrorTxt},
            							 true);	
                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    @Test(groups = { "testDeni" })
    public void VerifySuccessfullyNavigateToOtherPage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that you can navigate to other pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderNoSponsor );

            send.iaSurvey.goToPageInSurvey("Page 2");
                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "testDeni" })
    public void VerifySuccessfullyCreateAnotherPage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify you can create additional pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderNoSponsor );

            send.iaSurvey.addAndGoToPage(randNumber);
                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "testDeni" })
    public void VerifyCustomerCanDeletePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify you can create additional pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderNoSponsor );
            
            send.iaSurvey.addAndGoToPage("Page 3");
            send.iaSurvey.goToPageInSurvey("Page 3");
            send.iaSurvey.deletePageInSurvey();
            
                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "testDeni" })
    public void VerifyCustomerAddQuestionViaAddQuestionButton() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify you can add question via the \"Add question\" button" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                   .selectSurveyFolders( surveyType1, eventFolderNoSponsor );
            
            send.iaSurvey.iaQuestion.addNewQuestionType("Content", element.contentQId).updateQuestion();
            send.iaSurvey.iaQuestion.addNewQuestionType("Free Text", element.ftQId).updateQuestion();
            send.iaSurvey.iaQuestion.addOrUpdateMCQuestionBy( "Gender",QuestionStatus.NEW )
            .fillinMCQAnswers( MCcontactFiled.GENDER.option_1, MCcontactFiled.GENDER.option_2 )
            .mapMcQuestionToContactField( MCcontactFiled.GENDER )
            .updateQuestion();
            //TO DO
            //Add other types of questions
            
            
                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "testDeni" })
    public void VerifyCustomerCanSaveSurveyWithoutMapping() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify you can save survey if not mapped" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName );
            send.iaSurvey.saveSurvey().saveAndContinueToTheme();
                                                   
                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    @Test(groups = { "testDeni" })
    public void VerifySurveyCanBeMapped() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify you can map survey to IA folder" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( surveyName )
            .selectSurveyFolders( surveyType1, eventFolderNoSponsor );
                                                  
                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "testDeni" })
    public void userCanDeleteSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = 1543411259 + "surveyName";
        
        
        log.startTest( "Verify that survey can be deleted" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.deleteSurvey(surveyName);

            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();

}
}
