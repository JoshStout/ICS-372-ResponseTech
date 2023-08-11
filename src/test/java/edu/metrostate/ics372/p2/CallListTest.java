package edu.metrostate.ics372.p2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import edu.metrostate.ics372.p2.Event.Priority;

class CallListTest {

	@Test
	void testConstructor() {
		CallList list = new CallList();
		assertEquals(0, list.size());
	}
		
	@Test
	void testAddCall() {
		CallList list = new CallList();
		Call call = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 20);
		for(int i = 1; i < 5; i++) {
			list.addCall(call);
			assertEquals(i, list.size());
		}
	}
	
	@Test
	void testGetCallList() {
		CallList list = new CallList();
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10001", Priority.valueOf("T2"), 1);
		list.addCall(call1);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 2);
		list.addCall(call2);
		Call call3 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003", Priority.valueOf("T2"), 3);
		list.addCall(call3);
		int i = 1;
		for(Call c : list.getCallList()) {
			assertEquals(i, c.getDuration());
			i++;
		}
	}
	
	@Test
	void testRemove() {
		CallList list = new CallList();
		DispatchQueue q = new DispatchQueue();
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10001", Priority.valueOf("T2"), 1);
		list.addCall(call1);
		q.addCall(call1);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 2);
		list.addCall(call2);
		Call call3 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003", Priority.valueOf("T2"), 3);
		list.addCall(call3);
		q.addCall(call3);
		list.remove(q);
		assertEquals(1, list.size());
		assertEquals("X10002", list.peek().getCallID());
	}
	
	@Test
	void testTwoCallLists() {
		CallList list1 = new CallList();
		CallList list2 = new CallList();
		Call call1 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10001", Priority.valueOf("T2"), 1);
		list1.addCall(call1);
		Call call2 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 2);
		list1.addCall(call2);
		Call call3 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003", Priority.valueOf("T2"), 3);
		list1.addCall(call3);
		
		Call call4 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10001", Priority.valueOf("T2"), 4);
		list2.addCall(call4);
		Call call5 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10002", Priority.valueOf("T2"), 5);
		list2.addCall(call5);
		Call call6 = new Call(LocalDateTime.parse("2023-05-24T00:02"), 
				"X10003", Priority.valueOf("T2"), 6);
		list2.addCall(call6);
		
		int i = 1;
		for(Call c : list1.getCallList()) {
			assertEquals(i, c.getDuration());
			i++;
		}
		int j = 4;
		for(Call c : list2.getCallList()) {
			assertEquals(j, c.getDuration());
			j++;
		}
	}
}
