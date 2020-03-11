package concep.selenium.send;

import java.security.InvalidParameterException;

public class IAConnection extends Connections<IAConnection> {

	public enum ConnectionWizardButtonAction {

		CONNECT,
        BACK,
        NEXT,
        CANCEL,
        SAVE,
        SAVE_AND_CLOSE_ACTIVITY,
        CONFIGURE_FOLDERS,
        ADD_USERS,
        SAVE_AND_CLOSE_ALL;
    }
	
	public enum ConnectionWizardPages {

		LOGIN,
        USERS,
        ADD_USERS,
        SETTINGS,
        CONFIGURE_FOLDERS,
        ACTIVITIES;
    }
	
	public enum FolderTypes {
		
		PERSONNEL("Personnel"),
		CLIENT_ANALYSIS("Client Analysis"),
		ARCHIVE("Archive"),
		OTHER_ADMINISTRATIVE("Other Administrative"),
		DATA_MANAGEMENT("Data Management"),
		INFORMATION("Information"),
		OTHER_CONTACT_TYPES("Other Contact Types"),
		STATUS("Status"),
		ORGANIZATIONS("Organizations"),
		CLIENTS_AND_PROSPECTS("Clients and Prospects"),
		JD_TEST_FOLDERS("JD Test Folders"),
		MARKETING_LIST_WITH_SPONSORSHIP("Marketing List (with sponsorship)"),
		OTHER("Other"),
		MARKETING_LIST_NO_SPONSORSHIP("Marketing List (no sponsorship)"),
		PUBLIC("Public");
		
		public String folderType;
		
		private FolderTypes(String folderType) {
			
			this.folderType = folderType;
		}
	}
	
	public enum ActivityTypes {

		SURVEY_SUBMITTED("Survey Submitted"),
		SURVEY_ACCEPTED("Survey Accepted"),
		SURVEY_DECLINED("Survey Declined"),
		CAMPAIGN_SENT("Campaign Sent"),
		CAMPAIGN_VIEWED("Campaign Viewed"),
		COMPOUND_VIEW("Compound View"),
		PAGE_VIEW("Page View"),
		FORWARD_TO_FRIEND("Forward to a Friend"),
		URL_CLICK("URL Click"),
		ACCOUNT_LEVE_OPT_OUT("Account Level Opt Out"),
		CLIENT_LEVEL_OPT_OUT("Client Level Opt Out"),
		ABUSE_REPORT("Abuse Reported"),
		UNDELIVERABLE_PERMANENT("Undeliverable (Permanent)"),
		UNDELIVERABLE_TEMPORARY("Undeliverable (Temporary)"),
		CAMPAIGN_RESENT("Campaign Resent"),
		PREFERENCES_UPDATED("Preferences Updated");
		
	    public String stringValue;

	    private ActivityTypes( String stringValue) {

	        this.stringValue = stringValue;
	    }
    }
	
	public enum ActivityMappings {

		REMINDED_ABOUT("Reminded About"),
		PHONE_EXTENSION_CHANGE("Phone Extension Change"),
		ASSISTANT_NUMBER_CHANGE("Assistant Number Change"),
		MOBILE_NUMBER_CHANGE("Mobile Number Change"),
		OTHER_NUMBER_CHANGE("Other Number Change"),
		COMPUTER_NUMBER_CHANGE("Computer Number Change"),
		PAGER_NUMBER_CHANGE("Pager Number Change"),
		FAX_NUMBER_CHANGE("Fax Number Change"),
		PHONE_NUMBER_CHANGE("Phone Number Change"),
		OTHER_ELECTRONIC_ADDRESS_CHANGE("Other Electronic Address Change"),
		FTP_SITE_ADDRESS_CHANGE("FTP Site Address Change"),
		WEBSITE_CHANGE("Website Change"),
		EMAIL_ADDRESS_CHANGE("E-mail Address Change"),
		COMPANY_CHANGE("Company Change"),
		POSTAL_CODE_CHANGE("Postal Code Change"),
		EMPLOYMENT_CHANGE("Employment Change"),
		EMPLOYEE_UPDATE("Employee Update"),
		EMPLOYMENT_UPDATE("Employment Update"),
		EMPLOYEE_ADDDED("Employee Added"),
		EMPLOYMENT_ADDED("Employment Added");		
		
