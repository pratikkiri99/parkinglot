package com.parking.entities;

import java.util.Arrays;
import java.util.List;

public class ParkingLot {

	Level levels [] = new Level [3]; // there are three levels in the Parking lot
	
	public ParkingLot() {
		for (int i = 0; i < levels.length; i++) {
			levels [i] = new Level(i+1);
		}
	}
	
	public List<Level> getAllLevels(){
		return Arrays.asList(levels);
	}
	
}
