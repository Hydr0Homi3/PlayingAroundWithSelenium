import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class InvalidEmailSignUpTest {
    // sign up with incorrect email format to evaluate if the correct error is displayed
    @Test
    public void invalidEmailSignUp() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");

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