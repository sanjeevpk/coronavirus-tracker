/**
 * 
 */
package com.sanjeev.coronavirustracker.models;

/**
 * @author Sanjeev Kulkarni
 *
 */

public class CountryStats {
	private String country;
	private String state;
	private String totalReportedCases;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTotalReportedCases() {
		return totalReportedCases;
	}
	public void setTotalReportedCases(String totalReportedCases) {
		this.totalReportedCases = totalReportedCases;
	}
	
	@Override
	public String toString() {
		return "CountryStats [country=" + country + ", state=" + state + ", totalReportedCases=" + totalReportedCases
				+ "]";
	}
}
