package concep.selenium.preferenceManager;

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
import concep.selenium.preferenceManager.Dynamics;

public class BasePM {
    protected Elements                        elements         = new Elements();
    protected ElementsDynamics				  elementsDynamics = new ElementsDynamics();
    protected static GenericSeleniumWebDriver driver       	   = DriverAccessor.getDriverInstance();
    protected LogResults                      log              = new LogResults();
    protected Mailinator               		  mailinator       = new Mailinator();
    protected Dynamics						  dynamics	       = new Dynamics();
    protected InterAction                     iaApi;

    protected String                          preferenceManagerUrlPassEnabled;
    protected String                          preferenceManagerUrlPassDisabled;
    protected String                          registeredSubscriberEmail;
    protected String						  registeredSubscriberEmailPassDisabled;
    protected String						  registeredSubscriberEmailPassEnabled;
    protected String                          registeredSubscriberPassword;
    protected String						  registeredSubscriberEmailOnlyInPM;
    protected String						  registeredUserOnlyInPM;
    protected String						  registeredUserInPMAndHaveContact;
    protected String						  registeredUserInPMExistingAndContact;
    protected String						  registeredUserInPMExistingStatus;
    protected String						  inactiveContactEmail;
    protected String						  emailOnlyInPM;
    protected String						  emailOnlyInPMConfirmed;
    protected String						  onlyContactEmail;
    protected String						  duplicateContactEmail;
    protected String						  duplicateLeadEmail;
    protected String						  preferenceManagerDirectlyLoginPassDisabled;
    protected String						  preferenceManagerDirectlyLoginPassEnabled;
    protected String						  preferenceManagerChangeableEmail;
    protected String						  registeredSubscriberEmailChangeableEmail;
    protected String						  registeredSubscriberEmailChangeableEmailNotChangeIt;
    protected String						  registeredSubscriberEmailNotUpdateUserData;
    protected String 						  aoiTax;
    protected String						  aoiRealEstate;
    protected String						  aoiCapitalMarkets;
    protected String						  aoiPrivateEquity;
    protected String						  dynamicsConnectionURL;
    
    protected String[]                        allGroups    = {

            "Aerospace Alert Mailing List",
            "Healthcare Newsletter",
            "Medical Cannabis",
            "Transportation Mailing list"                 };

