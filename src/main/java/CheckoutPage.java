
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


class CheckoutPage {
    WebDriver driver;
    private WebDriverWait wait;

    String baseUrl = "https://magento.softwaretestingboard.com";
    String guestCheckoutUrl = "https://magento.softwaretestingboard.com/checkout/#shipping";
    String loggedInCheckoutUrl = "https://magento.softwaretestingboard.com/checkout/#shipping";

    String notLoggedInXPath = "/html/body/div[2]/header/div[1]/div/ul/li[2]";
    String checkoutNextToPaymentXPath = "//*[@id=\"shipping-method-buttons-container\"]/div/button";

    // XPaths
    String orderSummaryXpath = "//*[@id=\"opc-sidebar\"]/div[1]/span";
    String shippingInformationXPath = "//*[@id=\"opc-sidebar\"]/div[2]/div/div[1]/div[2]";
    String shippingMethodXPath = "//*[@id=\"opc-sidebar\"]/div[2]/div/div[2]/div[2]";
    String paymentOrderSummaryXpath = "//*[@id=\"opc-sidebar\"]/div[1]/span";
    String applyDiscountXPath = "//*[@id=\"discount-form\"]/div[2]/div/button/span/span";
    String cancelCouponXPath = "//*[@id=\"discount-form\"]/div[2]/div/button";
    String placeOrderBtnXpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[2]/div[2]/div[4]/div/button";
    String orderNoLinkXpath = "//*[@id=\"maincontent\"]/div[3]/div/div[2]/p[1]/a";
    String myOrdersLinkXPath = "//*[@id=\"block-collapsible-nav\"]/ul/li[2]/a"; //"//*[@id="\block-collapsible-nav\"]/ul/li[2]/a";

    // Locators
    By proceedToCheckoutBtn = By.id("top-cart-btn-checkout");
    By checkoutNextToPaymentBtn = By.xpath(checkoutNextToPaymentXPath);
    By nextToPaymentCheckoutBtn = By.xpath(checkoutNextToPaymentXPath);
    By placeOrderBtn = By.xpath(placeOrderBtnXpath);

    By overlay = By.cssSelector(".loading-mask"); // Common overlay class (adjust if needed)
    By orderNoLink = By.xpath(orderNoLinkXpath);
    By myOrdersLink = By.xpath(myOrdersLinkXPath);

    By orderSummary = By.xpath(orderSummaryXpath);
    By shippingInformation = By.xpath(shippingInformationXPath);
    By shippingMethod = By.xpath(shippingMethodXPath);
    By paymentOrderSummary = By.xpath(paymentOrderSummaryXpath);
    By discountCode = By.id("discount-code");
    By applyDiscountField = By.id("discount-code");
    By applyDiscountBtn = By.xpath(applyDiscountXPath);
    By cancelDiscountCouponBtn = By.xpath(cancelCouponXPath);

    By customerEmailField = By.id("customer-email");
    By firstNameField = By.name("firstname");
    By lastNameField = By.name("lastname");
    //By street = By.className("street[0]");
    By postalCodeField = By.name("postcode");
    //By stateProvince = By.name("region_id");
    //By country = By.name("country_id");
    By phoneNumber = By.cssSelector("input[name=\"telephone\"]");


