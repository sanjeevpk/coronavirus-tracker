/**
 * 
 */
package com.sanjeev.coronavirustracker.models;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Sanjeev
 *
 */
public class CountryDataModel {
	private String country;
    private List<StateDataModel> stateModelsList = new ArrayList<>();
    private int latestCases;
    private int diffFromPrevDay;
    private boolean updated;
    private Double longitude;
    private Double latitude;
    private int death;
    private int deathDiffFromPrevDay;
    private int recovery;
    private int recoveryDiffFromPrevDay;
    
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<StateDataModel> getStateModelsList() {
		return stateModelsList;
	}
	public void setStateModelsList(List<StateDataModel> stateModelsList) {
		this.stateModelsList = stateModelsList;
	}
	public int getLatestCases() {
		return latestCases;
	}
	public void setLatestCases(int latestCases) {
		this.latestCases = latestCases;
	}
	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}
	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
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
	public int getDeath() {
		return death;
	}
	public void setDeath(int death) {
		this.death = death;
	}
	public int getDeathDiffFromPrevDay() {
		return deathDiffFromPrevDay;
	}
	public void setDeathDiffFromPrevDay(int deathDiffFromPrevDay) {
		this.deathDiffFromPrevDay = deathDiffFromPrevDay;
	}
	public int getRecovery() {
		return recovery;
	}
	public void setRecovery(int recovery) {
		this.recovery = recovery;
	}
	public int getRecoveryDiffFromPrevDay() {
		return recoveryDiffFromPrevDay;
	}
	public void setRecoveryDiffFromPrevDay(int recoveryDiffFromPrevDay) {
		this.recoveryDiffFromPrevDay = recoveryDiffFromPrevDay;
	}
	
	@Override
	public String toString() {
		return "CountryDataModel [country=" + country + ", stateModelsList=" + stateModelsList + ", latestCases="
				+ latestCases + ", diffFromPrevDay=" + diffFromPrevDay + ", updated=" + updated + ", longitude="
				+ longitude + ", latitude=" + latitude + ", death=" + death + ", deathDiffFromPrevDay="
				+ deathDiffFromPrevDay + ", recovery=" + recovery + ", recoveryDiffFromPrevDay="
				+ recoveryDiffFromPrevDay + "]";
	}
}