    protected String[]                        titleOptions = {

                                                           "Mr.", "Ms.", "Mrs.", "Dr.", "Hon." };

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config" })
    public void startSelenium(
                               @Optional("config/firefox.PM") String configLocation ) throws Exception {

        driver.init( configLocation );
        preferenceManagerUrlPassEnabled = driver.config.getProperty( "preferenceManagerUrlPassEnabled" );
        preferenceManagerUrlPassDisabled = driver.config.getProperty( "preferenceManagerUrlPassDisabled" );
        registeredSubscriberEmail = driver.config.getProperty( "registeredSubscriberEmail" );
        registeredSubscriberEmailPassDisabled = driver.config.getProperty("registeredSubscriberEmailPassDisabled");
        registeredSubscriberPassword = driver.config.getProperty( "registeredSubscriberPassword" );
        preferenceManagerChangeableEmail = driver.config.getProperty( "preferenceManagerChangeableEmail" );
        registeredSubscriberEmailChangeableEmail = driver.config.getProperty( "registeredSubscriberEmailChangeableEmail" );
        registeredSubscriberEmailChangeableEmailNotChangeIt = driver.config.getProperty( "registeredSubscriberEmailChangeableEmailNotChangeIt" );
        registeredSubscriberEmailNotUpdateUserData = driver.config.getProperty( "registeredSubscriberEmailNotUpdateUserData" );
        registeredSubscriberEmailPassEnabled = driver.config.getProperty( "registeredSubscriberEmailPassEnabled" );
        registeredUserOnlyInPM = driver.config.getProperty( "registeredUserOnlyInPM" );
        registeredUserInPMAndHaveContact = driver.config.getProperty( "registeredUserInPMAndHaveContact" );
        registeredUserInPMExistingAndContact = driver.config.getProperty( "registeredUserInPMExistingAndContact" );
        registeredUserInPMExistingStatus = driver.config.getProperty( "registeredUserInPMExistingStatus" );
        inactiveContactEmail = driver.config.getProperty( "inactiveContactEmail" );
        emailOnlyInPM = driver.config.getProperty( "emailOnlyInPM" );
        dynamicsConnectionURL = driver.config.getProperty( "dynamicsConnectionURL" );
        onlyContactEmail = driver.config.getProperty( "onlyContactEmail" );
        duplicateContactEmail = driver.config.getProperty( "duplicateContactEmail" );
        emailOnlyInPMConfirmed = driver.config.getProperty( "emailOnlyInPMConfirmed" );
        duplicateLeadEmail = driver.config.getProperty( "duplicateLeadEmail" );
        
        /* iaUrl = driver.config.getProperty( "iaConnectionName" );
         iaUserName = driver.config.getProperty( "iaConnectionUsername" );
         iaPassword = driver.config.getProperty( "iaConnectionPassword" );
                
         this.iaApi = new IAmethods( iaUserName, iaPassword, iaUrl );*/
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {

        driver.stopSelenium();
    }

    protected BasePM accessPreferenceManagerLandingPage(
                                                         String prefManagerUrl ) throws Exception {

        log.startStep( "Navigate to the Preference Manager landing page" );
        driver.open( prefManagerUrl );
        log.endStep();

        return this;
    }

    protected BasePM verifyDisplayedElements(
                                              String[] uiElementXpaths,
                                              String[] uiElementNames,
                                              boolean shouldBeDisplayed ) throws Exception {

        if( !shouldBeDisplayed ) {

            for( int i = 0; i < uiElementXpaths.length; i++ ) {

                log.startStep( "Verify that the element '" + uiElementNames[i] + "' is not displayed" );
                //log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
                Assert.assertEquals(false, driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ));
            }

        } else {

            for( int i = 0; i < uiElementXpaths.length; i++ ) {

                log.startStep( "Verify that the element '" + uiElementNames[i]
                               + "' is successfully displayed" );
                //log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
                Assert.assertEquals(true, driver.isElementPresent( uiElementXpaths[i], driver.timeOut ));
            }
        }

        return this;
    }

    protected BasePM verifySubscriberDetail(
                                             String xPath,
                                             String comparingValue,
                                             boolean shouldBeEqual ) throws Exception {

        String detailValue = driver.getInputFieldValue( xPath );

        if( xPath == elements.mobile ) {

            String convertedMobilePhone = "(" + comparingValue.subSequence( 0, 3 ) + ")" + " "
                                          + comparingValue.subSequence( 3, 6 ) + "-"
                                          + comparingValue.subSequence( 6, 11 );

            //comparingValue = convertedMobilePhone;

            System.out.println( convertedMobilePhone );
        }

        if( shouldBeEqual ) {

            log.startStep( "Verify that subscriber detail '" + detailValue
                           + "' from Preference Manager is equal to '" + comparingValue );
            log.endStep( detailValue.equals( comparingValue ) );

        } else {

            log.startStep( "Verify that subscriber detail '" + detailValue
                           + "' from Preference Manager is not equal to '" + comparingValue );
            log.endStep( !detailValue.equals( comparingValue ) );
        }

        return this;
    }

