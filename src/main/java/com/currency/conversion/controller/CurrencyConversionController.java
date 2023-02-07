package com.currency.conversion.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency.conversion.model.CurrencyRatePath;
import com.currency.conversion.service.CurrecyConversionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyConversionController {

	@Autowired
	private CurrecyConversionService currecyConversionService;

	@GetMapping("/get-conversion-rate-csv")
	public void getCurrencyConversionRates(HttpServletResponse httpServletResponse) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

		String fileName = "Currency-coversion-rate.csv";
		httpServletResponse.setContentType("text/csv");
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + fileName + "\"");
		
		StatefulBeanToCsv<CurrencyRatePath> writer = new StatefulBeanToCsvBuilder<CurrencyRatePath>(httpServletResponse.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.withOrderedResults(Boolean.TRUE)
				.build();
		
		writer.write(currecyConversionService.getAllCurrencyRates("CAD"));
		
	}

}
