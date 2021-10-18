package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.concurrent.TimeUnit;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;


public class Steps {
    String thePrice;
    WebDriver driver;
    @Given("I add 4 different products to my wishlist")
    public void i_add_4_different_products_to_my_wishlist() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\JAVA\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://testscriptdemo.com/?post_type=product");
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=22']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=18']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=20']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=24']")).click();
        Thread.sleep(300);
    }

    @When("I view my wishlist table")
    public void i_view_my_wishlist() {
        driver.get("https://testscriptdemo.com/?page_id=233&wishlist-action");
    }

    @Then("I find total 4 selected items in my wishlist")
    public void i_find_total_4_selected_items_in_my_wishlist() {
        List<WebElement> wishlist = driver.findElements(By.className("product-name"));
        assertEquals(5, wishlist.size());
    }

    @When("I search for the lowest price product")
    public void i_search_for_lowest_price_product() throws InterruptedException {
        List<WebElement> price = driver.findElements(By.xpath("//span[@class='woocommerce-Price-amount amount']"));
        List<String> priceList = new ArrayList<>();
        for(WebElement e : price) {
            priceList.add(e.getText());
        }

        List<String> sortedPriceList = new ArrayList<>(priceList);

        Collections.sort(sortedPriceList);
        thePrice = sortedPriceList.get(0);
        assertEquals("£19.00", thePrice);

    }

    @And("I am able to add the lowest price item to my cart")
    public void i_am_able_to_add_the_lowest_price_item_to_my_cart() throws InterruptedException {
        String lowestPrice = thePrice.replace("£","");
        driver.findElement(By.xpath("//bdi[contains(text(), '" + lowestPrice + "')]//following::td[2]/a[1]")).click();
        Thread.sleep(300);
    }

    @Then("I am able to verify the item in my cart")
    public void i_am_able_to_verify_the_item_in_my_cart() throws InterruptedException {

        driver.get("https://testscriptdemo.com/?page_id=299");

        Thread.sleep(300);

        driver.navigate().refresh();

        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']"));
        assertEquals(1, rows.size());
        driver.close();
    }
}
