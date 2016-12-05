package org.matsim.contrib.carsharing.vehicles;

public interface BEVehicle {
	
	public int getChargingLevel(); //kWh
	public void setChargingLevel(int chargingLevel); //kWh

	public int getBatteryCapacity(); // kWh
	public int getEnergyConsumption(); // kWh/km

}
