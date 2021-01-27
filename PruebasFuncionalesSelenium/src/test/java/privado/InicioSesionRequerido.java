package privado;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class InicioSesionRequerido {

	public static WebDriver driverC;
	public static WebDriver driverF;

	// TODO borrar o hacer privados cuando se termine la modificación
	public static WebDriver driver;
	public static String baseURL;
	public static String dni;
	public static String password;
	public static String navegador;
	public static String cerficadoOClavePermanente;
	public static boolean ambos;

	@BeforeSuite
	public void cargaPropiedades() {
		iniciarSesionPreguntandoDatos();
	}

	@Test(priority = 1, groups = "Inicia sesión")
	public void realizarAutenticacion() throws InterruptedException {
		iniciarSesionConAssertsIncluidos(driver, dni, password);
		Reporter.log(cerficadoOClavePermanente.toLowerCase().startsWith("c")
				? "Se ha elegido la autenticación mediante Clave premanente. "
				: "Se ha elegido la autenticación mediante DNI electrónico. ");
		Reporter.log(navegador.toLowerCase().startsWith("c") ? "Se usará el navegador Chrome"
				: "Se usará el navegador Firefox");
	}

	@Test(priority = 2, groups = "MiPerfilPrivado", dependsOnMethods = "realizarAutenticacion")
	public void miPerfilMisDatosDniEscrito() {
		miPerfilMisDatosDniEscrito(driver);
	}

	public void miPerfilMisDatosDniEscrito(WebDriver driver) {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.xpath("//ion-label[contains(.,'MIS DATOS')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//ion-label[contains(.,'DNI: 74')]")).isDisplayed(), true);
	}

	@Test(priority = 3, groups = "MiPerfilPrivado", dependsOnMethods = "realizarAutenticacion")
	public void miPerfilMisProfesionalesCentroManzanares() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.xpath("//ion-label[contains(.,'MIS PROFESIONALES')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-label[contains(.,'C.S. MANZANARES')]")).isDisplayed());
	}

	@Test(priority = 3, groups = "MiPerfilPrivado", dependsOnMethods = "realizarAutenticacion")
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

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludAlergias() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'ALERGIAS')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'METALES')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludInformes() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'INFORMES')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-title[contains(.,'Informes')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludMedicacion() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'MEDICACION')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-title[contains(.,'Medicación')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludMisCitas() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'CITAS')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'DIGESTIVO')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludVacunas() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'VACUNAS')]")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//ion-col[contains(.,'No existen vacunas registradas en el sistema')]"))
						.isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
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

	@Test(priority = 5, groups = "Cerrando sesión", dependsOnMethods = "realizarAutenticacion")
	public void cerrarSesion() {
		driver.findElement(By.cssSelector("div > .button-clear")).click();
		driver.findElement(By.xpath("//button[2]/span")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(driver.findElement(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]")).isDisplayed());
	}

	@AfterSuite
	void tearDoown() {
		driver.close();
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
				Thread.sleep(4000);
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

	public void iniciarSesionPreguntandoDatos() {

		preguntarNavegadorAUtilizar();

		if (navegador.equals("Firefox") || navegador.equals("Ambos")) {
			cerficadoOClavePermanente = "Clave permanente";
		} else {
			preguntarDniOClavePermanente();
		}

		System.out.println(cerficadoOClavePermanente);

		if (cerficadoOClavePermanente.toLowerCase().startsWith("c")) {
			pedirDniYPassword();
		}

		if (navegador.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (navegador.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driver = new ChromeDriver();
			ambos = true;
		}

		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	private void preguntarDniOClavePermanente() {
		String[] identificadores = { "DNI electrónico", "Clave permanente" };
		Object selected2 = JOptionPane.showInputDialog(null, "¿Qué tipo de identificación desea usar?", "Selection",
				JOptionPane.DEFAULT_OPTION, null, identificadores, "Clave permanente");
		if (selected2 != null) {
			cerficadoOClavePermanente = selected2.toString();
		} else {
			System.out.println("Cancelado por el usuario");
			System.exit(1);
		}
	}

	private void preguntarNavegadorAUtilizar() {
		String[] navegadores = { "Chrome", "Firefox", "Ambos" };
		Object selected = JOptionPane.showInputDialog(null, "¿Qué navegador desea usar?", "Selection",
				JOptionPane.DEFAULT_OPTION, null, navegadores, "Chrome");
		if (selected != null) {
			navegador = selected.toString();
		} else {
			System.out.println("Cancelado por el usuario");
			System.exit(1);
		}

		System.out.println(navegador);
		if (navegador.equals("Ambos")) {
			ambos = true;
		} else {
			ambos = false;
		}
	}

	private void pedirDniYPassword() {
		dni = JOptionPane.showInputDialog("Escriba su DNI (12345678Z)");
		JPasswordField pwd = new JPasswordField();
		JOptionPane.showConfirmDialog(null, pwd, "Escriba la clave permanente", JOptionPane.OK_CANCEL_OPTION);
		password = "";
		for (char caracter : pwd.getPassword()) {
			password += caracter;
		}
	}
}
