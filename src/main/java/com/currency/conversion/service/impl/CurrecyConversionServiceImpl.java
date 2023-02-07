package com.currency.conversion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.currency.conversion.model.CurrencyRateModel;
import com.currency.conversion.model.CurrencyRatePath;
import com.currency.conversion.service.CurrecyConversionService;
import com.currency.conversion.utility.CurrencyConversionUtility;
import com.currency.conversion.utility.CurrencyMapAlgorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class CurrecyConversionServiceImpl implements CurrecyConversionService {

	@Value("${currency.rate.api.url}")
	private String currencyRateUrl;

	@Override
	public List<CurrencyRatePath> getAllCurrencyRates(String from) throws JsonMappingException, JsonProcessingException {
		// Fetching the data from API.
		List<CurrencyRateModel> currencyRateModels = CurrencyConversionUtility.fetchCurrencyRates(currencyRateUrl);

		Map<String, String> currencyCodeNameMap = currencyRateModels.stream().collect(Collectors.toMap(currency -> currency.getToCurrencyCode(), currency -> currency.getToCurrencyName(), (key1, key2) -> key1));
		
		// Creating the currency rate mapping for all currencies.
		Map<String, Map<String, Double>> currencyMappingGraph = buildCurrencyRateMapping(currencyRateModels);
		
		// Finding the best conversion rates from the currency rate mapping. 
		List<CurrencyRatePath> allPath = new ArrayList<>();
		allPath = getMaxCurrencyRatePath(from, currencyMappingGraph);
		return allPath.stream()
				.map(currency -> new CurrencyRatePath(currency.getCurrencyConversionPath(), currency.getRate() * 100))
				.map(currency -> CurrencyConversionUtility.createCurrencyRatePathModel(currency, currencyCodeNameMap))
				.collect(Collectors.toList());
	}

	

	/**
	 * Finding the best possible rate path for all the currencies from CAD.
	 * 
	 * @param from
	 * @param currencyMappingGraph
	 * @return
	 */
	private List<CurrencyRatePath> getMaxCurrencyRatePath(String from, Map<String, Map<String, Double>> currencyMappingGraph) {

		List<CurrencyRatePath> allCurrenciesPath = new ArrayList<>();
		if (!currencyMappingGraph.containsKey(from)) {
			return allCurrenciesPath;
		}

		
		// Finding the rate form the currency rate mapping map and creating the list for all currency.
		Map<String, String> parent = CurrencyMapAlgorithm.buildCurrencyMappingGraph(from, currencyMappingGraph);
		for (Entry<String, Map<String, Double>> entry : currencyMappingGraph.entrySet()) {
			if(!from.equals(entry.getKey())) {
				double rate = 0.0;
				if (currencyMappingGraph.get(from).containsKey(entry.getKey())) {
					rate = currencyMappingGraph.get(from).get(entry.getKey()); 
				}
				allCurrenciesPath.add(new CurrencyRatePath(CurrencyMapAlgorithm.findCurrencyPath(parent, from, entry.getKey()), rate));
			}
		}
		return allCurrenciesPath;
	}

	

	/**
	 * Build the currency rate mapping.
	 * 
	 * @param currencyRateModels
	 */
	private Map<String, Map<String, Double>> buildCurrencyRateMapping(List<CurrencyRateModel> currencyRateModels) {
		Map<String, Map<String, Double>> currencyMappingGraph = new HashMap<>();

		for (CurrencyRateModel currencyRate : currencyRateModels) {
			Map<String, Double> map = null;

			if (!currencyMappingGraph.containsKey(currencyRate.getFromCurrencyCode())) {
				map = new HashMap<>();
				map.put(currencyRate.getToCurrencyCode(), currencyRate.getExchangeRate());
			} else {
				map = currencyMappingGraph.get(currencyRate.getFromCurrencyCode());
				map.put(currencyRate.getToCurrencyCode(), currencyRate.getExchangeRate());
			}

			currencyMappingGraph.put(currencyRate.getFromCurrencyCode(), map);
		}
		
		for (CurrencyRateModel rate : currencyRateModels) {
			for (var val : currencyMappingGraph.get(rate.getFromCurrencyCode()).entrySet()) {
				if (!currencyMappingGraph.containsKey(val.getKey())) {
					currencyMappingGraph.put(val.getKey(), new HashMap<>());
				}

				currencyMappingGraph.get(val.getKey()).put(val.getKey(), 1.0);

				if (!currencyMappingGraph.get(val.getKey()).containsKey(rate.getFromCurrencyCode())) {
					currencyMappingGraph.get(val.getKey()).put(rate.getFromCurrencyCode(), 1.0 / val.getValue());
				}
			}
		}
		
		return currencyMappingGraph;
	}

}
