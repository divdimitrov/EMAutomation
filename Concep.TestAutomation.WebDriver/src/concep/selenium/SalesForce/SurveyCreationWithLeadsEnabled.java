/**
 * Test Cases for Survey Creation Page with Leads enabled
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

public class SurveyCreationWithLeadsEnabled extends BaseSFDC {

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    private void createConnectionWithLeadsOnAndContactEntity(
                                                              @Optional("config/firefox.SFDC") String configLocation )
                                                                                                                      throws Exception {

        driver.init( configLocation );
        driver.url = driver.config.getProperty( "url" );
        sfdcSendUser = driver.config.getProperty( "sfdcSendUser" );
        sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", true );
        driver.stopSelenium();
    }

    /**
     * 
     * Verify that survey can not be created when email is not mapped and click 'Save and Continue to Theme' button
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveAndContinueButton()
                                                                                              throws Exception {

        log.startTest( "LeadEnable: Verify that survey can not be created when Email is not mapped and you click Save and Continue to Theme button" );
        unsuccessfullyCreateSurveyWhenSaving( false );
        log.endStep();
    }

    /**
     * 
     * Verify that survey can not be created when email is not mapped and click Save button
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveButton() throws Exception {

        log.startTest( "LeadEnable: Verify that survey can not be created when Email is not mapped and you click Save button" );
        unsuccessfullyCreateSurveyWhenSaving( true );
        log.endTest();
    }

    /**
     * 
     * Verify that survey mappings can be successfully updated 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyUpdateAndVerifySurveyMappings() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "UpdateAndVerifySurveyMappings";
        String statusOption1Updated = "Sent";
        String statusOption2Updated = "Responded";

        log.startTest( "LeadEnable: Verify that survey mappings can be successfully updated" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( true )
                                                                                                                    .addFTQAndMapItToLastName( false );

            // Go Back to the Survey Page
            log.startStep( "Go Back to the Survey Page" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the check the 'Log responses in your CRM system' checkbox is present" );
            log.endStep( verifyLogResponseInCRM() );

            Thread.sleep( 1000 );
            send.sfdcSurvey.sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion();

            send.sfdcSurvey.sfdcQuestion.editQuestionType( FTcontactField.LASTNAME.question )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion();

            send.sfdcSurvey.sfdcQuestion.editQuestionType( MCcontactFiled.LEADSOURCE.question )
                                        .enterQuestion( MCcontactFiled.SALUTATION.question );
            send.sfdcSurvey.sfdcQuestion.mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 );
            send.sfdcSurvey.sfdcQuestion.fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 );
            send.sfdcSurvey.sfdcQuestion.addMCQStatusOptions()
                                        .updateQuestion()
                                        .editQuestionType( FTcontactField.LASTNAME.question );

            log.resultStep( "Verify that the Last Name drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            log.resultStep( "Verify that the Last Name drop down is selected for lead" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceLeadField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion().editQuestionType( FTcontactField.EMAIL.question );

            log.resultStep( "Verify that the 'Email' drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                 FTcontactField.EMAIL.conntactFiled ) );

            log.resultStep( "Verify that the 'Email' drop down is selected for lead" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceLeadField span",
                                                 FTcontactField.EMAIL.conntactFiled ) );

            //Verify the multiple choice question
            send.sfdcSurvey.sfdcQuestion.cancelQuestion()
                                        .editQuestionType( MCcontactFiled.SALUTATION.question );

            log.resultStep( "Verify that the 'Salutation' contact field option is selected" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span",
                                                 MCcontactFiled.SALUTATION.name ) );

            log.resultStep( "Verify that the 'Salutation' lead field option is selected" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceLeadField span",
                                                 MCcontactFiled.SALUTATION.name ) );

            log.resultStep( "Verify the 1st answer field" );
            log.endStep( driver.isTextPresent( "//input[@id='29374']",
                                               MCcontactFiled.SALUTATION.option_1,
                                               driver.timeOut ) );

            log.resultStep( "Verify the 2nd answer field" );
            log.endStep( driver.isTextPresent( "//input[@id='54119']",
                                               MCcontactFiled.SALUTATION.option_2,
                                               driver.timeOut ) );

            log.resultStep( "Verify the 1st status drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerStatus-29374']", statusOption1Updated ) );

            log.resultStep( "Verify the 2nd status drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerStatus-54119']", statusOption2Updated ) );

            log.resultStep( "Verify the 1st contact drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerContact-29374']",
                                            MCcontactFiled.SALUTATION.option_1 ) );

            log.resultStep( "Verify the 2nd contact drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerContact-54119']",
                                            MCcontactFiled.SALUTATION.option_2 ) );

            log.resultStep( "Verify the 1st lead drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerLead-29374']",
                                            MCcontactFiled.SALUTATION.option_2 ) );

            log.resultStep( "Verify the 2nd lead drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerLead-54119']",
                                            MCcontactFiled.SALUTATION.option_2 ) );

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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyVerifySurveyDefaultDropDowns() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "VerifyDefaultDropDowns";

        log.startTest( "LeadEnable: Verify that survey drop downs have default value Please select" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editQuestionType( "A free text question" );

            log.resultStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Please select" ) );

            log.resultStep( "Verify the Lead drop down" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceLeadField span", "Please select" ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion().editQuestionType( "A multiple choice question" );

            log.resultStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span", "Please select" ) );

            log.resultStep( "Verify the Lead drop down" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceLeadField span", "Please select" ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion().addNewQuestionType( "Free text", element.ftQId );

            log.resultStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Please select" ) );

            log.resultStep( "Verify the Lead drop down" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceLeadField span", "Please select" ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion().addNewQuestionType( "Multiple choice",
                                                                              element.mcQId );

            log.resultStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span", "Please select" ) );

            log.resultStep( "Verify the Lead drop down" );
            log.endStep( driver.isSelected( "#qTypemcSalesforceLeadField span", "Please select" ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyRetainingFieldMapping() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "RetainingFieldMapping";
        String theme = "Aqua";

        log.startTest( "LeadEnable: Verify that contact/lead fields retains their mappings after clicking again in the survey's content" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( theme );

            log.startStep( "Go back to the Survey" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the check the 'Log responses in your CRM system' checkbox is present" );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep( verifyLogResponseInCRM() );

            // Verify the 1st free text question 
            Thread.sleep( 1000 );
            send.sfdcSurvey.sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question );

            log.resultStep( "Verify the Contact drop down '" + FTcontactField.EMAIL.question
                            + "' is still retain 'Email' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                 FTcontactField.EMAIL.conntactFiled ) );

            log.resultStep( "Verify the Lead drop down '" + FTcontactField.EMAIL.question
                            + "' is still retain 'Email' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceLeadField span",
                                                 FTcontactField.EMAIL.conntactFiled ) );

            // Verify the 2nd free text question
            send.sfdcSurvey.sfdcQuestion.cancelQuestion().editQuestionType( FTcontactField.LASTNAME.question );

            log.resultStep( "Verify the Contact drop down '" + FTcontactField.LASTNAME.question
                            + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            log.resultStep( "Verify the Lead drop down '" + FTcontactField.LASTNAME.question
                            + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceLeadField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that error message is displayed when updating Multiple choice question and contact field has option set value which not mapped to answers
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-creation", "all-tests" })
    public void unsuccessfullyUpdatedQuestionDueToMissingMappingOfContactAnswers() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MissingMappingOfContactAnswers";

        log.startTest( "LeadEnable: Verify that an error message is displayed during survey creation when contact field is set and corresponding answers are not mapped" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType6, sfdcCampaign6 );
            send.sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.STATUS.question,
                                                                  QuestionStatus.EDIT )
                                        .fillinMCQAnswers( MCcontactFiled.STATUS.option_1,
                                                           MCcontactFiled.STATUS.option_2 )
                                        .mapQuestionToContactField( MCcontactFiled.STATUS.name,
                                                                    element.sfdcContactFieldMCid )
                                        .updateQuestion();

            log.resultStep( "Verify that the warning message is displayed 'Please map all marked fields'" );
            log.endStep( driver.isTextPresent( "//span[@class='validationMessage']",
                                               "Please map all marked fields",
                                               driver.ajaxTimeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that error message is displayed when updating Multiple choice question and Lead field has option set value which are not mapped to answers
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-creation", "all-tests" })
    public void unsuccessfullyUpdatedQuestionDueToMissingMappingOfLeadAnswers() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MissingMappingOfLeadAnswers";

        log.startTest( "LeadEnable: Verify that an error message is displayed during survey creation when lead field is set and corresponding answers are not mapped" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType2, sfdcCampaign2 );
            send.sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.STATUS.question,
                                                                  QuestionStatus.EDIT )
                                        .fillinMCQAnswers( MCcontactFiled.STATUS.option_1,
                                                           MCcontactFiled.STATUS.option_2 )
                                        .mapQuestionToContactField( MCcontactFiled.STATUS.name,
                                                                    element.sfdcLeadFieldMCid )
                                        .updateQuestion();

            log.resultStep( "Verify that the warning message is displayed 'Please map all marked fields'" );
            log.endStep( driver.isTextPresent( "//span[@class='validationMessage']",
                                               "Please map all marked fields",
                                               driver.ajaxTimeOut ) );
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

    private void retrainMappingsAfterAddingNewQuestions(
                                                         String description,
                                                         String qType ) throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "retaingMappingsAfterAdding" + qType + "Questions";
        String statusOption1 = "Sent";
        String statusOption2 = "Responded";

        log.startTest( "LeadEnable: Verify that questions retrain their mappings if you add new "
                       + description + " questions without pressing Save." );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );
            // Editing free text question
            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled );
            send.sfdcSurvey.sfdcQuestion.addFTQAndMapItToEmail( false )
                                        .editQuestionType( FTcontactField.LASTNAME.question );

            log.startStep( "Verify the Contact drop down '" + FTcontactField.LASTNAME.question
                           + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            log.startStep( "Verify the Lead drop down '" + FTcontactField.LASTNAME.question
                           + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceLeadField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            // Edit the Default MCQ 
            send.sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question,
                                                                  QuestionStatus.EDIT )
                                        .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                           MCcontactFiled.LEADSOURCE.option_2 );
            send.sfdcSurvey.sfdcQuestion.addMCQStatusOptions()
                                        .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
                                                                      MCcontactFiled.LEADSOURCE.option_1,
                                                                      MCcontactFiled.LEADSOURCE.option_2 );

            // Add new MCQ
            if( qType == "dragAndDrop" ) {
                send.sfdcSurvey.sfdcQuestion.dragAndDropMCquestionAndMapItToSalutation( true );
            } else {
                send.sfdcSurvey.sfdcQuestion.addMCquestionAndMapItToSalutation( true );
            }

            // Verify the default MCQ retained it's mappings
            send.sfdcSurvey.sfdcQuestion.editQuestionType( MCcontactFiled.LEADSOURCE.question );

            log.resultStep( "Verify the Contact Field retained it's value " + MCcontactFiled.LEADSOURCE.name );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span",
                                                 MCcontactFiled.LEADSOURCE.name ) );

            log.resultStep( "Verify the Lead Field retained it's value " + MCcontactFiled.LEADSOURCE.name );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceLeadField span",
                                                 MCcontactFiled.LEADSOURCE.name ) );

            // Verify drop down values for status
            log.resultStep( "Verify that the 1st status option is " + statusOption1 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerStatus')]]",
                                            statusOption1 ) );

            log.resultStep( "Verify that the 2nd status option is " + statusOption2 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerStatus')]]",
                                            statusOption2 ) );

            // Verify drop down values for Contacts 
            log.resultStep( "Verify that the 1st contact option is " + MCcontactFiled.LEADSOURCE.option_1 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerContact')]]",
                                            MCcontactFiled.LEADSOURCE.option_1 ) );

            log.resultStep( "Verify that the 2nd contact option is " + MCcontactFiled.LEADSOURCE.option_2 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerContact')]]",
                                            MCcontactFiled.LEADSOURCE.option_2 ) );
            // Verify drop down values for Leads
            log.resultStep( "Verify that the 1st lead option is " + MCcontactFiled.LEADSOURCE.option_1 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerLead')]]",
                                            MCcontactFiled.LEADSOURCE.option_1 ) );

            log.resultStep( "Verify that the 1st lead option is " + MCcontactFiled.LEADSOURCE.option_2 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerLead')]]",
                                            MCcontactFiled.LEADSOURCE.option_2 ) );

            log.resultStep( "Verify that the text field of the 1st answer is"
                            + MCcontactFiled.LEADSOURCE.option_1 );
            log.endStep( driver.isTextPresent( "//input[@id='29374']",
                                               MCcontactFiled.LEADSOURCE.option_1,
                                               driver.timeOut ) );

            log.resultStep( "Verify that the text field of the 2nd answer is"
                            + MCcontactFiled.LEADSOURCE.option_2 );
            log.endStep( driver.isTextPresent( "//input[@id='54119']",
                                               MCcontactFiled.LEADSOURCE.option_2,
                                               driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    //@Test(groups = { "survey-creation", "all-tests" })
    public void successfullyRetrainMappingsAfterAddingDragAndDropQuestions() throws Exception {

        retrainMappingsAfterAddingNewQuestions( "Drag And Drop", "dragAndDrop" );

    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyRetrainmappingsAfterAddingNewQuestions() throws Exception {

        retrainMappingsAfterAddingNewQuestions( "", "new" );
    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayedSurveyTypeAndCampaignDropdownsWhenCheckLogResponses() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MissingMappingOfLeadAnswers";

        log.startTest( "LeadEnable: Verify that Survey Type and Campaign dropdown menus are successfully displayed when Log Responses are checked" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName );

            log.startStep( "Verify that the 'Log responses in your CRM system' checkbox is displayed" );
            log.endStep( driver.isElementPresent( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']",
                                                  driver.timeOut ) );

            log.startStep( "Check the 'Log responses in your CRM system' checkbox" );
            send.sfdcSurvey.checkLogResponseInCRM();
            log.endStep();

            log.resultStep( "Verify that the Survey Type dropdown is displayed" );
            log.endStep( driver.isElementPresent( "//select[@id='surveyTypeDropDown']", driver.timeOut ) );

            log.resultStep( "Verify that the Campaign dropdown is dispalyed" );
            log.endStep( driver.isElementPresent( "//select[@id='surveySalesForceCampaigns']", driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void unsuccessfullyDisplayedSurveyTypeAndCampaignDropdownsWhenUnCheckLogResponses()
                                                                                              throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MissingMappingOfLeadAnswers";

        log.startTest( "LeadEnable: Verify that Survey Type and Campaign dropdown menus are successfully displayed when Log Responses are checked" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName );

            log.startStep( "Verify that the 'Log responses in your CRM system' checkbox is displayed" );
            log.endStep( driver.isElementPresent( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']",
                                                  driver.timeOut ) );

            log.resultStep( "Verify that the Survey Type dropdown is not displayed" );
            log.endStep( !driver.isElementPresent( "//select[@id='surveyTypeDropDown']", driver.negativeTimeOut ) );

            log.resultStep( "Verify that the Campaign dropdown is not dispalyed" );
            log.endStep( !driver.isElementPresent( "//select[@id='surveySalesForceCampaigns']",
                                                   driver.negativeTimeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyDisplayedSurveyTypeAndCampaignDropdownsWithDefaultValuesSelected()
                                                                                              throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MissingMappingOfLeadAnswers";

        log.startTest( "LeadEnable: Verify that Survey Type and Campaign dropdown menus have default values 'Please select'" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName );

            log.startStep( "Verify that the 'Log responses in your CRM system' checkbox is displayed" );
            log.endStep( driver.isElementPresent( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']",
                                                  driver.timeOut ) );

            log.startStep( "Check the 'Log responses in your CRM system' checkbox" );
            send.sfdcSurvey.checkLogResponseInCRM();
            log.endStep();

            log.startStep( "Verify that the Survey Type dropdown is displayed" );
            log.endStep( driver.isElementPresent( element.sfdcSurveyDropDownMenu, driver.timeOut ) );

            log.resultStep( "Verify that the Campaign dropdown is dispalyed" );
            log.endStep( driver.isElementPresent( element.sfdcCampaignDropDownMenu, driver.timeOut ) );

            driver.waitForAjaxToComplete( driver.timeOut );

            log.resultStep( "Verify that the Survey Type dropdown has default selected value" );
            log.endStep( driver.isValueSelected( "#select2-surveyTypeDropDown-container", "Please select" ) );

            log.resultStep( "Verify that the Campaign has default selected value" );
            log.endStep( driver.isValueSelected( "#select2-surveySalesForceCampaigns-container",
                                                 "Please select" ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplaySurveyLanguagePages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithLanguagePages";

        log.startTest( "LeadEnable: Verify that the newly added survey language page tabs are successfully dispalyed" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( true )
                                                                                                                    .addFTQAndMapItToLastName( false );
            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( new String[]{ "English", "Bulgarian" } )
                                                       .saveSurvey();
            send.goToSurveyTab( "Questions" );

            // Verify Language pages tabs
            log.resultStep( "Verify that the default language tab is visible from the surveys page" );
            log.endStep( driver.isElementPresent( "//ul[@id='langSelection']//a[text()='English']",
                                                  driver.timeOut ) );

            log.resultStep( "Verify that the second language tab is visible from the surveys page" );
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
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( true )
                                                                                                                    .addFTQAndMapItToLastName( false );
            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages )
                                                       .saveSurvey();
            send.goToSurveyTab( "Questions" );
            for( int i = 0; i < ( languages.length ); i++ ) {
                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] ).sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                                                                   .enterQuestion( FTcontactField.EMAIL.question
                                                                                                   + " - "
                                                                                                   + languages[i] );
                Thread.sleep( 500 );
                send.sfdcSurvey.sfdcQuestion.updateQuestion()
                                            .editQuestionType( FTcontactField.LASTNAME.question )
                                            .enterQuestion( FTcontactField.LASTNAME.question + " - "
                                                            + languages[i] );
                Thread.sleep( 500 );
                send.sfdcSurvey.sfdcQuestion.updateQuestion()
                                            .editQuestionType( MCcontactFiled.LEADSOURCE.question )
                                            .enterQuestion( MCcontactFiled.LEADSOURCE.question + " - "
                                                            + languages[i] );
                Thread.sleep( 500 );
                send.sfdcSurvey.sfdcQuestion.updateQuestion();
            }
            send.sfdcSurvey.saveSurvey();

            for( int i = 0; i < languages.length; i++ ) {
                // Verify question content and the mappings of the questions from the default language page
                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] ).sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question
                                                                                                      + " - "
                                                                                                      + languages[i] );

                log.resultStep( "Verify that the 'Primary Email' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Email" ) );

                send.sfdcSurvey.sfdcQuestion.cancelQuestion()
                                            .editQuestionType( FTcontactField.LASTNAME.question + " - "
                                                               + languages[i] );

                log.resultStep( "Verify that the Last Name drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Last Name" ) );

                send.sfdcSurvey.sfdcQuestion.cancelQuestion()
                                            .editQuestionType( MCcontactFiled.LEADSOURCE.question + " - "
                                                               + languages[i] );
                log.resultStep( "Verify that the 'Lead Source' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span", "Lead Source" ) );

                send.sfdcSurvey.sfdcQuestion.cancelQuestion();
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyRetainSurveyMappingsAndQuestionContentAdditionalLanguagePages() throws Exception {

        log.startTest( "LeadEnable: Verify that the mappings are successfully retained on the additional language pages" );
        retainSurveyMappingQuestionsWithAdditionalLanguages( new String[]{ "English", "Bulgarian" } );
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyRetainSurveyMappingsAndQuestionContentThreeAdditionalLanguagePages()
                                                                                                throws Exception {

        log.startTest( "LeadEnable: Verify that the mappings are successfully retained on the three additional language pages" );
        retainSurveyMappingQuestionsWithAdditionalLanguages( new String[]{ "English", "Bulgarian", "Arabic" } );
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "open-defects" })
    //CONCAM-995
    public void successfullyRetainSurveyMappingsAndQuestionContentAfterDeletingAdditionalLanguagePages()
                                                                                                        throws Exception {

        log.startTest( "LeadEnable: Verify that the mappings are successfully retained after deleting additional language pages" );
        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithLanguagePages";
        String[] languages = { "English", "Bulgarian", "Arabic" };
        String[] newLanguages = { "English", "Bulgarian" };
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true )
                                                                                                                    .addFTQAndMapItToLastName( false );
            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages )
                                                       .saveSurvey();
            send.goToSurveyTab( "Questions" );
            for( int i = 0; i < ( languages.length ); i++ ) {
                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] ).sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                                                                   .enterQuestion( FTcontactField.EMAIL.question
                                                                                                   + " - "
                                                                                                   + languages[i] )
                                                                                   .updateQuestion()
                                                                                   .editQuestionType( FTcontactField.LASTNAME.question )
                                                                                   .enterQuestion( FTcontactField.LASTNAME.question
                                                                                                   + " - "
                                                                                                   + languages[i] )
                                                                                   .updateQuestion()
                                                                                   .editQuestionType( MCcontactFiled.SALUTATION.question )
                                                                                   .enterQuestion( MCcontactFiled.SALUTATION.question
                                                                                                   + " - "
                                                                                                   + languages[i] )
                                                                                   .updateQuestion();
            }
            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .deleteLanguage( new String[]{ "3" } );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < newLanguages.length; i++ ) {
                // Verify question content and the mappings of the questions from the default language page
                send.sfdcSurvey.goToSurveyLanguagePage( newLanguages[i] ).sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question
                                                                                                         + " - "
                                                                                                         + newLanguages[i] );

                log.resultStep( "Verify that the 'Primary Email' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                     MCcontactFiled.SALUTATION.name ) );

                send.sfdcSurvey.sfdcQuestion.cancelQuestion()
                                            .editQuestionType( FTcontactField.LASTNAME.question + " - "
                                                               + newLanguages[i] );

                log.resultStep( "Verify that the Last Name drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                     FTcontactField.LASTNAME.conntactFiled ) );

                send.sfdcSurvey.sfdcQuestion.cancelQuestion()
                                            .editQuestionType( MCcontactFiled.SALUTATION.question + " - "
                                                               + newLanguages[i] );
                log.resultStep( "Verify that the 'Gender' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span",
                                                     MCcontactFiled.SALUTATION.name ) );

                send.sfdcSurvey.sfdcQuestion.cancelQuestion();
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyIsSaved() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when survey is mapped" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true );
            send.sfdcSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests" })
    public void unsuccessfullyDisplayMappedSurveyIndicatorWhenSurveyIsNotSaved() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is not displayed when survey is mapped but not saved" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );
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

    @Test(groups = { "survey-creation", "all-tests"})
    public void successfullyDisplayMappedSurveyIndicatorWhenAlreadyExistingNotMappedSurveyIsMapped()
                                                                                                    throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when already existing not mapped survey is mapped" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          false ).sfdcSurvey.openSurvey( surveyName )
                                                            .checkLogResponseInCRM()
                                                            .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                           .addFTQAndMapItToLastName( false )
                                                                                                                           .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests" })
    public void unsuccessfullyDisplayMappedSurveyIndicatorWhenAlreadyExistingMappedSurveyIsUnMapped()
                                                                                                     throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is not displayed when already existing mapped survey is un-mapped" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true ).sfdcSurvey.openSurvey( surveyName )
                                                           .uncheckLogResponseInCRM();

            send.sfdcSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSearchForMappedSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when user is searching for an existing mapped survey" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true ).sfdcSurvey.searchSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyRecordIsDeleted() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        int surveysCount = 2;

        log.startTest( "Verify that survey mapping indicator is successfully displayed when one of the surveys in the table is deleted" );
        try {

            loginToSend().goToSurveyPage();

            for( int i = 1; i <= surveysCount; i++ ) {

                send.sfdcSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                               .checkLogResponseInCRM()
                               .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                              .addFTQAndMapItToLastName( false )
                                                                                              .editMCquestionAndMapItToSalutation( true );
                send.sfdcSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.sfdcSurvey.openSurvey( surveyName + 1 ).uncheckLogResponseInCRM().saveSurvey();
            send.goToSurveyPage().sfdcSurvey.deleteSurvey( surveyName + 1 );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + 2 ) },
                                          new String[]{ surveyName + 2 + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorsWhenSortingRecords() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        int surveysCount = 3;

        log.startTest( "Verify that survey mapping indicator is successfully displayed for the mapped surveys when user sort the records from the table" );
        try {

            loginToSend().goToSurveyPage();

            for( int i = 0; i < surveysCount; i++ ) {

                send.sfdcSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                               .checkLogResponseInCRM()
                               .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                              .addFTQAndMapItToLastName( false )
                                                                                              .editMCquestionAndMapItToSalutation( true );
                send.sfdcSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.sfdcSurvey.sortSurveysBy( "Created" ).sortSurveysBy( "Created" ); //This is not a duplication. The method is called twice because we are interested only by the recently created surveys

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

    @Test(groups = { "survey-creation", "all-tests",  })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyRecordIsCopied() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        int surveysCount = 2;

        log.startTest( "Verify that survey mapping indicator is successfully displayed when one of the surveys in the table is copied" );
        try {

            loginToSend().goToSurveyPage();

            for( int i = 1; i <= surveysCount; i++ ) {

                send.sfdcSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                               .checkLogResponseInCRM()
                               .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                              .addFTQAndMapItToLastName( false )
                                                                                              .editMCquestionAndMapItToSalutation( true );
                send.sfdcSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.goToSurveyPage().sfdcSurvey.copySurvey( true, surveyName + 1, surveyName + 1 + "Copy", false );
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhilePaging() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when user is paging through the records" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true );
            send.sfdcSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );
            send.sfdcSurvey.pagingRecords( "Next", 1 ).pagingRecords( "Previous", 1 );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorTextWhenHoverCampaignSelected() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator text 'Linked To: <Marketing List Name>' is successfully displayed when the indicator is hovered" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true );
            send.sfdcSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName )
                                                        + element.getMappingIndicatorText( sfdcCampaign1
                                                                                           + " (owned by ali hamadi)" ) },
                                          new String[]{ "Linked To: " + sfdcCampaign1
                                                        + " (owned by ali hamadi)" + " text" },
                                          true );
            
            System.out.println(element.getSurveyMappingIndicator( surveyName )
                                                        + element.getMappingIndicatorText( sfdcCampaign1
                                                                                           + " (owned by ali hamadi)" ));

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorTextWhenHoverNoCampaignSelected() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator text 'No linked marketing list' is successfully displayed when the indicator is hovered" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, "" ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                         .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName )
                                                        + element.getMappingIndicatorText( "" ) },
                                          new String[]{ "No linked marketing list" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyDisplayMappedFreeTextQuestionsIndicatorWithoutSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped free text"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is not saved" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( defaultFTQtxt )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                             element.ftQId )
                                                                        .enterQuestion( addedFTQbyButton )
                                                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                        .enterQuestion( addedFTQbyDragDrop )
                                                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().sfdcSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyDisplayMappedFreeTextQuestionsIndicatorWhenSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped free text"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is saved" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( defaultFTQtxt )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                             element.ftQId )
                                                                        .enterQuestion( addedFTQbyButton )
                                                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                        .enterQuestion( addedFTQbyDragDrop )
                                                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                        .updateQuestion();
            send.sfdcSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().sfdcSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                           .enterQuestion( defaultFTQtxt )
                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                              element.ftQId )
                                                                         .enterQuestion( addedFTQbyButton )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                         .enterQuestion( addedFTQbyDragDrop )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().sfdcSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
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

    @Test(groups = { "survey-creation", "all-tests" })
    public void unsuccessfullyDisplayNotMappedFreeTextQuestionsIndicatorWhenSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String defaultFTQtxt = "Default Free Text Question";
        String addedFTQbyButton = "FTQ added by button";
        String addedFTQbyDragDrop = "FTQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator icon is not displayed for not mapped free text questions "
                       + "(default one, added by the button, added by drag-and-drop) when saving the survey" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( defaultFTQtxt )
                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                              element.ftQId )
                                                                         .enterQuestion( "Email" )
                                                                         .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                         .updateQuestion()
                                                                         .addNewQuestionType( "Free Text",
                                                                                              element.ftQId )
                                                                         .enterQuestion( "Last Name" )
                                                                         .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                         .updateQuestion()
                                                                         .addNewQuestionType( "Free Text",
                                                                                              element.ftQId )
                                                                         .enterQuestion( addedFTQbyButton )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                         .enterQuestion( addedFTQbyDragDrop )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false );
            send.sfdcSurvey.saveSurvey();
            send.goToSurveyPage().sfdcSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyDisplayMappedMultipleChoiceQuestionsIndicatorWithoutSavingSurvey()
                                                                                              throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is not saved" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addMCquestionAndMapItToLeadSource( true );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.dragAndDropMCquestionAndMapItToStatus( true );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().sfdcSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyDisplayMappedMultipleChoiceQuestionsIndicatorWhenSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is saved" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addMCquestionAndMapItToLeadSource( true );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.dragAndDropMCquestionAndMapItToStatus( true );
            send.sfdcSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().sfdcSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editQuestionType( "A multiple choice question" )
                                        .enterQuestion( defaultMCQtxt )
                                        .updateQuestion();

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                   QuestionStatus.NEW )
                                                                         .fillinMCQAnswers( MCcontactFiled.MARITALSTATUS.option_1,
                                                                                            MCcontactFiled.MARITALSTATUS.option_2 )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                   QuestionStatus.DRAGANDDROP )
                                                                         .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                            MCcontactFiled.ROLE.option_2 )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().sfdcSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
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

    @Test(groups = { "survey-creation", "all-tests" })
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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editQuestionType( "A multiple choice question" )
                                        .enterQuestion( defaultMCQtxt )
                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                   QuestionStatus.NEW )
                                                                         .fillinMCQAnswers( MCcontactFiled.MARITALSTATUS.option_1,
                                                                                            MCcontactFiled.MARITALSTATUS.option_2 )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                   QuestionStatus.DRAGANDDROP )
                                                                         .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                            MCcontactFiled.ROLE.option_2 )
                                                                         .updateQuestion();
            send.sfdcSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().sfdcSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( defaultFTQtxt )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                             element.ftQId )
                                                                        .enterQuestion( addedFTQbyButton )
                                                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                        .updateQuestion();

            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                        .enterQuestion( addedFTQbyDragDrop )
                                                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                        .updateQuestion();
            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages )
                                                       .saveSurvey();
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                      element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                      element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                              new String[]{ "Added by drag-and-drop free text question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default free text question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button free text quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              true );
            }

            send.goToSurveyPage().sfdcSurvey.openSurvey( surveyName );

            for( int i = 0; i < languages.length; i++ ) {

                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                      element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                      element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayMappedMultipleChoiceQuestionsIndicatorOtherLanguagePages()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String[] languages = {

        "English", "Bulgarian", "German" };

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) on the other Language pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addMCquestionAndMapItToLeadSource( true );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.dragAndDropMCquestionAndMapItToStatus( true );

            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages )
                                                       .saveSurvey();
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                              new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default multiple choice question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button multiple choice quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              true );
            }

            send.goToSurveyPage().sfdcSurvey.openSurvey( surveyName );

            for( int i = 0; i < languages.length; i++ ) {

                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( defaultFTQtxt )
                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                              element.ftQId )
                                                                         .enterQuestion( addedFTQbyButton )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                         .enterQuestion( addedFTQbyDragDrop )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator" },
                                          false );

            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ),
                                                      element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ),
                                                      element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A multiple choice question" )
                                        .enterQuestion( defaultMCQtxt )
                                        .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                   QuestionStatus.NEW )
                                                                         .fillinMCQAnswers( MCcontactFiled.MARITALSTATUS.option_1,
                                                                                            MCcontactFiled.MARITALSTATUS.option_2 )
                                                                         .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                   QuestionStatus.DRAGANDDROP )
                                                                         .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                            MCcontactFiled.ROLE.option_2 )
                                                                         .updateQuestion();

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator" },
                                          false )
                .goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addContentQuestion( false, addedCQbyButton );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by button content quesiton Mapping Indicator" },
                                          false ).sfdcSurvey.sfdcQuestion.addContentQuestion( true,
                                                                                              addedCQbyDragDrop );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyDragDrop ),
                                                  element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by drag-and-drop content question Mapping Indicator",
                                                  "Added by button content quesiton Mapping Indicator" },
                                          false );

            send.goToSurveyTab( "Settings" ).sfdcSurvey.goToSurveySettingsTab( "Languages" )
                                                       .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.sfdcSurvey.goToSurveyLanguagePage( languages[i] );
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyDisplayWarningMessageDuplicatedFreeTextQuestionMappingsSamePage()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated free text question mappings at the same page" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( false )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion();
            send.sfdcSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayWarningMessageDuplicatedFreeTextQuestionMappingsDifferentPages()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated free text question mappings at different pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Email Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                     .updateQuestion();
            send.sfdcSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayWarningMessageDuplicatedMultipleChoiceQuestionMappingsSamePage()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated multiple choice question mappings at the same page" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyDisplayWarningMessageDuplicatedMultipleChoiceQuestionMappingsDifferentPages()
                                                                                                         throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated multiple choice question mappings at different pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyCreateSurveyWithDuplicatedQuesitonMappingsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to create a survey with duplicated question mappings on the same page" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.saveSurvey().handleDuplicateMappingsDialog( true );
            send.goToSurveyPage().sfdcSurvey.openSurvey( surveyName );

            Thread.sleep( 3000 );
            // TODO: verification steps to check survey mappings are retained have to be added

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCreateSurveyWithDuplicatedQuesitonMappingsDifferentPage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to create a survey with duplicated question mappings on the different pages" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( "Email" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Email Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );
            send.sfdcSurvey.saveSurvey().handleDuplicateMappingsDialog( true );
            send.goToSurveyPage().sfdcSurvey.openSurvey( surveyName );

            Thread.sleep( 3000 );
            // TODO: verification steps to check survey mappings are retained have to be added

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
