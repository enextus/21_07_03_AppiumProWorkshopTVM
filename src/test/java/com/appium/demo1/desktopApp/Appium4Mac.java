package com.appium.demo1.desktopApp;

import com.appium.Secret;
import io.appium.java_client.AppiumDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Appium4Mac implements Secret {

    private AppiumDriver driver;

    @Before
    public void setUp() throws IOException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "Mac");
        caps.setCapability("deviceName", "Mac");

        caps.setCapability("app", "Activity Monitor");
        driver = new AppiumDriver(new URL("http://localhost:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception ign) {}
    }

    @Test
    public void testActivityMonitor() {
        String baseAXPath = "/AXApplication[@AXTitle='Activity Monitor']/AXWindow";
        String tabSelectorTemplate = baseAXPath + "/AXToolbar/AXGroup/AXRadioGroup/AXRadioButton[@AXTitle='%s']";
        driver.findElementByXPath(String.format(tabSelectorTemplate, "Memory")).click();
        driver.findElementByXPath(String.format(tabSelectorTemplate, "Energy")).click();
        driver.findElementByXPath(String.format(tabSelectorTemplate, "Disk")).click();
        driver.findElementByXPath(String.format(tabSelectorTemplate, "Network")).click();
        driver.findElementByXPath(String.format(tabSelectorTemplate, "CPU")).click();

        WebElement searchField = driver.findElementByXPath(baseAXPath + "/AXToolbar/AXGroup/AXTextField[@AXSubrole='AXSearchField']");
        searchField.sendKeys("node");

        WebElement firstRow = driver.findElementByXPath(baseAXPath + "/AXScrollArea/AXOutline/AXRow[0]/AXStaticText");

        Assert.assertEquals(" node", firstRow.getText());
    }

}
