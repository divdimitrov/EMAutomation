package concep.selenium.iaConnector;

import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.iaConnector.IAattributes.ContactDetails;
import concep.selenium.send.Campaign;
import concep.selenium.send.Contact;
import concep.selenium.send.IAConnection.ConnectionWizardButtonAction;

public class CampaignSendingLinkToCompanyEnabled extends BaseIAconnector {

	private final boolean isActivitiesEnabled = true;
	private final boolean isSurveysEnabled = true;	
	private final boolean isSourced = true;	
	private final boolean isActivityLinkedToCompany = true;
	
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
        campTemplateName = driver.config.getProperty( "campaignTemplateName" );
        iaContactEmailWithLinkedCompany = driver.config.getProperty( "iaContactEmailWithLinkedCompany" );
        iaContactFirstNameWithLinkedCompany = driver.config.getProperty( "iaContactFirstNameWithLinkedCompany" );
        iaContactLastNameWithLinkedCompany = driver.config.getProperty( "iaContactLastNameWithLinkedCompany" );
        iaCompanyWithLinkedContact = driver.config.getProperty( "iaCompanyWithLinkedContact" );
        iaContactEmail = driver.config.getProperty( "iaContactEmail" );
        iaContactLastName = driver.config.getProperty( "iaContactLastName" );

        driver.url = driver.config.getProperty( "url" );

        setConnectionConfiguration();

        cleanup();
    }
	
	@BeforeMethod(alwaysRun = true)
    public void initiateTestData() {

        iaActivities = new IAActivities();
        contactDetails = new ContactDetails();
        contacts = new ArrayList<Contact>();
        dateTime = driver.getDateTime( dateTimeFormat );

        campaignName = "campaign" + dateTime;
        campaignSubject = "subj" + dateTime;

        contactFirstName = "fn" + dateTime;
        contactLastName = "ln" + dateTime;
        contactEmail = "mail" + dateTime + emailAddressDomainPart;
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
	
	@Test(groups = { "link-activity-to-company-enabled" })
    public void successfullyLogActivitiesAgainstContactWhenDoesNotHaveRelatedCompanyAndFeatureEnabled() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );
        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        log.startTest( "Verify that activities will be successfully logged against contact only when 'Link to Company'"
                       + " feature is enabled but the contact has no company related to it" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contact.lastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
	
	@Test(groups = { "link-activity-to-company-enabled" })
    public void successfullyLogActivitiesAgainstContactAndRelatedCompanySourcedInFirmCompaniesAndFeatureEnabled() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( iaContactFirstNameWithLinkedCompany,
                                       iaContactLastNameWithLinkedCompany,
                                       iaContactEmailWithLinkedCompany,
                                       null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged against contact and its related company when 'Link to Company'"
                       + " feature is enabled and the company is sourced to Firm Contacts - Compannies" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastNameWithLinkedCompany, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastNameWithLinkedCompany );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
	
	@Test(groups = { "link-activity-to-company-enabled" })
    public void successfullyLogActivitiesAgainstContactOnlyWhenRelatedCompanyNotSourcedInFirmCompaniesAndFeatureEnabled() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, iaContactLastName, iaContactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged against contact only when 'Link to Company'"
                       + " feature is enabled and the company is not sourced to Firm Contacts - Compannies" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contact.lastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
