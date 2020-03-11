package concep.selenium.send;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;

public abstract class Connections<T> {

    protected Elements                        element = new Elements();
    protected static GenericSeleniumWebDriver driver  = DriverAccessor.getDriverInstance();
    protected LogResults                      log     = new LogResults();

    @SuppressWarnings("unchecked")
    public T createConnection(
                               String connectionName,
                               String connectionuserName,
                               String connectionPassword ) throws Exception {

        //Create a new connection
        log.startStep( "Click on the create new connection link" );
        driver.click( element.connectionOption, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Click the connection window" );
        driver.click( element.connectionWindow, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        log.startStep( "Verify that 2nd window for connection creation is displayed" );
        log.endStep( driver.isTextPresent( element.connectionLoginForm,
                                           "Please fill in the login information",
                                           driver.timeOut ) );

        log.startStep( "Enter the '" + connectionName + "' in the connection name text" );
        driver.type( element.connectionNameText, connectionName, driver.timeOut );
        log.endStep();

        log.startStep( "Enter the connection username" );
        driver.type( element.connectionUserNameText, connectionuserName, driver.timeOut );
        log.endStep();

        log.startStep( "Enter the connection password" );
        driver.type( element.connectionPasswordText, connectionuserName, driver.timeOut );
        log.endStep();

        log.startStep( "Click the 'Create' button" );
        driver.click( element.createConnectionButton, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );

        log.endStep();
        return ( T ) this;
    }
    
    @SuppressWarnings("unchecked")
    public T selectExistingConnection() throws Exception {
    	
    	 log.startStep( "Click on the exiting connection" );
         driver.click( element.connectionWindow, driver.timeOut );
         driver.waitForAjaxToComplete( driver.ajaxTimeOut );
         log.endStep();
         
         return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T loginToExistingConnection(
                                        String connectionUserName,
                                        String connectionPassword ) throws Exception {       

    	selectExistingConnection();
    	
        log.startStep( "Enter the connection username: " + connectionUserName );
        driver.clear( element.connectionUserNameText );
        driver.type( element.connectionUserNameText, connectionUserName, driver.timeOut );
        log.endStep();

        log.startStep( "Enter the connection password: " + connectionPassword );
        driver.clear( element.connectionPasswordText );
        driver.type( element.connectionPasswordText, connectionPassword, driver.timeOut );
        log.endStep();

        log.startStep( "Click the 'Create' button" );
        driver.click( element.createConnectionButton, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T loginToConnection(
                                String connectionUserName,
                                String connectionPassword ) throws Exception {

        if( driver.isElementPresent( element.connectionWindow, driver.timeOut ) ) {
            loginToExistingConnection( connectionUserName, connectionPassword );
        } else {
            createConnection( "Connection", connectionUserName, connectionPassword );
        }

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T saveAndCloseConnectionPage() throws Exception {

        log.startStep( "Click the 'Save and close' button" );
        driver.click( element.connectionSaveAndCloseButton, driver.timeOut );
        driver.waitForAjaxToComplete( driver.ajaxTimeOut );
        driver.waitForPageToLoad();
        log.endStep();
        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T enableSurveys(
                            String elementXpath ) throws Exception {

        log.startStep( "Verify is the survey enable box checked" );
        if( driver.isChecked( elementXpath ) ) {} else {
            driver.click( elementXpath, driver.timeOut );
        }
        log.endStep();

        return ( T ) this;
    }

    @SuppressWarnings("unchecked")
    public T disableSurveys(
                             String elementXpath ) throws Exception {

        log.startStep( "Verify is the survey enable box checked" );
        if( driver.isChecked( elementXpath ) ) {
            driver.click( elementXpath, driver.timeOut );
        }
        log.endStep();
        return ( T ) this;
    }
}
