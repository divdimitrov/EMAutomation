package concep.selenium.zohoMail;

public class ElementsZoho {

    protected final String zohoURL            			  = "https://mail.zoho.com";
    protected final String goToLogin  	      			  = "//div[contains(@class, 'zgh-utilities')]/div[contains(@class, 'zgh-accounts')]/a[contains(@class, 'zgh-login')]";
    protected final String emailField         			  = "//input[@id='lid']";
    protected final String passwordField	  			  = "//input[@id='pwd']";
    protected final String signInButton       			  = "//div[@id='signin_submit']";
    protected final String unreadMessages	  			  = "//div[@id='zm_unread']/div[contains(@class, 'zmTreeNDWra')]/div[contains(@class, 'zmTreeText zmBold')]/span[contains(@class, 'jsZmTreeText')]";
    protected final String confirmRegistrationEmailUnread = "//div[contains(@class, 'zmLFCD')]/div[contains(@class, 'zmLContent')]/span[contains(@class, 'zmLSub')]/span[contains(text(), 'Please, confirm your registration')]";
    																																									
    
    protected final String zohoEmail		  			  = "admin@concepdev.com";
    protected final String passwordZoho		  			  = "Password2018!";
    //protected String       loginInsideButton = "//div[@id='pubinboxfield']/btn";
    
    public String campaignReceived(
                                    String campaignName ) {

        return "//td[contains(text(),'" + campaignName + "')]";
    }

    protected final String pleaseConfirmYourRegistrationEmail = "//div[@id='1563804248233100001']/div[contains(@class, 'zmLFCD')]/div[contains(@class, 'zmLContent')]/span[contains(@class, 'zmLSub')]/span[contains(text(), 'Please, confirm your registration')]";
    protected final String clickHereCampaignHlink = "//div[contains(@class, 'x_-92625669cbT-content x_-92625669cbT-content-editable')]/a[2][contains(text(), 'Click here')]";
    protected final String forwardFriendHyperlink = "//div/span/a[1][contains(text(), 'Forward to a Friend')]";
    //protected final String forwardFriendURL       = "//a[@id='CBUI-forwardfriend-url']";
    protected final String unsubscribeHyperlink   = "//span/a[2][contains(text(), 'Unsubscribe')]";
    
    //protected final String reportSpamHyperlink    = "//span[contains(text(), 'Report Spam')]";
    //protected final String optOutRatio            = "//input[@id='radIndividual']";
    //protected final String optOutButton           = "//span[contains(text(), 'Opt Out')]";
    //public String          campaignContentIframe  = "//iframe[@id='publicshowmaildivcontent']";
    //public String          pmContentIFrame  	  = "//iframe[@id='msg_body']";
    
    
    //for PM:
    //protected final String pleaseConfirmYourRegistrationEmail = "Please confirm you registration";
   
    protected final String resetYourPasswordEmail = "Reset your password , please";
    
    protected final String clickOnConfirmationEmail = "//table[contains(@class, 'cbT-block')]/tbody/tr/td/table[contains(@class, 'cbT-block-inner')]"
    		+ "/tbody/tr/td/div[contains(@class, 'cbT-content cbT-content-editable')]";
   
    protected final String clickOnResetPasswordEmail = "//table[contains(@class, 'cbT-block')]/tbody/tr/td/table[contains(@class, 'cbT-block-inner')]"
    		+ "/tbody/tr/td/div[contains(@class, 'cbT-content cbT-content-editable')]/a[contains(text(), 'Click here')]";
    
    //protected final String emptyInbox				 = "//div[@id='publicm8rguy']/a[contains(text(), '[ This Inbox is Currently Empty ]')]";
    
}
