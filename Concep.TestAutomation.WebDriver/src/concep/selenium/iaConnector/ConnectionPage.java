package concep.selenium.iaConnector;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.IAConnection.ConnectionWizardButtonAction;
import concep.selenium.send.IAConnection.ConnectionWizardPages;

public class ConnectionPage extends BaseIAconnector {
				
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

        driver.stopSelenium();
        
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyAccessConnectionWizardPage() throws Exception {
		        
		log.startTest("Verify that Connection Wizard can be successfully accessed and all its elements are successfully displayed");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.selectExistingConnection();
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardLoginPageLbl,
													   element.connectionAddress,
													   element.connectionUserNameText,
													   element.connectionPasswordText,
													   element.connectionWizardBackBtn,
													   element.connectionWizardConnectBtn},
										 new String[] {element.connectionWizardLoginPageLabelTxt,
													   "Address readonly input field",
													   "Username input field",
													   "Password input field",
													   "Back button",
													   "Connect button"},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyDisplayRequiredFieldsValidationMessageEmptyUsernameAndPassword() throws Exception {
		        
		log.startTest("Verify that a validation message for required field will be successfully displayed when Username and Password fields are empty");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection("", "");
			
			send.verifyDisplayedElements(new String[] {element.getConnectionRequiredFieldValidationMsg("Username"),
													   element.getConnectionRequiredFieldValidationMsg("Password")},
										 new String[] {"Username validation message",
													   "Password validation message"},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyDisplayRequiredFieldsValidationMessageEmptyUsername() throws Exception {
		        
		log.startTest("Verify that a validation message for required field will be successfully displayed when Username field is empty");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection("", iaConnectionPassword);
			
			send.verifyDisplayedElements(new String[] {element.getConnectionRequiredFieldValidationMsg("Username")},
										 new String[] {"Username validation message"},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyDisplayRequiredFieldsValidationMessageEmptyPassword() throws Exception {
		        
		log.startTest("Verify that a validation message for required field will be successfully displayed when Password field is empty");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, "");
			
			send.verifyDisplayedElements(new String[] {element.getConnectionRequiredFieldValidationMsg("Password")},
										 new String[] {"Password validation message"},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyDisplayValidationMessageNotMatchingUsernameAndPassword() throws Exception {
		        
		log.startTest("Verify that a validation message will be displayed when Username and Password are not matching");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection("notmatchingcredentials", "notmatchingcredentials");
			
			send.verifyDisplayedElements(new String[] {element.connectionLoginErrorMsg},
										 new String[] {element.connectionLoginErrorTxt},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyQuitFromConnectionWizardByClickingBackButtonFromLoginPage() throws Exception {
		        
		log.startTest("Verify that user can successfully quit from the Connection Wizard by clicking on the Back button on the Login page");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().iaConnection.
			selectExistingConnection().connectionWizardButtonActions(ConnectionWizardButtonAction.BACK);
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardLoginPageLbl,
													   element.connectionAddress,
													   element.connectionUserNameText,
													   element.connectionPasswordText,
													   element.connectionWizardBackBtn,
													   element.connectionWizardConnectBtn},
										 new String[] {element.connectionWizardLoginPageLabelTxt,
													   "Address readonly input field",
													   "Username input field",
													   "Password input field",
													   "Back button",
													   "Connect button"},
										 false);
			
			send.verifyDisplayedElements(new String[] {element.connectionWindow},
										 new String[] {"Existing connection"},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyQuitFromConnectionWizardLoginPageByClickingCloseButton() throws Exception {
		        
		log.startTest("Verify that user is able to quit from the Connection wizard from Login page by clicking on the X (close) button");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.selectExistingConnection().closeConnectionWizardPage(ConnectionWizardPages.LOGIN);
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardLoginPageLbl,
													   element.connectionAddress,
													   element.connectionUserNameText,
													   element.connectionPasswordText,
													   element.connectionWizardBackBtn,
													   element.connectionWizardConnectBtn},
										 new String[] {element.connectionWizardLoginPageLabelTxt,
													   "Address readonly input field",
													   "Username input field",
													   "Password input field",
													   "Back button",
													   "Connect button"},
										 false);

			send.verifyDisplayedElements(new String[] {element.connectionWindow},
										 new String[] {"Existing connection"},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyLoginToConnectionWithExisitngUser() throws Exception {
		        
		log.startTest("Verify that user is able to successfully login to an existing connection with valid credentials");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword);
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardLoginPageLbl},
									     new String[] {element.connectionWizardLoginPageLabelTxt},
									     false);
			
			send.verifyDisplayedElements(new String[] {element.connectionUsersPageLabel},
										 new String[] {element.connectionUsersPageLabelTxt},
										 true);		
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyAccessConnectionWizardUsersPage() throws Exception {
		        
		log.startTest("Verify that user is able to successfully access 'Users' page from the Connection Wizard");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword);
			
			send.verifyDisplayedElements(new String[] {element.connectionUsersPageLabel,
													   element.manageUsersContainer,
													   element.addUsersBtn,
													   element.connectionWizardNextBtn,
													   element.manageUsersCloseBtn},
										 new String[] {element.connectionUsersPageLabelTxt,
													   "Manage users container",
													   "Add users button",
													   "Next button",
													   "Close button"},
										 true);		
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyAccessConnectionWizardAddUsersPage() throws Exception {
		        
		log.startTest("Verify that user is able to successfully access 'Add Users' page from the Connection Wizard");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword).
			connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.addUsersContainer,
													   element.connectionWizardCancelBtn,
													   element.connectionWizardSaveBtn,
													   element.addUsersCloseBtn},
										 new String[] {"Add users container",
													   "Cancel button",
													   "Save button",
													   "Close button"},
										 true);		
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void unsuccessfullyDisplayUserWithInjectedTokenWhenCloseButtonClicked() throws Exception {
		        
		dateTime = driver.getDateTime(dateTimeFormat);
		
		String username = "username" + dateTime;
		String password = "Aa111111!";
		String firstName = "firstName" + dateTime;
		String lastName = "lastName" + dateTime;
		String email = "email" + dateTime + emailAddressDomainPart;
		String role = "User";
		
		log.startTest("Verify that no token will be injected and no users added to the list of users with injected tokens when close button clicked");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToAdmin().goToUsers().
			startUserCreation().fillUserDetails(username, password, firstName, lastName, email, role);
			
			send.goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
										 new String[] {"Username with not injected token: " + username},
										 true);		
			
			send.iaConnection.injectUserToken(new String[] {username});
			send.iaConnection.closeConnectionWizardPage(ConnectionWizardPages.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithInjectedToken(username)},
					 new String[] {"Username with injected token: " + username},
					 false);		
			
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
					 new String[] {"Username with not injected token: " + username},
					 true);	
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyDisplayAllAccountsWithNotInjectedToken() throws Exception {
		        
		dateTime = driver.getDateTime(dateTimeFormat);
		
		String username = "username" + dateTime;
		String password = "Aa111111!";
		String firstName = "firstName" + dateTime;
		String lastName = "lastName" + dateTime;
		String email = "email" + dateTime + emailAddressDomainPart;
		String role = "User";
		
		log.startTest("Verify that all the users that have not injected token will be successfully displayed on the Connection wizard Users page");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToAdmin().goToUsers().
			startUserCreation().fillUserDetails(username, password, firstName, lastName, email, role);
			
			send.goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
										 new String[] {"Username with not injected token: " + username},
										 true);					
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyDisplayUserWithInjectedToken() throws Exception {
		        
		dateTime = driver.getDateTime(dateTimeFormat);
		
		String username = "username" + dateTime;
		String password = "Aa111111!";
		String firstName = "firstName" + dateTime;
		String lastName = "lastName" + dateTime;
		String email = "email" + dateTime + emailAddressDomainPart;
		String role = "User";
		
		log.startTest("Verify that if a token is injected for specific user, the same one will be "
				+ "removed from the list with not injected tokens and will be displayed on the list with the injected ones");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToAdmin().goToUsers().
			startUserCreation().fillUserDetails(username, password, firstName, lastName, email, role);
			
			send.goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
										 new String[] {"Username with not injected token: " + username},
										 true);		
			
			send.iaConnection.injectUserToken(new String[] {username}).
			connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithInjectedToken(username)},
					 new String[] {"Username with injected token: " + username},
					 true);		
			
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
					 new String[] {"Username with not injected token: " + username},
					 false);	
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	//@Test(groups = { "connections", "all-tests" })
	public void successfullyDisplayUserWithNoInjectedTokenAfterTokenIsRemoved() throws Exception {
		        
		dateTime = driver.getDateTime(dateTimeFormat);
		
		String username = "username" + dateTime;
		String password = "Aa111111!";
		String firstName = "firstName" + dateTime;
		String lastName = "lastName" + dateTime;
		String email = "email" + dateTime + emailAddressDomainPart;
		String role = "User";
		
		log.startTest("Verify that if injected token is removed for specific user, the same one will be "
				+ "removed from the list with injected tokens and will be displayed on the list with not injected ones");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToAdmin().goToUsers().
			startUserCreation().fillUserDetails(username, password, firstName, lastName, email, role);
			
			send.goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
										 new String[] {"Username with not injected token: " + username},
										 true);		
			
			send.iaConnection.injectUserToken(new String[] {username}).
			connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE);			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithInjectedToken(username)},
					 new String[] {"Username with injected token: " + username},
					 true);		
			
			send.iaConnection.removeUserToken(new String[] {username})
			.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);	
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
					 new String[] {"Username with not injected token: " + username},
					 true);	
			
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.CANCEL);
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithInjectedToken(username)},
					 new String[] {"Username with injected token: " + username},
					 false);	
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void unsuccessfullyDisplayUserWithInjectedTokenWhenCancelButtonClicked() throws Exception {
		        
		dateTime = driver.getDateTime(dateTimeFormat);
		
		String username = "username" + dateTime;
		String password = "Aa111111!";
		String firstName = "firstName" + dateTime;
		String lastName = "lastName" + dateTime;
		String email = "email" + dateTime + emailAddressDomainPart;
		String role = "User";
		
		log.startTest("Verify that user will not be displayed in the list with injected token users when injection is canceled");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToAdmin().goToUsers().
			startUserCreation().fillUserDetails(username, password, firstName, lastName, email, role);
			
			send.goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
										 new String[] {"Username with not injected token: " + username},
										 true);		
			
			send.iaConnection.injectUserToken(new String[] {username}).
			connectionWizardButtonActions(ConnectionWizardButtonAction.CANCEL);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithInjectedToken(username)},
					 new String[] {"Username with injected token: " + username},
					 false);		
			
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.ADD_USERS);
			
			send.verifyDisplayedElements(new String[] {element.getUserRecordWithoutInjectedToken(username)},
					 new String[] {"Username with not injected token: " + username},
					 true);	
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyAccessConnectionWizardSettingsPage() throws Exception {
		        
		String[] folderTypes = new String[] {
			
				"Personnel",
				"Client Analysis",
				"Archive",
				"Other Administrative",
				"Data Management",
				"Information",
				"Other Contact Types",
				"Status",
				"Organizations",
				"Clients and Prospects",
				"JD Test Folders",
				"Marketing List (with sponsorship)",
				"Other",
				"Marketing List (no sponsorship)",
				"Public"
		};
		
		log.startTest("Verify that user is able to successfully access 'Settings' page from the Connection Wizard");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardConfigureFoldersBtn,
													   element.connectionWizardNextBtn},
										 new String[] {"Configure folders button",
													   "Next button"},
										 true);		
			
			for (int i = 0; i < folderTypes.length; i++) {
				
				send.verifyDisplayedElements(new String[] {element.getFolderTypeCheckbox(folderTypes[i])},
											 new String[] {folderTypes[i]},
											 true);
			}
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullySelectAllFolderTypes() throws Exception {
		        
		String[] folderTypes = new String[] {
			
				"Personnel",
				"Client Analysis",
				"Archive",
				"Other Administrative",
				"Data Management",
				"Information",
				"Other Contact Types",
				//"Status", - not in use
				"Organizations",
				"Clients and Prospects",
				"JD Test Folders",
				"Marketing List (with sponsorship)",
				"Other",
				"Marketing List (no sponsorship)",
				"Public"
		};
		
		log.startTest("Verify that user is able to select all Folder Types checkboxes");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			
			for (int i = 0; i < folderTypes.length; i++) {
				
				send.iaConnection.selectFolderTypes(folderTypes);			
			}
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyDeselectAllFolderTypes() throws Exception {
		        
		String[] folderTypes = new String[] {
			
				"Personnel",
				"Client Analysis",
				"Archive",
				"Other Administrative",
				"Data Management",
				"Information",
				"Other Contact Types",
				//"Status", - not in use
				"Organizations",
				"Clients and Prospects",
				"JD Test Folders",
				"Marketing List (with sponsorship)",
				"Other",
				"Marketing List (no sponsorship)",
				"Public"
		};
		
		log.startTest("Verify that user is able to deselect all Folder Types checkboxes");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			
			for (int i = 0; i < folderTypes.length; i++) {
				
				send.iaConnection.selectFolderTypes(folderTypes);			
			}
			
			for (int i = 0; i < folderTypes.length; i++) {
				
				send.iaConnection.deselectFolderTypes(folderTypes);			
			}
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyQuitFromConnectionWizardSettingsPageByClickingCloseButton() throws Exception {
		        
		log.startTest("Verify that user is able to quit from the Connection wizard from Settings page by clicking on the X (close) button");
			
		try {				
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardConfigureFoldersBtn,
													   element.connectionWizardNextBtn},
										 new String[] {"Configure folders button",
													   "Next button"},
										 true);				
			
			send.iaConnection.closeConnectionWizardPage(ConnectionWizardPages.SETTINGS);
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardConfigureFoldersBtn,
													   element.connectionWizardNextBtn},
										 new String[] {"Configure folders button",
													   "Next button"},
										 false);		
			
			send.verifyDisplayedElements(new String[] {element.connectionWindow},
										 new String[] {"Existing connection"},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullyAccessConnectionWizardActivitiesPage() throws Exception {
		        
		String[] acitivties = new String[] {
				
				"Survey Submitted",
				"Survey Accepted",
				"Survey Declined",
				"Campaign Sent",
				"Campaign Viewed",
				"Compound View",
				"Page View",
				"Forward to a Friend",
				"URL Click",
				"Account Level Opt Out",
				"Client Level Opt Out",
				"Abuse Reported",
				"Undeliverable (Permanent)",
				"Undeliverable (Temporary)",
				"Campaign Resent",
				"Preferences Updated"
		};
		
		log.startTest("Verify that user is able to successfully access 'Activities' page from the Connection Wizard, to interact with it"
				+ "and to display all the fields that belong to a single activity");
			
		try {		
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);			
			Thread.sleep(1500); // TODO: Needs to be handled
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			
			send.verifyDisplayedElements(new String[] {element.connectionWizardActivitiesPageLabel,
													   element.connectionWizardSaveAndCloseAll},
										 new String[] {element.connectionWizardActivitiesPageLabelTxt,
													   "Save and close button"},
										 true);	
			
			//send.iaConnection.selectActivities(acitivties);
			//send.iaConnection.deselectActivities(acitivties);
			
			for (int i = 0; i < acitivties.length; i++) {				
				
		        send.verifyDisplayedElements(new String[] {element.getActivityMappingDropdown(acitivties[i]),
		        										   element.getActivityTypeEditButton(acitivties[i])},
		        							 new String[] {acitivties[i] + " mapping dropdown",
		        										   acitivties[i] + " edit button"},
		        							 true);
		        
//				send.iaConnection.startEditingActivityType(acitivties[i]);
//				send.verifyDisplayedElements(new String[] {element.mailMergeTagDropdown,
//														   element.editActivityTitle,
//														   element.editActivityDescription,
//														   element.linkToCompanyCheckbox,
//														   element.editActivityCancelBtn,
//														   element.editActivitySaveAndCloseBtn},
//											 new String[] {"Mail Merge dropdown for activity " + acitivties[i],
//														   "Activity Title input for activity " + acitivties[i],
//														   "Activity Description textarea for activity " + acitivties[i],
//														   "Link to Company for activity " + acitivties[i],
//														   "Cancel button for activity " + acitivties[i],
//														   "Save and close button for activity " + acitivties[i]},
//											 true);
//				
//				send.iaConnection.toggleLinkToCompanyFeature(true);
//				send.iaConnection.toggleLinkToCompanyFeature(false);
			}
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void successfullySaveActivityChangesWhenSaveButtonClicked() throws Exception {
				
		String activityType = "Campaign Sent";
		
		log.startTest("Verify that user is able to successfully save activity changes when save button is clicked");
			
		try {		
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);			
			Thread.sleep(1500); // TODO: Needs to be handled
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT).
			startEditingActivityType(activityType).toggleLinkToCompanyFeature(true).
			connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ACTIVITY).
			startEditingActivityType(activityType);
			
			log.startStep("Verify that the checkbox for feature 'Link to Company' is successfully checked");				
    		log.endStep(driver.isChecked(element.linkToCompanyCheckbox));
    		
    		send.iaConnection.toggleLinkToCompanyFeature(false).
    		connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ACTIVITY).
			startEditingActivityType(activityType);
			
			log.startStep("Verify that the checkbox for feature 'Link to Company' is successfully un-checked");				
    		log.endStep(!driver.isChecked(element.linkToCompanyCheckbox));		
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	
	@Test(groups = { "connections", "all-tests" })
	public void unsuccessfullySaveActivityChangesWhenSaveIsCanceled() throws Exception {
				
		String activityType = "Campaign Sent";
		
		log.startTest("Verify that activity changes are not saved when save is canceled");
			
		try {		
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);			
			Thread.sleep(1500); // TODO: Needs to be handled
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT).
			startEditingActivityType(activityType).toggleLinkToCompanyFeature(true).
			connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ACTIVITY).
			startEditingActivityType(activityType);
			
			log.startStep("Verify that the checkbox for feature 'Link to Company' is successfully checked");				
    		log.endStep(driver.isChecked(element.linkToCompanyCheckbox));
    		
    		send.iaConnection.toggleLinkToCompanyFeature(false).
    		connectionWizardButtonActions(ConnectionWizardButtonAction.CANCEL).
			startEditingActivityType(activityType);
			
			log.startStep("Verify that the checkbox for feature 'Link to Company' is successfully checked");				
    		log.endStep(driver.isChecked(element.linkToCompanyCheckbox));		
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
	@Test(groups = { "connections", "all-tests" })
	public void verifyAfterConnectionSendClientAdminIsDisplayed() throws Exception {
		        
		
		try {		
			
			send.loginToSend(iaSendUser, iaSendPassword).goToConnectionsPage().
			iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword)
			.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);			
			Thread.sleep(1500); // TODO: Needs to be handled
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.NEXT);
			send.iaConnection.connectionWizardButtonActions(ConnectionWizardButtonAction.SAVE_AND_CLOSE_ALL);
			send.iaConnection.loginToExistingConnection(iaConnectionUsername, iaConnectionPassword);
			
			send.verifyDisplayedElements(new String[] {element.connectionUsersPageLabel,
													   element.sendClientAdminLabel},
										 new String[] {element.connectionUsersPageLabelTxt,
													   element.sendClientAdminTxt},
										 true);
			
		} catch (Exception e) {
				
			log.endStep( false );
			throw e;
		}
			
		log.endTest();
    }
}
