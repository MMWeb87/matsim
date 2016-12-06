package org.matsim.contrib.carsharing.manager.supply;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.contrib.carsharing.stations.CarsharingStation;
import org.matsim.contrib.carsharing.stations.OneWayCarsharingStation;
import org.matsim.contrib.carsharing.vehicles.BEVehicle;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
import org.matsim.core.utils.collections.QuadTree;
import org.matsim.core.utils.geometry.CoordUtils;

/** 
 * @author balac
 */
public class OneWayContainer implements VehiclesContainer{	
	
	private QuadTree<CarsharingStation> owvehicleLocationQuadTree;	
	private Map<CSVehicle, Link> owvehiclesMap ;	
	
	public OneWayContainer(QuadTree<CarsharingStation> owvehicleLocationQuadTree2,
			Map<CSVehicle, Link> owvehiclesMap2) {
		this.owvehicleLocationQuadTree = owvehicleLocationQuadTree2;
		this.owvehiclesMap = owvehiclesMap2;
	}
	
	public double distance = 0; // Added by Marc

	public void reserveVehicle(CSVehicle vehicle) {
		Link link = this.owvehiclesMap.get(vehicle);
		Coord coord = link.getCoord();
		this.owvehiclesMap.remove(vehicle);			
		CarsharingStation station = owvehicleLocationQuadTree.getClosest(coord.getX(), coord.getY());
		
		((OneWayCarsharingStation)station).removeCar(vehicle);		
	}

	public void parkVehicle(CSVehicle vehicle, Link link) {
		Coord coord = link.getCoord();			
		owvehiclesMap.put(vehicle, link);			
		CarsharingStation station = owvehicleLocationQuadTree.getClosest(coord.getX(), coord.getY());
		((OneWayCarsharingStation)station).addCar(vehicle.getType(),  vehicle);	
		
	}	
	
	public void freeParkingSpot(Link link) {
		Coord coord = link.getCoord();
		OneWayCarsharingStation station = (OneWayCarsharingStation) this.owvehicleLocationQuadTree.getClosest(coord.getX(), coord.getY());

		station.freeParkingSpot();
	}

	@Override
	public Link getVehicleLocation(CSVehicle vehicle) {

		return this.owvehiclesMap.get(vehicle);
	}

	/* (non-Javadoc)
	 * @see org.matsim.contrib.carsharing.manager.supply.VehiclesContainer#findClosestAvailableVehicle(org.matsim.api.core.v01.network.Link, java.lang.String, double)
	 * @author: Marc Melliger (added BEV implementation)
	 */
	@Override
	public CSVehicle findClosestAvailableVehicle(Link startLink, String typeOfVehicle, double searchDstance) {


		//find the closest available car and reserve it (make it unavailable)
		//if no cars within certain radius return null
		Collection<CarsharingStation> location = 
				owvehicleLocationQuadTree.getDisk(startLink.getCoord().getX(), 
						startLink.getCoord().getY(), searchDstance);
		if (location.isEmpty()) return null;

		CarsharingStation closest = null;
		double closestFound = searchDstance;
		for(CarsharingStation station: location) {
			
			Coord coord = station.getLink().getCoord();
						
			if (CoordUtils.calcEuclideanDistance(startLink.getCoord(), coord) < closestFound 
					&& ((OneWayCarsharingStation)station).getNumberOfVehicles(typeOfVehicle) > 0) {
				
				// There is a station with Vehicles, need to check if there are not only uncharged BEV
				// which couldn't be reserved
				// TODO: also add to rejection rate
				// TODO: rule for charging on the way
					
				if(!hasOnlyUnchargedBEVehicles(station, typeOfVehicle, distance)){
					closest = station;
					closestFound = CoordUtils.calcEuclideanDistance(startLink.getCoord(), coord);
				}
				
			}
		}		
		if (closest != null) {
			// TODO: Implement critical condition for BEV. 
			// Idea: Choose car with sufficient costs that has lowest costs
			
			ArrayList<CSVehicle> potentialVehiclesToBeUsed = ((OneWayCarsharingStation)closest).getVehicles(typeOfVehicle);
			CSVehicle vehicleToBeUsed = null; // TODO: check if no errors
			
			for(CSVehicle vehicle: potentialVehiclesToBeUsed){
				
				// TODO: add costs decision for vehicle?
				if(vehicle instanceof BEVehicle){
					// BEV: need restriction

					if(sufficientlyChargedBEV(vehicle, distance)){
						vehicleToBeUsed = vehicle;
					}
				} else {
					// conventional vehicle -> no restrictions.
					vehicleToBeUsed = vehicle;
				}
				
			}
						
			return vehicleToBeUsed;
		}		
		return null;
		
		
	}

