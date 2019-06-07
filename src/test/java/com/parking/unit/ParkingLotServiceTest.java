package com.parking.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parking.constants.Constants.VEHICLE_TYPE;
import com.parking.entities.ParkingLot;
import com.parking.entities.Ticket;
import com.parking.services.impl.ParkingLotServiceImpl;
import com.parking.util.ParkingUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingLotServiceTest {

	ParkingLot parkingLot;

	Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
	
	@InjectMocks
	ParkingLotServiceImpl parkingService;
	
	@Mock
	ParkingUtility parkingUtil;
	
	@Before
	public void setUp() {
	   parkingLot = new ParkingLot();
	}
	
	@Test
	public void testGetAllAvailableSlots_Null() {
		
		when(parkingUtil.getAllEmptySpots(any(ParkingLot.class), any())).thenReturn(null);
		
		List<String> slots = parkingService.getAllAvailableSlots();
		assertEquals(slots.size(), 0);
	}
	
	
	@Test
	public void testParkVehicle_NoSpotAvailabe() {
		
		when(parkingUtil.getParkingSlotForVehicle(any(ParkingLot.class), any(VEHICLE_TYPE.class))).thenReturn(null);
		
		Ticket t = parkingService.parkVehicle(VEHICLE_TYPE.CAR, "RegNo1");
		assertEquals(t.getStatus(), "No Slots Avaliable");
	}
	
	@Test
	public void testUnParkVehicle_WithoutParking() {
		
		Ticket t = parkingService.unparkVehicle("RegNo1");
		assertNull(t);
	}
	
}
