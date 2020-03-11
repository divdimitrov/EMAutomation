package concep.selenium.send;

public class Elements {

    private final String   mappedQuestionIndicator    = "//img[@class='mappedIcon tooltip']";

    public final String    duplicateMappingsDialogTxt = "Two or more questions are mapped to the same field in your CRM. If both questions are answered the response to the most recently added question, which will have a higher ID number, will be used.";

    //Common elements
    protected final String searchInputField           = "//div[@id='searchBarContainer']//input[@id='txtSearch']";
    protected final String searchBtn                  = "//div[@id='searchBarContainer']//a[@id='btnSearch']";
    protected final String recordTools                = "//a[contains(text(), 'Tools')]";
    public String          homePage                   = "//a[@id='nav_home']/span[contains(text(),'Home')]";
    public String          closeButtonDeploySurvey    = "//div[@id='s_DOMWindow-footer']//span[contains(text(),'Close')]";
    public final String    questionIdContainer        = "//p[contains(text(), 'Question ID')]/span[@id='qID']";
    public String          surveyContentoptions       = "//a[@id='surveyDropdown']";

    protected String getContactRecordToolsMenu(
                                                String keyword ) {

        return "//div[@id='contentFrame']//tbody/tr/td/span[contains(text(), '" + keyword
               + "')]/../..//a[contains(text(), 'Tools')]";
    }

    protected String getSurveyRecordToolsMenu(
                                               String keyword ) {

        return "//div[@id='contentFrame']//tbody/tr/td/a[contains(text(), '" + keyword + "')]/../.."
               + recordTools;
    }

    protected String getSurveyToolsMenuFromInside(
                                                   String surveyName ) {

        return "//span[contains(text(), '" + surveyName + "')]/.." + recordTools;
    }

    protected final String editRecordBtn            = "//li/a[contains(text(),'Edit')]";
    protected final String deleteRecordBtn          = "//ul[@id='p_ab_contact']//a[contains(text(),'Delete')]";
    protected final String confirmDeletionBtn       = "//span[contains(text(), 'Delete')]";

    //questions 
    public String          ftQId                    = "radText";
    public String          mcQId                    = "radMc";
    public String          contentQId               = "radContent";
    public String          matrixQId                = "radMatrix";
    public String          fileUploadId             = "radUpload";
    public String          resultsQId               = "radResult";
    public String          addQuestion              = "//a[@id='qAdd']/span[contains(text(), 'Add Question')]";
    public String          updateQuestion           = "//a[@id='qUpdate']";
    public String          cancelQuestion           = "//a[@id='qCancel']";
    public String          mandatoryOpetion         = "//input[@id='qMandatory']";
    public String          deleteQuestion           = "//ul[@id='p_surveys_question']//li[contains(a,'Delete')]/a";
    public String          deleteQuestionCompletion = "//a[@class='s_button']/span[contains(text(),'Yes, Delete')]";
    public String          questionDisplyDropDown   = "//select[@id='qMCDisplay']";
    public String          allowOtherAnswerCheckBox = "//input[@id='qMCOther']";
    public String          allowMultipleAnswers     = "//input[@id='qMCMultiple']";
    public String          questionIframe           = "//div[@class='s_form-elementsRow']/label[contains(text(), 'Question Content')]/..//iframe";
    public String          mCQfirstAnswerTxt        = "//div[@id='qAnswersList']//div[contains(@class, 'qAnswerRow')][1]//input[@type='text']"; // TODO: Needs to be changed after the front-ender removes the duplicated element
    public String          mCQSecondAnswerTxt       = "//div[@id='qAnswersList']//div[contains(@class, 'qAnswerRow')][2]//input[@type='text']"; // TODO: Needs to be changed after the front-ender removes the duplicated element
    public String          searchInputQuestion      = "//span[@class='select2-search select2-search--dropdown']//input";

    public String showQuestionSettings(
                                        String questionId ) {

        return "//form[@id='" + questionId + "']//a[contains(text(),'Show')]";
    }

    public String hideQuestionSettings(
                                        String questionId ) {

        return "//form[@id='" + questionId + "']//a[contains(text(),'Hide')]";
    }

    public String questionRadioButton(
                                       String questionId ) {

        return "//input[@id='" + questionId + "' and @type='radio']";
    }

    public String ftQuestionOptionsButton(
                                           String question ) {

        return "//label[contains(text(), '" + question
               + "')]//..//..//..//a[contains(text(),'Question Settings')]";
    }

    public String mcQuestionOptionsButton(
                                           String question ) {

        return "//div[contains(text(), '" + question
               + "')]//..//..//..//a[contains(text(),'Question Settings')]";
    }

