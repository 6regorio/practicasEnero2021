package privado;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MiPerfilPrivadoLogOut {

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
	public void cerrarSesion() {
		driver.findElement(By.cssSelector("div > .button-clear")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(driver.findElement(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]")).isDisplayed());
	}
}
