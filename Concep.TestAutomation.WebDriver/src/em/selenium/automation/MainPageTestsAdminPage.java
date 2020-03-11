package em.selenium.automation;

import org.testng.annotations.Test;

public class MainPageTestsAdminPage extends BaseAdminPage {

//	
//    @Test(groups = { "all-tests" })
//    public void successfullyAccessPreferenceManagerMainPage() throws Exception {
//
//        log.startTest( "Verify that user can successfully access Preference Manager main page" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
//                                                                                                   registeredSubscriberPassword,
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.firstNamePreferencePage,
//                                                                                                                   		 elements.lastNamePreferencePage },
//                                                                                                           new String[]{ elements.company,
//                                                                                                        		   		 elements.jobTitle,
//                                                                                                        		   		 elements.businessPhone },
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
//    public void successfullyDisplayAllUiElementsSignUpPagePasswordEnabled() throws Exception {
//
//        log.startTest( "Verify that all UI elements from sign up page are succesfully displayed" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.email,
//                                                                                                                   		 elements.firstName},
//                                                                                                           new String[]{ elements.lastName,
//                                                                                                                   		 elements.companySignUpNoPass },
//                                                                                                           true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.password },
//                                                                                                           new String[]{ elements.confirmPassword },
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
//    
//    @Test(groups = { "all-tests" })
//    public void successfullyDisplayAllUiElementsLoginWithRegisteredUser() throws Exception {
//
//        log.startTest( "Verify that all UI elements from the Main page are succesfully displayed" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
//                                                                                                   registeredSubscriberPassword,
//                                                                                                   true )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.firstNamePreferencePage,
//                                                                                                                   		 elements.lastNamePreferencePage },
//                                                                                                           new String[]{ elements.jobTitle,
//                                                                                                                   		 elements.businessPhone},
//                                                                                                          true );
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
//    public void successfullyDisplayAllAreasOfInterest() throws Exception {
//    	    	
//    	log.startTest( "Verify that all Areas of Interest are successfully displayed" );
//        try {
//        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmail,
//                     																				registeredSubscriberPassword,
//                     																				true )
//																				  .verifyDisplayedElements( new String[] {elements.aoiTax},
//																						  					new String[] {elements.aoiGovernment},
//																						  					true )
//																				  .verifyDisplayedElements( new String[] {elements.aoiInternationalLegislation},
//																						  					new String[] {elements.aoiEnvironment},
//																						  					true )
//																				  .verifyDisplayedElements( new String[] {elements.aoiTechnology},
//																						  					new String[] {elements.aoiCapitalMarkets},
//																						  					true );
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
//    public void successfullyDisplayedEmptyFieldsJobTitleAndPhoneForNewUser() throws Exception {
//
//        log.startTest( "Verify that after accessing the main page with new subscriber email, first name, last name and company fields are populated, job title and business phone fields are empty" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( registeredSubscriberEmailNotUpdateUserData,
//																								   registeredSubscriberPassword,
//																								   true )
//                                                                                 /*.action( "submit" )*/
//                                                                                 .verifyDisplayedElements( new String[]{ elements.contactDetails },
//                                                                                                           new String[]{ elements.aoiText },
//                                                                                                           true )
//                                                                                 .verifySubscriberDetail( elements.firstNamePreferencePage,
//                                                                                                          "e",
//                                                                                                          true )
//                                                                                 .verifySubscriberDetail( elements.lastNamePreferencePage,
//                                                                                                          "w",
//                                                                                                          true )
//                                                                                 .verifySubscriberDetail( elements.company,
//                                                                                                          "q",
//                                                                                                          true )
//                                                                                 .verifySubscriberDetail( elements.jobTitle,
//                                                                                                          "",
//                                                                                                          true )
//                                                                                 .verifySubscriberDetail( elements.businessPhone,
//                                                                                                          "",
//                                                                                                          true );
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
//    public void successfullyDispalyValidationMessageAllRequiredFieldsEmpty() throws Exception {
//
//        log.startTest( "Verify when try to signUp a validation message is dispalyed when all the required fields are empty" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//            																     .action( "register" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.passwordRestrictions },
//                                                                       				   					   new String[]{ elements.closeInforMessageInvalidEmailForgotPass },
//                                                                       				   					   true );
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
//    public void successfullyDispalyValidationMessagePasswordAndConfirmPasswordDoesNotMatch() throws Exception {
//
//        String randNumber = driver.getTimestamp();
//
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String password = "qwerty12";
//        String confirmPassword = "qweqwe12";
//
//        log.startTest( "Verify that a validation message is dispalyed when Password and confirm Password does not match" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )                                                                      
//            																	 .fillEmailInputField( notRegisteredEmail,false )
//            																	 .fillFirstNameInputField(firstName, false)
//            																	 .fillLastNameInputField(lastName, false)
//            																	 .fillCompanyInputFieldSignUp(company, false)
//            																	 .fillPasswordInputField(password, false)
//            																	 .fillConfirmPasswordInputField(confirmPassword, false)
//            																	 .action( "register" )
//            																	 .verifyDisplayedElements( new String[]{ elements.passwordsNotMatch },
//            																			 				   new String[]{ elements.passwordsDoNotMatch },
//            																			 				   true );   
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
//    public void successfullyDispalyValidationMessageFirstNameFieldEmpty() throws Exception {
//    	
//    	String randNumber = driver.getTimestamp();
//   	 	String notRegisteredEmail = randNumber + "email@yopmail.com";
//        String firstName = "";
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String password = "password" + randNumber;
//                         
//        log.startTest( "Verify that a validation message is dispalyed when First Name input fields is empty" );
//        try {
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//        																		 .fillEmailInputField(notRegisteredEmail, false) 
//        																		 .fillFirstNameInputField(firstName, false)
//        																		 .fillLastNameInputField(lastName, false)
//        																		 .fillCompanyInputFieldSignUp(company, false)
//        																		 .fillPasswordInputField(password, false)
//        																		 .fillConfirmPasswordInputField(password, false)
//        																		 .action( "register" )
//        																		 .verifyDisplayedElements(new String[]{ elements.closeInforMessageInvalidEmailForgotPass },
//        																				  				  new String[]{ elements.emptyFieldsMessage },
//        																				  				  true );       
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
//    public void successfullyDispalyValidationMessageLastNameFieldEmpty() throws Exception {
//
//    	String randNumber = driver.getTimestamp();
//   	 	String notRegisteredEmail = randNumber + "email@yopmail.com";
//        String firstName = "firstName" + randNumber;
//        String lastName = "";
//        String company = "company" + randNumber;
//        String password = "password" + randNumber;
//
//        log.startTest( "Verify that a validation message is dispalyed when Last Name input fields is empty" );
//        try {
//
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//			 																	 .fillEmailInputField(notRegisteredEmail, false) 
//			 																	 .fillFirstNameInputField(firstName, false)
//			 																	 .fillLastNameInputField(lastName, false)
//			 																	 .fillCompanyInputFieldSignUp(company, false)
//			 																	 .fillPasswordInputField(password, false)
//			 																	 .fillConfirmPasswordInputField(password, false)
//			 																	 .action( "register" )
//			 																	 .verifyDisplayedElements(new String[]{ elements.closeInforMessageInvalidEmailForgotPass },
//																		  				  				  new String[]{ elements.emptyFieldsMessage },
//																		  				  				  true ); 
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
//    public void successfullyDispalyValidationMessageEmailFieldEmpty() throws Exception {
//
//    	String randNumber = driver.getTimestamp();
//   	 	String notRegisteredEmail = "";
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String password = "password" + randNumber;
//
//        log.startTest( "Verify that a validation message is dispalyed when Job Title input fields is empty" );
//        try {
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//			 																	 .fillEmailInputField(notRegisteredEmail, false) 
//			 																	 .fillFirstNameInputField(firstName, false)
//			 																	 .fillLastNameInputField(lastName, false)
//			 																	 .fillCompanyInputFieldSignUp(company, false)
//			 																	 .fillPasswordInputField(password, false)
//			 																	 .fillConfirmPasswordInputField(password, false)
//			 																	 .action( "register" )
//			 																	 .verifyDisplayedElements(new String[]{ elements.closeInforMessageInvalidEmailForgotPass },
//														  				  				  				  new String[]{ elements.emptyFieldsMessage },
//														  				  				  				  true ); 
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
//    public void successfullyDispalyValidationMessageCompanyFieldEmpty() throws Exception {
//
//        String randNumber = driver.getTimestamp();
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "";
//        String jobTitle = "jobTitle" + randNumber;
//        String businessPhone = "businessPhone" + randNumber;
//                 
//        log.startTest( "Verify that the company field is supported in the preference update page" );
//        try {
//
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( registeredSubscriberEmailPassDisabled, true )
//        																		  .action( "login pass disabled" )
//        																		  .fillFirstNameInputFieldPreferencePage(firstName, true)
//        																		  .fillLastNameInputFieldlPreferencePage(lastName, true)
//        																		  .fillCompanyInputField(company, true)
//        																		  .fillJobTitleInputField(jobTitle, true)
//        																		  .fillBusinessPhoneInputField(businessPhone, true)
//        																		  .action( "update" )
//        																		  .verifyDisplayedElements(new String[]{ elements.emptyFieldsPreferencePage },
//        																				  				   new String[]{ elements.closeInforMessageInvalidEmailForgotPass },
//        																				  				   true );
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
//    public void successfullyDispalyValidationMessagePasswordFieldEmpty() throws Exception {
//
//    	String randNumber = driver.getTimestamp();
//
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String password = "";
//        String confirmPassword = "qweqwe12";
//
//        log.startTest( "Verify that a validation message is dispalyed when Password and confirm Password does not match" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )                                                                      
//            																	 .fillEmailInputField( notRegisteredEmail,false )
//            																	 .fillFirstNameInputField(firstName, false)
//            																	 .fillLastNameInputField(lastName, false)
//            																	 .fillCompanyInputFieldSignUp(company, false)
//            																	 .fillPasswordInputField(password, false)
//            																	 .fillConfirmPasswordInputField(confirmPassword, false)
//            																	 .action( "register" )
//            																	 .verifyDisplayedElements( new String[]{ elements.passwordRestrictions },
//            																			 				   new String[]{ elements.passwordsDoNotMatch },
//            																			 				   true );   
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
//    public void successfullyDispalyValidationMessageConfirmPasswordFieldEmpty() throws Exception {
//
//    	 String randNumber = driver.getTimestamp();
//    	 String email = randNumber + "email@yopmail.com";
//         String firstName = "firstName" + randNumber;
//         String lastName = "lastName" + randNumber;
//         String company = "company" + randNumber;
//         String password = "password" + randNumber;
//         String confirmPassword = "";
//                  
//         log.startTest( "Verify that a validation message is dispalyed when Confirm Password input fields is empty" );
//         try {
//
//         	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//         																		 .fillEmailInputField(email, false) 
//         																		 .fillFirstNameInputField(firstName, false)
//         																		 .fillLastNameInputField(lastName, false)
//         																		 .fillCompanyInputFieldSignUp(company, false)
//         																		 .fillPasswordInputField(password, false)
//         																		 .fillConfirmPasswordInputField(confirmPassword, false)
//         																		 .action( "register" )
//         																		 .verifyDisplayedElements(new String[]{ elements.emptyFieldsMessage },
//         																				  				  new String[]{ elements.passwordsDoNotMatch },
//         																				  				  true );
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
//    public void successfullySignUpNewContactPassEnabled() throws Exception {
//    	
//        String randNumber = driver.getTimestamp();
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String password = "qwerty12";
//        String confirmPassword = "qwerty12";
//
//        log.startTest( "Verify that contact details can be successfully created" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//                                                                                 .fillEmailInputField( notRegisteredEmail,false )
//                                                                                 .fillFirstNameInputField(firstName, false)
//                                                                                 .fillLastNameInputField(lastName, false)
//                                                                                 .fillCompanyInputFieldSignUp(company, false)
//                                                                                 .fillPasswordInputField(password, false)
//                                                                                 .fillConfirmPasswordInputField(confirmPassword, false)
//                                                                                 .action( "register" )
//                                                                                 .verifyDisplayedElements( new String[] {elements.thankYouSigningUp},
//                                                           		   					  					   new String[] {elements.checkYourEmail,
//                                                           		   					  							         elements.almostThere},
//                                                           		   					  					   true );                                                                             
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
//    public void successfullyDisplayValidationMessagePasswordRequirements() throws Exception {
//
//        String randNumber = driver.getTimestamp();
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String password = "a";
//        
//        log.startTest( "Verify that a validation message is successfully displayed when the password doesn't meet the requirements" );
//        try {
//
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//			 																	 .fillEmailInputField( notRegisteredEmail, false )
//			 																	 .fillFirstNameInputField(firstName, false)
//			 																	 .fillLastNameInputField(lastName, false)
//			 																	 .fillCompanyInputFieldSignUp(company, false)
//			 																	 .fillPasswordInputField(password, false)
//			 																	 .fillConfirmPasswordInputField(password, false)
//			 																	 .action( "register" )
//			 																	 .verifyDisplayedElements( new String[] { elements.passwordRestrictions }, 
//			 																			 				   new String[] { elements.passwordRequirements }, 
//			 																			 				   true );
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
//    public void successfullyDispalyValidationMessagePasswordNotMatch() throws Exception {
//            
//        String randNumber = driver.getTimestamp();
//   	 	String notRegisteredEmail = randNumber + "email@yopmail.com";
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String password = "password" + randNumber;
//        String confirmPassword = "qwerty12";
//                 
//        log.startTest( "Verify that a validation message is dispalyed when passwords not match" );
//        try {
//
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//        																		 .fillEmailInputField(notRegisteredEmail, false) 
//        																		 .fillFirstNameInputField(firstName, false)
//        																		 .fillLastNameInputField(lastName, false)
//        																		 .fillCompanyInputFieldSignUp(company, false)
//        																		 .fillPasswordInputField(password, false)
//        																		 .fillConfirmPasswordInputField(confirmPassword, false)
//        																		 .action( "register" )
//        																		 .verifyDisplayedElements(new String[]{ elements.passwordsDoNotMatch },
//        																				  				  new String[]{ elements.passwordRestrictions },
//        																				  				  true );
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//
//    /*@Test(groups = { "all-tests" })
//    public void successfullySubscribeToGroupsNewUserAfterUpdating() throws Exception {
//
//        String randNumber = driver.getTimestamp();
//
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String jobTitle = "jobTitle" + randNumber;
//        String company = "company" + randNumber;
//        String password = "Aa111111!";
//        String confirmPassword = "Aa111111!";
//
//        log.startTest( "[Manual]Verify that user is able to subscribe to groups from the Managing Mailing Preferences page after updating" );
//        try {
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail,
//                                                                                                       false )
//                                                                                 .action( "Register" )
//                                                                                 .updateSubscriberDetail( "First Name",
//                                                                                                          firstName )
//                                                                                 .updateSubscriberDetail( "Last Name",
//                                                                                                          lastName )
//                                                                                 .updateSubscriberDetail( "Job Title",
//                                                                                                          jobTitle )
//                                                                                 .updateSubscriberDetail( "Company",
//                                                                                                          company )
//                                                                                 .updateSubscriberDetail( "Password",
//                                                                                                          password )
//                                                                                 .updateSubscriberDetail( "Confirm Password",
//                                                                                                          confirmPassword )
//                                                                                 .action( "Save" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textSuccessfullyUpdateContactDetails ) },
//                                                                                                           new String[]{ elements.textSuccessfullyUpdateContactDetails },
//                                                                                                           true )
//                                                                                 .switchTab( "Managing Mailing Preferences" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.mainHeadingManagingMailingPreferences,
//                                                                                                                   elements.getInstructionMessage( elements.textInstructionManagingMailingPreferences ) },
//                                                                                                           new String[]{ elements.textMailingPreferencesMainHeading,
//                                                                                                                   elements.textInstructionManagingMailingPreferences },
//                                                                                                           true )
//                                                                                 .manageMailingPreferences( true,
//                                                                                                            allGroups )
//                                                                                 .action( "Save" )
//                                                                                 .verifyDisplayedElements( new String[]{ elements.getSuccessMessage( elements.textUpdateMailingPreferences ) },
//                                                                                                           new String[]{ elements.textUpdateMailingPreferences },
//                                                                                                           true );
//
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).loginSubscriber( notRegisteredEmail,
//                                                                                                   password,
//                                                                                                   true )
//                                                                                 .switchTab( "Managing Mailing Preferences" )
//                                                                                 .verifySubscriberMailingPreferences( allGroups,
//                                                                                                                      true );
//
//            // TODO: Method for verifying that subscriber is linked to the relevant folders in IA
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }*/
//       
//    @Test(groups = { "deni" })
//    public void successfullyUpdateContactDetailsAndAreasOfInterestExistingSubscriberPasswordEnabled() throws Exception {
//
//    	String randNumber = driver.getTimestamp();
//    	//String notRegisteredEmail = randNumber + "@concepdev.com";qaconcep+111@gmail.com
//    	String notRegisteredEmail = "qaconcep+" +  randNumber + "@gmail.com";
//    	String firstName = "firstName" + randNumber;
//  	  	String lastName = "lastName" + randNumber;
//  	  	String company = "company" + randNumber;
//  	  	String password = "password12";
//    	String firstNameNew = "firstName";
//        String lastNameNew = "lastName";
//        String companyNew = "company";
//        String jobTitle = "jobTitle";
//        String businessPhone = "businessPhone";
//
//        log.startTest( "Verify that an existing contact can successfully update his details" );
//        try {
//
//        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//			  																	  .fillEmailInputField( notRegisteredEmail, false )
//			  																	  .fillFirstNameInputField(firstName, false)
//			  																	  .fillLastNameInputField(lastName, false)
//			  																	  .fillCompanyInputFieldSignUp(company, false)
//			  																	  .fillPasswordInputField(password, false)
//			  																	  .fillConfirmPasswordInputField(password, false);
//			  																	  driver.click(elements.registerBtn, driver.timeOut);
//			  																	  Thread.sleep(200);
//			  																	  
//			  																	  gmail.accessGmail()
//			  															     	  .fillEmailToLogin()
//			  															          .logInGmail()
//			  															     	  .goToUnread()
//			  															     	  .openConfirmationEmail()
//			  															     	  .generateClickConfirmationEmail();
//			  															     	   Thread.sleep(200);
//			  															           
//			  															           
//			  										   verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//			  																	new String[] {elements.confirmedRegistrationMessage},
//			  																	true )
//			  										   							  .action( "login confirmed email" )
//			  										   							  .fillEmailInputField(notRegisteredEmail, true)
//			  										   							  .fillPasswordInputField(password, true)
//			  										   							  .action( "login main page" )
//			  										   							  .fillFirstNameInputFieldPreferencePage(firstNameNew, true)
//			  										   							  .fillLastNameInputFieldlPreferencePage(lastNameNew, true)
//			  										   							  .fillCompanyInputField(companyNew, true)
//			  										   							  .fillJobTitleInputField(jobTitle, true)
//			  										   							  .fillBusinessPhoneInputField(businessPhone, true)
//			  										   							  .action( "tax" )
//			  																	  .action( "capital markets" )
//			  										   							  .action( "update" )
//			  										   							  .verifyDisplayedElements(new String[]{ elements.textSuccessfullyUpdateContactDetails },
//			  										   									  				   new String[]{ elements.successfullyUpdateContactHereText },
//			  										   									  				   true );
//        	  accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail, true )
//        	 																	   .fillPasswordInputField( password, true )
//        	 																	   .action( "submit" ) 
//        	 																	   .verifySubscriberDetail( elements.firstNamePreferencePage,
//        	 																			   					"firstName",
//        	 																			   					true )
//        	 																	   .verifySubscriberDetail( elements.lastNamePreferencePage,
//        	 																			   					"lastName",
//        	 																			   					true )
//        	 																	   .verifySubscriberDetail( elements.company,
//        	 																			   					"company",
//        	 																			   					true )
//        	 																	   .verifySubscriberDetail( elements.jobTitle,
//        	 																			   					"jobTitle",
//        	 																			   					true )
//        	 																	   .verifySubscriberDetail( elements.businessPhone,
//        	 																			   					"businessPhone",
//        	 																			   					true )
//        	 																	   .verifyDisplayedElements( new String[]{ elements.taxMLTrue},
//        	 																			   					 new String[]{ elements.capitalmarketsMLTrue,
//        	 																			   							 	   elements.technologyMLFalse},
//        	 																			   					 true )
//        	 																	   .verifyDisplayedElements( new String[]{ elements.internationalLegislationMLFalse},
//																		   					 				 new String[]{ elements.environmentMLFalse,
//																		   					 						 	   elements.governmentMLFalse},
//																		   					 				 true );
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
//    public void successfullyDisplayValidationMessageNotValidEmailPersonalDetails() throws Exception {
//
//    	String randNumber = driver.getTimestamp();
//    	String firstName = "firstName" + randNumber;
//    	String lastName = "lastName" + randNumber;
//    	String company = "company" + randNumber;
//    	String password = "password" + randNumber;
//    	    	
//        log.startTest( "Verify that a validation message is successfully displayed, when user is trying to enter email with an invalid value" );
//        try {
//        		accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//        																			 .fillEmailInputField( "onlyInPMEmail@", false )
//        																			 .fillFirstNameInputField(firstName, false)
//        																			 .fillLastNameInputField(lastName, false)
//        																			 .fillCompanyInputFieldSignUp(company, false)
//        																			 .fillPasswordInputField(password, false)
//        																			 .fillConfirmPasswordInputField(password, false)
//        																			 .action( "register" )
//        																			 .verifyDisplayedElements( new String[] { elements.emptyEmailField }, 
//			  																			  					   new String[] { elements.closeInforMessageInvalidEmailForgotPass }, 
//			  																			  					   true );
//
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
//    //Till here 21.03 evening:
//    @Test(groups = { "all-tests" })
//    public void successfullyUpdateContactDetailsAndAreasOfInterestAtTheSameTime() throws Exception {
//    	
//    	String randNumber = driver.getTimestamp();
//    	String notRegisteredEmail = randNumber + "@mailinator.com";
//    	String firstName = "firstName" + randNumber;
//  	  	String lastName = "lastName" + randNumber;
//  	  	String company = "company" + randNumber;
//    	String firstNameNew = "firstName";
//        String lastNameNew = "lastName";
//        String companyNew = "company";
//        String jobTitle = "jobTitle";
//        String businessPhone = "businessPhone";
//        
//        log.startTest( "Verify that an existing contact can successfully update his details and areas of interest at the same time" );
//        try {
//        	
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//																				  .fillEmailInputField( notRegisteredEmail, false )
//																				  .fillFirstNameInputField(firstName, false)
//																				  .fillLastNameInputField(lastName, false)
//																				  .fillCompanyInputFieldSignUp(company, false)
//																				  .action( "register new contact" );
//        																		  Thread.sleep(200);
//        																mailinator.accessMailinator()
//        																		  .searchCustomerEmail( notRegisteredEmail )
//        																		  .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//        																		  .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//            
//        																verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//        																						 new String[] {elements.confirmedRegistrationMessage},
//        																						 true )
//        																		  .action( "login confirmed email" )
//        																		  .fillEmailInputField( notRegisteredEmail, true )
//        																		  .action( "login pass disabled" )
//        																		  .fillFirstNameInputFieldPreferencePage(firstNameNew, true)
//        																		  .fillLastNameInputFieldlPreferencePage(lastNameNew, true)
//        																		  .fillCompanyInputField(companyNew, true)
//        																		  .fillJobTitleInputField(jobTitle, true)
//        																		  .fillBusinessPhoneInputField(businessPhone, true)
//        																		  .action( "international legislation" )
//        																		  .action( "government" )
//        																		  .action( "update" )
//        																		  .verifyDisplayedElements(new String[]{ elements.textSuccessfullyUpdateContactDetails },
//																			  				   			   new String[]{ elements.successfullyUpdateContactHereText },
//																			  				   			   true );
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( notRegisteredEmail, true )
//                                                                                  .action( "login pass disabled" ) 
//                                                                                  .verifySubscriberDetail( elements.firstNamePreferencePage,
//																		 				  "firstName",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.lastNamePreferencePage,
//																		 				  "lastName",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.company,
//																		 				  "company",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.jobTitle,
//																		 				  "jobTitle",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.businessPhone,
//																		 				 "businessPhone",
//																		 				 true )
//                                                                                  .verifyDisplayedElements( new String[]{ elements.internationalLegislationMLTrue},
//                                                                                		  					new String[]{ elements.governmentMLTrue},
//                                                                                		  					true );
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
//    public void unsuccessfullyUpdateContactDetailsSaveButtonNotClicked() throws Exception {
//
//        String randNumber = driver.getTimestamp();
//        
//        String firstName = "firstName" + randNumber;
//        String lastName = "lastName" + randNumber;
//        String company = "company" + randNumber;
//        String jobTitle = "jobTitle" + randNumber;
//        String businessPhone = "businessPhone" + randNumber;
//      
//        log.startTest( "Verify that subscriber details are not updated when Save button is not clicked" );
//        try {
//        	
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmailNotUpdateUserData, true)
//			 																	 .fillPasswordInputField(registeredSubscriberPassword, true)
//			 																	 .action( "submit" )
//			 																	 .fillFirstNameInputFieldPreferencePage(firstName, true)
//			 																	 .fillLastNameInputFieldlPreferencePage(lastName, true)
//			 																	 .fillCompanyInputField(company, true)
//			 																	 .fillJobTitleInputField(jobTitle, true)
//			 																	 .fillBusinessPhoneInputField(businessPhone, true);	 	        	
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmailNotUpdateUserData, true)
//        																		 .fillPasswordInputField(registeredSubscriberPassword, true)
//        																		 .action( "submit" )
//        																		  .verifyDisplayedElements( new String[]{ elements.contactDetails },
//        																					 				new String[]{ elements.aoiText },
//        																					 				true )
//        																			 .verifySubscriberDetail( elements.firstNamePreferencePage,
//        																					 				  "kkk",
//        																					 				  true )
//        																			 .verifySubscriberDetail( elements.lastNamePreferencePage,
//        																					 				  "bbb",
//        																					 				  true )
//        																			 .verifySubscriberDetail( elements.company,
//        																					 				  "nnn",
//        																					 				  true )
//        																			 .verifySubscriberDetail( elements.jobTitle,
//        																					 				  "",
//        																					 				  true )
//        																			 .verifySubscriberDetail( elements.businessPhone,
//        																					 				 "",
//        																					 				 true );
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
//    public void unsuccessfullyUpdateAreasOfInterestSaveButtonNotClicked() throws Exception {
//
//        String randNumber = driver.getTimestamp();
//        String jobTitle = "jobTitle" + randNumber;
//        String businessPhone = "businessPhone" + randNumber;
//
//        log.startTest( "Verify that subscriber areas of interest are not updated when Save button is not clicked" );
//        try {
//
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
//			 																	 .fillPasswordInputField(registeredSubscriberPassword, true)
//			 																	 .action( "submit" )
//			 																	 .fillJobTitleInputField(jobTitle, true)
//			 																	 .fillBusinessPhoneInputField(businessPhone, true)	 
//			 																	 .action( "technology" )
//			 																	 .action( "tax" );
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( registeredSubscriberEmail, true)
//			 																	 .fillPasswordInputField(registeredSubscriberPassword, true)
//			 																	 .action( "submit" )
//			 																	 .verifyDisplayedElements( new String[]{ elements.technologyMLFalse},
//			 																			 				   new String[]{ elements.taxMLFalse},
//			 																			 				   true );
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
//    //to edit all tests till the end:
//   @Test(groups = { "all-tests" })
//    public void successfullyUpdateContactDetailsAndMarketingListsPasswordDisabled() throws Exception {
//
//	    String randNumber = driver.getTimestamp();
//   		String notRegisteredEmail = randNumber + "@mailinator.com";
//   		String firstName = "firstName" + randNumber;
// 	  	String lastName = "lastName" + randNumber;
// 	  	String company = "company" + randNumber;
//    	String firstNameNew = "firstName1";
//        String lastNameNew = "lastName1";
//        String companyNew = "company1";
//        String jobTitle = "jobTitle";
//        String businessPhone = "businessPhone";
//   
//        log.startTest( "Verify that an existing contact can successfully update his details and Marketing lists at the same time" );
//        try {
//
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//        																		  .fillEmailInputField( notRegisteredEmail, false )
//        																		  .fillFirstNameInputField(firstName, false)
//        																		  .fillLastNameInputField(lastName, false)
//        																		  .fillCompanyInputFieldSignUp(company, false)
//        																		  .action( "register new contact" );
//        																		  Thread.sleep(200);
//        															    mailinator.accessMailinator()
//        															    		  .searchCustomerEmail( notRegisteredEmail )
//        															    		  .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//        															    		  .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//
//        													verifyDisplayedElements( new String[] {elements.loginBtnConfirmedRegistration},
//        																			 new String[] {elements.confirmedRegistrationMessage},
//        																			 true )
//        																		  .action( "login confirmed email" )
//        																		  .fillEmailInputField( notRegisteredEmail, true )
//        																		  .action( "login pass disabled" )
//        																		  .fillFirstNameInputFieldPreferencePage(firstNameNew, true)
//        																		  .fillLastNameInputFieldlPreferencePage(lastNameNew, true)
//        																		  .fillCompanyInputField(companyNew, true)
//        																		  .fillJobTitleInputField(jobTitle, true)
//        																		  .fillBusinessPhoneInputField(businessPhone, true)
//        																		  .action( "government" )
//        																		  .action( "technology" )
//        																		  .action( "update" )
//        																		  .verifyDisplayedElements(new String[]{ elements.textSuccessfullyUpdateContactDetails },
//                                                                                                           new String[]{ elements.successfullyUpdateContactHereText },
//                                                                                                           true );
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( notRegisteredEmail, true )
//                                                                                  .action( "login pass disabled" ) 
//                                                                                  .verifySubscriberDetail( elements.firstNamePreferencePage ,
//																		 				  "firstName1",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.lastNamePreferencePage,
//																		 				  "lastName1",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.company,
//																		 				  "company1",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.jobTitle,
//																		 				  "jobTitle",
//																		 				  true )
//                                                                                  .verifySubscriberDetail( elements.businessPhone,
//																		 				 "businessPhone",
//																		 				 true )
//                                                                                  .verifyDisplayedElements( new String[]{ elements.governmentMLTrue,
//															 								 							  elements.technologyMLTrue,
//															 								 							  elements.capitalmarketsMLFalse},
//                                                                                		  					new String[]{ elements.taxMLFalse,
//														 								 							  	  elements.internationalLegislationMLFalse,
//														 								 							  	  elements.environmentMLFalse},
//                                                                                		  					true );
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
//    public void successfullyUnselectPreferencesAndMarketingListsPasswordEnabled() throws Exception {
//
//    	//!!!check the information in Dynamics manually:
//    	String randNumber = driver.getTimestamp();
//        String notRegisteredEmail = randNumber + "@mailinator.com";
//    	String firstName = "firstName" + randNumber;
//  	  	String lastName = "lastName" + randNumber;
//  	  	String company = "company" + randNumber;
//  	  	String password = "password";
//  	  	String jobTitle = "jobTitle" + randNumber;
//  	  	String businessPhone = "businessPhone" + randNumber;
//
//        log.startTest( "Verify if contact is used (Use Leads = No) and password protected=Yes and unselect a preference on PM page, will be removed from Contacts Marketing List" );
//        try {
//        	
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).action( "signup" )
//																				 .fillEmailInputField( notRegisteredEmail, false )
//																				 .fillFirstNameInputField(firstName, false)
//																				 .fillLastNameInputField(lastName, false)
//																				 .fillCompanyInputFieldSignUp(company, false)
//																				 .fillPasswordInputField(password, false)
//																				 .fillConfirmPasswordInputField(password, false)
//																				 .action( "register new contact" );
//        																		
//        															   mailinator.accessMailinator()
//        															   			 .searchCustomerEmail( notRegisteredEmail )
//        															   			 .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//        															   			 .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//        															             Thread.sleep(200);
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled );
//        																		 Thread.sleep(600);
//        																		  fillEmailInputField( notRegisteredEmail, true )
//        															   			 .fillPasswordInputField( password, true )
//        															   			 .action( "submit" )
//			  																	 .fillJobTitleInputField(jobTitle, true)
//			  																	 .fillBusinessPhoneInputField(businessPhone, true)
//			  																	 .action( "environment" )
//			  																	 .action( "capital markets" )
//			  																	 .action( "update" );
//        															   			  
//        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail, true )
//        	 																	  .fillPasswordInputField( password, true )
//        	 																	  .action( "submit" )
//        	 																	  .action( "environment" )
//			  																	  .action( "capital markets" )
//			  																	  .action( "tax" )
//			  																	  .action( "government" )
//			  																	  .action( "update" );
//        	 accessPreferenceManagerLandingPage( preferenceManagerUrlPassEnabled ).fillEmailInputField( notRegisteredEmail, true )
//			  																	  .fillPasswordInputField( password, true )
//			  																	  .action( "submit" )	 
//        	 																	  .verifyDisplayedElements( new String[]{ elements.environmentMLFalse,
//        	 																			   								  elements.capitalmarketsMLFalse},
//        	 																			   					new String[]{ elements.taxMLTrue,
// 	 																			   								   		  elements.governmentMLTrue},
//        	 																			   					true );
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
//    public void successfullyUnselectPreferencesAndMarketingListsPasswordDisabled() throws Exception {

    	//!!!check the information in Dynamics manually:
    	