    public String contentQuestionOptionsButton(
                                                String question ) {

        return "//div[contains(text(), '" + question + "')]//..//a[contains(text(),'Question Settings')]";
    }

    //login
    public String userName                    = "//input[@id='txtUsername']";
    public String password                    = "//input[@id='txtPassword']";
    public String logIn                       = "//a[@id='btnLogin']";

    //logOut
    public String logOut                      = "//a[contains(text(),'Logout')]";

    //surveys
    public String surveyPage                  = "//a[@href='/surveys.aspx']";
    public String createSurvey                = "//a[@href ='/survey_01.aspx']";
    public String createSurveyTextName        = "//div[contains(@class,'ui-dialog')]//input[@class='s_form-textInput' and @type='text']";
    public String createSurveyCompletion      = "//a[1]/span[contains(text(), 'Create Survey')]";
    public String saveSurvey                  = "//div[@id='wholePageContainer']//a[@class='s_button surveySave']/span[text()='Save']";
    public String saveAndContinue             = "//div[@id='s_content-innerContainer']//span[contains(text(), 'Save and Continue to Theme »')]";
    public String deploySurvey                = "//a/span[contains(text(), 'View Survey')]";
    public String surveyNext                  = "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]";
    public String addPage                     = "//span[contains(text(),'Add Page')]";
    public String SurveyPageNameText          = "//div/label[contains(text(),'Page Name')]/../input[@type='text']";
    public String addPageCompletion           = "//a[@class='s_button']/span[contains(text(),'Add Page')]";
    public String surveySubmissionPage        = "//a[@id='btnNext']/span";
    public String selectThemeLabel            = "//h3[contains(text(), 'Select a theme.')]";
    public String saveAndContinueToDeploy     = "//a/span[contains(text(), 'Save and Continue to Deploy »')]";
    public String deleteSurvey                = "//ul[@id='p_surveys_survey']//a[contains(text(),'Delete')]";
    public String deleteTemplate              = "//ul[@id='p_templates_template']//a[contains(text(),'Delete')]";
    public String copySurvey                  = "//ul[@id='p_surveys_survey']//a[./text()='Copy']";
    public String moreToolsSurvey             = "//ul[@id='p_surveys_survey']//a[./text()='More']";
    public String saveAsTemplateTab           = "//ul[@id='typesMenu']/li//span[contains(text(), 'Save as Template')]";
    public String templateName                = "//input[@id='txtTemplateName']";
    public String saveAsTemplateBtn           = "//a[@class='s_button']/span[contains(text(), 'Save as Template')]";
    public String copyBtnDialogMsg            = "//div[@class='ui-dialog-buttonset']//a/span[./text()='Copy']";
    public String copyAndEditBtnDialogMsg     = "//div[@class='ui-dialog-buttonset']//a/span[./text()='Copy and Edit']";
    public String okButtonDialogMsg           = "//div[@class='ui-dialog-buttonset']//a/span[./text()='OK']";
    public String msdLogResponsesCheckbox     = "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']";
    public String msdSurveyTypeDropdown       = "//span[@id='select2-surveyTypeDropDown-container']";
    public String msdCampaignDropdown         = "//span[@id='select2-surveyDynamicsCampaigns-container']";
    public String pageDropdown         		  = "//a[@id='pageOptions']";
    public String deleteOptionForpage         = "//li[contains(@class, 's_form-dropdownOptionsSectionTop')]/a";
    public String confirmDeletionOfpage       = "//div[@class='ui-dialog-buttonpane ui-widget-content ui-helper-clearfix']//a//span[./text()='Yes, Delete']";
    public String confirmDeletionOfSurvey     = "//input[contains (@id, 'confirm')]";

