package com.appium.sanity;

import com.google.gson.internal.bind.util.ISO8601Utils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AppiumHelloWorld {


    File rootPath = new File(System.getProperty("user.dir"));
    File src = new File(rootPath,"/Downloads/");
    File appPath = new File(src,"TheApp-v1.7.1.apk");



    private AndroidDriver driver;
    // private String APP = "https://github.com/cloudgrey-io/the-app/releases/download/v1.7.1/TheApp-v1.7.1.apk";
    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Nexus");
        capabilities.setCapability("avd", "qa14_mob_nexus5");
        capabilities.setCapability("automationName", "UiAutomator2");
        // capabilities.setCapability("app", APP);
        capabilities.setCapability("app", appPath.getAbsolutePath());


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
        Thread.sleep(20000);
        driver.findElementByAccessibilityId("Login Screen").click();
    }
}
