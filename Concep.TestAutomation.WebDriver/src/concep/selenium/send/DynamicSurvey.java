package concep.selenium.send;

public class DynamicSurvey extends Surveys<DynamicSurvey> {

    public DynamicSurvey selectSurveyFolders(
                                              String surveyType,
                                              String Campaign ) throws Exception {

        if( surveyType != "" ) {

            log.startStep( "Select the 'Survey Type' option " + surveyType );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.select2DropDown( "//span[@id='select2-surveyTypeDropDown-container']",
                                    element.crmDropDownSearch,
                                    surveyType );
            log.endStep();
        }

        if( Campaign != "" ) {

            log.startStep( "Select a campaign '" + Campaign + "' from the 'Campaign' drop down" );
            driver.select2DropDown( "//span[@id='select2-surveyDynamicsCampaigns-container']",
                                    element.crmDropDownSearch,
                                    Campaign );
            log.endStep();
        }

        return this;
    }

}
