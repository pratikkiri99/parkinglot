package com.parking.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.parking.constants.Constants.VEHICLE_TYPE;
import com.parking.entities.BikeParkingSlot;
import com.parking.entities.CarParkingSlot;
import com.parking.entities.Level;
import com.parking.entities.ParkingLot;
import com.parking.entities.ParkingSlot;
import com.parking.entities.Ticket;

@Component
public class ParkingUtility {
	
	/**
	 * Returns list of available free parking spots for given vehicle type
	 * @param parkingLot
	 * @param vehicleType
	 * @return
	 */
	public List<String> getAllEmptySpots(ParkingLot parkingLot, VEHICLE_TYPE vehicleType) {
		
		if(parkingLot != null) {
			
			List<Level> levelList = parkingLot.getAllLevels();
			
			if(vehicleType == VEHICLE_TYPE.CAR) {
				
				return getAllAvailableCarSpots(levelList);
			} else if (vehicleType == VEHICLE_TYPE.BIKE) {
				
				return getAllAvailableBikeSpots(levelList);
			} else
				
				return null;
		}
		return null;
	}
	
	/**
	 * Returns list of free slots for Car
	 * @param levelList
	 * @return
	 */
	private List<String> getAllAvailableCarSpots(List<Level> levelList){
		List<String> carSpots = new ArrayList<>();
		for (Iterator<Level> iterator = levelList.iterator(); iterator.hasNext();) {
			Level level = (Level) iterator.next();
			List<CarParkingSlot> carSlots = level.getCarParkingLots();
			carSpots.addAll(
					carSlots.stream().filter(carSlot->carSlot.isSlotAvailable()).
			        map(CarParkingSlot::getSlotNumber).collect(Collectors.toList())
			      );
		}
		return carSpots;
	}
	 
	/**
	 * Returns list of free slots for Bike
	 * @param levelList
	 * @return
	 */
	private List<String> getAllAvailableBikeSpots(List<Level> levelList){
		List<String> bikeSpots = new ArrayList<>();
		for (Iterator<Level> iterator = levelList.iterator(); iterator.hasNext();) {
			Level level = (Level) iterator.next();
			List<BikeParkingSlot> bikeSLots = level.getBikeParkingSlots();
			bikeSpots.addAll(
					bikeSLots.stream().filter(bikeSlot->bikeSlot.isSlotAvailable()).
			        map(BikeParkingSlot::getSlotNumber).collect(Collectors.toList())
			      );
		}
		return bikeSpots;
	}
	
	public ParkingSlot getParkingSlotForVehicle(ParkingLot parkingLot, VEHICLE_TYPE vehicleType) {
		return getFreeParkingSlotForVehicle(parkingLot, vehicleType);
	}
	
	/**
	 * Returns available free slot for the given vehicle type
	 * @param parkingLot
	 * @param vehicleType
	 * @return
	 */
	public ParkingSlot getFreeParkingSlotForVehicle(ParkingLot parkingLot, VEHICLE_TYPE vehicleType) {
		List<Level> levelList = parkingLot.getAllLevels();
		ParkingSlot availableSlot = null;
		
		for (Iterator<Level> iterator = levelList.iterator(); iterator.hasNext();) {
			Level level = (Level) iterator.next();
			
			if(VEHICLE_TYPE.BIKE == vehicleType) {
				
				List<BikeParkingSlot> bikeSlots = level.getBikeParkingSlots();
			    availableSlot = bikeSlots.stream().filter(bikeSlot->bikeSlot.isSlotAvailable()).
			    		                              findFirst().get();
			} else {
				List<CarParkingSlot> carSlots = level.getCarParkingLots();
				availableSlot = carSlots.stream().filter(carSlot->carSlot.isSlotAvailable()).
                        findFirst().get();
						
			} 
			
			if(availableSlot!=null)
				return availableSlot;
		}
		return availableSlot;
	}
	
	/**
	 * Returns list of parkingslotnumber and registration number for given pattern(Bike or Car)
	 * @param matchPattern
	 * @param ticketSet
	 * @return
	 */
	public List<String> getAllRegistrationNumbers(String matchPattern, Set<Entry<String, Ticket>> ticketSet){
		
		List<String> registrationNums = new ArrayList<String>();
		
		for (Iterator <Entry<String, Ticket>> iterator = ticketSet.iterator(); iterator.hasNext();) {
			Entry<String, Ticket> entry = iterator.next();
			String key = entry.getKey();
			String value = entry.getValue().getVehicleRegNumber();
			if(key.startsWith(matchPattern))
				registrationNums.add("["+key+","+value+"]");
		}
		
		return registrationNums;
	}
	
	/**
	 * Takes the slot number and marks it free 
	 * @param parkingLot
	 * @param slotNum
	 */
	public void markSlotAvailable(ParkingLot parkingLot, String slotNum) {
		
		List<Level> levelList = parkingLot.getAllLevels();

		for (Iterator<Level> iterator = levelList.iterator(); iterator.hasNext();) {
			Level level = (Level) iterator.next();

			List<BikeParkingSlot> bikeSlots = level.getBikeParkingSlots();
			Optional<BikeParkingSlot> optional = bikeSlots.stream().filter(bikeSlot->bikeSlot.getSlotNumber().equalsIgnoreCase(slotNum)).
					findFirst();
			if(optional.isPresent())
			     optional.get().setSlotAvailable();
			else {
			     	
				List<CarParkingSlot> carSlots = level.getCarParkingLots();
				Optional<CarParkingSlot> optional2 = carSlots.stream().filter(carSlot->carSlot.getSlotNumber().equalsIgnoreCase(slotNum)).
						findFirst();
				
				if(optional2.isPresent())
					optional2.get().setSlotAvailable();
			}			
		}
	}
	
}
