package Tests;

import Pages.HomePage;
import Pages.ProductPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SeleniumGridTest {

    public static WebDriver driver;

    // Declare ExtentReports and ExtentTest objects
    private static  ExtentSparkReporter spark;
    private static ExtentReports extent;
    public static  ExtentTest test;

    @BeforeTest
    public static void setUp() throws InterruptedException {


        // code to run locally with the chrome browser, line no. 37 to 57
//        try {
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments("start-maximized");
//            options.addArguments("--lang=en");
//            driver = new ChromeDriver(options);
//            String url = "https://www.douglas.de/de/";
//            driver.get(url);
//            Actions actions = new Actions(driver);
//            actions.contextClick().perform();
//            Robot robot = new Robot();
//            // Wait for 1 second before pressing keys
//            for (int i = 0; i < 4; i++) {
//                Thread.sleep(1000);
//                robot.keyPress(KeyEvent.VK_UP);   // Press 'up' key
//                robot.keyRelease(KeyEvent.VK_UP); // Release 'up' key
//            }
//            robot.keyPress(KeyEvent.VK_ENTER);
//            robot.keyRelease(KeyEvent.VK_ENTER);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // this code is for multi browser testing using Selenium Grid.

        // Create the Extent Reports and attach the Spark Reporter
        spark = new ExtentSparkReporter("Selenium Grid SparkReport.html");
        spark.config().setDocumentTitle("Applause Assessment Selenium Test Report");
        spark.config().setReportName("Applause Assessment Extent Spark Report");

        extent = new ExtentReports();
        extent.setSystemInfo("Automation Engineer", "Fakhre Alam");
        extent.attachReporter(spark);


        WebDriver driver = null;
        String[] browsers = {"chrome", "firefox", "edge"};

        for (String browser : browsers) {
            try {
                 test = extent.createTest(browser.toUpperCase() + " Test");
                // Hub URL
                String hubUrl = "http://localhost:4444/wd/hub";

                if (browser.equals("chrome")) {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("start-maximized", "--lang=en");
                    driver = new RemoteWebDriver(new URL(hubUrl), chromeOptions);
                } else if (browser.equals("firefox")) {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    firefoxOptions.addPreference("intl.accept_languages", "en");
                    driver = new RemoteWebDriver(new URL(hubUrl), firefoxOptions);
                } else if (browser.equals("edge")) {
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("start-maximized", "--lang=en");
                    driver = new RemoteWebDriver(new URL(hubUrl), edgeOptions);
                }

                runTest(driver);
                test.log(Status.INFO, "Browser opened successfully");
                test.log(Status.PASS, "Navigated to Douglas website");


            } catch (Exception e) {
                e.printStackTrace();
                extent.createTest(browser.toUpperCase() + " Test").fail(e);
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }
        }
        extent.flush();
   }

    public static void runTest(WebDriver driver) {
        try {
            String url = "https://www.douglas.de/de/";
            driver.get(url);

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
        String giftForDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'Gift for')]";
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, giftForDropdownXPath, firstCheckboxesXPath, 8);

        // For Whom dropdown
        String forWhomDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'For whom')]";
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, forWhomDropdownXPath, firstCheckboxesXPath, 3);

        //  Brand dropdown
        String brandDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'brand')]";
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, brandDropdownXPath, firstCheckboxesXPath, 8);

        // Product Type dropdown
        String productTpyeDropdownXPath = "//div[contains(@class, 'facet')]//div[@class='facet__title']//font[contains(text(), 'product type')]";
        ProductPage.selectAllCheckboxesFromDropdown(driver, wait, actions, js, productTpyeDropdownXPath, firstCheckboxesXPath, 8);

        driver.findElement(By.xpath(productTpyeDropdownXPath)).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        Thread.sleep(3000);
        ProductPage.GridProductItemDetail();

    }

    @AfterTest
     void closeBrowser()
    {
        driver.close();
        driver.quit();
        test.log(Status.INFO, "Browser closed");
    }


}
