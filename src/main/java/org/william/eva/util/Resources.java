package org.william.eva.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Resources {
	private String lang;
	private String country;
	
	private ResourceBundle resourceBundle;
	
	public Resources(String lang, String country) {
		this.lang = lang;
		this.country = country;
		
		Locale locale = new Locale.Builder().setLanguage(lang).setRegion(country).build();
		
		resourceBundle = ResourceBundle.getBundle("lang", locale);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getText(String key) {
		return resourceBundle.getString(key);
	}
}
