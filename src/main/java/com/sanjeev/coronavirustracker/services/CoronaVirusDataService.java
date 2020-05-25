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
import com.sanjeev.coronavirustracker.models.StateStats;



/**
 * @author Sanjeev Kulkarni
 *
 */

@Service
public class CoronaVirusDataService {

	@Autowired
	RestTemplate restTemplate;

	public static String CONFIRMED_CASES_SOURCE_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	public static String RECOVERED_CASES_SOURCE_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
	public static String DEATH_CASES_SOURCE_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

	List<CountryStats> countryStatistics = new ArrayList<>();
	long totalNewCases = 0;
	long totalConfirmedCases = 0;
	long totalRecoveredCases = 0;
	long totalDeathCases = 0;

	public List<CountryStats> getCountryStatistics() {
		return countryStatistics;
	}

	public void setCountryStatistics(List<CountryStats> countryStatistics) {
		this.countryStatistics = countryStatistics;
	}

	public long getTotalNewCases() {
		return totalNewCases;
	}

	public void setTotalNewCases(long totalNewCases) {
		this.totalNewCases = totalNewCases;
	}

	public long getTotalConfirmedCases() {
		return totalConfirmedCases;
	}

	public void setTotalConfirmedCases(long totalConfirmedCases) {
		this.totalConfirmedCases = totalConfirmedCases;
	}

	public long getTotalRecoveredCases() {
		return totalRecoveredCases;
	}

	public void setTotalRecoveredCases(long totalRecoveredCases) {
		this.totalRecoveredCases = totalRecoveredCases;
	}

	public long getTotalDeathCases() {
		return totalDeathCases;
	}

	public void setTotalDeathCases(long totalDeathCases) {
		this.totalDeathCases = totalDeathCases;
	}

