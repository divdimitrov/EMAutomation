package concep.selenium.subscriptionForm;

public class Elements {

	protected final String mainPageHeadingText = "Welcome to the Knowledge Preference Centre, you can customise your preferences so "
				+ "that you receive updates and alerts based on your interests by selecting from the options below.";
	
	protected final String passwordValidationTxt = "The password you have entered does not match our records. Please check and try again."
															+ " If you have forgotten your password, please use the ‘Reset password’ link.";
	
	protected final String emailsAllowedValidationTxt = "Please note that you are only able to subscribe using a KPMG email address ending in "
																+ "kpmg.com, kpmg.co.uk, kpmg.com.au, concep.com or mailinator.com. Please check and try again.";
	
	protected final String welcomeText = "Thank you for your interest in our publications and email communications. Please enter your email below"
												+ " to add or manage your subscriptions and contact information.";
	
	protected final String notMatchingPasswordValidationTxt = "The passwords you have entered do not match. Please check and try again.";
	
	protected final String blankFieldsValidationTxt = "The following fields cannot be blank. Please enter valid values : ";
				
	// Login page
	protected final String loginPageHeading = "//h1[contains(text(), 'Login')]";
	protected final String emailInputField = "//input[@id='ctl00_ContentPlaceHolder1_txtEmail']";
	protected final String submitBtn = "//input[@id='ctl00_ContentPlaceHolder1_btnSubmit']";
	protected final String emailMissingValidationTxt = "//span[contains(text(), 'Please enter your email address.')]";
	protected final String emailNotValidValidationTxt = "//span[contains(text(), 'You must provide a valid email address.')]";
	protected final String emailNotAllowedValidationTxt = "//span[contains(text(), '" + emailsAllowedValidationTxt + "')]";
	protected final String welcomeTextLandingPage = "//p[contains(text(), '" + welcomeText + "')]";
	
	// Register page
	protected final String registerPageHeading = "//h1[contains(text(), 'You Are Not Yet Registered')]";
	protected final String backBtn = "//input[@id='ctl00_ContentPlaceHolder1_backButton']";
	protected final String registerBtn = "//input[@id='ctl00_ContentPlaceHolder1_btnRegister']";
	protected final String emailSentHeading = "//h1/span[contains(text(), 'Email Sent')]";
	protected final String enterPasswordPageHeading = "//h1/span[contains(text(), 'Enter Your Password')]";
	
	protected String getSentEmailConfirmationMsg(String email, boolean isRegistered) {
		
		if (!isRegistered) {
			
			return "//span[contains(text(), 'An email with instructions for  setting your password and completing your registration has been sent to " + email + "')]"; 
		
		} else {
			
			return "//span[contains(text(), 'An email with instructions for  resetting your password has been sent to " + email + "')]"; 
		}		
	}
	
	// Set password page
	protected final String setPasswordPageHeading = "//h1/span[contains(text(), 'Set Your Password')]";
	protected final String password = "//input[@id='ctl00_ContentPlaceHolder1_txtPassword'][@type='password']";
	protected final String passwordConfirm = "//input[@id='ctl00_ContentPlaceHolder1_txtPasswordConfirm']";
	protected final String confirmBtn = "//input[@id='ctl00_ContentPlaceHolder1_btnConfirm']";
	protected final String forgottenPasswordLink = "//a[@id='ctl00_ContentPlaceHolder1_lbForgottenPassword']";
	protected final String passwordValidationMsg = "//span[@id='ctl00_ContentPlaceHolder1_cvalPassword'][contains(text(), '" + passwordValidationTxt + "')]";
	protected final String passwordNotMatchValidationMsg = "//span[contains(text(), '" + notMatchingPasswordValidationTxt + "')]";
	
	protected String getValidationMsgRequiredField(boolean isEmailEmpty, boolean isFirstNameEmpty, boolean isLastNameEmpty) {
		
		String blankFieldsValidationMsg = "//span[contains(text(), '" + blankFieldsValidationTxt + "')]";
				
		if (isEmailEmpty) {
			
			blankFieldsValidationMsg += "[contains(., 'Email')]";
		}
		
		if (isFirstNameEmpty) {
			
			blankFieldsValidationMsg += "[contains(., 'First Name')]";
		}
		
		if (isLastNameEmpty) {
			
			blankFieldsValidationMsg += "[contains(., 'Last Name')]";
		}
		
		return blankFieldsValidationMsg;
	}
		
	protected String getEmailReadonlyField(String email) {
		
		return "//span[@id='ctl00_ContentPlaceHolder1_lblEmail'][contains(text(), '" + email + "')]";
	}
	