    protected BasePM fillEmailInputField(
                                          String email,
                                          boolean isSubscribed ) throws Exception {

        if( isSubscribed ) {

            log.startStep( "Type '" + email + "' in the 'Email' input field" );
            driver.clear( elements.emailExistingSubscriber );
            driver.type( elements.emailExistingSubscriber, email, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Type '" + email + "' in the 'Email' input field" );
            driver.clear( elements.emailNewSubscriber );
            driver.type( elements.emailNewSubscriber, email, driver.timeOut );
            log.endStep();
        }

        return this;
    }
    
    protected BasePM fillchangeEmailInputField(
            							String email,
            							boolean isSubscribed ) throws Exception {

    		if( isSubscribed ) {

    			log.startStep( "Type '" + email + "' in the 'Email' input field" );
    			driver.clear( elements.changeEmail );
    			driver.type( elements.changeEmail, email, driver.timeOut );
    			log.endStep();

    		} else {

    			log.startStep( "Type '" + email + "' in the 'Email' input field" );
    			driver.clear( elements.changeEmail );
    			driver.type( elements.changeEmail, email, driver.timeOut );
    			log.endStep();
    		}

    		return this;
    }
    
    protected BasePM fillFirstNameInputField(
            									String firstName,
            									boolean isSubscribed ) throws Exception {
    	if( isSubscribed ) {

    		log.startStep( "Type '" + firstName + "' in the 'FirstName' input field" );
            driver.clear( elements.firstName );
            driver.type( elements.firstName, firstName, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Type '" + firstName + "' in the 'FirstName' input field" );
            driver.clear( elements.firstName );
            driver.type( elements.firstName, firstName, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    protected BasePM fillLastNameInputField(
    										String lastName,
    										boolean isSubscribed ) throws Exception {
    	if( isSubscribed ) {
    		log.startStep( "Type '" + lastName + "' in the 'LastName' input field" );
            driver.clear( elements.lastName );
            driver.type( elements.lastName, lastName, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Type '" + lastName + "' in the 'LastName' input field" );
            driver.clear( elements.lastName );
            driver.type( elements.lastName, lastName, driver.timeOut );
            log.endStep();
        }

        return this;  	 		   	
    }
    
    protected BasePM fillCompanyInputField(
											String company,
											boolean isSubscribed ) throws Exception {
    	if( isSubscribed ) {
    		log.startStep( "Type '" + company + "' in the 'Company' input field" );
    		driver.clear( elements.company );
    		driver.type( elements.company, company, driver.timeOut );
    		log.endStep();

    	} else {

    		log.startStep( "Type '" + company + "' in the 'Company' input field" );
    		driver.clear( elements.company );
    		driver.type( elements.company, company, driver.timeOut );
    		log.endStep();
    	}

    	return this;  	 		   	
    }
    
    protected BasePM fillPasswordInputField(
											String password,
											boolean isSubscribed ) throws Exception {
    	if( isSubscribed ) {
    		log.startStep( "Type '" + password + "' in the 'Password' input field" );
    		driver.clear( elements.password );
    		driver.type( elements.password, password, driver.timeOut );
    		log.endStep();

    	} else {

    		log.startStep( "Type '" + password + "' in the 'Password' input field" );
    		driver.clear( elements.password );
    		driver.type( elements.password, password, driver.timeOut );
    		log.endStep();
    	}

    	return this;  	 		   	
    }	
    
    
    protected BasePM fillConfirmPasswordInputField(
												String confirmPassword,
												boolean isSubscribed ) throws Exception {
    	if( isSubscribed ) {
    		log.startStep( "Type '" + confirmPassword + "' in the 'ConfirmPassword' input field" );
    		driver.clear( elements.confirmPassword );
    		driver.type( elements.confirmPassword, confirmPassword, driver.timeOut );
    		log.endStep();

    	} else {

    		log.startStep( "Type '" + confirmPassword + "' in the 'Password' input field" );
    		driver.clear( elements.confirmPassword );
    		driver.type( elements.confirmPassword, confirmPassword, driver.timeOut );
    		log.endStep();
    	}

    	return this;  	 		   	
    }			

    protected BasePM fillCountryInputField(
												String country,
												boolean isSubscribed ) throws Exception {
    	if( isSubscribed ) {
    		log.startStep( "Type '" + country + "' in the 'Country' input field" );
    		driver.clear( elements.country );
    		driver.type( elements.country, country, driver.timeOut );
    		log.endStep();

    	} else {

    		log.startStep( "Type '" + country + "' in the 'Country' input field" );
    		driver.clear( elements.country );
    		driver.type( elements.country, country, driver.timeOut );
    		log.endStep();
    	}

    	return this;  	 		   	
	}
    
    protected BasePM fillCityInputField(
											String city,
											boolean isSubscribed ) throws Exception {
    	if( isSubscribed ) {
    		log.startStep( "Type '" + city + "' in the 'City' input field" );
    		driver.clear( elements.city );
    		driver.type( elements.city, city, driver.timeOut );
    		log.endStep();

    	} else {

    		log.startStep( "Type '" + city + "' in the 'Country' input field" );
    		driver.clear( elements.city );
    		driver.type( elements.city, city, driver.timeOut );
    		log.endStep();
    	}

    	return this;  	 		   	
    }
 
    protected BasePM verifySubscriberMailingPreferences(
                                                         String[] groups,
                                                         boolean isSubscribed ) throws Exception {

        if( isSubscribed ) {

            for( int i = 0; i < groups.length; i++ ) {

                log.startStep( "Verify that a contact is subscribed to group with name: " + groups[i] );
                log.endStep( driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) );
            }

        } else {

            for( int i = 0; i < groups.length; i++ ) {

                log.startStep( "Verify that a contact is not subscribed to group with name: " + groups[i] );
                log.endStep( !driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) );
            }
        }

        return this;
    }

    protected BasePM loginSubscriber(
                                      String email,
                                      String password,
                                      boolean isSubscribed ) throws Exception {

        if( isSubscribed ) {

            fillEmailInputField( email, isSubscribed );

            log.startStep( "Type '" + password + "' in the 'Password' input field" );
            driver.clear( elements.passwordInputField );
            driver.type( elements.passwordInputField, password, driver.timeOut );
            log.endStep();

            action( "Submit" );

        } else {

            fillEmailInputField( email, isSubscribed );
            action( "Register" );
        }

        return this;
    }
    
        
    protected BasePM action(
                             String actionType ) throws Exception {

        switch( actionType.toLowerCase() ){

            case "submit":

                log.startStep( "Click on the 'Submit' button" );
                driver.click( elements.submitBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                driver.waitUntilElementIsLocated( elements.mainHeadingContactDetailsPage, driver.timeOut );
                log.endStep();

                break;

            case "login pass disabled":

                log.startStep( "Click on 'Login' button" );
                driver.click( elements.submitBtn, driver.timeOut );
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
                
            case "register":

                log.startStep( "Click on the 'Register' button" );
                driver.click( elements.registerBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "ok":

                log.startStep( "Click on the 'OK' button" );
                driver.click( elements.okBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
                
            case "here":

            	log.startStep( "Click here to go back to our website." );
                driver.click( elements.clickHereFromUpdatePopup, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "ok validation error":

                log.startStep( "This e-mail address already exists in our database. Please use a different e-mail address or click on reset password to create a password for your new e-mail address." );
                driver.click( elements.validationErrorOKbtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break; 

            case "save":

                log.startStep( "Click on the 'Save' button" );

                if( !driver.isElementPresent( elements.saveBtnContactInfo, driver.negativeTimeOut ) ) {

                    driver.click( elements.saveBtnMailingPref, driver.timeOut );

                } else {

                    driver.click( elements.saveBtnContactInfo, driver.timeOut );
                }

                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                log.endStep();

                break;

            case "forgotten password":

                log.startStep( "Click on the 'Forgot your password' link" );
                driver.click( elements.forgottenPasswordLink, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "forgotten password button":

                log.startStep( "Click on the 'Forgotten password' button" );
                driver.click( elements.forgottenPasswordBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break; 
                
            case "forgotten password popup":

                log.startStep( "Click on the 'Forgotten password' button" );
                driver.click( elements.forgottenPassword, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;

            case "retrieve password":

                log.startStep( "Click on the 'Retrieve Password' button" );
                driver.click( elements.retrievePasswordBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;

            case "back":

                log.startStep( "Click on the 'Back' button" );
                driver.click( elements.backToLoginBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "signup":

                log.startStep( "Click on the 'signUp' button" );
                driver.click( elements.signUpMainPage, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "back aoi":

                log.startStep( "Click on the 'Back' button" );
                driver.click( elements.backBtnAreasOfInterest, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "close":

                log.startStep( "Click on 'Close' button" );
                driver.click( elements.close, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "close button":

                log.startStep( "Click on 'x' button" );
                driver.click( elements.closeBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "login confirmed email":

                log.startStep( "Click on the 'Login' button" );
                driver.click( elements.loginBtnConfirmedRegistration, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "login main page":

                log.startStep( "Click on the 'Login' button" );
                driver.click( elements.loginBtnMainPage, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "register new contact":

                log.startStep( "Click on the 'Register' button" );
                driver.click( elements.registerBtnAreasOfInterest, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                //driver.waitUntilElementIsLocated(elements.registerBtnAreasOfInterest, driver.timeOut);
                log.endStep();

                break;
                
            case "preference saved btn":

                log.startStep( "Your preferences have now been updated. \r\n" + 
                		"Click here to go back to our website." );
                driver.click( elements.preferenceSavedBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                driver.waitUntilElementIsLocated(elements.preferenceSavedBtn, driver.timeOut);
                log.endStep();

                break; 
                                
            case "save reset password":

                log.startStep( "Save reset password" );
                driver.click( elements.saveResetPassword, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                //driver.waitUntilElementIsLocated(elements.saveResetPassword, driver.timeOut);
                log.endStep();

                break; 
                
            case "continue":

                log.startStep( "Click on the 'CONTINUE' button" );
                driver.click( elements.continuePersonalDetailsPage, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "aoi tab":

                log.startStep( "Click on Areas of Interest tab" );
                driver.click( elements.areasOfInterestTab, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "tax":

                log.startStep( "Click on Tax AOI" );
                driver.click( elements.aoiTax, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "real estate":

                log.startStep( "Click on Real Estate AOI" );
                driver.click( elements.aoiRealEstate, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "capital markets":

                log.startStep( "Click on Capital Markets AOI" );
                driver.click( elements.aoiCapitalMarkets, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "private equity":

                log.startStep( "Click on Private Equity AOI" );
                driver.click( elements.aoiPrivateEquity, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
             
            case "tax ml":

                log.startStep( "Click on Tax Marketing list" );
                driver.click( elements.mlTax, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "real estate ml":

                log.startStep( "Click on Real Estate Marketing list" );
                driver.click( elements.mlRealEstate, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "capital markets ml":

                log.startStep( "Click on Capital Markets Marketing list" );
                driver.click( elements.mlCapitalMarkets, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "private equity ml":

                log.startStep( "Click on Private Equity Marketing list" );
                driver.click( elements.mlPrivateEquity, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "sales":

                log.startStep( "Click on Sales menu" );
                driver.click( elementsDynamics.salesMenu, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "contacts":

                log.startStep( "Click on Contacts menu" );
                driver.click( elementsDynamics.contactsMenu, driver.timeOut );
                driver.waitForAjaxToComplete( driver.ajaxTimeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;
                
            case "search contact":

                log.startStep( "Click on search for records" );
                driver.click( elementsDynamics.searchContactButton, driver.timeOut );
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

    protected BasePM updateSubscriberDetail(
                                             String detailName,
                                             String detailValue ) throws Exception {

        if( detailName == "Title" ) {

            log.startStep( "Update the '" + detailName + "' with value of: " + detailValue );
            driver.click( elements.getSubscriberDetailDropdown( detailName ), driver.timeOut ); // 1: Not working
            driver.click( elements.getTitleOption( detailValue ), driver.timeOut );
            //driver.select( elements.getSubscriberDetailDropdown( detailName ) ).selectByVisibleText(detailValue); //2: Not working	
            log.endStep();

        } else if( detailName == "Business Address" ) {

            log.startStep( "Update the '" + detailName + "' with value of: " + detailValue );
            driver.clear( elements.getSubscriberDetailTextarea( detailName ) );
            driver.type( elements.getSubscriberDetailTextarea( detailName ), detailValue, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Update the '" + detailName + "' with value of: " + detailValue );
            driver.clear( elements.getSubscriberDetailInputField( detailName ) );
            driver.type( elements.getSubscriberDetailInputField( detailName ), detailValue, driver.timeOut );
            log.endStep();
        }

        return this;
    }
    
    protected BasePM updateSubscriberDetailWithCountryAndCity(
            													String firstName,
            													String lastName,
            													String company) throws Exception {

    	if( firstName != null ) {

            updateSubscriberDetail( "First Name", firstName );
        }

    	if( lastName != null ) {

            updateSubscriberDetail( "Last Name", lastName );
        }

    	if( company != null ) {

            updateSubscriberDetail( "Company", company );
        }

    	return this;
    }

    protected BasePM updateAllSubscriberDetails(
                                                 String title,
                                                 String firstName,
                                                 String lastName,
                                                 String emailAddress,
                                                 String jobTitle,
                                                 String company,
                                                 String password,
                                                 String confirmPassword,
                                                 String mobile,
                                                 String businessAddress,
                                                 String city,
                                                 String state,
                                                 String zipCode,
                                                 String fax ) throws Exception {

        if( title != null ) {

            updateSubscriberDetail( "Title", title );
        }

        if( firstName != null ) {

            updateSubscriberDetail( "First Name", firstName );
        }

        if( lastName != null ) {

            updateSubscriberDetail( "Last Name", lastName );
        }

        if( emailAddress != null ) {

            updateSubscriberDetail( "Email Address", emailAddress );
        }

        if( jobTitle != null ) {

            updateSubscriberDetail( "Job Title", jobTitle );
        }

        if( company != null ) {

            updateSubscriberDetail( "Company", company );
        }

        if( password != null ) {

            updateSubscriberDetail( "Password", password );
        }

        if( confirmPassword != null ) {

            updateSubscriberDetail( "Confirm Password", confirmPassword );
        }

        if( mobile != null ) {

            updateSubscriberDetail( "Mobile", mobile );
        }

        if( businessAddress != null ) {

            updateSubscriberDetail( "Business Address", businessAddress );
        }

        if( city != null ) {

            updateSubscriberDetail( "City", city );
        }

        if( state != null ) {

            updateSubscriberDetail( "State", state );
        }

        if( zipCode != null ) {

            updateSubscriberDetail( "ZIP Code", zipCode );
        }

        if( fax != null ) {

            updateSubscriberDetail( "Fax", fax );
        }

        return this;
    }
    
    protected BasePM insertNewSubscriberDetails(
    		String emailNewSubscriber,
            String firstName,
            String lastName,
            String company,
            String password,
            String confirmPassword)
            		throws Exception {
    			if( elements.emailNewSubscriber == "" ) {

    					//updateSubscriberDetail( "Email Address", emailAddress );
    					log.startStep( "Type '" + emailNewSubscriber + "' in the 'Email' input field" );
    					//driver.clear( elements.emailNewSubscriber );
    					driver.type( elements.emailNewSubscriber, emailNewSubscriber, driver.timeOut );
    					log.endStep();
    					}

if( firstName == null ) {

updateSubscriberDetail( "First Name", firstName );
}

if( lastName == null ) {

updateSubscriberDetail( "Last Name", lastName );
}

if( company == null ) {

updateSubscriberDetail( "Company", company );
}

if( password == null ) {

updateSubscriberDetail( "Password", password );
}

if( confirmPassword == null ) {

updateSubscriberDetail( "Confirm Password", confirmPassword );
}

return this;
}

    protected BasePM switchTab(
                                String tabName ) throws Exception {

        switch( tabName ){

            case "Contact Information":

                log.startStep( "Switch to '" + tabName + "' tab" );
                driver.click( elements.contactInformationTab, driver.timeOut );
                log.endStep();

                break;

            case "Managing Mailing Preferences":

                log.startStep( "Switch to '" + tabName + "' tab" );
                driver.click( elements.managingMailingPreferencesTab, driver.timeOut );
                log.endStep();

                break;

            default:

                throw new InvalidParameterException( "The following parameter 'tab' has an invalid value: '"
                                                     + tabName + "'" );
        }

        return this;

    }

    protected BasePM manageMailingPreferences(
                                               boolean isSubscribe,
                                               String[] groups ) throws Exception {

        if( isSubscribe ) {

            for( int i = 0; i < groups.length; i++ ) {

                if( !driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) ) {

                    log.startStep( "Subscribe to " + groups[i] + "' group" );
                    driver.click( elements.getGroupCheckbox( groups[i] ), driver.timeOut );
                    log.endStep();
                }
            }

        } else {

            for( int i = 0; i < groups.length; i++ ) {

                if( driver.isChecked( elements.getGroupCheckbox( groups[i] ) ) ) {

                    log.startStep( "Unsubscribe from " + groups[i] + "' group" );
                    driver.click( elements.getGroupCheckbox( groups[i] ), driver.timeOut );
                    log.endStep();
                }
            }
        }

        return this;
    }
}