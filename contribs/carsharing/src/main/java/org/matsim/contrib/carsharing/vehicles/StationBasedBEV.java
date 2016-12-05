package org.matsim.contrib.carsharing.vehicles;
/** 
 * Battery Electric Vehicle Object. 
 * @author balac, Marc Melliger
 */
public class StationBasedBEV extends StationBasedVehicle implements CSVehicle, BEVehicle{
	
	
	private int chargingLevel = 0; //kWh
	private int energyConsumption; // kWh/km
	private int batteryCapacity; //kWh 

	public StationBasedBEV(String vehicleType, String vehicleId, 
			String stationId, String csType, String companyId,
			int energyConsumption, int batteryCapacity) {
		
		super(vehicleType, vehicleId, stationId, csType, companyId);
		
		this.energyConsumption = energyConsumption;
		this.batteryCapacity = batteryCapacity;
		

	}	
	
	/**
	 * @return the chargingLevel
	 */
	@Override
	public int getChargingLevel() {
		return chargingLevel;
	}
	
	/**
	 * @param chargingLevel the chargingLevel to set
	 */
	@Override
	public void setChargingLevel(int chargingLevel) {
		
		// No higher levels than the batteryCapacity are allowed
		if(chargingLevel <= batteryCapacity ){
			this.chargingLevel = chargingLevel;
		} else {
			this.chargingLevel = batteryCapacity;
		}
	}

	
	@Override
	public int getEnergyConsumption() {
		return energyConsumption;
	}


	@Override
	public int getBatteryCapacity() {
		return batteryCapacity;
	}



		
}
