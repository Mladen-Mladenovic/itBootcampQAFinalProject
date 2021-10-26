package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageZavrsni {

	WebDriver driver;
	WebDriverWait wait;
	WebElement formsButton;

	public HomePageZavrsni(WebDriver driver, WebDriverWait wait) {
		super();
		this.driver = driver;
		this.wait = wait;
	}

	public WebElement getFormsButton() {
		return driver.findElement(By.xpath("//h5[text()='Forms']/../../.."));
	}

	public void clickFormsButton() {
		getFormsButton().click();
	}
	
	
}
