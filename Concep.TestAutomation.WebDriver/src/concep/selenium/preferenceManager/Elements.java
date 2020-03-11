package concep.selenium.preferenceManager;

public class Elements {

    protected final String textNewSubscribersFormMainHeading          = "//div[contains(@class, 'row header-group')]/h5[contains(@class, 'headerRight')][contains(text(), 'Not yet a member, Sign up')]";
    protected final String textNewSubscribersFormHeading              = "//div/div[contains(@class, 'row')]";

    protected final String wrongCredentialsLogin					  = "//p[@id='dialog-message-wrong-credentials-text']";
    protected final String invalidEmail								  = "//div[contains(@class, 'ui-dialog-titlebar ui-corner-all ui-widget-header ui-helper-clearfix')]";
    protected final String almostThere								  = "//label[contains(@class, 'status colored-txt')][contains(text(), 'almost there')]";
    protected final String ready									  = "//label[contains(@class, 'status colored-txt')][contains(text(), 'ready')]";
    protected final String mainPageDisablePassword					  = "//section[contains(@class, 'header-content')]";
    protected final String aoiText									  = "//h4[contains(@class, 'headerLeft')][contains(@class, 'Enter your information into the below form to register.')]";
    protected final String areasOfInterestTab						  = "//li[@id='step2']/span[contains(@class, 'stepNumber')][contains(text(), 'Areas of Interest')]";
    protected final String aoiTab									  = "//li[@id='step2']/span[contains(@class, 'stepNumber')]";
    protected final String flagRegisteredUser						  = "//div[contains(@class, 'flag')]";
    protected final String forgottenPasswordButton					  = "/button[contains(@class, 'login-forgot-password ui-button ui-corner-all ui-widget')]";
    protected final String selectAOIbelow							  = "//div[contains(@class, 'top')]/p";
    protected final String unableToSendEmail						  = "//div[@id='dialog-message-error']";
    protected final String okBtn									  = "//button[contains(@class, 'sign-up-error-close ui-button ui-corner-all ui-widget')]";//div[contains(@class, 'form-group error-msg col-lg-12 col-md-12 col-sm-12 col-xs-12')]
    protected final String subscriberAlreadyExist					  = "//span[@id='ui-id-1'][contains(text(), 'Subscriber exists already')]";
    protected final String alreadyRegistered						  = "//p[@id='dialog-message-contact-exists-text'][contains(text(), 'You have already registered with our site. To continue, please reset your password.')]";
    protected final String signUpBtn								  = "//button[contains(@class, 'login-close ui-button ui-corner-all ui-widget')]";
    protected final String forgottenPassword    					  = "//button[contains(@class, 'login-forgot-password ui-button ui-corner-all ui-widget')]";
    protected final String wrongCredentialsPassEnabled 				  = "//div[@id='dialog-message-wrong-credentials']";
    protected final String changeEmail								  = "//input[@id='emailaddress1']";
    protected final String emailUpdated								  = "//p[@id='dialog-message-saved-text'][contains(text(), 'Your preferences have now been updated.\r\n" + 
    		"            \r\n" + 
    		"            Click here to go back to our website.')]";
    protected final String preferencesUpdated						  = "//span[@id='ui-id-1'][contains(text(), 'Preferences updated')]";
    protected final String resetPasswordEmailSent					  = "//span[@id='ui-id-1'][contains(text(), 'Reset password email sent')]";
    protected final String invalidEmailOrPassword 					  = "//span[@id='ui-id-1'][contains(text(), 'Invalid email or password')]";
    protected final String logoutBtn								  = "//span[@id='logout-btn']";
    protected final String emailAlreadyExist						  = "//p[@id='dialog-message-email-validation-error-text']"
    																	+ "[contains(text(), '\\\\\\\\\\\\\\\" + This e-mail address already exists in our database. "
    																	+ "Please use a different e-mail address or click on reset password to create a password for your new e-mail address. + \\\\\\\\\\\\\\\"')]";
    protected final String validationErrorOKbtn						  = "//button[contains(@class, 'preference-error-crm-close ui-button ui-corner-all ui-widget')]";
    protected final String emailCanNotBeUpdated						  = "//div[@id='dialog-email-exists-error'][contains(text(), '\" "
    																	+ "+ Subscriber's email can't be updated because there is already contact/lead with this email existing."
    																	+ " + \"')]";

