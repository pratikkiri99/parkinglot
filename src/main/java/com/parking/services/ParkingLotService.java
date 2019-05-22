package com.parking.services;

import java.util.List;

import com.parking.constants.Constants.VEHICLE_TYPE;
import com.parking.entities.Ticket;

public interface ParkingLotService {

	public List<String> getAllAvailableSlots();
	public Ticket parkVehicle(VEHICLE_TYPE vehicleType, String registrationNumber);
	public List<String> getAllRegistrationNumbers(VEHICLE_TYPE vehicleType);
	public String getParkingSlotNumber(String registrationNumber);
	public Ticket unparkVehicle(String registrationNum);
	
}
