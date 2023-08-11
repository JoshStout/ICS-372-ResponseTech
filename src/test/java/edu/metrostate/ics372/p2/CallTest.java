package edu.metrostate.ics372.p2;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import edu.metrostate.ics372.p2.Event.Priority;

import static org.junit.jupiter.api.Assertions.*;

class CallTest {
	
	static Call staticCall = new Call(LocalDateTime.parse("2023-05-24T00:02"),
			"X10002", Priority.valueOf("T2"), 20);
	
	@Test
	void testConstructor() {
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 20);
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), call.getCallTime());
		assertEquals("X10002", call.getCallID());
		assertEquals(Priority.valueOf("T2"), call.getPriority());
		assertEquals(20, call.getDuration());
	}
	
	@Test
	void testAmbulanceIDNotZero() {
		assertNotNull(staticCall.getAssignedAmbulanceID());
	}
	
	@Test
	void testAmbulanceIDOne() {
		staticCall.setAssignedAmbulanceID(1);
		assertEquals(1, staticCall.getAssignedAmbulanceID());
	}
	
	@Test
	void testAmbulanceIDMany() {
		staticCall.setAssignedAmbulanceID(1);
		staticCall.setAssignedAmbulanceID(2);
		staticCall.setAssignedAmbulanceID(3);
		assertNotEquals(1, staticCall.getAssignedAmbulanceID());
		assertNotEquals(2, staticCall.getAssignedAmbulanceID());
		assertEquals(3, staticCall.getAssignedAmbulanceID());
	}

	@Test
	void testAmbulanceIDBoundary() {
		staticCall.setAssignedAmbulanceID(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, staticCall.getAssignedAmbulanceID());
		staticCall.setAssignedAmbulanceID(Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, staticCall.getAssignedAmbulanceID());
	}
		
	@Test
	void testAvailTime() {
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 20);
		call.setDispatchTime(LocalDateTime.parse("2023-05-24T00:12"));
		assertEquals(LocalDateTime.parse("2023-05-24T00:32"), call.getAvailTime());
	}
	
	@Test
	void testGetCallID() {
		assertEquals("X10002", staticCall.getCallID());
	}
		
	@Test
	void testGetCallTime() {
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), staticCall.getCallTime());
	}
	
	@Test
	void testDispatchDelayInMinutesZero() {
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 20);
		call.setDispatchTime(LocalDateTime.parse("2023-05-24T00:02"));
		assertEquals(0, call.getDispatchDelayInMinutes());
	}
	
	@Test
	void testDispatchDelayInMinutesOne() {
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 20);
		call.setDispatchTime(LocalDateTime.parse("2023-05-24T00:03"));
		assertEquals(1, call.getDispatchDelayInMinutes());
	}
	
	@Test
	void testDispatchDelayInMinutesMany() {
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:00"), 
				"X10002", Priority.valueOf("T2"), 20);
		call.setDispatchTime(LocalDateTime.parse("2023-05-24T01:12"));
		assertEquals(72, call.getDispatchDelayInMinutes());
	}
	
	@Test
	void testDispatchDelayInMinutesDays() {
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:00"), 
				"X10002", Priority.valueOf("T2"), 20);
		call.setDispatchTime(LocalDateTime.parse("2023-05-26T00:00"));
		assertEquals(2880, call.getDispatchDelayInMinutes());
	}
	
	@Test
	void testDispatchTime() {
		staticCall.setDispatchTime(LocalDateTime.parse("2023-05-24T00:22"));
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), staticCall.getDispatchTime());
	}
	
	@Test
	void testGetDuration() {
		assertEquals(20, staticCall.getDuration());
	}
	
	@Test
	void testGetPriority() {
		assertEquals(Priority.T2, staticCall.getPriority());
	}
	
	@Test
	void testGetIdentifierInt() {
		assertEquals(10002, staticCall.getIdentifierInt());
	}
	
	// test call ID as tie breaker; lower ID number = higher priority 
	// -1 = closer to front of priority queue, 1 = closer to back of priority queue 
	@Test
	void testCallCompareToCallID() {
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002",
				Priority.valueOf("T2"),
				20);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10001",
				Priority.valueOf("T2"),
				20);
		assertEquals(1, call1.compareTo(call2));
	}
	 
	// -1 = closer to front of priority queue, 1 = closer to back of priority queue   
	@Test
	void testCallCompareToPriority() {
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002",
				Priority.valueOf("T1"),
				20);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003",
				Priority.valueOf("T2"),
				20);
		assertEquals(1, call1.compareTo(call2));
	}
	
	// -1 = closer to front of priority queue, 1 = closer to back of priority queue  
	@Test
	void testCallCompareToTime() {
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:03"), 
				"X10002",
				Priority.valueOf("T2"),
				20);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003",
				Priority.valueOf("T2"),
				20);
		assertEquals(1, call1.compareTo(call2));
	}
}
