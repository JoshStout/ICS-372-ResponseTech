package edu.metrostate.ics372.p2;

import java.util.LinkedList;

public class CallList {
	
	private LinkedList<Call> callList;
	
	/**
	 * Constructor creates a LinkedList for Call objects
	 * when the constructors is called.
	 */
	public CallList() {
		callList = new LinkedList<Call>();
	}
	
	/**
	 * 
	 * @return returns a LinkedList of Call objects
	 */
	public LinkedList<Call> getCallList(){
		return callList;
	}
	
	/**
	 *
	 * @param call a Call object to be added to the 
	 * LinkedList that was created by the constructor.
	 */
	public void addCall(Call call) {
		callList.add(call);
	}
	
	/**
	 * A modifier to remove any Call objects from the CallList
	 * object that are also in the DispatchQueue object passed as
	 * a parameter.
	 * @param queue a DispatchQueue object for comparison for removing
	 * Call objects.
	 */
	public void remove(DispatchQueue queue) {
		for(Call call : queue.getDispatchQueue()) {
			callList.remove(call);
		}
	}
	
	/**
	 * Retrieves the Call object at the head of the LinkedList but does not remove it.
	 * @return the Call object at the head.
	 */
	public Call peek() {
		return callList.peek();
	}
	
	/**
	 * Returns the size of the LinkedList of Call objects.
	 * @return an INT representing the size of the LinkedList of Call objects.
	 */
	public int size() {
		return callList.size();
	}
		
}
