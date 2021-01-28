package old_probando.publico;


import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class TestHilosClaseThread extends Thread {

	WebDriver currentDriver;

	public TestHilosClaseThread(WebDriver currentDriver) {
		this.currentDriver = currentDriver;
	}
	
	@Override
	public void run() {
      currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
      System.out.println("Hola");
      try {
        System.out.println("Esperando 200ms antes");
        Thread.sleep(200);
        System.out.println("Esperando 200ms depués");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(2)")).click();
        System.out.println("Hola2");
      currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");      
      String handleVentana1 = currentDriver.getWindowHandle();
      currentDriver.findElement(By.cssSelector("a .action-icon")).click();
      currentDriver.findElement(By.xpath("//span[contains(.,'Sí')]")).click();
      Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
      listadoHanleVentanas.remove(handleVentana1);
      String handleVentana2 = listadoHanleVentanas.iterator().next();
      currentDriver.switchTo().window(handleVentana1);
      currentDriver.close();
      currentDriver.switchTo().window(handleVentana2);
      Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(),
          "No estás en la pestaña adecuada");
      Assert.assertTrue(currentDriver
          .findElement(By.xpath("//span[contains(.,'Asistencia COVID-19')]")).isDisplayed());           
      currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
      currentDriver.findElement(By.xpath("//span[contains(.,'PEDIR CITA')]")).click();
      currentDriver.findElement(By.xpath("//span[contains(.,'PRIMARIA')]")).click();
      currentDriver.switchTo().frame(0);
      WebElement imagentarjeta = currentDriver.findElement(By.id("tarjetaportada"));
      Assert.assertTrue(imagentarjeta.isDisplayed());
	}
}
