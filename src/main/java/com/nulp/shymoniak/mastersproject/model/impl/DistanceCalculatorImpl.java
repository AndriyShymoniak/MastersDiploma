package com.nulp.shymoniak.mastersproject.model.impl;

import com.nulp.shymoniak.mastersproject.constant.ApplicationConstants;
import com.nulp.shymoniak.mastersproject.dto.LocationDTO;
import com.nulp.shymoniak.mastersproject.model.DistanceCalculator;
import org.springframework.stereotype.Component;

@Component
public class DistanceCalculatorImpl implements DistanceCalculator {

    /**
     * Calculates distance between two locations
     * @param loc1 - location from
     * @param loc2 - location to
     * @return
     */
    @Override
    public Double calculateDistance(LocationDTO loc1, LocationDTO loc2) {
        Double lat1 = Double.parseDouble(loc1.getLatitude());
        Double lon1 = Double.parseDouble(loc1.getLongitude());
        Double lat2 = Double.parseDouble(loc2.getLatitude());
        Double lon2 = Double.parseDouble(loc2.getLongitude());
        return calculateUsingHaversineFormula(lat1, lon1, lat2, lon2);
    }

    /**
     * Calculates distance between two locations using Haversine formula:
     * a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
     * c = 2 ⋅ atan2( √a, √(1−a) )
     * d = R ⋅ c
     * @param lat1 location from latitude
     * @param lon1 location from longitude
     * @param lat2 location to latitude
     * @param lon2 location to longitude
     * @return
     */
    private Double calculateUsingHaversineFormula(Double lat1, Double lon1,
                                                  Double lat2, Double lon2) {
        Double phi1 = Math.toRadians(lat1);
        Double phi2 = Math.toRadians(lat2);
        Double deltaPhi = Math.toRadians(lat2 - lat1);
        Double deltaLambda = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return ApplicationConstants.EARTH_RADIUS * c;
    }
}