package com.gridnine.testing.flightClient;

import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.flights.Flight;
import service.FilterChain;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class FlightClient {
    private final FilterChain filterChain;
    private boolean isUnknownFilterNumber = false;

    public FlightClient(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public void showFlightList() {
        System.out.println("Select the necessary filters in the list of flights and enter their numbers separated by a space:");
        System.out.println(parseFilterList().iterator().next());
        System.out.println("Enter numbers, separated by a space, to filter or press ENTER: ");
        AtomicInteger i = new AtomicInteger();
        StringBuffer outputErrors = new StringBuffer();
        try {
            Stream.of(filterChain.execute(getTarget()).stream().sequential())
                    .peek(flightStream -> Stream.of(flightStream)
                            .filter(flightStream1 -> isUnknownFilterNumber())
                            .forEach(flightStream1 -> outputErrors
                                    .append("Incorrect filter input!\nTry again...")))
                    .peek(flightStream -> Stream.of(flightStream)
                            .filter(flightStream2 -> filterChain.isFiltersListEmpty())
                            .forEach(flightStream2 -> outputErrors.append("Flight list is empty...")))
                    .peek(flightStream -> System.out.println(outputErrors.toString()))
                    .filter(flightStream -> outputErrors.length() == 0)
                    .forEach(flightStream -> ((Stream<Flight>) flightStream)
                            .peek(flight -> System.out.println("-----------------------------------------------------------------|"))
                            .peek(flight -> System.out.println("Flight № " + flight.getIndex()
                                    + ":"
                                    + printingSameLineLength(String.valueOf(flight.getIndex()).length(), 9)
                                    + "|    Departure date    |     Arrival date     |"))
                            .map(Flight::getSegments)
                            .forEach(s -> s
                                    .stream()
                                    .peek(segment -> System.out.print("-  Segment № "
                                            + (s.indexOf(segment) + 1)
                                            + printingSameLineLength(String.valueOf(s.indexOf(segment) + 1).length(), 6)
                                            + "|   "
                                            + segment.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm   |   "))))
                                    .forEach(segment
                                            -> System.out.println(segment.getArrivalDate()
                                            .format(DateTimeFormatter
                                                    .ofPattern("yyyy-MM-dd'T'HH:mm   |"))))
                            ));
        } catch (Exception e) {
            System.out.println("An error occurred while filtering! " + e.getMessage());
        }
    }

    public ArrayList<String> parseFilterList() {
        List<Filter> filtersList = (List<Filter>) filterChain.getFilters();
        ArrayList<String> nameFilters = new ArrayList<>();
        StringBuffer filtersBuffer = new StringBuffer();

        Stream.of(filtersList)
                .forEach(filters -> filters
                        .stream()
                        .peek(filter -> filtersBuffer.append("   ").append(filters.indexOf(filter) + 1).append(" - "))
                        .forEach(filter -> Stream.of(Arrays.asList(filter.getClass().getSimpleName().split("(?=\\p{Lu}.)")))
                                .peek(strings -> strings
                                        .stream()
                                        .filter(s -> strings.indexOf(s) == 0)
                                        .forEach(s -> filtersBuffer.append(s).append(" ")))
                                .peek(strings -> strings
                                        .stream()
                                        .filter(s -> strings.indexOf(s) != 0 && strings.indexOf(s) != strings.size() - 1)
                                        .map(String::toLowerCase)
                                        .forEach(s -> filtersBuffer.append(s).append(" ")))
                                .forEach(strings -> strings
                                        .stream()
                                        .filter(s -> strings.indexOf(s) == strings.size() - 1)
                                        .map(String::toLowerCase)
                                        .forEach(s -> filtersBuffer.append(s).append(";\n")))));
        nameFilters.add(filtersBuffer.toString());
        return nameFilters;
    }

    public List<Integer> getTarget() {
        InputScanner scanner = new InputScanner();
//        Stream<Flight> flightStream = filterChain.parallelStream();
        List<Integer> parsedLineList = scanner.getParsedLineList();
        List<Integer> targets = new ArrayList<>(parsedLineList.size());
        List<Filter> filters = (List<Filter>) filterChain.getFilters();

        if (scanner.isParsedLineSuccess()) {
            for (int i = 0; i < filters.size(); i++) {
                for (int indexParsedLine : parsedLineList) {
                    if (filters.indexOf(filters.get(i)) == indexParsedLine - 1) {
                        targets.add(i);
                    }
                    if (filters.size() < indexParsedLine) {
                        isUnknownFilterNumber = true;
                        break;
                    }
                }
            }
        } else {
            isUnknownFilterNumber = true;
        }
        return targets;
    }

    public boolean isUnknownFilterNumber() {
        return isUnknownFilterNumber;
    }

    private String printingSameLineLength(int numberSize, int charSize) {
        String space = " ";
        int count = charSize - numberSize;
        StringBuilder buffer = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            buffer.append(space);
        }
        return buffer.toString();
    }
}