    //campaigns
    public String campaignPage                = "//a[@id='nav_campaign']";
    public String createCampaignButton        = "//a[@class='s_button']//span[contains(text(), 'Create a Campaign')]";
    public String campaignNameField           = "//div[contains(@class,'ui-dialog')]//label[contains(text(), 'Campaign Title')]/../input[@class='s_form-textInput']";
    public String createButton                = "//div[@class='ui-dialog-buttonset']//span[contains(text(), 'Create')]";
    public String campaignSubjectField        = "//div[@class='s_form-3colsMid']//input[@id='txtSubject']";
    public String campaignSaveButton          = "//a[@class='s_button cButtonS']//span[contains(text(), 'Save')]";
    public String saveAndContinueToRecipients = "//a[@class='s_button']//span[contains(text(), 'Save and Continue to Recipients')]";
    public String continueToPreviewAndSend    = "//div[@id='s_content-innerContainer']//span[contains(text(), 'Continue to Preview and Send »')]";
    public String campaignSendBtn             = "//a[@id='btnSend']";
    public String sendConfirmBtn              = "//a[@class='s_button']//span[contains(text(), 'Yes, Send Now')]";
    public String campaignBody                = "//table[@class='cbT-block-inner']//..//div[contains(text(), 'This is dummy text')]";
    public String insertLinkBtn               = "//a[starts-with(@id, 'cke') and span[contains(text(),'Link')]]";
    public String insertSurveyTab             = "//ul[@id='s_nav-tabs']//li[@fetch='survey']//span";
    public String searchSurveyArrow           = "//a[@id='autoCompleteAll']";
    public String createSurveyLinkBtn         = "//a[@id='createLinkbtnAddSurvey']/span";
    public String campaignProfileDropdown     = "//div[@id='wholePageContainer']//dt[@id='SenderProfileSelect']";
    public String campaignFromField           = "//div[@id='wholePageContainer']//input[@id='txtSenderFromDomain']";
    public String regardingDropdown           = "//div[@id='regardingDropdownContainer']//span[@id='select2-regardingdropdown-container']";
    public String regardDropdownDefaultValue  = "Please select";
    public String campaignSentSuccessMsg      = "//div[contains(@class, 'ui-state-success')]//h4[contains(text(), 'Success')]";
    public String contentTabButton            = "//a[contains(text(), 'Content')]";
    public String backToContentBtn            = "//a[@class='s_button']/span[contains(text(), 'Back to Content')]";
    public String backToRecipientBtn          = "//a[@class='s_button']/span[contains(text(), 'Back to Recipients')]";
    public String deleteCampaign              = "//ul[@id='p_campaigns_campaign']//a[contains(text(),'Delete')]";

    public String getCampaignPageSubTab(
                                         String campaignTabName ) {

        return "//div[contains(@class, 's_tabs-container')]//span[contains(text(), '" + campaignTabName
               + "')]";
    }

    public String getCampaignHeadingName(
                                          String campaignName ) {

        return "//h2/span[@id='lblTitle'][contains(text(), '" + campaignName + "')]";
    }

    public String getRegardingDropdownSelectedValue(
                                                     String regardingValue ) {

        return "//span[@id='select2-regardingdropdown-container'][contains(text(), '" + regardingValue
               + "')]";
    }

    public String getCampaignRecord(
                                     String campaignTitle ) {

        return "//div[@id='contentFrame']//td/a[contains(text(), '" + campaignTitle + "')]";
    }

    public String getCampaignRecordCheckbox(
                                             String campaignTitle ) {

        return getCampaignRecord( campaignTitle ) + "/../..//a[contains(@class, 'itemSelect')]";
    }

    protected String getCampaignRecordToolsMenu(
                                                 String keyword ) {

        return "//div[@id='contentFrame']//tbody/tr/td/a[contains(text(), '" + keyword + "')]/../.."
               + recordTools;
    }

    public String getContactRecord(
                                    String email ) {

        return "//span[contains(text(), '" + email + "')]";
    }
    
    public String getContactRecordCheckbox(
            String email ) {

    	return "//span[contains(text(), '" + email + "')]/../..//a[@type='add']";
	}

    public String getGroupRecord(
                                  String groupName ) {

        return "//a[contains(text(), '" + groupName + "')]";
    }

    public String getContactGroupCheckbox(
                                           String groupName ) {

        return "//td[contains(text(), '" + groupName + "')]/../td[@class='s_table-checkbox']";
    }

    public String getGroupCheckbox(
                                    String groupName ) {

        return "//a[contains(text(), '" + groupName + "')]/../..//td[@class='s_table-checkbox']";
    }

    public String getSurveyInsideCampaign(
                                           String surveyName ) {

        return "//a[contains(text(), '" + surveyName + "')]";
    }

    public String getCampaignTemplate(
                                       String templateName ) {

        return "//div[@id='listTemplates']//p[contains(text(), '" + templateName
               + "')]/../a[@class='s_thumbnail-thumbLink']";
    }

    public String getSurveyTemplate(
                                     String templateName ) {

        return "//div[@id='listTemplates']//p[contains(text(), '" + templateName
               + "')]/../a[@class='s_thumbnail-thumbLink']";
    }

    public String getSurveyFTQcontentText(
                                           String question ) {

        return "//div[@id='surveyPageContainer']//div[@class='sQT']/label[contains(text(), '" + question
               + "')]";
    }

    public String getSurveyMCQcontentText(
                                           String question ) {

        return "//div[@id='surveyPageContainer']//div[@class='sQT']/div[contains(text(), '" + question
               + "')]";
    }

