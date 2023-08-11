package edu.metrostate.ics372.p2;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class SimClock {
	private LocalDateTime time;
		
	/**
	 * Initializes a SimClock object, representing
	 * the time as Calls are handled.
	 * @param time a LocalDateTime object representing the start time
	 * @precondition a LocalDateTime object must be passed to the constructor
	 */
	public SimClock(LocalDateTime time) {
		if(time == null) throw new NoSuchElementException();
		this.time = time;
	}
	
	/**
	 * Returns the time during the simulation 
	 * @return a LocalDateTime object representing the time
	 */
	public LocalDateTime getTime() {
		return time;
	}
	
	/**
	 * Modifier method to set the time in the simulation.
	 * @param t a LocalDateTime object representing the time
	 */
	public void setTime(LocalDateTime t) {
		time = t;
	}
	
}
