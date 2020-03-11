package concep.selenium.iaConnector;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.IAConnection.ConnectionWizardButtonAction;

public class CampaignCreation extends BaseIAconnector {
	    
	private final boolean isActivitiesEnabled = true;
	private final boolean isSurveysEnabled = false;	
	private final boolean isSourced = true;	
	private final boolean isActivityLinkedToCompany = false;
	
    private String  campTemplateName;
    
	@BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void beforeClassSetUp(
                                  @Optional("config/firefox.IAconnectorV1.1") String configLocation ) throws Exception {

        driver.init( configLocation );
        iaConnectionName = driver.config.getProperty( "iaConnectionName" );
        iaConnectionUsername = driver.config.getProperty( "iaConnectionUsername" );
        iaConnectionPassword = driver.config.getProperty( "iaConnectionPassword" );
        iaSendUser = driver.config.getProperty( "iaSendUser" );
        iaSendPassword = driver.config.getProperty( "iaSendPassword" );
        campTemplateName = driver.config.getProperty( "campaignTemplateName");
        driver.url = driver.config.getProperty( "url" );
        
        setConnectionConfiguration();
        
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
		.selectActivityMappings(campaignActivities, campaignActivityMappings, isActivityLinkedToCompany)
		.connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ALL);
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyAccessCampaignsPage() throws Exception {
		    	        
		log.startTest("Verify that 'Campaigns' page can be successfully accessed");
		
		try {
			
			loginToSend().goToCampaignsPage();
			send.verifyDisplayedElements(new String[] {element.createCampaignButton,
													   element.getCampaignPageSubTab("In Progress"),
													   element.getCampaignPageSubTab("Pending Approval"),
													   element.getCampaignPageSubTab("Scheduled"),
													   element.getCampaignPageSubTab("Sent")},
									     new String[] {"Create a Campaign button",
													   "In Progress tab",
													   "Pending Approval tab",
													   "Scheduled tab",
													   "Sent tab"},
									     true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
   	public void successfullySelectCampaignTemplate() throws Exception {
   		        
   		log.startTest("Verify that user can successfully select a campaign template");
   		
   		try {
   			
   			loginToSend().goToCampaignsPage().campaign.startCampaignCreation();
   			send.verifyDisplayedElements(new String[] {element.getCampaignTemplate(campTemplateName)},
   									     new String[] {campTemplateName + " template"},
   									     true);
   			send.campaign.selectCampaignTemplate(campTemplateName);
   			send.verifyDisplayedElements(new String[] {element.campaignNameField},
   										 new String[] {"Campaign Title input field"},
   										 true);
   			
   			
   		} catch (Exception e) {
   			
   			 log.endStep( false );
   	         throw e;
   		}
   		
   		log.endTest();
   	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
   	public void successfullyAccessCampaignEditPage() throws Exception {
   		        
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        
   		log.startTest("Verify that user can successfully access campaign edit page");
   		
   		try {
   			
   			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
   			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false);
   			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
   													   element.campaignProfileDropdown,
   													   element.campaignFromField,
   													   element.campaignSubjectField,
   													   element.regardingDropdown,
   													   element.getRegardingDropdownSelectedValue(element.regardDropdownDefaultValue)},
   										 new String[] {campaignName + " heading",
   													   "Profile dropdown",
   													   "From input field",
   													   "Subject input field",
   													   "Regarding dropdown",
   													   "Regarding dropdown has default value of Please select"},
   										 true).
   			verifyInputFieldValue(element.campaignFromField, iaSendUser, true);
   			   			
   		} catch (Exception e) {
   			
   			 log.endStep( false );
   	         throw e;
   		}
   		
   		log.endTest();
   	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
   	public void successfullyDisplayRegardingDropdownOption() throws Exception {
   		        
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        
   		log.startTest("Verify that all Interaction folders are successfully displayed in Regarding dropdown menu and they are grouped into the relevant headings");
   		
   		try {
   			
   			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
   			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false);
   			
   			// TODO: Logic have to be implemented in order to verify the folder grouping
   			   			
   		} catch (Exception e) {
   			
   			 log.endStep( false );
   	         throw e;
   		}
   		
   		log.endTest();
   	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
   	public void successfullySelectDifferentRegardingDropdownOptions() throws Exception {
   		        
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;    
        
        String[] iaFolders = {
        	
        		"QA Admin Both Contact and Company",
        		"QA Admin Company Only",
        		"QA Admin Contact Only",
        		"QA MList NoSponsor Contact And Company",
        		"QA NoSponsor Company Only",
        		"QA NoSponsor Contact Only",
        		"QA MList Sponsor Contact And Company",
        		"QA Sponsor Company Only",
        		"QA Sponsor Contact Only"        		
        };
        
   		log.startTest("Verify that user is able to select diferent values of Regarding dropdown menu");
   		
   		try {
   			
   			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
   			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false);
   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(element.regardDropdownDefaultValue)},
   										 new String[] {"Regarding dropdown has default value of Please select"},
   										 true);
   			
   			for (int i = 0; i < iaFolders.length; i++) {
				
   	   			send.campaign.setCampaignRegarding(iaFolders[i]);
   	   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(iaFolders[i])},
											 new String[] {"Regarding dropdown has selected value of: " + iaFolders[i]},
											 true);
			}
   			
   			send.campaign.setCampaignRegarding(element.regardDropdownDefaultValue);
	   		send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(element.regardDropdownDefaultValue)},
										 new String[] {"Regarding dropdown has default value of Please select"},
										 true);
   			   			
   		} catch (Exception e) {
   			
   			 log.endStep( false );
   	         throw e;
   		}
   		
   		log.endTest();
   	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
   	public void successfullySaveCampaignWithDifferentRegardingDropdownOptionsSaveButton() throws Exception {
   		        
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;    
        String campaignSubject = "subj" + dateTime;
        
        String[] iaFolders = {
        	
        		"QA Admin Both Contact and Company",
        		"QA Admin Company Only",
        		"QA Admin Contact Only",
        		"QA MList NoSponsor Contact And Company",
        		"QA NoSponsor Company Only",
        		"QA NoSponsor Contact Only",
        		"QA MList Sponsor Contact And Company",
        		"QA Sponsor Company Only",
        		"QA Sponsor Contact Only",
        		"QA WL Client Analysis",
        		"QA Working List",
        		"Please select"
        };
        
   		log.startTest("Verify that user is able to save a campaign with diferent values of Regarding dropdown menu when Save button is clicked");
   		
   		try {
   			
   			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
   			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).setCampaignSubject(campaignSubject);
   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(element.regardDropdownDefaultValue)},
   										 new String[] {"Regarding dropdown has default value of Please select"},
   										 true);
   			
   			for (int i = 0; i < iaFolders.length; i++) {
				
   	   			send.campaign.setCampaignRegarding(iaFolders[i]).saveCampaign(true);
   	   			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
   	   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(iaFolders[i])},
											 new String[] {"Regarding dropdown has selected value of: " + iaFolders[i]},
											 true);
			}
   			   			
   		} catch (Exception e) {
   			
   			 log.endStep( false );
   	         throw e;
   		}
   		
   		log.endTest();
   	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
   	public void successfullySaveCampaignWithDifferentRegardingDropdownOptionsSaveAndContinueButton() throws Exception {
   		        
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;    
        String campaignSubject = "subj" + dateTime;
        
        String[] iaFolders = {
        	
        		"QA Admin Both Contact and Company",
        		"QA Admin Company Only",
        		"QA Admin Contact Only",
        		"QA MList NoSponsor Contact And Company",
        		"QA NoSponsor Company Only",
        		"QA NoSponsor Contact Only",
        		"QA MList Sponsor Contact And Company",
        		"QA Sponsor Company Only",
        		"QA Sponsor Contact Only",
        		"QA WL Client Analysis",
        		"QA Working List",
        		"Please select"
        };
        
   		log.startTest("Verify that user is able to save a campaign with diferent values of Regarding dropdown menu when Save and Continue button is clicked");
   		
   		try {
   			
   			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
   			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).setCampaignSubject(campaignSubject);
   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(element.regardDropdownDefaultValue)},
   										 new String[] {"Regarding dropdown has default value of Please select"},
   										 true);
   			
   			for (int i = 0; i < iaFolders.length; i++) {
				
   	   			send.campaign.setCampaignRegarding(iaFolders[i]).saveCampaign(false);
   	   			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
   	   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(iaFolders[i])},
											 new String[] {"Regarding dropdown has selected value of: " + iaFolders[i]},
											 true);
			}
   			   			
   		} catch (Exception e) {
   			
   			 log.endStep( false );
   	         throw e;
   		}
   		
   		log.endTest();
   	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
   	public void successfullySaveRegardingDropdownValueWhenNoSubjectValidationTriggered() throws Exception {
   		        
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;
        String campaignSubject = "subj" + dateTime;
                
        String[] iaFolders = {
        	
        		"QA Admin Both Contact and Company",        		
        		"QA MList NoSponsor Contact And Company",        		
        		"QA MList Sponsor Contact And Company",
        		"QA Working List",
        		"Please select"
        };
        
   		log.startTest("Verify that user is able to save a campaign with diferent values of Regarding dropdown menu when no subject validation is triggered");
   		
   		try {
   			
   			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
   			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false);
   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(element.regardDropdownDefaultValue)},
   										 new String[] {"Regarding dropdown has default value of Please select"},
   										 true);
   			
   			for (int i = 0; i < iaFolders.length; i++) {
				
   	   			send.campaign.setCampaignRegarding(iaFolders[i]).saveCampaign(false).handleAddSubjectDialog(campaignSubject, true);
   	   			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
   	   			send.verifyDisplayedElements(new String[] {element.getRegardingDropdownSelectedValue(iaFolders[i])},
											 new String[] {"Regarding dropdown has selected value of: " + iaFolders[i]},
											 true);
			}
   			   			
   		} catch (Exception e) {
   			
   			 log.endStep( false );
   	         throw e;
   		}
   		
   		log.endTest();
   	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void unsuccessfullyAccessCampaignForEditingAfterSending() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String testContact = "contactwithsponsorandcompany@mailinator.com";
        
		log.startTest("Verify that already sent campaign can't be accessed and modified (it'll be not displayed at the 'Campaigns' page");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).saveCampaign(false);
			
			send.contact.goToContactsSubPage("Contacts").searchRecord(testContact).selectContact(testContact);
			send.campaign.continueToPreviewAndSend().sendCampaignNow(true);
			send.goToCampaignsPage().verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName)},
													         new String[] {campaignName},
													         false);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyCreateCampaignByPressingSaveButton() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
		log.startTest("Verify that campaign can be successsfully created by pressing 'Save' button");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(true);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolder)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolder},
									     true).
	        verifyInputFieldValue(element.campaignFromField, iaSendUser, true).
	        verifyInputFieldValue(element.campaignSubjectField, campaignSubject, true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyCreateCampaignByPressingSaveAndContinueToRecipientsButton() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
		log.startTest("Verify that campaign can be successsfully created by pressing 'Save and Continue to Recipients' button");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(false);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolder)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolder},
									     true).
	        verifyInputFieldValue(element.campaignFromField, iaSendUser, true).
	        verifyInputFieldValue(element.campaignSubjectField, campaignSubject, true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyEditCampaignByPressingSaveButton() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
        String iaRegardingFolderUpdateValue = "QA Admin Both Contact and Company";
        String campaignSubjectUpdateValue = "subj" + dateTime + "Updated";
        String campaignFromUpdateValue = "usernameUpdated";
        
		log.startTest("Verify that campaign can be successsfully edited by pressing 'Save' button");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(true);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolder)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolder},
									     true).
	        verifyInputFieldValue(element.campaignFromField, iaSendUser, true).
	        verifyInputFieldValue(element.campaignSubjectField, campaignSubject, true).
	        campaign.setCampaignRegarding(iaRegardingFolderUpdateValue).
	        		 setCampaignSubject(campaignSubjectUpdateValue).
	        		 setCampaignFrom(campaignFromUpdateValue).
	        		 saveCampaign(true);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolderUpdateValue)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolderUpdateValue},
									     true).
			verifyInputFieldValue(element.campaignFromField, campaignFromUpdateValue, true).
			verifyInputFieldValue(element.campaignSubjectField, campaignSubjectUpdateValue, true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyEditCampaignByPressingSaveAndContinueToRecipients() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
        String iaRegardingFolderUpdateValue = "QA Admin Both Contact and Company";
        String campaignSubjectUpdateValue = "subj" + dateTime + "Updated";
        String campaignFromUpdateValue = "usernameUpdated";
        
		log.startTest("Verify that campaign can be successsfully edited by pressing 'Save and Continue to Recipients' button");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(false);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolder)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolder},
									     true).
	        verifyInputFieldValue(element.campaignFromField, iaSendUser, true).
	        verifyInputFieldValue(element.campaignSubjectField, campaignSubject, true).
	        campaign.setCampaignRegarding(iaRegardingFolderUpdateValue).
	        		 setCampaignSubject(campaignSubjectUpdateValue).
	        		 setCampaignFrom(campaignFromUpdateValue).
	        		 saveCampaign(false);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.openCampaignRecord(campaignName);
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolderUpdateValue)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolderUpdateValue},
									     true).
			verifyInputFieldValue(element.campaignFromField, campaignFromUpdateValue, true).
			verifyInputFieldValue(element.campaignSubjectField, campaignSubjectUpdateValue, true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyRetainCampaignValuesByNavigatingBackToContentFromRecipientsPage() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
		log.startTest("Verify that campaign values are successfully retained after navigating back to the campaign content from Recipient page");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(false);
						
			send.campaign.backToCampaignContent(true); 
			
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolder)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolder},
									     true).
	        verifyInputFieldValue(element.campaignFromField, iaSendUser, true).
	        verifyInputFieldValue(element.campaignSubjectField, campaignSubject, true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyRetainCampaignValuesByNavigatingBackToContentFromPreviewAndSendPage() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
		log.startTest("Verify that campaign values are successfully retained after navigating back to the campaign content from Preview and Send page");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(false);
						
			send.campaign.continueToPreviewAndSend().backToCampaignContent(true); 
			
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolder)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolder},
									     true).
	        verifyInputFieldValue(element.campaignFromField, iaSendUser, true).
	        verifyInputFieldValue(element.campaignSubjectField, campaignSubject, true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyRetainCampaignValuesByNavigatingBackToContentFromPreviewSendThroughRecipientsPage() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
		log.startTest("Verify that campaign values are successfully retained after navigating back to the campaign content from Preview and Send page through Recipients");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(false);
						
			send.campaign.continueToPreviewAndSend().backToRecipients().backToCampaignContent(false); 
			
			send.verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName),
													   element.getRegardingDropdownSelectedValue(iaRegardingFolder)},
										 new String[] {campaignName,
													   "Regarding dropdown has selected value of: " + iaRegardingFolder},
									     true).
	        verifyInputFieldValue(element.campaignFromField, iaSendUser, true).
	        verifyInputFieldValue(element.campaignSubjectField, campaignSubject, true);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyDeleteNotMappedCampaignToRegardingFolder() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        
		log.startTest("Verify that campaign can be successsfully deleted when its regarding field is not mapped to IA folder");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).saveCampaign(true);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.deleteCampaignRecord(campaignName, true);
			send.goToCampaignsPage().verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName)},
			         new String[] {campaignName},
			         false);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    
    @Test(groups = { "campaign-creation", "campaigns-all", "all-tests" })
	public void successfullyDeleteMappedCampaignToRegardingFolder() throws Exception {
		
    	String dateTime = driver.getDateTime(dateTimeFormat);    	
        String campaignName = "campaign" + dateTime;        
        String campaignSubject = "subj" + dateTime;
        String iaRegardingFolder = "QA MList Sponsor Contact And Company"; 
        
		log.startTest("Verify that campaign can be successsfully deleted when its regarding field is mapped to IA folder");
		
		try {
			
			loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
			selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
			setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(true);
			
			send.goToCampaignsPage().searchRecord(campaignName).campaign.deleteCampaignRecord(campaignName, true);
			send.goToCampaignsPage().verifyDisplayedElements(new String[] {element.getCampaignHeadingName(campaignName)},
			         new String[] {campaignName},
			         false);
			
		} catch (Exception e) {
			
			 log.endStep( false );
	         throw e;
		}
		
		log.endTest();
	}
    @Test(groups = {"IA-test"})
    public void verifyCampaignContentArea() throws Exception {
    	
    	String dateTime = driver.getDateTime(dateTimeFormat);
    	String campaignName = "campaign" + dateTime;
    	String campaignSubject = "subj" + dateTime;
    	String iaRegardingFolder = "QA MList Sponsor Contact And Company";
    	
    	try {
    		loginToSend().goToCampaignsPage().campaign.startCampaignCreation().
    		selectCampaignTemplate(campTemplateName).setCampaignName(campaignName, false).
    		setCampaignSubject(campaignSubject).setCampaignRegarding(iaRegardingFolder).saveCampaign(true);
    		
    		
    	} catch (Exception e) {
    		log.endStep(false);
    		throw e;
    	}
    }
    
    
}
