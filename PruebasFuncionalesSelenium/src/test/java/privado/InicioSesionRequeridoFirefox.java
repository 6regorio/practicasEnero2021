package privado;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InicioSesionRequeridoFirefox {

	private WebDriver driver;
	private String baseURL;
	private String dni;
	private String password;
	private String navegador;
	private String cerficadoOClavePermanente;
	private boolean ambos;

	@BeforeClass
	public void cargaPropiedadesDeFirefoxSiEsParalelo() {
		ambos = InicioSesionRequerido.ambos;
		if (ambos) {
			dni = InicioSesionRequerido.dni;
			password = InicioSesionRequerido.password;
			navegador = "firefox";
			cerficadoOClavePermanente = InicioSesionRequerido.cerficadoOClavePermanente;
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driver = new FirefoxDriver();
			baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(baseURL);
		}
	}

	@Test(priority = 1, groups = "Inicia sesión")
	public void realizarAutenticacionP() throws InterruptedException {
		iniciarSesionConAssertsIncluidos(driver, dni, password);
		Reporter.log(cerficadoOClavePermanente.toLowerCase().startsWith("c")
				? "Se ha elegido la autenticación mediante Clave premanente. "
				: "Se ha elegido la autenticación mediante DNI electrónico. ");
		Reporter.log(navegador.toLowerCase().startsWith("c") ? "Se usará el navegador Chrome"
				: "Se usará el navegador Firefox");
	}

	@Test(priority = 2, groups = "MiPerfilPrivado")
	public void miPerfilMisDatosDniEscrito() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.xpath("//ion-label[contains(.,'MIS DATOS')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//ion-label[contains(.,'DNI: 74')]")).isDisplayed(), true);
	}

	@Test(priority = 3, groups = "MiPerfilPrivado")
	public void miPerfilMisProfesionalesCentroManzanares() {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
		driver.findElement(By.xpath("//ion-label[contains(.,'MIS PROFESIONALES')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-label[contains(.,'C.S. MANZANARES')]")).isDisplayed());
	}

	@Test(priority = 3, groups = "MiPerfilPrivado")
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

	@Test(priority = 4, groups = "CarpetaSalud")
	public void comprobarCarpetaDeSaludAlergias() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'ALERGIAS')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'METALES')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacionP")
	public void comprobarCarpetaDeSaludInformes() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'INFORMES')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-title[contains(.,'Informes')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacionP")
	public void comprobarCarpetaDeSaludMedicacion() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'MEDICACION')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//ion-title[contains(.,'Medicación')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacionP")
	public void comprobarCarpetaDeSaludMisCitas() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'CITAS')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'DIGESTIVO')]")).isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacionP")
	public void comprobarCarpetaDeSaludVacunas() throws InterruptedException {
		driver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]")).click();
		driver.findElement(By.xpath("//span[contains(.,'VACUNAS')]")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//ion-col[contains(.,'No existen vacunas registradas en el sistema')]"))
						.isDisplayed());
	}

	@Test(priority = 4, groups = "CarpetaSalud", dependsOnMethods = "realizarAutenticacionP")
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

	@Test(priority = 5, groups = "Cerrando sesión", dependsOnMethods = "realizarAutenticacionP")
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

		// Entrar con clave permanente
		List<WebElement> botonesAcceder = d.findElements(By.partialLinkText("Acce"));
		botonesAcceder.get(1).click();
		d.findElement(By.cssSelector("input#id_owner")).sendKeys(dni);
		d.findElement(By.cssSelector("input#pin")).sendKeys(password);
		d.findElement(By.cssSelector("input#pin")).submit();

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
}
