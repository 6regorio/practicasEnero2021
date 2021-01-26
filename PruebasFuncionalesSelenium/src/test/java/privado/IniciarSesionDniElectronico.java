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
	public static String baseURL;
	public static String dni;
	public static String password;
	public static String navegador;
	public static String cerficadoOClavePermanente;

	public static void iniciarSesionPreguntandoDatos() {
		cerficadoOClavePermanente = JOptionPane
				.showInputDialog("¿Qué certificado desea usar? [DNIe(d) o Clave premanente(c)]");
		if (cerficadoOClavePermanente.toLowerCase().startsWith("c")) {
			dni = JOptionPane.showInputDialog("Escriba su DNI (12345678Z)");
			password = JOptionPane.showInputDialog("Escriba la clave permanente");
			navegador = JOptionPane.showInputDialog("¿Qué navegador desea usar? [Firefox(f) o Chrome(c)]");
		} else {
			navegador = "Chrome";
		}
		if (navegador.toLowerCase().startsWith("c")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driver = new ChromeDriver();
		} else {
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@BeforeSuite
	public void cargaPropiedades() {
		iniciarSesionPreguntandoDatos();
	}

	@Test
	public void realizarAutenticacion() throws InterruptedException {
		iniciarSesionConAssertsIncluidos(driver, dni, password);
		Reporter.log(cerficadoOClavePermanente.toLowerCase().startsWith("c")
				? "Se ha elegido la autenticación mediante Clave premanente. "
				: "Se ha elegido la autenticación mediante DNI electrónico. ");
		Reporter.log(navegador.toLowerCase().startsWith("c") ? "Se usará el navegador Chrome"
				: "Se usará el navegador Firefox");
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

		if (cerficadoOClavePermanente.toLowerCase().startsWith("c")) {
			// Entrar con clave permanente
			List<WebElement> botonesAcceder = d.findElements(By.partialLinkText("Acce"));
			botonesAcceder.get(1).click();
			d.findElement(By.cssSelector("input#id_owner")).sendKeys(dni);
			d.findElement(By.cssSelector("input#pin")).sendKeys(password);
			d.findElement(By.cssSelector("input#pin")).submit();
		} else {
			// Entrar con DNI electrónico
			d.findElement(By.partialLinkText("Acce")).click();
		}

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
