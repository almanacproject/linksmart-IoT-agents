package it.ismb.pertlab.pwal.api.devices.interfaces;

import it.ismb.pertlab.pwal.api.devices.model.Location;
import it.ismb.pertlab.pwal.api.devices.model.Unit;

public interface Device {
	
	/**
	 * The identification given by the PWAL
	 */
	String getPwalId();
	/**
	 * The identification given by the PWAL
	 */
	void setPwalId(String pwalId);
	/**
	 * The identification inside the PWAL manager
	 */
	String getId();
	/**
	 * The identification inside the PWAL manager
	 */
	void setId(String id);
	/**
	 * Points the device taxonomy (es. Thermometer)
	 */
	String getType();
	/***
	 * Sensors position details
	 */
	Double getLatitude();
	void setLatitude(Double latitude);
	
	Double getLongitude();
	void setLongitude(Double longitude);
	/***
	 * Information about sensor's network
	 */
	String getNetworkType();
/**
	 * Returns the time when the current value has been
	 * updated the last time
	 * 
	 * @return last update
	 */
	String getUpdatedAt();
	/**
	 * Sets the last time when the current value has been updated
	 * 
	 * @param updatedAt
	 *             time to set
	 */
	void setUpdatedAt(String updatedAt);
	/**
	 * Returns the location of the device
	 * 
	 * @return the location of the device
	 */
	Location getLocation();
	/**
	 * Sets the location of the device
	 * 
	 * @param location
	 *             location to set
	 */
	void setLocation(Location location);
	/**
	 * Returns the unit of the measure done by the device
	 * 
	 * @return the unit of the measure
	 */
	Unit getUnit();
	/**
	 * Sets the unit of the measure done by the device
	 * 
	 * @param 
	 * 				the unit to set
	 */
	void setUnit(Unit unit);

}
