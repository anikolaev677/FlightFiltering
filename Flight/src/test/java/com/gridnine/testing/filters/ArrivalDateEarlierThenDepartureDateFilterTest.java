package com.gridnine.testing.filters;

import com.gridnine.testing.flights.Flight;
import com.gridnine.testing.flights.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrivalDateEarlierThenDepartureDateFilterTest {
    final Predicate<Segment> segmentPredicate = segment -> segment.getDepartureDate().isAfter(segment.getArrivalDate());
    final Predicate<Flight> flightPredicate = flight -> flight.getSegments().parallelStream().allMatch(segmentPredicate);
    LocalDateTime now = LocalDateTime.now();
    Segment segment = new Segment(now, now.minusDays(2));
    List<Segment> segments = new ArrayList<>();
    Flight flight = new Flight(segments);

    @Test
    void testFiltering() {
        segments.add(segment);
        boolean b = flightPredicate.test(flight);
        assertTrue(b);

    }

}