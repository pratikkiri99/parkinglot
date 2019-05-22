package com.parking.entities;

import com.parking.constants.Constants.VEHICLE_TYPE;

public class Car extends Vehicle {

	private Car() {};
	
	public Car(String registrationNum) {
		this.registrationNum = registrationNum;
		this.vehicleType = VEHICLE_TYPE.CAR;
	}
	
	@Override
	public boolean isSlotAvailabe() {
		
		return false;
	}

}
