package concep.seleniumWD.core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDriverAssertion {

    private RemoteWebDriver driver;
    private int elementTimeOut;

    public SeleniumDriverAssertion( RemoteWebDriver driver, WaitValue waitValue ) {
        this.driver = driver;
        this.elementTimeOut=waitValue.getElementTimeOut();
    }

    public boolean isTextPresent(
                                  String xpathLocator,
                                  String text,
                                  int timeOut ) {

        try {
            if( this.driver.findElement( By.xpath( xpathLocator ) ).getText().contains( text ) );
            
            return true;

        } catch( Exception e ) {
            return false;
        }
    }
    
    public boolean isTextPresent(String xpathLocator, String text) {
    	
    	return isTextPresent(xpathLocator, text, elementTimeOut);
    }

    public boolean isCheckBoxChecked(
                                      String checkBoxName ) {

        boolean checked = ( boolean ) ( ( JavascriptExecutor ) this.driver ).executeScript( " function isChecked(searchedItem) { var surveyinnerId = document.getElementById('surveysListsInnerContainer'),labels = surveyinnerId.getElementsByTagName('label'),checked = [];for (var i = 0; i < labels.length; i++) { var input = labels[i].getElementsByTagName('input'), span = labels[i].getElementsByTagName('span'); input[0].checked && span[0].innerHTML == searchedItem ? checked.push(input[i]) : false; }return checked.length != 0;} return isChecked('"
                                                                                            + checkBoxName
                                                                                            + "');" );

        return checked;
    }

    public boolean isClickable(
                                String xpathlocator,
                                int timeOut ) {

        WebDriverWait wait = new WebDriverWait( this.driver, timeOut );

        try {
            wait.until( ExpectedConditions.elementToBeClickable( By.xpath( xpathlocator ) ) );
            return true;
        } catch( Exception e ) {
            return false;
        }

    }

    public boolean isChecked(
                              WebElement element ) {

        try {
            return element.isSelected();
        } catch( Exception e ) {
            return false;
        }
    }

    public boolean isSelected(
                               WebElement element,
                               String value ) {

        try {
            Select dropDown = new Select( element );
            String selectedValue = dropDown.getFirstSelectedOption().getText();
            if( selectedValue.equals( value ) ) {
                return true;
            }
        } catch( Exception e ) {
            return false;
        }
        return false;

    }

    public boolean isElementPresent(
                                     WebElement element,
                                     int timeOut ) {

        int seconds = timeOut / 1000;

        boolean isFound = false;
        for( int i = 0; i < seconds; i++ ) {

            try {
                if( element.isDisplayed() ) {
                    return isFound = true;
                } else {
                    try {
                        Thread.sleep( 1000 );
                    } catch( InterruptedException e ) {}
                }
            } catch( Exception e ) {
                try {
                    Thread.sleep( 1000 );
                } catch( InterruptedException e1 ) {}
            }
        }

        return isFound;
    }
    
    public boolean isElementPresent(WebElement element){
    	
        return this.isElementPresent(element, elementTimeOut);    	
    }

    private String getSelectValue(
                                   String css3 ) {

        String selectedValue = "";
        try {
            selectedValue = ( String ) ( ( JavascriptExecutor ) this.driver ).executeScript( "return $('"
                                                                                             + css3
                                                                                             + "').clone().children().remove().end().text()" );

        } catch( Exception e ) {

        }
        return selectedValue;
    }

    //for the hidden drop down menus
    public boolean isValueSelected(
                                    String css3,
                                    String value ) {

        String selectedValue = getSelectValue( css3 );
        if( selectedValue.equals( value ) ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isElementVisable(
                                     WebElement element,
                                     int timeOut ) {

        boolean found = true;
        if( element == null ) {
            return false;
        } else {
            try {

                WebDriverWait wait = new WebDriverWait( driver, timeOut );
                wait.until( ExpectedConditions.visibilityOf( element ) );
            } catch( Exception e ) {
                found = false;
            }
            return found;
        }
    }
    
    public boolean isElementVisible(WebElement element) {
    	
    	return isElementVisable(element, elementTimeOut);
    }
}
