package com.parking.parkinglot.common;

public class CarDto {
    Long id;
    String licensePlate;
    String parkingSpot;
    String ownerName;

    public CarDto(String ownerName, String parkingSpot, String licensePlate, Long id) {
        this.ownerName = ownerName;
        this.parkingSpot = parkingSpot;
        this.licensePlate = licensePlate;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