    public String getSurveyMCQAnswerOption(
                                            String question,
                                            String answer ) {

        return getSurveyMCQcontentText( question ) + "/../.." + "//div[@class='sA']/label[contains(text(), '"
               + answer + "')]";
    }

    // Add Subject dialog
    public String addLaterDialogBtn                 = "//div[@role='dialog']//a[contains(@class,'s_button')]/span[contains(text(), 'Add Later')]";
    public String addSubjectDialogBtn               = "//div[@role='dialog']//a[contains(@class,'s_button')]/span[contains(text(), 'Add Subject')]";
    public String subjectDialogInputField           = "//label[contains(text(), 'Subject')]/../input";

    // Discard Changes dialog
    public String discardChangesDialogBtn           = "//div[@role='dialog']//span[contains(text(), 'Yes, Discard')]";
    public String cancelDialogBtn                   = "//div[@role='dialog']//span[contains(text(), 'Cancel')]";

    // Duplicate Mappings dialog
    public String duplicateMappingsDialogMsg        = "//div[@id='duplicatefieldserrorinform']//p[text()[contains(., '"
                                                      + duplicateMappingsDialogTxt + "')]]";
    public String reviewDuplicatedMappingsDialogBtn = "//div[@id='duplicatefieldserrorinform']//span[contains(text(), 'Review')]";
    public String saveDuplicatedMappingsDialogBtn   = "//div[@id='duplicatefieldserrorinform']//span[contains(text(), 'Save')]";

    public String surveyLangauge(
                                  String language ) {

        return "//ul[@id='langSelection']//a[text()='" + language + "']";
    }

    public String surveyTheme(
                               String theme ) {

        return "//ul[@id='themeList']//a[contains(text(), '" + theme + "')]";
    }

    public String pageInSurvey(
                                String pageName ) {

        return "//span[contains(text(), '" + pageName + "')]";
    }

    public String surveysubmissionLanguage(
                                            String language ) {

        return "//div[@id='surveyInnerContainer']//a[contains(text(),'" + language + "')]";
    }

    public String surveySubPage(
                                 String surveySubPage ) {

        return "//ul[@id='typesMenu']//span[contains(text(), '" + surveySubPage + "')]";
    }

    public String surveySettingsSubPage(
                                         String surveySettingsSubPage ) {

        return "//div[@id='settingsNav']//span[contains(text(), '" + surveySettingsSubPage + "')]";
    }

    public String freeTextSurveyAnswers(
                                         String question ) {

        return "//div[@id='surveyPageContainer']//label[contains(text(),'" + question + "')]";
    }

    public String multipleChoiceSurveyAnswers(
                                               String question,
                                               String answer ) {

        return "//div[@id='surveyPageContainer']//div[contains(text(),'" + question
               + "')]//../..//label[contains(text(),'" + answer + "')]";
    }

    public String dropDownMenuSurveyAnswer(
                                            String question ) {

        return "//div[@id='surveyPageContainer']//label[contains(text(),'" + question + "')]//..//..//select";
    }

    public String getSurveyMappingIndicator(
                                             String surveyName ) {

        return "//div[@id='contentFrame']//tbody//td/a[contains(text(), '" + surveyName
               + "')]/../..//img[@class='mappedIcon tooltip']";
    }

    public String getMappingIndicatorText(
                                           String marketingListName ) {

        if( marketingListName == "" ) {

            return "[@title='No linked marketing list']";

        } else {

            return "[@title='Linked to: " + marketingListName + "']";

        }
    }

    public String getSortingCriteria(
                                      String criteriaName ) {

        return "//div[@id='contentFrame']//table//th/span[contains(text(), '" + criteriaName + "')]";
    }

    public String getFreeTextQuestionMappingIndicator(
                                                       String questionContent,
                                                       String fieldMapping ) {

        if( fieldMapping == "" ) {

            return "//label[contains(text(), '" + questionContent + "')]/../../.." + mappedQuestionIndicator;

        } else {

            return "//label[contains(text(), '" + questionContent + "')]/../../.." + mappedQuestionIndicator
                   + "[@title='Linked to: " + fieldMapping + "']";
        }
    }

    public String getMultipleChoiceQUesitonMappingIndicator(
                                                             String questionContent,
                                                             String fieldMapping ) {

        if( fieldMapping == "" ) {

            return "//div[contains(text(), '" + questionContent + "')]/../../.." + mappedQuestionIndicator;

        } else {

            return "//div[contains(text(), '" + questionContent + "')]/../../.." + mappedQuestionIndicator
                   + "[@title='Linked to: " + fieldMapping + "']";
        }
    }

