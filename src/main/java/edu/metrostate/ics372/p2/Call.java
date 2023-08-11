package edu.metrostate.ics372.p2;

import java.time.LocalDateTime;

import java.time.Duration;

public class Call implements Event, Comparable <Call> {

	private int assignedAmbulanceID;
	private String callID;
	private LocalDateTime callTime;
	private LocalDateTime dispatchTime;
	private int duration;
	private Priority priority;
	
	/**
	 * Constructor for the Call object. The delimited data within the scenario file
	 * make up the constructor parameters. Each line of the scenario file become an 
	 * object of the Call class, a class that implements the Event methods.  
	 * @param callTime is the LocalDateTime received from the scenario file: the call received time.
	 * @param callID is a String representing the call identifier. Each call ID is unique for each scenario.
	 * @param p is a three state ENUM indicating priority of the call, T1 for lowest and T3 for highest.
	 * @param duration an INT representing duration of the call in minutes. 
	 */
	public Call(LocalDateTime callTime, String callID, Priority p, int duration) {
		this.callTime = callTime;
		this.callID = callID;
		this.priority = p;
		this.duration = duration;
	}

	/**
	 * Returns the Ambulance ID that this Event was assigned to
	 * @return an int representing the ambulance ID
	 */
	@Override
	public int getAssignedAmbulanceID() {
		return assignedAmbulanceID;
	}
	
	/**
	 * Modifier for assigning the Ambulance ID
	 * @param assignedAmbulanceID an INT representing the ambulance ID
	 */
	public void setAssignedAmbulanceID(int assignedAmbulanceID) {
		this.assignedAmbulanceID = assignedAmbulanceID;
	}

	/**
	 * Returns the time the ambulance assigned to this event became available
	 * @return a LocalDateTime that represents when the assigned ambulance is available.
	 */
	@Override
	public LocalDateTime getAvailTime() {
		return this.getDispatchTime().plusMinutes(duration);
	}
	
	/**
	 * Returns a String to get the call identifier.
	 * @return a String representing the call ID.
	 */
	@Override
	public String getCallID() {
		return callID;
	}

	/**
	 * Returns the request time of this Event
	 * @return a String representing the call time received
	 */
	@Override
	public LocalDateTime getCallTime() {
		return callTime;
	}

	/**
	 * Returns the number of minutes delay between the call and dispatch time in minutes
	 * @return an INT representing the difference between call received and when an ambulance
	 * was dispatched to the call.
	 */
	@Override
	public int getDispatchDelayInMinutes() {
		LocalDateTime from = getCallTime();
		LocalDateTime to = getDispatchTime();		
		return (int) Duration.between(from, to).toMinutes();
	}
	
	/**
	 * Returns the dispatch time of this Event
	 * @return a LocalDateTime representing the time an ambulance was dispatched to the call.
	 */
	@Override
	public LocalDateTime getDispatchTime() {
		return dispatchTime;
	}
	
	/**
	 * Modifier to set the time when an ambulance was dispatch to the call.
	 * @param dispatchTime a LocalDateTime representing the time an ambulance was dispatched to the call.
	 */
	public void setDispatchTime(LocalDateTime dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	/**
	 * Returns the duration of this Event in minutes
	 * @return an INT representing the duration of the call in minutes.
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	/**
	 * Returns the priority of this Event
	 * @return a three state ENUM representing the priority of the call.
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}
	
	/**
	 * Compares priority and callID. The lower call id number has higher priority 
	 * @param obj a Call object to compare with this object
	 */	
	public int compareTo(Call obj) {
		//if(this.callTime.isAfter(obj.getCallTime())) return 1;
		//if(this.callTime.isBefore(obj.getCallTime())) return -1;
		if(this.priority.compareTo(obj.getPriority()) > 0) return 1;
		if(this.priority.compareTo(obj.getPriority()) == 0 && this.getIdentifierInt() > obj.getIdentifierInt()) return 1;
		
		// if call time is equal and priority is equal, use call ID to break ties
//		if(this.callTime.isEqual(obj.getCallTime())
//				&& (this.priority.compareTo(obj.getPriority()) == 0) 
//				&& (this.getIdentifierInt() > obj.getIdentifierInt())) {
//			return 1;
//		}
		return -1;
	}
	
	/**
	 * Return the numeric portion of the Call ID
	 * @return an int representing only the numeric portion of the Call ID
	 */
	public int getIdentifierInt() {
		String str = this.callID;
		str = str.replaceAll("[^0-9]", "");
		int strToInt = Integer.parseInt(str);
		return strToInt;
	}
	
	/**
	 * @return a String for each attribute implemented from Event
	 */
	public String toString() {
		String output = "";
		output += "| call time: " + getCallTime();
		output += "| priority: " + getPriority();
		output += "| call ID:  " + getCallID();
		output += "| ambulance ID: " + getAssignedAmbulanceID();
		output += "| dispatch time: " + getDispatchTime();
		output += "| duration: " + getDuration();
		output += "| availTime: " + getAvailTime();
		output += "| dispatch delay: " + getDispatchDelayInMinutes();
		output += "|";
		return output;
	}

}
