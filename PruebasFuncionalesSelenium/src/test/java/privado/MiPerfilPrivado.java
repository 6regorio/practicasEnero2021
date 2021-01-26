package privado;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MiPerfilPrivado {

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
	public void miPerfilMisDatosDniEscrito	() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.cssSelector(".ion-page:nth-child(2) .md:nth-child(4) .icon-rotate-collapsed")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//ion-label[contains(.,'DNI: 74')]")).isDisplayed(), true);
	}

	@Test
	public void miPerfilMisProfesionalesCentroManzanares() {
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
