package publico;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ProbandoGrid {
  private WebDriver driverC;
  private WebDriver driverF;
  private WebDriver driverO;

  private String nodeUrl;

  @BeforeTest
  public void StartBrowser() throws MalformedURLException {
    nodeUrl = "http://localhost:4444/wd/hub/";
    ChromeOptions capabilitiesC = new ChromeOptions();
    driverC = new RemoteWebDriver(new URL(nodeUrl), capabilitiesC);
    
    FirefoxOptions capabilitiesF = new FirefoxOptions();
    driverF = new RemoteWebDriver(new URL(nodeUrl), capabilitiesF);
    
    OperaOptions capabilitiesO = new OperaOptions();
    driverO = new RemoteWebDriver(new URL(nodeUrl), capabilitiesO);

  }

  @Test
  public void simpleTest() {
    driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    driverC.findElement(By.cssSelector(".action-icon-telephone")).click();
    
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Assert.assertTrue(driverC.findElement(By.linkText("900 12 21 12")).isDisplayed(),
        "La pantalla es incorrecta");

    driverC.findElement(By.cssSelector(".ion-page > .ion-color")).click();

  }
  
  @Test
  public void simpleTest3() {
    driverO.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    driverO.findElement(By.cssSelector(".action-icon-telephone")).click();
    
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Assert.assertTrue(driverO.findElement(By.linkText("900 12 21 12")).isDisplayed(),
        "La pantalla es incorrecta");

    driverO.findElement(By.cssSelector(".ion-page > .ion-color")).click();
    

  }
  
  @Test
  public void comprobarCoronavirusLink() {
    driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");

    FuncionesSinAutenticar.comprobarEnlaceCoronavirus(driverC);
  }
  
  @Test
  public void comprobarCoronavirusLink2() {
    driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    FuncionesSinAutenticar.comprobarEnlaceCoronavirus(driverF);
  }
  
  @Test
  public void comprobarCoronavirusLink3() {
    driverO.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    FuncionesSinAutenticar.comprobarEnlaceCoronavirus(driverO);
  }
  
  
  @Test
  public void simpleTestF() {
    driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    driverF.findElement(By.cssSelector(".action-icon-telephone")).click();
    
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Assert.assertTrue(driverF.findElement(By.linkText("900 12 21 12")).isDisplayed(),
        "La pantalla es incorrecta");

    driverF.findElement(By.cssSelector(".ion-page > .ion-color")).click();

  }

  @AfterTest
  public void cerrar() {
    driverC.quit();
    driverF.quit();
    driverO.quit();
  }

}
