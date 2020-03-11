package concep.selenium.subscriptionForm;

import org.testng.annotations.Test;

import concep.selenium.send.Elements;

public class MainPageTests extends BaseSF {

    private Elements                sendElements     = new Elements();

    protected static final String[] allGroupBlocks   = {

            "Account and Company",
            "Experience",
            "Expertise",
            "Global News",
            "Industry and Sector",
            "News and Awareness"                    };

    protected static final String[] allGroups        = {

            "Global Financial Services - Client Agenda",
            "Credentials Alert",
            "Wins Alert",
            "Global Technology, Media and Telecommunications Update",
            "Partner News Brief",
            "Global News Wire",
            "Daily Davos Roundup",
            "Global Automotive - News mail",
            "Global Consumer Markets - Monthly Update",
            "Global Financial Services - EMA CoE Alerts",
            "Global Financial Services - EMA CoE Regulatory Update",
            "Global Financial Services - Strategy Updates",
            "Global FS Marketing newsletter",
            "Global Industrial Aerospace & Defence - Newsletter",
            "Global Industrial Manufacturing -  Newsletter",
            "Global Life Sciences Round-up",
            "Global Media & Telecommunications- Quarterly Market Update",
            "Global Mining - Newsletter",
            "Global Technology - Issues Notes (TechNotes)",
            "Global Technology - Quarterly Market Update",
            "Global Telecommunications - Issues Notes (TeleNotes)",
            "Global Aerospace & Defence - Announcements",
            "Global Automotive - Announcements",
            "Global Industrial Manufacturing - Announcements" };

    protected static final String[] triggerList      = {

            "Global Financial Services - Client Agenda",
            "Daily Davos Roundup",
            "Global Automotive - Announcements"     };

    protected static final String[] notificationList = {

                                                     "notifficationemails@mailinator.com" };