    public String getContentQuesiton(
                                      String questionContent ) {

        return "//div[contains(@class,'sContent')]//div[contains(text(), '" + questionContent + "')]/.."
               + mappedQuestionIndicator;
    }

    // Survey list table paging    
    public String getPagingButton(
                                   String buttonName ) {

        return "//div[@id='contentFrame']//tfoot//a[contains(text(), '" + buttonName + "')]";
    }

    //admin page
    public String adminPage                  = "//div[@id='s_mastheader-userLinks']//a[contains(text(),'Admin')]";
    public String usersPage                  = "//ul[@id='typesMenu']//span[contains(text(), 'Users')]";
    public String addUserBtn                 = "//div[@id='usersNav']//span[contains(text(), 'Add User')]";
    public String addAndCloseBtn             = "//div[contains(@class, 's_button-pane')]//span[contains(text(), 'Add and Close')]";

    public String usernameAdd                = "//input[@id='txtUsername']";
    public String passwordAdd                = "//input[@id='txtNewPass1']";
    public String rePasswordAdd              = "//input[@id='txtNewPass2']";
    public String firstNameAdd               = "//input[@id='txtUserFName']";
    public String lastNameAdd                = "//input[@id='txtUserLName']";
    public String emailAdd                   = "//input[@id='txtUserEmail']";
    public String roleAdd                    = "//select[@id='ddlUserRole']";

    //Settings and connection
    public String connectionOption           = "//div[@id='s_tabs-content-general']//a[contains(text(), 'Create one now?')]";
    public String connectionWindow           = "//ul[@id='ACconnntetionsList' or @id='connntetionsList']//a[@href='#']";
    public String connectionLoginForm        = "//div[@id='DOMWindow']//div[@class='loginForm']/p";
    public String connectionTab              = "//a/span[contains(text(), 'Connections')]";
    public String settigsPage                = "//a[@href ='/settings.aspx']";
    public String connectionSecondWindow     = "//div[@class='loginForm']/p";
    public String connectionNameText         = "//input[@id='acConnectionName']";
    public String connectionAddress          = "//label[contains(text(), 'Address')]/../input[@disabled='disabled']";
    public String connectionUserNameText     = "//input[@id='acUsername' or @id='ecUsername']";
    public String connectionPasswordText     = "//input[@id='acPassword' or @id='ecPassword' and @type='password']";
    public String createConnectionButton     = "//div[@class='s_button-pane s_button-pane2']//a[1]/span[contains(text(), 'Create') or contains(text(),'Connect')]";
    public String connectionLabel            = "//div[@id='existingConnections']/h3[contains(text(), 'Connections')]";
    public String requiredFieldValidationMsg = "//span[contains(text(), 'This field is required')]";
    public String connectionLoginErrorTxt    = "Login failed. Please check your information and try again.";
    public String connectionLoginErrorMsg    = "//div[@class='Error'][contains(text(), '"
                                               + connectionLoginErrorTxt + "')]";

    public String getConnectionRequiredFieldValidationMsg(
                                                           String inputLabel ) {

        return "//label[contains(text(), '" + inputLabel
               + "')]/../span[contains(text(), 'This field is required')]";
    }

    //ia connection page
    public String connectionSaveAndCloseButton = "//a/span[contains(text(), 'Save and close')]";

    public String iaSurveyFoldersCheckBox(
                                           String IAFolder ) {

        return "//span[contains(text(), '" + IAFolder + "')]";
    }

    //IA specific

    //IA Platform v2 search drop-downs
    public String interactionFieldMC              = "//span[@id='select2-qTypemcInteractionField-container']";
    public String interactionFieldFT              = "//span[@id='select2-qTypetextInteractionField-container']";

    //IA Platform v1 search drop-downs
    // public String interactionFieldMC              = "//span[@id='select2-qTypemcInteractionFieldDropdown-container']";
    // public String interactionFieldFT              = "//span[@id='select2-qTypetextInteractionFieldDropdwown-container']";

    public String iaEventFolderDropDown_2         = "//span[@id='select2-surveyInteractionFolder-container']";
    public String crmSurveyTypeDropDown           = "//span[@id='select2-surveyTypeDropDown-container']";

    public String campaignRegardingDropdown       = "//span[@id='select2-regardingdropdown-container']";
    public String campaignRegardingDropdownSearch = "//input[@class='select2-search__field']";
    
    public String enableRegardingFolderCheckbox = "//label[text()='Enable']/..//input[@type='checkbox']";

    public String getIAfieldQuestionMapping(
                                             boolean isFreeText,
                                             String questionContent,
                                             String mapping ) {

        if( isFreeText ) {

            return interactionFieldFT + "[contains(text(), '" + mapping + "')]";

        } else {

            return interactionFieldMC + "[contains(text(), '" + mapping + "')]";
        }
    }

