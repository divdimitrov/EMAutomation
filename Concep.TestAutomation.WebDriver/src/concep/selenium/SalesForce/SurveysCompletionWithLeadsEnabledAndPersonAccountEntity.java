/**
 * Test cases for SalesForce Surveys completion when connection has Leads enabled and 'Lead' entity set.
 * 
 * @author Petar.Bozhinov
 * 
 */

package concep.selenium.SalesForce;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.SendEnum;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;

import java.util.*;

public class SurveysCompletionWithLeadsEnabledAndPersonAccountEntity extends BaseSFDC {

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    private void createConnectionWithLeadsOnAndPersonAccountEntity(
                                                                    @Optional("config/firefox.SFDC") String configLocation )
                                                                                                                            throws Exception {

        driver.init( configLocation );
        driver.url = driver.config.getProperty( "url" );
        sfdcSendUser = driver.config.getProperty( "sfdcSendUser" );
        sfdcSendUser3 = driver.config.getProperty( "sfdcSendUser3" );
        sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
        loginToSend3().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Person account", true );
        driver.stopSelenium();

    }

    /**
     * 
     * Data driven method which verifies that survey can be completed with different survey types and campaigns while using connection which has leads enabled and person account entity
     * 
     * @throws Exception
     * 
     */

    private void completeSurveyWithLinkedQuestionsAndNonExistingPersonAccount(
                                                                               String surveyType,
                                                                               String sfdcCampaign,
                                                                               SendEnum.MCcontactFiled questionContentMC )
                                                                                                                          throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + surveyType;
        String testDescription = "LeadEnable: Verify that Survey type "
                                 + surveyType
                                 + " can be completed successfully with non existing user which has leads enabled and person account entity options";

        log.startTest( testDescription );
        try {
            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType, sfdcCampaign ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                  .addOrUpdateMCQuestion( true,
                                                                                                                                          QuestionStatus.EDIT,
                                                                                                                                          questionContentMC )
                                                                                                                  .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( questionContentMC.question,
                                                                                                                          new String[]{ questionContentMC.option_1 } )
                                                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                          emailAddress )
                                                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                          lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType;
            surveyAttributes.campaign.name=sfdcCampaign;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(questionContentMC.question);

            surveyAttributes.answers.add(questionContentMC.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[1].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that Survey type Survey can be completed successfully with linked
     * questions which has leads enabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests" })
    public void successfullyCompletedSurveyTypeSurveyWithLinkedQuestionsLeadsEnabledAndPerson()
                                                                                               throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingPersonAccount( surveyType1,
                                                                      sfdcCampaign1,
                                                                      MCcontactFiled.LEADSOURCE );
    }

