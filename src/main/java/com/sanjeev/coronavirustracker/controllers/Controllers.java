/**
 * 
 */
package com.sanjeev.coronavirustracker.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sanjeev.coronavirustracker.models.CountryDataModel;
import com.sanjeev.coronavirustracker.services.CoronaVirusNewDataService;

/**
 * @author Sanjeev Kulkarni
 *
 */

@Controller
public class Controllers {
	
	@Autowired
	CoronaVirusNewDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		try {
            Map<String, CountryDataModel> dataMap = coronaVirusDataService.getCountryDataMap();
            List<CountryDataModel> countryStats = new ArrayList<>();
            for (Map.Entry<String, CountryDataModel> map : dataMap.entrySet()) {
                countryStats.add(map.getValue());
            }
            int totalReportedCases = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getLatestCases()).sum();
            int totalNewCases = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getDiffFromPrevDay()).sum();

            int totalDeaths = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getDeath()).sum();
            int totalDeathsToday = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getDeathDiffFromPrevDay()).sum();

            int totalRecovered = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getRecovery()).sum();
            int totalRecoveredToday = dataMap.entrySet().stream().mapToInt(stat -> stat.getValue().getRecoveryDiffFromPrevDay()).sum();
            
            model.addAttribute("locationsStats", countryStats);
            model.addAttribute("totalReportedCases", totalReportedCases);
            model.addAttribute("totalNewCases", totalNewCases);
            model.addAttribute("totalDeaths", totalDeaths);
            model.addAttribute("totalRecovered", totalRecovered);
            model.addAttribute("totalDeathsToday", totalDeathsToday);
            model.addAttribute("totalRecoveredToday", totalRecoveredToday);
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return "home";
	}
}
