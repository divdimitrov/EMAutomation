package em.selenium.automation;

import org.testng.annotations.Test;


public class AdminLandingPageTests extends BaseAdminPage {

    @Test(groups = { "all-tests" })
    public void successfullyAccessAdminLandingPage() throws Exception {

        log.startTest( "Verify that user can successfully access Admin landing page" );
        try {

        	AdminLandingPage( adminPanelURL ).verifyDisplayedElementsAdmin( new String[]{ elements.email,
                                                                                		 								 elements.password},
                                                                                         					true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyLoginInAdminPanelWithCorrectCredentials() throws Exception {

        log.startTest( "Verify that user can successfully login in Admin with correct credentials" );
        try {

        	AdminLandingPage( adminPanelURL ).fillEmailInputField( superAdminUsername )
        									 .fillPasswordInputField(superAdminPassword)
            								 .action( "login to admin panel" )
            								 .verifyDisplayedElements( new String[]{ elements.DashboardMainHeading},
            								  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyDisplayErrorWhenWrongUsernameIsEntered() throws Exception {

        log.startTest( "Verify that error is thrown when wrong username is entered" );
        try {

        	AdminLandingPage( adminPanelURL ).fillEmailInputField( wrongUsername )
        									 .fillPasswordInputField(superAdminPassword)
            								 .action( "login to admin panel" )
            								 .verifyDisplayedElements( new String[]{ elements.wrongCredentialsMsg},
            								  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyDisplayErrorWhenWrongPasswordIsEntered() throws Exception {

        log.startTest( "Verify that error is thrown when wrong password is entered" );
        try {

        	AdminLandingPage( adminPanelURL ).fillEmailInputField( superAdminUsername )
        									 .fillPasswordInputField(wrongPassword)
            								 .action( "login to admin panel" )
            								 .verifyDisplayedElements( new String[]{ elements.wrongCredentialsMsg},
            								  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyDisplayMessagesThatEmailAnPasswordFieldsAreRequired() throws Exception {

        log.startTest( "Verify that error is thrown when no email and password is entered" );
        try {

        	AdminLandingPage( adminPanelURL ).action( "login to admin panel" )
            								 .verifyDisplayedElements( new String[]{
            										 elements.emailFieldRequired, elements.passwordFieldRequired},
            								  true );
            								// .verifyDisplayedElements(new String[] {elements.passwordFieldRequired}, true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyCheckRememberMeCheckbox() throws Exception {

        log.startTest( "Verify customer successfully check remember me checkbox" );
        try {

        	AdminLandingPage( adminPanelURL ).fillEmailInputField( superAdminUsername )
        									 .action( "remember me" )
        									 .verifyCheckboxIsChecked(elements.rememberMeCheckbox, true)
        									 .action("remember me")
        									 .verifyCheckboxIsChecked(elements.rememberMeCheckbox, false);
        					// .verifyDisplayedElements(new String[] {elements.passwordFieldRequired}, true);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDisplayFormAlreadySubscribedUsers() throws Exception {
//
//        log.startTest( "Verify that a form for already subscribed users is successfully displayed" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).verifyDisplayedElements( new String[]{ elements.email,
//                                                                                                                   		 elements.password},
//                                                                                                           new String[]{ elements.logoMorganAndHustler,
//                                                                                                        		         elements.forgottenPassword},
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//    
//    @Test(groups = { "all-tests" })
//    public void unsuccessfullyLoginWithoutPassword() throws Exception {
//
//        log.startTest( "Verify can't login without password, only with e-mail of registered user, when Password protection = Yes" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( superAdminUsername )
//                                                                                 .action( "login main page" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.firstNamePreferencePage,
//                                                                                		 								 elements.lastNamePreferencePage},
//                                                                                                           new String[]{ elements.jobTitle,
//                                                                          		 								 		 elements.businessPhone },
//                                                                                                           false );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void unsuccessfullyDisplayedPasswordFieldPasswordDisabled() throws Exception {
//
//        log.startTest( "Verify that a password input field and forgotten passowrd link are not displayed when password protection is disabled from the settings.xml file" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).verifyDisplayedElements( new String[]{ elements.mainHeadingExistingSubscriberForm },
//                                                                                                            new String[]{ elements.email },
//                                                                                                            true )
//                                                                                  .verifyDisplayedElements( new String[]{ elements.password},
//                                                                                                            new String[]{ elements.forgottenPassword },
//                                                                                                            false );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//    
//    @Test(groups = { "all-tests" })
//    public void unsuccessfullyLoginWithNoEmailExistingSubscribersForm() throws Exception {
//
//        log.startTest( "Verify that can't login with no e-mail, but with existing password, when Password protection = Yes" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( "", "qwerty12", true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.email },
//                                                                                                           new String[]{ elements.loginBtn },
//                                                                                                           true );
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//    
//    @Test(groups = { "all-tests" })
//    public void unsuccessfullyLoginWithWrongEmailPasswordEnabled() throws Exception {
//
//        log.startTest( "Verify that can't login with wrong e-mail, when Password Protection = Yes" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "notvalidemail", false )
//                                                                                 .action( "login main page" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.email },
//                                                                                                           new String[]{ elements.password },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDisplayValidationMessageNoEmailExistingSubscribersForm() throws Exception {
//
//        log.startTest( "Verify that a validation message is dispalyed, when already subscribed user is trying to login with email input field blank" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( "",
//                                                                                                   "pa$$w0rd",
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.invalidEmailOrPassword },
//                                                                                                           new String[]{ elements.closeInforMessage },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDisplayValidationMessageNotValidEmailExistingSubscribersForm() throws Exception {
//
//        log.startTest( "Verify that a validation message is dispalyed when already subscribed user is trying to login with not valid email" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( "notvalidemail",
//                                                                                                   "",
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.invalidEmailOrPassword },
//                                                                                                           new String[]{ elements.closeInforMessage },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDisplayValidationMessageNotExistingEmailExistingSubscribersForm()
//                                                                                             throws Exception {
//
//        String randNumber = driver.getTimestamp();
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//
//        log.startTest( "Verify that a validation message is dispalyed when user is trying to login with not existing email" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( notRegisteredEmail,
//                                                                                                   "pa$$w0rd",
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.invalidEmailOrPassword },
//                                                                                                           new String[]{ elements.closeInforMessage },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDisplayValidationMessageNoPasswordExistingSubscribersForm() throws Exception {
//
//        log.startTest( "Verify that a validation message is dispalyed when user is trying to login without password" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
//                                                                                                   "",
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.unableToSignHereBtn },
//                                                                                                           new String[]{ elements.invalidEmailOrPassword },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDisplayValidationMessageUsernameAndPasswordNotMatchExistingSubscribersForm()
//                                                                                                        throws Exception {
//
//        log.startTest( "Verify that a validation message is dispalyed when username and password doesn't match" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
//                                                                                                   "notmatchpassword",
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.unableToSignHereBtn },
//                                                                                                           new String[]{ elements.invalidEmailOrPassword },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyLoginWithValidEmailAndPasswordWhenPassEnabled() throws Exception {
//
//        log.startTest( "Verify that an existing user can login successfully when password is enabled" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
//                                                                                                   registeredSubscriberPassword,
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.contactDetails,
//                                                                                                                         elements.aoiText },
//                                                                                                           new String[]{ elements.firstName,
//                                                                                                                         elements.lastName },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyRedirectUserResetPasswordPage() throws Exception {
//
//        log.startTest( "Verify that user is redirected to Password Reset page" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password button" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.headingPasswordResetPage,
//                                                                                                                   		 elements.backToLoginBtn },
//                                                                                                           new String[]{ elements.email,
//                                                                                                                   		 elements.resetPasswordBtn },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//     @Test(groups = { "all-tests" })
//    public void successfullyReturnUserToLandingPageFromPasswordResetPage() throws Exception {
//
//        log.startTest( "Verify that user can successfully return to Landing Page from the Password Reset page" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password button" )
//                                                                                 .action( "Back" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.mainHeadingExistingSubscriberForm,
//                                                                                                                   		 elements.mainHeadingContactDetailsPage },
//                                                                                                           new String[]{ elements.email,
//                                                                                                                   		 elements.password },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDispalyValidationMessageNoEmailResetPassword() throws Exception {
//
//        log.startTest( "Verify that a validation message is successfully displayed when user is trying to reset his password without specifying email address" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password button" )
//                                                                                 .action( "reset my password" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.closeInforMessageInvalidEmailForgotPass },
//                                                                                                           new String[]{ elements.emptyEmailField },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDispalyValidationMessageNotValidEmailResetPassword() throws Exception {
//
//        log.startTest( "Verify that a validation message is successfully displayed when user is trying to reset his password using invalid email" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password button" )
//                                                                                 .fillEmailInputField( "notvalidemail",
//                                                                                                       true )
//                                                                                 .action( "reset my password" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.closeInforMessageInvalidEmailForgotPass },
//                                                                                                           new String[]{ elements.emptyEmailField },
//                                                                                                           true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    @Test(groups = { "all-tests" })
//    public void successfullyDispalyValidationMessageNotExistingEmailResetPassword() throws Exception {
//
//        String randNumber = driver.getTimestamp();
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//
//        log.startTest( "Verify that a validation message is successfully displayed when user is trying to reset passwort to not existing user" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password button" )
//                                                                                 .fillEmailInputField( notRegisteredEmail, true )
//                                                                                 .action( "reset my password" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.resetPasswordEmailSent},
//                                                                                                           new String[]{ elements.sentLinkToResetPassword,
//                                                                                                                   		 elements.closeInforMessageResetPassEmailSent },
//                                                                                                           true );
//            														   mailinator.accessMailinator()
//            														   			  .searchCustomerEmail( notRegisteredEmail );
//            														   			   verifyDisplayedElements( new String[] {elements.publicInbox},
//            														   					   					new String[] {elements.emptyInbox},
//            														   					   					true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//    
//    @Test(groups = { "all-tests" })
//    public void successfullyLoginWithValidEmailWhenPassDisabled() throws Exception {
//
//        log.startTest( "Verify that an existing user can login successfully when password is disabled" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled,
//                                                                                                        true )
//                                                                                  .action( "Submit" )
//                                                                                  .verifyDisplayedElements( new String[]{ elements.contactDetails,
//                                                                                                                          elements.aoiText },
//                                                                                                            new String[]{ elements.firstName ,
//                                                                                                            		      elements.lastName  },
//                                                                                                            true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//    
//  @Test(groups = { "all-tests" })
//  public void successfullyUpdateAllUserDetailsForRegisteredUserWhenPassDisabled() throws Exception {
//      
//    	String randNumber = driver.getTimestamp();
//    	String firstName = "firstName" + randNumber;
//    	String lastName = "lastName" + randNumber;
//    	String company = "company" + randNumber;
//    	String jobTitle = "jobTitle" + randNumber;
//    	String businessPhone = "businessPhone" + randNumber;
//    	
//    	log.startTest( "Verify can update all user details for registered user, when password is disabled" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled,
//                                                                                                        true )
//                                                                                  .action( "login pass disabled" )
//                                                                                  .fillFirstNameInputFieldPreferencePage(firstName, true)
//                                                                                  .fillLastNameInputFieldlPreferencePage(lastName, true)
//                                                                                  .fillCompanyInputField(company, true)
//                                                                                  .fillJobTitleInputField(jobTitle, true)
//                                                                                  .fillBusinessPhoneInputField(businessPhone, true)
//                                                                                  .action( "update" )
//                                                                                  .verifyDisplayedElements(new String[]{ elements.textSuccessfullyUpdateContactDetails},
//                                                                                                            new String[]{ elements.successfullyUpdateContactHereText},
//                                                                                                            true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithNotRegisteredUserPasswordDisabled() throws Exception {
//          	   	
//    	log.startTest( "Verify can't login with not registered user pass disabled" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( "55555@yopmail.com", false )
//                                                                                  .action( "login pass disabled" )
//                                                                                  .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword }, 
//                                                                                		  					new String[] { elements.closeInforMessage }, 
//                                                                                		  					true );
//                                                                                  
//                                                                                  
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyRegisterNewUserPasswordDisabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@yopmail.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//      
//
//      log.startTest( "Verify that user can successfully sign up password disabled" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillEmailInputField( notRegisteredEmail, false )
//                                                                                .action( "register new contact" )
//                                                                                .verifyDisplayedElements( new String[] {elements.thankYouSigningUp},
//                                                                            		   					  new String[] {elements.checkYourEmail,
//                                                                            		   							 		elements.almostThere},
//                                                                                                          true );
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyRegisterNewUserPasswordEnabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//      log.startTest( "Verify that when a subscriber signs up, he will be able to create a contact in the CRM when the CRM user has only the security role that comes with PM Solution" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" )
//                                                                                .verifyDisplayedElements( new String[] {elements.thankYouSigningUp},
//                                                          		   					  					  new String[] {elements.checkYourEmail,
//                                                          		   					  							  		elements.almostThere},
//                                                                                true );
//           														 	 mailinator.accessMailinator()
//  																		  	   .searchCustomerEmail( notRegisteredEmail )
//  																		       .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//  																		       .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//
//  													verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//  																			 new String[] {elements.confirmedRegistrationMessage},
//  																			 			   true )
//  																			   .action( "login confirmed email" )
//  																			   .fillEmailInputField( notRegisteredEmail, true )
//      																	       .fillPasswordInputField(password, true)
//                                                                               .action( "submit" );
//       
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyElementsNotRegisteredSubscriberPasswordDisabled() throws Exception {
//
//	  String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@yopmail.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	 	  
//	  log.startTest( "Verify all elements are displayed in sign up page password disabled" );
//      try {
//    	  
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//          																		.verifyDisplayedElements( new String[] {elements.email,
//          																											    elements.firstName},
//          																								  new String[] {elements.lastName,
//          																											    elements.companySignUpNoPass}, 
//          																								  true)
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.verifyDisplayedElements( new String[] {elements.signUpMainHeading },
//          																								  new String[] {elements.signUpInfoMessage,
//          																										  		elements.alreadyAmember}, 
//          																								  true)
//          																		.action( "register new contact" )
//                                                                                .verifyDisplayedElements( new String[] {elements.thankYouSigningUp},
//                                                                            		   					  new String[] {elements.checkYourEmail,
//                                                                            		   							 		elements.almostThere},
//                                                                                                          true );
//          																		
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyElementsNotRegisteredSubscriberPasswordEnabled() throws Exception {
//
//	  String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@yopmail.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//	 	  
//	  log.startTest( "Verify all elements are displayed in sign up page password enabled" );
//      try {
//    	  
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.verifyDisplayedElements( new String[] {elements.email,
//          																												elements.companySignUpNoPass},
//          																								  new String[] {elements.password,
//          																											  	elements.confirmPassword}, 
//          																								true)
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//          																		.verifyDisplayedElements( new String[] {elements.signUpMainHeading },
//																						  				  new String[] {elements.signUpInfoMessage,
//																						  						  	    elements.alreadyAmember}, 
//																						  				  true)
//          																		.action( "register new contact" )
//          																		.verifyDisplayedElements( new String[] {elements.thankYouSigningUp},
//                                                          		   					  					  new String[] {elements.checkYourEmail,
//                                                          		   					  							  		elements.almostThere},
//                                                          		   					  					  true );
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyElementsRegisteredSubscriberPasswordDisabled() throws Exception {
//          	   	
//    	log.startTest( "Verify successfully are dispaly elements for Registered user" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled,
//                                                                                                        true )
//                                                                                  .action( "login pass disabled" )
//                                                                                  .verifyDisplayedElements( new String[] {elements.firstNamePreferencePage,
//                                                                                		  								  elements.lastNamePreferencePage},
//																											new String[] {elements.company,
//																														  elements.businessPhone}, 
//																											true)
//                                                                                  .verifyDisplayedElements( new String[] {elements.aoiInternationalLegislation,
//																														  elements.aoiGovernment},
//                                                                                    						new String[] {elements.aoiCapitalMarkets,
//                                                                                    									  elements.aoiTechnology},
//                                                                                    						true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyElementsRegisteredSubscriberPasswordEnabled() throws Exception {
//          	   	
// 	  	log.startTest( "Verify successfully are displayed elements for Registered user" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail,true )
//            																	 .fillPasswordInputField(registeredSubscriberPassword, true)
//                                                                                  .action( "submit" )
//                                                                                  .verifyDisplayedElements( new String[] {elements.firstNamePreferencePage,
//                                                		  								  								  elements.lastNamePreferencePage},
//                                                                                		  					new String[] {elements.company,
//                                                                                		  								  elements.businessPhone}, 
//                                                                                		  					true)
//                                                                                  .verifyDisplayedElements( new String[] {elements.aoiInternationalLegislation,
//																						  								  elements.aoiGovernment},
//                                                                                		  					new String[] {elements.aoiCapitalMarkets,
//                                                                                		  								  elements.aoiTechnology},
//                                                                                		  					true );    																  
//                                                                                               
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyAccessWithoutSubscriberLogingPasswordDisabled() throws Exception {
//      	    	
//	  log.startTest( "Verify that the Preference page cannot be access without a subscriber loging in Preference Manager" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerDirectlyLoginPassDisabled );
//                                                verifyDisplayedElements( new String[]{ elements.aoiCapitalMarkets,
//            																		   elements.aoiInternationalLegislation },
//            															 new String[]{ elements.aoiTax,
//            																	 	   elements.aoiTechnology},
//            															 false );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithNotConfirmedSubscriberPasswordDisabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@yopmail.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//      
//
//      log.startTest( "Verify that a subscriber cannot login if he has not been confirmed" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )	
//          																		.fillEmailInputField( notRegisteredEmail, false)
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.action( "register new contact" )
//          																		.verifyDisplayedElements( new String[] {elements.thankYouSigningUp},
//                                                           		   					 					  new String[] {elements.checkYourEmail,
//                                                           		   					 							  		elements.almostThere},
//                                                           		   					 					  true );
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( notRegisteredEmail, true)
//          																		.action( "login pass disabled" )
//          																		.verifyDisplayedElements( new String[] { elements.invalidEmail }, 
//          																								  new String[] { elements.wrongCredentialsLogin }, 
//          																								  true );        																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithNotConfirmedSubscriberPasswordEnabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@yopmail.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//      String password = "password" + randNumber;
//
//      log.startTest( "Verify error message if user has registered for PM, but has not completed the double opt in" );
//      try {
//
//    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//																			   .fillEmailInputField( notRegisteredEmail, false )
//																			   .fillFirstNameInputField(firstName, false)
//																			   .fillLastNameInputField(lastName, false)
//																			   .fillPasswordInputField(password, false)
//																			   .fillConfirmPasswordInputField(password, false)
//																			   .fillCompanyInputFieldSignUp(company, false)
//         																	   .action( "register new contact" )
//         																	   .verifyDisplayedElements( new String[] {elements.thankYouSigningUp},
//                                                          		   					 					 new String[] {elements.checkYourEmail,
//                                                          		   					 							  		elements.almostThere},
//                                                          		   					 					 true );
//    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail, true)
//    	  																	   .fillPasswordInputField( password, true )
//    	  																	   .action( "submit" )
//    	  																	   .verifyDisplayedElements( new String[] { elements.invalidEmail}, 
//    	  																								  new String[] { elements.wrongCredentialsLogin }, 
//    	  																								  true );
//          
//          																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyErrorMessageSignUpWithAlreadyRegisteredInCRMUserPasswordDisabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//      
//
//      log.startTest( "Verify Error message that user already exists in the CRM" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//          																		.fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//                                                                                .action( "register new contact" )
//                                                                                .verifyDisplayedElements( new String[] {elements.unableToSignUp},
//                                                                            		   					  new String[] {elements.errorMsgCantSignUp },
//                                                                                                          true )
//          																	    .verifyDisplayedElements( new String[] { elements.closeErrorMessageCantSignUp }, 
//          																								 new String[] { elements.forgottenPasswordHereBtn }, 
//          																								 true )
//          																	    .action( "forgotten password error message" );          																   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDisplayErrorMessageSignUpWithAlreadyRegisteredInCRMUserPasswordEnabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//      String password = "password" + randNumber;
//
//      log.startTest( "Verify Error message that user already exists in the CRM" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( registeredSubscriberEmail, true )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" )
//                                                                                .verifyDisplayedElements( new String[] {elements.unableToSignUp},
//                                                          		   					  					  new String[] {elements.errorMsgCantSignUp },
//                                                          		   					  					  true )
//                                                                                .verifyDisplayedElements( new String[] { elements.closeErrorMessageCantSignUp }, 
//																						 				  new String[] { elements.forgottenPasswordHereBtn }, 
//																						 				  true )
//                                                                                .action( "forgotten password error message" )
//          																	    .verifyDisplayedElements( new String[] { elements.email }, 
//          																								  new String[] { elements.resetPasswordBtn }, 
//          																								  true );															   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  //not working correct-> e-mail is hardcoded in the test - should change it every time when create a new customer in CM:
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyErrorMessageSignUpWithAlreadyRegisteredInPMUserPasswordDisabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//      
//
//      log.startTest( "Verify error message The user is already registered" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//          																		.fillEmailInputField("k_onlyInPM@yopmail.com", true)//change this e-mail if customer is new
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//                                                                                .action( "register new contact" )
//                                                                                .verifyDisplayedElements( new String[] {elements.unableToSendSignUpEmail},
//                                        		   					  					  				  new String[] {elements.unableToSendSignUpEmailCloseBtn},
//                                        		   					  					  				  true );	
//        																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
////not working correct-> e-mail is hardcoded in the test - should change it every time when create a new customer in CM:
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyErrorMessageSignUpWithAlreadyRegisteredInPMUserPasswordEnabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//
//      log.startTest( "Verify error message The user is already registered" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField("k_onlyInPM@yopmail.com", true)//change this e-mail if customer is new
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//          																		.action( "register new contact" )
//                                                                                .verifyDisplayedElements( new String[] {elements.unableToSendSignUpEmail},
//                                         		   					  					  				  new String[] {elements.unableToSendSignUpEmailCloseBtn},
//                                         		   					  					  				  true );
//          
//          																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySignUpWithDeletedFromPMUserPasswordDisabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//      
//
//      log.startTest( "Verify error message when user has been deleted from PM and try to Sign up" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//          																		.fillEmailInputField("k_toDeleteNew@yopmail.com", true)//change this e-mail if customer in CM is new
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.action( "register new contact" )
//          																		.verifyDisplayedElements( new String[] {elements.unableToSignUp},
//                                        		   					  					  				  new String[] {elements.errorMsgCantSignUp },
//                                        		   					  					  				  true )
//          																		.verifyDisplayedElements( new String[] { elements.closeErrorMessageCantSignUp }, 
//																		 				  				  new String[] { elements.forgottenPasswordHereBtn }, 
//																		 				  				  true );
//                    																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithDeletedFromPMUserPasswordEnabled() throws Exception {
//           
//      log.startTest( "Verify error message when user has been deleted from PM and try to Login" );
//      try {
//
//    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "k_toDeleteNew@yopmail.com", true)
//    	  																	   .fillPasswordInputField( "qwerty12", true )
//																			   .action( "submit" )
//																			   .verifyDisplayedElements( new String[]{ elements.invalidEmailOrPassword },
//                                                                                       					 new String[]{ elements.closeInforMessage },
//                                                                                       					 true );														   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyChangeEmailAndLoginWithTheNewOne() throws Exception {
//      
//	    String randNumber = driver.getTimestamp();
//	    String notRegisteredEmail = randNumber + "email@mailinator.com";
//	    String changedEmail = randNumber + "@mailinator.com";
//	    String firstName = "firstName" + randNumber;
//	    String lastName = "lastName" + randNumber;
//		String company = "company" + randNumber;
//		String password = "password" + randNumber;
//    	String jobTitle = "jobTitle" + randNumber;
//    	String businessPhone = "businessPhone" + randNumber;
//    	
//    	log.startTest( "As a PM user I want to be able to change my email and to login with the new Email" );
//        try {
//
//        	accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).action( "signup" )
//																				  .fillEmailInputField( notRegisteredEmail, false )
//																				  .fillFirstNameInputField(firstName, false)
//																				  .fillLastNameInputField(lastName, false)
//																				  .fillCompanyInputFieldSignUp(company, false)
//																				  .fillPasswordInputField(password, false)
//																				  .fillConfirmPasswordInputField(password, false)
//																				  .action( "register new contact" );
//
//        																mailinator.accessMailinator()
//        																		  .searchCustomerEmail( notRegisteredEmail )
//        																		  .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//        																		  .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
// 
//        													verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//        																			 new String[] {elements.confirmedRegistrationMessage},
//        																			 			   true )
//        																			.action( "login confirmed email" )
//        																			.fillEmailInputField( notRegisteredEmail, true )
//            																	    .fillPasswordInputField(password, true)
//                                                                                    .action( "submit" )
//                                                                                    .fillchangeEmailInputField(changedEmail, true) 
//                                                                                    .fillJobTitleInputField(jobTitle, true)
//                                                                                    .fillBusinessPhoneInputField(businessPhone, true)
//                                                                                    .action( "update" )
//                                                                                    .verifyDisplayedElements( new String[] { elements.textSuccessfullyUpdateContactDetails }, 
//																   					 						new String[] { elements.successfullyUpdateContactHereText }, 
//																   					 						true )
//                                                                                  .action( "logout" ); 
//      	    accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).fillEmailInputField(changedEmail, true) 
//      	  																	      .fillPasswordInputField( password, true )
//      	  																		  .action( "submit" )
//      	  																		  .verifyDisplayedElements( new String[] { elements.firstNamePreferencePage}, 
//      	  																								    new String[] { elements.lastNamePreferencePage }, 
//      	  																								    true );
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyChangeEmailAndNotAbleToLoginWithTheOldOne() throws Exception {
//      
//    	
//    	log.startTest( "As a PM user I want to be able to change my email and to not be able to login with the old Email" );
//        try {
//
//             accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailChangeableEmail, true) 
//      	  																	      .fillPasswordInputField( registeredSubscriberPassword, true )
//      	  																	      .action( "submit" )
//      	  																		  .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword}, 
//      	  																								  	new String[] { elements.closeInforMessage }, 
//      	  																								  	true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyChangeEmailWithExistingInPMUser() throws Exception {
//      
//    	String randNumber = driver.getTimestamp();
//    	    	
//    	String firstName = "firstName" + randNumber;
//    	String lastName = "lastName" + randNumber;
//    	String company = "company" + randNumber;
//    	String password = "password" + randNumber;
//    	String jobTitle = "jobTitle" + randNumber;
//    	String businessPhone = "businessPhone" + randNumber;
//    	
//    	log.startTest( "As a PM user I can’t change my email with existing in PM DB email" );
//        try {
//        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//																				  .fillEmailInputField( "onlyInPMEmail@yopmail.com", false )
//																				  .fillFirstNameInputField(firstName, false)
//																				  .fillLastNameInputField(lastName, false)
//																				  .fillCompanyInputFieldSignUp(company, false)
//																				  .fillPasswordInputField(password, false)
//																				  .fillConfirmPasswordInputField(password, false)
//																				  .action( "register new contact" )
//																				  .verifyDisplayedElements( new String[] {elements.almostThere},
//																						  					new String[] {elements.checkYourEmail},
//																						  					true );
//         	accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).fillEmailInputField( registeredSubscriberEmailChangeableEmailNotChangeIt, true )
//            																	  .fillPasswordInputField(registeredSubscriberPassword, true)
//                                                                                  .action( "submit" )
//                                                                                  .fillchangeEmailInputField("onlyInPMEmail@yopmail.com", true) 
//                                                                                  .fillJobTitleInputField(jobTitle, true)
//                                                                                  .fillBusinessPhoneInputField(businessPhone, true)
//                                                                                  .action( "update" )
//                                                                                  .verifyDisplayedElements( new String[] { elements.emailAlreadyExistPreferencePage }, 
//																   					 						new String[] { elements.emailAlreadyExistResetPassBtn }, 
//																   					 						true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyChangeEmailWithExistingInDynamicsEmail() throws Exception {
//      
//    	String randNumber = driver.getTimestamp();
//    	String jobTitle = "jobTitle" + randNumber;
//    	String businessPhone = "businessPhone" + randNumber;
//    	
//    	log.startTest( "As a PM user I can’t change my email with existing in Dynamics email" );
//        try {
//         	accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).fillEmailInputField( registeredSubscriberEmailChangeableEmailNotChangeIt, true )
//            																	  .fillPasswordInputField(registeredSubscriberPassword, true)
//                                                                                  .action( "submit" )
//                                                                                  .fillchangeEmailInputField("1029-1352@yopmail.com", true) 
//                                                                                  .fillJobTitleInputField(jobTitle, true)
//                                                                                  .fillBusinessPhoneInputField(businessPhone, true)
//                                                                                  .action( "update" )
//                                                                                  .verifyDisplayedElements( new String[] { elements.emailAlreadyExistPreferencePage }, 
//												   					 										new String[] { elements.emailAlreadyExistResetPassBtn }, 
//												   					 										true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalyErrorMessageLoginWithWrongPassword() throws Exception {
//           
//      log.startTest( "Verify error message when user try to login with wrong password" );
//      try {
//
//    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
//    	  																	   .fillPasswordInputField( "qwerty11", true )
//																			   .action( "submit" )
//																			   .verifyDisplayedElements( new String[]{ elements.invalidEmailOrPassword },
//                                                                                       					 new String[]{ elements.closeInforMessage },
//                                                                                       					 true );
//    	    																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyRedirectToSignUpPageWhenLoginWithWrongPassword() throws Exception {
//           
//      log.startTest( "Verify user is successfully redirect to sign up page, when try to login wrong password" );
//      try {
//
//    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
//    	  																	   .fillPasswordInputField( "qwerty11", true )
//																			   .action( "submit" )
//																			   .verifyDisplayedElements( new String[] { elements.wrongCredentials }, 
//																					   					 new String[] { elements.invalidEmailOrPassword }, 
//																					   					 true )
//																			   .action( "here sign up" )
//																			   .verifyDisplayedElements(new String[] { elements.firstName,
//																					   								   elements.lastName}, 
//																	   					 				new String[] { elements.password,
//																	   					 							   elements.confirmPassword}, 
//																	   					 				true );																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyAppearPopUpOnlyWhenClickOnUpdateButton() throws Exception {
// 
//      log.startTest( "Verify that the pop-up appears even if the user hasn't changed any of his preference, but just clicked the Update button" );
//      try {
//
//      	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
//      																		  .action( "login pass disabled" )
//      																		  .action( "update" )
//      																		  .verifyDisplayedElements(new String[]{ elements.textSuccessfullyUpdateContactDetails },
//                                                                                                       new String[]{ elements.successfullyUpdateContactHereText },
//                                                                                                       true );
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyCloseUpdatePopUpWithExitButton() throws Exception {
// 
//      log.startTest( "Verify that the Exit button successfully closes the Update pop-up" );
//      try {
//
//      	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(registeredSubscriberEmailPassEnabled, true)
//      																		  .fillPasswordInputField(registeredSubscriberPassword, true)
//      																		  .action( "submit" )
//      																		  .action( "update" )
//      																		  .verifyDisplayedElements(new String[]{ elements.textSuccessfullyUpdateContactDetails },
//                                                                                                       new String[]{ elements.successfullyUpdateContactHereText },
//                                                                                                       true )
//      																		  .action( "close update button" )
//      																		  .verifyDisplayedElements(new String[]{ elements.textSuccessfullyUpdateContactDetails },
//                                                                                                       new String[]{ elements.successfullyUpdateContactHereText },
//                                                                                                       false );	
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyConfirmNewRegisteredUserPasswordEnabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//
//      log.startTest( "Verify that user can successfully confirm registration via mailinator with password enabled" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           																		Thread.sleep(90);
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true );
//       
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySentResetPasswordEmailForSubscriberDoesntExistInPMAndCRM() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//
//      log.startTest( "Verify that if a subscriber doesn't exist in PM's DB and there is no lead or contact with that email address in the CRM, "
//      		+ "no reset password email will be sent and no subscriber will be created in the DB no matter Use Leads is set to " );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password" )
//                                                                               .fillEmailInputField( notRegisteredEmail, true )
//                                                                               .action( "reset my password" );
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( notRegisteredEmail );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySentResetPasswordEmailForSubscriberInStatusPending() throws Exception {
//
//	  String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@yopmail.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email even if there is a lead existing in the CRM with that email and Use Leads is either set to Yes or No   " );
//      try {
//    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//																			   .fillEmailInputField( notRegisteredEmail, false )
//																			   .fillFirstNameInputField(firstName, false)
//																			   .fillLastNameInputField(lastName, false)
//																			   .fillCompanyInputFieldSignUp(company, false)
//																			   .fillPasswordInputField(password, false)
//																			   .fillConfirmPasswordInputField(password, false)
//																			   .action( "register new contact" );
//    	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
//                                                                               .fillEmailInputField( notRegisteredEmail, true )
//                                                                               .action( "reset my password" );
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( notRegisteredEmail );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySentResetPasswordEmailForSubscriberInStatusPendingExistingInTheCRM() throws Exception {
//
//      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email even if there is a lead existing in the CRM with that email and Use Leads is either set to Yes or No   " );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "Forgotten Password" )
//                                                                               .fillEmailInputField( "lastnameEQW1@mailinator.com",
//                                                                                                     true )
//                                                                               .action( "reset my password" )
//                                                                               .verifyDisplayedElements(new String[] {elements.unableToSendResetPassword},
//                             		   					  					  						    new String[] {elements.unableToSendResetPasswordText,
//                             		   					  					  						    			  elements.unableToSendResetPasswordHereLink},
//                             		   					  					  							true );
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( "lastnameEQW1@mailinator.com" );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyResetPasswordAndLoginWithTheNewPassword() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//	  String newPassword = "newPassword" + randNumber;
//      
//
//      log.startTest( "Verify that user can successfully confirm registration via mailinator with password enabled" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true )
//														             			.action( "login confirmed email" )
//														             			.action( "forgotten Password" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .action( "reset my password" );
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( notRegisteredEmail )
//															          			.openEmailCampaign( elements.resetYourPasswordEmail )
//															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//	          														  
//															          			verifyDisplayedElements( new String[] {elements.resetYourPasswordTextResetPage},
//                                         		   					  					  				 new String[] {elements.password,
//                                         		   					  					  						 	   elements.confirmPassword},
//                                         		   					  					  				 true )
//															          			.fillPasswordInputField(newPassword, false)
//															          			.fillConfirmPasswordInputField(newPassword, false)
//															          			.action("save reset password")
//															          			.verifyDisplayedElements(new String[] {elements.sucessfullyResetPassword },
//                                         		   					  					  				 new String[] {elements.backToLoginBtn },
//                                         		   					  					  				 true )
//															          			.action( "back" )
//	          																    .fillEmailInputField(notRegisteredEmail, true)
//	      																		.fillPasswordInputField(newPassword, true)
//	      																		.action( "submit" )
//	      																		.verifyDisplayedElements(new String[] {elements.firstNamePreferencePage},
//                                         		   					  					  				 new String[] {elements.lastNamePreferencePage,
//                                         		   					  					  						 	   elements.company},
//                                         		   					  					  				 true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyRedirectUserFromTheLinksFromUpdatePopUp() throws Exception {
//      
//    	String randNumber = driver.getTimestamp();
//    	String firstName = "firstName" + randNumber;
//    	String lastName = "lastName" + randNumber;
//    	String company = "company" + randNumber;
//    	String jobTitle = "jobTitle" + randNumber;
//    	String businessPhone = "businessPhone" + randNumber;
//    	
//    	log.startTest( "Verify that the links in the Update pop-up do direct the user to the correct pages" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
//                                                                                  .action( "login pass disabled" )
//                                                                                  .fillFirstNameInputFieldPreferencePage(firstName, true)
//                                                                                  .fillLastNameInputFieldlPreferencePage(lastName, true)
//                                                                                  .fillCompanyInputField(company, true)
//                                                                                  .fillJobTitleInputField(jobTitle, true)
//                                                                                  .fillBusinessPhoneInputField(businessPhone, true)
//                                                                                  .action( "update" )
//                                                                                  .verifyDisplayedElements(new String[] {elements.textSuccessfullyUpdateContactDetails},
//                                         		   					  					  				   new String[] {elements.successfullyUpdateContactHereText},
//                                         		   					  					  				   true )
//                                                                                  .action( "here update message" )
//                                                                                  .verifyDisplayedElements(new String[] {elements.aboutUsConcepPage},
//                                         		   					  					  				   new String[] {elements.contactConcepPage},
//                                         		   					  					  				   true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyReceiveResetPasswordEmailSubscriberExistingInPMDBAndLeadWithTheSameEmail() throws Exception {
//
//      String notRegisteredEmail = "k_pmlead@mailinator.com";
//  	      
//      log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Existing' and there is a lead but no contact with that email address in Dynamics CRM a password reset email will NOT be sent no matter what Use Leads is set to" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .action( "reset my password" );
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( notRegisteredEmail );
//															          			verifyDisplayedElements( new String[] {elements.publicInbox},
//												   					   									 new String[] {elements.emptyInbox},
//												   					   									 true );
//	          														  			
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyReceiveResetPasswordEmailSubscriberInStatusConfirmed() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//
//      log.startTest( "Verify that a subscriber with status 'Confirmed' and an existing contact, can receive a reset password email" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true )
//														             			.action( "login confirmed email" )
//														             			.action( "forgotten Password" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .action( "reset my password" );
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( notRegisteredEmail )
//															          			.openEmailCampaign( elements.resetYourPasswordEmail )
//															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//	          														  
//															          			verifyDisplayedElements( new String[] {elements.resetYourPasswordTextResetPage},
//                                         		   					  					  				 new String[] {elements.password,
//                                         		   					  					  						 	   elements.confirmPassword},
//                                         		   					  					  				 true );				
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyShowErrorMessageConnectedWithForgotPasswordUserExistOnlyInPM() throws Exception {
//
//     
//      log.startTest( "Verify that the error message connected with Forgot Password problems appears and is prescriptive enough" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//                                                                               .fillEmailInputField( emailOnlyInPM,
//                                                                                                     false )
//                                                                               .action( "reset my password" )
//                                                                               .verifyDisplayedElements( new String[] {elements.resetPasswordEmailSent},
//                       		   					  					  				 					 new String[] {elements.sentLinkToResetPassword,
//                       		   					  					  				 							 	   elements.closeInforMessageResetPassEmailSent},
//                       		   					  					  				 					 true );
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( emailOnlyInPM );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyAppearErrorMessageWhenPasswordsAreNotTheSameResetPassword() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//	  String confirmPassword = "confirmPassword" + randNumber;
//      
//      log.startTest( "Verify that a subscriber must successfully confirm the new password so that he can change the old one" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerChangeableEmail ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true )
//														             			.action( "login confirmed email" )
//														             			.action( "forgotten Password" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .action( "reset my password" );
//														             			Thread.sleep(90);
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( notRegisteredEmail )
//															          			.openEmailCampaign( elements.resetYourPasswordEmail )
//															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//															          			
//	          														verifyDisplayedElements( new String[] {elements.resetYourPasswordTextResetPage},
//            		   					  					  				 				 new String[] {elements.password,
//            		   					  					  				 						 	   elements.confirmPassword},
//            		   					  					  				 				 true )
//	          																	.fillPasswordInputField(password, false)
//	          																	.fillConfirmPasswordInputField(confirmPassword, false)
//	          																	.verifyDisplayedElements( new String[]{ elements.passwordsDoNotMatch },
//																		 				   				  new String[]{ elements.confirmPassword },
//																		 				   				  true );  
//	          														
//	    } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithOldPasswordWhenItsAlreadyReset() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//	  String newPassword = "newPassword" + randNumber;
//      
//
//      log.startTest( "Verify that after the subscriber changes his password he cannot login with the old one" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           																		Thread.sleep(250);
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true )
//														             			.action( "login confirmed email" )
//														             			.action( "forgotten Password" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .action( "reset my password" );
//														             
//														             			Thread.sleep(250);
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( notRegisteredEmail )
//															          			.openEmailCampaign( elements.resetYourPasswordEmail )
//															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//	          														  
//	          														  			verifyDisplayedElements( new String[] {elements.resetYourPasswordTextResetPage},
//	          														  									 new String[] {elements.password,
//	          														  											 	   elements.confirmPassword},
//	          														  									 true )
//	          														  			.fillPasswordInputField(newPassword, false)
//	          														  			.fillConfirmPasswordInputField(newPassword, false)
//	          														  			.action("save reset password")
//	          														  			.verifyDisplayedElements(new String[] {elements.sucessfullyResetPassword},
//	          														  									 new String[] {elements.backToLoginBtn},
//	          														  									 true )
//	          														  			.action( "back" )
//	          														  			.fillEmailInputField(notRegisteredEmail, true)
//	          														  			.fillPasswordInputField(password, true)
//	          														  			.action( "submit" )
//	          														  			.verifyDisplayedElements(new String[] {elements.invalidEmailOrPassword},
//	          														  									 new String[] {elements.closeInforMessage},
//	          														  									 true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyLoginWithOldPasswordWhenResetPasswordLinkIsNotClicked() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//      log.startTest( "Verify that the subscriber can login with their old password even though they have requested a password reset but haven't clicked the link" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true )
//														             			.action( "login confirmed email" )
//														             			.action( "forgotten Password" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .action( "reset my password" );
//														             
//		  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(notRegisteredEmail, true)
//	          														  		   .fillPasswordInputField(password, true)
//	          														  		   .action( "submit" )
//	          														  		   .verifyDisplayedElements(new String[] {elements.firstNamePreferencePage},
//	          														  									new String[] {elements.lastNamePreferencePage},
//	          														  									true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyLoginWithOldPasswordWhenHaventEnteredNewPassword() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//      log.startTest( "Verify that the subscriber can login with their old password even though they have requested a password reset, have clicked the link but haven’t entered a new password" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true )
//														             			.action( "login confirmed email" )
//														             			.action( "forgotten Password" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .action( "reset my password" );
//														             			Thread.sleep(150);
//														              mailinator.accessMailinator()
//   																				.searchCustomerEmail( notRegisteredEmail )
//   																				.openEmailCampaign( elements.resetYourPasswordEmail )
//   																				.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//   														  
//   														  			verifyDisplayedElements( new String[] {elements.resetYourPasswordTextResetPage},
//   														  									 new String[] {elements.password,
//   														  											 	   elements.confirmPassword},
//   														  									 true );
//		  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(notRegisteredEmail, true)
//	          														  		   .fillPasswordInputField(password, true)
//	          														  		   .action( "submit" )
//	          														  		   .verifyDisplayedElements(new String[] {elements.firstNamePreferencePage},
//	          														  									new String[] {elements.lastNamePreferencePage},
//	          														  									true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySignUpWithEmailExistingInDynamicsPasswordDisabled() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = "k_onlyContact@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	 
//      log.startTest( "As a user I can't Sign up if the email address exists in Dynamics" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.action( "register new contact" )
//          																		.verifyDisplayedElements( new String[] {elements.unableToSignUp},
//                                                          		   					  					  new String[] {elements.errorMsgCantSignUp },
//                                                          		   					  					  true )
//          																		.verifyDisplayedElements( new String[] { elements.closeErrorMessageCantSignUp }, 
//          																								  new String[] { elements.forgottenPasswordHereBtn }, 
//          																								  true )
//          																		.action( "forgotten password error message" ); 
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyPresentedForgotPasswordInLoginPagePasswordDisabled() throws Exception {
//     
//      log.startTest( "Verify that Forgot password is not presented" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).verifyDisplayedElements( new String[] {elements.forgottenPassword},
//                                                           		   					 					  new String[] {elements.forgottenPasswordBtn},
//                                                           		   					 					  false );
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyReceivedForgotPasswordEmailWhenUserExistingOnlyInPM() throws Exception {
//
//      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email, if there is no contact existing in the CRM with that email" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//                                                                               .fillEmailInputField( registeredUserOnlyInPM, true )
//                                                                               .action( "reset my password" );
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( registeredUserOnlyInPM );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyReceivedForgotPasswordEmailWhenUserIsPendingInPMAndHaveContactInCRM() throws Exception {
//
//      log.startTest( "Verify that a subscriber with status 'Pending' cannot receive a reset password email even if there is a contact existing in the CRM with that email" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//                                                                               .fillEmailInputField( registeredUserInPMAndHaveContact, true )
//                                                                               .action( "reset my password" )
//                                                                               /*.verifyDisplayedElements(new String[] {elements.unableToSendResetPassword},
//                             		   					  					  						    new String[] {elements.unableToSendResetPasswordText,
//                             		   					  					  						    			  elements.unableToSendResetPasswordHereLink},
//                             		   					  					  							true )*/;
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( registeredUserInPMAndHaveContact );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyReceiveForgotPasswordEmailWhenUserIsExistingInPMAndHaveContactInCRM() throws Exception {
//
//	  log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Existing' and there is a contact with that email address in Dynamics CRM a password reset email will still be sent" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//	                                                                            .fillEmailInputField( registeredUserInPMExistingAndContact, true )
//	                                                                            .action( "reset my password" );
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( registeredUserInPMExistingAndContact )
//															          			.openEmailCampaign( elements.resetYourPasswordEmail )
//															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//	          														  
//															          			verifyDisplayedElements( new String[] {elements.resetYourPasswordTextResetPage},
//                                         		   					  					  				 new String[] {elements.password,
//                                         		   					  					  						 	   elements.confirmPassword},
//                                         		   					  					  				 true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//    
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyReceiveForgotPasswordEmailWhenUserIsOnlyInPMWithStatusExisting() throws Exception {
//
//      log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Existing' but there is no lead or contact with that email address in Dynamics CRM a password reset email will still be sent??? no matter what Use Leads is set to" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//                                                                               .fillEmailInputField( registeredUserInPMExistingStatus, true )
//                                                                               .action( "reset my password" )
//                                                                               /*.verifyDisplayedElements(new String[] {elements.closeInforMessageInvalidEmailForgotPass},
//                             		   					  					  						    new String[] {elements.emptyEmailField},
//                             		   					  					  							true )*/;
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( registeredUserInPMExistingStatus );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithNotRegisteredUserPasswordEnabled() throws Exception {
//          	   	
//    	log.startTest( "Verify that the error messages connected with Login problems appears and is prescriptive enough" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true )
//            																	 .fillPasswordInputField( "qweqwe12", true)
//                                                                                 .action( "submit" )
//                                                                                 .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword }, 
//                                                                                		  				   new String[] { elements.closeInforMessage }, 
//                                                                                		  				   true );
//          } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithSubscribedLeadWithUseLeadsSetToNo() throws Exception {
//          	   	
//    	log.startTest( "Verify that a subscriber with a lead will be handled when they try to login while Use Leads is set to No" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "subscribedLead@mailinator.com", true )
//            																	 .fillPasswordInputField( registeredSubscriberPassword, true)
//                                                                                 .action( "submit" )
//                                                                                 .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword }, 
//                                                                                		  				   new String[] { elements.closeInforMessage }, 
//                                                                                		  				   true );
//                                                                                  
//                                                                                  
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithSubscribedContactDeletedFromCRMPasswordEnabled() throws Exception {
//          	   	
//    	log.startTest( "Verify error message when the user is not found in the CRM" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "deletedInCRM@mailinator.com", true )
//            																	 .fillPasswordInputField( registeredSubscriberPassword, true)
//                                                                                 .action( "submit" )
//                                                                                 .verifyDisplayedElements( new String[] { elements.invalidEmailOrPassword }, 
//                                                                                		  				   new String[] { elements.closeInforMessage }, 
//                                                                                		  				   true );
//                                                                                  
//                                                                                  
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyResetPasswordWhenPasswordResetLinkHasExpired() throws Exception {
//     
//      log.startTest( "Verify error message if you try to use the password reset link after it has expired" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//           																		.fillEmailInputField( registeredSubscriberEmailPassEnabled, true )
//	                                                                            .action( "reset my password" );
//           																		Thread.sleep(900010);
//           																		           																		           
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( registeredSubscriberEmailPassEnabled )
//	          																	.openEmailCampaign( elements.resetYourPasswordEmail )
//															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//	          														  
//														verifyDisplayedElements( new String[] {elements.invalidTokenResetPassword},
//                                         		   					  			 new String[] {elements.invalidTokenResetPassword},
//                                         		   					  			 true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyConfirmSignUpDoubleOptLinkDontHaveExpirationPeriod() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "password" + randNumber;
//      
//      log.startTest( "Verify error message if you try to use the double opt in after the link has expired" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField(firstName, false)
//          																		.fillLastNameInputField(lastName, false)
//          																		.fillCompanyInputFieldSignUp(company, false)
//          																		.fillPasswordInputField(password, false)
//          																		.fillConfirmPasswordInputField(password, false)
//                                                                                .action( "register new contact" );
//           																	   Thread.sleep(900000);
//		           
//           															 mailinator.accessMailinator()
//           															 		   .searchCustomerEmail( notRegisteredEmail )
//           															 		   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//           															 		   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//			  
//           												verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//																			 	 new String[] {elements.confirmedRegistrationMessage},
//																			 	 true );
//                   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDisplayCompanyFieldInSignUpPage() throws Exception {
//               
//      log.startTest( "Verify that the company field is supported in the sign up page" );
//      try {
//
//      	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//      																		  .verifyDisplayedElements(new String[]{ elements.firstName,
//                                                                                        						     elements.lastName },
//      																				  				   new String[]{ elements.companySignUpNoPass,
//      																				  						   		 elements.email},
//      																				  								 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyRegisterSubscriberWhenWelcomeEmailIsDeleted() throws Exception {
//  	
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//      String firstName = "firstName" + randNumber;
//      String lastName = "lastName" + randNumber;
//      String company = "company" + randNumber;
//
//      log.startTest( "Verify that the welcome email is not mandatory for registering a subscriber and creating a lead/contact" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//                                                                               .fillEmailInputField( notRegisteredEmail,false )
//                                                                               .fillFirstNameInputField(firstName, false)
//                                                                               .fillLastNameInputField(lastName, false)
//                                                                               .fillCompanyInputFieldSignUp(company, false)
//                                                                               .action( "register new contact" );
//
//			 														mailinator.accessMailinator()
//			 																  .searchCustomerEmail( notRegisteredEmail )
//			 																  .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//			 																  .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//			 														
//			 																	verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//			 																							 new String[] {elements.confirmedRegistrationMessage},
//			 																							 true );
//                    
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySentResetPasswordEmailForLeadExistingOnlyInCRM() throws Exception {
//
//      log.startTest( "Verify that if a subscriber isn't registered but a lead exists with that email address in the CRM, a reset password email won't be sent if Use Leads is set to No and the subscriber won't be added" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten Password" )
//                                                                               .fillEmailInputField( "onlyLead@mailinator.com", true )
//                                                                               .action( "reset my password" );
//          															 mailinator.accessMailinator()
//          																	   .searchCustomerEmail( "onlyLead@mailinator.com" );
//          																		verifyDisplayedElements( new String[] {elements.publicInbox},
//                             		   					  					  							 new String[] {elements.emptyInbox},
//                             		   					  					  							 true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullyLoginWithInactiveContactPasswordEnabled() throws Exception {
//
//      log.startTest( "Verify that a subscriber can't login if it's contact is inactive in Dynamics CRM" );
//      try {
//
//    	   accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( "k_inactiveContact@mailinator.com", true)
//    	  																	    .fillPasswordInputField( registeredSubscriberPassword, true )
//    	  																		.action( "submit" )
//    	  																		.verifyDisplayedElements( new String[] { elements.invalidEmail}, 
//    	  																								  new String[] { elements.wrongCredentialsLogin }, 
//    	  																								  true );
//          
//          																	   
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyDispalySignUpPageWithoutPreferences() throws Exception {
//   	    	
//  	log.startTest( "Verify that for the Sign up template, on the first page there is only contact information while on the second there are only preferences" );
//      try {
//      	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//      	 																	  .verifyDisplayedElements(new String[] {elements.email,
//      	 																			  								 elements.firstName,
//      	 																			  								 elements.lastName},
//																						  			   new String[] {elements.company,
//																						  					   		 elements.password,
//																						  					   		 elements.confirmPassword},
//																						  			   true )
//      	 																	  .verifyDisplayedElements( new String[] {elements.aoiTax},
//																	  									new String[] {elements.aoiEnvironment},
//																	  									false )
//      	 																	  .verifyDisplayedElements( new String[] {elements.aoiInternationalLegislation},
//																	  									new String[] {elements.aoiCapitalMarkets},
//																	  									false );
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//     
//    
//  @Test(groups = { "all-tests" })
//  public void successfullySignUpOnlyAfterClickSaveButtonDoubleOptInEmailIsSent() throws Exception {
//	  
//	    String randNumber = driver.getTimestamp();
//	    String notRegisteredEmail = randNumber + "email@mailinator.com";
//	    String firstName = "firstName" + randNumber;
//	    String lastName = "lastName" + randNumber;
//		String company = "company" + randNumber;
//		String password = "password" + randNumber;
//  
//		log.startTest( "Verify that for the Sign up template only when the user clicks the save button and all the required information is there, will the subscriber be put into the DB and the double opt-in email will be sent" );
//		try {
//
//			 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//																				  .fillEmailInputField( notRegisteredEmail, false )
//																				  .fillFirstNameInputField(firstName, false)
//																				  .fillLastNameInputField(lastName, false)
//																				  .fillCompanyInputFieldSignUp(company, false)
//																				  .fillPasswordInputField(password, false)
//																				  .fillConfirmPasswordInputField(password, false);
//			 
//																		mailinator.accessMailinator()
//            														   			  .searchCustomerEmail( notRegisteredEmail );
//            														   			   verifyDisplayedElements( new String[] {elements.publicInbox},
//            														   					   					new String[] {elements.emptyInbox},
//            														   					   					true );
//																				  action( "register new contact" );
//
//      																   mailinator.accessMailinator()
//      																		  	 .searchCustomerEmail( notRegisteredEmail )
//      																		     .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//      																		     .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//
//      													verifyDisplayedElements( new String[] {elements.almostThere},
//      																			 new String[] {elements.confirmedRegistrationMessage,
//      																					       elements.loginBtnConfirmedRegistration},
//      																			 			   true )
//      																			.action( "login confirmed email" )
//      																			.fillEmailInputField( notRegisteredEmail, true )
//          																	    .fillPasswordInputField(password, true)
//                                                                                .action( "submit" )
//    	  																		.verifyDisplayedElements( new String[] { elements.firstName}, 
//    	  																								  new String[] { elements.lastName }, 
//    	  																								  true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyAskForResetPasswordWhenSignUpWithExistingInCRMContactPasswordEnabled() throws Exception {
//	  
//	    String randNumber = driver.getTimestamp();
//	    String firstName = "firstName" + randNumber;
//	    String lastName = "lastName" + randNumber;
//		String company = "company" + randNumber;
//		String password = "password" + randNumber;
//  
//		log.startTest( "Verify that if a subscriber tries to sign up with an existing contact, PM will ask them to reset their password" );
//		try {
//
//			 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//																				  .fillEmailInputField( onlyContactEmail, false )
//																				  .fillFirstNameInputField(firstName, false)
//																				  .fillLastNameInputField(lastName, false)
//																				  .fillCompanyInputFieldSignUp(company, false)
//																				  .fillPasswordInputField(password, false)
//																				  .fillConfirmPasswordInputField(password, false)
//																				  .action( "register new contact" )
//																				  .verifyDisplayedElements( new String[] {elements.unableToSignUp},
//                                                            		   					  					new String[] {elements.errorMsgCantSignUp },
//                                                            		   					  					true )
//																				  .verifyDisplayedElements( new String[] { elements.closeErrorMessageCantSignUp }, 
//																							 				new String[] { elements.forgottenPasswordHereBtn }, 
//																							 				true );
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//
//  @Test(groups = { "all-tests" })
//  public void successfullyAskForResetPasswordWhenSignUpWithEmailOfDuplicateContact() throws Exception {
//	  
//	    String randNumber = driver.getTimestamp();
//	    String firstName = "firstName" + randNumber;
//	    String lastName = "lastName" + randNumber;
//		String company = "company" + randNumber;
//		String password = "password" + randNumber;
//  
//		log.startTest( "Verify that if a subscriber tries to register with the email of a duplicate contact, they will be asked to recover their password" );
//		try {
//
//			 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//																				  .fillEmailInputField( duplicateContactEmail, false )
//																				  .fillFirstNameInputField(firstName, false)
//																				  .fillLastNameInputField(lastName, false)
//																				  .fillCompanyInputFieldSignUp(company, false)
//																				  .fillPasswordInputField(password, false)
//																				  .fillConfirmPasswordInputField(password, false)
//																				  .action( "register new contact" )
//																				  .verifyDisplayedElements( new String[] {elements.unableToSignUp},
//                                          		   					  										new String[] {elements.errorMsgCantSignUp },
//                                          		   					  										true )
//																				  .verifyDisplayedElements( new String[] { elements.closeErrorMessageCantSignUp }, 
//																			 								new String[] { elements.forgottenPasswordHereBtn }, 
//																			 								true )
//																				  .action( "forgotten password error message" );
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullySeeAndChangeCompanyFieldForRegisteredContact() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//      String notRegisteredEmail = randNumber + "email@mailinator.com";
//  	  String firstName = "firstName" + randNumber;
//	  String lastName = "lastName" + randNumber;
//	  String company = "company" + randNumber;
//	  String password = "qwerty12";
//	        
//      log.startTest( "Verify that a registered subscriber that is a contact can login and see his/her company field and change it" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//          																		.fillEmailInputField( notRegisteredEmail, false )
//          																		.fillFirstNameInputField( firstName, false )
//          																		.fillLastNameInputField( lastName, false )
//          																		.fillCompanyInputFieldSignUp( company, false )
//          																		.fillPasswordInputField( password, false )
//          																		.fillConfirmPasswordInputField( password, false )
//                                                                                .action( "register new contact" );
//           
//														             mailinator.accessMailinator()
//														          			   .searchCustomerEmail( notRegisteredEmail )
//														          			   .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//														          			   .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//														             
//														             verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//                                               		   					  					  new String[] {elements.confirmedRegistrationMessage},
//                                               		   					  					  true )
//														             			.action( "login confirmed email" )
//	                                                                            .fillEmailInputField( notRegisteredEmail, true )
//	                                                                            .fillPasswordInputField( password, true )
//	                                                                            .action( "submit" )
//	                                                                            .fillCompanyInputField( "newValueCompany", true )
//	                                                                            .fillJobTitleInputField( "newValuejobTitle", true )
//	                                                                            .fillBusinessPhoneInputField( "newValuebusinessPhone", true )
//	                                                                            .action( "update" );
//		  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField(notRegisteredEmail, true)
//	          														  		   .fillPasswordInputField(password, true)
//	          														  		   .action( "submit" )
//	          														  		   .verifySubscriberDetail(elements.company, "newValueCompany", true)
//	          														  		   .verifySubscriberDetail(elements.jobTitle, "newValuejobTitle", true)
//	          														  		   .verifySubscriberDetail(elements.businessPhone, "newValuebusinessPhone", true);
//		  					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySentResetPasswordEmailForSubscriberExistingOnlyInPMDBWithStatusConfirmed() throws Exception {
//     
//
//      log.startTest( "Verify that if a subscriber exists in PM's DB and has status 'Confirmed' but there is no lead or contact with that email address in Dynamics CRM a password reset email will NOT be sent no matter what Use Leads is set to" );
//      try {
//
//          accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password" )
//                                                                               .fillEmailInputField( emailOnlyInPMConfirmed, true )
//                                                                               .action( "reset my password" )
//                                                                               /*.verifyDisplayedElements(new String[] {elements.unableToSendResetPassword},
//            		   					  					  						    				new String[] {elements.unableToSendResetPasswordText,
//            		   					  					  						    							  elements.unableToSendResetPasswordHereLink},
//            		   					  					  						    				true )*/;
//          														   mailinator.accessMailinator()
//          														   			  .searchCustomerEmail( emailOnlyInPMConfirmed );
//          														   			   verifyDisplayedElements( new String[] {elements.publicInbox},
//          														   					   					new String[] {elements.emptyInbox},
//          														   					   					true );
//
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void unsuccessfullySentResetPasswordEmailAndResetPasswordForDuplicateLeadsExistingOnlyInCRM() throws Exception {
//     	  
//	  log.startTest( "Verify that if duplicate leads exist in the CRM user is NOT able to request a password reset and successfully reset their password no matter what Use Leads is set to" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password" )
//	                                                                            .fillEmailInputField( duplicateLeadEmail, true )
//	                                                                            .action( "reset my password" );
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( duplicateLeadEmail );
//	          																	verifyDisplayedElements( new String[] {elements.publicInbox},
//												   					   									 new String[] {elements.emptyInbox},
//												   					   									 true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  }
//  
//  @Test(groups = { "all-tests" })
//  public void successfullySentResetPasswordEmailAndResetPasswordForDuplicateContactsExistingOnlyInCRM() throws Exception {
//
//      String randNumber = driver.getTimestamp();
//	  String newPassword = "newPassword" + randNumber;
//	  
//	  //Test uses only Use Leads = No:
//      log.startTest( "Verify that if duplicate contacts exist in the CRM a subscriber is able to request a password reset and successfully reset their password no matter what Use Leads is set to" );
//      try {
//
//           accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "forgotten password" )
//	                                                                            .fillEmailInputField( duplicateContactEmail, true )
//	                                                                            .action( "reset my password" );
//	          														  mailinator.accessMailinator()
//	          																	.searchCustomerEmail( duplicateContactEmail )
//															          			.openEmailCampaign( elements.resetYourPasswordEmail )
//															          			.clickLinkFromEmailContentPM( elements.clickOnResetPasswordEmail );
//	          														  
//															          			verifyDisplayedElements( new String[] {elements.resetYourPasswordTextResetPage},
//                                         		   					  					  				 new String[] {elements.password,
//                                         		   					  					  						 	   elements.confirmPassword},
//                                         		   					  					  				 true )
//															          			.fillPasswordInputField(newPassword, false)
//															          			.fillConfirmPasswordInputField(newPassword, false)
//															          			.action("save reset password")
//															          			.verifyDisplayedElements(new String[] {elements.sucessfullyResetPassword},
//                                         		   					  					  				 new String[] {elements.backToLoginBtn},
//                                         		   					  					  				 true )
//															          			.action( "back" )
//	          																    .fillEmailInputField(duplicateContactEmail, true)
//	      																		.fillPasswordInputField(newPassword, true)
//	      																		.action( "submit" )
//	      																		.verifyDisplayedElements(new String[] {elements.firstNamePreferencePage},
//                                         		   					  					  				 new String[] {elements.lastNamePreferencePage,
//                                         		   					  					  						 	   elements.businessPhone,
//                                         		   					  					  						 	   elements.jobTitle},
//                                         		   					  					  				 true );
//															          			
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  } 
//  
//  @Test(groups = { "all-tests" })
//  public void successfullyAccessDynamics() throws Exception {
//   	 
//      log.startTest( "Verify can access Dynamics and it's menus successful" );
//      try {
//    	  			dynamics.accessDynamics().generateClose();
//    	  			
//    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
//					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
//					  				        true );
//					
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  } 
//  
// @Test(groups = { "all-tests" })
//  public void successfullySearchAndFindRegisteredContact() throws Exception {
//   	  
//      log.startTest( "Verify can access and search in Contact menu" );
//      try {
//    	  			dynamics.accessDynamics().generateClose();
//    	  			
//    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
//					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
//					  				        true );
//    	  			dynamics.actionDynamics( "sales" )
//    	  					.actionDynamics( "contacts" );
//    	  			dynamics.searchCustomerEmail( "inPMandContact@mailinator.com" )
//    	  					.verifyDisplayedContact( "inPMandContact@mailinator.com" );
//    	  	   	  				
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  } 
//  
//  @Test(groups = { "all-tests" })
//  public void verifyUpdatedCorrectContactInformationAndAoi() throws Exception {
//   	  
//      log.startTest( "Verify Contact names and Areas of Interests are updated correct" );
//      try {
//    	    dynamics.accessDynamics().generateClose();
//    	  			
//    	  			verifyDisplayedElements(new String[] { elementsDynamics.salesMenu },
//					  				 	    new String[] { elementsDynamics.msDynamicsCRM },
//					  				        true );
//    	  	dynamics.actionDynamics( "sales" )
//    	  			.actionDynamics( "contacts" );
//    	  	dynamics.searchCustomerEmail( "inPMandContact@mailinator.com" )
//    	  			.verifyDisplayedContact( "inPMandContact@mailinator.com" )
//    	  			.openFoundContact()
//    	  			.verifyDisplayedElementsDynamics( "111 555" )
//    	  			.verifyDisplayedElementsInIFrame( new String[]{ elementsDynamics.capitalMarketsNo,
//    	  										   					elementsDynamics.realEstateNo},
//    	  							 				  new String[]{ elementsDynamics.taxNo,
//    	  							 						  		elementsDynamics.privateEquityNo},
//    	  							 				  true );
//    	  			    	  	   	  				
//      } catch( Exception e ) {
//
//          log.endStep( false );
//          throw e;
//      }
//
//      log.endTest();
//  } 
// 
}