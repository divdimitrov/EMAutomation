package concep.selenium.Dynamic;

import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.Dynamic.Attributes.SurveyAttributes;
import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.Theme;

public class SurveyTemplate extends BaseMSD {

	private String surveyTemplateOnePage;
	private String surveyTemplateThreePages;
	private String surveyTemplateThreeLanguagePages;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "config" })
    public void initSurveyTemplateParameters(
                                               @Optional("config/firefox.MSD4") String configLocation )
                                                                                                     throws Exception {

        surveyTemplateOnePage = driver.config.getProperty( "surveyTemplateOnePage");
        surveyTemplateThreePages = driver.config.getProperty( "surveyTemplateThreePages" );
        surveyTemplateThreeLanguagePages = driver.config.getProperty( "surveyTemplateThreeLanguagePages");
    }
    
    private SurveyTemplate mapSurveyTemplateOnePage() throws Exception {
    	
    	send.msdSurvey.dynamicQuestion.editQuestionType(MCcontactFiled.GENDER.question).
				       mapMCQuestionToContactField( MCcontactFiled.GENDER ).
				       addMCResponseCodeOptions().
				       updateQuestion().
				       editQuestionType( FTcontactField.EMAIL.question ).
				       mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled ).
				       updateQuestion().
				       editQuestionType( FTcontactField.FIRSTNAME.question ).
				       mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled ).
				       updateQuestion().
				       editQuestionType( FTcontactField.LASTNAME.question ).
				       mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled ).
				       updateQuestion().
				       editQuestionType(MCcontactFiled.MARITALSTATUS.question).
				       mapMCQuestionToContactField( MCcontactFiled.MARITALSTATUS ).
				       addMCResponseCodeOptions().
				       updateQuestion().
				       editQuestionType(MCcontactFiled.ROLE.question).
				       mapMCQuestionToContactField( MCcontactFiled.ROLE ).
				       addMCResponseCodeOptions().
				       updateQuestion();
    	
    	return this;
    }
    
    private SurveyTemplate verifySurveyTemplateMappingsOnePage() throws Exception {
    	
    	log.startStep( "Verify that 'log responses in our CRM system check box is checked'" );
		driver.waitUntilElementIsLocated(element.msdLogResponsesCheckbox, driver.timeOut);
		driver.waitUntilElementIsClickable(element.msdLogResponsesCheckbox);
        log.endStep( verifyLogResponseInCRM() );
        
        send.verifyDisplayedElements(new String[] {element.getFreeTextQuestionMappingIndicator(FTcontactField.EMAIL.question, FTcontactField.EMAIL.conntactFiled),
									        	   element.getFreeTextQuestionMappingIndicator(FTcontactField.FIRSTNAME.question, FTcontactField.FIRSTNAME.conntactFiled),
									        	   element.getFreeTextQuestionMappingIndicator(FTcontactField.LASTNAME.question, FTcontactField.LASTNAME.conntactFiled),
									        	   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.GENDER.question, MCcontactFiled.GENDER.name),
        										   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.MARITALSTATUS.question, MCcontactFiled.MARITALSTATUS.name),
        										   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.ROLE.question, MCcontactFiled.ROLE.name)},
        						     new String[] {"Email mapping",
        										   "First Name mapping",
        										   "Last Name mapping",
        										   "Gender mapping",
        										   "Marital Status mapping",
        										   "Role mapping"},
        						     true);    	
    	return this;
    }
    
    private SurveyTemplate verifySurveyTemplateOnePageContent() throws Exception {
    	
    	send.verifyDisplayedElements(new String[] {element.msdLogResponsesCheckbox,
    											   element.getSurveyMCQcontentText(MCcontactFiled.GENDER.question),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.GENDER.question, MCcontactFiled.GENDER.option_1),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.GENDER.question, MCcontactFiled.GENDER.option_2),
												   element.getSurveyFTQcontentText(FTcontactField.EMAIL.question),
												   element.getSurveyFTQcontentText(FTcontactField.FIRSTNAME.question),
												   element.getSurveyFTQcontentText(FTcontactField.LASTNAME.question),
												   element.getSurveyMCQcontentText(MCcontactFiled.MARITALSTATUS.question),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.MARITALSTATUS.question, MCcontactFiled.MARITALSTATUS.option_1),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.MARITALSTATUS.question, MCcontactFiled.MARITALSTATUS.option_2),
												   element.getSurveyMCQcontentText(MCcontactFiled.ROLE.question),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.ROLE.question, MCcontactFiled.ROLE.option_1),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.ROLE.question, MCcontactFiled.ROLE.option_2)},
								     new String[] {"Log responses in your CRM system",
    											   "Gender mapping",
												   "Male answer option",
												   "Female answer option",
												   "Email FTQ",
												   "First Name FTQ",
												   "Last Name FTQ",
												   "Marital Status MCQ",
												   "Single answer option",
												   "Married answer option",
												   "Role MCQ",
												   "Employee answer option",
												   "Influencer answer option",},
									 true);
    	
    	return this;
    }  
    
	private SurveyTemplate mapSurveyTemplateThreePages() throws Exception {
	    	
	    	send.msdSurvey.goToPageInSurvey("Page 1").
	    		           dynamicQuestion.editQuestionType(MCcontactFiled.GENDER.question).
					       mapMCQuestionToContactField( MCcontactFiled.GENDER ).
					       addMCResponseCodeOptions().
					       updateQuestion().
					       editQuestionType( FTcontactField.EMAIL.question ).
					       mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled ).
					       updateQuestion().
					       editQuestionType( FTcontactField.FIRSTNAME.question ).
					       mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled ).
					       updateQuestion();
	    	send.msdSurvey.goToPageInSurvey("Page 2").
	    				   dynamicQuestion.
					       editQuestionType( FTcontactField.LASTNAME.question ).
					       mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled ).
					       updateQuestion().
					       editQuestionType(MCcontactFiled.MARITALSTATUS.question).
					       mapMCQuestionToContactField( MCcontactFiled.MARITALSTATUS ).
					       addMCResponseCodeOptions().
					       updateQuestion();
	    	send.msdSurvey.goToPageInSurvey("Page 3").
	    				   dynamicQuestion.
					       editQuestionType(MCcontactFiled.ROLE.question).
					       mapMCQuestionToContactField( MCcontactFiled.ROLE ).
					       addMCResponseCodeOptions().
					       updateQuestion();
	    	
	   	return this;
	}
	
	private SurveyTemplate verifySurveyTemplateMappingsThreePages() throws Exception {
    	
		log.startStep( "Verify that 'log responses in our CRM system check box is checked'" );
		driver.waitUntilElementIsLocated(element.msdLogResponsesCheckbox, driver.timeOut);
		driver.waitUntilElementIsClickable(element.msdLogResponsesCheckbox);
        log.endStep( verifyLogResponseInCRM() );
        
        send.msdSurvey.goToPageInSurvey("Page 1");
        
        send.verifyDisplayedElements(new String[] {element.getFreeTextQuestionMappingIndicator(FTcontactField.EMAIL.question, FTcontactField.EMAIL.conntactFiled),
									        	   element.getFreeTextQuestionMappingIndicator(FTcontactField.FIRSTNAME.question, FTcontactField.FIRSTNAME.conntactFiled),
									        	   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.GENDER.question, MCcontactFiled.GENDER.name)},
									  new String[] {"Email mapping",
													"First Name mapping",
													"Gender mapping"},
									  true);
    	
    	send.msdSurvey.goToPageInSurvey("Page 2");
    	
    	send.verifyDisplayedElements(new String[] {element.getFreeTextQuestionMappingIndicator(FTcontactField.LASTNAME.question, FTcontactField.LASTNAME.conntactFiled),
												   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.MARITALSTATUS.question, MCcontactFiled.MARITALSTATUS.name)},
								     new String[] {"Last Name mapping",
												   "Marital Status mapping"},
								     true);  
    	
    	send.msdSurvey.goToPageInSurvey("Page 3");
    	
    	send.verifyDisplayedElements(new String[] {element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.ROLE.question, MCcontactFiled.ROLE.name)},
								     new String[] {"Role mapping"},
								     true);    	
    	
    	return this;
    }
    
    private SurveyTemplate verifySurveyTemplateThreePagesContent() throws Exception {
    	
    	send.verifyDisplayedElements(new String[] {element.msdLogResponsesCheckbox,
    											   element.msdSurveyTypeDropdown,
    											   element.msdCampaignDropdown,
    											   element.getSurveyMCQcontentText(MCcontactFiled.GENDER.question),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.GENDER.question, MCcontactFiled.GENDER.option_1),
												   element.getSurveyMCQAnswerOption(MCcontactFiled.GENDER.question, MCcontactFiled.GENDER.option_2),
												   element.getSurveyFTQcontentText(FTcontactField.EMAIL.question),
												   element.getSurveyFTQcontentText(FTcontactField.FIRSTNAME.question)},
								     new String[] {"Log responses in your CRM system",
    											   "Survey Type dropdown",
    											   "Campaign dropdown",
    											   "Gender MCQ",
												   "Male answer option",
												   "Female answer option",
												   "Email FTQ",
												   "First Name FTQ"},
									 true).msdSurvey.goToPageInSurvey("Page 2");
	    send.verifyDisplayedElements(new String[] {element.getSurveyMCQcontentText(MCcontactFiled.MARITALSTATUS.question),
											       element.getSurveyMCQAnswerOption(MCcontactFiled.MARITALSTATUS.question, MCcontactFiled.MARITALSTATUS.option_1),
											       element.getSurveyMCQAnswerOption(MCcontactFiled.MARITALSTATUS.question, MCcontactFiled.MARITALSTATUS.option_2),
											       element.getSurveyFTQcontentText(FTcontactField.LASTNAME.question)},
							         new String[] {"Marital Status MCQ",
												   "Single answer option",
												   "Married answer option",
												   "Last Name FTQ"},
									true).msdSurvey.goToPageInSurvey("Page 3");
	    send.verifyDisplayedElements(new String[] {element.getSurveyMCQcontentText(MCcontactFiled.ROLE.question),
											       element.getSurveyMCQAnswerOption(MCcontactFiled.ROLE.question, MCcontactFiled.ROLE.option_1),
											       element.getSurveyMCQAnswerOption(MCcontactFiled.ROLE.question, MCcontactFiled.ROLE.option_2)},
								     new String[] {"Role MCQ",
												   "Employee answer option",
												   "Influencer answer option"},
									true);	    
    	
    	return this;
    }
    
	@Test(groups = { "survey-creation", "all-tests" })
    public void successfullyCreateSurveyWithTemplateQuestionsOnePage() throws Exception {

		String number = driver.getTimestamp();
		String surveyName = number + "surveyName";
		
		log.startTest( "Verify that survey can be successfully created with template quesitons only one page" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
           send.msdSurvey.saveSurvey();
           
           send.goToSurveyPage().msdSurvey.openSurvey(surveyName);
           verifySurveyTemplateOnePageContent().
           verifySurveyTemplateMappingsOnePage();          
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
	
	@Test(groups = { "survey-creation", "all-tests" })
    public void successfullyCreateSurveyWithTemplateQuestionsOnePageNewQuestionAdded() throws Exception {

		String number = driver.getTimestamp();
		String surveyName = number + "surveyName";
		
		log.startTest( "Verify that survey can be successfully created with template quesitons only one page and another question is added" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
           
           send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
								         .enterQuestion( FTcontactField.JOBTITLE.question )
								         .mapFTQuestionToContactField( FTcontactField.JOBTITLE.conntactFiled )
								         .updateQuestion();
           send.msdSurvey.saveSurvey();
           
           send.goToSurveyPage().msdSurvey.openSurvey(surveyName);
           verifySurveyTemplateOnePageContent().
           verifySurveyTemplateMappingsOnePage();          
           
           send.verifyDisplayedElements(new String[] {element.getSurveyFTQcontentText(FTcontactField.JOBTITLE.question)},
									    new String[] {"Job Title mapping"},
									    true);    	
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
	
	@Test(groups = { "survey-creation", "all-tests" })
    public void successfullyRetainCreatedSurveyMappingsWithTemplateOnePage() throws Exception {

		String number = driver.getTimestamp();
		String surveyName = number + "surveyName";
		
		log.startTest( "Verify that survey with template with one page successfully retains its mappings when user click back to Content tab " );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
           send.msdSurvey.saveAndContinueToTheme();
                    
           log.startStep( "Go back to the Survey" );
           driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
           driver.waitForPageToLoad();
           driver.waitForAjaxToComplete( driver.ajaxTimeOut );
           log.endStep();
           
           verifySurveyTemplateOnePageContent().
           verifySurveyTemplateMappingsOnePage();
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
	
	@Test(groups = { "survey-creation", "all-tests" })
    public void successfullyCreateSurveyWithTemplateQuestionsThreePages() throws Exception {

		String number = driver.getTimestamp();
		String surveyName = number + "surveyName";
		
		log.startTest( "Verify that survey can be successfully created with template quesitons with three pages" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
           send.msdSurvey.saveSurvey();
           
           send.goToSurveyPage().msdSurvey.openSurvey(surveyName);
           verifySurveyTemplateThreePagesContent().
           verifySurveyTemplateMappingsThreePages();          
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
	
	@Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyCreateSurveyWithTemplateQuestionsThreePagesNewQuestionAdded() throws Exception {

		String number = driver.getTimestamp();
		String surveyName = number + "surveyName";
		
		log.startTest( "Verify that survey can be successfully created with template quesitons on three pages and another question is added" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
           
           send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
								         .enterQuestion( FTcontactField.JOBTITLE.question )
								         .mapFTQuestionToContactField( FTcontactField.JOBTITLE.conntactFiled )
								         .updateQuestion();
           send.msdSurvey.saveSurvey();
           
           send.goToSurveyPage().msdSurvey.openSurvey(surveyName);
           verifySurveyTemplateThreePagesContent().
           verifySurveyTemplateMappingsThreePages();          
           
           send.verifyDisplayedElements(new String[] {element.getSurveyFTQcontentText(FTcontactField.JOBTITLE.question)},
									    new String[] {"Job Title mapping"},
									    true);    	
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
	
	@Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyRetainCreatedSurveyMappingsWithTemplateThreePages() throws Exception {

		String number = driver.getTimestamp();
		String surveyName = number + "surveyName";
		
		log.startTest( "Verify that survey with template with three pages successfully retains its mappings when user click back to Content tab" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
           send.msdSurvey.saveAndContinueToTheme();
                    
           log.startStep( "Go back to the Survey" );
           driver.click( "//a[contains(text(), 'Content')]", driver.timeOut );
           driver.waitForPageToLoad();
           driver.waitForAjaxToComplete( driver.ajaxTimeOut );
           log.endStep();
           
           verifySurveyTemplateThreePagesContent().
           verifySurveyTemplateMappingsThreePages();
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
	
	@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteSurveyWithTemplateQuestionsOnePage() throws Exception {

		String number = driver.getTimestamp();
		
		String surveyName = number + "surveyName";
		String emailAddress = number + "email@concep.com";
		String firstName = number + "firstName";
        String lastName = number + "lastName";
		
		log.startTest( "Verify that survey can be successfully completed with template quesitons only one page" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
           send.msdSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
																									 	                    new String[]{ MCcontactFiled.GENDER.option_1 } )
																										 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																										 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName )
																										 .answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																										 .answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
																							 	                            new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
																							 	         .answerMcQuestion( MCcontactFiled.ROLE.question,
																							 	                            new String[]{ MCcontactFiled.ROLE.option_1 } );
           
           send.msdSurvey.surveyNext();
           
           questionsArray = new ArrayList<>();
           questionsArray.add( MCcontactFiled.GENDER.question );
           questionsArray.add( FTcontactField.EMAIL.question );
           questionsArray.add( FTcontactField.FIRSTNAME.question );
           questionsArray.add( FTcontactField.LASTNAME.question );
           questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
           questionsArray.add( MCcontactFiled.ROLE.question );

           answersArray = new ArrayList<>();
           answersArray.add( MCcontactFiled.GENDER.option_1 );
           answersArray.add( emailAddress );
           answersArray.add( firstName );
           answersArray.add( lastName );
           answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
           answersArray.add( MCcontactFiled.ROLE.option_1 );

           campaignResponseArray = new ArrayList<>();
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );

           log.startStep( "start parsing to Json Object" );
           SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                          campaignName1,
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
	
	@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteSurveyWithTemplateQuestionsOnePageNewQuestionAdded() throws Exception {

		String number = driver.getTimestamp();
		
		String surveyName = number + "surveyName";
		String emailAddress = number + "email@concep.com";
		String firstName = number + "firstName";
        String lastName = number + "lastName";
        String jobTitle = number + "jobTitle";
		
		log.startTest( "Verify that survey can be successfully completed with template quesitons only one page and another question is added" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
           
           send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
								         .enterQuestion( FTcontactField.JOBTITLE.question )
								         .mapFTQuestionToContactField( FTcontactField.JOBTITLE.conntactFiled )
								         .updateQuestion();
           
           send.msdSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
																									 	                    new String[]{ MCcontactFiled.GENDER.option_1 } )
																										 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																										 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName )
																										 .answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																										 .answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
																							 	                            new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } )
																							 	         .answerMcQuestion( MCcontactFiled.ROLE.question,
																							 	                            new String[]{ MCcontactFiled.ROLE.option_1 } )
																							 	         .answerFtQuestion( FTcontactField.JOBTITLE.question, jobTitle);
           
           send.msdSurvey.surveyNext();
           
           questionsArray = new ArrayList<>();
           questionsArray.add( MCcontactFiled.GENDER.question );
           questionsArray.add( FTcontactField.EMAIL.question );
           questionsArray.add( FTcontactField.FIRSTNAME.question );
           questionsArray.add( FTcontactField.LASTNAME.question );
           questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
           questionsArray.add( MCcontactFiled.ROLE.question );
           questionsArray.add( FTcontactField.JOBTITLE.question );
           
           answersArray = new ArrayList<>();
           answersArray.add( MCcontactFiled.GENDER.option_1 );
           answersArray.add( emailAddress );
           answersArray.add( firstName );
           answersArray.add( lastName );
           answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
           answersArray.add( MCcontactFiled.ROLE.option_1 );
           answersArray.add( jobTitle );

           campaignResponseArray = new ArrayList<>();
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );

           log.startStep( "start parsing to Json Object" );
           SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                          campaignName1,
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
	
	@Test(groups = { "survey-completion", "all-tests" })
    public void successfullyCompleteSurveyWithTemplateQuestionsThreePages() throws Exception {

		String number = driver.getTimestamp();
		
		String surveyName = number + "surveyName";
		String emailAddress = number + "email@concep.com";
		String firstName = number + "firstName";
        String lastName = number + "lastName";
		
		log.startTest( "Verify that survey can be successfully completed with template quesitons with three pages" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
           send.msdSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
																									 	                    new String[]{ MCcontactFiled.GENDER.option_1 } )
																										 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																										 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
           													  send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																										 .answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
																							 	                            new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } );
           													  send.msdSurvey.surveyNext().dynamicQuestion.answerMcQuestion( MCcontactFiled.ROLE.question,
																							 	                            new String[]{ MCcontactFiled.ROLE.option_1 } );
           
           send.msdSurvey.surveyNext();
           
           questionsArray = new ArrayList<>();
           questionsArray.add( MCcontactFiled.GENDER.question );
           questionsArray.add( FTcontactField.EMAIL.question );
           questionsArray.add( FTcontactField.FIRSTNAME.question );
           questionsArray.add( FTcontactField.LASTNAME.question );
           questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
           questionsArray.add( MCcontactFiled.ROLE.question );

           answersArray = new ArrayList<>();
           answersArray.add( MCcontactFiled.GENDER.option_1 );
           answersArray.add( emailAddress );
           answersArray.add( firstName );
           answersArray.add( lastName );
           answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
           answersArray.add( MCcontactFiled.ROLE.option_1 );

           campaignResponseArray = new ArrayList<>();
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );

           log.startStep( "start parsing to Json Object" );
           SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                          campaignName1,
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
	
	@Test(groups = { "survey-completion", "all-tests", "key-tests" })
    public void successfullyCompleteSurveyWithTemplateQuestionsThreePagesNewQuestionAdded() throws Exception {

		String number = driver.getTimestamp();
		
		String surveyName = number + "surveyName";
		String emailAddress = number + "email@concep.com";
		String firstName = number + "firstName";
        String lastName = number + "lastName";
        String jobTitle = number + "jobTitle";
		
		log.startTest( "Verify that survey can be successfully completed with template quesitons on three pages and another question is added" );
        try {

           loginToSendTemplates().goToSurveyPage().msdSurvey.
           startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
           checkLogResponseInCRM().selectSurveyFolders( surveyType1, campaignName1 );
           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
           
           send.msdSurvey.dynamicQuestion.addNewQuestionType( "Free Text", element.ftQId )
								         .enterQuestion( FTcontactField.JOBTITLE.question )
								         .mapFTQuestionToContactField( FTcontactField.JOBTITLE.conntactFiled )
								         .updateQuestion();
           
           send.msdSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().dynamicQuestion.answerMcQuestion( MCcontactFiled.GENDER.question,
																										                     new String[]{ MCcontactFiled.GENDER.option_1 } )
																										 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																										 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
													          send.msdSurvey.surveyNext().dynamicQuestion.answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																										 .answerMcQuestion( MCcontactFiled.MARITALSTATUS.question,
																										                     new String[]{ MCcontactFiled.MARITALSTATUS.option_1 } );
															  send.msdSurvey.surveyNext().dynamicQuestion.answerMcQuestion( MCcontactFiled.ROLE.question,
																										                    new String[]{ MCcontactFiled.ROLE.option_1 } )
																							 	         .answerFtQuestion( FTcontactField.JOBTITLE.question, jobTitle);
           
           send.msdSurvey.surveyNext();
           
           questionsArray = new ArrayList<>();
           questionsArray.add( MCcontactFiled.GENDER.question );
           questionsArray.add( FTcontactField.EMAIL.question );
           questionsArray.add( FTcontactField.FIRSTNAME.question );
           questionsArray.add( FTcontactField.LASTNAME.question );
           questionsArray.add( MCcontactFiled.MARITALSTATUS.question );
           questionsArray.add( MCcontactFiled.ROLE.question );
           questionsArray.add( FTcontactField.JOBTITLE.question );
           
           answersArray = new ArrayList<>();
           answersArray.add( MCcontactFiled.GENDER.option_1 );
           answersArray.add( emailAddress );
           answersArray.add( firstName );
           answersArray.add( lastName );
           answersArray.add( MCcontactFiled.MARITALSTATUS.option_1 );
           answersArray.add( MCcontactFiled.ROLE.option_1 );
           answersArray.add( jobTitle );

           campaignResponseArray = new ArrayList<>();
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );
           campaignResponseArray.add( campaignResponse1 );

           log.startStep( "start parsing to Json Object" );
           SurveyAttributes surveyAttribute = injectSurveyAttributeToken( surveyType1,
                                                                          campaignName1,
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
	
	@Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyCreateSurveyTemplate() throws Exception {
		
		String number = driver.getTimestamp();
		
		String surveyName = number + "surveyName";
		String templateName = number + "templateName";
		
		log.startTest( "Verify that survey template can be successfully created" );
        try {

        	loginToSendTemplates().goToSurveyPage().msdSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName);            
        	            
        	 send.msdSurvey.saveSurveyAsTemplate(surveyName, templateName);
        	 
        	 send.goToSurveyPage().msdSurvey.startSurveyCreation();
        	 send.verifyDisplayedElements(new String[] {element.getSurveyTemplate(templateName)},
        			 					  new String[] {templateName},
        			 					  true);
        	 
        	 send.msdSurvey.selectSurveyTemplate(templateName, surveyName);
        	 verifySurveyTemplateOnePageContent();
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
	
	@Test(groups = { "survey-creation", "all-tests", "key-tests" })
    public void successfullyDeleteSurveyTemplate() throws Exception {
		
		String number = driver.getTimestamp();
		
		String surveyName = number + "surveyName";
		String templateName = number + "templateName";
		
		log.startTest( "Verify that survey template can be successfully created" );
        try {

        	loginToSendTemplates().goToSurveyPage().msdSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName);            
        	            
        	 send.msdSurvey.saveSurveyAsTemplate(surveyName, templateName);
        	 
        	 send.logOut().typeInUserName(sendSuperUser).typeInPassword(sendSuperPassword).loginSendButton().goToAdmin();
        	 send.msdSurvey.deleteSurveyTemplate(templateName);
        	 
        	 send.logOut().typeInUserName(msdSendUserTemplates).typeInPassword(msdSendPassword).loginSendButton().goToSurveyPage().msdSurvey.startSurveyCreation();        	 
        	 
        	 send.verifyDisplayedElements(new String[] {element.getSurveyTemplate(templateName)},
										  new String[] {templateName},
										  false);
           
        } catch( Exception e ) {
        	
            log.endStep( false );
            throw e;
        }
        
        log.endTest();
    }
}
