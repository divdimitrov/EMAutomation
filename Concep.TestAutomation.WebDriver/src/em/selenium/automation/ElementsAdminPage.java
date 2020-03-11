package em.selenium.automation;

public class ElementsAdminPage {

	protected final String email                                        = "//input[@id='email']";
	protected final String password                                     = "//input[@id='password']";
	protected final String rememberMeCheckbox                           = "//input[@id='remember']";
	protected final String loginButton                          	    = "//button";
	protected final String forgotPasswordLink                           = "//a[contains(@class, 'btn btn-link')]";
	protected final String languageDropDown                             = "//span[@id='select2-langSelector-container']";
	protected final String wrongCredentialsMsg                          = ".//strong[contains(text(), 'These credentials do not match our records.')]";
	protected final String emailFieldRequired                           = ".//strong[contains(text(), 'The email field is required.')]";
	protected final String passwordFieldRequired                        = ".//strong[contains(text(), 'The password field is required.')]";
	
	
	//euromaster/admin
	protected final String DashboardMainHeading 						= "//h1";
	protected final String DashboardMainHeadingName 					= "//h1[contains(text(), 'Dashboard')]";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected final String firstName                                    = "//input[@id='firstName']";
    protected final String lastName                                     = "//input[@id='lastName']";
    protected final String companySignUpNoPass                          = "//input[@id='company']";
    protected final String company                                      = "//input[@id='parentcustomerid']";
    protected final String confirmPassword                              = "//input[@id='confirmpassword']";
    protected final String businessPhone                                = "//input[@id='telephone1']";
    protected final String jobTitle                                     = "//input[@id='jobtitle']";
    
    protected final String firstNamePreferencePage                      = "//input[@id='firstname']";
    protected final String lastNamePreferencePage                       = "//input[@id='lastname']";
    protected final String emailPreferencePage                          = "//input[@id='emailaddress1']";
    
    
    protected final String emailNewSubscriber                           = "//input[@id='email']";
    protected final String changeEmail								    = "//input[@id='email']";
    protected final String phone								        = "";
    
    protected final String loginBtn                                     = "//button/label[contains(text(), 'Login')]";
    protected final String logoutBtn                                    = "//a[@id='logout-btn']";
    protected final String registerBtn                                  = "//button/label[contains(text(), 'register')]";
    protected final String resetPasswordBtn                             = "//button/label[contains(text(), 'reset my password')]";
    protected final String closeInforMessage							= "//div[@id='message-wrong-credentials']/span[@id='msg-close-btn']";
    protected final String closeInforMessageInvalidEmailForgotPass		= "//div[@id='invalid-login-form']/span[@id='msg-close-btn'][contains(text(), 'close')]";
    protected final String closeInforMessageResetPassEmailSent		    = "//div[@id='message-ok']/span[@id='msg-close-btn'][contains(text(), 'close')]";
    protected final String saveResetPassword							= "//button/label[contains(text(), 'Save')]";
    protected final String alreadyAmember							    = "//div[@id='login-btn']/p[contains(text(), 'Already a member,')]";
    protected final String loginSignUpPage						        = "//div[@id='login-btn']/a[contains(text(), 'login')]";
    protected final String signUpMainHeading							= "//h1[contains(text(), 'Sign Up to Morgan & Hustler')]";
    protected final String signUpInfoMessage							= "//h3[contains(@class, 'sub title')][contains(text(), 'Enter your information into the below form to register.')]";
    protected final String mainHeadingExistingSubscriberForm            = "//h1[contains(text(), 'Manage your Preferences')]";
    protected final String mainHeadingContactDetailsPage                = "//h3[contains(text(), 'Enter your information into the below form to login.')]";
    protected final String forgottenPasswordBtn							= "//a[@id='forgot-password-btn'][contains(text(), 'Forgot password?')]";
    protected final String cookieMessageLoginPage						= "//p[@id='cookie-message-container-text']";
    
