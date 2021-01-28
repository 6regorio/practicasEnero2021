package old_probando.privado;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import publico.SinAutenticarChrome;

public class Funciones {

  public static WebDriver driverC;
  public static WebDriver driverF;

  private static String baseURL;
  public static String dni;
  public static String password;
  private static String navegador;
  private static String certificadoOClavePermanente;

  public static void realizarAutenticacion(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        iniciarSesionConAssertsIncluidos(currentDriver, dni, password);
      }
    }
  }

  public static void iniciarSesionConAssertsIncluidos(WebDriver d, String dni, String password) {
    baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
    d.get(baseURL);
    d.manage().window().maximize();
    List<WebElement> botonesIdentificarme =
        d.findElements(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]"));
    botonesIdentificarme.get(0).click();
    List<WebElement> botonesAceptar =
        d.findElements(By.xpath("//ion-button[contains(.,'Aceptar')]"));
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
    WebDriverWait wait = new WebDriverWait(d, 30);
    wait.until(ExpectedConditions
        .invisibilityOfElementLocated(By.cssSelector("ion-icon[name='lock-closed']")));
    Assert.assertFalse(d.findElement(By.cssSelector("ion-icon[name='lock-closed']")).isDisplayed());
  }

  public static void iniciarSesionPreguntandoDatos() {
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
      System.setProperty("webdriver.chrome.driver",
          "./src/test/resources/chromedriver/chromedriver.exe");
      driverC = new ChromeDriver();
      driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverC.manage().window().maximize();
      driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    } else if (navegador.equals("Firefox")) {
      System.setProperty("webdriver.gecko.driver",
          "./src/test/resources/firefoxdriver/geckodriver.exe");
      driverF = new FirefoxDriver();
      driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverF.manage().window().maximize();
      driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    } else {
      System.setProperty("webdriver.chrome.driver",
          "./src/test/resources/chromedriver/chromedriver.exe");
      driverC = new ChromeDriver();
      driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverC.manage().window().maximize();
      driverC.get("https://sescampre.jccm.es/portalsalud/app/inicio");
      System.setProperty("webdriver.gecko.driver",
          "./src/test/resources/firefoxdriver/geckodriver.exe");
      driverF = new FirefoxDriver();
      driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverF.manage().window().maximize();
      driverF.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    }
    baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
  }

  private static void preguntarDniOClavePermanente() {
    String[] identificadores = {"DNI electrónico", "Clave permanente"};
    Object selected2 = JOptionPane.showInputDialog(null, "¿Qué tipo de identificación desea usar?",
        "Elija identificación", JOptionPane.DEFAULT_OPTION, null, identificadores,
        "Clave permanente");
    if (selected2 != null) {
      certificadoOClavePermanente = selected2.toString();
    } else {
      System.out.println("Cancelado por el usuario");
      System.exit(1);
    }
  }

  private static void preguntarNavegadorAUtilizar() {
    String[] navegadores = {"Chrome", "Firefox", "Ambos"};
    Object selected = JOptionPane.showInputDialog(null, "¿Qué navegador desea usar?",
        "Elija navegador", JOptionPane.DEFAULT_OPTION, null, navegadores, "Chrome");
    if (selected != null) {
      navegador = selected.toString();
    } else {
      System.out.println("Cancelado por el usuario");
      System.exit(1);
    }

    System.out.println(navegador);
  }

  private static void pedirDniYPassword() {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new BorderLayout(5, 5));
    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
    label.add(new JLabel("DNI (12345678Z)", SwingConstants.RIGHT));
    label.add(new JLabel("Clave permanente", SwingConstants.RIGHT));
    panel.add(label, BorderLayout.WEST);
    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
    JTextField txtDni = new JTextField();
    controls.add(txtDni);
    JPasswordField txtPassword = new JPasswordField();
    controls.add(txtPassword);
    panel.add(controls, BorderLayout.CENTER);
    JOptionPane.showMessageDialog(frame, panel, "Iniciar sesión", JOptionPane.QUESTION_MESSAGE);
    dni = txtDni.getText();
    password = "";
    for (char caracter : txtPassword.getPassword()) {
      password += caracter;
    }
    if (dni.isBlank() || (password.isBlank())) {
      System.out.println("Cancelado por el usuario");
      System.exit(1);
    }
  }

  public static void miPerfilMisDatosDniEscrito(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
        currentDriver.findElement(By.xpath("//ion-label[contains(.,'MIS DATOS')]")).click();
        Assert.assertEquals(
            currentDriver.findElement(By.xpath("//ion-label[contains(.,'DNI: 74')]")).isDisplayed(),
            true);
      }
    }
  }

  public static void miPerfilMisProfesionalesCentroManzanares(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
        currentDriver.findElement(By.xpath("//ion-label[contains(.,'MIS PROFESIONALES')]")).click();
        Assert.assertTrue(currentDriver
            .findElement(By.xpath("//ion-label[contains(.,'C.S. MANZANARES')]")).isDisplayed());
      }
    }
  }

  public static void historiaClinicaSNS(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        String handleVentana1 = currentDriver.getWindowHandle();
        currentDriver.findElement(By.xpath("//ion-segment-button[@value='hcdsns']")).click();
        Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
        listadoHanleVentanas.remove(handleVentana1);
        String handleVentana2 = listadoHanleVentanas.iterator().next();
        currentDriver.switchTo().window(handleVentana2);
        Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(),
            "No estás en la pestaña adecuada");
        String titulo = currentDriver.findElement(By.id("page-title")).getText();
        Assert.assertTrue(titulo.substring(0, 8).equals("Historia") ? true : false);
        currentDriver.close();
        currentDriver.switchTo().window(handleVentana1);
      }
    }
  }

  public static void comprobarCarpetaDeSaludAlergias(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'ALERGIAS')]")).click();
        Assert.assertTrue(
            currentDriver.findElement(By.xpath("//h2[contains(.,'METALES')]")).isDisplayed());
      }
    }
  }

  public static void comprobarCarpetaDeSaludInformes(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'INFORMES')]")).click();
        Assert.assertTrue(currentDriver.findElement(By.xpath("//ion-title[contains(.,'Informes')]"))
            .isDisplayed());
      }
    }
  }

  public static void comprobarCarpetaDeSaludMedicacion(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'MEDICACION')]")).click();
        Assert.assertTrue(currentDriver
            .findElement(By.xpath("//ion-title[contains(.,'Medicación')]")).isDisplayed());
      }
    }
  }

  public static void comprobarCarpetaDeSaludMisCitas(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'CITAS')]")).click();
        Assert.assertTrue(
            currentDriver.findElement(By.xpath("//h2[contains(.,'DIGESTIVO')]")).isDisplayed());
      }
    }
  }

  public static void comprobarCarpetaDeSaludVacunas(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'VACUNAS')]")).click();
        Assert.assertTrue(currentDriver
            .findElement(
                By.xpath("//ion-col[contains(.,'No existen vacunas registradas en el sistema')]"))
            .isDisplayed());
      }
    }
  }

  public static void comprobarCarpetaDeSaludListaEspera(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        String handleVentana1 = currentDriver.getWindowHandle();
        currentDriver.findElement(By.xpath("//span[contains(.,'ESPERA')]")).click();
        Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
        listadoHanleVentanas.remove(handleVentana1);
        String handleVentana2 = listadoHanleVentanas.iterator().next();
        currentDriver.switchTo().window(handleVentana2);
        Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(),
            "No estás en la pestaña adecuada");
        boolean ListaDeEspera = false;
        try {
          currentDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
          if (currentDriver.findElement(By.xpath("//span[contains(.,'Lista de espera')]"))
              .isDisplayed()) {
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

  public static void cerrarSesion(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        SinAutenticarChrome.saberSiEsChromeOFirefox(i);
        currentDriver.findElement(By.cssSelector("div > .button-clear")).click();
        currentDriver.findElement(By.xpath("//button[2]/span")).click();
        WebDriverWait wait = new WebDriverWait(currentDriver, 30);
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]")));
        Assert.assertTrue(currentDriver
            .findElement(By.xpath("//ion-button[contains(.,'IDENTIFICARME')]")).isDisplayed());
      }
    }
  }


}