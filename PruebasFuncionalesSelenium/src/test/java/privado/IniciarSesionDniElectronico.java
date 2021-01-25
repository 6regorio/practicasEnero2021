package privado;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class IniciarSesionDniElectronico {

	public static WebDriver driver;
	private String baseURL;

	@BeforeSuite
	public void cargaPropiedades() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	@Test
	public void identificarmeVistaEscritorio() throws InterruptedException {
		iniciarSesionConAssertsIncluidos(driver);		
	}

	public void iniciarSesionConAssertsIncluidos(WebDriver d) {
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

		d.findElement(By.partialLinkText("Acce")).click();

		// Ahora salta un aviso de navegación privada que tengo que quitar
	/*	d.findElement(By.cssSelector("button#details-button")).click();
		d.findElement(By.partialLinkText("Proceed to dcentricapigatewaypre.sescam.jclm.es (unsafe)")).click();*/

		// Error de CIP inválido
		Assert.assertTrue(d.findElement(By.id("alert-1-hdr")).isDisplayed());
		d.findElement(By.xpath("//span[contains(.,'Acep')]")).click();

		// Comprobar que aparece el nombre GREGORIO
		// Assert.assertTrue(driver.findElement(By.xpath("//ion-button[contains(.,'GREGORIO')]")).isDisplayed());

		// Comprobar que no aparece el candado
		Assert.assertFalse(d.findElement(By.cssSelector("ion-icon[name='lock-closed']")).isDisplayed());
	}

	@AfterSuite
	void tearDoown() {
		driver.close();
	}

}
