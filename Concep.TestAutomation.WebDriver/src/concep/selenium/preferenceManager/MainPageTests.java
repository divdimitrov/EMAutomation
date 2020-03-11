package concep.selenium.preferenceManager;

import org.testng.annotations.Test;

public class MainPageTests extends BasePM {

    @Test(groups = { "all-tests" })
    public void successfullyAccessPreferenceManagerMainPage() throws Exception {

        log.startTest( "Verify that user can successfully access Preference Manager main page" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
                                                                                                   registeredSubscriberPassword,
                                                                                                   true )
                                                                                 .verifyDisplayedElements( new String[]{ elements.aoiCapitalMarkets,
                                                                                                                   elements.aoiRealEstate},
                                                                                                           new String[]{ elements.aoiTax,
                                                                                                                   elements.aoiPrivateEquity},
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayAllUiElementsSignUpPagePasswordEnabled() throws Exception {

        log.startTest( "Verify that all UI elements from sign up page are succesfully displayed" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.email,
                                                                                                                   elements.firstName,
                                                                                                                   elements.lastName,
                                                                                                                   elements.company,
                                                                                                                   elements.password,
                                                                                                                   elements.confirmPassword,
                                                                                                                   elements.continuePersonalDetailsPage},
                                                                                                           new String[]{ "Email Address input field",
                                                                                                                   "First Name input field",
                                                                                                                   "Last Name input field",
                                                                                                                   "Company input field",
                                                                                                                   "Password input field",
                                                                                                                   "Confirm Password input field",
                                                                                                                   "Save button" },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

   /* @Test(groups = { "all-tests" })
    public void successfullyDispalyAllOptionValuesFromTitleDropdown() throws Exception {

        log.startTest( "Verify that all dropdown values are successfully displayed in the Title dropdown" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
                                                                                                   registeredSubscriberPassword,
                                                                                                   true );

            for( int i = 0; i < titleOptions.length; i++ ) {

                verifyDisplayedElements( new String[]{ elements.getTitleOption( titleOptions[i] ) },
                                         new String[]{ titleOptions[i] },
                                         true );
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/

   
    @Test(groups = { "all-tests" })
    public void successfullyDisplayAllAreasOfInterest() throws Exception {

    	String randNumber = driver.getTimestamp();
    	    	
    	String notRegisteredEmail = randNumber + "email@mailinator.com";
    	String firstName = "firstName" + randNumber;
    	String lastName = "lastName" + randNumber;
    	String company = "company" + randNumber;
    	String password = "password" + randNumber;
    	    	
    	log.startTest( "Verify that all Areas of Interest are successfully displayed" );
        try {
        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																				  .fillEmailInputField( notRegisteredEmail, false )
																				  .fillFirstNameInputField(firstName, false)
																				  .fillLastNameInputField(lastName, false)
																				  .fillCompanyInputField(company, false)
																				  .fillPasswordInputField(password, false)
																				  .fillConfirmPasswordInputField(password, false)
																				  .action( "continue" )
																				  .verifyDisplayedElements( new String[] {elements.aoiTax},
																						  					new String[] {elements.aoiPrivateEquity},
																						  					true )
																				  .verifyDisplayedElements( new String[] {elements.aoiRealEstate},
																						  					new String[] {elements.aoiCapitalMarkets},
																						  					true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayedEmptyFieldsCountryAndCityForNewUser() throws Exception {

        log.startTest( "Verify that after accessing the main page with new subscriber email, first name, last name and company fields are populated, city and country fields are empty" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmailNotUpdateUserData, true)
            																	 .fillPasswordInputField(registeredSubscriberPassword, true)
                                                                                 .action( "submit" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.contactInformationTab },
                                                                                                           new String[]{ elements.selectAOIbelow },
                                                                                                           true )
                                                                                 .verifySubscriberDetail( elements.firstName,
                                                                                                          "kkk",
                                                                                                          true )
                                                                                 .verifySubscriberDetail( elements.lastName,
                                                                                                          "bbb",
                                                                                                          true )
                                                                                 .verifySubscriberDetail( elements.company,
                                                                                                          "nnn",
                                                                                                          true )
                                                                                 .verifySubscriberDetail( elements.city,
                                                                                                          "",
                                                                                                          true )
                                                                                 .verifySubscriberDetail( elements.country,
                                                                                                          "",
                                                                                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyOpenAreasOfInterestTabWithNewUsers() throws Exception {

        log.startTest( "Verify that Areas of Interest tab is not accessible before a new subscriber enter his details at leas once" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
            																	 .action( "aoi tab" )
            																	 .action( "continue" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.aoiCapitalMarkets,
                                                                                                                   		 elements.aoiPrivateEquity},
                                                                                                           new String[]{ elements.aoiTab,
                                                                                                             		     elements.aoiRealEstate},
                                                                                                           false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageAllRequiredFieldsEmpty() throws Exception {

        log.startTest( "Verify when try to signUp a validation message is dispalyed when all the required fields are empty" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
            																     .action( "continue" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.allFieldsAreEmptry },
                                                                       				   					   new String[]{ elements.continuePersonalDetailsPage },
                                                                       				   					   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessagePasswordAndConfirmPasswordDoesNotMatch() throws Exception {

        String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String password = "qwerty12";
        String confirmPassword = "qweqwe12";

        log.startTest( "Verify that a validation message is dispalyed when Password and confirm Password does not match" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )                                                                      
            																	 .fillEmailInputField( notRegisteredEmail,false )
            																	 .fillFirstNameInputField(firstName, false)
            																	 .fillLastNameInputField(lastName, false)
            																	 .fillCompanyInputField(company, false)
            																	 .fillPasswordInputField(password, false)
            																	 .fillConfirmPasswordInputField(confirmPassword, false)
            																	 .action( "continue" )
            																	 .verifyDisplayedElements( new String[]{ elements.password },
            																			 				   new String[]{ elements.passwordsDoNotMatch },
            																			 				   true );   

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageFirstNameFieldEmpty() throws Exception {
    	
    	String randNumber = driver.getTimestamp();
   	 	String notRegisteredEmail = randNumber + "email@yopmail.com";
        String firstName = "";
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String password = "password" + randNumber;
                         
        log.startTest( "Verify that a validation message is dispalyed when First Name input fields is empty" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
        																		 .fillEmailInputField(notRegisteredEmail, false) 
        																		 .fillFirstNameInputField(firstName, false)
        																		 .fillLastNameInputField(lastName, false)
        																		 .fillCompanyInputField(company, false)
        																		 .fillPasswordInputField(password, false)
        																		 .fillConfirmPasswordInputField(password, false)
        																		 .action( "continue" )
        																		 .verifyDisplayedElements(new String[]{ elements.allFieldsAreEmptry },
        																				  				  new String[]{ elements.firstName },
        																				  				  true );       
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageLastNameFieldEmpty() throws Exception {

    	String randNumber = driver.getTimestamp();
   	 	String notRegisteredEmail = randNumber + "email@yopmail.com";
        String firstName = "firstName" + randNumber;
        String lastName = "";
        String company = "company" + randNumber;
        String password = "password" + randNumber;

        log.startTest( "Verify that a validation message is dispalyed when Last Name input fields is empty" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
			 																	 .fillEmailInputField(notRegisteredEmail, false) 
			 																	 .fillFirstNameInputField(firstName, false)
			 																	 .fillLastNameInputField(lastName, false)
			 																	 .fillCompanyInputField(company, false)
			 																	 .fillPasswordInputField(password, false)
			 																	 .fillConfirmPasswordInputField(password, false)
			 																	 .action( "continue" )
			 																	 .verifyDisplayedElements(new String[]{ elements.allFieldsAreEmptry },
																		  				  				  new String[]{ elements.firstName },
																		  				  				  true ); 

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageEmailFieldEmpty() throws Exception {

    	String randNumber = driver.getTimestamp();
   	 	String notRegisteredEmail = "";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String password = "password" + randNumber;

        log.startTest( "Verify that a validation message is dispalyed when Job Title input fields is empty" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
			 																	 .fillEmailInputField(notRegisteredEmail, false) 
			 																	 .fillFirstNameInputField(firstName, false)
			 																	 .fillLastNameInputField(lastName, false)
			 																	 .fillCompanyInputField(company, false)
			 																	 .fillPasswordInputField(password, false)
			 																	 .fillConfirmPasswordInputField(password, false)
			 																	 .action( "continue" )
			 																	 .verifyDisplayedElements(new String[]{ elements.allFieldsAreEmptry },
			 																			 				  new String[]{ elements.email },
			 																			 				  true ); 

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageCompanyFieldEmpty() throws Exception {

        String randNumber = driver.getTimestamp();
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "";
        String country = "country" + randNumber;
        String city = "city" + randNumber;
                 
        log.startTest( "Verify that the company field is supported in the preference update page" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
        																		  .action( "login pass disabled" )
        																		  .fillFirstNameInputField(firstName, true)
        																		  .fillLastNameInputField(lastName, true)
        																		  .fillCompanyInputField(company, true)
        																		  .fillCountryInputField(country, true)
        																		  .fillCityInputField(city, true)
        																		  .action( "register new contact" )
        																		  .verifyDisplayedElements(new String[]{ elements.allFieldsAreEmptry },
        																				  				   new String[]{ elements.company },
        																				  				   true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessagePasswordFieldEmpty() throws Exception {

    	String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String password = "";
        String confirmPassword = "qweqwe12";

        log.startTest( "Verify that a validation message is dispalyed when Password and confirm Password does not match" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )                                                                      
            																	 .fillEmailInputField( notRegisteredEmail,false )
            																	 .fillFirstNameInputField(firstName, false)
            																	 .fillLastNameInputField(lastName, false)
            																	 .fillCompanyInputField(company, false)
            																	 .fillPasswordInputField(password, false)
            																	 .fillConfirmPasswordInputField(confirmPassword, false)
            																	 .action( "continue" )
            																	 .verifyDisplayedElements( new String[]{ elements.allFieldsAreEmptry },
            																			 				   new String[]{ elements.passwordEmpty },
            																			 				   true );   

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageConfirmPasswordFieldEmpty() throws Exception {

    	 String randNumber = driver.getTimestamp();
    	 String email = randNumber + "email@yopmail.com";
         String firstName = "firstName" + randNumber;
         String lastName = "lastName" + randNumber;
         String company = "company" + randNumber;
         String password = "password" + randNumber;
         String confirmPassword = "";
                  
         log.startTest( "Verify that a validation message is dispalyed when Confirm Password input fields is empty" );
         try {

         	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
         																		 .fillEmailInputField(email, false) 
         																		 .fillFirstNameInputField(firstName, false)
         																		 .fillLastNameInputField(lastName, false)
         																		 .fillCompanyInputField(company, false)
         																		 .fillPasswordInputField(password, false)
         																		 .fillConfirmPasswordInputField(confirmPassword, false)
         																		 .action( "continue" )
         																		 .verifyDisplayedElements(new String[]{ elements.allFieldsAreEmptry},
         																				  				  new String[]{ elements.confirmPassword },
         																				  				  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySignUpNewContactPassEnabled() throws Exception {
    	
        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String password = "qwerty12";
        String confirmPassword = "qwerty12";

        log.startTest( "Verify that contact details can be successfully created" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
                                                                                 .fillEmailInputField( notRegisteredEmail,false )
                                                                                 .fillFirstNameInputField(firstName, false)
                                                                                 .fillLastNameInputField(lastName, false)
                                                                                 .fillCompanyInputField(company, false)
                                                                                 .fillPasswordInputField(password, false)
                                                                                 .fillConfirmPasswordInputField(confirmPassword, false)
                                                                                 .action( "continue" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.selectAOI },
                                                                                         				   new String[]{ elements.areasOfInterest },
                                                                                         				   true );                                                                             
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

      @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessagePasswordRequirements() throws Exception {

        String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String password = "a";
        

        log.startTest( "Verify that a validation message is successfully displayed when the password doesn't meet the requirements" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
			 																	 .fillEmailInputField( notRegisteredEmail, false )
			 																	 .fillFirstNameInputField(firstName, false)
			 																	 .fillLastNameInputField(lastName, false)
			 																	 .fillCompanyInputField(company, false)
			 																	 .fillPasswordInputField(password, false)
			 																	 .fillConfirmPasswordInputField(password, false)
			 																	 .action( "continue" )
			 																	 .verifyDisplayedElements( new String[] { elements.passwordRequirements }, 
			 																			 				   new String[] { elements.password }, 
			 																			 				   true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessagePasswordNotMatch() throws Exception {
            
        String randNumber = driver.getTimestamp();
   	 	String notRegisteredEmail = randNumber + "email@yopmail.com";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String password = "password" + randNumber;
        String confirmPassword = "qwerty12";
                 
        log.startTest( "Verify that a validation message is dispalyed when passwords not match" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
        																		 .fillEmailInputField(notRegisteredEmail, false) 
        																		 .fillFirstNameInputField(firstName, false)
        																		 .fillLastNameInputField(lastName, false)
        																		 .fillCompanyInputField(company, false)
        																		 .fillPasswordInputField(password, false)
        																		 .fillConfirmPasswordInputField(confirmPassword, false)
        																		 .action( "continue" )
        																		 .verifyDisplayedElements(new String[]{ elements.passwordsDoNotMatch,
                                                                                          								 elements.confirmPassword },
        																				  					new String[]{ "Passwords do not match!",
                                                                                          								  "Confirm Password" },
        																				  								true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /*@Test(groups = { "all-tests" })
    public void successfullySubscribeToGroupsNewUserAfterUpdating() throws Exception {

        String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String jobTitle = "jobTitle" + randNumber;
        String company = "company" + randNumber;
        String password = "Aa111111!";
        String confirmPassword = "Aa111111!";

        log.startTest( "[Manual]Verify that user is able to subscribe to groups from the Managing Mailing Preferences page after updating" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail,
                                                                                                       false )
                                                                                 .action( "Register" )
                                                                                 .updateSubscriberDetail( "First Name",
                                                                                                          firstName )
                                                                                 .updateSubscriberDetail( "Last Name",
                                                                                                          lastName )
                                                                                 .updateSubscriberDetail( "Job Title",
                                                                                                          jobTitle )
                                                                                 .updateSubscriberDetail( "Company",
                                                                                                          company )
                                                                                 .updateSubscriberDetail( "Password",
                                                                                                          password )
                                                                                 .updateSubscriberDetail( "Confirm Password",
                                                                                                          confirmPassword )
                                                                                 .action( "Save" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textSuccessfullyUpdateContactDetails ) },
                                                                                                           new String[]{ elements.textSuccessfullyUpdateContactDetails },
                                                                                                           true )
                                                                                 .switchTab( "Managing Mailing Preferences" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.mainHeadingManagingMailingPreferences,
                                                                                                                   elements.getInstructionMessage( elements.textInstructionManagingMailingPreferences ) },
                                                                                                           new String[]{ elements.textMailingPreferencesMainHeading,
                                                                                                                   elements.textInstructionManagingMailingPreferences },
                                                                                                           true )
                                                                                 .manageMailingPreferences( true,
                                                                                                            allGroups )
                                                                                 .action( "Save" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textUpdateMailingPreferences ) },
                                                                                                           new String[]{ elements.textUpdateMailingPreferences },
                                                                                                           true );

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( notRegisteredEmail,
                                                                                                   password,
                                                                                                   true )
                                                                                 .switchTab( "Managing Mailing Preferences" )
                                                                                 .verifySubscriberMailingPreferences( allGroups,
                                                                                                                      true );

            // TODO: Method for verifying that subscriber is linked to the relevant folders in IA

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/
   
    /*@Test(groups = { "all-tests" })
    public void successfullyLoggedInformationInteractionExistingUser() throws Exception {

        String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String title = "Mr.";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String jobTitle = "jobTitle" + randNumber;
        String company = "company" + randNumber;
        String password = "Aa111111!";
        String confirmPassword = "Aa111111!";
        String mobile = randNumber;
        String businessAddress = "Test Address street: " + randNumber;
        String city = "Arlington";
        String state = "DC";
        String zipCode = "22215";
        String fax = "666666";

        log.startTest( "[Manual]Verify that when an existing user updates his details, mailing preferences the same information is logged for that contact in InterAction" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail,
                                                                                                       false )
                                                                                 .action( "Register" )
                                                                                 .updateAllSubscriberDetails( title,
                                                                                                              firstName,
                                                                                                              lastName,
                                                                                                              null,
                                                                                                              jobTitle,
                                                                                                              company,
                                                                                                              password,
                                                                                                              confirmPassword,
                                                                                                              mobile,
                                                                                                              businessAddress,
                                                                                                              city,
                                                                                                              state,
                                                                                                              zipCode,
                                                                                                              fax )
                                                                                 .action( "Save" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textSuccessfullyUpdateContactDetails ) },
                                                                                                           new String[]{ elements.textSuccessfullyUpdateContactDetails },
                                                                                                           true );

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( notRegisteredEmail,
                                                                                                   password,
                                                                                                   true )
                                                                                 .updateAllSubscriberDetails( "Dr.",
                                                                                                              firstName
                                                                                                                      + "Updated",
                                                                                                              lastName
                                                                                                                      + "Updated",
                                                                                                              null,
                                                                                                              jobTitle
                                                                                                                      + "Updated",
                                                                                                              company
                                                                                                                      + "Updated",
                                                                                                              password,
                                                                                                              confirmPassword,
                                                                                                              mobile
                                                                                                                      + "1",
                                                                                                              businessAddress
                                                                                                                      + "Updated",
                                                                                                              city,
                                                                                                              state,
                                                                                                              zipCode
                                                                                                                      + "1",
                                                                                                              fax
                                                                                                                      + "1" )
                                                                                 .action( "Save" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textUpdateContactInfo ) },
                                                                                                           new String[]{ elements.textUpdateContactInfo },
                                                                                                           true );

            // TODO: Method for verifying that newly created user has the same information in IA as the updated one from the PM

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/

    @Test(groups = { "all-tests" })
    public void successfullyUpdateContactDetailsAndAreasOfInterestExistingSubscriberPasswordEnabled() throws Exception {

        String firstName = "firstName";
        String lastName = "lastName";
        String company = "company";
        String country = "country";
        String city = "city";

        log.startTest( "Verify that an existing contact can successfully update his details" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailPassEnabled, true)
        																		  .fillPasswordInputField(registeredSubscriberPassword, true)
			  																	  .action( "submit" )
			  																	  .fillFirstNameInputField(firstName, true)
			  																	  .fillLastNameInputField(lastName, true)
			  																	  .fillCompanyInputField(company, true)
			  																	  .fillCountryInputField(country, true)
			  																	  .fillCityInputField(city, true)
			  																	  .action( "private equity" )
			  																	  .action( "capital markets" )
			  																	  .action( "register new contact" )
			  																	  .verifyDisplayedElements(new String[]{ elements.preferencesUpdated },
			  																			  				   new String[]{ elements.emailUpdated },
			  																			  				   true );
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailPassEnabled, true)
        	 																	   .fillPasswordInputField(registeredSubscriberPassword, true)
        	 																	   .action( "submit" ) 
        	 																	   .verifySubscriberDetail( elements.firstName,
        	 																			   					"firstName",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.lastName,
        	 																			   					"lastName",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.company,
        	 																			   					"company",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.city,
        	 																			   					"country",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.country,
        	 																			   					"city",
        	 																			   					true )
        	 																	   .verifyDisplayedElements( new String[]{ elements.realEstateFalse,
        	 																			   								   elements.taxFalse,
        	 																			   								   elements.privateequityTrue,
        	 																			   								   elements.capitalmarketsTrue},
        	 																			   					new String[]{ "Real Estate",
        	 																			   								  "Tax",
        	 																			   								  "Private equity",
        	 																	   										  "Capital markets"},
        	 																			   					true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageNotValidEmailPersonalDetails() throws Exception {

    	String randNumber = driver.getTimestamp();
    	
    	String firstName = "firstName" + randNumber;
    	String lastName = "lastName" + randNumber;
    	String company = "company" + randNumber;
    	String password = "password" + randNumber;
    	    	
        log.startTest( "Verify that a validation message is successfully displayed, when user is trying to enter email with an invalid value" );
        try {
        		accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
        																			 .fillEmailInputField( "onlyInPMEmail@", false )
        																			 .fillFirstNameInputField(firstName, false)
        																			 .fillLastNameInputField(lastName, false)
        																			 .fillCompanyInputField(company, false)
        																			 .fillPasswordInputField(password, false)
        																			 .fillConfirmPasswordInputField(password, false)
        																			 .action( "continue" )
        																			 .verifyDisplayedElements( new String[] { elements.aoiTax }, 
			  																			  					   new String[] { elements.aoiCapitalMarkets }, 
			  																			  					   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /*@Test(groups = { "all-tests" })
    public void successfullySubscribeAndUnsubscribeAtTheSameTime() throws Exception {

        String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String jobTitle = "jobTitle" + randNumber;
        String company = "company" + randNumber;
        String password = "Aa111111!";
        String confirmPassword = "Aa111111!";

        log.startTest( "[Manual]Verify that an existing contact mailing preferences are successfully updated when subscribe and unsubscribe at the same time" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail,
                                                                                                       false )
                                                                                 .action( "Register" )
                                                                                 .updateSubscriberDetail( "First Name",
                                                                                                          firstName )
                                                                                 .updateSubscriberDetail( "Last Name",
                                                                                                          lastName )
                                                                                 .updateSubscriberDetail( "Job Title",
                                                                                                          jobTitle )
                                                                                 .updateSubscriberDetail( "Company",
                                                                                                          company )
                                                                                 .updateSubscriberDetail( "Password",
                                                                                                          password )
                                                                                 .updateSubscriberDetail( "Confirm Password",
                                                                                                          confirmPassword )
                                                                                 .action( "Save" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textSuccessfullyUpdateContactDetails ) },
                                                                                                           new String[]{ elements.textSuccessfullyUpdateContactDetails },
                                                                                                           true )
                                                                                 .switchTab( "Managing Mailing Preferences" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.mainHeadingManagingMailingPreferences,
                                                                                                                   elements.getInstructionMessage( elements.textInstructionManagingMailingPreferences ) },
                                                                                                           new String[]{ elements.textMailingPreferencesMainHeading,
                                                                                                                   elements.textInstructionManagingMailingPreferences },
                                                                                                           true )
                                                                                 .manageMailingPreferences( true,
                                                                                                            new String[]{ allGroups[0],
                                                                                                                    allGroups[2] } )
                                                                                 .manageMailingPreferences( false,
                                                                                                            new String[]{ allGroups[1],
                                                                                                                    allGroups[3] } )
                                                                                 .action( "Save" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textUpdateMailingPreferences ) },
                                                                                                           new String[]{ elements.textUpdateMailingPreferences },
                                                                                                           true );

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( notRegisteredEmail,
                                                                                                   password,
                                                                                                   true )
                                                                                 .switchTab( "Managing Mailing Preferences" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.mainHeadingManagingMailingPreferences,
                                                                                                                   elements.getInstructionMessage( elements.textInstructionManagingMailingPreferences ) },
                                                                                                           new String[]{ elements.textMailingPreferencesMainHeading,
                                                                                                                   elements.textInstructionManagingMailingPreferences },
                                                                                                           true )
                                                                                 .manageMailingPreferences( false,
                                                                                                            new String[]{ allGroups[0],
                                                                                                                    allGroups[2] } )
                                                                                 .manageMailingPreferences( true,
                                                                                                            new String[]{ allGroups[1],
                                                                                                                    allGroups[3] } )
                                                                                 .action( "Save" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textUpdateMailingPreferences ) },
                                                                                                           new String[]{ elements.textUpdateMailingPreferences },
                                                                                                           true );

            // TODO: Method for verifying that a subscriber is successfully updated with the relevant information in IA

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }*/

    @Test(groups = { "all-tests" })
    public void successfullyUpdateContactDetailsAndAreasOfInterestAtTheSameTime() throws Exception {
    	
    	//check the information in Dynamics manually:
    	
        String firstName = "firstName";
        String lastName = "lastName";
        String company = "company";
        String country = "country";
        String city = "city";
        
        log.startTest( "Verify that an existing contact can successfully update his details and areas of interest at the same time" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
        																		  .action( "login pass disabled" )
        																		  .fillFirstNameInputField(firstName, true)
        																		  .fillLastNameInputField(lastName, true)
        																		  .fillCompanyInputField(company, true)
        																		  .fillCountryInputField(country, true)
        																		  .fillCityInputField(city, true)
        																		  .action( "tax" )
        																		  .action( "real estate" )
        																		  .action( "register new contact" )
        																		  .verifyDisplayedElements(new String[]{ elements.preferencesUpdated },
                                                                                                           new String[]{ elements.emailUpdated },
                                                                                                           true );
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
                                                                                  .action( "login pass disabled" ) 
                                                                                  .verifySubscriberDetail( elements.firstName,
																		 				  "firstName",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.lastName,
																		 				  "lastName",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.company,
																		 				  "company",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.city,
																		 				  "country",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.country,
																		 				 "city",
																		 				 true )
                                                                                  .verifyDisplayedElements( new String[]{ elements.realEstateTrue,
															 								 							  elements.taxTrue,
															 								 							  elements.privateequityFalse,
															 								 							  elements.capitalmarketsFalse},
                                                                                		  					new String[]{ "Real Estate",
                                                                                		  								  "Tax",
                                                                                		  								  "Private equity",
													 										 							  "Tax"},
                                                                                		  					true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyUpdateContactDetailsSaveButtonNotClicked() throws Exception {

        String randNumber = driver.getTimestamp();
        
        String firstName = "firstName" + randNumber;
        String lastName = "lastName" + randNumber;
        String company = "company" + randNumber;
        String country = "country" + randNumber;
        String city = "city" + randNumber;
      
        log.startTest( "Verify that subscriber details are not updated when Save button is not clicked" );
        try {
        	
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmailNotUpdateUserData, true)
			 																	 .fillPasswordInputField(registeredSubscriberPassword, true)
			 																	 .action( "submit" )
			 																	 .fillFirstNameInputField(firstName, true)
			 																	 .fillLastNameInputField(lastName, true)
			 																	 .fillCompanyInputField(company, true)
			 																	 .fillCountryInputField(country, true)
			 																	 .fillCityInputField(city, true);	 	        	
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmailNotUpdateUserData, true)
        																		 .fillPasswordInputField(registeredSubscriberPassword, true)
        																			 .action( "submit" )
        																			 .verifyDisplayedElements( new String[]{ elements.contactInformationTab,
        																					 								 elements.registerBtnAreasOfInterest },
        																					 				   new String[]{ "Contact Information tab",
        																					 						   		 "User details tab",
        																			 										 "Save button" },
        																					 				   true )
        																			 .verifySubscriberDetail( elements.firstName,
        																					 				  "kkk",
        																					 				  true )
        																			 .verifySubscriberDetail( elements.lastName,
        																					 				  "bbb",
        																					 				  true )
        																			 .verifySubscriberDetail( elements.company,
        																					 				  "nnn",
        																					 				  true )
        																			 .verifySubscriberDetail( elements.city,
        																					 				  "",
        																					 				  true )
        																			 .verifySubscriberDetail( elements.country,
        																					 				 "",
        																					 				 true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyUpdateAreasOfInterestSaveButtonNotClicked() throws Exception {

        String randNumber = driver.getTimestamp();

        String country = "country" + randNumber;
        String city = "city" + randNumber;

        log.startTest( "Verify that subscriber areas of interest are not updated when Save button is not clicked" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
			 																	 .fillPasswordInputField(registeredSubscriberPassword, true)
			 																	 .action( "submit" )
			 																	 .fillCountryInputField(country, true)
			 																	 .fillCityInputField(city, true)
			 																	 .action( "real estate" )
			 																	 .action( "private equity" );
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
			 																	 .fillPasswordInputField(registeredSubscriberPassword, true)
			 																	 .action( "submit" )
			 																	 .verifyDisplayedElements( new String[]{ elements.realEstateFalse,
			 																			 								 elements.taxTrue},
			 																			 				   new String[]{ elements.privateequityFalse,
		 																			 								     elements.capitalmarketsFalse},
			 																			 				   true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyUpdateContactDetailsAndMarketingListsPasswordDisabled() throws Exception {

    	//!!!check the information in Dynamics manually:
    	
        String firstName = "firstName1";
        String lastName = "lastName1";
        String company = "company1";
        String country = "country1";
        String city = "city1";
   
        log.startTest( "Verify that an existing contact can successfully update his details and Marketing lists at the same time" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
        																		  .action( "login pass disabled" )
        																		  .fillFirstNameInputField(firstName, true)
        																		  .fillLastNameInputField(lastName, true)
        																		  .fillCompanyInputField(company, true)
        																		  .fillCountryInputField(country, true)
        																		  .fillCityInputField(city, true)
        																		  .action( "tax ml" )
        																		  .action( "real estate ml" )
        																		  .action( "register new contact" )
        																		  .verifyDisplayedElements(new String[]{ elements.preferencesUpdated },
                                                                                                           new String[]{ elements.emailUpdated },
                                                                                                           true );
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
                                                                                  .action( "login pass disabled" ) 
                                                                                  .verifySubscriberDetail( elements.firstName,
																		 				  "firstName1",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.lastName,
																		 				  "lastName1",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.company,
																		 				  "company1",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.city,
																		 				  "country1",
																		 				  true )
                                                                                  .verifySubscriberDetail( elements.country,
																		 				 "city1",
																		 				 true )
                                                                                  .verifyDisplayedElements( new String[]{ elements.realEstateMLTrue,
															 								 							  elements.taxMLTrue},
                                                                                		  					new String[]{ elements.privateequityMLFalse,
														 								 							      elements.capitalmarketsMLFalse},
                                                                                		  					true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyUpdateContactDetailsAndMarketingListsPasswordEnabled() throws Exception {

    	//!!!check the information in Dynamics manually:
    	
        String firstName = "firstName3";
        String lastName = "lastName3";
        String company = "company3";
        String country = "country3";
        String city = "city3";

        log.startTest( "Verify that an existing contact can successfully update his details and Marketing lists at the same time" );
        try {

        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailPassEnabled, true)
        																		  .fillPasswordInputField(registeredSubscriberPassword, true)
			  																	  .action( "submit" )
			  																	  .fillFirstNameInputField(firstName, true)
			  																	  .fillLastNameInputField(lastName, true)
			  																	  .fillCompanyInputField(company, true)
			  																	  .fillCountryInputField(country, true)
			  																	  .fillCityInputField(city, true)
			  																	  .action( "private equity ml" )
			  																	  .action( "capital markets ml" )
			  																	  .action( "register new contact" )
			  																	  .verifyDisplayedElements(new String[]{ elements.preferencesUpdated },
			  																			  				   new String[]{ elements.emailUpdated },
			  																			  				   true );
        	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailPassEnabled, true)
        	 																	   .fillPasswordInputField(registeredSubscriberPassword, true)
        	 																	   .action( "submit" ) 
        	 																	   .verifySubscriberDetail( elements.firstName,
        	 																			   					"firstName3",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.lastName,
        	 																			   					"lastName3",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.company,
        	 																			   					"company3",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.city,
        	 																			   					"country3",
        	 																			   					true )
        	 																	   .verifySubscriberDetail( elements.country,
        	 																			   					"city3",
        	 																			   					true )
        	 																	   .verifyDisplayedElements( new String[]{ elements.realEstateMLFalse,
        	 																			   								   elements.taxMLFalse,
        	 																			   								   elements.privateequityMLTrue,
        	 																			   								   elements.capitalmarketsMLTrue},
        	 																			   					new String[]{ "Real Estate Marketing list",
        	 																			   								  "Tax Marketing list",
        	 																			   								  "Private equity Marketing list",
        	 																	   										  "Capital markets Marketing list"},
        	 																			   					true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyUnselectPreferencesAndMarketingListsPasswordEnabled() throws Exception {

    	//!!!check the information in Dynamics manually:
    	
    	String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";
    	String firstName = "firstName" + randNumber;
  	  	String lastName = "lastName" + randNumber;
  	  	String company = "company" + randNumber;
  	  	String password = "password" + randNumber;
  	  	String country = "country" + randNumber;
  	  	String city = "city" + randNumber;

        log.startTest( "Verify if contact is used (Use Leads = No) and password protected=Yes and unselect a preference on PM page, will be removed from Contacts Marketing List" );
        try {
        	
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																				 .fillEmailInputField( notRegisteredEmail, false )
																				 .fillFirstNameInputField(firstName, false)
																				 .fillLastNameInputField(lastName, false)
																				 .fillCompanyInputField(company, false)
																				 .fillPasswordInputField(password, false)
																				 .fillConfirmPasswordInputField(password, false)
																				 .action( "continue" )
																				 .action( "private equity ml" )
																				 .action( "capital markets ml" )
																				 .action( "register new contact" )
																				 .action( "close" );
        	
        															   mailinator.accessMailinator()
        															   			 .searchCustomerEmail( notRegisteredEmail )
        															   			 .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
        															   			 .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
        															   			  action( "login confirmed email" )
        															   			 .fillEmailInputField( notRegisteredEmail, true )
        															   			 .fillPasswordInputField( password, true )
        															   			 .action( "submit" )
			  																	  .fillCountryInputField(country, true)
			  																	  .fillCityInputField(city, true)
			  																	  .action( "private equity ml" )
			  																	  .action( "capital markets ml" )
			  																	  .action( "register new contact" );
        															   			  
        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail, true )
        	 																	  .fillPasswordInputField( password, true )
        	 																	  .action( "submit" )
        	 																	  .verifyDisplayedElements( new String[]{ elements.realEstateMLFalse,
        	 																			   								  elements.taxMLFalse},
        	 																			   					new String[]{ elements.privateequityMLFalse,
        	 																			   								  elements.capitalmarketsMLFalse},
        	 																			   					true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyUnselectPreferencesAndMarketingListsPasswordDisabled() throws Exception {

    	//!!!check the information in Dynamics manually:
    	
    	String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";
    	String firstName = "firstName" + randNumber;
  	  	String lastName = "lastName" + randNumber;
  	  	String company = "company" + randNumber;
  	  	String country = "country" + randNumber;
  	  	String city = "city" + randNumber;

        log.startTest( "Verify if contact is used (Use Leads = No) and password protected=No and unselect a preference on PM page, will be removed from Contacts Marketing List" );
        try {
        	
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
																				 .fillEmailInputField( notRegisteredEmail, false )
																				 .fillFirstNameInputField(firstName, false)
																				 .fillLastNameInputField(lastName, false)
																				 .fillCompanyInputField(company, false)
																				 .action( "continue" )
																				 .action( "tax ml" )
																				 .action( "real estate ml" )
																				 .action( "register new contact" )
																				 .action( "close" );
        	
        															   mailinator.accessMailinator()
        															   			 .searchCustomerEmail( notRegisteredEmail )
        															   			 .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
        															   			 .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
        															   			  action( "login confirmed email" )
        															   			 .fillEmailInputField( notRegisteredEmail, true )
        															   			 .action( "submit" )
			  																	  .fillCountryInputField(country, true)
			  																	  .fillCityInputField(city, true)
			  																	  .action( "tax ml" )
			  																	  .action( "real estate ml" )
			  																	  .action( "private equity ml" )
			  																	  .action( "register new contact" );
        															   			  
        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( notRegisteredEmail, true )
        	 																	  .action( "submit" )
        	 																	  .verifyDisplayedElements( new String[]{ elements.realEstateMLFalse,
        	 																			  								  elements.taxMLFalse},
        	 																			   					new String[]{ elements.privateequityMLTrue,
        	 																			   								  elements.capitalmarketsMLFalse},
        	 																			   					true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
