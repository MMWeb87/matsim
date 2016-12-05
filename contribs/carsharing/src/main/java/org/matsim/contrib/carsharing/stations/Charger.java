package org.matsim.contrib.carsharing.stations;

import org.matsim.contrib.carsharing.vehicles.BEVehicle;

public class Charger {

	private String chargerID;
	private double power; //kW
	
	public Charger(String chargerID, double power) {
		this.chargerID = chargerID;
		this.power = power;
	}
	
	/**
	 * Charge from specific charger for every tick
	 * @param vehicle
	 */
	public void chargeVehicle(BEVehicle vehicle){
		
		if(!vehicle.isFullyCharged()){
			// charing function: kW * tick[s] / 3600 -> kWh
			double charge = power * (1/3600);
			
			vehicle.charge(charge);
		}
		
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
	public double getPower() {
		return power;
	}
	
	
	

}
