package em.selenium.automation;

import java.security.InvalidParameterException;

import org.junit.Assert;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.IA.Api.InterAction;
import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import concep.selenium.mailinator.Mailinator;
import em.selenium.automation.DynamicsPMNEWTemplates;
import concep.selenium.gMail.Gmail;

public class BaseAdminPage {
    protected ElementsAdminPage          	  elements         = new ElementsAdminPage();
    protected ElementsDynamicsPMNEWTemplates  elementsDynamics = new ElementsDynamicsPMNEWTemplates();
    protected static GenericSeleniumWebDriver driver       	   = DriverAccessor.getDriverInstance();
    protected LogResults                      log              = new LogResults();
    protected Mailinator               		  mailinator       = new Mailinator();
    protected Gmail						      gmail		       = new Gmail();
    protected DynamicsPMNEWTemplates		  dynamics	       = new DynamicsPMNEWTemplates();
    protected InterAction                     iaApi;


    protected String                          adminPanelURL;
    protected String                          superAdminUsername;
    protected String                          superAdminPassword;
    protected String                          wrongUsername;
    protected String                          wrongPassword;
    
    
    
    
//    protected String                          preferenceManagerUrlPassEnabled;
//    protected String                          preferenceManagerUrlPassDisabled;
//    protected String                          registeredSubscriberEmail;
//    protected String						  registeredSubscriberEmailPassDisabled;
//    protected String						  registeredSubscriberEmailPassEnabled;
//    protected String                          registeredSubscriberPassword;
//    protected String						  registeredSubscriberEmailOnlyInPM;
//    protected String						  registeredUserOnlyInPM;
//    protected String						  registeredUserInPMAndHaveContact;
//    protected String						  registeredUserInPMExistingAndContact;
//    protected String						  registeredUserInPMExistingStatus;
//    protected String						  inactiveContactEmail;
//    protected String						  emailOnlyInPM;
//    protected String						  emailOnlyInPMConfirmed;
//    protected String						  onlyContactEmail;
//    protected String						  duplicateContactEmail;
//    protected String						  duplicateLeadEmail;
//    protected String						  preferenceManagerDirectlyLoginPassDisabled;
//    protected String						  preferenceManagerDirectlyLoginPassEnabled;
//    protected String						  preferenceManagerChangeableEmail;
//    protected String						  registeredSubscriberEmailChangeableEmail;
//    protected String						  registeredSubscriberEmailChangeableEmailNotChangeIt;
//    protected String						  registeredSubscriberEmailNotUpdateUserData;
//    
//    protected String 						  aoiTax;
//    protected String						  aoiRealEstate;
//    protected String						  aoiCapitalMarkets;
//    protected String						  aoiPrivateEquity;
//    protected String						  dynamicsConnectionURL;
    