    protected final String almostThere								    = "//h4[contains(text(), 'Almost there')]";
    protected final String checkYourEmail								= "//p[contains(text(), 'Please check your email to validate your account')]";
    protected final String thankYouSigningUp							= "//h1[contains(text(), 'Thank you for signing up')]";
    protected final String signUpSecondMessage                          = "//h3[contains(@class, 'To Morgan & Hustler’s Preference Manager')]";
    																		    
    protected final String logoMorganAndHustler							= "//header/div[contains(@class, 'logo-holder')]/a[contains(@class, 'logo')]";
    protected final String logoMorganAndHustlerFooter					= "//footer/div[contains(@class, 'logo-holder')]/a[contains(@class, 'logo')]";
        
    protected final String mainHeadingMainPageWelcome					= "//h1[contains(@class, 'main title')][contains(text(), 'Welcome')]";
    protected final String confirmedRegistrationMessage					= "//section/p[1][contains(text(), 'Your registration has been confirmed.')]";
    protected final String welcomeBackMessage						    = "//p[contains(text(), 'Please login to manage your preferences')]";
    protected final String ready									    = "//h4[contains(text(), 'ready')]";
    protected final String loginBtnConfirmedRegistration				= "//label[contains(text(), 'Login')]";
    // Main Page - Managing Mailing Preferences:
    protected final String aoiText									    = "//section[contains(@class, 'additional-info')]/h4[contains(text(), 'Areas of Interest')]";
    protected final String contactDetails								= "//section[contains(@class, 'register-block concep_mlistid')]/h4[contains(text(), 'Contact Details')]";
    protected final String updateBtn						            = "//button/label[contains(text(), 'Update')]";
    
    protected final String aoiTax										= "//input[@type='checkbox' and @id='concep_mlist_preference1']";
    protected final String aoiCapitalMarkets							= "//input[@type='checkbox' and @id='concep_mlist_preference2']";
    protected final String aoiEnvironment							    = "//input[@type='checkbox' and @id='concep_mlist_preference3']";
    protected final String aoiGovernment								= "//input[@type='checkbox' and @id='concep_mlist_preference4']";
    protected final String aoiInternationalLegislation					= "//input[@type='checkbox' and @id='concep_mlist_preference5']";
    protected final String aoiTechnology					            = "//input[@type='checkbox' and @id='concep_mlist_preference6']";
       
    protected final String taxMLTrue									= "//input[@type='checkbox' and @id='concep_mlist_preference1' and @value='true']";
    protected final String taxMLFalse									= "//input[@type='checkbox' and @id='concep_mlist_preference1' and @value='false']";
    protected final String capitalmarketsMLTrue							= "//input[@type='checkbox' and @id='concep_mlist_preference2' and @value='true']";
    protected final String capitalmarketsMLFalse						= "//input[@type='checkbox' and @id='concep_mlist_preference2' and @value='false']";
    protected final String environmentMLTrue							= "//input[@type='checkbox' and @id='concep_mlist_preference3' and @value='true']";
    protected final String environmentMLFalse						    = "//input[@type='checkbox' and @id='concep_mlist_preference3' and @value='false']";
    protected final String governmentMLTrue							    = "//input[@type='checkbox' and @id='concep_mlist_preference4' and @value='true']";
    protected final String governmentMLFalse						    = "//input[@type='checkbox' and @id='concep_mlist_preference4' and @value='false']";
    protected final String internationalLegislationMLTrue				= "//input[@type='checkbox' and @id='concep_mlist_preference5' and @value='true']";
    protected final String internationalLegislationMLFalse				= "//input[@type='checkbox' and @id='concep_mlist_preference5' and @value='false']";
    protected final String technologyMLTrue							    = "//input[@type='checkbox' and @id='concep_mlist_preference6' and @value='true']";
    protected final String technologyMLFalse							= "//input[@type='checkbox' and @id='concep_mlist_preference6' and @value='false']";
    
