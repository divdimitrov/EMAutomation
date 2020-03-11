/*

*/

package concep.selenium.Dynamic;

import org.testng.annotations.Test;

import concep.selenium.send.SendEnum;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;

public class SurveyCreation extends BaseMSD {

    private String missingLastNameErrorMessage = "Sorry! We noticed you have not mapped the Last Name field. Are you sure you want to save this survey without Last Name field?";
    private String missingEmailErrorMessage    = "In order to log responses in your CRM system, E-Mail must be mapped. Please map a question to this contact field and try again.";

    private void unsuccessfullyCreatedSurveyWhenSaving(
                                                        String testDescription,
                                                        SendEnum.FTcontactField contactField,
                                                        Boolean saveOnly,
                                                        String surveyMappingErrorMessage ) throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MappingsErrorMessage";

        log.startTest( testDescription );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType1, campaignName1 ).dynamicQuestion.addOrUpdateFreeTextQuestion( QuestionStatus.EDIT,
                                                                                                                                                    contactField,
                                                                                                                                                    false );
            if( saveOnly ) {

                log.startStep( "Click the 'Save and Continue to Theme' button" );
                driver.click( "//div[@id='s_content-innerContainer']//a[@class='s_button surveySave']/span[contains(text(), 'Save')]",
                              driver.timeOut );
                driver.waitForPageToLoad();
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                log.endStep();

            } else {

                log.startStep( "Click the 'Save and Continue to Theme' button" );
                driver.click( "//div[@id='s_content-innerContainer']//span[contains(text(), 'Save and Continue to Theme »')]",
                              driver.timeOut );
                driver.waitForPageToLoad();
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                log.endStep();
            }

            log.startStep( "verify that '" + surveyMappingErrorMessage + "'" );
            driver.isTextPresent( "//div[@class='modalContent ui-helper-clearfix']/div",
                                  surveyMappingErrorMessage,
                                  driver.timeOut );
            log.endStep();

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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveAndContinueButton()
                                                                                              throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( "Verify that survey can not be created when primary email is not mapped and you click Save and Continue to Theme button",
                                               FTcontactField.LASTNAME,
                                               false,
                                               missingEmailErrorMessage );
    }

    /**
     * 
     * Verify that survey can not be created when email is not mapped and you
     * click Save button
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveButton() throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( "Verify that survey can not be created when primary email is not mapped and you click Save button",
                                               FTcontactField.LASTNAME,
                                               true,
                                               missingEmailErrorMessage );
    }

    /**
     * 
     * Verify the warning message when last name is not mapped and you click Save button
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyCreatedSurveyWhenLastNameIsNotMappedAndClickSaveButton() throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( " Verify the warning message when last name is not mapped and you click Save button",
                                               FTcontactField.EMAIL,
                                               true,
                                               missingLastNameErrorMessage );
    }

    /**
     * 
     * Verify the warning message when last name is not mapped and you click Save and Continue button
     * 
     * @throws Exception
     * 
     */
    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyCreatedSurveyWhenLastNameIsNotMappedAndClickSaveButtonAndContinue()
                                                                                               throws Exception {

        unsuccessfullyCreatedSurveyWhenSaving( " Verify the warning message when last name is not mapped and you click Save and Continue button",
                                               FTcontactField.EMAIL,
                                               false,
                                               missingLastNameErrorMessage );
    }

    /**
     * 
     * Verify that survey drop downs have default value Please select.
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyVerifySurveyDefaultDropDowns() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "VerifyDefaultDropDowns";

        log.startTest( "Verify that survey drop downs have default value Please select" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName );

            log.startStep( "Verify that the 'Log responses in your CRM system' checkbox is displayed" );
            log.endStep( driver.isElementPresent( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']",
                                                  driver.timeOut ) );

            log.startStep( "Check the 'Log responses in your CRM system' checkbox" );
            send.msdSurvey.checkLogResponseInCRM();
            log.endStep();

            log.startStep( "Verify the Survey drop down default value" );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep( driver.isValueSelected( "#select2-surveyTypeDropDown-container span",
                                                 "Please select" ) );

            log.startStep( "Verify the Campaign Type drop down default value" );
            log.endStep( driver.isValueSelected( "#select2-surveyDynamicsCampaigns-container span",
                                                 "Please select" ) );

            log.startStep( "Select the 'Campaign Type' option " + campaignName4 );
            driver.select2DropDown( element.msdCampaignDropDown, element.crmDropDownSearch, campaignName4 );
            log.endStep();

            log.startStep( "Select the folder '" + surveyType4 + "' from the 'Campaign' drop down" );
            driver.select2DropDown( element.crmSurveyTypeDropDown, element.crmDropDownSearch, surveyType4 );
            log.endStep();

            // Verify the default free text question
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" );

            log.startStep( "Verify the Contact Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span", "Please select" ) );

            log.startStep( "Click the Cancel button" );
            driver.click( "//a[@id='qCancel']", driver.timeOut );
            log.endStep();

            // Verify the default multiple choice question
            send.msdSurvey.dynamicQuestion.editQuestionType( "A multiple choice question" );

            log.startStep( "Verify the Contact Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypemcDynamicsContactsField span", "Please select" ) );

            log.startStep( "Verify the Response Codes default values" );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]/ul/li[1]/select", "Please select" )
                         && driver.isSelected( "//ul[@id='qAnswerList']/li[2]/ul/li[1]/select",
                                               "Please select" )
                         && driver.isSelected( "//ul[@id='qAnswerList']/li[3]/ul/li[1]/select",
                                               "Please select" )
                         && driver.isSelected( "//ul[@id='qAnswerList']/li[4]/ul/li[1]/select",
                                               "Please select" ) );
            send.msdSurvey.dynamicQuestion.cancelQuestion();

            // Add new free text question and verify the drop downs
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId );

            log.startStep( "Verify the Contact Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span", "Please select" ) );

            log.startStep( "Click the Cancel button" );
            driver.click( "//a[@id='qCancel']", driver.timeOut );
            log.endStep();

            // Add new multiple choice question and verify the drop downs
            log.startStep( "Click the 'Add Question' button to adda new multiple choice question" );
            driver.click( "//a[@id='qAdd']/span[contains(text(), 'Add Question')]", driver.timeOut );
            log.endStep();

            log.startStep( "Click the 'Multiple Choice' radio button" );
            driver.click( "//input[@id='radMc' and @type='radio']", driver.timeOut );
            log.endStep();

            log.startStep( "Verify the Contact Field drop down default value" );
            log.endStep( driver.isValueSelected( "#qTypemcDynamicsContactsField span", "Please select" ) );

            log.startStep( "Verify the Response Codes default values" );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]/ul/li[1]/select", "Please select" ) );

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

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyRetainingFieldMappings() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "RetainingFieldMappings";

        log.startTest( "Verify that contact/lead fields retains their mappings after clicking again in the survey's content" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( "Beach" );

            // Go Back to the Survey
            log.startStep( "Go back to the Survey" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that 'log responses in our CRM system check box is checked'" );
            log.endStep( verifyLogResponseInCRM() );

            // Verify the 1st free text question 
            log.startStep( "Click in the '" + FTcontactField.EMAIL.question + "' free text question section" );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.click( "//label[contains(text(), '" + FTcontactField.EMAIL.question + "')]",
                          driver.timeOut );
            log.endStep();

            log.startStep( "Verify the Contact field drop down '" + FTcontactField.EMAIL.question
                           + "' is still retain 'Primary Email' value" );
            log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                 FTcontactField.EMAIL.conntactFiled ) );

            // Verify the 2nd free text question
            log.startStep( "Click in the '" + FTcontactField.LASTNAME.question
                           + "' free text question section" );
            driver.click( "//label[contains(text(), '" + FTcontactField.LASTNAME.question + "')]",
                          driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify the Contact field drop down '" + FTcontactField.LASTNAME.question
                           + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyUpdateAndVerifySurveyMappings() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "UpdateAndVerifySurveyMappings";

        log.startTest( "Verify that survey mappings can be successfully updated" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType5, campaignName5 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToMartialStatus()
                                                                                                                      .addFTQAndMapItToLastName( false );

            // Go Back to the Survey
            log.startStep( "Go back to the Survey" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the check the 'Log responses in your CRM system' checkbox is present" );
            log.endStep( verifyLogResponseInCRM() );

            // Update Email Question
            send.msdSurvey.dynamicQuestion.editQuestionType( FTcontactField.LASTNAME.question )
                                          .mapQuestionToContactField( FTcontactField.LASTNAME.conntactFiled,
                                                                      element.msdContactFieldFTid )
                                          .updateQuestion()

                                          // Update Last Name Question
                                          .editQuestionType( FTcontactField.EMAIL.question )
                                          .mapQuestionToContactField( FTcontactField.EMAIL.conntactFiled,
                                                                      element.msdContactFieldFTid )
                                          .updateQuestion()

                                          // Update Multiple Choice Question
                                          .editQuestionType( MCcontactFiled.MARITALSTATUS.question )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapQuestionToContactField( MCcontactFiled.GENDER.name,
                                                                      element.msdContactFieldMCid );

            log.startStep( "Enter '" + MCcontactFiled.GENDER.option_1 + "' in the 1st answer text field" );
            driver.clear( "//input[@id='29374']" );
            driver.type( "//input[@id='29374']", MCcontactFiled.GENDER.option_1, driver.timeOut );
            log.endStep();

            log.startStep( "Enter '" + MCcontactFiled.GENDER.option_2 + "' in the 1st answer text field" );
            driver.clear( "//input[@id='54119']" );
            driver.type( "//input[@id='54119']", MCcontactFiled.GENDER.option_2, driver.timeOut );
            log.endStep();

            // Update 'Mapping' drop-down options
            log.startStep( "Update the 1st mapping option '" + MCcontactFiled.GENDER.option_1 + "'" );
            driver.select( "//select[@id='answerDropDown-29374']" )
                  .selectByVisibleText( MCcontactFiled.GENDER.option_1 );
            log.endStep();

            log.startStep( "Update the 2nd mapping option '" + MCcontactFiled.GENDER.option_2 + "'" );
            driver.select( "//select[@id='answerDropDown-54119']" )
                  .selectByVisibleText( MCcontactFiled.GENDER.option_2 );
            log.endStep();

            // Update 'Response codes' drop-down options
            log.startStep( "Update the 1st response code option 'Interested'" );
            driver.select( "//select[@id='responseCodeDropDown-29374']" ).selectByVisibleText( "Interested" );
            log.endStep();

            log.startStep( "Update the 2nd response code option 'Not Interested'" );
            driver.select( "//select[@id='responseCodeDropDown-54119']" )
                  .selectByVisibleText( "Not Interested" );
            log.endStep();

            send.msdSurvey.dynamicQuestion.updateQuestion().editQuestionType( FTcontactField.EMAIL.question );

            log.startStep( "Verify that the Email drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                 FTcontactField.EMAIL.conntactFiled ) );
            send.msdSurvey.dynamicQuestion.cancelQuestion()
                                          .editQuestionType( FTcontactField.LASTNAME.question );

            log.startStep( "Verify that the 'Last Name' drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            send.msdSurvey.dynamicQuestion.cancelQuestion().editQuestionType( MCcontactFiled.GENDER.question );

            log.startStep( "Verify that the '" + MCcontactFiled.GENDER.name
                           + "' contact field option is selected" );
            log.endStep( driver.isValueSelected( "#qTypemcDynamicsContactsField span",
                                                 MCcontactFiled.GENDER.name ) );

            log.startStep( "Verify the 1st answer field" );
            log.endStep( driver.isTextPresent( "//input[@id='29374'] ",
                                               MCcontactFiled.GENDER.option_1,
                                               driver.timeOut ) );

            log.startStep( "Verify the 2nd answer field" );
            log.endStep( driver.isTextPresent( "//input[@id='54119']",
                                               MCcontactFiled.GENDER.option_2,
                                               driver.timeOut ) );

            log.startStep( "Verify the 1st mapping drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerDropDown-29374']",
                                            MCcontactFiled.GENDER.option_1 ) );

            log.startStep( "Verify the 2nd mapping drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerDropDown-54119']",
                                            MCcontactFiled.GENDER.option_2 ) );

            log.startStep( "Verify the 1st response code drop down" );
            log.endStep( driver.isSelected( "//select[@id='responseCodeDropDown-29374']", "Interested" ) );

            log.startStep( "Verify the 2nd response code drop down" );
            log.endStep( driver.isSelected( "//select[@id='responseCodeDropDown-54119']", "Not Interested" ) );

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

    private void retrainMappingsAfterAddingNewFreeTextQuestion(
                                                                String description,
                                                                String qType ) throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "retaingMappingsAfterAdding" + qType + "Questions";

        log.startTest( "Verify that questions retrain their mappings if you add new free text " + description
                       + " question without pressing Save." );
        try {

            // Log in Send
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType5, campaignName5 );

            // Editing free text question
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .addFTQAndMapItToEmail( false );

            // Add Free Text Question
            send.msdSurvey.dynamicQuestion.editQuestionType( FTcontactField.LASTNAME.question );

            log.startStep( "Verify the Contact drop down '" + FTcontactField.LASTNAME.question
                           + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

            // Edit the Default MCQ 
            send.msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.GENDER.question,
                                                                    QuestionStatus.EDIT )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 );

            // Add new MCQ
            if( qType == "dragAndDrop" ) {
                send.msdSurvey.dynamicQuestion.dragAndDropMCQAndMapItToMartialStatus();
            } else {

                send.msdSurvey.dynamicQuestion.addMCQAndMapItToMartialStatus();
            }

            send.msdSurvey.dynamicQuestion.editQuestionType( MCcontactFiled.GENDER.question );

            log.startStep( "Verify that the contact Field retained it's value " + MCcontactFiled.GENDER.name );
            log.endStep( driver.isValueSelected( "#qTypemcDynamicsContactsField span",
                                                 MCcontactFiled.GENDER.name ) );

            // Verify drop down values for 'Mappings'
            log.startStep( "Verify that the 1st mapping option is " + MCcontactFiled.GENDER.option_1 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'answerDropDown')]]",
                                            MCcontactFiled.GENDER.option_1 ) );

            log.startStep( "Verify that the 2nd mapping option is " + MCcontactFiled.GENDER.option_2 );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'answerDropDown')]]",
                                            MCcontactFiled.GENDER.option_2 ) );

            // Verify the drop down values for the 'Response Codes'
            log.startStep( "Verify that the 1st Response Code option is 'Interested'" );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[1]//select[@id[starts-with(.,'responseCodeDropDown')]]",
                                            "Interested" ) );

            log.startStep( "Verify that the 2nd Response Code option is 'Not Interested'" );
            log.endStep( driver.isSelected( "//ul[@id='qAnswerList']/li[2]//select[@id[starts-with(.,'responseCodeDropDown')]]",
                                            "Not Interested" ) );

            log.startStep( "Verify that the text field of the 1st answer is" + MCcontactFiled.GENDER.option_1 );
            log.endStep( driver.isTextPresent( "//input[@id='29374']",
                                               MCcontactFiled.GENDER.option_1,
                                               driver.timeOut ) );

            log.startStep( "Verify that the text field of the 2nd answer is" + MCcontactFiled.GENDER.option_2 );
            log.endStep( driver.isTextPresent( "//input[@id='54119']",
                                               MCcontactFiled.GENDER.option_2,
                                               driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    // @Test(groups = { "survey-creation", "all-tests" })
    //TODO uncomment when PLAT-2316 is fixed.
    public void successfullyRetainMappingsAfterAddingDragAndDropQuestions() throws Exception {

        retrainMappingsAfterAddingNewFreeTextQuestion( "Drag And Drop", "dragAndDrop" );

    }

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyRetainmappingsAfterAddingNewFTQuestions() throws Exception {

        retrainMappingsAfterAddingNewFreeTextQuestion( "", "new" );

    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplaySurveyLanguagePages() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithLanguagePages";

        log.startTest( "Verify that the newly added survey language page tabs are successfully dispalyed" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
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
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
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
            send.msdSurvey.saveSurvey();
            for( int i = 0; i < languages.length; i++ ) {
                // Verify question content and the mappings of the questions from the default language page
                send.msdSurvey.goToSurveyLanguagePage( languages[i] ).dynamicQuestion.editQuestionType( FTcontactField.EMAIL.question
                                                                                                        + " - "
                                                                                                        + languages[i] );

                log.startStep( "Verify that the 'Primary Email' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                     FTcontactField.EMAIL.conntactFiled ) );

                send.msdSurvey.dynamicQuestion.cancelQuestion()
                                              .editQuestionType( FTcontactField.LASTNAME.question + " - "
                                                                 + languages[i] );

                log.startStep( "Verify that the Last Name drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                     FTcontactField.LASTNAME.conntactFiled ) );

                send.msdSurvey.dynamicQuestion.cancelQuestion()
                                              .editQuestionType( MCcontactFiled.GENDER.question + " - "
                                                                 + languages[i] );
                log.startStep( "Verify that the 'Gender' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypemcDynamicsContactsField span",
                                                     MCcontactFiled.GENDER.name ) );

                send.msdSurvey.dynamicQuestion.cancelQuestion();
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyRetainSurveyMappingsAndQuestionContentAdditionalLanguagePages() throws Exception {

        log.startTest( "Verify that the mappings are successfully retained on the additional language pages" );
        retainSurveyMappingQuestionsWithAdditionalLanguages( new String[]{ "English", "Bulgarian" } );
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyRetainSurveyMappingsAndQuestionContentThreeAdditionalLanguagePages()
                                                                                                throws Exception {

        log.startTest( "Verify that the mappings are successfully retained on the three additional language pages" );
        retainSurveyMappingQuestionsWithAdditionalLanguages( new String[]{ "English", "Bulgarian", "Arabic" } );
        log.endTest();
    }

    public void successfullyRetainSurveyMappingsAndQuestionContentAfterDeletingAdditionalLanguagePages()
                                                                                                        throws Exception {

        log.startTest( "Verify that the mappings are successfully retained after deleting additional language pages" );
        String number = driver.getTimestamp();
        String surveyName = number + "SurveyWithLanguagePages";
        String[] languages = { "English", "Bulgarian", "Arabic" };
        String[] newLanguages = { "English", "Bulgarian" };
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .addLanguage( languages )
                                                      .saveSurvey();
            send.goToSurveyTab( "Questions" );
            for( int i = 0; i < ( languages.length ) - 1; i++ ) {
                send.msdSurvey.goToSurveyLanguagePage( languages[i + 1] ).dynamicQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                                                                         .enterQuestion( FTcontactField.EMAIL.question
                                                                                                         + " - "
                                                                                                         + languages[i + 1] )
                                                                                         .updateQuestion()
                                                                                         .editQuestionType( FTcontactField.LASTNAME.question )
                                                                                         .enterQuestion( FTcontactField.LASTNAME.question
                                                                                                         + " - "
                                                                                                         + languages[i + 1] )
                                                                                         .updateQuestion()
                                                                                         .editQuestionType( MCcontactFiled.GENDER.question )
                                                                                         .enterQuestion( MCcontactFiled.GENDER.question
                                                                                                         + " - "
                                                                                                         + languages[i + 1] )
                                                                                         .updateQuestion();
            }
            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .deleteLanguage( new String[]{ "3" } );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < newLanguages.length; i++ ) {
                // Verify question content and the mappings of the questions from the default language page
                send.msdSurvey.goToSurveyLanguagePage( newLanguages[i] ).dynamicQuestion.editQuestionType( FTcontactField.EMAIL.question
                                                                                                           + " - "
                                                                                                           + newLanguages[i] );

                log.startStep( "Verify that the 'Primary Email' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                     FTcontactField.EMAIL.conntactFiled ) );

                send.msdSurvey.dynamicQuestion.cancelQuestion()
                                              .editQuestionType( FTcontactField.LASTNAME.question + " - "
                                                                 + newLanguages[i] );

                log.startStep( "Verify that the Last Name drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypetextDynamicsContactsField span",
                                                     FTcontactField.LASTNAME.conntactFiled ) );

                send.msdSurvey.dynamicQuestion.cancelQuestion()
                                              .editQuestionType( MCcontactFiled.GENDER.question + " - "
                                                                 + newLanguages[i] );
                log.startStep( "Verify that the 'Gender' drop down is selected for contact" );
                log.endStep( driver.isValueSelected( "#qTypemcDynamicsContactsField span",
                                                     MCcontactFiled.GENDER.name ) );

                send.msdSurvey.dynamicQuestion.cancelQuestion();
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyIsSaved() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when survey is mapped" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void unsuccessfullyDisplayMappedSurveyIndicatorWhenSurveyIsNotSaved() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is not displayed when survey is mapped but not saved" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
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

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenAlreadyExistingNotMappedSurveyIsMapped()
                                                                                                    throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when already existing not mapped survey is mapped" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          false );
            send.msdSurvey.openSurvey( surveyName )
                          .checkLogResponseInCRM()
                          .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                            .addFTQAndMapItToLastName( false )
                                                                                            .editMCQAndMapItToGender();

            send.msdSurvey.saveSurvey();
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );
            send.msdSurvey.openSurvey( surveyName ).uncheckLogResponseInCRM();

            send.msdSurvey.saveSurvey();
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );
            send.msdSurvey.searchSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
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

                send.msdSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                              .checkLogResponseInCRM()
                              .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                .addFTQAndMapItToLastName( false )
                                                                                                .editMCQAndMapItToGender();
                send.msdSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.msdSurvey.sortSurveysBy( "Created" ).sortSurveysBy( "Created" ); //This is not a duplication. The method is called twice because we are interested only by the recently created surveys

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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorTextWhenHoverCampaignSelected() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator text 'Linked To: <Marketing List Name>' is successfully displayed when the indicator is hovered" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName )
                                                        + element.getMappingIndicatorText( campaignName3 ) },
                                          new String[]{ "Linked To: " + campaignName3 + " text" },
                                          true );

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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, "" ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                           .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhilePaging() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when user is paging through the records" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName ) },
                                          new String[]{ surveyName + " Mapping Indicator" },
                                          true );
            send.msdSurvey.pagingRecords( "Next", 1 ).pagingRecords( "Previous", 1 );
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

                send.msdSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                              .checkLogResponseInCRM()
                              .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                .addFTQAndMapItToLastName( false )
                                                                                                .editMCQAndMapItToGender();
                send.msdSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.msdSurvey.openSurvey( surveyName + 1 ).uncheckLogResponseInCRM().saveSurvey();
            send.goToSurveyPage().msdSurvey.deleteSurvey( surveyName + 1 );
            send.verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + 2 ) },
                                          new String[]{ surveyName + 2 + " Mapping Indicator" },
                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSurveyRecordIsCopied() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        int surveysCount = 2;

        log.startTest( "Verify that survey mapping indicator is successfully displayed when one of the surveys in the table is copied" );
        try {

            loginToSend().goToSurveyPage();

            for( int i = 1; i <= surveysCount; i++ ) {

                send.msdSurvey.createSurveyAndTypeSurveyName( surveyName + i )
                              .checkLogResponseInCRM()
                              .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                .addFTQAndMapItToLastName( false )
                                                                                                .editMCQAndMapItToGender();
                send.msdSurvey.saveSurvey();
                send.goToSurveyPage()
                    .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName + i ) },
                                              new String[]{ surveyName + i + " Mapping Indicator" },
                                              true );
            }

            send.goToSurveyPage().msdSurvey.copySurvey( true, surveyName + 1, surveyName + 1 + "Copy", false );
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( defaultFTQtxt )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                               element.ftQId )
                                                                          .enterQuestion( addedFTQbyButton )
                                                                          .mapQuestionToContactField( FTcontactField.LASTNAME.conntactFiled,
                                                                                                      element.msdContactFieldFTid )
                                                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                          .enterQuestion( addedFTQbyDragDrop )
                                                                          .mapQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled,
                                                                                                      element.msdContactFieldFTid )
                                                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().msdSurvey.discardChangesDialog( true ).openSurvey( surveyName );
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( defaultFTQtxt )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                               element.ftQId )
                                                                          .enterQuestion( addedFTQbyButton )
                                                                          .mapQuestionToContactField( FTcontactField.LASTNAME.conntactFiled,
                                                                                                      element.msdContactFieldFTid )
                                                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                          .enterQuestion( addedFTQbyDragDrop )
                                                                          .mapQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled,
                                                                                                      element.msdContactFieldFTid )
                                                                          .updateQuestion();
            send.msdSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, FTcontactField.FIRSTNAME.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().msdSurvey.openSurvey( surveyName );
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

    @Test(groups = { "survey-creation", "all-tests" })
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( defaultFTQtxt )
                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                                element.ftQId )
                                                                           .enterQuestion( addedFTQbyButton )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                           .enterQuestion( addedFTQbyDragDrop )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().msdSurvey.discardChangesDialog( true ).openSurvey( surveyName );
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( defaultFTQtxt )
                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text",
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
                                          false ).msdSurvey.dynamicQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                           .enterQuestion( addedFTQbyDragDrop )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ),
                                                  element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator",
                                                  "Default free text question Mapping Indicator",
                                                  "Added by button free text quesiton Mapping Indicator" },
                                          false );
            send.msdSurvey.saveSurvey();
            send.goToSurveyPage().msdSurvey.openSurvey( surveyName );
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

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is not saved" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( "Email" )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( "Last Name" )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .editQuestionType( "A multiple choice question" )
                                                                                                                      .enterQuestion( defaultMCQtxt )
                                                                                                                      .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                                                                      .deleteMcQuestionAnswers( 2 )
                                                                                                                      .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name  ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                    QuestionStatus.NEW )
                                                                          .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                                                             MCcontactFiled.COLORGLOBAL.option_2 )
                                                                          .mapMCQuestionToContactField( MCcontactFiled.COLORGLOBAL )
                                                                          .addMCResponseCodeOptions()
                                                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                    QuestionStatus.DRAGANDDROP )
                                                                          .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                             MCcontactFiled.ROLE.option_2 )
                                                                          .mapMCQuestionToContactField( MCcontactFiled.ROLE )
                                                                          .addMCResponseCodeOptions()
                                                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, MCcontactFiled.ROLE.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().msdSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, MCcontactFiled.ROLE.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
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

        String defaultMCQtxt = "Default Multiple Choice Question";
        String addedMCQbyButton = "MCQ added by button";
        String addedMCQbyDragDrop = "MCQ added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is successfully displayed for all the types of mapped multiple choice"
                       + " quesitons (default one, added by the button, added by drag-and-drop) when survey is saved" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .enterQuestion( defaultMCQtxt )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                    QuestionStatus.NEW )
                                                                          .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                                                             MCcontactFiled.COLORGLOBAL.option_2 )
                                                                          .mapMCQuestionToContactField( MCcontactFiled.COLORGLOBAL )
                                                                          .addMCResponseCodeOptions()
                                                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                    QuestionStatus.DRAGANDDROP )
                                                                          .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                             MCcontactFiled.ROLE.option_2 )
                                                                          .mapMCQuestionToContactField( MCcontactFiled.ROLE )
                                                                          .addMCResponseCodeOptions()
                                                                          .updateQuestion();
            send.msdSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, MCcontactFiled.ROLE.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          true )
                .goToSurveyPage().msdSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, MCcontactFiled.ROLE.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
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

    @Test(groups = { "survey-creation", "all-tests"})
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( "Email" )
                                                                                                                      .mapQuestionToContactField( FTcontactField.EMAIL.conntactFiled,
                                                                                                                                                  element.msdContactFieldFTid )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( "Last Name" )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .editQuestionType( "A multiple choice question" )
                                                                                                                      .enterQuestion( defaultMCQtxt )
                                                                                                                      .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                     QuestionStatus.NEW )
                                                                           .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                                                              MCcontactFiled.COLORGLOBAL.option_2 )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                     QuestionStatus.DRAGANDDROP )
                                                                           .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                              MCcontactFiled.ROLE.option_2 )
                                                                           .updateQuestion();
            send.msdSurvey.saveSurvey();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ),
                                                  element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator",
                                                  "Default multiple choice question Mapping Indicator",
                                                  "Added by button multiple choice quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().msdSurvey.openSurvey( surveyName );
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email" )
                                          .mapQuestionToContactField( FTcontactField.EMAIL.conntactFiled,
                                                                      element.msdContactFieldFTid )
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
                                          false ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                     QuestionStatus.NEW )
                                                                           .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                                                              MCcontactFiled.COLORGLOBAL.option_2 )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
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
                .goToSurveyPage().msdSurvey.discardChangesDialog( true ).openSurvey( surveyName );
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
    public void unsuccessfullyDisplayContentQuestionsIndicatorWithoutSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String addedCQbyButton = "Content Question added by button";
        String addedCQbyDragDrop = "Content Question added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is not displayed for the content type of questions"
                       + " (added by the button, added by drag-and-drop) when survey is not saved" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.addFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .addContentQuestion( false,
                                                                                                                                           addedCQbyButton );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by button content quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addContentQuestion( true,
                                                                                                addedCQbyDragDrop );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyDragDrop ),
                                                  element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by drag-and-drop content question Mapping Indicator",
                                                  "Added by button content quesiton Mapping Indicator" },
                                          false )
                .goToSurveyPage().msdSurvey.discardChangesDialog( true ).openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyButton ),
                                                  element.getContentQuesiton( addedCQbyDragDrop ) },
                                          new String[]{ "Added by button content quesiton Mapping Indicator",
                                                  "Added by drag-and-drop content question Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void unsuccessfullyDisplayContentQuestionsIndicatorWhenSavingSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String addedCQbyButton = "Content Question added by button";
        String addedCQbyDragDrop = "Content Question added by drag-and-drop";

        log.startTest( "Verify that the mapping indicator is not displayed for the content type of questions"
                       + " (added by the button, added by drag-and-drop) when survey is saved" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addFTQAndMapItToEmail( false )
                                          .addFTQAndMapItToLastName( false )
                                          .addContentQuestion( false, addedCQbyButton );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by button content quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addContentQuestion( true,
                                                                                                addedCQbyDragDrop );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyDragDrop ),
                                                  element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by drag-and-drop content question Mapping Indicator",
                                                  "Added by button content quesiton Mapping Indicator" },
                                          false );
            send.msdSurvey.saveSurvey();
            send.goToSurveyPage().msdSurvey.openSurvey( surveyName );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyButton ),
                                                  element.getContentQuesiton( addedCQbyDragDrop ) },
                                          new String[]{ "Added by button content quesiton Mapping Indicator",
                                                  "Added by drag-and-drop content question Mapping Indicator" },
                                          false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "key-tests"})
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editQuestionType( "A free text question" )
                                                                                                                      .enterQuestion( defaultFTQtxt )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                      .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, FTcontactField.EMAIL.conntactFiled ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                               element.ftQId )
                                                                          .enterQuestion( addedFTQbyButton )
                                                                          .mapQuestionToContactField( FTcontactField.LASTNAME.conntactFiled,
                                                                                                      element.msdContactFieldFTid )
                                                                          .updateQuestion();

            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, FTcontactField.LASTNAME.conntactFiled ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                          .enterQuestion( addedFTQbyDragDrop )
                                                                          .mapQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled,
                                                                                                      element.msdContactFieldFTid )
                                                                          .updateQuestion();
            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .addLanguage( languages )
                                                      .saveSurvey();
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] );
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

            send.goToSurveyPage().msdSurvey.openSurvey( surveyName );

            for( int i = 0; i < languages.length; i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] );
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .enterQuestion( defaultMCQtxt )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                    QuestionStatus.NEW )
                                                                          .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                                                             MCcontactFiled.COLORGLOBAL.option_2 )
                                                                          .mapMCQuestionToContactField( MCcontactFiled.COLORGLOBAL )
                                                                          .addMCResponseCodeOptions()
                                                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                    QuestionStatus.DRAGANDDROP )
                                                                          .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                             MCcontactFiled.ROLE.option_2 )
                                                                          .mapMCQuestionToContactField( MCcontactFiled.ROLE )
                                                                          .addMCResponseCodeOptions()
                                                                          .updateQuestion();

            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .addLanguage( languages )
                                                      .saveSurvey();
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, MCcontactFiled.ROLE.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
                                              new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator on the "
                                                            + languages[i] + " page",
                                                      "Default multiple choice question Mapping Indicator on the "
                                                              + languages[i] + " page",
                                                      "Added by button multiple choice quesiton Mapping Indicator on the "
                                                              + languages[i] + " page" },
                                              true );
            }

            send.goToSurveyPage().msdSurvey.openSurvey( surveyName );

            for( int i = 0; i < languages.length; i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] );
                send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, MCcontactFiled.ROLE.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, MCcontactFiled.GENDER.name ),
                                                      element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, MCcontactFiled.COLORGLOBAL.name ) },
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests"})
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( defaultFTQtxt )
                                          .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( defaultFTQtxt, "" ) },
                                          new String[]{ "Default free text question Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                                element.ftQId )
                                                                           .enterQuestion( addedFTQbyButton )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyButton, "" ) },
                                          new String[]{ "Added by button free text quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addDragAndDropQuestionType( "Free Text" )
                                                                           .enterQuestion( addedFTQbyDragDrop )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getFreeTextQuestionMappingIndicator( addedFTQbyDragDrop, "" ) },
                                          new String[]{ "Added by drag-and-drop free text question Mapping Indicator" },
                                          false );

            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] );
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editQuestionType( "A multiple choice question" )
                                                                                                                      .enterQuestion( defaultMCQtxt )
                                                                                                                      .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( defaultMCQtxt, "" ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyButton,
                                                                                                     QuestionStatus.NEW )
                                                                           .fillinMCQAnswers( MCcontactFiled.COLORGLOBAL.option_1,
                                                                                              MCcontactFiled.COLORGLOBAL.option_2 )
                                                                           .updateQuestion();
            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyButton, "" ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addOrUpdateMCQuestionBy( addedMCQbyDragDrop,
                                                                                                     QuestionStatus.DRAGANDDROP )
                                                                           .fillinMCQAnswers( MCcontactFiled.ROLE.option_1,
                                                                                              MCcontactFiled.ROLE.option_2 )
                                                                           .updateQuestion();

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( addedMCQbyDragDrop, "" ) },
                                          new String[]{ "Added by drag-and-drop multiple choice question Mapping Indicator" },
                                          false )
                .goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] );
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
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

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addContentQuestion( false, addedCQbyButton );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by button content quesiton Mapping Indicator" },
                                          false ).msdSurvey.dynamicQuestion.addContentQuestion( true,
                                                                                                addedCQbyDragDrop );
            send.verifyDisplayedElements( new String[]{ element.getContentQuesiton( addedCQbyDragDrop ),
                                                  element.getContentQuesiton( addedCQbyButton ) },
                                          new String[]{ "Added by drag-and-drop content question Mapping Indicator",
                                                  "Added by button content quesiton Mapping Indicator" },
                                          false );

            send.goToSurveyTab( "Settings" ).msdSurvey.goToSurveySettingsTab( "Languages" )
                                                      .addLanguage( languages );
            send.goToSurveyTab( "Questions" );

            for( int i = 0; i < languages.length; i++ ) {

                send.msdSurvey.goToSurveyLanguagePage( languages[i] );
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

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayWarningMessageDuplicatedFreeTextQuestionMappingsSamePage()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated free text question mappings at the same page" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .enterQuestion( "Gender" )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion();
            send.msdSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyDisplayWarningMessageDuplicatedFreeTextQuestionMappingsDifferentPages()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated free text question mappings at different pages" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .enterQuestion( "Gender" )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                            element.ftQId )
                                                                       .enterQuestion( "Email Duplicated" )
                                                                       .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                       .updateQuestion();
            send.msdSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayWarningMessageDuplicatedMultipleChoiceQuestionMappingsSamePage()
                                                                                                   throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated multiple choice question mappings at the same page" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .enterQuestion( "Gender" )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .updateQuestion()
                                          .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();
            send.msdSurvey.saveSurvey();
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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyDisplayWarningMessageDuplicatedMultipleChoiceQuestionMappingsDifferentPages()
                                                                                                         throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that a warning message is successfully displayed when there are duplicated multiple choice question mappings at different pages" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .enterQuestion( "Gender" )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                                 QuestionStatus.NEW )
                                                                       .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                          MCcontactFiled.GENDER.option_2 )
                                                                       .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                       .addMCResponseCodeOptions()
                                                                       .updateQuestion();

            send.msdSurvey.saveSurvey();
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
    public void successfullyCreateSurveyWithDuplicatedQuesitonMappingsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to create a survey with duplicated question mappings on the same page" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 );
            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( "Email" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Last Name" )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .enterQuestion( "Gender" )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( "Email Duplicated" )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addOrUpdateMCQuestionBy( "Gender Duplicated", QuestionStatus.NEW )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            send.msdSurvey.saveSurvey().handleDuplicateMappingsDialog( true );
            send.goToSurveyPage().msdSurvey.openSurvey( surveyName );

            Thread.sleep( 3000 );
            // TODO: verification steps to check survey mappings are retained have to be added

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void successfullyCreateSurveyWithDuplicatedQuesitonMappingsDifferentPage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that user is able to create a survey with duplicated question mappings on the different pages" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editQuestionType( "A free text question" )
                                                                                                                      .enterQuestion( "Email" )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .addNewQuestionType( "Free Text",
                                                                                                                                           element.ftQId )
                                                                                                                      .enterQuestion( "Last Name" )
                                                                                                                      .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                                                                      .updateQuestion()
                                                                                                                      .editQuestionType( "A multiple choice question" )
                                                                                                                      .enterQuestion( "Gender" )
                                                                                                                      .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                                                                      .deleteMcQuestionAnswers( 2 )
                                                                                                                      .updateQuestion();

            send.msdSurvey.goToPageInSurvey( "Page 2" ).dynamicQuestion.addNewQuestionType( "Free Text",
                                                                                            element.ftQId )
                                                                       .enterQuestion( "Email Duplicated" )
                                                                       .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                       .updateQuestion()
                                                                       .addOrUpdateMCQuestionBy( "Gender Duplicated",
                                                                                                 QuestionStatus.NEW )
                                                                       .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                                                          MCcontactFiled.GENDER.option_2 )
                                                                       .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                                                       .addMCResponseCodeOptions()
                                                                       .updateQuestion();

            send.msdSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );
            send.msdSurvey.saveSurvey().handleDuplicateMappingsDialog( true );
            send.goToSurveyPage().msdSurvey.openSurvey( surveyName );

            Thread.sleep( 3000 );
            // TODO: verification steps to check survey mappings are retained have to be added

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-creation", "all-tests", "key-tests"})
    public void successfullyKeppAllSurveyMappingsAfterMaintenanceJobExecution() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "MaintenanceJobTestSurvey";

        log.startTest( "Verify that newly created survey keeps its mappings after the execution of the Maintenance Job" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .addFTQAndMapItToLastName( false )
                                                                                                                      .addMCQAndMapItToMartialStatus()
                                                                                                                      .addMCQAndMapItToRole()
                                                                                                                      .editMCQAndMapItToGender();
            send.msdSurvey.saveSurvey();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
