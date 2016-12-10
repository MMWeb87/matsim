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
	private static int lowBattCapa = 20;
	private static int highBattCapa = 60;
	private static double lowEnCons = 0.015;
	private static double highEnCons = 0.02;
	private static int freeparking = 2;
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
		for (int i = 1; i <= 400; i++) {
			Link link = (Link) array[r.nextInt(numberLinks)];

			writeStation("oneway", link, i);
		}
		writeVehicles();

		
		writeEndTag("company");
		writeEndTag("companies");

		
		close();
	}
	
	
	private void writeVehicles() {

		Network network = this.scenario.getNetwork();
		
		int cars = 1000;
		Object[] array = network.getLinks().values().toArray();
		
		int numberLinks = array.length;
		Random r = new Random(456);
		
		for (int i = 0; i < cars; i++) {
			
			Link link = (Link) array[r.nextInt(numberLinks)];
			writeVehicle(link, counter, r);
			counter++;
		}		
	}

	private void writeVehicle(Link link, int id, Random random) {

		
		List<Tuple<String, String>> attsV = new ArrayList<Tuple<String, String>>();
		
		attsV.add(new Tuple<>("id", "OW_" + Integer.toString(id)));
		attsV.add(new Tuple<>("x", Double.toString(link.getCoord().getX())));
		attsV.add(new Tuple<>("y", Double.toString(link.getCoord().getY())));
		attsV.add(new Tuple<>("freeparking", Integer.toString(freeparking))); // added by Elyas
		
		// not correct: was related to the freefloating
	/*	if (random.nextDouble() < 0.9)
		
			attsV.add(new Tuple<>("type", "car"));
		else
			attsV.add(new Tuple<>("type", "transporter"));
			*/ 

		writeStartTag("oneway", attsV, true);		
	}
	
	private void writeStation(String type, Link link, int id) {
		
		Random random = MatsimRandom.getRandom();
		
		List<Tuple<String, String>> attsV = new ArrayList<Tuple<String, String>>();
		
		attsV.add(new Tuple<>("id", Integer.toString(id)));
		attsV.add(new Tuple<>("x", Double.toString(link.getCoord().getX())));
		attsV.add(new Tuple<>("y", Double.toString(link.getCoord().getY())));
		

		writeStartTag("oneway", attsV); // 
		
		int numberOfVehicles = random.nextInt(4) + 1;
		
		for (int i = 1; i <= numberOfVehicles; i++) {
			
			
			List<Tuple<String, String>> atts = new ArrayList<Tuple<String, String>>();
			
			atts.add(new Tuple<>("id", "OW_" + Integer.toString(counterOW++)));
			atts.add(new Tuple<>("type", "car"));
			writeStartTag("vehicle", atts, true);
			
			//added by Elyas
			List<Tuple<String, String>> attse = new ArrayList<Tuple<String, String>>();
	
			atts.add(new Tuple<>("energyConsumption", Double.toString(lowEnCons)));
			atts.add(new Tuple<>("batteryCapacity", Integer.toString(lowBattCapa)));
			atts.add(new Tuple<>("id", "OW_" + Integer.toString(counterOW++)));
			atts.add(new Tuple<>("type", "ecar"));
			
			if (random.nextDouble() < 0.5){
			attse.add(new Tuple<>("batteryCapacity", Double.toString(highBattCapa)));
			attse.add(new Tuple<>("energyConsumption", Double.toString(highEnCons)));
			}else{
			attse.add(new Tuple<>("batteryCapacity", Double.toString(lowBattCapa)));
			attse.add(new Tuple<>("energyConsumption", Double.toString(lowEnCons)));
			}
	
			writeStartTag("electrovehicle",attse,true);
			// end of Elyas
		}
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
