package concep.selenium.preferenceManager;

import org.testng.annotations.Test;


public class LandingPageTests extends BasePM {

    @Test(groups = { "all-tests" })
    public void successfullyAccessPreferenceManagerLandingPage() throws Exception {

        log.startTest( "Verify that user can successfully access Preference Manager landing page" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).verifyDisplayedElements( new String[]{ elements.mainHeadingExistingSubscriberForm,
                                                                                                                   elements.mainPageDisablePassword },
                                                                                                           new String[]{ elements.email,
                                                                                                                   elements.password },
                                                                                                           true )
                                                                                 .verifySubscriberDetail( elements.emailExistingSubscriber,
                                                                                                          "",
                                                                                                          true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyDisplayedPasswordFieldPasswordDisabled() throws Exception {

        log.startTest( "Verify that a password input field and forgotten passowrd link are not displayed when password protection is disabled from the settings.xml file" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).verifyDisplayedElements( new String[]{ elements.email },
                                                                                                            new String[]{ elements.textNewSubscribersFormMainHeading},
                                                                                                            true )
                                                                                  .verifyDisplayedElements( new String[]{ elements.passwordInputField},
                                                                                                            new String[]{ elements.forgottenPasswordLink },
                                                                                                            false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyLoginWithNoEmailExistingSubscribersForm() throws Exception {

        log.startTest( "Verify that can't login with no e-mail, but with existing password, when Password protection = Yes" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( "",
                                                                                                   "qwerty12",
                                                                                                   true )
                                                                                 .verifyDisplayedElements( new String[]{ elements.email },
                                                                                                           new String[]{ elements.loginBtnMainPage },
                                                                                                           true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageNotExistingEmailExistingSubscribersForm()
                                                                                             throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";

        log.startTest( "Verify that a validation message is dispalyed when user is trying to login with not existing email" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( notRegisteredEmail,
                                                                                                   "qwerty12",
                                                                                                   true )
                                                                                 .verifyDisplayedElements( new String[]{ elements.wrongCredentialsLogin },
                                                                                                           new String[]{ elements.forgottenPassword },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageUsernameAndPasswordNotMatchExistingSubscribersForm()
                                                                                                        throws Exception {

        log.startTest( "Verify that a validation message is dispalyed when username and password doesn't match" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
                                                                                                   "notmatchpassword",
                                                                                                   true )
                                                                                 .verifyDisplayedElements( new String[]{ elements.wrongCredentialsLogin },
                                                                                                           new String[]{ elements.forgottenPassword },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyLoginWithValidEmailAndPasswordWhenPassEnabled() throws Exception {

        log.startTest( "Verify that an existing user can login successfully, when password is enabled" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
                                                                                                   registeredSubscriberPassword,
                                                                                                   true )
                                                                                 .verifyDisplayedElements( new String[]{ elements.firstName,
                                                                                                                   		 elements.lastName},
                                                                                                           new String[]{ elements.company,
                                                                                                        		    	 elements.city },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyRedirectUserResetPasswordPage() throws Exception {

        log.startTest( "Verify that user is redirected to Password Reset page" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.backToLogin,
                                                                                                                   		 elements.headingPasswordResetPage },
                                                                                                           new String[]{ elements.emailExistingSubscriber,
                                                                                                                         elements.retrievePasswordBtn },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    
    @Test(groups = { "all-tests" })
    public void successfullyReturnUserToLandingPageFromPasswordResetPage() throws Exception {

        log.startTest( "Verify that user can successfully return to Landing Page from the Password Reset page" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                                 .action( "Back" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.mainHeadingExistingSubscriberForm,
                                                                                                                         elements.mainHeadingNewSubscriberForm },
                                                                                                           new String[]{ elements.email,
                                                                                                                         elements.textNewSubscribersFormMainHeading },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageNoEmailResetPassword() throws Exception {

        log.startTest( "Verify that a validation message is successfully displayed when user is trying to reset his password without specifying email address" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                                 .action( "Retrieve Password" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.emailExistingSubscriber },
                                                                                                           new String[]{ elements.retrievePasswordBtn },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageNotValidEmailResetPassword() throws Exception {

        log.startTest( "Verify that a validation message is successfully displayed when user is trying to reset his password using invalid email" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                                 .fillEmailInputField( "notvalidemail",
                                                                                                       true )
                                                                                 .action( "Retrieve Password" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.emailExistingSubscriber },
                                                                                                           new String[]{ elements.retrievePasswordBtn },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDispalyValidationMessageNotExistingEmailResetPassword() throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";

        log.startTest( "Verify that a validation message is successfully displayed when user is trying to reset passwort to not existing user" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                                 .fillEmailInputField( notRegisteredEmail, true )
                                                                                 .action( "Retrieve Password" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.resetPasswordEmailSent},
                                                                                                           new String[]{ elements.sentLinkToResetPassword,
                                                                                                                   elements.okBtnResetPasswordMessage },
                                                                                                           true );
            														   mailinator.accessMailinator()
            														   			  .searchCustomerEmail( notRegisteredEmail );
            														   			   verifyDisplayedElements( new String[] {elements.publicInbox},
            														   					   					new String[] {elements.emptyInbox},
            														   					   					true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayFormNewSubscribers() throws Exception {

        log.startTest( "Verify that a form for new subscribers is successfully displayed" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).verifyDisplayedElements( new String[]{ elements.mainHeadingNewSubscriberForm,
                                                                                                                   elements.emailExistingSubscriber },
                                                                                                           new String[]{ elements.textNewSubscribersFormMainHeading,
                                                                                                                   elements.textNewSubscribersFormHeading},
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyLoginWithoutPassword() throws Exception {

        log.startTest( "Verify can't login without password, only with e-mail of registered user, when Password protection = Yes" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail,
                                                                                                       true )
                                                                                 .action( "Register" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.firstName,
                                                                                		 								 elements.company},
                                                                                                           new String[]{ elements.country,
                                                                          		 								 		 elements.city },
                                                                                                           false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyLoginWithWrongEmailPasswordEnabled() throws Exception {

        log.startTest( "Verify that can't login with wrong e-mail, when Password Protection = Yes" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "notvalidemail",
                                                                                                       false )
                                                                                 .action( "Register" )
                                                                                 .verifyDisplayedElements( new String[]{ elements.email },
                                                                                                           new String[]{ elements.password },
                                                                                                           true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyLoginWithValidEmailWhenPassDisabled() throws Exception {

        log.startTest( "Verify that an existing user can login successfully, when password is disabled" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled,
                                                                                                        true )
                                                                                  .action( "Submit" )
                                                                                  .verifyDisplayedElements( new String[]{ elements.firstName,
                                                                                                                    	  elements.lastName },
                                                                                                            new String[]{ elements.company,
                                                                                                            			  elements.city},
                                                                                                            true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
  @Test(groups = { "all-tests" })
  public void successfullyUpdateAllUserDetailsForRegisteredUserWhenPassDisabled() throws Exception {
      
    	String randNumber = driver.getTimestamp();
    	String firstName = "firstName" + randNumber;
    	String lastName = "lastName" + randNumber;
    	String company = "company" + randNumber;
    	String country = "country" + randNumber;
    	String city = "city" + randNumber;
    	
    	log.startTest( "Verify can update all user details for registered user, when password is disabled" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled,
                                                                                                        true )
                                                                                  .action( "login pass disabled" )
                                                                                  .fillFirstNameInputField(firstName, true)
                                                                                  .fillLastNameInputField(lastName, true)
                                                                                  .fillCompanyInputField(company, true)
                                                                                  .fillCountryInputField(country, true)
                                                                                  .fillCityInputField(city, true)
                                                                                  .action( "register new contact" )
                                                                                  .action( "preference saved btn" );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithNotRegisteredUserPasswordDisabled() throws Exception {
          	   	
    	log.startTest( "Verify can't login with not registered user pass disabled" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( "55555@yopmail.com", false )
                                                                                  .action( "login pass disabled" )
                                                                                  .verifyDisplayedElements( new String[] { elements.invalidEmail }, 
                                                                                		  					new String[] { elements.wrongCredentialsLogin }, 
                                                                                		  					true );
                                                                                  
                                                                                  

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void successfullyRegisterNewUserPasswordDisabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@yopmail.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
      

      log.startTest( "Verify that user can successfully sign up password disabled" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail,
                                                                                                     false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.almostThere},
                      		   					  					  				  					  new String[] {elements.flagRegisteredUser},
                      		   					  					  				  					  true )
          																	   .action( "close" );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyRegisterNewUserPasswordEnabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      
      log.startTest( "Verify that when a subscriber signs up, he will be able to create a contact in the CRM when the CRM user has only the security role that comes with PM Solution" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.almostThere},
                                                          		   					  					  new String[] {elements.flagRegisteredUser},
                                                          		   					  					  true )
           																	   .action( "close" );
           														 	 mailinator.accessMailinator()
  																		  	   .searchCustomerEmail( notRegisteredEmail )
  																		       .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
  																		       .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );

           														 	verifyDisplayedElements( new String[] {elements.ready},
            		   					  					  				  				 new String[] {elements.confirmedRegistrationMessage,
            		   					  					  				  						 	   elements.loginBtnConfirmedRegistration},
            		   					  					  				  				 true )
  																			   .action( "login confirmed email" )
  																			   .fillEmailInputField( notRegisteredEmail, true )
      																	       .fillPasswordInputField(password, true)
                                                                               .action( "submit" );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyDispalyElementsNotRegisteredSubscriberPasswordDisabled() throws Exception {

	  String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@yopmail.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	 	  
	  log.startTest( "Verify all elements are displayed in sign up page password disabled" );
      try {
    	  
          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
          																		.verifyDisplayedElements( new String[] {elements.email,
          																								elements.firstName, 
          																								elements.lastName,
          																								elements.company},
          																								new String[] {"E-mail Address",
          																										"First Name",
          																										"Last Name",
          																										"Company"}, 
          																								true)
          																		.fillEmailInputField( notRegisteredEmail,
                                                                                        false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.aoiTax,
          																												elements.aoiRealEstate,
          																												elements.aoiCapitalMarkets,
          																												elements.aoiPrivateEquity},
          																									new String[] {"Tax",
          																												"Real Estate",
          																												"Capital Markets",
          																												"Private Equity"},
          																												true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyDispalyElementsNotRegisteredSubscriberPasswordEnabled() throws Exception {

	  String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@yopmail.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
	 	  
	  log.startTest( "Verify all elements are displayed in sign up page password enabled" );
      try {
    	  
          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.verifyDisplayedElements( new String[] {elements.email,
          																								elements.firstName, 
          																								elements.lastName,
          																								elements.company,
          																								elements.password,
          																								elements.confirmPassword},
          																								new String[] {"E-mail Address",
          																										"First Name",
          																										"Last Name",
          																										"Company",
          																										"Password",
          																										"Confirm password"}, 
          																								true)
          																		.fillEmailInputField( notRegisteredEmail,
                                                                                        false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.aoiTax,
          																												elements.aoiRealEstate,
          																												elements.aoiCapitalMarkets,
          																												elements.aoiPrivateEquity},
          																									new String[] {"Tax",
          																												"Real Estate",
          																												"Capital Markets",
          																												"Private Equity"},
          																												true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyDispalyElementsRegisteredSubscriberPasswordDisabled() throws Exception {
          	   	
    	log.startTest( "Verify successfully are dispaly elements for Registered user" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled,
                                                                                                        true )
                                                                                  .action( "login pass disabled" )
                                                                                  .verifyDisplayedElements( new String[] {elements.firstName,
                                                                                		  								elements.lastName, 
                                                                                		  								elements.company,
                                                                                		  								elements.country,
                                                                                		  								elements.city},
																											new String[] {"First Name",
																													"Last Name",
																													"Company",
																													"Country",
																													"City"}, 
																											true)
                                                                                    .verifyDisplayedElements( new String[] {elements.aoiTax,
																															elements.aoiRealEstate,
																															elements.aoiCapitalMarkets,
																															elements.aoiPrivateEquity},
                                                                                    						new String[] {"Tax",
                                                                                    								"Real Estate",
                                                                                    								"Capital Markets",
																													"Private Equity"},
                                                                                    								true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void successfullyDispalyElementsRegisteredSubscriberPasswordEnabled() throws Exception {
          	   	
 	  	log.startTest( "Verify successfully are displayed elements for Registered user" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail,true )
            																	 .fillPasswordInputField(registeredSubscriberPassword, true)
                                                                                  .action( "submit" )
                                                                                  .verifyDisplayedElements( new String[] {elements.firstName,
                                                                                		  								elements.lastName, 
                                                                                		  								elements.company,
                                                                                		  								elements.country,
                                                                                		  								elements.city},
																											new String[] {"First Name",
																													"Last Name",
																													"Company",
																													"Country",
																													"City"}, 
																											true)
                                                                                 .verifyDisplayedElements( new String[] {elements.aoiTax,
            																												elements.aoiRealEstate,
            																												elements.aoiCapitalMarkets,
            																												elements.aoiPrivateEquity},
            																									new String[] {"Tax",
            																												"Real Estate",
            																												"Capital Markets",
            																												"Private Equity"},
            																												true );
                                                                                    

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

  @Test(groups = { "all-tests" })
  public void unsuccessfullyAccessWithoutSubscriberLogingPasswordDisabled() throws Exception {
      	    	
	  log.startTest( "Verify that the Preference page cannot be access without a subscriber loging in Preference Manager" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerDirectlyLoginPassDisabled );
          
            verifyDisplayedElements( new String[]{ elements.firstName,
            									   elements.lastName },
            					     new String[]{ elements.email },
            						 false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithNotConfirmedSubscriberPasswordDisabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@yopmail.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber; 

      log.startTest( "Verify that a subscriber cannot login if he has not been confirmed" );
      try {
          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false)
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.backToLoginBtn},
                                                           		   					 					  new String[] {elements.areasOfInterestTab},
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.almostThere},
                                                                            		   					  new String[] {elements.flagRegisteredUser},
                                                                                                          true )
          																	   .action( "close" );
          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( notRegisteredEmail, true)
          																		.action( "login pass disabled" )
          																		.verifyDisplayedElements( new String[] { elements.invalidEmail }, 
          																								  new String[] { elements.wrongCredentialsLogin }, 
          																								  true );
  
          																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithNotConfirmedSubscriberPasswordEnabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@yopmail.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
      String password = "password" + randNumber;

      log.startTest( "Verify error message if user has registered for PM, but has not completed the double opt in" );
      try {

    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																			   .fillEmailInputField( notRegisteredEmail, false )
																			   .fillFirstNameInputField(firstName, false)
																			   .fillLastNameInputField(lastName, false)
																			   .fillCompanyInputField(company, false)
																			   .fillPasswordInputField(password, false)
																			   .fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.backToLoginBtn},
                                                           		   					 					  new String[] {elements.areasOfInterestTab},
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.almostThere},
                                                                            		   					 new String[] {elements.mainPageDisablePassword},
                                                                                                                 true )
          																	   .action( "close" );
    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail, true)
    	  																	    .fillPasswordInputField( password, true )
    	  																		.action( "submit" )
    	  																		.verifyDisplayedElements( new String[] { elements.invalidEmail}, 
    	  																								  new String[] { elements.wrongCredentialsLogin }, 
    	  																								  true );
          
          																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyDispalyErrorMessageSignUpWithAlreadyRegisteredInCRMUserPasswordDisabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
      

      log.startTest( "Verify Error message that user already exists in the CRM" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
          																		.fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.aoiTab},
                                                           		   					 					  new String[] {elements.selectAOIbelow},
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.mainPageDisablePassword},
                                                                            		   					  new String[] {elements.alreadyAmember},
                                                                                                          true )
          																	   .verifyDisplayedElements( new String[] { elements.subscriberAlreadyExist}, 
          																								 new String[] { elements.alreadyRegistered, elements.forgottenPasswordBtn }, 
          																								 true )
          																	   .action( "forgotten password button" );
          
          																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyDisplayErrorMessageSignUpWithAlreadyRegisteredInCRMUserPasswordEnabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
      String password = "password" + randNumber;

      log.startTest( "Verify Error message that user already exists in the CRM" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( registeredSubscriberEmail, true )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.aoiTab},
                                                           		   					 					  new String[] {elements.selectAOIbelow},
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.subscriberAlreadyExist},
                                                                            		   					  new String[] {elements.alreadyRegistered},
                                                                                                          true )
          																	   .action( "forgotten password button" )
          																	   .verifyDisplayedElements( new String[] { elements.emailExistingSubscriber }, 
          																								 new String[] { elements.retrievePasswordBtn }, 
          																								 true );														   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  //not working correct-> e-mail is hardcoded in the test - should change it every time when create a new customer in CM:
  @Test(groups = { "all-tests" })
  public void successfullyDispalyErrorMessageSignUpWithAlreadyRegisteredInPMUserPasswordDisabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
      

      log.startTest( "Verify error message The user is already registered" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
          																		.fillEmailInputField("k_onlyInPM@yopmail.com", true)//change this e-mail if customer is new
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.aoiTab },
                                                           		   					 					  new String[] {elements.selectAOIbelow },
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.unableToSendEmail},
                                                                            		   					 new String[] {elements.okBtn},
                                                                                                                 true )
          																	   .action( "ok" )
          																	   .verifyDisplayedElements( new String[] { elements.aoiTab }, 
          																								 new String[] { elements.selectAOIbelow }, 
          																								 true );
          
          																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
//not working correct-> e-mail is hardcoded in the test - should change it every time when create a new customer in CM:
  @Test(groups = { "all-tests" })
  public void successfullyDispalyErrorMessageSignUpWithAlreadyRegisteredInPMUserPasswordEnabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      

      log.startTest( "Verify error message The user is already registered" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField("k_onlyInPM@yopmail.com", true)//change this e-mail if customer is new
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.aoiTab },
                                                           		   					 					  new String[] {elements.selectAOIbelow },
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.unableToSendEmail},
                                                                            		   					 new String[] {elements.okBtn},
                                                                                                                 true )
          																	   .action( "ok" )
          																	   .verifyDisplayedElements( new String[] { elements.aoiTab }, 
          																								 new String[] { elements.selectAOIbelow }, 
          																								 true );
          
          																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySignUpWithDeletedFromPMUserPasswordDisabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
      

      log.startTest( "Verify error message when user has been deleted from PM and try to Sign up" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
          																		.fillEmailInputField("k_toDelete@yopmail.com", true)//change this e-mail if customer in CM is new
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.aoiTab },
                                                           		   					 					  new String[] {elements.selectAOIbelow },
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.subscriberAlreadyExist},
                                                                            		   					 new String[] {elements.alreadyRegistered},
                                                                                                                 true )
                                                                                .action( "forgotten password button" )
                                                                                .verifyDisplayedElements(new String[] {elements.email},
                                                                            		   					 new String[] {elements.retrievePasswordBtn},
                                                                                                         true );
          																	   
          
          																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithDeletedFromPMUserPasswordEnabled() throws Exception {
           
      log.startTest( "Verify error message when user has been deleted from PM and try to Login" );
      try {

    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "k_toDeletePassEn@yopmail.com", true)
    	  																	   .fillPasswordInputField( "qwerty12", true )
																			   .action( "submit" )
																			   .verifyDisplayedElements( new String[] { elements.invalidEmail }, 
																					   					 new String[] { elements.wrongCredentialsPassEnabled }, 
																					   					 true )
																			   .verifyDisplayedElements( new String[] { elements.forgottenPassword }, 
																					   					 new String[] { elements.signUpBtn },
																					   					 true );
    	    																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyChangeEmailAndLoginWithTheNewOne() throws Exception {
      
	    String randNumber = driver.getTimestamp();
	    String notRegisteredEmail = randNumber + "email@mailinator.com";
	    String changedEmail = randNumber + "@mailinator.com";
	    String firstName = "firstName" + randNumber;
	    String lastName = "lastName" + randNumber;
		String company = "company" + randNumber;
		String password = "password" + randNumber;
    	String country = "country" + randNumber;
    	String city = "city" + randNumber;
    	
    	log.startTest( "As a PM user I want to be able to change my email and to login with the new Email" );
        try {

        	accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).action( "signup" )
																				  .fillEmailInputField( notRegisteredEmail, false )
																				  .fillFirstNameInputField(firstName, false)
																				  .fillLastNameInputField(lastName, false)
																				  .fillCompanyInputField(company, false)
																				  .fillPasswordInputField(password, false)
																				  .fillConfirmPasswordInputField(password, false)
																				  .action( "continue" )
																				  .action( "register new contact" )
																				  .action( "close" );

        																mailinator.accessMailinator()
        																		  .searchCustomerEmail( notRegisteredEmail )
        																		  .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
        																		  .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
 
        													verifyDisplayedElements( new String[] {elements.ready},
        																			 new String[] {elements.flagRegisteredUser},
        																			 			   true )
        																			.action( "login confirmed email" )
        																			.fillEmailInputField( notRegisteredEmail, true )
            																	    .fillPasswordInputField(password, true)
                                                                                    .action( "submit" )
                                                                                    .fillchangeEmailInputField(changedEmail, true) 
                                                                                    .fillCountryInputField(country, true)
                                                                                    .fillCityInputField(city, true)
                                                                                    .action( "register new contact" )
                                                                                    .verifyDisplayedElements( new String[] { elements.preferencesUpdated }, 
																   					 						new String[] { elements.emailUpdated }, 
																   					 						true )
                                                                                  .action( "preference saved btn" )
                                                                                  .action( "logout" ); 
      	    accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).fillEmailInputField(changedEmail, true) 
      	  																	      .fillPasswordInputField( password, true )
      	  																		  .action( "submit" )
      	  																		  .verifyDisplayedElements( new String[] { elements.firstName}, 
      	  																								    new String[] { elements.lastName }, 
      	  																								    true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void successfullyChangeEmailAndNotAbleToLoginWithTheOldOne() throws Exception {
      
    	
    	log.startTest( "As a PM user I want to be able to change my email and to not be able to login with the old Email" );
        try {

             accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailChangeableEmail, true) 
      	  																	      .fillPasswordInputField( registeredSubscriberPassword, true )
      	  																	      .action( "submit" )
      	  																		  .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword}, 
      	  																								  new String[] { elements.wrongCredentialsLogin }, 
      	  																								  true )
      	  																		  .verifyDisplayedElements( new String[] { elements.forgottenPassword}, 
																							  new String[] { elements.signUpBtn }, 
																							  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyChangeEmailWithExistingInPMUser() throws Exception {
      
    	String randNumber = driver.getTimestamp();
    	    	
    	String firstName = "firstName" + randNumber;
    	String lastName = "lastName" + randNumber;
    	String company = "company" + randNumber;
    	String password = "password" + randNumber;
    	String country = "country" + randNumber;
    	String city = "city" + randNumber;
    	
    	log.startTest( "As a PM user I can’t change my email with existing in PM DB email" );
        try {
        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																				  .fillEmailInputField( "onlyInPMEmail@yopmail.com", false )
																				  .fillFirstNameInputField(firstName, false)
																				  .fillLastNameInputField(lastName, false)
																				  .fillCompanyInputField(company, false)
																				  .fillPasswordInputField(password, false)
																				  .fillConfirmPasswordInputField(password, false)
																				  .action( "continue" )
																				  .verifyDisplayedElements( new String[] {elements.backBtnAreasOfInterest},
																						  					new String[] {elements.areasOfInterestTab},
																						  					true )
																				  .action( "register new contact" )
																				  .verifyDisplayedElements( new String[] {elements.almostThere},
																						  					new String[] {elements.flagRegisteredUser},
																						  					true )
																				  .action( "close" );
         	accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).fillEmailInputField( registeredSubscriberEmailChangeableEmailNotChangeIt, true )
            																	  .fillPasswordInputField(registeredSubscriberPassword, true)
                                                                                  .action( "submit" )
                                                                                  .fillchangeEmailInputField("onlyInPMEmail@yopmail.com", true) 
                                                                                  .fillCountryInputField(country, true)
                                                                                  .fillCityInputField(city, true)
                                                                                  .action( "register new contact" )
                                                                                  .verifyDisplayedElements( new String[] { elements.invalidEmail }, 
																   					 						new String[] { elements.emailAlreadyExist }, 
																   					 						true )
                                                                                  .action( "ok validation error" );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyChangeEmailWithExistingInDynamicsEmail() throws Exception {
      
    	String randNumber = driver.getTimestamp();
    	String country = "country" + randNumber;
    	String city = "city" + randNumber;
    	
    	log.startTest( "As a PM user I can’t change my email with existing in Dynamics email" );
        try {
         	accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).fillEmailInputField( registeredSubscriberEmailChangeableEmailNotChangeIt, true )
            																	  .fillPasswordInputField(registeredSubscriberPassword, true)
                                                                                  .action( "submit" )
                                                                                  .fillchangeEmailInputField("1029-1352@yopmail.com", true) 
                                                                                  .fillCountryInputField(country, true)
                                                                                  .fillCityInputField(city, true)
                                                                                  .action( "register new contact" )
                                                                                  .verifyDisplayedElements( new String[] { elements.invalidEmail }, 
																   					 						new String[] { elements.emailCanNotBeUpdated }, 
																   					 						true )
                                                                                  .action( "ok validation error" );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void successfullyDispalyErrorMessageLoginWithWrongPassword() throws Exception {
           
      log.startTest( "Verify error message when user try to login with wrong password" );
      try {

    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
    	  																	   .fillPasswordInputField( "qwerty11", true )
																			   .action( "submit" )
																			   .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword }, 
																					   					 new String[] { elements.wrongCredentialsLogin }, 
																					   					 true )
																			   .verifyDisplayedElements( new String[] { elements.forgottenPassword }, 
																					   					 new String[] { elements.signUpBtn },
																					   					 true );
    	    																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyRedirectToForgottenPasswordPageWhenLoginWithWrongPassword() throws Exception {
           
      log.startTest( "Verify user is successfully redirect to forgotten password page, when try to login wrong password" );
      try {

    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
    	  																	   .fillPasswordInputField( "qwerty11", true )
																			   .action( "submit" )
																			   .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword }, 
																					   					 new String[] { elements.wrongCredentialsLogin }, 
																					   					 true )
																			   .verifyDisplayedElements( new String[] { elements.forgottenPassword }, 
																					   					 new String[] { elements.signUpBtn },
																					   					 true )
																			   .action( "forgotten password popup" )
																			   .verifyDisplayedElements(new String[] { elements.forgottenPass,
																					   								   elements.forgottenPassPage}, 
																	   					 				new String[] { elements.email,
																	   					 							   elements.retrievePasswordBtn}, 
																	   					 				true );  	  
    	    																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyAppearPopUpOnlyWhenClickOnUpdateButton() throws Exception {
 
      log.startTest( "Verify that the pop-up appears even if the user hasn't changed any of his preference, but just clicked the Update button" );
      try {

      	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
      																		  .action( "login pass disabled" )
      																		  .action( "register new contact" )
      																		  .verifyDisplayedElements(new String[]{ elements.preferencesUpdated },
                                                                                                       new String[]{ elements.emailUpdated },
                                                                                                       true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyCloseUpdatePopUpWithExitButton() throws Exception {
 
      log.startTest( "Verify that the Exit button successfully closes the Update pop-up" );
      try {

      	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailPassEnabled, true)
      																		  .fillPasswordInputField(registeredSubscriberPassword, true)
      																		  .action( "submit" )
      																		  .action( "register new contact" )
      																		  .verifyDisplayedElements(new String[]{ elements.preferencesUpdated },
                                                                                                       new String[]{ elements.emailUpdated },
                                                                                                       true )
      																		  .action("close button" )
      																		  .verifyDisplayedElements(new String[]{ elements.preferencesUpdated },
                                                                                                       new String[]{ elements.emailUpdated },
                                                                                                       false );	
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyConfirmNewRegisteredUserPasswordEnabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      

      log.startTest( "Verify that user can successfully confirm registration via mailinator with password enabled" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.almostThere},
                                                                            		   					  new String[] {elements.flagRegisteredUser},
                                                                                                          true )
           																	   .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                                               		   					  					  new String[] {elements.confirmedRegistrationMessage,
                                               		   					  							  		elements.loginBtnConfirmedRegistration},
                                               		   					  					  true );
														          			  
        
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySentResetPasswordEmailForSubscriberDoesntExistInPMAndCRM() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";

      log.startTest( "Verify that if a subscriber doesn't exist in PM's DB and there is no lead or contact with that email address in the CRM, "
      		+ "no reset password email will be sent and no subscriber will be created in the DB no matter Use Leads is set to " );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( notRegisteredEmail,
                                                                                                     true )
                                                                               .action( "Retrieve Password" );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( notRegisteredEmail );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySentResetPasswordEmailForSubscriberInStatusPending() throws Exception {

	  String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@yopmail.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      
      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email even if there is a lead existing in the CRM with that email and Use Leads is either set to Yes or No   " );
      try {
    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																			   .fillEmailInputField( notRegisteredEmail, false )
																			   .fillFirstNameInputField(firstName, false)
																			   .fillLastNameInputField(lastName, false)
																			   .fillCompanyInputField(company, false)
																			   .fillPasswordInputField(password, false)
																			   .fillConfirmPasswordInputField(password, false)
																			   .action( "continue" )
																			   .action( "register new contact" )
																			   .action( "close" );
    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( notRegisteredEmail,
                                                                                                     true )
                                                                               .action( "Retrieve Password" );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( notRegisteredEmail );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySentResetPasswordEmailForSubscriberInStatusPendingExistingInTheCRM() throws Exception {

      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email even if there is a lead existing in the CRM with that email and Use Leads is either set to Yes or No   " );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( "lastnameEQW1@mailinator.com",
                                                                                                     true )
                                                                               .action( "Retrieve Password" )
                                                                               .verifyDisplayedElements(new String[] {elements.unexpectedErrorForgottenPassword},
                             		   					  					  						    new String[] {elements.unexpectedErrorMessage},
                             		   					  					  							true );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( "lastnameEQW1@mailinator.com" );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyResetPasswordAndLoginWithTheNewPassword() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
	  String newPassword = "newPassword" + randNumber;
      

      log.startTest( "Verify that user can successfully confirm registration via mailinator with password enabled" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
           																	    .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                                               		   					  					  new String[] {elements.flagRegisteredUser,
                                               		   					  							  		elements.loginBtnConfirmedRegistration},
                                               		   					  					  true )
														             			.action( "login confirmed email" )
														             			.action( "Forgotten Password" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( notRegisteredEmail )
															          			.openEmailCampaign( elements.resetYourPasswordEmail )
															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
	          														  
															          			verifyDisplayedElements( new String[] {elements.concepPM},
                                         		   					  					  				 new String[] {elements.password,
                                         		   					  					  						 	   elements.confirmPassword},
                                         		   					  					  				 true )
															          			.fillPasswordInputField(newPassword, false)
															          			.fillConfirmPasswordInputField(newPassword, false)
															          			.action("save reset password")
															          			.verifyDisplayedElements(new String[] {elements.email},
                                         		   					  					  				 new String[] {elements.password},
                                         		   					  					  				 true )
	          																    .fillEmailInputField(notRegisteredEmail, true)
	      																		.fillPasswordInputField(newPassword, true)
	      																		.action( "submit" )
	      																		.verifyDisplayedElements(new String[] {elements.firstName},
                                         		   					  					  				 new String[] {elements.lastName,
                                         		   					  					  						 	   elements.company,
                                         		   					  					  						 	   elements.country,
                                         		   					  					  						 	   elements.city},
                                         		   					  					  				 true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyRedirectUserFromTheLinksFromUpdatePopUp() throws Exception {
      
    	String randNumber = driver.getTimestamp();
    	String firstName = "firstName" + randNumber;
    	String lastName = "lastName" + randNumber;
    	String company = "company" + randNumber;
    	String country = "country" + randNumber;
    	String city = "city" + randNumber;
    	
    	log.startTest( "Verify that the links in the Update pop-up do direct the user to the correct pages" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
                                                                                  .action( "login pass disabled" )
                                                                                  .fillFirstNameInputField(firstName, true)
                                                                                  .fillLastNameInputField(lastName, true)
                                                                                  .fillCompanyInputField(company, true)
                                                                                  .fillCountryInputField(country, true)
                                                                                  .fillCityInputField(city, true)
                                                                                  .action( "register new contact" )
                                                                                  .verifyDisplayedElements(new String[] {elements.preferencesUpdated},
                                         		   					  					  				   new String[] {elements.emailUpdated},
                                         		   					  					  				   true )
                                                                                  .action( "here" )
                                                                                  .verifyDisplayedElements(new String[] {elements.aboutUsConcepPage},
                                         		   					  					  				   new String[] {elements.contactConcepPage},
                                         		   					  					  				   true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyReceiveResetPasswordEmailSubscriberExistingInPMDBAndLeadWithTheSameEmail() throws Exception {

      String notRegisteredEmail = "k_pmlead@mailinator.com";
  	      
      log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Existing' and there is a lead but no contact with that email address in Dynamics CRM a password reset email will NOT be sent no matter what Use Leads is set to" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( notRegisteredEmail );
															          			verifyDisplayedElements( new String[] {elements.publicInbox},
												   					   									 new String[] {elements.emptyInbox},
												   					   									 true );
	          														  			
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyReceiveResetPasswordEmailSubscriberInStatusConfirmed() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      

      log.startTest( "Verify that a subscriber with status 'Confirmed' and an existing contact, can receive a reset password email" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
           																	    .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                                               		   					  					  new String[] {elements.flagRegisteredUser,
                                               		   					  							  		elements.loginBtnConfirmedRegistration},
                                               		   					  					  true )
														             			.action( "login confirmed email" )
														             			.action( "Forgotten Password" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( notRegisteredEmail )
															          			.openEmailCampaign( elements.resetYourPasswordEmail )
															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
	          														  
															          			verifyDisplayedElements( new String[] {elements.concepPM},
                                         		   					  					  				 new String[] {elements.password,
                                         		   					  					  						 	   elements.confirmPassword},
                                         		   					  					  				 true );				
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyShowErrorMessageConnectedWithForgotPasswordUserExistOnlyInPM() throws Exception {

     
      log.startTest( "Verify that the error message connected with Forgot Password problems appears and is prescriptive enough" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( emailOnlyInPM,
                                                                                                     false )
                                                                               .action( "Retrieve Password" )
                                                                               .verifyDisplayedElements( new String[] {elements.unexpectedErrorForgottenPassword},
                       		   					  					  				 					 new String[] {elements.unexpectedErrorMessage,
                       		   					  					  				 							 	   elements.okBtnErrorMessage},
                       		   					  					  				 					 true );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( emailOnlyInPM );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyAppearErrorMessageWhenPasswordsAreNotTheSameResetPassword() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
	  String confirmPassword = "confirmPassword" + randNumber;
      
      log.startTest( "Verify that a subscriber must successfully confirm the new password so that he can change the old one" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
           																	    .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                             		   					  					  				  new String[] {elements.flagRegisteredUser},
                             		   					  					  				  true )
														             			.action( "login confirmed email" )
														             			.action( "Forgotten Password" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( notRegisteredEmail )
															          			.openEmailCampaign( elements.resetYourPasswordEmail )
															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
															          			
	          														verifyDisplayedElements( new String[] {elements.userDetailsSignupPassword},
            		   					  					  				 				 new String[] {elements.email,elements.confirmPassword},
            		   					  					  				 				 true )
	          																	.fillPasswordInputField(password, false)
	          																	.fillConfirmPasswordInputField(confirmPassword, false)
	          																	.action("save reset password")
	          																	.verifyDisplayedElements( new String[]{ elements.confirmPassword },
																		 				   				  new String[]{ elements.passwordsDoNotMatch },
																		 				   				  true );  
	          														
	    } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithOldPasswordWhenItsAlreadyReset() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
	  String newPassword = "newPassword" + randNumber;
      

      log.startTest( "Verify that after the subscriber changes his password he cannot login with the old one" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
           																	    .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                                               		   					  					  new String[] {elements.flagRegisteredUser,
                                               		   					  							  		elements.loginBtnConfirmedRegistration},
                                               		   					  					  true )
														             			.action( "login confirmed email" )
														             			.action( "Forgotten Password" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( notRegisteredEmail )
															          			.openEmailCampaign( elements.resetYourPasswordEmail )
															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
	          														  
	          														  			verifyDisplayedElements( new String[] {elements.concepPM},
	          														  									 new String[] {elements.password,
	          														  											 	   elements.confirmPassword},
	          														  									 true )
	          														  			.fillPasswordInputField(newPassword, false)
	          														  			.fillConfirmPasswordInputField(newPassword, false)
	          														  			.action("save reset password")
	          														  			.verifyDisplayedElements(new String[] {elements.email},
	          														  									 new String[] {elements.password},
	          														  									 true )
	          														  			.fillEmailInputField(notRegisteredEmail, true)
	          														  			.fillPasswordInputField(password, true)
	          														  			.action( "submit" )
	          														  			.verifyDisplayedElements(new String[] {elements.invalidEmailOrPassword},
	          														  									 new String[] {elements.forgottenPassword,
	          														  											  	   elements.signUpBtn},
	          														  									 true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyLoginWithOldPasswordWhenResetPasswordLinkIsNotClicked() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      
      log.startTest( "Verify that the subscriber can login with their old password even though they have requested a password reset but haven't clicked the link" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
           																	    .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                                               		   					  					  new String[] {elements.flagRegisteredUser,
                                               		   					  							  		elements.loginBtnConfirmedRegistration},
                                               		   					  					  true )
														             			.action( "login confirmed email" )
														             			.action( "Forgotten Password" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .action( "Retrieve Password" );
														             
		  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(notRegisteredEmail, true)
	          														  		   .fillPasswordInputField(password, true)
	          														  		   .action( "submit" )
	          														  		   .verifyDisplayedElements(new String[] {elements.firstName},
	          														  									 new String[] {elements.lastName},
	          														  									 true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyLoginWithOldPasswordWhenHaventEnteredNewPassword() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      
      log.startTest( "Verify that the subscriber can login with their old password even though they have requested a password reset, have clicked the link but haven’t entered a new password" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
                                                                                .action( "register new contact" )
           																	    .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                              		   					  					  				  new String[] {elements.confirmedRegistrationMessage,
                              		   					  					  						  		elements.loginBtnConfirmedRegistration},
                              		   					  					  				  true )
														             			.action( "login confirmed email" )
														             			.action( "Forgotten Password" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .action( "Retrieve Password" );
														              mailinator.accessMailinator()
   																				.searchCustomerEmail( notRegisteredEmail )
   																				.openEmailCampaign( elements.resetYourPasswordEmail )
   																				.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
   														  
   														  			verifyDisplayedElements( new String[] {elements.userDetailsSignupPassword},
   														  									 new String[] {elements.email,
   														  											 	   elements.confirmPassword},
   														  									 true );
		  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(notRegisteredEmail, true)
	          														  		   .fillPasswordInputField(password, true)
	          														  		   .action( "submit" )
	          														  		   .verifyDisplayedElements(new String[] {elements.firstName},
	          														  									 new String[] {elements.lastName},
	          														  									 true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySignUpWithEmailExistingInDynamicsPasswordDisabled() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = "k_onlyContact@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	 
      log.startTest( "As a user I can't Sign up if the email address exists in Dynamics" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.action( "continue" )
          																		.action( "register new contact" )
          																		.verifyDisplayedElements( new String[] {elements.invalidEmail},
                                                           		   					 					  new String[] {elements.alreadyRegistered,
                                                           		   					 							  		elements.forgottenPasswordBtn},
                                                           		   					 					  true )
                                                                                .action( "forgotten password button" )
                                                                                .verifyDisplayedElements( new String[] {elements.emailExistingSubscriber},
                                                                            		   					  new String[] {elements.submitBtn},
                                                                                                          true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyPresentedForgotPasswordInLoginPagePasswordDisabled() throws Exception {
     
      log.startTest( "Verify that Forgot password is not presented" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).verifyDisplayedElements( new String[] {elements.forgottenPasswordLink},
                                                           		   					 					 new String[] {elements.forgottenPasswordBtn},
                                                           		   					 					 false );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyReceivedForgotPasswordEmailWhenUserExistingOnlyInPM() throws Exception {

      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email, if there is no contact existing in the CRM with that email" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( registeredUserOnlyInPM,
                                                                                                     true )
                                                                               .action( "Retrieve Password" )
                                                                               .verifyDisplayedElements(new String[] {elements.unexpectedErrorForgottenPassword},
                             		   					  					  						    new String[] {elements.unexpectedErrorMessage},
                             		   					  					  							true );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( registeredUserOnlyInPM );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyReceivedForgotPasswordEmailWhenUseIsPendingInPMAndHaveContactInCRM() throws Exception {

      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email even if there is a contact existing in the CRM with that email" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( registeredUserInPMAndHaveContact,
                                                                                                     true )
                                                                               .action( "Retrieve Password" )
                                                                               .verifyDisplayedElements(new String[] {elements.unexpectedErrorForgottenPassword},
                             		   					  					  						    new String[] {elements.unexpectedErrorMessage},
                             		   					  					  							true );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( registeredUserInPMAndHaveContact );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyReceiveForgotPasswordEmailWhenUserIsExistingInPMAndHaveContactInCRM() throws Exception {

      log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Existing' and there is a contact with that email address in Dynamics CRM a password reset email will still be sent" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
	                                                                            .fillEmailInputField( registeredUserInPMExistingAndContact, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( registeredUserInPMExistingAndContact )
															          			.openEmailCampaign( elements.resetYourPasswordEmail )
															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
	          														  
															          			verifyDisplayedElements( new String[] {elements.password},
															          									 new String[] {elements.confirmPassword},
                                         		   					  					  				 true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
    
  @Test(groups = { "all-tests" })
  public void unsuccessfullyReceiveForgotPasswordEmailWhenUserIsOnlyInPMWithStatusExisting() throws Exception {

      log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Existing' but there is no lead or contact with that email address in Dynamics CRM a password reset email will still be sent??? no matter what Use Leads is set to" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( registeredUserInPMExistingStatus,
                                                                                                     true )
                                                                               .action( "Retrieve Password" )
                                                                               .verifyDisplayedElements(new String[] { elements.unexpectedErrorForgottenPassword },
                             		   					  					  						    new String[] { elements.okBtnErrorMessage },
                             		   					  					  							true );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( registeredUserInPMExistingStatus );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithNotRegisteredUserPasswordEnabled() throws Exception {
          	   	
    	log.startTest( "Verify that the error messages connected with Login problems appears and is prescriptive enough" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true )
            																	 .fillPasswordInputField( "qweqwe12", true)
                                                                                 .action( "submit" )
                                                                                 .verifyDisplayedElements( new String[] { elements.invalidEmail }, 
                                                                                		  					new String[] { elements.wrongCredentialsLogin }, 
                                                                                		  					true );
                                                                                  
                                                                                  

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithSubscribedLeadWithUseLeadsSetToNo() throws Exception {
          	   	
    	log.startTest( "Verify that a subscriber with a lead will be handled when they try to login while Use Leads is set to No" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "subscribedLead@mailinator.com", true )
            																	 .fillPasswordInputField( registeredSubscriberPassword, true)
                                                                                 .action( "submit" )
                                                                                 .verifyDisplayedElements( new String[] { elements.invalidEmail }, 
                                                                                		  				   new String[] { elements.wrongCredentialsLogin }, 
                                                                                		  				   true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithSubscribedContactDeletedFromCRMPasswordEnabled() throws Exception {
          	   	
    	log.startTest( "Verify error message when the user is not found in the CRM" );
        try {

            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "deletedInCRM@mailinator.com", true )
            																	 .fillPasswordInputField( registeredSubscriberPassword, true)
                                                                                 .action( "submit" )
                                                                                 .verifyDisplayedElements( new String[] { elements.invalidEmail }, 
                                                                                		  				   new String[] { elements.wrongCredentialsLogin }, 
                                                                                		  				   true );                                                                             

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyResetPasswordWhenPasswordResetLinkHasExpired() throws Exception {
     
      log.startTest( "Verify error message if you try to use the password reset link after it has expired" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
           																		.fillEmailInputField( registeredSubscriberEmailPassEnabled, true )
	                                                                            .action( "Retrieve Password" );
           																		Thread.sleep(900000);
           																		           																		           
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( registeredSubscriberEmailPassEnabled )
	          																	.openEmailCampaign( elements.resetYourPasswordEmail )
															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
	          														  
														verifyDisplayedElements( new String[] {elements.invalidTokenResetPassword},
                                         		   					  			 new String[] {elements.invalidTokenResetPassword},
                                         		   					  			 true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyConfirmSignUpDoubleOptLinkDontHaveExpirationPeriod() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
      
      log.startTest( "Verify error message if you try to use the double opt in after the link has expired" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField(firstName, false)
          																		.fillLastNameInputField(lastName, false)
          																		.fillCompanyInputField(company, false)
          																		.fillPasswordInputField(password, false)
          																		.fillConfirmPasswordInputField(password, false)
          																		.action( "continue" )
          																		.verifyDisplayedElements( new String[] {elements.selectAOIbelow},
                                                           		   					 					  new String[] {elements.mainPageDisablePassword},
                                                           		   					 					  true )
                                                                                .action( "register new contact" )
                                                                                .verifyDisplayedElements( new String[] {elements.almostThere},
                                                                            		   					  new String[] {elements.areasOfInterestTab},
                                                                                                          true )
           																	   .action( "close" );
           																	   Thread.sleep(900000);
		           
           															 mailinator.accessMailinator()
           															 		   .searchCustomerEmail( notRegisteredEmail )
           															 		   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
           															 		   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
			  
           															verifyDisplayedElements( new String[] {elements.ready},
                             		   					  					  				 new String[] {elements.flagRegisteredUser,
                             		   					  					  						 	   elements.loginBtnConfirmedRegistration},
                             		   					  					  				 true );
                   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyDisplayCompanyFieldInSignUpPage() throws Exception {
               
      log.startTest( "Verify that the company field is supported in the sign up page" );
      try {

      	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
      																		  .verifyDisplayedElements(new String[]{ elements.firstName,
                                                                                        						     elements.lastName },
      																				  				   new String[]{ elements.company,
      																				  						   		 elements.city,
      																				  						   		 elements.country},
      																				  								 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyRegisterSubscriberWhenWelcomeEmailIsDeleted() throws Exception {
  	
      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
      String firstName = "firstName" + randNumber;
      String lastName = "lastName" + randNumber;
      String company = "company" + randNumber;

      log.startTest( "Verify that the welcome email is not mandatory for registering a subscriber and creating a lead/contact" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
                                                                               .fillEmailInputField( notRegisteredEmail,false )
                                                                               .fillFirstNameInputField(firstName, false)
                                                                               .fillLastNameInputField(lastName, false)
                                                                               .fillCompanyInputField(company, false)
                                                                               .action( "continue" )
                                                                               .action( "register new contact" );

			 														mailinator.accessMailinator()
			 																  .searchCustomerEmail( notRegisteredEmail )
			 																  .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
			 																  .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
			 														
			 														 verifyDisplayedElements( new String[] {elements.ready},
                              		   					  					  				  new String[] {elements.confirmedRegistrationMessage,
                              		   					  					  						  	    elements.loginBtnConfirmedRegistration},
                              		   					  					  				  true );
                    
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySentResetPasswordEmailForLeadExistingOnlyInCRM() throws Exception {

      log.startTest( "Verify that if a subscriber isn't registered but a lead exists with that email address in the CRM, a reset password email won't be sent if Use Leads is set to No and the subscriber won't be added" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( "onlyLead@mailinator.com",
                                                                                                     true )
                                                                               .action( "Retrieve Password" )
                                                                               .verifyDisplayedElements( new String[]{ elements.resetPasswordEmailSent },
                                                                                       					 new String[]{ elements.sentLinkToResetPassword },
                                                                                       					 true );
          															 mailinator.accessMailinator()
          																	   .searchCustomerEmail( "onlyLead@mailinator.com" );
          																		verifyDisplayedElements( new String[] {elements.publicInbox},
                             		   					  					  							 new String[] {elements.emptyInbox},
                             		   					  					  							 true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullyLoginWithInactiveContactPasswordEnabled() throws Exception {

      log.startTest( "Verify that a subscriber can't login if it's contact is inactive in Dynamics CRM" );
      try {

    	   accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "k_inactiveContact@mailinator.com", true)
    	  																	    .fillPasswordInputField( registeredSubscriberPassword, true )
    	  																		.action( "submit" )
    	  																		.verifyDisplayedElements( new String[] { elements.invalidEmail}, 
    	  																								  new String[] { elements.wrongCredentialsLogin }, 
    	  																								  true );
          
          																	   
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyDispalySignUpOnFirstPageOnlyContactInformationOnSecondOnlyPreferences() throws Exception {

  	String randNumber = driver.getTimestamp();
  	    	
  	String notRegisteredEmail = randNumber + "email@mailinator.com";
  	String firstName = "firstName" + randNumber;
  	String lastName = "lastName" + randNumber;
  	String company = "company" + randNumber;
  	String password = "password" + randNumber;
  	    	
  	log.startTest( "Verify that for the Sign up template, on the first page there is only contact information while on the second there are only preferences" );
      try {
      	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
      	 																	  .verifyDisplayedElements(new String[] {elements.email,
      	 																			  								 elements.firstName,
      	 																			  								 elements.lastName},
																						  			   new String[] {elements.company,
																						  					   		 elements.password,
																						  					   		 elements.confirmPassword},
																						  			   true )
      	 																	  .verifyDisplayedElements( new String[] {elements.aoiTax},
																	  									new String[] {elements.aoiPrivateEquity},
																	  									false )
      	 																	  .verifyDisplayedElements( new String[] {elements.aoiRealEstate},
																	  									new String[] {elements.aoiCapitalMarkets},
																	  									false )
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
																						  				true )
																			  .verifyDisplayedElements(new String[] {elements.email,
														  								 							 elements.firstName,
														  								 							 elements.lastName},
																					  				   new String[] {elements.company,
																					  						   		 elements.password,
																					  						   		 elements.confirmPassword},
																					  				   false );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyNavigateOnSignUpPageFromSecondPageToTheFirstPage() throws Exception {

  	String randNumber = driver.getTimestamp();
  	    	
  	String notRegisteredEmail = randNumber + "email@mailinator.com";
  	String firstName = "firstName" + randNumber;
  	String lastName = "lastName" + randNumber;
  	String company = "company" + randNumber;
  	String password = "password" + randNumber;
  	    	
  	log.startTest( "Verify that for the Sign up template the user can navigate back to the first page from the second page" );
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
																						  				true )
																			  .action( "back aoi" )
																			  .verifyDisplayedElements(new String[] {elements.email,
														  								 							 elements.firstName,
														  								 							 elements.lastName},
																					  				   new String[] {elements.company,
																					  								 elements.password,
																					  								 elements.confirmPassword},
																					  				   true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullySaveInformationOnFirstPageWhenGoesToSecondPageAndTurnBackOnFirstPage() throws Exception {

  	String notRegisteredEmail = "email@mailinator.com";
  	String firstName = "firstName";
  	String lastName = "lastName";
  	String company = "company";
  	String password = "password";
  	    	
  	log.startTest( "Verify that for the Sign up template when the user enters information in the first page, goes to the second page and then goes back, the information is still there" );
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
																						  				true )
																			  .action( "back aoi" );
																			  verifySubscriberDetail(elements.email, "email@mailinator.com", true);
																			  verifySubscriberDetail(elements.firstName, "firstName", true);
																			  verifySubscriberDetail(elements.lastName, "lastName", true);
																			  verifySubscriberDetail(elements.company, "company", true);
																			  verifySubscriberDetail(elements.password, "password", true);
																			  verifySubscriberDetail(elements.confirmPassword, "password", true);
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullySaveChoosedInformationOnSecondPageWhenGoesToFirstPageAndTurnBackOnSecondPage() throws Exception {

	  String notRegisteredEmail = "email@mailinator.com";
	  String firstName = "firstName";
	  String lastName = "lastName";
	  String company = "company";
	  String password = "password";
      
      log.startTest( "Verify that for the Sign up template when the user enters information in the second page, goes to the first page and then goes back, the information is still there" );
      try {

    	  	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
    	  	 																	  .fillEmailInputField( notRegisteredEmail, false )
			  																	  .fillFirstNameInputField(firstName, true)
			  																	  .fillLastNameInputField(lastName, true)
			  																	  .fillCompanyInputField(company, true)
			  																	  .fillPasswordInputField(password, true)
			  																	  .fillConfirmPasswordInputField(password, true)
			  																	  .action( "continue" )
			  																	  .action( "private equity" )
			  																	  .action( "capital markets" )
			  																	  .action( "back aoi" )
			  																	  .action( "continue" )
			  																	  .verifyDisplayedElements( new String[]{ elements.realEstateFalse,
      	 																			   								      elements.taxFalse},
      	 																			   						new String[]{ elements.privateequityTrue,
   	 																			   								          elements.capitalmarketsTrue},
      	 																			   						true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullySignUpOnlyAfterClickSaveButtonDoubleOptInEmailIsSent() throws Exception {
	  
	    String randNumber = driver.getTimestamp();
	    String notRegisteredEmail = randNumber + "email@mailinator.com";
	    String firstName = "firstName" + randNumber;
	    String lastName = "lastName" + randNumber;
		String company = "company" + randNumber;
		String password = "password" + randNumber;
  
		log.startTest( "Verify that for the Sign up template only when the user clicks the save button and all the required information is there, will the subscriber be put into the DB and the double opt-in email will be sent" );
		try {

			 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																				  .fillEmailInputField( notRegisteredEmail, false )
																				  .fillFirstNameInputField(firstName, false)
																				  .fillLastNameInputField(lastName, false)
																				  .fillCompanyInputField(company, false)
																				  .fillPasswordInputField(password, false)
																				  .fillConfirmPasswordInputField(password, false)
																				  .action( "continue" );
			 
																		mailinator.accessMailinator()
            														   			  .searchCustomerEmail( notRegisteredEmail );
            														   			   verifyDisplayedElements( new String[] {elements.publicInbox},
            														   					   					new String[] {elements.emptyInbox},
            														   					   					true );
																				  action( "register new contact" )
																				  .action( "close" );

      																   mailinator.accessMailinator()
      																		  	 .searchCustomerEmail( notRegisteredEmail )
      																		     .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
      																		     .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );

      													verifyDisplayedElements( new String[] {elements.ready},
      																			 new String[] {elements.flagRegisteredUser,
      																					       elements.loginBtnConfirmedRegistration},
      																			 			   true )
      																			.action( "login confirmed email" )
      																			.fillEmailInputField( notRegisteredEmail, true )
          																	    .fillPasswordInputField(password, true)
                                                                                .action( "submit" )
    	  																		.verifyDisplayedElements( new String[] { elements.firstName}, 
    	  																								  new String[] { elements.lastName }, 
    	  																								  true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullyAskForResetPasswordWhenSignUpWithExistingInCRMContactPasswordEnabled() throws Exception {
	  
	    String randNumber = driver.getTimestamp();
	    String firstName = "firstName" + randNumber;
	    String lastName = "lastName" + randNumber;
		String company = "company" + randNumber;
		String password = "password" + randNumber;
  
		log.startTest( "Verify that if a subscriber tries to sign up with an existing contact, PM will ask them to reset their password" );
		try {

			 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																				  .fillEmailInputField( onlyContactEmail, false )
																				  .fillFirstNameInputField(firstName, false)
																				  .fillLastNameInputField(lastName, false)
																				  .fillCompanyInputField(company, false)
																				  .fillPasswordInputField(password, false)
																				  .fillConfirmPasswordInputField(password, false)
																				  .action( "continue" )
																				  .action( "register new contact" )
																				  .verifyDisplayedElements( new String[] { elements.subscriberAlreadyExist}, 
    	  																								    new String[] { elements.alreadyRegistered,
    	  																										  		   elements.forgottenPasswordBtn}, 
    	  																								    true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }

  @Test(groups = { "all-tests" })
  public void successfullyAskForResetPasswordWhenSignUpWithEmailOfDuplicateContact() throws Exception {
	  
	    String randNumber = driver.getTimestamp();
	    String firstName = "firstName" + randNumber;
	    String lastName = "lastName" + randNumber;
		String company = "company" + randNumber;
		String password = "password" + randNumber;
  
		log.startTest( "Verify that if a subscriber tries to register with the email of a duplicate contact, they will be asked to recover their password" );
		try {

			 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
																				  .fillEmailInputField( duplicateContactEmail, false )
																				  .fillFirstNameInputField(firstName, false)
																				  .fillLastNameInputField(lastName, false)
																				  .fillCompanyInputField(company, false)
																				  .fillPasswordInputField(password, false)
																				  .fillConfirmPasswordInputField(password, false)
																				  .action( "continue" )
																				  .action( "register new contact" )
																				  .verifyDisplayedElements( new String[] { elements.subscriberAlreadyExist}, 
    	  																								    new String[] { elements.alreadyRegistered,
    	  																										  		   elements.forgottenPasswordBtn}, 
    	  																								    true );
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullySeeAndChangeCompanyFieldForRegisteredContact() throws Exception {

      String randNumber = driver.getTimestamp();
      String notRegisteredEmail = randNumber + "email@mailinator.com";
  	  String firstName = "firstName" + randNumber;
	  String lastName = "lastName" + randNumber;
	  String company = "company" + randNumber;
	  String password = "password" + randNumber;
	        
      log.startTest( "Verify that a registered subscriber that is a contact can login and see his/her company field and change it" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
          																		.fillEmailInputField( notRegisteredEmail, false )
          																		.fillFirstNameInputField( firstName, false )
          																		.fillLastNameInputField( lastName, false )
          																		.fillCompanyInputField( company, false )
          																		.fillPasswordInputField( password, false )
          																		.fillConfirmPasswordInputField( password, false )
          																		.action( "continue" )
                                                                                .action( "register new contact" )
           																	    .action( "close" );
           
														             mailinator.accessMailinator()
														          			   .searchCustomerEmail( notRegisteredEmail )
														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
														             
														             verifyDisplayedElements( new String[] {elements.ready},
                                               		   					  					  new String[] {elements.flagRegisteredUser,
                                               		   					  							  		elements.loginBtnConfirmedRegistration},
                                               		   					  					  true )
														             			.action( "login confirmed email" )
	                                                                            .fillEmailInputField( notRegisteredEmail, true )
	                                                                            .fillPasswordInputField( password, true )
	                                                                            .action( "submit" )
	                                                                            .fillCompanyInputField( "newValueCompany", true )
	                                                                            .fillCityInputField( "newValueCity", true )
	                                                                            .fillCountryInputField( "newValueCountry", true )
	                                                                            .action( "register new contact" );
		  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(notRegisteredEmail, true)
	          														  		   .fillPasswordInputField(password, true)
	          														  		   .action( "submit" )
	          														  		   .verifySubscriberDetail(elements.company, "newValueCompany", true)
	          														  		   .verifySubscriberDetail(elements.city, "newValueCity", true)
	          														  		   .verifySubscriberDetail(elements.country, "newValueCountry", true);
		  					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySentResetPasswordEmailForSubscriberExistingOnlyInPMDBWithStatusConfirmed() throws Exception {
     

      log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Confirmed' but there is no lead or contact with that email address in Dynamics CRM a password reset email will NOT be sent no matter what Use Leads is set to" );
      try {

          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
                                                                               .fillEmailInputField( emailOnlyInPMConfirmed, true )
                                                                               .action( "Retrieve Password" )
                                                                               .verifyDisplayedElements( new String[]{ elements.unexpectedErrorForgottenPassword},
                                                                                                         new String[]{ elements.unexpectedErrorMessage,
                                                                                                                       elements.okBtnErrorMessage },
                                                                                                         true );
          														   mailinator.accessMailinator()
          														   			  .searchCustomerEmail( emailOnlyInPMConfirmed );
          														   			   verifyDisplayedElements( new String[] {elements.publicInbox},
          														   					   					new String[] {elements.emptyInbox},
          														   					   					true );

      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void unsuccessfullySentResetPasswordEmailAndResetPasswordForDuplicateLeadsExistingOnlyInCRM() throws Exception {
     	  
	  //Test uses only Use Leads = No:
      log.startTest( "Verify that if duplicate leads exist in the CRM a subscriber is NOT able to request a password reset and successfully reset their password no matter what Use Leads is set to" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
	                                                                            .fillEmailInputField( duplicateLeadEmail, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( duplicateLeadEmail );
	          																	verifyDisplayedElements( new String[] {elements.publicInbox},
												   					   									 new String[] {elements.emptyInbox},
												   					   									 true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullySentResetPasswordEmailAndResetPasswordForDuplicateContactsExistingOnlyInCRM() throws Exception {

      String randNumber = driver.getTimestamp();
	  String newPassword = "newPassword" + randNumber;
	  
	  //Test uses only Use Leads = No:
      log.startTest( "Verify that if duplicate contacts exist in the CRM a subscriber is able to request a password reset and successfully reset their password no matter what Use Leads is set to" );
      try {

           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
	                                                                            .fillEmailInputField( duplicateContactEmail, true )
	                                                                            .action( "Retrieve Password" );
	          														  mailinator.accessMailinator()
	          																	.searchCustomerEmail( duplicateContactEmail )
															          			.openEmailCampaign( elements.resetYourPasswordEmail )
															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
	          														  
															          			verifyDisplayedElements( new String[] {elements.concepPM},
                                         		   					  					  				 new String[] {elements.password,
                                         		   					  					  						 	   elements.confirmPassword},
                                         		   					  					  				 true )
															          			.fillPasswordInputField(newPassword, false)
															          			.fillConfirmPasswordInputField(newPassword, false)
															          			.action("save reset password")
															          			.verifyDisplayedElements(new String[] {elements.email},
                                         		   					  					  				 new String[] {elements.password},
                                         		   					  					  				 true )
	          																    .fillEmailInputField(duplicateContactEmail, true)
	      																		.fillPasswordInputField(newPassword, true)
	      																		.action( "submit" )
	      																		.verifyDisplayedElements(new String[] {elements.firstName},
                                         		   					  					  				 new String[] {elements.lastName,
                                         		   					  					  						 	   elements.company,
                                         		   					  					  						 	   elements.country,
                                         		   					  					  						 	   elements.city},
                                         		   					  					  				 true );
															          			
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  } 
  
  @Test(groups = { "all-tests" })
  public void successfullyAccessDynamics() throws Exception {
   	 
      log.startTest( "Verify can access Dynamics and it's menus successful" );
      try {
    	  			dynamics.accessDynamics().generateClose();
    	  			
    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
					  				        true );
					
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  } 
  
  @Test(groups = { "all-tests" })
  public void successfullySearchAndFindRegisteredContact() throws Exception {
   	  
      log.startTest( "Verify can access and search in Contact menu" );
      try {
    	  			dynamics.accessDynamics().generateClose();
    	  			
    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
					  				        true );
    	  			dynamics.actionDynamics( "sales" )
    	  					.actionDynamics( "contacts" );
    	  			dynamics.searchCustomerEmail( "inPMandContact@mailinator.com" )
    	  					.verifyDisplayedContact( "inPMandContact@mailinator.com" );
    	  	   	  				
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  } 
  
  @Test(groups = { "all-tests" })
  public void verifyUpdatedCorrectContactInformationAndAoi() throws Exception {
   	  
      log.startTest( "Verify Contact names and Areas of Interests are updated correct" );
      try {
    	    dynamics.accessDynamics().generateClose();
    	  			
    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
					  				        true );
    	  	dynamics.actionDynamics( "sales" )
    	  			.actionDynamics( "contacts" );
    	  	dynamics.searchCustomerEmail( "inPMandContact@mailinator.com" )
    	  			.verifyDisplayedContact( "inPMandContact@mailinator.com" )
    	  			.openFoundContact()
    	  			.verifyDisplayedElementsDynamics( "111 555" )
    	  			.verifyDisplayedElementsInIFrame( new String[]{ elementsDynamics.capitalMarketsNo,
    	  										   elementsDynamics.realEstateNo,
    	  										   elementsDynamics.taxNo,
    	  										   elementsDynamics.privateEquityNo},
    	  							 new String[]{ "Capital markets",
    	  									 	   "Real Estate",
    	  									 	   "Tax",
						  						   "Private equity"},
    	  							 true );
    	  			    	  	   	  				
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  } 
  
  @Test(groups = { "all-tests" })
  public void verifyUpdatedMLInformationContact() throws Exception {
   	  
      log.startTest( "Verify Marketing lists are updated correct for Contact in Dynamics" );
      try {
    	    dynamics.accessDynamics().generateClose();
    	  			
    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
					  				        true );
    	  	dynamics.actionDynamics( "sales" )
    	  			.actionDynamics( "contacts" );
    	  	dynamics.searchCustomerEmail( "inPMandContact@mailinator.com" )
    	  			.verifyDisplayedContact( "inPMandContact@mailinator.com" )
    	  			.openFoundContact();
    	  	dynamics.verifyDisplayedElementsDynamics( "111 555" )
    	  			.actionDynamics( "common menu" );
    	  	/*verifyDisplayedElements( new String[]{ elementsDynamics.capitalMarketsNo,
    	  										   elementsDynamics.realEstateNo,
    	  										   elementsDynamics.taxNo,
    	  										   elementsDynamics.privateEquityNo},
    	  							 new String[]{ "Capital markets",
    	  									 	   "Real Estate",
    	  									 	   "Tax",
						  						   "Private equity"},
    	  							 true );*/	  			
    	  	   	  				
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  }
  
  @Test(groups = { "all-tests" })
  public void successfullySupportDynamicsFieldsByPM() throws Exception {
   	  
      log.startTest( "Verify Dynamics fields support by PM" );
      try {
    	    dynamics.accessDynamics().generateClose();
    	  			
    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
					  				        true );
    	  	dynamics.actionDynamics( "sales" )
    	  			.actionDynamics( "contacts" );
    	  	dynamics.searchCustomerEmail( "inPMandContact@mailinator.com" )
    	  			.verifyDisplayedContact( "inPMandContact@mailinator.com" )
    	  			.openFoundContact()
    	  			.verifyDisplayedElementsDynamics( "111 555" );
    	  			
    	  	verifyDisplayedElements( new String[]{ elementsDynamics.capitalMarketsNo,
    	  										   elementsDynamics.realEstateNo,
    	  										   elementsDynamics.taxNo,
    	  										   elementsDynamics.privateEquityNo,
    	  										   elementsDynamics.fullNameDynamics,
    	  										   elementsDynamics.companyNameDynamics,
    	  										   elementsDynamics.emailStatus},
    	  							 new String[]{ "Capital markets",
    	  									 	   "Real Estate",
    	  									 	   "Tax",
						  						   "Private equity",
						  						   "Full name",
						  						   "Company name",
						  						   "Email Status"},
    	  							 true );
    	  	/*Full Name
    	  	Company Name
    	  	AOI_Capital Markets
    	  	AOI_Real Estate
    	  	AOI_Tax
    	  	AOI_Private Equity
    	  	Email Status
    	  	Email Format
    	  	Preference manager (keeps date of Sign Up)
    	  	Preference manager (keeps date of Last Update)*/
    	  			    	  	   	  				
      } catch( Exception e ) {

          log.endStep( false );
          throw e;
      }

      log.endTest();
  } 
    
}