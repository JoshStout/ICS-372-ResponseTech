package edu.metrostate.ics372.p2;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class DispatchQueue {
	
	private PriorityQueue<Call> dispatchQueue;
	
	/**
	 * Constructor creates a PriorityQueue for Call objects
	 * ready for dispatching when called.
	 */
	public DispatchQueue() {
		dispatchQueue = new PriorityQueue<Call>();
	}
	
	/**
	 * 
	 * @return returns a PriorityQueue of Call objects
	 */
	public PriorityQueue<Call> getDispatchQueue(){
		return dispatchQueue;
	}
	
	/**
	 * 
	 * @param call a Call object to be added to the 
	 * PriorityQueue
	 */
	public void addCall(Call call) {
		dispatchQueue.add(call);
	}
	
	/**
	 * add a Call object at or before the SimClock object's
	 * current time. 
	 * @param callList a CallList object containing Call objects to be
	 * evaluated for inclusion to the PriortyQueue for dispatching.
	 * @param time a SimClock object containing the current time
	 */
	public void addCalls(CallList callList, SimClock time) {
		LinkedList<Call> calls = callList.getCallList();
		for(int i = 0; i < callList.size(); i++) {
			if(
			(calls.get(i).getCallTime().isBefore(time.getTime())
			|| calls.get(i).getCallTime().isEqual(time.getTime()))) {
				this.addCall(calls.get(i));
			}
		}
	}
	
	/**
	 * removes the next Call object from the head of the PriorityQueue
	 * @return returns the head of the PriorityQueue
	 */
	public Call getNextCall() {
		return dispatchQueue.remove();
	}
	
	/**
	 * 
	 * @return an INT representing the size of the PriorityQueue
	 */
	public int getSize() {
		return dispatchQueue.size();
	}

}