    protected final String hereBtnUpdatedPreferences				    = "//a[@id='msg-link'][contains(text(), 'here')]";
    protected final String textSuccessfullyUpdateContactDetails			= "//p[@id='dialog-message-saved-text'][contains(text(), 'Your preferences have now been updated.')]";
    protected final String successfullyUpdateContactHereText			= "//div[@id='message-ok']/div[contains(@class, 'error-message')]/p[2][contains(text(), 'Click here to go back to our website.')]";
    protected final String preferencesUpdated				            = "//span[@id='ui-id-1'][contains(text(), 'Preferences updated')]";
    protected final String preferencesUpdatesClosePopUp				    = "///button[contains(@class, 'ui-button ui-corner-all ui-widget ui-button-icon-only ui-dialog-titlebar-close')]";
       
    // Password Reset Page
    protected final String retrievePasswordBtn                          = "//input[contains(@class, 'btn btn-primary btn-lg btn-block')]";
    protected final String resetMyPasswordBtn                           = "//button/label[contains(text(), 'reset my password')]";
    protected final String headingPasswordResetPage						= "//h1[contains(@class, 'main title')][contains(text(), 'Forgotten password')]";
    protected final String backToLoginBtn                               = "//a/span[contains(text(), 'Return to login page')]";
    protected final String emptyEmailField								= "//p[contains(@class, 'login-error-content')][contains(text(), 'Email format is invalid')]";
    protected final String passwordRestrictions						    = "//p[contains(@class, 'login-error-content')][contains(text(), 'Password must be at least 6 characters long')]";
    protected final String passwordsNotMatch						    = "//p[contains(@class, 'login-error-content')][contains(text(), 'Passwords does not match')]";
    protected final String emptyFieldsMessage						    = "//p[contains(@class, 'login-error-content')][contains(text(), 'Please make sure you have filled all fields')]";
    protected final String emptyFieldsPreferencePage					= "//p[contains(@class, 'login-error-content')][contains(text(), 'There are empty fields, form is invalid')]";
    
    protected final String goBackBtn                                    = "//span[@id='login-btn']";
    protected final String messageContactAlreadyExist					= "//div[@id='dialog-message-contact-exists']";
    protected final String emailAlreadyExistPreferencePage				= "//div[@id='dialog-message-crm-error']/div[contains(@class, 'error-message')]/p[@id='message-crm-error-text']";
    protected final String emailAlreadyExistResetPassText				= "//div[@id='dialog-message-crm-error']/div[contains(@class, 'error-message')]/p[2]";
    protected final String emailAlreadyExistResetPassBtn    			= "//a[@id='dialog-message-crm-error-contact-exist'][contains(text(), 'here')]";
    protected final String resetYourPasswordTextResetPage               = "//form/section[contains(@class, 'register-block')]/h4[contains(text(), 'Reset your password')]";
    protected final String sucessfullyResetPassword						= "//main/section[contains(@class, 'register-block')]/h4[contains(text(), 'Your password was sucessfully reset')]";
             
    //In Mailinator:
    protected final String emptyInbox				 					= "//div[@id='publicm8rguy']/a[contains(text(), '[ This Inbox is Currently Empty ]')]";
    protected final String publicInbox									= "//div[contains(@class, 'ng-binding')]";
    protected final String resetYourPasswordEmail 					    = "Reset your password , please";
    protected final String pleaseConfirmYourRegistrationEmail 		    = "Please, confirm your registration";
    
   
    
    //Info and error messages:
    protected final String unableToSignUp								= "//p[@id='message-sub-exists-content'][contains(text(), 'We have been unable to sign you up for Preference Manager.')]";
    protected final String errorMsgCantSignUp							= "//div[@id='message-contact-exists']/div[contains(@class, 'error-message')]";
    protected final String unableToSendSignUpEmail						= "//p[@id='dialog-message-error-text']";
    protected final String unableToSendResetPassword					= "//p[@id='dialog-message-error-text'][contains(text(), 'We have been unable to send you a reset password link.')]";
    protected final String unableToSendResetPasswordText				= "//div[@id='message-error']/div[contains(@class, 'error-message')]/p[2][contains(text(), 'If you are not yet registered to Preference Manager')]";
    protected final String unableToSendResetPasswordHereLink			= "//a[@id='dialog-message-error-text-signup-btn'][contains(text(), 'here')]";
    
