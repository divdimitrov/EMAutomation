package concep.selenium.zohoMail;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class Zoho {
    private GenericSeleniumWebDriver driver;
    private LogResults               log     = new LogResults();
    private ElementsZoho             element = new ElementsZoho();

    public Zoho() {

        this.driver = DriverAccessor.getDriverInstance();
    }

    public Zoho accessZoho() throws Exception {

        log.startStep( "Go to Zoho mail" );
        driver.navigate( element.zohoURL );
        driver.waitForPageToLoad();
        log.endStep();

        return this;
    }
    
    public Zoho accessBtnLogin() throws Exception {

        log.startStep("Click on button Login");
        driver.click( element.goToLogin, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }
    
    public Zoho logInEmail() throws Exception {

        log.startStep("Log with email and password");
        driver.type( element.emailField, element.zohoEmail, driver.timeOut );
        driver.type( element.passwordField, element.passwordZoho, driver.timeOut );
        driver.click( element.signInButton, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }
    
    public Zoho goToUnread() throws Exception {

        log.startStep("Click on Unread");
        driver.click( element.unreadMessages, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }

    public Zoho openConfirmationEmail() throws Exception {

        log.startStep( "Click on the confirmation email" );
        driver.click( element.confirmRegistrationEmailUnread, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

   

    public Zoho verifyCampaignExistance(
                                    String campaignSubject ) throws Exception {

        log.startStep( "Verify that campaign with subject " + campaignSubject + " exists in Mailinator" );
        log.endStep( driver.isElementPresent( element.campaignReceived( campaignSubject ), driver.timeOut ) );
        
        return this;
    }

    public Zoho openEmailCampaign(
                                         String campaignName ) throws Exception {

        log.startStep( "Select campaign " + campaignName );
        driver.click( element.campaignReceived( campaignName ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        //driver.switchToIframe( element.campaignContentIframe );
        log.endStep();

        return this;
    }
    
   
    public void generateClick() {

        driver.selectWindow();
        //driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.clickHereCampaignHlink, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        driver.close();
        driver.selectWindow();
    }

    public void generateClickOnResetPasswordLink() {

        driver.selectWindow();
        //driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.clickOnResetPasswordEmail, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        driver.close();
        driver.selectWindow();
    }
    public void generateForward() throws Exception {

        log.startStep( "Click on 'Forward to a Friend' link" );
        driver.selectWindow();
        driver.click( element.forwardFriendHyperlink, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        driver.switchToTopWindow();
        //driver.click( element.forwardFriendURL, driver.timeOut );
        driver.close();
        log.endStep();

    }

    public void generateSpam() throws Exception {

        log.startStep( "Click on the link 'To unsubscribe, click here'" );
        driver.selectWindow();
        //driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.unsubscribeHyperlink, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Click on the button 'Report Spam'" );
        driver.selectWindow();
        //driver.click( element.reportSpamHyperlink, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.close();
        log.endStep();

    }

    /*public void generateOptOut() throws Exception {

        log.startStep( "Click on the link 'To unsubscribe, click here'" );
        driver.selectWindow();
        driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.unsubscribeHyperlink, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Check the Opt Out check box" );
        driver.selectWindow();
        driver.click( element.optOutRatio, driver.timeOut );
        log.endStep();

        log.startStep( "Click on the button 'Opt Out'" );
        driver.click( element.optOutButton, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.close();
        log.endStep();
    }*/

    public void clickLinkFromEmailContent(
                                           String linkXPath ) throws Exception {

        log.startStep( "Click on the content from the recently sent Subscription Form email" );
        driver.selectWindow();
        //driver.switchToIframe( element.campaignContentIframe );
        driver.click( linkXPath, driver.timeOut );
        driver.waitForPageToLoad();
        driver.selectWindow();
        log.endStep();
    }

    public void clickLinkFromEmailContentPM(
                                           String linkXPath ) throws Exception {

        log.startStep( "Click on the content from the recently sent Subscription Form email" );
        driver.selectWindow();
        //driver.switchToIframe( element.pmContentIFrame );
        driver.click( linkXPath, driver.timeOut );
        driver.waitForPageToLoad();
        driver.selectWindow();
        log.endStep();
    }
}
