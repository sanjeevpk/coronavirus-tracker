/**
 * 
 */
package com.sanjeev.coronavirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sanjeev.coronavirustracker.constants.Constants;
import com.sanjeev.coronavirustracker.models.CountryDataModel;
import com.sanjeev.coronavirustracker.models.StateDataModel;

/**
 * @author Sanjeev
 *
 */

@Service
public class CoronaVirusNewDataService {
	
	@Autowired
	RestTemplate restTemplate;
    
	private Map<String, CountryDataModel> countryDataMap = new TreeMap<>();
    private Map<String, CountryDataModel> newStats;

    public Map<String, CountryDataModel> getCountryDataMap() {
        return getSortedSet();
    }

    public void setData(String uri) {
        newStats = new TreeMap<>();
        for (Iterator<CSVRecord> it = parseCSVIterator(Constants.CONFIRMED_CASES_SOURCE_DATA_URL); it.hasNext(); ) {
            CSVRecord record = it.next();
            newStats.put(record.get(Constants.COUNTRY), setCoronaCountyModel(record));
        }
        countryDataMap.putAll(newStats);
    }

    public void setDeathData(String uri) {
        for (Iterator<CSVRecord> it = parseCSVIterator(Constants.DEATH_CASES_SOURCE_DATA_URL); it.hasNext(); ) {
            CSVRecord record = it.next();
            setCoronaCountryModelDeathData(record);
        }
    }

    private CountryDataModel setCoronaCountyModel(CSVRecord record) {
    	CountryDataModel model;
        int latestCase = (!record.get(record.size() - 1).isEmpty()) ?
                Integer.parseInt(record.get(record.size() - 1)) : 0;
        int prevDayCase = (!record.get(record.size() - 2).isEmpty()) ?
                Integer.parseInt(record.get(record.size() - 2)) : 0;
        if (newStats.containsKey(record.get(Constants.COUNTRY))) {
            model = newStats.get(record.get(Constants.COUNTRY));
            boolean valueUpdated = (!record.get(record.size() - 1).isEmpty());
            if (!valueUpdated) {
                model.setUpdated(valueUpdated);
            }
            if (valueUpdated) {
                model.setLatestCases(model.getLatestCases() + latestCase);
                model.setDiffFromPrevDay(model.getDiffFromPrevDay() + latestCase - prevDayCase);
            }
        } else {
            model = new CountryDataModel();
            model.setCountry(record.get(Constants.COUNTRY));
            boolean valueUpdated = (!record.get(record.size() - 1).isEmpty());
            model.setUpdated(valueUpdated);
            if (valueUpdated) {
                model.setLatestCases(latestCase);
                model.setDiffFromPrevDay(latestCase - prevDayCase);
            }

        }
        if (record.get(Constants.STATE).isEmpty()) {
            model.setLatitude(Double.parseDouble(record.get(Constants.LATITUDE)));
            model.setLongitude(Double.parseDouble(record.get(Constants.LONGITUDE)));
        } else {
            model.getStateModelsList().add(setCoronaStateModel(record));
        }
        return model;
    }

    private StateDataModel setCoronaStateModel(CSVRecord record) {
    	StateDataModel model = new StateDataModel();
        boolean valueUpdated = (!record.get(record.size() - 1).contentEquals(""));
        model.setUpdated(valueUpdated);
        if (valueUpdated) {
            int latestCase = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCase = Integer.parseInt(record.get(record.size() - 2));
            model.setLatestCases(latestCase);
            model.setDiffFromPrevDay(latestCase - prevDayCase);
        }
        model.setState(record.get(Constants.STATE));
        model.setLatitude(Double.parseDouble(record.get(Constants.LATITUDE)));
        model.setLongitude(Double.parseDouble(record.get(Constants.LONGITUDE)));
        return model;
    }

    private void setCoronaCountryModelDeathData(CSVRecord record) {
        CountryDataModel model = newStats.get(record.get(Constants.COUNTRY));
        int death = (!record.get(record.size() - 1).isEmpty()) ? Integer.parseInt(record.get(record.size() - 1)) : 0;
        int prevDeath = death - Integer.parseInt(record.get(record.size() - 2));
        if (model.getDeath() > 0) death += model.getDeath();
        if (model.getDeathDiffFromPrevDay() > 0) prevDeath += model.getDeathDiffFromPrevDay();
        model.setDeath(death);
        model.setDeathDiffFromPrevDay(prevDeath);
        if(!record.get(Constants.STATE).isEmpty()) setCoronaStateModelDeathData(record, model);
    }

    private void setCoronaStateModelDeathData(CSVRecord record, CountryDataModel model) {
        for (StateDataModel stateModel : model.getStateModelsList()) {
            if(record.get(Constants.STATE).contentEquals(stateModel.getState())) {
                int death = (!record.get(record.size() - 1).isEmpty()) ? Integer.parseInt(record.get(record.size() - 1)) : 0;
                stateModel.setDeath(death);
            }
        }
    }
    
    public void setRecoveryData(String uri) {
        for (Iterator<CSVRecord> it = parseCSVIterator(Constants.RECOVERED_CASES_SOURCE_DATA_URL); it.hasNext(); ) {
            CSVRecord record = it.next();
            setCoronaCountryModelRecoveryData(record);
        }
    }

    private void setCoronaCountryModelRecoveryData(CSVRecord record) {
        CountryDataModel model = newStats.get(record.get(Constants.COUNTRY));
        int recovery = (!record.get(record.size() - 1).isEmpty()) ? Integer.parseInt(record.get(record.size() - 1)) : 0;
        int prevRecovery = recovery - Integer.parseInt(record.get(record.size() - 2));
        if (model.getRecovery() > 0) recovery += model.getRecovery();
        if (model.getRecoveryDiffFromPrevDay() > 0) prevRecovery += model.getRecoveryDiffFromPrevDay();
        model.setRecovery(recovery);
        model.setRecoveryDiffFromPrevDay(prevRecovery);
        if(!record.get(Constants.STATE).isEmpty()) setCoronaStateModelRecoveryData(record, model);
    }

	private void setCoronaStateModelRecoveryData(CSVRecord record, CountryDataModel model) {
        for (StateDataModel stateModel : model.getStateModelsList()) {
            if(record.get(Constants.STATE).contentEquals(stateModel.getState())) {
                int recovery = (!record.get(record.size() - 1).isEmpty()) ? Integer.parseInt(record.get(record.size() - 1)) : 0;
                stateModel.setRecovery(recovery);
            }
        }
    }

	public void setCountryDataMap() {
        countryDataMap.putAll(newStats);
    }

    private Map<String, CountryDataModel> getSortedSet() {
        List<Map.Entry<String, CountryDataModel>> list =
                new LinkedList<>(countryDataMap.entrySet());

        Collections.sort(list, Comparator.comparing(o -> o.getValue().getCountry()));

        HashMap<String, CountryDataModel> sortedMap = new LinkedHashMap<String, CountryDataModel>();
        for (Map.Entry<String, CountryDataModel> map : list) {
            sortedMap.put(map.getKey(), map.getValue());
        }
        return sortedMap;
    }
    
    public Iterator<CSVRecord> parseCSVIterator(String uri) {
        Iterable<CSVRecord> records = null;
        try {
            StringReader csvReader = new StringReader(fetchVirusData(uri));
            records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records.iterator();
    }

	private String fetchVirusData(String uri) {
		ResponseEntity<String> confirmedResponse = restTemplate.getForEntity(uri, String.class);
		return confirmedResponse.getBody();
	}
}
