package concep.selenium.send;

public class SFDCSurvey extends Surveys<SFDCSurvey> {

    public SFDCSurvey selectSurveyFolders(
                                           String surveyType,
                                           String sfdcCampaign ) throws Exception {

        if( surveyType != "" ) {

            log.startStep( "Select the 'Survey Type' option " + surveyType );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            driver.select2DropDown( element.sfdcSurveyDropDownMenu, element.crmDropDownSearch, surveyType );
            log.endStep();

        }

        if( sfdcCampaign != "" ) {

            log.startStep( "Select a campaign '" + sfdcCampaign + "' from the 'Campaign' drop down" );
            driver.select2DropDown( element.sfdcCampaignDropDownMenu, element.crmDropDownSearch, sfdcCampaign );
            log.endStep();
        }

        return this;
    }
}
