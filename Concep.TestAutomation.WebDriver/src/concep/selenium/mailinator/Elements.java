package concep.selenium.mailinator;

public class Elements {

    protected final String mailinatorURL     = "http://mailinator.com/";
    protected final String emailField        = "//input[@id='inboxfield']";//div[@id='publicm8rguy']
    protected final String loginButton       = "//button[contains(text(), 'Go')]";
    protected String       loginInsideButton = "//div[@id='pubinboxfield']/btn";
    
    public String campaignReceived(
                                    String campaignName ) {

        return "//td[contains(text(),'" + campaignName + "')]";
    }
    

    protected final String clickHereCampaignHlink = "//a[contains(text(), 'Click here')]";
    protected final String forwardFriendHyperlink = "//a[contains(text(), 'Forward to a Friend')]";
    protected final String forwardFriendURL       = "//a[@id='CBUI-forwardfriend-url']";
    protected final String unsubscribeHyperlink   = "//font[contains(text(), 'To unsubscribe, click')]/a[contains(text(), 'here')]";
    protected final String reportSpamHyperlink    = "//span[contains(text(), 'Report Spam')]";
    protected final String optOutRatio            = "//input[@id='radIndividual']";
    protected final String optOutButton           = "//span[contains(text(), 'Opt Out')]";
    public String          campaignContentIframe  = "//iframe[@id='publicshowmaildivcontent']";
    public String          pmContentIFrame  	  = "//iframe[@id='msg_body']";
    
    
    //for PM:
    protected final String pleaseConfirmYourRegistrationEmail = "Please confirm you registration";
   
    protected final String resetYourPasswordEmail = "Reset your password , please";
    
    protected final String clickOnConfirmationEmail = "//table[contains(@class, 'cbT-block')]/tbody/tr/td/table[contains(@class, 'cbT-block-inner')]"
    		+ "/tbody/tr/td/div[contains(@class, 'cbT-content cbT-content-editable')]";
   
    protected final String clickOnResetPasswordEmail = "//table[contains(@class, 'cbT-block')]/tbody/tr/td/table[contains(@class, 'cbT-block-inner')]"
    		+ "/tbody/tr/td/div[contains(@class, 'cbT-content cbT-content-editable')]/a[contains(text(), 'Click here')]";
    
    protected final String emptyInbox				 = "//div[@id='publicm8rguy']/a[contains(text(), '[ This Inbox is Currently Empty ]')]";
    
}