    protected final String alreadyAmember							  = "//h4[contains(@class, 'headerRight')][contains(text(), '\" + Already a member, login + \"')]"; 
    protected final String userDetailsSignup						  = "//div[contains(@class, 'form-group col-lg-12 col-md-12 col-sm-12 col-xs-12')]/label[contains(@class, 'col-md-3 col-sm-3 col-xs-10')]";
    protected final String userDetailsSignupPassword       			  = "//div[contains(@class, 'form-group password col-lg-12 col-md-12 col-sm-12 col-xs-12')]/label[contains(@class, 'col-md-3 col-sm-3 col-xs-10')]";
    protected final String closeBtn									  = "//span[contains(@class, 'ui-button-icon ui-icon ui-icon-closethick')]";
    protected final String notRegisteredEmail		  				  = "Please confirm you registration";
    protected final String confirmRegistrationLink					  = "(//a[contains(text(), 'Click here')])[2]";
    protected final String confirmedRegistrationMessage				  = "//div[contains(@class, 'main-content col-lg-12 col-md-12 col-sm-11 col-xs-11')]/p";
        
    protected final String unexpectedErrorMessage					  = "//p[@id='dialog-message-error-text'][contains(text(), 'We were unable to send you a new password link. Please check that your email address is correct. If you have not already signed up to Preference Manager please sign up here.')]";
    protected final String unexpectedError							  = "//span[@id='ui-id-2'][contains(text(), 'Unexpected error')]";
    protected final String unexpectedErrorForgottenPassword			  = "//span[@id='ui-id-1'][contains(text(), 'Unexpected error')]";
    protected final String resetYourPasswordEmail 					  = "Reset your password , please";
    protected final String clickOnResetPasswordEmail 				  = "(//a[contains(text(), 'Click here')])[2]";
    protected final String pleaseConfirmYourRegistrationEmail 		  = "Please confirm you registration";
    protected final String welcomeInPMEmail							  = "";
    protected final String hereBtnUpdatedPreferences				  = "//a[@id='msg-link' and @contains(text(), 'here')]";
    protected final String aboutUsConcepPage						  = "//nav[contains(@class, 'nav-footer')]/ul/li[1]/a[contains(text(), 'About Us')]";
    protected final String contactConcepPage						  = "//nav[contains(@class, 'nav-footer')]/ul/li[4]/a[contains(text(), 'Contact')]";
    protected final String okBtnErrorMessage						  = "//button[contains(@class, 'reset-forgot-pass-fail ui-button ui-corner-all ui-widget')][contains(text(), 'Ok')]";	
    protected final String sentLinkToResetPassword				      = "//p[@id='dialog-message-text'][contains(text(), 'We have sent you a link to reset your password. Please check your inbox to access this link and reset your password.')]";
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
    }//input[contains(@class, 'btn btn-primary btn-lg btn-block')]
    
    protected String getSubscribedMessage(
            								String text ) {

    	return "//label[contains(@class, 'status colored-txt')][contains(text(), '\" + text + \"')]";
    }

    // Form for already subscribed users
    protected final String emailExistingSubscriber                      = "//input[@id='email']";
    protected final String passwordInputField                           = "//input[@id='password']";
    protected final String forgottenPasswordLink                        = "//label[@id='forgot-password-btn']";
    protected final String backToLogin                                  = "//label[@id='forgot-password-btn']";
    protected final String submitBtn                                    = "//input[contains(@class, 'btn btn-primary btn-lg btn-block')]";
    protected final String preferenceSavedBtn						    = "//button[contains(@class, 'preferences-saved ui-button ui-corner-all ui-widget')]";
    protected final String mainHeadingExistingSubscriberForm            = "//div[contains(@class, 'row header-group')]";
   
    protected final String signUpMainPage								= "//div[contains(@class, 'row header-group')]/h5[contains(@class, 'headerRight')]/span[@id='signup-btn']";
    															
    // Form for not subscribed users
    protected final String emailNewSubscriber                           = "//input[@id='email']";
    protected final String registerBtn                                  = "//input[contains(@class, 'btn btn-primary btn-lg btn-block')]";
    protected final String mainHeadingNewSubscriberForm                 = "//span[contains(@class, 'title')][contains(text(), 'preference manager')]";
    protected final String concepPM										= "//span[contains(@class, 'title')][contains(text(), 'concep`s preference manager')]";
    protected final String preferenceManager							= "//span[contains(@class, 'title')][contains(text(), 'preference manager')]";
    protected final String logoConcep                     			    = "//div[@id='logo']";
    protected final String close										= "//button";
    protected final String loginBtnConfirmedRegistration				= "//button[contains(text(), 'Login')]";
    protected final String emptyInbox				 					= "//div[@id='publicm8rguy']/a[contains(text(), '[ This Inbox is Currently Empty ]')]";
    protected final String publicInbox									= "//div[contains(@class, 'ng-binding')]";

    // Password Reset Page
    protected final String retrievePasswordBtn                          = "//input[contains(@class, 'btn btn-primary btn-lg btn-block')]";
    protected final String backToLoginBtn                               = "//span[@id='login-btn']";
   
    protected final String headingPasswordResetPage                     = "//span[contains(@class, 'welcome')][contains(text(), 'Forgotten')]";
    
    protected final String goBackBtn                                    = "//span[@id='login-btn']";//div[@id='noTabs']//a[strong='Go Back']
    protected final String forgottenPasswordBtn							= "//button[contains(@class, 'sign-up-contact-exists ui-button ui-corner-all ui-widget')]";
    protected final String messageContactAlreadyExist					= "//div[@id='dialog-message-contact-exists']";
    
    // Main Page - Contact Information
    protected final String contactInformationTab                        = "//section[contains(@class, 'header-content preference-page')]";
    protected final String preferenceManagerTitle						= "//div[contains(@class, 'header-group preference-page')]/label/span[contains(@class, 'title')][contains(text(), 'preference manager')]";
    protected final String mainHeadingContactDetailsPage                = "//section[contains(@class, 'header-content')]";
    protected final String welcomeBackMessage						    = "//div[@id='template']/label/span[contains(@class, 'title')]";
   
    protected final String firstName                                    = "//input[@id='firstname']";
    protected final String lastName                                     = "//input[@id='lastname']";
    protected final String email                                        = "//input[@id='email']";
    protected final String jobTitle                                     = "//div[@id='contactInfoForm']//input[@id='jobTitle']";
    protected final String company                                      = "//input[@id='parentcustomerid']";
    protected final String password                                     = "//input[@id='password']";
    protected final String confirmPassword                              = "//input[@id='confirmpassword']";
    protected final String mobile                                       = "//div[@id='contactInfoForm']//input[@id='Phone\\Business ASD\\phoneNumber']";
    protected final String businessAddress                              = "//div[@id='contactInfoForm']//textarea[@id='Street\\Business ASD\\street']";
    protected final String city                                         = "//input[@id='address1_city']";
 
    protected final String saveBtnContactInfo                           = "//input[@id='btnSaveContactInfo']";
    protected final String saveBtnMailingPref                           = "//input[@id='btnSave']";
    protected final String continuePersonalDetailsPage					= "//div[@id='continue-registration']/button[contains(@class, 'btn btn-lg btn-block')]";
    protected final String registerBtnAreasOfInterest					= "//input[contains(@class, 'btn btn-lg btn-block')]";
    protected final String backBtnAreasOfInterest						= "//div[@id='back-registration']/button[contains(@class, 'btn btn-lg btn-block')]";
    protected final String loginBtnMainPage								= "//span[@id='login-btn']";
    protected final String mainHeadingMainPageWelcome					= "//span[contains(@class, 'welcome')][contains(text(), 'welcome back')]"; 
    protected final String forgottenPass								= "//span[contains(@class, 'welcome')][contains(text(), 'Forgotten')]"; 
    protected final String forgottenPassPage							= "//h2/span[contains(@class, 'title')][contains(text(), 'password')]";
    protected final String mainHeadingMainPageWelcomeMessage			= "//div[@id='template']/label/span[contains(@class, 'title')][contains(text(), '\\\\\\\\\\\\\\\" + firstName + \\\\\\\\\\\\\\\"')]";
    protected final String sectionAOI									= "//section[contains(@class, 'preference-page areaOfInterest form-group col-lg-12 col-md-12 col-sm-12 col-xs-12')]";
    protected final String selectAOI									= "//div[contains(@class, 'top')]/p";
    protected final String country 										= "//input[@id='address1_country']";
    protected final String saveResetPassword							= "//input[contains(@class, 'btn btn-lg btn-block')]";
    
    // Main Page - Managing Mailing Preferences
    protected final String allFieldsAreEmptry							= "//label[@id='error-filed-msg-label'][contains(text(), '* Please ensure that all fields contain a value to continue')]";
    protected final String passwordsDoNotMatch							= "//label[@id='error-filed-msg-label'][contains(text(), '* Passwords do not match!')]";
    protected final String passwordValidation							= "//label[@id='error-filed-msg-label'][contains(text(), '\\\\\\\" + * Password must be at least 4 symbols long! + \\\\\\\"')]";
    protected final String passwordEmpty								= "//label[@id='error-filed-msg-label'][contains(text(), '\\\\\\\" + * Please ensure that all fields contain a value to continue + \\\\\\\"')]";
    protected final String enterValidEmail								= "//label[@id='error-filed-msg-label'][contains(text(), '* Please enter a valid E-mail address')]";
    protected final String passwordRequirements							= "//label[@id='error-filed-msg-label'][contains(text(), '* Password must be at least 4 symbols long!')]";		
    protected final String areasOfInterest 								= "//li[@id='step2']";
    protected final String managingMailingPreferencesTab                = "//input[@id='tab2'][@alt='managing mailing preferences']";
    
    protected final String aoiAndML										= "//div[contains(@class, 'radio-buttons col-lg-12 col-md-12 col-sm-12 col-xs-12')]";
    protected final String aoiRealEstate								= "//*[@id=\"main-form\"]/section[2]/div[2]/div/div[1]";
    protected final String aoiTax										= "//*[@id=\"main-form\"]/section[2]/div[2]/div/div[2]";
    protected final String aoiCapitalMarkets							= "//*[@id=\"main-form\"]/section[2]/div[2]/div/div[3]";
    protected final String aoiPrivateEquity								= "//*[@id=\"main-form\"]/section[2]/div[2]/div/div[4]";
           
    protected final String mlRealEstate									= "//*[@id=\"main-form\"]/section[2]/div[3]/div/div[1]";
    protected final String mlTax										= "//*[@id=\"main-form\"]/section[2]/div[3]/div/div[2]";
    protected final String mlCapitalMarkets								= "//*[@id=\"main-form\"]/section[2]/div[3]/div/div[3]";
    protected final String mlPrivateEquity								= "//*[@id=\"main-form\"]/section[2]/div[3]/div/div[4]";
    
    protected final String realEstateTrue								= "//input[@class='onoffswitch-inner1' and @id='concep_aoi_realestate' and @value='true']";
    protected final String realEstateFalse								= "//input[@class='onoffswitch-inner1' and @id='concep_aoi_realestate' and @value='false']";
    protected final String taxTrue										= "//input[@class='onoffswitch-inner2' and @id='concep_aoi_tax' and @value='true']";
    protected final String taxFalse										= "//input[@class='onoffswitch-inner2' and @id='concep_aoi_tax' and @value='false']";
    protected final String capitalmarketsTrue							= "//input[@class='onoffswitch-inner3' and @id='concep_aoi_capitalmarkets' and @value='true']";
    protected final String capitalmarketsFalse							= "//input[@class='onoffswitch-inner3' and @id='concep_aoi_capitalmarkets' and @value='false']";
    protected final String privateequityTrue							= "//input[@class='onoffswitch-inner4' and @id='concep_aoi_privateequity' and @value='true']";
    protected final String privateequityFalse							= "//input[@class='onoffswitch-inner4' and @id='concep_aoi_privateequity' and @value='false']";
    
    protected final String realEstateMLTrue								= "//input[@type='checkbox' and @id='concep_mlist_mlknrealestate' and @value='true']";
    protected final String realEstateMLFalse							= "//input[@type='checkbox' and @id='concep_mlist_mlknrealestate' and @value='false']";
    protected final String taxMLTrue									= "//input[@type='checkbox' and @id='concep_mlist_mlkntax' and @value='true']";
    protected final String taxMLFalse									= "//input[@type='checkbox' and @id='concep_mlist_mlkntax' and @value='false']";
    protected final String capitalmarketsMLTrue							= "//input[@type='checkbox' and @id='concep_mlist_mlkncapitalmarkets' and @value='true']";
    protected final String capitalmarketsMLFalse						= "//input[@type='checkbox' and @id='concep_mlist_mlkncapitalmarkets' and @value='false']";
    protected final String privateequityMLTrue							= "//input[@type='checkbox' and @id='concep_mlist_mlknprivateequity' and @value='true']";
    protected final String privateequityMLFalse							= "//input[@type='checkbox' and @id='concep_mlist_mlknprivateequity' and @value='false']";
    
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