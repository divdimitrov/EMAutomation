package concep.selenium.performance;

import concep.selenium.core.DriverAccessor;
import concep.selenium.core.GenericSeleniumWebDriver;
import concep.selenium.core.LogResults;
import concep.selenium.mailinator.Mailinator;
import concep.selenium.send.Elements;
import concep.selenium.send.Send;

public class BasePerformance {

    protected Send                            send       = new Send();
    protected Elements                        element    = new Elements();
    protected static GenericSeleniumWebDriver driver     = DriverAccessor.getDriverInstance();
    protected LogResults                      log        = new LogResults();
    protected Mailinator                      mailinator = new Mailinator();

    protected String                          sendUserName;
    protected String                          sendPassword;

    public Send loginToSend() throws Exception {

        return send.loginToSend( sendUserName, sendPassword );
    }

}
