package base;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import page.HomePageZavrsni;
import page.NavBarZavrsni;
import page.PracticeFormZavrsni;

public class BaseZavrsni {

	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	public ExcelReader xcelReader;
	public HomePageZavrsni homePageZavrsni;
	public NavBarZavrsni navBarZavrsni;
	public PracticeFormZavrsni practiceFormZavrsni;

	@BeforeClass
	public void setup() throws IOException {
		System.setProperty("webdriver.chrome.driver", "driver-lib/chromedriver");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 10);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		xcelReader = new ExcelReader("/Users/mladenimac/itbootcamp/zavrsniProjekat/TestData.xlsx");

		homePageZavrsni = new HomePageZavrsni(driver, wait);
		navBarZavrsni = new NavBarZavrsni(driver, wait);
		practiceFormZavrsni = new PracticeFormZavrsni(driver, wait, js);
	}

	@AfterClass
	public void tearDown() {
		driver.manage().deleteAllCookies();
		driver.close();
	}
}
