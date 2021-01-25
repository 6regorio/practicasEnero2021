package publico;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import privado.IniciarSesionDniElectronico;

public class PedirCita2Links {

	WebDriver driver;

	By inicioLocator = By.xpath("//span[contains(.,'PEDIR CITA')]");
	By citaLocatorPrimaria = By.xpath("//span[contains(.,'PRIMARIA')]");
	By citaLocatorHospitalaria = By.xpath("//span[contains(.,'HOSPITALARIA')]");

	@BeforeClass
	public void cargaPropiedades() {
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
	public void paginaInicioCitaPreviaAtencionPrimaria() {
		driver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		driver.findElement(inicioLocator).click();
		driver.findElement(citaLocatorPrimaria).click();
		driver.switchTo().frame(0);
		WebElement imagentarjeta = driver.findElement(By.id("tarjetaportada"));
		Assert.assertTrue(imagentarjeta.isDisplayed());
	}

	@Test
	public void paginaInicioCitaPreviaAtencionHospitalaria() {
		driver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		driver.findElement(inicioLocator).click();
		driver.findElement(citaLocatorHospitalaria).click();
		driver.switchTo().frame(0);
		Assert.assertEquals(driver.findElement(By.id("ot1")).getText(), "Bienvenido a Citación Especializada");
	}
}
