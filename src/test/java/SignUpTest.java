import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpTest extends BaseTest{
    @Test
    public void signUp() {

        // opening the sign-up form
        driver.findElements(By.xpath("//li[@id='li_myaccount']")).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        // add firstName, lastName, phoneNumber, unique/random emailAddress to the sign-up form
        String lastName = "Kubala";
        int randomNumber = (int) (Math.random()*1000);
        String email = "testJakub" + randomNumber + "@testing.pl";
        driver.findElement(By.name("firstname")).sendKeys("Jakub");
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys("111111111");
        driver.findElement(By.name("email")).sendKeys(email);

        // add password to the sign-up form, click sign up and check if we get to the signed user page
        driver.findElement(By.name("password")).sendKeys("Test123");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();
        WebElement heading = driver.findElement(By.xpath("//h3[@class='RTL']"));
        Assert.assertTrue(heading.getText().contains(lastName));

    }
}