	@PostConstruct
	@Scheduled(cron = "* * * 1 * *")
	public void getTheConfirmedCasesData() {
		List<CountryStats> latestCountryStatistics = new ArrayList<>();

		ResponseEntity<String> confirmedResponse = restTemplate.getForEntity(CONFIRMED_CASES_SOURCE_DATA_URL,
				String.class);
		StringReader confirmedStringReader = new StringReader(confirmedResponse.getBody());

		ResponseEntity<String> recoveredResponse = restTemplate.getForEntity(RECOVERED_CASES_SOURCE_DATA_URL,
				String.class);
		StringReader recoveredStringReader = new StringReader(recoveredResponse.getBody());

		ResponseEntity<String> deathResponse = restTemplate.getForEntity(DEATH_CASES_SOURCE_DATA_URL, String.class);
		StringReader deathStringReader = new StringReader(deathResponse.getBody());

		Iterable<CSVRecord> confirmedRecords;
		Iterable<CSVRecord> recoveredRecords;
		Iterable<CSVRecord> deathRecords;
		Long totalNewCases = (long) 0;
		Long totalConfirmedCases = (long) 0;
		try {
			confirmedRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(confirmedStringReader);
			recoveredRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(recoveredStringReader);
			deathRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(deathStringReader);

			for (CSVRecord confirmedRecord : confirmedRecords) {
				CountryStats countryStats = new CountryStats();
				
				String country = confirmedRecord.get("Country/Region");
				String countrywiseTotalCases = confirmedRecord.get(confirmedRecord.size() - 1);

				countryStats.setCountry(country);
				countryStats.setTotalReportedCases(countrywiseTotalCases);
				countryStats.setDifferenceFromPrevDay(String.valueOf(Integer.parseInt(countrywiseTotalCases)
						- Integer.parseInt(confirmedRecord.get(confirmedRecord.size() - 2))));
				
				System.out.println(countryStats);
				String state = confirmedRecord.get("Province/State");
				if (!state.isEmpty()) 
					countryStats.getStateStats().add(setCoronaStateModel(confirmedRecord));
				latestCountryStatistics.add(countryStats);
			}
				
				
				
				
				
				/*
//				for (CSVRecord recoveredRecord : recoveredRecords) {
//					for (CSVRecord deathRecord : deathRecords) {
//					if (confirmedRecord.get("Country/Region").trim()
//							.equalsIgnoreCase(recoveredRecord.get("Country/Region").trim())) {
//								&& recoveredRecord.get("Country/Region").equalsIgnoreCase(deathRecord.get("Country/Region"))) {
//						break;
//						}
						String state = confirmedRecord.get("Province/State");

						String country = confirmedRecord.get("Country/Region");
						String countrywiseTotalCases = confirmedRecord.get(confirmedRecord.size() - 1);

						CountryStats countryStats = new CountryStats();
						countryStats.setCountry(country);
						// countryStats.setState(state);
						countryStats.setTotalReportedCases(countrywiseTotalCases);
						countryStats.setDifferenceFromPrevDay(String.valueOf(Integer.parseInt(countrywiseTotalCases)
								- Integer.parseInt(confirmedRecord.get(confirmedRecord.size() - 2))));
//						countryStats.setTotalRecoveredCases(recoveredRecord.get(recoveredRecord.size() - 1));
//						String countrywiseRecoveredCases = recoveredRecord.get(recoveredRecord.size() - 1);
//						String countrywiseRecoveredCases2 = recoveredRecord.get(recoveredRecord.size() - 2);
//						countryStats.setDifferenceFromPreRecoveredCases(Integer.parseInt(countrywiseRecoveredCases)
//								- Integer.parseInt(countrywiseRecoveredCases2));

						*//**
						 * Total New Cases
						 *//*
						totalNewCases = totalNewCases + Long.parseLong(countryStats.getDifferenceFromPrevDay());
						countryStats.setTotalNewCases(totalNewCases.toString());
						this.totalNewCases = totalNewCases;

						*//**
						 * Total Confirmed Cases
						 *//*
						totalConfirmedCases = totalConfirmedCases
								+ Long.parseLong(countryStats.getTotalReportedCases());
						countryStats.setTotalConfirmedCases(totalConfirmedCases.toString());
						this.totalConfirmedCases = totalConfirmedCases;
						System.out.println(countryStats);

//						if (state.isEmpty()) {
							latestCountryStatistics.add(countryStats);
//						}
//						break;
//						}
//					}
				}*/
//			}
			this.countryStatistics = latestCountryStatistics;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private StateStats setCoronaStateModel(CSVRecord record) {
        StateStats model = new StateStats();
        boolean valueUpdated = (!record.get(record.size() - 1).contentEquals(""));
//        model.setUpdated(valueUpdated);
//        if (valueUpdated) {
            int latestCase = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCase = Integer.parseInt(record.get(record.size() - 2));
            model.setTotalConfirmedCases(String.valueOf(latestCase));
            model.setDifferenceFromPrevDay(String.valueOf(latestCase - prevDayCase));
//        }
        model.setState(record.get("Province/State"));
//        model.setLatitude(Double.parseDouble(record.get(LATITUDE)));
//        model.setLongitude(Double.parseDouble(record.get(LONGITUDE)));
        return model;
    }

	@PostConstruct
	@Scheduled(cron = "* * * 1 * *")
	public void getTheRecoveredCasesData() {
		List<CountryStats> latestCountryStatistics = new ArrayList<>();
		Iterable<CSVRecord> recoveredRecords;
		Long totalNewCases = (long) 0;
		Long totalConfirmedCases = (long) 0;
		Long totalRecoveredCases = (long) 0;

		try {
			ResponseEntity<String> recoveredResponse = restTemplate.getForEntity(RECOVERED_CASES_SOURCE_DATA_URL,
					String.class);
			StringReader recoveredStringReader = new StringReader(recoveredResponse.getBody());
			recoveredRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(recoveredStringReader);

			for (CSVRecord recoveredRecord : recoveredRecords) {
				String state = recoveredRecord.get("Province/State");
				String country = recoveredRecord.get("Country/Region");
				String countrywiseTotalCases = recoveredRecord.get(recoveredRecord.size() - 1);

				CountryStats countryStats = new CountryStats();
				countryStats.setCountry(country);
				countryStats.setTotalReportedCases(countrywiseTotalCases);
				countryStats.setTotalRecoveredCases(countrywiseTotalCases);
				countryStats.setDifferenceFromPrevDay(String.valueOf(Integer.parseInt(countrywiseTotalCases)
						- Integer.parseInt(recoveredRecord.get(recoveredRecord.size() - 2))));
//				countryStats.setTotalRecoveredCases(recoveredRecord.get(recoveredRecord.size() - 1));
//				String countrywiseRecoveredCases = recoveredRecord.get(recoveredRecord.size() - 1);
//				String countrywiseRecoveredCases2 = recoveredRecord.get(recoveredRecord.size() - 2);
//				countryStats.setDifferenceFromPreRecoveredCases(Integer.parseInt(countrywiseRecoveredCases)
//						- Integer.parseInt(countrywiseRecoveredCases2));
				
				

				/**
				 * Total New Cases
				 */
				totalNewCases = totalNewCases + Long.parseLong(countryStats.getDifferenceFromPrevDay());
				countryStats.setTotalNewCases(totalNewCases.toString());
				this.totalNewCases = totalNewCases;

				/**
				 * Total Confirmed Cases
				 */
				totalConfirmedCases = totalConfirmedCases
						+ Long.parseLong(countryStats.getTotalReportedCases());
				countryStats.setTotalConfirmedCases(totalConfirmedCases.toString());
				this.totalConfirmedCases = totalConfirmedCases;
				
				totalRecoveredCases = totalRecoveredCases + Long.parseLong(countryStats.getTotalRecoveredCases());
				countryStats.setTotalRecoveredCases(totalRecoveredCases.toString());
				this.totalRecoveredCases = totalRecoveredCases;

				System.out.println(countryStats);

//				if (state.isEmpty()) {
					latestCountryStatistics.add(countryStats);
//				}
			}
			this.countryStatistics = latestCountryStatistics;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
