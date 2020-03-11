package concep.iaAPI.verifications;

import org.testng.annotations.Test;

public class SurveyCompletionWithAllMappingsVerifications extends BaseIAapiVerifications {

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithDifferentTypesOfAdditionalField() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey with different types of additional field",
                                  fileName );
    }
    
    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithBMobileHMobileDepartmentAssistantFields() throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey with Department, Assistant, Business Mobile Phone and Home Mobile Phone fields",
                                  fileName );
    }
}
