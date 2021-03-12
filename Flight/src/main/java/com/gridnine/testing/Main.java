package com.gridnine.testing;

import com.gridnine.testing.filters.ArrivalDateEarlierThenDepartureDateFilter;
import com.gridnine.testing.filters.DepartureEarlierTheCurrentTimeFilter;
import com.gridnine.testing.filters.TotalTimeBetweenFlightsIsMoreTwoHoursFilter;
import com.gridnine.testing.flightClient.FlightClient;
import com.gridnine.testing.flights.Flight;
import com.gridnine.testing.flights.FlightBuilder1;
import service.FilterChain;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        long t0 = System.nanoTime();
        final List<Flight> flights = FlightBuilder1.createFlights();
        FilterChain filterChain = new FilterChain(flights);
        filterChain.setFilters(
                new ArrivalDateEarlierThenDepartureDateFilter(),
                new DepartureEarlierTheCurrentTimeFilter(),
                new TotalTimeBetweenFlightsIsMoreTwoHoursFilter()
        );
        FlightClient flightClient = new FlightClient(filterChain);
        flightClient.showFlightList();
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("List sort took: %d ms", millis));
    }
}
