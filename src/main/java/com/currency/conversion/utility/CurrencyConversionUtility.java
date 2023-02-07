package com.currency.conversion.utility;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import com.currency.conversion.model.CurrencyRateModel;
import com.currency.conversion.model.CurrencyRatePath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrencyConversionUtility {

	private static final RestTemplate restTemplate = new RestTemplate();

	private static final ObjectMapper mapper = new ObjectMapper();

	private static <T> T callRestService(String url, HttpMethod httpMethod, @Nullable HttpEntity<String> httpEntity,
			Class<T> type) {
		return restTemplate.exchange(url, httpMethod, httpEntity, type).getBody();
	}

	public static List<CurrencyRateModel> fetchCurrencyRates(String currencyRateUrl)
			throws JsonProcessingException, JsonMappingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String currencyRates = callRestService(currencyRateUrl, HttpMethod.GET, entity, String.class);

		List<CurrencyRateModel> currencyRateModels = mapper.readValue(currencyRates,
				new TypeReference<List<CurrencyRateModel>>() {
				});
		return currencyRateModels;
	}

	public static CurrencyRatePath createCurrencyRatePathModel(CurrencyRatePath currencyRatePath,
			Map<String, String> currencyNameMaster) {
		String currencyCode = currencyRatePath.getCurrencyConversionPath().substring(
				currencyRatePath.getCurrencyConversionPath().lastIndexOf("|") + 1,
				currencyRatePath.getCurrencyConversionPath().length());
		currencyRatePath.setToCurrencyCode(currencyCode);
		currencyRatePath.setToCurrencyName(currencyNameMaster.get(currencyCode));
		return currencyRatePath;
	}

}
