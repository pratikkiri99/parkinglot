package com.parking;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.parking.entities.ParkingLot;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingLotApplicationTests {
	
	@Before
	public void setUp() {
		ParkingLot parkingLot = new ParkingLot();
	}



}
