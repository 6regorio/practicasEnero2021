package publico;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PruebasSinIniciarSesion {

	private WebDriver driverC;
	private WebDriver driverF;

	@BeforeClass
	public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {

		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driverC = new ChromeDriver();
		driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driverC.manage().window().maximize();

		System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
		driverF = new FirefoxDriver();
		driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driverF.manage().window().maximize();

	}

	@BeforeMethod
	public void cargarPaginaInicial() {
		if (driverF != null) {
			driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		}
		if (driverC != null) {
			driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
		}
	}

	@Test
	public void comprobarClickTelefono() {
		comprobarClickTelefono(driverC, driverF);
	}

	@Test
	public void comprobarEnlaceCoronavirus() {
		comprobarEnlaceCoronavirus(driverC, driverF);
	}

	@Test
	public void masPrivacidad() {
		masPrivacidad(driverC, driverF);
	}

	@Test
	public void masContacto() {
		masContacto(driverC, driverF);
	}

	@Test
	public void masAyuda() {
		masAyuda(driverC, driverF);
	}

	@Test
	public void mostarNotificaciones() {
		mostarNotificaciones(driverC, driverF);
	}

	@Test
	public void paginaInicioCitaPreviaAtencionHospitalaria() {
		paginaInicioCitaPreviaAtencionHospitalaria(driverC, driverF);
	}

	@Test
	public void paginaInicioCitaPreviaAtencionPrimaria() {
		paginaInicioCitaPreviaAtencionPrimaria(driverC, driverF);
	}

	@Test(dataProvider = "EncoFarmProvider")
	public void comprobarClickTelefono(String provincia, String pueblo, String year, String mes, String dia) {
		encuentraFarmaciaParametros(provincia, pueblo, year, mes, dia, driverC, driverF);
	}

	public void comprobarClickTelefono(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.cssSelector(".action-icon-telephone")).click();
				Assert.assertTrue(currentDriver.findElement(By.linkText("900 12 21 12")).isDisplayed(),
						"La pantalla es incorrecta");
				currentDriver.findElement(By.cssSelector(".ion-page > .ion-color")).click();
			}
		}
	}

	public void comprobarEnlaceCoronavirus(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				String handleVentana1 = currentDriver.getWindowHandle();
				currentDriver.findElement(By.cssSelector("a .action-icon")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'Sí')]")).click();
				Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
				listadoHanleVentanas.remove(handleVentana1);
				String handleVentana2 = listadoHanleVentanas.iterator().next();
				currentDriver.switchTo().window(handleVentana1);
				currentDriver.close();
				currentDriver.switchTo().window(handleVentana2);
				Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(), "No estás en la pestaña adecuada");
				Assert.assertTrue(
						currentDriver.findElement(By.xpath("//span[contains(.,'Asistencia COVID-19')]")).isDisplayed());
			}
		}
	}

	public void masPrivacidad(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(2)")).click();
				Assert.assertEquals(currentDriver.findElement(By.xpath("//h1[contains(.,'Privacidad')]")).isDisplayed(),
						true);
			}
		}
	}

	public void masAyuda(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(3)")).click();
				Assert.assertTrue(
						currentDriver.findElement(By.xpath("//ion-title[contains(.,'Ayuda')]")).isDisplayed());
			}
		}
	}

	public void mostarNotificaciones(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Notificaciones')]")).click();
				Assert.assertEquals(
						currentDriver.findElement(By.xpath("//div[contains(.,'JCCM-COVID')]")).isDisplayed(), true);
			}
		}
	}

	public void masContacto(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				String handleVentana1 = currentDriver.getWindowHandle();
				currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentDriver.findElement(By.xpath("//ion-segment-button[@value='reclamaciones']")).click();
				Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
				listadoHanleVentanas.remove(handleVentana1);
				String handleVentana2 = listadoHanleVentanas.iterator().next();
				currentDriver.switchTo().window(handleVentana1);
				currentDriver.close();
				currentDriver.switchTo().window(handleVentana2);
				Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(), "No estás en la pestaña adecuada");
				Assert.assertTrue(currentDriver
						.findElement(By.xpath("//h1[contains(.,'Reclamaciones centros sanitarios')]")).isDisplayed());
			}
		}
	}

	public void paginaInicioCitaPreviaAtencionPrimaria(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
				currentDriver.findElement(By.xpath("//span[contains(.,'PEDIR CITA')]")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'PRIMARIA')]")).click();
				currentDriver.switchTo().frame(0);
				WebElement imagentarjeta = currentDriver.findElement(By.id("tarjetaportada"));
				Assert.assertTrue(imagentarjeta.isDisplayed());
			}
		}
	}

	public void paginaInicioCitaPreviaAtencionHospitalaria(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);
				currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
				currentDriver.findElement(By.xpath("//span[contains(.,'PEDIR CITA')]")).click();
				currentDriver.findElement(By.xpath("//span[contains(.,'HOSPITALARIA')]")).click();
				currentDriver.switchTo().frame(0);
				Assert.assertEquals(currentDriver.findElement(By.id("ot1")).getText(),
						"Bienvenido a Citación Especializada");
			}
		}
	}

	public void encuentraFarmaciaParametros(String provincia, String pueblo, String year, String mes, String dia,
			WebDriver... drivers) {

		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				saberSiEsChromeOFirefox(i);

				currentDriver.findElement(By.xpath("//span[contains(.,'ENCONTRAR')]")).click();

				currentDriver.findElement(By.xpath("//ion-item[contains(.,'Provincia')]")).click();
				currentDriver.findElement(By.xpath("//button[contains(.,'" + provincia + "')]")).click();
				currentDriver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();

				currentDriver.findElement(By.xpath("//ion-item[contains(.,'Localidad')]")).click();
				currentDriver.findElement(By.xpath("//button[contains(.,'" + pueblo + "')]")).click();
				currentDriver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();

				currentDriver.findElement(By.xpath("//ion-item[contains(.,'Fecha')]")).click();
				currentDriver.findElement(By.cssSelector("span.mat-button-wrapper")).click();
				currentDriver.findElement(By.cssSelector("td[aria-label='" + year + "']")).click();
				currentDriver.findElement(By.xpath("//td[contains(.,'" + mes + "')]")).click();
				currentDriver.findElement(By.xpath("//td[contains(.,'" + dia + "')]")).click();

				currentDriver.findElement(By.xpath("//ion-button[contains(.,'Farmacias')]")).click();

				List<WebElement> listaFarmacias = currentDriver.findElements(By.cssSelector("h3"));
				System.out.println("Listado de farmacias el " + dia + "/" + mes + "/" + year);
				listaFarmacias.forEach(o -> System.out.println(o.getText()));

				if (pueblo.equals("ALARCON")) {
					Assert.assertEquals("M", listaFarmacias.get(0).getText().substring(0, 1));

					String handleVentana1 = currentDriver.getWindowHandle();
					System.out.println(handleVentana1);

					List<WebElement> listaFarmaciasMapa = currentDriver
							.findElements(By.cssSelector("ion-item a.text-end"));
					listaFarmaciasMapa.get(0).click();

					Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
					listadoHanleVentanas.remove(handleVentana1);
					String handleVentana2 = listadoHanleVentanas.iterator().next();
					currentDriver.switchTo().window(handleVentana1);
					currentDriver.close();
					currentDriver.switchTo().window(handleVentana2);
					Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(),
							"No estás en la pestaña adecuada");
				}
			}
		}
	}

	@DataProvider(name = "EncoFarmProvider")
	public Object[][] getData() {
		// String provincia, String pueblo, String year, String mes, String dia
		Object[][] data = { { "CUENCA", "ALARCON", "2020", "DIC", "30" },
				{ "ALBACETE", "CILANCO", "2020", "FEB", "16" }, { "CIUDAD REAL", "ALAMILLO", "2019", "ENE", "11" },
				{ "TOLEDO", "ACECA", "2018", "ABR", "25" } };
		return data;
	}

	private void saberSiEsChromeOFirefox(int i) {
		if (i == 0) {
			System.out.println("Prueba en navegador Chrome");
			Reporter.log("Chrome");
		} else {
			System.out.println("Prueba en navegador Firefox");
			Reporter.log("Firefox");
		}
	}
}
