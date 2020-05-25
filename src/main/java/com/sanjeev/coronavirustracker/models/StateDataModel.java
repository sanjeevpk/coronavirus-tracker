/**
 * 
 */
package com.sanjeev.coronavirustracker.models;

/**
 * @author Sanjeev
 *
 */
public class StateDataModel {
	private String state;
    private int latestCases;
    private int diffFromPrevDay;
    private boolean updated;
    private Double longitude;
    private Double latitude;
    private int death;
    private int recovery;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public int getRecovery() {
		return recovery;
	}
	public void setRecovery(int recovery) {
		this.recovery = recovery;
	}
	
	@Override
	public String toString() {
		return "StateDataModel [state=" + state + ", latestCases=" + latestCases + ", diffFromPrevDay="
				+ diffFromPrevDay + ", updated=" + updated + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", death=" + death + ", recovery=" + recovery + "]";
	}
}
