package android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;


@Test
public class AppiumExample {

  private String reportDirectory = "reports";
  private String reportFormat = "xml";
  private String testName = "Untitled";
  protected AndroidDriver<WebElement> driver = null;
  DesiredCapabilities dc;

  @BeforeMethod
  public void setUp() throws MalformedURLException {
    dc = new DesiredCapabilities();
    dc.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    dc.setCapability("reportDirectory", reportDirectory);
    dc.setCapability("reportFormat", reportFormat);
    dc.setCapability("testName", testName);
    dc.setCapability("name", "Pixel 4 API 24");

    driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
    driver.setLogLevel(Level.INFO);

    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
  }



  @Test
  public void probandoCalculadora() {

    driver.findElement(By.xpath("//*[@text='Calculator']")).click();

    new WebDriverWait(driver, 10)
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='5']")));

    driver.findElement(By.xpath("//*[@text='5']")).click();
    driver.findElement(By.xpath("//*[@text='+']")).click();
    driver.findElement(By.xpath("//*[@text='3']")).click();
    driver.findElement(By.xpath("//*[@text='=']")).click();

    WebElement resultado =
        driver.findElement(By.xpath("//*[@resource-id='com.android.calculator2:id/result']"));

    Assert.assertEquals(Integer.parseInt(resultado.getText()), 8);

    driver.findElement(By.xpath("//*[@text='CLR']")).click();
  }


  @Test
  public void comprobarPedirCita() {

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    driver.findElement(By.xpath("//*[@text='MyApp']")).click();
    driver.findElement(By.xpath("//*[@text='PEDIR CITA']")).click();
    driver.findElement(By.xpath("//*[@text='CITA ATENCIÓN PRIMARIA']")).click();

    Assert.assertTrue(
        driver.findElement(By.xpath("//*[@resource-id='tarjetaportada']")).isDisplayed());
    
    driver.findElement(By.xpath("//*[@text='Inicio']")).click();

    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
  }



  @AfterTest
  public void tearDown() {
    driver.quit();
  }
}