    protected final String unableToSendSignUpEmailCloseBtn				= "//div[@id='message-error']/span[@id='msg-close-btn'][contains(@text, 'close')]";
    protected final String alreadyRegisteredLoginHereBtn				= "//a[@id='dialog-message-sub-exists-text-login-btn'][contains(@class, 'here')]";
    protected final String forgottenPasswordHereBtn						= "//a[@class='msg-link'][contains(@id, 'dialog-message-sub-exists-text-reset-btn')]";
    protected final String closeErrorMessageCantSignUp					= "//div[@id='message-contact-exists']/span[@id='msg-close-btn']";
    protected final String allFieldsAreEmptry							= "//label[@id='error-filed-msg-label'][contains(text(), '\" + * Please ensure that all fields contain a value to continue + \"')]";
    protected final String passwordsDoNotMatch							= "//label[@id='error-filed-msg-label'][contains(text(), '* Passwords do not match')]";
    protected final String passwordValidation							= "//label[@id='error-filed-msg-label'][contains(text(), '\\\\\\\" + * Password must be at least 4 symbols long! + \\\\\\\"')]";
    protected final String passwordEmpty								= "//label[@id='error-filed-msg-label'][contains(text(), '\\\\\\\" + * Please ensure that all fields contain a value to continue + \\\\\\\"')]";
    protected final String enterValidEmail								= "//label[@id='error-filed-msg-label'][contains(text(), '\" + * Please enter a valid E-mail address + \"')]";
    protected final String passwordRequirements							= "//label[@id='error-filed-msg'][contains(text(), '* Password does not match minimum length of 6 characters')]";		
   	    	
	protected final String wrongCredentialsLogin					  = "//p[@id='message-wrong-credentials-text'][contains(text(), 'We were unable to log you into Preference Manager.')]";
	protected final String wrongCredentials    					      = "//p[@id='message-wrong-credentials-text'][contains(text(), 'We were unable to sign you into Preference Manager.')]";
    protected final String invalidEmail								  = "//div[@id='message-wrong-credentials']/div[contains(@class, 'error-message')]";
    
    protected final String mainPageDisablePassword					  = "//section[contains(@class, 'header-content')]";
    protected final String unableToSendEmail						  = "//div[@id='dialog-message-error']";
    protected final String okBtn									  = "//button[contains(@class, 'sign-up-error-close ui-button ui-corner-all ui-widget')]";//div[contains(@class, 'form-group error-msg col-lg-12 col-md-12 col-sm-12 col-xs-12')]
    protected final String subscriberAlreadyExist					  = "//span[@id='ui-id-1'][contains(text(), 'Subscriber exists already')]";//input[@id='company']
    protected final String alreadyRegistered						  = "//p[@id='dialog-message-contact-exists-text'][contains(text(), 'You have already registered with our site. To continue, please reset your password.')]";
    protected final String signUpBtn								  = "//div[@id='signup-btn']/a[contains(text(), 'sign up')]";
    protected final String forgottenPassword    					  = "//a[@id='forgot-password-btn'][contains(text(), 'Forgot password?')]";
    protected final String wrongCredentialsPassEnabled 				  = "//div[@id='dialog-message-wrong-credentials']";
    
    
    protected final String resetPasswordEmailSent					  = "//p[@id='dialog-message-text'][contains(text(), 'We have sent you a link to reset your password.')]";
    protected final String invalidEmailOrPassword 					  = "//div[@id='message-wrong-credentials']/div[contains(@class, 'error-message')]";
    protected final String unableToSignHereBtn						  = "//a[@id='dialog-message-wrong-credentials-text-signup-btn'][contains(text(), 'here')]";
    //protected final String textValidationNotValidEmail				  = "//div[contains(@class, 'error-message')][contains(text(), '')]";
    
    
    protected final String validationErrorOKbtn						  = "//button[contains(@class, 'preference-error-crm-close ui-button ui-corner-all ui-widget')]";
    
