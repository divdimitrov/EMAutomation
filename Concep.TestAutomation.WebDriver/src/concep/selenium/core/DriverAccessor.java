package concep.selenium.core;

public class DriverAccessor {

    private static GenericSeleniumWebDriver driverAccessor;

    public static GenericSeleniumWebDriver getDriverInstance() {

        return ( driverAccessor == null )
                                         ? ( driverAccessor = new GenericSeleniumWebDriver() )
                                         : ( driverAccessor );
    }

}
