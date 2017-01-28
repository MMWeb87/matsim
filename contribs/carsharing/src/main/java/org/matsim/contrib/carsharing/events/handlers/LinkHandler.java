package org.matsim.contrib.carsharing.events.handlers;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.manager.supply.CarsharingSupplyInterface;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
import org.matsim.contrib.carsharing.vehicles.StationBasedBEV;
import org.matsim.vehicles.Vehicle;

import com.google.inject.Inject;


public class LinkHandler implements LinkLeaveEventHandler {
	
	@Inject CarsharingSupplyInterface carsharingSupplyInterface;
	
	@Inject Scenario scenario;	

	@Override
	public void reset(int iteration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(LinkLeaveEvent event) {
		
		// Links
		Id<Link> LinkID = event.getLinkId();
		Link link = scenario.getNetwork().getLinks().get(LinkID);
		
		// Link length
		//double linkLength = link.getLength();
	
		// Vehicles	
		Id<Vehicle> vehicleID = event.getVehicleId();
		CSVehicle vehicleOnLink = carsharingSupplyInterface.getAllVehicles().get(vehicleID.toString());
		
		
		// Uncharge Vehicle
		if(vehicleOnLink instanceof StationBasedBEV){
			((StationBasedBEV)vehicleOnLink).driveAndUncharge(link);
		}
		
	}

}
