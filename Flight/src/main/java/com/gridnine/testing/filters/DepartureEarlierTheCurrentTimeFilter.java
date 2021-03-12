package com.gridnine.testing.filters;

import com.gridnine.testing.flights.Flight;
import com.gridnine.testing.flights.Segment;

import java.util.function.Predicate;

public class DepartureEarlierTheCurrentTimeFilter implements Filter {
    private final Predicate<Segment> segmentPredicate = segment -> segment.getDepartureDate().isBefore(NOW);
    private final Predicate<Flight> flightPredicate = flight -> flight.getSegments().parallelStream().allMatch(segmentPredicate);

    @Override
    public Predicate<Flight> getFilter() {
        return flightPredicate;
    }
}
