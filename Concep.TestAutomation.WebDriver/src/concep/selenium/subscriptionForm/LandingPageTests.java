package concep.selenium.subscriptionForm;

import org.testng.annotations.Test;

public class LandingPageTests extends BaseSF {

    @Test(groups = { "all-tests" })
    public void successfullyAccessSubscriptionFormLandingPage() throws Exception {

        log.startTest( "Verify that user can access the Subscription Form landing page succesfully" );
        try {

            accessSubscriptionFormPage().verifyDisplayedElements( new String[]{ elements.welcomeTextLandingPage,
                                                                          elements.emailInputField },
                                                                  new String[]{ "Welcome Text Landing Page",
                                                                          "Email Input Field" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageNoEmail() throws Exception {

        log.startTest( "Verify that a validation message for no email value is displayed" );
        try {

            accessSubscriptionFormPage().action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.emailMissingValidationTxt },
                                                                  new String[]{ "Please enter your email address." },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageNotValidEmail() throws Exception {

        log.startTest( "Verify that a validation message for not valid email is displayed" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( "notValidEmailValue" )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.emailNotValidValidationTxt },
                                                                  new String[]{ "You must provide a valid email address." },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageNotAllowedEmail() throws Exception {

        log.startTest( "Verify that a validation message for not allowed email is displayed" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( "emai@notallowed.com" )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.emailNotAllowedValidationTxt },
                                                                  new String[]{ "Please note that you are only able to subscribe using a KPMG email address ending in "
                                                                                + "kpmg.com, kpmg.co.uk, kpmg.com.au, concep.com or mailinator.com. Please check and try again." },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyAccessPasswordEnteringPageWithExistingUserWithPasswordEntry() throws Exception {

        log.startTest( "Verify that the page for entering passowrd can be successfully accessed with existing user that has an entry in the address book field 'password'" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredSubscriberEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.getEmailReadonlyField( registeredSubscriberEmail ),
                                                                          elements.password,
                                                                          elements.backBtn,
                                                                          elements.forgottenPasswordLink,
                                                                          elements.confirmBtn },
                                                                  new String[]{ "Subscriber Email Readonly Field",
                                                                          "Password Field",
                                                                          "Back Button",
                                                                          "Forgotten Password Link",
                                                                          "Confirm Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyAccessPasswordEnteringPageWithExistingUserWithoutPasswordEntry() throws Exception {

        String registeredEmailEmptyPasswordField = "emptypasswordfieldcontact@mailinator.com";

        log.startTest( "Verify that the page for entering passowrd can be successfully accessed with existing user that doesn't have an entry in the address book field 'password'" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredEmailEmptyPasswordField )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyAccessRegistrationPageNotRegisteredEmail() throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";

        log.startTest( "Verify that the registration page can be successfully accessed with not registered email" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( notRegisteredEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyReturnToLandingPageFromPasswordEnteringPage() throws Exception {

        log.startTest( "Verify that the user can successfully return to the Landing page from the Password Entering page" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredSubscriberEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.getEmailReadonlyField( registeredSubscriberEmail ),
                                                                          elements.password,
                                                                          elements.forgottenPasswordLink,
                                                                          elements.backBtn,
                                                                          elements.confirmBtn },
                                                                  new String[]{ "Enter Your Password",
                                                                          "Subscriber Email Readonly Field",
                                                                          "Password Field",
                                                                          "Forgotten Password Link",
                                                                          "Back Button",
                                                                          "Confirm Button" },
                                                                  true )
                                        .action( "Back" )
                                        .verifyDisplayedElements( new String[]{ elements.welcomeTextLandingPage,
                                                                          elements.emailInputField,
                                                                          elements.submitBtn },
                                                                  new String[]{ "Thank you for your interest in our publications and email communications. Please enter your email below"
                                                                                + " to add or manage your subscriptions and contact information.",
                                                                          "Email Field",
                                                                          "Submit Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyReturnToLandingPageFromRegistrationPage() throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";

        log.startTest( "Verify that the user can successfully return to the Landing page from the Registration page" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( notRegisteredEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true )
                                        .action( "Back" )
                                        .verifyDisplayedElements( new String[]{ elements.welcomeTextLandingPage,
                                                                          elements.emailInputField,
                                                                          elements.submitBtn },
                                                                  new String[]{ "Thank you for your interest in our publications and email communications. Please enter your email below"
                                                                                + " to add or manage your subscriptions and contact information.",
                                                                          "Email Field",
                                                                          "Submit Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySendRegistrationEmailCampaignNotExistingContact() throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";

        log.startTest( "Verify that a registration email campaign can be successfully sent to not existing contact" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( notRegisteredEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true )
                                        .action( "Register" )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( notRegisteredEmail,
                                                                                                                false ) },
                                                                  new String[]{ "Email Sent",
                                                                          "An email with instructions for  resetting your password has been sent to"
                                                                                  + notRegisteredEmail },
                                                                  true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( notRegisteredEmail )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName )
                      .clickLinkFromEmailContent( elements.passwordResetLink );

            verifyDisplayedElements( new String[]{ elements.setPasswordPageHeading,
                                             elements.getEmailReadonlyField( notRegisteredEmail ),
                                             elements.password,
                                             elements.passwordConfirm,
                                             elements.backBtn,
                                             elements.confirmBtn },
                                     new String[]{ "Set Your Password page heading",
                                             notRegisteredEmail + "Readonly field",
                                             "Password Field",
                                             "Password Confirm Field",
                                             "Back Button",
                                             "Confirm Button" },
                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySendRegistrationEmailCampaignExistingContactEmptyPasswordField() throws Exception {

        String registeredEmailEmptyPasswordField = "emptypasswordfieldcontact@mailinator.com";

        log.startTest( "Verify that a registration email campaign can be successfully sent to existing contact with empty password field" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredEmailEmptyPasswordField )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered Page Heading",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true )
                                        .action( "Register" )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( registeredEmailEmptyPasswordField,
                                                                                                                false ) },
                                                                  new String[]{ "Email Sent Page Heading",
                                                                          "'An email with instructions for  setting your password and completing your registration has been sent to "
                                                                                  + registeredEmailEmptyPasswordField },
                                                                  true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( registeredEmailEmptyPasswordField )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName );

            verifyDisplayedElements( new String[]{ elements.passwordResetLink },
                                     new String[]{ "Password Reset Link" },
                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySendForgottenPasswordResetEmailCampaign() throws Exception {

        log.startTest( "Verify that a forgotten password reset email campaign can be successfully sent" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredSubscriberEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.backBtn },
                                                                  new String[]{ "Enter Your Password Page Heading",
                                                                          "Back Button" },
                                                                  true )
                                        .resetPassword( true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( registeredSubscriberEmail )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName );

            verifyDisplayedElements( new String[]{ elements.passwordResetLink },
                                     new String[]{ "Password Reset Link" },
                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageWrongPassword() throws Exception {

        log.startTest( "Verify that a validation message is dispalyed when user is trying to login with wrong password" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredSubscriberEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.getEmailReadonlyField( registeredSubscriberEmail ),
                                                                          elements.backBtn,
                                                                          elements.confirmBtn },
                                                                  new String[]{ "Enter Your Password",
                                                                          "Email Readonly Field",
                                                                          "Back Button",
                                                                          "Confirm Button" },
                                                                  true )
                                        .fillPasswordInputField( "wrongPassword", false )
                                        .action( "Confirm" )
                                        .verifyDisplayedElements( new String[]{ elements.passwordValidationMsg },
                                                                  new String[]{ elements.passwordValidationTxt },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayAdviceMessageUserMustRegisterWhenPasswordEnabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";

        log.startTest( "Verify that a message is displayed advising the user to click on the 'Register' button"
                       + " to recieve an email with a link to set a password" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( notRegisteredEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered Page Heading",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageNotMatchingPasswords() throws Exception {

        log.startTest( "Verify that a validation message is displayed when password are not matching" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredSubscriberEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.backBtn,
                                                                          elements.confirmBtn },
                                                                  new String[]{ "Enter Your Password Page Heading",
                                                                          "Back Button",
                                                                          "Confirm Button" },
                                                                  true )
                                        .resetPassword( true )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( registeredSubscriberEmail,
                                                                                                                true ) },
                                                                  new String[]{ "Email Sent page heading",
                                                                          "An email with instructions for  resetting your password has been sent to" },
                                                                  true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( registeredSubscriberEmail )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName )
                      .clickLinkFromEmailContent( elements.passwordResetLink );

            verifyDisplayedElements( new String[]{ elements.setPasswordPageHeading,
                                             elements.getEmailReadonlyField( registeredSubscriberEmail ),
                                             elements.password,
                                             elements.passwordConfirm,
                                             elements.backBtn,
                                             elements.confirmBtn },
                                     new String[]{ "Set Your Password Page Heading",
                                             "Email Readonly Field",
                                             "Password",
                                             "Password Confirm",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( "Aa111111!", false )
                                           .fillPasswordInputField( "Aa111111?", true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( new String[]{ elements.passwordNotMatchValidationMsg },
                                                                     new String[]{ "The passwords you have entered do not match. Please check and try again." },
                                                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayForgottenPasswordLink() throws Exception {

        log.startTest( "Verify that a forgotten password link is displayed from wher user can reset his password" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredSubscriberEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.getEmailReadonlyField( registeredSubscriberEmail ),
                                                                          elements.forgottenPasswordLink },
                                                                  new String[]{ "Enter Your Password Page Heading",
                                                                          "Email Readonly Field",
                                                                          "Forgotten Password Link" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyResetPasswordFromForgottenPasswordLink() throws Exception {

        String randNumber = driver.getTimestamp();

        String email = randNumber + "email@mailinator.com";
        String password = "Aa111111!";
        String newPassword = "newPassw0rdValue!";

        log.startTest( "Verify that a subscriber can successfully reset his password from the forgotten password link" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( email )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered Page Heading",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true )
                                        .action( "Register" )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( email,
                                                                                                                false ) },
                                                                  new String[]{ "Email Sent Page Heading",
                                                                          "An email with instructions for  setting your password and completing your registration has been sent to "
                                                                                  + email },
                                                                  true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( email )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName )
                      .clickLinkFromEmailContent( elements.passwordResetLink );

            verifyDisplayedElements( new String[]{ elements.setPasswordPageHeading,
                                             elements.getEmailReadonlyField( email ),
                                             elements.password,
                                             elements.passwordConfirm,
                                             elements.backBtn,
                                             elements.confirmBtn },
                                     new String[]{ "Set Your Password Page Heading",
                                             "Email Readonly Field",
                                             "Password Field",
                                             "Password Confirm",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( password, false )
                                           .fillPasswordInputField( password, true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                     new String[]{ elements.mainPageHeadingText },
                                                                     true )
                                           .resetPassword( false )
                                           .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                             elements.getSentEmailConfirmationMsg( email,
                                                                                                                   true ) },
                                                                     new String[]{ "Email Sent Page Heading",
                                                                             "An email with instructions for  resetting your password has been sent to "
                                                                                     + email },
                                                                     true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( email )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName )
                      .clickLinkFromEmailContent( elements.passwordResetLink );

            verifyDisplayedElements( new String[]{ elements.setPasswordPageHeading,
                                             elements.getEmailReadonlyField( email ),
                                             elements.password,
                                             elements.passwordConfirm,
                                             elements.backBtn,
                                             elements.confirmBtn },
                                     new String[]{ "Set Your Password Page Heading",
                                             "Email Readonly Field",
                                             "Password Field",
                                             "Password Confirm Field",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( newPassword, false )
                                           .fillPasswordInputField( newPassword, true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                     new String[]{ elements.mainPageHeadingText },
                                                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessagePasswordRequirementsMatchingPasswordsButNotVallid()
                                                                                                       throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String password = "pass";

        log.startTest( "Verify that a validation message is disaplyed when user is trying to register using a password that not match with the requirements" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( notRegisteredEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered Page Heading",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true )
                                        .action( "Register" )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( notRegisteredEmail,
                                                                                                                false ) },
                                                                  new String[]{ "Email Sent Page Heading",
                                                                          "An email with instructions for  setting your password and completing your registration has been sent to "
                                                                                  + notRegisteredEmail },
                                                                  true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( notRegisteredEmail )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName )
                      .clickLinkFromEmailContent( elements.passwordResetLink );

            verifyDisplayedElements( new String[]{ elements.setPasswordPageHeading,
                                             elements.getEmailReadonlyField( notRegisteredEmail ),
                                             elements.password,
                                             elements.passwordConfirm,
                                             elements.backBtn,
                                             elements.confirmBtn },
                                     new String[]{ "Set Your Password",
                                             "Email Readonly Field",
                                             "Password Field",
                                             "Password Confirm Field",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( password, false )
                                           .fillPasswordInputField( password, true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( elements.passwordRequiredmentsMsg,
                                                                     new String[]{ "Please ensure your password meets the necessary requirements. It must be between 8 and 20 characters long and must contain at least one:",
                                                                             "- upper case letter",
                                                                             "- lower case letter",
                                                                             "- A numeral",
                                                                             "- Special character: !@#£$%^*?\"',." },
                                                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessagePasswordRequirementsNotMatchingPasswords()
                                                                                              throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String password = "p1!";
        String passConfirmation = "p2!";

        log.startTest( "Verify that a validation message is disaplyed when user is trying to register using a matches password but not match with the requirements" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( notRegisteredEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered Page Heading",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true )
                                        .action( "Register" )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( notRegisteredEmail,
                                                                                                                false ) },
                                                                  new String[]{ "Email Sent",
                                                                          "An email with instructions for  setting your password and completing your registration has been sent to "
                                                                                  + notRegisteredEmail },
                                                                  true );

            mailinator.accessMailinator()
                      .searchCustomerEmail( notRegisteredEmail )
                      .openEmailCampaign( elements.passwordResetEmailCampaignName )
                      .clickLinkFromEmailContent( elements.passwordResetLink );

            verifyDisplayedElements( new String[]{ elements.setPasswordPageHeading,
                                             elements.getEmailReadonlyField( notRegisteredEmail ),
                                             elements.password,
                                             elements.passwordConfirm,
                                             elements.backBtn,
                                             elements.confirmBtn },
                                     new String[]{ "Set Your Password",
                                             "Email Readonly Field",
                                             "Password Field",
                                             "Password Confirm Field",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( password, false )
                                           .fillPasswordInputField( passConfirmation, true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( elements.passwordRequiredmentsMsg,
                                                                     new String[]{ "Please ensure your password meets the necessary requirements. It must be between 8 and 20 characters long and must contain at least one:",
                                                                             "- upper case letter",
                                                                             "- lower case letter",
                                                                             "- A numeral",
                                                                             "- Special character: !@#£$%^*?\"',." },
                                                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageResetPasswordOptedOutContact() throws Exception {

        String optedOutEmail = "optedoutcontact@mailinator.com";

        log.startTest( "Verify that a friendly error message is displayed that reset password email can't be sent to contact which is opted out" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( optedOutEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.getEmailReadonlyField( optedOutEmail ),
                                                                          elements.forgottenPasswordLink },
                                                                  new String[]{ "Enter Your Password Page Heading",
                                                                          "Email Readonly Field",
                                                                          "Forgotten Password Link" },
                                                                  true )
                                        .resetPassword( true )
                                        .verifyDisplayedElements( new String[]{ elements.getSentEmailConfirmationMsg( optedOutEmail,
                                                                                                                      true ) },
                                                                  new String[]{ "An email with instructions for  resetting your password has been sent to "
                                                                                + optedOutEmail },
                                                                  false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyLoginSubscriberWithRightPassword() throws Exception {

        log.startTest( "Verify that a subscriber can be successfully logged in with right password" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading,
                                                                          elements.subscriberDetailsHeading },
                                                                  new String[]{ elements.mainPageHeadingText,
                                                                          "Verify Your Details Heading" },
                                                                  true );

            // TODO: An additional verification must be added when is clear the error message text

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyReturnToLandingPageFromPasswordEnteringPageBrowserBack() throws Exception {

        log.startTest( "Verify that the user can successfully return to the Landing page from the Password Entering page when browser back button is clicked" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( registeredSubscriberEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.getEmailReadonlyField( registeredSubscriberEmail ),
                                                                          elements.password,
                                                                          elements.forgottenPasswordLink,
                                                                          elements.backBtn,
                                                                          elements.confirmBtn },
                                                                  new String[]{ "Enter Your Password Page Heading",
                                                                          "Email Readonly Field",
                                                                          "Password Field",
                                                                          "Forgotten Password Link",
                                                                          "Back Button",
                                                                          "Confirm Button" },
                                                                  true )
                                        .browserCommand( "Back" )
                                        .verifyDisplayedElements( new String[]{ elements.welcomeTextLandingPage,
                                                                          elements.emailInputField,
                                                                          elements.submitBtn },
                                                                  new String[]{ elements.welcomeText,
                                                                          "Email Input Field",
                                                                          "Submit Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyReturnToLandingPageFromRegistrationPageBrowserBack() throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";

        log.startTest( "Verify that the user can successfully return to the Landing page from the Registration page when browser back button is clicked" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( notRegisteredEmail )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered Page Heading",
                                                                          "Back Button",
                                                                          "Register Button" },
                                                                  true )
                                        .browserCommand( "Back" )
                                        .verifyDisplayedElements( new String[]{ elements.welcomeTextLandingPage,
                                                                          elements.emailInputField,
                                                                          elements.submitBtn },
                                                                  new String[]{ elements.welcomeText,
                                                                          "Email Input Field",
                                                                          "Submit Button" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}