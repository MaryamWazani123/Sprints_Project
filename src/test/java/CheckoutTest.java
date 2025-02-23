import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class CheckoutTest {
    WebDriver driver;
    CheckoutPage checkoutPage;
    // Recommended to put as data provider or read it from file but it has been done in a hurry.
    String discountCode = "20poff";

    // Before Annotations
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - Runs before all tests in the suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - Runs before any test method in this test");
    }

    @BeforeClass
    public void setUp() {
        System.out.println("Before Class - Runs once before any method in this class");
        //driver = new EdgeDriver();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.open();
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - Runs before each test method");
    }

    // Tests Come Here
    @Test
    public void testIsGuestUser() {
        if (checkoutPage.isGuestUser()) {
            System.out.println("User is not logged in: Guest user.");
            Assert.assertTrue(true);
        }
    }

    // Data Provider for Name and Shipping and Billing Addresses for Guest User.
    @DataProvider(name = "nameAddressData")
    public Object[][] nameAddressProvider() {
        return new Object[][] {
                {"Hanan", "Azzam", "123", "Street1", "stateProvince", "Togo", "0097156970577577"},
        };
    }

    // Here tests For Logged In User
    @Test
    public void testLogin(){
        checkoutPage.login();
    }

    @Test
    public void testAddToCart(){
        checkoutPage.addToCart();
    }


    @Test
    // Login , add item or more to cart then view the cart items
    public void testBeforeCheckout(){
        checkoutPage.login();
        checkoutPage.addToCart();
        checkoutPage.viewCart();
    }

    @Test
    public void testLoggedInUserCheckout() throws InterruptedException {
        // Login , add item or more to cart then view the cart items
        testBeforeCheckout();

        // Proceed to checkout page
        checkoutPage.proceedToCheckout();

        // Check existence of Order Summary
        boolean isOrderSummaryExist = checkoutPage.isOrderSummaryExist();

        // Continue from user name, shipping and billing addresses and shipping method to the payment page in checkout process
        checkoutPage.checkoutContinueToPayment();

        // Check the existence of shipping information
        boolean isShippingInformationExist = checkoutPage.isShippingInformationExist();

        // Check existence of shipping method
        boolean isShippingMethodExist = checkoutPage.isShippingMethodExist();

        // Check existence of Order Summary in the payment page
        boolean isPaymentOrderSummaryExist = checkoutPage.isPaymentOrderSummaryExist();
        checkoutPage.toggleDiscountCode();

        // Apply Discount
        checkoutPage.applyDiscount(discountCode);
        Thread.sleep(50000);

        // Cancel Discount Coupon
        checkoutPage.cancelDiscountCoupon();

        //checkoutPage.paymentOrderSummaryDiscountView();
        //checkoutPage.paymentOrderSummaryView();

        // Confirm and place the order and end the checkout process
        checkoutPage.placeOrder();

        // Navigate to the placed order during checkout process and view its details
        checkoutPage.navigateToOrderDetails();

        // Navigate to My Orders and view the placed order with other previously placed orders
        checkoutPage.navigateToMyOrders();
    }


    @Test
    public void testLoggedInCheckout() throws InterruptedException {
        checkoutPage.openLoggedInCheckout();
        checkoutPage.loggedInCheckout();
    }

    // Here the tests for the Guest user
    @Test(dataProvider = "nameAddressData")
    public void testfillCheckoutDetails(String firstName, String lastName, String postalCode, String street, String stateProvince, String country, String phoneNumber) throws InterruptedException {
        checkoutPage.fillCheckoutDetails(firstName, lastName, postalCode, street, stateProvince, country, phoneNumber);
    }

    @Test
    public void TestGuestCheckout(String firstName, String lastName, String postalCode, String street, String stateProvince, String country, String phoneNumber) throws InterruptedException {
        checkoutPage.openGuestCheckout();
        checkoutPage.fillCheckoutDetails(firstName, lastName, postalCode, street, stateProvince, country, phoneNumber);
    }

    // After Annotations
    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - Runs after each test method");
    }

    @AfterClass
    public void tearDown()  {
        System.out.println("After Class - Runs once after all methods in this class");
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test - Runs after all test methods have run");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - Runs after all tests in the suite");
    }
}