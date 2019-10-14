package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {

    @Test
    void parking_boy_should_park_car_into_the_parking_lot_and_return_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy bata = new ParkingBoy(asList(parkingLot));

        ParkingTicket papel = bata.park(new Car());

        assertThat(papel, is(notNullValue()));
    }

    @Test
    void parking_boy_should_fetch_car_after_receiving_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));
        Car car1 = new Car();
        ParkingTicket paper1 = utusan.park(car1);

        Car fetchedCar = utusan.fetch(paper1);

        assertThat(fetchedCar, is(car1));
    }

    @Test
    void parking_boy_should_fetch_multiple_cars_according_to_tickets() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));
        Car car1 = new Car();
        Car car2 = new Car();
        ParkingTicket paper1 = utusan.park(car1);
        ParkingTicket paper2 = utusan.park(car2);

        Car fetchedCar1 = utusan.fetch(paper1);
        Car fetchedCar2 = utusan.fetch(paper2);

        assertThat(fetchedCar1, is(car1));
        assertThat(fetchedCar2, is(car2));
    }

    @Test
    void parking_boy_should_fetch_no_car_if_given_invalid_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(new ParkingTicket());

        assertThat(fetchedCar, is(nullValue()));
    }

    @Test
    void parking_boy_should_fetch_no_car_if_given_no_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(null);

        assertThat(fetchedCar, is(nullValue()));
    }

    @Test
    void parking_boy_should_fetch_no_car_if_given_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));
        Car car = new Car();
        ParkingTicket ticket = utusan.park(car);

        Car fetchedCar = utusan.fetch(ticket);
        Car fetchedCarAgain = utusan.fetch(ticket);

        assertThat(fetchedCar, is(notNullValue()));
        assertThat(fetchedCarAgain, is(nullValue()));
    }

    @Test
    void parking_boy_should_not_park_car_if_exceed_capacity() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));

        addNumberOfCarsInParkingLot(10, parkingLot);

        ParkingTicket ticket = utusan.park(new Car());

        assertThat(ticket, is(nullValue()));
    }

    @Test
    void parking_boy_should_return_error_message_when_given_invalid_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(new ParkingTicket());

        assertThat(fetchedCar, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Unrecognized parking ticket."));
    }

    @Test
    void parking_boy_should_return_error_message_when_given_no_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(null);

        assertThat(fetchedCar, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Please provide your parking ticket."));
    }

    @Test
    void parking_boy_should_return_error_message_if_parking_lot_exceed_capacity() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot));

        addNumberOfCarsInParkingLot(10, parkingLot);

        ParkingTicket ticket = utusan.park(new Car());

        assertThat(ticket, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Not enough position."));
    }

    @Test
    void parking_boy_should_park_car_in_second_parking_lot_if_exceed_capacity_of_first() {
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingBoy utusan = new ParkingBoy(asList(parkingLot1, parkingLot2));

        addNumberOfCarsInParkingLot(10, parkingLot1);

        Car eleventhCar = new Car();
        utusan.park(eleventhCar);

        assertThat(parkingLot2.getCars().containsValue(eleventhCar), is(true));
    }

    @Test
    void smart_parking_boy_should_park_car_in_parking_lot_with_most_capacity_remaining() {
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot1, parkingLot2));

        addNumberOfCarsInParkingLot(9, parkingLot1);
        addNumberOfCarsInParkingLot(1, parkingLot2);

        Car eleventhCar = new Car();
        utusan.park(eleventhCar);

        assertThat(parkingLot2.getCars().containsValue(eleventhCar), is(true));
    }

    @Test
    void smart_parking_boy_should_park_car_into_the_parking_lot_and_return_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy bata = new SmartParkingBoy(asList(parkingLot));

        ParkingTicket papel = bata.park(new Car());

        assertThat(papel, is(notNullValue()));
    }

    @Test
    void smart_parking_boy_should_fetch_car_after_receiving_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));
        Car car1 = new Car();
        ParkingTicket paper1 = utusan.park(car1);

        Car fetchedCar = utusan.fetch(paper1);

        assertThat(fetchedCar, is(car1));
    }

    @Test
    void smart_parking_boy_should_fetch_multiple_cars_according_to_tickets() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));
        Car car1 = new Car();
        Car car2 = new Car();
        ParkingTicket paper1 = utusan.park(car1);
        ParkingTicket paper2 = utusan.park(car2);

        Car fetchedCar1 = utusan.fetch(paper1);
        Car fetchedCar2 = utusan.fetch(paper2);

        assertThat(fetchedCar1, is(car1));
        assertThat(fetchedCar2, is(car2));
    }

    @Test
    void smart_parking_boy_should_fetch_no_car_if_given_invalid_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(new ParkingTicket());

        assertThat(fetchedCar, is(nullValue()));
    }

    @Test
    void smart_parking_boy_should_fetch_no_car_if_given_no_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(null);

        assertThat(fetchedCar, is(nullValue()));
    }

    @Test
    void smart_parking_boy_should_fetch_no_car_if_given_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));
        Car car = new Car();
        ParkingTicket ticket = utusan.park(car);

        Car fetchedCar = utusan.fetch(ticket);
        Car fetchedCarAgain = utusan.fetch(ticket);

        assertThat(fetchedCar, is(notNullValue()));
        assertThat(fetchedCarAgain, is(nullValue()));
    }

    @Test
    void smart_parking_boy_should_not_park_car_if_exceed_capacity() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));

        addNumberOfCarsInParkingLot(10, parkingLot);

        ParkingTicket ticket = utusan.park(new Car());

        assertThat(ticket, is(nullValue()));
    }

    @Test
    void smart_parking_boy_should_return_error_message_when_given_invalid_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(new ParkingTicket());

        assertThat(fetchedCar, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Unrecognized parking ticket."));
    }

    @Test
    void smart_parking_boy_should_return_error_message_when_given_no_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(null);

        assertThat(fetchedCar, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Please provide your parking ticket."));
    }

    @Test
    void smart_parking_boy_should_return_error_message_if_parking_lot_exceed_capacity() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy utusan = new SmartParkingBoy(asList(parkingLot));

        addNumberOfCarsInParkingLot(10, parkingLot);

        ParkingTicket ticket = utusan.park(new Car());

        assertThat(ticket, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Not enough position."));
    }

    @Test
    void super_smart_parking_boy_should_park_car_in_parking_lot_with_largest_available_position_rate() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingLotJumbo parkingLotJumbo = new ParkingLotJumbo();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot, parkingLotJumbo));

        addNumberOfCarsInParkingLot(1, parkingLot);
        addNumberOfCarsInParkingLot(10, parkingLotJumbo);

        Car twelfthCar = new Car();
        utusan.park(twelfthCar);

        assertThat(parkingLot.getCars().containsValue(twelfthCar), is(true));
    }

    @Test
    void super_smart_parking_boy_should_park_car_into_the_parking_lot_and_return_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy bata = new SuperSmartParkingBoy(asList(parkingLot));

        ParkingTicket papel = bata.park(new Car());

        assertThat(papel, is(notNullValue()));
    }

    @Test
    void super_smart_parking_boy_should_fetch_car_after_receiving_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));
        Car car1 = new Car();
        ParkingTicket paper1 = utusan.park(car1);

        Car fetchedCar = utusan.fetch(paper1);

        assertThat(fetchedCar, is(car1));
    }

    @Test
    void super_smart_parking_boy_should_fetch_multiple_cars_according_to_tickets() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));
        Car car1 = new Car();
        Car car2 = new Car();
        ParkingTicket paper1 = utusan.park(car1);
        ParkingTicket paper2 = utusan.park(car2);

        Car fetchedCar1 = utusan.fetch(paper1);
        Car fetchedCar2 = utusan.fetch(paper2);

        assertThat(fetchedCar1, is(car1));
        assertThat(fetchedCar2, is(car2));
    }

    @Test
    void super_smart_parking_boy_should_fetch_no_car_if_given_invalid_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(new ParkingTicket());

        assertThat(fetchedCar, is(nullValue()));
    }

    @Test
    void super_smart_parking_boy_should_fetch_no_car_if_given_no_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(null);

        assertThat(fetchedCar, is(nullValue()));
    }

    @Test
    void super_smart_parking_boy_should_fetch_no_car_if_given_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));
        Car car = new Car();
        ParkingTicket ticket = utusan.park(car);

        Car fetchedCar = utusan.fetch(ticket);
        Car fetchedCarAgain = utusan.fetch(ticket);

        assertThat(fetchedCar, is(notNullValue()));
        assertThat(fetchedCarAgain, is(nullValue()));
    }

    @Test
    void super_smart_parking_boy_should_not_park_car_if_exceed_capacity() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));

        addNumberOfCarsInParkingLot(10, parkingLot);

        ParkingTicket ticket = utusan.park(new Car());

        assertThat(ticket, is(nullValue()));
    }

    @Test
    void super_smart_parking_boy_should_return_error_message_when_given_invalid_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(new ParkingTicket());

        assertThat(fetchedCar, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Unrecognized parking ticket."));
    }

    @Test
    void super_smart_parking_boy_should_return_error_message_when_given_no_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));

        Car fetchedCar = utusan.fetch(null);

        assertThat(fetchedCar, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Please provide your parking ticket."));
    }

    @Test
    void super_smart_parking_boy_should_return_error_message_if_parking_lot_exceed_capacity() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy utusan = new SuperSmartParkingBoy(asList(parkingLot));

        addNumberOfCarsInParkingLot(10, parkingLot);

        ParkingTicket ticket = utusan.park(new Car());

        assertThat(ticket, is(nullValue()));
        assertThat(utusan.getLastErrorMessage(), is("Not enough position."));
    }

    private void addNumberOfCarsInParkingLot(int numberOfCars, ParkingLot parkingLot) {
        for (int i = 0; i < numberOfCars; i++) {
            parkingLot.addCar(new Car());
        }
    }
}