//    	String randNumber = driver.getTimestamp();
//        String notRegisteredEmail = randNumber + "email@mailinator.com";
//    	String firstName = "firstName" + randNumber;
//  	  	String lastName = "lastName" + randNumber;
//  	  	String company = "company" + randNumber;
//  	  	String jobTitle = "jobTitle" + randNumber;
//  	  	String businessPhone = "businessPhone" + randNumber;
//
//        log.startTest( "Verify if contact is used (Use Leads = No) and password protected=No and unselect a preference on PM page, will be removed from Contacts Marketing List" );
//        try {
//        	
//        	accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).action( "signup" )
//			 																	  .fillEmailInputField( notRegisteredEmail, false )
//			 																	  .fillFirstNameInputField(firstName, false)
//			 																	  .fillLastNameInputField(lastName, false)
//			 																	  .fillCompanyInputFieldSignUp(company, false)
//			 																	  .action( "register new contact" );
//			
//        															    mailinator.accessMailinator()
//        															    		  .searchCustomerEmail( notRegisteredEmail )
//        															    		  .openEmailCampaign( elements.pleaseConfirmYourRegistrationEmail )
//        															    		  .clickLinkFromEmailContentPM( elements.confirmRegistrationLink );
//        															    		   Thread.sleep(200);
//            accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled );
//			 																	   Thread.sleep(600);
//			 																	   fillEmailInputField( notRegisteredEmail, true )
//			 																	   .action( "submit" )
//			 																	   .fillJobTitleInputField(jobTitle, true)
//			 																	   .fillBusinessPhoneInputField(businessPhone, true)
//			 																	   .action( "environment" )
//			 																	   .action( "capital markets" )
//			 																	   .action( "update" );
//  			  
//		    accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( notRegisteredEmail, true )
//			  																	  .action( "submit" )
//			  																	  .action( "environment" )
//			  																	  .action( "capital markets" )
//			  																	  .action( "tax" )
//			  																	  .action( "government" )
//			  																	  .action( "update" );
//		    accessPreferenceManagerLandingPage( preferenceManagerUrlPassDisabled ).fillEmailInputField( notRegisteredEmail, true )
//				  																  .action( "submit" )	 
//				  																  .verifyDisplayedElements( new String[]{ elements.environmentMLFalse,
//				  																		  							      elements.capitalmarketsMLFalse},
//				  																		  					new String[]{ elements.taxMLTrue,
//				  																		  								  elements.governmentMLTrue},
//				  																		  					true );
//        } catch( Exception e ) {
//
//            log.endStep( false );
//            throw e;
//        }
//
//        log.endTest();
//    }
}
