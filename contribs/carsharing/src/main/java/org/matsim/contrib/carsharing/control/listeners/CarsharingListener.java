package org.matsim.contrib.carsharing.control.listeners;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Person;
import org.matsim.contrib.carsharing.events.handlers.NoChargedBEVHandler;
import org.matsim.contrib.carsharing.manager.demand.AgentRentals;
import org.matsim.contrib.carsharing.manager.demand.DemandHandler;
import org.matsim.contrib.carsharing.manager.demand.RentalInfo;
import org.matsim.contrib.carsharing.manager.supply.CarsharingSupplyInterface;
import org.matsim.contrib.carsharing.vehicles.BEVehicle;
import org.matsim.contrib.carsharing.vehicles.CSVehicle;
import org.matsim.core.controler.MatsimServices;
import org.matsim.core.controler.events.IterationEndsEvent;
import org.matsim.core.controler.listener.IterationEndsListener;
import org.matsim.core.utils.io.IOUtils;

/** 
 * 
 * @author balac
 */
public class CarsharingListener implements IterationEndsListener{

	@Inject private MatsimServices controler;
	@Inject private DemandHandler demandHandler;
	@Inject private NoChargedBEVHandler noChargedBEVHandler;

	@Inject private CarsharingSupplyInterface carsharingSupply;
	int frequency = 1;
	
	public CarsharingListener() {		
		
	}
	
	@Override
	public void notifyIterationEnds(IterationEndsEvent event) {
		// TODO Auto-generated method stub
	
		
		Map<Id<Person>, AgentRentals> agentRentalsMap = demandHandler.getAgentRentalsMap();
		final BufferedWriter outLink = IOUtils.getBufferedWriter(this.controler.getControlerIO().getIterationFilename(event.getIteration(), "CS.txt"));
		try {
			outLink.write("personID,carsharingType,startTime,endTIme,startLink,pickupLink,dropoffLink,endLink,distance,inVehicleTime,accessTime,egressTime,vehicleID,companyID,carsharingType, chargingLevel");
			outLink.newLine();		
		
		for (Id<Person> personId: agentRentalsMap.keySet()) {
			//outLink.write(personId + ",");
			
			
			
			for (RentalInfo i : agentRentalsMap.get(personId).getArr()) {
				
			
				
				CSVehicle vehicle = this.carsharingSupply.getAllVehicles().get(i.getVehId().toString());
								
				outLink.write(personId + "," + i.toString() + "," + vehicle.getCompanyId() + "," + vehicle.getType());
				if(vehicle instanceof BEVehicle)
					if (((BEVehicle) vehicle).getChargingLevels().containsKey(i.getEndLinkId()))
						outLink.write("," + ((BEVehicle) vehicle).getChargingLevels().get(i.getEndLinkId()).toString());
				
				
				outLink.newLine();
			}
			
		}
		
		outLink.flush();
		outLink.close();
		
		final BufferedWriter outLink2 = IOUtils.getBufferedWriter(this.controler.getControlerIO().getIterationFilename(event.getIteration(), "noChargedBEV.txt"));
		
		for(Double time: noChargedBEVHandler.getNoChargedBEVList()){
			outLink2.write(time.toString());
			outLink2.newLine();
		}
		outLink2.flush();
		outLink2.close();
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
