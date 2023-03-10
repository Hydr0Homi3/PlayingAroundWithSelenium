package kubala.seleniumdemo.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import kubala.seleniumdemo.pages.HotelSearchPage;
import kubala.seleniumdemo.pages.ResultsPage;
import kubala.seleniumdemo.utils.ExcelReader;
import kubala.seleniumdemo.utils.SeleniumHelper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest() throws IOException {
        ExtentTest test = extentReports.createTest("Search Hotel Test");
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        test.log(Status.PASS,"Setting city done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setDates("27/04/2023", "29/04/2023");
        test.log(Status.PASS,"Setting dates done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setTravellers(1, 1);
        test.log(Status.PASS,"Setting travellers done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.performSearch().getHotelNames();
        test.log(Status.PASS,"Performing search done");
        test.log(Status.PASS,"Screenshot", SeleniumHelper.getScreenshot(driver));

        ResultsPage resultsPage = new ResultsPage(driver);
        List<String> hotelNames = resultsPage.getHotelNames();

        Assert.assertEquals(hotelNames.get(0), "Jumeirah Beach Hotel");
        Assert.assertEquals(hotelNames.get(1), "Oasis Beach Tower");
        Assert.assertEquals(hotelNames.get(2), "Rose Rayhaan Rotana");
        Assert.assertEquals(hotelNames.get(3), "Hyatt Regency Perth");
        test.log(Status.PASS,"Assertions passed", SeleniumHelper.getScreenshot(driver));
    }

    @Test
    public void searchHotelWithNoNameTest() throws IOException {
        ExtentTest test = extentReports.createTest("Search Hotel Test Without City");
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setDates("27/04/2023", "29/04/2023");
        test.log(Status.PASS,"Setting dates done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setTravellers(0, 1);
        test.log(Status.PASS,"Setting travellers done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.performSearch();
        test.log(Status.PASS,"Performing search done");
        test.log(Status.PASS,"Screenshot", SeleniumHelper.getScreenshot(driver));

        ResultsPage resultsPage = new ResultsPage(driver);

        Assert.assertTrue(resultsPage.resultHeading.isDisplayed());
        Assert.assertEquals(resultsPage.getHeadingText(), "No Results Found");
        test.log(Status.PASS,"Assertions passed", SeleniumHelper.getScreenshot(driver));
    }

    @Test(dataProvider = "data")
    public void searchHotelTestWithDataProvider(String city, String hotelName) throws IOException {
        ExtentTest test = extentReports.createTest("Search Hotel Test With Data Provider for " + city);
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity(city);
        test.log(Status.PASS,"Setting city done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setDates("27/04/2023", "29/04/2023");
        test.log(Status.PASS,"Setting dates done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.setTravellers(1, 1);
        test.log(Status.PASS,"Setting travellers done", SeleniumHelper.getScreenshot(driver));
        hotelSearchPage.performSearch();
        test.log(Status.PASS,"Performing search done");
        test.log(Status.PASS,"Screenshot", SeleniumHelper.getScreenshot(driver));

        ResultsPage resultsPage = new ResultsPage(driver);
        List<String> hotelNames = resultsPage.getHotelNames();

        Assert.assertEquals(hotelNames.get(0), hotelName);
        test.log(Status.PASS,"Assertions passed", SeleniumHelper.getScreenshot(driver));

    }

    @DataProvider
    public Object[][] data() throws IOException {
        return ExcelReader.readExcel("testData.xlsx");
    }
}
