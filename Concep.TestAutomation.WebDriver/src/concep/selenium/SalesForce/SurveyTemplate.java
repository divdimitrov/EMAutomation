package concep.selenium.SalesForce;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import concep.selenium.send.SendEnum.FTcontactField;
import concep.selenium.send.SendEnum.MCcontactFiled;
import concep.selenium.send.SendEnum.Theme;

public class SurveyTemplate extends BaseSFDC{
	
	 private String surveyTemplateOnePage;
	 private String surveyTemplateThreePages;
	 private String surveyTemplateThreeLanguagePages;
	 
	 @BeforeClass(alwaysRun = true)
	 @Parameters({ "config" })
	 private void createConnectionWithLeadsOnAndContactEntity(
	                                                          @Optional("config/firefox.SFDC") String configLocation )
	                                                                                                                  throws Exception {		 
	    driver.init( configLocation );
		driver.url = driver.config.getProperty( "url" );
		sfdcSendUser = driver.config.getProperty( "sfdcSendUserTemplates" );
		sfdcSendPassword = driver.config.getProperty( "sfdcSendPassword" );
		surveyTemplateOnePage = driver.config.getProperty( "surveyTemplateOnePage");
        surveyTemplateThreePages = driver.config.getProperty( "surveyTemplateThreePages" );
        surveyTemplateThreeLanguagePages = driver.config.getProperty( "surveyTemplateThreeLanguagePages");
		loginToSend().goToConnectionsPage().sfdcConnection.updateExistingConnection( "Contact", true );
		driver.stopSelenium();
	 }
	 
