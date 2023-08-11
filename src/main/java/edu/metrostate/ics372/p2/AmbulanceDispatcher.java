package edu.metrostate.ics372.p2;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.metrostate.ics372.p2.Event.Priority;

public class AmbulanceDispatcher {
	
	private Fleet fleet;
	
	// constructor creates a fleet of ambulances at construction 
	private AmbulanceDispatcher(int numAmbulances) {
		this.fleet = new Fleet();
		createFleet(this.fleet, numAmbulances); 
	}
	
	private Fleet getFleet() {
		return this.fleet;
	}
		
	// creates an ArrayList of Ambulances objects.
	private void createFleet(Fleet fleet, int numAmbulances) {
		for(int i = 0; i < numAmbulances; i++) {
			fleet.add();
		}
	}
	
	private Call dispatchAmbulance(Call call, Ambulance ambulance, LocalDateTime time) {
		call.setAssignedAmbulanceID(ambulance.getAmbulanceID());
		LocalDateTime dispatchTime = ambulance.getTimeAvailable();
		if(dispatchTime == null) dispatchTime = call.getCallTime();
		if(dispatchTime.isBefore(call.getCallTime())) dispatchTime = call.getCallTime();
		call.setDispatchTime(dispatchTime);
		ambulance.setTimeAvailable(call.getAvailTime());
		return call;
	}
	
	private Ambulance getNextAmbulance(Fleet fleet, LocalDateTime lastDispatchTime) {
		Ambulance currentAmbulance = fleet.getNextAmbulance();
		LocalDateTime earliest = currentAmbulance.getTimeAvailable();
		Ambulance earliestAmbulance = currentAmbulance;
		for(int j = 0; j < fleet.getFleetSize(); j++) {
			currentAmbulance = fleet.getNextAmbulance();
			LocalDateTime availTime = currentAmbulance.getTimeAvailable();
			if(earliest != null && earliest.isAfter(availTime) && earliest.isAfter(lastDispatchTime)) {
				earliest = availTime;
				earliestAmbulance = currentAmbulance;
			}
		}
		return earliestAmbulance;
	}
			
	/**
	 * A static method for processing Events listed in the file specified by the scenarioFile parameter. 
	 * It returns an array of Events data, including ambulance dispatch times and responding ambulance ID for each
	 * call passed in the scenario file. 
	 * @precondition numAmbulances must be greater than or equal to one.
	 * @param numAmbulances an INT representing the number of Ambulances available to process events.
	 * @param scenarioFilename a String representing the filename containing the scenarios for processing.
	 * @return an array of Events.
	 */
	public static edu.metrostate.ics372.p2.Event [] processEvents(int numAmbulances, String scenarioFilename) {
		
		// check if null or empty filename passed 
		if(scenarioFilename == null || scenarioFilename == "") throw new NullPointerException();
		
		// check if file does not exist and create scanner object
		File myFile = new File(scenarioFilename);
		Scanner scanner = null;
		try {
			scanner = new Scanner(myFile);
		} catch (FileNotFoundException e) {
			// ignore 
		}
		
		// check if file is empty
		if(myFile.length() == 0) throw new NoSuchElementException("File must contain one or more Events");
		
		// precondition: numAmbulances must be >= 1
		if(numAmbulances < 1) throw new IllegalArgumentException("numAmbulances must be one or greater");
		
		// (step 1) read all calls from Scenario File into Incoming Call List
		CallList callList = new CallList();
		while(scanner.hasNext()) {
			String eventStr = scanner.nextLine();
			Call nextScenarioCall = parseCall(eventStr);
			callList.addCall(nextScenarioCall);
		}
		scanner.close();
		
		// (step 2) create Ambulance(s)
		AmbulanceDispatcher dispatcher = new AmbulanceDispatcher(numAmbulances);
		
		// (step 3) initialize SimClock to Incoming Call Time
		SimClock time = new SimClock(callList.peek().getCallTime());
		
		LinkedList<Call> eventList = new LinkedList<Call>();
		DispatchQueue dispatchQueue = new DispatchQueue();
		
		while(!callList.getCallList().isEmpty() || !dispatchQueue.getDispatchQueue().isEmpty()) {
			
			for(int i = 0; i < numAmbulances; i++) {
				
				// (step 4) remove all calls at or before SimClock time from call list and add to dispatchQueue
				dispatchQueue.addCalls(callList, time);
				callList.remove(dispatchQueue);
				
				// (step 5) get available Ambulance 
				Ambulance nextAmbulance = dispatcher.getNextAmbulance(dispatcher.getFleet(), time.getTime());
				
				// (step 6) dispatch ambulance to next call in dispatchQueue
				if(dispatchQueue.getSize() != 0) {
					Call nextCall = dispatchQueue.getNextCall();
					Call dispatchedCall = dispatcher.dispatchAmbulance(nextCall, nextAmbulance, time.getTime());
					
					// (step 6.a) add processed event to event list
					eventList.add(dispatchedCall);
				}

			} // (step 7) if dispatch queue not empty, goto step 5
						
			// (step 8) update SimClock
			time = updateTime(eventList, time);
			
		} // (step 9) goto 4
		
		// return an array of Events
		Event[] eventArray = new Event[eventList.size()];
		for(int i = 0; i < eventList.size(); i++) {
			eventArray[i] = eventList.get(i);
		}
		return eventArray;
	}
	
	// convert String from scenario file, a single line in file, to a Call object
	private static Call parseCall(String s) {
		String[] tokens = s.split("\\|");		
		LocalDateTime callTime = LocalDateTime.parse(tokens[0]);
		String callID = tokens[1];
		Priority priority = Priority.valueOf(tokens[2]);
		int duration = Integer.parseInt(tokens[3]);
		Call obj = new Call(callTime, callID, priority, duration);
		return obj;
	}
	
	// find the call with the next available time after current time
	private static SimClock updateTime(LinkedList<Call> eventList, SimClock time) {
		LocalDateTime earliest = eventList.getLast().getAvailTime();
		for(Call call : eventList) {
			if(call.getAvailTime().isAfter(time.getTime()) 
				&& call.getAvailTime().isBefore(earliest)) {
				earliest = call.getAvailTime();
			}
		}
		time.setTime(earliest);
		return time;
	}
}