    // Payment Order Summary Locators
    By paymentOrderSummaryDiscount = By.xpath("//*[@id=\"opc-sidebar\"]/div[1]/table/tbody/tr[2]/th/span[1]");//cssSelector("tr.totals.discount .amount .price");
    By paymentOrderSummaryCartItems = By.cssSelector(".product-item-name");
    By paymentOrderSummaryQuantity = By.cssSelector(".details-qty .value");
    By paymentOrderSummaryPrice = By.cssSelector(".cart-price .price");
    By paymentOrderSummaryShippingCost = By.cssSelector("tr.totals.shipping .amount .price");
    By paymentOrderSummaryOrderTotal = By.xpath("//*[@id=\"opc-sidebar\"]/div[1]/table/tbody/tr[4]/th/strong");

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        //this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(100));
    }

    public void open(){
        driver.get(baseUrl);
    }

    public void openGuestCheckout(){
        driver.get(guestCheckoutUrl);
    }

    public void openLoggedInCheckout(){
        driver.get(loggedInCheckoutUrl);
    }

    // Method to login
    public void login(){
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/");
        By email = By.id("email");
        By password = By.id("pass");
        By loginBtn = By.id("send2");

        // Recommended to use Data provider
        driver.findElement(email).sendKeys("hanan.azzam@gmail.com");
        driver.findElement(password).sendKeys("balkeesme369@");

        driver.findElement(loginBtn).click();

        // Wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Method to add to cart
    public void addToCart(){
        driver.get("https://magento.softwaretestingboard.com/didi-sport-watch.html");
        By addToCartBtn = By.id("product-addtocart-button");
        driver.findElement(addToCartBtn).click();
    }

    // Method to view the cart items then proceed to checkout
    public void viewCart(){
        String cartXpath = "/html/body/div[2]/header/div[2]/div[1]/a/span[2]";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for the cart icon to be visible
        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cartXpath)));

        // Click the cart icon
        cartIcon.click();
        System.out.println("Cart viewed successfully!");
    }

    // Method to proceed to checkout page
    public void proceedToCheckout(){
        driver.findElement(proceedToCheckoutBtn).click();
    }

    // Method to check existence of Order Summary
    public boolean isOrderSummaryExist() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(orderSummary));
            WebElement element = driver.findElement(orderSummary);
            boolean isExisted = element.isDisplayed();
            System.out.println("Order Summary exists: " + isExisted);
            return isExisted;
        } catch (NoSuchElementException e) {
            Assert.fail("Order Summary does not appear.");
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            Assert.fail("Error happened: " + e.getMessage());
            return false;
        }
    }

    // Method to continue from user name, shipping and billing addresses and shipping method to the payment page in checkout process
    public void checkoutContinueToPayment(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextToPaymentCheckoutBtn));

        // Scroll into view before clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextButton);

        // Adding a slight delay to ensure scrolling is completed
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        // Click the button
        nextButton.click();
        System.out.println("Next button is clicked successfully");
    }

    // Method to check the existence of shipping information
    public boolean isShippingInformationExist() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(shippingInformation));
            WebElement element = driver.findElement(shippingInformation);
            boolean isExisted = element.isDisplayed();
            System.out.println("Shipping information exists: " + isExisted);
            return isExisted;
        } catch (NoSuchElementException e) {
            Assert.fail("Shipping information is not Set.");
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            Assert.fail("Error happened: " + e.getMessage());
            return false;
        }
    }

    // Method to check existence of shipping method
    public boolean isShippingMethodExist() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(shippingMethod));
            WebElement element = driver.findElement(shippingMethod);
            boolean isExisted = element.isDisplayed();
            System.out.println("Shipping Method exists: " + isExisted);
            return isExisted;
        } catch (NoSuchElementException e) {
            Assert.fail("Shipping Method does not appear.");
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            Assert.fail("Error happened: " + e.getMessage());
            return false;
        }
    }

    // Method to check existence of Order Summary in the payment page
    public boolean isPaymentOrderSummaryExist() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(paymentOrderSummary));
            WebElement element = driver.findElement(paymentOrderSummary);
            boolean isExisted = element.isDisplayed();
            System.out.println("Payment Order Summary exists: " + isExisted);
            return isExisted;
        } catch (NoSuchElementException e) {
            Assert.fail("Payment Order Summary does not appear.");
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            Assert.fail("Error happened: " + e.getMessage());
            return false;
        }
    }

    // Method to toggle the discount code section
    public void toggleDiscountCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By discountToggleButton = By.id("block-discount-heading");
        By overlay = By.className("loading-mask");

        try {
            // Wait for the overlay to disappear (if applicable)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

            // Wait until the element is visible and clickable
            WebElement toggleButton = wait.until(ExpectedConditions.elementToBeClickable(discountToggleButton));

            // Scroll into view before clicking
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", toggleButton);
            Thread.sleep(500);

            // Click the button normally
            toggleButton.click();

            System.out.println("Discount Code section toggled successfully.");
        } catch (Exception e) {
            System.err.println("Failed to toggle Discount Code section: " + e.getMessage());
        }
    }


    // Method to Apply Discount
    public void applyDiscount(String discountCode){
        driver.findElement(applyDiscountField).sendKeys(discountCode);
        driver.findElement(applyDiscountBtn).click();
    }

    // Method to Cancel Discount Coupon
    public void cancelDiscountCoupon(){
        //driver.findElement(cancelDiscountCouponBtn).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement cancelDiscountCouponButton = wait.until(ExpectedConditions.elementToBeClickable(cancelDiscountCouponBtn));

        // Scroll into view before clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelDiscountCouponButton);

        // Adding a slight delay to ensure scrolling is completed
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        // Click the button
        cancelDiscountCouponButton.click();
        System.out.println("Next button is clicked successfully");
    }

    // Method to print the locator as a label and the inner text
    String getElementText(By locator, String label) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            String text = element.getText().trim();
            System.out.println(label + ": " + text);
            return text;
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println(label + ": Not Found");
            return "";
        }
    }

    // Method to confirm and place the order and end the checkout process
    public void placeOrder() {
        try {
            System.out.println("Waiting for the 'Place Order' button...");

            // Wait for any overlay to disappear
            waitForOverlayToDisappear();

            // Wait for the button to be clickable
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));

            // Click using JavaScriptExecutor to bypass interactability issues
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", button);

            System.out.println("'Place Order' button clicked successfully.");

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            Assert.fail("Unexpected error clicking 'Place Order' button.");
        }
    }

    private void waitForOverlayToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (TimeoutException e) {
            System.out.println("Overlay still present, proceeding with clicking.");
        }
    }

    // Method to navigate to the placed order during checkout process and view its details
    public void navigateToOrderDetails() {
        try {
            System.out.println("Navigating to the order details page.");

            // Wait for the order link to be visible and clickable
            WebElement orderLink = wait.until(ExpectedConditions.elementToBeClickable(orderNoLink));
            String orderDetailsUrl = orderLink.getAttribute("href");

            if (orderDetailsUrl != null) {
                driver.get(orderDetailsUrl); // Open the order details page
                System.out.println("Navigated to order details: " + orderDetailsUrl);
            } else {
                System.err.println("Order number link is missing!");
            }
        } catch (Exception e) {
            System.err.println("Error navigating to order details: " + e.getMessage());
            throw e;
        }
    }

    // Method to navigate to My Orders and view the placed order with other previously placed orders
    public void navigateToMyOrders() {
        try {
            System.out.println("Navigating to the My Orders page.");

            // Wait for the order link to be visible and clickable
            WebElement orderLink = wait.until(ExpectedConditions.elementToBeClickable(myOrdersLink));
            String myOrdersUrl = orderLink.getAttribute("href");

            if (myOrdersUrl != null) {
                driver.get(myOrdersUrl); // Open the order details page
                System.out.println("Navigated to My Orders: " + myOrdersUrl);
            } else {
                System.err.println("My Orders link is missing!");
            }
        } catch (Exception e) {
            System.err.println("Error navigating to My Orders: " + e.getMessage());
            throw e;
        }
    }

    // Method to view the applied discount data and value
    public void paymentOrderSummaryDiscountView() {
        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(paymentOrderSummaryDiscount));
            //WebElement element = driver.findElement(paymentOrderSummaryDiscount);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(paymentOrderSummaryDiscount));

            boolean isExisted = element.isDisplayed();
            System.out.println("Payment Order Summary Discount exists: " + isExisted);

        } catch (NoSuchElementException e) {
            Assert.fail("Payment Order Summary does not appear.");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            Assert.fail("Error happened: " + e.getMessage());

        }
    }

    // Method to view the Order Summary: items, quantity, prices, discount, shipping cost and order total
    public void fetchOrderSummary() {
        try {
            System.out.println("Fetching Order Summary...");

            // Wait for discount field to be visible and interactable before fetching
            wait.until(ExpectedConditions.visibilityOfElementLocated(paymentOrderSummaryDiscount));
            WebElement discountElement = driver.findElement(paymentOrderSummaryDiscount);

            if (discountElement.isDisplayed() && discountElement.isEnabled()) {
                String discount = discountElement.getText();
                System.out.println("Discount Applied: " + discount);
            } else {
                System.out.println("No discount applied.");
            }

            // Fetch Cart Items
            List<WebElement> items = driver.findElements(paymentOrderSummaryCartItems);
            List<WebElement> quantities = driver.findElements(paymentOrderSummaryQuantity);
            List<WebElement> prices = driver.findElements(paymentOrderSummaryPrice);

            double orderTotal = 0.0;

            System.out.println("---- Order Summary ----");
            for (int i = 0; i < items.size(); i++) {
                String itemName = items.get(i).getText();
                int quantity = Integer.parseInt(quantities.get(i).getText().trim());
                double price = Double.parseDouble(prices.get(i).getText().replace("$", "").trim());

                orderTotal += (quantity * price);
                System.out.println("Item: " + itemName + " | Qty: " + quantity + " | Price: $" + price);
            }

            //System.out.println("Total Calculated: $" + orderTotal);
            System.out.println("Order Summary finished successfully.");

        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.err.println("Element not interactable: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error fetching order summary: " + e.getMessage());
        }
    }


    public void loggedInCheckout(){
        openLoggedInCheckout();
        //driver.findElement(nextButton).click();
    }

    public boolean isGuestUser() {
        List<WebElement> guestElements = driver.findElements(By.xpath(notLoggedInXPath));
        return !guestElements.isEmpty();
    }

    public void chooseShippingMethod(String methodValue) {
        List<WebElement> shippingMethods = driver.findElements(By.xpath(shippingMethodXPath));
        for (WebElement method : shippingMethods) {
            if (method.getAttribute("value").equals(methodValue)) {
                method.click();
                System.out.println("Selected shipping method: " + methodValue);
                return;
            }
        }
        throw new RuntimeException("Shipping method " + methodValue + " not found");
    }

    public void fillCheckoutDetails(String firstName, String lastName, String postalCode, String street, String stateProvince, String country, String phoneNumber) throws InterruptedException {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
        //driver.findElement(street).sendKeys(street);
        //driver.findElement(stateProvince).sendKeys(stateProvince);
        //driver.findElement(country).sendKeys(country);
        //driver.findElement(phoneNumber).sendKeys(phoneNumber);

        // check for it driver.findElement(nextButton).click();
        Thread.sleep(3000);
        /*try {
            Thread.sleep(3000); // Handle the exception inside the method
        } catch (InterruptedException e) {
            e.printStackTrace(); // Print the exception stack trace (optional)
            Thread.currentThread().interrupt(); // Restore the interrupt status
        }*/
    }

}





