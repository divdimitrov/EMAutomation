package concep.selenium.send;

public class IASurvey extends Surveys<IASurvey> {

    public IASurvey selectSurveyFolders(
                                         String surveyType,
                                         String eventFolder ) throws Exception {

        log.startStep( "Verify that the 'Interaction Folder' drop down is displayed" );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep( driver.isElementPresent( "//span[@id='select2-surveyInteractionFolder-container']",
                                              driver.timeOut ) );

        if( eventFolder != "" ) {

            log.startStep( "Select the folder '" + eventFolder + "' from the 'Interaction Folder' drop down" );
            driver.select2DropDown( element.iaEventFolderDropDown_2, element.crmDropDownSearch, eventFolder );
            driver.waitForAjaxToComplete( driver.ajaxTimeOut );
            log.endStep();
        }

        if( surveyType != "" ) {

            log.startStep( "Select the 'Survey Type' option " + surveyType );
            driver.select2DropDown( element.crmSurveyTypeDropDown, element.crmDropDownSearch, surveyType );
            log.endStep();
        }
        return this;
    }

}
