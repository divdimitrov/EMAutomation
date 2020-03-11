package concep.selenium.send;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class Send {
    protected Elements                      element           = new Elements();
    private static GenericSeleniumWebDriver driver;
    public IASurvey                         iaSurvey          = new IASurvey();
    public DynamicSurvey                    msdSurvey         = new DynamicSurvey();
    public SFDCSurvey                       sfdcSurvey        = new SFDCSurvey();
    public IAConnection                     iaConnection      = new IAConnection();
    public sfdcConnection                   sfdcConnection    = new sfdcConnection();
    public MSD4Connection                   dynamicConnection = new MSD4Connection();
    private LogResults                      log               = new LogResults();
    public Contact                         contact;
    public Campaign                         campaign;

    public Send() {

        driver = DriverAccessor.getDriverInstance();
        this.contact = new Contact();
        this.campaign = new Campaign();
    }

    public Send typeInUserName(
                                String sendUser ) throws Exception {

        log.startStep( "Type in the username '" + sendUser + "'" );
        driver.clear( element.userName );
        driver.type( element.userName, sendUser, driver.timeOut );
        log.endStep();
        return this;
    }

    public Send typeInPassword(
                                String sendPassword ) throws Exception {

        log.startStep( "Type in the password '" + sendPassword + "'" );
        driver.clear( element.password );
        driver.type( element.password, sendPassword, driver.timeOut );
        log.endStep();

        return this;
    }

    public Send loginSendButton() throws Exception {

        log.startStep( "Click on the Login button" );
        driver.click( element.logIn, driver.timeOut );
        driver.waitForPageToLoad();
        driver.isElementPresent( element.logOut, driver.timeOut );
        log.endStep();

        return this;
    }

    public Send loginToSend(
                             String sendUser,
                             String sendPassword ) throws Exception {

        driver.open();
        typeInUserName( sendUser );
        typeInPassword( sendPassword );
        loginSendButton();

        return this;
    }

    public Send goToHomePage() {

        driver.click( element.homePage, driver.timeOut );
        driver.waitForPageToLoad();
        return this;
    }

    public Send goToConnectionsPage() throws Exception {

        log.startStep( "Go to the connection Page" );
        driver.click( element.settigsPage, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.click( element.connectionTab, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.isElementPresent( element.connectionLabel, driver.timeOut );
        log.endStep();

        return this;
    }
    
    public Send goToCampaignsPage() throws Exception {

        log.startStep( "Click on the 'Campaigns' tab" );
        driver.click( element.campaignPage, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public Send goToSurveyPage() throws Exception {

        log.startStep( "Click on the 'Surveys' tab" );
        driver.click( element.surveyPage, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public Send goToSurveyTab(
                               String surveySubPage ) throws Exception {

        log.startStep( "Click on the '" + surveySubPage + "' subpage tab" );
        driver.click( element.surveySubPage( surveySubPage ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public Send goToAdmin() throws Exception {

        log.startStep( "Go to the 'Admin' page" );
        driver.click( element.adminPage, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }
    
    public Send goToUsers() throws Exception {
    	
        log.startStep( "Go to the 'Users' page" );
    	driver.click(element.usersPage, driver.timeOut);
    	driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
    	
    	return this;
    }
    
    public Send startUserCreation() throws Exception {
    	
        log.startStep( "Go to the 'Add User' page" );
    	driver.click(element.addUserBtn, driver.timeOut);
        log.endStep();
        
        log.startStep("Switch to iframe with id=DOMWindowIframe");
    	driver.switchToIframeByID("DOMWindowIframe");
    	log.endStep();
    	
    	return this;
    }
    
    public Send fillUserDetails(String username,
    							String password,
    							String firstName,
    							String lastName,
    							String email,
    							String role) throws Exception {
    	
    	log.startStep("Type in Username: " + username);
    	driver.type(element.usernameAdd, username, driver.timeOut);
    	log.endStep();
    	
    	log.startStep("Type in Password: " + password);
    	driver.type(element.passwordAdd, password, driver.timeOut);
    	log.endStep();
    	
    	log.startStep("Re-Enter Password: " + password);
    	driver.type(element.rePasswordAdd, password, driver.timeOut);
    	log.endStep();
    	
    	log.startStep("Type in First Name: " + firstName);
    	driver.type(element.firstNameAdd, firstName, driver.timeOut);
    	log.endStep();
    	
    	log.startStep("Type in Last Name: " + lastName);
    	driver.type(element.lastNameAdd, lastName, driver.timeOut);
    	log.endStep();
    	
    	log.startStep("Type in Email: " + email);
    	driver.type(element.emailAdd, email, driver.timeOut);
    	log.endStep();
    	
    	log.startStep("Select Role: " + role);
    	driver.select(element.roleAdd).selectByVisibleText(role);
    	log.endStep();
    	
    	log.startStep("Click on the 'Add and Close' button");
    	driver.click(element.addAndCloseBtn, driver.timeOut);
    	driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		driver.waitForPageToLoad();
    	log.endStep();
    	
    	log.startStep("Switch to top window");
    	driver.switchToTopWindow();
    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    	driver.waitForAjaxToComplete(driver.ajaxTimeOut);
    	log.endStep();
    	
    	return this;
    }

    public Send logOut() throws Exception {

        log.startStep( "Log out from Concep Send" );
        driver.click( element.logOut, driver.timeOut );
        driver.waitForPageToLoad();
        log.endStep();
        
        return this;
    }
    
    public Send searchRecord( String recordName ) throws Exception {

		log.startStep( "Type '" + recordName + "' in the Search input field" );
		driver.clear( element.searchInputField );
		driver.type( element.searchInputField, recordName, driver.timeOut );
		log.endStep();
		
		log.startStep( "Click on the 'Search' button" );
		driver.click( element.searchBtn, driver.timeOut );
		driver.waitForAjaxToComplete( driver.ajaxTimeOut );
		driver.waitForPageToLoad();
		log.endStep();
		
		return this;
	}

    public Send verifyDisplayedElements(
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
    
    public Send verifyInputFieldValue(String xPath,
								      String comparingValue,
								      boolean shouldBeEqual ) throws Exception {
		
		String concepSendValue = driver.getInputFieldValue( xPath );
		
		if( shouldBeEqual ) {
		
			log.startStep( "Verify input field value '" + concepSendValue + "' from Concep Send is equal to '" + comparingValue );
			log.endStep( concepSendValue.equals( comparingValue ) );
		
		} else {
		
			log.startStep( "Verify input field value '" + concepSendValue + "' from Concep Send is not equal to '" + comparingValue );
			log.endStep( !concepSendValue.equals( comparingValue ) );
		}
		
		return this;
	}
}