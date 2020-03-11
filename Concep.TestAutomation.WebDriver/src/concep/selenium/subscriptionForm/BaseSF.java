package concep.selenium.subscriptionForm;

import java.security.InvalidParameterException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import concep.selenium.mailinator.Mailinator;
import concep.selenium.send.Send;

public class BaseSF {

    protected final String             registeredSubscriberEmail    = "subscriptionformtestuser@mailinator.com";
    protected final String             registeredSubscriberPassword = "Aa111111!";

    protected Elements                 elements                     = new Elements();
    protected GenericSeleniumWebDriver driver                       = DriverAccessor.getDriverInstance();
    protected LogResults               log                          = new LogResults();
    protected Send                     send                         = new Send();
    protected Mailinator               mailinator                   = new Mailinator();

    protected String                   subscriptionFormUrl;
    protected String                   subscriptionFormSendUser;
    protected String                   subscriptionFormSendPassword;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config" })
    public void startSelenium(
                               @Optional("config/firefox.SF") String configLocation ) throws Exception {

        driver.init( configLocation );
        subscriptionFormUrl = driver.config.getProperty( "subscriptionFormUrl" );
        subscriptionFormSendUser = driver.config.getProperty( "subscriptionFormSendUser" );
        subscriptionFormSendPassword = driver.config.getProperty( "subscriptionFormSendPassword" );
        driver.url = driver.config.getProperty( "url" );
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {

        driver.stopSelenium();
    }

    protected BaseSF accessSubscriptionFormPage() throws Exception {

        log.startStep( "Navigate to the Subscription Form landing page" );
        driver.open( subscriptionFormUrl );
        log.endStep();

        return this;
    }

    protected BaseSF fillEmailInputField(
                                          String email ) throws Exception {

        log.startStep( "Type '" + email + "' in the 'Email' input field" );
        driver.type( elements.emailInputField, email, driver.timeOut );
        log.endStep();

        return this;
    }

    protected BaseSF fillPasswordInputField(
                                             String password,
                                             boolean isPasswordConfirmation ) throws Exception {

        if( isPasswordConfirmation ) {

            log.startStep( "Type '" + password + "' in the 'Password' input field" );
            driver.type( elements.passwordConfirm, password, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Type '" + password + "' in the 'Password' input field" );
            driver.type( elements.password, password, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    protected BaseSF login(
                            String email,
                            String password ) throws Exception {

        this.fillEmailInputField( email ).action( "Submit" );
        this.fillPasswordInputField( password, false ).action( "Confirm" );

        return this;
    }

    protected BaseSF subscribeGroup(
                                     String[] targetGroups ) throws Exception {

        if( driver.isChecked( elements.unsubscribeAll ) ) {

            log.startStep( "Uncheck the 'Unsubscribe from all areas of interest' checkbox" );
            driver.click( elements.unsubscribeAll, driver.timeOut );
            log.endStep();
        }

        for( int i = 0; i < targetGroups.length; i++ ) {

            if( !driver.isChecked( elements.getGroupCheckbox( targetGroups[i] ) ) ) {

                log.startStep( "Subscribe to " + targetGroups[i] + "' group" );
                driver.click( elements.getGroupCheckbox( targetGroups[i] ), driver.timeOut );
                log.endStep();
            }
        }

        return this;
    }

    protected BaseSF unsubscribeGroup(
                                       String[] targetGroups,
                                       boolean isGlobal ) throws Exception {

        if( isGlobal ) {

            if( !driver.isChecked( elements.unsubscribeAll ) ) {

                log.startStep( "Click on the 'Unsubscribe from all areas of interest' checkbox" );
                driver.click( elements.unsubscribeAll, driver.timeOut );
                log.endStep();
            }

        } else {

            for( int i = 0; i < targetGroups.length; i++ ) {

                if( driver.isChecked( elements.getGroupCheckbox( targetGroups[i] ) ) ) {

                    log.startStep( "Unsubscribe to " + targetGroups[i] + "' group" );
                    driver.click( elements.getGroupCheckbox( targetGroups[i] ), driver.timeOut );
                    log.endStep();
                }
            }
        }

        return this;
    }

    protected BaseSF disableGlobalUnsubscribe() throws Exception {

        if( driver.isChecked( elements.unsubscribeAll ) ) {

            log.startStep( "Uncheck 'Unsubscribe from all areas of interest' checkbox" );
            driver.click( elements.unsubscribeAll, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    protected BaseSF action(
                             String actionType ) throws Exception {

        switch( actionType ){

            case "Submit":

                log.startStep( "Click on the 'Submit' button" );
                driver.click( elements.submitBtn, driver.timeOut );
                driver.waitForAjaxToComplete( driver.timeOut );
                log.endStep();

                break;

            case "Confirm":

                log.startStep( "Click on the 'Confirm' button" );
                driver.click( elements.confirmBtn, driver.timeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;

            case "Back":

                log.startStep( "Click on the 'Back' button" );
                driver.click( elements.backBtn, driver.timeOut );
                driver.waitForPageToLoad();
                log.endStep();

                break;

            case "Register":

                log.startStep( "Click on the 'Register' button" );
                driver.click( elements.registerBtn, driver.timeOut );
                log.endStep();

                break;

            default:

                throw new InvalidParameterException( "The following parameter 'actionType' has an invalid value: '"
                                                     + actionType + "'" );
        }

        return this;
    }

    protected BaseSF resetPassword(
                                    boolean isForgotten ) throws Exception {

        if( isForgotten ) {

            log.startStep( "Click on the 'Reset password' link" );
            driver.click( elements.forgottenPasswordLink, driver.timeOut );
            log.endStep();

        } else {

            log.startStep( "Click on the 'Reset password' link" );
            driver.click( elements.resetPasswordLink, driver.timeOut );
            log.endStep();
        }

        return this;
    }

    protected BaseSF closePopUpMsg() throws Exception {

        log.startStep( "Click on the 'X' button from the pop-up message" );
        driver.click( elements.closeBtn, driver.timeOut );
        log.endStep();

        return this;
    }

    protected BaseSF verifyDisplayedElements(
                                              String[] uiElementXpaths,
                                              String[] uiElementNames,
                                              boolean shouldBeDisplayed ) throws Exception {

        if( !shouldBeDisplayed ) {

            for( int i = 0; i < uiElementXpaths.length; i++ ) {

                log.startStep( "Verify that the element '" + uiElementNames[i] + "' is not displayed" );
                log.endStep( !driver.isElementPresent( uiElementXpaths[i], driver.negativeTimeOut ) );
            }

        } else {

            for( int i = 0; i < uiElementXpaths.length; i++ ) {

                log.startStep( "Verify that the element '" + uiElementNames[i]
                               + "' is successfully displayed" );
                log.endStep( driver.isElementPresent( uiElementXpaths[i], driver.timeOut ) );
            }
        }

        return this;
    }

    protected BaseSF verifySubscriberDetail(
                                             String xPath,
                                             String comparingValue,
                                             boolean shouldBeEqual ) throws Exception {

        String detailValue = driver.getInputFieldValue( xPath );

        if( shouldBeEqual ) {

            log.startStep( "Verify that subscriber detail '" + detailValue
                           + "' from Subscription Form is equal to '" + comparingValue );
            log.endStep( detailValue.equals( comparingValue ) );

        } else {

            log.startStep( "Verify that subscriber detail '" + detailValue
                           + "' from Subscription Form is not equal to '" + comparingValue );
            log.endStep( !detailValue.equals( comparingValue ) );
        }

        return this;
    }

    protected BaseSF browserCommand(
                                     String command ) throws Exception {

        log.startStep( "Browser " + command );
        driver.browserInteract( command );
        log.endStep();

        return this;
    }
}