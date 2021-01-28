package old_probando.privado;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AutenticacionRequerida {

  public static WebDriver driverC;
  public static WebDriver driverF;

  public static String dni;
  public static String password;

  Funciones f;

  @BeforeSuite
  public void cargaPropiedades() {
    Funciones.iniciarSesionPreguntandoDatos();
    driverC=Funciones.driverC;
    driverF=Funciones.driverF;
  }

  @Test(priority = 1, groups = "Inicia sesión")
  public void realizarAutenticacion() {
    Funciones.realizarAutenticacion(driverC);
  }

  @Test(priority = 2, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void miPerfilMisDatosDniEscrito() {
    Funciones.miPerfilMisDatosDniEscrito(driverC);
  }

  @Test(priority = 3, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void miPerfilMisProfesionalesCentroManzanares() {
    Funciones.miPerfilMisProfesionalesCentroManzanares(driverC);
  }

  @Test(priority = 3, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void historiaClinicaSNS() {
    Funciones.historiaClinicaSNS(driverC);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludAlergias() {
    Funciones.comprobarCarpetaDeSaludAlergias(driverC);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludInformes() {
    Funciones.comprobarCarpetaDeSaludInformes(driverC);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludMedicacion() {
    Funciones.comprobarCarpetaDeSaludMedicacion(driverC);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludMisCitas() {
    Funciones.comprobarCarpetaDeSaludMisCitas(driverC);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludVacunas() {
    Funciones.comprobarCarpetaDeSaludVacunas(driverC);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludListaEspera() {
    Funciones.comprobarCarpetaDeSaludListaEspera(driverC);
  }

  @Test(priority = 5, groups = "Cerrando sesión", dependsOnMethods = "realizarAutenticacion")
  public void cerrarSesion() {
    Funciones.cerrarSesion(driverC);
  }

  @AfterSuite
  void tearDoown() {
    if (driverF != null) {
      driverF.quit();
    }
    if (driverC != null) {
      driverC.quit();
    }
  }

}
