/**
 * 
 */
package com.sanjeev.coronavirustracker.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjeev Kulkarni
 *
 */

public class CountryStats {
	private String country;
	private String state;
	private String totalReportedCases;
	private String differenceFromPrevDay;
	private String totalNewCases;
	private String totalConfirmedCases;
	private String totalRecoveredCases;
	private int differenceFromPreRecoveredCases;
	private List<StateStats> stateStats = new ArrayList<>();
	private boolean updated;
	private Double longitude;
	private Double latitude;
	
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
	
	public String getDifferenceFromPrevDay() {
		return differenceFromPrevDay;
	}
	public void setDifferenceFromPrevDay(String differenceFromPrevDay) {
		this.differenceFromPrevDay = differenceFromPrevDay;
	}
	
	public String getTotalNewCases() {
		return totalNewCases;
	}
	public void setTotalNewCases(String totalNewCases) {
		this.totalNewCases = totalNewCases;
	}
	
	public String getTotalConfirmedCases() {
		return totalConfirmedCases;
	}
	public void setTotalConfirmedCases(String totalConfirmedCases) {
		this.totalConfirmedCases = totalConfirmedCases;
	}

	public String getTotalRecoveredCases() {
		return totalRecoveredCases;
	}
	public void setTotalRecoveredCases(String totalRecoveredCases) {
		this.totalRecoveredCases = totalRecoveredCases;
	}
	public int getDifferenceFromPreRecoveredCases() {
		return differenceFromPreRecoveredCases;
	}
	public void setDifferenceFromPreRecoveredCases(int differenceFromPreRecoveredCases) {
		this.differenceFromPreRecoveredCases = differenceFromPreRecoveredCases;
	}
	
	public List<StateStats> getStateStats() {
		return stateStats;
	}
	public void setStateStats(List<StateStats> stateStats) {
		this.stateStats = stateStats;
	}
	
	public boolean isUpdated() {
		return updated;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		return "CountryStats [country=" + country + ", state=" + state + ", totalReportedCases=" + totalReportedCases
				+ ", differenceFromPrevDay=" + differenceFromPrevDay + ", totalNewCases=" + totalNewCases
				+ ", totalConfirmedCases=" + totalConfirmedCases + ", totalRecoveredCases=" + totalRecoveredCases
				+ ", differenceFromPreRecoveredCases=" + differenceFromPreRecoveredCases + "]";
	}
}
