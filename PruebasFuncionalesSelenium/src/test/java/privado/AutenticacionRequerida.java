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

import publico.SinAutenticar;

public class AutenticacionRequerida {

	public static WebDriver driverC;
	public static WebDriver driverF;

	private String baseURL;
	public static String dni;
	public static String password;
	private String navegador;
	private String certificadoOClavePermanente;

	@BeforeSuite
	public void cargaPropiedades() {
		iniciarSesionPreguntandoDatos();
	}

	@Test(priority = 1, groups = "Inicia sesión")
	public void realizarAutenticacion() {
		realizarAutenticacion(driverC, driverF);
	}

	@Test(priority = 2, groups = "MiPerfilPrivado", dependsOnMethods = "realizarAutenticacion")
	public void miPerfilMisDatosDniEscrito() {
		miPerfilMisDatosDniEscrito(driverC, driverF);
	}

	@Test(priority = 3, groups = "MiPerfilPrivado", dependsOnMethods = "realizarAutenticacion")
	public void miPerfilMisProfesionalesCentroManzanares() {
		miPerfilMisProfesionalesCentroManzanares(driverC, driverF);
	}

	@Test(priority = 3, groups = "MiPerfilPrivado", dependsOnMethods = "realizarAutenticacion")
	public void historiaClinicaSNS() {
		historiaClinicaSNS(driverC, driverF);
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludAlergias() {
		comprobarCarpetaDeSaludAlergias(driverC, driverF);
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludInformes() {
		comprobarCarpetaDeSaludInformes(driverC, driverF);
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludMedicacion() {
		comprobarCarpetaDeSaludMedicacion(driverC, driverF);
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludMisCitas() {
		comprobarCarpetaDeSaludMisCitas(driverC, driverF);
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludVacunas() {
		comprobarCarpetaDeSaludVacunas(driverC, driverF);
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacion")
	public void comprobarCarpetaDeSaludListaEspera() {
		comprobarCarpetaDeSaludListaEspera(driverC, driverF);
	}

	@Test(priority = 5, groups = "Cerrando sesión", dependsOnMethods = "realizarAutenticacion")
	public void cerrarSesion() {
		cerrarSesion(driverC, driverF);
	}

	@AfterSuite
	void tearDoown() {
		if (driverF != null) {
			driverF.quit();
		}
		if (driverC != null) {
			driverC.quit();
		}
	}

	public void realizarAutenticacion(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				iniciarSesionConAssertsIncluidos(currentDriver, dni, password);
			}
		}
	}

	public void iniciarSesionConAssertsIncluidos(WebDriver d, String dni, String password) {
		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
		d.get(baseURL);
		d.manage().window().maximize();
		List<WebElement> botonesIdentificarme = d.findElements(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]"));
		botonesIdentificarme.get(0).click();
		List<WebElement> botonesAceptar = d.findElements(By.xpath("//ion-button[contains(.,'Aceptar')]"));
		botonesAceptar.get(0).click();
		if (certificadoOClavePermanente.toLowerCase().startsWith("c")) {
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
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(d.findElement(By.xpath("//ion-button[contains(.,'GREGORIO')]")).isDisplayed());
		// Comprobar que no aparece el candado
		Assert.assertFalse(d.findElement(By.cssSelector("ion-icon[name='lock-closed']")).isDisplayed());
	}

	public void iniciarSesionPreguntandoDatos() {
		preguntarNavegadorAUtilizar();
		
		if (navegador.equals("Firefox") || navegador.equals("Ambos")) {
			certificadoOClavePermanente = "Clave permanente";
		} else {
			preguntarDniOClavePermanente();
		}
		System.out.println(certificadoOClavePermanente);

		if (certificadoOClavePermanente.equals("Clave permanente")) {
			pedirDniYPassword();
		}

		if (navegador.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driverC = new ChromeDriver();
			driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverC.manage().window().maximize();
			driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		} else if (navegador.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driverF = new FirefoxDriver();
			driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverF.manage().window().maximize();
			driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		} else {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driverC = new ChromeDriver();
			driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverC.manage().window().maximize();
			driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driverF = new FirefoxDriver();
			driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverF.manage().window().maximize();
			driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		}
		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
	}

	private void preguntarDniOClavePermanente() {
		String[] identificadores = { "DNI electrónico", "Clave permanente" };
		Object selected2 = JOptionPane.showInputDialog(null, "¿Qué tipo de identificación desea usar?", "Selection",
				JOptionPane.DEFAULT_OPTION, null, identificadores, "Clave permanente");
		if (selected2 != null) {
			certificadoOClavePermanente = selected2.toString();
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

	public void miPerfilMisDatosDniEscrito(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
				currentDriver.findElement(By.xpath("//ion-label[contains(.,'MIS DATOS')]")).click();
				Assert.assertEquals(
						currentDriver.findElement(By.xpath("//ion-label[contains(.,'DNI: 74')]")).isDisplayed(), true);
			}
		}
	}

	public void miPerfilMisProfesionalesCentroManzanares(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
				currentDriver.findElement(By.xpath("//ion-label[contains(.,'MIS PROFESIONALES')]")).click();
				Assert.assertTrue(currentDriver.findElement(By.xpath("//ion-label[contains(.,'C.S. MANZANARES')]"))
						.isDisplayed());
			}
		}
	}

	public void historiaClinicaSNS(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				String handleVentana1 = currentDriver.getWindowHandle();
				currentDriver.findElement(By.xpath("//ion-segment-button[@value='hcdsns']")).click();
				Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
				listadoHanleVentanas.remove(handleVentana1);
				String handleVentana2 = listadoHanleVentanas.iterator().next();
				currentDriver.switchTo().window(handleVentana2);
				Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(), "No estás en la pestaña adecuada");
				String titulo = currentDriver.findElement(By.id("page-title")).getText();
				Assert.assertTrue(titulo.substring(0, 8).equals("Historia") ? true : false);
				currentDriver.close();
				currentDriver.switchTo().window(handleVentana1);
			}
		}
	}

