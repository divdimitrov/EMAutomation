/**
 * Test Cases for Survey Creation Page with Leads Disabled
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
import concep.selenium.send.SendEnum.Theme;

public class SurveyCreationWithLeadsDisabled extends BaseSFDC {

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    private void createConnectionWithLeadsOffAndContactEntity(
                                                               @Optional("config/firefox.SFDC") String configLocation )
                                                                                                                       throws Exception {

        driver.init( configLocation );
        sfdcSendUser = driver.config.getProperty( "sfdcSendUser" );
        sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
        driver.url = driver.config.getProperty( "url" );
        loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", false );
        driver.stopSelenium();

    }

    /**
     * 
     * Verify that survey can not be created when email is not mapped and click 'Save and Continue to Theme' button
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveAndContinueButton()
                                                                                              throws Exception {

        log.startTest( "LeadDisable: Verify that survey can not be created when Email is not mapped and you click Save and Continue to Theme button" );
        unsuccessfullyCreateSurveyWhenSaving( false );
        log.endStep();
    }

    /**
     * 
     * Verify that survey can not be created when email is mapped and you click Save button
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void unsuccessfullyCreatedSurveyWhenEmailIsNotMappedAndClickSaveButton() throws Exception {

        log.startTest( "LeadDisable: Verify that survey can not be created when Email is not mapped and you click Save button" );
        unsuccessfullyCreateSurveyWhenSaving( true );
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests" })
    public void successfullyDisplayedSurveyTypeAndCampaignDropdownsWhenCheckLogResponses() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MissingMappingOfLeadAnswers";

        log.startTest( "LeadDisable: Verify that Survey Type and Campaign dropdown menus are successfully displayed when Log Responses are checked" );
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

        log.startTest( "LeadDisable: Verify that Survey Type and Campaign dropdown menus are successfully displayed when Log Responses are checked" );
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

        log.startTest( "LeadDisable: Verify that Survey Type and Campaign dropdown menus have default values 'Please select'" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName );

            log.startStep( "Verify that the 'Log responses in your CRM system' checkbox is displayed" );
            log.endStep( driver.isElementPresent( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']",
                                                  driver.timeOut ) );

            log.startStep( "Check the 'Log responses in your CRM system' checkbox" );
            send.sfdcSurvey.checkLogResponseInCRM();
            log.endStep();

            log.startStep( "Verify that the Survey Type dropdown is displayed" );
            log.endStep( driver.isElementPresent( "//select[@id='surveyTypeDropDown']", driver.timeOut ) );

            log.startStep( "Verify that the Campaign dropdown is dispalyed" );
            log.endStep( driver.isElementPresent( "//select[@id='surveySalesForceCampaigns']", driver.timeOut ) );

            driver.waitForAjaxToComplete( driver.timeOut );

            log.resultStep( "Verify that the Survey Type dropdown has default selected value" );
            log.endStep( driver.isSelected( "//select[@id='surveyTypeDropDown']", "Please select" ) );

            log.resultStep( "Verify that the Campaign has default selected value" );
            log.endStep( driver.isSelected( "//select[@id='surveySalesForceCampaigns']", "Please select" ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
    public void unsuccessfullyUpdatedQuestionDueToMissingMappingOfContactAnswers() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "MissingMappingOfContactAnswers";

        log.startTest( "LeadDisable: Verify that an error message is displayed during survey creation when contact field is set and corresponding answers are not mapped" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType6, sfdcCampaign6 ).sfdcQuestion.addOrUpdateMCQuestionBy( MCcontactFiled.SALUTATION.question,
                                                                                                                                              QuestionStatus.EDIT )
                                                                                                                    .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                                                                       MCcontactFiled.SALUTATION.option_1 );
            send.sfdcSurvey.sfdcQuestion.mapQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                    element.sfdcContactFieldMCid );
            send.sfdcSurvey.sfdcQuestion.addMCQStatusOptions().updateQuestion();

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

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyVerifySurveyDefaultDropDowns() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "VerifyDefaultDropDowns";

        log.startTest( "LeadDisable: Verify that survey drop downs have default value Please select" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editQuestionType( "A free text question" );

            log.resultStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Please select" ) );

            send.sfdcSurvey.sfdcQuestion.updateQuestion()

            // Verify the default multiple choice question
                                        .editQuestionType( "A multiple choice question" );

            log.startStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span", "Please select" ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion().

            // Add new free text question and verify the drop downs
                                        addNewQuestionType( "Free text", element.ftQId );

            log.startStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Please select" ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion();

            // Add new multiple choice question and verify the drop downs
            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Multiple choice", element.mcQId );

            log.startStep( "Verify the Contact Field drop down" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span", "Please select" ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyUpdateAndVerifySurveyMappings() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "UpdateAndVerifySurveyMappings";
        String statusOption1Updated = "Sent";
        String statusOption2Updated = "Responded";

        log.startTest( "LeadDisable: Verify that survey mappings can be successfully updated" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( false )
                                                                                                                    .addFTQAndMapItToLastName( false );

            // Save the survey, select a theme and deploy
            send.sfdcSurvey.saveAndSelectTheme( Theme.WATER.type );

            // Go Back to the Survey Page
            log.startStep( "Go Back to the Survey Page" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the check the 'Log responses in your CRM system' checkbox is present" );
            log.endStep( verifyLogResponseInCRM() );

            //Update Email Question
            send.sfdcSurvey.sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapQuestionToContactField( FTcontactField.LASTNAME.conntactFiled,
                                                                    element.sfdcContactFieldFT )
                                        .updateQuestion()

                                        //Update Last Name Question
                                        .editQuestionType( FTcontactField.LASTNAME.question )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapQuestionToContactField( FTcontactField.EMAIL.conntactFiled,
                                                                    element.sfdcContactFieldFTid )
                                        .updateQuestion()

                                        //Update Multiple Choice Question
                                        .editQuestionType( MCcontactFiled.LEADSOURCE.question )
                                        .enterQuestion( MCcontactFiled.SALUTATION.question )
                                        .mapQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                    element.sfdcContactFieldMC )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .updateQuestion()

                                        //Verify the Last Name Question
                                        .editQuestionType( FTcontactField.LASTNAME.question );

            log.resultStep( "Verify that the Last Name drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Last Name" ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion()

            //Verify the email question
                                        .editQuestionType( FTcontactField.EMAIL.question );

            log.resultStep( "Verify that the 'Email' drop down is selected for contact" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span", "Email" ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion()

            //Verify the multiple choice question
                                        .editQuestionType( MCcontactFiled.SALUTATION.question );

            log.resultStep( "Verify that the 'Salutation' contact field option is selected" );
            log.endStep( driver.isValueSelected( "#qTypemcSalesforceContactsField span",
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
                                            MCcontactFiled.SALUTATION.option_2 ) );

            log.resultStep( "Verify the 2nd contact drop down" );
            log.endStep( driver.isSelected( "//select[@id='answerContact-54119']",
                                            MCcontactFiled.SALUTATION.option_2 ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyRetainingFieldMapping() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "RetainingFieldMapping";

        log.startTest( "LeadDisable: Verify that contact fields retains their mappings after clicking again in the survey's content" );
        try {
            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false );

            // Save the Survey
            send.sfdcSurvey.saveAndSelectTheme( Theme.WATER.type );

            // Go Back to the Survey
            log.startStep( "Go back to the Survey" );
            driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

            log.startStep( "Verify that the check the 'Log responses in your CRM system' checkbox is present" );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep( verifyLogResponseInCRM() );

            // Verify the 1st free text question 
            send.sfdcSurvey.sfdcQuestion.editQuestionType( FTcontactField.EMAIL.question );

            log.resultStep( "Verify the Contact drop down '" + FTcontactField.EMAIL.question
                            + "' is still retain 'Email' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                 FTcontactField.EMAIL.conntactFiled ) );

            send.sfdcSurvey.sfdcQuestion.cancelQuestion()

            // Verify the 2nd free text question
                                        .editQuestionType( FTcontactField.LASTNAME.question );

            log.resultStep( "Verify the Contact drop down '" + FTcontactField.LASTNAME.question
                            + "' is still retain 'Last Name' value" );
            log.endStep( driver.isValueSelected( "#qTypetextSalesforceContactsField span",
                                                 FTcontactField.LASTNAME.conntactFiled ) );

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

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( false );
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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests" })
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

    @Test(groups = { "survey-creation", "all-tests" })
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
                                          false );
            send.sfdcSurvey.openSurvey( surveyName )
                           .checkLogResponseInCRM()
                           .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                          .addFTQAndMapItToLastName( false )
                                                                                          .editMCquestionAndMapItToSalutation( false );

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
                                                                                                                    .editMCquestionAndMapItToSalutation( false );

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

    @Test(groups = { "survey-creation", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyDisplayMappedSurveyIndicatorWhenSearchForMappedSurvey() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        log.startTest( "Verify that survey mapping indicator is successfully displayed when user is searching for an existing mapped survey" );
        try {

            loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( false );

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
                                                                                              .editMCquestionAndMapItToSalutation( false );
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
                                                                                              .editMCquestionAndMapItToSalutation( false );
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

    @Test(groups = { "survey-creation", "all-tests" })
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
                                                                                              .editMCquestionAndMapItToSalutation( false );
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
                                                                                                                    .editMCquestionAndMapItToSalutation( false );
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
                                                                                                                    .editMCquestionAndMapItToSalutation( false );
            send.sfdcSurvey.saveSurvey();
            send.goToSurveyPage()
                .verifyDisplayedElements( new String[]{ element.getSurveyMappingIndicator( surveyName )
                                                        + element.getMappingIndicatorText( sfdcCampaign1
                                                                                           + " (owned by ali hamadi)" ) },
                                          new String[]{ "Linked To: " + sfdcCampaign1
                                                        + " (owned by ali hamadi)" + " text" },
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
                                        .editMCquestionAndMapItToSalutation( false );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addMCquestionAndMapItToLeadSource( false );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.dragAndDropMCquestionAndMapItToStatus( false );

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
                                        .editMCquestionAndMapItToSalutation( false );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addMCquestionAndMapItToLeadSource( false );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.dragAndDropMCquestionAndMapItToStatus( false );
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
                                        .editMCquestionAndMapItToSalutation( false );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name ) },
                                          new String[]{ "Default multiple choice question Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.addMCquestionAndMapItToLeadSource( false );

            send.verifyDisplayedElements( new String[]{ element.getMultipleChoiceQUesitonMappingIndicator( MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name ) },
                                          new String[]{ "Added by button multiple choice quesiton Mapping Indicator" },
                                          true ).sfdcSurvey.sfdcQuestion.dragAndDropMCquestionAndMapItToStatus( false );

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
                                        .editMCquestionAndMapItToSalutation( false );

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
                                        .editMCquestionAndMapItToSalutation( false )
                                        .addMCquestionAndMapItToSalutation( false );

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
                                        .editMCquestionAndMapItToSalutation( false );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addMCquestionAndMapItToSalutation( false );

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
                                        .editMCquestionAndMapItToSalutation( false )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Email Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addMCquestionAndMapItToSalutation( false );

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
                                        .editMCquestionAndMapItToSalutation( false );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Email Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addMCquestionAndMapItToSalutation( false );

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
    
    @Test(groups = { "survey-creation", "all-tests", "maintenance-job" })
    public void successfullyKeepCreatedSurveyMappingsAfterMaintenanceJobExecution() throws Exception {

    	String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
         
        log.startTest( "Verify that a newly created survey keeps all its mappings after the execution of the Maintenance job" );
        try {

           loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
							            .enterQuestion( FTcontactField.EMAIL.question )
							            .mapFTQuestionToContactField(FTcontactField.EMAIL.conntactFiled)							            
							            .updateQuestion()
							            .addNewQuestionType( "Free Text", element.ftQId )
							            .enterQuestion( FTcontactField.LASTNAME.question )
							            .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
							            .updateQuestion()
							            .addNewQuestionType( "Free Text", element.ftQId )
							            .enterQuestion( FTcontactField.FIRSTNAME.question )
							            .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
							            .updateQuestion()
							            .addOrUpdateMCQuestionBy( MCcontactFiled.SALUTATION.question, QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question, QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                           MCcontactFiled.LEADSOURCE.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
                                                                   MCcontactFiled.LEADSOURCE.option_1,
                                                                   MCcontactFiled.LEADSOURCE.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();
            
            send.sfdcSurvey.saveSurvey();                                        
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();        
    }    
}