	    public String stringValue;

	    private ActivityMappings( String stringValue) {

	        this.stringValue = stringValue;
	    }
    }
	
	public String[] iaFolders = { "Data Management",
						            "Marketing List (no sponsorship)",
						            "Marketing List (with sponsorship)" };
	
    public IAConnection selectIAFolder(
                                        String[] folderName ) throws Exception {

        for( int i = 0; i < folderName.length; i++ ) {
            log.startStep( "Select a folder '" + folderName[i] + "'" );
            if( driver.isCheckBoxChecked( folderName[i] ) ) {} else {
                driver.click( element.iaSurveyFoldersCheckBox( folderName[i] ), driver.timeOut );
            }
            log.endStep();
        }

        return this;
    }
    
    public IAConnection selectFolderTypes(String[] folderTypes) throws Exception {
    	
    	for (int i = 0; i < folderTypes.length; i++) {
			
    		driver.waitUntilElementIsLocated(element.getFolderTypeCheckbox(folderTypes[i]), driver.timeOut);
    		driver.waitUntilElementIsClickable(element.getFolderTypeCheckbox(folderTypes[i]));
    		
    		if (!driver.isChecked(element.getFolderTypeCheckbox(folderTypes[i]))) {
				
    			log.startStep("Check the '" + folderTypes[i] + "' folder type checkbox ");
    			driver.click(element.getFolderTypeCheckbox(folderTypes[i]), driver.timeOut);
    			log.endStep();
    			
    			log.startStep("Verify that the checkbox is successfully checked for folder with name: " + folderTypes[i]);				
				log.endStep(driver.isChecked(element.getFolderTypeCheckbox(folderTypes[i])));
				
			} else {
				
				log.startStep(folderTypes[i] + " is already selected");
				log.endStep();
			}
		}
    	
    	return this;
    }
    
    public IAConnection deselectFolderTypes(String[] folderTypes) throws Exception {
    	
    	for (int i = 0; i < folderTypes.length; i++) {
			
    		driver.waitUntilElementIsLocated(element.getFolderTypeCheckbox(folderTypes[i]), driver.timeOut);
    		driver.waitUntilElementIsClickable(element.getFolderTypeCheckbox(folderTypes[i]));
    		
    		if (driver.isChecked(element.getFolderTypeCheckbox(folderTypes[i]))) {
				
    			log.startStep("Uncheck the '" + folderTypes[i] + "' folder type checkbox ");
    			driver.click(element.getFolderTypeCheckbox(folderTypes[i]), driver.timeOut);
    			log.endStep();
    			
    			log.startStep("Verify that the checkbox is successfully un-checked for folder with name: " + folderTypes[i]);				
				log.endStep(!driver.isChecked(element.getFolderTypeCheckbox(folderTypes[i])));
			}
		}
    	
    	return this;
    }

    public IAConnection unSelectIAFolder(
                                          String[] folderName ) throws Exception {

        for( int i = 0; i < folderName.length; i++ ) {
            log.startStep( "Select a folder '" + folderName[i] + "'" );
            if( driver.isCheckBoxChecked( folderName[i] ) ) {
                driver.click( element.iaSurveyFoldersCheckBox( folderName[i] ), driver.timeOut );
            }
            log.endStep();
        }
        return this;
    }

    public IAConnection enableSurveyAndSelectIAFolders(
                                                        String enableSurveyXpath,
                                                        boolean isSurveyEnabled,
                                                        boolean isFoldersEnabled ) throws Exception {       

        log.startStep( "verify that survey window is displayed" );
        log.endStep( driver.isElementPresent( enableSurveyXpath, driver.timeOut ) );

        if( isSurveyEnabled ) {
            enableSurveys( enableSurveyXpath );
            if( isFoldersEnabled ) {
                selectIAFolder( iaFolders );
            } else {
                unSelectIAFolder( iaFolders );
            }
        } else {
            disableSurveys( enableSurveyXpath );
        }
        return this;
    }
    
