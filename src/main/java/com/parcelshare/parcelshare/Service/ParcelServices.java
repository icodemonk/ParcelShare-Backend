package com.parcelshare.parcelshare.Service;

import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Repository.ParcelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParcelServices {


    @Autowired
    ParcelRepo parcelRepo;

    @Autowired
    UserServices userServices;

    public String addParcel(Parcel parcel , int creatorid){
        parcel.setParcelcreaterid(creatorid);
        parcelRepo.save(parcel);
        return "Parcel added Sucessfully" ;
    }

}
