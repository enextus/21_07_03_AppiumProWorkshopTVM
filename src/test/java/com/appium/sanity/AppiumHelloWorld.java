package com.appium.sanity;

import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumHelloWorld {

    private AndroidDriver driver;
    private String APP = "https://github.com/cloudgrey-io/the-app/releases/download/v1.7.1/TheApp-v1.7.1.apk";

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Nexus");
        capabilities.setCapability("avd", "Nexus");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("app", APP);

        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void appiumHelloWorld() throws InterruptedException {
        Thread.sleep(10000);
        driver.findElementByAccessibilityId("Login Screen").click();
    }
}