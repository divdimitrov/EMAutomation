package concep.selenium.ia;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.IA.Api.InterAction;
import concep.json.Json;
import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import concep.selenium.iaConnector.IAActivities;
import concep.selenium.iaConnector.IAActivities.Activities;
import concep.selenium.iaConnector.IAattributes.ContactDetails;
import concep.selenium.mailinator.Mailinator;
import concep.selenium.send.Campaign;
import concep.selenium.send.Contact;
import concep.selenium.send.Elements;
import concep.selenium.send.Send;
import concep.selenium.send.IAConnection.ActivityMappings;
import concep.selenium.send.IAConnection.ActivityTypes;

public class BaseIA {
	protected final String[] sendAccounts = new String[] {

			"TestAutomation_QA_IA_Connector_Plat2",
					"TestAutomation_QA_IA_Connector_Plat2_Templates" };

			protected final String[] folderTypes = new String[] {

			"Data Management", "Marketing List (with sponsorship)",
					"Marketing List (no sponsorship)" };

			protected final String seachPeopleFolder = "Firm Contacts - People";
			protected final String searchCompaniesFolder = "Firm Contacts - Companies";

			protected final String[] linkFolders = new String[] {

			"JD Test 1", "JD Test 2", "JD Test 3" };

			protected final String sourceFolder = "QA Source Folder";

			// TODO: Have to find a fix for the issue with clicking the edit button of an activity displayed near to the container border
			protected final String[] campaignActivities = new String[] {

			ActivityTypes.CAMPAIGN_SENT.stringValue,
					ActivityTypes.CAMPAIGN_VIEWED.stringValue,
					ActivityTypes.PAGE_VIEW.stringValue,
					/*ActivityTypes.URL_CLICK.stringValue*/ };

			protected final String[] campaignActivityMappings = new String[] {

			ActivityMappings.ASSISTANT_NUMBER_CHANGE.stringValue,
					ActivityMappings.MOBILE_NUMBER_CHANGE.stringValue,
					ActivityMappings.COMPUTER_NUMBER_CHANGE.stringValue,
					/*ActivityMappings.FAX_NUMBER_CHANGE.stringValue*/ };

			protected final String[] surveyActivities = new String[] {

			ActivityTypes.SURVEY_SUBMITTED.stringValue,
					ActivityTypes.SURVEY_ACCEPTED.stringValue,
					ActivityTypes.SURVEY_DECLINED.stringValue, };

			protected final String[] surveyActivityMappings = new String[] {

			ActivityMappings.REMINDED_ABOUT.stringValue,
					ActivityMappings.ASSISTANT_NUMBER_CHANGE.stringValue,
					ActivityMappings.PHONE_EXTENSION_CHANGE.stringValue, };

			protected final String dateTimeFormat = "ddMMyy-HHmmss";
			protected final String emailAddressDomainPart = "@mailinator.com";
			
			protected static GenericSeleniumWebDriver driver = DriverAccessor.getDriverInstance();
			
			public String dateTime;

			private String iaUserName;
			private String iaPassword;
			private String iaUrl;

			protected String campTemplateName;
			protected String campaignName;
			protected String campaignSubject;

			protected String contactFirstName;
			protected String contactLastName;
			protected String contactEmail;

			protected String iaContactEmail;
			protected String iaContactLastName;

			protected String iaContactEmailWithLinkedCompany;
			protected String iaContactFirstNameWithLinkedCompany;
			protected String iaContactLastNameWithLinkedCompany;
			protected String iaCompanyWithLinkedContact;

			protected String surveyType1;
			protected String surveyType2;
			protected String surveyType3;
			protected String surveyType4;
			protected String eventFolderAdministrative;
			protected String AdminCompanyOnly;
			protected String AdminContactOnly;
			protected String eventFolderWithSponsor;
			protected String MarketingListSposorCompanyOnly;
			protected String MarketingListSposorContactOnly;
			protected String eventFolderNoSponsor;
			protected String MarketingListNoSponsorCompanyOnly;
			protected String MarketingListNoSponsorContactOnly;
			protected String iaExistingContact;
			protected String iaExistingCompany;
			protected String iaSendUser;
			protected String iaSendPassword;
			protected String sendSuperUser;
			protected String sendSuperPassword;
			protected String iaConnectionName;
			protected String iaConnectionUsername;
			protected String iaConnectionPassword;
			protected String surveyEnableXPath;
			protected boolean isJson;

			protected List<String> questionsArray;
			protected List<String> answersArray;
			protected List<String> phoneNumbersArray;
			protected InterAction interAction;
			
