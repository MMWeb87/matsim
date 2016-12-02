package org.matsim.contrib.carsharing.vehicles;
/** 
 * @author balac, Marc Melliger
 */
public class StationBasedVehicle implements CSVehicle{
	
	private String type;
	private String vehicleId;
	private String stationId;
	private String csType;
	private String companyId;
	
	private String range;
	private String chargingLevel;
	private String energyConsumption; // Decide how we represent range, and rest of range...

	public StationBasedVehicle(String vehicleType, String vehicleId, 
			String stationId, String csType, String companyId) {
		
		this.type = vehicleType;
		this.vehicleId = vehicleId;
		this.stationId = stationId;
		this.csType = csType;
		this.companyId = companyId;

	}	
	@Override
	public String getVehicleId() {
		return vehicleId;
	}
	
	public String getStationId() {
		return stationId;
	}
	@Override
	public String getType() {
		return type;
	}
	@Override
	public String getCsType() {
		return csType;
	}
	@Override
	public String getCompanyId() {
		return companyId;
	}	

	
	public String getRange() {
		return range;
	}	
	public String getChargingLevel() {
		return chargingLevel;
	}	
	public String getEnergyConsumption() {
		return energyConsumption;
	}		
}
