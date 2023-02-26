package kubala.seleniumdemo.tests;

import kubala.seleniumdemo.model.User;
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

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(),"Hi, Piotr Kijania");
    }

    @Test
    public void signUpTestAfterRefactor() {

        String lastName = "Kijania";
        int randomNumber = (int) (Math.random()*1000);
        String email = "testPiotr" + randomNumber + "@testing.pl";

        User user = new User();
        user.setFirstName("Piotr");
        user.setLastName("Kijania");
        user.setPhone("123456789");
        user.setEmail(email);
        user.setPassword("Haslo123");

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        //signUpPage.fillSignUpForm("Piotr", lastName,"123456789", email, "Haslo123");
        signUpPage.fillSignUpFormWithUser(user);

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(user.getLastName()));
        Assert.assertEquals(loggedUserPage.getHeadingText(),"Hi, Piotr Kijania");
    }

    @Test
    public void signUpEmptyFormTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();
        SignUpPage signUpPage = new SignUpPage(driver);
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

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Piotr");
        signUpPage.setLastName("Kijania");
        signUpPage.setPhone("123456789");
        signUpPage.setEmail("email");
        signUpPage.setPassword("Haslo123");
        signUpPage.confirmPassword("Haslo123");
        signUpPage.signUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }
}
