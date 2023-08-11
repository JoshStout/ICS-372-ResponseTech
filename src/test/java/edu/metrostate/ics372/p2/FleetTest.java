package edu.metrostate.ics372.p2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FleetTest {
	
	@Test
	void testConstructor() {
		Fleet testFleet = new Fleet();
		assertNotNull(testFleet);
	}
	
	@Test
	void testZeroFleet() {
		Fleet testFleet = null;
		assertNull(testFleet);
	}
		
	@Test
	void testZeroAmbulances() {
		Fleet testFleet = new Fleet();
		assertThrows(IndexOutOfBoundsException.class,
				() -> {
					testFleet.getNextAmbulance();
				});
	}
	
	@Test
	void testAdd() {
		Fleet testFleet = new Fleet();
		assertEquals(0, testFleet.getFleetSize());
		testFleet.add();
		assertEquals(1, testFleet.getFleetSize());
		testFleet.add();
		assertEquals(2, testFleet.getFleetSize());
		testFleet.add();
		assertEquals(3, testFleet.getFleetSize());
	}
	
	@Test
	void testAddManyAmbulances() {
		Fleet testFleet = new Fleet();
		for(int i = 0; i < 1000; i++) {
			testFleet.add();
		}
		assertEquals(1000, testFleet.getFleetSize());
		testFleet.clear();
		assertEquals(0, testFleet.getFleetSize());
	}
	
	@Test
	void testClear(){
		Fleet testFleet = new Fleet();
		assertEquals(0, testFleet.getFleetSize());
		for(int i = 0; i < 5; i++) {
			testFleet.add();
		}
		assertEquals(5, testFleet.getFleetSize());
		testFleet.clear();
		assertEquals(0, testFleet.getFleetSize());
	}
	
	@Test
	void testManyFleetsManyAmbulances() {
		Fleet[] fleetArray = new Fleet[3000];
		for(int i = 0; i < 3000; i++) {
			fleetArray[i] = new Fleet();
			for(int j = 0; j < 3000; j++) {
				fleetArray[i].add();
			}
		}
		for(int i = 0; i < 3000; i++) {
			for(int j = 0; j < 3000; j++) {
				assertEquals(j+1, fleetArray[i].getNextAmbulance().getAmbulanceID());
			}
		}
	}
	
}
