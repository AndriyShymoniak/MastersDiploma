package com.nulp.shymoniak.mastersproject.model;

import com.nulp.shymoniak.mastersproject.dto.LocationDTO;

public interface DistanceCalculator {
    Double calculateDistance(LocationDTO loc1, LocationDTO loc2);
}