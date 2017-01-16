package org.matsim.contrib.carsharing.vehicles;

import java.util.HashMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.stations.Charger;
import org.matsim.contrib.carsharing.stations.ChargingVehicles;

/** 
 * Battery Electric Vehicle Object. 
 * @author balac, Marc Melliger
 */
public class StationBasedBEV extends StationBasedVehicle implements CSVehicle, BEVehicle{
	
	
	private double chargingLevel; //kWh
	private double energyConsumption; // kWh/m
	private double batteryCapacity; //kWh 
	private boolean fullyCharged;
	private Charger attachedCharger = null;
	private HashMap<Id<Link>,Double> chargingLevels;

	public StationBasedBEV(String vehicleType, String vehicleId, 
			String stationId, String csType, String companyId,
			double energyConsumption, double batteryCapacity) {
		
		super(vehicleType, vehicleId, stationId, csType, companyId);
		
		this.energyConsumption = energyConsumption;
		this.batteryCapacity = batteryCapacity;
		
		// fully charge all Vehicles in the beginning
		chargingLevel = batteryCapacity; 
		fullyCharged = true;
		
		chargingLevels = new HashMap<>();
		

	}	
	
	/**
	 * @return the chargingLevel
	 */
	@Override
	public double getChargingLevel() {
		return chargingLevel;
	}
	
	/**
	 * @param chargingLevel the chargingLevel to set
	 */
	@Override
	public void setChargingLevel(double chargingLevel) {
		
		if(chargingLevel >= 0 && chargingLevel <= batteryCapacity){
			this.chargingLevel = chargingLevel;
			fullyCharged = false;
		} else if(chargingLevel >= batteryCapacity) {
			this.chargingLevel = batteryCapacity;
			fullyCharged = true;
		} else if(chargingLevel < 0){
			this.chargingLevel = 0;
			fullyCharged = false;
		}
		
		
	}
	
	public HashMap<Id<Link>,Double> getChargingLevels() {
		return chargingLevels;
	}

	public void setChargingLevels(HashMap<Id<Link>,Double> chargingLevels) {
		this.chargingLevels = chargingLevels;
	}

	public void charge(double charge){
		setChargingLevel(getChargingLevel()+charge);
	}
	
	public void uncharge(double charge){
		setChargingLevel(getChargingLevel()-charge);
	}

	
	@Override
	public double getEnergyConsumption() {
		return energyConsumption;
	}


	@Override
	public double getBatteryCapacity() {
		return batteryCapacity;
	}


	public boolean isFullyCharged() {
		return fullyCharged;
	}

	public Charger getAttachedCharger() {
		return attachedCharger;
	}

	public void attachCharger(Charger attachedCharger) {
		this.attachedCharger = attachedCharger;
		ChargingVehicles.addToChargingVehicles(this);
	}

	public void removeCharger() {
		this.attachedCharger = null;
		ChargingVehicles.removeFromChargingVehicles(this);
	}
	
	/**
	 * @param distance Distance in meters
	 */
	public void driveAndUncharge(Link link){
		
		double distance = link.getLength();
		uncharge(energyConsumption * distance);
		
		//chargingLevels.put(link.getId(), chargingLevel);
		
		
	}

		
}
