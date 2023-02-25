package kubala.seleniumdemo.tests;

import kubala.seleniumdemo.pages.HotelSearchPage;
import kubala.seleniumdemo.pages.SignUpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SignUpTest extends BaseTest {
    @Test
    public void signUpTest() {

        // add firstName, lastName, phoneNumber, unique/random emailAddress to the sign-up form
        String lastName = "Kijania";
        int randomNumber = (int) (Math.random()*1000);
        String email = "testPiotr" + randomNumber + "@testing.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Piotr");
        signUpPage.setLastName(lastName);
        signUpPage.setPhone("123456789");
        signUpPage.setEmail(email);
        signUpPage.setPassword("Haslo123");
        signUpPage.confirmPassword("Haslo123");
        signUpPage.signUp();

        WebElement heading = driver.findElement(By.xpath("//h3[@class='RTL']"));
        Assert.assertTrue(heading.getText().contains(lastName));
    }

    @Test
    public void signUpEmptyFormTest() {

        // opening the sign-up form and click on a sign up button
        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        // asserting if the correct error messages are displayed
        List<String> errors = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p")).stream()
                .map(WebElement::getText)
                .toList();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Email field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertTrue(errors.contains("The Last Name field is required."));
        softAssert.assertAll();
    }

    @Test
    public void invalidEmailSignUpTest() {

        // opening the sign-up form
        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        // add firstName, lastName, phoneNumber, unique/random emailAddress to the sign-up form
        String lastName = "Kubala";
        int randomNumber = (int) (Math.random()*1000);
        String email = "testJakub" + randomNumber;
        driver.findElement(By.name("firstname")).sendKeys("Jakub");
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys("111111111");
        driver.findElement(By.name("email")).sendKeys(email);

        // add password to the sign-up form, click sign up and check if we get to the signed user page
        driver.findElement(By.name("password")).sendKeys("Test123");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        // asserting if the correct error message is displayed
        List<String> errors = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p")).stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertTrue(errors.contains("The Email field must contain a valid email address."));
    }
}
