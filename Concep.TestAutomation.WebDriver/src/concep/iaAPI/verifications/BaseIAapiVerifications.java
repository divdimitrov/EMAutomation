package concep.iaAPI.verifications;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.IA.Api.Contact;
import concep.IA.Api.InterAction;
import concep.json.Json;
import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public class BaseIAapiVerifications {

    private String                            iaUserName;
    private String                            iaPassword;
    private String                            iaUrl;

    public Properties                         config;
    protected Json                            json;
    protected String                          iaResultsFileLocation;
    protected InterAction                     interAction;
    protected static GenericSeleniumWebDriver driver = DriverAccessor.getDriverInstance();
    protected LogResults                      log    = new LogResults();

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config", "iaResults" })
    public void IAProperties(
                              @Optional("config/firefox.IAconnectorV1.1") String configLocation,
                              @Optional("C:/Concep.TestAutomation.WebDriver/iaResults/") String iaResults ) throws Exception {

        init( configLocation );
        iaUserName = config.getProperty( "iaConnectionUsername" );
        iaPassword = config.getProperty( "iaConnectionPassword" );
        iaUrl = config.getProperty( "iaConnectionName" );
        interAction = new InterAction( iaUserName, iaPassword, iaUrl );
        iaResultsFileLocation = iaResults;
        json = new Json();;

    }

    public boolean isElementInTheList(
                                       List<String> list,
                                       String element ) {

        boolean results = false;
        for( int i = 0; i < list.size(); i++ ) {
            if( list.get( i ).trim().equals( element ) )
                results = true;
        }
        return results;
    }

    public void init(
                      String configProperties ) throws FileNotFoundException, Exception {

        config = new Properties();
        config.load( new FileInputStream( configProperties ) );
    }

    protected void iaParametersVerification(
                                             String testDescription,
                                             String fileName ) throws Exception {

        Contact sendContact = new Contact();

        log.startTest( testDescription );

        try {

            //Read from file
            log.startStep( "Read from file " + fileName );
            sendContact = json.readIt( iaResultsFileLocation, fileName );
            log.endStep();

            //Establish connection to IA
            log.startStep( "Establish connection to IA" );
            interAction.connection.login();
            log.endStep();

            //Get contact attributes
            log.startStep( "Get contact attributes for " + sendContact.lastName );
            interAction.getPersonAtrributs( "findContacts", "lastName", sendContact.lastName );
            interAction.getContactAdditionalFields( interAction.contact.contactId );
            interAction.getPersonAtrributs( "findFoldersForContact",
                                            "contactId",
                                            interAction.contact.contactId );
            log.endStep();

            //Get folder name
            log.startStep( "the folder Name of the " + sendContact.lastName + " is: "
                           + interAction.contact.folderName );
            log.endStep();

            for( int i = 0; i < sendContact.answers.size(); i++ ) {
                log.startStep( "verify that " + sendContact.answers.get( i ) + " exisit in IA" );
                log.endStep( isElementInTheList( interAction.contact.answers,
                                                 sendContact.answers.get( i ) ) );
            }

            for( int i = 0; i < sendContact.phoneNumber.size(); i++ ) {

                log.startStep( "verify that " + sendContact.phoneNumber.get( i ) + " exisit in IA" );
                log.endStep( isElementInTheList( interAction.contact.phoneNumber,
                                                 sendContact.phoneNumber.get( i ) ) );
            }

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
}
