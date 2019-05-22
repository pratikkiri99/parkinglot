package com.parking.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parking.constants.Constants.VEHICLE_TYPE;
import com.parking.entities.Ticket;
import com.parking.services.ParkingLotService;

@RestController
@RequestMapping("/parking")
public class ParkingController {
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);
	
	@Autowired
	ParkingLotService parkingService;
	

	@RequestMapping("/greeting")
	public String greeting() {
		return "Welcome to Victor Parking. "
			+ " <p>We are 3 story parking with each floor having 3 slots for cars and 6 slots for bikes</p>";
	}
	
	@RequestMapping("/getAllAvailableSlots")
	public List<String> getAllAvailableSlots() {
		return parkingService.getAllAvailableSlots();
	}
	
	@GetMapping("/parkVehicle/{vehicleType}")
	public Ticket parkVehicle(@PathVariable String vehicleType,
							@RequestParam ("registrationNumber")String registrationNumber) {
		logger.info("vehicleType->"+vehicleType+",registrationNumber->"+registrationNumber);
		Ticket  ticket = null;
		if("bike".equalsIgnoreCase(vehicleType)) {
			
			ticket = parkingService.parkVehicle(VEHICLE_TYPE.BIKE, registrationNumber);
		} else if("car".equalsIgnoreCase(vehicleType)) {
			
			ticket = parkingService.parkVehicle(VEHICLE_TYPE.CAR, registrationNumber);
		} else {
			
			ticket = new Ticket();
			ticket.setVehicleRegNumber(registrationNumber);
			ticket.setStatus("Invalid Vehicle type. Enter bike or car.");
		}
	   return ticket;
	}
	
	@GetMapping("/getAllRegistrationNumbers/{vehicleType}")
	public List<String> getAllRegistrationNumbers(@PathVariable String vehicleType) {
		
		logger.info("vehicleType->"+vehicleType);
		List<String> vehicleDetail = null;
		if("bike".equalsIgnoreCase(vehicleType)) {
			
			vehicleDetail = parkingService.getAllRegistrationNumbers(VEHICLE_TYPE.BIKE);
		} else if("car".equalsIgnoreCase(vehicleType)) {
			
			vehicleDetail = parkingService.getAllRegistrationNumbers(VEHICLE_TYPE.CAR);
		} else {
			
			vehicleDetail = new ArrayList<>();
			vehicleDetail.add("Invalid Vehicle type. Enter bike or car");
		}
	   return vehicleDetail;
	}
	
	@GetMapping("/getParkingSlotNumber")
	public String getParkingSlotNumber(@RequestParam ("registrationNumber")String registrationNumber) {
		
		String parkingSlotNum = null;
		if(StringUtils.hasText(registrationNumber)) {
			parkingSlotNum = parkingService.getParkingSlotNumber(registrationNumber);
		}
		
		return parkingSlotNum;
	}
	
	@GetMapping("/unparkVehicle")
	public Ticket unparkVehicle(@RequestParam ("registrationNumber")String registrationNumber) {
		logger.info("registrationNumber->"+registrationNumber);
		Ticket  ticket = null;
		
		if(StringUtils.hasText(registrationNumber)) {
			ticket = parkingService.unparkVehicle(registrationNumber);
		} else {
			ticket = new Ticket();
			ticket.setStatus("Invalid Registration Number");
		}
		return ticket;
	}
}