    //Dynamic4 specific
    public String          contactFieldsMC                   = "//span[@id='select2-qTypemcDynamicsContactsFieldDropdown-container']";
    public String          contactFieldsFT                   = "//span[@id='select2-qTypetextDynamicsContactsFieldDropdown-container']";
    public String          msdCampaignDropDown               = "//span[@id='select2-surveyDynamicsCampaigns-container']";
    public String          crmDropDownSearch                 = "//span[@class='select2-search select2-search--dropdown']/input";
    public String          msdContactFieldMCid               = "qTypemcDynamicsContactsField";
    public String          msdContactFieldFTid               = "qTypetextDynamicsContactsField";

    //SFDC specific 
    public String          sfdcContactFieldMC                = "//span[@id='select2-qTypemcSalesforceContactsFieldDropdown-container']";
    public String          sfdcLeadFieldMC                   = "//span[@id='select2-qTypemcSalesforceLeadFieldDropdown-container']";
    public String          sfdcContactFieldFT                = "//span[@id='select2-qTypetextSalesforceContactsFieldDropdown-container']";
    public String          sfdcLeadFieldFT                   = "//span[@id='select2-qTypetextSalesforceLeadFieldDropdown-container']";
    public String          sfdcContactFieldFTid              = "qTypetextSalesforceContactsField";
    public String          sfdcLeadFieldFTid                 = "qTypetextSalesforceLeadField";
    public String          sfdcContactFieldMCid              = "qTypemcSalesforceContactsField";
    public String          sfdcLeadFieldMCid                 = "qTypemcSalesforceLeadField";
    public String          sfdcSurveyDropDownMenu            = "//span[@id='select2-surveyTypeDropDown-container']";
    public String          sfdcCampaignDropDownMenu          = "//span[@id='select2-surveySalesForceCampaigns-container']";

    //SFDC connection Page
    public String          sfdcConnectionSurveyEnableBox     = "//label[@id='surveysEnabled']/input";
    public String          sfdcConnectionLeadEnableBox       = "//label[@id='AreLeadsEnabled']/input";
    public String          sfdcConnectionSelectContactEntity = "//label[contains(text(), 'Select which contact entity to use:')]/select";
    public String          sfdcConnectionActivCampaignsBox   = "//label[@id='onlyActiveCampaigns']/input";

    //Dynamic4 connection Page
    public String          msd4ConnectionSurveyEnableBox     = "//div[@id='dynamicsFeaturesListsContainer']//input[@type='checkbox']";

    //Contact page
    protected final String contactExistanceDialogMsg         = "//div[contains(@class,'ui-dialog')]//p[contains(text(), 'A contact with the email address you entered already exists or has previously existed in your records.')]";
    protected final String contactsTab                       = "//div[@id='s_nav-main']//a/span[contains(text(), 'Contacts')]";

    protected String getContactsSubPage(
                                         String subTabName ) {

        return "//ul[@id='typesMenu']//a/span[contains(text(), '" + subTabName + "')]";
    }

    public String getContactTableRecord(
                                         String keyword ) {

        return "//div[@id='contentFrame']//tbody/tr/td/span[contains(text(), '" + keyword + "')]/../..";
    }

    protected String getGroupTableRecord(
                                          String keyword ) {

        return "//div[@id='contentFrame']//tbody/tr/td/a[contains(text(), '" + keyword + "')]/../..";
    }

    protected String getGroupLink(
                                   String keyword ) {

        return "//div[@id='contentFrame']//tbody/tr/td/a[contains(text(), '" + keyword + "')]";
    }

    protected String getContactDetailsType(
                                            String detailsType ) {

        return "//ul[@id='s_nav-tabs']//span[contains(text(), '" + detailsType + "')]";
    }

    public String       addContactButton                       = "//div[@class='s_tabs-container s_clear']//a[@class='inlineWindow' and contains(text(), 'Add Contact')]";
    public String       addGroupHyperlink                      = "//a[contains(text(), 'Add Group')]";
    public String       groupNameField                         = "//div[contains(@class,'ui-dialog')]//label[contains(text(), 'Group Name')]/../input[@class='s_form-textInput']";
    public String       addGroupButton                         = "//a[@class='s_button']//span[contains(text(), 'Add Group')]";
    public String       saveAndCloseButton                     = "//a[@id='btnAddClose']";
    public String       updateAndCloseButton                   = "//a[@id='btnUpdate']";

