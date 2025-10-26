package Automationtestingproject;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EcommerceTest {
    WebDriver driver;

    @BeforeTest
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        Thread.sleep(2000); // small wait for the page to load
    }

    @Test(priority = 1)
    public void loginTest() throws InterruptedException {
        // Enter username and password
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        Thread.sleep(2000);
        
        // Click on login button
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(3000);

        // Validate successful login
        String title = driver.findElement(By.className("title")).getText();
        AssertJUnit.assertEquals(title, "Products");
        System.out.println("✅ Login Test Passed Successfully");
    }

    @Test(priority = 2)
    public void addToCartTest() throws InterruptedException {
        // Add a product to the cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(2000);

        // Check if cart badge shows 1 item
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        AssertJUnit.assertEquals(cartBadge.getText(), "1");
        System.out.println("✅ Add To Cart Test Passed");
    }

    @Test(priority = 3)
    public void verifyProductDetails() throws InterruptedException {
        // Go to the product detail page
        driver.findElement(By.className("inventory_item_name")).click();
        Thread.sleep(3000);

        // Verify product name and price
        String productName = driver.findElement(By.className("inventory_details_name")).getText();
        String productPrice = driver.findElement(By.className("inventory_details_price")).getText();

        System.out.println("🛒 Product Selected: " + productName);
        System.out.println("💲 Product Price: " + productPrice);

        // Basic validations
        AssertJUnit.assertTrue(productName.contains("Sauce Labs"));
        AssertJUnit.assertTrue(productPrice.startsWith("$"));
        System.out.println("✅ Product Details Verified");
        
        // Navigate back to product list
        driver.findElement(By.id("back-to-products")).click();
        Thread.sleep(2000);
    }

    @Test(priority = 4)
    public void logoutTest() throws InterruptedException {
        // Click on the menu (burger icon)
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(2000);

        // Click on logout link
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(3000);

        // Verify user is back to login page
        boolean loginDisplayed = driver.findElement(By.id("login-button")).isDisplayed();
        AssertJUnit.assertTrue(loginDisplayed);
        System.out.println("✅ Logout Test Passed Successfully");
    }

    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
        System.out.println("🔚 Browser Closed Successfully");
    }
}
