package test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public class FirstTest {
    public static void main(String[] args) {
        String fechaAComprar = "camel";
        int cantAComprar = 10;
        int count = 0;
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
            WebDriverWait wait = new WebDriverWait(appiumDriver, SECONDS.ordinal());
            appiumDriver.manage().timeouts().implicitlyWait(15L, SECONDS);

            //Si aparece mensaje de error para iniciar sesion
            if (appiumDriver.findElementById("com.infinixsoft.bombo:id/tvTitle").isDisplayed()) {
                appiumDriver.findElementById("com.infinixsoft.bombo:id/btnPositive").click();
            }

            //Ingresar datos de login
            appiumDriver.findElementByXPath("//android.widget.EditText[@text=\"Email\"]").sendKeys("candelaabrilcasella@hotmail.com");
            appiumDriver.findElementByXPath("//android.widget.EditText[@text=\"Contraseña\"]").sendKeys("Chuchuni1");
            appiumDriver.findElementById("com.infinixsoft.bombo:id/btnLogin").click();
            wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath("//android.widget.EditText[@resource-id=\"android:id/search_src_text\"]")));

            //Circuito de búsqueda y compra
            appiumDriver.findElementById("android:id/search_mag_icon").click();
            wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath("//android.widget.TextView[@text=\"EVENTOS\"]")));
            appiumDriver.findElementById("android:id/search_src_text").sendKeys(fechaAComprar);
            appiumDriver.findElementByXPath("//android.widget.TextView[@text=\"EVENTOS\"]").click();

            boolean noResultsmsg = true;
            while (noResultsmsg) {
                try {
                    MobileElement noResults = appiumDriver.findElementByXPath("//android.widget.TextView[@text=\"No hay resultados en eventos\"]");
                } catch (NoSuchElementException e) {
                    noResultsmsg = false;
                }
                if(noResultsmsg){
                    appiumDriver.findElementById("android:id/search_src_text").sendKeys("\b");
                    appiumDriver.findElementById("android:id/search_src_text").sendKeys(fechaAComprar);
                }

            }
            appiumDriver.findElementByXPath("//android.widget.FrameLayout[@resource-id=\"com.infinixsoft.bombo:id/cardViewPicture\"]").click();
            boolean soldOut = true;
            while(soldOut){

                MobileElement earlyItem = (MobileElement) appiumDriver.findElement(
                        MobileBy.AndroidUIAutomator(
                                "new UiScrollable(new UiSelector()).scrollIntoView("
                                        + "new UiSelector().textMatches(\"EARLY BIRD\"));"));
                try {
                    MobileElement soldoutText = appiumDriver.findElementByXPath("//android.widget.TextView[@text='EARLY BIRD']/following-sibling::android.widget.TextView[@text='SOLD OUT']");
                } catch (NoSuchElementException e) {
                    soldOut = false;
                }
                //If early bird sold out
                if (soldOut) {
                    appiumDriver.findElementByXPath("//android.widget.ImageButton[@resource-id=\"com.infinixsoft.bombo:id/btnBack\"]").click();
                    appiumDriver.findElementByXPath("//android.widget.FrameLayout[@resource-id=\"com.infinixsoft.bombo:id/cardViewPicture\"]").click();
                }
            }

            //Click para aumentar dos entradas
            appiumDriver.findElementByXPath("//android.widget.TextView[@text='EARLY BIRD']/following-sibling::android.widget.FrameLayout/android.view.ViewGroup/android.widget.ImageView[2]").click();
            appiumDriver.findElementByXPath("//android.widget.TextView[@text='EARLY BIRD']/following-sibling::android.widget.FrameLayout/android.view.ViewGroup/android.widget.ImageView[2]").click();

            if (!appiumDriver.findElementByXPath("//android.widget.Button[@text=\"Comprar ahora\"]").isDisplayed()) {
                MobileElement buyNowBtn = (MobileElement) appiumDriver.findElement(
                        MobileBy.AndroidUIAutomator(
                                "new UiScrollable(new UiSelector()).scrollIntoView("
                                        + "new UiSelector().textMatches(\"Comprar ahora\"));"));
            }


            while (count < cantAComprar) {
                appiumDriver.findElementByXPath("//android.widget.Button[@text=\"Comprar ahora\"]").click();
                //Scroll to Finalizar compra
                /*HashMap<String, Object> scrollObject1 = new HashMap();
                AndroidElement element1 = (AndroidElement) appiumDriver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.infinixsoft.bombo:id/action_bar_root\")"));
                scrollObject1.put("elementId", element1.getId());
                scrollObject1.put("strategy", "-android uiautomator");
                scrollObject1.put("selector", "text(\"Finalizar compra\")");
                appiumDriver.executeScript("mobile: scroll", scrollObject1);*/
                appiumDriver.findElement(MobileBy.AndroidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToEnd(10);"));
                appiumDriver.findElementByXPath("//android.widget.Button[@text=\"Finalizar compra\"]").click();
                //Thread.sleep(500);
                if (appiumDriver.findElementByXPath("//android.view.View[@text=\"Bombo Community\"]").isDisplayed()) {
                    count += 2;
                    appiumDriver.navigate().back();
                    wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath("//android.widget.TextView[@resource-id=\"com.infinixsoft.bombo:id/tvPlaceholderTitle\"]")));
                    appiumDriver.navigate().back();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
