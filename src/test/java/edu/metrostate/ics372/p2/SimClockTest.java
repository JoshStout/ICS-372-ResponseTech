package edu.metrostate.ics372.p2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class SimClockTest {

	@Test
	void test() {
		SimClock clock = new SimClock(LocalDateTime.parse("2023-05-24T00:10"));
		assertEquals(LocalDateTime.parse("2023-05-24T00:10"), clock.getTime());
	}
	
	@Test
	void testSetClock() {
		SimClock clock = new SimClock(LocalDateTime.parse("2023-05-24T00:10"));
		clock.setTime(LocalDateTime.parse("2023-05-24T00:20"));
		assertEquals(LocalDateTime.parse("2023-05-24T00:20"), clock.getTime());
	}
	
	@Test
	void testNullArgumentClock() {
		assertThrows(NoSuchElementException.class, () -> new SimClock(null));
	}

}