	public void comprobarCarpetaDeSaludAlergias(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'ALERGIAS')]")).click();
				Assert.assertTrue(currentDriver.findElement(By.xpath("//h2[contains(.,'METALES')]")).isDisplayed());
			}
		}
	}

	public void comprobarCarpetaDeSaludInformes(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'INFORMES')]")).click();
				Assert.assertTrue(
						currentDriver.findElement(By.xpath("//ion-title[contains(.,'Informes')]")).isDisplayed());
			}
		}
	}

	public void comprobarCarpetaDeSaludMedicacion(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'MEDICACION')]")).click();
				Assert.assertTrue(
						currentDriver.findElement(By.xpath("//ion-title[contains(.,'Medicación')]")).isDisplayed());
			}
		}
	}

	public void comprobarCarpetaDeSaludMisCitas(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'CITAS')]")).click();
				Assert.assertTrue(currentDriver.findElement(By.xpath("//h2[contains(.,'DIGESTIVO')]")).isDisplayed());
			}
		}
	}

	public void comprobarCarpetaDeSaludVacunas(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'VACUNAS')]")).click();
				Assert.assertTrue(currentDriver
						.findElement(By.xpath("//ion-col[contains(.,'No existen vacunas registradas en el sistema')]"))
						.isDisplayed());
			}
		}
	}

	public void comprobarCarpetaDeSaludListaEspera(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
				String handleVentana1 = currentDriver.getWindowHandle();
				currentDriver.findElement(By.xpath("//span[contains(.,'ESPERA')]")).click();
				Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
				listadoHanleVentanas.remove(handleVentana1);
				String handleVentana2 = listadoHanleVentanas.iterator().next();
				currentDriver.switchTo().window(handleVentana2);
				Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(), "No estás en la pestaña adecuada");
				boolean ListaDeEspera = false;
				try {
					currentDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					if (currentDriver.findElement(By.xpath("//span[contains(.,'Lista de espera')]")).isDisplayed()) {
						ListaDeEspera = true;
					}
				} catch (NoSuchElementException e) {
					Reporter.log("No se ha cargado correctamente la página de lista de espera");
				}
				currentDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				currentDriver.close();
				currentDriver.switchTo().window(handleVentana1);
				Assert.assertTrue(ListaDeEspera);
			}
		}
	}

	public void cerrarSesion(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.cssSelector("div > .button-clear")).click();
				currentDriver.findElement(By.xpath("//button[2]/span")).click();
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Assert.assertTrue(
						currentDriver.findElement(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]")).isDisplayed());
			}
		}
	}

}
