package publico;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import privado.IniciarSesionDniElectronico;

public class EncontrarFarmaciaConFecha {

	WebDriver driver;
	String baseURL;

	@BeforeClass
	public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
		driver = IniciarSesionDniElectronico.driver;
		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
	}

	@BeforeMethod
	public void cargarPaginaInicial() {
		driver.get(baseURL);
	}

	@Test(dataProvider = "EncoFarmProvider")
	void encuentraFarmaciaParametros(String provincia, String pueblo, String year, String mes, String dia)
			throws InterruptedException {
		System.out.println(provincia);
		System.out.println(pueblo);
		System.out.println(year);
		System.out.println(mes);
		System.out.println(dia);

		driver.findElement(By.xpath("//span[contains(.,'ENCONTRAR')]")).click();

		// Compruebo que he llegado a Farmacia correctamente
		String urlDeseada = "https://sescampre.jccm.es/portalsalud/app/farmacias";
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url, urlDeseada, "la dirección no es igual, es: " + url + " y se esperaba: " + urlDeseada);

		driver.findElement(By.xpath("//ion-item[contains(.,'Provincia')]")).click();
		driver.findElement(By.xpath("//button[contains(.,'" + provincia + "')]")).click();
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();

		driver.findElement(By.xpath("//ion-item[contains(.,'Localidad')]")).click();
		driver.findElement(By.xpath("//button[contains(.,'" + pueblo + "')]")).click();
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();

		driver.findElement(By.xpath("//ion-item[contains(.,'Fecha')]")).click();
		// driver.findElement(By.cssSelector("span.mat-button-wrapper div.mat-calendar-arrow")).click();
		driver.findElement(By.cssSelector("span.mat-button-wrapper")).click();
		driver.findElement(By.cssSelector("td[aria-label='" + year + "']")).click();
		driver.findElement(By.xpath("//td[contains(.,'" + mes + "')]")).click();
		driver.findElement(By.xpath("//td[contains(.,'" + dia + "')]")).click();

		driver.findElement(By.xpath("//ion-button[contains(.,'Farmacias')]")).click();

		List<WebElement> listaFarmacias = driver.findElements(By.cssSelector("h3"));
		System.out.println("Listado de farmacias el " + dia + "/" + mes + "/" + year);
		listaFarmacias.forEach(o -> System.out.println(o.getText()));

		if (pueblo.equals("ALARCON")) {
			Assert.assertEquals("M", listaFarmacias.get(0).getText().substring(0, 1));

			String handleVentana1 = driver.getWindowHandle();
			System.out.println(handleVentana1);

			List<WebElement> listaFarmaciasMapa = driver.findElements(By.cssSelector("ion-item a.text-end"));
			listaFarmaciasMapa.get(0).click();

			Set<String> listadoHanleVentanas = driver.getWindowHandles();
			listadoHanleVentanas.remove(handleVentana1);
			String handleVentana2 = listadoHanleVentanas.iterator().next();
			driver.switchTo().window(handleVentana1);
			driver.close();
			driver.switchTo().window(handleVentana2);
			Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");
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

}
