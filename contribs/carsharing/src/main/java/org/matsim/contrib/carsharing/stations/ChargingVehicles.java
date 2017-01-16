package org.matsim.contrib.carsharing.stations;

import java.util.HashSet;

import org.matsim.contrib.carsharing.vehicles.StationBasedBEV;

/**
 * This class with static methods stores all the charged vehicles.
 * @author marc
 * 
 */
public class ChargingVehicles {
	
	private static HashSet<StationBasedBEV> chargingVehicles;
	
	public static void addToChargingVehicles(StationBasedBEV vehicle){
		
		if(chargingVehicles==null){
			chargingVehicles = new HashSet<>();
		}
		
		chargingVehicles.add(vehicle);
	}
	
	public static void removeFromChargingVehicles(StationBasedBEV vehicle){
		chargingVehicles.remove(vehicle);
	}
	
	public static HashSet<StationBasedBEV> getChargingVehicles(){
		return chargingVehicles;
	}
	

}
