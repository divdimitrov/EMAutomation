package concep.selenium.core;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.testng.Reporter;

public class LogResults {

    private final String screenshotPath = "C:\\Users\\Vladimir.Vasilev\\Desktop\\Screenshots\\"; // TODO: The right directory path have to be set
    private final String screenshotFormat = "png";

    private GenericSeleniumWebDriver driver;
    private String stepDescription;
    private int    stepNumber;        

    public LogResults() {		
	}
    
    public LogResults(GenericSeleniumWebDriver driver) {		
    	this.driver = driver;
	}

	//Login reports
    public void startTest(
                           String test ) {

        Reporter.log( "<h2>" + test + "</h2>" );
        Reporter.log( "<ol type='1'>" );

        stepNumber = 0;
    }

    public void resultStep(
                            String step ) {

        stepDescription = "<b><big><font color='DarkBlue'>" + step + "</font></b></big>";
    }

    public void endTest() {

        Reporter.log( "</ol>" );
        //  cleanUpIntermediateScreenshot();
        //  checkForVerificationErrors();

    }

    public void startStep(
                           String step ) {

        //  cleanUpIntermediateScreenshot();
        stepDescription = step;
        stepNumber++;
    }

    public void endStep(
                         boolean condition ) throws Exception {

        String logLine;
        logLine = "<li>" + stepDescription;

        if( condition ) {
            // Format the status
            logLine = logLine + "...<b><font color='green'>OK</font></b>";
        } else {

        	logLine = logLine + "...<b><font color='red'>FAILED!</font></b>";
        	
        	//TODO: Take screenshot mechanism have to be fixed after the lates changes
        	/*String screenshotName = driver.getTimestamp();
        	takeScreenshot(screenshotPath, screenshotName, screenshotFormat);
            logLine = logLine + "...<b><font color='red'>FAILED!</font></b>"
            		+ "<b><a href=" + screenshotPath + screenshotName + "." + screenshotFormat + 
            		" target='_blank' class='see-screenshot-tip'> - See screenshot</a></b>";*/
        }

        logLine = logLine + "</li>";
        Reporter.log( logLine );
        // verifyTrue( condition );
    }

    public void endStep() throws Exception {

        endStep( true );
    }
    
    public void takeScreenshot(String path, String name, String format) throws Exception {
		    	
		Robot robot = new Robot();
		
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage screenFullImage = robot.createScreenCapture(screen);
		ImageIO.write(screenFullImage, screenshotFormat, new File(path + name + "." + format ));
	}
}
