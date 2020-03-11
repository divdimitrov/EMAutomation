package concep.selenium.Dynamic;

import java.util.ArrayList;

import org.testng.annotations.Test;

import concep.selenium.Dynamic.Attributes.SurveyAttributes;
import concep.selenium.send.SendEnum;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCQuestionAnswerType;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;
import concep.selenium.send.SendEnum.Theme;


public class SurveyCompletionEmailCampaign extends BaseMSD{
	
	@Test(groups = { "survey-completion", "all-tests", "smoke-tests", "key-tests" })
    public void successfullyCompletedSurveyFTQMailMergeInMCQ() throws Exception {

        String number = driver.getTimestamp();
        String surveyName = number + "surveyMailMerge";
        String emailAddress = number + "email@mailinator.com";
        String lastName = number + "lastName";
        String firstName = number + "firstName";
        String campaignName = number + "campaignName";
        String MCQ = "Dear [first_name] [last_name] [email] What is your gender?";

        log.startTest( "Verify that free text mail merge attributes are shown in MCQ" );
        try {

            loginToSend().goToSurveyPage().msdSurvey.createSurveyAndTypeSurveyName( surveyName )
                                                    .checkLogResponseInCRM()
                                                    .selectSurveyFolders( surveyType3, campaignName3 ).dynamicQuestion.editQuestionType("A multiple choice question")
                                                    																  .enterQuestion(MCQ)
                                                    																  .mapMCQuestionToContactField(MCcontactFiled.GENDER.name, 
                                                    																		  					   MCcontactFiled.GENDER.option_1,
                                                    																		  					   MCcontactFiled.GENDER.option_2)
                                                    																  .fillinMCQAnswers(MCcontactFiled.GENDER.option_1, 
                                                    																		            MCcontactFiled.GENDER.option_2)
                                                    																  .deleteMcQuestionAnswers(2)
                                                    																  .updateQuestion().editFTQAndMapItToEmail(false)
                                                    																  .addFTQAndMapItToLastName(false);
            
            
            send.msdSurvey.saveAndSelectTheme( Theme.WATER.type );
            
            send.contact.goToContactsPage().createContact(lastName, firstName, emailAddress, null, null);
            
            send.goToCampaignsPage().campaign.startCampaignCreation().
        	selectCampaignTemplate( "EmailCampaignSurveysCompletionTemp" ).
        	setCampaignName(campaignName, false).
        	setCampaignSubject("subject" + campaignName).
        	saveCampaign(true).
        	insertSurvey(surveyName, true);
            send.contact.goToContactsSubPage("Contacts").
        	searchRecord(emailAddress);
        	
        	Thread.sleep(5000);
        	
        	send.contact.selectContact(emailAddress);
        	
            send.campaign.continueToPreviewAndSend().sendCampaignNow(true);
            
            mailinator.accessMailinator().searchCustomerEmail(emailAddress).openEmailCampaign("subject" + campaignName);
            driver.switchToIframeByID("publicshowmaildivcontent");
            driver.click("//a[contains(text(), 'First collector')]", driver.timeOut);
            driver.switchToTopWindow();
            
            driver.waitForPageToLoad();
            
            driver.selectWindow();
            
            log.startStep("Verify the MCQ contains " + lastName + " for last name");
            log.endStep(driver.isTextPresent("//div[@class='sQT']/div", lastName, driver.timeOut));
            
            log.startStep("Verify the MCQ contains " + firstName + " for last name");
            log.endStep(driver.isTextPresent("//div[@class='sQT']/div", firstName, driver.timeOut));
            
            log.startStep("Verify the MCQ contais " + emailAddress + " for email address");
            log.endStep(driver.isTextPresent("//div[@class='sQT']/div", emailAddress, driver.timeOut));
            
            send.msdSurvey.dynamicQuestion.answerMcQuestion( "Dear " + firstName + " " + lastName + " " + emailAddress + " What is your gender?", 
            												new String[]{ MCcontactFiled.GENDER.option_1 } )
                                          .answerFtQuestion( FTcontactField.EMAIL.question,
                                                             emailAddress )
                                          .answerFtQuestion( FTcontactField.LASTNAME.question,
                                                                          lastName );
            
            send.msdSurvey.surveyNext();

            questionsArray = new ArrayList<>();
            questionsArray.add( MCcontactFiled.GENDER.question );
            questionsArray.add( FTcontactField.EMAIL.question );
            questionsArray.add( FTcontactField.FIRSTNAME.question );
            questionsArray.add( FTcontactField.LASTNAME.question );

            answersArray = new ArrayList<>();
            answersArray.add( MCcontactFiled.GENDER.option_1 );
            answersArray.add( emailAddress );
            answersArray.add( firstName );
            answersArray.add( lastName );


            log.startStep( "start parsing to Json Object" );
            SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType3,
                                                                           campaignName3,
                                                                           surveyName );
            surveyAttribute.SurveyResponseLength = 1;
            
            StackTraceElement[] stack = new Throwable().getStackTrace();
            json.saveItToDynamic( stack[0].getMethodName(), surveyAttribute, true );
            
            log.endStep();

        } catch( Exception e ) {
            log.endStep( false );
            throw e;
        }

        log.endTest();
    }

}
