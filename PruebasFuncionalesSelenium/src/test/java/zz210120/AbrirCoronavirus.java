package zz210120;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

		Set<String> listadoHanleVentanas = driver.getWindowHandles(); // Descargo la lista de ventanas/pestañas abiertas
		listadoHanleVentanas.remove(handleVentana1); // elimino la que ya conocemos, la 1
		String handleVentana2 = listadoHanleVentanas.iterator().next(); // mediante el iterator cojo la segunda pagina y
																		// la meto en el String handleVentana2
		driver.switchTo().window(handleVentana1); // cambio a la ventana uno
		driver.close(); // cierro la ventana uno
		driver.switchTo().window(handleVentana2); // cambio/activo a la ventana dos para que trabaje con ella
		Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");

		String urlDeseada = "https://play.google.com/store/apps/details?id=es.gob.asistenciacovid19&hl=es_419";
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url, urlDeseada, "la dirección no es igual, es: " + url + " y se esperaba: " + urlDeseada);
		By Imagen = By.xpath(
				"//img[@src='https://play-lh.googleusercontent.com/3KYd6EDM09AnLiTTolx4ZCuGDQi6PWjvhuAmJKkbhQIv6SrqWPdISIVI792HH-JeiOI=s180-rw']");
		Assert.assertTrue(driver.findElement(Imagen).isDisplayed());
	}
}
