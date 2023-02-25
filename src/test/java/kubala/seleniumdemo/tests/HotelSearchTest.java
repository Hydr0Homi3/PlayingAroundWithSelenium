package kubala.seleniumdemo.tests;

import kubala.seleniumdemo.pages.HotelSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        hotelSearchPage.setDates("27/04/2023","29/04/2023");
        hotelSearchPage.setTravellers();
        hotelSearchPage.performSearch();

        List<String> hotelNames = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b")).stream()
                .map(el -> el.getAttribute("textContent")).toList();

        Assert.assertEquals(hotelNames.get(0),"Jumeirah Beach Hotel");
        Assert.assertEquals(hotelNames.get(1),"Oasis Beach Tower");
        Assert.assertEquals(hotelNames.get(2),"Rose Rayhaan Rotana");
        Assert.assertEquals(hotelNames.get(3),"Hyatt Regency Perth");
    }

    @Test
    public void searchHotelWithNoNameTest() {

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
