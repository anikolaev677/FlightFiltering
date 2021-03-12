package com.gridnine.testing.flights;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightBuilder1 {
    static int size = 150;
    static List<Flight> flights = new ArrayList<>(size);

    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        for (int i = 0; i < size; i++) {
            if (i < 50) {
                flights.add(createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(2)));
            }
            if (i >= 50 && i < 100) {
                flights.add(createFlight(threeDaysFromNow.minusDays(4), threeDaysFromNow.minusDays(4).plusHours(2)));
            }
            if (i >= 100) {
//                flights.add(createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(2)));

                flights.add(createFlight(threeDaysFromNow.minusDays(4), threeDaysFromNow.minusDays(5).minusHours(2),
                        threeDaysFromNow.minusDays(5).plusHours(4), threeDaysFromNow.minusDays(5).minusHours(3),
                        threeDaysFromNow.minusDays(4).plusMinutes(1), threeDaysFromNow.minusDays(4).minusHours(9)));
            }
        }
        return flights;
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
