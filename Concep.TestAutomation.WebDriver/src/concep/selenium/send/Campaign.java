package concep.selenium.send;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class Campaign {

    protected static GenericSeleniumWebDriver driver  = DriverAccessor.getDriverInstance();
    protected Elements                        element = new Elements();
    protected LogResults                      log     = new LogResults();

    public String                             name;
    public String                             subject;
    public String                             iaFolder;

    public Campaign() {

    }

    public Campaign( String name,
                     String subject,
                     String iaFolder ) {

        this.name = name;
        this.subject = subject;

        if( iaFolder != null ) {

            this.iaFolder = iaFolder;
        }
    }

    public Campaign startCampaignCreation() throws Exception {

        log.startStep( "Click on the 'Create a Campaign' button" );
        driver.click( element.createCampaignButton, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public Campaign selectCampaignTemplate(
                                            String templateName ) throws Exception {

        log.startStep( "Click the campaign template" );
        driver.click( "//span[contains(text(),'My Templates')]", driver.timeOut );
        driver.click( element.getCampaignTemplate( templateName ), driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public Campaign setCampaignName(
                                     String campaignName,
                                     boolean isCanceled ) throws Exception {

        if( campaignName != "" ) {

            log.resultStep( "Type in 'Campaign Title': " + campaignName );
            driver.type( element.campaignNameField, campaignName, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();
        }

        if( isCanceled ) {

            log.startStep( "Click on the 'Cancel' button" );
            driver.click( element.cancelDialogBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();

        } else {

            log.startStep( "Click on the 'Create' button" );
            driver.click( element.createButton, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();
        }

        return this;
    }

    public Campaign setCampaignSubject(
                                        String campaignSubject ) throws Exception {

        log.startStep( "Type in 'Subject': " + campaignSubject );
        driver.clear( element.campaignSubjectField );
        driver.type( element.campaignSubjectField, campaignSubject, driver.timeOut );
        log.endStep();

        return this;
    }
    
    public Campaign enableRegardingFolderFeature() throws Exception {
    	
    	/*log.startStep("Wait until the element is fully loaded");
    	driver.waitUntilElementIsLocated(element.enableRegardingFolderCheckbox, driver.timeOut);
    	driver.waitUntilElementIsClickable(element.enableRegardingFolderCheckbox);
    	log.endStep();*/
    	
    	Thread.sleep(1500);
    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    	
    	if (!driver.isChecked(element.enableRegardingFolderCheckbox)) {
			log.startStep("Enable 'Regarding' dropdown feature");
			driver.waitUntilElementIsLocated(element.enableRegardingFolderCheckbox, driver.timeOut);
			driver.waitUntilElementIsClickable(element.enableRegardingFolderCheckbox);
			driver.click(element.enableRegardingFolderCheckbox, driver.timeOut);
			driver.waitForAjaxToComplete(driver.ajaxTimeOut);
			log.endStep();
		}
    	
    	return this;
    }
    
    public Campaign disableRegardingFolderFeature() throws Exception {
    	
    	if (driver.isChecked(element.enableRegardingFolderCheckbox)) {
			log.startStep("Disable 'Regarding' dropdown feature");
			driver.click(element.enableRegardingFolderCheckbox, driver.timeOut);
			driver.waitForAjaxToComplete(driver.ajaxTimeOut);
			log.endStep();
		}
    	
    	return this;
    }

    public Campaign setCampaignRegarding(
                                          String folderName ) throws Exception {

        if( folderName != null ) {

        	enableRegardingFolderFeature();
        	
            log.startStep( "Select regarding folder: " + folderName );
            driver.select2DropDown( element.campaignRegardingDropdown,
                                    element.campaignRegardingDropdownSearch,
                                    folderName );
            log.endStep();
        }

        return this;
    }

    public Campaign setCampaignFrom(
                                     String fromValue ) throws Exception {

        log.startStep( "Type in 'From': campaignSubject" + fromValue );
        driver.clear( element.campaignFromField );
        driver.type( element.campaignFromField, fromValue, driver.timeOut );
        log.endStep();

        return this;
    }

    public Campaign saveCampaign(
                                  boolean isSaveOnly ) throws Exception {

        if( isSaveOnly ) {

            log.startStep( "Click on the 'Save' button" );
            driver.click( element.campaignSaveButton, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();

        } else {

            log.startStep( "Click on the 'Save and Continue to Recipients' button" );
            driver.click( element.saveAndContinueToRecipients, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();
        }

        return this;
    }

    public Campaign continueToPreviewAndSend() throws Exception {

        log.startStep( "Click on 'Continue to Preview and Send' button" );
        driver.click( element.continueToPreviewAndSend, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.waitForPageToLoad();
        log.endStep();

        return this;
    }

    public Campaign sendCampaignNow(
                                     boolean isConfirmed ) throws Exception {

        log.startStep( "Click on 'Send Campaign Now' button" );
        driver.click( element.campaignSendBtn, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.waitForPageToLoad();
        log.endStep();

        if( isConfirmed ) {

            log.startStep( "Click on 'Yes, Send Now'" );
            driver.click( element.sendConfirmBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();

        } else {

            log.startStep( "Click on 'Cancel'" );
            driver.click( element.cancelDialogBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();
        }

        return this;
    }

    public Campaign insertSurvey(
                                  String surveyName,
                                  boolean saveAndContinue ) throws Exception {

        log.startStep( "Insert " + surveyName + " in the campaign" );
        driver.switchToIframeByID( "editorFrame" );
        driver.click( element.campaignBody, driver.timeOut );
        driver.click( element.insertLinkBtn, driver.timeOut );
        driver.switchToTopWindow();
        driver.switchToIframeByID( "DOMWindowIframe" );
        driver.click( element.insertSurveyTab, driver.timeOut );
        driver.click( element.searchSurveyArrow, driver.timeOut );
        driver.click( element.getSurveyInsideCampaign( surveyName ), driver.timeOut );
        driver.click( element.createSurveyLinkBtn, driver.timeOut );
        driver.switchToTopWindow();

        if( saveAndContinue ) {
            driver.click( element.saveAndContinueToRecipients, driver.timeOut );
            log.endStep();

        } else {
            driver.click( element.campaignSaveButton, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    public Campaign openCampaignRecord(
                                        String campaignTitle ) throws Exception {

        log.startStep( "Click on the campaign record with name: " + campaignTitle );
        driver.click( element.getCampaignRecord( campaignTitle ), driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.waitForPageToLoad();
        log.endStep();

        return this;
    }

    public Campaign deleteCampaignRecord(
                                          String campaignTitle,
                                          boolean isConfirmed ) throws Exception {

        log.startStep( "Click on the 'Tools' dropdown menu" );
        driver.click( element.getCampaignRecordToolsMenu( campaignTitle ), driver.timeOut );
        log.endStep();

        log.startStep( "Click on the 'Delete' record button" );
        driver.click( element.deleteCampaign, driver.timeOut );
        log.endStep();

        if( isConfirmed ) {

            log.startStep( "Confirm the deletion clicking on the 'Yes, Delete' button" );
            driver.click( element.confirmDeletionBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();

        } else {

            log.startStep( "Confirm the deletion clicking on the 'Cancel' button" );
            driver.click( element.cancelDialogBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();
        }

        return this;
    }

    public Campaign handleAddSubjectDialog(
                                            String subject,
                                            boolean isAddLater ) throws Exception {

        if( isAddLater ) {

            log.startStep( "Click on the 'Add Later' button" );
            driver.click( element.addLaterDialogBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();

        } else {

            log.startStep( "Type in a subject: " + subject );
            driver.clear( element.subjectDialogInputField );
            driver.type( element.subjectDialogInputField, subject, driver.timeOut );
            log.endStep();

            log.startStep( "Click on the 'Add Subject' button" );
            driver.click( element.addSubjectDialogBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();
        }

        return this;
    }

    public Campaign backToCampaignContent(
                                           boolean isTabUsed ) throws Exception {

        if( isTabUsed ) {

            log.startStep( "Click on the 'Content' tab" );
            driver.click( element.contentTabButton, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();

        } else {

            log.startStep( "Click on the 'Back to Content' button" );
            driver.click( element.backToContentBtn, driver.timeOut );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.waitForPageToLoad();
            log.endStep();
        }

        return this;
    }

    public Campaign backToRecipients() throws Exception {

        log.startStep( "Click on the 'Back to Recipients' tab" );
        driver.click( element.backToRecipientBtn, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.waitForPageToLoad();
        log.endStep();

        return this;
    }
}
