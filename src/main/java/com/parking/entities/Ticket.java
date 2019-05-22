package com.parking.entities;

import java.util.Date;

import org.springframework.stereotype.Repository;

@Repository
public class Ticket {

	String slotNumber;
	String vehicleRegNumber;
	Date inTime;
	Date outTime;
	String status;
	
	public Ticket() {};
	
	public Ticket(String slotNumber, String vehicleRegNumber) {
		this.slotNumber = slotNumber;
		this.vehicleRegNumber = vehicleRegNumber;
		inTime = new Date();
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	
	
}
