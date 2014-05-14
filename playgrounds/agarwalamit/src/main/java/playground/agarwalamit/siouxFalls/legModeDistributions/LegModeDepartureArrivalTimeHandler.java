/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2014 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package playground.agarwalamit.siouxFalls.legModeDistributions;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;

/**
 * @author amit
 * 
 * need to be tested first.
 */
public class LegModeDepartureArrivalTimeHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler {

	private final Logger logger = Logger.getLogger(LegModeDepartureArrivalTimeHandler.class);
	private Map<String, Map<Id, double[]>> mode2PersonId2ArrivalTime;
	private Map<String, Map<Id, double[]>> mode2PersonId2DepartureTime;

	public LegModeDepartureArrivalTimeHandler() {
		this.mode2PersonId2ArrivalTime = new HashMap<String, Map<Id,double[]>>();
		this.mode2PersonId2DepartureTime = new HashMap<String, Map<Id,double[]>>();
	}

	@Override
	public void reset(int iteration) {
		this.mode2PersonId2ArrivalTime.clear();
		this.mode2PersonId2DepartureTime.clear();
	}

	@Override
	public void handleEvent(PersonDepartureEvent event) {
		String legMode = event.getLegMode();
		Id personId = event.getPersonId();
		double departureTime =event.getTime();

		if(this.mode2PersonId2DepartureTime.containsKey(legMode)){
			Map<Id, double[]> personId2DepartureTime = this.mode2PersonId2DepartureTime.get(legMode);
			if(personId2DepartureTime.containsKey(personId)){
				double departureTimes [] = {personId2DepartureTime.get(personId)[0],0};
				departureTimes[1] = departureTime;
				personId2DepartureTime.put(personId, departureTimes);
			} else {
				double departureTimes [] = {departureTime,0};
				personId2DepartureTime.put(personId,departureTimes );
			}
		} else {
			Map<Id, double[]> personId2DepartureTime = new HashMap<Id, double[]>();
			double departureTimes [] = {departureTime,0};
			personId2DepartureTime.put(personId, departureTimes);
			this.mode2PersonId2DepartureTime.put(legMode, personId2DepartureTime);
		}
	}

	@Override
	public void handleEvent(PersonArrivalEvent event) {
		String legMode = event.getLegMode();
		Id personId = event.getPersonId();
		double arrivalTime =event.getTime();

		if(this.mode2PersonId2ArrivalTime.containsKey(legMode)){
			Map<Id, double[]> personId2ArrivalTime = this.mode2PersonId2ArrivalTime.get(legMode);
			if(personId2ArrivalTime.containsKey(personId)){
				double arrivalTimes [] = {personId2ArrivalTime.get(personId)[0],0};
				arrivalTimes[1] = arrivalTime;
				personId2ArrivalTime.put(personId, arrivalTimes);
			} else {
				double arrivalTimes [] = {arrivalTime,0};
				personId2ArrivalTime.put(personId,arrivalTimes );
			}
		} else {
			Map<Id, double[]> personId2ArrivalTime = new HashMap<Id, double[]>();
			double arrivalTimes [] = {arrivalTime,0};
			personId2ArrivalTime.put(personId, arrivalTimes);
			this.mode2PersonId2ArrivalTime.put(legMode, personId2ArrivalTime);
		}
	}

	public Map<String, Map<Id, double[]>> getLegMode2PersonId2DepartureTime(){
		return this.mode2PersonId2DepartureTime;
	}
	public Map<String, Map<Id, double[]>> getLegMode2PersonId2ArrivalTime(){
		return this.mode2PersonId2ArrivalTime;
	}
}
