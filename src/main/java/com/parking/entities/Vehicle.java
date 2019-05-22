package com.parking.entities;

import com.parking.constants.Constants.VEHICLE_TYPE;

public abstract class Vehicle {

	protected String registrationNum;
	protected VEHICLE_TYPE vehicleType;
	
	public abstract boolean isSlotAvailabe();
}
