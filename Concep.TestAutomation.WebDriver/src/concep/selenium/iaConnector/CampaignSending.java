package concep.selenium.iaConnector;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.iaConnector.IAActivities.Activities;
import concep.selenium.iaConnector.IAattributes.ContactDetails;
import concep.selenium.mailinator.Mailinator;
import concep.selenium.send.Campaign;
import concep.selenium.send.Contact;
import concep.selenium.send.IAConnection.ActivityMappings;
import concep.selenium.send.IAConnection.ActivityTypes;
import concep.selenium.send.IAConnection.ConnectionWizardButtonAction;

public class CampaignSending extends BaseIAconnector {
		
	private final boolean isActivitiesEnabled = true;
	private final boolean isSurveysEnabled = false;	
	private final boolean isSourced = true;	
	private final boolean isActivityLinkedToCompany = false;	   

	private String         iaContactShortId;
	private String         iaContactShortIdSplit;
	private String         iaContactLongId;
	private String         iaContactLongIdSplit;
	private String         notExistingShortId;
	private String         notExistingLongId;
	private String         notExistingShortIdSplit;
	private String         notExistingLongIdSplit;	    	    
    private String         invalidId;  

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

        iaContactShortId = driver.config.getProperty( "iaContactShortId" );
        iaContactShortIdSplit = driver.config.getProperty( "iaContactShortIdSplit" );
        iaContactLongId = driver.config.getProperty( "iaContactLongId" );
        iaContactLongIdSplit = driver.config.getProperty( "iaContactLongIdSplit" );
        notExistingShortId = driver.config.getProperty( "notExistingShortId" );
        notExistingLongId = driver.config.getProperty( "notExistingLongId" );
        notExistingShortIdSplit = driver.config.getProperty( "notExistingShortIdSplit" );
        notExistingLongIdSplit = driver.config.getProperty( "notExistingLongIdSplit" );
        invalidId = driver.config.getProperty( "invalidId" );

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
   
    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullySentCampaignToOneRecipient() throws Exception {

        String dateTime = driver.getDateTime( dateTimeFormat );
        String campaignName = "campaign" + dateTime;
        String campaignSubject = "subj" + dateTime;
        String regardingFolder = "QA MList Sponsor Contact And Company";

        String contactFirstName = "fn" + dateTime;
        String contactLastName = "ln" + dateTime;
        String contactEmail = "mail" + dateTime + emailAddressDomainPart;

        log.startTest( "Verify that a campaign can be successfully sent to one recipient" );

        try {

            Campaign campaign = new Campaign( campaignName, campaignSubject, regardingFolder );
            Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
            contacts.add( contact );
            sendCampaign( campaign, contacts );

            mailinator.accessMailinator()
                      .searchCustomerEmail( contactEmail )
                      .verifyCampaignExistance( campaignSubject );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullySentCampaignToMoreThanOneRecipients() throws Exception {

        String dateTime = driver.getDateTime( dateTimeFormat );
        String campaignName = "campaign" + dateTime;
        String campaignSubject = "subj" + dateTime;
        String regardingFolder = "QA MList Sponsor Contact And Company";

        String contactFirstName1 = "1fn" + dateTime;
        String contactLastName1 = "1ln" + dateTime;
        String contactEmail1 = "1mail" + dateTime + emailAddressDomainPart;

        String contactFirstName2 = "2fn" + dateTime;
        String contactLastName2 = "2ln" + dateTime;
        String contactEmail2 = "2mail" + dateTime + emailAddressDomainPart;

        log.startTest( "Verify that a campaign can be successfully sent to more than one recipients,and activities are succesffully logged" );

        try {
            createContactIaCRM( contactEmail1, contactLastName1, regardingFolder );
            createContactIaCRM( contactEmail2, contactLastName2, regardingFolder );

            Campaign campaign = new Campaign( campaignName, campaignSubject, regardingFolder );
            Contact contact1 = new Contact( contactFirstName1, contactLastName1, contactEmail1, null );
            Contact contact2 = new Contact( contactFirstName2, contactLastName2, contactEmail2, null );

            contacts.add( contact1 );
            contacts.add( contact2 );

            sendCampaign( campaign, contacts );

            mailinator.accessMailinator()
                      .searchCustomerEmail( contactEmail1 )
                      .verifyCampaignExistance( campaignSubject )
                      .accessMailinator()
                      .searchCustomerEmail( contactEmail2 )
                      .verifyCampaignExistance( campaignSubject );

            //need to have a list of lastName so that we can verify more then one contact activity
            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName1 );
            contactDetails.lastName.add( contactLastName2 );
            json.saveItToIA( stack[0].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullySentCampaignToOneGroup() throws Exception {

        String dateTime = driver.getDateTime( dateTimeFormat );
        String campaignName = "campaign" + dateTime;
        String campaignSubject = "subj" + dateTime;
        String regardingFolder = "QA MList Sponsor Contact And Company";

        String groupName = "group" + dateTime;

        String contactFirstName1 = "1fn" + dateTime;
        String contactLastName1 = "1ln" + dateTime;
        String contactEmail1 = "1mail" + dateTime + emailAddressDomainPart;

        String contactFirstName2 = "2fn" + dateTime;
        String contactLastName2 = "2ln" + dateTime;
        String contactEmail2 = "2mail" + dateTime + emailAddressDomainPart;

        log.startTest( "Verify that a campaign can be successfully sent to one group, and activities are successfully logged to the recipients" );

        try {

            createContactIaCRM( contactEmail1, contactLastName1, regardingFolder );
            createContactIaCRM( contactEmail2, contactLastName2, regardingFolder );

            Campaign campaign = new Campaign( campaignName, campaignSubject, regardingFolder );
            Contact contact1 = new Contact( contactFirstName1, contactLastName1, contactEmail1, null );
            Contact contact2 = new Contact( contactFirstName2, contactLastName2, contactEmail2, null );

            contacts.add( contact1 );
            contacts.add( contact2 );

            sendCampaign( campaign, contacts );

            mailinator.accessMailinator()
                      .searchCustomerEmail( contactEmail1 )
                      .verifyCampaignExistance( campaignSubject )
                      .accessMailinator()
                      .searchCustomerEmail( contactEmail2 )
                      .verifyCampaignExistance( campaignSubject );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName1 );
            contactDetails.lastName.add( contactLastName2 );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullySentCampaignToMoreThanOneGroups() throws Exception {

        String dateTime = driver.getDateTime( dateTimeFormat );
        String campaignName = "campaign" + dateTime;
        String campaignSubject = "subj" + dateTime;
        String regardingFolder = "QA MList Sponsor Contact And Company";

        String groupName1 = "1group" + dateTime;
        String groupName2 = "2group" + dateTime;

        String contactFirstName1 = "1fn" + dateTime;
        String contactLastName1 = "1ln" + dateTime;
        String contactEmail1 = "1mail" + dateTime + emailAddressDomainPart;

        String contactFirstName2 = "2fn" + dateTime;
        String contactLastName2 = "2ln" + dateTime;
        String contactEmail2 = "2mail" + dateTime + emailAddressDomainPart;

        String contactFirstName3 = "3fn" + dateTime;
        String contactLastName3 = "3ln" + dateTime;
        String contactEmail3 = "3mail" + dateTime + emailAddressDomainPart;

        String contactFirstName4 = "4fn" + dateTime;
        String contactLastName4 = "4ln" + dateTime;
        String contactEmail4 = "4mail" + dateTime + emailAddressDomainPart;

        log.startTest( "Verify that a campaign can be successfully sent to more than one groups,and all recipients has the activities created successfully" );

        try {

            createContactIaCRM( contactEmail1, contactLastName1, regardingFolder );
            createContactIaCRM( contactEmail2, contactLastName2, regardingFolder );
            createContactIaCRM( contactEmail3, contactLastName3, regardingFolder );
            createContactIaCRM( contactEmail4, contactLastName4, regardingFolder );

            Campaign campaign = new Campaign( campaignName, campaignSubject, regardingFolder );
            Contact contact1 = new Contact( contactFirstName1, contactLastName1, contactEmail1, null );
            Contact contact2 = new Contact( contactFirstName2, contactLastName2, contactEmail2, null );
            Contact contact3 = new Contact( contactFirstName3, contactLastName3, contactEmail3, null );
            Contact contact4 = new Contact( contactFirstName4, contactLastName4, contactEmail4, null );

            contacts.add( contact1 );
            contacts.add( contact2 );
            contacts.add( contact3 );
            contacts.add( contact4 );

            sendCampaign( campaign, contacts );

            mailinator.accessMailinator()
                      .searchCustomerEmail( contactEmail1 )
                      .verifyCampaignExistance( campaignSubject )
                      .accessMailinator()
                      .searchCustomerEmail( contactEmail2 )
                      .verifyCampaignExistance( campaignSubject )
                      .accessMailinator()
                      .searchCustomerEmail( contactEmail3 )
                      .verifyCampaignExistance( campaignSubject )
                      .accessMailinator()
                      .searchCustomerEmail( contactEmail4 )
                      .verifyCampaignExistance( campaignSubject );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName1 );
            contactDetails.lastName.add( contactLastName2 );
            contactDetails.lastName.add( contactLastName3 );
            contactDetails.lastName.add( contactLastName4 );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactWithBlankIdFieldButExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );
        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has blank External Contact ID field but existing email in IA CRM" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactWithBlankIdFieldButExistingEmailTwoTimes() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );
        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has blank External Contact ID field but existing email in IA CRM"
                       + " that appears twice - the most recently updated existing contact will be used" );

        try {

            createContactIaCRM( contact.emailAddress, "1" + contact.lastName, iaContactSourceFolder );
            createContactIaCRM( contact.emailAddress, "2" + contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void unsuccessfullyLogActivitiesAgainstContactWithBlankIdFieldAndNotExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that no activities will be logged into IA CRM and the action will be marked as Processed, Failed with reason - Contact not found"
                       + " when a campaign is sent to recipient that has blank External Contact ID field not existing email in IA CRM" );

        try {

            sendCampaign( campaign, contacts );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingLongIDAndNotExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, iaContactLongId );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Long ID in IA CRM but not existing email" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingSplitLongIDAndNotExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       iaContactLongIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Split Long ID in IA CRM but not existing email" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingLongIDAndExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, iaContactLongId );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Long ID in IA CRM and existing email" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingSplitLongIDAndExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       iaContactLongIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Split Long ID in IA CRM and existing email" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingLongIDAndExistingSameEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, iaContactEmail, iaContactLongId );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Long ID in IA CRM and existing email"
                       + " that matches with the email of the contact with that Long ID" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingSplitLongIDAndExistingSameEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       iaContactEmail,
                                       iaContactLongIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Long ID in IA CRM and existing email"
                       + " that matches with the email of the contact with that Split Long ID" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingLongIdButExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, notExistingLongId );
        contacts.add( contact );
        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Long ID set in Concep Send but existing email" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingSplitLongIdButExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       notExistingLongIdSplit );
        contacts.add( contact );
        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Split Long ID set in Concep Send but existing email" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingLongIdButExistingEmailTwoTimes() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, notExistingLongId );
        contacts.add( contact );
        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Long ID set in Concep Send but existing email"
                       + " that appears twice - the most recently updated existing contact will be used" );

        try {

            createContactIaCRM( contact.emailAddress, "1" + contact.lastName, iaContactSourceFolder );
            createContactIaCRM( contact.emailAddress, "2" + contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( "2" + contact.lastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingSplitLongIdButExistingEmailTwoTimes() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       notExistingLongIdSplit );
        contacts.add( contact );
        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Split Long ID set in Concep Send but existing email"
                       + " that appears twice - the most recently updated existing contact will be used" );

        try {

            createContactIaCRM( contact.emailAddress, "1" + contact.lastName, iaContactSourceFolder );
            createContactIaCRM( contact.emailAddress, "2" + contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( "2" + contact.lastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void unsuccessfullyLogActivitiesAgainstContactByNotExistingLongIdAndEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, notExistingLongId );
        contacts.add( contact );

        log.startTest( "Verify that no activities will be logged into IA CRM and the action will be marked as Processed, Failed with reason - Contact not found"
                       + " when a campaign is sent to recipient that has not existing External Contact Long ID and not existing email in IA CRM" );

        try {

            sendCampaign( campaign, contacts );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void unsuccessfullyLogActivitiesAgainstContactByNotExistingSplitLongIdAndEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       notExistingLongIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that no activities will be logged into IA CRM and the action will be marked as Processed, Failed with reason - Contact not found"
                       + " when a campaign is sent to recipient that has not existing External Contact Split Long ID and not existing email in IA CRM" );

        try {

            sendCampaign( campaign, contacts );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingShortIDAndNotExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, iaContactShortId );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Short ID in IA CRM but not existing email" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingSplitShortIDAndNotExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       iaContactShortIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Split Short ID in IA CRM but not existing email" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingShortIDAndExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, iaContactShortId );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Short ID in IA CRM and existing email" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingSplitShortIDAndExistingEmail() throws Exception {

        String iaContactSourceFolder = "QA MList NoSponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       iaContactShortIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Split Short ID in IA CRM and existing email" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingShortIDAndExistingSameEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, iaContactEmail, iaContactShortId );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Short ID in IA CRM and existing email"
                       + " that matches with the email of the contact with that Short ID" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByExistingSplitShortIDAndExistingSameEmail() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       iaContactEmail,
                                       iaContactShortIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has existing Split Short ID in IA CRM and existing email"
                       + " that matches with the email of the contact with that Short ID" );

        try {

            sendCampaign( campaign, contacts );
            logIaContactDetails( iaContactLastName, iaContactSourceFolder );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( iaContactLastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingShortIdButExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, notExistingShortId );
        contacts.add( contact );
        String iaContactSourceFolder = "QA MList NoSponsor Contact And Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Short ID set in Concep Send but existing email" );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingSplitShortIdButExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       notExistingShortIdSplit );
        contacts.add( contact );
        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Split Short ID set in Concep Send but existing email" );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingShortIdButExistingEmailTwoTimes() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, notExistingShortId );
        contacts.add( contact );
        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Short ID set in Concep Send but existing email"
                       + " that appears twice - the most recently updated existing contact will be used" );

        try {

            createContactIaCRM( contact.emailAddress, "1" + contact.lastName, iaContactSourceFolder );
            createContactIaCRM( contact.emailAddress, "2" + contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( "2" + contact.lastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByNotExistingSplitShortIdButExistingEmailTwoTimes() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       notExistingShortIdSplit );
        contacts.add( contact );
        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has not exiting Split Short ID set in Concep Send but existing email"
                       + " that appears twice - the most recently updated existing contact will be used" );

        try {

            createContactIaCRM( contact.emailAddress, "1" + contact.lastName, iaContactSourceFolder );
            createContactIaCRM( contact.emailAddress, "2" + contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( "2" + contact.lastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void unsuccessfullyLogActivitiesAgainstContactByNotExistingShortIdAndEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, notExistingShortId );
        contacts.add( contact );

        log.startTest( "Verify that no activities will be logged into IA CRM and the action will be marked as Processed, Failed with reason - Contact not found"
                       + " when a campaign is sent to recipient that has not existing External Contact Short ID and not existing email in IA CRM" );

        try {

            sendCampaign( campaign, contacts );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void unsuccessfullyLogActivitiesAgainstContactByNotExistingSplitShortIdAndEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName,
                                       contactLastName,
                                       contactEmail,
                                       notExistingShortIdSplit );
        contacts.add( contact );

        log.startTest( "Verify that no activities will be logged into IA CRM and the action will be marked as Processed, Failed with reason - Contact not found"
                       + " when a campaign is sent to recipient that has not existing External Contact Split Short ID and not existing email in IA CRM" );

        try {

            sendCampaign( campaign, contacts );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByInvalidIdButExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, invalidId );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged to recipient that has an invalid ID set in Concep Send but existing email" );

        try {

            String iaContactSourceFolder = "QA MList NoSponsor Contact And Company";

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

    //Till here
    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactByInvalidIdButExistingEmailTwice() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, invalidId );
        contacts.add( contact );
        String iaContactSourceFolder = "QA MList NoSponsor Contact And Company";

        log.startTest( "Verify that activities will be successfully logged to recipient that has an invalid ID set in Concep Send but existing email"
                       + " that appears twice - the most recently updated existing contact will be used" );

        try {

            createContactIaCRM( contact.emailAddress, "1" + contact.lastName, iaContactSourceFolder );
            createContactIaCRM( contact.emailAddress, "2" + contact.lastName, iaContactSourceFolder );
            sendCampaign( campaign, contacts );

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( "2" + contact.lastName );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void unsuccessfullyLogActivitiesAgainstContactByInvalidIdAndNotExistingEmail() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, invalidId );
        contacts.add( contact );

        log.startTest( "Verify that no activities will be logged into IA CRM and the action will be marked as Processed, Failed with reason - Contact not found"
                       + " when a campaign is sent to recipient that has an invalid ID and not existing email in IA CRM" );

        try {

            sendCampaign( campaign, contacts );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingFolderQaAdminBothContactAndCompany() throws Exception {

        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged regarding " + iaContactSourceFolder
                       + " folder when such is selected from the campaign" );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingFolderQaAdminContactOnly() throws Exception {

        String iaContactSourceFolder = "QA Admin Contact Only";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged regarding " + iaContactSourceFolder
                       + " folder when such is selected from the campaign" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, campaign.iaFolder );
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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingFolderQaMListSponsorContactAndCompany() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged regarding " + iaContactSourceFolder
                       + " folder when such is selected from the campaign" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, campaign.iaFolder );
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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingFolderQaSponsorContactOnly() throws Exception {

        String iaContactSourceFolder = "QA Sponsor Contact Only";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged regarding " + iaContactSourceFolder
                       + " folder when such is selected from the campaign" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, campaign.iaFolder );
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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingFolderQaMListNoSponsorContactAndCompany() throws Exception {

        String iaContactSourceFolder = "QA MList NoSponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add(contact);

        log.startTest( "Verify that activities will be successfully logged regarding " + iaContactSourceFolder
                       + " folder when such is selected from the campaign" );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingFolderQaNoSponsorContactOnly() throws Exception {

        String iaContactSourceFolder = "QA NoSponsor Contact Only";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged regarding " + iaContactSourceFolder
                       + " folder when such is selected from the campaign" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, campaign.iaFolder );
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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingFolderQaWorkingList() throws Exception {

        String iaContactSourceFolder = "QA Working List";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged regarding " + iaContactSourceFolder
                       + " folder when such is selected from the campaign" );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, campaign.iaFolder );
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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingContactQaAdminBothContactAndCompany() throws Exception {

        String iaContactSourceFolder = "QA Admin Both Contact and Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, iaContactSourceFolder );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged contact located in "
                       + iaContactSourceFolder );

        try {

            createContactIaCRM( contact.emailAddress, contact.lastName, campaign.iaFolder );
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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingContactQaMListSponsorContactAndCompany() throws Exception {

        String iaContactSourceFolder = "QA MList Sponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged contact located in "
                       + iaContactSourceFolder );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingContactQaMListNoSponsorContactAndCompany() throws Exception {

        String iaContactSourceFolder = "QA MList NoSponsor Contact And Company";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged contact located in "
                       + iaContactSourceFolder );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesRegardingContactQaWorkingList() throws Exception {

        String iaContactSourceFolder = "QA Working List";

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged contact located in "
                       + iaContactSourceFolder );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactWhenDoesNotHaveRelatedCompanyAndFeatureDisabled() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, contactLastName, contactEmail, null );
        contacts.add( contact );
        String iaContactSourceFolder = "QA MList NoSponsor Contact And Company";

        log.startTest( "Verify that activities will be successfully logged against contact only when 'Link to Company'"
                       + " feature is disabled and the contact has no company related to it" );

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactOnlyWhenRelatedCompanySourcedInFirmCompaniesAndFeatureDisabled() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( iaContactFirstNameWithLinkedCompany,
                                       iaContactLastNameWithLinkedCompany,
                                       iaContactEmailWithLinkedCompany,
                                       null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged against contact only when 'Link to Company'"
                       + " feature is disabled but the contact has no company related to it and sourced in Firm Contacts - Companies" );

        try {

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

    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesAgainstContactOnlyWhenRelatedCompanyNotSourcedInFirmCompaniesAndFeatureDisabled() throws Exception {

        Campaign campaign = new Campaign( campaignName, campaignSubject, null );
        Contact contact = new Contact( contactFirstName, iaContactLastName, iaContactEmail, null );
        contacts.add( contact );

        log.startTest( "Verify that activities will be successfully logged against contact only when 'Link to Company'"
                       + " feature is disabled but the contact has company related to it and not sourced in Firm Contacts - Companies" );

        try {

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
    
    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesWhenSendingCampaignToOneExistingContactAndManyNotExisting() throws Exception {

    	String dateTime = driver.getDateTime( dateTimeFormat );
        String campaignName = "campaign" + dateTime;
        String campaignSubject = "subj" + dateTime;
        String regardingFolder = "QA MList Sponsor Contact And Company";        

        String contactFirstName1 = "1fn" + dateTime;
        String contactLastName1 = "1ln" + dateTime;
        String contactEmail1 = "1mail" + dateTime + emailAddressDomainPart;
        
        int notExistingContactCount = 10;
        
    	log.startTest( "Verify that activities will be successfully logged when email campaign is send to one existing contact and many not exising" );

        try {
        
            Campaign campaign = new Campaign( campaignName, campaignSubject, regardingFolder );

        	createContactIaCRM( contactEmail1, contactLastName1, regardingFolder );
            
            Contact contact1 = new Contact( contactFirstName1, contactLastName1, contactEmail1, null );
            contacts.add( contact1 );
            
            for (int i = 1; i <= notExistingContactCount; i++) {
				
            	contacts.add(new Contact(i + "fnnotexisting" + dateTime,
            						     i + "lnnotexisitng" + dateTime,
            						     i + "mailnotexisting" + dateTime + emailAddressDomainPart,
            						     null));
			}

            sendCampaign( campaign, contacts );

            mailinator.accessMailinator()
                      .searchCustomerEmail( contactEmail1 )
                      .verifyCampaignExistance( campaignSubject )
                      .accessMailinator();

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName1 );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "campaign-sending", "campaigns-all", "all-tests" })
    public void successfullyLogActivitiesWhenSendingCampaignToMoreThanOneExistingContactAndManyNotExisting() throws Exception {

    	String dateTime = driver.getDateTime( dateTimeFormat );
        String campaignName = "campaign" + dateTime;
        String campaignSubject = "subj" + dateTime;
        String regardingFolder = "QA MList Sponsor Contact And Company";        

        String contactFirstName1 = "1fn" + dateTime;
        String contactLastName1 = "1ln" + dateTime;
        String contactEmail1 = "1mail" + dateTime + emailAddressDomainPart;
        
        String contactFirstName2 = "2fn" + dateTime;
        String contactLastName2 = "2ln" + dateTime;
        String contactEmail2 = "2mail" + dateTime + emailAddressDomainPart;
        
        String contactFirstName3 = "3fn" + dateTime;
        String contactLastName3 = "3ln" + dateTime;
        String contactEmail3 = "3mail" + dateTime + emailAddressDomainPart;
        
        int notExistingContactCount = 10;
        
    	log.startTest( "Verify that activities will be successfully logged when email campaign is send to more than one existing contacts and many not exising" );

        try {
        
            Campaign campaign = new Campaign( campaignName, campaignSubject, regardingFolder );

        	createContactIaCRM( contactEmail1, contactLastName1, regardingFolder );
        	createContactIaCRM( contactEmail2, contactLastName2, regardingFolder );
        	createContactIaCRM( contactEmail3, contactLastName3, regardingFolder );
            
            Contact contact1 = new Contact( contactFirstName1, contactLastName1, contactEmail1, null );
            Contact contact2 = new Contact( contactFirstName2, contactLastName2, contactEmail2, null );
            Contact contact3 = new Contact( contactFirstName3, contactLastName3, contactEmail3, null );

            contacts.add( contact1 );
            contacts.add( contact2 );
            contacts.add( contact3 );
            
            for (int i = 1; i <= notExistingContactCount; i++) {
				
            	contacts.add(new Contact(i + "fnnotexisting" + dateTime,
            						     i + "lnnotexisitng" + dateTime,
            						     i + "mailnotexisting" + dateTime + emailAddressDomainPart,
            						     null));
			}

            sendCampaign( campaign, contacts );

            mailinator.accessMailinator()
                      .searchCustomerEmail( contactEmail1 )
                      .verifyCampaignExistance( campaignSubject )
                      .accessMailinator();

            log.startStep( "test data were successfully serilaized" );
            StackTraceElement[] stack = new Throwable().getStackTrace();
            contactDetails.lastName.add( contactLastName1 );
            json.saveItToIA( stack[1].getMethodName(), contactDetails, isJson );
            log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    // These test cases could be used for manual testing when we need existing contacts in the CRM
    /*@Test
    public void createIaCrmContactDateTime() throws Exception {

    	String dateTime = driver.getDateTime( dateTimeFormat );
    	
    	String administrationContactAndCompany = "QA Admin Both Contact and Company";
        String marketingListContactAndCompanyWithSponsor = "QA MList Sponsor Contact And Company";        
        String marketingListContactAndCompanyWithoutSponsor = "QA MList NoSponsor Contact And Company";  

        String contactFirstName = "fn" + dateTime;
        String contactLastName = "ln" + dateTime;
        String contactEmail = "mail" + dateTime + emailAddressDomainPart;        
        
        String parentFolder = marketingListContactAndCompanyWithSponsor;
        int contactsCount = 2;
        
    	log.startTest( "Create a contact into IA CRM" );
        try {
        
        	for (int i = 0; i < contactsCount; i++) {
            	createContactIaCRM( contactEmail, contactLastName, parentFolder );
            	
            	System.out.println("=======================================");
            	System.out.println(parentFolder);
            	System.out.println(contactEmail);
            	System.out.println(contactLastName);
            	System.out.println("=======================================");
			}

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test
    public void createIaCrmContactCustom() throws Exception {

    	String dateTime = driver.getDateTime( dateTimeFormat );
    	
    	String administrationContactAndCompany = "QA Admin Both Contact and Company";
        String marketingListContactAndCompanyWithSponsor = "QA MList Sponsor Contact And Company";        
        String marketingListContactAndCompanyWithoutSponsor = "QA MList NoSponsor Contact And Company";  

        Contact[] testContacts = new Contact[]{
        		
        		new Contact("03012018-SCT1-1-@mailinator.com", "03012018-SCT1-1", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-2-@mailinator.com", "03012018-SCT1-2", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-3-@mailinator.com", "03012018-SCT1-3", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-4-@mailinator.com", "03012018-SCT1-4", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-5-@mailinator.com", "03012018-SCT1-5", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-6-@mailinator.com", "03012018-SCT1-6", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-7-@mailinator.com", "03012018-SCT1-7", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-8-@mailinator.com", "03012018-SCT1-8", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-9-@mailinator.com", "03012018-SCT1-9", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-10-@mailinator.com", "03012018-SCT1-10", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-11-@mailinator.com", "03012018-SCT1-11", marketingListContactAndCompanyWithSponsor),
        		new Contact("03012018-SCT1-12-@mailinator.com", "03012018-SCT1-12", marketingListContactAndCompanyWithSponsor)
        };
        
    	log.startTest( "Create a contact into IA CRM" );
        try {
        
        	for (int i = 0; i < testContacts.length; i++) {
            	createContactIaCRM( testContacts[i].emailAddress, testContacts[i].lastName, testContacts[i].parentFolder );
            	
            	System.out.println("=======================================");
            	System.out.println(testContacts[i].parentFolder);
            	System.out.println(testContacts[i].emailAddress);
            	System.out.println(testContacts[i].lastName);
            	System.out.println("=======================================");
			}

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
}
