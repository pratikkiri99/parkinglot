package com.parking.entities;

import com.parking.constants.Constants;

public class BikeParkingSlot extends ParkingSlot {
	
	public BikeParkingSlot(Level level, int num) {
		this.level = level;
		generateSlotNumber(num);
	}
	

	@Override
	public void generateSlotNumber(int num) {
		if(this.slotNumber == null)
			  slotNumber = Constants.BIKE_CODE+num+Constants.LEVEL_CODE+level.levelNum;
	}

}
