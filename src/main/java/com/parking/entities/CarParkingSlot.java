package com.parking.entities;

import com.parking.constants.Constants;

public class CarParkingSlot extends ParkingSlot {
	
    public CarParkingSlot(Level level, int num) {
    	this.level = level;
    	generateSlotNumber(num);
	}
	

	@Override
	public void generateSlotNumber(int num) {
		if(this.slotNumber == null)
		  slotNumber = Constants.CAR_CODE+num+Constants.LEVEL_CODE+level.levelNum;
	}

}
