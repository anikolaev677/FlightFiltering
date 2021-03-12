package com.gridnine.testing.filters;

import com.gridnine.testing.flights.Flight;

import java.util.function.Predicate;

public class FilterPredicateImpl implements Predicate<Flight> {
    @Override
    public boolean test(Flight flight) {
        return false;
    }

    @Override
    public Predicate<Flight> and(Predicate<? super Flight> other) {
        return null;
    }

    @Override
    public Predicate<Flight> negate() {
        return null;
    }

    @Override
    public Predicate<Flight> or(Predicate<? super Flight> other) {
        return null;
    }
}
