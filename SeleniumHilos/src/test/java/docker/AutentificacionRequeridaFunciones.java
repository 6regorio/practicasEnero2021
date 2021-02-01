package docker;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Conjunto de métodos y pruebas que chequean la parte privada de la aplicación web. Se tiene la
 * opción de elegir entre realizar las pruebas en Chrome, Firefox o ambos. La identifación en Chrome
 * puede ser mediante DNI electrónico o clave permanente. La identificación en los demás casos será
 * siempre con clave permanente.
 */
public class AutentificacionRequeridaFunciones {
  /**
   * Será el controlador de Chrome
   */
  public static WebDriver driverC;
  /**
   * Será el controlador de Opera
   */
  public static WebDriver driverO;
  /**
   * Será el controlador de Firefox
   */
  public static WebDriver driverF;

  public static String baseURL;
  public static String dni;
  public static String password;
  public static String navegador;
  public static String certificadoOClavePermanente;

  public static String nodeUrl = "http://localhost:4444/wd/hub/";


  /**
   * Inicia sesión y comprueba que se haya realizado con los navegadores que se le pasan como
   * parámetro
   * 
   * @param drivers - Los WebDriver con los que se va a comprobar
   */
  public static void realizarAutenticacion(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        iniciarSesionConAssertsIncluidos(currentDriver, dni, password);
      }
    }
  }

  /**
   * Inicia sesión en el sistema mediante la forma de identificación elegida y comprueba que se haya
   * realizado correctamente
   * 
   * @param d - El WebDriver que deseamos usar
   * @param dni
   * @param password
   */
  public static void iniciarSesionConAssertsIncluidos(WebDriver d, String dni, String password) {
    baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
    d.get("https://sescampre.jccm.es/portalsalud/ayuda-pasarela");
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
    try {
      Thread.sleep(7000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    WebDriverWait wait = new WebDriverWait(d, 30);
    wait.until(ExpectedConditions
        .invisibilityOfElementLocated(By.cssSelector("ion-icon[name='lock-closed']")));
    Assert.assertFalse(d.findElement(By.cssSelector("ion-icon[name='lock-closed']")).isDisplayed());

  }

  /**
   * Recorre las funciones que recopilan los datos necesarios para el arranque del programa
   * @throws MalformedURLException 
   */
  public static void iniciarSesionPreguntandoDatos() throws MalformedURLException {
    preguntarNavegadorAUtilizar();

    if (navegador.equals("Firefox") || navegador.equals("Todos")) {
      certificadoOClavePermanente = "Clave permanente";
    } else {
      preguntarDniOClavePermanente();
    }
    System.out.println(certificadoOClavePermanente);

    if (certificadoOClavePermanente.equals("Clave permanente")) {
      pedirDniYPassword();
    }

    if (navegador.equals("Chrome")) {


      ChromeOptions capabilitiesC = new ChromeOptions();
      driverC = new RemoteWebDriver(new URL(nodeUrl), capabilitiesC);
      driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverC.manage().window().maximize();



    } else if (navegador.equals("Firefox")) {

      FirefoxOptions capabilitiesF = new FirefoxOptions();
      driverF = new RemoteWebDriver(new URL(nodeUrl), capabilitiesF);
      driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverF.manage().window().maximize();


    } else if (navegador.equals("Opera")) {

      OperaOptions capabilitiesO = new OperaOptions();
      driverO = new RemoteWebDriver(new URL(nodeUrl), capabilitiesO);
      driverO.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverO.manage().window().maximize();



    } else {

      ChromeOptions capabilitiesC = new ChromeOptions();
      driverC = new RemoteWebDriver(new URL(nodeUrl), capabilitiesC);
      driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverC.manage().window().maximize();

      FirefoxOptions capabilitiesF = new FirefoxOptions();
      driverF = new RemoteWebDriver(new URL(nodeUrl), capabilitiesF);
      driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverF.manage().window().maximize();

      OperaOptions capabilitiesO = new OperaOptions();
      driverO = new RemoteWebDriver(new URL(nodeUrl), capabilitiesO);
      driverO.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      driverO.manage().window().maximize();



    }
    baseURL = "https://sescampre.jccm.es/portalsalud/app/inicio";
  }

  /**
   * Muestra una ventana que permite elegir si se desea usar identificación por DNI electrónico o
   * usar identificación por clave permanente
   */
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

  /**
   * Muestra una ventana que permite elegir qué navegador usar
   */
  private static void preguntarNavegadorAUtilizar() {
    String[] navegadores = {"Chrome", "Firefox", "Opera", "Todos"};
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

  /**
   * Muestra una ventana que pide un DNI y una clave permanenente para la autenticación en el
   * sistema
   */
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
    if (dni.isEmpty() || (password.isEmpty())) {
      System.out.println("Cancelado por el usuario");
      System.exit(1);
    }
  }

  /**
   * Comprueba el DNI dentro de mi perfil, mis datos
   * 
   * @param drivers
   */
  public static void miPerfilMisDatosDniEscrito(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
        currentDriver.findElement(By.xpath("//ion-label[contains(.,'MIS DATOS')]")).click();
        Assert.assertEquals(
            currentDriver.findElement(By.xpath("//ion-label[contains(.,'DNI: 74')]")).isDisplayed(),
            true);
      }
    }
  }

  /**
   * Comprueba el centro dentro de mi perfil, mis profesionales
   * 
   * @param drivers
   */
  public static void miPerfilMisProfesionalesCentroManzanares(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Perfil')]")).click();
        currentDriver.findElement(By.xpath("//ion-label[contains(.,'MIS PROFESIONALES')]")).click();
        Assert.assertTrue(currentDriver
            .findElement(By.xpath("//ion-label[contains(.,'C.S. MANZANARES')]")).isDisplayed());
      }
    }
  }

  /**
   * Comprueba ir a la carpeta de salud y entrar en informes
   * 
   * @param drivers
   */
  public static void historiaClinicaSNS(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
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

  /**
   * Comprueba ir a la carpeta de salud y entrar en alergias
   * 
   * @param drivers
   */
  public static void comprobarCarpetaDeSaludAlergias(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'ALERGIAS')]")).click();
        Assert.assertTrue(
            currentDriver.findElement(By.xpath("//h2[contains(.,'METALES')]")).isDisplayed());
      }
    }
  }

  /**
   * Comprueba ir a la carpeta de salud y entrar en informes
   * 
   * @param drivers
   */
  public static void comprobarCarpetaDeSaludInformes(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'INFORMES')]")).click();
        Assert.assertTrue(currentDriver.findElement(By.xpath("//ion-title[contains(.,'Informes')]"))
            .isDisplayed());
      }
    }
  }

  /**
   * Comprueba ir a la carpeta de salud y entrar en medicación
   * 
   * @param drivers
   */
  public static void comprobarCarpetaDeSaludMedicacion(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'MEDICACION')]")).click();
        Assert.assertTrue(currentDriver
            .findElement(By.xpath("//ion-title[contains(.,'Medicación')]")).isDisplayed());
      }
    }
  }

  /**
   * Comprueba ir a la carpeta de salud y entrar en Mis Citas
   * 
   * @param drivers
   */
  public static void comprobarCarpetaDeSaludMisCitas(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'CITAS')]")).click();
        Assert.assertTrue(
            currentDriver.findElement(By.xpath("//h2[contains(.,'MEDICO')]")).isDisplayed());
      }
    }
  }

  /**
   * Comprueba ir a la carpeta de salud y entrar en vacunas
   * 
   * @param drivers
   */
  public static void comprobarCarpetaDeSaludVacunas(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
        currentDriver.findElement(By.xpath("//ion-segment-button[contains(.,'Carpeta de Salud')]"))
            .click();
        currentDriver.findElement(By.xpath("//span[contains(.,'VACUNAS')]")).click();
        Assert.assertTrue(currentDriver
            .findElement(
                By.xpath("//ion-col[contains(.,'Colera')]"))
            .isDisplayed());
      }
    }
  }

  /**
   * Comprueba ir a la carpeta de salud y entrar lista de espera
   * 
   * @param drivers
   */
  public static void comprobarCarpetaDeSaludListaEspera(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
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

  /**
   * Cierra la sesión y se asegura de que ha salido a la página principal
   * 
   * @param drivers
   */
  public static void cerrarSesion(WebDriver... drivers) {
    for (int i = 0; i < drivers.length; i++) {
      WebDriver currentDriver = drivers[i];
      if (currentDriver != null) {
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
