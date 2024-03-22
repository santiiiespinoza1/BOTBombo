package test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;


public class LaunchApp {

    public static void main(String[] args) {
        try {
            AppiumDriver<MobileElement> appiumDriver;

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
            desiredCapabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            desiredCapabilities.setCapability("appPackage", "com.infinixsoft.bombo");
            desiredCapabilities.setCapability("appActivity", "com.infinixsoft.bombo.ui.eventDetail.EventDetailActivity");

            //Setup the Appium server URL to connect to
            URL appiumServer = new URL("http://localhost:4723");

            appiumDriver = new AppiumDriver<>(appiumServer, desiredCapabilities);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
