package org.matsim.contrib.carsharing.stations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.vehicles.BEVehicle;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
/** 
 * @author balac
 */

public class OneWayCarsharingStationWithCharger extends OneWayCarsharingStation implements CarsharingStation{

	private Charger charger;
	public OneWayCarsharingStationWithCharger(String stationId, Link link, Map<String, Integer> numberOfvehiclesPerType,
			Map<String, ArrayList<CSVehicle>> vehiclesPerType, int availableParkingSpots, Charger charger)/*int chargingMachine*/ {
		
		super(stationId, link, numberOfvehiclesPerType,	vehiclesPerType, availableParkingSpots);
		
		this.charger = charger;
	}

	@Override
	public void removeCar(CSVehicle vehicle) {
		
		super.removeCar(vehicle);
		// here?(BEVehicle)vehicle.removeCharger();		
		
	}
	
	// Marc: here we could make a function to check the charge of the vehicles in the Station. 
	
}