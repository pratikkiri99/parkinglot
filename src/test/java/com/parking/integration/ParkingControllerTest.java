package com.parking.integration;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.parking.constants.Constants.VEHICLE_TYPE;
import com.parking.entities.Ticket;
import com.parking.rest.ParkingController;
import com.parking.services.ParkingLotService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ParkingControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingControllerTest.class);
	
    private MockMvc mvc;
	
	@Mock
    private ParkingLotService parkingService;
	
	private ParkingController parkingController; 
	
	@Before
	public void setUp() {
		parkingController = new ParkingController();
		mvc = MockMvcBuilders.standaloneSetup(parkingController).build();
	}
	
	private Ticket getDummyTest() {
		
		Ticket ticket = new Ticket();
		
		ticket.setSlotNumber("Slot1");
		ticket.setVehicleRegNumber("Vehicle1");
		ticket.setInTime(new Date());
		ticket.setOutTime(new Date());
		ticket.setStatus("Success");
		
		return ticket;
	}
	
	private List<String> getAllRegistrationNum(){
		
		List<String> regNumList = new ArrayList<>();
		regNumList.add("vehicle1");
		regNumList.add("vehicle2");
		
		return regNumList;
	}
	
	@Test
	public void testParking_Ok() {
		
		Ticket ticket = getDummyTest();
		
		when(parkingService.parkVehicle(any(VEHICLE_TYPE.class), any(String.class))).thenReturn(ticket);
		
		try {
			mvc.perform(get("/parking/parkVehicle/{vehicleType}","bike")
					 .param("registrationNumber", "Vehicle1")
					 .contentType(MediaType.APPLICATION_JSON)
					 )
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.slotNumber", is("Slot1")))
			.andExpect(jsonPath("$.vehicleRegNumber", is("Vehicle1")))
			.andExpect(jsonPath("$.status", is("Success")));
		} catch (Exception e) {
			logger.error("Exception in testParking_Ok", e);
		}
	}
	
	@Test
	public void testAllRegistrationNumbers_Ok() {
		
		when(parkingService.getAllRegistrationNumbers(any(VEHICLE_TYPE.class))).
		                                thenReturn(getAllRegistrationNum());
		
		try {
			mvc.perform(get("/parking/getAllRegistrationNumbers/{vehicleType}","bike")
					 .contentType(MediaType.APPLICATION_JSON)
					 )
			.andExpect(status().isOk())
			.andExpect(content().json("{'data':[\"vehicle1\",\"vehicle2\"}"));
		} catch (Exception e) {
			logger.error("Exception in testAllRegistrationNumbers_Ok", e);
		}
	}
	
	@Test
	public void testGetSlotNumberForVehicle_Ok() {
		
		String slot = "slot1";
		when(parkingService.getParkingSlotNumber(any(String.class))).
		                                thenReturn("Slot1");
		
		try {
			MvcResult result = mvc.perform(get("/parking/getParkingSlotNumber/")
					 .param("registrationNumber", "Vehicle1")
					 .contentType(MediaType.APPLICATION_JSON)
					 )
			.andExpect(status().isOk()).andReturn();
			
			String content = result.getResponse().getContentAsString();
			assertEquals(slot, content);
		} catch (Exception e) {
			logger.error("Exception in testAllRegistrationNumbers_Ok", e);
		}
	}
	
	
}
