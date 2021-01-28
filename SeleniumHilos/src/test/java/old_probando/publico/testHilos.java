package old_probando.publico;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import old_probando.privado.AutenticacionRequerida;
import privado.AutenticacionRequeridaChrome;

public class testHilos {

  private WebDriver driverC;
  private WebDriver driverF;

  @BeforeClass
  public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
    driverF = AutenticacionRequerida.driverF;
    driverC = AutenticacionRequerida.driverC;
    // Código usado sólo cuando hago alguno de los test de esta clase por separado
    if (driverC == null && driverF == null) {
      System.setProperty("webdriver.chrome.driver",
          "./src/test/resources/chromedriver/chromedriver.exe");
      driverC = new ChromeDriver();
      driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverC.manage().window().maximize();
      driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
      System.setProperty("webdriver.gecko.driver",
          "./src/test/resources/firefoxdriver/geckodriver.exe");
      driverF = new FirefoxDriver();
      driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverF.manage().window().maximize();
      driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    }
  }

  @BeforeMethod
  public void cargarPaginaInicial() {
    if (driverF != null) {
      driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    }
    if (driverC != null) {
      driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    }
  }


  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void masPrivacidad() {

    Thread miHilo = new TestHilosClaseThread(driverC);
    miHilo.setName("Hilo" + 1);
    System.out.println("Nombre de hilo: " + miHilo.getName());
    miHilo.start();
    
    Thread miHilo2 = new TestHilosClaseThread(driverF);
    miHilo2.setName("Hilo" + 2);
    System.out.println("Nombre de hilo: " + miHilo2.getName());
    miHilo2.start();
  
  
  
  }
  
  @Test
  public void masPrivacidadDelSource() {
    masPrivacidad(driverC,driverF);
  }

  public void masPrivacidad(WebDriver... drivers) {
    for (int i = 0; i < 2; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        saberSiEsChromeOFirefox(i);
        
        //Creo el hilo que extiende de Thread
        //TestHilosClaseThread miHilo = new TestHilosClaseThread(currentDriver);
        Thread miHilo = new TestHilosClaseThread(currentDriver);
        miHilo.setName("Hilo" + i);
        System.out.println("Nombre de hilo: " + miHilo.getName());
        miHilo.start();
        
//        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
//        try {
//          Thread.sleep(200);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//        currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(2)")).click();
//        Assert.assertEquals(
//            currentDriver.findElement(By.xpath("//h1[contains(.,'Privacidad')]")).isDisplayed(),
//            true);
      }
    }
  }

  public static void saberSiEsChromeOFirefox(int i) {
    if (i == 0) {
      Reporter.log("Chrome ");
    } else {
      Reporter.log("Firefox ");
    }
  }
}
