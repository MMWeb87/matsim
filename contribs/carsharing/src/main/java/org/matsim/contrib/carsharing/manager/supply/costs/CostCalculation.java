package org.matsim.contrib.carsharing.manager.supply.costs;

import org.matsim.contrib.carsharing.manager.demand.RentalInfo;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
/** 
 * @author balac
 */
public interface CostCalculation {
	
	public double getCost(RentalInfo rentalInfo, CSVehicle vehicle);

}
