package concep.selenium.send;

public class MSD4Connection extends Connections<MSD4Connection> {

    protected String msd4ConnectionName     = "http://qa-crm4-app1.concep.com/msd4test";
    protected String msd4ConnectionUsername = "dom\\Pro4";
    protected String msd4ConnectionPassword = "Password12";

    public MSD4Connection createConnection() throws Exception {

        return super.createConnection( msd4ConnectionName, msd4ConnectionUsername, msd4ConnectionPassword );
    }

    public MSD4Connection loginToExistingConnection() throws Exception {

        return loginToExistingConnection( msd4ConnectionUsername, msd4ConnectionPassword );
    }

    public MSD4Connection enableSurveys() throws Exception {

        return enableSurveys( element.msd4ConnectionSurveyEnableBox );
    }

    public MSD4Connection disableSurveys() throws Exception {

        return disableSurveys( element.msd4ConnectionSurveyEnableBox );
    }

    public MSD4Connection updateExistingConnection(
                                                    boolean isSurveyEnabled ) throws Exception {

        try {

            if( driver.isElementPresent( element.connectionWindow, driver.timeOut ) ) {

                loginToExistingConnection();

            } else {

                createConnection();
            }

            log.startStep( "Verify that the Features window is displayed" );
            log.endStep( driver.isElementPresent( element.msd4ConnectionSurveyEnableBox, driver.timeOut ) );

            if( isSurveyEnabled ) {
                enableSurveys();
            } else {
                disableSurveys();
            }

            saveAndCloseConnectionPage();

            log.startStep( "check that connection is succesfully created" );
            log.endStep( driver.isElementPresent( element.connectionWindow, driver.timeOut ) );

        } catch( Exception e ) {
            log.endStep( false );
            throw e;

        }

        return this;
    }

}
