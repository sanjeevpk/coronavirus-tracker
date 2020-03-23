/**
 * 
 */
package com.sanjeev.coronavirustracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
		model.addAttribute("countryStatistics", coronaVirusDataService.getCountryStatistics());
		return "home";
	}
}
