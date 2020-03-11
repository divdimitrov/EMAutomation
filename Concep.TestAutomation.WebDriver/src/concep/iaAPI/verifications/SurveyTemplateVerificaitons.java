package concep.iaAPI.verifications;

import org.testng.annotations.Test;

	public class SurveyTemplateVerificaitons extends BaseIAapiVerifications {

	 @Test(groups = { "all-tests" })
	 public void successfullyCompleteSurveyWithTemplateQuestionsOnePage() throws Exception {

		 String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

	     iaParametersVerification( "Verify successfully completion of survey with template questions on one page",
	                                  fileName );
	 }
	 
	 @Test(groups = { "all-tests" })
	 public void successfullyCompleteSurveyWithTemplateQuestionsOnePageNewQuestionAdded() throws Exception {

		 String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

	     iaParametersVerification( "Verify successfully completion of survey with template questions on one page and a new question added",
	                                  fileName );
	 }
	 
	 @Test(groups = { "all-tests" })
	 public void successfullyCompleteSurveyWithTemplateQuestionsThreePages() throws Exception {

		 String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

	     iaParametersVerification( "Verify successfully completion of survey with template questions on three pages",
	                                  fileName );
	 }
	 
	 @Test(groups = { "all-tests" })
	 public void successfullyCompleteSurveyWithTemplateQuestionsThreePagesNewQuestionAdded() throws Exception {

		 String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

	     iaParametersVerification( "Verify successfully completion of survey with template questions on three pages and a new question added",
	                                  fileName );
	 }
}
