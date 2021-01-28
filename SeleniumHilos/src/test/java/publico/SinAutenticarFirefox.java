package publico;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import privado.Funciones;

/**
 * Conjunto de métodos y pruebas que chequean la parte pública de la aplicación web. Se tiene la
 * opción de probar la búsqueda de farmacias con un DataProvider para ejecutar distintas pruebas en
 * la misma ejecución.
 */
public class SinAutenticarFirefox {
  /**
   * Será el controlador de Chrome/Firefox
   */
  public static WebDriver currentDriver;


  /**
   * Se ejecuta antes de comenzar las pruebas de esta clase
   */
  @BeforeClass
  public void cargaPropiedadesMásIdentificarSiNoSeHaHecho() {
    currentDriver = Funciones.driverF;
  }

  /**
   * Se ejecuta antes de comenzar cada una de las pruebas
   */
  @BeforeMethod
  public void cargarPaginaInicial() {
    if (currentDriver != null) {
      currentDriver.get("https://sescampre.jccm.es/portalsalud/app/inicio");
    }
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void comprobarClickTelefono() {
    FuncionesSinAutenticar.comprobarClickTelefono(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void comprobarEnlaceCoronavirus() {
    FuncionesSinAutenticar.comprobarEnlaceCoronavirus(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void masPrivacidad() {
    FuncionesSinAutenticar.masPrivacidad(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void masContacto() {
    FuncionesSinAutenticar.masContacto(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void masAyuda() {
    FuncionesSinAutenticar.masAyuda(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void mostarNotificaciones() {
    FuncionesSinAutenticar.mostarNotificaciones(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void paginaInicioCitaPreviaAtencionHospitalaria() {
    FuncionesSinAutenticar.paginaInicioCitaPreviaAtencionHospitalaria(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Comprobaciones sin iniciar sesión")
  public void paginaInicioCitaPreviaAtencionPrimaria() {
    FuncionesSinAutenticar.paginaInicioCitaPreviaAtencionPrimaria(currentDriver);
  }

  /**
   * Ejecuta la prueba
   */
  @Test(groups = "Búsqueda de farmacias", dataProvider = "EncoFarmProvider", dataProviderClass = FuncionesSinAutenticar.class)
  public void encuentraFarmaciaParametros(String provincia, String pueblo, String year, String mes,
      String dia) {
    FuncionesSinAutenticar.encuentraFarmaciaParametros(provincia, pueblo, year, mes, dia,
        currentDriver);
  }

  /**
   * Imprime en el Report si la prueba se ha ejecutado con Chrome o con Firefox
   * 
   * @param i
   */
  public static void saberSiEsChromeOFirefox(int i) {
    if (i == 0) {
      Reporter.log("Chrome");
    } else {
      Reporter.log("Firefox");
    }
  }
}
