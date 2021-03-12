package com.gridnine.testing.flights;


import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean that represents a flight.
 */
public class Flight {
    private static int count = 1;
    private final int index = count;
    private final List<Segment> segments;

    public Flight(final List<Segment> segs) {
        segments = segs;
        count++;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    public int getIndex() {
        return index;
    }
}

