package com.oocl.cultivation;

import java.util.Comparator;
import java.util.List;

import static com.oocl.cultivation.ParkingBoyConstants.NOT_ENOUGH_POSITION;

public class SmartParkingBoy extends ParkingBoy{

    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = getParkingLotList().stream()
                .filter(aParkingLot -> aParkingLot.getAvailableParkingPosition() > 0)
                .max(Comparator.comparing(ParkingLot::getAvailableParkingPosition))
                .orElse(null);

        if (parkingLot == null) {
            setLastErrorMessage(NOT_ENOUGH_POSITION);
            return null;
        }

        return parkingLot.addCar(car);
    }
}
