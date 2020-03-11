package concep.selenium.mailinator;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class Mailinator {
    private GenericSeleniumWebDriver driver;
    private LogResults               log     = new LogResults();
    private Elements                 element = new Elements();

    public Mailinator() {

        this.driver = DriverAccessor.getDriverInstance();
    }

    public Mailinator accessMailinator() throws Exception {

        log.startStep( "Go to Mailinator" );
        driver.navigate( element.mailinatorURL );
        driver.waitForPageToLoad();
        log.endStep();

        return this;
    }

    public Mailinator searchCustomerEmail(
                                           String customerEmail ) throws Exception {

        log.startStep( "Log with email " + customerEmail );
        driver.type( element.emailField, customerEmail, driver.timeOut );
        driver.click( element.loginButton, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

    public Mailinator searchCustomerEmailSubPage(
                                                  String customerEmail ) throws Exception {

        log.startStep( "Log with email " + customerEmail );
        driver.type( element.emailField, customerEmail, driver.timeOut );
        driver.click( element.loginInsideButton, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }

    public Mailinator verifyCampaignExistance(
                                    String campaignSubject ) throws Exception {

        log.startStep( "Verify that campaign with subject " + campaignSubject + " exists in Mailinator" );
        log.endStep( driver.isElementPresent( element.campaignReceived( campaignSubject ), driver.timeOut ) );
        
        return this;
    }

    public Mailinator openEmailCampaign(
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
        driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.clickHereCampaignHlink, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        driver.close();
        driver.selectWindow();
    }

    public void generateClickOnResetPasswordLink() {

        driver.selectWindow();
        driver.switchToIframe( element.campaignContentIframe );
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
        driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.forwardFriendHyperlink, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        driver.switchToTopWindow();
        driver.click( element.forwardFriendURL, driver.timeOut );
        driver.close();
        log.endStep();
    }

    public void generateSpam() throws Exception {

        log.startStep( "Click on the link 'To unsubscribe, click here'" );
        driver.selectWindow();
        driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.unsubscribeHyperlink, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Click on the button 'Report Spam'" );
        driver.selectWindow();
        driver.click( element.reportSpamHyperlink, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.close();
        log.endStep();

    }

    public void generateOptOut() throws Exception {

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
    }

    public void clickLinkFromEmailContent(
                                           String linkXPath ) throws Exception {

        log.startStep( "Click on the content from the recently sent Subscription Form email" );
        driver.selectWindow();
        driver.switchToIframe( element.campaignContentIframe );
        driver.click( linkXPath, driver.timeOut );
        driver.waitForPageToLoad();
        driver.selectWindow();
        log.endStep();
    }

    public void clickLinkFromEmailContentPM(
                                           String linkXPath ) throws Exception {

        log.startStep( "Click on the content from the recently sent Subscription Form email" );
        driver.selectWindow();
        driver.switchToIframe( element.pmContentIFrame );
        driver.click( linkXPath, driver.timeOut );
        driver.waitForPageToLoad();
        driver.selectWindow();
        log.endStep();
    }
}
