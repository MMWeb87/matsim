package org.matsim.contrib.carsharing.stations;

import org.matsim.contrib.carsharing.vehicles.BEVehicle;

public class Charger {

	private String chargerID;
	private int power; //kW
	
	public Charger(String chargerID, int power) {
		this.chargerID = chargerID;
		this.power = power;
	}
	
	/**
	 * Charge for every tick
	 * @param vehicle
	 */
	public static BEVehicle chargeVehicle(BEVehicle vehicle){
		
		// charing function
		
		
		return vehicle;
		

		
	}

	/**
	 * @return the chargerID
	 */
	public String getChargerID() {
		return chargerID;
	}

	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	
	
	

}