    private MainPageTests updateSubscriberDetail(
                                                  String detailName,
                                                  String detailValue ) throws Exception {

        if( detailName == "First Name" || detailName == "Last Name" || detailName == "Email address"
            || detailName == "Job title" ) {

            log.startStep( "Update the '" + detailName + "' with value of: " + detailValue );
            driver.clear( elements.getSubscriberDetailInputField( detailName ) );
            driver.type( elements.getSubscriberDetailInputField( detailName ), detailValue, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Update the '" + detailName + "' with vlaue of: " + detailValue );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.select( elements.getSubscriberDetailDropdown( detailName ) )
                  .selectByVisibleText( detailValue );
            log.endStep();
        }

        return this;
    }

    private MainPageTests updateAllSubscriberDetails(
                                                      String titleSalutation,
                                                      String firstName,
                                                      String lastName,
                                                      String country,
                                                      String emailAddress,
                                                      String jobTitle,
                                                      String grade,
                                                      String function,
                                                      String lineOffBusiness,
                                                      String sector,
                                                      String subSector ) throws Exception {

        if( titleSalutation != null ) {

            updateSubscriberDetail( "Title salutation", titleSalutation );
        }

        if( firstName != null ) {

            updateSubscriberDetail( "First Name", firstName );
        }

        if( lastName != null ) {

            updateSubscriberDetail( "Last Name", lastName );
        }

        if( country != null ) {

            updateSubscriberDetail( "Country", country );
        }

        if( emailAddress != null ) {

            updateSubscriberDetail( "Email address", emailAddress );
        }

        if( jobTitle != null ) {

            updateSubscriberDetail( "Job title", jobTitle );
        }

        if( grade != null ) {

            updateSubscriberDetail( "Grade", grade );
        }

        if( function != null ) {

            updateSubscriberDetail( "Function", function );
        }

        if( lineOffBusiness != null ) {

            updateSubscriberDetail( "Line of Business", lineOffBusiness );
            Thread.sleep( 3000 ); // TODO: Must be removed
        }

        if( sector != null ) {

            updateSubscriberDetail( "Sector", sector );
            Thread.sleep( 3000 ); // TODO: Must be removed
        }

        if( subSector != null ) {

            updateSubscriberDetail( "Sub Sector", subSector );
        }

        return this;
    }

    @Test(groups = { "all-tests" })
    public void successfullyAccessSubscriptionFormMainPage() throws Exception {

        log.startTest( "Verify that user can successfully login to Subscription Form" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
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
    public void successfullyDisplayUnsubscribeFromAllGroupsCheckbox() throws Exception {

        log.startTest( "Verify that a checkbox that allows the user to unsubscribe from all groups is successfully displayed" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.unsubscribeAll },
                                                                  new String[]{ "Unsubscribe from all AOI checkbox" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayResetPasswordLink() throws Exception {

        log.startTest( "Verify that a checkbox that allows the user to reset his password is successfully displayed" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.resetPasswordLink },
                                                                  new String[]{ "Reset Password Link" },
                                                                  true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayErrorMessageWhenEmailChangedNotAllowedDomain() throws Exception {

        log.startTest( "Verify that an error message is successfully displayed when user is trying to change his email with not allowed domain" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading,
                                                                          elements.subscriberDetailsHeading },
                                                                  new String[]{ elements.mainPageHeadingText,
                                                                          "Verify Your Details Heading" },
                                                                  true );

            updateSubscriberDetail( "Email address", "email@notallowed.com" ).action( "Submit" );

            verifyDisplayedElements( new String[]{ elements.emailNotAllowedValidationTxt },
                                     new String[]{ elements.emailsAllowedValidationTxt },
                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayAllGroups() throws Exception {

        log.startTest( "Verify that all groups are successfully displayed" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword );

            for( int i = 0; i < allGroups.length; i++ ) {

                verifyDisplayedElements( new String[]{ elements.getGroupCheckbox( allGroups[i] ) },
                                         new String[]{ allGroups[i] + " checkbox" },
                                         true );
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayAllGroupBlocks() throws Exception {

        log.startTest( "Verify that all groups blocks are successfully displayed" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword );

            for( int i = 0; i < allGroupBlocks.length; i++ ) {

                verifyDisplayedElements( new String[]{ elements.getGroupBlockLabel( allGroupBlocks[i] ) },
                                         new String[]{ allGroupBlocks[i] + " group block" },
                                         true );
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyUpdateAllSubscriberDetails() throws Exception {

        String randNumber = driver.getTimestamp();

        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String jobTitle = randNumber + "jobTitle";

        log.startTest( "Verify that all groups blocks are successfully displayed" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword );
            updateAllSubscriberDetails( "Dr",
                                        firstName,
                                        lastName,
                                        "Bulgaria",
                                        null,
                                        jobTitle,
                                        "Director or equivalent",
                                        "Global Markets",
                                        "Consumer Markets",
                                        "Retail",
                                        "Online Retail" ).action( "Submit" )
                                                         .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                                   new String[]{ "Subscriber Information Updated Message" },
                                                                                   true )
                                                         .closePopUpMsg()
                                                         .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                                   new String[]{ "Subscriber Information Updated Message" },
                                                                                   false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySentNotificationEmailSubscribeToOneGroup() throws Exception {

        String[] groupTriggerList = { "Global Financial Services - Client Agenda" };

        log.startTest( "[Manual]Verify that a notiffication email is successfully sent when a subscriber subscribes to one group from the trigger list" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( groupTriggerList )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySentNotificationEmailSubscribeToMoreThanOneGroups() throws Exception {

        String[] groupTriggerList = { "Global Financial Services - Client Agenda",
                "Daily Davos Roundup",
                "Global Automotive - Announcements" };

        log.startTest( "[Manual]Verify that a notiffication email is successfully sent when a subscriber subscribes to more than one group from the trigger list" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( groupTriggerList )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySentNotificationEmailUnsubscribeToOneGroup() throws Exception {

        String[] groupTriggerList = { "Global Financial Services - Client Agenda" };

        log.startTest( "[Manual]Verify that a notiffication email is successfully sent when a subscriber unsubscribes to one group from the trigger list" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .unsubscribeGroup( groupTriggerList, false )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySentNotificationEmailUnsubscribeToMoreThanOneGroups() throws Exception {

        log.startTest( "[Manual]Verify that a notiffication email is successfully sent when a subscriber unsubscribes to more than one group from the trigger list" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .unsubscribeGroup( triggerList, false )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySentNotificationEmailSubscribeAndUnsubscribeSameTimeTrigerListGroupsOnly()
                                                                                                      throws Exception {

        log.startTest( "[Manual]Verify that a notiffication email is successfully sent when a subscriber subscribes and unsubscribes to group from the trigger list at the same time" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( new String[]{ triggerList[0], triggerList[2] } )
                                        .unsubscribeGroup( new String[]{ triggerList[1] }, false )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayEmailNotificationInformation() throws Exception {

        log.startTest( "[Manual]Verify that the notification email contains the required information" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( triggerList )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false )
                                        .unsubscribeGroup( triggerList, false )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyDisplayedGropNotPartFromTrigerList() throws Exception {

        String groupNameNotPartFromTrigerList = "Wins Alert";

        log.startTest( "[Manual]Verify that if a group is not part of the trigger list it never appears within a notification campaign" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( new String[]{ groupNameNotPartFromTrigerList } )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullyDisplayedGropPartFromTrigerListNoUpdate() throws Exception {

        log.startTest( "[Manual]Verify that if a group is part of the trigger list but the subscriber is not updated it never appears within a notification campaign" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( triggerList );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayedGropPartFromTrigerListUnsubscribeFromAll() throws Exception {

        log.startTest( "[Manual]Verify that a notification email will be successfully sent with relevant information when unsubscribe from all checkbox is checked" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .unsubscribeGroup( null, true )
                                        .action( "Submit" );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullySentNotificationEmailSubscribeAndUnsubscribeSameTimeTrigerListGroupsNotOnly()
                                                                                                         throws Exception {

        log.startTest( "[Manual]Verify that a notiffication email is successfully sent when a subscriber subscribes and unsubscribes to group which are part and not from the trigger list at the same time" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( new String[]{ "Daily Davos Roundup", "Wins Alert" } )
                                        .unsubscribeGroup( new String[]{ "Global Automotive - Announcements",
                                                                   "Partner News Brief" },
                                                           false )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );
            //TODO: Verification can't be automated because notification e-mails are delivered with a delay

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyResetPasswordFromSetPasswordLink() throws Exception {

        String randNumber = driver.getTimestamp();

        String email = randNumber + "email@mailinator.com";
        String password = "Aa111111!";
        String newPassword = "newPassw0rdValue!";

        log.startTest( "Verify that a subscriber can successfully reset his password from the set password link" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( email )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.registerPageHeading,
                                                                          elements.backBtn,
                                                                          elements.registerBtn },
                                                                  new String[]{ "You Are Not Yet Registered Page Headings",
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
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Password Confirmation",
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
                                     new String[]{ "Set Your Password",
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Password Confirm",
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
    public void successfullyDisplayValidationMessagePasswordsRequirementsWhenSetPassword() throws Exception {

        String notValidPassword = "p1!";

        log.startTest( "Verify that a validation message is displayed when password are matching but is not fulfill the requirements while user is trying to set new password" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .resetPassword( false )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( registeredSubscriberEmail,
                                                                                                                true ) },
                                                                  new String[]{ "Email Sent Heading",
                                                                          "An email with instructions for  resetting your password has been sent to "
                                                                                  + registeredSubscriberEmail },
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
                                     new String[]{ "Set Your Password Heading",
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Password Confirm",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( notValidPassword, false )
                                           .fillPasswordInputField( notValidPassword, true )
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
    public void successfullyDisplayValidationMessageNotMatchingPasswords() throws Exception {

        log.startTest( "Verify that a validation message is displayed when password are not matching while user is trying to set new passowrd" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .resetPassword( false )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( registeredSubscriberEmail,
                                                                                                                true ) },
                                                                  new String[]{ "Email Sent Page Heading",
                                                                          "An email with instructions for  resetting your password has been sent to "
                                                                                  + registeredSubscriberEmail },
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
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( "Aa111111!", false )
                                           .fillPasswordInputField( "Aa111111?", true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( new String[]{ elements.passwordNotMatchValidationMsg },
                                                                     new String[]{ elements.notMatchingPasswordValidationTxt },
                                                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageNotMatchingAndInvalidPasswords() throws Exception {

        log.startTest( "Verify that a validation message is displayed when password are invalid and not matching while user is trying to set new passowrd" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .resetPassword( false )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( registeredSubscriberEmail,
                                                                                                                true ) },
                                                                  new String[]{ "Email Sent Page Heading",
                                                                          "An email with instructions for  resetting your password has been sent to "
                                                                                  + registeredSubscriberEmail },
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
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( "p1!", false )
                                           .fillPasswordInputField( "p2!", true )
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
    public void successfullyMatchingContactInformationWhenCreateUser() throws Exception {

        String randNumber = driver.getTimestamp();

        String email = randNumber + "email@mailinator.com";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String jobTitle = randNumber + "jobTitle";
        String title = "Dr";
        String country = "Bulgaria";
        String grade = "Director or equivalent";
        String function = "Global Markets";
        String lineOfBusiness = "Consumer Markets";
        String sector = "Retail";
        String subSector = "Online Retail";

        log.startTest( "Verify that all contact details from the Subscription Form exactly match with the information stored in Concep Send when a new user is created" );
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
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( "Aa111111!", false )
                                           .fillPasswordInputField( "Aa111111!", true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                     new String[]{ elements.mainPageHeadingText },
                                                                     true );

            updateAllSubscriberDetails( "Dr",
                                        firstName,
                                        lastName,
                                        "Bulgaria",
                                        null,
                                        jobTitle,
                                        "Director or equivalent",
                                        "Global Markets",
                                        "Consumer Markets",
                                        "Retail",
                                        "Online Retail" ).action( "Submit" )
                                                         .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                                   new String[]{ "Subscriber Information Updated Text" },
                                                                                   true )
                                                         .closePopUpMsg()
                                                         .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                                   new String[]{ "Subscriber Information Updated Message" },
                                                                                   false );

            send.loginToSend( subscriptionFormSendUser, subscriptionFormSendPassword ).contact.goToContactsPage()
                                                                                              .goToContactsSubPage( "Contacts" )
                                                                                              .searchRecord( email )
                                                                                              .startEditingRecord( email )
                                                                                              .accessContactDetails( "Name and Email" )
                                                                                              .verifyContactDetail( sendElements.email,
                                                                                                                    email,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.firstName,
                                                                                                                    firstName,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.lastName,
                                                                                                                    lastName,
                                                                                                                    true )
                                                                                              .accessContactDetails( "Other Fields" )
                                                                                              .verifyContactDetail( sendElements.jobTitle,
                                                                                                                    jobTitle,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.title,
                                                                                                                    title,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.country,
                                                                                                                    country,
                                                                                                                    true )
                                                                                              .accessContactDetails( "Custom Fields" )
                                                                                              .verifyContactDetail( sendElements.grade,
                                                                                                                    grade,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.function,
                                                                                                                    function,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.lineOfBusiness,
                                                                                                                    lineOfBusiness,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.sector,
                                                                                                                    sector,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.subSector,
                                                                                                                    subSector,
                                                                                                                    true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyImportContactToAssociatedGroups() throws Exception {

        String[] someGroups = {

                "Global Financial Services - Client Agenda",
                "Global Technology, Media and Telecommunications Update",
                "Global News Wire",
                "Daily Davos Roundup",
                "Global Automotive - News mail",
                "Global Industrial Manufacturing - Announcements" };

        log.startTest( "Verify that a contact can be successfully associated with the groups he is subscribed with" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .subscribeGroup( someGroups )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  true )
                                        .closePopUpMsg()
                                        .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                  new String[]{ "Subscriber Information Updated Message" },
                                                                  false );

            send.loginToSend( subscriptionFormSendUser, subscriptionFormSendPassword ).contact.goToContactsPage();

            for( int i = 0; i < someGroups.length; i++ ) {

                send.contact.goToContactsSubPage( "Groups" )
                            .searchRecord( someGroups[i] )
                            .openGroup( someGroups[i] );
                verifyDisplayedElements( new String[]{ sendElements.getContactTableRecord( registeredSubscriberEmail ) },
                                         new String[]{ "Table Record for contact "
                                                       + registeredSubscriberEmail },
                                         true );
            }

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyRetainSubscriberValuesInConcepSendWhenSubmitWithoutChanges() throws Exception {

        String email = "subscriptionformtestnochanges@mailinator.com";
        String password = "Aa111111!";
        String firstName = "fnNoChanged";
        String lastName = "lnNoChanged";
        String jobTitle = "QA";
        String title = "Mr";
        String country = "Australia";
        String grade = "Manager or equivalent";
        String function = "Audit";
        String lineOfBusiness = "Industrial Markets";
        String sector = "Life Sciences";
        String subSector = "Biotechnology";

        log.startTest( "Verify that nothing is changed in Concep Send when no changes are made to a contact and 'Submit' button is clicked" );
        try {

            accessSubscriptionFormPage().login( email, password ).action( "Submit" );

            verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                     new String[]{ "Subscriber Information Updated Message" },
                                     true ).closePopUpMsg()
                                           .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                     new String[]{ "Subscriber Information Updated Message" },
                                                                     false );

            send.loginToSend( subscriptionFormSendUser, subscriptionFormSendPassword ).contact.goToContactsPage()
                                                                                              .goToContactsSubPage( "Contacts" )
                                                                                              .searchRecord( email )
                                                                                              .startEditingRecord( email )
                                                                                              .accessContactDetails( "Name and Email" )
                                                                                              .verifyContactDetail( sendElements.email,
                                                                                                                    email,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.firstName,
                                                                                                                    firstName,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.lastName,
                                                                                                                    lastName,
                                                                                                                    true )
                                                                                              .accessContactDetails( "Other Fields" )
                                                                                              .verifyContactDetail( sendElements.jobTitle,
                                                                                                                    jobTitle,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.title,
                                                                                                                    title,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.country,
                                                                                                                    country,
                                                                                                                    true )
                                                                                              .accessContactDetails( "Custom Fields" )
                                                                                              .verifyContactDetail( sendElements.grade,
                                                                                                                    grade,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.function,
                                                                                                                    function,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.lineOfBusiness,
                                                                                                                    lineOfBusiness,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.sector,
                                                                                                                    sector,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.subSector,
                                                                                                                    subSector,
                                                                                                                    true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyMatchingContactInformationWhenUpdateUser() throws Exception {

        String randNumber = driver.getTimestamp();

        String email = randNumber + "email@mailinator.com";
        String firstName = randNumber + "firstName";
        String lastName = randNumber + "lastName";
        String jobTitle = randNumber + "jobTitle";
        String title = "Dr";
        String country = "Bulgaria";
        String grade = "Director or equivalent";
        String function = "Global Markets";
        String lineOfBusiness = "Consumer Markets";
        String sector = "Retail";
        String subSector = "Online Retail";

        log.startTest( "Verify that all contact details from the Subscription Form exactly match with the information stored in Concep Send when a new user is created" );
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
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( "Aa111111!", false )
                                           .fillPasswordInputField( "Aa111111!", true )
                                           .action( "Confirm" )
                                           .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                     new String[]{ elements.mainPageHeadingText },
                                                                     true );

            updateAllSubscriberDetails( "Dr",
                                        firstName,
                                        lastName,
                                        "Bulgaria",
                                        null,
                                        jobTitle,
                                        "Director or equivalent",
                                        "Global Markets",
                                        "Consumer Markets",
                                        "Retail",
                                        "Online Retail" ).action( "Submit" )
                                                         .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                                   new String[]{ "Subscriber Information Updated Text" },
                                                                                   true )
                                                         .closePopUpMsg()
                                                         .verifyDisplayedElements( new String[]{ elements.subscriberInfoUpdateMsg },
                                                                                   new String[]{ "Subscriber Information Updated Message" },
                                                                                   false );

            send.loginToSend( subscriptionFormSendUser, subscriptionFormSendPassword ).contact.goToContactsPage()
                                                                                              .goToContactsSubPage( "Contacts" )
                                                                                              .searchRecord( email )
                                                                                              .startEditingRecord( email )
                                                                                              .accessContactDetails( "Name and Email" )
                                                                                              .verifyContactDetail( sendElements.email,
                                                                                                                    email,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.firstName,
                                                                                                                    firstName,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.lastName,
                                                                                                                    lastName,
                                                                                                                    true )
                                                                                              .accessContactDetails( "Other Fields" )
                                                                                              .verifyContactDetail( sendElements.jobTitle,
                                                                                                                    jobTitle,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.title,
                                                                                                                    title,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.country,
                                                                                                                    country,
                                                                                                                    true )
                                                                                              .accessContactDetails( "Custom Fields" )
                                                                                              .verifyContactDetail( sendElements.grade,
                                                                                                                    grade,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.function,
                                                                                                                    function,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.lineOfBusiness,
                                                                                                                    lineOfBusiness,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.sector,
                                                                                                                    sector,
                                                                                                                    true )
                                                                                              .verifyContactDetail( sendElements.subSector,
                                                                                                                    subSector,
                                                                                                                    true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageEmptyValueFirstName() throws Exception {

        String firstName = "";
        String lastName = "notEmptyValue";

        log.startTest( "Verify that a validation message is successfully displayed when a subscriber is updated with empty value for the First Name" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                  new String[]{ elements.mainPageHeadingText },
                                                                  true );

            updateAllSubscriberDetails( null,
                                        firstName,
                                        lastName,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null ).action( "Submit" )
                                              .verifyDisplayedElements( new String[]{ elements.getValidationMsgRequiredField( false,
                                                                                                                              true,
                                                                                                                              false ) },
                                                                        new String[]{ "The following fields cannot be blank. Please enter valid values : First Name" },
                                                                        true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageEmptyValueLastName() throws Exception {

        String firstName = "notEmptyValue";
        String lastName = "";

        log.startTest( "Verify that a validation message is successfully displayed when a subscriber is updated with empty value for the Last Name" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                  new String[]{ elements.mainPageHeadingText },
                                                                  true );

            updateAllSubscriberDetails( null,
                                        firstName,
                                        lastName,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null ).action( "Submit" )
                                              .verifyDisplayedElements( new String[]{ elements.getValidationMsgRequiredField( false,
                                                                                                                              false,
                                                                                                                              true ) },
                                                                        new String[]{ "The following fields cannot be blank. Please enter valid values : Last Name" },
                                                                        true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageEmptyValueEmail() throws Exception {

        String email = "";
        String firstName = "notEmptyValue";
        String lastName = "notEmptyValue";

        log.startTest( "Verify that a validation message is successfully displayed when a subscriber is updated with empty value for the Email" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                  new String[]{ elements.mainPageHeadingText },
                                                                  true );

            updateAllSubscriberDetails( null,
                                        firstName,
                                        lastName,
                                        null,
                                        email,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null ).action( "Submit" )
                                              .verifyDisplayedElements( new String[]{ elements.getValidationMsgRequiredField( true,
                                                                                                                              false,
                                                                                                                              false ) },
                                                                        new String[]{ "The following fields cannot be blank. Please enter valid values : Email" },
                                                                        true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplayValidationMessageAllRequiredFieldsEmpty() throws Exception {

        String email = "";
        String firstName = "";
        String lastName = "";

        log.startTest( "Verify that a validation message is successfully displayed when a subscriber is updated with all required fields empty" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                  new String[]{ elements.mainPageHeadingText },
                                                                  true );

            updateAllSubscriberDetails( null,
                                        firstName,
                                        lastName,
                                        null,
                                        email,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null ).action( "Submit" )
                                              .verifyDisplayedElements( new String[]{ elements.getValidationMsgRequiredField( true,
                                                                                                                              true,
                                                                                                                              true ) },
                                                                        new String[]{ "The following fields cannot be blank. Please enter valid values : Email, First Name, Last Name" },
                                                                        true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void unsuccessfullySaveSubscriberInformationNotValidEmail() throws Exception {

        String email = "notValidEmail";
        String firstName = "notEmptyValue";
        String lastName = "notEmptyValue";

        log.startTest( "Verify that a validation message is successfully displayed when a subscriber is trying to update his information with not valid email" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                  new String[]{ elements.mainPageHeadingText },
                                                                  true );

            updateAllSubscriberDetails( null,
                                        firstName,
                                        lastName,
                                        null,
                                        email,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null ).action( "Submit" )
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
    public void unsuccessfullySaveSubscriberInformationNotAllowedEmail() throws Exception {

        String email = "email@notAllowed.com";
        String firstName = "notEmptyValue";
        String lastName = "notEmptyValue";

        log.startTest( "Verify that a validation message is successfully displayed when a subscriber is trying to update his information with not allowed email" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscriptionFormMainPageHeading },
                                                                  new String[]{ elements.mainPageHeadingText },
                                                                  true );

            updateAllSubscriberDetails( null,
                                        firstName,
                                        lastName,
                                        null,
                                        email,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null ).action( "Submit" )
                                              .verifyDisplayedElements( new String[]{ elements.emailNotAllowedValidationTxt },
                                                                        new String[]{ elements.emailsAllowedValidationTxt },
                                                                        true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyHashedOutPasswordStoredConcepSend() throws Exception {

        String randNumber = driver.getTimestamp();
        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String password = "Aa111111!";

        log.startTest( "Verify that password is stored obfuscated / hidden / hashed out in Concep Send" );
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
                                     new String[]{ "Set Your Password Page Heading",
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( password, false )
                                           .fillPasswordInputField( password, true )
                                           .action( "Confirm" );

            send.loginToSend( subscriptionFormSendUser, subscriptionFormSendPassword ).contact.goToContactsPage()
                                                                                              .goToContactsSubPage( "Contacts" )
                                                                                              .searchRecord( notRegisteredEmail )
                                                                                              .startEditingRecord( notRegisteredEmail )
                                                                                              .accessContactDetails( "Custom Fields" )
                                                                                              .verifyContactDetail( sendElements.passwordCustomField,
                                                                                                                    password,
                                                                                                                    false );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyTreatedAsNotRegisteredSubscriberWhenDeletedFromConcepSend() throws Exception {

        String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String password = "Aa111111!";

        log.startTest( "Verify that a subscriber is treated as a new subscriber when is deleted from Concep Send" );
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
                                     new String[]{ "Set Your Password Page Heading",
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( password, false )
                                           .fillPasswordInputField( password, true )
                                           .action( "Confirm" );

            send.loginToSend( subscriptionFormSendUser, subscriptionFormSendPassword ).contact.goToContactsPage()
                                                                                              .goToContactsSubPage( "Contacts" )
                                                                                              .searchRecord( notRegisteredEmail );

            verifyDisplayedElements( new String[]{ sendElements.getContactTableRecord( notRegisteredEmail ) },
                                     new String[]{ "Table Record for contact " + notRegisteredEmail },
                                     true );

            send.contact.deleteRecord( notRegisteredEmail ).searchRecord( notRegisteredEmail );

            verifyDisplayedElements( new String[]{ sendElements.getContactTableRecord( notRegisteredEmail ) },
                                     new String[]{ "Table Record for contact " + notRegisteredEmail },
                                     false ).accessSubscriptionFormPage()
                                            .fillEmailInputField( notRegisteredEmail )
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
    public void successfullyDisplaySubscribeAndUnsubscribeInstructions() throws Exception {

        log.startTest( "Verify that a paragraph with instructions for subscribe and unsubscribe is successfully displayed" );
        try {

            accessSubscriptionFormPage().login( registeredSubscriberEmail, registeredSubscriberPassword )
                                        .verifyDisplayedElements( new String[]{ elements.subscribeInstructionsHeading,
                                                                          elements.subscribeInstructionsTxt,
                                                                          elements.unsubscribeInstructionsHeading,
                                                                          elements.unsubscribeInstructionsTxt,
                                                                          elements.unsubscribeAllInstructionsHeading,
                                                                          elements.unsubscribeAllInstructionsTxt,
                                                                          elements.submitInstructionsTxt },
                                                                  new String[]{ "To subscribe:",
                                                                          "Please select the area(s) you are interested in.",
                                                                          "To unsubscribe:",
                                                                          "Simply de-select the check-box(es).",
                                                                          "To unsubscribe from all:",
                                                                          "Simply select the \"unsubsribe from all\" check-box.",
                                                                          "Please click on ‘Submit’ after you have made your selections" },
                                                                  true );
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyDisplaySubscriberDefaultValues() throws Exception {

        String randNumber = driver.getTimestamp();

        String notRegisteredEmail = randNumber + "email@mailinator.com";
        String password = "Aa111111!";

        log.startTest( "Verify that any default values from Concep Send are successfully displayed" );
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
                                     new String[]{ "Set Your Password Page Heading",
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( password, false )
                                           .fillPasswordInputField( password, true )
                                           .action( "Confirm" )
                                           .verifySubscriberDetail( elements.titleSalutation, "Mr", true )
                                           .verifySubscriberDetail( elements.country, "Afghanistan", true )
                                           .verifySubscriberDetail( elements.emailInputField,
                                                                    notRegisteredEmail,
                                                                    true )
                                           .verifySubscriberDetail( elements.grade,
                                                                    "Director or equivalent",
                                                                    true )
                                           .verifySubscriberDetail( elements.function, "Advisory", true )
                                           .verifySubscriberDetail( elements.lineOfBusiness,
                                                                    "Consumer Markets",
                                                                    true )
                                           .verifySubscriberDetail( elements.sector,
                                                                    "Food, Drink and Consumer Goods",
                                                                    true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "all-tests" })
    public void successfullyResetPasswordAfterChangingAndBrowserBack() throws Exception {

        String email = "setpassworduser@mailinator.com";
        String password = "newPassw0rd!";
        String newPassword = "newPassw0rd!";

        log.startTest( "Verify that a subscriber can successfully reset his password from the set password link" );
        try {

            accessSubscriptionFormPage().login( email, password )
                                        .resetPassword( false )
                                        .verifyDisplayedElements( new String[]{ elements.emailSentHeading,
                                                                          elements.getSentEmailConfirmationMsg( email,
                                                                                                                true ) },
                                                                  new String[]{ "Email Sent",
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
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( newPassword, false )
                                           .fillPasswordInputField( newPassword, true )
                                           .action( "Confirm" )
                                           .browserCommand( "Back" )
                                           .accessSubscriptionFormPage()
                                           .login( email, newPassword )
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
    public void successfullyExpiredPasswordResetLinkAfterChange() throws Exception {

        String email = "1443189507email@mailinator.com";
        String newPassword = "Aa111111!";

        log.startTest( "Verify that a subscriber can successfully reset his password from the set password link" );
        try {

            accessSubscriptionFormPage().fillEmailInputField( email )
                                        .action( "Submit" )
                                        .verifyDisplayedElements( new String[]{ elements.enterPasswordPageHeading,
                                                                          elements.backBtn,
                                                                          elements.confirmBtn },
                                                                  new String[]{ "Enter Your Password",
                                                                          "Back Button",
                                                                          "Confirm Button" },
                                                                  true )
                                        .resetPassword( true )
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
                                             "Readonly Email Field",
                                             "Password Field",
                                             "Confirm Password",
                                             "Back Button",
                                             "Confirm Button" },
                                     true ).fillPasswordInputField( newPassword, false )
                                           .fillPasswordInputField( newPassword, true )
                                           .action( "Confirm" )
                                           .browserCommand( "Back" )
                                           .browserCommand( "Refresh" )
                                           .verifyDisplayedElements( new String[]{ elements.expiredLinkHeading,
                                                                             elements.expiredLinkTxt },
                                                                     new String[]{ "Invalid Link Text",
                                                                             "The URL you have tried to access has expired or is not a valid recovery URL" },
                                                                     true );

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}