    /**
     * 
     * Verify that Survey type Registration Form can be completed successfully
     * with linked questions which has leads enabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeRegistrationFormWithLinkedQuestionsLeadsEnabledAndPerson()
                                                                                                         throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingPersonAccount( surveyType2,
                                                                      sfdcCampaign2,
                                                                      MCcontactFiled.SALUTATION );
    }

    /**
     * 
     * Verify that Survey type Feedback Form can be completed successfully with
     * linked questions which has leads enabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeFeedbackFormWithLinkedQuestionsLeadsEnabledAndPerson()
                                                                                                     throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingPersonAccount( surveyType3,
                                                                      sfdcCampaign3,
                                                                      MCcontactFiled.LEADSOURCE );
    }

    /**
     * 
     * Verify that Survey type Questionnaire can be completed successfully with
     * linked questions which has leads enabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyTypeQuestionnaireWithLinkedQuestionsLeadsEnabledAndPerson()
                                                                                                      throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingPersonAccount( surveyType4,
                                                                      sfdcCampaign4,
                                                                      MCcontactFiled.SALUTATION );
    }

    /**
     * 
     * Verify that Survey type Form can be completed successfully with linked
     * questions which has leads enabled and person account entity options
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyTypeFormWithLinkedQuestionsLeadsEnabledAndPerson()
                                                                                             throws Exception {

        completeSurveyWithLinkedQuestionsAndNonExistingPersonAccount( surveyType6,
                                                                      sfdcCampaign6,
                                                                      MCcontactFiled.LEADSOURCE );
    }

    /**
     * 
     * Verify that survey with 10 mapped questions can be completed successfully 
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWith10MappedQuestions() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "10MappedQuestions";

        log.startTest( "LeadEnable: Verify that survey with 10 mapped questions can be completed successfully" );
        try {
            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true )

                                                                                                                    .addNewQuestionType( "free text",
                                                                                                                                         element.ftQId )
                                                                                                                    .enterQuestion( FTcontactField.LASTNAME.question );

            driver.showHiddenElement( "qTypetextSalesforceContactsField select" );
            driver.showHiddenElement( "qTypetextSalesforceLeadField select" );

            String[] contactsDropDown = driver.getDropDownOptions( "//label[@id='qTypetextSalesforceContactsField']/select" );
            String[] leadsDropDown = driver.getDropDownOptions( "//label[@id='qTypetextSalesforceLeadField']/select" );

            driver.hideShowenElement( "qTypetextSalesforceContactsField select" );
            driver.hideShowenElement( "qTypetextSalesforceLeadField select" );

            send.sfdcSurvey.sfdcQuestion.mapFTQuestionToContactField( "Last Name" ).updateQuestion();

            //adding 7 questions
            for( int i = 6; i <= 11; i++ ) {
                if( contactsDropDown[i].equals( FTcontactField.EMAIL.conntactFiled )
                    || contactsDropDown[i].equals( FTcontactField.LASTNAME.conntactFiled )
                    || contactsDropDown[i].equals( "Please select" )
                    || contactsDropDown[i].equals( "Assistant's Name" )
                    || contactsDropDown[i].equals( "Asst. Phone" ) )
                    continue;

                send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free text", element.ftQId )
                                            .enterQuestion( contactsDropDown[i] )
                                            .mapQuestionToContactField( contactsDropDown[i],
                                                                        element.sfdcContactFieldFTid )
                                            .mapQuestionToContactField( leadsDropDown[i],
                                                                        element.sfdcLeadFieldFTid )
                                            .updateQuestion();
                
                surveyAttributes.questions.add(leadsDropDown[i].toString());
            }

            // Save and deploy survey
            send.sfdcSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                          new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                          emailAddress )
                                                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                          lastName );

            //Answer the additional 7 questions
            for( int i = 6; i <= 11; i++ ) {
                if( contactsDropDown[i].equals( "Email" ) || contactsDropDown[i].equals( "Last Name" )
                    || contactsDropDown[i].equals( "Please select" )
                    || contactsDropDown[i].equals( "Assistant's Name" )
                    || contactsDropDown[i].equals( "Asst. Phone" ) )
                    continue;
                send.sfdcSurvey.sfdcQuestion.answerFtQuestion( contactsDropDown[i], "Answer" );
                
                surveyAttributes.answers.add("Answer");
            }

            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed with 10 Pages
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWith10Pages() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "10Pages";

        log.startTest( "LeadEnable: Verify that survey can be completed with 10 Pages" );
        try {
            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                      .checkLogResponseInCRM()
                                                      .selectSurveyFolders( surveyType6, sfdcCampaign6 ).sfdcQuestion.editMCquestionAndMapItToSalutation( true )

                                                                                                                     //Delete the Default Free Text Question
                                                                                                                     .deleteFtQuestionType( "A free text question" );
            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addFTQAndMapItToEmail( false )
                                                                     .deleteContentQuestionType( "Thank you" );
            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addNewQuestionType( "free text",
                                                                                        element.ftQId )
                                                                   .enterQuestion( FTcontactField.LASTNAME.question );

            String[] contactsDropDown = { "Business Fax",
                    "Business Phone",
                    "Contact Attributes",
                    "Contact Description",
                    "Department",
                    "Distribution Lists",
                    "Full Name",
                    "Home Phone",
                    "Mailing City",
                    "Mailing Country" };
            String[] leadsDropDown = { "City",
                    "Company",
                    "Country",
                    "Description",
                    "Fax",
                    "Full Name",
                    "Position Level",
                    "State/Province",
                    "Street",
                    "Title" };

            send.sfdcSurvey.sfdcQuestion.mapFTQuestionToContactField( "Last Name" ).updateQuestion();

            //Add 7 more pages with a question
            for( int i = 0; i < 10; i++ ) {

                int pageNumber = i + 4;

                //Add a Page And go to it
                send.sfdcSurvey.addAndGoToPage( "Page " + pageNumber ).

                sfdcQuestion.addNewQuestionType( "Free text", element.ftQId )
                            .enterQuestion( contactsDropDown[i] )
                            .mapQuestionToContactField( contactsDropDown[i], element.sfdcContactFieldFTid )
                            .mapQuestionToContactField( leadsDropDown[i], element.sfdcLeadFieldFTid )
                            .updateQuestion();
                
                surveyAttributes.questions.add(leadsDropDown[i]);

            }

            //Add Thank You Page    
            send.sfdcSurvey.addAndGoToPage( "Last Page" );

            //Add a Content Question
            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Content", element.contentQId )
                                        .enterQuestion( "Thank You" )
                                        .updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( Theme.FUNKY.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                           new String[]{ MCcontactFiled.SALUTATION.option_1 } );
            send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                        emailAddress );
            send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                        lastName );

            // Answer the additional 7 questions
            for( int i = 0; i < 10; i++ ) {

                send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( contactsDropDown[i], "Answer" );
                
                surveyAttributes.answers.add("Answer");
            }

            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType6;
            surveyAttributes.campaign.name=sfdcCampaign6;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed successfully with drag and drop questions on two pages.
     * 
     * @throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDragAndDropOn3Pages() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String surveyName = number + "DragAndDropOn3Pages";

        log.startTest( "LeadEnable: Verify that survey can be completed successfully with drag and drop questions on three pages." );
        try {
            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 ).sfdcQuestion.deleteMcQuestionType( "A multiple choice question" )
                                                                                                                    .dragAndDropFTQAndMapItToEmail( false )
                                                                                                                    .deleteFtQuestionType( "A free text question" )
                                                                                                                    .dragAndDropMCquestionAndMapItToLeadSource( true );
            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.dragAndDropFTQAndMapItToLastName( false )
                                                                     .deleteContentQuestionType( "Thank you" )
                                                                     .dragAndDropFTQAndMapItToFirstName( false );
            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addDragAndDropQuestionType( "Content" )
                                                                   .enterQuestion( "Thank you" )
                                                                   .updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                          new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                          emailAddress );
            send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                        firstName )
                                                     .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                        lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType3;
            surveyAttributes.campaign.name=sfdcCampaign3;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);

            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(firstName);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that 'alt email' is successfully recorded in the campaign member field
     * 
     * PLAT-1299
     * 
     * @throws Exception 
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithAltEmail() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "AltEmail";
        String altEmail = number + "altemail" + "@test.com";

        log.startTest( "LeadEnable: Verify that 'alt email' is successfully recorded in the campaign member field" );
        try {
            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToLeadSource( true )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addOrUpdateFreeTextQuestion( QuestionStatus.NEW,
                                                                                                                                                  FTcontactField.ALTEMAIL,
                                                                                                                                                  true,
                                                                                                                                                  false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.AQUA.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                                                                          new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
                                                                                                       .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                          emailAddress )
                                                                                                       .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                          lastName )
                                                                                                       .answerFtQuestion( FTcontactField.ALTEMAIL.question,
                                                                                                                          altEmail );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(FTcontactField.ALTEMAIL.question);

            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(altEmail);
            
            surveyAttributes.email.add(emailAddress);
            
            campaign.campaignMember.add(altEmail);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
     * 
     * Verify that survey can be completed with an existing Person Account can successfully be updated
     * @sfdcExistingContact is hardcoded at the config file
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "updating-data", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithExistingPersonAccount() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = sfdcExistingPersonAccount;
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String surveyName = number + "UpdatingPAccount";

        log.startTest( "LeadEnable: Verify that survey can be completed with an existing Person Account can successfully be updated" );
        try {
            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType2, sfdcCampaign2 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .editMCquestionAndMapItToSalutation( true )
                                                                                                                    .addFTQAndMapItToLastName( false )
                                                                                                                    .addFTQAndMapItToFirstName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.FUNKY.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                           new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName )
                                                                                                        .answerFtQuestion( FTcontactField.FIRSTNAME.question,
                                                                                                                           firstName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType2;
            surveyAttributes.campaign.name=sfdcCampaign2;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Account";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
            
        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
    *
    * Verify that survey can be successfully Completed with Different Symbols in the Email
    * 
    * throws Exception
    * @emailAddress - Email address with specific symbol required for the test
    * @description - Test Description
    * 
    */

