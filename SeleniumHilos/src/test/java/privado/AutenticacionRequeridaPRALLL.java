package privado;

import org.testng.annotations.Factory;

public class AutenticacionRequeridaPRALLL {

  @Factory
  public Object[] factoryMethod() {
    new AutenticacionRequeridaPara("Chrome").funcion();
    return new Object[] {new AutenticacionRequeridaPara("Chrome"),
        new AutenticacionRequeridaPara("Firefox"), new AutenticacionRequeridaPara("Edge")};
  }
}
