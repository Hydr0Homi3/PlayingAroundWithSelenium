import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class SignUpTest {

    @Test
    public void signUp() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");

        // opening the sign-up form
        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        // add firstName, lastName, phoneNumber, emailAddress to the sign-up form
        driver.findElement(By.name("firstname")).sendKeys("Jakub");
        driver.findElement(By.name("lastname")).sendKeys("Kubala");
        driver.findElement(By.name("phone")).sendKeys("668048049");
        driver.findElement(By.name("email")).sendKeys("jk432@vp.pl");


    }
}
