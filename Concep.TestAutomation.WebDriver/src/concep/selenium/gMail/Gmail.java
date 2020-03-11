package concep.selenium.gMail;

import concep.selenium.core.DriverAccessor;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class Gmail {
    private GenericSeleniumWebDriver driver;
    private LogResults               log     = new LogResults();
    private ElementsGmail            element = new ElementsGmail();

    public Gmail() {

        this.driver = DriverAccessor.getDriverInstance();
    }

    public Gmail accessGmail() throws Exception {

        log.startStep( "Go to Gmail" );
        driver.navigate( element.gmailURL );
        driver.waitForPageToLoad();
        log.endStep();

        return this;
    }
    
    public Gmail fillEmailToLogin() throws Exception {

        log.startStep("Click on button Login");
        driver.click( element.emailField, driver.timeOut );
        driver.type( element.emailField, element.gmailEmail, driver.timeOut );
        driver.click( element.forwardButton, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        Thread.sleep(3000);
        
        return this;
    }
    
    
    public Gmail logInGmail() throws Exception {

        log.startStep("Type password and login");
        //driver.click( element.passwordField, driver.timeOut );
        driver.type( element.passwordField, element.gmailPassword, driver.timeOut );
        driver.click( element.nextButton, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }
    
    public Gmail goToUnread() throws Exception {

        log.startStep("Search for Unread messages");
        driver.click( element.searchField, driver.timeOut );
        driver.type( element.searchField, element.unreadMessages, driver.timeOut );
        driver.click( element.searchButton, driver.timeOut);
        //driver.findElement(element.searchField.sendKeys(Keys.ENTER);
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();
        
        return this;
    }

    public Gmail openConfirmationEmail() throws Exception {

        log.startStep( "Click on the confirmation email" );
       // List<WebElement> email = driver.findElements(By.cssSelector("div.xT>div.y6>span>b"), driver.timeOut);//(By.cssSelector("div.xT>div.y6>span>b");
        //WebElement clickOnEmail = driver.click("//div[contains(@class, 'all_message-min')]/div[contains(@class, 'all_message-min_autor ng-binding')][2]", driver.timeOut);
        
       // driver.click("//div[contains(@class, 'all_message-min')]/div[contains(@class, 'all_message-min_autor ng-binding')][2]", driver.timeOut);
       // clickOnEmail[0].Click();
        
        //List<WebElement> mailsObj = driver.findElements(By.xpath("//*[@class='zA zE']"), driver.timeOut );
        //mailsObj.get(0).click();
        
              
        //driver.get
        
        
        driver.click( element.confirmRegistrationEmailUnread, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return this;
    }

   

    public Gmail verifyCampaignExistance(
                                    String campaignSubject ) throws Exception {

        log.startStep( "Verify that campaign with subject " + campaignSubject + " exists in Mailinator" );
        log.endStep( driver.isElementPresent( element.campaignReceived( campaignSubject ), driver.timeOut ) );
        
        return this;
    }

    public Gmail openEmailCampaign(
                                         String campaignName ) throws Exception {

        log.startStep( "Select campaign " + campaignName );
        driver.click( element.campaignReceived( campaignName ), driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        //driver.switchToIframe( element.campaignContentIframe );
        log.endStep();

        return this;
    }
    
   
    public void generateClickConfirmationEmail() {

        driver.selectWindow();
        //driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.clickHereConfirmationEmail, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        driver.close();
        driver.selectWindow();
    }

    public void generateClickOnResetPasswordLink() {

        driver.selectWindow();
        //driver.switchToIframe( element.campaignContentIframe );
        driver.click( element.clickHereResetPassword, driver.timeOut );
        driver.waitForPageToLoad();
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.selectWindow();
        driver.close();
        driver.selectWindow();
    }
    /*public void generateForward() throws Exception {

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
