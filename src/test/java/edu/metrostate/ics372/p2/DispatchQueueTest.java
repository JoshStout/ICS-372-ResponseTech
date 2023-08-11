package edu.metrostate.ics372.p2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

import edu.metrostate.ics372.p2.Event.Priority;

class DispatchQueueTest {
	
	@Test
	void testConstructor() {
		DispatchQueue q = new DispatchQueue();
		assertEquals(0, q.getSize());
	}
	
	@Test
	void testAddCall() {
		DispatchQueue q = new DispatchQueue();
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 20);
		for(int i = 1; i < 5; i++) {
			q.addCall(call);
			assertEquals(i, q.getSize());
		}
	}
		
	@Test
	void testGetNextCall() {
		DispatchQueue q = new DispatchQueue();
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10001", Priority.valueOf("T1"), 3);
		q.addCall(call1);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 2);
		q.addCall(call2);
		Call call3 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003", Priority.valueOf("T3"), 1);
		q.addCall(call3);
		assertEquals("X10003", q.getNextCall().getCallID());
		assertEquals("X10002", q.getNextCall().getCallID());
		assertEquals("X10001", q.getNextCall().getCallID());
	}
	
	@Test
	void testGetDispatchQueue() {
		DispatchQueue q = new DispatchQueue();
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10001", Priority.valueOf("T1"), 3);
		q.addCall(call1);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 2);
		q.addCall(call2);
		Call call3 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003", Priority.valueOf("T3"), 1);
		q.addCall(call3);
		PriorityQueue<Call> pq = new PriorityQueue<Call>();
		pq = q.getDispatchQueue();
		assertEquals("X10003", pq.remove().getCallID());
		assertEquals("X10002", pq.remove().getCallID());
		assertEquals("X10001", pq.remove().getCallID());
	}
	
	@Test
	void testAddCallsBeforeOrAtTime() {
		SimClock clock = new SimClock(LocalDateTime.parse("2023-05-24T00:10"));
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:00"), 
				"X10001", Priority.valueOf("T1"), 3);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:10"), 
				"X10002", Priority.valueOf("T2"), 2);
		Call call3 = new Call(LocalDateTime.parse("2023-05-24T00:20"), 
				"X10003", Priority.valueOf("T3"), 1);
		CallList list = new CallList();
		list.addCall(call1);
		list.addCall(call2);
		list.addCall(call3);
		DispatchQueue q = new DispatchQueue();
		q.addCalls(list, clock);
		assertEquals("X10002", q.getNextCall().getCallID());
		assertEquals("X10001", q.getNextCall().getCallID());
		assertEquals(0, q.getSize());
	}
}
