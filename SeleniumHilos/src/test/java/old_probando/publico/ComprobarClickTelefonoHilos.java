package old_probando.publico;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ComprobarClickTelefonoHilos implements Runnable {

	WebDriver currentDriver;

	public ComprobarClickTelefonoHilos(WebDriver currentDriver) {
		this.currentDriver = currentDriver;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Ha pasado " + (i+1) + " segundos en " + Thread.currentThread().getName());
		}
		currentDriver.findElement(By.cssSelector(".action-icon-telephone")).click();
		Assert.assertTrue(currentDriver.findElement(By.linkText("900 12 21 12")).isDisplayed(),
				"La pantalla es incorrecta");
		currentDriver.findElement(By.cssSelector(".ion-page > .ion-color")).click();
		System.out.println("Más");
		currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(2)")).click();
		Assert.assertEquals(currentDriver.findElement(By.xpath("//h1[contains(.,'Privacidad')]")).isDisplayed(),
				true);
		Assert.fail("Obligado");
	}
}
