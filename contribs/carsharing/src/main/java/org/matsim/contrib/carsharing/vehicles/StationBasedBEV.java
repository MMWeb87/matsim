package org.matsim.contrib.carsharing.vehicles;

import org.matsim.contrib.carsharing.stations.Charger;

/** 
 * Battery Electric Vehicle Object. 
 * @author balac, Marc Melliger
 */
public class StationBasedBEV extends StationBasedVehicle implements CSVehicle, BEVehicle{
	
	
	private double chargingLevel; //kWh
	private double energyConsumption; // kWh/km
	private double batteryCapacity; //kWh 
	private boolean fullyCharged;
	private Charger attachedCharger = null;

	public StationBasedBEV(String vehicleType, String vehicleId, 
			String stationId, String csType, String companyId,
			double energyConsumption, double batteryCapacity) {
		
		super(vehicleType, vehicleId, stationId, csType, companyId);
		
		this.energyConsumption = energyConsumption;
		this.batteryCapacity = batteryCapacity;
		
		// fully charge all Vehicles in the beginning
		chargingLevel = batteryCapacity; 
		fullyCharged = true;
		

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
	}

	public void removeCharger() {
		this.attachedCharger = null;
	}
	
	/**
	 * @param distance Distance in km's
	 */
	public void driveAndUncharge(double distance){
		
		// TODO: find out if meters of km.
		uncharge(energyConsumption * distance);
		
	}

		
}
