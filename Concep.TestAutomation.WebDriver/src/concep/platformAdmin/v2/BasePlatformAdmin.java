package concep.platformAdmin.v2;

import java.security.InvalidParameterException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class BasePlatformAdmin {

    protected final String                    requiredFieldTextMsg = "This field is required";

    protected static GenericSeleniumWebDriver driver               = DriverAccessor.getDriverInstance();
    protected LogResults                      log                  = new LogResults(this.driver);

    protected Elements                        elements             = new Elements();

    protected SystemsPage getSystemPage(
                                         SystemsPage systemsPage ) {

        return systemsPage;
    }

    protected AlertPage getAlertPage(
                                      AlertPage alertPage ) {

        return alertPage;
    }

    protected SchedulesPage getSchedulesPage(
                                              SchedulesPage schedulesPage ) {

        return schedulesPage;
    }

    protected LogsPage getLogsPage(
                                    LogsPage logsPage ) {

        return logsPage;
    }   

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config" })
    public void startSelenium(
                               @Optional("config/firefox.PlatformAdmin") String configLocation )
                                                                                                throws Exception {

        driver.init( configLocation );
        driver.url = driver.config.getProperty( "platformAdminUrl" );
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {

        driver.stopSelenium();
    }

    protected BasePlatformAdmin accessPlatformAdminHomePage() throws Exception {

        log.startStep( "Navigate to the Platform Admin home page" );
        driver.open();
        log.endStep();

        return this;
    }

    protected BasePlatformAdmin navigateToPage(
                                                String pageName ) throws Exception {

        log.startStep( "Navigate to " + pageName + " page" );
        driver.click( elements.getPageTab( pageName ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        //Thread.sleep(3000); // TODO: Need to find a better solution to wait for not completed AJAX
        log.endStep();        

        return this;
    }

    protected BasePlatformAdmin navigateToSubPage(
                                                   String subpageName ) throws Exception {

        log.startStep( "Navigate to " + subpageName + " subpage" );
        driver.click( elements.getSubPageTab( subpageName ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        //Thread.sleep( 3000 ); // TODO: Need to find a better solution to wait for not completed AJAX
        log.endStep();

        return this;
    }
    
    protected BasePlatformAdmin clikTextHyperlink(String xPath, String text) throws Exception {
    	
    	log.startStep( "Click on the hyperlink with text: " + text );
    	driver.click(xPath, driver.timeOut);
		driver.waitForPageToLoad();
		driver.waitForAjaxToComplete(driver.ajaxTimeOut);
		log.endStep();
    	
    	return this;
    }

    protected BasePlatformAdmin startCreatingRecord(
                                                     boolean isSubgridRecord ) throws Exception {    	
    	
        if( !isSubgridRecord ) {

            log.startStep( "Click on the '+' button" );
            driver.waitUntilElementIsLocated(elements.addNewRecord, driver.timeOut);
            driver.waitUntilElementIsClickable(elements.addNewRecord);
            driver.click( elements.addNewRecord, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

        } else {

            log.startStep( "Click on the '+' button" );
            driver.click( elements.addSubgridRecord, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
        }
        
        return this;
    }

    protected BasePlatformAdmin saveRecord(
                                            boolean isSubgridRecord ) throws Exception {

        if( !isSubgridRecord ) {

            log.startStep( "Save the record" );
            driver.click( elements.saveRecord, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

        } else {

            log.startStep( "Save the record" );
            driver.click( elements.saveSubgridRecord, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
        }

        Thread.sleep( 3000 ); // TODO: Need to find a better solution to wait for not completed AJAX

        return this;
    }

    protected BasePlatformAdmin startEditingRecord(
    												String recordXpath,
                                                    boolean isSubgridRecord,
                                                    String subgridRecordParam ) throws Exception {
    	
    	log.startStep("Wait for record Edit button to be displayed");
    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    	driver.waitUntilElementIsLocated(recordXpath + elements.editRecord, driver.timeOut);
    	driver.waitUntilElementIsClickable(recordXpath + elements.editRecord);
    	log.endStep();
    	
        if( !isSubgridRecord ) {

            log.startStep( "Start editing record" );
            driver.click( recordXpath + elements.editRecord, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

        } else {

            log.startStep( "Start editing record" );
            driver.click( elements.getEditSubgridRecord( subgridRecordParam ), driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
        }

        return this;
    }

    protected BasePlatformAdmin cancelRecord(boolean isSubgridRecord) throws Exception {
        
        if( !isSubgridRecord ) {

            log.startStep( "Cancel editing record" );
            driver.click( elements.cancelRecord, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

        } else {

        	log.startStep( "Cancel editing subgrid record" );
            driver.click( elements.cancelSubgridRecord, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
        }

        return this;
    }

    protected BasePlatformAdmin deleteRecord(
                                              String recordParam,
                                              boolean isConfirmed ) throws Exception {      

        log.startStep( "Delete record" );
        driver.click( elements.getDeleteRecordBtn( recordParam ), driver.timeOut );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();
        
        log.startStep( "Verify that that a warning message '" + elements.deleteConfirmationTxt2 +  "' for deleting the record is displayed" );
        log.endStep( driver.isElementPresent( elements.deleteConfirmation2, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for cancel the deletion is displayed [Cancel button]" );
        log.endStep( driver.isElementPresent( elements.cancelDeletion, driver.timeOut ) );
        
        log.startStep( "Verify that that a button for confirm the deletion is displayed [OK button]" );
        log.endStep( driver.isElementPresent( elements.confirmDeletion, driver.timeOut ) );        

        if( isConfirmed ) {           
                   
            log.startStep( "Confirm the deletion" );
            driver.click( elements.confirmDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
                       
            log.startStep("Verify the Delete record message");
            log.endStep(driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));

        } else {           

            log.startStep( "Cancel the deletion" );
            driver.click( elements.cancelDeletion, driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);            
            log.endStep();
        }

        return this;
    }
    
    protected BasePlatformAdmin deleteConnectionRecord(
								            String recordParam,
								            boolean isConfirmed ) throws Exception {      
			
			log.startStep( "Delete record" );
			driver.click( elements.getDeleteRecordBtn( recordParam ), driver.timeOut );
			driver.waitForAjaxToComplete(driver.ajaxTimeOut);
			log.endStep();
			
			log.startStep( "Verify that that a warning message '" + elements.deleteConfirmationTxt1 +  "' for deleting the record is displayed" );
			log.endStep( driver.isElementPresent( elements.deleteConfirmation1, driver.timeOut ) );
			
			log.startStep( "Verify that that a warning message note '" + elements.deleteConfirmationTxtNote +  "' for deleting the record is displayed" );
			log.endStep( driver.isElementPresent( elements.deleteConfirmationNote, driver.timeOut ) );
			
			log.startStep( "Verify that 'I understand' checkbox for deleting the record is displayed" );
			log.endStep( driver.isElementPresent( elements.deleteConfirmationUnderstandCheckbox, driver.timeOut ) );
			
			log.startStep( "Verify that that a button for cancel the deletion is displayed [Cancel button]" );
			log.endStep( driver.isElementPresent( elements.cancelDeletion, driver.timeOut ) );
			
			log.startStep( "Verify that that a button for confirm the deletion is displayed [OK button]" );
			log.endStep( driver.isElementPresent( elements.confirmDeletion, driver.timeOut ) );        
			
			if( isConfirmed ) {           
			
			log.startStep("Check the 'I understand' checkbox");
			driver.click(elements.deleteConfirmationUnderstandCheckbox, driver.timeOut);
			log.endStep();
			
			log.startStep( "Confirm the deletion" );
			driver.click( elements.confirmDeletion, driver.timeOut );
			driver.waitForAjaxToComplete(driver.ajaxTimeOut);
			log.endStep();
			
			log.startStep( "Verify that that a warning message '" + elements.deleteConfirmationTxt2 +  "' for deleting the record is displayed" );
			log.endStep( driver.isElementPresent( elements.deleteConfirmation2, driver.timeOut ) );
			
			log.startStep( "Verify that that a button for cancel the deletion is displayed [Cancel button]" );
			log.endStep( driver.isElementPresent( elements.cancelDeletion, driver.timeOut ) );
			
			log.startStep( "Verify that that a button for confirm the deletion is displayed [OK button]" );
			log.endStep( driver.isElementPresent( elements.confirmDeletion, driver.timeOut ) );      
			
			log.startStep( "Confirm the deletion" );
			driver.click( elements.confirmDeletion, driver.timeOut );
			driver.waitForAjaxToComplete(driver.ajaxTimeOut);
			log.endStep();
			
			log.startStep("Verify the Delete record message");
			log.endStep(driver.waitUntilElementIsLocated(elements.recordDeletedMsg, driver.timeOut));
			
			} else {           
			
			log.startStep( "Cancel the deletion" );
			driver.click( elements.cancelDeletion, driver.timeOut );
			driver.waitForAjaxToComplete(driver.ajaxTimeOut);            
			log.endStep();
			}
			
			return this;
	}

    protected BasePlatformAdmin filterRecordsBy(
                                                 String parameter,
                                                 String value,
                                                 String fieldType ) throws Exception {

        log.startStep( "Filter records by " + parameter );
        driver.click( elements.getFilterIcon( parameter ), driver.timeOut );
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();

        if( fieldType == "Text" ) {

            log.startStep( "Type the vlaue '" + value + "' in the filter input field" );
            driver.clear(elements.filterInput);
            driver.type( elements.filterInput, value, driver.timeOut );
            log.endStep();

        } else if( fieldType == "Select" ) {
            log.startStep( "Select the vlaue '" + value + "' from the dropdown menu" );
            driver.select( elements.filterDropdown ).selectByVisibleText( value );
            log.endStep();

        } else {

            throw new InvalidParameterException( "The Field Type value of '" + fieldType + "' is not valid." );
        }

        log.startStep( "Click on the 'Filter' button" );
        driver.click( elements.filterButton, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();

        return this;
    }

    protected BasePlatformAdmin clearFilters(
                                              String parameter,
                                              boolean isClearAllFilters ) throws Exception {

        if( isClearAllFilters ) {

            log.startStep( "Click on the 'Clear filters' button" );
            driver.click( elements.clearAllFilters, driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

        } else {

            log.startStep( "Filter records by " + parameter );
            driver.click( elements.getFilterIcon( parameter ), driver.timeOut );
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

            log.startStep( "Click on the 'Clear filter' button from the '" + parameter + "' column" );
            driver.click( elements.clearSingleFilter, driver.timeOut );
            driver.waitForPageToLoad();
            driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
        }

        return this;
    }

    protected BasePlatformAdmin pagingRecords(
                                               String pageNumber ) throws Exception {

        log.startStep( "Navigate to page number " + pageNumber );
        driver.click( elements.getPageNumber( pageNumber ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();

        return this;
    }

    protected BasePlatformAdmin accessRecordSubgrid(String recordXpath) throws Exception {

        log.startStep( "Click on the folder icon to access the record subgrid" );
        driver.waitUntilElementIsClickable(recordXpath + elements.subgridIcon);
        driver.click( recordXpath + elements.subgridIcon, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete(driver.ajaxTimeOut);
        log.endStep();

        return this;
    }

    protected BasePlatformAdmin verifyPageTitle(
                                                 String pageTitle ) throws Exception {

        log.startStep( "Verfiy the page title text - '" + pageTitle + "' is displayed" );
        log.endStep( driver.isElementPresent( elements.getPageTitle( pageTitle ), driver.timeOut ) );

        return this;
    }

    protected BasePlatformAdmin verifyPageSubmenuTabs(
                                                       String[] submenuTabs ) throws Exception {

        for( int i = 0; i < submenuTabs.length; i++ ) {

            log.startStep( "Verify that the submenu tab '" + submenuTabs[i] + "' is successfully displayed" );
            log.endStep( driver.isElementPresent( elements.getSubPageTab( submenuTabs[i] ), driver.timeOut ) );
        }

        return this;
    }

    protected BasePlatformAdmin verifyDisplayedElements(
											            String[] uiElementXpaths,
											            String[] uiElementNames,
											            boolean shouldBeDisplayed ) throws Exception {
		
		if( !shouldBeDisplayed ) {
		
			for( int i = 0; i < uiElementXpaths.length; i++ ) {
			
			log.startStep( "Verify that the element '" + uiElementNames[i] + "' is not displayed" );
			log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
		}
		
		} else {
		
			for( int i = 0; i < uiElementXpaths.length; i++ ) {
			
				log.startStep( "Verify that the element '" + uiElementNames[i]
				+ "' is successfully displayed" );
				log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
			}
		}
		
		return this;
	}
    
    protected BasePlatformAdmin browserCommand(String command ) throws Exception {

		log.startStep( "Browser " + command );
		driver.browserInteract( command );
		log.endStep();
		
		return this;
	}
}
