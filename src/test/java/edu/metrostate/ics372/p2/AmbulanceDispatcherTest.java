package edu.metrostate.ics372.p2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.metrostate.ics372.p2.Event.Priority;

class AmbulanceDispatcherTest {
	
	private String getSystemPath(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResource(fileName).getPath().replaceAll("%20", " ");
	}
	
	@Test
	void testFileNotFound() {
		assertThrows(NoSuchElementException.class, () -> AmbulanceDispatcher.processEvents(1, "file_does_not_exist"));
	}
	
	@Test
	void testZeroAmbulancesIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> AmbulanceDispatcher.processEvents(0, getSystemPath("scenario_0")));
	}
	
	@Test
	void testEmptyFileZeroScenarios() {
		assertThrows(NoSuchElementException.class, () -> AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_empty")));
	}
	
	@Test
	void testEmptyFileNullFileNameScenarios() {
		assertThrows(NullPointerException.class, () -> AmbulanceDispatcher.processEvents(1, getSystemPath(null)));
	}
	
	@Test
	void testEmptyFileEmptryNameScenarios() {
		assertThrows(NullPointerException.class, () -> AmbulanceDispatcher.processEvents(1, getSystemPath("")));
	}
	
	// read file outside of method and compare with what method returns that they have the same number of Events
	@Test
	void testNumCallEqualToReturnArray() {
		File myFile = new File(getSystemPath("scenario_0"));
		Scanner scanner = null;
		try {
			scanner = new Scanner(myFile);
		} catch (FileNotFoundException e) {
			// ignore
		}
		int numCalls = 0;
		while(scanner.hasNext()) {
			scanner.nextLine();
			numCalls++;
		}
		scanner.close();
		Event[] result = AmbulanceDispatcher.processEvents(3, getSystemPath("scenario_0"));
		assertEquals(numCalls, result.length);
	}
	
	@Test
	void testOneCall() {
		Event[] events = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_1"));
		assertEquals(1, events.length);
		assertEquals(1, events[0].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), events[0].getAvailTime());
		assertEquals("X10001", events[0].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), events[0].getCallTime());
		assertEquals(0, events[0].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), events[0].getDispatchTime());
		assertEquals(20, events[0].getDuration());
		assertEquals(Priority.valueOf("T2"), events[0].getPriority());	
	}
	
	@Test
	void testTwoCallsOneAmbulance() {
		Event[] events = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_2"));		
		assertEquals(2, events.length);
		// first index should be X10002 with priority of T2
		assertEquals(1, events[0].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), events[0].getAvailTime());
		assertEquals("X10002", events[0].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), events[0].getCallTime());
		assertEquals(0, events[0].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), events[0].getDispatchTime());
		assertEquals(20, events[0].getDuration());
		assertEquals(Priority.valueOf("T2"), events[0].getPriority());
		// second index should be X10001 with priority of T1
		assertEquals(1, events[1].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:42"), events[1].getAvailTime());
		assertEquals("X10001", events[1].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), events[1].getCallTime());
		assertEquals(20, events[1].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), events[1].getDispatchTime());
		assertEquals(20, events[1].getDuration());
		assertEquals(Priority.valueOf("T1"), events[1].getPriority());
	}
	
	@Test
	void testThreeCallsOneAmbulance() {
		Event[] events = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_7"));
		assertEquals(3, events.length);
		
		// first index should be X10002 with priority of T2
		assertEquals(LocalDateTime.parse("2023-05-24T00:00"), events[0].getCallTime());
		assertEquals(Priority.valueOf("T2"), events[0].getPriority());
		assertEquals("X10002", events[0].getCallID());
		assertEquals(1, events[0].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:00"), events[0].getDispatchTime());
		assertEquals(10, events[0].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T00:10"), events[0].getAvailTime());
		assertEquals(0, events[0].getDispatchDelayInMinutes());
		
		// second index should be X10003 with priority of T3
		assertEquals(LocalDateTime.parse("2023-05-24T00:10"), events[1].getCallTime());
		assertEquals(Priority.valueOf("T3"), events[1].getPriority());
		assertEquals("X10003", events[1].getCallID());
		assertEquals(1, events[1].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:10"), events[1].getDispatchTime());
		assertEquals(10, events[1].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T00:20"), events[1].getAvailTime());
		assertEquals(0, events[1].getDispatchDelayInMinutes());
		
		// third index should be X10001 with priority of T1
		assertEquals(LocalDateTime.parse("2023-05-24T00:00"), events[2].getCallTime());
		assertEquals(Priority.valueOf("T1"), events[2].getPriority());
		assertEquals("X10001", events[2].getCallID());
		assertEquals(1, events[2].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:20"), events[2].getDispatchTime());
		assertEquals(10, events[2].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T00:30"), events[2].getAvailTime());
		assertEquals(20, events[2].getDispatchDelayInMinutes());
	}
		
	@Test
	void testThreeCallsTwoAmbulance() {
		Event[] events = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_7"));
		assertEquals(3, events.length);
		
		// first index should be X10002 with priority of T2
		assertEquals(LocalDateTime.parse("2023-05-24T00:00"), events[0].getCallTime());
		assertEquals(Priority.valueOf("T2"), events[0].getPriority());
		assertEquals("X10002", events[0].getCallID());
		assertEquals(1, events[0].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:00"), events[0].getDispatchTime());
		assertEquals(10, events[0].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T00:10"), events[0].getAvailTime());
		assertEquals(0, events[0].getDispatchDelayInMinutes());
		
		// second index should be X10003 with priority of T3
		assertEquals(LocalDateTime.parse("2023-05-24T00:10"), events[1].getCallTime());
		assertEquals(Priority.valueOf("T3"), events[1].getPriority());
		assertEquals("X10003", events[1].getCallID());
		assertEquals(1, events[1].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:10"), events[1].getDispatchTime());
		assertEquals(10, events[1].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T00:20"), events[1].getAvailTime());
		assertEquals(0, events[1].getDispatchDelayInMinutes());
		
		// third index should be X10001 with priority of T1
		assertEquals(LocalDateTime.parse("2023-05-24T00:00"), events[2].getCallTime());
		assertEquals(Priority.valueOf("T1"), events[2].getPriority());
		assertEquals("X10001", events[2].getCallID());
		assertEquals(1, events[2].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:20"), events[2].getDispatchTime());
		assertEquals(10, events[2].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T00:30"), events[2].getAvailTime());
		assertEquals(20, events[2].getDispatchDelayInMinutes());
	}
	
	// for fileOne, each has same call times but different priorities. 
	@Test
	void testReadTwoFiles() {
		Event[] fileOne = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_2"));
		Event[] fileTwo = AmbulanceDispatcher.processEvents(2, getSystemPath("scenario_3"));
		assertEquals("| call time: 2023-05-24T00:02| priority: T2| call ID:  X10002| ambulance ID: 1| "
				+ "dispatch time: 2023-05-24T00:02| duration: 20| availTime: 2023-05-24T00:22| dispatch delay: 0|",
				fileOne[0].toString());
		assertEquals("| call time: 2023-05-24T00:02| priority: T1| call ID:  X10001| ambulance ID: 1| "
				+ "dispatch time: 2023-05-24T00:22| duration: 20| availTime: 2023-05-24T00:42| dispatch delay: 20|",
				fileOne[1].toString());
		assertEquals("| call time: 2023-05-24T00:02| priority: T2| call ID:  X10001| ambulance ID: 1| "
				+ "dispatch time: 2023-05-24T00:02| duration: 20| availTime: 2023-05-24T00:22| dispatch delay: 0|",
				fileTwo[0].toString());
		assertEquals("| call time: 2023-05-24T00:02| priority: T2| call ID:  X10002| ambulance ID: 2| "
				+ "dispatch time: 2023-05-24T00:02| duration: 20| availTime: 2023-05-24T00:22| dispatch delay: 0|",
				fileTwo[1].toString());
	}
	
	@Test
	void testFiveCallsThreeAmbulances() {
		Event[] events = AmbulanceDispatcher.processEvents(3, getSystemPath("scenario_9"));
		
		// check fourth call is correct
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), events[3].getCallTime());
		assertEquals(Priority.valueOf("T3"), events[3].getPriority());
		assertEquals("X10005", events[3].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), events[3].getDispatchTime());
		assertEquals(60, events[3].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T01:22"), events[3].getAvailTime());
		assertEquals(0, events[3].getDispatchDelayInMinutes());
		
		// check fifth and last call is correct
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), events[4].getCallTime());
		assertEquals(Priority.valueOf("T1"), events[4].getPriority());
		assertEquals("X10004", events[4].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:32"), events[4].getDispatchTime());
		assertEquals(50, events[4].getDuration());
		assertEquals(LocalDateTime.parse("2023-05-24T01:22"), events[4].getAvailTime());
		assertEquals(30, events[4].getDispatchDelayInMinutes());
	}
	
	@Test
	void testReadMultipleFiles() {
		Event[] eventsTwo = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_2"));		
		assertEquals(2, eventsTwo.length);
		assertEquals(1, eventsTwo[0].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), eventsTwo[0].getAvailTime());
		assertEquals("X10002", eventsTwo[0].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsTwo[0].getCallTime());
		assertEquals(0, eventsTwo[0].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsTwo[0].getDispatchTime());
		assertEquals(20, eventsTwo[0].getDuration());
		assertEquals(Priority.valueOf("T2"), eventsTwo[0].getPriority());
		
		Event[] eventsOne = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_1"));
		assertEquals(1, eventsOne.length);
		assertEquals(1, eventsOne[0].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), eventsOne[0].getAvailTime());
		assertEquals("X10001", eventsOne[0].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsOne[0].getCallTime());
		assertEquals(0, eventsOne[0].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsOne[0].getDispatchTime());
		assertEquals(20, eventsOne[0].getDuration());
		assertEquals(Priority.valueOf("T2"), eventsOne[0].getPriority());
		
		assertEquals(1, eventsTwo[1].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:42"), eventsTwo[1].getAvailTime());
		assertEquals("X10001", eventsTwo[1].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsTwo[1].getCallTime());
		assertEquals(20, eventsTwo[1].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), eventsTwo[1].getDispatchTime());
		assertEquals(20, eventsTwo[1].getDuration());
		assertEquals(Priority.valueOf("T1"), eventsTwo[1].getPriority());
		
		Event[] eventsZero = AmbulanceDispatcher.processEvents(3, getSystemPath("scenario_0"));
		assertEquals(62, eventsZero.length);
		assertEquals(1, eventsZero[3].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T01:12"), eventsZero[3].getAvailTime());
		assertEquals("X10004", eventsZero[3].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsZero[3].getCallTime());
		assertEquals(20, eventsZero[3].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), eventsZero[3].getDispatchTime());
		assertEquals(50, eventsZero[3].getDuration());
		assertEquals(Priority.valueOf("T1"), eventsZero[3].getPriority());
	}
	
	@Test
	void testReadSameFileTwoTimes() {
		Event[] eventsZero = AmbulanceDispatcher.processEvents(3, getSystemPath("scenario_0"));
		Event[] eventsOne = AmbulanceDispatcher.processEvents(4, getSystemPath("scenario_0"));
		
		assertEquals(62, eventsZero.length);
		assertEquals(1, eventsZero[3].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T01:12"), eventsZero[3].getAvailTime());
		assertEquals("X10004", eventsZero[3].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsZero[3].getCallTime());
		assertEquals(20, eventsZero[3].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:22"), eventsZero[3].getDispatchTime());
		assertEquals(50, eventsZero[3].getDuration());
		assertEquals(Priority.valueOf("T1"), eventsZero[3].getPriority());
		
		assertEquals(62, eventsOne.length);
		assertEquals(2, eventsOne[5].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T01:25"), eventsOne[5].getAvailTime());
		assertEquals("X10007", eventsOne[5].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:45"), eventsOne[5].getCallTime());
		assertEquals(0, eventsOne[5].getDispatchDelayInMinutes());
		assertEquals(LocalDateTime.parse("2023-05-24T00:45"), eventsOne[5].getDispatchTime());
		assertEquals(40, eventsOne[5].getDuration());
		assertEquals(Priority.valueOf("T2"), eventsOne[5].getPriority());
	}
	
	@Test
	void testReadSameFileManyTimes() {
		Event[] eventsOne = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_0"));
		Event[] eventsTwo = AmbulanceDispatcher.processEvents(2, getSystemPath("scenario_0"));
		Event[] eventsThree = AmbulanceDispatcher.processEvents(3, getSystemPath("scenario_0"));
		Event[] eventsFour = AmbulanceDispatcher.processEvents(4, getSystemPath("scenario_0"));
		
		assertEquals(1, eventsOne[0].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsOne[0].getCallTime());
		assertEquals("X10001", eventsOne[0].getCallID());
		assertEquals(Priority.valueOf("T2"), eventsOne[0].getPriority());
		assertEquals(20, eventsOne[0].getDuration());
		
		assertEquals(4, eventsFour[3].getAssignedAmbulanceID());
		assertEquals("X10004", eventsFour[3].getCallID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsFour[3].getCallTime());
		assertEquals(Priority.valueOf("T1"), eventsFour[3].getPriority());
		assertEquals(50, eventsFour[3].getDuration());
				
		assertEquals(3, eventsThree[2].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsThree[2].getCallTime());
		assertEquals("X10003", eventsThree[2].getCallID());
		assertEquals(Priority.valueOf("T2"), eventsThree[2].getPriority());
		assertEquals(70, eventsThree[2].getDuration());
		
		assertEquals(2, eventsTwo[1].getAssignedAmbulanceID());
		assertEquals(LocalDateTime.parse("2023-05-24T00:02"), eventsTwo[1].getCallTime());
		assertEquals("X10002", eventsTwo[1].getCallID());
		assertEquals(Priority.valueOf("T2"), eventsTwo[1].getPriority());
		assertEquals(20, eventsTwo[1].getDuration());
	}
	
	@Test
	void testSameTimeDifferentPriorities() {
		Event[] events = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_4"));
		assertEquals("X10003", events[0].getCallID());
		assertEquals("X10001", events[1].getCallID());
		assertEquals("X10002", events[2].getCallID());
	}
	
	@Test
	void testCallProcessEventsManyTimes() {
		for(int i = 1; i < 500; i++) {
			AmbulanceDispatcher.processEvents(i, getSystemPath("scenario_1"));
		}
	}
	
	// only two call in file but have a fleet of 20 ambulances 
	@Test
	void testMoreAmbulancesThanCalls() {
		Event[] events = AmbulanceDispatcher.processEvents(20, getSystemPath("scenario_2"));
		assertEquals(1, events[0].getAssignedAmbulanceID());
		assertEquals(2, events[1].getAssignedAmbulanceID());
	}
	
	@Test
	void testOneAmbulanceManyCalls() {
		Event[] events = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_0"));
		for(int i = 0; i < events.length; i++) {
			assertEquals(1, events[i].getAssignedAmbulanceID());
		}
	}
	
	@Test
	void testReadManyFiles() {
		Event[] one = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_0"));
		Event[] two = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_1"));
		Event[] three = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_2"));
		Event[] four = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_3"));
		Event[] five = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_4"));
		Event[] six = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_5"));
		Event[] seven = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_6"));
		for(int i = 0; i < one.length; i++) {
			assertNotNull(one[i]);
			assertEquals(1, one[i].getAssignedAmbulanceID());
		}
		for(int i = 0; i < two.length; i++) {
			assertNotNull(two[i]);
			assertEquals(1, two[i].getAssignedAmbulanceID());
		}
		for(int i = 0; i < three.length; i++) {
			assertNotNull(three[i]);
			assertEquals(1, three[i].getAssignedAmbulanceID());
		}
		for(int i = 0; i < four.length; i++) {
			assertNotNull(four[i]);
			assertEquals(1, four[i].getAssignedAmbulanceID());
		}
		for(int i = 0; i < five.length; i++) {
			assertNotNull(five[i]);
			assertEquals(1, five[i].getAssignedAmbulanceID());
		}
		for(int i = 0; i < six.length; i++) {
			assertNotNull(six[i]);
			assertEquals(1, six[i].getAssignedAmbulanceID());
		}
		for(int i = 0; i < seven.length; i++) {
			assertNotNull(seven[i]);
			assertEquals(1, seven[i].getAssignedAmbulanceID());
		}
	}
	
	// start of first call to end of last call will equal the sum of call durations
	@Test
	void testOneAmbulanceSumOfDuration() {
		Event[] events = AmbulanceDispatcher.processEvents(1, getSystemPath("scenario_0"));
		int sum = 0;
		for(int i =0; i < events.length; i++) {
			sum += events[i].getDuration();
		}
		LocalDateTime from = events[0].getCallTime();
		LocalDateTime to = events[events.length-1].getAvailTime();		
		assertEquals(sum, (int) Duration.between(from, to).toMinutes());
	}
}