			protected LogResults log = new LogResults(this.driver);
			protected Send send = new Send();
			protected Elements element = new Elements();
			protected Json json = new Json();
			protected Mailinator mailinator = new Mailinator();

			protected ContactDetails contactDetails;
			protected IAActivities iaActivities;
			protected List<Contact> contacts;

			@BeforeMethod(alwaysRun = true)
			@Parameters({ "config" })
			public void startSelenium(
					@Optional("config/firefox.IAconnectorV1.1") String configLocation)
					throws Exception {

				driver.init(configLocation);
				surveyType1 = driver.config.getProperty("surveyType1");
				surveyType2 = driver.config.getProperty("surveyType2");
				surveyType3 = driver.config.getProperty("surveyType3");
				surveyType4 = driver.config.getProperty("surveyType4");
				eventFolderAdministrative = driver.config
						.getProperty("AdministrativeFolder");
				AdminCompanyOnly = driver.config.getProperty("AdminCompanyOnly");
				AdminContactOnly = driver.config.getProperty("AdminContactOnly");
				eventFolderWithSponsor = driver.config
						.getProperty("MarketingListWithSponsorFolder");
				MarketingListSposorCompanyOnly = driver.config
						.getProperty("MarketingListSposorCompanyOnly");
				MarketingListSposorContactOnly = driver.config
						.getProperty("MarketingListSposorContactOnly");
				eventFolderNoSponsor = driver.config
						.getProperty("MarketingListNoSponsorFolder");
				MarketingListNoSponsorCompanyOnly = driver.config
						.getProperty("MarketingListNoSponsorCompanyOnly");
				MarketingListNoSponsorContactOnly = driver.config
						.getProperty("MarketingListNoSponsorContactOnly");
				sendSuperUser = driver.config.getProperty("sendSuperUser");
				sendSuperPassword = driver.config.getProperty("sendSuperPassword");
				iaConnectionName = driver.config.getProperty("iaConnectionName");
				iaConnectionUsername = driver.config
						.getProperty("iaConnectionUsername");
				iaConnectionPassword = driver.config
						.getProperty("iaConnectionPassword");
				iaExistingContact = driver.config.getProperty("iaExistingContact");
				iaExistingCompany = driver.config.getProperty("iaExistingCompany");
				driver.url = driver.config.getProperty("url");
				iaUserName = driver.config.getProperty("iaConnectionUsername");
				iaPassword = driver.config.getProperty("iaConnectionPassword");
				iaUrl = driver.config.getProperty("iaConnectionName");
				surveyEnableXPath = driver.config.getProperty("surveyEnableXpath");

				this.interAction = new InterAction(iaUserName, iaPassword, iaUrl);

				isJson = Boolean.parseBoolean(driver.config.getProperty("isJson"));
			}

			@AfterMethod(alwaysRun = true)
			public void cleanup() {

				driver.stopSelenium();
			}

			protected Send loginToSend() throws Exception {

				send.loginToSend(iaSendUser, iaSendPassword);

				return send;
			}

			public void returnConnectionToDefault() throws Exception {

				// Log and Go to Connection Page
				loginToSend().goToConnectionsPage();

				send.iaConnection
						.loginToConnection(iaConnectionUsername, iaConnectionPassword)
						.clickNextToFolderTypesPage()
						.selectFolderTypes(send.iaConnection.iaFolders)
						.clickNextToFolderConfigurationPage()
						.selectFoldersConfiguration(seachPeopleFolder,	searchCompaniesFolder, linkFolders, sourceFolder, true)
						.clickNextToActivtiesPage();

				send.iaConnection.selectSurveyConnectionActivities()
				
						.saveAndCloseConnectionPage();

			}

			public void clearAndCreatConnection(String sourceFolder, boolean isSourced)
					throws Exception {

				// clear the connection settings
				returnConnectionToDefault();

				// Create new Connection to IA.
				/*send.iaConnection
						.loginToExistingConnection(iaConnectionUsername,
								iaConnectionPassword).clickNextToFolderTypesPage()
						.selectFolderTypes(send.iaConnection.iaFolders);

				send.iaConnection.selectAndSaveFolderConfiguration(sourceFolder,
						isSourced).clickNextToActivtiesPage();

				send.iaConnection.selectSurveyConnectionActivities()
						.saveAndCloseConnectionPage();

				log.startStep("Verify that the connection is created successfully");
				log.endStep(driver.isElementPresent(element.connectionWindow,
						driver.timeOut));*/
			}

