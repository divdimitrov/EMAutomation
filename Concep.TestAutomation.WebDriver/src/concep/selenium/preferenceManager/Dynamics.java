package concep.selenium.preferenceManager;

import java.security.InvalidParameterException;

import org.junit.Assert;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Dynamics {
	
		private GenericSeleniumWebDriver driver;
	    private LogResults               log     = new LogResults();
	    private Elements                 element = new Elements();
	    private ElementsDynamics         elementDynamics = new ElementsDynamics();

	    public Dynamics() {

	        this.driver = DriverAccessor.getDriverInstance();
	    }
	    

	    public Dynamics accessDynamics() throws Exception {

	        log.startStep( "Navigate to Dynamics" );
	        driver.navigate( elementDynamics.dynamicsConnectionURL );
	        driver.waitForPageToLoad();
	        log.endStep();

	        return this;
	    }
	    
	    public void generateClose() {

	        driver.selectWindow();
	        driver.switchToIframe( elementDynamics.inlineDialogIframe );
	        driver.click( elementDynamics.closeButton, driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        //driver.selectWindow();
	        //driver.close();
	        //driver.selectWindow();
	    }
	    
	   public Dynamics searchCustomerEmail(
				          					String contact) throws Exception {

	    	log.startStep( "Type contact in the 'Search Contact' input field" + contact );
	    	driver.selectWindow();
		    driver.switchToIframe( elementDynamics.contactsIframe );
	    	driver.type( elementDynamics.searchContactField, contact, driver.timeOut);
	    	driver.pressEnter();
	    	//driver.findElement(By.id("Value")).sendKeys(Keys.ENTER);
	    	//driver.click( elementDynamics.searchContactButton, driver.timeOut );
	    	driver.waitForPageToLoad();
	    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
	    	//driver.selectWindow();
	    	//driver.switchToTopWindow();
		    //driver.close();
	        //log.endStep();

	    	return this;  	 		   	
	   }

	   public Dynamics verifyDisplayedContact(
               								  String searchedEmail ) throws Exception {

		   log.startStep( "Verify contact with email " + searchedEmail + " exists in Dynamics" );
		   //log.endStep( driver.isElementPresent( elementDynamics.foundContactEmail( searchedEmail ), driver.timeOut ) );
		   Assert.assertEquals(true, driver.isElementPresent( elementDynamics.foundContactEmail( searchedEmail ), driver.timeOut ));

		   return this;
	   }
	   
	   public Dynamics verifyDisplayedElementsDynamics(
														String fullName ) throws Exception {

		   log.startStep( "Verify contact full name is " + fullName );
		   log.endStep( driver.isElementPresent( elementDynamics.contactFullName( fullName ), driver.timeOut ) );

		   return this;
	   }
	   
	   public Dynamics verifyDisplayedElementsInIFrame(
               									String[] uiElementXpaths,
               									String[] uiElementNames,
               									boolean shouldBeDisplayed ) throws Exception {

		   	if( !shouldBeDisplayed ) {

		   		for( int i = 0; i < uiElementXpaths.length; i++ ) {
		   			
		   driver.switchToIframe( elementDynamics.mainContactIframe );
		   log.startStep( "Verify that the element '" + uiElementNames[i] + "' is not displayed" );
		   //log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
		   Assert.assertEquals(false, driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ));
		   		}

		   	} else {

		   		for( int i = 0; i < uiElementXpaths.length; i++ ) {
		   			
		   driver.switchToIframe( elementDynamics.contactsIframe );
		   log.startStep( "Verify that the element '" + uiElementNames[i] + "' is successfully displayed" );
		   //log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
		   Assert.assertEquals(true, driver.isElementPresent( uiElementXpaths[i], driver.timeOut ));
		   		}
		   	}

		   	return this;
	   }

	   
	   /*public Dynamics verifyDisplayedCompanyNameDynamics(
														String companyName ) throws Exception {

		   log.startStep( "Verify contact full name is " + companyName );
		   log.endStep( driver.isElementPresent( elementDynamics.companyNameDynamics( companyName ), driver.timeOut ) );

		   return this;
	   }*/
	   
	   
	   public Dynamics openFoundContact() {

	        driver.selectWindow();
	        //driver.switchToIframe(elementDynamics.mainContactIframe);
	        //driver.click( elementDynamics.contactData, driver.timeOut );
	        driver.click( elementDynamics.openContactWithEnter, driver.timeOut );
	        driver.pressEnter();
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        //driver.selectWindow();
	        
	        return this;
	    }
	   
	   protected Dynamics actionDynamics(
               							String actionType ) throws Exception {

		 switch( actionType.toLowerCase() ){
		 case "sales":

		   	log.startStep( "Click on Sales menu" );
		   	driver.click( elementDynamics.salesMenu, driver.timeOut );
		   	driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		   	driver.waitForPageToLoad();
		   	log.endStep();

		   	break;
  
		 case "contacts":

			 log.startStep( "Click on Contacts menu" );
			 driver.click( elementDynamics.contactsMenu, driver.timeOut );
			 driver.waitForAjaxToComplete( driver.ajaxTimeOut );
			 driver.waitForPageToLoad();
			 log.endStep();

			 break;
  
		 case "search contact":

			 log.startStep( "Click on search for records" );
			 driver.click( elementDynamics.searchContactButton, driver.timeOut );
			 driver.waitForAjaxToComplete( driver.ajaxTimeOut );
			 driver.waitForPageToLoad();
			 log.endStep();

			 break; 
			 
		 case "common menu":

			 log.startStep( "Click in Contact on common menu" );
			 driver.click( elementDynamics.commonMenuContact, driver.timeOut );
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
	   
	     
	  		        
	    /*public Mailinator searchCustomerEmail(
	                                           String customerEmail ) throws Exception {

	        log.startStep( "Log with email " + customerEmail );
	        driver.type( element.emailField, customerEmail, driver.timeOut );
	        driver.click( element.loginButton, driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        log.endStep();

	        return this;
	    }

	    public Dynamics searchCustomerEmailSubPage(
	                                                  String customerEmail, String customerPassword ) throws Exception {

	        log.startStep( "Log with email " + customerEmail + customerPassword);
	        driver.type( element.emailField, customerEmail, driver.timeOut );
	        driver.click( element.loginInsideButton, driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        log.endStep();
	        
	        return this;
	    }

	    public Mailinator verifyCampaignExistance(
	                                    String campaignSubject ) throws Exception {

	        log.startStep( "Verify that campaign with subject " + campaignSubject + " exists in Mailinator" );
	        log.endStep( driver.isElementPresent( element.campaignReceived( campaignSubject ), driver.timeOut ) );
	        
	        return this;
	    }

	    public Mailinator openEmailCampaign(
	                                         String campaignName ) throws Exception {

	        log.startStep( "Select campaign " + campaignName );
	        driver.click( element.campaignReceived( campaignName ), driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        //driver.switchToIframe( element.campaignContentIframe );
	        log.endStep();

	        return this;
	    }
	    
	    public void generateClickOnResetPasswordLink() {

	        driver.selectWindow();
	        driver.switchToIframe( element.campaignContentIframe );
	        driver.click( element.clickOnResetPasswordEmail, driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        driver.selectWindow();
	        driver.close();
	        driver.selectWindow();
	    }
	    public void generateForward() throws Exception {

	        log.startStep( "Click on 'Forward to a Friend' link" );
	        driver.selectWindow();
	        driver.switchToIframe( element.campaignContentIframe );
	        driver.click( element.forwardFriendHyperlink, driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        driver.selectWindow();
	        driver.switchToTopWindow();
	        driver.click( element.forwardFriendURL, driver.timeOut );
	        driver.close();
	        log.endStep();

	    }

	   
	    public void generateOptOut() throws Exception {

	        log.startStep( "Click on the link 'To unsubscribe, click here'" );
	        driver.selectWindow();
	        driver.switchToIframe( element.campaignContentIframe );
	        driver.click( element.unsubscribeHyperlink, driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        log.endStep();

	        log.startStep( "Check the Opt Out check box" );
	        driver.selectWindow();
	        driver.click( element.optOutRatio, driver.timeOut );
	        log.endStep();

	        log.startStep( "Click on the button 'Opt Out'" );
	        driver.click( element.optOutButton, driver.timeOut );
	        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
	        driver.close();
	        log.endStep();
	    }

	    public void clickLinkFromEmailContent(
	                                           String linkXPath ) throws Exception {

	        log.startStep( "Click on the content from the recently sent Subscription Form email" );
	        driver.selectWindow();
	        driver.switchToIframe( element.campaignContentIframe );
	        driver.click( linkXPath, driver.timeOut );
	        driver.waitForPageToLoad();
	        driver.selectWindow();
	        log.endStep();
	    }
    
	}*/

}
