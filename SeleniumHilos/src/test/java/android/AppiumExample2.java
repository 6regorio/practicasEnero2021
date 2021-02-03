package android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import java.net.MalformedURLException;
import java.net.URL;

public class AppiumExample2 {

  WebDriver driver;
  WebDriverWait wait;
  String AppURL = "https://sescampre.jccm.es/portalsalud/app/inicio";

  @BeforeTest
  public void setup() throws MalformedURLException {
    DesiredCapabilities capabilities = DesiredCapabilities.android();

    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 4 API 24");
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);

    driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);


    wait = new WebDriverWait(driver, 5);
  }

  @Test
  public void testSearchAppium() {
    driver.get(AppURL);

    driver.findElement(By.xpath("//span[contains(.,'PEDIR CITA')]")).click();
    driver.findElement(By.xpath("//span[contains(.,'PRIMARIA')]")).click();
    driver.switchTo().frame(0);
    WebElement imagentarjeta = driver.findElement(By.id("tarjetaportada"));
    Assert.assertTrue(imagentarjeta.isDisplayed());


  }

  @AfterTest
  public void tearDown() {
    driver.quit();
  }
}
































// driver.findElement(By.xpath("//*[@contentDescription='Inicio']")).click();



//
//
//
//
//
// driver.findElement(By.xpath("//*[@text='MyApp']")).click();
//
//
// try {
// Thread.sleep(1500);
// } catch (InterruptedException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
//// driver.context("NATIVE_APP");
//
// //Farmacia
// // driver.findElement(By.xpath("(((//*[@class='android.view.View' and
// ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View' and
// (./preceding-sibling::* | ./following-sibling::*)[@class='android.widget.TabWidget'] and
// ./parent::*[@class='android.view.View']]]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*/*[@text
// and @class='android.view.View' and ./parent::*[@class='android.view.View']])[2]")).click();
//
// //Pedir cita
//// driver.findElement(By.xpath("(((//*[@class='android.view.View' and
// ./parent::*[@class='android.view.View' and ./parent::*[@class='android.view.View' and
// (./preceding-sibling::* | ./following-sibling::*)[@class='android.widget.TabWidget'] and
// ./parent::*[@class='android.view.View']]]]/*[@class='android.view.View'])[2]/*[@class='android.view.View'])[2]/*/*[@text
// and @class='android.view.View' and ./parent::*[@class='android.view.View']])[1]")).click();
//
// driver.findElement(By.xpath("//*[@text='ENCONTRAR FARMACIA']")).click();


// driver.findElement(By.xpath("//*[@bounds='[552,470][1014,841]']")).click();
// driver.findElement(By.xpath("//*[@text='Provincia']")).click();
// driver.findElement(By.xpath("//*[@bounds='[44,280][899,338]']")).click();
// driver.findElement(By.xpath("//*[@text='Localidad']")).click();
// driver.findElement(By.xpath("//*[@bounds='[44,415][899,473]']")).click();
// driver.findElement(By.xpath("//*[@text='Fecha']")).click();
// driver.findElement(By.xpath("//*[@bounds='[44,550][770,607]']")).click();
// driver.findElement(By.xpath("//*[@text='Sescam Mobile App']")).click();
// new WebDriverWait(driver,
// 120).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='android.widget.Button'
// and ./parent::*[@text='Provincia']]")));
// driver.findElement(By.xpath("//*[@class='android.widget.Button' and
// ./parent::*[@text='Provincia']]")).click();
// driver.findElement(By.xpath("//*[@text='ALBACETE']")).click();
// driver.findElement(By.xpath("//*[@text='OK']")).click();
// driver.findElement(By.xpath("//*[@class='android.widget.Button' and
// ./parent::*[@text='Localidad']]")).click();
// driver.findElement(By.xpath("//*[@text='ABEJUELA']")).click();
// driver.findElement(By.xpath("//*[@text='OK']")).click();
// driver.findElement(By.xpath("//*[@class='android.widget.Button' and
// ./parent::*[@text='Fecha']]")).click();
// driver.findElement(By.xpath("((//*[@class='android.app.Dialog']/*[@class='android.view.View'])[2]/*/*/*[@text='02'])[1]")).click();
// driver.findElement(By.xpath("//*[@text='01' and (./preceding-sibling::* |
// ./following-sibling::*)[@text='05']]")).click();
// driver.findElement(By.xpath("//*[@text='2020']")).click();
// driver.findElement(By.xpath("//*[@text='OK']")).click();
// driver.findElement(By.xpath("//*[@text='FARMACIAS']")).click();

// driver.findElement(By.xpath("//*[@text='FERNANDO LUIS ORTUÑO TOMAS']")).click();
// driver.findElement(By.xpath("//*[@bounds='[44,896][742,979]']")).click();



//
//
// driver
// .findElement(
// By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='Provincia']]"))
// .click();
// driver.findElement(By.xpath("//*[@text='ALBACETE']")).click();
// driver.findElement(By.xpath("//*[@text='OK']")).click();
// driver
// .findElement(
// By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='Localidad']]"))
// .click();
// driver.findElement(By.xpath("//*[@text='ABEJUELA']")).click();
// driver.findElement(By.xpath("//*[@text='OK']")).click();
// driver
// .findElement(By.xpath("//*[@class='android.widget.Button' and ./parent::*[@text='Fecha']]"))
// .click();
// // new TouchAction(driver).press(373, 2042).waitAction(Duration.ofMillis(4048)).moveTo(418,
// // 623).release().perform();
// driver.findElement(By.xpath("//*[@text='13']")).click();
// new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
// "((//*[@class='android.app.Dialog']/*[@class='android.view.View'])[2]/*/*/*[@text='01'])[2]")));
// driver.findElement(By.xpath(
// "((//*[@class='android.app.Dialog']/*[@class='android.view.View'])[2]/*/*/*[@text='01'])[2]"))
// .click();
// driver.findElement(By.xpath("//*[@text='2020']")).click();
// driver.findElement(By.xpath("//*[@text='OK']")).click();
// driver.findElement(By.xpath("//*[@text='FARMACIAS']")).click();
//// new WebDriverWait(driver, 10).until(
//// ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Sescam Mobile App']")));
//// driver.findElement(By.xpath("//*[@text='Sescam Mobile App']")).click();
//// driver.findElement(By.xpath("//*[@text='Sescam Mobile App']")).click();
//// driver.findElement(By.xpath("//*[@id='home']")).click();
