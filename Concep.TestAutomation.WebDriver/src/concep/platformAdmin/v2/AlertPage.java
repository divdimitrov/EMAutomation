package concep.platformAdmin.v2;

import java.security.InvalidParameterException;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.IsChecked;

public class AlertPage extends BasePlatformAdmin {

	public enum MetaModelRecordType {

        CATEGORY("Category"),
        AREA_OF_INTEREST("AreaOfInterest"),
        TAG("Tag");
        
        public String recordType;

        private MetaModelRecordType( String recordType) {

            this.recordType = recordType;
        }
    }
	
	public enum ChannelAvailableScheduleType {

        MANUAL("Manual"),
        DAILY("Daily"),
        WEEKLY("Weekly"),
        MONTHLY("Monthly"),
        FORTNIGHTLY("Fortnightly");
        
        public String schedule;

        private ChannelAvailableScheduleType( String schedule) {

            this.schedule = schedule;
        }
    }
	
	@BeforeSuite(alwaysRun = true)
    @Parameters({ "config" })
    public void testDataCreation(
                               @Optional("config/firefox.PlatformAdmin") String configLocation )
                                                                                                throws Exception {
		driver.init( configLocation );
        driver.url = driver.config.getProperty( "platformAdminUrl" );
		generateAlertTestData();  
		driver.stopSelenium();
    }
	
	private void generateAlertTestData() throws Exception {
		
		String randNumber = driver.getTimestamp();
		
		SystemsPage systemPage = new SystemsPage();
        
		String emailProviderExternalSystem = randNumber + "EmailProviderName";
        String crmProviderExternalSystem = randNumber + "CrmProviderName";
        
        String emailProviderProfile = "emailProviderProfile";
        String crmProviderProfile = "crmProviderProfile";
        
        accessPlatformAdminHomePage().navigateToPage("Systems").navigateToSubPage( "System users" );
        
        if (driver.isElementPresent(elements.getRecordRow( emailProviderProfile ), driver.timeOut) &&
        	driver.isElementPresent(elements.getRecordRow( crmProviderProfile ), driver.timeOut)) {
			
        	log.resultStep("Alert test data is already existing in the Platform!");
        	log.endStep();
        	
		} else {
			
			log.resultStep("Alert test data is not existing in the Platform, so a creation process will start");
        	log.endStep();
		
			navigateToPage( "Systems" ).startCreatingRecord(false)
			   .getSystemPage(systemPage)
			   .fillExternalSystemRecord(emailProviderExternalSystem,
              		 				   "EmailProvider",
              		 				   "Send",
              		 				   "None",
              		 				   randNumber + "EmailProviderAddress",
              		 				   "")
              .saveRecord(false)
              .startCreatingRecord(false)
				 .getSystemPage(systemPage)
				 .fillExternalSystemRecord(crmProviderExternalSystem,
              		 				   "CrmProvider",
              		 				   "InterAction",
              		 				   "None",
              		 				   randNumber + "CrmProviderAddress",
              		 				   "")
              .saveRecord(false)
              .navigateToSubPage( "System users" )
              .startCreatingRecord( false )
              .getSystemPage( systemPage )
              .fillExternalSystemIdentityRecord( emailProviderExternalSystem,
             		 							emailProviderProfile,
                		 							"testpass")
              .saveRecord( false )
              .startCreatingRecord( false )
              .getSystemPage( systemPage )
              .fillExternalSystemIdentityRecord( crmProviderExternalSystem,
             		 							crmProviderProfile,
                		 							"testpass")
              .saveRecord( false );
			
			log.resultStep("Alert test data creation process is completed!");
        	log.endStep();
		}    
	}
	
    private AlertPage fillContentRecord(
                                         String externalID,
                                         String title,
                                         String description,
                                         String author,
                                         String url,
                                         String media,
                                         String type,
                                         String publicationDate,
                                         String expiryDate ) throws Exception {

        if( externalID != "" ) {

            log.startStep( "Type in '" + externalID + "' in the External Id input field" );
            driver.clear( elements.alertContentExtID );
            driver.type( elements.alertContentExtID, externalID, driver.timeOut );
            log.endStep();
        }

        if( title != "" ) {

            log.startStep( "Type in '" + title + "' in the Title input field" );
            driver.clear( elements.alertContentTitle );
            driver.type( elements.alertContentTitle, title, driver.timeOut );
            log.endStep();
        }

        if( description != "" ) {

            log.startStep( "Type in '" + description + "' in the Description input field" );
            driver.clear( elements.alertContentDescription );
            driver.type( elements.alertContentDescription, description, driver.timeOut );
            log.endStep();
        }
        
        if( author != "" ) {

            log.startStep( "Type in '" + author + "' in the Author input field" );
            driver.clear( elements.alertContentAuthor );
            driver.type( elements.alertContentAuthor, author, driver.timeOut );
            log.endStep();
        }

        if( url != "" ) {

            log.startStep( "Type in '" + url + "' in the Url input field" );
            driver.clear( elements.alertContentUrl );
            driver.type( elements.alertContentUrl, url, driver.timeOut );
            log.endStep();
        }

        if( media != "" ) {

            log.startStep( "Type in '" + media + "' in the Media input field" );
            driver.clear( elements.alertContentMedia );
            driver.type( elements.alertContentMedia, media, driver.timeOut );
            log.endStep();
        }

        if( type != "" ) {

            log.startStep( "Type in '" + type + "' in the Type input field" );
            driver.clear( elements.alertContentType );
            driver.type( elements.alertContentType, type, driver.timeOut );
            log.endStep();
        }

        if( publicationDate != "" ) {

            String[] publicDateAndTime = publicationDate.split( " " );

            log.startStep( "Type in '" + publicDateAndTime[0] + "' in the Publication Date input field" );
            driver.clear( elements.alertContentPublicationDate );
            driver.type( elements.alertContentPublicationDate, publicDateAndTime[0], driver.timeOut );
            log.endStep();

            log.startStep( "Type in '" + publicDateAndTime[1] + "' in the Publication Date input field" );
            driver.clear( elements.alertContentPublicationTime );
            driver.type( elements.alertContentPublicationTime, publicDateAndTime[1], driver.timeOut );
            log.endStep();
        }

        if( expiryDate != "" ) {

            String[] expDateAndTime = expiryDate.split( " " );

            log.startStep( "Type in '" + expDateAndTime[0] + "' in the Expiry Date input field" );
            driver.clear( elements.alertContentExpiryDate );
            driver.type( elements.alertContentExpiryDate, expDateAndTime[0], driver.timeOut );
            log.endStep();

            log.startStep( "Type in '" + expDateAndTime[1] + "' in the Expiry Date input field" );
            driver.clear( elements.alertContentExpiryTime );
            driver.type( elements.alertContentExpiryTime, expDateAndTime[1], driver.timeOut );
            log.endStep();
        }

        return this;
    }