    /*protected String[]                        allGroups    = {

            "Aerospace Alert Mailing List",
            "Healthcare Newsletter",
            "Medical Cannabis",
            "Transportation Mailing list"                 };

    protected String[]                        titleOptions = {

                                                           "Mr.", "Ms.", "Mrs.", "Dr.", "Hon." };*/

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config" })
    public void startSelenium(
                               @Optional("config/chrome.EMAutomation") String configLocation ) throws Exception {

        driver.init( configLocation );
        adminPanelURL = driver.config.getProperty( "adminPanelURL" );
        superAdminUsername = driver.config.getProperty( "superAdminUsername" );
        superAdminPassword = driver.config.getProperty( "superAdminPassword" );
        wrongUsername = driver.config.getProperty( "wrongUsername" );
        wrongPassword = driver.config.getProperty( "wrongPassword" );
        
        
        
        
        
        
        
        
        
        
//        preferenceManagerUrlPassEnabled = driver.config.getProperty( "preferenceManagerUrlPassEnabled" );
//        preferenceManagerUrlPassDisabled = driver.config.getProperty( "preferenceManagerUrlPassDisabled" );
//        registeredSubscriberEmail = driver.config.getProperty( "registeredSubscriberEmail" );
//        registeredSubscriberEmailPassDisabled = driver.config.getProperty("registeredSubscriberEmailPassDisabled");
//        registeredSubscriberPassword = driver.config.getProperty( "registeredSubscriberPassword" );
//        preferenceManagerChangeableEmail = driver.config.getProperty( "preferenceManagerChangeableEmail" );
//        registeredSubscriberEmailChangeableEmail = driver.config.getProperty( "registeredSubscriberEmailChangeableEmail" );
//        registeredSubscriberEmailChangeableEmailNotChangeIt = driver.config.getProperty( "registeredSubscriberEmailChangeableEmailNotChangeIt" );
//        registeredSubscriberEmailNotUpdateUserData = driver.config.getProperty( "registeredSubscriberEmailNotUpdateUserData" );
//        registeredSubscriberEmailPassEnabled = driver.config.getProperty( "registeredSubscriberEmailPassEnabled" );
//        registeredUserOnlyInPM = driver.config.getProperty( "registeredUserOnlyInPM" );
//        registeredUserInPMAndHaveContact = driver.config.getProperty( "registeredUserInPMAndHaveContact" );
//        registeredUserInPMExistingAndContact = driver.config.getProperty( "registeredUserInPMExistingAndContact" );
//        registeredUserInPMExistingStatus = driver.config.getProperty( "registeredUserInPMExistingStatus" );
//        inactiveContactEmail = driver.config.getProperty( "inactiveContactEmail" );
//        emailOnlyInPM = driver.config.getProperty( "emailOnlyInPM" );
//        dynamicsConnectionURL = driver.config.getProperty( "dynamicsConnectionURL" );
//        onlyContactEmail = driver.config.getProperty( "onlyContactEmail" );
//        duplicateContactEmail = driver.config.getProperty( "duplicateContactEmail" );
//        emailOnlyInPMConfirmed = driver.config.getProperty( "emailOnlyInPMConfirmed" );
//        duplicateLeadEmail = driver.config.getProperty( "duplicateLeadEmail" );
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {

        driver.stopSelenium();
    }
    
    protected BaseAdminPage AdminLandingPage(
            String adminPanelURL ) throws Exception {

    	log.startStep( "Navigate to the Admin Panel landing page" );
    	driver.open( adminPanelURL );
    	
    	log.endStep();

    	return this;
    }
    
    protected BaseAdminPage verifyDisplayedElementsAdmin(
            String[] uiElementXpaths,
            boolean shouldBeDisplayed ) throws Exception {

    	if( !shouldBeDisplayed ) {

    		for( int i = 0; i < uiElementXpaths.length; i++ ) {

    			log.startStep( "Verify that the element is not displayed" );
    			//log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
    			Assert.assertEquals(false, driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ));
    		}

    	} else {

    		for( int i = 0; i < uiElementXpaths.length; i++ ) {

    			log.startStep( "Verify that the element is successfully displayed" );
    			//log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
    			Assert.assertEquals(true, driver.isElementPresent( uiElementXpaths[i], driver.timeOut ));
    		}
    	}

    	return this;
    }
    
    protected BaseAdminPage fillEmailInputField(
    		String email ) throws Exception {

    	log.startStep( "Type '" + email + "' in the 'Email' input field" );
    	driver.clear( elements.email );
    	driver.type( elements.email, email, driver.timeOut );
    	log.endStep();

    	return this;
    }
    
