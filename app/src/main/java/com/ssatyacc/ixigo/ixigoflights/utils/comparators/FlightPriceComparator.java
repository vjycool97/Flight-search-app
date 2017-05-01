package com.ssatyacc.ixigo.ixigoflights.utils.comparators;

import com.ssatyacc.ixigo.ixigoflights.model.Flight;

import java.util.Comparator;

/**
 * Created by vijay on 4/4/17.
 */

public class FlightPriceComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight o1, Flight o2) {
        if (o1.getMinFare().getFare() == o2.getMinFare().getFare()) {
            return 0;
        } else if (o1.getMinFare().getFare() > o2.getMinFare().getFare()) {
            return 1;
        }
        return -1;
    }
}
