package privado;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Conjunto de métodos y pruebas que chequean la parte privada de la aplicación web. Se tiene la
 * opción de elegir entre realizar las pruebas en Chrome, Firefox o ambos. La identifación en Chrome
 * puede ser mediante DNI electrónico o clave permanente. La identificación en los demás casos será
 * siempre con clave permanente.
 */
public class AutenticacionRequeridaPara {
  /**
   * Será el controlador del navegador usado Chrome/Firefox
   */
  public static WebDriver currentDriver;

  public String browser;

  public AutenticacionRequeridaPara(String browser) {
    this.browser = browser;
  }

  /**
   * Se ejecuta antes de comenzar todas las pruebas
   */
  @BeforeSuite
  public void cargaPropiedadesIniciales() {
    AutentificacionRequeridaFunciones.iniciarSesionPreguntandoDatos();
  }

  public void funcion() {
    System.out.println("Sólo imprimir");
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println("tiempo " + i);
    }
  }


  /**
   * Se ejecuta antes de comenzar las pruebas
   */
  @BeforeClass
  public void cargaPropiedades() {
    switch (browser) {
      case "Chrome":
        currentDriver = AutentificacionRequeridaFunciones.driverC; // Uso el navegador de Chrome
        break;
      case "Firefox":
        currentDriver = AutentificacionRequeridaFunciones.driverF; // Uso el navegador de Firefox
        break;
      case "Edge":
        currentDriver = AutentificacionRequeridaFunciones.driverE; // Uso el navegador de Edge
        break;
      default:
        System.out.println("No hay navegador seleccionado");
        System.exit(1);
        break;
    }
  }

  /**
   * Arranca la prueba. Inicio de sesión
   */
  @Test(priority = 1, groups = "Inicia sesión")
  public void Firefox_realizarAutenticacion() {
    if (currentDriver == null)
      throw new SkipException("Sin pruebas con Firefox");
    AutentificacionRequeridaFunciones.realizarAutenticacion(currentDriver);
  }

  /**
   * Arranca la prueba del DNI dentro de mi perfil, mis datos
   */
  @Test(priority = 2, groups = "Mi perfil privado",
      dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_miPerfilMisDatosDniEscrito() {
    AutentificacionRequeridaFunciones.miPerfilMisDatosDniEscrito(currentDriver);
  }

  /**
   * Arranca la prueba del centro dentro de mi perfil, mis profesionales
   */
  @Test(priority = 3, groups = "Mi perfil privado",
      dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_miPerfilMisProfesionalesCentroManzanares() {
    AutentificacionRequeridaFunciones.miPerfilMisProfesionalesCentroManzanares(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en informes
   */
  @Test(priority = 3, groups = "Mi perfil privado",
      dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_historiaClinicaSNS() {
    AutentificacionRequeridaFunciones.historiaClinicaSNS(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en alergias
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_comprobarCarpetaDeSaludAlergias() {
    AutentificacionRequeridaFunciones.comprobarCarpetaDeSaludAlergias(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en informes
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_comprobarCarpetaDeSaludInformes() {
    AutentificacionRequeridaFunciones.comprobarCarpetaDeSaludInformes(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en medicación
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_comprobarCarpetaDeSaludMedicacion() {
    AutentificacionRequeridaFunciones.comprobarCarpetaDeSaludMedicacion(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en Mis Citas
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_comprobarCarpetaDeSaludMisCitas() {
    AutentificacionRequeridaFunciones.comprobarCarpetaDeSaludMisCitas(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en vacunas
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_comprobarCarpetaDeSaludVacunas() {
    AutentificacionRequeridaFunciones.comprobarCarpetaDeSaludVacunas(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar lista de espera
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_comprobarCarpetaDeSaludListaEspera() {
    AutentificacionRequeridaFunciones.comprobarCarpetaDeSaludListaEspera(currentDriver);
  }

  /**
   * Arranca la prueba. Cierra la sesión y se asegura de que ha salido a la página principal
   */
  @Test(priority = 5, groups = "Cerrando sesión",
      dependsOnMethods = "Firefox_realizarAutenticacion")
  public void Firefox_cerrarSesion() {
    AutentificacionRequeridaFunciones.cerrarSesion(currentDriver);
  }

  /**
   * Cierra los navegadores
   */
  @AfterClass
  void tearDoown() {
    if (currentDriver != null) {
      currentDriver.quit();
    }
  }
}