    protected final String userDetailsSignup						  = "//div[contains(@class, 'form-group col-lg-12 col-md-12 col-sm-12 col-xs-12')]/label[contains(@class, 'col-md-3 col-sm-3 col-xs-10')]";
    protected final String userDetailsSignupPassword       			  = "//div[contains(@class, 'form-group password col-lg-12 col-md-12 col-sm-12 col-xs-12')]/label[contains(@class, 'col-md-3 col-sm-3 col-xs-10')]";
    protected final String closeBtn									  = "//span[contains(@class, 'ui-button-icon ui-icon ui-icon-closethick')]";
   
    protected final String confirmRegistrationLink					  = "(//a[contains(text(), 'Click here')])[2]";
       
    protected final String unableToSendConfirmationEmail			  = "//p[@id='dialog-message-error-text'][contains(text(), 'We have been unable to send you a confirmation email at this time. Please try again later.')]";
    
    protected final String unexpectedErrorMessage					  = "//span[@id='ui-id-2']/a[contains(text(), 'Unexpected error')]";
    protected final String unexpectedError							  = "//span[@id='ui-id-1'][contains(text(), 'Unexpected error')]";
    protected final String clickOnResetPasswordEmail 				  = "(//a[contains(text(), 'Click here')])[2]";

    
    protected final String aboutUsConcepPage						  = "//nav[contains(@class, 'nav-footer')]/ul/li[1]/a[contains(text(), 'About Us')]";
    protected final String contactConcepPage						  = "//nav[contains(@class, 'nav-footer')]/ul/li[4]/a[contains(text(), 'Contact')]";
    protected final String okBtnErrorMessage						  = "//button[contains(@class, 'reset-forgot-pass-fail ui-button ui-corner-all ui-widget')]";	
    protected final String sentLinkToResetPassword				      = "//div[@id='message-ok']/div[contains(@class, 'error-message')]/p[2][contains(text(), 'Check your inbox to access this link and reset your password.')]";
    protected final String okBtnResetPasswordMessage				  = "//button[contains(@class, 'reset-forgot-pass-done ui-button ui-corner-all ui-widget')]";
    protected final String invalidTokenResetPassword				  = "//pre[contains(text(), 'Invalid token')]";
    protected final String clickHereFromUpdatePopup					  = "//a[@id='msg-link'][contains(text(), 'here')]";

    protected String getValidationMessage(
                                           String text ) {

        return "//div[@id='divMessage'][@class='validation'][contains(text(), '" + text + "')]";
    }

    protected String getSuccessMessage(
                                        String text ) {

        return "//div[@id='divMessage'][@class='success'][contains(text(), '" + text + "')]";
    }
    
    protected String getInstructionMessage(
                                            String text ) {

        return "//h2[@id='instructionText'][contains(text(), '" + text + "')]";
    }
    
    protected String getSubscribedMessage(
            								String text ) {

    	return "//label[contains(@class, 'status colored-txt')][contains(text(), '\" + text + \"')]";
    }
   
    protected String getSubscriberDetailInputField(
                                                    String detailName ) {

        return "//label/span[contains(text(), '" + detailName + "')]/../..//input";
    }

    protected String getSubscriberDetailDropdown(
                                                  String detailName ) {

        return "//label/span[contains(text(), '" + detailName + "')]/../..//select";
    }

    protected String getTitleOption(
                                     String optionValue ) {

        return "//option[contains(text(), '" + optionValue + "')]";
    }

    protected String getSubscriberDetailTextarea(
                                                  String detailName ) {

        return "//label/span[contains(text(), '" + detailName + "')]/../..//textarea";
    }

    protected String getGroupCheckbox(
                                       String groupName ) {

        return "//span[contains(text(), '" + groupName + "')]/../..//input[@type='checkbox']";
    }
}