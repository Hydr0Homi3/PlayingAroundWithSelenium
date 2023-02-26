package kubala.seleniumdemo.tests;

import kubala.seleniumdemo.pages.HotelSearchPage;
import kubala.seleniumdemo.pages.LoggedUserPage;
import kubala.seleniumdemo.pages.SignUpPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SignUpTest extends BaseTest {
    @Test
    public void signUpTest() {

        String lastName = "Kijania";
        int randomNumber = (int) (Math.random()*1000);

        LoggedUserPage loggedUserPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstName("Piotr")
                .setLastName(lastName)
                .setPhone("123456789")
                .setEmail("testPiotr" + randomNumber + "@testing.pl")
                .setPassword("Haslo123")
                .confirmPassword("Haslo123")
                .signUp();

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(),"Hi, Piotr Kijania");
    }



    @Test
    public void signUpEmptyFormTest() {

        SignUpPage signUpPage = new HotelSearchPage(driver).openSignUpForm();
        signUpPage.signUp();
        List<String> errors = signUpPage.getErrors();

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

        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstName("Piotr")
                .setLastName("Kijania")
                .setPhone("123456789")
                .setEmail("email")
                .setPassword("Haslo123")
                .confirmPassword("Haslo123");
        signUpPage.signUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }
}
