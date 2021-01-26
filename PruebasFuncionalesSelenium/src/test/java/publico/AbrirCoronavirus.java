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

public class AbrirCoronavirus {

	private WebDriver driver;
	By Asistente = By.cssSelector("a .action-icon");
	By AsistenciaCovid = By.xpath("//span[contains(.,'Sí')]");
	By Asistentetetelefono = By.cssSelector(".action-icon-telephone");
	By telefonos = By.linkText("900 12 21 12");
	By telfencontrado = By.xpath("//span[contains(.,'Teléfonos gratuitos de atencion')]");
	By volver = By.cssSelector(".ion-page > .ion-color");
	By Encontrar = By.xpath("//span[contains(.,'ENCONTRAR')]");

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
	public void comprobarEnlaceCoronavirus() throws InterruptedException {
		String handleVentana1 = driver.getWindowHandle();
		driver.findElement(Asistente).click();
		driver.findElement(AsistenciaCovid).click();
		Set<String> listadoHanleVentanas = driver.getWindowHandles();
		listadoHanleVentanas.remove(handleVentana1);
		String handleVentana2 = listadoHanleVentanas.iterator().next(); 
		driver.switchTo().window(handleVentana1);
		driver.close(); 
		driver.switchTo().window(handleVentana2);
		Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(.,'Asistencia COVID-19')]")).isDisplayed());
	}
}
