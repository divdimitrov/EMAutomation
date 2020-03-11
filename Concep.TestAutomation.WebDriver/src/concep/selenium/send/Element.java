package concep.selenium.send;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Element {

	public String elementString;
	private EventFiringWebDriver driver;

	public Element(String elementString, EventFiringWebDriver driver) {
		this.driver = driver;
		this.elementString = elementString;
	}

	public WebElement webElement() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementString)));
			 return this.driver.findElement( By.xpath( elementString ) );
		} catch (Exception e) {
			throw e;
		}
	}
}