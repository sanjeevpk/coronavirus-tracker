/**
 * 
 */
package com.sanjeev.coronavirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sanjeev.coronavirustracker.models.CountryStats;

/**
 * @author Sanjeev Kulkarni
 *
 */

@Service
public class CoronaVirusDataService {

	@Autowired
	RestTemplate restTemplate;
	
	public static String SOURCE_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	
	List<CountryStats> countryStatistics = new ArrayList<>();
	
	public List<CountryStats> getCountryStatistics() {
		return countryStatistics;
	}

	public void setCountryStatistics(List<CountryStats> countryStatistics) {
		this.countryStatistics = countryStatistics;
	}

	@PostConstruct
	@Scheduled(cron = "* * * 1 * *")
	public void getTheConfirmedCasesData() {
		List<CountryStats> latestCountryStatistics = new ArrayList<>();
		
		ResponseEntity<String> response = restTemplate.getForEntity(SOURCE_DATA_URL, String.class);
		
		StringReader stringReader = new StringReader(response.getBody());
		
		Iterable<CSVRecord> records;
		try {
			records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(stringReader);
			for (CSVRecord record : records) {
			    String state = record.get("Province/State");
			    String country = record.get("Country/Region");
			    String totalConfirmedCases = record.get(record.size() -1);
			    
			    CountryStats countryStats = new CountryStats();
			    countryStats.setCountry(country);
			    countryStats.setState(state);
			    countryStats.setTotalReportedCases(totalConfirmedCases);
			    System.out.println(countryStats);
			    latestCountryStatistics.add(countryStats);
			}
			this.countryStatistics = latestCountryStatistics;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