	/**
	 * Return true if there are only uncharged BEV Vehicles 
	 * @author Marc Melliger
	 * @param station
	 * @param typeOfVehicle
	 * @param distance
	 * @return
	 */
	private boolean hasOnlyUnchargedBEVehicles(CarsharingStation station, String typeOfVehicle, double distance) {
		
		int unchargedOrNonBEV = 0;
		int SufficientlyChargedBEVehicles = 0;
		
		ArrayList<CSVehicle> vehicles = ((OneWayCarsharingStation)station).getVehicles(typeOfVehicle);
		
		for (CSVehicle vehicle: vehicles){
			if(sufficientlyChargedBEV(vehicle, distance)){
				SufficientlyChargedBEVehicles++;
			}
			else {
				// no BEV or unchargeBEV
				unchargedOrNonBEV++;
			}
		}
		
		if(SufficientlyChargedBEVehicles == 0 && unchargedOrNonBEV == 0){
			// there are no sufficiently charge vehicles and no other cars -> only uncharged cars.
			return true;
		} else {
			return false;
		}
		
		
	}

	/**
	 * Checks if a BEV car cover a specific trip
	 * @author Marc Melliger
	 * @param vehicle Any vehicle
	 * @param distance The distance to the destination. 
	 * @return
	 */
	private boolean sufficientlyChargedBEV(CSVehicle vehicle, double distance) {
		
		if(vehicle instanceof BEVehicle){
			// kWh/km * km
			double chargeRequirement = ((BEVehicle) vehicle).getEnergyConsumption() * distance;
			
			// enough charge for this distance
			if(((BEVehicle) vehicle).getChargingLevel() > chargeRequirement){
				return true;
			} 
		} 
		
		return false;
	}

	@Override
	public Link findClosestAvailableParkingLocation(Link destinationLink, double searchDstance) {		

		//find the closest available parking space and reserve it (make it unavailable)
		//if there are no parking spots within search radius, return null		

		Collection<CarsharingStation> location = 
				this.owvehicleLocationQuadTree.getDisk(destinationLink.getCoord().getX(), 
						destinationLink.getCoord().getY(), searchDstance);
		if (location.isEmpty()) return null;

		CarsharingStation closest = null;
		double closestFound = searchDstance;
		for(CarsharingStation station: location) {
			
			Coord coord = station.getLink().getCoord();
			
			if (CoordUtils.calcEuclideanDistance(destinationLink.getCoord(), coord) < closestFound 
					&& ((OneWayCarsharingStation)station).getAvaialbleParkingSpots() > 0) {
				closest = station;
				closestFound = CoordUtils.calcEuclideanDistance(destinationLink.getCoord(), coord);
			}
		}
		
		return closest.getLink();
	}

	@Override
	public void reserveParking(Link destinationLink) {
		Coord coord = destinationLink.getCoord();

		OneWayCarsharingStation station = (OneWayCarsharingStation) this.owvehicleLocationQuadTree.getClosest(coord.getX(), coord.getY());

		((OneWayCarsharingStation)station).reserveParkingSpot();	
		
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
