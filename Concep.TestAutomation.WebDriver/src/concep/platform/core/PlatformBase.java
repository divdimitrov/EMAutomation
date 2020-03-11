package concep.platform.core;

import java.security.InvalidParameterException;

import concep.Utilities.LogResults;
import concep.seleniumWD.core.SeleniumDriverCore;

public class PlatformBase {

    protected SeleniumDriverCore driver;
    protected PlatformElement    element;
    protected LogResults         log;

    public PlatformBase( SeleniumDriverCore driver ) {
        this.driver = driver;
    }

    public void navigateToPage(
                                PlatformPage page ) throws Exception {

        log.startStep( "Navigate to page: " + page );

        switch( page ){
            case SYSTEMS:
                driver.click( this.element.systemsPageTab() );
                break;
            case ALERT:
                driver.click( this.element.alertPageTab() );
                break;
            case SCHEDULES:
                driver.click( this.element.schedulesPageTab() );
                break;
            case LOGS:
                driver.click( this.element.logsPageTab() );
                break;
            default:
                throw new InvalidParameterException( "The following parameter is invalid: " + page );
        }

        driver.waits.waitForPageToLoad();
        log.endStep();
    }

    public void startCreatingRecord(
                                     boolean isSubgridRecord ) throws Exception {

        if( !isSubgridRecord ) {

            log.startStep( "Click on the '+' button" );
            // driver.waitUntilElementIsLocated(element.addNewRecord(),
            // driver.waitUntilElementIsClickable(element.addNewRecord);
            driver.click( element.addNewRecord() );
            // driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

        } else {

            log.startStep( "Click on the '+' button" );
            driver.click( element.addSubgridRecord() );
            // driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
        }
    }

    public void saveRecord(
                            boolean isSubgridRecord ) throws Exception {

        if( !isSubgridRecord ) {

            log.startStep( "Save the record" );
            driver.click( element.saveRecord() );
            // driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();

        } else {

            log.startStep( "Save the record" );
            driver.click( element.saveSubgridRecord() );
            // driver.waitForAjaxToComplete(driver.ajaxTimeOut);
            log.endStep();
        }
    }
}