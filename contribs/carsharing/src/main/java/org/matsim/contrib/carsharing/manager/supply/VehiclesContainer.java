package org.matsim.contrib.carsharing.manager.supply;

import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
import org.matsim.core.api.experimental.events.EventsManager;
/** 
 * @author balac
 */
public interface VehiclesContainer {
	
	public void reserveVehicle(CSVehicle vehicle);
	public void parkVehicle(CSVehicle vehicle, Link link);
	public Link getVehicleLocation(CSVehicle vehicle);
	public CSVehicle findClosestAvailableVehicle(Link startLink, String typeOfVehicle, double searchDistance);
	public CSVehicle findClosestAvailableVehicleWithCharge(Link startLink, String typeOfVehicle, double searchDstance,
			double distance, double time, EventsManager eventsManager);

	public Link findClosestAvailableParkingLocation(Link destinationLink, double searchDistance);
	public void reserveParking(Link destinationLink);
	
	
}
