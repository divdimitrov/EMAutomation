package concep.selenium.performance;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.Theme;

public class SendInterAction extends BasePerformance {

    private int loopCount;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "loopCount" })
    public void beforeClassSetUP(
                                  @Optional("1000") String loopCount ) throws Exception {

        driver.init( "config/firefox.Performance" );
        sendUserName = driver.config.getProperty( "iaSendUser" );
        sendPassword = driver.config.getProperty( "sendPassword" );
        driver.url = driver.config.getProperty( "url" );
        this.loopCount = Integer.parseInt( loopCount );

    }

    public void surveyResponseSubmition() throws Exception {

        log.startTest( "Verify that if too many responses ("
                       + this.loopCount
                       + ") are awaiting on the Platform's DB to be synchronized they will be successfully extracted" );

        String number = driver.getTimestamp();
        String lastName = "performance1000resp-1202";
        String firstName = "performance1000resp-1202";
        String surveyType = "Survey";
        String marketingList = "PerformanceCM3 5000recipients2500act1000res";

        try {

            //Go to Survey Page.
            loginToSend().goToSurveyPage().iaSurvey.createSurveyAndTypeSurveyName( number
                                                                                   + "surveyPerformance" )
                                                   .selectSurveyFolders( surveyType, marketingList );

            send.iaSurvey.iaQuestion.editFTQAndMapItToEmail( false )
                                    .editMCQAndMapItToGenderG()
                                    .addFTQAndMapItToLastName( false )
                                    .dragAndDropFTQAndMapItToFirstName(false);

            // Save and deploy survey
            send.iaSurvey.saveAndSelectTheme( Theme.AQUA.type );
            for( int i = 1; i <= this.loopCount; i++ ) {
                send.iaSurvey.viewAndDeployTheSurvey().iaQuestion.answerMcQuestion( MCcontactFiled.GENDERGLOBAL.question,
                                                                                    new String[]{ MCcontactFiled.GENDERGLOBAL.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    firstName + "-" + i
                                                                                            + "@concepdev.com" )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName + "-" + i )
                                                                 .answerFtQuestion(FTcontactField.FIRSTNAME.question,
                                                                		 			firstName + "-" + i);

                send.msdSurvey.surveyNext();
                driver.close();
                driver.selectWindow();
            }

            driver.close();
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "InterAction" })
    public void sendSurveyResponse() throws Exception {

        surveyResponseSubmition();
    }

    @AfterClass(alwaysRun = true)
    public void shutDownSelenium() {

        driver.stopSelenium();

    }
}
