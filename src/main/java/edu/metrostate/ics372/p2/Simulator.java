package edu.metrostate.ics372.p2;

public class Simulator {

	/**
	 * entry point of the program using the default main arguments. 
	 * @param args default String array parameter 
	 */
	public static void main(String[] args) {
		
		Event[] events2 = AmbulanceDispatcher.processEvents(3, "scenario_0");
		for(int i = 0; i < events2.length; i++) {
			System.out.println(events2[i]);
		}
		
	}
	
}
