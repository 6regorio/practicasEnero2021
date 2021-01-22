package zz210120;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ComprobarCarpetaSalud6Links {

	WebDriver driver;

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
	public void comprobarCarpetaDeSaludAlergias() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'ALERGIAS')]")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//ion-col[contains(.,'No existen alergias registradas en el sistema')]"))
						.isDisplayed());
	}

	@Test
	public void comprobarCarpetaDeSaludInformes() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'INFORMES')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-title[contains(.,'Informes')]")).isDisplayed());
	}

	@Test
	public void comprobarCarpetaDeSaludMedicacion() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'MEDICACION')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-title[contains(.,'Medicación')]")).isDisplayed());
	}

	@Test
	public void comprobarCarpetaDeSaludMisCitas() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'CITAS')]")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//ion-col[contains(.,'No existen citas registradas en el sistema')]"))
						.isDisplayed());
	}

	@Test
	public void comprobarCarpetaDeSaludVacunas() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'VACUNAS')]")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//ion-col[contains(.,'No existen vacunas registradas en el sistema')]"))
						.isDisplayed());
	}

	@Test
	public void comprobarCarpetaDeSaludListaEspera() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		String handleVentana1 = driver.getWindowHandle();
		driver.findElement(By.xpath("//span[contains(.,'ESPERA')]")).click();
		Set<String> listadoHanleVentanas = driver.getWindowHandles();
		listadoHanleVentanas.remove(handleVentana1);
		String handleVentana2 = listadoHanleVentanas.iterator().next();
		driver.switchTo().window(handleVentana2);
		Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(.,'MANTENIMIENTO')]")).isDisplayed());
		driver.close();
		driver.switchTo().window(handleVentana1);
	}
}
