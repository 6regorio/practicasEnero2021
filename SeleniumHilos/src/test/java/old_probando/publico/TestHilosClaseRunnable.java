package old_probando.publico;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class TestHilosClaseRunnable implements Runnable {

	WebDriver currentDriver;

	public TestHilosClaseRunnable(WebDriver currentDriver) {
		this.currentDriver = currentDriver;
	}
	
	@Override
	public void run() {
      currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Más')]")).click();
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      currentDriver.findElement(By.cssSelector(".popover-viewport > .md:nth-child(2)")).click();
      Assert.assertEquals(
          currentDriver.findElement(By.xpath("//h1[contains(.,'Privacidad')]")).isDisplayed(),
          true);
	}
}
