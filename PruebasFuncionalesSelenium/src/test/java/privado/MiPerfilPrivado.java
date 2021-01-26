package privado;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MiPerfilPrivado {

	WebDriver driver;

	@BeforeClass
	public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
		driver = IniciarSesionDniElectronico.driver;
		if (driver == null) {
			String dni = JOptionPane.showInputDialog("Escriba su DNI (12345678Z)");
			String password = JOptionPane.showInputDialog("Escriba la clave permanente");
			String navegador = JOptionPane.showInputDialog("¿Qué navegador desea usar? [Firefox(f) o Chrome(c)]");
			if (navegador.toLowerCase().startsWith("c")) {
				System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
				Reporter.log("Se usará el navegador Chrome");
				driver = new ChromeDriver();
			} else {
				System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
				Reporter.log("Se usará el navegador Firefox");
				driver = new FirefoxDriver();
			}
			String baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(baseURL);
			new IniciarSesionDniElectronico().iniciarSesionConAssertsIncluidos(driver, dni, password);
		}
	}

	@Test
	public void testeoDniEscrito() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.cssSelector(".ion-page:nth-child(2) .md:nth-child(4) .icon-rotate-collapsed")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//ion-label[contains(.,'DNI: 74')]")).isDisplayed(), true);
	}

	@Test
	public void testeoMisProfesionales() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.cssSelector(".ion-page:nth-child(2) .md:nth-child(5) .icon-rotate-collapsed")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-label[contains(.,'C.S. MANZANARES')]")).isDisplayed());
	}

	@Test
	public void historiaClinicaSNS() {
		String handleVentana1 = driver.getWindowHandle();
		driver.findElement(By.xpath("//ion-segment-button[@value='hcdsns']")).click();
		Set<String> listadoHanleVentanas = driver.getWindowHandles();
		listadoHanleVentanas.remove(handleVentana1);
		String handleVentana2 = listadoHanleVentanas.iterator().next();
		driver.switchTo().window(handleVentana2);
		Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");
		String titulo = driver.findElement(By.id("page-title")).getText();
		Assert.assertTrue(titulo.substring(0, 8).equals("Historia") ? true : false);
		driver.close();
		driver.switchTo().window(handleVentana1);
	}
}
