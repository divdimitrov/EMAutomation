package concep.platform.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import concep.platform.Systems.SystemsPage;
import concep.seleniumWD.core.SeleniumDriver;
import concep.seleniumWD.core.SeleniumDriverCore;

public class PlatformBaseTests {

    protected SeleniumDriverCore driver;
    public SystemsPage           system;
    protected String             platformAdminUrl;
    public Properties            config;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "config" })
    public void runSelenium(
                             @Optional("Config/platformAdmin") String appConfig ) throws FileNotFoundException,
                                                                                  IOException {

        this.config = new Properties();
        this.config.load( new FileInputStream( appConfig ) );
        this.driver = SeleniumDriver.getDriverInstance();
        this.system = new SystemsPage( driver );
        this.platformAdminUrl = this.config.getProperty( "platformAdminUrl" );

    }

    @AfterMethod(alwaysRun = true)
    public void testCleanUp() {

        driver.quit();
    }
}
