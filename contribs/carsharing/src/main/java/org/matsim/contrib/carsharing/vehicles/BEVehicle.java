package org.matsim.contrib.carsharing.vehicles;

import java.util.HashMap;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.stations.Charger;

public interface BEVehicle{
	
	public double getChargingLevel(); //kWh
	public void setChargingLevel(double chargingLevel); //kWh

	public double getBatteryCapacity(); // kWh
	public double getEnergyConsumption(); // kWh/m
	
	public boolean isFullyCharged();
	public void charge(double charge);
	public void uncharge(double charge);
	
	public Charger getAttachedCharger();
	public void attachCharger(Charger attachedCharger);
	public void removeCharger();
	
	public void driveAndUncharge(Link link);
	
	public HashMap<Id<Link>,Double> getChargingLevels();

}
