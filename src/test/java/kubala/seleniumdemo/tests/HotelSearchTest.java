package kubala.seleniumdemo.tests;

import kubala.seleniumdemo.pages.HotelSearchPage;
import kubala.seleniumdemo.pages.ResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        List<String> hotelNames = hotelSearchPage
                .setCity("Dubai")
                .setDates("27/04/2023", "29/04/2023")
                .setTravellers(1, 1)
                .performSearch().getHotelNames();

        Assert.assertEquals(hotelNames.get(0), "Jumeirah Beach Hotel");
        Assert.assertEquals(hotelNames.get(1), "Oasis Beach Tower");
        Assert.assertEquals(hotelNames.get(2), "Rose Rayhaan Rotana");
        Assert.assertEquals(hotelNames.get(3), "Hyatt Regency Perth");

    }

    @Test
    public void searchHotelWithNoNameTest() {

        ResultsPage resultsPage = new HotelSearchPage(driver)
                .setDates("27/04/2023", "29/04/2023")
                .setTravellers(0, 1)
                .performSearch();

        Assert.assertTrue(resultsPage.resultHeading.isDisplayed());
        Assert.assertEquals(resultsPage.getHeadingText(), "No Results Found");
    }
}
