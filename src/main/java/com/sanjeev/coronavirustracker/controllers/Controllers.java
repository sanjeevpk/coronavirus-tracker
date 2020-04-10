/**
 * 
 */
package com.sanjeev.coronavirustracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sanjeev.coronavirustracker.models.CountryStats;
import com.sanjeev.coronavirustracker.services.CoronaVirusDataService;

/**
 * @author Sanjeev Kulkarni
 *
 */

@Controller
public class Controllers {
	
	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<CountryStats> list = coronaVirusDataService.getCountryStatistics();
		model.addAttribute("countryStatistics", list);
		model.addAttribute("totalNewCases", coronaVirusDataService.getTotalNewCases());
		model.addAttribute("totalConfirmedCases", coronaVirusDataService.getTotalConfirmedCases());
		coronaVirusDataService.getTheRecoveredCasesData();
		model.addAttribute("totalRecoveredCases", coronaVirusDataService.getTotalRecoveredCases());
		return "home";
	}
}
