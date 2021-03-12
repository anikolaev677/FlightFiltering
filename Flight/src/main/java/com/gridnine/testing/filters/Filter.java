package com.gridnine.testing.filters;

import com.gridnine.testing.flights.Flight;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * The implemented class in the name should contain the logic of the filter,
 * and at the end of the name there should be the word "Filter"!
 */
public interface Filter {
    LocalDateTime NOW = LocalDateTime.now();

    Predicate<Flight> getFilter();
}