	 private SurveyTemplate verifySurveyTemplateOnePageContent() throws Exception {
	    	
	    	send.verifyDisplayedElements(new String[] {element.sfdcSurveyDropDownMenu,
	    											   element.sfdcCampaignDropDownMenu,
	    											   element.getSurveyMCQcontentText(MCcontactFiled.SALUTATION.question),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.option_1),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.option_2),
													   element.getSurveyFTQcontentText(FTcontactField.EMAIL.question),
													   element.getSurveyFTQcontentText(FTcontactField.FIRSTNAME.question),
													   element.getSurveyFTQcontentText(FTcontactField.LASTNAME.question),
													   element.getSurveyMCQcontentText(MCcontactFiled.LEADSOURCE.question),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.option_1),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.option_2),
													   element.getSurveyMCQcontentText(MCcontactFiled.STATUS.question),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.option_1),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.option_2)},
									     new String[] {"Survey Type dropdown",
	    											   "Campaign dropdown",
	    											   "Salutation mapping",
													   "Mr. answer option",
													   "Mrs. answer option",
													   "Email FTQ",
													   "First Name FTQ",
													   "Last Name FTQ",
													   "Lead SourceMCQ",
													   "Partner answer option",
													   "Other answer option",
													   "Status MCQ",
													   "Active answer option",
													   "Inactive answer option",},
										 true);
	    	
	    	return this;
	    }  
	 	 
	 private SurveyTemplate mapSurveyTemplateOnePage() throws Exception {
	    	
	    	send.sfdcSurvey.sfdcQuestion.editQuestionType(MCcontactFiled.SALUTATION.question).
					       mapMCQuestionToContactField(MCcontactFiled.SALUTATION.name, MCcontactFiled.SALUTATION.option_1, MCcontactFiled.SALUTATION.option_2).
					       mapMcQuestionToLeadField(MCcontactFiled.SALUTATION.name, MCcontactFiled.SALUTATION.option_1, MCcontactFiled.SALUTATION.option_2).
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
					       editQuestionType(MCcontactFiled.LEADSOURCE.question).
					       mapMCQuestionToContactField(MCcontactFiled.LEADSOURCE.name, MCcontactFiled.LEADSOURCE.option_1, MCcontactFiled.LEADSOURCE.option_2).
					       mapMcQuestionToLeadField(MCcontactFiled.LEADSOURCE.name, MCcontactFiled.LEADSOURCE.option_1, MCcontactFiled.LEADSOURCE.option_2).
					       updateQuestion().
					       editQuestionType(MCcontactFiled.STATUS.question).
					       mapMCQuestionToContactField(MCcontactFiled.STATUS.name, MCcontactFiled.STATUS.option_1, MCcontactFiled.STATUS.option_2).
					       mapMcQuestionToLeadField(MCcontactFiled.STATUS.name, MCcontactFiled.STATUS.option_1, MCcontactFiled.STATUS.option_2).
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
										        	   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name),
	        										   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name),
	        										   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name)},
	        						     new String[] {"Email mapping",
	        										   "First Name mapping",
	        										   "Last Name mapping",
	        										   "Salutation mapping",
	        										   "Lead Source mapping",
	        										   "Status mapping"},
	        						     true);    	
	    	return this;
	    }
	 
	 private SurveyTemplate verifySurveyTemplateThreePagesContent() throws Exception {
	    	
	    	send.verifyDisplayedElements(new String[] {element.msdLogResponsesCheckbox,
	    											   element.sfdcSurveyDropDownMenu,
	    											   element.sfdcCampaignDropDownMenu,
	    											   element.getSurveyMCQcontentText(MCcontactFiled.SALUTATION.question),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.option_1),
													   element.getSurveyMCQAnswerOption(MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.option_2),
													   element.getSurveyFTQcontentText(FTcontactField.EMAIL.question),
													   element.getSurveyFTQcontentText(FTcontactField.FIRSTNAME.question)},
									     new String[] {"Log responses in your CRM system",
	    											   "Survey Type dropdown",
	    											   "Campaign dropdown",
	    											   "Salutation MCQ",
													   "Mr. answer option",
													   "Mrs. answer option",
													   "Email FTQ",
													   "First Name FTQ"},
										 true).sfdcSurvey.goToPageInSurvey("Page 2");
		    send.verifyDisplayedElements(new String[] {element.getSurveyMCQcontentText(MCcontactFiled.LEADSOURCE.question),
												       element.getSurveyMCQAnswerOption(MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.option_1),
												       element.getSurveyMCQAnswerOption(MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.option_2),
												       element.getSurveyFTQcontentText(FTcontactField.LASTNAME.question)},
								         new String[] {"Lead Source MCQ",
													   "Partner answer option",
													   "Other answer option",
													   "Last Name FTQ"},
										true).sfdcSurvey.goToPageInSurvey("Page 3");
		    send.verifyDisplayedElements(new String[] {element.getSurveyMCQcontentText(MCcontactFiled.STATUS.question),
												       element.getSurveyMCQAnswerOption(MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.option_1),
												       element.getSurveyMCQAnswerOption(MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.option_2)},
									     new String[] {"Status MCQ",
													   "Active answer option",
													   "Inactive answer option"},
										true);	    
	    	
	    	return this;
	    }
	 
	 private SurveyTemplate mapSurveyTemplateThreePages() throws Exception {
	    	
	    	send.sfdcSurvey.goToPageInSurvey("Page 1").sfdcQuestion.
	    		           editQuestionType(MCcontactFiled.SALUTATION.question).
	    		           mapMCQuestionToContactField(MCcontactFiled.SALUTATION.name, MCcontactFiled.SALUTATION.option_1, MCcontactFiled.SALUTATION.option_2).
					       mapMcQuestionToLeadField(MCcontactFiled.SALUTATION.name, MCcontactFiled.SALUTATION.option_1, MCcontactFiled.SALUTATION.option_2).
					       updateQuestion().
					       editQuestionType( FTcontactField.EMAIL.question ).
					       mapFTQuestionToContactField( FTcontactField.EMAIL.conntactFiled ).
					       updateQuestion().
					       editQuestionType( FTcontactField.FIRSTNAME.question ).
					       mapFTQuestionToContactField( FTcontactField.FIRSTNAME.conntactFiled ).
					       updateQuestion();
	    	send.sfdcSurvey.goToPageInSurvey("Page 2").sfdcQuestion.
					       editQuestionType( FTcontactField.LASTNAME.question ).
					       mapFTQuestionToContactField( FTcontactField.LASTNAME.conntactFiled ).
					       updateQuestion().
					       editQuestionType(MCcontactFiled.LEADSOURCE.question).
					       mapMCQuestionToContactField(MCcontactFiled.LEADSOURCE.name, MCcontactFiled.LEADSOURCE.option_1, MCcontactFiled.LEADSOURCE.option_2).
					       mapMcQuestionToLeadField(MCcontactFiled.LEADSOURCE.name, MCcontactFiled.LEADSOURCE.option_1, MCcontactFiled.LEADSOURCE.option_2).
					       updateQuestion();
	    	send.sfdcSurvey.goToPageInSurvey("Page 3").sfdcQuestion.
					       editQuestionType(MCcontactFiled.STATUS.question).
					       mapMCQuestionToContactField(MCcontactFiled.STATUS.name, MCcontactFiled.STATUS.option_1, MCcontactFiled.STATUS.option_2).
					       mapMcQuestionToLeadField(MCcontactFiled.STATUS.name, MCcontactFiled.STATUS.option_1, MCcontactFiled.STATUS.option_2).
					       updateQuestion();
	    	
	   	return this;
	}
	 
	 private SurveyTemplate verifySurveyTemplateMappingsThreePages() throws Exception {
	    	
			log.startStep( "Verify that 'log responses in our CRM system check box is checked'" );
			driver.waitUntilElementIsLocated(element.msdLogResponsesCheckbox, driver.timeOut);
			driver.waitUntilElementIsClickable(element.msdLogResponsesCheckbox);
	        log.endStep( verifyLogResponseInCRM() );
	        
	        send.sfdcSurvey.goToPageInSurvey("Page 1");
	        
	        send.verifyDisplayedElements(new String[] {element.getFreeTextQuestionMappingIndicator(FTcontactField.EMAIL.question, FTcontactField.EMAIL.conntactFiled),
										        	   element.getFreeTextQuestionMappingIndicator(FTcontactField.FIRSTNAME.question, FTcontactField.FIRSTNAME.conntactFiled),
										        	   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.SALUTATION.question, MCcontactFiled.SALUTATION.name)},
										  new String[] {"Email mapping",
														"First Name mapping",
														"Salutation mapping"},
										  true);
	    	
	    	send.sfdcSurvey.goToPageInSurvey("Page 2");
	    	
	    	send.verifyDisplayedElements(new String[] {element.getFreeTextQuestionMappingIndicator(FTcontactField.LASTNAME.question, FTcontactField.LASTNAME.conntactFiled),
													   element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.LEADSOURCE.question, MCcontactFiled.LEADSOURCE.name)},
									     new String[] {"Last Name mapping",
													   "Lead Source mapping"},
									     true);  
	    	
	    	send.sfdcSurvey.goToPageInSurvey("Page 3");
	    	
	    	send.verifyDisplayedElements(new String[] {element.getMultipleChoiceQUesitonMappingIndicator(MCcontactFiled.STATUS.question, MCcontactFiled.STATUS.name)},
									     new String[] {"Status mapping"},
									     true);    	
	    	
	    	return this;
	    }
	 
	 @Test(groups = { "survey-creation", "all-tests" })
	    public void successfullyCreateSurveyWithTemplateQuestionsOnePage() throws Exception {

			String number = driver.getTimestamp();
			String surveyName = number + "surveyName";
			
			log.startTest( "Verify that survey can be successfully created with template quesitons only one page" );
	        try {

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
	        	checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	        	
	        	verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
	            send.sfdcSurvey.saveSurvey();
	            
	            send.goToSurveyPage().sfdcSurvey.openSurvey(surveyName);
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
	        	checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	        	
	           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
	           
	           send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
									         .enterQuestion( FTcontactField.FULLNAME.question )
									         .mapFTQuestionToContactField( FTcontactField.FULLNAME.conntactFiled )
									         .mapFTQuestionToLeadField( FTcontactField.FULLNAME.conntactFiled )
									         .updateQuestion();
	           send.sfdcSurvey.saveSurvey();
	           
	           send.goToSurveyPage().sfdcSurvey.openSurvey(surveyName);
	           verifySurveyTemplateOnePageContent().
	           verifySurveyTemplateMappingsOnePage();          
	           
	           send.verifyDisplayedElements(new String[] {element.getSurveyFTQcontentText(FTcontactField.FULLNAME.question)},
										    new String[] {"Full Name mapping"},
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
	        	checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	        	
	           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
	           send.sfdcSurvey.saveAndContinueToTheme();
	                    
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

	           loginToSend().goToSurveyPage().sfdcSurvey.
	           startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
	           checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
	           send.sfdcSurvey.saveSurvey();
	           
	           send.goToSurveyPage().sfdcSurvey.openSurvey(surveyName);
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.
		        startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
		        checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
	           
	           send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
									       .enterQuestion( FTcontactField.FULLNAME.question )
									       .mapFTQuestionToContactField( FTcontactField.FULLNAME.conntactFiled )
									       .mapFTQuestionToLeadField( FTcontactField.FULLNAME.conntactFiled )
									       .updateQuestion();
	           
	           send.sfdcSurvey.saveSurvey();
	           
	           send.goToSurveyPage().sfdcSurvey.openSurvey(surveyName);
	           verifySurveyTemplateThreePagesContent().
	           verifySurveyTemplateMappingsThreePages();          
	           
	           send.verifyDisplayedElements(new String[] {element.getSurveyFTQcontentText(FTcontactField.FULLNAME.question)},
										    new String[] {"Full Name mapping"},
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.
		        startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
		        checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
	           send.sfdcSurvey.saveAndContinueToTheme();
	                    
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
	        	checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	        	
	           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
	           send.sfdcSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																										 	                    new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																											 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																											 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName )
																											 .answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																											 .answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
																								 	                            new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
																								 	         .answerMcQuestion( MCcontactFiled.STATUS.question,
																								 	                            new String[]{ MCcontactFiled.STATUS.option_1 } );
	           
	           send.sfdcSurvey.surveyNext();
	           
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
	        String fullName = number + "fullName";
			
			log.startTest( "Verify that survey can be successfully completed with template quesitons only one page and another question is added" );
	        try {
	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName).
	        	checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
	           
	           send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
		       .enterQuestion( FTcontactField.FULLNAME.question )
		       .mapFTQuestionToContactField( FTcontactField.FULLNAME.conntactFiled )
		       .mapFTQuestionToLeadField( FTcontactField.FULLNAME.conntactFiled )
		       .updateQuestion();
	           
	           verifySurveyTemplateOnePageContent().mapSurveyTemplateOnePage();
	           send.sfdcSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																										 	                    new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																											 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																											 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName )
																											 .answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																											 .answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
																								 	                            new String[]{ MCcontactFiled.LEADSOURCE.option_1 } )
																								 	         .answerMcQuestion( MCcontactFiled.STATUS.question,
																								 	                            new String[]{ MCcontactFiled.STATUS.option_1 } )
																								 	         .answerFtQuestion( FTcontactField.FULLNAME.question, fullName );

	           send.sfdcSurvey.surveyNext();
	           
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
	        	checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
	           send.sfdcSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																										 	                    new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																											 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																											 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
	           													  send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																											 .answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
																								 	                            new String[]{ MCcontactFiled.LEADSOURCE.option_1 } );
	           													  send.sfdcSurvey.surveyNext().sfdcQuestion.answerMcQuestion( MCcontactFiled.STATUS.question,
																								 	                            new String[]{ MCcontactFiled.STATUS.option_1 } );
	           
	           send.sfdcSurvey.surveyNext();
	           
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
	        String fullName = number + "fullName";
			
			log.startTest( "Verify that survey can be successfully completed with template quesitons on three pages and another question is added" );
	        try {

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateThreePages, surveyName).
	        	checkLogResponseInCRM().selectSurveyFolders( surveyType1, sfdcCampaign1 );
	           verifySurveyTemplateThreePagesContent().mapSurveyTemplateThreePages();
	           
	           send.sfdcSurvey.sfdcQuestion.addNewQuestionType( "Free Text", element.ftQId )
		       .enterQuestion( FTcontactField.FULLNAME.question )
		       .mapFTQuestionToContactField( FTcontactField.FULLNAME.conntactFiled )
		       .mapFTQuestionToLeadField( FTcontactField.FULLNAME.conntactFiled )
		       .updateQuestion();
	           
	           send.sfdcSurvey.saveAndSelectTheme( Theme.TABLE.type ).viewAndDeployTheSurvey().sfdcQuestion.answerMcQuestion( MCcontactFiled.SALUTATION.question,
																											                     new String[]{ MCcontactFiled.SALUTATION.option_1 } )
																											 .answerFtQuestion( FTcontactField.EMAIL.question, emailAddress )
																											 .answerFtQuestion( FTcontactField.FIRSTNAME.question, firstName );
														          send.sfdcSurvey.surveyNext().sfdcQuestion.answerFtQuestion( FTcontactField.LASTNAME.question, lastName )
																											 .answerMcQuestion( MCcontactFiled.LEADSOURCE.question,
																											                     new String[]{ MCcontactFiled.LEADSOURCE.option_1 } );
																  send.sfdcSurvey.surveyNext().sfdcQuestion.answerMcQuestion( MCcontactFiled.STATUS.question,
																											                    new String[]{ MCcontactFiled.STATUS.option_1 } )
																								 	        .answerFtQuestion( FTcontactField.FULLNAME.question, fullName );
	           
	           send.sfdcSurvey.surveyNext();
	           
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName);            
	        	            
	        	 send.sfdcSurvey.saveSurveyAsTemplate(surveyName, templateName);
	        	 
	        	 send.goToSurveyPage().sfdcSurvey.startSurveyCreation();
	        	 send.verifyDisplayedElements(new String[] {element.getSurveyTemplate(templateName)},
	        			 					  new String[] {templateName},
	        			 					  true);
	        	 
	        	 send.sfdcSurvey.selectSurveyTemplate(templateName, surveyName);
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

	        	loginToSend().goToSurveyPage().sfdcSurvey.startCreatingSurveyUsingTemplate(surveyTemplateOnePage, surveyName);            
	        	            
	        	 send.sfdcSurvey.saveSurveyAsTemplate(surveyName, templateName);
	        	 
	        	 send.logOut().typeInUserName(sendSuperUser).typeInPassword(sendSuperPassword).loginSendButton().goToAdmin();
	        	 send.sfdcSurvey.deleteSurveyTemplate(templateName);
	        	 
	        	 send.logOut().typeInUserName(sfdcSendUser).typeInPassword(sfdcSendPassword).loginSendButton().goToSurveyPage().sfdcSurvey.startSurveyCreation();        	 
	        	 
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
