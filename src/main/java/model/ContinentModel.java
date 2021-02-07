package model;

import java.util.ArrayList;

/**
 * This class will store information related to specific continent
 */
public class ContinentModel {
	String d_continentId;
	int d_continentValue;
	ArrayList<CountryModel> d_listOfCountries = new ArrayList<CountryModel>();

	public ContinentModel(String p_continentId, int p_continentValue) {
		d_continentId  = p_continentId;
		d_continentValue = p_continentValue;
	}
	
	void addCountries(ArrayList<CountryModel> p_listOfCountries) {
		for(CountryModel l_countryIndex : p_listOfCountries) {
			d_listOfCountries.add(l_countryIndex);
		}
	}
	
	void addCountry(CountryModel p_country) {
		d_listOfCountries.add(p_country);
	}
}
