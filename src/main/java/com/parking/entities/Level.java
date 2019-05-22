package com.parking.entities;

import java.util.Arrays;
import java.util.List;

public class Level {
	
	int levelNum;
	CarParkingSlot [] carParking = new CarParkingSlot [3];  // each level has 3 car slots
	BikeParkingSlot [] bikeParking = new BikeParkingSlot[6]; // each level has 6 bike slots
	
	public Level(int levelNum) {
		this.levelNum = levelNum;
		initializeCarParking();
		initializeBikeParking();
	}
	
	private void initializeCarParking() {
		for (int i = 0; i < carParking.length; i++) {
			carParking [i] = new CarParkingSlot(this, (i+1));
		}
	}
	
	private void initializeBikeParking() {
		for (int i = 0; i < bikeParking.length; i++) {
			bikeParking [i] = new BikeParkingSlot(this, (i+1));
		}
	}
	
    public List<CarParkingSlot> getCarParkingLots(){
		return Arrays.asList(carParking);
	}
	
    public List<BikeParkingSlot> getBikeParkingSlots(){
		return Arrays.asList(bikeParking);
	}
}