    private AlertPage fillSubgridRecord(
                                         String key,
                                         String displayName,
                                         String dataType,
                                         String specialType,
                                         String value ) throws Exception {

        if( key != "" ) {

            log.startStep( "Type in '" + key + "' in the Key input field" );
            driver.clear( elements.subgridKey );
            driver.type( elements.subgridKey, key, driver.timeOut );
            log.endStep();
        }

        if( displayName != "" ) {

            log.startStep( "Type in '" + displayName + "' in the Display input field" );
            driver.clear( elements.subgridDisplayName );
            driver.type( elements.subgridDisplayName, displayName, driver.timeOut );
            log.endStep();
        }

        if( dataType != "" ) {

            log.startStep( "Select a '" + dataType + "' from the Data Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.subgridDataType, dataType));
            driver.select( elements.subgridDataType ).selectByVisibleText( dataType );
            log.endStep();
        }

        if( specialType != "" ) {

            log.startStep( "Select a '" + specialType + "' from the Special Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.subgridSpecialType, specialType));
            driver.select( elements.subgridSpecialType ).selectByVisibleText( specialType );
            log.endStep();
        }

        if( value != "" ) {

            log.startStep( "Type in '" + value + "' in the Display input field" );
            driver.clear( elements.subgridValue );
            driver.type( elements.subgridValue, value, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    private AlertPage fillContactRecord(
                                         String externalReference,
                                         String firstName,
                                         String lastName,
                                         String email,
                                         String phoneNumber,
                                         String company,
                                         String jobTitle ) throws Exception {

        if( externalReference != "" ) {

            log.startStep( "Type in '" + externalReference + "' in the External Reference input field" );
            driver.clear( elements.contactExtReference );
            driver.type( elements.contactExtReference, externalReference, driver.timeOut );
            log.endStep();
        }

        if( firstName != "" ) {

            log.startStep( "Type in '" + firstName + "' in the First Name input field" );
            driver.clear( elements.contactFirstName );
            driver.type( elements.contactFirstName, firstName, driver.timeOut );
            log.endStep();
        }

        if( lastName != "" ) {

            log.startStep( "Type in '" + lastName + "' in the Last Name input field" );
            driver.clear( elements.contactLastName );
            driver.type( elements.contactLastName, lastName, driver.timeOut );
            log.endStep();
        }

        if( email != "" ) {

            log.startStep( "Type in '" + email + "' in the Email input field" );
            driver.clear( elements.contactEmail );
            driver.type( elements.contactEmail, email, driver.timeOut );
            log.endStep();
        }

        if( phoneNumber != "" ) {

            log.startStep( "Type in '" + phoneNumber + "' in the Phone Number input field" );
            driver.clear( elements.contactPhoneNumber );
            driver.type( elements.contactPhoneNumber, phoneNumber, driver.timeOut );
            log.endStep();
        }

        if( company != "" ) {

            log.startStep( "Select a '" + company + "' from the Company dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.contactCompany, company));
            driver.select( elements.contactCompany ).selectByVisibleText( company );
            log.endStep();
        }

        if( jobTitle != "" ) {

            log.startStep( "Type in '" + jobTitle + "' in the Job Title input field" );
            driver.clear( elements.contactJobTitle );
            driver.type( elements.contactJobTitle, jobTitle, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    private AlertPage fillTemplateRecord(
                                          String name,
                                          String channel,
                                          String deliveryChannelType,
                                          String templateType,
                                          String content,
                                          String reference ) throws Exception {

        if( name != "" ) {

            log.startStep( "Type in '" + name + "' in the Name input field" );
            driver.clear( elements.templateName );
            driver.type( elements.templateName, name, driver.timeOut );
            log.endStep();
        }

        if( channel != "" ) {

            log.startStep( "Select a '" + channel + "' from the Channel dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.templateChannel, channel));
            driver.select( elements.templateChannel ).selectByVisibleText( channel );
            log.endStep();
        }

        if( deliveryChannelType != "" ) {

            log.startStep( "Select a '" + deliveryChannelType + "' from the Delivery Channel Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.templateDeliveryChannelType, deliveryChannelType));
            driver.select( elements.templateDeliveryChannelType ).selectByVisibleText( deliveryChannelType );
            log.endStep();
        }

        if( templateType != "" ) {

            log.startStep( "Select a '" + templateType + "' from the Template Type dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.templateType, templateType));
            driver.select( elements.templateType ).selectByVisibleText( templateType );
            log.endStep();
        }

        if( content != "" ) {

            log.startStep( "Type in a content value in the Content input field" );
            driver.clear( elements.templateContent );
            driver.type( elements.templateContent, content, driver.timeOut );
            log.endStep();
        }

        if( reference != "" ) {

            log.startStep( "Type in '" + reference + "' in the Reference input field" );
            driver.clear( elements.templateReference );
            driver.type( elements.templateReference, reference, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    private AlertPage accessChannelSubscribersGrid(
                                                    String channelName ) throws Exception {

        log.startStep( "Access the grid with all subscribers to'" + channelName + "' channel" );
        driver.click( elements.getSubscribersChannel( channelName ), driver.timeOut );
        log.endStep();

        return this;
    }

    private AlertPage addAreaOfInterest(
                                         String areaOfInterest ) throws Exception {

        if( areaOfInterest != "" ) {

            log.startStep( "Select an '" + areaOfInterest + "' from the Area Of Interest dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.subscriberAreaOfInterest, areaOfInterest));
            driver.select( elements.subscriberAreaOfInterest ).selectByVisibleText( areaOfInterest );
            log.endStep();
        }

        return this;
    }

    private AlertPage interactMetaModelTree(
                                             String interactionType ) throws Exception {

        log.startStep( interactionType + " the Meta Model tree" );
        driver.click( elements.getMetaModelInteraction( interactionType ), driver.timeOut );
        log.endStep();

        return this;
    }

    private AlertPage addCategory(
                                   String categoryName,
                                   String categoryKey ) throws Exception {

    	if (categoryName != "") {
			
    		log.startStep( "Type in '" + categoryName + "' in the 'Category Name' input field" );
            driver.type( elements.metaModelCategoryName, categoryName, driver.timeOut );
            log.endStep();
		}
    	
    	if (categoryKey != "") {
			
    		log.startStep( "Type in '" + categoryKey + "' in the 'Category Key' input field" );
            driver.type( elements.metaModelCategoryKey, categoryKey, driver.timeOut );
            log.endStep();
		}       

        log.startStep( "Click on the 'Add Category' button" );
        driver.click( elements.metaModelAddCategory, driver.timeOut );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();

        return this;
    }
    
    private AlertPage startEditingMetaModelRecord(String metaModelRecordType, String recordName) throws Exception {
    	
    	switch (metaModelRecordType) {
    	
		case "Category":
			
			log.startStep("Click on the " + recordName + " category edit button");
			driver.click(elements.getMetaModelEditRecord(recordName), driver.timeOut);
			log.endStep();			
			break;
			
		case "AreaOfInterest":
			
			log.startStep("Click on the " + recordName + " area of Interest edit button");
			driver.click(elements.getMetaModelEditRecord(recordName), driver.timeOut);
			log.endStep();			
			break;	
			
		case "Tag":
			
			log.startStep("Click on the " + recordName + " tag edit button");
			driver.click(elements.getMetaModelEditRecord(recordName), driver.timeOut);
			log.endStep();			
			break;

		default:
			
			throw new InvalidParameterException( "The following parameter 'actionType' has an invalid value: '"
                    + metaModelRecordType + "'" );			
		}
    	
    	return this;
    }
    
    private AlertPage editCategoryValues(String categoryName, String categoryKey, boolean isSaved) throws Exception {
    	
    	if (categoryName != "") {
			
    		log.startStep("Change the Category Name value to: " + categoryName);
    		driver.clear(elements.metaModelCategoryNameEdit);
    		driver.type(elements.metaModelCategoryNameEdit, categoryName, driver.timeOut);
    		log.endStep();
		}
    	
    	if (categoryKey != "") {
			
    		log.startStep("Change the Category Key value to: " + categoryKey);
    		driver.clear(elements.metaModelCategoryKeyEdit);
    		driver.type(elements.metaModelCategoryKeyEdit, categoryKey, driver.timeOut);
    		log.endStep();
		}
    	
    	if (isSaved) {
			
    		log.startStep("Click on the Save record button");
    		driver.click(elements.getMetaModelCategorySaveRecord(), driver.timeOut);
    		log.endStep();
    		
		} else {
			
			log.startStep("Click on the Save record button");
    		driver.click(elements.getMetaModelCategoryCancelRecord(), driver.timeOut);
    		log.endStep();
		}
    	
    	return this;
    }

    private AlertPage addAOI(
                              String aoiName,
                              String aoiKey,
                              String targetCategory ) throws Exception {

    	if (aoiName != "") {
    		
    		log.startStep( "Type in '" + aoiName + "' in the 'AOI Name' input field" );
            driver.type( elements.getMetaModelAOIName( targetCategory ), aoiName, driver.timeOut );
            log.endStep();
		}
    	
    	if (aoiKey != "") {
			
    		log.startStep( "Type in '" + aoiKey + "' in the 'AOI Key' input field" );
            driver.type( elements.getMetaModelAOIKey( targetCategory ), aoiKey, driver.timeOut );
            log.endStep();
		}       

        log.startStep( "Click on the 'Add AOI' button" );
        driver.click( elements.getMetaModelAddAOI( targetCategory ), driver.timeOut );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();

        return this;
    }
    
    private AlertPage editAoiValues(String aoiName, String aoiKey, boolean isSaved) throws Exception {
    	
    	if (aoiName != "") {
			
    		log.startStep("Change the AOI Name value to: " + aoiName);
    		driver.clear(elements.metaModelAoiNameEdit);
    		driver.type(elements.metaModelAoiNameEdit, aoiName, driver.timeOut);
    		log.endStep();
		}
    	
    	if (aoiKey != "") {
			
    		log.startStep("Change the AOI Key value to: " + aoiKey);
    		driver.clear(elements.metaModelAoiKeyEdit);
    		driver.type(elements.metaModelAoiKeyEdit, aoiKey, driver.timeOut);
    		log.endStep();
		}
    	
    	if (isSaved) {
			
    		log.startStep("Click on the Save record button");
    		driver.click(elements.getMetaModelAoiSaveRecord(), driver.timeOut);
    		log.endStep();
    		
		} else {
			
			log.startStep("Click on the Save record button");
    		driver.click(elements.getMetaModelAoiCancelRecord(), driver.timeOut);
    		log.endStep();
		}
    	
    	return this;
    }

    private AlertPage addTag(
                              String tagName,
                              String targetCategory,
                              String targetAoi) throws Exception {

    	if (tagName != "" && targetCategory != "" && targetAoi != "") {
			
    		log.startStep( "Type in '" + tagName + "' in the 'Tag Name' input field" );
            driver.type( elements.getMetaModelTagName(targetCategory, targetAoi), tagName, driver.timeOut );
            log.endStep();
            
            log.startStep( "Click on the 'Add tag' button" );
            driver.click( elements.getMetaModelAddTag( targetCategory, targetAoi ), driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
            
		} else {
			
			throw new InvalidParameterException("Invalid parameter exception: Empty String");
		}      

        return this;
    }
    
    private AlertPage editTagValue(String tagName, boolean isSaved) throws Exception {
    	
    	if (tagName != "") {
			
    		log.startStep("Change the Tag Name value to: " + tagName);
    		driver.clear(elements.metaModelTagNameEdit);
    		driver.type(elements.metaModelTagNameEdit, tagName, driver.timeOut);
    		log.endStep();
		}
    	
    	if (isSaved) {
			
    		log.startStep("Click on the Save record button");
    		driver.click(elements.getMetaModelTagSaveRecord(), driver.timeOut);
    		driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    		log.endStep();
    		
		} else {
			
			log.startStep("Click on the Save record button");
    		driver.click(elements.getMetaModelTagCancelRecord(), driver.timeOut);
    		driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    		log.endStep();
		}
    	
    	return this;
    }

    private AlertPage deleteMetaModelRecord(
                                      String targetRecord,
                                      boolean isConfirmed ) throws Exception {

        log.startStep( "Click on the delete button of the category with name '" + targetRecord );
        driver.click( elements.getMetaModelDeleteRecord( targetRecord ), driver.timeOut );
        log.endStep();
        
        log.startStep( "Verify that that a warning message '" + elements.deleteConfirmationTxt2 +  "' for deleting the record is displayed" );
        log.endStep( driver.isElementPresent( elements.deleteConfirmation2, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for cancel the deletion is displayed [Cancel button]" );
        log.endStep( driver.isElementPresent( elements.cancelDeletion, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for confirm the deletion is displayed [OK button]" );
        log.endStep( driver.isElementPresent( elements.confirmDeletion, driver.timeOut ) );

        if( isConfirmed ) {

            log.startStep( "Confirm record deletion" );
            driver.click( elements.confirmDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
            
            log.startStep("Verify that the Delete record message is successfully displayed");
            log.endStep(driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));

        } else {

            log.startStep( "Cancel record deletion" );
            driver.click( elements.cancelDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
            
            log.startStep("Verify that the Delete record message is not displayed");
            log.endStep(!driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));
        }

        return this;
    }

    private AlertPage dragAndDropAOItoChannel(
                                               String aoiName,
                                               String channelName ) throws Exception {

        log.startStep( "Drag and drop AOI with name '" + aoiName + "' to a channel name '" + channelName
                       + "'" );
        driver.waitUntilElementIsLocated( elements.getAOIdragAndDropArea( aoiName ), driver.timeOut );
        driver.waitUntilElementIsClickable(elements.getAOIdragAndDropArea( aoiName ));
        driver.waitUntilElementIsLocated( elements.getMetaModelTargetChannel( channelName ), driver.timeOut );
        driver.dragAndDrop( elements.getAOIdragAndDropArea( aoiName ),
                            elements.getMetaModelTargetChannel( channelName ) );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();
        
        System.out.println(elements.getAOIdragAndDropArea( aoiName ));
        System.out.println(elements.getMetaModelTargetChannel( channelName ));

        return this;
    }

    private AlertPage removeAOIfromChannel(
                                            String aoiName,
                                            String channelName,
                                            boolean isConfirmed) throws Exception {

        log.startStep( "Remove the '" + aoiName + "' AOI from the '" + channelName + "' channel" );
        driver.click( elements.getMetaModelChannelAOIDeleteButton( channelName, aoiName ), driver.timeOut );
        log.endStep();
        
        log.startStep( "Verify that that a warning message '" + elements.deleteConfirmationTxt2 +  "' for deleting the record is displayed" );
        log.endStep( driver.isElementPresent( elements.deleteConfirmation2, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for cancel the deletion is displayed [Cancel button]" );
        log.endStep( driver.isElementPresent( elements.cancelDeletion, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for confirm the deletion is displayed [OK button]" );
        log.endStep( driver.isElementPresent( elements.confirmDeletion, driver.timeOut ) );

        if( isConfirmed ) {

            log.startStep( "Confirm record deletion" );
            driver.click( elements.confirmDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
            
            log.startStep("Verify that the Delete record message is successfully displayed");
            log.endStep(driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));

        } else {

            log.startStep( "Cancel record deletion" );
            driver.click( elements.cancelDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
            
            log.startStep("Verify that the Delete record message is not displayed");
            log.endStep(!driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));
        }

        return this;
    }

    protected AlertPage startCreatingChannel() throws Exception {

        log.startStep( "Click on the 'Add Channel' button" );
        driver.click( elements.channelEditorAddChannel, driver.timeOut );
        log.endStep();

        return this;
    }
    
    protected AlertPage startEditingChannel(String channelName) throws Exception {
    	
    	log.startStep("Click on the " + channelName + " Edit button");
    	driver.click(elements.getChannelEditorEditButton(channelName), driver.timeOut);
    	log.endStep();
    	
    	return this;
    }

    protected AlertPage fillChannelRecord(
                                         String key,
                                         String displayName,
                                         String maxContentAge,
                                         boolean isSuppressed,
                                         String deliveryTemplateKey,
                                         String emailProviderProfile,
                                         String senderProfileId,
                                         String domainId,
                                         String crmProviderProfile,
                                         String defaultSchedule,
                                         boolean isSaveButtonClicked ) throws Exception {

        if( key != "" ) {

            log.startStep( "Type in '" + key + "' in the Key input field" );
            driver.clear( elements.channelEditorKey );
            driver.type( elements.channelEditorKey, key, driver.timeOut );
            log.endStep();
        }

        if( displayName != "" ) {

            log.startStep( "Type in '" + displayName + "' in the Display Name input field" );
            driver.clear( elements.channelEditorDisplayName );
            driver.type( elements.channelEditorDisplayName, displayName, driver.timeOut );
            log.endStep();
        }

        if( maxContentAge != "" ) {

            log.startStep( "Type in '" + maxContentAge + "' in the Max Content Age input field" );
            driver.clear( elements.channelEditorMaxContentAge );
            driver.type( elements.channelEditorMaxContentAge, maxContentAge, driver.timeOut );
            log.endStep();
        }

        if( isSuppressed ) {

            log.startStep( "Click on the 'Suppress Duplicate Content' checkbox" );
            driver.click( elements.channelEditorSuppressDuplContent, driver.timeOut );
            log.endStep();
        }

        if( deliveryTemplateKey != "" ) {

            log.startStep( "Type in '" + deliveryTemplateKey + "' in the Delivery Template Key input field" );
            driver.clear( elements.channelEditorDeliveryTemplateKey );
            driver.type( elements.channelEditorDeliveryTemplateKey, deliveryTemplateKey, driver.timeOut );
            log.endStep();
        }

        if( emailProviderProfile != "" ) {

            log.startStep( "Select '" + emailProviderProfile + "' from the Email Provider Profile dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.channelEditorEmailProviderProfile, emailProviderProfile));
            driver.select( elements.channelEditorEmailProviderProfile ).selectByVisibleText( emailProviderProfile );
            log.endStep();
        }

        if( senderProfileId != "" ) {

            log.startStep( "Type in '" + senderProfileId + "' in the Sender Profile Id input field" );
            driver.clear( elements.channelEditorSenderProfileIdInput );
            driver.type( elements.channelEditorSenderProfileIdInput, senderProfileId, driver.timeOut );
            log.endStep();
        }
        
        if( domainId != "" ) {

            log.startStep( "Type in '" + domainId + "' in the Domain Id input field" );
            driver.clear( elements.channelEditorDomainIdInput );
            driver.type( elements.channelEditorDomainIdInput, domainId, driver.timeOut );
            log.endStep();
        }

        if( crmProviderProfile != "" ) {

            log.startStep( "Select '" + crmProviderProfile + "' from the Email Provider Profile dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.channelEditorCrmProviderProfile, crmProviderProfile));
            driver.select( elements.channelEditorCrmProviderProfile ).selectByVisibleText( crmProviderProfile );
            log.endStep();
        }

        if( defaultSchedule != "" ) {

            log.startStep( "Select '" + defaultSchedule + "' from the Default Schedule dropdown" );
            driver.waitUntilElementIsClickable(elements.getDropdownOption(elements.channelEditorDefaultSchedule, defaultSchedule));
            driver.select( elements.channelEditorDefaultSchedule ).selectByVisibleText( defaultSchedule );
            log.endStep();
        }

        if( isSaveButtonClicked ) {

        	saveChannelRecord();
        }

        return this;
    }
    
    protected AlertPage saveChannelRecord() throws Exception {
    	
    	log.startStep( "Click on the 'Save Changes' button" );
        driver.waitUntilElementIsLocated(elements.channelEditorSaveChanges, driver.timeOut);
        driver.waitUntilElementIsClickable(elements.channelEditorSaveChanges);
        driver.click( elements.channelEditorSaveChanges, driver.timeOut );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();
        
        log.startStep("Verify the 'Record saved' success message is displayed");
        driver.waitUntilElementIsLocated(elements.recordSavedSuccess, driver.timeOut);
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();
    	
    	return this;
    }
    
    private AlertPage closeSubgrid() throws Exception {
    	
    	log.startStep("Click on the 'Close' button from the subgrid table");
    	driver.click(elements.subgridClose, driver.timeOut);
    	Thread.sleep(1500); // TODO: have to be replaced with some other wait function (currently we have no AJAX or page for load at this step)
    	log.endStep();
    	
    	return this;
    }
    
    protected AlertPage setAvailableSchedule(String[] schedules, String[] marketingLists, boolean isEnabled) throws Exception {
    	    	
    	if (isEnabled) {
    		
    		for (int i = 0; i < schedules.length; i++) {
    			
        		if (!driver.isChecked(elements.getChannelAvailableSchedulesCheckbox(schedules[i]))) {
        			
        			log.startStep("Enable available schedule: " + schedules[i]);
            		driver.click(elements.getChannelAvailableSchedulesCheckbox(schedules[i]), driver.timeOut);
                	log.endStep();
                	
                	log.startStep("Type in marketing list name: " + marketingLists[i] );
            		driver.type(elements.getChannelAvailableSchedulesInput(schedules[i]), marketingLists[i], driver.timeOut);
                	log.endStep();
    			}    		
    		}
    		
		} else {
			
			for (int i = 0; i < schedules.length; i++) {
    			
        		if (driver.isChecked(elements.getChannelAvailableSchedulesCheckbox(schedules[i]))) {
        			
        			log.startStep("Disable available schedule: " + schedules[i]);
            		driver.click(elements.getChannelAvailableSchedulesCheckbox(schedules[i]), driver.timeOut);
                	log.endStep();
    			}    		
    		}
		}    	   	
        	
    	return this;
    }
    
    protected AlertPage disableAvailableSchedule(String[] schedules, String[] marketingLists) throws Exception {
    	
    	for (int i = 0; i < schedules.length; i++) {
			
    		log.startStep("Select available schedule: " + schedules[i] );
    		driver.click(elements.getChannelAvailableSchedulesCheckbox(schedules[i]), driver.timeOut);
        	log.endStep();
		}
    	
    	for (int i = 0; i < marketingLists.length; i++) {
			
    		log.startStep("Type in marketing list name: " + marketingLists[i] );
    		driver.type(elements.getChannelAvailableSchedulesInput(schedules[i]), marketingLists[i], driver.timeOut);
        	log.endStep();
		}
        	
    	return this;
    }

    protected AlertPage deleteChannel(
                                     String channelName,
                                     boolean isConfirmed ) throws Exception {

        log.startStep( "Delete the channel with name '" + channelName + "'" );
        driver.click( elements.getChannelEditorDeleteButton( channelName ), driver.timeOut );
        log.endStep();
        
        log.startStep( "Verify that that a warning message '" + elements.deleteConfirmationTxt2 +  "' for deleting the record is displayed" );
        log.endStep( driver.isElementPresent( elements.deleteConfirmation2, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for cancel the deletion is displayed [Cancel button]" );
        log.endStep( driver.isElementPresent( elements.cancelDeletion, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for confirm the deletion is displayed [OK button]" );
        log.endStep( driver.isElementPresent( elements.confirmDeletion, driver.timeOut ) );

        if( isConfirmed ) {

            log.startStep( "Confirm record deletion" );
            driver.click( elements.confirmDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
            
            log.startStep("Verify that the Delete record message is successfully displayed");
            log.endStep(driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));

        } else {

            log.startStep( "Cancel record deletion" );
            driver.click( elements.cancelDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
            
            log.startStep("Verify that the Delete record message is not displayed");
            log.endStep(!driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));
        }

        return this;
    }

    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayAlertTabMainNavigationFromOtherPages() throws Exception {

        log.startTest( "Verify that 'Alert' tab from the main navigation is successfully displayed from the other pages" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Systems" ).
            verifyDisplayedElements(new String[] {elements.getPageTab("Alert")}, 
            						new String[] {"Alert tab"}, true).
            navigateToPage("Alert").
            verifyDisplayedElements(new String[] {elements.getPageTab("Alert")}, 
									new String[] {"Alert tab"}, true).
	        navigateToPage("Schedules").
	        verifyDisplayedElements(new String[] {elements.getPageTab("Alert")}, 
					new String[] {"Alert tab"}, true).
			navigateToPage("Logs").
			verifyDisplayedElements(new String[] {elements.getPageTab("Alert")}, 
					new String[] {"Alert tab"}, true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayAlertPageSubmenuTabs() throws Exception {

        log.startTest( "Verify that the Alert submenu elements are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .verifyPageSubmenuTabs( new String[]{ "Contacts",
							                                                  "Content",
							                                                  "Meta Model",
							                                                  "Channel/Aoi Editor",
							                                         		  "Subscribers",
							                                         		  "Templates"} );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactsTableGrid() throws Exception {

        log.startTest( "Verify that the Contacts table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .verifyDisplayedElements( new String[]{ elements.recordsTableGrid },
                                        		 				   new String[] {"Contacts table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactsTableColumns() throws Exception {

        log.startTest( "Verify that Contacts table grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .verifyDisplayedElements( new String[]{ elements.getTableHeader( "External Reference" ),
                                                                           elements.getTableHeader( "First name" ),
                                                                           elements.getTableHeader( "Last Name" ),
                                                                           elements.getTableHeader( "Email" ),
                                                                           elements.getTableHeader( "Phone Number" ),
                                                                           elements.getTableHeader( "Company" ),
                                                                           elements.getTableHeader( "Job Title" ),
                                                                           elements.clearAllFilters },
                                                                   new String[] {"External Reference column",
                                        		 								 "First Name column",
                                         										 "Last Name column",
                                         										"Email column",
                                         										"Phone Number column",
                                         										"Company column",
                                         										"Job Title column",
                                         										"Clear all Filters button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactRecordEditButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = ""; // TODO: Need to be discussed what will be the values of Company dropdown
        String jobTitle = randNumber + "jobTitle";

        log.startTest( "Verify that a button for editing Contact record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy("External Reference", externalReference, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(externalReference) + elements.editRecord},
                                        		 				  new String[] {externalReference + " Contact record Edit button"},
					                                        	  true);
                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactRecordDeleteButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = ""; // TODO: Need to be discussed what will be the values of Company dropdown
        String jobTitle = randNumber + "jobTitle";

        log.startTest( "Verify that a button for delete a Contact record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy("External Reference", externalReference, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(externalReference) + elements.deleteRecord},
                                        		 				  new String[] {externalReference + " Contact record Delete button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactRecordCancelButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = ""; // TODO: Need to be discussed what will be the values of Company dropdown
        String jobTitle = randNumber + "jobTitle";

        log.startTest( "Verify that a button for cancel the edit of a Contact record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy("External Reference", externalReference, "Text")
                                         .startEditingRecord(elements.getRecordRow(externalReference), false, "")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(externalReference) + elements.cancelRecord},
                                        		 				  new String[] {externalReference + " Contact record Cancel button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactRecordSaveButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = ""; // TODO: Need to be discussed what will be the values of Company dropdown
        String jobTitle = randNumber + "jobTitle";

        log.startTest( "Verify that a button for save a Contact record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy("External Reference", externalReference, "Text")
                                         .startEditingRecord(elements.getRecordRow(externalReference), false, "")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(externalReference) + elements.saveRecord},
                                        		 				  new String[] {externalReference + " Contact record Save button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactAddNewRecordButton() throws Exception {

        log.startTest( "Verify that a button for adding Contact record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
        						         .navigateToSubPage( "Contacts" )
									     .verifyDisplayedElements( new String[]{ elements.addNewRecord },
									                               new String[] {"Add new Contact record button"},
									                               true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddContactRecordToGrid() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that a new Contact record can be added to the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
        						         .navigateToSubPage( "Contacts" )
        						         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy("External Reference", externalReference, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(externalReference)},
                                        		 				  new String[] {externalReference + " Contact record"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactRecordFields() throws Exception {
   	
        log.startTest( "Verify that all the Contact record fields are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.contactExtReference,
                                                                           elements.contactFirstName,
                                                                           elements.contactLastName,
                                                                           elements.contactEmail,
                                                                           elements.contactPhoneNumber,
                                                                           elements.contactCompany,
                                                                           elements.contactJobTitle},
                                                                   new String[] {"External Reference input field",
                                        		 								 "First Name input field",
                                        		 								 "Last Name input field",
                                        		 								 "Email input field",
                                        		 								 "Phone Number input field",
                                        		 								 "Company dropdown",
                                        		 								 "Job Title input field"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContactWithoutFirstName() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = "";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that a validation message is displayed when Contact record is saved without First Name" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.contactFirstName,
                                                 	super.requiredFieldTextMsg ) },
                                                 				   new String[] {super.requiredFieldTextMsg},
                                                 true );

                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContactWithoutLastName() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = "";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that a validation message is displayed when Contact record is saved without Last Name" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.contactLastName,
                                                 	super.requiredFieldTextMsg ) },
                                                 				   new String[] {super.requiredFieldTextMsg},
                                                 true );

                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyAddContactRecordWhenCancelButtonClicked() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that a new Contact record is not added to the grid when Cancel button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .cancelRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( externalReference ) },
                      		 				   new String[] { externalReference + " Contact record"},
                                                 false );                                        

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditContactRecordWhenCancelButtonClicked() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that already existing Contact record is not updated when click on the Cancel button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )                                         
                                         .startEditingRecord(elements.getRecordRow(externalReference), false, "")
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference + "Changed",
                                        		 firstName + "Changed",
                                        		 lastName + "Changed",
                                        		 "Changed" + email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle + "Changed")
                                         .cancelRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( externalReference + "Changed" ) },
                      		 				   new String[] { externalReference + "Changed" + " Contact record"},
                                                 false )
                                          .clearFilters("", true)
                                          .filterRecordsBy( "External Reference", externalReference, "Text" )
                                          .verifyDisplayedElements( new String[]{ elements.getRecordRow( externalReference ) },
                      		 				   new String[] { externalReference + " Contact record"},
                                                 true );                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditContactRecordWhenSaveButtonClicked() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that already existing Contact record can be successfully edited when click on the Save button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )                                         
                                         .startEditingRecord(elements.getRecordRow(externalReference), false, "")
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference + "Changed",
                                        		 firstName + "Changed",
                                        		 lastName + "Changed",
                                        		 "Changed" + email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle + "Changed")
                                         .saveRecord(false)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "External Reference", externalReference + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( externalReference + "Changed" ) },
                      		 				   new String[] { externalReference + "Changed" + " Contact record"},
                                                 true );                                 

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteContactRecordWhenDeletionIsCanceled() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that a Contact record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .deleteRecord(externalReference, false)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( externalReference ) },
                      		 				   new String[] {externalReference + " Contact record"},
                                                 true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteContactRecordWhenDeletionIsConfirmed() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that the Contact record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .deleteRecord(externalReference, true)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( externalReference ) },
                      		 				   new String[] {externalReference + " Contact record"},
                                                 false );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridTable() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that the Contacts table sub-grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(externalReference))
                                         .verifyDisplayedElements(new String[] {elements.recordsTableSubgrid},
                                        		 				  new String[] {"Contact subgrid table"}, 
                                        		 				  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridTableColumns() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
        log.startTest( "Verify that the Contacts table sub-grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(externalReference))
                                         .verifyDisplayedElements(new String[] {elements.getSubgridTableHeader("Key"),
                                        		 								elements.getSubgridTableHeader("Display Name"),                                        		 								
                                        		 								elements.getSubgridTableHeader("Data Type"), 
                                        		 								elements.getSubgridTableHeader("Special Type"),
                                        		 								elements.getSubgridTableHeader("Value")},
                                        		 				  new String[] {"Key column",
                                        		 								"Display Name column",
                                        		 								"Data Type column",
                                        		 								"Special Type column",
                                        		 								"Value column"}, 
                                        		 				  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridRecordEditButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for editing Contact subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(externalReference))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .verifyDisplayedElements(new String[] {elements.getEditSubgridRecord(key)}, 
                                        		                  new String[] {"Edit Subgrid record button"},
                                        		                  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridRecordDeleteButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for deleting Contact subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(externalReference))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .verifyDisplayedElements(new String[] {elements.getDeleteRecordBtn(key)}, 
                                        		                  new String[] {"Delete Subgrid record button"},
                                        		                  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridRecordCancelButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for cancel Contact subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(externalReference))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .startEditingRecord("", true, key)
                                         .verifyDisplayedElements(new String[] {elements.getCancelSubgridRecord(key)},
                                        		                  new String[] {key + " Subgrid Record Cancel button"},
                                        		                  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridRecordSaveButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
         String externalReference = randNumber + "externalRef";
         String firstName = randNumber + "firstName";
         String lastName = randNumber + "lastName";
         String email = randNumber + "email@mail.com";
         String phoneNumber = randNumber;
         String company = "";
         String jobTitle = randNumber + "jobTitle";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for saving Contact subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Contacts" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContactRecord(externalReference,
                                        		 firstName,
                                        		 lastName,
                                        		 email,
                                        		 phoneNumber,
                                        		 company,
                                        		 jobTitle)
                                         .saveRecord(false)
                                         .filterRecordsBy( "External Reference", externalReference, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(externalReference))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .startEditingRecord("", true, key)
                                         .verifyDisplayedElements(new String[] {elements.getSaveSubgridRecord(key)},
                                        		                  new String[] {key + " Subgrid Record Save button"},
                                        		                  true);
            
            System.out.println(elements.getSaveSubgridRecord(key));
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridAddNewRecordButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        log.startTest( "Verify that a button for adding a Contact subgrid record exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
									    .verifyDisplayedElements( new String[]{ elements.addSubgridRecord },
									                              new String[] {"Add new Contact Subgrid record button"},
									                              true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddContactSubgridRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a new Contact subgrid record can be added to the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
									                              new String[] {key + " Contact Subgrid record"},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridCloseButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        log.startTest( "Verify that a button for closing the Contact subgrid exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
									    .verifyDisplayedElements( new String[]{ elements.subgridClose },
									                              new String[] {"Subgrid Close button"},
									                              true )
									    .getAlertPage(this)
									    .closeSubgrid()
									    .verifyDisplayedElements( new String[]{ elements.recordsTableSubgrid },
									                              new String[] {"Contact subgrid table"},
									                              false );									    

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridRecordFields() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        log.startTest( "Verify that all the Contact subgrid record fields are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .verifyDisplayedElements(new String[] {elements.subgridKey,
							            									   elements.subgridDisplayName,
							            									   elements.subgridDataType,
							            									   elements.subgridSpecialType,
							            									   elements.subgridValue},
							            		                 new String[] {"Key input field",
							            									   "Display Name input field",
							            									   "Data Type input field",
							            									   "Special Type input field",
							            									   "Value input field"},
							            		                 true);
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContactSubgridRecordWithoutKey() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = "";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Contact subgrid record is saved without Key" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridKey, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContactSubgridRecordWithoutDisplayName() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = "";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Contact subgrid record is saved without Display Name" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridDisplayName, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContactSubgridRecordWithoutDataType() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Contact subgrid record is saved without Data Type" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridDataType, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContactSubgridRecordWithoutSpecialType() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Contact subgrid record is saved without Special Type" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridSpecialType, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContactSubgridRecordWithoutValue() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = "";
        
        log.startTest( "Verify that a validation message is displayed when Contact subgrid record is saved without Value" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridValue, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridRecordDataTypeDropdownValues() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        log.startTest( "Verify that all the values for Data Type are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.subgridDataType, "String"),
							            									   elements.getDropdownOption(elements.subgridDataType, "Int32"),
							            									   elements.getDropdownOption(elements.subgridDataType, "Decimal"),
							            									   elements.getDropdownOption(elements.subgridDataType, "Boolean"),
							            									   elements.getDropdownOption(elements.subgridDataType, "DateTime")},
							            		                 new String[] {"String dropdown option",
							            									   "Int32 dropdown option",
							            									   "Decimal dropdown option",
							            									   "Boolean dropdown option",
							            									   "DateTime dropdown option"},
							            		                 true);
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContactSubgridRecordSpecialTypeDropdownValues() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        log.startTest( "Verify that all the values for Special Type are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.subgridSpecialType, "NotApplicable"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Tags"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Language"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Thumbnail"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Email"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Telephone"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Mobile"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Password"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Company")},
							            		                 new String[] {"NotApplicable dropdown option",
							            									   "Tags dropdown option",
							            									   "Language dropdown option",
							            									   "Thumbnail dropdown option",
							            									   "Email dropdown option",
							            									   "Telephone dropdown option",
							            									   "Mobile dropdown option",
							            									   "Password dropdown option",
							            									   "Company dropdown option",},
							            		                 true);
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyAddContactSubgridRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = "";
        
        log.startTest( "Verify that a new Contact subgrid record is not added to the grid when Cancel button is clicked" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .cancelRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
									                              new String[] {key + " Contact Subgrid record"},
									                              false );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditContactSubgridRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that already existing Contact subgrid record can be successfully edited when click on the Save button" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .startEditingRecord("", true, key)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key + "Changed", displayName + "Changed", dataType, specialType, value + "Changed")
									    .saveRecord(true)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key + "Changed") },
									                              new String[] {key + "Changed" + " Contact Subgrid record"},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditContactSubgridRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that already existing Contact subgrid record is not updated when click on the Cancel button" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .startEditingRecord("", true, key)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key + "Changed", displayName + "Changed", dataType, specialType, value + "Changed")
									    .cancelRecord(true)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key + "Changed") },
									                              new String[] {key + "Changed" + " Contact Subgrid record"},
									                              false );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteContactSubgridRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that the Contact subgrid record is successfully deleted when deletion is confirmed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .deleteRecord(key, true)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
					                              				  new String[] {key + " Contact Subgrid record"},
					                                              false );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteContactSubgridRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalReference = randNumber + "externalRef";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String email = randNumber + "email@mail.com";
        String phoneNumber = randNumber;
        String company = "";
        String jobTitle = randNumber + "jobTitle";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that the Contact subgrid record is not deleted when deletion is canceled" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Contacts" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContactRecord(externalReference,
							           	                	firstName,
							           		                lastName,
							           		                email,
							           		                phoneNumber,
							           		                company,
							           		                jobTitle)
							            .saveRecord(false)
							            .filterRecordsBy( "External Reference", externalReference, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(externalReference))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .deleteRecord(key, false)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
					                              				  new String[] {key + " Contact Subgrid record"},
					                                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentTableGrid() throws Exception {

        log.startTest( "Verify that the Content table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .verifyDisplayedElements( new String[]{ elements.recordsTableGrid },
                                        		 				   new String[] {"Content table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentTableColumns() throws Exception {

        log.startTest( "Verify that Content table grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .verifyDisplayedElements( new String[]{ elements.getTableHeader( "External Id" ),
                                                                           elements.getTableHeader( "Title" ),
                                                                           elements.getTableHeader( "Description" ),
                                                                           elements.getTableHeader("Author"),
                                                                           elements.getTableHeader( "Url" ),
                                                                           elements.getTableHeader( "Media" ),
                                                                           elements.getTableHeader( "Type" ),
                                                                           elements.getTableHeader( "Publication Date" ),
                                                                           elements.getTableHeader( "Expiry Date" ),
                                                                           elements.clearAllFilters },
                                                                   new String[] {"External Id column",
                                        		 								 "Title column",
                                         										 "Description column",
                                         										 "Author",
                                         										"Url column",
                                         										"Media column",
                                         										"Type column",
                                         										"Publication Date column",
                                         										"Expiry Date column",
                                         										"Clear all Filters button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentRecordEditButton() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";

        log.startTest( "Verify that a button for editing Content record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
            							 .navigateToSubPage("Content")
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy("Title", title, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(title) + elements.editRecord},
                                        		 				  new String[] {title + " Content record Edit button"},
					                                        	  true);                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentRecordDeleteButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";

        log.startTest( "Verify that a button for delete a Content record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
            							 .navigateToSubPage("Content")
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy("Title", title, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(title) + elements.deleteRecord},
                                        		 				  new String[] {title + " Content record Delete button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentRecordCancelButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";

        log.startTest( "Verify that a button for cancel the edit of a Content record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
            							 .navigateToSubPage("Content")
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy("Title", title, "Text")
                                         .startEditingRecord(elements.getRecordRow(title), false, "")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(title) + elements.cancelRecord},
                                        		 				  new String[] {title + " Content record Cancel button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentRecordSaveButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";

        log.startTest( "Verify that a button for save a Content record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
            							 .navigateToSubPage("Content")
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy("Title", title, "Text")
                                         .startEditingRecord(elements.getRecordRow(title), false, "")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(title) + elements.saveRecord},
                                        		 				  new String[] {title + " Content record Save button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentAddNewRecordButton() throws Exception {

        log.startTest( "Verify that a button for adding Content record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
        						         .navigateToSubPage( "Content" )
									     .verifyDisplayedElements( new String[]{ elements.addNewRecord },
									                               new String[] {"Add new Content record button"},
									                               true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddContentRecordToGrid() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a new Contnet record can be added to the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
        						         .navigateToSubPage( "Content" )
        						         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy("Title", title, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(title)},
                                        		 				  new String[] {title + " Contact record"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentRecordFields() throws Exception {
   	
        log.startTest( "Verify that all the Content record fields are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.alertContentExtID,
                                                                           elements.alertContentTitle,
                                                                           elements.alertContentDescription,
                                                                           elements.alertContentUrl,
                                                                           elements.alertContentMedia,
                                                                           elements.alertContentType,
                                                                           elements.alertContentPublicationDate,
                                                                           elements.alertContentPublicationTime,
                                                                           elements.alertContentExpiryDate,
                                                                           elements.alertContentExpiryTime},
                                                                   new String[] {"External Id input field",
                                        		 								 "Title input field",
                                        		 								 "Description input field",
                                        		 								 "Url input field",
                                        		 								 "Media input field",
                                        		 								 "Type input",
                                        		 								 "Publication Date input field",
                                        		 								 "Publication Time input field",
                                        		 								 "Expiry Date input field",
                                        		 								 "Expiry Time input field"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddContentRecordToGridWithAuthorFieldValueOf100() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = "AbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghijAbcdefghij";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a new Contnet record can be added to the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
        						         .navigateToSubPage( "Content" )
        						         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy("Title", title, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(title)},
                                        		 				  new String[] {title + " Contact record"},
					                                        	  true)
					                     .startEditingRecord(elements.getRecordRow(title), false, null);
            
            log.startStep("Author input value is equal to 100");
            log.endStep(driver.getInputFieldValue(elements.alertContentAuthor).length() == 100);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentWithoutExternalId() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = "";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a validation message is displayed when Content record is saved without External Id" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.alertContentExtID,
                                                 	super.requiredFieldTextMsg ) },
                                                 				   new String[] {super.requiredFieldTextMsg},
                                                 true );

                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentWithoutTitle() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = "";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a validation message is displayed when Content record is saved without Title" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.alertContentTitle,
                                                 	super.requiredFieldTextMsg ) },
                                                 				   new String[] {super.requiredFieldTextMsg},
                                                 true );

                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentWithoutUrl() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = "";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a validation message is displayed when Content record is saved without Url" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.alertContentUrl,
                                                 	super.requiredFieldTextMsg ) },
                                                 				   new String[] {super.requiredFieldTextMsg},
                                                 true );

                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentWithoutType() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = "";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a validation message is displayed when Content record is saved without Type" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.alertContentType,
                                                 	super.requiredFieldTextMsg ) },
                                                 				   new String[] {super.requiredFieldTextMsg},
                                                 true );

                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyAddContentRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a new Content record is not added to the grid when Cancel button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .cancelRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( title ) },
                      		 				   new String[] { title + " Content record"},
                                                 false );                                        

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditContentRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that already existing Content record can be successfully edited when click on the Save button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )                                         
                                         .startEditingRecord(elements.getRecordRow(title), false, "")
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId + "Changed",
                                        		 			title + "Changed",
                                        		 			description + "Changed",
                                        		 			author + "Changed",
                                        		 			"Changed" + url,
                                        		 			media + "Changed",
                                        		 			type + "Changed",
                                        		 			publicationDate,
                                        		 			expiryDate)
                                         .saveRecord(false)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "Title", title + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( title + "Changed" ) },
                      		 				   new String[] { title + "Changed" + " Content record"},
                                                 true );                                 

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditContentRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that already existing Content record is not updated when click on the Cancel button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )                                         
                                         .startEditingRecord(elements.getRecordRow(title), false, "")
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId + "Changed",
                                        		 			title + "Changed",
                                        		 			description + "Changed",
                                        		 			author + "Changed",
                                        		 			"Changed" + url,
                                        		 			media + "Changed",
                                        		 			type + "Changed",
                                        		 			publicationDate,
                                        		 			expiryDate)
                                         .cancelRecord(false)
                                         .filterRecordsBy( "Title", title + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( title + "Changed" ) },
                      		 				   new String[] { title + "Changed" + " Content record"},
                                                 false )
                                          .clearFilters("", true)
                                          .filterRecordsBy( "Title", title, "Text" )
                                          .verifyDisplayedElements( new String[]{ elements.getRecordRow( title ) },
                      		 				   new String[] { title + " Content record"},
                                                 true );                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteContentRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that the Content record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .deleteRecord(title, true)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( title ) },
                      		 				   new String[] {title + " Content record"},
                                                 false );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteContentRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that a Content record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .deleteRecord(title, false)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( title ) },
                      		 				   new String[] {title + " Content record"},
                                                 true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridTable() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that the Content table sub-grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(title))
                                         .verifyDisplayedElements(new String[] {elements.recordsTableSubgrid},
                                        		 				  new String[] {"Content subgrid table"}, 
                                        		 				  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridRecordFields() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        log.startTest( "Verify that all the Contnet subgrid record fields are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .verifyDisplayedElements(new String[] {elements.subgridKey,
							            									   elements.subgridDisplayName,
							            									   elements.subgridDataType,
							            									   elements.subgridSpecialType,
							            									   elements.subgridValue},
							            		                 new String[] {"Key input field",
							            									   "Display Name input field",
							            									   "Data Type input field",
							            									   "Special Type input field",
							            									   "Value input field"},
							            		                 true);
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridTableColumns() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
         
        log.startTest( "Verify that the Content table sub-grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(title))
                                         .verifyDisplayedElements(new String[] {elements.getSubgridTableHeader("Key"),
                                        		 								elements.getSubgridTableHeader("Display Name"),                                        		 								
                                        		 								elements.getSubgridTableHeader("Data Type"), 
                                        		 								elements.getSubgridTableHeader("Special Type"),
                                        		 								elements.getSubgridTableHeader("Value")},
                                        		 				  new String[] {"Key column",
                                        		 								"Display Name column",
                                        		 								"Data Type column",
                                        		 								"Special Type column",
                                        		 								"Value column"}, 
                                        		 				  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridRecordEditButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
        
         String externalId = randNumber + "externalId";
         String title = randNumber + "title";
         String description = randNumber + "description";
         String author = randNumber + "author";
         String url = randNumber + "url.com";
         String media = randNumber + "media";
         String type = randNumber + "type";
         String publicationDate = "";
         String expiryDate = "";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for editing Content subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(title))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .verifyDisplayedElements(new String[] {elements.getEditSubgridRecord(key)}, 
                                        		                  new String[] {"Edit Subgrid record button"},
                                        		                  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridRecordDeleteButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
    	 String externalId = randNumber + "externalId";
         String title = randNumber + "title";
         String description = randNumber + "description";
         String author = randNumber + "author";
         String url = randNumber + "url.com";
         String media = randNumber + "media";
         String type = randNumber + "type";
         String publicationDate = "";
         String expiryDate = "";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for deleting Content subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(title))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .verifyDisplayedElements(new String[] {elements.getDeleteRecordBtn(key)}, 
                                        		                  new String[] {"Delete Subgrid record button"},
                                        		                  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridRecordCancelButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
    	 String externalId = randNumber + "externalId";
         String title = randNumber + "title";
         String description = randNumber + "description";
         String author = randNumber + "author";
         String url = randNumber + "url.com";
         String media = randNumber + "media";
         String type = randNumber + "type";
         String publicationDate = "";
         String expiryDate = "";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for cancel Content subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(title))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .startEditingRecord("", true, key)
                                         .verifyDisplayedElements(new String[] {elements.getCancelSubgridRecord(key)},
                                        		                  new String[] {key + " Subgrid Record Cancel button"},
                                        		                  true);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridRecordSaveButton() throws Exception {

    	 String randNumber = driver.getTimestamp();
         
    	 String externalId = randNumber + "externalId";
         String title = randNumber + "title";
         String description = randNumber + "description";
         String author = randNumber + "author";
         String url = randNumber + "url.com";
         String media = randNumber + "media";
         String type = randNumber + "type";
         String publicationDate = "";
         String expiryDate = "";
         
         String key = randNumber + "Key";
         String displayName = randNumber + "Name";
         String dataType = "System.String";
         String specialType = "NotApplicable";
         String value = randNumber + "value";
         
        log.startTest( "Verify that a button for saving Content subgrid record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Content" )
                                         .startCreatingRecord( false )
                                         .getAlertPage( this )
                                         .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
                                         .saveRecord(false)
                                         .filterRecordsBy( "Title", title, "Text" )
                                         .accessRecordSubgrid(elements.getRecordRow(title))
                                         .startCreatingRecord(true)
                                         .getAlertPage(this)
                                         .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                         .saveRecord(true)
                                         .startEditingRecord("", true, key)
                                         .verifyDisplayedElements(new String[] {elements.getSaveSubgridRecord(key)},
                                        		                  new String[] {key + " Subgrid Record Save button"},
                                        		                  true);
            
            System.out.println(elements.getSaveSubgridRecord(key));
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridAddNewRecordButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        log.startTest( "Verify that a button for adding a Content subgrid record exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
									    .verifyDisplayedElements( new String[]{ elements.addSubgridRecord },
									                              new String[] {"Add new Content Subgrid record button"},
									                              true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddContentSubgridRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a new Content subgrid record can be added to the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
									                              new String[] {key + " Contact Subgrid record"},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridCloseButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        log.startTest( "Verify that a button for closing the Content subgrid exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
									    .verifyDisplayedElements( new String[]{ elements.subgridClose },
									                              new String[] {"Subgrid Close button"},
									                              true )
								         .getAlertPage(this)
								         .closeSubgrid()
							             .verifyDisplayedElements( new String[]{ elements.recordsTableSubgrid },
															       new String[] {"Content subgrid table"},
																   false );	

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentSubgridRecordWithoutKey() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = "";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Content subgrid record is saved without Key" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridKey, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentSubgridRecordWithoutDisplayName() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = "";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Content subgrid record is saved without Display Name" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridDisplayName, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentSubgridRecordWithoutDataType() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "";
        String specialType = "NotApplicable";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Content subgrid record is saved without Data Type" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridDataType, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentSubgridRecordWithoutSpecialType() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "";
        String value = randNumber + "value";
        
        log.startTest( "Verify that a validation message is displayed when Content subgrid record is saved without Special Type" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridSpecialType, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageContentSubgridRecordWithoutValue() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = "";
        
        log.startTest( "Verify that a validation message is displayed when Content subgrid record is saved without Value" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField(elements.subgridValue, super.requiredFieldTextMsg) },
									                              new String[] {super.requiredFieldTextMsg},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridRecordDataTypeDropdownValues() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        log.startTest( "Verify that all the values for Data Type are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.subgridDataType, "String"),
							            									   elements.getDropdownOption(elements.subgridDataType, "Int32"),
							            									   elements.getDropdownOption(elements.subgridDataType, "Decimal"),
							            									   elements.getDropdownOption(elements.subgridDataType, "Boolean"),
							            									   elements.getDropdownOption(elements.subgridDataType, "DateTime")},
							            		                 new String[] {"String dropdown option",
							            									   "Int32 dropdown option",
							            									   "Decimal dropdown option",
							            									   "Boolean dropdown option",
							            									   "DateTime dropdown option"},
							            		                 true);
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayContentSubgridRecordSpecialTypeDropdownValues() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        log.startTest( "Verify that all the values for Special Type are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.subgridSpecialType, "NotApplicable"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Tags"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Language"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Thumbnail"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Email"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Telephone"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Mobile"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Password"),
							            									   elements.getDropdownOption(elements.subgridSpecialType, "Company")},
							            		                 new String[] {"NotApplicable dropdown option",
							            									   "Tags dropdown option",
							            									   "Language dropdown option",
							            									   "Thumbnail dropdown option",
							            									   "Email dropdown option",
							            									   "Telephone dropdown option",
							            									   "Mobile dropdown option",
							            									   "Password dropdown option",
							            									   "Company dropdown option",},
							            		                 true);
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyAddContentSubgridRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = "";
        
        log.startTest( "Verify that a new Content subgrid record is not added to the grid when Cancel button is clicked" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .cancelRecord(true)
									    .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
									                              new String[] {key + " Contact Subgrid record"},
									                              false );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditContentSubgridRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that already existing Content subgrid record can be successfully edited when click on the Save button" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .startEditingRecord("", true, key)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key + "Changed", displayName + "Changed", dataType, specialType, value + "Changed")
									    .saveRecord(true)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key + "Changed") },
									                              new String[] {key + "Changed" + " Contact Subgrid record"},
									                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditContentSubgridRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that already existing Content subgrid record is not updated when click on the Cancel button" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .startEditingRecord("", true, key)
                                        .getAlertPage(this)
                                        .fillSubgridRecord(key + "Changed", displayName + "Changed", dataType, specialType, value + "Changed")
									    .cancelRecord(true)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key + "Changed") },
									                              new String[] {key + "Changed" + " Contact Subgrid record"},
									                              false );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteContentSubgridRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that the Content subgrid record is not deleted when deletion is canceled" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .deleteRecord(key, false)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
					                              				  new String[] {key + " Content Subgrid record"},
					                                              true );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteContentSubgridRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String externalId = randNumber + "externalId";
        String title = randNumber + "title";
        String description = randNumber + "description";
        String author = randNumber + "author";
        String url = randNumber + "url.com";
        String media = randNumber + "media";
        String type = randNumber + "type";
        String publicationDate = "";
        String expiryDate = "";
        
        String key = randNumber + "Key";
        String displayName = randNumber + "Name";
        String dataType = "System.String";
        String specialType = "NotApplicable";
        String value = randNumber + "Value";
        
        log.startTest( "Verify that the Content subgrid record is successfully deleted when deletion is confirmed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Content" )
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillContentRecord(externalId, title, description, author, url, media, type, publicationDate, expiryDate)
							            .saveRecord(false)
							            .filterRecordsBy( "Title", title, "Text" )
							            .accessRecordSubgrid(elements.getRecordRow(title))
							            .startCreatingRecord(true)
							            .getAlertPage(this)
                                        .fillSubgridRecord(key, displayName, dataType, specialType, value)
                                        .saveRecord(true)
                                        .deleteRecord(key, true)
                                        .verifyDisplayedElements( new String[]{ elements.getSubgridRecordRow(key) },
					                              				  new String[] {key + " Contact Subgrid record"},
					                                              false );
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplatesTableGrid() throws Exception {

        log.startTest( "Verify that the Templates table grid is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Templates" )
                                         .verifyDisplayedElements( new String[]{ elements.recordsTableGrid },
                                        		 				   new String[] {"Templates table grid"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplatesTableColumns() throws Exception {

        log.startTest( "Verify that Templates table grid columns are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Templates" )
                                         .verifyDisplayedElements( new String[]{ elements.getTableHeader( "Name" ),
                                                                           elements.getTableHeader( "Channel" ),
                                                                           elements.getTableHeader( "Delivery Channel Type" ),
                                                                           elements.getTableHeader( "Template Type" ),
                                                                           elements.getTableHeader( "Content" ),
                                                                           elements.getTableHeader( "Reference" ),
                                                                           elements.clearAllFilters },
                                                                   new String[] {"Name column",
                                        		 								 "Channel column",
                                         										 "Delivery Channel Type column",
                                         										"Template Type column",
                                         										"Content column",
                                         										"Reference column",
                                         										"Clear all Filters button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateRecordEditButton() throws Exception {

        String randNumber = driver.getTimestamp();       
        
        String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";

        log.startTest( "Verify that a button for editing Template record exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
            							 .navigateToSubPage("Channel/Aoi Editor")
            							 .getAlertPage( this )
            							 .startCreatingChannel()
            							 .fillChannelRecord(key,
            									 			channelName,
            									 			maxContentAge,
            									 			isSuppressed,
            									 			deliveryTemplateKey,
            									 			emailProviderProfile,
							            				    "",
							            				    domainId,
							            				    crmProviderProfile,
							            				    "",
							            				    true)
							              .navigateToSubPage("Templates")
							              .startCreatingRecord( false )
							              .getAlertPage( this )
							              .fillTemplateRecord(name,
							            		              channel,
							            		              deliveryChannelType,
							            		              templateType,
							            		              content,
							            		              reference)
							              .saveRecord(false)
							              .filterRecordsBy("Name", name, "Text")
							              .verifyDisplayedElements(new String[] {elements.getRecordRow(name) + elements.editRecord},
							            		  				   new String[] {name + " Template record Edit button"},
							            		  				   true);                                         

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateRecordDeleteButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";

        log.startTest( "Verify that a button for delete a Template record exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							           				    "",
							           				 domainId,
							           				    crmProviderProfile,
							           				    "",
							           				    true)
							             .navigateToSubPage("Templates")
							             .startCreatingRecord( false )
							             .getAlertPage( this )
							             .fillTemplateRecord(name,
							           		              channel,
							           		              deliveryChannelType,
							           		              templateType,
							           		              content,
							           		              reference)
							             .saveRecord(false)
                                         .filterRecordsBy("Name", name, "Text")
                                         .verifyDisplayedElements(new String[] {elements.getRecordRow(name) + elements.deleteRecord},
                                        		 				  new String[] {name + " Template record Delete button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateRecordCancelButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";

        log.startTest( "Verify that a button for cancel the edit of a Template record exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							          				    "",
							          				  domainId,
							          				    crmProviderProfile,
							          				    "",
							          				    true)
							            .navigateToSubPage("Templates")
							            .startCreatingRecord( false )
							            .getAlertPage( this )
							            .fillTemplateRecord(name,
							          		              channel,
							          		              deliveryChannelType,
							          		              templateType,
							          		              content,
							          		              reference)
							            .saveRecord(false)
							            .filterRecordsBy("Name", name, "Text")
                                        .startEditingRecord(elements.getRecordRow(name), false, "")
                                        .verifyDisplayedElements(new String[] {elements.getRecordRow(name) + elements.cancelRecord},
                                        		 				  new String[] {name + " Template record Cancel button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateRecordSaveButton() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";

        log.startTest( "Verify that a button for save a Template record exists in the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							         				    "",
							         				   domainId,
							         				    crmProviderProfile,
							         				    "",
							         				    true)
							           .navigateToSubPage("Templates")
							           .startCreatingRecord( false )
							           .getAlertPage( this )
							           .fillTemplateRecord(name,
							         		              channel,
							         		              deliveryChannelType,
							         		              templateType,
							         		              content,
							         		              reference)
							           .saveRecord(false)
							           .filterRecordsBy("Name", name, "Text")
                                       .startEditingRecord(elements.getRecordRow(name), false, "")
                                       .verifyDisplayedElements(new String[] {elements.getRecordRow(name) + elements.saveRecord},
                                        		 				  new String[] {name + " Template record Save button"},
					                                        	  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateAddNewRecordButton() throws Exception {

        log.startTest( "Verify that a button for adding Template record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
        						         .navigateToSubPage( "Template" )
									     .verifyDisplayedElements( new String[]{ elements.addNewRecord },
									                               new String[] {"Add new Template record button"},
									                               true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddTemplateRecordToGrid() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that a new Template record can be added to the grid" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
								        			    	"",
								        			    	domainId,
								        				    crmProviderProfile,
								        				    "",
								        				    true)
							          .navigateToSubPage("Templates")
							          .startCreatingRecord( false )
							          .getAlertPage( this )
							          .fillTemplateRecord(name,
							        		              channel,
							        		              deliveryChannelType,
							        		              templateType,
							        		              content,
							        		              reference)
							          .saveRecord(false)
                                      .filterRecordsBy("Name", name, "Text")
                                      .verifyDisplayedElements(new String[] {elements.getRecordRow(name)},
                                        		 		       new String[] {name + " Template record"},
					                                           true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateRecordFields() throws Exception {
   	
        log.startTest( "Verify that all the Template record fields are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Templates" )
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.templateName,
                                                                           elements.templateChannel,
                                                                           elements.templateDeliveryChannelType,
                                                                           elements.templateType,
                                                                           elements.templateContent,
                                                                           elements.templateReference },
                                                                   new String[] {"Name input field",
                                        		 								 "Channel dropdown",
                                        		 								 "Delivery Channel Type dropdown",
                                        		 								 "Template Type dropdown",
                                        		 								 "Content textarea",
                                        		 								 "Reference input field"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateRecordChannelAsDropdownOption() throws Exception {
   	
    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        		
        log.startTest( "Verify that the newly created channel is succesfsully displayed in the Template record Channel dropdown" );
        try {
        	
        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Channel/Aoi Editor" )
							             .getAlertPage(this)
							             .startCreatingChannel()
							             .fillChannelRecord(key,
							           	   	 			    channelName,
							           		 			    maxContentAge,
							           		 			    isSuppressed,
							           		 			    deliveryTemplateKey,
							           		 			emailProviderProfile,
							           		 			    "",
							           		 			domainId,
							           		 			    crmProviderProfile,
							           		 			    "",
							           		 			    true)
			.verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
								     new String[] {channelName + " record"},
								     true).navigateToPage( "Alert" )
                                          .navigateToSubPage( "Templates" )
                                          .startCreatingRecord( false )
             .verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.templateChannel, channelName)},
            		 				  new String[] {channelName + " dropdown option"},
            		 				  true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageTemplateWithoutName() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = "";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that a validation message is displayed when Template record is saved without Name" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							       				    "",
							       				 domainId,
							       				    crmProviderProfile,
							       				    "",
							       				    true)
							         .navigateToSubPage("Templates")
							         .startCreatingRecord( false )
							         .getAlertPage( this )
							         .fillTemplateRecord(name,
							       		              channel,
							       		              deliveryChannelType,
							       		              templateType,
							       		              content,
							       		              reference)
							         .saveRecord(false)
                                     .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.templateName,
                                                 			   super.requiredFieldTextMsg ) }, new String[] {super.requiredFieldTextMsg},
                                                 			   true );                                       

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageTemplateWithoutChannel() throws Exception {

    	String randNumber = driver.getTimestamp();
        
        String name = randNumber + "name";
        String channel = "";
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that a validation message is displayed when Template record is saved without Channel" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )										 
								         .navigateToSubPage("Templates")
								         .startCreatingRecord( false )
								         .getAlertPage( this )
								         .fillTemplateRecord(name,
								       		              channel,
								       		              deliveryChannelType,
								       		              templateType,
								       		              content,
								       		              reference)
								         .saveRecord(false)
	                                     .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.templateChannel,
	                                                 			   super.requiredFieldTextMsg ) }, new String[] {super.requiredFieldTextMsg},
	                                                 			   true );                                       

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageTemplateWithoutDeliveryChannelType() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that a validation message is displayed when Template record is saved without Delivery Channel Type" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							       				    "",
							       				 domainId,
							       				    crmProviderProfile,
							       				    "",
							       				    true)
							         .navigateToSubPage("Templates")
							         .startCreatingRecord( false )
							         .getAlertPage( this )
							         .fillTemplateRecord(name,
							       		              channel,
							       		              deliveryChannelType,
							       		              templateType,
							       		              content,
							       		              reference)
							         .saveRecord(false)
                                     .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.templateDeliveryChannelType,
                                                 			   super.requiredFieldTextMsg ) }, new String[] {super.requiredFieldTextMsg},
                                                 			   true );                                       

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageTemplateWithoutTemplateType() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that a validation message is displayed when Template record is saved without Template Type" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							       				    "",
							       				 domainId,
							       				    crmProviderProfile,
							       				    "",
							       				    true)
							         .navigateToSubPage("Templates")
							         .startCreatingRecord( false )
							         .getAlertPage( this )
							         .fillTemplateRecord(name,
							       		              channel,
							       		              deliveryChannelType,
							       		              templateType,
							       		              content,
							       		              reference)
							         .saveRecord(false)
                                     .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.templateType,
                                                 			   super.requiredFieldTextMsg ) }, new String[] {super.requiredFieldTextMsg},
                                                 			   true );                                       

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayValidationMessageTemplateWithoutReference() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = "";
         
        log.startTest( "Verify that a validation message is displayed when Template record is saved without Reference" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							       				    "",
							       				 domainId,
							       				    crmProviderProfile,
							       				    "",
							       				    true)
							         .navigateToSubPage("Templates")
							         .startCreatingRecord( false )
							         .getAlertPage( this )
							         .fillTemplateRecord(name,
							       		              channel,
							       		              deliveryChannelType,
							       		              templateType,
							       		              content,
							       		              reference)
							         .saveRecord(false)
                                     .verifyDisplayedElements( new String[]{ elements.getValidationMessageRecordField( elements.templateReference,
                                                 			   super.requiredFieldTextMsg ) }, new String[] {super.requiredFieldTextMsg},
                                                 			   true );                                       

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateDeliveryChannelTypeDropdownValues() throws Exception {

        log.startTest( "Verify that all the values for Template Delivery Channel Type is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
            							 .navigateToSubPage("Templates")
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getDropdownOption( elements.templateDeliveryChannelType,
                                                                                                             "EmailPlainText" ),
                                                                           elements.getDropdownOption( elements.templateDeliveryChannelType,
                                                                                                       "EmailHtml" ),
                                                                           elements.getDropdownOption( elements.templateDeliveryChannelType,
                                                                                                       "Mobile" ),
                                                                           elements.getDropdownOption( elements.templateDeliveryChannelType,
                                                                                                       "Sms" ),
                                                                           elements.getDropdownOption( elements.templateDeliveryChannelType,
                                                                                                       "Twitter" ),
                                                                           elements.getDropdownOption( elements.templateDeliveryChannelType,
                                                                                                       "Yammer" ),
                                                                           elements.getDropdownOption( elements.templateDeliveryChannelType,
                                                                                                       "Print" )},
                                                                   new String[] {"EmailPlainText dropdown option",
                                        		 								 "EmailHtml dropdown option",
                                        		 								 "Mobile dropdown option",
                                        		 								 "Sms dropdown option",
                                        		 								 "Twitter dropdown option",
                                        		 								 "Yammer dropdown option",
                                        		 								 "Print dropdown option"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayTemplateTypeDropdownValues() throws Exception {

        log.startTest( "Verify that all the values for Template Type is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
            							 .navigateToSubPage("Templates")
                                         .startCreatingRecord( false )
                                         .verifyDisplayedElements( new String[]{ elements.getDropdownOption( elements.templateType,
                                                                                                             "NotSet" ),
                                                                           elements.getDropdownOption( elements.templateType,
                                                                                                       "AutoAlerts" )},
                                                                   new String[] {"NotSet dropdown option",
                                        		 								 "AutoAlerts dropdown option"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyAddTemplateRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
        
        log.startTest( "Verify that a new Template record is not added to the grid when Cancel button is clicked" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							      				    "",
							      				  domainId,
							      				    crmProviderProfile,
							      				    "",
							      				    true)
							        .navigateToSubPage("Templates")
							        .startCreatingRecord( false )
							        .getAlertPage( this )
							        .fillTemplateRecord(name,
							      		              channel,
							      		              deliveryChannelType,
							      		              templateType,
							      		              content,
							      		              reference)
                                         .cancelRecord(false)
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name ) },
                      		 				   new String[] { name + " Template record"},
                                                 false );                                        

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditTemplateRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that already existing Template record can be successfully edited when click on the Save button" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							     				    "",
							     				   domainId,
							     				    crmProviderProfile,
							     				    "",
							     				    true)
							       .navigateToSubPage("Templates")
							       .startCreatingRecord( false )
							       .getAlertPage( this )
							       .fillTemplateRecord(name,
							     		              channel,
							     		              deliveryChannelType,
							     		              templateType,
							     		              content,
							     		              reference)
							     		 .saveRecord(false)
                                         .filterRecordsBy( "Name", name, "Text" )                                         
                                         .startEditingRecord(elements.getRecordRow(name), false, "")
                                         .getAlertPage( this )
                                         .fillTemplateRecord(name + "Changed",
						     		              "",
						     		              "Twitter",
						     		              "NotSet",
						     		              content + "Changed",
						     		              reference + "Changed")
                                         .saveRecord(false)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "Name", name + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name + "Changed" ) },
                      		 				   new String[] { name + "Changed" + " Content record"},
                                                 true );                                 

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditTemplateRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that already existing Content record is not updated when click on the Cancel button" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							     				    "",
							     				   domainId,
							     				    crmProviderProfile,
							     				    "",
							     				    true)
							       .navigateToSubPage("Templates")
							       .startCreatingRecord( false )
							       .getAlertPage( this )
							       .fillTemplateRecord(name,
							     		              channel,
							     		              deliveryChannelType,
							     		              templateType,
							     		              content,
							     		              reference)
							     		 .saveRecord(false)
                                         .filterRecordsBy( "Name", name, "Text" )                                         
                                         .startEditingRecord(elements.getRecordRow(name), false, "")
                                         .getAlertPage( this )
                                         .fillTemplateRecord(name + "Changed",
						     		              "",
						     		              "Twitter",
						     		              "NotSet",
						     		              content + "Changed",
						     		              reference + "Changed")
                                         .cancelRecord(false)
                                         .clearFilters("", true)
                                         .filterRecordsBy( "Name", name + "Changed", "Text" )
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name + "Changed" ) },
                      		 				   new String[] { name + "Changed" + " Content record"},
                                                 false );                                 

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteTemplateRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that a Template record is not deleted when deletion is canceled" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							    				    "",
							    				    domainId,
							    				    crmProviderProfile,
							    				    "",
							    				    true)
							      .navigateToSubPage("Templates")
							      .startCreatingRecord( false )
							      .getAlertPage( this )
							      .fillTemplateRecord(name,
							    		              channel,
							    		              deliveryChannelType,
							    		              templateType,
							    		              content,
							    		              reference)
							    		 .saveRecord(false)							            
                                         .filterRecordsBy( "Name", name, "Text" )
                                         .deleteRecord(name, false)
                                         .clearFilters("", true)
                                         .verifyDisplayedElements( new String[]{ elements.getRecordRow( name ) },
                      		 				   new String[] {name + " Content record"},
                                                 true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteTemplateRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
        
    	String key = randNumber + "key";
        String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String name = randNumber + "name";
        String channel = channelName;
        String deliveryChannelType = "EmailHtml";
        String templateType = "AutoAlerts";
        String content = randNumber + "TestContnet";
        String reference = randNumber + "type";
         
        log.startTest( "Verify that the Template record is successfully deleted when deletion is confirmed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
										 .navigateToSubPage("Channel/Aoi Editor")
										 .getAlertPage( this )
										 .startCreatingChannel()
										 .fillChannelRecord(key,
												 			channelName,
												 			maxContentAge,
												 			isSuppressed,
												 			deliveryTemplateKey,
												 			emailProviderProfile,
							   				    "",
							   				 domainId,
							   				    crmProviderProfile,
							   				    "",
							   				    true)
							     .navigateToSubPage("Templates")
							     .startCreatingRecord( false )
							     .getAlertPage( this )
							     .fillTemplateRecord(name,
							   		              channel,
							   		              deliveryChannelType,
							   		              templateType,
							   		              content,
							   		              reference)
							   		 .saveRecord(false)							            
							            .filterRecordsBy( "Name", name, "Text" )
							            .deleteRecord(name, true)
							            .clearFilters("", true)
							            .verifyDisplayedElements( new String[]{ elements.getRecordRow( name ) },
								 				   new String[] {name + " Content record"},
							                    false );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelExpandAndCollapseRecordsButton() throws Exception {

        log.startTest( "Verify that the Expand/Collapse button is successfully displayed at the Meta Model page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .verifyDisplayedElements( new String[]{ elements.metaModelExpandAllBtn},
                                        		 				   new String[] {"Expand All button"},
                                                                   true )
                                         .verifyDisplayedElements( new String[]{ elements.metaModelCollapseAllBtn},
                                        		 				   new String[] {"Collapse All button"},
                                                                   false )
                                         .getAlertPage(this)
                                         .interactMetaModelTree("Expand")
                                         .verifyDisplayedElements( new String[]{ elements.metaModelCollapseAllBtn},
                                          		 				   new String[] {"Collapse All button"},
                                                                   true )
                                         .verifyDisplayedElements( new String[]{ elements.metaModelExpandAllBtn},
                                        		 				   new String[] {"Expand All button"},
                                                                   false )
                                         .getAlertPage(this)
                                         .interactMetaModelTree("Collapse")
                                         .verifyDisplayedElements( new String[]{ elements.metaModelCollapseAllBtn},
                                                       		 	   new String[] {"Collapse All button"},
                                                                   false )
                                         .verifyDisplayedElements( new String[]{ elements.metaModelExpandAllBtn},
                                                             	   new String[] {"Expand All button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelAddCategoryUiElements() throws Exception {

        log.startTest( "Verify that Category name, Category key input fields and Add Category button is successfully displayed at the Meta Model page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .verifyDisplayedElements( new String[]{ elements.metaModelCategoryName,
                                        		 							     elements.metaModelCategoryKey,
                                        		 							     elements.metaModelAddCategory},
                                        		 				   new String[] {"Category Name input field",
                                         										 "Category Key input field",
                                         										 "Add Category button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddMetaModelCategoryRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that a Category record can be successfully created at the Meta Model page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryRecord(categoryName)},
            					     new String[] {categoryName + " category"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisabledMetaModelAddCategoryButtonWhithoutCategoryName() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = "";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that Add Category button is disabled when Category Name input field has no value" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
            .verifyDisplayedElements(new String[] {elements.metaModelAddCategoryBtnDisabled},
            					     new String[] {"Add Category button - disabled"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisabledMetaModelAddCategoryButtonWhithoutCategoryKey() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = "";
    	 
        log.startTest( "Verify that Add Category button is disabled when Category Key input field has no value" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
            .verifyDisplayedElements(new String[] {elements.metaModelAddCategoryBtnDisabled},
            					     new String[] {"Add Category button - disabled"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisabledMetaModelAddCategoryButtonWhithoutCategoryNameCategoryKey() throws Exception {
    	
    	String categoryName = "";
    	String categoryKey = "";
    	 
        log.startTest( "Verify that Add Category button is disabled when Category Name and Category Key input field has no value" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
            .verifyDisplayedElements(new String[] {elements.metaModelAddCategoryBtnDisabled},
            					     new String[] {"Add Category button - disabled"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelCategoryRecordEditButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that a button for editing an existing Category record is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryRecord(categoryName),
            									   elements.getMetaModelEditRecord(categoryName)},
            					     new String[] {categoryName + " category",
            									   categoryName + " edit button"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelCategoryRecordCancelButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that a button for canceling an existing Category record is successfully displayed when the record is in edit mode" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .startEditingMetaModelRecord(MetaModelRecordType.CATEGORY.recordType, categoryName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryCancelRecord()},
            						 new String[] {categoryName + " Cancel button"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelCategoryRecordSaveButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that a button for saving an existing Category record is successfully displayed when the record is in edit mode" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .startEditingMetaModelRecord(MetaModelRecordType.CATEGORY.recordType, categoryName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategorySaveRecord()},
            						 new String[] {categoryName + " Save button"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditMetaModelCategoryRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that already existing Category record is not updated when click on the Cancel button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .startEditingMetaModelRecord(MetaModelRecordType.CATEGORY.recordType, categoryName)
                                         .editCategoryValues(categoryName + "Changed", categoryKey + "Changed", false)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryRecord(categoryName + "Changed")},
            						 new String[] {categoryName + "Changed" + " category record"},
            						 false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditMetaModelCategoryRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that already existing Category record is successfully updated when click on the Saves button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .startEditingMetaModelRecord(MetaModelRecordType.CATEGORY.recordType, categoryName)
                                         .editCategoryValues(categoryName + "Changed", categoryKey + "Changed", true)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryRecord(categoryName + "Changed")},
            						 new String[] {categoryName + "Changed" + " category record"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelCategoryRecordDeleteButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that a button for deleting an existing Category record is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryRecord(categoryName),
            									   elements.getMetaModelDeleteRecord(categoryName)},
            					     new String[] {categoryName + " category",
            									   categoryName + " delete button"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteMetaModelCategoryRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that the Meta Model Category record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .deleteMetaModelRecord(categoryName, true)
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryRecord(categoryName)},
            						 new String[] {categoryName + " record"},
            						 false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteMetaModelCategoryRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that the Meta Model Category record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .deleteMetaModelRecord(categoryName, false)
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.getMetaModelCategoryRecord(categoryName)},
            						 new String[] {categoryName + " record"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelAoiUiElements() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	 
        log.startTest( "Verify that AOI name, AOI key input fields and Add AOI button is successfully displayed at the Meta Model page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAOIName(categoryName),
            									   elements.getMetaModelAOIKey(categoryName),
            									   elements.getMetaModelAddAOI(categoryName)},
            						 new String[] {categoryName + " AOI name input field",
            									   categoryName + " AOI key input field",
            									   categoryName + " Add AOI button"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddMetaModelAOIRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that an AOI record can be successfully created at the Meta Model page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiRecord(categoryName, aoiName)},
            					     new String[] {aoiName + " aoi"},
            					     true);                        

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelAoiRecordEditButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that a button for editing an existing AOI record is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelEditRecord(aoiName)},
            					     new String[] {aoiName + " Edit button"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelAoiRecordSaveButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that a button for saving an existing AOI record is successfully displayed when the record is in edit mode" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.AREA_OF_INTEREST.recordType, aoiName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiSaveRecord()},
            						 new String[] {aoiName + " Save button"},
            						 true);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelAoiRecordCancelButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that a button for canceling an existing AOI record is successfully displayed when the record is in edit mode" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.AREA_OF_INTEREST.recordType, aoiName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiCancelRecord()},
            						 new String[] {aoiName + " Cancel button"},
            						 true);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditMetaModelAoiRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that already existing AOI record is not updated when click on the Cancel button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.AREA_OF_INTEREST.recordType, aoiName)
                                         .editAoiValues(aoiName + "Changed", aoiKey + "Changed", false)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiRecord(categoryName, aoiName + "Changed")},
            						 new String[] {aoiName + "Changed" + " aoi record"},
            						 false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditMetaModelAoiRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that already existing AOI record is successfully updated when click on the Save button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.AREA_OF_INTEREST.recordType, aoiName)
                                         .editAoiValues(aoiName + "Changed", aoiKey + "Changed", true)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiRecord(categoryName, aoiName + "Changed")},
            						 new String[] {aoiName + "Changed" + " aoi record"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelAoiRecordDeleteButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that a button for deleting an existing Aoi record is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiRecord(categoryName, aoiName),
            									   elements.getMetaModelDeleteRecord(aoiName)},
            					     new String[] {aoiName + " aoi",
            									   aoiName + " delete button"},
            					     true);           

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteMetaModelAoiRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	     	 
        log.startTest( "Verify that the Meta Model AOI record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .deleteMetaModelRecord(aoiName, true)
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiRecord(categoryName, aoiName)},
            						 new String[] {aoiName + " record"},
            						 false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteMetaModelAoiRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	     	 
        log.startTest( "Verify that the Meta Model AOI record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .deleteMetaModelRecord(aoiName, false)
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.getMetaModelAoiRecord(categoryName, aoiName)},
            						 new String[] {aoiName + " record"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelTagUiElements() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	 
        log.startTest( "Verify that Tag name input field and Add Tag button is successfully displayed at the Meta Model page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagName(categoryName, aoiName),
            									   elements.getMetaModelAddTag(categoryName, aoiName)},
            						 new String[] {aoiName + " Tag name input field",
            									   aoiName + " Add Tag button"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddMetaModelTagRecord() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	 
        log.startTest( "Verify that a Tag record can be successfully created at the Meta Model page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagRecord(categoryName, aoiName, tagName)},
            					     new String[] {tagName + " tag"},
            					     true);                        

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelTagRecordEditButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	 
        log.startTest( "Verify that a button for editing an existing Tag record is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelEditRecord(tagName)},
            					     new String[] {tagName + " Edit button"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelTagRecordSaveButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	 
        log.startTest( "Verify that a button for saving an existing Tag record is successfully displayed when the record is in edit mode" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.TAG.recordType, tagName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagSaveRecord()},
            						 new String[] {tagName + " Save button"},
            						 true);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelTagRecordCancelButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	 
        log.startTest( "Verify that a button for canceling an existing Tag record is successfully displayed when the record is in edit mode" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.TAG.recordType, tagName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagCancelRecord()},
            						 new String[] {tagName + " Cancel button"},
            						 true);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditMetaModelTagRecordWhenCancelButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	 
        log.startTest( "Verify that already existing Tag record is not updated when click on the Cancel button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.TAG.recordType, tagName)
                                         .editTagValue(tagName + "Changed", false)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagRecord(categoryName, aoiName, tagName + "Changed")},
            						 new String[] {tagName + "Changed" + " tag record"},
            						 false);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditMetaModelTagRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	 
        log.startTest( "Verify that already existing Tag record is successfully updated when click on the Save button" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
                                         .startEditingMetaModelRecord(MetaModelRecordType.TAG.recordType, tagName)
                                         .editTagValue(tagName + "Changed", true)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagRecord(categoryName, aoiName, tagName + "Changed")},
            						 new String[] {tagName + "Changed" + " tag record"},
            						 true);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelTagRecordDeleteButton() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	 
        log.startTest( "Verify that a button for deleting an existing Tag record is successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagRecord(categoryName, aoiName, tagName),
            									   elements.getMetaModelDeleteRecord(tagName)},
            					     new String[] {tagName + " tag",
            									   tagName + " delete button"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteMetaModelTagRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	     	 
        log.startTest( "Verify that the Meta Model Tag record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
                                         .deleteMetaModelRecord(tagName, false)
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagRecord(categoryName, aoiName, tagName)},
            						 new String[] {tagName + " record"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteMetaModelTagRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
    	String tagName = randNumber + "tagName";
    	     	 
        log.startTest( "Verify that the Meta Model Tag record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
                                         .addCategory(categoryName, categoryKey)
                                         .addAOI(aoiName, aoiKey, categoryName)
                                         .addTag(tagName, categoryName, aoiName)
                                         .deleteMetaModelRecord(tagName, true)
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.getMetaModelTagRecord(categoryName, aoiName, tagName)},
            						 new String[] {tagName + " record"},
            						 false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelChannelRecordsContainer() throws Exception {
	     	 
        log.startTest( "Verify that Meta Model page has a separate container where all the channels created are listed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.metaModelChannelsListContainer},
            						 new String[] {"Channels records list container"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayMetaModelChannelRecordInContainerWhenAdded() throws Exception {
	     	 
    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        log.startTest( "Verify that when a new Channel record is created is successfully dispalyed in Channel records container from the Meta Model page" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Channel/Aoi Editor" )
							             .getAlertPage(this)
							             .startCreatingChannel()
							             .fillChannelRecord(key,
							           	  	 			    channelName,
							           		 			    maxContentAge,
							           		 			    isSuppressed,
							           		 			    deliveryTemplateKey,
							           		 			emailProviderProfile,
							           		 			    "",
							           		 			domainId,
							           		 			    crmProviderProfile,
							           		 		 	    "",
							           		 			    true)
			.verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
								     new String[] {channelName + " record"},
								     true).navigateToSubPage( "Meta Model" )
                                         .getAlertPage(this)
            .verifyDisplayedElements(new String[] {elements.getMetaModelChannelContainerRecord(channelName)},
            						 new String[] {"Channels records list container"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyRemoveMetaModelChannelRecordFromContainerWhenDeleted() throws Exception {
	     	 
    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        log.startTest( "Verify that when a new Channel record is deleted is also removed from Channel records container at the Meta Model page" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Channel/Aoi Editor" )
							             .getAlertPage(this)
							             .startCreatingChannel()
							             .fillChannelRecord(key,
							           	  	 			    channelName,
							           		 			    maxContentAge,
							           		 			    isSuppressed,
							           		 			    deliveryTemplateKey,
							           		 			emailProviderProfile,
							           		 			    "",
							           		 			domainId,
							           		 			    crmProviderProfile,
							           		 		 	    "",
							           		 			    true)
			.verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
								     new String[] {channelName + " record"},
								     true).navigateToSubPage( "Meta Model" )
            .verifyDisplayedElements(new String[] {elements.getMetaModelChannelContainerRecord(channelName)},
            						 new String[] {"Channels records list container"},
            						 true).navigateToSubPage( "Channel/Aoi Editor" )
            						 	  .getAlertPage(this)
            						 	  .deleteChannel(channelName, true)
            						 	  .navigateToSubPage( "Meta Model" )
            .verifyDisplayedElements(new String[] {elements.getMetaModelChannelContainerRecord(channelName)},
            						 new String[] {"Channels records list container"},
            						 false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddAreaOfInterestToChannelByDragAndDrop() throws Exception {
	     	 
    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
        
        log.startTest( "Verify that AOI can be successfully added to a channel by drag-and-drop" );
        try {
        	
        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Channel/Aoi Editor" )
							             .getAlertPage(this)
							             .startCreatingChannel()
							             .fillChannelRecord(key,
							           	  	 			    channelName,
							           		 			    maxContentAge,
							           		 			    isSuppressed,
							           		 			    deliveryTemplateKey,
							           		 			emailProviderProfile,
							           		 			    "",
							           		 			domainId,
							           		 			    crmProviderProfile,
							           		 		 	    "",
							           		 			    true)
								          .navigateToSubPage( "Meta Model" )
										  .getAlertPage(this)
										  .addCategory(categoryName, categoryKey)
									      .addAOI(aoiName, aoiKey, categoryName)
									      .dragAndDropAOItoChannel(aoiName, channelName)
        .verifyDisplayedElements(new String[] {elements.getMetaModelChannelAOISubRecord(channelName, aoiName)},
        					     new String[] {aoiName + " AOI under the" + channelName + " channel"},
        					     true);
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    

    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyRemoveAreaOfInterestFromChannelWhenDeletionIsConfirmed() throws Exception {
	     	 
    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
        
        log.startTest( "Verify that AOI can be successfully removed from a channel when deletion is confirmed" );
        try {
        	
        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Channel/Aoi Editor" )
							             .getAlertPage(this)
							             .startCreatingChannel()
							             .fillChannelRecord(key,
							           	  	 			    channelName,
							           		 			    maxContentAge,
							           		 			    isSuppressed,
							           		 			    deliveryTemplateKey,
							           		 			emailProviderProfile,
							           		 			    "",
							           		 			domainId,
							           		 			    crmProviderProfile,
							           		 		 	    "",
							           		 			    true)
								          .navigateToSubPage( "Meta Model" )
										  .getAlertPage(this)
										  .addCategory(categoryName, categoryKey)
									      .addAOI(aoiName, aoiKey, categoryName)
									      .dragAndDropAOItoChannel(aoiName, channelName)
        .verifyDisplayedElements(new String[] {elements.getMetaModelChannelAOISubRecord(channelName, aoiName)},
        					     new String[] {aoiName + " AOI under the " + channelName + " channel"},
        					     true).getAlertPage(this)
        					     	  .removeAOIfromChannel(aoiName, channelName, true)
        .verifyDisplayedElements(new String[] {elements.getMetaModelChannelAOISubRecord(channelName, aoiName)},
        					     new String[] {aoiName + " AOI under the " + channelName + " channel"},
        					     false);
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyRemoveAreaOfInterestFromChannelWhenDeletionIsCanceled() throws Exception {
	     	 
    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String categoryName = randNumber + "categoryName";
    	String categoryKey = randNumber + "categoryKey";
    	String aoiName = randNumber + "aoiName";
    	String aoiKey = randNumber + "aoiKey";
        
        log.startTest( "Verify that AOI is not removed from a channel when deletion is canceled" );
        try {
        	
        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Channel/Aoi Editor" )
							             .getAlertPage(this)
							             .startCreatingChannel()
							             .fillChannelRecord(key,
							           	  	 			    channelName,
							           		 			    maxContentAge,
							           		 			    isSuppressed,
							           		 			    deliveryTemplateKey,
							           		 			emailProviderProfile,
							           		 			    "",
							           		 			domainId,
							           		 			    crmProviderProfile,
							           		 		 	    "",
							           		 			    true)
								          .navigateToSubPage( "Meta Model" )
										  .getAlertPage(this)
										  .addCategory(categoryName, categoryKey)
									      .addAOI(aoiName, aoiKey, categoryName)
									      .dragAndDropAOItoChannel(aoiName, channelName)
        .verifyDisplayedElements(new String[] {elements.getMetaModelChannelAOISubRecord(channelName, aoiName)},
        					     new String[] {aoiName + " AOI under the " + channelName + " channel"},
        					     true).getAlertPage(this)
        					     	  .removeAOIfromChannel(aoiName, channelName, false)
        .verifyDisplayedElements(new String[] {elements.getMetaModelChannelAOISubRecord(channelName, aoiName)},
        					     new String[] {aoiName + " AOI under the " + channelName + " channel"},
        					     true);
        
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayChannelAoiEditorPageUiElements() throws Exception {

        log.startTest( "Verify that the Channel/Aoi Editor records container and Add Channel button are successfully displayed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .verifyDisplayedElements( new String[]{ elements.channelRecordsContainer,
                                        		 								 elements.channelEditorAddChannel},
                                        		 				   new String[] {"Channel records container table grid",
                                        		 								 "Add Channel button"},
                                                                   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayChannelRecordPropertyFields() throws Exception {

        log.startTest( "Verify that all the Channel record property fields successfully displayed when new channel is going to be created" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
            .verifyDisplayedElements(new String[] {elements.channelEditorKey,
            									   elements.channelEditorDisplayName,
            									   elements.channelEditorMaxContentAge,
            									   elements.channelEditorSuppressDuplContent,
            									   elements.channelEditorDeliveryTemplateKey,
            									   elements.channelEditorEmailProviderProfile,
            									   elements.channelEditorSenderProfileIdLabel,
            									   elements.channelEditorSenderProfileIdInput,
            									   elements.channelEditorDomainIdLabel,
            									   elements.channelEditorDomainIdInput,
            									   elements.channelEditorCrmProviderProfile,
            									   elements.channelEditorDefaultSchedule,
            									   elements.channelEditorSaveChanges},
            						 new String[] {"Key input field",
            									   "Display Name input field",
            									   "Max Content Age (in days) input field",
            									   "Suppress Duplicate Content checkbox",
            									   "Delivery Template Key input field",
            									   "Email Provider Profile dropdown",
            									   "Sender Profile Id label",
            									   "Sender Profile Id input field",
            									   "Sender Domain Id label",
            									   "Sender Domain Id input field",
            									   "CRM Provider Profile drodpdown",
            									   "Default Schedule dropdown",
            									   "Save Changes button"},
            						 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayChannelRecordDefaultScheduleDropdownOptions() throws Exception {

    	String[] defaultScheduleOptions = {
    			
    			"Manual",
    			"Daily",
    			"Weekly",
    			"Monthly",
    			"Fortnightly"
    	};
    	
        log.startTest( "Verify that all the Default Schedule dropdown options are successfully disaplyed for the property of given Channel record" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel();
            
            for (int i = 0; i < defaultScheduleOptions.length; i++) {
				
            	verifyDisplayedElements(new String[] {elements.getDropdownOption(elements.channelEditorDefaultSchedule, defaultScheduleOptions[i])},
				            			new String[] {defaultScheduleOptions[i] + " dropdown option"},
				            			true);
			}

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayChannelRecordEditButton() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that a button for editing a Channel record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
            .verifyDisplayedElements(new String[] {elements.getChannelEditorEditButton(channelName)},
            					     new String[] {channelName + "Edit button"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyAddChannelRecord() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that a new Channel record can be successfully added when Save button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
            					     new String[] {channelName + " record"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyAddChannelRecordWhenSaveButtonNotClicked() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that a new Channel record is not created when Save button is not clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			false)
                                        .navigateToSubPage( "Meta Model" )		
                                        .navigateToSubPage( "Channel/Aoi Editor" )	
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
            					     new String[] {channelName + " record"},
            					     false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyEditChannelRecordWhenSaveButtonNotClicked() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that already existing Channel record is not updated when edited with new values but Save button is not clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
                                        .startEditingChannel(channelName)
                                        .fillChannelRecord(key + "Changed",
                                        		 		   channelName + "Changed",
                                        		 		   maxContentAge,
                                        		 		   isSuppressed,
                                        		 		   "",
                                        		 		   "",
                                        		 		   "",
                                        		 		   "",
                                        		 		   "",
                                        		 		   "",
                                        		 		   false)                                        
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName + "Changed")},
            					     new String[] {channelName + "Changed" + " record"},
            					     false)
            .navigateToSubPage( "Meta Model" )
            .navigateToSubPage("Channel/Aoi Editor")
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName + "Changed")},
            					     new String[] {channelName + "Changed" + " record"},
            					     false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyEditChannelRecordWhenSaveButtonClicked() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that already existing Channel record is successfully updated when edited with new values and Save button is clicked" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
                                        .startEditingChannel(channelName)
                                        .fillChannelRecord(key + "Ch",
                                        		 		   channelName + "Ch",
                                        		 		   maxContentAge,
                                        		 		   isSuppressed,
                                        		 		   deliveryTemplateKey,
                                        		 		   "",
                                        		 		   "",
                                        		 		   "",
                                        		 		   "",
                                        		 		   "",
                                        		 		   true)                                        
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName + "Ch")},
            					     new String[] {channelName + "Ch" + " record"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyCreateChannelWithoutCrmProviderProfile() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "";
    	
        log.startTest( "Verify that no new channel will be created when Crm Profile Provider is not specified" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
            					     new String[] {channelName + " record"},
            					     false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyCreateChannelWithoutEmailProviderProfile() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that no new channel will be created when Email Profile Provider is not specified" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
            					     new String[] {channelName + " record"},
            					     false);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplayChannelRecordDeleteButton() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that a button for deleting a Channel record exists in the grid" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
            .verifyDisplayedElements(new String[] {elements.getChannelEditorDeleteButton(channelName)},
            					     new String[] {channelName + " Delete button"},
            					     true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDeleteChannelRecordWhenDeletionIsConfirmed() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that the Channel record is successfully deleted when deletion is confirmed" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
                                         .deleteChannel(channelName, true)
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
            					     new String[] {channelName + " record"},
            					     false);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void unsuccessfullyDeleteChannelRecordWhenDeletionIsCanceled() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that the Channel record is not deleted when deletion is canceled" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
                                         .deleteChannel(channelName, false)
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
            					     new String[] {channelName + " record"},
            					     true);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplaySubscribersAllAvailableChannelsList() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that a list with all available Channel records is successfully displayed at Subscribers page" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
            .verifyDisplayedElements(new String[] {elements.getChannelRecord(channelName)},
            					     new String[] {channelName + " record"},
            					     true).navigateToSubPage( "Subscribers" )
	        .verifyDisplayedElements(new String[] {elements.subscriberChannelsListContainer,
	        									   elements.getSubscribersChannel(channelName)},
						        	 new String[] {"Subscribers page Channels container",
	        									   channelName + " record"},
						        	 true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplaySubscribersChannelTableGrid() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
    	
        log.startTest( "Verify that a grid with Channel subscribers successfully displayed at Subscribers page when specific channel is selected" );
        try {

            accessPlatformAdminHomePage().navigateToPage( "Alert" )
                                         .navigateToSubPage( "Channel/Aoi Editor" )
                                         .getAlertPage(this)
                                         .startCreatingChannel()
                                         .fillChannelRecord(key,
                                        		 			channelName,
                                        		 			maxContentAge,
                                        		 			isSuppressed,
                                        		 			deliveryTemplateKey,
                                        		 			emailProviderProfile,
                                        		 			"",
                                        		 			domainId,
                                        		 			crmProviderProfile,
                                        		 			"",
                                        		 			true)
                                        .navigateToSubPage( "Subscribers" )
                                        .getAlertPage(this)
                                        .accessChannelSubscribersGrid(channelName)
             .verifyDisplayedElements(new String[] {elements.recordsTableGrid},
				            		  new String[] {"Subscribers table grid"},
				            		  true);            

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests", "alert-tests" })
    public void successfullyDisplaySubscribersChannelTableColumns() throws Exception {

    	String randNumber = driver.getTimestamp();

    	String key = randNumber + "key";    	
    	String channelName = randNumber + "ChannelName";
        String maxContentAge = "100";
        boolean isSuppressed = false;
        String deliveryTemplateKey = randNumber + "deliveryTemplateKey";
        String emailProviderProfile = "emailProviderProfile";
        String domainId = randNumber + "domainId";
        String crmProviderProfile = "crmProviderProfile";
        
        String[] subscribersTableHeaders = {
    			
    			"First Name",
    			"Last Name",
    			"Email",
    			"External Reference",
    			"Last Content Sent",
    			"Frequency"
    	};
        
        log.startTest( "Verify that Contacts table grid columns are successfully displayed" );
        try {

        	accessPlatformAdminHomePage().navigateToPage( "Alert" )
							             .navigateToSubPage( "Channel/Aoi Editor" )
							             .getAlertPage(this)
							             .startCreatingChannel()
							             .fillChannelRecord(key,
							           		  			    channelName,
							           		 			    maxContentAge,
							           		 			    isSuppressed,
							           		 			    deliveryTemplateKey,
							           		 			emailProviderProfile,
							           		 			    "",
							           		 			domainId,
							           		 			    crmProviderProfile,
							           		 			    "",
							           		 			    true)
							           .navigateToSubPage( "Subscribers" )
							           .getAlertPage(this)
							           .accessChannelSubscribersGrid(channelName);
        	
        	for (int i = 0; i < subscribersTableHeaders.length; i++) {
				
        		verifyDisplayedElements(new String[] {elements.getTableHeader( subscribersTableHeaders[i] )},
				        				new String[] {subscribersTableHeaders[i] + " column"},
				        				true);
			}        	

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
