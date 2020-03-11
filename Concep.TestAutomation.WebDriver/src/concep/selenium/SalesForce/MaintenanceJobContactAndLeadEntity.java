package concep.selenium.SalesForce;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.QuestionStatus;

public class MaintenanceJobContactAndLeadEntity extends BaseSFDC {

	 @BeforeClass(alwaysRun = true)
	 @Parameters({ "config" })
	 private void createConnectionWithLeadsOnAndContactEntity(
	                                                           @Optional("config/firefox.SFDC") String configLocation )
	                                                                                                                   throws Exception {		 
	     driver.init( configLocation );
	     driver.url = driver.config.getProperty( "url" );
	     sfdcSendUser = driver.config.getProperty( "sfdcSendUserMaintenanceJob" );
	     sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
	     loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", true );
	     driver.stopSelenium();
	 }
	 
	 @Test(groups = { "all-tests", "maintenance-job", "key-tests" })
	    public void successfullyKeppAllSurveyMappingsAfterMaintenanceJobExecutionOnlyLeadQuestionsMapped() throws Exception {

	    	String randNumber = driver.getTimestamp();
	        String surveyName = randNumber + "surveyName";
	         
	        log.startTest( "Verify that survey keeps all its mappings after the execution of the Maintenance job and only Lead entity questions are mapped" );
	        try {

	           loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
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
	            
	            send.sfdcSurvey.saveSurvey();                                        
	            
	        } catch( Exception e ) {

	            log.endStep( false );
	            throw e;
	        }

	        log.endTest();        
	    }    
	    
	    @Test(groups = { "all-tests", "maintenance-job", "key-tests"})
	    public void successfullyKeppAllSurveyMappingsAfterMaintenanceJobExecutionOnlyContactQuestionsMapped() throws Exception {

	    	String randNumber = driver.getTimestamp();
	        String surveyName = randNumber + "surveyName";
	         
	        log.startTest( "Verify that survey keeps all its mappings after the execution of the Maintenance job and only Contact entity questions are mapped" );
	        try {

	           loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
	                                                     .checkLogResponseInCRM()
	                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

	            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
								            .enterQuestion( FTcontactField.EMAIL.question )
								            .mapFTQuestionToContactField(FTcontactField.EMAIL.conntactFiled)							            
								            .updateQuestion()
								            .addNewQuestionType( "Free Text", element.ftQId )
								            .enterQuestion( FTcontactField.LASTNAME.question )
								            .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
								            .mapFTQuestionToLeadField( "Please select" )
								            .updateQuestion()
								            .addNewQuestionType( "Free Text", element.ftQId )
								            .enterQuestion( FTcontactField.FIRSTNAME.question )
								            .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
								            .mapFTQuestionToLeadField( "Please select" )
								            .updateQuestion()
								            .addOrUpdateMCQuestionBy( MCcontactFiled.SALUTATION.question, QuestionStatus.NEW )
	                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
	                                                           MCcontactFiled.SALUTATION.option_2 )
	                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
	                                                                   MCcontactFiled.SALUTATION.option_1,
	                                                                   MCcontactFiled.SALUTATION.option_2 )
	                                        .addMCQStatusOptions()
	                                        .updateQuestion()
	                                        .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question, QuestionStatus.NEW )
	                                        .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
	                                                           MCcontactFiled.LEADSOURCE.option_2 )
	                                        .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
	                                                                   MCcontactFiled.LEADSOURCE.option_1,
	                                                                   MCcontactFiled.LEADSOURCE.option_2 )
	                                        .addMCQStatusOptions()
	                                        .updateQuestion();
	            
	            send.sfdcSurvey.saveSurvey();                                        
	            
	        } catch( Exception e ) {

	            log.endStep( false );
	            throw e;
	        }

	        log.endTest();        
	    }  
	    
	    @Test(groups = { "all-tests", "maintenance-job", "key-tests" })
	    public void successfullyKeppAllSurveyMappingsAfterMaintenanceJobExecutionContactAndLeadQuestionsMapped() throws Exception {

	    	String randNumber = driver.getTimestamp();
	        String surveyName = randNumber + "surveyName";
	         
	        log.startTest( "Verify that survey keeps all its mappings after the execution of the Maintenance job - Contact and Lead entity questions are mapped" );
	        try {

	           loginToSend().goToSurveyPage().sfdcSurvey.createSurveyAndTypeSurveyName( surveyName )
	                                                     .checkLogResponseInCRM()
	                                                     .selectSurveyFolders( surveyType3, sfdcCampaign3 );

	            send.sfdcSurvey.sfdcQuestion.editQuestionType( "A free text question" )
								            .enterQuestion( FTcontactField.EMAIL.question )
								            .mapFTQuestionToContactField(FTcontactField.EMAIL.conntactFiled)							            
								            .updateQuestion()
								            .addNewQuestionType( "Free Text", element.ftQId )
								            .enterQuestion( FTcontactField.LASTNAME.question )
								            .mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled )
								            .updateQuestion()
								            .addNewQuestionType( "Free Text", element.ftQId )
								            .enterQuestion( FTcontactField.FIRSTNAME.question )
								            .mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled )
								            .updateQuestion()
								            .addOrUpdateMCQuestionBy( MCcontactFiled.SALUTATION.question, QuestionStatus.NEW )
	                                        .fillinMCQAnswers( MCcontactFiled.SALUTATION.option_1,
	                                                           MCcontactFiled.SALUTATION.option_2 )
	                                        .mapMCQuestionToContactField( MCcontactFiled.SALUTATION.name,
	                                                                   MCcontactFiled.SALUTATION.option_1,
	                                                                   MCcontactFiled.SALUTATION.option_2 )
	                                        .mapMcQuestionToLeadField( MCcontactFiled.SALUTATION.name,
	                                                                   MCcontactFiled.SALUTATION.option_1,
	                                                                   MCcontactFiled.SALUTATION.option_2 )
	                                        .addMCQStatusOptions()
	                                        .updateQuestion()
	                                        .addOrUpdateMCQuestionBy( MCcontactFiled.LEADSOURCE.question, QuestionStatus.NEW )
	                                        .fillinMCQAnswers( MCcontactFiled.LEADSOURCE.option_1,
	                                                           MCcontactFiled.LEADSOURCE.option_2 )
	                                        .mapMCQuestionToContactField( MCcontactFiled.LEADSOURCE.name,
	                                                                   MCcontactFiled.LEADSOURCE.option_1,
	                                                                   MCcontactFiled.LEADSOURCE.option_2 )
	                                        .mapMcQuestionToLeadField( MCcontactFiled.LEADSOURCE.name,
	                                                                   MCcontactFiled.LEADSOURCE.option_1,
	                                                                   MCcontactFiled.LEADSOURCE.option_2 )
	                                        .addMCQStatusOptions()
	                                        .updateQuestion();
	            
	            send.sfdcSurvey.saveSurvey();                                        
	            
	        } catch( Exception e ) {

	            log.endStep( false );
	            throw e;
	        }

	        log.endTest();        
	    }  
}
