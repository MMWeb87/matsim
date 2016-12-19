package org.matsim.contrib.carsharing.manager.supply.costs;

import org.matsim.contrib.carsharing.manager.demand.RentalInfo;
import org.matsim.contrib.carsharing.manager.supply.CarsharingSupplyInterface;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;

import com.google.inject.Inject;
/** 
 * @author balac
 */
public class CostCalculationBEV implements CostCalculation {

	@Inject CarsharingSupplyInterface supplyInterface;
	
	private final static double betaTT = 1.0;
	private final static double betaRentalTIme = 1.0;
	private final static double scaleTOMatchCar = 4.0;
	
	@Override
	public double getCost(RentalInfo rentalInfo, CSVehicle vehicle) {
		
		
	//	CSVehicle vehicle = supplyInterface.getAllVehicles().get(rentalInfo.getVehId().toString());
		
		double fuelCosts = 0.3;
		double timeCosts = 0.15;
		
		if(vehicle.getType().equals("ecar_highrange")){
			fuelCosts = 0.4;
			timeCosts = 0.25;
		} else if(vehicle.getType().equals("ecar_lowrange")){
			fuelCosts = 0.3;
			timeCosts = 0.15;
		}

		
		double rentalTIme = rentalInfo.getEndTime() - rentalInfo.getStartTime();
		double inVehicleTime = rentalInfo.getInVehicleTime();
		
		
		
		return CostCalculationBEV.scaleTOMatchCar * 
				(inVehicleTime /60.0 * timeCosts  + (rentalTIme - inVehicleTime) / 60.0 * fuelCosts);
	}

}
