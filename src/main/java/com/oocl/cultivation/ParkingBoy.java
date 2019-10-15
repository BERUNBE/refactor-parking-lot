package com.oocl.cultivation;

import java.util.List;

public class ParkingBoy {

    public static final String NOT_ENOUGH_POSITION = "Not enough position.";
    public static final String PLEASE_PROVIDE_YOUR_PARKING_TICKET = "Please provide your parking ticket.";
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";

    private final List<ParkingLot> parkingLotList;
    private String lastErrorMessage;

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = getFirstParkingLotWithAvailablePosition();

        if (parkingLot == null) {
            setLastErrorMessage(NOT_ENOUGH_POSITION);
            return null;
        }

        return parkingLot.addCar(car);
    }

    private ParkingLot getFirstParkingLotWithAvailablePosition() {
        return getParkingLotList().stream()
                .filter(aParkingLot -> aParkingLot.getAvailableParkingPosition() > 0)
                .findFirst()
                .orElse(null);
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
        ParkingLot parkingLot = getParkingLotFromListByTicket(ticket);
        Car car = parkingLot.getCars().get(ticket);
        parkingLot.removeCarWithTicket(ticket);
        return car;
    }

    private boolean isTicketFoundInAnyParkingLot(ParkingTicket ticket) {
        return parkingLotList.stream()
                .anyMatch(aParkingLot -> aParkingLot.getCars().containsKey(ticket));
    }

    private ParkingLot getParkingLotFromListByTicket(ParkingTicket ticket) {
        return parkingLotList.stream()
                .filter(aParkingLot -> aParkingLot.getCars().containsKey(ticket))
                .findFirst()
                .get();
    }
}
