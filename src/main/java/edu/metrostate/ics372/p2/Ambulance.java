package edu.metrostate.ics372.p2;

import java.time.LocalDateTime;

public class Ambulance {
	
	private int ambulanceID;
	private static int nextAmbulanceID = 1;
	private LocalDateTime availTime;
	
	/**
	 * Initializes an new Ambulance object.
	 * Ambulance ID number are auto incremented
	 * with each new instance of Ambulance object.
	 */
	public Ambulance() {
		ambulanceID = nextAmbulanceID;
		nextAmbulanceID++;
	}
	
	/**
	 * Returns the Ambulance ID
	 * @return an INT representing the Ambulance ID
	 */
	public int getAmbulanceID() {
		return this.ambulanceID;
	}
	
	/**
	 * Modifier method to set the time the Ambulance is 
	 * available for dispatch to sent to another call.
	 * @param availTime a LocalDateTime object representing the time the Ambulance 
	 * is available.
	 */
	public void setTimeAvailable(LocalDateTime availTime) {
		this.availTime = availTime;
	}
	
	/**
	 * Returns the time the Ambulance is available for dispatch to sent to a call.
	 * @return a LocalDateTime object representing the time the Ambulance is
	 * available.
	 */
	public LocalDateTime getTimeAvailable() {
		return availTime;
	}
	
	public static void clear() {
		nextAmbulanceID = 1;
	}
	
}
