package org.matsim.contrib.carsharing.events.handlers;


import org.matsim.contrib.carsharing.events.NoChargedBEVEvent;
import org.matsim.core.events.handler.EventHandler;

public interface NoChargedBEVEventHandler extends EventHandler {
	
	public void handleEvent (NoChargedBEVEvent event);
	
}