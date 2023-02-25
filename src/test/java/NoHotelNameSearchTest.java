import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NoHotelNameSearchTest extends BaseTest{

    @Test
    public void noHotelNameSearch() {

        // add check in and check out dates, add 1 child, click search
        driver.findElement(By.name("checkin")).sendKeys("17/04/2023");
        driver.findElement(By.name("checkout")).sendKeys("20/04/2023");
        driver.findElement(By.id("travellersInput")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();

        // check if the No result found massage was displayed
        WebElement noResultHeading = driver.findElement(By.xpath("//div[@class='itemscontainer']//h2"));
        Assert.assertTrue(noResultHeading.isDisplayed());
        Assert.assertEquals(noResultHeading.getText(), "No Results Found");

    }
}
