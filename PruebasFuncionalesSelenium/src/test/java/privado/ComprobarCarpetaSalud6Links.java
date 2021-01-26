package privado;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ComprobarCarpetaSalud6Links {

	WebDriver driver;

	@BeforeClass
	public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
		driver = IniciarSesionDniElectronico.driver;
		if (driver == null) {
			IniciarSesionDniElectronico iniciarSesionDniElectronico = new IniciarSesionDniElectronico();
			IniciarSesionDniElectronico.iniciarSesionPreguntandoDatos();
			iniciarSesionDniElectronico.iniciarSesionConAssertsIncluidos(IniciarSesionDniElectronico.driver,
					IniciarSesionDniElectronico.dni, IniciarSesionDniElectronico.password);
			driver = IniciarSesionDniElectronico.driver;
		}
	}

	@Test
	public void comprobarCarpetaDeSaludAlergias() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'ALERGIAS')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'METALES')]")).isDisplayed());
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
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'DIGESTIVO')]")).isDisplayed());
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
		boolean ListaDeEspera = false;
		try {
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			if (driver.findElement(By.xpath("//span[contains(.,'Lista de espera')]")).isDisplayed()) {
				ListaDeEspera = true;
			}
		} catch (NoSuchElementException e) {
			Reporter.log("No se ha cargado correctamente la página de lista de espera");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.close();
		driver.switchTo().window(handleVentana1);
		Assert.assertTrue(ListaDeEspera);
	}
}
