package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavBarZavrsni {

	WebDriver driver;
	WebDriverWait wait;
	WebElement practiceFormButton;

	public NavBarZavrsni(WebDriver driver, WebDriverWait wait) {
		super();
		this.driver = driver;
		this.wait = wait;
	}

	public WebElement getPracticeFormButton() {
		return driver.findElement(By.xpath("//span[text()='Practice Form']/../../.."));
	}
	
	public void clickPracticeFormButton() {
		getPracticeFormButton().click();
	}

}
