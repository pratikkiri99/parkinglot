package com.parking.entities;

import com.parking.constants.Constants.PARKING_SLOT_CONDITION;

public abstract class ParkingSlot {

	Level level;
	String slotNumber;
	PARKING_SLOT_CONDITION parkingSlotCondition = PARKING_SLOT_CONDITION.AVAILABLE;
	
	public boolean isSlotAvailable() {
		if(parkingSlotCondition == PARKING_SLOT_CONDITION.TAKEN)
			return false;
		else 
			return true;
	};

	
	public String getSlotNumber() {
		return this.slotNumber;
	}
	
	public void setSlotUnavailabe() {
		parkingSlotCondition = PARKING_SLOT_CONDITION.TAKEN;
	}
	
	public void setSlotAvailable() {
		parkingSlotCondition = PARKING_SLOT_CONDITION.AVAILABLE;
	}
	
	public abstract void generateSlotNumber(int num) ;
}
