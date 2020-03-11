package concep.iaAPI.verifications;

import org.testng.annotations.Test;

public class SurveyCompletionVerifications extends BaseIAapiVerifications {

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWithExistingCompany() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey no sponsor folder with existing company",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyNoSponsorContactOnlyFolderWithExistingCompany() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey no sponsor contact only with existing contact",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWithExistingContact() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey no sponsor folder with existing contact",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyTypeAdministrativeContactOnlyfolderWithExistingCompnay()
                                                                                                   throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey type administrative contact only,"
                                          + " folder with existing company sponsor company with existing contact",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorfolderWithExistingCompany() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey type sponsor folder with existing company",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorContactOnlyfolderWithExistingCompany() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey type sponsor contact only, "
                                  + "folder with existing company", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorfolderWithExistingContact() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey type sponsor folder with existing contact",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWithExistingContactInConnectionSearchFolder()
                                                                                                       throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey no sponsor folder with existing contact"
                                          + "in connection search folder",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyTypeSponsorfolderWithExistingCompanyInConnectionSearchFolder()
                                                                                                         throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey type sponsor folder with existing company"
                                          + "in connection search folder",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveySponsorFolderWithExistingContactInIaButNotInSourceFolder()
                                                                                                     throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey type sponsor folder with existing contact"
                                          + "in IA but not in source folder",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWith10Pages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with 10 pages", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWith3Pages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with 3 pages", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWith5TypesOfQuestions() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with 5 types of questions", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropAndAddQuestion() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with drag and drop and add question", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropOn3Pages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with drag and drop on 3 pages", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropQuestionsOnly() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with drag and drop questions only", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithLoginAs() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with Login As", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompleteSurveyWithOutAnsweringMCMappedQuestion() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey without answering multiple choice mapped question",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompleteSurveyWithOutAnsweringFTMappedQuestion() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey without answering free text mapped question",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompleteSurveyWithOutAnsweringMCQuestion() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey without answering multiple choice question",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithRSVPQuestion() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with RSVP question", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCreatedCompanyInSponsorFolderWhenPartOfCompanyNameExist() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify creation of company in sponsor folder when part of company name exist",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithSpecialCharacters() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with special characters", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithHyphen() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with hyphen", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDigits0to9InTheEmail() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with digits from 0 to 9 in the email",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDot() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with dot", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithRequiredFTQ() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with required FTQ", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithBusinessRequiredMappings() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with business required mappings", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithMCQWithDropDown() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with MCQ with drop down", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithMCQWithMultipleAnswers() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with MCQ with multiple answers", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithRequiredMCQ() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with required MCQ", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithPageJumping() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with page jumping", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithContentQuestionWithLink() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with content question with link", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithAdditionalContentQuestion() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with additional content question", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDragAndDropContentQuestion() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with drag and drop content question", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithEmailConfirmation() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify completion of survey with email confirmation", fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithTwoAdditionalLanguagePages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that survey can be successfully completed with Two additional language pages",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithThreeAdditionalLanguagePages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that survey can be successfully completed with three additional language pages",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyUsingSecondaryLanguage() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that survey can be successfully submitted via the secondary language",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyUsingThirdLanguage() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that survey can be successfully submitted via the secondary language",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDifferentSpecialCharacters() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that survey must be completed successfully when email contains special legal characters",
                                  fileName );
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDifferentSpecialCharactersInCompanyName() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that survey must be completed successfully when email contains special legal characters in the company name",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePage() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same page",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsDifferentPages()
                                                                                               throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on different pages",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredSamePage() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that blank values are successfully handled when only first question is answered on the same page",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredSamePage() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that blank values are successfully handled when only second question is answered on the same page",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyHandleBlankValuesOnlyFirstQuestionAsnweredDifferentPages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that blank values are successfully handled when only first question is answered on different pages",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyHandleBlankValuesOnlySecondQuestionAsnweredDifferentPages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that blank values are successfully handled when only second question is answered on different pages",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsSamePage() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on the same page",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyHandleBlankValuesAllDuplicatedQuestionsDifferentPages() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that blank values are successfully handled when user doesn't answer on the duplicated questions on different pages",
                                  fileName );
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyCompleteSurveyWithNotMappedFTQs() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that both not mapped FTQ responses will be logged as answers to the CRM",
                                  fileName );
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyCompleteSurveyWithNotMappedMCQOnlyMappedToCampaignResponse() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that both not mapped MCQ campaign responses will be logged to the CRM",
                                  fileName );
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyLogHighestIDQuestionEnteredValueToCrmDuplicatedQuestionMappingsSamePageAndTwoNotMappedMCQCampaignResponses() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that the highest ID question entered value is logged to the CRM when there are duplicated question mappings on the same page together with the not mapped MCQs (only to RSVP field)",
                                  fileName );
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyCompleteSurveyWithOneMappedMCQAndMCQMappedOnlyToCampaignResponse() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify that both MCQ campaign responses will be logged to the CRM, when one of the MCQ is not mapped to contact field",
                                  fileName );
    }
}
