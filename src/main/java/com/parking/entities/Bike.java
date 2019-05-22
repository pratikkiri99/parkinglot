package com.parking.entities;

import com.parking.constants.Constants.VEHICLE_TYPE;

public class Bike extends Vehicle {

	private Bike() {};
	
	public Bike(String registrationNum) {
		this.registrationNum = registrationNum;
		this.vehicleType = VEHICLE_TYPE.BIKE;
	}
	
	@Override
	public boolean isSlotAvailabe() {
		return false;
	}

}
