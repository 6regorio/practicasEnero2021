package docker;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

/**
 * Conjunto de métodos y pruebas que chequean la parte pública de la aplicación web. Se tiene la
 * opción de probar la búsqueda de farmacias con un DataProvider para ejecutar distintas pruebas en
 * la misma ejecución.
 */
public class FuncionesSinAutenticar {
  /**
   * Será el controlador de Chrome
   */
  public static WebDriver driverC;
  /**
   * Será el controlador de Firefox
   */
  public static WebDriver driverF;

  /**
   * Comprueba el popup del teléfono en la página principal
   * 
   * @param drivers
   */
  public static void comprobarClickTelefono(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.cssSelector(".action-icon-telephone")).click();
        Assert.assertTrue(currentDriver.findElement(By.linkText("900 12 21 12")).isDisplayed(),
            "La pantalla es incorrecta");
        currentDriver.findElement(By.cssSelector(".ion-page > .ion-color")).click();
      }
    }
  }

  /**
   * Comprueba el enlace del coronavirus en la página principal
   * 
   * @param drivers
   */
  public static void comprobarEnlaceCoronavirus(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        String handleVentana1 = currentDriver.getWindowHandle();
        currentDriver.findElement(By.cssSelector("a .action-icon")).click();


//        try {
//          Thread.sleep(500);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
        

      //  WebDriverWait wait = new WebDriverWait(currentDriver, 30);
        By btnSi = By.xpath("//span[contains(.,'Sí')]");
    //    wait.until(ExpectedConditions.invisibilityOfElementLocated(btnSi));
        currentDriver.findElement(btnSi).click();

       // currentDriver.findElement(By.xpath("//span[contains(.,'Sí')]")).click();


        Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
        listadoHanleVentanas.remove(handleVentana1);
        String handleVentana2 = listadoHanleVentanas.iterator().next();
        currentDriver.switchTo().window(handleVentana1);
        currentDriver.close();
        currentDriver.switchTo().window(handleVentana2);
        Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(),
            "No estás en la pestaña adecuada");
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        Assert.assertTrue(currentDriver
            .findElement(By.xpath("//span[contains(.,'Asistencia COVID-19')]")).isDisplayed());
      }
    }
  }

  /**
   * Comprueba pulsar más y entrar en privacidad
   * 
   * @param drivers
   */
  public static void masPrivacidad(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(2)")).click();
        Assert.assertEquals(
            currentDriver.findElement(By.xpath("//h1[contains(.,'Privacidad')]")).isDisplayed(),
            true);
      }
    }
  }

  /**
   * Comprueba pulsar más y entrar en ayuda
   * 
   * @param drivers
   */
  public static void masAyuda(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(3)")).click();
        Assert.assertTrue(
            currentDriver.findElement(By.xpath("//ion-title[contains(.,'Ayuda')]")).isDisplayed());
      }
    }
  }

  /**
   * Comprueba pulsar más y entrar en notificaciones
   * 
   * @param drivers
   */
  public static void mostarNotificaciones(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Notificaciones')]"))
            .click();
        Assert.assertEquals(
            currentDriver.findElement(By.xpath("//div[contains(.,'JCCM-COVID')]")).isDisplayed(),
            true);
      }
    }
  }

  /**
   * Comprueba pulsar más y entrar en contacto
   * 
   * @param drivers
   */
  public static void masContacto(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        String handleVentana1 = currentDriver.getWindowHandle();
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        currentDriver.findElement(By.xpath("//ion-segment-button[@value='reclamaciones']")).click();
        Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
        listadoHanleVentanas.remove(handleVentana1);
        String handleVentana2 = listadoHanleVentanas.iterator().next();
        currentDriver.switchTo().window(handleVentana1);
        currentDriver.close();
        currentDriver.switchTo().window(handleVentana2);
        Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(),
            "No estás en la pestaña adecuada");
        Assert.assertTrue(currentDriver
            .findElement(By.xpath("//h1[contains(.,'Reclamaciones centros sanitarios')]"))
            .isDisplayed());
      }
    }
  }

  /**
   * Comprueba entrar en cita previa de atención primaria
   * 
   * @param drivers
   */
  public static void paginaInicioCitaPreviaAtencionPrimaria(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
        currentDriver.findElement(By.xpath("//span[contains(.,'PEDIR CITA')]")).click();
        currentDriver.findElement(By.xpath("//span[contains(.,'PRIMARIA')]")).click();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
        }
        currentDriver.switchTo().frame(0);
        WebElement imagentarjeta = currentDriver.findElement(By.id("tarjetaportada"));
        Assert.assertTrue(imagentarjeta.isDisplayed());
      }
    }
  }

  /**
   * Comprueba entrar en cita previa de atención primaria
   * 
   * @param drivers
   */
  public static void paginaInicioCitaPreviaAtencionHospitalaria(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
        currentDriver.findElement(By.xpath("//span[contains(.,'PEDIR CITA')]")).click();
        currentDriver.findElement(By.xpath("//span[contains(.,'HOSPITALARIA')]")).click();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
        }
        currentDriver.switchTo().frame(0);
        Assert.assertEquals(currentDriver.findElement(By.id("ot1")).getText(),
            "Bienvenido a Citación Especializada");
      }
    }
  }

  /**
   * Busca una farmacia con los datos que se le suministran
   * 
   * @param provincia
   * @param pueblo
   * @param year
   * @param mes
   * @param dia
   * @param drivers - navegador
   */
  public static void encuentraFarmaciaParametros(String provincia, String pueblo, String year,
      String mes, String dia, WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
        try {
          currentDriver.findElement(By.xpath("//span[contains(.,'ENCONTRAR')]")).click();

          currentDriver.findElement(By.xpath("//ion-item[contains(.,'Provincia')]")).click();
          currentDriver.findElement(By.xpath("//button[contains(.,'" + provincia + "')]")).click();
          currentDriver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();

          currentDriver.findElement(By.xpath("//ion-item[contains(.,'Localidad')]")).click();
          currentDriver.findElement(By.xpath("//button[contains(.,'" + pueblo + "')]")).click();
          currentDriver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();

          currentDriver.findElement(By.xpath("//ion-item[contains(.,'Fecha')]")).click();
          currentDriver.findElement(By.cssSelector("span.mat-button-wrapper")).click();
          currentDriver.findElement(By.cssSelector("td[aria-label='" + year + "']")).click();
          currentDriver.findElement(By.xpath("//td[contains(.,'" + mes + "')]")).click();
          currentDriver.findElement(By.xpath("//td[contains(.,'" + dia + "')]")).click();

          currentDriver.findElement(By.xpath("//ion-button[contains(.,'Farmacias')]")).click();

          // Esperar a que cargue
          WebDriverWait wait = new WebDriverWait(currentDriver, 30);
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3")));
          List<WebElement> listaFarmacias = currentDriver.findElements(By.cssSelector("h3"));
          System.out.println("Listado de farmacias el " + dia + "/" + mes + "/" + year);
          listaFarmacias.forEach(o -> System.out.println(o.getText()));

          if (pueblo.equals("ALARCON")) {
            Assert.assertEquals("M", listaFarmacias.get(0).getText().substring(0, 1));

            String handleVentana1 = currentDriver.getWindowHandle();
            System.out.println(handleVentana1);

            List<WebElement> listaFarmaciasMapa =
                currentDriver.findElements(By.cssSelector("ion-item a.text-end"));
            listaFarmaciasMapa.get(0).click();

            Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
            listadoHanleVentanas.remove(handleVentana1);
            String handleVentana2 = listadoHanleVentanas.iterator().next();
            currentDriver.switchTo().window(handleVentana2);
            currentDriver.close();
            currentDriver.switchTo().window(handleVentana1);
          }
        } catch (ElementNotInteractableException e) {
          System.out.println("No se hizo click");
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * DataProvider para la función encuentraFarmaciaParametros
   * 
   * @return
   */
  @DataProvider(name = "EncoFarmProvider")
  public Object[][] getData() {
    // String provincia, String pueblo, String year, String mes, String dia
    Object[][] data = {{"CUENCA", "ALARCON", "2020", "DIC", "30"},
        {"ALBACETE", "CILANCO", "2020", "FEB", "16"},
        {"CIUDAD REAL", "ALAMILLO", "2019", "ENE", "11"}, {"TOLEDO", "ACECA", "2018", "ABR", "25"}};
    return data;
  }
}
