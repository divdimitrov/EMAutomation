package concep.selenium.iaConnector;

import java.util.ArrayList;

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

public class SurveyTemplate extends BaseIAconnector {

	private final boolean isActivitiesEnabled = false;
	private final boolean isSurveysEnabled = true;	
	private final boolean isSourced = true;	
	private final boolean isActivityLinkedToCompany = false;
	
    private String  surveyTemplateOnePage;
    private String  surveyTemplateThreePages;
    private String  surveyTemplateThreeLanguagePages;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void beforeClassSetUP(
                                  @Optional("config/firefox.IAconnectorV1.1") String configLocation ) throws Exception {

        driver.init( configLocation );
        iaConnectionName = driver.config.getProperty( "iaConnectionName" );
        iaConnectionUsername = driver.config.getProperty( "iaConnectionUsername" );
        iaConnectionPassword = driver.config.getProperty( "iaConnectionPassword" );
        iaSendUser = driver.config.getProperty( "iaSendUserTemplates" );
        iaSendPassword = driver.config.getProperty( "iaSendPassword" );
        surveyTemplateOnePage = driver.config.getProperty( "surveyTemplateOnePage" );
        surveyTemplateThreePages = driver.config.getProperty( "surveyTemplateThreePages" );
        surveyTemplateThreeLanguagePages = driver.config.getProperty( "surveyTemplateThreeLanguagePages" );
        driver.url = driver.config.getProperty( "url" );
        surveyEnableXPath = driver.config.getProperty( "surveyEnableXpath" );

        setConnectionConfiguration();
        
        // TODO: The order of calling the methods have to be reworked because of the missing Create Survey button when there is already existing template
        /*send.goToSurveyPage().iaSurvey.startSurveyCreation();
        if (verifyTemplatesExistance(new String[] {surveyTemplateOnePage, surveyTemplateThreePages})) {
        	
        	createAllRequiredTemplates();
        }*/

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

    private boolean verifyTemplatesExistance(
                                              String[] templateNames ) throws Exception {

        boolean areAllTemplatesExisting;
        int countNotExistingTemplates = 0;

        for( int i = 0; i < templateNames.length; i++ ) {

            if( !driver.isElementPresent( element.getSurveyTemplate( templateNames[i] ), driver.timeOut ) ) {

                countNotExistingTemplates++;
            }
        }

        if( countNotExistingTemplates == templateNames.length ) {

            areAllTemplatesExisting = false;

        } else {

            areAllTemplatesExisting = true;
        }

        return areAllTemplatesExisting;
    }

    private SurveyTemplate createAllRequiredTemplates() throws Exception {

        createOnePageTemplate();
        createThreePagesTemplate();

        return this;
    }

    private SurveyTemplate createOnePageTemplate() throws Exception {

        send.goToSurveyPage().iaSurvey.typeSurveyNameAndClickCreate( surveyTemplateOnePage )
                                      .selectSurveyFolders( surveyType3, eventFolderNoSponsor );

        send.iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                .enterQuestion( FTcontactField.PRIMARYEMAIL.question )
                                .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                .updateQuestion()
                                .addNewQuestionType( "Free Text", element.ftQId )
                                .enterQuestion( FTcontactField.FIRSTNAME.question )
                                .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                .updateQuestion()
                                .addNewQuestionType( "Free Text", element.ftQId )
                                .enterQuestion( FTcontactField.LASTNAME.question )
                                .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                .updateQuestion()
                                .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                          QuestionStatus.NEW )
                                .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                   MCcontactFiled.GENDERGLOBAL.option_2 )
                                .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                .updateQuestion()
                                .addOrUpdateMCQuestionBy( MCcontactFiled.COLORLOCAL.question,
                                                          QuestionStatus.NEW )
                                .fillinMCQAnswers( MCcontactFiled.COLORLOCAL.option_1,
                                                   MCcontactFiled.COLORLOCAL.option_2 )
                                .mapMcQuestionToContactField( MCcontactFiled.COLORLOCAL )
                                .updateQuestion();

