package concep.selenium.performance;

import java.net.MalformedURLException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SendConnector extends BasePerformance {

    private int      loopCount;
    private String[] emails;
    private String   campaignSubject;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "loopCount", "emails", "campaignSubject" })
    public void startSelenium(
                               @Optional("1") String loopCount,
                               @Optional("ali.hamadi@mailinator.com") String email,
                               @Optional("campaignubject") String campaigSubject ) throws Exception {

        driver.init( "config/firefox.Performance" );
        driver.url = driver.config.getProperty( "mailinatorUrl" );
        this.loopCount = Integer.parseInt( loopCount );
        this.campaignSubject = campaigSubject;
        this.emails = email.split( "," );

    }

    public void generateEmailCampaignActivites(
                                                String[] customerEmail,
                                                String campaignName ) throws Exception {

        log.startTest( "Verify that activities can be successfully generated against the connetor" );
        try {
            mailinator.accessMailinator();
            mailinator.searchCustomerEmail( customerEmail[0] );
            for( int i = 0; i < this.emails.length; i++ ) {
                mailinator.searchCustomerEmailSubPage( customerEmail[i] ).openEmailCampaign( campaignName );
                log.startStep( "generating " + this.loopCount + " clicks" );
                for( int j = 0; j < this.loopCount; j++ ) {
                    mailinator.generateClick();
                    driver.selectWindow();
                }
                log.endStep();
            }
            driver.close();
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "Dynamic-Connector" })
    public void generatActivity() throws Exception {

        generateEmailCampaignActivites( this.emails, this.campaignSubject );
    }

    @AfterClass(alwaysRun = true)
    public void shutDownSelenium() {

        driver.stopSelenium();

    }
}
