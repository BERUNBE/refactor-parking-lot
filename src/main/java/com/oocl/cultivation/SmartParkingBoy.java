package com.oocl.cultivation;

import java.util.Comparator;
import java.util.List;

public class SmartParkingBoy extends ParkingBoy{

    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = getParkingLotWithMostAvailablePosition();

        if (parkingLot == null) {
            setLastErrorMessage(NOT_ENOUGH_POSITION);
            return null;
        }

        return parkingLot.addCar(car);
    }

    private ParkingLot getParkingLotWithMostAvailablePosition() {
        return getParkingLotList().stream()
                .filter(aParkingLot -> aParkingLot.getAvailableParkingPosition() > 0)
                .max(Comparator.comparing(ParkingLot::getAvailableParkingPosition))
                .orElse(null);
    }
}
