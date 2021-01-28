package privado;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Conjunto de métodos y pruebas que chequean la parte privada de la aplicación web. Se tiene la
 * opción de elegir entre realizar las pruebas en Chrome, Firefox o ambos. La identifación en Chrome
 * puede ser mediante DNI electrónico o clave permanente. La identificación en los demás casos será
 * siempre con clave permanente.
 */
public class AutenticacionRequeridaChrome {
  /**
   * Será el controlador del navegador usado Chrome/Firefox
   */
  public static WebDriver currentDriver;

  /**
   * Se ejecuta antes de comenzar todas las pruebas
   */
  @BeforeSuite
  public void cargaPropiedadesIniciales() {
    Funciones.iniciarSesionPreguntandoDatos();
  }

  /**
   * Se ejecuta antes de comenzar las pruebas
   */
  @BeforeClass
  public void cargaPropiedades() {
    currentDriver = Funciones.driverC; // Primero uso el navegado de Chrome
  }

  /**
   * Arranca la prueba. Inicio de sesión
   */
  @Test(priority = 1, groups = "Inicia sesión")
  public void realizarAutenticacion() {
    Funciones.realizarAutenticacion(currentDriver);
  }

  /**
   * Arranca la prueba del DNI dentro de mi perfil, mis datos
   */
  @Test(priority = 2, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void miPerfilMisDatosDniEscrito() {
    Funciones.miPerfilMisDatosDniEscrito(currentDriver);
  }

  /**
   * Arranca la prueba del centro dentro de mi perfil, mis profesionales
   */
  @Test(priority = 3, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void miPerfilMisProfesionalesCentroManzanares() {
    Funciones.miPerfilMisProfesionalesCentroManzanares(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en informes
   */
  @Test(priority = 3, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void historiaClinicaSNS() {
    Funciones.historiaClinicaSNS(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en alergias
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludAlergias() {
    Funciones.comprobarCarpetaDeSaludAlergias(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en informes
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludInformes() {
    Funciones.comprobarCarpetaDeSaludInformes(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en medicación
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludMedicacion() {
    Funciones.comprobarCarpetaDeSaludMedicacion(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en Mis Citas
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludMisCitas() {
    Funciones.comprobarCarpetaDeSaludMisCitas(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar en vacunas
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludVacunas() {
    Funciones.comprobarCarpetaDeSaludVacunas(currentDriver);
  }

  /**
   * Arranca la prueba. Ir a la carpeta de salud y entrar lista de espera
   */
  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludListaEspera() {
    Funciones.comprobarCarpetaDeSaludListaEspera(currentDriver);
  }

  /**
   * Arranca la prueba. Cierra la sesión y se asegura de que ha salido a la página principal
   */
  @Test(priority = 5, groups = "Cerrando sesión", dependsOnMethods = "realizarAutenticacion")
  public void cerrarSesion() {
    Funciones.cerrarSesion(currentDriver);
  }

  /**
   * Cierra los navegadores
   */
  @AfterSuite
  void tearDoown() {
    if (currentDriver != null) {
      currentDriver.quit();
    }
  }
}
