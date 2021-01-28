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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import publico.SinAutenticarChrome;

public class AutenticacionRequerida2 {

  public static WebDriver driverC;
  public static WebDriver driverF;

  @BeforeClass
  public void cargaPropiedades() {
    // Funciones.iniciarSesionPreguntandoDatos();
    driverC = Funciones.driverC;
    driverF = Funciones.driverF;
  }

  @Test(priority = 1, groups = "Inicia sesión")
  public void realizarAutenticacion() {
    Funciones.realizarAutenticacion(driverF);
  }

  @Test(priority = 2, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void miPerfilMisDatosDniEscrito() {
    Funciones.miPerfilMisDatosDniEscrito(driverF);
  }

  @Test(priority = 3, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void miPerfilMisProfesionalesCentroManzanares() {
    Funciones.miPerfilMisProfesionalesCentroManzanares(driverF);
  }

  @Test(priority = 3, groups = "Mi perfil privado", dependsOnMethods = "realizarAutenticacion")
  public void historiaClinicaSNS() {
    Funciones.historiaClinicaSNS(driverF);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludAlergias() {
    Funciones.comprobarCarpetaDeSaludAlergias(driverF);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludInformes() {
    Funciones.comprobarCarpetaDeSaludInformes(driverF);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludMedicacion() {
    Funciones.comprobarCarpetaDeSaludMedicacion(driverF);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludMisCitas() {
    Funciones.comprobarCarpetaDeSaludMisCitas(driverF);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludVacunas() {
    Funciones.comprobarCarpetaDeSaludVacunas(driverF);
  }

  @Test(priority = 4, groups = "Carpeta Salud", dependsOnMethods = "realizarAutenticacion")
  public void comprobarCarpetaDeSaludListaEspera() {
    Funciones.comprobarCarpetaDeSaludListaEspera(driverF);
  }

  @Test(priority = 5, groups = "Cerrando sesión", dependsOnMethods = "realizarAutenticacion")
  public void cerrarSesion() {
    Funciones.cerrarSesion(driverF);
  }


}
