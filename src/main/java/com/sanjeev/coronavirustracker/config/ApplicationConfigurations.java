/**
 * 
 */
package com.sanjeev.coronavirustracker.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sanjeev.coronavirustracker.constants.Constants;
import com.sanjeev.coronavirustracker.services.CoronaVirusNewDataService;


/**
 * @author Sanjeev
 *
 */

@Component
public class ApplicationConfigurations {
	
	@Autowired
	CoronaVirusNewDataService service;
	
	@PostConstruct
    @Scheduled(cron = "0 */30 * ? * *")
    public void init() throws RuntimeException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.setData(Constants.CONFIRMED_CASES_SOURCE_DATA_URL);
                service.setDeathData(Constants.DEATH_CASES_SOURCE_DATA_URL);
                service.setRecoveryData(Constants.RECOVERED_CASES_SOURCE_DATA_URL);
                service.setCountryDataMap();
            }
        }).start();
    }
}