			protected BaseIA addEmailConfirmation() throws Exception {

				send.iaSurvey.iaQuestion.showQuestionSettings("qTypetext")
						.addManadatoryOption();

				log.startStep("Check the 'Confirm Email Address' check-box");
				if (!driver.isChecked("//input[@id='chkConfirm']")) {
					driver.click("//input[@id='chkConfirm']", driver.timeOut);
				}
				log.endStep();

				send.iaSurvey.iaQuestion.hideQuestionSettings("qTypetext");
				return this;
			}

			protected BaseIA addEmailConfirmationContactMappings(
					String firstQuestion, String firstQuestionMapping,
					String secondQuestion, String secondQuestionMapping,
					String thirdQuestion, String thirdQuestionMapping) throws Exception {

				// A pop up Add Action Window appears
				log.startStep("Click 'Add Action' button");
				driver.click("//a/span[contains(text(), 'Add Action')]", driver.timeOut);
				driver.waitForAjaxToComplete(driver.ajaxTimeOut);
				log.endStep();

				log.startStep("Select 'Add Contact' radio button");
				driver.click("//input[@id='radContact']", driver.timeOut);
				log.endStep();

				log.startStep("Type 'Add Contact' in the 'Title' field");
				driver.type("//input[@id='frmATitle']", "Add Contact", driver.timeOut);
				log.endStep();

				log.startStep("Select mapping value for the " + firstQuestion
						+ " question");
				driver.select(
						"//td[contains(text(), '" + firstQuestion + "')]//..//select")
						.selectByVisibleText(firstQuestionMapping);
				log.endStep();

				log.startStep("Select mapping value for the " + secondQuestion
						+ " question");
				driver.select(
						"//td[contains(text(), '" + secondQuestion + "')]//..//select")
						.selectByVisibleText(secondQuestionMapping);
				log.endStep();

				log.startStep("Select mapping value for the " + thirdQuestion
						+ " question");
				driver.select(
						"//td[contains(text(), '" + thirdQuestion + "')]//..//select")
						.selectByVisibleText(thirdQuestionMapping);
				log.endStep();

				log.startStep("Click the 'Update' button");
				driver.click(
						"//div[@id='s_DOMWindow-footer']//span[contains(text(), 'Update')]",
						driver.timeOut);
				log.endStep();

				log.startStep("Click the 'Save and Close' button");
				driver.click("//span[contains(text(),'Save and Close')]",
						driver.timeOut);
				log.endStep();

				return this;
			}

			protected BaseIA createContactIaCRM(String emailAddress,
					String lastName, String iaFolder) throws Exception {

				log.startStep("Establish a connection to IA CRM");
				interAction.connection.login();
				log.endStep();

				log.resultStep("Creating a contact with Email Address: " + emailAddress
						+ " and Last Name: " + lastName + " in IA folder: " + iaFolder
						+ " via IA Api");
				interAction.addPerson(lastName, emailAddress,
						interAction.getFolderId(iaFolder), false, "");
				log.endStep();

				return this;
			}

			protected void sendCampaign(Campaign campaign, List<Contact> contacts)
					throws Exception {

				loginToSend().goToCampaignsPage().campaign.startCampaignCreation()
						.selectCampaignTemplate(campTemplateName)
						.setCampaignName(campaign.name, false)
						.setCampaignSubject(campaign.subject)
						.setCampaignRegarding(campaign.iaFolder).saveCampaign(false);

				send.contact.goToContactsSubPage("Contacts");
				for (Contact contact : contacts) {
					contact.createContact(contact.firstName, contact.lastName,
							contact.emailAddress, contact.externalId, null)
							.searchRecord(contact.emailAddress)
							.selectContact(contact.emailAddress);
				}

				send.campaign.continueToPreviewAndSend().sendCampaignNow(true);

				for (int i = 0; i < contacts.size(); i++) {

					contactDetails.contactActivities.add(iaActivities.addActivity(
							Activities.EMAILSENT, iaActivities.defualtRegarding));
				}

			}

			protected BaseIA verifyCampaignIsSuccessfullySent(
					String contactEmail, String campaignSubject) throws Exception {

				mailinator.accessMailinator().searchCustomerEmail(contactEmail)
						.verifyCampaignExistance(campaignSubject);

				return this;
			}

			protected BaseIA logIaContactDetails(String iaContactLastName,
					String iaContactSourceFolder) throws Exception {

				log.resultStep("IA Contact Details - Last Name: " + iaContactLastName
						+ "; Sourced Folder: " + iaContactSourceFolder);
				log.endStep();

				return this;
			}
}