    protected BaseAdminPage fillPasswordInputField(String password) throws Exception {
	
		log.startStep( "Type '" + password + "' in the 'Password' input field" );
		driver.clear( elements.password );
		driver.type( elements.password, password, driver.timeOut );
		log.endStep();

		return this;  	 		   	
    }
    
    
    protected BaseAdminPage action(
    		String actionType ) throws Exception {

    	switch( actionType.toLowerCase() ){

    	
    	case "login to admin panel":

    		log.startStep( "Click on 'Login' button" );
    		driver.click( elements.loginButton, driver.timeOut );
    		driver.waitForAjaxToComplete( driver.ajaxTimeOut );
    		driver.waitForPageToLoad();
    		//driver.waitUntilElementIsLocated( elements.welcomeBackMessage, driver.timeOut );
    		log.endStep();

    		break;

    	case "logout":

    		log.startStep( "Click on 'Logout' button" );
    		driver.click( elements.logoutBtn, driver.timeOut );
    		driver.waitForAjaxToComplete( driver.ajaxTimeOut );
    		driver.waitForPageToLoad();
    		driver.waitUntilElementIsLocated( elements.logoutBtn, driver.timeOut );
    		log.endStep();

    		break;
    		
    	case "remember me":

    		log.startStep( "Click on 'Remember me' checkbox" );
    		driver.click( elements.rememberMeCheckbox, driver.timeOut );
    		driver.waitForAjaxToComplete( driver.ajaxTimeOut );
    		driver.waitForPageToLoad();
    		//driver.waitUntilElementIsLocated( elements.logoutBtn, driver.timeOut );
    		log.endStep();

    		break;

    	
    	  default:
    		throw new InvalidParameterException( "The following parameter 'actionType' has an invalid value: '"
    		                                      + actionType + "'" );
    		          }
    		  
    		  return this;
    	}



	protected BaseAdminPage verifyDisplayedElements(
			String[] uiElementXpaths,
			boolean shouldBeDisplayed ) throws Exception {

		if( !shouldBeDisplayed ) {

			for( int i = 0; i < uiElementXpaths.length; i++ ) {

				log.startStep( "Verify that the element is not displayed" );
				//log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
				Assert.assertEquals(false, driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ));
			}

		} else {

			for( int i = 0; i < uiElementXpaths.length; i++ ) {

				log.startStep( "Verify that the element is successfully displayed" );
				//log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
				Assert.assertEquals(true, driver.isElementPresent( uiElementXpaths[i], driver.timeOut ));
			}
		}

		return this;
	}
	
	protected BaseAdminPage verifyCheckboxIsChecked(
			String uiElementXpaths,
			boolean shouldBeChecked ) throws Exception {

		if( !shouldBeChecked ) {
				log.startStep( "Verify that the element is not checked" );
				//log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
				Assert.assertEquals(false, driver.isChecked( uiElementXpaths ));
			
		} else {

				log.startStep( "Verify that the element is successfully displayed" );
				//log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
				Assert.assertEquals(true, driver.isChecked( uiElementXpaths ));
			}
		
		return this;
	}
	
}
    


    

