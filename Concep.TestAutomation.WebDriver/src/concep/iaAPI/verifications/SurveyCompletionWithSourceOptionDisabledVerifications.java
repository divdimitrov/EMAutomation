package concep.iaAPI.verifications;

import org.testng.annotations.Test;

public class SurveyCompletionWithSourceOptionDisabledVerifications extends BaseIAapiVerifications {

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWhenSourcethemInThisFolderEnabled()
                                                                                             throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey no sponsor folder when source them in this folder is enabled",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyAdministrativeFolderWhenSourcethemInThisFolderEnabled()
                                                                                                  throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey administrative folder when source them in this folder is enabled",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourcethemInThisFolderEnabled()
                                                                                               throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey with sponsor folder when source them in this folder is enabled",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyNoSponsorFolderWhenSourcethemInThisFolderEnabledWithExistingCompany()
                                                                                                                throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey no sponsor folder when source them in this folder is enabled"
                                          + " with existing company",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyAdministrativeFolderWhenSourcethemInThisFolderEnabledWithExistingCompany()
                                                                                                                     throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey administrative folder when source them in this folder is enabled"
                                          + " with existing company",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourcethemInThisFolderEnabledWithExistingCompany()
                                                                                                                  throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey with sponsor folder when source them in this folder is enabled"
                                          + " with existing company",
                                  fileName );
    }

    @Test(groups = { "all-tests" })
    public void successfullyCompletedSurveyWithSponsorFolderWhenSourcethemInThisFolderEnabledAndtypeContactOnly()
                                                                                                                 throws Exception {

        String fileName = Thread.currentThread().getStackTrace()[1].getMethodName();

        iaParametersVerification( "Verify successfully completion of survey with sponsor folder when source them in this folder is enabled"
                                          + " and type contact only",
                                  fileName );
    }
}
