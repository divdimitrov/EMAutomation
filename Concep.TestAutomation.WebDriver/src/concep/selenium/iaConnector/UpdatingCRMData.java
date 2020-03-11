/*

*/

package concep.selenium.iaConnector;


public class UpdatingCRMData extends BaseIAconnector {

    /* *//**
          * 
          * Verify that by Completing a Survey an Existing Company can Successfully be Updated
          * 
          * @throws Exception 
          * 
          */
    /*

    @Test(groups = { "surveys", "all-tests", "updating-data" })
    public void successfullyUpatingCompany() throws Exception {

     startTest( "Verify that by Completing a Survey an Existing Company can Successfully be Updated" );

     String number = getTimestamp();
     String emailAddress = iaExistingContact;
     String lastName = number + "lastName";
     String company = iaExistingCompany;
     String firstName = number + "firstName";
     String surveyName = number + surveyType1 + eventFolderAdministrative;
     String IAfolderValueFT1 = "Primary Email";
     String IAfolderValueFT2 = "Last Name";
     String IAfolderValueFT3 = "Company";
     String IAfolderValueFT4 = "First Name";
     String theme = "Aqua";

     try {

         //Go to Survey Page.
         loginAndGoToSurveyPage();

         //Create and name the survey.
         createSurveyAndTypeSurveyName( surveyName );

         //Select InterAction Event Folder and Survey Type
         selectInterActionFolderAndSurveyType( surveyType1, eventFolderAdministrative );

         //Edit the already existing Free Text Question.
         addAndUpdateFreeTextQuestion( "", emailQuestion, IAfolderValueFT1 );

         //Edit the already existing Multiple Choice Question.
         editAndUpdateMultipleChoiceQuestion( colorQuestionGF,
                                              GlobalFieldTypeList2,
                                              "Orange",
                                              "Yellow",
                                              "Orange",
                                              "Yellow",
                                              false );

         //Add New Free Text Question For Last Name.
         addAndUpdateFreeTextQuestion( "new", lNameQuestion, IAfolderValueFT2 );

         //Add New Free Text Question For Company.
         addAndUpdateFreeTextQuestion( "new", companyQuestion, IAfolderValueFT3 );

         //Add New Free Text Question For First Name.
         addAndUpdateFreeTextQuestion( "new", fNameQuestion, IAfolderValueFT4 );

         //Press Save and Continue, select a Theme and go to Deploy Tab.
         saveSelectTemplateAndDeploySurvey( theme );

         //Deploy the Survey.
         startStep( "Click the 'Preview Survey' button" );
         click( "//a/span[contains(text(), 'View Survey')]", timeOut );
         endStep();

         //Add Answers to the Questions and Complete the Survey.
         startStep( "Verify that the survey's preview page is displayed" );
         Thread.sleep( 3000 );
         selectWindow();
         endStep( isElementPresent( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]",
                                    timeOut ) );

         startStep( "Select 'Orange' option of the " + colorQuestionGF + " question " );
         click( "//div[@id='q_34281']//label[1]", timeOut );
         endStep();

         startStep( "Enter an email " + emailAddress + " in the " + emailQuestion + " question text field" );
         type( "//input[@id='q_13147_text']", emailAddress, timeOut );
         endStep();

         startStep( "Enter a Last Name " + lastName + " in the " + lNameQuestion + " question text field" );
         type( "//label[contains(text(),'" + lNameQuestion + "')]/../..//input[@id[starts-with(.,'q_')]]",
               lastName,
               timeOut );
         endStep();

         startStep( "Enter a Company " + company + " in the " + companyQuestion + " question text field" );
         type( "//label[contains(text(),'" + companyQuestion
               + "')]/../..//input[@id[starts-with(.,'q_')]]", company, timeOut );
         endStep();

         startStep( "Enter a First Name " + firstName + " in the " + fNameQuestion
                    + " question text field" );
         type( "//label[contains(text(),'" + fNameQuestion + "')]/../..//input[@id[starts-with(.,'q_')]]",
               firstName,
               timeOut );
         endStep();

         startStep( "Click the Next button to complete the survey" );
         click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", timeOut );
         waitForAjaxToComplete( timeOut );
         endStep();

     } catch( Exception e ) {
         endStep( false );
         throw e;
     }

     endTest();
    }

    *//**
      * 
      * Verify that by Completing a Survey First and Last Name of an Existing Person can be Updated.
      * 
      * @throws Exception 
      * 
      */
    /*

    @Test(groups = { "surveys", "all-tests", "updating-data" })
    public void successfullyUpdatingAllPersonsFirstAndLastName() throws Exception {

     startTest( "Verify that by Completing a Survey First and Last Name of an Existing Person can be Updated." );

     String number = getTimestamp();
     String emailAddress = iaExistingContact;
     String lastName = number + "lastName";
     String firstName = number + "firstName";
     String surveyName = number + surveyType2 + eventFolderWithSponsor;
     String IAfolderValueFT1 = "Primary Email";
     String IAfolderValueFT2 = "Last Name";
     String IAfolderValueFT3 = "First Name";
     String theme = "Water";

     try {

         //Go to Survey Page.
         loginAndGoToSurveyPage();

         //Create and name the survey.
         createSurveyAndTypeSurveyName( surveyName );

         //Select InterAction Event Folder and Survey Type
         selectInterActionFolderAndSurveyType( surveyType2, eventFolderWithSponsor );

         //Edit the already existing Free Text Question.
         addAndUpdateFreeTextQuestion( "", emailQuestion, IAfolderValueFT1 );

         //Edit the already existing Multiple Choice Question.
         editAndUpdateMultipleChoiceQuestion( colorQuestionGF,
                                              GlobalFieldTypeList2,
                                              "Orange",
                                              "Yellow",
                                              "Orange",
                                              "Yellow",
                                              false );

         //Add New Free Text Question For Last Name.
         addAndUpdateFreeTextQuestion( "new", lNameQuestion, IAfolderValueFT2 );

         //Add New Free Text Question For First Name.
         addAndUpdateFreeTextQuestion( "new", fNameQuestion, IAfolderValueFT3 );

         //Press Save and Continue, select a Theme and go to Deploy Tab.
         saveSelectTemplateAndDeploySurvey( theme );

         //Deploy the Survey.
         startStep( "Click the 'Preview Survey' button" );
         click( "//a/span[contains(text(), 'View Survey')]", timeOut );
         endStep();

         //Add Answers to the Questions and Complete the Survey.
         startStep( "Verify that the survey's preview page is displayed" );
         Thread.sleep( 3000 );
         selectWindow();
         endStep( isElementPresent( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]",
                                    timeOut ) );

         startStep( "Select 'Orange' option of the " + colorQuestionGF + " question " );
         click( "//div[@id='q_34281']//label[1]", timeOut );
         endStep();

         startStep( "Enter an email " + emailAddress + " in the " + emailQuestion + " question text field" );
         type( "//input[@id='q_13147_text']", emailAddress, timeOut );
         endStep();

         startStep( "Enter a Last Name " + lastName + " in the " + lNameQuestion + " question text field" );
         type( "//label[contains(text(),'" + lNameQuestion + "')]/../..//input[@id[starts-with(.,'q_')]]",
               lastName,
               timeOut );
         endStep();

         startStep( "Enter a First Name " + firstName + " in the " + fNameQuestion
                    + " question text field" );
         type( "//label[contains(text(),'" + fNameQuestion + "')]/../..//input[@id[starts-with(.,'q_')]]",
               firstName,
               timeOut );
         endStep();

         startStep( "Click the Next button to complete the survey" );
         click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", timeOut );
         waitForAjaxToComplete( timeOut );
         endStep();

     } catch( Exception e ) {
         endStep( false );
         throw e;
     }

     endTest();
    }

    *//**
      * 
      * Verify that First Name is not Overwritten when Survey is Completed with Primary Email and Last Name.
      * 
      * @throws Exception 
      * 
      * PLAT-1479
      * 
      */
    /*
    @Test(groups = { "surveys", "updating-data" })
    public void successfullyUpdatingExistingPersonLastName() throws Exception {

     startTest( "Verify that First Name is not Overwritten when Survey is Completed with Primary Email and Last Name." );

     String number = getTimestamp();
     String emailAddress = iaExistingContact;
     String lastName = number + "lastName";
     String surveyName = number + surveyType3 + eventFolderNoSponsor;
     String IAfolderValueFT1 = "Primary Email";
     String IAfolderValueFT2 = "Last Name";
     String IAfolderValueMC = "Working Day";
     String mappingOption1 = "Monday";
     String mappingOption2 = "Friday";
     String answer1 = "Monday";
     String answer2 = "Friday";
     String theme = "Oceanic";

     try {

         //Go to Survey Page.
         loginAndGoToSurveyPage();

         //Create and name the survey.
         createSurveyAndTypeSurveyName( surveyName );

         //Select InterAction Event Folder and Survey Type
         selectInterActionFolderAndSurveyType( surveyType3, eventFolderNoSponsor );

         //Edit the already existing Free Text Question.
         addAndUpdateFreeTextQuestion( "", emailQuestion, IAfolderValueFT1 );

         //Edit the already existing Multiple Choice Question.
         editAndUpdateMultipleChoiceQuestion( colorQuestionGF,
                                              IAfolderValueMC,
                                              mappingOption1,
                                              mappingOption2,
                                              answer1,
                                              answer2,
                                              false );

         //Add New Free Text Question For Last Name.
         addAndUpdateFreeTextQuestion( "new", lNameQuestion, IAfolderValueFT2 );

         //Press Save and Continue, select a Theme and go to Deploy Tab.
         saveSelectTemplateAndDeploySurvey( theme );

         //Deploy the Survey.
         startStep( "Click the 'Preview Survey' button" );
         click( "//a/span[contains(text(), 'View Survey')]", timeOut );
         endStep();

         //Add Answers to the Questions and Complete the Survey.
         startStep( "Verify that the survey's preview page is displayed" );
         Thread.sleep( 3000 );
         selectWindow();
         endStep( isElementPresent( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]",
                                    timeOut ) );

         startStep( "Select 'Orange' option of the " + colorQuestionGF + " question " );
         click( "//div[@id='q_34281']//label[1]", timeOut );
         endStep();

         startStep( "Enter an email " + emailAddress + " in the " + emailQuestion + " question text field" );
         type( "//input[@id='q_13147_text']", emailAddress, timeOut );
         endStep();

         startStep( "Enter a Last Name " + lastName + " in the " + lNameQuestion + " question text field" );
         type( "//label[contains(text(),'" + lNameQuestion + "')]/../..//input[@id[starts-with(.,'q_')]]",
               lastName,
               timeOut );
         endStep();

         startStep( "Click the Next button to complete the survey" );
         click( "//div[@id='surveyInnerContainer']//span[contains(text(), 'Next')]", timeOut );
         waitForAjaxToComplete( timeOut );
         endStep();

     } catch( Exception e ) {
         endStep( false );
         throw e;
     }

     endTest();
    }
    */
}
