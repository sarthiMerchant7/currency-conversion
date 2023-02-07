package com.currency.conversion.service;

import java.util.List;

import com.currency.conversion.model.CurrencyRatePath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface CurrecyConversionService {

	public List<CurrencyRatePath> getAllCurrencyRates(String from) throws JsonMappingException, JsonProcessingException;
	
}
