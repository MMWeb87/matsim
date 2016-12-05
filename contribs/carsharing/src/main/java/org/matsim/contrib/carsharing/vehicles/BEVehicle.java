package org.matsim.contrib.carsharing.vehicles;

public interface BEVehicle{
	
	public double getChargingLevel(); //kWh
	public void setChargingLevel(double chargingLevel); //kWh

	public double getBatteryCapacity(); // kWh
	public double getEnergyConsumption(); // kWh/km
	
	public boolean isFullyCharged();
	public void charge(double charge);
	public void uncharge(double charge);


}
