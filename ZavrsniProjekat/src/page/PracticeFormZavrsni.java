package page;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PracticeFormZavrsni {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;

	// Input fields
	WebElement firstNameField;
	WebElement lastNamefield;
	WebElement maleRadioButton;
	WebElement femaleRadioButton;
	WebElement otherRadioButton;
	WebElement mobileNumbeField;
	WebElement emailField;
	WebElement dateOfBirthField;
	WebElement SubjectsField;
	WebElement sportsCheckbox;
	WebElement readingCheckbox;
	WebElement musicCheckbox;
	WebElement imgUploadbutton;
	WebElement currentAddressField;
	WebElement stateButton;
	WebElement cityButton;
	WebElement submitButton;

	// Output fields
	String imeIPrezimeOutput;
	String emailOutput;
	String genderOutput;
	String mobileOutput;
	String dateOfBirthOutput;
	String subjectsOutput;
	String hobbiesOutput;
	String pictureOutput;
	String addressOutput;
	String stateAndCity;

	public PracticeFormZavrsni(WebDriver driver, WebDriverWait wait, JavascriptExecutor js) {
		super();
		this.driver = driver;
		this.wait = wait;
		this.js = js;
	}

	public String getFirstAndLastNameOutput() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Student Name']")));
		return driver.findElement(By.xpath("//td[text()='Student Name']/../td[position()=2]")).getText();
	}

	public String getEmailOutput() {
		return driver.findElement(By.xpath("//td[text()='Student Email']/../td[position()=2]")).getText();
	}

	public String getGenderOutput() {
		return driver.findElement(By.xpath("//td[text()='Gender']/../td[position()=2]")).getText();
	}

	public String getMobileOutput() {
		return driver.findElement(By.xpath("//td[text()='Mobile']/../td[position()=2]")).getText();
	}

	public String getDateOfBirthOutput() {
		return driver.findElement(By.xpath("//td[text()='Date of Birth']/../td[position()=2]")).getText();

	}

	public String getSubjectsOutput() {
		return driver.findElement(By.xpath("//td[text()='Subjects']/../td[position()=2]")).getText();
	}

	public String getHobbiesOutput() {
		return driver.findElement(By.xpath("//td[text()='Hobbies']/../td[position()=2]")).getText();
	}

	public String getPictureOutput() {
		return driver.findElement(By.xpath("//td[text()='Picture']/../td[position()=2]")).getText();
	}

	public String getAddressOutput() {
		return driver.findElement(By.xpath("//td[text()='Address']/../td[position()=2]")).getText();
	}

	public String getStateAndCityOutput() {
		return driver.findElement(By.xpath("//td[text()='State and City']/../td[position()=2]")).getText();
	}

	public WebElement getFirstNameField() {
		return driver.findElement(By.cssSelector("input[placeholder='First Name']"));
	}

	public void enterFirstName(String firstName) {
		getFirstNameField().clear();
		getFirstNameField().sendKeys(firstName);
	}

	public void clearFirstName() {
		getFirstNameField().clear();
	}

	public WebElement getLastNamefield() {
		return driver.findElement(By.cssSelector("input[placeholder='Last Name']"));
	}

	public void enterLastName(String lastName) {
		getLastNamefield().clear();
		getLastNamefield().sendKeys(lastName);
	}

	public WebElement getMaleRadioButton() {
		return driver.findElement(By.xpath("//input[@id='gender-radio-1']/.."));
	}

	public WebElement getMaleRadioButtonSelection() {
		return driver.findElement(By.cssSelector("input[value='Male']"));
	}

	public void clickMaleRadioButton() {
		getMaleRadioButton().click();
	}

	public WebElement getFemaleRadioButton() {
		return driver.findElement(By.xpath("//input[@id='gender-radio-2']/.."));
	}

	public WebElement getFemaleRadioButtonSelection() {
		return driver.findElement(By.cssSelector("input[value='Female']"));
	}

	public void clickFemaleRadioButton() {
		getFemaleRadioButton().click();
	}

	public WebElement getOtherRadioButton() {
		return driver.findElement(By.xpath("//input[@id='gender-radio-3']/.."));
	}

	public WebElement getOtherRadioButtonSelection() {
		return driver.findElement(By.cssSelector("input[value='Other']"));
	}

	public void clickOtherRadioButton() {
		getOtherRadioButton().click();
	}

	public WebElement getMobileNumbeField() {
		return driver.findElement(By.cssSelector("input[placeholder='Mobile Number']"));
	}

	public void enterMobileNumber(String mobileNumber) {
		getMobileNumbeField().clear();
		getMobileNumbeField().sendKeys(mobileNumber);
	}

	public WebElement getEmailField() {
		return driver.findElement(By.cssSelector("input[placeholder='name@example.com']"));
	}

	public void enterEmail(String email) {
		getEmailField().clear();
		getEmailField().sendKeys(email);
	}

	public WebElement getDatePickerField() {
		return driver.findElement(By.id("dateOfBirthInput"));
	}

	// Returns date presented when date picker is left without interaction. By rule
	// it should be todays date.
	public String getSelectedDateOfBirth(String format) {
		String defaultDateOfBirth = driver.findElement(By.id("dateOfBirthInput")).getAttribute("value");
		// Returns date string extracted from web element in its original form.
		if (format.equalsIgnoreCase("unfotmated")) {
			return defaultDateOfBirth;
			// Takes date extracted from web element and formats it to match the output
			// format.
		} else if (format.equalsIgnoreCase("formated")) {

			return monthFormating(defaultDateOfBirth);
		} else {
			throw new IllegalArgumentException("Error: wrong argument for getDateOfBirthOutput");
		}

	}

	public void enterDateOfBirth(String dateOfBirth, String tool) {
		/*
		 * Takes data String and mode string. If mode string == "picker" date will be
		 * entered using date picker If mode string == "manual" date will be entered by
		 * sending keys directly to the date field.
		 */
		String day = dateOfBirth.split(" ")[0];
		String month = dateOfBirth.split(" ")[1];
		String year = dateOfBirth.split(" ")[2];

		if (tool.contains("picker")) {
			try {
				getDatePickerField().click();
				WebElement yearPicker = driver.findElement(By.className("react-datepicker__year-select"));
				yearPicker.click();
				WebElement selectYear = driver.findElement(
						By.cssSelector("select[class='react-datepicker__year-select'] option[value='" + year + "']"));
				selectYear.click();

				WebElement monthPicker = driver.findElement(By.className("react-datepicker__month-select"));
				monthPicker.click();
				WebElement selectMonth = driver.findElement(
						By.xpath("//select[@class='react-datepicker__month-select']/option[text()='" + month + "']"));
				selectMonth.click();

				WebElement selectDay = driver.findElement(
						By.cssSelector("div[class='react-datepicker__day react-datepicker__day--0" + day + "']"));
				selectDay.click();
			} catch (Exception NoSuchElementException) {
				System.out.println("Date not in the list of dates / not found");
			}
		} else if (tool.contains("manual")) {
			getDatePickerField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			getDatePickerField().sendKeys(dateOfBirth);
		}

	}

	public WebElement previousMonthArrow() {
		return driver.findElement(By.cssSelector("button[aria-label = 'Previous Month']"));
	}

	public void clickPreviousMonthArrow() {
		previousMonthArrow().click();
	}

	public WebElement nextMonthArrow() {
		return driver.findElement(By.cssSelector("button[aria-label = 'Next Month']"));
	}

	public void clickNextMonthArrow() {
		nextMonthArrow().click();
	}

	public WebElement getDatePickerHeader() {
		return driver.findElement(By.cssSelector(
				"div [class='react-datepicker__current-month react-datepicker__current-month--hasYearDropdown react-datepicker__current-month--hasMonthDropdown']"));
	}

	public String datePickerHeader(String option) {
		String[] splited = getDatePickerHeader().getText().split(" ");
		if (option.equals("month")) {
			return splited[0];
		} else if (option.equals("year")) {
			return splited[1];
		} else if (option.equals("full")) {
			return getDatePickerHeader().getText();
		} else {
			System.out.println("Invalid option - enter \"month\", \"year\" or \"full\". ");
			return "Invalid option - enter \"month\", \"year\" or \"full\". ";
		}
	}

	public void changeYearWithArrows(String direction, String untilYear) {
		String currentYear = getSelectedDateOfBirth("unformated");
		if (direction.equals("back")) {
			while (currentYear != untilYear) {
				clickPreviousMonthArrow();
			}
		} else if (direction.equals("forward")) {
			while (currentYear != untilYear) {
				clickNextMonthArrow();
			}
		}
	}

	public String getBorderColor(WebElement element) {
		return element.getCssValue("border-color");
	}

	public WebElement getSubjectsField() {
		return driver.findElement(By.id("subjectsContainer"));
	}

	public WebElement getSelectedSubjectsField() {
		return driver.findElement(By.id("subjectsInput"));
	}

	public WebElement getSubjectAutocomplete(String subjects) {
		return driver.findElement(By.xpath("//div[@tabindex='-1' and text()='" + subjects + "']"));
	}

	// TODO Create a list input option for multiple subject picks.
	public void enterSubjects(String subjects) throws InterruptedException {
		getSubjectsField().click();
		getSelectedSubjectsField().sendKeys(subjects);
		getSubjectAutocomplete(subjects).click();
	}

	public WebElement getSubjectEntered(String subject) {
		return driver.findElement(By.xpath("//div[text()='" + subject + "']"));
	}

	public WebElement getSportsCheckbox() {
		return driver.findElement(By.xpath("//input[@id='hobbies-checkbox-1']/.."));
	}

	public void clickSportsCheckbox() {
		getSportsCheckbox().click();
	}

	public WebElement getSelectedSportsCheckbox() {
		return driver.findElement(By.id("hobbies-checkbox-1"));
	}

	public WebElement getReadingCheckbox() {
		return driver.findElement(By.xpath("//input[@id='hobbies-checkbox-2']/.."));
	}

	public void clickReadingCheckbox() {
		getReadingCheckbox().click();
	}

	public WebElement getSelectedReadingCheckbox() {
		return driver.findElement(By.id("hobbies-checkbox-2"));
	}

	public WebElement getMusicCheckbox() {
		return driver.findElement(By.xpath("//input[@id='hobbies-checkbox-3']/.."));
	}

	public void clickMusicCheckbox() {
		getMusicCheckbox().click();
	}

	public WebElement getSelectedMusicCheckbox() {
		return driver.findElement(By.id("hobbies-checkbox-3"));
	}

	public WebElement getImgUploadbutton() {
		return driver.findElement(By.cssSelector("input#uploadPicture"));
	}

	public void addPictureForUpload(String imgAddress) {
		getImgUploadbutton().sendKeys(imgAddress);
	}

	public WebElement getCurrentAddressField() {
		return driver.findElement(By.cssSelector("textarea#currentAddress"));
	}

	public void enterCurrentAddress(String curAddress) {
		getCurrentAddressField().clear();
		getCurrentAddressField().sendKeys(curAddress);
	}

	public WebElement getStateButton() {
		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//div[text()='Select State']/.")));
		return driver.findElement(By.xpath("//div[text()='Select State']/."));
	}

	public WebElement getStateDropdownElement(String state) {
		return driver.findElement(By.xpath("//div[@id='react-select-3-option-0' and text()='" + state + "']"));
	}

	public void enterState(String state) {
		getStateButton().click();
		getStateDropdownElement(state).click();
	}

	public String getSelectedState() {
		return driver.findElement(By.cssSelector("div#state div[class=' css-1uccc91-singleValue']")).getText();
	}

	public WebElement getCityButton() {
		return driver.findElement(By.cssSelector("div[class=' css-1wa3eu0-placeholder']"));
	}

	public WebElement getCityDopdownElement(String city) {
		return driver.findElement(By.xpath("//div[@tabindex='-1' and text()='" + city + "']"));
	}

	public void enterCity(String city) {
		getCityButton().click();
		getCityDopdownElement(city).click();
	}

	public String getSelectedCity() {
		return driver.findElement(By.cssSelector("div#city div[class=' css-1uccc91-singleValue']")).getText();
	}

	public WebElement getSubmitButton() {
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//button[@id='submit']/..")));
		return driver.findElement(By.xpath("//button[@id='submit']/.."));
	}

	public void clickSubmitButton() {
		getSubmitButton().click();
	}

	public WebElement getCloseButton() {
		return driver.findElement(By.cssSelector("button#closeLargeModal"));
	}

	public WebElement getAdCloseButton() {
		return driver.findElement(By.id("close-fixedban"));
	}

	public void clickCloseButton() {
		wait.until(ExpectedConditions.elementToBeClickable(getCloseButton()));
		try {
			getAdCloseButton().click();
		} catch (Error e) {

		} finally {
			getCloseButton().click();
		}
	}

	public WebElement getInactiveBackground() {
		return driver.findElement(By.xpath("//body"));
	}

	public void clickOnInactiveBackground() {
		getInactiveBackground().click();
	}

	public WebElement outputDialog() {
		return driver.findElement(By.className("modal-dialog modal-lg"));
	}

	public boolean isOutputDialogDisplayed() {
		try {
			outputDialog().isDisplayed();
			return true;
		} catch (Exception NoSuchElementException) {
			return false;
		}
	}

	// Takes date string and changes the shortened month name to full.
	public String monthFormating(String date) {
		String formated = "";
		int num = 0;
		if (date.contains("Jan")) {
			formated = date.replace("Jan", "January");
		} else if (date.contains("Feb")) {
			formated = date.replace("Feb", "February");
		} else if (date.contains("Mar")) {
			formated = date.replace("Mar", "March");
		} else if (date.contains("Apr")) {
			formated = date.replace("Apr", "April");
		} else if (date.contains("May")) {
			formated = date;
		} else if (date.contains("Jun")) {
			formated = date.replace("Jun", "June");
		} else if (date.contains("Jul")) {
			formated = date.replace("Jul", "July");
		} else if (date.contains("Aug")) {
			formated = date.replace("Aug", "August");
		} else if (date.contains("Sep")) {
			formated = date.replace("Sep", "September");
		} else if (date.contains("Oct")) {
			formated = date.replace("Oct", "October");
		} else if (date.contains("Nov")) {
			formated = date.replace("Nov", "November");
		} else if (date.contains("Dec")) {
			formated = date.replace("Dec", "December");
		}
		return formated;

	}

}
