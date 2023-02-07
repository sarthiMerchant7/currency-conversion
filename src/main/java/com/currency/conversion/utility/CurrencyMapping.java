package com.currency.conversion.utility;

public class CurrencyMapping implements Comparable<CurrencyMapping> {
	public String currency;
	public String parent;
	public double cost;

	public CurrencyMapping(String parent, String currency, double cost) {
		this.currency = currency;
		this.parent = parent;
		this.cost = cost;
	}

	@Override
	public int compareTo(CurrencyMapping o) {
		if (this.cost == o.cost) {
			return 1;
		} else {
			return Double.compare(this.cost, o.cost);
		}
	}
}
