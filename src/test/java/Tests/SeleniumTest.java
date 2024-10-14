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
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class SeleniumTest {

    public static WebDriver driver;

    // Declare ExtentReports and ExtentTest objects
    private static  ExtentSparkReporter spark;
    private static ExtentReports extent;
    public static  ExtentTest test;

    @BeforeTest
    public static void setUp() throws InterruptedException {

        // Setup Extent Spark Reporter
        spark = new ExtentSparkReporter("extentReportSpark.html");
        spark.config().setDocumentTitle("Applause Assessment Selenium Test Report");
        spark.config().setReportName("Applause Assessment Extent Spark Report");

        // Initialize ExtentReports with Spark reporter
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Automation Engineer", "Fakhre Alam");
        extent.setSystemInfo("Browser", "Chrome");

        // Create a test in the report
        test = extent.createTest("Open Douglas Website");

        // This code is to run locally with chrome browser
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("--lang=en");
            driver = new ChromeDriver(options);
            runTest(driver);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runTest(WebDriver driver) {
        try {
            String url = "https://www.douglas.de/de/";
            driver.get(url);
            test.log(Status.INFO, "Browser opened successfully");
            test.log(Status.PASS, "Navigated to Douglas website");

            // Actions and Robot class interactions
            Actions actions = new Actions(driver);
            // Right-click on the page
            actions.contextClick().perform();

            Robot robot = new Robot();
            for (int i = 0; i < 4; i++) {
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_UP);
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void teststeps () throws InterruptedException, IOException {

        // Handle Cokkies
        HomePage.handle_cokkies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Click on Purfume Tab
        ProductPage.click_purfume_tab();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Criteria(Highligts) Dropdown
        String highlightsDropdownXpath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'highlights')]";
        String firstCheckboxesXPath = "//div[@class='facet__menu-content']//a";
        // This will select 3 checkbox
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, highlightsDropdownXpath, firstCheckboxesXPath, 3);

        // Gift For dropdown
        String giftForDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'Gift for')]";
        // This will select 8 checkbox
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, giftForDropdownXPath, firstCheckboxesXPath, 8);

        // For Whom dropdown
        String forWhomDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'For whom')]";
        // This will select 3 checkbox
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, forWhomDropdownXPath, firstCheckboxesXPath, 3);

        //  Brand dropdown
        String brandDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'brand')]";
        // This will select 8 checkbox
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, brandDropdownXPath, firstCheckboxesXPath, 8);

        // Product Type dropdown
        String productTpyeDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'product type')]";
        // This will select 8 checkbox
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, productTpyeDropdownXPath, firstCheckboxesXPath, 8);

        // to close dropdown after checkbox selection
        driver.findElement(By.xpath(productTpyeDropdownXPath)).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        Thread.sleep(3000);
        // call the method
        ProductPage.GridProductItemDetail();
    }

    @AfterTest
     void closeBrowser()
    {
        driver.close();
        driver.quit();
        test.log(Status.INFO, "Browser closed");
        extent.flush();
    }


}