	protected final String passwordResetEmailCampaignName = "Set your password for the KPMG Subscription Form";
	protected final String notifficationEmailCampaignName = "Subscription Form Update";
	protected final String passwordResetLink = "//a[contains(text(), 'http://ali-qa.concep.com/PasswordRecovery')]";
	
	protected final String[] passwordRequiredmentsMsg = {
			"//span[contains(text(), 'Please ensure your password meets the necessary requirements. It must be between 8 and 20 characters long and must contain at least one:')]",
			"//li[contains(text(), 'upper case letter')]",
			"//li[contains(text(), 'lower case letter')]",
			"//li[contains(text(), 'A numeral')]",
			"//li[contains(text(), 'Special character:')]"
	};
	
	protected final String expiredLinkHeading = "//h1/span[contains(text(), 'Invalid Link')]";
	protected final String expiredLinkTxt = "//p/span[contains(text(), 'The URL you have tried to access has expired or is not a valid recovery URL')]";
	
	// Subscription Form main page
	protected final String subscriptionFormMainPageHeading = "//p[contains(text(), 'Welcome to the Knowledge Preference Centre, you can customise your preferences so')]";
	protected final String subscriberDetailsHeading = "//h1[contains(text(), 'Verify Your Details')]";
	protected final String subscribeInstructionsHeading = "//div[@class='concep_container']//h3[contains(text(), 'To subscribe:')]";
	protected final String subscribeInstructionsTxt = "//div[@class='concep_container']//p[contains(text(), 'Please select the area(s) you are interested in.')]";
	protected final String unsubscribeInstructionsHeading = "//div[@class='concep_container']//h3[contains(text(), 'To unsubscribe:')]";
	protected final String unsubscribeInstructionsTxt = "//div[@class='concep_container']//p[contains(text(), 'Simply de-select the check-box(es).')]";
	protected final String unsubscribeAllInstructionsHeading = "//div[@class='concep_container']//h3[contains(text(), 'To unsubscribe from all:')]";
	protected final String unsubscribeAllInstructionsTxt = "//div[@class='concep_container']//p[contains(text(), 'Simply select the \"unsubsribe from all\" check-box.')]";
	protected final String submitInstructionsTxt = "//div[@class='concep_container']//p[contains(text(), 'Please click on ‘Submit’ after you have made your selections')]";
		
	// Subscriber Details
	protected final String titleSalutation = "//select[@id='ctl00_ContentPlaceHolder1_drpTitle']";
	protected final String firstName = "//input[@id='ctl00_ContentPlaceHolder1_txtFirstName']";
	protected final String lastName = "//input[@id='ctl00_ContentPlaceHolder1_txtLastName']";
	protected final String country = "//select[@id='ctl00_ContentPlaceHolder1_ddlCountry']";
	protected final String jobTitle = "//input[@id='ctl00_ContentPlaceHolder1_txtJobTitle']";
	protected final String grade = "//select[@id='ctl00_ContentPlaceHolder1_ddlGrade']";
	protected final String function = "//select[@id='ctl00_ContentPlaceHolder1_drpFunction']";
	protected final String lineOfBusiness = "//select[@id='ctl00_ContentPlaceHolder1_drpLob']";
	protected final String sector = "//select[@id='ctl00_ContentPlaceHolder1_drpSector']";
	protected final String subSector = "//select[@id='ctl00_ContentPlaceHolder1_drpSubSector']";
	protected final String resetPasswordLink = "//a[@id='ctl00_ContentPlaceHolder1_lbResetPassword']";
	
	protected String getSubscriberDetailInputField(String detailName) {
		
		return "//label/span[contains(text(), '" + detailName + "')]/../..//input";
	}
	
	protected String getSubscriberDetailDropdown(String detailName) {
		
		return "//label/span[contains(text(), '" + detailName + "')]/../..//select";
	}
	
	// Groups
	protected String getGroupCheckbox(String groupName) {
		
		return "//label[contains(text(),'" + groupName + "')]/../input";
	}
	
	protected String getGroupBlockLabel(String blockName) {
		
		return "//span[contains(text(), '" + blockName + "')]";
	}
	
	protected String unsubscribeAll = "//label[contains(text(),'Unsubscribe from all areas of interest')]/../input";
	
	protected final String subscriberInfoUpdateMsg = "//div[@id='ctl00_ContentPlaceHolder1_pnlConfirm']//span[contains(text(), 'Subscriber Information Updated')]";
	protected final String subscriberInfoErrorMsg = "//div[@id='ctl00_ContentPlaceHolder1_pnlValidate']//span[contains(text(), 'The following fields cannot be blank. Please enter valid values :')]";
	protected final String closeBtn = "//div[@id='ctl00_ContentPlaceHolder1_pnlConfirm']//img[@id='ctl00_ContentPlaceHolder1_btnClose']";	
}