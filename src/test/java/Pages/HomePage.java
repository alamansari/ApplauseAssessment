package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static Tests.SeleniumTest.driver;

public class HomePage {

    public static void  handle_cokkies(){
        WebElement shadowHost = driver.findElement(By.id("usercentrics-root"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        WebElement shadowWebElement = shadowRoot.findElement(By.cssSelector(".sc-dcJsrY.eIFzaz"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        shadowWebElement = wait.until(ExpectedConditions.elementToBeClickable(shadowWebElement));
        shadowWebElement.click();
    }
}
