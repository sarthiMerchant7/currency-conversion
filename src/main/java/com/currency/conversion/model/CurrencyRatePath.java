package com.currency.conversion.model;

public class CurrencyRatePath {

	private String currencyConversionPath;

	private double rate;
	
	private String toCurrencyName;
	
	private String toCurrencyCode;
	
	public String getToCurrencyName() {
		return toCurrencyName;
	}

	public void setToCurrencyName(String toCurrencyName) {
		this.toCurrencyName = toCurrencyName;
	}

	public String getToCurrencyCode() {
		return toCurrencyCode;
	}

	public void setToCurrencyCode(String toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}

	public String getCurrencyConversionPath() {
		return currencyConversionPath;
	}

	public void setCurrencyConversionPath(String path) {
		this.currencyConversionPath = path;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public CurrencyRatePath(String path, double rate) {
		this.currencyConversionPath = path;
		this.rate = rate;
	}
	
	public CurrencyRatePath(String path, double rate, String toCurrencyCode, String toCurrencyName) {
		this.currencyConversionPath = path;
		this.rate = rate;
		this.toCurrencyCode = toCurrencyCode;
		this.toCurrencyName = toCurrencyName;
	}

	public CurrencyRatePath() {
		super();
	}

	@Override
	public String toString() {
		return currencyConversionPath + " and rate=" + rate;
	}
}
