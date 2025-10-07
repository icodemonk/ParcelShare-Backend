package com.parcelshare.parcelshare.Controller;


import com.parcelshare.parcelshare.ALGORITHM.matchForParcel;
import com.parcelshare.parcelshare.Model.ENUM.ParcelMatchTraveler;
import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.ParcelMatch;
import com.parcelshare.parcelshare.Model.Traveler;
import com.parcelshare.parcelshare.Repository.ParcelMatchRepo;
import com.parcelshare.parcelshare.Repository.ParcelRepo;
import com.parcelshare.parcelshare.Repository.TravelerRepo;
import com.parcelshare.parcelshare.Service.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service/")
public class ServiceController {


    private final ParcelRepo parcelRepo;
    private final TravelerRepo travelerRepo;
    private final matchForParcel matchForParcel;
    private final ParcelMatchRepo parcelMatchRepo;


//    @PreAuthorize("hasAuthority('ROLE_TRAVEL')")

    @PreAuthorize("hasAnyRole('PARCEL','ADMIN')")
    @PostMapping("addparcel")
    public String addParcel(@RequestBody Parcel parcel){

        parcelRepo.save(parcel);

        return "Parcel added successfully";

    }
    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @PostMapping("addtraveler")
    public String addTraveler(@RequestBody Traveler traveler){
        travelerRepo.save(traveler);
        return "Traveler added successfully";
    }

    @PreAuthorize("hasAnyRole('ADMIN','PARCEL')")
    @PostMapping("alo/parcel/{id}")
    public List<Traveler> matchForParcel(@PathVariable int id){
        List<Traveler> travelers = matchForParcel.matchForParcel(id);
        return  travelers;
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @PostMapping("algo/traveler/{id}/{currentuser}")
    public List<Parcel> matchforTraveler(@PathVariable int id, @PathVariable int currentuser) {
        List<Parcel> parcels = matchForParcel.matchForTraveler(id);
        List<ParcelMatch> parcelMatches = parcelMatchRepo.findByTravelerid(currentuser);
        List<Parcel> filterparcel = new ArrayList<>();

        if (parcelMatches.isEmpty()) {
            return parcels;
        }

        for (Parcel parcel : parcels) {
            int idParcel = parcel.getId();
            boolean alreadyAccepted = parcelMatches.stream()
                    .anyMatch(pm -> pm.getParcelid() == idParcel && pm.getTravelerid() == currentuser
                            && pm.getTraveler() != ParcelMatchTraveler.NOTACCEPTED);

                if (!alreadyAccepted) {
                    filterparcel.add(parcel);

        }

        }
        return filterparcel;
    }


}
