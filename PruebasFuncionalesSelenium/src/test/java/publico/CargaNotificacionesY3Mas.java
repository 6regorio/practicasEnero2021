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

import privado.IniciarSesionDniElectronico;

public class CargaNotificacionesY3Mas {

	WebDriver driver;

	By frameLocator = By.id("tarjetaportada");
	By politicaPrivacidad = By.xpath("//h1[contains(.,'Privacidad')]");
	By ayudaAssert = By.xpath("//ion-title[contains(.,'Ayuda')]");
	By contactoAssert = By.xpath("//h1[contains(.,'Reclamaciones centros sanitarios')]");

	@BeforeClass
	public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
		driver = IniciarSesionDniElectronico.driver;
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
		Assert.assertEquals(driver.findElement(politicaPrivacidad).isDisplayed(), true);
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
		Assert.assertTrue(driver.findElement(ayudaAssert).isDisplayed());
	}

	@Test
	public void mostarNotificaciones() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Notificaciones')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(.,'JCCM-COVID')]")).isDisplayed(), true);
	}

	@Test
	public void masContacto() {
		String handleVentana1 = driver.getWindowHandle(); // recupero la matricula de la ventana

		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//ion-segment-button[@value='reclamaciones']")).click();

		Set<String> listadoHanleVentanas = driver.getWindowHandles(); // Descargo la lista de ventanas/pestañas abiertas
		listadoHanleVentanas.remove(handleVentana1); // elimino la que ya conocemos, la 1
		String handleVentana2 = listadoHanleVentanas.iterator().next(); // mediante el iterator cojo la segunda pagina y
																		// la meto en el String handleVentana2
		driver.switchTo().window(handleVentana1); // cambio a la ventana uno
		driver.close(); // cierro la ventana uno
		driver.switchTo().window(handleVentana2); // cambio/activo a la ventana dos para que trabaje con ella
		Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");
		Assert.assertTrue(driver.findElement(contactoAssert).isDisplayed());
	}

}
