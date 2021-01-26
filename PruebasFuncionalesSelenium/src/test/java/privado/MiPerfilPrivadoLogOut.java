package privado;

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

public class MiPerfilPrivadoLogOut {

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
	public void logoutDesdePrivado() {
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
