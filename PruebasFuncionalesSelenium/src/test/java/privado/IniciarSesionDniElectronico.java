package privado;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class IniciarSesionDniElectronico {

	public static WebDriver driver;
	private String baseURL;
	private String dni;
	private String password;
	private String navegador;

	@BeforeSuite
	public void cargaPropiedades() {
		this.dni = JOptionPane.showInputDialog("Escriba su DNI (12345678Z)");
		this.password = JOptionPane.showInputDialog("Escriba la clave permanente");
		this.navegador = JOptionPane.showInputDialog("¿Qué navegador desea usar? [Firefox(f) o Chrome(c)]");
		if (navegador.toLowerCase().startsWith("c")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			Reporter.log("Se usará el navegador Chrome");
			driver = new ChromeDriver();
		} else {
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			Reporter.log("Se usará el navegador Firefox");
			driver = new FirefoxDriver();
		}
		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@Test
	public void identificarmeVistaEscritorio() throws InterruptedException {
		iniciarSesionConAssertsIncluidos(driver, dni, password);
	}

	public void iniciarSesionConAssertsIncluidos(WebDriver d, String dni, String password) {
		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
		d.get(baseURL);
		d.manage().window().maximize();

		List<WebElement> botonesIdentificarme = d.findElements(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]"));

		if (botonesIdentificarme.get(1).isDisplayed()) {
			botonesIdentificarme.get(1).click();
			System.out.println("Es pantalla estrecha");
		} else {
			botonesIdentificarme.get(0).click();
			System.out.println("Es pantalla ancha");
		}

		List<WebElement> botonesAceptar = d.findElements(By.xpath("//ion-button[contains(.,'Aceptar')]"));
		botonesAceptar.get(0).click();

		// Entrar con DNI electrónico
		// d.findElement(By.partialLinkText("Acce")).click();

		// Entrar con clave permanente
		List<WebElement> botonesAcceder = d.findElements(By.partialLinkText("Acce"));
		botonesAcceder.get(1).click();

		d.findElement(By.cssSelector("input#id_owner")).sendKeys(dni);
		d.findElement(By.cssSelector("input#pin")).sendKeys(password);
		d.findElement(By.cssSelector("input#pin")).submit();

		// Ahora salta un aviso de navegación privada que tengo que quitar
		/*
		 * d.findElement(By.cssSelector("button#details-button")).click();
		 * d.findElement(By.
		 * partialLinkText("Proceed to dcentricapigatewaypre.sescam.jclm.es (unsafe)")).
		 * click();
		 */

		// Error de CIP inválido
		// Assert.assertTrue(d.findElement(By.id("alert-1-hdr")).isDisplayed());
		// d.findElement(By.xpath("//span[contains(.,'Acep')]")).click();
		/*
		 * // Comprobar que aparece el nombre GREGORIO
		 * Assert.assertTrue(driver.findElement(By.xpath(
		 * "//ion-button[contains(.,'GREGORIO')]")).isDisplayed()); // Comprobar que
		 * aparece el botón de apagar
		 * Assert.assertTrue(driver.findElement(By.cssSelector("div > .button-clear")).
		 * isDisplayed());
		 */
		boolean repetir = true;
		do {
			try {
				Thread.sleep(1000);
				repetir = !driver.findElement(By.cssSelector("div > .button-clear")).isDisplayed()
						|| !driver.findElement(By.xpath("//ion-button[contains(.,'GREGORIO')]")).isDisplayed();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				repetir = true;
				System.out.println("Todavía no ha aparecido");
			}
		} while (repetir);

		// Comprobar que no aparece el candado
		Assert.assertFalse(d.findElement(By.cssSelector("ion-icon[name='lock-closed']")).isDisplayed());
	}

	@AfterSuite
	void tearDoown() {
		driver.close();
	}

}
