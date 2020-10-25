package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import garage.ParkingChargeDriver;

class TestcaseHours_01 {

	ParkingChargeDriver Instance;
	
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("before method setUp()");
		Instance = new ParkingChargeDriver();
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("after method tearDown()");
		Instance = null;
	}

	@Test
	void test() {
		System.out.println("test method 01 test()");
		double input_hours = 10.0;
		double expected = 5.5;
		Instance.calculate(input_hours);
		double actual = ParkingChargeDriver.d_charges;
		assertEquals(expected, actual, 0.001);
		if(expected != actual)
			fail("test failed.");
	}

}
