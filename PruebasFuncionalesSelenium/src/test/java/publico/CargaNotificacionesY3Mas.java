package publico;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import privado.InicioSesionRequerido;

public class CargaNotificacionesY3Mas {

	WebDriver driver;

	@BeforeClass
	public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
		driver = InicioSesionRequerido.driver;
		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
	}

	@BeforeMethod
	public void cargarPaginaInicial() {
		driver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
	}

	@Test
	public void masPrivacidad() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(2)")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//h1[contains(.,'Privacidad')]")).isDisplayed(), true);
	}

	@Test
	public void masAyuda() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(3)")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-title[contains(.,'Ayuda')]")).isDisplayed());
	}

	@Test
	public void mostarNotificaciones() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Notificaciones')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(.,'JCCM-COVID')]")).isDisplayed(), true);
	}

	@Test
	public void masContacto() {
		String handleVentana1 = driver.getWindowHandle();
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//ion-segment-button[@value='reclamaciones']")).click();
		Set<String> listadoHanleVentanas = driver.getWindowHandles();
		listadoHanleVentanas.remove(handleVentana1);
		String handleVentana2 = listadoHanleVentanas.iterator().next();
		driver.switchTo().window(handleVentana1);
		driver.close();
		driver.switchTo().window(handleVentana2);
		Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");
		Assert.assertTrue(
				driver.findElement(By.xpath("//h1[contains(.,'Reclamaciones centros sanitarios')]")).isDisplayed());
	}

}