    //Contact details - Name and Email
    public final String firstName                              = "//input[@id='txtFirstname']";
    public final String lastName                               = "//input[@id='txtLastname']";
    public final String email                                  = "//input[@id='txtEmail']";

    //Contact details - Other Fields
    public final String jobTitle                               = "//input[@id='txtJobTitle']";
    public final String title                                  = "//select[@id='ddlTitle']";
    public final String country                                = "//input[@id='txtCountry']";
    public final String externalId                             = "//input[@id='txtExternal']";

    //Contact details - Custom Fields
    public final String grade                                  = "//div/label[contains(text(), 'grade')]/../input";
    public final String function                               = "//div/label[contains(text(), 'function')]/../input";
    public final String lineOfBusiness                         = "//div/label[contains(text(), 'lineofbusiness')]/../input";
    public final String sector                                 = "//div/label[contains(text(), 'sector')]/../input";
    public final String subSector                              = "//div/label[contains(text(), 'subsector')]/../input";
    public final String passwordCustomField                    = "//div/label[contains(text(), 'password')]/../input";

    //Contact details - Groups
    public final String contactGroup                           = "//ul[@id='s_nav-tabs']//span[contains(text(), 'Groups')]";
    public final String contactOtherFields                     = "//ul[@id='s_nav-tabs']//span[contains(text(), 'Other Fields')]";

    // Connection Wizard IA Connector v1.1
    public final String connectionWizardLoginPageLabelTxt      = "Please fill in the login information of your connection.";
    public final String connectionWizardLoginPageLbl           = "//div[contains(@class, 'modalContent ')]//p[contains(text(), '"
                                                                 + connectionWizardLoginPageLabelTxt + "')]";
    public final String connectionWizardConnectBtn             = "//div[contains(@class,'modalContent')]//span[contains(text(), 'Connect')]";
    public final String connectionWizardBackBtn                = "//div[contains(@class,'modalContent')]//span[contains(text(), 'Back')]";
    public final String connectionWizardNextBtn                = "//div[@class='modalContent']//span[contains(text(), 'Next')]";
    public final String connectionWizardLoginPageCloseBtn      = "//div[@id='DOMWindow']//span[contains(text(), 'close')]";
    public final String connectionWizardCancelBtn              = "//div[@class='modalContent' or @class='modalInnerPanel']//span[contains(text(), 'Cancel')]";
    public final String connectionWizardSaveBtn                = "//div[@class='modalContent' or @class='modalInnerPanel']//span[contains(text(), 'Save')]";
    public final String connectionWizardSaveAndCloseActivity   = "//div[@id='editactivity']//span[contains(text(), 'Save and close')]";
    public final String connectionWizardSaveAndCloseAll        = "//div[@class='modalContent']//span[contains(text(), 'Save and close')]";
    public final String connectionWizardConfigureFoldersBtn    = "//div[@class='modalContent']//span[contains(text(), 'Configure folders')]";
    public final String connectionWizardAddUsersBtn            = "//div[@class='modalContent']//span[contains(text(), 'Add users')]";
    public final String connectionUsersPageLabelTxt            = "Select permissions by adding required accounts to the list below and specifying their functionality.";
    public final String connectionUsersPageLabel               = "//div[@class='modalContent']//p[contains(text(), '"
                                                                 + connectionUsersPageLabelTxt + "')]";
    public final String connectionWizardActivitiesPageLabelTxt = "Select which activities you would like to log in InterAction.";
    public final String connectionWizardActivitiesPageLabel    = "//div[@class='modalContent']//p[contains(text(), '"
                                                                 + connectionWizardActivitiesPageLabelTxt
                                                                 + "')]";
    public final String deleteQuestionErrorLabel 			   = "//p[@id='de_45824']";
    public final String deleteQuestionErrorTxt 			   	   = "Your page needs at least one question";
    
    public final String sendClientAdminTxt            		   = "TestAutomation_QA_IA_Connector_Plat2";
    public final String sendClientAdminLabel	               = "//div[@id='manageUsersInnerContainer']//span[contains(text(), '"
    															 + sendClientAdminTxt + "')]";

