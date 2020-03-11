package concep.seleniumWD.core;

public class SeleniumDriver {

    private static SeleniumDriverCore driverAccessor;

    public static SeleniumDriverCore getDriverInstance() {
    	if(driverAccessor == null){
    		return driverAccessor = new SeleniumDriverCore();
    	}
    	else if(driverAccessor.driver.getSessionId()==null){
    		return driverAccessor = new SeleniumDriverCore();
    	}else{
    		return driverAccessor;
    	}
    }
}
