package org.matsim.contrib.carsharing.stations;

import java.util.ArrayList;
import java.util.Map;

import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.vehicles.BEVehicle;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
/** 
 * @author Marc Melliger
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
		if(((BEVehicle)vehicle).getAttachedCharger()!=null)
			((BEVehicle)vehicle).removeCharger();		
		
	}

	public Charger getCharger() {
		return charger;
	}

		
}