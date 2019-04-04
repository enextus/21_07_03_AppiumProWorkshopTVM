package com.appium.demo6.grid;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppiumGrid {

  /**
   * *
   *
   * java -cp *:. org.openqa.grid.selenium.GridLauncherV3 -role hub -port 4443
   * appium --nodeconfig node-config-device4724.json -p 4724 -cp 4726
   * appium --nodeconfig node-config-device4725.json -p 4725 -cp 2727
   */
  private String APP_IOS =
      "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.app.zip";

    private IOSDriver driver;
    private WebDriverWait wait;

    private static By pickerScreen = MobileBy.AccessibilityId("Picker Demo");
    private static By pickers = MobileBy.className("XCUIElementTypePickerWheel");
    private static By learnMoreBtn = MobileBy.AccessibilityId("learnMore");

    @Before
    public void setUp() throws IOException {
        String udid ="8EE2364D-2487-4C37-BD5B-821EA341334B";
        //String udid ="A0C01930-7ACD-4766-81AF-CCA441CDFB35";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "11.4");
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("deviceName", udid);
        capabilities.setCapability("app", APP_IOS);
        Random r = new Random();
        int low = 6000;
        int high = 7000;
        int wdaPort = r.nextInt(high-low) + low;
        capabilities.setCapability("wdaLocalPort", wdaPort);

        driver = new IOSDriver<>(new URL("http://192.168.1.63:4443/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPicker() {
        // get to the picker view
        wait.until(ExpectedConditions.presenceOfElementLocated(pickerScreen)).click();

        // find the picker elements
        List<WebElement> pickerEls = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(pickers));

        // use the sendKeys method to set the picker wheel values directly
        pickerEls.get(0).sendKeys("March");
        pickerEls.get(1).sendKeys("6");

        // trigger the API call to get date info
        driver.findElement(learnMoreBtn).click();

        // verify info was retrieved for the correct date
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        Assert.assertThat(alert.getText(), Matchers.containsString("On this day"));

        // clear the alert
        alert.accept();
        wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));

        // use the selectPickerWheelValue method to move to the next value in the 'month' wheel
        HashMap<String, Object> params = new HashMap<>();
        params.put("order", "next");
        params.put("offset", 0.15);
        params.put("element", ((RemoteWebElement) pickerEls.get(0)).getId());
        driver.executeScript("mobile: selectPickerWheelValue", params);

        // and move to the previous value in the 'day' wheel
        params.put("order", "previous");
        params.put("element", ((RemoteWebElement) pickerEls.get(1)).getId());
        driver.executeScript("mobile: selectPickerWheelValue", params);

        // trigger the API call to get date info
        driver.findElement(learnMoreBtn).click();

        // and finally verify info was retrieved for the correct date (4/5)
        wait.until(ExpectedConditions.alertIsPresent());
        alert = driver.switchTo().alert();
        Assert.assertThat(alert.getText(), Matchers.containsString("On this day"));
        alert.accept();
    }
}