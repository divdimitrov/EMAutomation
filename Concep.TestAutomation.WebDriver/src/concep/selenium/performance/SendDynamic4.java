package concep.selenium.performance;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.Theme;

public class SendDynamic4 extends BasePerformance {

    private int loopCount;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "loopCount" })
    public void startSelenium(
                               @Optional("1") String loopCount ) throws Exception {

        driver.init( "config/firefox.Performance" );
        sendUserName = driver.config.getProperty( "msdSendUser" );
        sendPassword = driver.config.getProperty( "sendPassword" );
        driver.url = driver.config.getProperty( "url" );
        this.loopCount = Integer.parseInt( loopCount );
    }

    public void surveyResponseSubmition() throws Exception {

        String number = driver.getTimestamp();
        String surveyType = "Survey";
        String campaignType = "Environmental Event";

        log.startTest( "Verify that if too many responses ("
                       + this.loopCount
                       + ") are awaiting on the Platform's DB to be synchronized they will be successfully extracted" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( number
                                                                                    + "surveyPerformance" )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType, campaignType );

            send.msdSurvey.dynamicQuestion.editQuestionType( "A free text question" )
                                          .enterQuestion( FTcontactField.EMAIL.question )
                                          .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.LASTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                          .updateQuestion()
                                          .addNewQuestionType( "Free Text", element.ftQId )
                                          .enterQuestion( FTcontactField.FIRSTNAME.question )
                                          .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                          .updateQuestion()
                                          .editQuestionType( "A multiple choice question" )
                                          .fillinMCQAnswers( MCcontactFiled.GENDER.option_1,
                                                             MCcontactFiled.GENDER.option_2 )
                                          .enterQuestion( MCcontactFiled.GENDER.question )
                                          .mapMCQuestionToContactField( MCcontactFiled.GENDER )
                                          .deleteMcQuestionAnswers( 2 )
                                          .addMCResponseCodeOptions()
                                          .updateQuestion();

            send.msdSurvey.saveAndContinueToTheme().selectTheme( Theme.TABLE.type ).saveAndContinueToDeploy();

            for( int i = 0; i < this.loopCount; i++ ) {

                send.msdSurvey.viewAndDeployTheSurvey().dynamicQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                          number
                                                                                                  + "mail"
                                                                                                  + i
                                                                                                  + "@test.com" )
                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                          number + "lastName"
                                                                                                  + i )
                                                                       .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                          "number +firstName"
                                                                                                  + i )
                                                                       .answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                          new String[]{ MCcontactFiled.GENDER.option_1 } );

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

    @Test(groups = { "Dynamic4" })
    public void sendSurveyResponse() throws Exception {

        surveyResponseSubmition();
    }

    @AfterClass(alwaysRun = true)
    public void shutDownSelenium() {

        driver.stopSelenium();

    }
}
