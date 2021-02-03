package android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;


@Test
public class AndroidSinAutenticar {

  private String reportDirectory = "reports";
  private String reportFormat = "xml";
  private String testName = "Untitled";
  protected AndroidDriver<WebElement> driver = null;
  DesiredCapabilities dc;

  @BeforeSuite
  /**
   * Carga todo lo necesario para conectarse con el emulador de Android
   * 
   * @throws MalformedURLException
   */
  public void setUp() throws MalformedURLException {

    dc = new DesiredCapabilities();
    dc.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
    dc.setCapability("reportDirectory", reportDirectory);
    dc.setCapability("reportFormat", reportFormat);
    dc.setCapability("testName", testName);
    dc.setCapability("name", "Pixel 4 API 24");

    driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
    driver.setLogLevel(Level.INFO);

    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    driver.findElement(By.xpath("//*[@text='MyApp']")).click();
  }

  /**
   * Pulsa en el botón Inicio de la aplicación por si estábamos en otro lado
   */
  @BeforeMethod
  public void pulsarTeclaInicio() {
    driver.findElement(By.xpath("//*[@text='Inicio']")).click();
  }

  /**
   * Comprueba entrar en cita previa de atención primaria
   * 
   */
  @Test
  public void paginaInicioCitaPreviaAtencionPrimaria() {
    driver.findElement(By.xpath("//*[@text='PEDIR CITA']")).click();
    driver.findElement(By.xpath("//*[@text='CITA ATENCIÓN PRIMARIA']")).click();
    Assert.assertTrue(
        driver.findElement(By.xpath("//*[@resource-id='tarjetaportada']")).isDisplayed());
  }

  /**
   * Comprueba entrar en cita previa de atención hospitalaria
   * 
   */
  @Test
  public void paginaInicioCitaPreviaAtencionHospitalaria() {
    driver.findElement(By.xpath("//*[@text='PEDIR CITA']")).click();
    driver.findElement(By.xpath("//*[@text='CITA ATENCIÓN HOSPITALARIA']")).click();
    Assert.assertEquals(driver.findElement(By.xpath("//*[@resource-id='ot1']")).getText(),
        "Bienvenido a Citación Especializada");
  }



  @Test
  public void encuentraFarmacia() {
    driver.findElement(By.xpath("//*[@text='ENCONTRAR FARMACIA']")).click();

    driver.findElement(By.xpath("//*[@text='Provincia']")).click();
    driver.findElement(By.xpath("//*[@text='ALBACETE']")).click();
    driver.findElement(By.xpath("//*[@text='OK']")).click();

    driver.findElement(By.xpath("//*[@text='Localidad']")).click();
    driver.findElement(By.xpath("//*[@text='ABEJUELA']")).click();
    driver.findElement(By.xpath("//*[@text='OK']")).click();

    driver.findElement(By.xpath("//*[@text='Fecha']")).click();

    driver.findElement(By.xpath(
        "((//*[@class='android.app.Dialog']/*[@class='android.view.View'])[2]/*/*/*[@text='03'])[1]"))
        .click();
    driver
        .findElement(By.xpath(
            "//*[@text='02' and (./preceding-sibling::* | ./following-sibling::*)[@text='05']]"))
        .click();

    driver.findElement(By.xpath("//*[@text='2020']")).click();
    driver.findElement(By.xpath("//*[@text='OK']")).click();

    driver.findElement(By.xpath("//*[@text='FARMACIAS']")).click();

    Assert.assertTrue(
        driver.findElement(By.xpath("//*[@text='FERNANDO LUIS ORTUÑO TOMAS']")).isDisplayed());
  }



  /**
   * Comprueba el popup del teléfono en la página principal
   */
  @Test
  public void comprobarClickTelefono() {
    driver.findElement(By.xpath("//*[contains(@text,'TELÉFONOS DE ATENCIÓN')]")).click();
    Assert.assertTrue(driver.findElement(By.xpath("//*[@text='900 12 21 12']")).isDisplayed(),
        "La pantalla es incorrecta");
    driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
  }



  /**
   * Comprueba el enlace del coronavirus en la página principal
   */
  @Test
  public void comprobarEnlaceCoronavirus() {
    driver.findElement(By.xpath("//*[contains(@text,'ASISTENTE VIRTUAL')]")).click();
    driver.findElement(By.xpath("//*[contains(@text,'SÍ')]")).click();
    Assert.assertTrue(
        driver.findElement(By.xpath("//*[contains(@text,'Asistencia COVID-19')]")).isDisplayed());
    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    driver.findElement(By.xpath("//*[@text='MyApp']")).click();
    driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
  }

  /**
   * Comprueba pulsar más y entrar en privacidad
   */
  @Test
  public void masPrivacidad() {
    driver.findElement(By.xpath("//*[@text='menu']")).click();
    driver.findElement(By.xpath("//*[contains(@text,'Privacidad')]")).click();
    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    driver.findElement(By.xpath("//*[@text='MyApp']")).click();
    Assert.assertTrue(
        driver.findElement(By.xpath("//*[contains(@text,'Privacidad')]")).isDisplayed());
  }

  /**
   * Comprueba pulsar más y entrar en ayuda
   */
  @Test
  public void masAyuda() {
    driver.findElement(By.xpath("//*[@text='menu']")).click();
    driver.findElement(By.xpath("//*[@text='Ayuda']")).click();
    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    driver.findElement(By.xpath("//*[@text='MyApp']")).click();
    Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'Ayuda')]")).isDisplayed());
  }

  /**
   * Comprueba pulsar más y entrar en notificaciones
   */
  @Test
  public void mostarNotificaciones() {
    driver.findElement(By.xpath("//*[@text='menu']")).click();
    driver.findElement(By.xpath("//*[@text='Notificaciones']")).click();
    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    driver.findElement(By.xpath("//*[@text='MyApp']")).click();
    Assert.assertTrue(
        driver.findElement(By.xpath("//*[contains(@text,'Notificaciones')]")).isDisplayed());
  }

  /**
   * Comprueba pulsar más y entrar en contacto
   */
  @Test
  public void masContacto() {
    driver.findElement(By.xpath("//*[@text='menu']")).click();
    driver.findElement(By.xpath("//*[@text='Contacto']")).click();
    driver.findElement(By.xpath("//*[contains(@text,'Chrome')]")).click();
    driver.findElement(By.xpath("//*[@resource-id='android:id/button_once']")).click();
    Assert.assertEquals(
        driver.findElement(By.xpath("//*[@resource-id='com.android.chrome:id/url_bar']")).getText(),
        "sanidad.castillalamancha.es/ciudadanos/reclamaciones");
    driver.context("WEBVIEW_chrome");
    Assert.assertTrue(
        driver.findElement(By.xpath("//span[contains(.,'Consejería de Sanidad')]")).isDisplayed());
    driver.context("NATIVE_APP");
    driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    driver.findElement(By.xpath("//*[@text='MyApp']")).click();
  }



  @AfterTest
  public void tearDown() {
    driver.findElement(By.xpath("//*[@text='Inicio']")).click();
    driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    driver.quit();
  }
}
