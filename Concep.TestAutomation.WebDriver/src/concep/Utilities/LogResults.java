package concep.Utilities;

import org.testng.Reporter;

public class LogResults {

    private String stepDescription;
    private int    stepNumber;

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
                         boolean condition ) {

        String logLine;
        logLine = "<li>" + stepDescription;

        if( condition ) {
            // Format the status
            logLine = logLine + "...<b><font color='green'>OK</font></b>";
        } else {

            logLine = logLine + "...<b><font color='red'>FAILED!</font></b>";
        }

        logLine = logLine + "</li>";
        Reporter.log( logLine );
        // verifyTrue( condition );
    }

    public void endStep() {

        endStep( true );
    }
}
