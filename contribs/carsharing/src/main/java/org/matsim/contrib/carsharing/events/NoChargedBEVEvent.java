package org.matsim.contrib.carsharing.events;

import java.util.LinkedHashMap;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.stations.CarsharingStation;

public class NoChargedBEVEvent extends Event{

	public static final String EVENT_TYPE = "no charged BEV available at station";
	
	private final String carsharingType;
	private final CarsharingStation station;
	private final String typeOfVehicle;
	private final double distance;
	
	
	public NoChargedBEVEvent(double time, String carsharingType, CarsharingStation station, String typeOfVehicle,
			double distance) {
		super(time);
		this.carsharingType = carsharingType;
		this.station = station;
		this.typeOfVehicle = typeOfVehicle;
		this.distance = distance;

		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Map<String, String> getAttributes() {
		Map<String, String> attr = new LinkedHashMap<String, String>();
		attr.put(ATTRIBUTE_TIME, Double.toString(super.getTime()));
		attr.put(ATTRIBUTE_TYPE, getEventType());
		attr.put(ATTRIBUTE_TYPE, this.getCarsharingType());
		attr.put(ATTRIBUTE_TYPE, Double.toString(this.getDistance()));
		
		return attr;
	}

	@Override
	public String getEventType() {
		return EVENT_TYPE;
	}
	

	public String getCarsharingType() {
		return this.carsharingType;
	}

	/**
	 * @return the station
	 */
	public CarsharingStation getStation() {
		return station;
	}

	/**
	 * @return the typeOfVehicle
	 */
	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	
	
}
