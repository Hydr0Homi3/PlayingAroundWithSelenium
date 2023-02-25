import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class NoHotelNameSearchTest {

    @Test
    public void noHotelNameSearch() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");


        // add check in and check out dates, add 1 child, click search
        driver.findElement(By.name("checkin")).sendKeys("17/04/2023");
        driver.findElement(By.name("checkout")).sendKeys("20/04/2023");
        driver.findElement(By.id("travellersInput")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();

        // check if the No result found massage was displayed
        WebElement heading = driver.findElement(By.xpath("//h2[text()='No Results Found']"));
        Assert.assertEquals(heading.getText(), "No Results Found");
        Assert.assertTrue(heading.getText().contains("No Results"));
    }
}
