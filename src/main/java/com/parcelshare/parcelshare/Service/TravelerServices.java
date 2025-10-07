package com.parcelshare.parcelshare.Service;

import com.parcelshare.parcelshare.Model.Traveler;
import com.parcelshare.parcelshare.Repository.TravelerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelerServices {

    @Autowired
    TravelerRepo travelerRepo;
    @Autowired
    private UserServices userServices;

    public String addTraveler(Traveler traveler , int creatorid){
       traveler.setTravelercreatorid(creatorid);
        travelerRepo.save(traveler);
        return "Traveler added Sucessfully" ;
    }


}