    public IAConnection clickConfigureButton() throws Exception {

        log.startStep( "Click the 'Configure folders' button" );
        driver.click( "//span[contains(text(), 'Configure folders')]", driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep( driver.isElementPresent( "//select[@id='searchPeople']", driver.timeOut ) );

        return this;
    }

    public IAConnection selectContactAndCompanyDefaultSearch() throws Exception {

        log.startStep( "Select a folder 'Firm Contacts - People' for the 'Only search people contacts sourced in/linked to' option" );
        driver.select( "//select[@id='searchPeople']" ).selectByVisibleText( "Firm Contacts - People" );
        log.endStep();

        log.startStep( "Select a folder 'Firm Contacts - Companies' for the 'Only search company contacts sourced in/linked to' " );
        driver.select( "//select[@id='searchCompany']" ).selectByVisibleText( "Firm Contacts - Companies" );
        log.endStep();
        return this;
    }

    public IAConnection selectCompanySourcedFolder( String sourceFolder ) throws Exception {

        log.startStep( "Select a folder 'New Contact Review - Companies' for the 'Source them in this folder" );
        driver.select( "//select[@id='sourceFolder']" ).selectByVisibleText( sourceFolder );
        log.endStep();
        
        return this;
    }
    
    public IAConnection selectCompanyLinkedFolder() throws Exception {
    	
    	log.startStep( "Select a folder 'JD Test 1' for the 'Link them to these folders" );
        driver.select( "//select[@id='linkedFolders']" ).selectByVisibleText( "JD Test 1" );
        log.endStep();

        log.startStep( "Select a folder 'JD Test 2' for the 'Link them to these folders" );
        driver.select( "//div[@id='connectionConfiguration']//div[1]//div[5]/select" )
              .selectByVisibleText( "JD Test 2" );
        log.endStep();

        log.startStep( "Select a folder 'JD Test 3' for the 'Link them to these folders" );
        driver.select( "//div[@id='connectionConfiguration']//div[1]//div[6]/select" )
              .selectByVisibleText( "JD Test 3" );
        log.endStep();
    	
    	return this;
    }

    public IAConnection enableCompanySourceFolder() throws Exception {

        if( driver.isChecked( "//div[@class='ui-helper-clearfix third']/label/input" ) ) {} else {
            log.startStep( "Check the 'Source new company contacts in Marketing Event folder, not the one above?' checkbox " );
            driver.click( "//div[@class='ui-helper-clearfix third']/label/input", driver.timeOut );
        }
        log.endStep();

        return this;
    }

    public IAConnection disableCompanySourceFolder() {

        if( driver.isChecked( "//div[@class='ui-helper-clearfix third']/label/input" ) ) {
            log.startStep( "Check the 'Source new company contacts in Marketing Event folder, not the one above?' checkbox " );
            driver.click( "//div[@class='ui-helper-clearfix third']/label/input", driver.timeOut );
        }
        return this;
    }

    public IAConnection saveConfigurationFolderSettings() throws Exception {

        log.startStep( "Click the 'Save' button" );
        driver.click( "//a[@class='s_button right']/span[contains(text(), 'Save')]", driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        return this;
    }

    public IAConnection selectAndSaveFolderConfiguration(
                                                          String sourceFolder,
                                                          boolean isSourceChecked ) throws Exception {

        clickConfigureButton();
        selectContactAndCompanyDefaultSearch();
        selectCompanyLinkedFolder();

        if( isSourceChecked ) {
            enableCompanySourceFolder();
        } else {
            disableCompanySourceFolder();
            selectCompanySourcedFolder( sourceFolder );
        }
        saveConfigurationFolderSettings();

        return this;
    }

    public IAConnection selectSurveyConnectionActivities() throws Exception {

        selectActivityForSubmittedSurvey();
        selectActivityForRSVPAccepted();
        selectActivityForRSVPDeclined();

        return this;
    }

    public IAConnection clickNextToActivtiesPage() throws Exception {

        connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
    	
        return this;
    }
    
    public IAConnection clickNextToFolderConfigurationPage() throws Exception {

        connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
    	
        return this;
    }
    
    public IAConnection clickNextToFolderTypesPage() throws Exception {

        log.startStep( "Click the 'Next' button" );
        driver.click( "//span[contains(text(), 'Next')]", driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }
    
    public IAConnection selectFoldersConfiguration(String searchPeopleFolder,
    												  String searchCompanyFolder,    												  
    												  String[] linkFolders,
    												  String sourceFolder,
    												  boolean isSourced) throws Exception {
    	
    	if (searchPeopleFolder != "") {    		
            
    		log.startStep( "Select '" + searchPeopleFolder + "' from the 'Only search people contacts sourced in/linked to' drop down" );
            driver.select2DropDown(element.peopleDropdownSearchContainer, element.dropdownSearch, searchPeopleFolder, element.getSelect2DropdownListitem(searchPeopleFolder));
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();  		
		}
    	
    	if (searchCompanyFolder != "") {
    		
    		log.startStep( "Select '" + searchCompanyFolder + "' from the 'Only search company contacts sourced in/linked to' drop down" );
            driver.select2DropDown(element.companyDropdownSearchContainer, element.dropdownSearch, searchCompanyFolder, element.getSelect2DropdownListitem(searchCompanyFolder));
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();  		
		}
    	
    	if (isSourced) {
			
    		enableCompanySourceFolder();
    		
		} else {
			
			disableCompanySourceFolder();		
			
			log.startStep( "Select '" + sourceFolder + "' from the 'Source them in this folder' drop down" );
            driver.select2DropDown(element.sourceFolderDropdownSearchContainer, element.dropdownSearch, sourceFolder, element.getSelect2DropdownListitem(sourceFolder));
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();
		}
    	
    	if (linkFolders != null && linkFolders.length <= 3) {
			
    		for (int i = 0; i < linkFolders.length; i++) {
				
    			log.startStep("Select " + linkFolders[i] + " for 'Link them to these folders' dropdown");
                driver.select2DropDown(element.getLinkToFolderDropdownNChild(i + 1), element.dropdownSearch, linkFolders[i], element.getSelect2DropdownListitem(linkFolders[i]));
    			log.endStep();
			}
		}
    	
    	return this;
    }

    public IAConnection selectActivityForSubmittedSurvey() throws Exception {

    	String activityTypeSubmitted = "Survey Submitted";
    	
    	driver.waitUntilElementIsLocated(element.getActivityTypeCheckbox(activityTypeSubmitted), driver.timeOut);
		driver.waitUntilElementIsClickable(element.getActivityTypeCheckbox(activityTypeSubmitted));
		
    	if (!driver.isChecked(element.getActivityTypeCheckbox(activityTypeSubmitted))) {
			
    		log.startStep("Check the '" + activityTypeSubmitted + "' folder type checkbox ");
			driver.click(element.getActivityTypeCheckbox(activityTypeSubmitted), driver.timeOut);
			log.endStep();
		}
    	
        log.startStep( "Select the activity 'Assistant Number Change' for the 'Survey Submitted' option" );
        driver.select( "//tr[1]/td[2]/select[@class='iaActivities']" )
              .selectByVisibleText( "Assistant Number Change" );

        log.endStep();
        return this;
    }

    public IAConnection selectActivityForRSVPAccepted() throws Exception {

    	String activityTypeAccepted = "Survey Accepted";
    	
    	driver.waitUntilElementIsLocated(element.getActivityTypeCheckbox(activityTypeAccepted), driver.timeOut);
		driver.waitUntilElementIsClickable(element.getActivityTypeCheckbox(activityTypeAccepted));
		
    	if (!driver.isChecked(element.getActivityTypeCheckbox(activityTypeAccepted))) {
			
    		log.startStep("Check the '" + activityTypeAccepted + "' folder type checkbox ");
			driver.click(element.getActivityTypeCheckbox(activityTypeAccepted), driver.timeOut);
			log.endStep();
		}
    	
        log.startStep( "Select the activity 'E-mail Address Change' for the 'Survey Accepted' option" );
        driver.select( "//tr[2]/td[2]/select[@class='iaActivities']" )
              .selectByVisibleText( "E-mail Address Change" );
        log.endStep();

        return this;
    }

    public IAConnection selectActivityForRSVPDeclined() throws Exception {
    	
    	String activityTypeDeclined = "Survey Declined";
    	
    	driver.waitUntilElementIsLocated(element.getActivityTypeCheckbox(activityTypeDeclined), driver.timeOut);
		driver.waitUntilElementIsClickable(element.getActivityTypeCheckbox(activityTypeDeclined));
		
    	if (!driver.isChecked(element.getActivityTypeCheckbox(activityTypeDeclined))) {
			
    		log.startStep("Check the '" + activityTypeDeclined + "' folder type checkbox ");
			driver.click(element.getActivityTypeCheckbox(activityTypeDeclined), driver.timeOut);
			log.endStep();
		}

        log.startStep( "Select the activity 'Job Title Change' for the 'SSurvey Declined' option" );
        driver.select( "//tr[3]/td[2]/select[@class='iaActivities']" )
              .selectByVisibleText( "Job Title Change" );
        log.endStep();

        return this;
    }
    
    public IAConnection selectActivities(String[] activities) throws Exception {
    	
    	for (int i = 0; i < activities.length; i++) {
			
    		driver.waitUntilElementIsLocated(element.getActivityTypeCheckbox(activities[i]), driver.timeOut);
    		driver.waitUntilElementIsClickable(element.getActivityTypeCheckbox(activities[i]));
    		
        	if (!driver.isChecked(element.getActivityTypeCheckbox(activities[i]))) {
    			
        		log.startStep("Check the '" + activities[i] + "' folder type checkbox ");
    			driver.click(element.getActivityTypeCheckbox(activities[i]), driver.timeOut);
    			log.endStep();
    			
    			log.startStep("Verify that the checkbox is successfully checked for activity with name: " + activities[i]);				
				log.endStep(driver.isChecked(element.getActivityTypeCheckbox(activities[i])));
    		}
		}    	
    	
    	return this;
    }
    
    public IAConnection selectActivityMappings(String[] activityTypes, String[] activityMappings, boolean isCompanyLinked) throws Exception {
    	
    	selectActivities(activityTypes);
    	
    	for (int i = 0; i < activityTypes.length; i++) {
			
    		// This two lines of code cause a delay in the execution (if we have a problem with allocating the elements needs to be commented out
    		/*driver.waitUntilElementIsLocated(element.getActivityMappingDropdown(activityMappings[i]), driver.timeOut); 
    		driver.waitUntilElementIsClickable(element.getActivityMappingDropdown(activityMappings[i]));*/
    		
        	log.startStep("Map activity of type: " + activityTypes[i] + " to mapping: " + activityMappings[i]);
            driver.select( element.getActivityMappingDropdown(activityTypes[i]) ).selectByVisibleText( activityMappings[i] );
        	log.endStep();
        	
        	startEditingActivityType(activityTypes[i]).toggleLinkToCompanyFeature(isCompanyLinked).
			connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ACTIVITY);
		}    	
    	
    	return this;
    }
    
    public IAConnection deselectActivities(String[] activities) throws Exception {
    	
    	for (int i = 0; i < activities.length; i++) {
			
    		driver.waitUntilElementIsLocated(element.getActivityTypeCheckbox(activities[i]), driver.timeOut);
    		driver.waitUntilElementIsClickable(element.getActivityTypeCheckbox(activities[i]));
    		
        	if (driver.isChecked(element.getActivityTypeCheckbox(activities[i]))) {
    			
        		log.startStep("Check the '" + activities[i] + "' folder type checkbox ");
    			driver.click(element.getActivityTypeCheckbox(activities[i]), driver.timeOut);
    			log.endStep();
    			
    			log.startStep("Verify that the checkbox is successfully un-checked for activity with name: " + activities[i]);				
				log.endStep(!driver.isChecked(element.getActivityTypeCheckbox(activities[i])));
    		}
		}    	
    	
    	return this;
    }
	
    public IAConnection addUsers(String[] usersArr) throws Exception {
		
    	for (int i = 0; i < usersArr.length; i++) {
			
    		if (!driver.isElementPresent(element.getUserRecordWithInjectedToken(usersArr[i]), driver.timeOut)) {
				
    			connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
    			injectUserToken(new String[] {usersArr[i]});
    			connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE);
    			
			} else {
				
				log.startStep(usersArr[i] + " has already token injected");
				log.endStep();
			}
		}    	
    	
    	return this;
	}
	
    public IAConnection deleteUsers(String[] usersArr) throws Exception {
		
    	
    	
    	return this;
	}
	
    public IAConnection setPermissions(String[] usernames, boolean isActivityEnabled, boolean isSurveyEnabled) throws Exception {
		
    	for (int i = 0; i < usernames.length; i++) {
		
    		setActivitiesPermissions(usernames[i], isActivityEnabled);
        	setSurveysPermissions(usernames[i], isSurveyEnabled);
		}
    	
    	return this;
	}

	private void setActivitiesPermissions(String username, boolean isActivityEnabled) throws Exception {
		
		if (isActivityEnabled) {
			
    		if (!driver.isChecked(element.getUserRecordActivitiesPermissionCheckbox(username))) {
    			
        		log.startStep("Enable Activities permission for username: " + username);
        		driver.waitUntilElementIsClickable(element.getUserRecordActivitiesPermissionCheckbox(username));
        		driver.click(element.getUserRecordActivitiesPermissionCheckbox(username), driver.timeOut);
        		log.endStep();
        		
    		} else {
				
    			log.startStep("Activities permissions is already enabled for username: " + username);
    			log.endStep();
			}
    		
		} else {
			
			if (driver.isChecked(element.getUserRecordActivitiesPermissionCheckbox(username))) {
    			
        		log.startStep("Disable Activities permission for username: " + username);
        		driver.waitUntilElementIsClickable(element.getUserRecordActivitiesPermissionCheckbox(username));
        		driver.click(element.getUserRecordActivitiesPermissionCheckbox(username), driver.timeOut);
        		log.endStep();
        		
    		} else {
				
    			log.startStep("Activities permissions is already disabled for username: " + username);
    			log.endStep();
			}
		}
	}
	
	private void setSurveysPermissions(String username, boolean isActivityEnabled) throws Exception {
		
		if (isActivityEnabled) {
			
    		if (!driver.isChecked(element.getUserRecordSurveysPermissionCheckbox(username))) {
    			
        		log.startStep("Enable Surveys permission for username: " + username);
        		driver.waitUntilElementIsClickable(element.getUserRecordSurveysPermissionCheckbox(username));
        		driver.click(element.getUserRecordSurveysPermissionCheckbox(username), driver.timeOut);
        		log.endStep();
        		
    		} else {
				
    			log.startStep("Surveys permissions is already enabled for username: " + username);
    			log.endStep();
			}
    		
		} else {
			
			if (driver.isChecked(element.getUserRecordSurveysPermissionCheckbox(username))) {
    			
        		log.startStep("Disable Surveys permission for username: " + username);
        		driver.waitUntilElementIsClickable(element.getUserRecordSurveysPermissionCheckbox(username));
        		driver.click(element.getUserRecordSurveysPermissionCheckbox(username), driver.timeOut);
        		log.endStep();
        		
    		} else {
				
    			log.startStep("Surveys permissions is already disabled for username: " + username);
    			log.endStep();
			}
		}
	}
    
    public IAConnection connectionWizardButtonActions(ConnectionWizardButtonAction actionType) throws Exception {
    	
    	switch (actionType) {
    	
    	case CONNECT:			
			
			log.startStep("Click on the 'Connect' button");
			driver.click(element.connectionWizardConnectBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case BACK:			
			
			log.startStep("Click on the 'Back' button");
			driver.click(element.connectionWizardBackBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case NEXT:
			
			log.startStep("Click on the 'Next' button");
			driver.click(element.connectionWizardNextBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case CANCEL:
			
			log.startStep("Click on the 'Cancel' button");
			driver.click(element.connectionWizardCancelBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;	
			
		case SAVE:
			
			log.startStep("Click on the 'Save' button");
			driver.click(element.connectionWizardSaveBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case SAVE_AND_CLOSE_ACTIVITY:
			
			log.startStep("Click on the 'Save and close' button");
			driver.click(element.connectionWizardSaveAndCloseActivity, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case CONFIGURE_FOLDERS:
			
			log.startStep("Click on the 'Configure folders' button");
			driver.click(element.connectionWizardConfigureFoldersBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;	
			
		case ADD_USERS:
			
			log.startStep("Click on the 'Add users' button");
			driver.click(element.connectionWizardAddUsersBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;	
			
		case SAVE_AND_CLOSE_ALL:
			
			log.startStep("Click on the 'Save and close' button for Connection Wizard");
			driver.click(element.connectionWizardSaveAndCloseAll, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;

		default:			
			throw new InvalidParameterException( "The following parameter 'actionType' has an invalid value: '"
                    + actionType + "'" );
		}
    	
    	return this;
    }
    
    public IAConnection removeUserToken(String[] usernamesArr) throws Exception {
    	
    	if (usernamesArr.length <= 0) {
			
    		throw new InvalidParameterException("Not a valid parameter - 'usernamesArr'. Array has no elements.");
    		
		} else {
			
			for (int i = 0; i < usernamesArr.length; i++) {
				
	    		log.startStep("Click on the 'Remove token' button for username: " + usernamesArr[i]);
	    		driver.click(element.getUserRecordRemoveTokenBtn(usernamesArr[i]), driver.timeOut);
	    		driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	    	    driver.waitForPageToLoad();
	    		log.endStep();
			}    	
		}    	
    	
    	return this;
    }
    
    public IAConnection injectUserToken(String[] usernamesArr) throws Exception {
    	
    	if (usernamesArr.length <= 0) {
    		
    		throw new InvalidParameterException("Not a valid parameter - 'usernamesArr'. Array has no elements.");

		} else {
			
			for (int i = 0; i < usernamesArr.length; i++) {
				
				log.startStep("Click on the 'Inject token' button for username: " + usernamesArr[i]);
				driver.click(element.getUserRecordInjectTokenBtn(usernamesArr[i]), driver.timeOut);
				driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	    	    driver.waitForPageToLoad();
				log.endStep();
			}			
		}
    	    	
    	return this;
    }
    
    public IAConnection startEditingActivityType(String activityType) throws Exception {
    	
    	log.startStep("Click on the Edit button for activity type: " + activityType);
    	driver.click(element.getActivityTypeEditButton(activityType), driver.timeOut);
    	driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	    driver.waitForPageToLoad();
    	log.endStep();
    	
    	return this;
    }
    
    public IAConnection toggleLinkToCompanyFeature(boolean isEnable) throws Exception {
    	
    	if (isEnable) {   		
        	
    		driver.waitUntilElementIsLocated(element.linkToCompanyCheckbox, driver.timeOut);
        	driver.waitUntilElementIsClickable(element.linkToCompanyCheckbox);
        	
        	if (!driver.isChecked(element.linkToCompanyCheckbox)) {
				
        		log.startStep("Enable the 'Link to Company' activity feature");            	
        		driver.click(element.linkToCompanyCheckbox, driver.timeOut);
            	log.endStep();
			}        	
        	
        	log.startStep("Verify that the checkbox for feature 'Link to Company' is successfully checked");				
    		log.endStep(driver.isChecked(element.linkToCompanyCheckbox));
    		
		} else {
			
			driver.waitUntilElementIsLocated(element.linkToCompanyCheckbox, driver.timeOut);
        	driver.waitUntilElementIsClickable(element.linkToCompanyCheckbox);
        	
        	if (driver.isChecked(element.linkToCompanyCheckbox)) {
				
        		log.startStep("Disable the 'Link to Company' activity feature");            	
        		driver.click(element.linkToCompanyCheckbox, driver.timeOut);
            	log.endStep();
			}        	
        	
        	log.startStep("Verify that the checkbox for feature 'Link to Company' is successfully un-checked");				
    		log.endStep(!driver.isChecked(element.linkToCompanyCheckbox));		
		}    	
    	
    	return this;
    }
    
    public IAConnection closeConnectionWizardPage(ConnectionWizardPages page) throws Exception {
    	
    	switch (page) {
    	
		case LOGIN:
			
			log.startStep("Click on the 'X' button from Login connection wizard page");
			driver.click(element.connectionWizardLoginPageCloseBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case USERS:
			
			log.startStep("Click on the 'X' button from Users connection wizard page");
			driver.click(element.manageUsersCloseBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case ADD_USERS:
			
			log.startStep("Click on the 'X' button from Add Users connection wizard page");
			driver.click(element.addUsersCloseBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case SETTINGS:
			
			log.startStep("Click on the 'X' button from Folder Types connection wizard page");
			driver.click(element.folderTypesCloseBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case CONFIGURE_FOLDERS:
			
			log.startStep("Click on the 'X' button from Configure Folders connection wizard page");
			driver.click(element.configureFoldersCloseBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;
			
		case ACTIVITIES:
			
			log.startStep("Click on the 'X' button from Activities connection wizard page");
			driver.click(element.activitiesCloseBtn, driver.timeOut);
			driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		    driver.waitForPageToLoad();
			log.endStep();
			
			break;

		default:
			break;
		}
    	
    	return this;    	
    }
}
