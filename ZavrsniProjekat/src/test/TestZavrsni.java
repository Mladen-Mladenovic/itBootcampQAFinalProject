package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.security.Key;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseZavrsni;

public class TestZavrsni extends BaseZavrsni {

	@BeforeMethod
	public void pageSetup() {
		String url = xcelReader.getStringData("Data", 1, 0);
		String formsUrl = xcelReader.getStringData("Data", 2, 0);
		String practiveFormsUrl = xcelReader.getStringData("Data", 3, 0);
		driver.get(url);
		driver.manage().window().maximize();

		homePageZavrsni.clickFormsButton();
		assertEquals(driver.getCurrentUrl(), formsUrl); // Check if driver navigated to forms url.
		navBarZavrsni.clickPracticeFormButton();
		assertEquals(driver.getCurrentUrl(), practiveFormsUrl); // Check if driver navigated to pracitce form url.
	}

	@Test(enabled = false)
	public void happyPathRequiredFieldsOnly() throws InterruptedException {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),// Check if default date corresponds with output date. Original default date has to be formated.
				practiceFormZavrsni.getSelectedDateOfBirth("formated"));
		practiceFormZavrsni.clickCloseButton();
		
		assertEquals(driver.getCurrentUrl(), formUrl);// Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void happyPathAllFields() throws InterruptedException {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 1, 4);
		String dateOfBirth = xcelReader.getStringData("Data", 1, 6);
		String subject = xcelReader.getStringData("Data", 1, 7);
		String imgAddress = xcelReader.getStringData("Data", 1, 9);
		File imgFile = new File(imgAddress);
		String curAddress = xcelReader.getStringData("Data", 1, 10);
		String state = xcelReader.getStringData("Data", 1, 11);
		String city = xcelReader.getStringData("Data", 1, 12);
		String hobbies = xcelReader.getStringData("Data", 1, 8);

		for (int i = 2; i < 4; i++) {

			hobbies += ", " + xcelReader.getStringData("Data", i, 8);
		}

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected
		
		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		
		practiceFormZavrsni.enterDateOfBirth(dateOfBirth, "picker");
		assertEquals(practiceFormZavrsni.getSelectedDateOfBirth("formated"), dateOfBirth); // Check if displayed date corresponds with picked date.
		
		practiceFormZavrsni.enterSubjects(subject);
		assertTrue(practiceFormZavrsni.getSubjectEntered(subject).isDisplayed());
		
		practiceFormZavrsni.clickSportsCheckbox();
		assertTrue(practiceFormZavrsni.getSelectedSportsCheckbox().isSelected()); // Check if Sports checkbox is selected.
		
		practiceFormZavrsni.clickReadingCheckbox();
		assertTrue(practiceFormZavrsni.getSelectedReadingCheckbox().isSelected()); // Check if Reading checkbox is selected.
		
		practiceFormZavrsni.clickMusicCheckbox();
		assertTrue(practiceFormZavrsni.getSelectedMusicCheckbox().isSelected()); // Check if Music checkbox is selected.

		practiceFormZavrsni.addPictureForUpload(imgAddress);
		practiceFormZavrsni.enterCurrentAddress(curAddress);
		practiceFormZavrsni.enterState(state);
		assertEquals(practiceFormZavrsni.getSelectedState(), state); // Check if state that is clicked on is the one that gets selected.

		practiceFormZavrsni.enterCity(city);
		assertEquals(practiceFormZavrsni.getSelectedCity(), city); // Check if state that is clicked on is the one that gets selected.

		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getEmailOutput(), email); // Check if Student Email corresponds with email entered.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male");// Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "), 
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.
		assertEquals(practiceFormZavrsni.getHobbiesOutput(), hobbies); // Check if Hobbies output matches hobbies input.
		assertEquals(practiceFormZavrsni.getPictureOutput(), imgFile.getName()); // Check if output picture name is the same as name of the input picture.
		assertEquals(practiceFormZavrsni.getAddressOutput(), curAddress); // Check if Address output is matching current address input.
		assertEquals(practiceFormZavrsni.getStateAndCityOutput(), state + " " + city); // Check if State and City output matches state inout and city input separated by space.
		practiceFormZavrsni.clickCloseButton();
		
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void SignUpWithoutEntry() {
		practiceFormZavrsni.clickSubmitButton();
		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed()); // Check if output dialog is NOT displayed
	}

	@Test(enabled = false)
	public void signUpWithLowercaseFirstName() {
		String firstName = xcelReader.getStringData("Data", 2, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithLowercaseLastName() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 2, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithSpaceAfterFirstName() {
		String firstName = xcelReader.getStringData("Data", 3, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(),
				firstName.replaceFirst(" $", "") + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithSpaceAfterLastName() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 3, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(),
				firstName + " " + lastName.replaceFirst(" $", "")); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.
		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithFirstNameWithDiacritic() {
		String firstName = xcelReader.getStringData("Data", 4, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.
		
		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithLastNameWithDiacritic() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 4, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithTwoFirstNames() {
		String firstName = xcelReader.getStringData("Data", 7, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithTwoLastNames() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 7, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl);
	}

	

	@Test(enabled = false)
	public void signUpWithFirstNameWithNumber() {
		String firstName = xcelReader.getStringData("Data", 5, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}

	@Test(enabled = false)
	public void signUpWithLastNameWithNumber() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 5, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}

	@Test(enabled = false)
	public void signUpWithFirstNameWithSpecial() {
		String firstName = xcelReader.getStringData("Data", 6, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected
		
		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}

	@Test(enabled = false)
	public void signUpWithLastNameWithSpecial() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 6, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithFemaleGender() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickFemaleRadioButton();
		assertTrue(practiceFormZavrsni.getFemaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Female"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}
	
	@Test(enabled = false)
	public void signUpWithOtherGender() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickOtherRadioButton();
		
		assertTrue(practiceFormZavrsni.getOtherRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Other"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}
	
	@Test(enabled = false)
	public void signUpWithEmailEndingWithSpace() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 2, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}
	
	@Test(enabled = false)
	public void signUpWithEmailWithoutMonkey() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 3, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithEmailWithoutDotCom() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 4, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithEmailWithNumInDomainName() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 5, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}

	@Test(enabled = false)
	public void signUpWithEmailWithNumInDotCom() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 6, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithSpecialCharInDomainName() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 7, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithSpecialCharInDotCom() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 8, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithSpaceInDomainName() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 9, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithSpaceInDotCom() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 10, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithEmailWithCapitalLetterinLocal() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 11, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}
	
	@Test(enabled = false)
	public void signUpWithEmailWithNumberInLocal() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 12, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		
		assertEquals(practiceFormZavrsni.getFirstAndLastNameOutput(), firstName + " " + lastName); // Check if first and last name input match the full name output.
		assertEquals(practiceFormZavrsni.getGenderOutput(), "Male"); // Check if radio button selection corresponds with output.
		assertEquals(practiceFormZavrsni.getMobileOutput(), mobileNumber); // Check if mobile number input matches the mobile number output.
		assertEquals(practiceFormZavrsni.getDateOfBirthOutput().replace(",", " "),
				practiceFormZavrsni.getSelectedDateOfBirth("formated")); // Check if default date corresponds with output date. Original default date has to be formated.

		practiceFormZavrsni.clickCloseButton();
		assertEquals(driver.getCurrentUrl(), formUrl); // Check if page url after output popup is closed is the same as before output popup appeared.
	}
	
	@Test(enabled = false)
	public void signUpWithEmailWithSpecialCharInLocal() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 13, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void signUpWithEmailWIthSpaceInLocal() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String email = xcelReader.getStringData("Data", 14, 4);

		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.enterEmail(email);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		practiceFormZavrsni.clickSubmitButton();

		String formUrl = driver.getCurrentUrl();

		assertTrue(!practiceFormZavrsni.isOutputDialogDisplayed());
		assertEquals(driver.getCurrentUrl(), formUrl);
	}
	
	@Test(enabled = false)
	public void enterFutureDate () {
		
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String dateOfBirth = xcelReader.getStringData("Data", 2, 6);
		
		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		String borderColor = practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField()); // Check field border color before entering values.
		practiceFormZavrsni.enterDateOfBirth(dateOfBirth, "picker");
		practiceFormZavrsni.getInactiveBackground().click();
		assertEquals(practiceFormZavrsni.getSelectedDateOfBirth("formated"), dateOfBirth); // Check if displayed date corresponds with entered date.
		assertEquals(borderColor, practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField())); // Check field border color after entering values.
	}
	
	@Test(enabled = false)
	public void enterDateBeforeLastListed() {
		// TODO: fix to check for last listed date 
		
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String dateOfBirth = xcelReader.getStringData("Data", 3, 6);
		
		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		String borderColor = practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField()); // Check field border color before entering values.
		practiceFormZavrsni.enterDateOfBirth(dateOfBirth, "manual");
		practiceFormZavrsni.getDatePickerField().sendKeys(Keys.ENTER);
		assertEquals(practiceFormZavrsni.getSelectedDateOfBirth("formated"), dateOfBirth); // Check if displayed date corresponds with entered date.
		assertEquals(borderColor, practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField())); // Check field border color after entering values.
	}
	
	@Test(enabled = false)
	public void enterInvalidMonth() {  
		
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String dateOfBirth = xcelReader.getStringData("Data", 6, 6);
		
		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		String borderColor = practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField()); // Check field border color before entering values.
		practiceFormZavrsni.enterDateOfBirth(dateOfBirth, "manual");
		practiceFormZavrsni.getDatePickerField().sendKeys(Keys.ENTER);
		assertEquals(practiceFormZavrsni.getSelectedDateOfBirth("formated"), dateOfBirth); // Check if displayed date corresponds with entered date.
		assertEquals(borderColor, practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField())); // Check field border color after entering values.
	}
	
	@Test(enabled = false)
	public void enterInvaldDay() {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		String dateOfBirth = xcelReader.getStringData("Data", 4, 6);
		
		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		String borderColor = practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField()); // Check field border color before entering values.
		practiceFormZavrsni.enterDateOfBirth(dateOfBirth, "manual");
		practiceFormZavrsni.getDatePickerField().sendKeys(Keys.ENTER);
		assertEquals(practiceFormZavrsni.getSelectedDateOfBirth("formated"), dateOfBirth); // Check if displayed date corresponds with entered date.
		assertEquals(borderColor, practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField())); // Check field border color after entering values.
	}
	
	@Test(enabled = true)
	public void pickDateWithPickerArrows () {
		String firstName = xcelReader.getStringData("Data", 1, 1);
		String lastName = xcelReader.getStringData("Data", 1, 2);
		String mobileNumber = String.valueOf(xcelReader.getIntData("Data", 1, 5));
		
		practiceFormZavrsni.enterFirstName(firstName);
		practiceFormZavrsni.enterLastName(lastName);
		practiceFormZavrsni.clickMaleRadioButton();
		assertTrue(practiceFormZavrsni.getMaleRadioButtonSelection().isSelected()); // Check if Male radio button is selected

		practiceFormZavrsni.enterMobileNumber(mobileNumber);
		String borderColor = practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField()); // Check field border color before entering values.
		String defaultDate = practiceFormZavrsni.getSelectedDateOfBirth("unformated");
		practiceFormZavrsni.changeYearWithArrows("forward", "2020");
		assertEquals(defaultDate, practiceFormZavrsni.getSelectedDateOfBirth("unformated"));
		
		
		assertEquals(borderColor, practiceFormZavrsni.getBorderColor(practiceFormZavrsni.getDatePickerField()));  // Check field border color after entering values.
	}
}