        send.iaSurvey.saveSurveyAsTemplate( surveyTemplateOnePage, surveyTemplateOnePage );

        return this;
    }

    private SurveyTemplate createThreePagesTemplate() throws Exception {

        send.goToSurveyPage().iaSurvey.iaQuestion.editQuestionType( "A free text question" )
                                                 .enterQuestion( FTcontactField.PRIMARYEMAIL.question )
                                                 .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                 .updateQuestion()
                                                 .addNewQuestionType( "Free Text", element.ftQId )
                                                 .enterQuestion( FTcontactField.FIRSTNAME.question )
                                                 .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled );
        send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.updateQuestion()
                                                             .addNewQuestionType( "Free Text", element.ftQId )
                                                             .enterQuestion( FTcontactField.LASTNAME.question )
                                                             .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                             .updateQuestion()
                                                             .addOrUpdateMCQuestionBy( MCcontactFiled.GENDERGLOBAL.question,
                                                                                       QuestionStatus.NEW )
                                                             .fillinMCQAnswers( MCcontactFiled.GENDERGLOBAL.option_1,
                                                                                MCcontactFiled.GENDERGLOBAL.option_2 )
                                                             .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                             .updateQuestion();
        send.iaSurvey.addAndGoToPage( "Page 3" ).iaQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.COLORLOCAL.question,
                                                                                     QuestionStatus.NEW )
                                                           .fillinMCQAnswers( MCcontactFiled.COLORLOCAL.option_1,
                                                                              MCcontactFiled.COLORLOCAL.option_2 )
                                                           .mapMcQuestionToContactField( MCcontactFiled.COLORLOCAL )
                                                           .updateQuestion();
        send.iaSurvey.addAndGoToPage( "Page 4" ).iaQuestion.addContentQuestion( false, "Thank you" );

        send.iaSurvey.saveSurveyAsTemplate( surveyTemplateOnePage, surveyTemplateOnePage );

        return this;
    }

    private SurveyTemplate verifySurveyTemplateOnePageContent() throws Exception {

        send.verifyDisplayedElements( new String[]{ element.iaEventFolderDropDown_2,
                                                    element.crmSurveyTypeDropDown,
                                                    element.getSurveyMCQcontentText( MCcontactFiled.GENDER.question ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDER.question,
                                                                                      MCcontactFiled.GENDER.option_1 ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDER.question,
                                                                                      MCcontactFiled.GENDER.option_2 ),
                                                    element.getSurveyFTQcontentText( FTcontactField.PRIMARYEMAIL.question ),
                                                    element.getSurveyFTQcontentText( FTcontactField.FIRSTNAME.question ),
                                                    element.getSurveyFTQcontentText( FTcontactField.LASTNAME.question ),
                                                    element.getSurveyMCQcontentText( MCcontactFiled.GENDERGLOBAL.question ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDERGLOBAL.question,
                                                                                      MCcontactFiled.GENDERGLOBAL.option_1 ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDERGLOBAL.question,
                                                                                      MCcontactFiled.GENDERGLOBAL.option_2 ),
                                                    element.getSurveyMCQcontentText( MCcontactFiled.COLORLOCAL.question ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.COLORLOCAL.question,
                                                                                      MCcontactFiled.COLORLOCAL.option_1 ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.COLORLOCAL.question,
                                                                                      MCcontactFiled.COLORLOCAL.option_2 ) },
                                      new String[]{ "Interaction Folder dropdown",
                                                    "Survey Type dropdown",
                                                    "Gender mapping",
                                                    "Male answer option",
                                                    "Female answer option",
                                                    "Email FTQ",
                                                    "First Name FTQ",
                                                    "Last Name FTQ",
                                                    "Gender Global MCQ",
                                                    "Male answer option",
                                                    "Female answer option",
                                                    "Color Local MCQ",
                                                    "Orange answer option",
                                                    "Yellow answer option", },
                                      true );

        return this;
    }

    private SurveyTemplate mapSurveyTemplateOnePage() throws Exception {

        send.iaSurvey.iaQuestion.editQuestionType( MCcontactFiled.GENDER.question )
                                .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                .updateQuestion()
                                .editQuestionType( FTcontactField.EMAIL.question )
                                .mapFtQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                .updateQuestion()
                                .editQuestionType( FTcontactField.FIRSTNAME.question )
                                .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                .updateQuestion()
                                .editQuestionType( FTcontactField.LASTNAME.question )
                                .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                .updateQuestion()
                                .editQuestionType( MCcontactFiled.GENDERGLOBAL.question )
                                .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                .updateQuestion()
                                .editQuestionType( MCcontactFiled.COLORLOCAL.question )
                                .mapMcQuestionToContactField( MCcontactFiled.COLORLOCAL )
                                .updateQuestion();

        return this;
    }

    private SurveyTemplate verifySurveyTemplateMappingsOnePage() throws Exception {

        log.startStep( "Wait for IA folder to be displayed" );
        driver.waitUntilElementIsLocated( element.iaEventFolderDropDown_2, driver.timeOut );
        driver.waitUntilElementIsClickable( element.iaEventFolderDropDown_2 );
        log.endStep();

        send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( FTcontactField.PRIMARYEMAIL.question,
                                                                                                 FTcontactField.PRIMARYEMAIL.conntactFiled ),
                                                    element.getFreeTextQuestionMappingIndicator( FTcontactField.FIRSTNAME.question,
                                                                                                 FTcontactField.FIRSTNAME.conntactFiled ),
                                                    element.getFreeTextQuestionMappingIndicator( FTcontactField.LASTNAME.question,
                                                                                                 FTcontactField.LASTNAME.conntactFiled ),
                                                    element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.GENDER.question,
                                                                                                       MCcontactFiled.GENDER.name ),
                                                    element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                       MCcontactFiled.GENDERGLOBAL.name ),
                                                    element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.COLORLOCAL.question,
                                                                                                       MCcontactFiled.COLORLOCAL.name ) },
                                      new String[]{ "Primary Email mapping",
                                                    "First Name mapping",
                                                    "Last Name mapping",
                                                    "Gender mapping",
                                                    "Gender Global mapping",
                                                    "Color Local mapping" },
                                      true );
        return this;
    }

    private SurveyTemplate verifySurveyTemplateThreePagesContent() throws Exception {

        send.verifyDisplayedElements( new String[]{ element.iaEventFolderDropDown_2,
                                                    element.crmSurveyTypeDropDown,
                                                    element.getSurveyMCQcontentText( MCcontactFiled.GENDER.question ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDER.question,
                                                                                      MCcontactFiled.GENDER.option_1 ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDER.question,
                                                                                      MCcontactFiled.GENDER.option_2 ),
                                                    element.getSurveyFTQcontentText( FTcontactField.PRIMARYEMAIL.question ),
                                                    element.getSurveyFTQcontentText( FTcontactField.FIRSTNAME.question ) },
                                      new String[]{ "Interaction Folder dropdown",
                                                    "Survey Type dropdown",
                                                    "Gender MCQ",
                                                    "Male answer option",
                                                    "Female answer option",
                                                    "Email FTQ",
                                                    "First Name FTQ" },
                                      true ).iaSurvey.goToPageInSurvey( "Page 2" );
        send.verifyDisplayedElements( new String[]{ element.getSurveyMCQcontentText( MCcontactFiled.GENDERGLOBAL.question ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDERGLOBAL.question,
                                                                                      MCcontactFiled.GENDERGLOBAL.option_1 ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.GENDERGLOBAL.question,
                                                                                      MCcontactFiled.GENDERGLOBAL.option_2 ),
                                                    element.getSurveyFTQcontentText( FTcontactField.LASTNAME.question ) },
                                      new String[]{ "Gender Global MCQ",
                                                    "Male answer option",
                                                    "Female answer option",
                                                    "Last Name FTQ" },
                                      true ).iaSurvey.goToPageInSurvey( "Page 3" );
        send.verifyDisplayedElements( new String[]{ element.getSurveyMCQcontentText( MCcontactFiled.COLORLOCAL.question ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.COLORLOCAL.question,
                                                                                      MCcontactFiled.COLORLOCAL.option_1 ),
                                                    element.getSurveyMCQAnswerOption( MCcontactFiled.COLORLOCAL.question,
                                                                                      MCcontactFiled.COLORLOCAL.option_2 ) },
                                      new String[]{ "Color Local MCQ",
                                                    "Orange answer option",
                                                    "Yellow answer option" },
                                      true );

        return this;
    }

    private SurveyTemplate mapSurveyTemplateThreePages() throws Exception {

        send.iaSurvey.goToPageInSurvey( "Page 1" ).iaQuestion.editQuestionType( MCcontactFiled.GENDER.question )
                                                             .mapMcQuestionToContactField( MCcontactFiled.GENDER )
                                                             .updateQuestion()
                                                             .editQuestionType( FTcontactField.PRIMARYEMAIL.question )
                                                             .mapFtQuestionToContactField( FTcontactField.PRIMARYEMAIL.conntactFiled )
                                                             .updateQuestion()
                                                             .editQuestionType( FTcontactField.FIRSTNAME.question )
                                                             .mapFtQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                             .updateQuestion();
        send.iaSurvey.goToPageInSurvey( "Page 2" ).iaQuestion.editQuestionType( FTcontactField.LASTNAME.question )
                                                             .mapFtQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                             .updateQuestion()
                                                             .editQuestionType( MCcontactFiled.GENDERGLOBAL.question )
                                                             .mapMcQuestionToContactField( MCcontactFiled.GENDERGLOBAL )
                                                             .updateQuestion();
        send.iaSurvey.goToPageInSurvey( "Page 3" ).iaQuestion.editQuestionType( MCcontactFiled.COLORLOCAL.question )
                                                             .mapMcQuestionToContactField( MCcontactFiled.COLORLOCAL )
                                                             .updateQuestion();

        return this;
    }

    private SurveyTemplate verifySurveyTemplateMappingsThreePages() throws Exception {

        log.startStep( "Wait for IA folder to be displayed" );
        driver.waitUntilElementIsLocated( element.iaEventFolderDropDown_2, driver.timeOut );
        driver.waitUntilElementIsClickable( element.iaEventFolderDropDown_2 );
        log.endStep();

        send.iaSurvey.goToPageInSurvey( "Page 1" );

        send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( FTcontactField.PRIMARYEMAIL.question,
                                                                                                 FTcontactField.PRIMARYEMAIL.conntactFiled ),
                                                    element.getFreeTextQuestionMappingIndicator( FTcontactField.FIRSTNAME.question,
                                                                                                 FTcontactField.FIRSTNAME.conntactFiled ),
                                                    element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.GENDER.question,
                                                                                                       MCcontactFiled.GENDER.name ) },
                                      new String[]{ "Email mapping", "First Name mapping", "Gender mapping" },
                                      true );

        send.iaSurvey.goToPageInSurvey( "Page 2" );

        send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( FTcontactField.LASTNAME.question,
                                                                                                 FTcontactField.LASTNAME.conntactFiled ),
                                                    element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.GENDERGLOBAL.question,
                                                                                                       MCcontactFiled.GENDERGLOBAL.name ) },
                                      new String[]{ "Last Name mapping", "Gender Global mapping" },
                                      true );

        send.iaSurvey.goToPageInSurvey( "Page 3" );

        send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.COLORLOCAL.question,
                                                                                                       MCcontactFiled.COLORLOCAL.name ) },
                                      new String[]{ "Color Local mapping" },
                                      true );

        return this;
    }

    @Test(groups = { "survey-template" })
    public void successfullyCreateSurveyWithTemplateQuestionsOnePage() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "surveyName";

        log.startTest( "Verify that survey can be successfully created with template quesitons only one page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateOnePage,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();

            send.iaSurvey.saveSurvey();

            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );
            verifySurveyTemplateOnePageContent().verifySurveyTemplateMappingsOnePage();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCreateSurveyWithTemplateQuestionsOnePageNewQuestionAdded() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "surveyName";

        log.startTest( "Verify that survey can be successfully created with template quesitons only one page and another question is added" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateOnePage,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.COMPANY.question )
                                    .mapFtQuestionToContactField( FTcontactField.COMPANY.conntactFiled )
                                    .updateQuestion();
            send.iaSurvey.saveSurvey();

            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );
            verifySurveyTemplateOnePageContent().verifySurveyTemplateMappingsOnePage();

            send.verifyDisplayedElements( new String[]{ element.getSurveyFTQcontentText( FTcontactField.COMPANY.question ) },
                                          new String[]{ "Company mapping" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyRetainCreatedSurveyMappingsWithTemplateOnePage() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "surveyName";

        log.startTest( "Verify that survey with template with one page successfully retains its mappings when user click back to Content tab " );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateOnePage,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();

            send.iaSurvey.saveAndContinueToTheme();

            log.startStep( "Go back to the Survey" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            verifySurveyTemplateOnePageContent().verifySurveyTemplateMappingsOnePage();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCreateSurveyWithTemplateQuestionsThreePages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "surveyName";

        log.startTest( "Verify that survey can be successfully created with template quesitons with three pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateThreePages,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();

            send.iaSurvey.saveSurvey();

            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );
            verifySurveyTemplateThreePagesContent().verifySurveyTemplateMappingsThreePages();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCreateSurveyWithTemplateQuestionsThreePagesNewQuestionAdded() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "surveyName";

        log.startTest( "Verify that survey can be successfully created with template quesitons on three pages and another question is added" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateThreePages,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.COMPANY.question )
                                    .mapFtQuestionToContactField( FTcontactField.COMPANY.conntactFiled )
                                    .updateQuestion();
            send.iaSurvey.saveSurvey();

            send.goToSurveyPage().iaSurvey.openSurvey( surveyName );
            verifySurveyTemplateThreePagesContent().verifySurveyTemplateMappingsThreePages();

            send.verifyDisplayedElements( new String[]{ element.getSurveyFTQcontentText( FTcontactField.COMPANY.question ) },
                                          new String[]{ "Company mapping" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyRetainCreatedSurveyMappingsWithTemplateThreePages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "surveyName";

        log.startTest( "Verify that survey with template with three pages successfully retains its mappings when user click back to Content tab" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateThreePages,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
            send.iaSurvey.saveAndContinueToTheme();

            log.startStep( "Go back to the Survey" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            verifySurveyTemplateThreePagesContent().verifySurveyTemplateMappingsThreePages();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCompleteSurveyWithTemplateQuestionsOnePage() throws Exception {

        //Contact par = new Contact();
        ContactDetails par = new ContactDetails();

        String number = driver.getTimestamp();

        String surveyName = number + "surveyName";
        String emailAddress = number + "email@concep.com";
        String firstName = number + "firstName";
        String lastName = number + "lastName";

        log.startTest( "Verify that survey can be successfully completed with template quesitons only one page" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateOnePage,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
            send.iaSurvey.saveAndSelectTheme( Theme.TABLE.type )
                         .viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                             .answerFtQuestion( FTcontactField.PRIMARYEMAIL.question,
                                                                                emailAddress )
                                                             .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                firstName )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName )
                                                             .answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                             .answerMcQuestion( MCcontactFiled.COLORLOCAL.question,
                                                                                new String[]{ MCcontactFiled.COLORLOCAL.option_1 } );

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( MCcontactFiled.GENDER.name );
            questionsArray.add( FTcontactField.PRIMARYEMAIL.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( MCcontactFiled.COLORLOCAL.name );

            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( MCcontactFiled.COLORLOCAL.option_1 );
            par.lastName.add( lastName );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.additionalFields.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.additionalFields.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToIA( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCompleteSurveyWithTemplateQuestionsOnePageNewQuestionAdded() throws Exception {

        //Contact par = new Contact();
        ContactDetails par = new ContactDetails();

        String number = driver.getTimestamp();

        String surveyName = number + "surveyName";
        String emailAddress = number + "email@concep.com";
        String firstName = number + "firstName";
        String lastName = number + "lastName";
        String companyName = number + "companyName";

        log.startTest( "Verify that survey can be successfully completed with template quesitons only one page and another question is added" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateOnePage,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.COMPANY.question )
                                    .mapFtQuestionToContactField( FTcontactField.COMPANY.conntactFiled )
                                    .updateQuestion();

            send.iaSurvey.saveAndSelectTheme( Theme.TABLE.type )
                         .viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                             .answerFtQuestion( FTcontactField.PRIMARYEMAIL.question,
                                                                                emailAddress )
                                                             .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                firstName )
                                                             .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                lastName )
                                                             .answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                             .answerMcQuestion( MCcontactFiled.COLORLOCAL.question,
                                                                                new String[]{ MCcontactFiled.COLORLOCAL.option_1 } )
                                                             .answerFtQuestion( FTcontactField.COMPANY.question,
                                                                                companyName );

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( MCcontactFiled.GENDER.name );
            questionsArray.add( FTcontactField.PRIMARYEMAIL.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( MCcontactFiled.COLORLOCAL.name );
            questionsArray.add( FTcontactField.COMPANY.conntactFiled );

            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( MCcontactFiled.COLORLOCAL.option_1 );
            answersArray.add( companyName );

            par.lastName.add( lastName );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.additionalFields.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.additionalFields.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToIA( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCompleteSurveyWithTemplateQuestionsThreePages() throws Exception {

        //Contact par = new Contact();
        ContactDetails par = new ContactDetails();

        String number = driver.getTimestamp();

        String surveyName = number + "surveyName";
        String emailAddress = number + "email@concep.com";
        String firstName = number + "firstName";
        String lastName = number + "lastName";

        log.startTest( "Verify that survey can be successfully completed with template quesitons with three pages" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateThreePages,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
            send.iaSurvey.saveAndSelectTheme( Theme.TABLE.type )
                         .viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                             .answerFtQuestion( FTcontactField.PRIMARYEMAIL.question,
                                                                                emailAddress )
                                                             .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                firstName );
            send.iaSurvey.surveyNext().iaQuestion.answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                    lastName )
                                                 .answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                    new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } );
            send.iaSurvey.surveyNext().iaQuestion.answerMcQuestion( MCcontactFiled.COLORLOCAL.question,
                                                                    new String[]{ MCcontactFiled.COLORLOCAL.option_1 } );

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( MCcontactFiled.GENDER.name );
            questionsArray.add( FTcontactField.PRIMARYEMAIL.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( MCcontactFiled.COLORLOCAL.name );

            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( MCcontactFiled.COLORLOCAL.option_1 );
            par.lastName.add( lastName );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.additionalFields.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.additionalFields.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToIA( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCompleteSurveyWithTemplateQuestionsThreePagesNewQuestionAdded() throws Exception {

        //Contact par = new Contact();
        ContactDetails par = new ContactDetails();

        String number = driver.getTimestamp();

        String surveyName = number + "surveyName";
        String emailAddress = number + "email@concep.com";
        String firstName = number + "firstName";
        String lastName = number + "lastName";
        String companyName = number + "companyName";

        log.startTest( "Verify that survey can be successfully completed with template quesitons on three pages and another question is added" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateThreePages,
                                                                                      surveyName )
                                                   .selectSurveyFolders( surveyType1,
                                                                         eventFolderWithSponsor );
            verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();

            send.iaSurvey.iaQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                    .enterQuestion( FTcontactField.COMPANY.question )
                                    .mapFtQuestionToContactField( FTcontactField.COMPANY.conntactFiled )
                                    .updateQuestion();

            send.iaSurvey.saveAndSelectTheme( Theme.TABLE.type )
                         .viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                             .answerFtQuestion( FTcontactField.PRIMARYEMAIL.question,
                                                                                emailAddress )
                                                             .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                firstName );
            send.iaSurvey.surveyNext().iaQuestion.answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                    lastName )
                                                 .answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                    new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } );
            send.iaSurvey.surveyNext().iaQuestion.answerMcQuestion( MCcontactFiled.COLORLOCAL.question,
                                                                    new String[]{ MCcontactFiled.COLORLOCAL.option_1 } )
                                                 .answerFtQuestion( FTcontactField.COMPANY.question,
                                                                    companyName );

            send.iaSurvey.surveyNext();

            log.startStep( "Save all the parameters to external file" );

            questionsArray = new ArrayList<>();
            answersArray = new ArrayList<>();

            questionsArray.add( MCcontactFiled.GENDER.name );
            questionsArray.add( FTcontactField.PRIMARYEMAIL.conntactFiled );
            questionsArray.add( FTcontactField.FIRSTNAME.conntactFiled );
            questionsArray.add( FTcontactField.LASTNAME.conntactFiled );
            questionsArray.add( MCcontactFiled.GENDERGLOBAL.name );
            questionsArray.add( MCcontactFiled.COLORLOCAL.name );
            questionsArray.add( FTcontactField.COMPANY.conntactFiled );

            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( MCcontactFiled.GENDERGLOBAL.option_1 );
            answersArray.add( MCcontactFiled.COLORLOCAL.option_1 );
            answersArray.add( companyName );

            par.lastName.add( lastName );

            for( int i = 0; i < questionsArray.size(); i++ ) {

                par.additionalFields.questions.add( questionsArray.get( i ) );
            }

            for( int i = 0; i < answersArray.size(); i++ ) {

                par.additionalFields.answers.add( answersArray.get( i ) );
            }

            par.folderName = eventFolderAdministrative;

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToIA( stack[0].getMethodName(), par, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyCreateSurveyTemplate() throws Exception {

        String number = driver.getTimestamp();

        String surveyName = number + "surveyName";
        String templateName = number + "templateName";

        log.startTest( "Verify that survey template can be successfully created" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateOnePage,
                                                                                      surveyName );

            send.iaSurvey.saveSurveyAsTemplate( surveyName, templateName );

            send.goToSurveyPage().iaSurvey.startSurveyCreation();
            send.verifyDisplayedElements( new String[]{ element.getSurveyTemplate( templateName ) },
                                          new String[]{ templateName },
                                          true );

            send.iaSurvey.selectSurveyTemplate( templateName, surveyName );
            verifySurveyTemplateOnePageContent();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-template" })
    public void successfullyDeleteSurveyTemplate() throws Exception {

        String number = driver.getTimestamp();

        String surveyName = number + "surveyName";
        String templateName = number + "templateName";

        log.startTest( "Verify that survey template can be successfully created" );
        try {

            loginToSend().goToSurveyPage().iaSurvey.startCreatingSurveyUsingTemplate( surveyTemplateOnePage,
                                                                                      surveyName );

            send.iaSurvey.saveSurveyAsTemplate( surveyName, templateName );

            send.logOut()
                .typeInUserName( sendSuperUser )
                .typeInPassword( sendSuperPassword )
                .loginSendButton()
                .goToAdmin();
            send.iaSurvey.deleteSurveyTemplate( templateName );

            send.logOut()
                .typeInUserName( iaSendUser )
                .typeInPassword( iaSendPassword )
                .loginSendButton()
                .goToSurveyPage().iaSurvey.startSurveyCreation();

            send.verifyDisplayedElements( new String[]{ element.getSurveyTemplate( templateName ) },
                                          new String[]{ templateName },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
