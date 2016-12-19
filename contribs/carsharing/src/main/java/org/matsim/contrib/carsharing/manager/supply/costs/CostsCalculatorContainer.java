package org.matsim.contrib.carsharing.manager.supply.costs;

import java.util.HashMap;
import java.util.Map;

import org.matsim.contrib.carsharing.manager.demand.RentalInfo;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
/** 
 * @author balac
 */
public class CostsCalculatorContainer {
	
	
	private Map<String, CompanyCosts> companyCostsMap = new HashMap<String, CompanyCosts>();
	
	
		
	public Map<String, CompanyCosts> getCompanyCostsMap() {
		return this.companyCostsMap ;
	}



	public double getCost(String company, String carsharingType, RentalInfo rentalInfo, CSVehicle vehicle) {
		
		return this.companyCostsMap.get(company).calcCost(carsharingType, rentalInfo, vehicle);
	}
	

}