    public final String manageUsersContainer                   = "//div[@id='manageUsersContainer']";
    public final String addUsersBtn                            = "//div[@id='manageUsers']//a[@class='s_button right']//span[text()='Add users']";
    public final String addUsersContainer                      = "//div[@id='usersAdd']";
    public final String manageUsersCloseBtn                    = "//div[@id='manageUsers']//span[contains(text(), 'close')]";
    public final String addUsersCloseBtn                       = "//div[@id='usersAdd']//span[contains(text(), 'close')]";
    public final String folderTypesCloseBtn                    = "//div[@id='surveys']//span[contains(text(), 'close')]";
    public final String configureFoldersCloseBtn               = "//div[@id='connectionConfiguration']//span[contains(text(), 'close')]";
    public final String activitiesCloseBtn                     = "//div[@id='activities']//span[contains(text(), 'close')]";
    public final String activitiesContainer                    = "//div[@id='activitiesListsInnerContainer']";
    public final String editActivityContainer                  = "//div[@id='editactivity']";
    public final String removeUserTokenBtn                     = "//span/img[@title='Remove token']";
    public final String injectUserTokenBtn                     = "//span/img[@title='Add user']";                                                                                 // TODO: Needs to be changed by Dido
    public final String mailMergeTagDropdown                   = editActivityContainer
                                                                 + "//label[contains(text(), 'Insert mail-merge tags:')]/..//select";
    public final String editActivityTitle                      = editActivityContainer
                                                                 + "//label[contains(text(), 'Title: ')]/input";
    public final String editActivityDescription                = editActivityContainer
                                                                 + "//label[contains(text(), 'Title: ')]/../textarea";
    public final String linkToCompanyCheckbox                  = editActivityContainer
                                                                 + "//span[contains(text(), 'Link to Company')]/../input[@type='checkbox']";
    public final String editActivityCancelBtn                  = editActivityContainer
                                                                 + "//span[contains(text(), 'Cancel')]";
    public final String editActivitySaveAndCloseBtn            = editActivityContainer
                                                                 + "//span[contains(text(), 'Save and close')]";

    public final String peopleDropdownSearchContainer          = "//span[@id='select2-searchPeople-container']";
    public final String companyDropdownSearchContainer         = "//span[@id='select2-searchCompany-container']";
    public final String sourceFolderDropdownSearchContainer    = "//span[@id='select2-sourceFolder-container']";

    public final String dropdownSearch                         = "//span[@class='select2-search select2-search--dropdown']/input";

    public final String linkToFoldersFirstDropdown             = "//div[@class='right dropdownContainer']//div[2]//span[@class='select2-selection__rendered']";
    public final String linkToFoldersSecondDropdown            = "//div[@class='right dropdownContainer']//div[3]//span[@class='select2-selection__rendered']";
    public final String linkToFoldersThirdDropdown             = "//div[@class='right dropdownContainer']//div[4]//span[@class='select2-selection__rendered']";

    public final String getSelect2DropdownListitem(
                                                    String listItemText ) {

        return "//span[@class='select2-results']//li[contains(text(), '" + listItemText + "')]";
    }

    public final String getLinkToFolderDropdownNChild(
                                                       int childNumber ) {

        return "//div[@class='right dropdownContainer']//div[" + ( childNumber + 1 )
               + "]//span[@class='select2-selection__rendered']";
    }

    public String getUserRecordWithInjectedToken(
                                                  String username ) {

        return manageUsersContainer + "//div[contains(@class, 'modalContent')]/div[1]/a[contains(@class, 's_button right')]" + username + "']";
    }

    protected String getUserRecordRemoveTokenBtn(
                                                  String username ) {

        return getUserRecordWithInjectedToken( username ) + "/../../.." + removeUserTokenBtn;
    }

    public String getUserRecordWithoutInjectedToken(
                                                     String username ) {

        return addUsersContainer + "//tr//span[contains(text(), '" + username + "')]";
    }

    protected String getUserRecordInjectTokenBtn(
                                                  String username ) {

        return getUserRecordWithoutInjectedToken( username ) + "/../../.." + injectUserTokenBtn;
    }

    protected String getUserRecordActivitiesPermissionCheckbox(
                                                                String username ) {

        return getUserRecordWithInjectedToken( username ) + "/../../..//input[@name='activities']";
    }

    protected String getUserRecordSurveysPermissionCheckbox(
                                                             String username ) {

        return getUserRecordWithInjectedToken( username ) + "/../../..//input[@name='surveys']";
    }

    public String getFolderTypeCheckbox(
                                         String folderType ) {

        return "//div[@id='surveysListsInnerContainer']//span[contains(text(), '" + folderType
               + "')]/../input[@type='checkbox']";
    }

    public String getActivityTypeCheckbox(
                                           String activityType ) {

        return activitiesContainer + "//span[contains(text(), '" + activityType
               + "')]/../input[@type='checkbox']";
    }

    public String getActivityMappingDropdown(
                                              String activityType ) {

        return "//span[contains(text(), '" + activityType + "')]/../../..//select[@class='iaActivities']";
    }

    public String getActivityTypeEditButton(
                                             String activityType ) {

        return activitiesContainer + "//span[contains(text(), '" + activityType
               + "')]/../../..//span[@class='editButton']";
    }
}
