package com.parcelshare.parcelshare.ALGORITHM;

import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.Traveler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {

    private static final double MAX_DISTANCE_KM = 25; // max distance tolerance
    private static final int WEIGHT_MARGIN = 5;        // kg buffer

    // Public method to find travelers for a parcel
    public List<Traveler> findMatchesForParcel(Parcel parcel, List<Traveler> travelers) {
        List<Traveler> matches = new ArrayList<>();

        for (Traveler traveler : travelers) {
            if (isRouteMatching(parcel, traveler)) {
                matches.add(traveler);
            }
        }
        return matches;
    }

    // Public method to find parcels for a traveler
    public List<Parcel> findMatchesForTraveler(Traveler traveler, List<Parcel> parcels) {
        List<Parcel> matches = new ArrayList<>();
        for (Parcel parcel : parcels) {
            if (isRouteMatching(parcel, traveler)) {
                matches.add(parcel);
            }
        }
        return matches;
    }

    // Core matching logic
    private boolean isRouteMatching(Parcel parcel, Traveler traveler) {
        // Distance from parcel pickup to traveler's origin
        double pickupDistance = calculateDistance(
                parcel.getPickupLat(), parcel.getPickupLng(),
                traveler.getOriginLat(), traveler.getOriginLng()
        );

        // Distance from parcel drop to traveler's destination
        double dropDistance = calculateDistance(
                parcel.getDestinationLat(), parcel.getDestinationLng(),
                traveler.getDestinationLat(), traveler.getDestinationLng()
        );

        boolean routeMatch = pickupDistance <= MAX_DISTANCE_KM && dropDistance <= MAX_DISTANCE_KM;

        // Date and weight checks
        boolean dateOk = !parcel.getPickupDate().after(traveler.getOriginDate());
        boolean weightOk = parcel.getWeight() <= traveler.getWeight() + WEIGHT_MARGIN;

        return routeMatch && dateOk && weightOk;
    }

    // Haversine formula to calculate distance between two lat/lng points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distance in km
    }
}