package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static Tests.SeleniumTest.driver;

public class ProductPage
{
    public static void  click_purfume_tab()
    {
        WebDriverWait wait;
        WebElement perfumeTab = driver.findElement(By.xpath("//font[contains(text(),'PERFUME')]"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15-second timeout
        perfumeTab = wait.until(ExpectedConditions.elementToBeClickable(perfumeTab));
        perfumeTab.click();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        }

        public static void selectAllCheckboxesFromDropdown(WebDriver driver,  WebDriverWait wait, Actions actions, JavascriptExecutor js,String dropdownXPath, String  checkboxesXPath, int numberOfCheckboxesToSelect) throws InterruptedException {
            // Find the dropdown toggle and click it to open the dropdown
            WebElement dropdownToggle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXPath)));
            actions.moveToElement(dropdownToggle).click().perform();
            System.out.println("Dropdown opened using Actions.");

            // Wait for the dropdown content (facet__menu-content) to be visible
            WebElement dropdownContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='facet__menu-content']")));
            System.out.println("Dropdown content loaded.");

            // Try finding the <a> tags associated with the checkboxes
            List<WebElement> aTags = driver.findElements(By.xpath(checkboxesXPath));
            System.out.println("Number of <a> tags found: " + aTags.size());

            // Iterate through <a> tags and click each checkbox or text aTags.size();
            for (int i = 0; i < Math.min(numberOfCheckboxesToSelect, aTags.size());  i++) {
                try {
                    aTags = driver.findElements(By.xpath(checkboxesXPath)); // Re-fetch the <a> tags
                    if (i < aTags.size()) {
                        WebElement aTag = aTags.get(i);
                        System.out.println("Attempting to select checkbox associated with <a> tag: " + aTag.getText());

                        // Scroll the <a> tag into view
                        js.executeScript("arguments[0].scrollIntoView(true);", aTag);

                        // Click the <a> tag or its checkbox
                        actions.moveToElement(aTag).click().perform();
                        System.out.println("Checkbox " + (i + 1) + " selected via <a> tag click.");

                        // Wait briefly after selecting a checkbox to handle page refresh/interaction
                        Thread.sleep(2000);

                        // Check if the dropdown has closed, reopen if necessary
                        boolean isDropdownClosed = driver.findElements(By.xpath("//div[@class='facet__menu-content']")).isEmpty();
                        if (isDropdownClosed) {
                            dropdownToggle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXPath)));
                            actions.moveToElement(dropdownToggle).click().perform();
                            System.out.println("Dropdown opened again.");
                        }
                    }

                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element encountered at checkbox " + (i + 1) + ". Retrying...");
                    i--;  // Retry the current checkbox
                } catch (Exception e) {
                    System.out.println("Error encountered at checkbox " + (i + 1) + ": " + e.getMessage());
                }
            }
        }

        public static void  GridProductItemDetail() throws InterruptedException, IOException {
        try {
            Thread.sleep(5000);
            System.out.println("to get info from grid items.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread.sleep(5);

        // List to store all item details
        List<Map<String, String>> allItemsDetails = new ArrayList<>();
        // Find all grid items (adjust the locator to fit your grid structure)
        List<WebElement> gridItems = driver.findElements(By.xpath("//div[contains(@class, 'product-grid-column ui-col-6 ui-col-lg-3 ui-col-xl-3')]"));
        // Prepare to write to CSV file
        FileWriter fileWriter = new FileWriter("gridItems.csv");
        // Write the CSV headers to match your table format
        fileWriter.append("Criteria (Highlights),Brand,Product Type,Gift For,For Whom\n");

        for (WebElement item : gridItems) {
            Map<String, String> itemDetails = new HashMap<>();

            // Check for 'New' criteria
            String productNewItem = "";
            try {
                productNewItem = item.findElement(By.xpath(".//div[@class='eyecatcher eyecatcher--rectangle eyecatcher--new ']")).getText();
            } catch (Exception e) {
                productNewItem = "N/A";
            }

            // Check for 'Sale' criteria
            String productSale = "";
            try {
                productSale = item.findElement(By.xpath(".//div[@class='eyecatcher eyecatcher--rectangle eyecatcher--discount ']")).getText();
            } catch (Exception e) {
                productSale = "N/A";
            }

            String productSponsored = "";
            try {
                productSponsored = item.findElement(By.xpath("//span[@class='sponsored-label sponsored-label--medium']//font[contains(text(),'Sponsored')]")).getText();
                System.out.println(productSponsored);
            } catch (Exception e) {
                productSponsored = "N/A";
            }

            String productBrand = "";
            try {
                productBrand = item.findElement(By.xpath(".//div[@class='text top-brand']")).getText();
            }
            catch (Exception e){
                productBrand = "N/A";
            }

            String productType = "";
              try {
                  productType = item.findElement(By.xpath(".//div[@class='text category']")).getText();
              }
              catch (Exception e){
                  productType = "N/A";
              }

            String productLimited = "";
            try {
                productLimited = "Limited";
            } catch (Exception e) {
                productNewItem = "N/A";
            }

            String giftFor = "";
            String forWhom = "";

            // Write each criteria to CSV separately

            // New Item
            if (!productNewItem.equals("N/A")) {
                // Append 'New' to the CSV
                itemDetails.put("New", productNewItem);
                fileWriter.append("New,");

                itemDetails.put("Product Brand", productBrand);
               // fileWriter.append(itemDetails.get("Product Brand")).append(",");
                fileWriter.append(productBrand.isEmpty() ? "?" : productBrand).append(",");

                itemDetails.put("Product Type", productType);
                fileWriter.append(itemDetails.get("Product Type")).append(",");

                itemDetails.put("Gift For",giftFor);
                fileWriter.append(giftFor.isEmpty() ? "-" : giftFor).append(",");
               // fileWriter.append(itemDetails.get("Gift For")).append(",");

                itemDetails.put("For Whom", forWhom);
                fileWriter.append(forWhom.isEmpty() ? "?" : forWhom).append("\n");
              //  fileWriter.append(itemDetails.get("For Whom")).append("\n");
            }
            // Sale Item
            if (!productSale.equals("N/A")) {
                // Append 'Sale' to the CSV
                itemDetails.put("Sale", productSale);
                fileWriter.append("Sale,");

                itemDetails.put("Product Brand", productBrand);
                fileWriter.append(itemDetails.get("Product Brand")).append(",");

                itemDetails.put("Product Type", productType);
                fileWriter.append(itemDetails.get("Product Type")).append(",");

                itemDetails.put("Gift For",giftFor);
                fileWriter.append(giftFor.isEmpty() ? "-" : giftFor).append(",");
               // fileWriter.append(itemDetails.get("Gift For")).append(",");

                itemDetails.put("For Whom", forWhom);
                fileWriter.append(forWhom.isEmpty() ? "?" : forWhom).append("\n");

            }

            // Limited
            if(productNewItem.equals("N/A") && productSale.equals("N/A") && !productLimited.equals("N/A")){
                itemDetails.put("Limited", productLimited);
                fileWriter.append("Limited,");

                itemDetails.put("Product Brand", productBrand);
                fileWriter.append(itemDetails.get("Product Brand")).append(",");

                itemDetails.put("Product Type", productType);
                fileWriter.append(itemDetails.get("Product Type")).append(",");

                itemDetails.put("Gift For", giftFor);
                fileWriter.append(giftFor.isEmpty() ? "-" : giftFor).append(",");
                // fileWriter.append(itemDetails.get("Gift For")).append(",");

                itemDetails.put("For Whom", forWhom);
                fileWriter.append(forWhom.isEmpty() ? "?" : forWhom).append("\n");
            }

            // Sponsored Item
            if (productNewItem.equals("N/A") && productSale.equals("N/A") && productLimited.equals("N/A") &&!productSponsored.equals("N/A")) {
                itemDetails.put("Sponsored", productSponsored);
                System.out.println(productSponsored);
                fileWriter.append("Sponsored,\n");
            }

            // Add item details to the list
            allItemsDetails.add(itemDetails);

        }
        // Close the file writer and the WebDriver
        fileWriter.flush();
        fileWriter.close();
        System.out.println("Grid item details saved to gridItems.csv");

    }


}
