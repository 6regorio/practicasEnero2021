package privado;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MiPerfilPrivadoLogOut {

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
	public void logoutDesdePrivado() {
		driver.findElement(By.cssSelector("div > .button-clear")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]")).isDisplayed());
	}
}
