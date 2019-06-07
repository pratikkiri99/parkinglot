package com.parking.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parking.constants.Constants;
import com.parking.constants.Constants.VEHICLE_TYPE;
import com.parking.entities.ParkingLot;
import com.parking.entities.ParkingSlot;
import com.parking.entities.Ticket;
import com.parking.util.ParkingUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingUtilityTest {
	
	ParkingLot parkingLot;

	@InjectMocks
	ParkingUtility parkingUtility;
	
	@Before
	public void setUp() {
	   parkingLot = new ParkingLot();
	}
	
	@Test
	public void getAllEmptySpots_null() {
				
		List<String> parkingSpots = parkingUtility.getAllEmptySpots(null, null);
		assertThat(parkingSpots, is(nullValue()));
	}
	
	@Test
	public void getAllEmptySpots() {
				
		List<String> parkingSpots = parkingUtility.getAllEmptySpots(parkingLot, VEHICLE_TYPE.BIKE);
		assertFalse(parkingSpots.isEmpty());
	}
	
	@Test
	public void getFreeParkingSpot_null() {
		ParkingSlot parkingSlot = parkingUtility.getFreeParkingSlotForVehicle(null, null);
		assertThat(parkingSlot, is(nullValue()));
	}
	
	@Test
	public void getFreeParkingSpot() {
		ParkingSlot parkingSlot = parkingUtility.getFreeParkingSlotForVehicle(parkingLot, VEHICLE_TYPE.CAR);
		assertNotNull(parkingSlot);
	}
	
	@Test
	public void getAllRegNumForVehicleType_null() {
		
		Set<Entry<String, Ticket>> ticketSet = null;
		String matchPattern = null;
		
		List<String> regNums = parkingUtility.getAllRegistrationNumbers(matchPattern, ticketSet);
		assertEquals(regNums.size(), 0);
	}
	
	@Test
	public void getAllRegNumForVehicleByType() {
		
		Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
		ticketMap.put("B1SL1", new Ticket("B1SL1", "B1"));
		ticketMap.put("C1SL2", new Ticket("C1SL2", "C1"));
		ticketMap.put("B2SL3", new Ticket("B2SL3", "B2"));
		
		String expected [] = {"[B1SL1,B1]", "[B2SL3,B2]"};
		
		List<String> regNums = parkingUtility.getAllRegistrationNumbers(Constants.BIKE_CODE, ticketMap.entrySet());
		assertEquals(regNums.size(), 2);
		assertArrayEquals(regNums.toArray(), expected);
	}
	
	
}
