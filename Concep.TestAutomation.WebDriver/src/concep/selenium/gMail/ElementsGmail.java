package concep.selenium.gMail;

public class ElementsGmail {

    protected final String gmailURL            			  = "https://gmail.com";
    
    protected final String gmailEmail					  = "qaconcep@gmail.com";
    protected final String gmailPassword				  = "Sofia2013!";
    protected final String unreadMessages				  = "label:unread";
    
    protected final String emailField					  = "//input[@id='identifierId']";
    protected final String forwardButton				  = "//span[contains(@class, 'CwaK9')]";
    
    protected final String nextButton					  = "//div[@id='passwordNext']";
    protected final String searchButton 				  = "//button[contains(@class, 'gb_kf gb_lf')]";
    
    protected final String passwordField	  			  = "//input[@type='password' and @class='whsOnd zHQkBf']";
    protected final String searchField					  = "//input[@class='gb_bf' and @aria-label='Search mail']"; //"//td[contains(@class, 'gsib_a gb_bf')]";
    
    protected final String confirmRegistrationEmailUnread = "//span[contains(@class, 'bqe')][1][contains(text(), 'Please, confirm your registration')]";
    protected final String resetPasswordEmail			  = "//span[contains(@class, 'bqe')][contains(text(), 'Reset your password , please')]";
    
    protected final String clickHereConfirmationEmail     = "//a[2][contains(text(), 'Click here')]";
    protected final String clickHereResetPassword		  = "//a[2][contains(text(), 'Click here')]";
    
    
        
    public String campaignReceived(
                                    String campaignName ) {

        return "//td[contains(text(),'" + campaignName + "')]";
    }

    
    
    //for PM:
    //protected final String pleaseConfirmYourRegistrationEmail = "Please confirm you registration";
   
   
    
    //protected final String emptyInbox				 = "//div[@id='publicm8rguy']/a[contains(text(), '[ This Inbox is Currently Empty ]')]";
    
}
