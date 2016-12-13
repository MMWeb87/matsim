package utils.carsharing.inputcreation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.gbl.MatsimRandom;
import org.matsim.core.scenario.MutableScenario;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.collections.Tuple;
import org.matsim.core.utils.io.MatsimXmlWriter;

public class PlaceStationsVehicles extends MatsimXmlWriter {
	
	private Scenario scenario;
	private static int counter = 0;
	private static int counterOW = 1;
	private static int counterch = 1;
	private static int numberOfStations = 500; // TODO: maybe add as share of total links?
	
	private static int freeparking = 2;
	
	// EV additions
	private static int lowBattCapa = 20;
	private static int highBattCapa = 60;
	private static double lowEnCons = 0.17; // kWh/km
	private static double highEnCons = 0.24; // kWh/km
	private static double chargerPower = 3.3;

	double shareOfBEV = 0.7; // Format: 0-1
	double shareOfChargersTotal = 0.8; // Format: 0-1
	double shareOfChargersPerStation = 0.5; // Format: 0-1
	int maxNumberOfCarsPerStation = 3;

	
	public PlaceStationsVehicles(Scenario scenario) {
		
		this.scenario = scenario;
	}

	public void write(String file) {
		
		Network network = this.scenario.getNetwork();
		
		Object[] array = network.getLinks().values().toArray();
		
		int numberLinks = array.length;
		Random r = new Random(456);
		
		
		openFile(file);
		
		writeXmlHead();
		List<Tuple<String, String>> attsC = new ArrayList<Tuple<String, String>>();
		
		attsC.add(new Tuple<>("name", "BEVRule"));
		writeStartTag("companies", null);
		writeStartTag("company", attsC);
		for (int i = 1; i <= numberOfStations; i++) {
			Link link = (Link) array[r.nextInt(numberLinks)];

			writeStation("oneway", link, i);
		}
		
		writeEndTag("company");
		writeEndTag("companies");

		
		close();
	}
	
	
//	private void writeVehicles() {
//
//		Network network = this.scenario.getNetwork();
//		
//		int cars = numberOfStations; // Is this the number of cars?
//		Object[] array = network.getLinks().values().toArray();
//		
//		int numberLinks = array.length;
//		Random r = new Random(456);
//		
//		for (int i = 0; i < cars; i++) {
//			
//			Link link = (Link) array[r.nextInt(numberLinks)];
//			writeVehicle(link, counter, r);
//			counter++;
//		}		
//	}

//	private void writeVehicle(Link link, int id, Random random) {
//
//		
//		List<Tuple<String, String>> attsV = new ArrayList<Tuple<String, String>>();
//		
//		attsV.add(new Tuple<>("id", "OW_" + Integer.toString(id)));
//		attsV.add(new Tuple<>("x", Double.toString(link.getCoord().getX())));
//		attsV.add(new Tuple<>("y", Double.toString(link.getCoord().getY())));
//		attsV.add(new Tuple<>("freeparking", Integer.toString(freeparking))); // added by Elyas
//		
//		// not correct: was related to the freefloating
//	/*	if (random.nextDouble() < 0.9)
//		
//			attsV.add(new Tuple<>("type", "car"));
//		else
//			attsV.add(new Tuple<>("type", "transporter"));
//			*/ 
//
//		writeStartTag("oneway", attsV, true);		
//	}
//	
	private void writeStation(String type, Link link, int id) {
		
		Random random = MatsimRandom.getRandom();
		
		List<Tuple<String, String>> attsV = new ArrayList<Tuple<String, String>>();
		
		attsV.add(new Tuple<>("id", Integer.toString(id)));
		attsV.add(new Tuple<>("x", Double.toString(link.getCoord().getX())));
		attsV.add(new Tuple<>("y", Double.toString(link.getCoord().getY())));
		attsV.add(new Tuple<>("freeparking", Integer.toString(freeparking))); // added by Elyas


		writeStartTag("oneway", attsV); // 
		
		int numberOfVehicles = random.nextInt(maxNumberOfCarsPerStation) + 1; // The number of vehicles
		
		ArrayList<List<Tuple<String, String>>> electroVehicles = new ArrayList<>();
		ArrayList<List<Tuple<String, String>>> normalVehicles = new ArrayList<>();			
		ArrayList<List<Tuple<String, String>>> chargingStations = new ArrayList<>();
		
		for (int i = 1; i <= numberOfVehicles; i++) {
			
			List<Tuple<String, String>> atts = new ArrayList<Tuple<String, String>>();
			

			
			
			
			atts.add(new Tuple<>("vehicleID", "OW_" + Integer.toString(counterOW++)));
			
			
			
			// Decide if to use a conventional or e-vehicle
			
			if (random.nextDouble() < shareOfBEV){
				
				atts.add(new Tuple<>("type", "ecar"));

				if (random.nextDouble() < 0.5){
					atts.add(new Tuple<>("batteryCapacity", Double.toString(highBattCapa)));
					atts.add(new Tuple<>("energyConsumption", Double.toString(highEnCons/1000)));
				} else {
					atts.add(new Tuple<>("batteryCapacity", Double.toString(lowBattCapa)));
					atts.add(new Tuple<>("energyConsumption", Double.toString(lowEnCons/1000)));
				}
				
				electroVehicles.add(atts);
				
			} else {
				atts.add(new Tuple<>("type", "car"));
				normalVehicles.add(atts);

			}
			
			// Add a new charger with probability "shareOfChargers"
			if (random.nextDouble() < shareOfChargersTotal * shareOfChargersPerStation){
			
				List<Tuple<String, String>> attsc = new ArrayList<Tuple<String, String>>();
				attsc.add(new Tuple<>("chargerID", "ch_"+ Integer.toString(counterch++)));
				attsc.add(new Tuple<>("power", Double.toString(chargerPower)));
			
				chargingStations.add(attsc);
			}
		}
		
		for(List<Tuple<String, String>> normalVehicle: normalVehicles)
			writeStartTag("vehicle", normalVehicle, true);
		
		for(List<Tuple<String, String>> electroVehicle: electroVehicles)
			writeStartTag("electrovehicle",electroVehicle,true);	
		
		for(List<Tuple<String, String>> chargingStation: chargingStations)
			writeStartTag("charger",chargingStation, true);

		
		writeEndTag("oneway"); 

	}

	public static void main(String[] args) {

		Config config = ConfigUtils.createConfig();
		config.network().setInputFile(args[0]);
        Scenario scenario = ScenarioUtils.loadScenario(config);

		PlaceStationsVehicles place = new PlaceStationsVehicles(scenario);
		place.write(args[1]);		
	}
}
