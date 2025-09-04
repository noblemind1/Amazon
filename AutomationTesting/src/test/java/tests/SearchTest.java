package tests;

import base.DriverSetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.SearchPage;

public class SearchTest {

    WebDriver driver;
    SearchPage searchPage;
    WebDriverWait wait;
    Actions actions;

    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) {
        searchPage = new SearchPage(DriverSetup.getDriver(browser));
    }

    @Test(groups = "search", dependsOnGroups = "login", priority = 1)
    public void testSearchProduct() {
        searchPage.searchProduct("laptop");
        Assert.assertTrue(searchPage.verifyResultsDisplayed(), "Search results not displayed!");
    }
    
    @Test(groups = "search", dependsOnGroups = "login", priority = 2)
    public void testApplyFilters() throws InterruptedException {
        // Apply brand filter
        searchPage.selectBrand("HP");
        Thread.sleep(3000);
        // Apply price range
        searchPage.applyPriceRange();
        
        // Apply customer ratings.
        searchPage.applyCustomerRatings();

        // Verify results displayed after applying filters
        Assert.assertTrue(searchPage.verifyResultsDisplayed(), "Filtered results not displayed!");
    }

    @Test(groups = "search", dependsOnGroups = "login", priority = 3)
    public void testSortLowToHigh() {
        searchPage.sortLowToHigh();
        Assert.assertTrue(searchPage.verifyResultsDisplayed(), "Results not visible after Low-High sort!");
    }
    
    
    @Test(groups = "search", dependsOnGroups = "login", priority = 4)
    public void testBlankSearch() {
        String title = searchPage.searchBlank();
        Assert.assertTrue(title.contains("Amazon"), "Blank search did not stay on amazon");
    }

    @Test(groups = "search", dependsOnGroups = "login", priority = 5)
    public void testViewProductDetails() {
       searchPage.searchProduct("laptop");
       searchPage.clickFirstProduct();
       
       DriverSetup.switchTab();

       String title = searchPage.getSelectedProductTitle();
       String price = searchPage.getSelectedProductPrice();

       System.out.println("Product Title: " + title);
       System.out.println("Product Price: " + price);

       Assert.assertFalse(title.isEmpty(), "Product title is not displayed!");
   }
}