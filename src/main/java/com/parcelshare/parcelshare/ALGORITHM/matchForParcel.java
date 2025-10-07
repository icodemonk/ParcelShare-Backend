package com.parcelshare.parcelshare.ALGORITHM;


import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.Traveler;
import com.parcelshare.parcelshare.Repository.ParcelRepo;
import com.parcelshare.parcelshare.Repository.TravelerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class matchForParcel {

    @Autowired
    TravelerRepo repository;
    @Autowired
    MatchingService matchingService;
    @Autowired
    ParcelRepo parcelRepo;

    public List<Traveler> matchForParcel(int id){
        //all traveler to match
        List<Traveler> travelers = repository.findAll();

        Parcel parcel=parcelRepo.findByid(id);
        //filtered traveler
        List<Traveler> filTraveler =  matchingService.findMatchesForParcel(parcel ,travelers);
        return filTraveler;
    }

    public List<Parcel> matchForTraveler(int id){

        List<Parcel> parcels= parcelRepo.findAll();
        Traveler traveler=repository.findById(id);
        List<Parcel> filParcel= matchingService.findMatchesForTraveler(traveler , parcels);
        return filParcel;

    }

    public List<Traveler> allTravelers(){
        List<Traveler> travelers=new ArrayList<>();
        travelers= repository.findAll();
        return travelers;
    }


}