//    protected BaseAdminPage accessPreferenceManagerLandingPage(
//                                                         String prefManagerUrl ) throws Exception {
//
//        log.startStep( "Navigate to the Preference Manager landing page" );
//        driver.open( prefManagerUrl );
//        log.endStep();
//
//        return this;
//    }
//    
//    
//   
//
//    protected BaseAdminPage verifyDisplayedElements(
//                                              String[] uiElementXpaths,
//                                              String[] uiElementNames,
//                                              boolean shouldBeDisplayed ) throws Exception {
//
//        if( !shouldBeDisplayed ) {
//
//            for( int i = 0; i < uiElementXpaths.length; i++ ) {
//
//                log.startStep( "Verify that the element '" + uiElementNames[i] + "' is not displayed" );
//                //log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
//                Assert.assertEquals(false, driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ));
//            }
//
//        } else {
//
//            for( int i = 0; i < uiElementXpaths.length; i++ ) {
//
//                log.startStep( "Verify that the element '" + uiElementNames[i]
//                               + "' is successfully displayed" );
//                //log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
//                Assert.assertEquals(true, driver.isElementPresent( uiElementXpaths[i], driver.timeOut ));
//            }
//        }
//
//        return this;
//    }
//
//    protected BaseAdminPage verifySubscriberDetail(
//                                             String xPath,
//                                             String comparingValue,
//                                             boolean shouldBeEqual ) throws Exception {
//
//        String detailValue = driver.getInputFieldValue( xPath );
//
//        if( xPath == elements.phone ) {
//
//            String convertedMobilePhone = "(" + comparingValue.subSequence( 0, 3 ) + ")" + " "
//                                          + comparingValue.subSequence( 3, 6 ) + "-"
//                                          + comparingValue.subSequence( 6, 11 );
//
//            //comparingValue = convertedMobilePhone;
//
//            System.out.println( convertedMobilePhone );
//        }
//
//        if( shouldBeEqual ) {
//
//            log.startStep( "Verify that subscriber detail '" + detailValue
//                           + "' from Preference Manager is equal to '" + comparingValue );
//            log.endStep( detailValue.equals( comparingValue ) );
//
//        } else {
//
//            log.startStep( "Verify that subscriber detail '" + detailValue
//                           + "' from Preference Manager is not equal to '" + comparingValue );
//            log.endStep( !detailValue.equals( comparingValue ) );
//        }
//
//        return this;
//    }
//
//    protected BaseAdminPage fillEmailInputField(
//                                          String email ) throws Exception {
//
//            log.startStep( "Type '" + email + "' in the 'Email' input field" );
//            driver.clear( elements.email );
//            driver.type( elements.email, email, driver.timeOut );
//            log.endStep();
//
//        return this;
//    }
//    
//    protected BaseAdminPage fillchangeEmailInputField(
//            							String email,
//            							boolean isSubscribed ) throws Exception {
//
//    		if( isSubscribed ) {
//
//    			log.startStep( "Type '" + email + "' in the 'Email' input field" );
//    			driver.clear( elements.emailPreferencePage );
//    			driver.type( elements.emailPreferencePage, email, driver.timeOut );
//    			log.endStep();
//
//    		} else {
//
//    			log.startStep( "Type '" + email + "' in the 'Email' input field" );
//    			driver.clear( elements.emailPreferencePage );
//    			driver.type( elements.emailPreferencePage, email, driver.timeOut );
//    			log.endStep();
//    		}
//
//    		return this;
//    }
//    
//    protected BaseAdminPage fillFirstNameInputField(
//            									String firstName,
//            									boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//
//    		log.startStep( "Type '" + firstName + "' in the 'FirstName' input field" );
//            driver.clear( elements.firstName );
//            driver.type( elements.firstName, firstName, driver.timeOut );
//            log.endStep();
//
//        } else {
//
//            log.startStep( "Type '" + firstName + "' in the 'FirstName' input field" );
//            driver.clear( elements.firstName );
//            driver.type( elements.firstName, firstName, driver.timeOut );
//            log.endStep();
//        }
//
//        return this;
//    }
//    
//    protected BaseAdminPage fillFirstNameInputFieldPreferencePage(
//												String firstName,
//												boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//
//    		log.startStep( "Type '" + firstName + "' in the 'FirstName' input field" );
//    		driver.clear( elements.firstNamePreferencePage );
//    		driver.type( elements.firstNamePreferencePage, firstName, driver.timeOut );
//    		log.endStep();
//
//    	} else {
//
//    		log.startStep( "Type '" + firstName + "' in the 'FirstName' input field" );
//    		driver.clear( elements.firstNamePreferencePage );
//    		driver.type( elements.firstNamePreferencePage, firstName, driver.timeOut );
//    		log.endStep();
//    	}
//
//    	return this;
//    }
//
//    protected BaseAdminPage fillLastNameInputField(
//    										String lastName,
//    										boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//    		log.startStep( "Type '" + lastName + "' in the 'LastName' input field" );
//            driver.clear( elements.lastName );
//            driver.type( elements.lastName, lastName, driver.timeOut );
//            log.endStep();
//
//        } else {
//
//            log.startStep( "Type '" + lastName + "' in the 'LastName' input field" );
//            driver.clear( elements.lastName );
//            driver.type( elements.lastName, lastName, driver.timeOut );
//            log.endStep();
//        }
//
//        return this;  	 		   	
//    }
//    
//    protected BaseAdminPage fillLastNameInputFieldlPreferencePage(
//											String lastName,
//											boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//    		log.startStep( "Type '" + lastName + "' in the 'LastName' input field" );
//    		driver.clear( elements.lastNamePreferencePage );
//    		driver.type( elements.lastNamePreferencePage, lastName, driver.timeOut );
//    		log.endStep();
//
//    	} else {
//
//    		log.startStep( "Type '" + lastName + "' in the 'LastName' input field" );
//    		driver.clear( elements.lastNamePreferencePage );
//    		driver.type( elements.lastNamePreferencePage, lastName, driver.timeOut );
//    		log.endStep();
//    	}
//
//    	return this;  	 		   	
//    }
//    
//    protected BaseAdminPage fillCompanyInputField(
//											String company,
//											boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//    		log.startStep( "Type '" + company + "' in the 'Company' input field" );
//    		driver.clear( elements.company );
//    		driver.type( elements.company, company, driver.timeOut );
//    		log.endStep();
//
//    	} else {
//
//    		log.startStep( "Type '" + company + "' in the 'Company' input field" );
//    		driver.clear( elements.company );
//    		driver.type( elements.company, company, driver.timeOut );
//    		log.endStep();
//    	}
//
//    	return this;  	 		   	
//    }
//    
//    protected BaseAdminPage fillCompanyInputFieldSignUp(
//											String company,
//											boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//    		log.startStep( "Type '" + company + "' in the 'Company' input field" );
//    		driver.clear( elements.companySignUpNoPass );
//    		driver.type( elements.companySignUpNoPass, company, driver.timeOut );
//    		log.endStep();
//
//    	} else {
//
//    		log.startStep( "Type '" + company + "' in the 'Company' input field" );
//    		driver.clear( elements.companySignUpNoPass );
//    		driver.type( elements.companySignUpNoPass, company, driver.timeOut );
//    		log.endStep();
//    	}
//
//    	return this;  	 		   	
//    }
//    
//    protected BaseAdminPage fillPasswordInputField(String password) throws Exception {
//    	
//    		log.startStep( "Type '" + password + "' in the 'Password' input field" );
//    		driver.clear( elements.password );
//    		driver.type( elements.password, password, driver.timeOut );
//    		log.endStep();
//
//    	return this;  	 		   	
//    }	
//    
//    
//    protected BaseAdminPage fillConfirmPasswordInputField(
//												String confirmPassword,
//												boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//    		log.startStep( "Type '" + confirmPassword + "' in the 'ConfirmPassword' input field" );
//    		driver.clear( elements.confirmPassword );
//    		driver.type( elements.confirmPassword, confirmPassword, driver.timeOut );
//    		log.endStep();
//
//    	} else {
//
//    		log.startStep( "Type '" + confirmPassword + "' in the 'Password' input field" );
//    		driver.clear( elements.confirmPassword );
//    		driver.type( elements.confirmPassword, confirmPassword, driver.timeOut );
//    		log.endStep();
//    	}
//
//    	return this;  	 		   	
//    }			
//
//    protected BaseAdminPage fillJobTitleInputField(
//												String jobTitle,
//												boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//    		log.startStep( "Type '" + jobTitle + "' in the 'Country' input field" );
//    		driver.clear( elements.jobTitle );
//    		driver.type( elements.jobTitle, jobTitle, driver.timeOut );
//    		log.endStep();
//
//    	} else {
//
//    		log.startStep( "Type '" + jobTitle + "' in the 'Country' input field" );
//    		driver.clear( elements.jobTitle );
//    		driver.type( elements.jobTitle, jobTitle, driver.timeOut );
//    		log.endStep();
//    	}
//
//    	return this;  	 		   	
//	}
//    
//    protected BaseAdminPage fillBusinessPhoneInputField(
//											String businessPhone,
//											boolean isSubscribed ) throws Exception {
//    	if( isSubscribed ) {
//    		log.startStep( "Type '" + businessPhone + "' in the 'City' input field" );
//    		driver.clear( elements.businessPhone );
//    		driver.type( elements.businessPhone, businessPhone, driver.timeOut );
//    		log.endStep();
//
//    	} else {
//
//    		log.startStep( "Type '" + businessPhone + "' in the 'Country' input field" );
//    		driver.clear( elements.businessPhone );
//    		driver.type( elements.businessPhone, businessPhone, driver.timeOut );
//    		log.endStep();
//    	}
//
//    	return this;  	 		   	
//    }
// 
//    /*protected BaseAdminPage verifySubscriberMailingPreferences(
//                                                         String[] groups,
//                                                         boolean isSubscribed ) throws Exception {
//
//        if( isSubscribed ) {
//
//            for( int i = 0; i < groups.length; i++ ) {
//
//                log.startStep( "Verify that a contact is subscribed to group with name: " + groups[i] );
//                log.endStep( driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) );
//            }
//
//        } else {
//
//            for( int i = 0; i < groups.length; i++ ) {
//
//                log.startStep( "Verify that a contact is not subscribed to group with name: " + groups[i] );
//                log.endStep( !driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) );
//            }
//        }
//
//        return this;
//    }*/
//
//    protected BaseAdminPage loginSubscriber(
//                                      String email,
//                                      String password,
//                                      boolean isSubscribed ) throws Exception {
//
//        if( isSubscribed ) {
//
//            fillEmailInputField( email, isSubscribed );
//
//            log.startStep( "Type '" + password + "' in the 'Password' input field" );
//            driver.clear( elements.password);
//            driver.type( elements.password, password, driver.timeOut );
//            log.endStep();
//
//            action( "Submit" );
//
//        } else {
//
//            fillEmailInputField( email, isSubscribed );
//            action( "Register" );
//        }
//
//        return this;
//    }
//    
//        
//    protected BaseAdminPage action(
//                             String actionType ) throws Exception {
//
//        switch( actionType.toLowerCase() ){
//
//            case "submit":
//
//                log.startStep( "Click on the 'Submit' button" );
//                driver.click( elements.loginBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                //driver.waitUntilElementIsLocated( elements.mainHeadingContactDetailsPage, driver.timeOut );
//                log.endStep();
//
//                break;
//
//            case "login to admin panel":
//
//                log.startStep( "Click on 'Login' button" );
//                driver.click( elements.loginButton, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                //driver.waitUntilElementIsLocated( elements.welcomeBackMessage, driver.timeOut );
//                log.endStep();
//
//                break;
//                
//            case "logout":
//
//                log.startStep( "Click on 'Logout' button" );
//                driver.click( elements.logoutBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                driver.waitUntilElementIsLocated( elements.logoutBtn, driver.timeOut );
//                log.endStep();
//
//                break;
//                
//            case "register":
//
//                log.startStep( "Click on the 'Register' button" );
//                driver.click( elements.registerBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "ok":
//
//                log.startStep( "Click on the 'OK' button" );
//                driver.click( elements.okBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//                
//            case "here sign up":
//
//            	log.startStep( "Click here to go back to our website." );
//                driver.click( elements.unableToSignHereBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "here update message":
//
//            	log.startStep( "Click here to go back to our website." );
//                driver.click( elements.clickHereFromUpdatePopup, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "ok validation error":
//
//                log.startStep( "This e-mail address already exists in our database. Please use a different e-mail address or click on reset password to create a password for your new e-mail address." );
//                driver.click( elements.validationErrorOKbtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break; 
//           
//            case "forgotten password":
//
//                log.startStep( "Click on the 'Forgot your password' link" );
//                driver.click( elements.forgottenPassword, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "forgotten password button":
//
//                log.startStep( "Click on the 'Forgotten password' button" );
//                driver.click( elements.forgottenPasswordBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break; 
//                
//            case "forgotten password popup":
//
//                log.startStep( "Click on the 'Forgotten password' button" );
//                driver.click( elements.forgottenPassword, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "forgotten password error message":
//
//                log.startStep( "Click on the 'here' button" );
//                driver.click( elements.forgottenPasswordHereBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//
//            case "retrieve password":
//
//                log.startStep( "Click on the 'Retrieve Password' button" );
//                driver.click( elements.retrievePasswordBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "reset my password":
//
//                log.startStep( "Click on the 'Retrieve Password' button" );
//                driver.click( elements.resetMyPasswordBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//
//            case "back":
//
//                log.startStep( "Click on the 'Back' button" );
//                driver.click( elements.backToLoginBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "signup":
//
//                log.startStep( "Click on the 'signUp' button" );
//                driver.click( elements.signUpBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                           
//            case "close":
//
//                log.startStep( "Click on 'Close' button" );
//                driver.click( elements.closeBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "close button":
//
//                log.startStep( "Click on 'x' button" );
//                driver.click( elements.closeBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "close update button":
//
//                log.startStep( "Click on 'x' button" );
//                driver.click( elements.closeInforMessageResetPassEmailSent, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "login confirmed email":
//
//                log.startStep( "Click on the 'Login' button" );
//                driver.click( elements.loginBtnConfirmedRegistration, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "login main page":
//
//                log.startStep( "Click on the 'Login' button" );
//                driver.click( elements.loginBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "register new contact":
//
//                log.startStep( "Click on the 'Register' button" );
//                driver.click( elements.registerBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                driver.waitUntilElementIsLocated(elements.registerBtn, driver.timeOut);
//                log.endStep();
//
//                break;
//                
//            case "update":
//
//                log.startStep( "Your preferences have now been updated. \r\n" + 
//                		"Click here to go back to our website." );
//                driver.click( elements.updateBtn, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                driver.waitUntilElementIsLocated(elements.updateBtn, driver.timeOut);
//                log.endStep();
//
//                break; 
//                                
//            case "save reset password":
//
//                log.startStep( "Save reset password" );
//                driver.click( elements.saveResetPassword, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                driver.waitUntilElementIsLocated(elements.saveResetPassword, driver.timeOut);
//                log.endStep();
//
//                break; 
//           
//            case "tax":
//
//                log.startStep( "Click on Tax AOI" );
//                driver.click( elements.aoiTax, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//             
//            case "capital markets":
//
//                log.startStep( "Click on Capital Markets AOI" );
//                driver.click( elements.aoiCapitalMarkets, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "environment":
//
//                log.startStep( "Click on Private Equity AOI" );
//                driver.click( elements.aoiEnvironment, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//             
//            case "government":
//
//                log.startStep( "Click on Tax Marketing list" );
//                driver.click( elements.aoiGovernment, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "international legislation":
//
//                log.startStep( "Click on Real Estate Marketing list" );
//                driver.click( elements.aoiInternationalLegislation, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "technology":
//
//                log.startStep( "Click on Capital Markets Marketing list" );
//                driver.click( elements.aoiTechnology, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            
//                
//            case "sales":
//
//                log.startStep( "Click on Sales menu" );
//                driver.click( elementsDynamics.salesMenu, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "contacts":
//
//                log.startStep( "Click on Contacts menu" );
//                driver.click( elementsDynamics.contactsMenu, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break;
//                
//            case "search contact":
//
//                log.startStep( "Click on search for records" );
//                driver.click( elementsDynamics.searchContactButton, driver.timeOut );
//                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
//                driver.waitForPageToLoad();
//                log.endStep();
//
//                break; 
//                
//
//            default:
//
//                throw new InvalidParameterException( "The following parameter 'actionType' has an invalid value: '"
//                                                     + actionType + "'" );
//        }
//
//        return this;
//    }
//
//    
////   protected BaseAdminPage updateSubscriberDetailWithCountryAndCity(
////            													String firstName,
////            													String lastName,
////            													String company) throws Exception {
////
////    	if( firstName != null ) {
////
////            updateSubscriberDetail( "First Name", firstName );
////        }
////
////    	if( lastName != null ) {
////
////            updateSubscriberDetail( "Last Name", lastName );
////        }
////
////    	if( company != null ) {
////
////            updateSubscriberDetail( "Company", company );
////        }
////
////    	return this;
////    }
//
////    protected BaseAdminPage updateAllSubscriberDetails(
////                                                 String firstName,
////                                                 String lastName,
////                                                 String jobTitle,
////                                                 String company,
////                                                 String email,
////                                                 String businessPhone) throws Exception {
////
////        if( firstName != null ) {
////
////            updateSubscriberDetail( "First Name", firstName );
////        }
////
////        if( lastName != null ) {
////
////            updateSubscriberDetail( "Last Name", lastName );
////        }
////
////        if( jobTitle != null ) {
////
////            updateSubscriberDetail( "Job Title", jobTitle );
////        }
////
////        if( company != null ) {
////
////            updateSubscriberDetail( "Company", company );
////        }
////
////        if( businessPhone != null ) {
////
////            updateSubscriberDetail( "Business Phone", businessPhone );
////        }
////
////        if( email != null ) {
////
////            updateSubscriberDetail( "Email", email );
////        }
////        
////        return this;
////    }
//    
////    protected BaseAdminPage insertNewSubscriberDetails(
////    		String emailNewSubscriber,
////            String firstName,
////            String lastName,
////            String company,
////            String password,
////            String confirmPassword)
////            		throws Exception {
////    			if( elements.emailNewSubscriber == "" ) {
////
////    					//updateSubscriberDetail( "Email Address", emailAddress );
////    					log.startStep( "Type '" + emailNewSubscriber + "' in the 'Email' input field" );
////    					//driver.clear( elements.emailNewSubscriber );
////    					driver.type( elements.emailNewSubscriber, emailNewSubscriber, driver.timeOut );
////    					log.endStep();
////    					}
////
////if( firstName == null ) {
////
////updateSubscriberDetail( "First Name", firstName );
////}
////
////if( lastName == null ) {
////
////updateSubscriberDetail( "Last Name", lastName );
////}
////
////if( company == null ) {
////
////updateSubscriberDetail( "Company", company );
////}
////
////if( password == null ) {
////
////updateSubscriberDetail( "Password", password );
////}
////
////if( confirmPassword == null ) {
////
////updateSubscriberDetail( "Confirm Password", confirmPassword );
////}
////
////return this;
////}
//
////    protected BaseAdminPage switchTab(
////                                String tabName ) throws Exception {
////
////        switch( tabName ){
////
////            case "Contact Information":
////
////                log.startStep( "Switch to '" + tabName + "' tab" );
////                driver.click( elements.contactInformationTab, driver.timeOut );
////                log.endStep();
////
////                break;
////
////            case "Managing Mailing Preferences":
////
////                log.startStep( "Switch to '" + tabName + "' tab" );
////                driver.click( elements.managingMailingPreferencesTab, driver.timeOut );
////                log.endStep();
////
////                break;
////
////            default:
////
////                throw new InvalidParameterException( "The following parameter 'tab' has an invalid value: '"
////                                                     + tabName + "'" );
////        }
////
////        return this;
////
////    }
//
//    protected BaseAdminPage manageMailingPreferences(
//                                               boolean isSubscribe,
//                                               String[] groups ) throws Exception {
//
//        if( isSubscribe ) {
//
//            for( int i = 0; i < groups.length; i++ ) {
//
//                if( !driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) ) {
//
//                    log.startStep( "Subscribe to " + groups[i] + "' group" );
//                    driver.click( elements.getGroupCheckbox( groups[i] ), driver.timeOut );
//                    log.endStep();
//                }
//            }
//
//        } else {
//
//            for( int i = 0; i < groups.length; i++ ) {
//
//                if( driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) ) {
//
//                    log.startStep( "Unsubscribe from " + groups[i] + "' group" );
//                    driver.click( elements.getGroupCheckbox( groups[i] ), driver.timeOut );
//                    log.endStep();
//                }
//            }
//        }
//
//        return this;
    	
    
