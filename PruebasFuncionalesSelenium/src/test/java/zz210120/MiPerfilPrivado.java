package zz210120;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MiPerfilPrivado { 

	WebDriver driver;

	By dniLocator = By.xpath("//ion-label[contains(.,'DNI: 74')]");
	By misProfesionales = By.cssSelector(".itemExpanded > .ion-text-wrap");

	@BeforeClass
	public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
		driver = IniciarSesionDniElectronico.driver;
		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			new IniciarSesionDniElectronico().iniciarSesionConAssertsIncluidos(driver);
		}
	}

	@Test
	public void testeoDniEscrito() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.cssSelector(".ion-page:nth-child(2) .md:nth-child(4) .icon-rotate-collapsed")).click();
		Assert.assertEquals(driver.findElement(dniLocator).isDisplayed(), true); 
	}

	@Test
	public void testeoMisProfesionales() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.cssSelector(".ion-page:nth-child(2) .md:nth-child(5) .icon-rotate-collapsed")).click();
		Assert.assertTrue(driver.findElement(misProfesionales).isDisplayed());
	}

	@Test(enabled = false)
	public void logoutDesdePrivado() {
		// driver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		driver.findElement(By.cssSelector("div > .button-clear")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		Assert.fail();
		new IniciarSesionDniElectronico().iniciarSesionConAssertsIncluidos(driver);
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
		Assert.assertEquals(driver.getTitle(),
				"Historia Clínica Digital SNS | Servicio de Salud de Castilla-La Mancha");
		driver.close(); 
		driver.switchTo().window(handleVentana1); 
	}
}
