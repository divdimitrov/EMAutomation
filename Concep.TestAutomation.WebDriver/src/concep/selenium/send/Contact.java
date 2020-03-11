package concep.selenium.send;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class Contact {

    private static GenericSeleniumWebDriver driver;
    private LogResults                      log     = new LogResults();
    protected Elements                      element = new Elements();
    
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String externalId;
    public String parentFolder;

    public Contact() {

        driver = DriverAccessor.getDriverInstance();
    }
    
    public Contact(String firstName, String lastName, String emailAddress, String externalId) {
    	
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.emailAddress = emailAddress;
    	
    	if (externalId != null) {
			
    		this.externalId = externalId;
		}
    }
    
    public Contact(String emailAddress, String lastName, String parentFolder ) {
    	
    	this.emailAddress = emailAddress;
    	this.lastName = lastName;
    	this.parentFolder = parentFolder;    	
    }

    public Contact goToContactsPage() throws Exception {

        log.startStep( "Navigate to 'Contacts' tab" );
        driver.click( element.contactsTab, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.timeOut );
        log.endStep();

        return this;
    }

    public Contact goToContactsSubPage(
                                         String subPageName ) throws Exception {

        log.startStep( "Click on the '" + subPageName + "' tab" );
        driver.click( element.getContactsSubPage( subPageName ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.timeOut );
        log.endStep();

        return this;
    }

    public Contact searchRecord(
                                  String recordName ) throws Exception {

        log.startStep( "Type '" + recordName + "' in the Search input field" );
        driver.clear( element.searchInputField );
        driver.type( element.searchInputField, recordName, driver.timeOut );
        log.endStep();

        log.startStep( "Click on the 'Search' button" );
        driver.click( element.searchBtn, driver.timeOut );
        //driver.waitForAjaxToComplete( driver.ajaxTimeOut ); //TODO: To be investigated
        Thread.sleep(1000);
        driver.waitForPageToLoad();
        log.endStep();

        return this;
    }

    // this method need to have a parmeter for the record that's needs editing 
    public Contact startEditingRecord(
                                        String keyword ) throws Exception {

        log.startStep( "Click on the 'Tools' dropdown menu" );
        driver.click( element.getContactRecordToolsMenu( keyword ), driver.timeOut );
        log.endStep();

        log.startStep( "Click on the 'Edit' record button" );
        driver.click( element.editRecordBtn, driver.timeOut );
        log.endStep();

        return this;
    }

    // same as above note, the xpath needs to be dynamic
    public Contact deleteRecord(
                                  String keyword ) throws Exception {

        log.startStep( "Click on the 'Tools' dropdown menu" );
        driver.click( element.getContactRecordToolsMenu( keyword ), driver.timeOut );
        log.endStep();

        log.startStep( "Click on the 'Delete' record button" );
        driver.click( element.deleteRecordBtn, driver.timeOut );
        log.endStep();

        log.startStep( "Confirm the deletion clicking on the 'Yes, Delete' button" );
        driver.click( element.confirmDeletionBtn, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public Contact accessContactDetails(
                                          String detailsType ) throws Exception {

        driver.switchToIframeByID( "DOMWindowIframe" );
        log.startStep( "Access contact details type: " + detailsType );
        driver.click( element.getContactDetailsType( detailsType ), driver.timeOut );
        log.endStep();

        return this;
    }

    public Contact verifyContactDetail(
                                         String xPath,
                                         String comparingValue,
                                         boolean shouldBeEqual ) throws Exception {

        String concepSendValue = driver.getInputFieldValue( xPath );

        if( shouldBeEqual ) {

            log.startStep( "Verify contact detail '" + concepSendValue + "' from Concep Send is equal to '"
                           + comparingValue );
            log.endStep( concepSendValue.equals( comparingValue ) );

        } else {

            log.startStep( "Verify contact detail '" + concepSendValue
                           + "' from Concep Send is not equal to '" + comparingValue );
            log.endStep( !concepSendValue.equals( comparingValue ) );
        }

        return this;
    }
    
    public Contact createGroup(String groupName) throws Exception {
    	
    	log.startStep( "Click on the 'Add Group' hyperlink" );
    	driver.click( element.addGroupHyperlink, driver.timeOut );
    	log.endStep();
    	
    	log.startStep("Type in a Group Name: " + groupName);
    	driver.clear(element.groupNameField);
    	driver.type(element.groupNameField, groupName, driver.timeOut);
    	log.endStep();
    	
    	log.startStep( "Click on the 'Add Group' button" );
    	driver.click( element.addGroupButton, driver.timeOut );
    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    	log.endStep();
    	
    	return this;
    }

    public Contact openGroup(
                               String groupName ) throws Exception {

        log.startStep( "Open a group with name: " + groupName );
        driver.click( element.getGroupLink( groupName ), driver.timeOut );
        log.endStep();

        return this;
    }
    
    public Contact createContact(
            					  String contactFirstName,
            					  String contactLastName,
            					  String contactEmail,
            					  String externalId,
            					  String[] groups) throws Exception {
    	
    	if (verifyContactExistance(contactEmail)) {
			
    		log.resultStep("Contact with email " + contactEmail + " is already existing in Send. The process of editing will start.");
    		log.endStep();
    		
    		startEditingRecord(contactEmail);
    		   		
    		log.startStep("Switch to iframe with id=DOMWindowIframe");
	    	driver.switchToIframeByID("DOMWindowIframe");
	    	log.endStep();
    		
    		fillContactDetails(contactFirstName, contactLastName, contactEmail, externalId, groups );  
    		
    		log.startStep("Click on the 'Update and Close' button");
	    	driver.click(element.updateAndCloseButton, driver.timeOut);
	    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
	    	log.endStep();
    		
		} else {
			
			log.startStep( "Click on the 'Add Contact' button" );
	    	driver.click( element.addContactButton, driver.timeOut );
	    	log.endStep();    	
	    	
	    	log.startStep("Switch to iframe with id=DOMWindowIframe");
	    	driver.switchToIframeByID("DOMWindowIframe");
	    	log.endStep();
	    	
    		fillContactDetails(contactFirstName, contactLastName, contactEmail, externalId, groups ); 
    		
	    	log.startStep("Click on the 'Save and Close' button");
	    	driver.click(element.saveAndCloseButton, driver.timeOut);
	    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
	    	log.endStep();    	
		}    	
    	
    	log.startStep("Switch to top window");
    	driver.switchToTopWindow();
    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    	log.endStep();

    	return this;
    }
    
    public Contact selectContact(String contactEmail) throws Exception {
    	
    	log.startStep("Select a contact with email: " + contactEmail);
    	driver.click(element.getContactRecordCheckbox(contactEmail), driver.ajaxTimeOut);
    	driver.waitForPageToLoad();
    	log.endStep();
    	
    	return this;
    }
    
    public Contact selectGroup(String groupName) throws Exception {
    	    		
    	log.startStep("Select a gorup with name: " + groupName);
        driver.click(element.getGroupCheckbox(groupName), driver.ajaxTimeOut);
        driver.waitForPageToLoad();
        log.endStep();
    	
    	return this;
    }
    
    public Contact fillContactDetails(String firstName,
    								  String lastName,
    								  String email,
    								  String externalId,
    								  String[] groups) throws Exception {
    	
    	log.resultStep("Type in the contact First Name: " + firstName);
    	driver.clear(element.firstName);
    	driver.type(element.firstName, firstName, driver.timeOut);
    	log.endStep();
    	
    	log.resultStep("Type in the contact Last Name: " + lastName);
    	driver.clear(element.lastName);
    	driver.type(element.lastName, lastName, driver.timeOut);
    	log.endStep();
    	
    	log.resultStep("Type in the contact Email: " + email);
    	driver.clear(element.email);
    	driver.type(element.email, email, driver.timeOut);
    	log.endStep();
    	
    	if (externalId != null) {
    		
    		log.startStep("Click on the 'Other Fields' tab from inside the contact");
    		driver.click(element.contactOtherFields, driver.timeOut);
    		driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    		log.endStep();
    		
    		log.resultStep("Type in the External Contact ID: " + externalId);
    		driver.clear(element.externalId);
    		driver.type(element.externalId, externalId, driver.timeOut);
    		driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    		log.endStep();
		}
    	    	
    	if (groups != null) {
			
    		log.startStep("Click on the 'Groups' tab from inside the contact");
    		driver.click(element.contactGroup, driver.timeOut);
    		driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    		log.endStep();
    		
    		for (int i = 0; i < groups.length; i++) {
				
    			log.resultStep("Check the checkbox of group with name: " + groups[i]);
    			driver.click(element.getContactGroupCheckbox(groups[i]), driver.timeOut);
    			log.endStep();
    			
			}
		}
    	
    	return this;
    }
    
    public boolean verifyContactExistance(String contactEmail) throws Exception {
		
    	searchRecord(contactEmail);
    	
    	return driver.isElementPresent(element.getContactRecord(contactEmail), driver.timeOut);
    }
}
