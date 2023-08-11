package edu.metrostate.ics372.p2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class AmbulanceTest {
	
	@Test
	void testZero() {
		// TODO Create ambulance zero test
	}
	
	@Test
	void testOne() {
		Ambulance amb = new Ambulance();
		assertEquals(1, amb.getAmbulanceID());
		Ambulance.clear();
	}
	
	@Test
	void testTwo() {
		new Ambulance();
		Ambulance amb = new Ambulance();
		assertEquals(2, amb.getAmbulanceID());
		Ambulance.clear();
	}
	
	@Test
	void testAutoIncrementAndClear() {
		Ambulance amb = new Ambulance(); // 1
		assertEquals(1, amb.getAmbulanceID());
		new Ambulance(); // 2
		amb = new Ambulance(); // 3
		assertEquals(3, amb.getAmbulanceID());
		Ambulance.clear();
	}
	
	@Test
	void testAutoIncrementAndClear_2() {
		Ambulance amb = new Ambulance(); // #1
		assertEquals(1, amb.getAmbulanceID());
		new Ambulance(); // #2
		new Ambulance(); // #3
		new Ambulance(); // #4
		new Ambulance(); // #5
		new Ambulance(); // #6
		new Ambulance(); // #7
		amb = new Ambulance(); // #8
		assertEquals(8, amb.getAmbulanceID());
		Ambulance.clear();
		amb = new Ambulance();
		assertEquals(1, amb.getAmbulanceID());
		Ambulance.clear();
	}
	
	@Test
	void testBoundaryAmbulance() {
		Ambulance amb = new Ambulance();
		for(int i = 1; i < 3000; i++) {
			amb = new Ambulance();
			assertEquals(i+1, amb.getAmbulanceID());
		}
		Ambulance.clear();
	}
	
	@Test
	void testNoTimeAvailable() {
		Ambulance amb = new Ambulance();
		assertNull(amb.getTimeAvailable());
		Ambulance.clear();
	}
	
	@Test
	void testTimeAvailable() {
		Ambulance amb = new Ambulance();
		amb.setTimeAvailable(LocalDateTime.parse("2023-05-24T00:02"));
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), amb.getTimeAvailable());
		Ambulance.clear();
	}
	
	@Test
	void testChangeTimeAvailable() {
		Ambulance amb = new Ambulance();
		amb.setTimeAvailable(LocalDateTime.parse("2023-05-24T00:02"));
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), amb.getTimeAvailable());
		amb.setTimeAvailable(LocalDateTime.parse("2023-05-24T00:22"));
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), amb.getTimeAvailable());
		Ambulance.clear();
	}
	
}


