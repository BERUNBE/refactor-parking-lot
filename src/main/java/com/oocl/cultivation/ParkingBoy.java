package com.oocl.cultivation;

import java.util.List;

import static com.oocl.cultivation.ParkingBoyConstants.*;

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
        ParkingLot parkingLot = getParkingLotList().stream()
                .filter(aParkingLot -> aParkingLot.getAvailableParkingPosition() > 0)
                .findFirst()
                .orElse(null);

        if (parkingLot == null) {
            setLastErrorMessage(NOT_ENOUGH_POSITION);
            return null;
        }

        return parkingLot.addCar(car);
    }

    public Car fetch(ParkingTicket ticket) {
        if (ticket == null) {
            setLastErrorMessage(PLEASE_PROVIDE_YOUR_PARKING_TICKET);
            return null;
        }
        if (!isTicketFoundInAnyParkingLot(ticket)) {
            setLastErrorMessage(UNRECOGNIZED_PARKING_TICKET);
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
