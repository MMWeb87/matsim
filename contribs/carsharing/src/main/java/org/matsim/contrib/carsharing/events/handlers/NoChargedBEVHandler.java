package org.matsim.contrib.carsharing.events.handlers;

import java.util.ArrayList;

import org.matsim.api.core.v01.Scenario;
import org.matsim.contrib.carsharing.events.NoChargedBEVEvent;
import org.matsim.core.api.experimental.events.EventsManager;

import com.google.inject.Inject;
/** 
 * 
 * @author marcmel
 */
public class NoChargedBEVHandler implements NoChargedBEVEventHandler {	

	@Inject EventsManager eventsManager;
	@Inject Scenario scenario;

	private ArrayList<Double> noChargedBEVList = new ArrayList<>();
	
	@Override
	public void reset(int iteration) {
		

		noChargedBEVList = new ArrayList<>(); 
	}

	@Override
	public void handleEvent(NoChargedBEVEvent event) {
		// TODO Auto-generated method stub
		noChargedBEVList.add(event.getTime());
		
	}

	/**
	 * @return the noChargedBEVList
	 */
	public ArrayList<Double> getNoChargedBEVList() {
		return noChargedBEVList;
	}	
}
