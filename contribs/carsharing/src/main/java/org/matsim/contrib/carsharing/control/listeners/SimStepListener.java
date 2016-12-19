package org.matsim.contrib.carsharing.control.listeners;

import java.util.HashSet;

import org.matsim.contrib.carsharing.stations.ChargingVehicles;
import org.matsim.contrib.carsharing.vehicles.StationBasedBEV;
import org.matsim.core.mobsim.framework.events.MobsimAfterSimStepEvent;
import org.matsim.core.mobsim.framework.listeners.MobsimAfterSimStepListener;



public class SimStepListener implements MobsimAfterSimStepListener {

	public SimStepListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notifyMobsimAfterSimStep(MobsimAfterSimStepEvent e) {
		
		
		// get all the vehicles that are currently being charged and charge em:
		
		HashSet<StationBasedBEV> vehicles = ChargingVehicles.getChargingVehicles();
		
		if(vehicles!=null)
		for(StationBasedBEV vehicle: vehicles){
			if(vehicle.getAttachedCharger() != null){
				vehicle.getAttachedCharger().chargeVehicle(vehicle);
			}
		}
		
		

		
	}

}