    private void completedSurveyWithDifferentEmails(
                                                     String surveyName,
                                                     String emailAddress,
                                                     String desciption ) throws Exception {

        String number = driver.getTimestamp();
        String lastName = number + "lastName";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with " + desciption );
        try {
            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                      .checkLogResponseInCRM()
                                                      .selectSurveyFolders( surveyType6, sfdcCampaign6 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                     .editMCquestionAndMapItToSalutation( true )
                                                                                                                     .addFTQAndMapItToLastName( false );
            send.sfdcSurvey.saveAndSelectTheme( Theme.FUNKY.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                                                           new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                        .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                           emailAddress )
                                                                                                        .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                           lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType2;
            surveyAttributes.campaign.name=sfdcCampaign2;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[1].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    /**
    *
    * Verify that survey can be successfully completed with Special Characters in the email address -
    * ! # $ % & ' * + - / = ? ^ _ ` { | } ~
    * 
    * throws Exception
    * 
    */

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithSpecialCharacters() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmails( number + "specialCharatersInEmail",
                                            number + "!#$%&*+-/=?^_^`'{|}~Em'@c.n",
                                            "apostophe in the email address !#$%&*+-/=?^_^`'{|}~' " );
    }

    /**
     * 
     * Verify that survey can be successfully completed with Hyphen in the email
     * 
     * throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithHyphen() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmails( number + "EmailWithHyphen",
                                            number + "email-comment@concep.com",
                                            "Hyphen in the email" );
    }

    /**
     * 
     * Verify that survey can be successfully completed with Digits 0 to 9 in the email
     * 
     * throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompletedSurveyWithDigits0to9InTheEmail() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmails( number + "Digits0to9InTheEmail",
                                            number + "email0123456789@concep.com",
                                            "Digits 0 to 9 in the email" );
    }

    /**
     * 
     * Verify that survey can be successfully completed with Dot "." in the email
     * 
     * throws Exception
     * 
     */

    @Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompletedSurveyWithDot() throws Exception {

        String number = driver.getTimestamp();

        completedSurveyWithDifferentEmails( number + "EmailWithDot",
                                            number + "email.test@concep.com",
                                            "Dot '.' in the email" );
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyWithPageJumping() throws Exception {

        String number = driver.getTimestamp();
        String emailAddress = number + "email@concep.com";
        String lastName = number + "lastName";
        String surveyName = number + "PageJumping";

        log.startTest( "LeadEnable: Verify that survey can be successfully completed with page jumping" );
        try {

            // Log And Go to Survey Page
            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType4, sfdcCampaign4 ).sfdcQuestion.editFTQAndMapItToEmail( false )
                                                                                                                    .addFTQAndMapItToLastName( false );

            // Add Additional Pages
            for( int i = 1; i < 4; i++ ) {
                String pageNumber = Integer.toString( i + 2 );
                send.sfdcSurvey.addAndGoToPage( "Page " + pageNumber );
            }

            // Edit the default multiple choice question
            send.sfdcSurvey.goToPageInSurvey( "Page 1" ).sfdcQuestion.editQuestionType( "A multiple choice question" )
                                                                     .enterQuestion( MCcontactFiled.SALUTATION.question );

            send.sfdcSurvey.sfdcQuestion.mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .deleteMcQuestionAnswers( 2 )
                                        .addMCQStatusOptions();

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 3" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.sfdcSurvey.sfdcQuestion.updateQuestion();
            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Multiple Choice",
                                                                                          element.mcQId )
                                                                     .enterQuestion( MCcontactFiled.LEADSOURCE.question );

            log.startStep( "Add Additonal Answer field" );
            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
            log.endStep();

            send.sfdcSurvey.sfdcQuestion.mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
                                                                      MCcontactFiled.LEADSOURCE.option_1,
                                                                      MCcontactFiled.LEADSOURCE.option_2 )
                                        .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                           MCcontactFiled.LEADSOURCE.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
                                                                   MCcontactFiled.LEADSOURCE.option_1,
                                                                   MCcontactFiled.LEADSOURCE.option_2 )
                                        .addMCQStatusOptions();

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 5" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.sfdcSurvey.sfdcQuestion.updateQuestion();
            send.sfdcSurvey.goToPageInSurvey( "Page 3" ).sfdcQuestion.addNewQuestionType( "Multiple Choice",
                                                                                          element.mcQId )
                                                                     .enterQuestion( MCcontactFiled.STATUS.question );

            log.startStep( "Add Additonal Answer field" );
            driver.click( "//a[@id='qAddAnswer']", driver.timeOut );
            log.endStep();

            // Fill the MCQ 
            send.sfdcSurvey.sfdcQuestion.mapMCQuestionToContactField( MCcontactFiled.STATUS.name,
                                                                      MCcontactFiled.STATUS.option_1,
                                                                      MCcontactFiled.STATUS.option_2 )
                                        .fillinMCQAnswers( MCcontactFiled.STATUS.option_1,
                                                           MCcontactFiled.STATUS.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.MARITALSTATUS.name,
                                                                   MCcontactFiled.MARITALSTATUS.option_1,
                                                                   MCcontactFiled.MARITALSTATUS.option_2 )
                                        .addMCQStatusOptions();

            log.startStep( "Map the MCQ answers to Pages" );
            driver.select( "//li[1]//select[@class='qPagelist']" ).selectByVisibleText( "Page 2" );
            driver.select( "//li[2]//select[@class='qPagelist']" ).selectByVisibleText( "Page 4" );
            log.endStep();

            send.sfdcSurvey.sfdcQuestion.updateQuestion();
            send.sfdcSurvey.goToPageInSurvey( "Page 5" ).sfdcQuestion.addNewQuestionType( "Content",
                                                                                          element.contentQId )
                                                                     .enterQuestion( "Thank you" )
                                                                     .updateQuestion();
            send.sfdcSurvey.saveAndSelectTheme( "Survey" ).viewAndDeployTheSurvey().sfdcQuestion

            // Answer the Survey questions
            .answerMcQuestion( MCcontactFiled.SALUTATION.question,
                               new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                                                .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                                                   lastName )
                                                                                                .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                                                   emailAddress );
            send.sfdcSurvey.surveyNext().sfdcQuestion.answerMcQuestion( MCcontactFiled.STATUS.question,
                                                                        new String[]{ MCcontactFiled.STATUS.option_1 } );
            send.sfdcSurvey.surveyNext().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
                                                                        new String[]{ MCcontactFiled.LEADSOURCE.option_1 } );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType4;
            surveyAttributes.campaign.name=sfdcCampaign4;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(MCcontactFiled.MARITALSTATUS.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(MCcontactFiled.MARITALSTATUS.option_1);
            surveyAttributes.answers.add(emailAddress);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(emailAddress);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }
        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";
        String lastNameSecond = randNumber + "lastNameSecond";
        int submissionsCount = 20;
        
        int idFTQ1;
        int idFTQ2;
        int idMCQ1;
        int idMCQ2;
        
        Map<Integer, String> duplicatedFTQids = new HashMap<Integer, String>();
        Map<Integer, String> duplicatedMCQids = new HashMap<Integer, String>();

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same page" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion();
            
            idFTQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ1, lastNameFirst);
            
            send.sfdcSurvey.sfdcQuestion.editMCquestionAndMapItToSalutation( true );
            
            idMCQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ1, MCcontactFiled.SALUTATION.option_1);
            
            send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion();
            
            idFTQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ2, lastNameSecond);
            
            send.sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();
            
            idMCQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ2, MCcontactFiled.SALUTATION.option_2);

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy();
            
            for (int i = 0; i < submissionsCount; i++) {
				
            	send.sfdcSurvey.viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																                        new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																     .answerFtQuestion( FTcontactField.EMAIL.question,
																                        i + email )
																     .answerFtQuestion( FTcontactField.LASTNAME.question,
																                        lastNameFirst )
																     .answerFtQuestion( "Last Name Duplicated",
																                        lastNameSecond )
																     .answerMcQuestion( "Salutation Duplicated",
																                        new String[]{ MCcontactFiled.SALUTATION.option_2 } );
            	
				send.sfdcSurvey.surveyNext();
				driver.close();
                driver.selectWindow();
			}
                        
            log.resultStep("Highest FTQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids) +
            		" and has an answer of: " +  duplicatedFTQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids)));
        	log.endStep();
        	
        	log.resultStep("Highest MCQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids) +
            		" and has an answer of: " +  duplicatedMCQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids)));
        	log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsDifferentPages()
                                                                                               throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";
        String lastNameSecond = randNumber + "lastNameSecond";
        int submissionsCount = 20;
        
        int idFTQ1;
        int idFTQ2;
        int idMCQ1;
        int idMCQ2;
        
        Map<Integer, String> duplicatedFTQids = new HashMap<Integer, String>();
        Map<Integer, String> duplicatedMCQids = new HashMap<Integer, String>();

        log.startTest( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on different pages" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion();
            
            idFTQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ1, lastNameFirst);
            
            send.sfdcSurvey.sfdcQuestion.editMCquestionAndMapItToSalutation( true );
            
            idMCQ1 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ1, MCcontactFiled.SALUTATION.option_1);

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Last Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                     .updateQuestion();
            
            idFTQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedFTQids.put(idFTQ2, lastNameSecond);
            
            send.sfdcSurvey.sfdcQuestion.addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();
            
            idMCQ2 = send.sfdcSurvey.sfdcQuestion.questionID;
            duplicatedMCQids.put(idMCQ2, MCcontactFiled.SALUTATION.option_2);

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy();
            
            for (int i = 0; i < submissionsCount; i++) {
				
            	send.sfdcSurvey.viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																                        new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																     .answerFtQuestion( FTcontactField.EMAIL.question,
																                        i + email )
																     .answerFtQuestion( FTcontactField.LASTNAME.question,
																                        lastNameFirst );
																
			    send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( "Last Name Duplicated",
																            lastNameSecond )
																.answerMcQuestion( "Salutation Duplicated",
																            new String[]{ MCcontactFiled.SALUTATION.option_2 } );
																
				send.sfdcSurvey.surveyNext();
				driver.close();
                driver.selectWindow();
			}            
            
            log.resultStep("Highest FTQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids) +
            		" and has an answer of: " +  duplicatedFTQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedFTQids)));
        	log.endStep();
        	
        	log.resultStep("Highest MCQ question ID is " + send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids) +
            		" and has an answer of: " +  duplicatedMCQids.get(send.sfdcSurvey.sfdcQuestion.getHighesQuestionId(duplicatedMCQids)));
        	log.endStep();

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastNameFirst";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on the same page" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "Last Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                    new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstNameSecond = randNumber + "firstNameSecond";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on the same page" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
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
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "First Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName )
                                                                 .answerFtQuestion( "First Name Duplicated",
                                                                                    firstNameSecond )
                                                                 .answerMcQuestion( "Salutation Duplicated",
                                                                                    new String[]{ MCcontactFiled.SALUTATION.option_2 } );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add("First Name Duplicated");
            surveyAttributes.questions.add("Salutation Duplicated");

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(firstNameSecond);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastNameFirst = randNumber + "lastNameFirst";

        log.startTest( "Verify that blank values are successfully handled when only first question is answered on different pages" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
                                        .enterQuestion( FTcontactField.EMAIL.question )
                                        .mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled )
                                        .updateQuestion()
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( FTcontactField.LASTNAME.question )
                                        .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                        .updateQuestion()
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "Last Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
                                                                                    new String[]{ MCcontactFiled.SALUTATION.option_1 } )
                                                                 .answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastNameFirst );

            send.sfdcSurvey.surveyNext().surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastNameFirst);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstNameSecond = randNumber + "firstNameSecond";

        log.startTest( "Verify that blank values are successfully handled when only second question is answered on different pages" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
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
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "First Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).dynamicQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );

            send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( "First Name Duplicated",
                                                                        firstNameSecond )
                                                     .answerMcQuestion( "Salutation Duplicated",
                                                                        new String[]{ MCcontactFiled.SALUTATION.option_2 } );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add("First Name Duplicated");
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add("Salutation Duplicated");

            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstNameSecond);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsSamePage() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on the same page" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
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
                                        .editMCquestionAndMapItToSalutation( true )
                                        .addNewQuestionType( "Free Text", element.ftQId )
                                        .enterQuestion( "First Name Duplicated" )
                                        .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( "Salutation Duplicated", QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                      MCcontactFiled.SALUTATION.option_1,
                                                                      MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName );
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "mappings-duplication", "key-tests" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsDifferentPages() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";

        log.startTest( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on different pages" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
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
                                        .editMCquestionAndMapItToSalutation( true );

            send.sfdcSurvey.goToPageInSurvey( "Page 2" ).sfdcQuestion.addNewQuestionType( "Free Text",
                                                                                          element.ftQId )
                                                                     .enterQuestion( "First Name Duplicated" )
                                                                     .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
                                                                     .updateQuestion()
                                                                     .addOrUpdateMCQuestionBy( "Salutation Duplicated",
                                                                                               QuestionStatus.NEW )
                                                                     .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                                                        MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
                                                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                                                   MCcontactFiled.SALUTATION.option_2 )
                                                                     .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                                                MCcontactFiled.SALUTATION.option_1,
                                                                                                MCcontactFiled.SALUTATION.option_2 )
                                                                     .addMCQStatusOptions()
                                                                     .updateQuestion();

            send.sfdcSurvey.addAndGoToPage( "Page 3" ).sfdcQuestion.addContentQuestion( false, "Thank you" );

            send.sfdcSurvey.saveAndContinueToTheme()
                           .handleDuplicateMappingsDialog( true )
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                       email )
                                                                    .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                       lastName );

            send.sfdcSurvey.surveyNext().surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyUpdateExistingPAccountFirstLastNameLeadEnabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_PAccount1_test3@mail.bg";
        String firstName = randNumber + "firstNameUpdate";
        String lastName = randNumber + "lastNameUpdate";

        log.startTest( "Verify that existing Person Account's First name and Last name can be updated when the Lead is enabled" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType1, sfdcCampaign1 );

            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToFirstName(false)
            							.addFTQAndMapItToLastName(false)
            							.addFTQAndMapItToEmail(false);

            send.sfdcSurvey.saveAndContinueToTheme()
                           .selectTheme( Theme.TABLE.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName )
                                                                 .answerFtQuestion(FTcontactField.FIRSTNAME.question,
                                                                		 			firstName);
                                                                 
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.personType="Account";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);

            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(firstName);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyUpdateExistingPAccountMCQLeadEnabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_PAccount1_test4@mail.bg";

        log.startTest( "Verify that existing Person Account's MCQ can be updated when the Lead is enabled" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType2, sfdcCampaign2 );

            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToEmail(false)
            							.editMCquestionAndMapItToLeadSource(true)
            							.addMCquestionAndMapItToSalutation(true);
            							

            send.sfdcSurvey.saveAndContinueToTheme()
                           .selectTheme( Theme.DISCO.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerMcQuestion(MCcontactFiled.LEADSOURCE.question,
                                                                		 			new String[] { MCcontactFiled.LEADSOURCE.option_1 })
                                                                 .answerMcQuestion(MCcontactFiled.SALUTATION.question,
                                                                		 			new String[] { MCcontactFiled.SALUTATION.option_2 });
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType1;
            surveyAttributes.campaign.name=sfdcCampaign1;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Account";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
    }
    
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyUpdateExistingPAccountFTQandMCQLeadEnabled() throws Exception {

        String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";

        String email = "mail_PAccount1_test5@mail.bg";
        String lastName = randNumber + "lastNameUpdate";
        String firstName = randNumber + "firstNameUpdate";

        log.startTest( "Verify that existing Person Account's FTQ and MCQ can be updated when the Lead is enabled" );
        try {

            loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

            send.sfdcSurvey.sfdcQuestion.editFTQAndMapItToLastName(false)
            							.editMCquestionAndMapItToLeadSource(true)
            							.addMCquestionAndMapItToSalutation(true)
            							.addFTQAndMapItToEmail(false)
            							.addFTQAndMapItToFirstName(false);
            							

            send.sfdcSurvey.saveAndContinueToTheme()
                           .selectTheme( Theme.SPORT.type )
                           .saveAndContinueToDeploy()
                           .viewAndDeployTheSurvey().sfdcQuestion.answerFtQuestion( FTcontactField.EMAIL.question,
                                                                                    email )
                                                                 .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                                    lastName )
                                                                 .answerFtQuestion(FTcontactField.FIRSTNAME.question,
                                                                		 			firstName)                  
                                                                 .answerMcQuestion(MCcontactFiled.LEADSOURCE.question,
                                                                		 			new String[] { MCcontactFiled.LEADSOURCE.option_1 })
                                                                 .answerMcQuestion(MCcontactFiled.SALUTATION.question,
                                                                		 			new String[] { MCcontactFiled.SALUTATION.option_2 });
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType3;
            surveyAttributes.campaign.name=sfdcCampaign3;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseRespond);
            surveyAttributes.personType="Account";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(firstName);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_2);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);

        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();
        
    }    
    
    @Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompleteSurveyWithMappedQuestionsOnlyForLead() throws Exception {

    	String randNumber = driver.getTimestamp();
        String surveyName = randNumber + "surveyName";
        
        String email = randNumber + "testmail@mail.com";
        String lastName = randNumber + "lastName";
        String firstName = randNumber + "firstName";
         
        log.startTest( "Verify that a survey response with all the information and no missing questions or answers will be "
        		+ "logged successfully into CRM when survey has questions mapped to Lead only - [Person Account Enabled]" );
        try {

        	loginToSend3().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                     .checkLogResponseInCRM()
                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
							            .enterQuestion( FTcontactField.EMAIL.question )
							            .mapFTQuestionToLeadField(FTcontactField.EMAIL.conntactFiled)							            
							            .updateQuestion()
							            .addNewQuestionType( "Free Text", element.ftQId )
							            .enterQuestion( FTcontactField.LASTNAME.question )
							            .mapFTQuestionToLeadField( FTcontactField.LASTNAME.conntactFiled )
							            .mapFTQuestionToContactField("Please select")
							            .updateQuestion()
							            .addNewQuestionType( "Free Text", element.ftQId )
							            .enterQuestion( FTcontactField.FIRSTNAME.question )
							            .mapFTQuestionToLeadField( FTcontactField.FIRSTNAME.conntactFiled )
							            .mapFTQuestionToContactField("Please select")
							            .updateQuestion()
							            .addOrUpdateMCQuestionBy( MCcontactFiled.SALUTATION.question, QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
                                                           MCcontactFiled.SALUTATION.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
                                                                   MCcontactFiled.SALUTATION.option_1,
                                                                   MCcontactFiled.SALUTATION.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion()
                                        .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question, QuestionStatus.NEW )
                                        .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
                                                           MCcontactFiled.LEADSOURCE.option_2 )
                                        .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
                                                                   MCcontactFiled.LEADSOURCE.option_1,
                                                                   MCcontactFiled.LEADSOURCE.option_2 )
                                        .addMCQStatusOptions()
                                        .updateQuestion();
            
            send.sfdcSurvey.saveAndSelectTheme( Theme.WATER.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
						                    new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
						   .answerFtQuestion( FTcontactField.EMAIL.question, email )
						   .answerFtQuestion( FTcontactField.LASTNAME.question, lastName)
						   .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName)
						   .answerMcQuestion( MCcontactFiled.SALUTATION.question, new String[]{ MCcontactFiled.SALUTATION.option_1 } )
						   .answerMcQuestion( MCcontactFiled.LEADSOURCE.question, new String[]{ MCcontactFiled.LEADSOURCE.option_1 } );
			
            send.sfdcSurvey.surveyNext();
            
            surveyAttributes.surveyName=surveyName;
            surveyAttributes.surveyType=surveyType3;
            surveyAttributes.campaign.name=sfdcCampaign3;
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.campaign.campaignResponse.add(campaignResponseSent);
            surveyAttributes.personType="Lead";

            surveyAttributes.questions.add(FTcontactField.EMAIL.question);
            surveyAttributes.questions.add(FTcontactField.LASTNAME.question);
            surveyAttributes.questions.add(FTcontactField.FIRSTNAME.question);
            surveyAttributes.questions.add(MCcontactFiled.LEADSOURCE.question);
            surveyAttributes.questions.add(MCcontactFiled.SALUTATION.question);
            
            surveyAttributes.answers.add(email);
            surveyAttributes.answers.add(firstName);
            surveyAttributes.answers.add(lastName);
            surveyAttributes.answers.add(MCcontactFiled.LEADSOURCE.option_1);
            surveyAttributes.answers.add(MCcontactFiled.SALUTATION.option_1);
            
            surveyAttributes.email.add(email);

            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToSFDC(stack[0].getMethodName(),surveyAttributes,isJson);
            
        } catch( Exception e ) {

            log.endStep( false );
            throw e;
        }

        log.endTest();        
    }    
}
