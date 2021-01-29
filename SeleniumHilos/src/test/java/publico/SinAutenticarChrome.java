package publico;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import privado.AutentificacionRequeridaFunciones;

/**
 * Conjunto de métodos y pruebas que chequean la parte pública de la aplicación web. Se tiene la
 * opción de probar la búsqueda de farmacias con un DataProvider para ejecutar distintas pruebas en
 * la misma ejecución.
 */
public class SinAutenticarChrome {
  /**
   * Será el controlador de Chrome/Firefox
   */
  public static WebDriver currentDriver;

  /**
   * Se ejecuta antes de comenzar las pruebas de esta clase
   */
  @BeforeClass
  public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
    currentDriver = AutentificacionRequeridaFunciones.driverC;
  }

  /**
   * Se ejecuta antes de comenzar cada una de las pruebas
   */
  @BeforeMethod
  public void Chrome_cargarPaginaInicial() {
    if (currentDriver != null) {
      currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    }
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión",
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_comprobarClickTelefono() {
    FuncionesSinAutenticar.comprobarClickTelefono(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(priority = 1, groups = "Comprobaciones sin iniciar sesión")
  public void Chrome_comprobarEnlaceCoronavirus() {
    if (currentDriver == null)
      throw new SkipException("Sin pruebas con Chrome");
    FuncionesSinAutenticar.comprobarEnlaceCoronavirus(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión",
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_masPrivacidad() {
    FuncionesSinAutenticar.masPrivacidad(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión",
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_masContacto() {
    FuncionesSinAutenticar.masContacto(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión",
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_masAyuda() {
    FuncionesSinAutenticar.masAyuda(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión",
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_mostarNotificaciones() {
    FuncionesSinAutenticar.mostarNotificaciones(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión",
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_paginaInicioCitaPreviaAtencionHospitalaria() {
    FuncionesSinAutenticar.paginaInicioCitaPreviaAtencionHospitalaria(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión",
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_paginaInicioCitaPreviaAtencionPrimaria() {
    FuncionesSinAutenticar.paginaInicioCitaPreviaAtencionPrimaria(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Búsqueda de farmacias", dataProvider = "EncoFarmProvider",
      dataProviderClass = FuncionesSinAutenticar.class,
      dependsOnMethods = "Chrome_comprobarEnlaceCoronavirus")
  public void Chrome_encuentraFarmaciaParametros(String provincia, String pueblo, String year,
      String mes, String dia) {
    FuncionesSinAutenticar.encuentraFarmaciaParametros(provincia, pueblo, year, mes, dia,
        currentDriver);
  }
}
