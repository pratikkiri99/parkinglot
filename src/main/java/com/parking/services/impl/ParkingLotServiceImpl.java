package com.parking.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parking.constants.Constants;
import com.parking.constants.Constants.VEHICLE_TYPE;
import com.parking.entities.ParkingLot;
import com.parking.entities.ParkingSlot;
import com.parking.entities.Ticket;
import com.parking.services.ParkingLotService;
import com.parking.util.ParkingUtility;

@Component
public class ParkingLotServiceImpl implements ParkingLotService, InitializingBean {

	ParkingLot parkingLot;
	
	Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
	
	@Autowired
	ParkingUtility parkingUtil;

	@Override
	public void afterPropertiesSet() throws Exception {
		parkingLot = new ParkingLot();
	}

	/**
	 * Returns all free slots irrespective of vehicle type
	 */
	@Override
	public List<String> getAllAvailableSlots() {
		List<String> availableSlots = new ArrayList<String>();
		availableSlots.addAll(parkingUtil.getAllEmptySpots(parkingLot, VEHICLE_TYPE.BIKE));
		availableSlots.addAll(parkingUtil.getAllEmptySpots(parkingLot, VEHICLE_TYPE.CAR));
		return availableSlots;
	}
	
	/**
	 * Parks the vehicle with given registration number
	 * Block of code has been synchonized so that no two vehicle can book the same slot 
	 */
	@Override
	public Ticket parkVehicle(VEHICLE_TYPE vehicleType, String registrationNumber) {
		
		Ticket ticket = new Ticket();
		ticket.setVehicleRegNumber(registrationNumber);
		ParkingSlot availableSlot = parkingUtil.getParkingSlotForVehicle(parkingLot, vehicleType);
		
		if(availableSlot != null) {
			
			synchronized (this) {
				availableSlot.setSlotUnavailabe();
				
				ticket.setInTime(new Date());
				ticket.setSlotNumber(availableSlot.getSlotNumber());
				ticket.setStatus("SUCCESS");
				
				ticketMap.put(availableSlot.getSlotNumber(), ticket);
			}
		} else {
			
			ticket.setStatus("No Slots Avaliable");
		}

		return ticket;
	}

	/**
	 * Takes input as vehicle type Bike or Car and returns list of all the vehicle parked
	 * in the parking lot 
	 */
	@Override
	public List<String> getAllRegistrationNumbers(VEHICLE_TYPE vehicleType) {
		
		List<String> registrationNums ;
		Set<Entry<String, Ticket>> ticketSet = ticketMap.entrySet();
		
		if(VEHICLE_TYPE.BIKE == vehicleType) {
			
			registrationNums = parkingUtil.getAllRegistrationNumbers(Constants.BIKE_CODE, ticketSet);
		} else {
			
			registrationNums = parkingUtil.getAllRegistrationNumbers(Constants.CAR_CODE, ticketSet);
		}
		return registrationNums;
	}

	/**
	 * for any given number plate, returns parking slot number
	 */
	@Override
	public String getParkingSlotNumber(String registrationNumber) {
		
		String parkingSlotNumber = "Parking Not Found";
		Set<Entry<String, Ticket>> ticketSet = ticketMap.entrySet();
		
		for (Iterator <Entry<String, Ticket>> iterator = ticketSet.iterator(); iterator.hasNext();) {
			Entry<String, Ticket> entry = iterator.next();
			String key = entry.getKey();
			String value = entry.getValue().getVehicleRegNumber();
			if(registrationNumber.trim().equalsIgnoreCase(value)) {
				parkingSlotNumber = key;
				return parkingSlotNumber;
			}
		}
		return parkingSlotNumber;
	}

	/**
	 * Unparks the vehicle, generates ticket and makes the slot available for use
	 */
	@Override
	public Ticket unparkVehicle(String registrationNum) {
		
		String parkingSlotNumber = null;
		Set<Entry<String, Ticket>> ticketSet = ticketMap.entrySet();
		Ticket t = null;
		one : for (Iterator <Entry<String, Ticket>> iterator = ticketSet.iterator(); iterator.hasNext();) {
			Entry<String, Ticket> entry = iterator.next();
			String key = entry.getKey();
			String value = entry.getValue().getVehicleRegNumber();
			if(registrationNum.trim().equalsIgnoreCase(value)) {
				parkingSlotNumber = key;
				t = entry.getValue();
				t.setOutTime(new Date());
				break one;
			}	
		}
		
		parkingUtil.markSlotAvailable(parkingLot, parkingSlotNumber);
		
		return t;
	}
	
	
}
