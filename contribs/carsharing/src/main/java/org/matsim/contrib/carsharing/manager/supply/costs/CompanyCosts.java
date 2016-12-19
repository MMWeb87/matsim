package org.matsim.contrib.carsharing.manager.supply.costs;

import java.util.HashMap;
import java.util.Map;

import org.matsim.contrib.carsharing.manager.demand.RentalInfo;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
/** 
 * @author balac
 */
public class CompanyCosts {	
	
	private Map<String, CostCalculation> perTypeCostCalculator = new HashMap<String, CostCalculation>();
	
	public CompanyCosts(Map<String, CostCalculation> perTypeCostCalculator) {
		
		this.perTypeCostCalculator = perTypeCostCalculator;
	}
	
	public double calcCost(String carsharingType, RentalInfo rentalInfo, CSVehicle vehicle) {
		
		return this.perTypeCostCalculator.get(carsharingType).getCost(rentalInfo, vehicle);
	}

}
