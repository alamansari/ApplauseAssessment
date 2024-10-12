package Tests;

import Pages.HomePage;
import Pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class SeleniumTest {

    public static WebDriver driver;

    @BeforeTest
    public static void setUp() throws InterruptedException {

        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("--lang=en");
            driver = new ChromeDriver(options);
            String url = "https://www.douglas.de/de/";
            driver.get(url);
            Actions actions = new Actions(driver);
            actions.contextClick().perform();
            Robot robot = new Robot();
            // Wait for 1 second before pressing keys
            for (int i = 0; i < 4; i++) {
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_UP);   // Press 'up' key
                robot.keyRelease(KeyEvent.VK_UP); // Release 'up' key
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);
    }

    @Test
    void teststeps () throws InterruptedException, IOException {
        HomePage.handle_cokkies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        ProductPage.click_purfume_tab();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Criteria(Highligts) Dropdown
        String highlightsDropdownXpath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'highlights')]";
        String firstCheckboxesXPath = "//div[@class='facet__menu-content']//a";
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, highlightsDropdownXpath, firstCheckboxesXPath, 3);

        // Gift For dropdown
        String giftForDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'Gift for')]";  // Replace with actual XPath for second dropdown
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, giftForDropdownXPath, firstCheckboxesXPath, 8);

        // For Whom dropdown
        String forWhomDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'For whom')]";  // Replace with actual XPath for second dropdown
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, forWhomDropdownXPath, firstCheckboxesXPath, 3);

        //  Brand dropdown
        String brandDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'brand')]";  // Replace with actual XPath for second dropdown
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, brandDropdownXPath, firstCheckboxesXPath, 8);

        // Product Type dropdown
        String productTpyeDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'product type')]";  // Replace with actual XPath
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, productTpyeDropdownXPath, firstCheckboxesXPath, 8);


        driver.findElement(By.xpath(productTpyeDropdownXPath)).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        Thread.sleep(3000);
        ProductPage.GridProductItemDetail();

    }

//    @AfterTest
//     void closeBrowser()
//    {
//        driver.close();
//        driver.quit();
//    }


}
