package org.matsim.contrib.carsharing.manager.supply;

import java.util.Map;
import java.util.Set;

import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
import org.matsim.core.api.experimental.events.EventsManager;

public interface CarsharingSupplyInterface {

	Map<CSVehicle, Link> getAllVehicleLocations();

	Map<String, CSVehicle> getAllVehicles();

	CompanyContainer getCompany(String companyId);

	CSVehicle getVehicleWithId(String vehicleId);

	CSVehicle findClosestAvailableVehicle(Link startLink, String carsharingType, String typeOfVehicle, String companyId,
			double searchDistance);

	Link findClosestAvailableParkingSpace(Link destinationLink, String carsharingType, String companyId,
			double searchDistance);

	void populateSupply();

	Set<String> getCompanyNames();
	
	// hack for BEV
	public void setDistance(double distance);
	public void setTime(double time);

	CSVehicle findClosestAvailableVehicleWithCharge(Link startLink, String carsharingType, String typeOfVehicle, String companyId,
			double searchDistance, double distance, double time, EventsManager eventsManager);

}