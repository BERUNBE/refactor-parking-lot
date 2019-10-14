package com.oocl.cultivation;

import java.util.List;

public class ParkingBoy {

    private final List<ParkingLot> parkingLotList;
    private String lastErrorMessage;

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public ParkingTicket park(Car car) {
        for(int i = 0; i < parkingLotList.size(); i++) {
            ParkingLot parkingLot = parkingLotList.get(i);
            if (parkingLot.getAvailableParkingPosition() > 0){
                return parkingLot.addCar(car);
            }
        }
        setLastErrorMessage("Not enough position.");
        return null;
    }

    public Car fetch(ParkingTicket ticket) {
        if (ticket == null) {
            setLastErrorMessage("Please provide your parking ticket.");
            return null;
        }
        if (!isTicketFoundInAnyParkingLot(ticket)) {
            setLastErrorMessage("Unrecognized parking ticket.");
            return null;
        }
        ParkingLot parkingLot = parkingLotList.stream()
                .filter(aParkingLot -> aParkingLot.getCars().containsKey(ticket))
                .findFirst()
                .get();
        Car car = parkingLot.getCars().get(ticket);
        parkingLot.removeCarWithTicket(ticket);
        return car;
    }

    private boolean isTicketFoundInAnyParkingLot(ParkingTicket ticket) {
        return parkingLotList.stream()
                .anyMatch(aParkingLot -> aParkingLot.getCars().containsKey(ticket));
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }
}
