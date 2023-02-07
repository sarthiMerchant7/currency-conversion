package com.currency.conversion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRateModel {

	@JsonProperty("exchangeRate")
	private double exchangeRate;
	
	@JsonProperty("fromCurrencyCode")
	private String fromCurrencyCode;
	
	@JsonProperty("fromCurrencyName")
	private String fromCurrencyName;
	
	@JsonProperty("toCurrencyCode")
	private String toCurrencyCode;
	
	@JsonProperty("toCurrencyName")
	private String toCurrencyName;

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getFromCurrencyCode() {
		return fromCurrencyCode;
	}

	public void setFromCurrencyCode(String fromCurrencyCode) {
		this.fromCurrencyCode = fromCurrencyCode;
	}

	public String getFromCurrencyName() {
		return fromCurrencyName;
	}

	public void setFromCurrencyName(String fromCurrencyName) {
		this.fromCurrencyName = fromCurrencyName;
	}

	public String getToCurrencyCode() {
		return toCurrencyCode;
	}

	public void setToCurrencyCode(String toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}

	public String getToCurrencyName() {
		return toCurrencyName;
	}

	public void setToCurrencyName(String toCurrencyName) {
		this.toCurrencyName = toCurrencyName;
	}

	@Override
	public String toString() {
		return "CurrencyRateModel [exchangeRate=" + exchangeRate + ", fromCurrencyCode=" + fromCurrencyCode
				+ ", fromCurrencyName=" + fromCurrencyName + ", toCurrencyCode=" + toCurrencyCode + ", toCurrencyName="
				+ toCurrencyName + "]";
	}
}
