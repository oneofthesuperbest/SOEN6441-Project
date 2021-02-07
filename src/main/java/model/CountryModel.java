package model;

import java.util.ArrayList;

/**
 * This class will contain information related to specific country
 */
public class CountryModel {
	String d_countryId;
	ArrayList<CountryModel> d_neighbourCountries = new ArrayList<CountryModel>();
}
