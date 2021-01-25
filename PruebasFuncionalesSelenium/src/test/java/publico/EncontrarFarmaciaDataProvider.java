package publico;

import org.testng.annotations.DataProvider;

public class EncontrarFarmaciaDataProvider {

	@DataProvider(name = "EncoFarmProvider")
	public Object[][] getData() {
		// String provincia, String pueblo, String year, String mes, String dia		
		Object[][] data = { { "CUENCA", "ALARCON", "2020", "DIC", "30" },
				{ "ALBACETE", "CILANCO", "2020", "FEB", "16" },
				{ "CIUDAD REAL", "ALAMILLO", "2019", "ENE", "11" },
				{ "TOLEDO", "ACECA", "2018", "ABR", "25" } };
		return data;
	}
}
