package edu.metrostate.ics372.p2;

import java.util.ArrayList;
import java.util.List;


// some method modified from MultiGreeter lab
public class Fleet {
	
	private int fleetIndex;
	private List<Ambulance> fleet;
	
	/**
	 * Initializes a new fleet of ambulances
	 */
	public Fleet() {
		fleet = new ArrayList<>();
		clear();
	}
	
	/**
	 * Removes all ambulances in the fleet of ambulances.
	 */
	public final void clear() {
		fleet.clear();
		fleetIndex = 0;
		Ambulance.clear();
	}
	
	/**
	 * Returns the next ambulance in the fleet.
	 * @return an Ambulance object within the fleet of Ambulances.
	 */
	public Ambulance getNextAmbulance() {
		if(fleet.size() == 0) throw new IndexOutOfBoundsException();
		Ambulance currAmbulance = fleet.get(fleetIndex);
		fleetIndex = (fleetIndex + 1) % fleet.size();
		return currAmbulance;
	}
	
	/**
	 * Adds another ambulance object to the fleet of Ambulances.
	 */
	public void add() {
		fleet.add(new Ambulance());
	}
	
	/**
	 * Returns the number of ambulances currently within the fleet.
	 * @return an INT representing the number of ambulances in the fleet.
	 */
	public int getFleetSize() {
		return fleet.size();
	}

}
