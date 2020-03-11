package concep.selenium.send;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public abstract class Surveys<T> {

    protected Elements                        element         = new Elements();
    protected static GenericSeleniumWebDriver driver          = DriverAccessor.getDriverInstance();
    protected LogResults                      log             = new LogResults();
    public SfdcQuestions                      sfdcQuestion    = new SfdcQuestions();
    public MSD4Questions                      dynamicQuestion = new MSD4Questions();
    public IAQuestions                        iaQuestion      = new IAQuestions();

    @SuppressWarnings("unchecked")
    public T goToSurveySettingsTab(
                                    String surveySettingsSubPage ) throws Exception {

        log.startStep( "Click on the '" + surveySettingsSubPage + "' subpage tab" );
        driver.click( element.surveySettingsSubPage( surveySettingsSubPage ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T addLanguage(
                          String[] languages ) throws Exception {

        log.startStep( "Add default language - '" + languages[0] + "'" );
        driver.click( "//a[@id='langAdd']", driver.timeOut );
        driver.clear( "//label[contains(text(), 'Default Language')]/../input" );
        driver.type( "//label[contains(text(), 'Default Language')]/../input", languages[0], driver.timeOut );
        driver.click( "//div[@class='ui-dialog-buttonset']//span[contains(text(),'Add')]", driver.timeOut );
        log.endStep();

        for( int i = 0; i < ( languages.length ) - 1; i++ ) {
            if( languages.length > 1 ) {
                log.startStep( "Add a language - '" + languages[i + 1] + "'" );
                driver.type( "//div[@id='langList']//div[contains(@class, 'langListRow')][" + ( i + 2 ) + "]//input",
                             languages[i + 1],
                             driver.timeOut );
                driver.click( "//a[@id='langAdd']", driver.timeOut );
                log.endStep();
            } else {
                break;
            }
        }

        return ( T ) this;
    }

    //language ids [Bulgarian=2, Arabic=3]
    @SuppressWarnings("unchecked")
    public T deleteLanguage(
                             String[] languageId ) throws Exception {

        log.startStep( "Click on the 'Add Language' link" );
        for( int i = 0; i < languageId.length; i++ ) {
            driver.click( "//ul[@id='langList']/li[" + languageId[i] + "]//a[contains(text(),'Delete')]",
                          driver.timeOut );
            driver.click( "//span[contains(text(),'Yes, Delete')]", driver.timeOut );
        }
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T goToSurveyLanguagePage(
                                     String language ) throws Exception {

        log.startStep( "Click on the '" + language + "' survey page" );
        driver.click( element.surveyLangauge( language ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T startSurveyCreation() throws Exception {

        log.startStep( "Click the 'Create a Survey' button" );
        driver.click( element.createSurvey, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }
    
    @SuppressWarnings("unchecked")
    public T typeSurveyNameAndClickCreate(String surveyName) throws Exception {

    	 log.startStep( "Enter survey name '" + surveyName + "'" );
         driver.selectWindow();
         driver.type( element.createSurveyTextName, surveyName, driver.timeOut );
         log.endStep();

         log.startStep( "Click on the create button" );
         driver.clickAndVerify( element.createSurveyCompletion, element.surveyContentoptions );
         driver.waitForPageToLoad();
         driver.waitForAjaxToComplete( driver.ajaxTimeOut );
         log.endStep();

        return ( T ) this;
    }
    
    @SuppressWarnings("unchecked")
    public T createSurveyAndTypeSurveyName(
                                            String surveyName ) throws Exception {

    	startSurveyCreation();
    	typeSurveyNameAndClickCreate(surveyName);

        return ( T ) this;
    }
    
    @SuppressWarnings("unchecked")
    public T startCreatingSurveyUsingTemplate( String templateName, String surveyName ) throws Exception {

        log.startStep( "Click the 'Create a Survey' button" );
        driver.click( element.createSurvey, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        selectSurveyTemplate(templateName, surveyName);

        return ( T ) this;
    }
    
    @SuppressWarnings("unchecked")
    public T selectSurveyTemplate( String templateName, String surveyName ) throws Exception {
            
        log.startStep("Select template with name: " + templateName);
        driver.click(element.getSurveyTemplate(templateName), driver.timeOut);
        log.endStep();

        log.startStep( "Enter survey name '" + surveyName + "'" );
        driver.selectWindow();
        driver.type( element.createSurveyTextName, surveyName, driver.timeOut );
        log.endStep();

        log.startStep( "Click on the create button" );
        driver.clickAndVerify( element.createSurveyCompletion, element.surveyContentoptions );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }
    
    @SuppressWarnings("unchecked")
    public T saveSurveyAsTemplate(String surveyName, String templateName) throws Exception {

        log.startStep( "Click the 'Tools' menu" );
        driver.click( element.getSurveyToolsMenuFromInside(surveyName), driver.timeOut);
        log.endStep();
        
        log.startStep( "Click the 'More' menu option" );
        driver.click( element.moreToolsSurvey, driver.timeOut);
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.switchToIframeByID("DOMWindowIframe");
        log.endStep();
        
        log.startStep( "Click the 'Save As Template' tab" );
        driver.click( element.saveAsTemplateTab, driver.timeOut);
        log.endStep();        

        log.startStep( "Enter template name '" + templateName + "'" );
        driver.type( element.templateName, templateName, driver.timeOut );
        log.endStep();
        
        log.startStep( "Click the 'Save As Template' button" );
        driver.click( element.saveAsTemplateBtn, driver.timeOut);
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep(); 
        
        log.startStep( "Click on the 'OK' button" );
        driver.click( element.okButtonDialogMsg, driver.timeOut);
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep(); 
        
        log.startStep( "Click on the 'Close' button" );
        driver.click( element.closeButtonDeploySurvey, driver.timeOut);
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.switchToTopWindow();

        return ( T ) this;
    }
    
    @SuppressWarnings("unchecked")
    public T deleteSurveyTemplate(String templateName) throws Exception {

    	log.startStep( "Click on the 'Templates' tab" );
        driver.click( "//ul[@id='typesMenu']//span[contains(text(),'Templates')]", driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        log.startStep( "Type '" + templateName + "' in the search text box" );
        driver.type( "//input[@id='txtSearch']", templateName, driver.timeOut );
        log.endStep();
        
        log.startStep( "Click the 'Search' button" );
        driver.click( "//a[@id='btnSearch']", driver.timeOut );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        driver.waitForPageToLoad();
        log.endStep();
        
        log.startStep( "Click on the 'Tools' dropdown menu" );
        driver.click( element.getSurveyRecordToolsMenu( templateName ), driver.timeOut );
        log.endStep();

        log.startStep( "Click on the 'Delete' record button" );
        driver.click( element.deleteTemplate, driver.timeOut );
        log.endStep();

        log.startStep( "Confirm the deletion clicking on the 'Yes, Delete' button" );
        driver.click( element.confirmDeletionBtn, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T saveSurvey() throws Exception {

        log.startStep( "Click the 'Save' button" );
        driver.click( element.saveSurvey, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T saveAndSelectTheme(
                                 String theme ) throws Exception {

        saveAndContinueToTheme();

        selectTheme( theme );

        saveAndContinueToDeploy();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T saveAndContinueToTheme() throws Exception {

        log.startStep( "Click the 'Save and Continue to Theme' button" );
        driver.click( element.saveAndContinue, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T selectTheme(
                          String theme ) throws Exception {

        log.startStep( "Verify that the 'Select a theme.' page is displayed" );
        log.endStep( driver.isElementPresent( element.selectThemeLabel, driver.timeOut ) );

        log.startStep( "Click the theme " + theme );
        driver.click( element.surveyTheme( theme ), driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T saveAndContinueToDeploy() throws Exception {

        log.startStep( "Click the 'Save and Continue to Deploy »' button" );
        driver.click( element.saveAndContinueToDeploy, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T viewAndDeployTheSurvey() throws Exception {

        log.startStep( "Click the 'Preview Survey' button" );
        driver.click( element.deploySurvey, driver.timeOut );
        log.endStep();

        log.startStep( "Verify that the survey's preview page is displayed" );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        log.endStep( driver.isElementPresent( element.surveySubmissionPage, driver.timeOut ) );

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T surveyNext() throws Exception {

        log.startStep( "Click the Next button to Complete the survey" );
        driver.click( element.surveyNext, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T addAndGoToPage(
                             String pageName ) throws Exception {

        log.startStep( "Click on Add Page button" );
        driver.click( element.addPage, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Name the Page" );
        driver.type( element.SurveyPageNameText, pageName, driver.timeOut );
        driver.click( element.addPageCompletion, driver.timeOut );
        log.endStep();

        goToPageInSurvey( pageName );

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T goToPageInSurvey(
                               String pageName ) throws Exception {

        log.startStep( "go to page name " + pageName );
        driver.waitUntilElementIsLocated(element.pageInSurvey( pageName ), driver.timeOut);
        driver.waitUntilElementIsClickable(element.pageInSurvey( pageName ));
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        Thread.sleep(1500);
        driver.click( element.pageInSurvey( pageName ), driver.timeOut );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();

        return ( T ) this;
    }
    @SuppressWarnings("unchecked")
    public T deletePageInSurvey() throws Exception {

        log.startStep("Start deletion of the page");
        driver.click(element.pageDropdown, 10);
        driver.click(element.deleteOptionForpage, 10);
        driver.click(element.confirmDeletionOfpage, 10);
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T switchSurveySubmitionPageLanguage(
                                                String language ) throws Exception {

        log.startStep( "switch the survey language to '" + language + "'" );
        driver.click( element.surveysubmissionLanguage( language ), driver.timeOut );
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T openSurvey(
                         String surveyName ) throws Exception {

        log.startStep( "Open the survey with name " + surveyName );
        driver.click( "//td/a[contains(text(), '" + surveyName + "')]", driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T searchSurvey(
                           String surveyName ) throws Exception {

        log.startStep( "Type '" + surveyName + "' in the Search input field" );
        driver.clear( element.searchInputField );
        driver.type( element.searchInputField, surveyName, driver.timeOut );
        log.endStep();

        log.startStep( "Click on the 'Search' button" );
        driver.click( element.searchBtn, driver.timeOut );
        driver.waitForAjaxToComplete( driver.timeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T sortSurveysBy(
                            String criteria ) throws Exception {

        log.startStep( "Click on the '" + criteria + "' sorting criteria heading" );
        driver.click( element.getSortingCriteria( criteria ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T deleteSurvey(
                           String surveyName ) throws Exception {

        log.startStep( "Click on the 'Tools' dropdown menu" );
        driver.click( element.getSurveyRecordToolsMenu( surveyName ), driver.timeOut );
        log.endStep();

        log.startStep( "Click on the 'Delete' record button" );
        driver.click( element.deleteSurvey, driver.timeOut );
        log.endStep();
        
        //driver.selectWindow();
        driver.type(element.confirmDeletionOfSurvey, "Yes", driver.timeOut);
        
        log.startStep( "Confirm the deletion clicking on the 'Yes, Delete' button" );
        driver.click( element.confirmDeletionBtn, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T copySurvey(
                         boolean isFromOutside,
                         String surveyName,
                         String newName,
                         boolean isEdit ) throws Exception {

        if( isFromOutside ) {

            log.startStep( "Click on the 'Tools' dropdown menu" );
            driver.click( element.getSurveyRecordToolsMenu( surveyName ), driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Click on the 'Tools' dropdown menu" );
            driver.click( element.getSurveyToolsMenuFromInside( surveyName ), driver.timeOut );
            log.endStep();
        }

        log.startStep( "Click on the 'Copy' record button" );
        driver.click( element.copySurvey, driver.timeOut );
        log.endStep();

        log.startStep( "Enter survey name '" + newName + "'" );
        driver.selectWindow();
        driver.clear( element.createSurveyTextName );
        driver.type( element.createSurveyTextName, newName, driver.timeOut );
        log.endStep();

        if( isEdit ) {

            log.startStep( "Click on the 'Copy and Edit' button from the dialog message" );
            driver.click( element.copyAndEditBtnDialogMsg, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Click on the 'Copy' button from the dialog message" );
            driver.click( element.copyBtnDialogMsg, driver.timeOut );
            log.endStep();
        }

        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T pagingRecords(
                            String pageCommand,
                            int clicksCount ) throws Exception {

        for( int i = 0; i < clicksCount; i++ ) {

            log.startStep( "Click on the '" + pageCommand + "' button from the paging" );
            driver.click( element.getPagingButton( pageCommand ), driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();
        }

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T discardChangesDialog(
                                   boolean isDiscard ) throws Exception {

        if( isDiscard ) {

            log.startStep( "Click on the 'Yes, Discard' button from the Discard Changes dialog" );
            driver.click( element.discardChangesDialogBtn, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Click on the 'Cancel' button from the Discard Changes dialog" );
            driver.click( element.cancelDialogBtn, driver.timeOut );
            log.endStep();
        }

        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T handleDuplicateMappingsDialog(
                                            boolean isSave ) throws Exception {

        if( isSave ) {

            log.startStep( "Click on the 'Save' button from the Duplicate Mappings dialog" );
            driver.click( element.saveDuplicatedMappingsDialogBtn, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Click on the 'Review' button from the Duplicate Mappings dialog" );
            driver.click( element.reviewDuplicatedMappingsDialogBtn, driver.timeOut );
            log.endStep();
        }

        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T closeDeploySurveyWindow() {

        driver.click( element.closeButtonDeploySurvey, driver.timeOut );
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T checkLogResponseInCRM() {

        for( int i = 0;; i++ ) {
            if( i >= driver.timeOut )
                break;
            try {
                driver.click( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']",
                              driver.timeOut );
                if( driver.isChecked( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']" ) ) {
                    break;
                }
            } catch( Exception e ) {}
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T uncheckLogResponseInCRM() {

        for( int i = 0;; i++ ) {
            if( i >= driver.timeOut )
                break;
            try {
                driver.click( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']",
                              driver.timeOut );
                if( !driver.isChecked( "//label[contains(text(), 'Log responses in your CRM system')]/input[@type='checkbox']" ) ) {
                    break;
                }
            } catch( Exception e ) {}
            try {
                Thread.sleep( 1000 );
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
        return ( T ) this;
    }

}
