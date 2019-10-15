package com.oocl.cultivation;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SuperSmartParkingBoy extends ParkingBoy{

    public SuperSmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = getParkingLotWithMostAvailablePositionRate();

        if (parkingLot == null) {
            setLastErrorMessage(NOT_ENOUGH_POSITION);
            return null;
        }

        return parkingLot.addCar(car);
    }

    private ParkingLot getParkingLotWithMostAvailablePositionRate() {
        return getParkingLotList().stream()
                .filter(aParkingLot -> aParkingLot.getAvailableParkingPosition() > 0)
                .max(Comparator.comparing(getParkingLotAvailablePositionRate()))
                .orElse(null);
    }

    private Function<ParkingLot, Integer> getParkingLotAvailablePositionRate() {
        return aParkingLot -> aParkingLot.getAvailableParkingPosition() / aParkingLot.getCapacity();
    }
}
