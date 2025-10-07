package com.parcelshare.parcelshare.Model;


import com.parcelshare.parcelshare.Model.ENUM.ParcelMatchTraveler;
import com.parcelshare.parcelshare.Model.ENUM.ParcelMatchedCourier;
import com.parcelshare.parcelshare.Model.ENUM.Stat;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ParcelMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private ParcelMatchTraveler traveler = ParcelMatchTraveler.NOTACCEPTED;

    private int parcelid;

    @Enumerated(EnumType.STRING)
    private ParcelMatchedCourier courier= ParcelMatchedCourier.NODATA;

    private int travelerid;

    @Enumerated(EnumType.STRING)
    private Stat status= Stat.PENDING;

}
