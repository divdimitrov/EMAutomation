/*

*/

package concep.selenium.Dynamic;

import org.testng.annotations.Test;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.Theme;

public class UpdatingCrmData extends BaseMSD {

    /**
     * 
     * Verify that the first name of an existed contact can successfully be updated
     * 
     * @throws Exception 
     * 
     */
    @Test(groups = { "all-tests", "updating-data", "smoke-tests" })
    public void successfullyUpdatingFirstNameContactProperty() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "UpdatingFirstName";
        String emailAddress = msdExistingContact;
        String firstName = number + "firstName";

        log.startTest( "Verify that the first name of an existed contact can successfully be updated" );

        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType1, campaignName1 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToMartialStatus()
                                                                                                                      .addFTQAndMapItToFirstName( false )
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( "Water" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
                                                                                                                    new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
                                                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                    emailAddress )
                                                                                                 .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                    firstName );
            send.msdSurvey.surveyNext();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    /**
     * 
     * Verify that all properties of existing contact can be successfully updated 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "all-tests", "updating-data", "smoke-tests", "key-tests" })
    public void successfullyUpdatingAllContactProperties() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "UpdatingAllContactProperties";
        String emailAddress = msdExistingContact;
        String lastName = number + "lastName";
        String firstName = number + "firstName";

        log.startTest( "Verify that all properties of an existed contact can be successfully updated" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType2, campaignName2 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToGender()
                                                                                                                      .addFTQAndMapItToFirstName( false )
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                             new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                             emailAddress )
                                                                                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                             lastName )
                                                                                                          .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                             firstName );

            send.msdSurvey.surveyNext();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that the last name of an existing contact can be successfully updated 
     * 
     * @throws Exception 
     * 
     */
    @Test(groups = { "all-tests", "updating-data" })
    public void successfullyUpdatingLastNameContactProperty() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "UpdatingLastName";
        String emailAddress = msdExistingContact;
        String lastName = number + "lastName";

        log.startTest( "Verify that the last name of an existing contact can be successfully updated" );
        try {

            // Log And Go to Survey Page
            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editFTQAndMapItToEmail( false )
                                                                                                                      .editMCQAndMapItToGender()
                                                                                                                      .addFTQAndMapItToLastName( false );
            send.msdSurvey.saveAndSelectTheme( "Party Blue" ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
                                                                                                                         new String[]{ MCcontactFiled.GENDER.option_1 } )
                                                                                                      .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                         emailAddress )
                                                                                                      .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                         lastName );

            send.msdSurvey.surveyNext();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }
}
